package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBoughtDao;
import com.dangdang.digital.model.Bought;
import com.dangdang.digital.model.BoughtChapter;
import com.dangdang.digital.model.BoughtDetail;
import com.dangdang.digital.model.OrderDetail;
import com.dangdang.digital.model.OrderDetailChapter;
import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.service.IBoughtChapterService;
import com.dangdang.digital.service.IBoughtDetailService;
import com.dangdang.digital.service.IBoughtService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.vo.AddBoughtMessage;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.CreateBoughtDetailVo;
import com.dangdang.digital.vo.CreateBoughtVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;

@Service
public class BoughtServiceImpl extends BaseServiceImpl<Bought, Long> implements IBoughtService {

	@Resource
	private IBoughtDao boughtDao;

	@Resource
	private ICacheService cacheService;

	@Override
	public IBaseDao<Bought> getBaseDao() {
		return boughtDao;
	}

	@Resource
	private IBoughtDetailService boughtDetailService;

	@Resource
	private IBoughtChapterService boughtChapterService;

	@Override
	@SuppressWarnings("unchecked")
	public List<Bought> getBoughtListByCustId(Long custId, Integer start, Integer count,String fromPaltform) {
		if (start == null) {
			start = 0;
		}
		if (count == null) {
			count = Integer.MAX_VALUE;
		}
		fromPaltform = PayFromPaltform.getParentSource(fromPaltform).getSource();
		if(StringUtils.isBlank(fromPaltform)){
			fromPaltform = PayFromPaltform.YC.getSource();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("custId", custId);
		paramMap.put("fromPaltform", fromPaltform);
		return (List<Bought>) boughtDao.getSqlSessionQueryTemplate().selectList("BoughtMapper.getAllOrderByUpdateTime", paramMap,
				new RowBounds(start, count));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Long> getMyBoughtWholeMediaIds(Long custId, Date updateTime,String fromPaltform) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		fromPaltform = PayFromPaltform.getParentSource(fromPaltform).getSource();
		if(StringUtils.isBlank(fromPaltform)){
			fromPaltform = PayFromPaltform.YC.getSource();
		}
		paramMap.put("custId", custId);
		paramMap.put("updateTime", updateTime);
		paramMap.put("fromPaltform", fromPaltform);
		return (List<Long>) boughtDao.getSqlSessionQueryTemplate().selectList("BoughtMapper.getMyBoughtWholeMediaIds",
				paramMap);
	}

	@Override
	public void addBought(CreateOrderVo createOrderVo) throws Exception {
		if (createOrderVo == null || createOrderVo.getOrderMain() == null
				|| CollectionUtils.isEmpty(createOrderVo.getOrderMain().getOrderDetails())) {
			return;
		}
		// 封装已购信息map
		Map<Long, Bought> boughtMap = getBoughtMap(createOrderVo);
		// 保存已购信息
		saveBought(boughtMap);
	}

	/**
	 * 保存已购信息 Description:
	 * 
	 * @Version1.0 2015年1月4日 上午11:22:51 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtMap
	 */
	private void saveBought(Map<Long, Bought> boughtMap) {
		for (Bought bought : boughtMap.values()) {
			if (bought.getSaveType() == Bought.SAVE_TYPE_INSERT) {
				// 保存已购信息
				this.save(bought);
			} else {
				// 更新已购信息
				boughtDao.updateIncremental(bought);
			}
			// 新增已购详情和已购章节
			if (!CollectionUtils.isEmpty(bought.getBoughtDetailList())) {
				for (BoughtDetail boughtDetail : bought.getBoughtDetailList()) {
					boughtDetail.setBoughtId(bought.getBoughtId());
					// 保存已购详情
					boughtDetailService.save(boughtDetail);
					if (!CollectionUtils.isEmpty(boughtDetail.getBoughtChapterList())) {
						for (BoughtChapter boughtChapter : boughtDetail.getBoughtChapterList()) {
							boughtChapter.setBoughtDetailId(boughtDetail.getBoughtDetailId());
							// 保存已购章节信息
							boughtChapterService.save(boughtChapter);
						}
					}

				}
			}

		}
	}

	/**
	 * 
	 * Description: 封装已购信息map
	 * 
	 * @Version1.0 2015年1月4日 上午10:40:17 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param createOrderVo
	 * @return
	 * @throws Exception
	 */
	private Map<Long, Bought> getBoughtMap(CreateOrderVo createOrderVo) throws Exception {
		OrderMain orderMain = createOrderVo.getOrderMain();
		// 已购信息map
		Map<Long, Bought> boughtMap = new HashMap<Long, Bought>();
		List<OrderDetail> orderDetailList = orderMain.getOrderDetails();
		// 对订单详情 做循环
		for (OrderDetail orderDetail : orderDetailList) {
			// 获取购买书的信息
			Long saleId = orderDetail.getSaleInfoId();
			MediaSaleCacheVo mediaSale = cacheService.getMediaSaleFromCache(saleId);
			MediaCacheWholeVo media = mediaSale.getMediaList().get(0);
			Long mediaId = media.getMediaId();
			// 如果是全本购买
			if (orderMain.getWholeFlag() == Constans.ORDER_WHOLE_YES) {
				// 如果map中不存在
				if (!boughtMap.containsKey(mediaId)) {
					// 从数据库里查询
					Bought bought = this.getBoughtByMediaId(mediaId, createOrderVo.getCustId());
					if (bought == null) {
						// 如果数据库中也不存在，新建Bought
						bought = new Bought();
						bought.setAuthorPenName(media.getAuthorPenname());
						bought.setCustId(createOrderVo.getCustId());
						bought.setMediaId(mediaId);
						bought.setMediaTitle(media.getTitle());
						bought.setSaleId(saleId);
						bought.setWholeFlag(Bought.WHOLE_FLAG_MEDIA);
						bought.setSaveType(Bought.SAVE_TYPE_INSERT);
						bought.setFromPaltform(PayFromPaltform.YC.getSource());
					} else {
						// 如果数据库中存在，则作更新
						bought.setSaveType(Bought.SAVE_TYPE_UPDATE);
						// 如果已经有了单章权限，设置单章、全本都有
						if (Bought.WHOLE_FLAG_CHAPTER.equals(bought.getWholeFlag())) {
							bought.setWholeFlag(Bought.WHOLE_FLAG_BOTH);
						}
					}
					bought.setPayMainPrice(orderDetail.getPayMainPrice());
					bought.setPaySubPrice(orderDetail.getPaySubPrice());
					bought.setUpdateTime(new Date());
					boughtMap.put(mediaId, bought);

				} else {
					// map中已经存在，对金额做加法,其它字段不做操作
					Bought bought = boughtMap.get(mediaId);
					bought.setPayMainPrice(bought.getPayMainPrice() + orderDetail.getPayMainPrice());
					bought.setPaySubPrice(bought.getPaySubPrice() + orderDetail.getPaySubPrice());
					// 如果已经有了单章权限，设置单章、全本都有
					if (Bought.WHOLE_FLAG_CHAPTER.equals(bought.getWholeFlag())) {
						bought.setWholeFlag(Bought.WHOLE_FLAG_BOTH);
					}
				}
			} else {
				// 非全本购买
				// 如果map中不存在
				if (!boughtMap.containsKey(mediaId)) {
					// 从数据库里查询
					Bought bought = this.getBoughtByMediaId(mediaId, createOrderVo.getCustId());
					if (bought == null) {
						// 如果数据库中也不存在，新建Bought
						bought = new Bought();
						bought.setAuthorPenName(media.getAuthorPenname());
						bought.setCustId(createOrderVo.getCustId());
						bought.setMediaId(mediaId);
						bought.setMediaTitle(media.getTitle());
						bought.setSaleId(saleId);
						bought.setWholeFlag(Bought.WHOLE_FLAG_CHAPTER);
						bought.setSaveType(Bought.SAVE_TYPE_INSERT);
						bought.setFromPaltform(PayFromPaltform.YC.getSource());
					} else {
						// 如果数据库中存在
						bought.setSaveType(Bought.SAVE_TYPE_UPDATE);
						// 如果已经有了全本权限，设置单章、全本都有
						if (Bought.WHOLE_FLAG_MEDIA.equals(bought.getWholeFlag())) {
							bought.setWholeFlag(Bought.WHOLE_FLAG_BOTH);
						}
					}
					bought.setPayMainPrice(orderDetail.getPayMainPrice());
					bought.setPaySubPrice(orderDetail.getPaySubPrice());
					bought.setUpdateTime(new Date());
					// 新增已购详情
					this.addBoughtDetail(orderMain, orderDetail, bought, boughtMap, mediaId);
				} else {
					// map中已经存在，对金额做加法
					Bought bought = boughtMap.get(mediaId);
					bought.setPayMainPrice(bought.getPayMainPrice() + orderDetail.getPayMainPrice());
					bought.setPaySubPrice(bought.getPaySubPrice() + orderDetail.getPaySubPrice());
					// 如果已经有了全本权限，设置单章、全本都有
					if (Bought.WHOLE_FLAG_MEDIA.equals(bought.getWholeFlag())) {
						bought.setWholeFlag(Bought.WHOLE_FLAG_BOTH);
					}
					// 新增已购详情
					this.addBoughtDetail(orderMain, orderDetail, bought, boughtMap, mediaId);

				}
			}
		}
		return boughtMap;

	}

	/**
	 * 
	 * Description: 在已购信息中增加已购详情
	 * 
	 * @Version1.0 2015年1月4日 上午10:35:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param orderMain
	 * @param orderDetail
	 * @param bought
	 * @param boughtMap
	 * @throws Exception
	 */
	private void addBoughtDetail(OrderMain orderMain, OrderDetail orderDetail, Bought bought,
			Map<Long, Bought> boughtMap, Long mediaId) throws Exception {
		// 新建BoughtDetail
		BoughtDetail boughtDetail = new BoughtDetail();
		boughtDetail.setOrderNo(orderMain.getOrderNo());
		boughtDetail.setOrderTime(new Date());
		boughtDetail.setPayMainPrice(orderDetail.getPayMainPrice());
		boughtDetail.setPaySubPrice(orderDetail.getPaySubPrice());
		boughtDetail.setBoughtType("购买");
		if (bought.getBoughtId() != null) {
			boughtDetail.setBoughtId(bought.getBoughtId());
		}
		if (orderDetail.getOrderDetailChapters().size() == 1) {
			// 如果是单章购买
			boughtDetail.setOrderType(BoughtDetail.ORDER_TYPE_SINGLE);
			Long chapterId = orderDetail.getOrderDetailChapters().get(0).getChapterId();
			boughtDetail.setChapterId(chapterId);
			ChapterCacheBasicVo chapter = cacheService.getChapterBasicFromCache(chapterId);
			boughtDetail.setOrderContent("单章购买：" + chapter.getTitle());
		} else if (orderDetail.getOrderDetailChapters().size() > 1) {
			// 如果多章购买
			boughtDetail.setOrderType(BoughtDetail.ORDER_TYPE_MULTI);
			boughtDetail.setOrderContent("多章购买：购买" + orderDetail.getOrderDetailChapters().size() + "章");
			// 多章购买还需要创建已购章节信息
			List<Long> chapterIds = new ArrayList<Long>();
			for (OrderDetailChapter orderDetailChapter : orderDetail.getOrderDetailChapters()) {
				chapterIds.add(orderDetailChapter.getChapterId());
			}
			List<ChapterCacheBasicVo> chapters = cacheService.batchGetChapterBasicFromCache(chapterIds);
			Map<Long, ChapterCacheBasicVo> chapterMap = new HashMap<Long, ChapterCacheBasicVo>();
			for (ChapterCacheBasicVo chapter : chapters) {
				chapterMap.put(chapter.getId(), chapter);
			}
			List<BoughtChapter> boughtChapterList = new ArrayList<BoughtChapter>();
			for (OrderDetailChapter orderDetailChapter : orderDetail.getOrderDetailChapters()) {
				BoughtChapter boughtChapter = new BoughtChapter();
				Long chapterId = orderDetailChapter.getChapterId();
				boughtChapter.setChapterId(orderDetailChapter.getChapterId());
				boughtChapter.setPayMainPrice(orderDetailChapter.getPayMainPrice());
				boughtChapter.setPaySubPrice(orderDetailChapter.getPaySubPrice());
				if (chapterMap.containsKey(chapterId)) {
					boughtChapter.setChapterIndex(chapterMap.get(chapterId).getIndex());
					boughtChapter.setChapterTitle(chapterMap.get(chapterId).getTitle());
				}
				boughtChapterList.add(boughtChapter);
			}
			boughtDetail.setBoughtChapterList(boughtChapterList);

		} else {
			throw new Exception("订单详情数量错误");
		}
		bought.getBoughtDetailList().add(boughtDetail);
		boughtMap.put(mediaId, bought);
	}

	@Override
	public Bought getBoughtByMediaId(Long mediaId, Long custId) {
		if (mediaId == null || custId == null) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mediaId", mediaId);
		paramMap.put("custId", custId);
		
		List<Bought> boughtList = (List<Bought>)boughtDao.getSqlSessionTemplate().selectList("BoughtMapper.getAll", paramMap);
		
		if(boughtList!=null && boughtList.size()>0){
			return boughtList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Integer getBoughtCountByCustId(Long custId,String fromPaltform) {
		fromPaltform = PayFromPaltform.getParentSource(fromPaltform).getSource();
		if(StringUtils.isBlank(fromPaltform)){
			fromPaltform = PayFromPaltform.YC.getSource();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("custId", custId);
		paramMap.put("fromPaltform", fromPaltform);
		return boughtDao.getCount(paramMap);
	}

	@Override
	public void addBought(AddBoughtMessage addBoughtMessage) throws Exception {
		if (addBoughtMessage == null) {
			return;
		}
		// 封装已购信息map
		Map<Long, Bought> boughtMap = getBoughtMap(addBoughtMessage);
		// 保存已购信息
		saveBought(boughtMap);
	}

	/**
	 * 
	 * Description: 封装已购信息map
	 * 
	 * @Version1.0 2015年1月4日 上午10:40:17 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param createOrderVo
	 * @return
	 * @throws Exception
	 */
	private Map<Long, Bought> getBoughtMap(AddBoughtMessage addBoughtMessage) throws Exception {
		// 已购信息map
		Map<Long, Bought> boughtMap = new HashMap<Long, Bought>();
		Long mediaId = addBoughtMessage.getMediaId();
		Long saleId = addBoughtMessage.getSaleId();
		MediaCacheWholeVo media = null;
		if (mediaId != null) {
			media = cacheService.getMediaWholeFromCache(mediaId);
			if (saleId == null) {
				saleId = media.getSaleId();
			}
		} else if (saleId != null) {
			MediaSaleCacheVo mediaSale = cacheService.getMediaSaleFromCache(saleId);
			media = mediaSale.getMediaList().get(0);
		} else {
			throw new Exception("mediaId和saleId不能同时为空");
		}
		// 如果是全本
		if (addBoughtMessage.getWholeFlag() == Bought.WHOLE_FLAG_MEDIA) {
			// 从数据库里查询
			Bought bought = this.getBoughtByMediaId(mediaId, addBoughtMessage.getCustId());
			if (bought == null) {
				// 如果数据库中也不存在，新建Bought
				bought = new Bought();
				bought.setAuthorPenName(media.getAuthorPenname());
				bought.setCustId(addBoughtMessage.getCustId());
				bought.setMediaId(mediaId);
				bought.setMediaTitle(media.getTitle());
				bought.setSaleId(saleId);
				bought.setWholeFlag(Bought.WHOLE_FLAG_MEDIA);
				bought.setSaveType(Bought.SAVE_TYPE_INSERT);
				bought.setFromPaltform(PayFromPaltform.YC.getSource());
			} else {
				// 如果数据库中存在，则作更新
				bought.setSaveType(Bought.SAVE_TYPE_UPDATE);
				// 如果已经有了单章权限，设置单章、全本都有
				if (Bought.WHOLE_FLAG_CHAPTER.equals(bought.getWholeFlag())) {
					bought.setWholeFlag(Bought.WHOLE_FLAG_BOTH);
				}
			}
			bought.setPayMainPrice(0);
			bought.setPaySubPrice(0);
			bought.setUpdateTime(new Date());
			boughtMap.put(mediaId, bought);

		} else if (addBoughtMessage.getWholeFlag() == Bought.WHOLE_FLAG_CHAPTER) {
			// 非全本购买
			// 从数据库里查询
			Bought bought = this.getBoughtByMediaId(mediaId, addBoughtMessage.getCustId());
			if (bought == null) {
				// 如果数据库中也不存在，新建Bought
				bought = new Bought();
				bought.setAuthorPenName(media.getAuthorPenname());
				bought.setCustId(addBoughtMessage.getCustId());
				bought.setMediaId(mediaId);
				bought.setMediaTitle(media.getTitle());
				bought.setSaleId(saleId);
				bought.setWholeFlag(Bought.WHOLE_FLAG_CHAPTER);
				bought.setSaveType(Bought.SAVE_TYPE_INSERT);
				bought.setFromPaltform(PayFromPaltform.YC.getSource());
			} else {
				// 如果数据库中存在
				bought.setSaveType(Bought.SAVE_TYPE_UPDATE);
				// 如果已经有了全本权限，设置单章、全本都有
				if (Bought.WHOLE_FLAG_MEDIA.equals(bought.getWholeFlag())) {
					bought.setWholeFlag(Bought.WHOLE_FLAG_BOTH);
				}
			}
			bought.setPayMainPrice(0);
			bought.setPaySubPrice(0);
			bought.setUpdateTime(new Date());
			// 新增已购详情
			this.addBoughtDetail(addBoughtMessage, bought, boughtMap, mediaId);

		}
		return boughtMap;
	}

	/**
	 * 
	 * Description: 在已购信息中增加已购详情
	 * 
	 * @Version1.0 2015年1月4日 上午10:35:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param orderMain
	 * @param orderDetail
	 * @param bought
	 * @param boughtMap
	 * @throws Exception
	 */
	private void addBoughtDetail(AddBoughtMessage addBoughtMessage, Bought bought, Map<Long, Bought> boughtMap,
			Long mediaId) throws Exception {
		List<ChapterCacheBasicVo> chapterList = cacheService.batchGetChapterBasicFromCache(addBoughtMessage
				.getChapterIds());

		// 新建BoughtDetail
		BoughtDetail boughtDetail = new BoughtDetail();
		boughtDetail.setOrderTime(new Date());
		boughtDetail.setPayMainPrice(0);
		boughtDetail.setPaySubPrice(0);
		boughtDetail.setBoughtType(addBoughtMessage.getBoughtType().getName());
		if (bought.getBoughtId() != null) {
			boughtDetail.setBoughtId(bought.getBoughtId());
		}
		if (chapterList.size() == 1) {
			// 如果是单章购买
			boughtDetail.setOrderType(BoughtDetail.ORDER_TYPE_SINGLE);
			boughtDetail.setChapterId(chapterList.get(0).getId());
			boughtDetail.setOrderContent(addBoughtMessage.getBoughtType().getName() + " "
					+ addBoughtMessage.getBoughtContent() + " " + chapterList.get(0).getTitle());
		} else if (chapterList.size() > 1) {
			// 如果多章购买
			boughtDetail.setOrderType(BoughtDetail.ORDER_TYPE_MULTI);
			boughtDetail.setOrderContent(addBoughtMessage.getBoughtType().getName() + " "
					+ addBoughtMessage.getBoughtContent());
			// 多章购买还需要创建已购章节信息
			List<BoughtChapter> boughtChapterList = new ArrayList<BoughtChapter>();
			for (ChapterCacheBasicVo chapter : chapterList) {
				BoughtChapter boughtChapter = new BoughtChapter();
				Long chapterId = chapter.getId();
				boughtChapter.setChapterId(chapterId);
				boughtChapter.setPayMainPrice(0);
				boughtChapter.setPaySubPrice(0);
				boughtChapter.setChapterIndex(chapter.getIndex());
				boughtChapter.setChapterTitle(chapter.getTitle());
				boughtChapterList.add(boughtChapter);
			}
			boughtDetail.setBoughtChapterList(boughtChapterList);

		} else {
			throw new Exception("订单详情数量错误");
		}
		bought.getBoughtDetailList().add(boughtDetail);
		boughtMap.put(mediaId, bought);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,String> createAndSaveBought(CreateBoughtVo createBoughtVo) {
		Map<String,String> resultMap = new HashMap<String,String>();
		checkParmsForcreateAndSaveBought(createBoughtVo,resultMap);
		if(!resultMap.containsKey("errorMsg")){
			Bought bought = encapsulateBought(createBoughtVo);
			this.save(bought);
			if(!CollectionUtils.isEmpty(bought.getBoughtDetailList())){
				for(BoughtDetail boughtDetail : bought.getBoughtDetailList()){
					boughtDetail.setBoughtId(bought.getBoughtId());
					boughtDetailService.save(boughtDetail);
				}
			}
		}
		return resultMap;
	}

	private void checkParmsForcreateAndSaveBought(CreateBoughtVo createBoughtVo,Map<String,String> resultMap){
		if(createBoughtVo == null){
			resultMap.put("errorMsg", "创建已购记录时参数createBoughtVo为空");
			return;
		}
		if(createBoughtVo.getCustId() == null){
			resultMap.put("errorMsg", "创建已购记录时参数custId为空");
			return;
		}
		if(StringUtils.isBlank(createBoughtVo.getFromPaltform())){
			resultMap.put("errorMsg", "创建已购记录时参数fromPaltform为空");
			return;
		}
		if(StringUtils.isBlank(createBoughtVo.getOrderNo())){
			resultMap.put("errorMsg", "创建已购记录时参数orderNo为空");
			return;
		}
		if(createBoughtVo.getOrderTime() == null){
			resultMap.put("errorMsg", "创建已购记录时参数orderTime为空");
			return;
		}
		if(createBoughtVo.getPayMainPrice() == null && createBoughtVo.getPaySubPrice() == null){
			resultMap.put("errorMsg", "创建已购记录时参数payMainPrice和paySubPrice同时为空");
			return;
		}
		if(CollectionUtils.isEmpty(createBoughtVo.getCreateBoughtDetailVos())){
			resultMap.put("errorMsg", "创建已购记录时参数createBoughtDetailVos为空");
			return;
		}
	}
	
	private Bought encapsulateBought(CreateBoughtVo createBoughtVo){
		Bought bought = new Bought();
		bought.setCustId(createBoughtVo.getCustId());
		bought.setPayMainPrice(createBoughtVo.getPayMainPrice() != null ? createBoughtVo.getPayMainPrice() : 0);
		bought.setPaySubPrice(createBoughtVo.getPaySubPrice() != null ? createBoughtVo.getPaySubPrice() : 0);
		bought.setUpdateTime(createBoughtVo.getOrderTime());
		bought.setWholeFlag(Bought.WHOLE_FLAG_MEDIA);
		String formPlatForm = createBoughtVo.getFromPaltform();
		formPlatForm = PayFromPaltform.getParentSource(formPlatForm).getSource();
		if(StringUtils.isBlank(formPlatForm)){
			formPlatForm = PayFromPaltform.DS.getSource();
		}
		bought.setFromPaltform(formPlatForm);
		List<BoughtDetail> boughtDetailList = new ArrayList<BoughtDetail>();
		for(CreateBoughtDetailVo createBoughtDetailVo : createBoughtVo.getCreateBoughtDetailVos()){
			BoughtDetail boughtDetail = new BoughtDetail();
			boughtDetail.setBoughtType("购买");
			boughtDetail.setOrderNo(createBoughtVo.getOrderNo());
			boughtDetail.setOrderTime(createBoughtVo.getOrderTime());
			boughtDetail.setOrderType(BoughtDetail.ORDER_TYPE_MULTI);
			boughtDetail.setPayMainPrice(createBoughtDetailVo.getPayMainPrice());
			boughtDetail.setPaySubPrice(createBoughtDetailVo.getPaySubPrice());		
			boughtDetail.setAuthorPenName(createBoughtDetailVo.getAuthorPenName());
			boughtDetail.setMediaId(createBoughtDetailVo.getProductId());
			boughtDetail.setMediaTitle(createBoughtDetailVo.getTitle());
			boughtDetail.setUnitPrice(createBoughtDetailVo.getUnitPrice());
			boughtDetailList.add(boughtDetail);
		}		
		bought.setBoughtDetailList(boughtDetailList);
		return bought;
	}

	@Override
	public String mergeUserBought(Long oldCustId, Long newCustId,String boughtId) {
		if(StringUtils.isBlank(boughtId)){
			List<Bought> result = this.findMasterListByParams("custId",oldCustId);
			if(CollectionUtils.isEmpty(result)){
				return "";
			}
			StringBuffer boughtIdStr = new StringBuffer();
			for(Bought bought : result){
				boughtIdStr.append(bought.getBoughtId()).append(",");
			}
			boughtId = boughtIdStr.substring(0,boughtIdStr.length() - 1);
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("oldCustId", oldCustId);
		parameter.put("newCustId", newCustId);
		parameter.put("ids", boughtId);
		this.getBaseDao().update("BoughtMapper.mergeUserBought", parameter);
		return boughtId;	
	}

}
