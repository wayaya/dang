package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.facade.IRankListApiFacade;
import com.dangdang.digital.model.CustomerMediaHistory;
import com.dangdang.digital.model.RecommendRelation;
import com.dangdang.digital.service.ICustomerMediaHistoryService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.IRecommendRelationService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.utils.MediaCoverPicUrlUtil;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.view.CustomerMediaData;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 根据客户端传来的 mediaId 获得买了此商品的人都还买了哪些商品
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:36:02  by 魏嵩（weisong@dangdang.com）创建
 */

@Component("hapigetBuyAlsoBuyprocessor")
public class GetBuyAlsoBuyProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetBuyAlsoBuyProcessor.class);
	
	private static List<String> defaultReturnFields = new ArrayList<String>();
	
	static{
		defaultReturnFields.add("coverPic");
		defaultReturnFields.add("authorPenname");
		defaultReturnFields.add("title");
		defaultReturnFields.add("subTitle");
		defaultReturnFields.add("saleId");
	}

	@Resource
	private IRankListApiFacade rankListApiFacade;
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate; 
	
	@Resource
	private IMediaService mediaService;
	
	@Resource
	private IMediaSaleService mediaSaleService;
	
	@Resource
	private IRecommendRelationService recommendRelationService;
	
	@Resource
	private ICustomerMediaHistoryService customerMediaHistoryService;
	
	@Resource 
	IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		getBuyAlsoBuyByMediaId(request, response, sender);
	}

	
	/**
	 * 
	 * Description: 根据mediaId 获得买了此商品的人都还买了哪些商品
	 * @Version1.0 2014年12月8日 上午10:53:25 by 魏嵩（weisong@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @throws Exception
	 */
	private void getBuyAlsoBuyByMediaId(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		String mediaIdStr = request.getParameter("mediaId");
		String startStr = request.getParameter("start");
		String endStr = request.getParameter("end");
		String tokenStr = request.getParameter("token");
		Long custId = 0L;
		boolean anonymous = true;
		
		if(!StringUtils.isEmpty(tokenStr)){
			UserTradeBaseVo vo =  authorityApiFacade.getUserInfoByToken(tokenStr);
			if(! (vo==null || vo.getCustId()==null) ){
				custId = vo.getCustId();
				anonymous = false;
			}
//			custId = Long.parseLong(request.getParameter("custId"));
		}
		
		//验证mediaId是否合法
		if (!StringUtils.isNumeric(mediaIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_14001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14001.getErrorMessage(), response);
			return;
		}
		if (!StringUtils.isNumeric(startStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_14002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14002.getErrorMessage(), response);
			return;
		}
		if (!StringUtils.isNumeric(endStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_14003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14003.getErrorMessage(), response);
			return;
		}
		int start = 0;
		int end = 0;
		try{
			start = Integer.valueOf(startStr);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,start:" + startStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_14002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14002.getErrorMessage(), response);
			return;
		}
		
		try{
			end = Integer.valueOf(endStr);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,end:" + endStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_14003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14003.getErrorMessage(), response);
			return;
		}
			
		//需要return书的哪些可选字段，值为空表示返回所有 （mediaId必返回）
		String returnFields = request.getParameter("returnFields");
		List<String> returnFieldList = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(returnFields)){
			String[] array = Constans.commaSpliter.split(returnFields);
			for(String field: array){
				returnFieldList.add(field);
			}
		}else{
			returnFieldList.addAll(defaultReturnFields);
		}
		
		Long mediaId = null;
		try{
			mediaId = Long.valueOf(mediaIdStr);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaId:" + mediaIdStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_14001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14001.getErrorMessage(), response);
			return;
		}
		
		List<Map<String, Object>> buyAlsoBuyList = new ArrayList<Map<String, Object>>();
		
		//若缓存中没有，那么去数据库查，查完放缓存
		RecommendRelation recommendRelation = new RecommendRelation();
		recommendRelation.setRelationType((byte)1);
		recommendRelation.setMediaId(mediaId);
		recommendRelation.setCreationDate(DateUtil.getOnlyDay(new Date()));
		
		Query query = new Query();
		query.setPage(1);
		query.setPageSize(Constans.BUYAGAIN_RECOMMEND_NUMBER);
		List<MediaCacheBasicVo> buyAlsoBuyIdsAll  = recommendRelationService.getRelatedMediaListAndToCache(recommendRelation, query);
		
		if(buyAlsoBuyIdsAll.size()<Constans.BUYAGAIN_RECOMMEND_NUMBER){
			
			//取男频热销榜、女频热销榜补齐
			int supplementNumber = Constans.BUYAGAIN_RECOMMEND_NUMBER - buyAlsoBuyIdsAll.size();
			int eachSupplementNumber = SafeConvert.convertDoubleToInteger(Math.ceil((double)supplementNumber/2D));
			//从男频、女频热销榜各取出eachSupplementNumber个补齐
			//拿到男频女频的热销saleIds，根据saleId去查书的ids, 再补齐
			List<MediaCacheWholeVo> npList = rankListApiFacade.getRandomMediaListByTypeAndNumber("np_sale", eachSupplementNumber);
			List<MediaCacheWholeVo> vpList = rankListApiFacade.getRandomMediaListByTypeAndNumber("vp_sale", eachSupplementNumber);
			buildSupplementDataToList(buyAlsoBuyIdsAll, npList, vpList);
			
		}
		if(!anonymous){
			//去掉用户购买过的
			CustomerMediaHistory customerMediaHistory = new CustomerMediaHistory();
			customerMediaHistory.setCustId(custId);
			Date creationDate = DateUtil.getOnlyDay(new Date());
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.HOUR_OF_DAY)<=4){
				creationDate = DateUtil.addDate(creationDate, -1);
			}
			
			customerMediaHistory.setCreationDate(creationDate);
			customerMediaHistory.setType((byte)1);
			
			CustomerMediaHistory history = customerMediaHistoryService.getHistory(customerMediaHistory);
			if(history!=null){
				CustomerMediaData custData = JSON.parseObject(history.getMediaData(), CustomerMediaData.class );
				if(custData!=null && custData.getHistory() !=null){
					this.removeBuyHistory(buyAlsoBuyIdsAll, custData.getHistory());
				}
			}
		}
		
		this.removeDupAndCurrentBook(buyAlsoBuyIdsAll, mediaId);
		
		//如果非意外情况，不可能发生此if
		if(buyAlsoBuyIdsAll == null || buyAlsoBuyIdsAll.size() ==0){
			sender.put("mediaList", buyAlsoBuyList);
			sender.put("total", 0);
			sender.put("timeCost", System.currentTimeMillis()-startTime);
			sender.success(response);
			return;
		}
		
		//endIndex is not included for subList
		end+=1;
		int endIndex = buyAlsoBuyIdsAll.size() >= end?end:buyAlsoBuyIdsAll.size();
		
		int startIndex = start;
		
		List<MediaCacheBasicVo> buyAlsoBuyIds = buyAlsoBuyIdsAll.subList(startIndex, endIndex);
		
		//在缓存里取出media的基础信息和内置销售主体 
//		Map<Long, MediaCacheBasicVo> mediaBasicInfoCacheMap = mediaService.getMediaBasicMap(buyAlsoBuyIds);
		
		for (MediaCacheBasicVo media : buyAlsoBuyIds) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mediaId", media.getMediaId());
			
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("coverPic"))){
				map.put("coverPic", MediaCoverPicUrlUtil.getMediaCoverPic(media.getMediaId()));
			}
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("authorPenname"))){
				map.put("authorPenname", media.getAuthorPenname());
			}
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("title"))){
				map.put("title", media.getTitle());
			}
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("subTitle"))){
				map.put("subTitle", media.getSubTitle());
			}
			
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("saleId"))){
				map.put("saleId", media.getSaleId());
			}
			
			buyAlsoBuyList.add(map);
		}

		// 返回值：买了又买列表
		sender.put("mediaList", buyAlsoBuyList);
		sender.put("total", buyAlsoBuyIdsAll.size());
		sender.put("timeCost", System.currentTimeMillis()-startTime);
		sender.success(response);
		
	}


	private void removeDupAndCurrentBook(List<MediaCacheBasicVo> buyAlsoBuyIdsAll, Long mediaId) {
		
		
		LinkedHashMap<Long, MediaCacheBasicVo> map = new LinkedHashMap<Long, MediaCacheBasicVo>();
		
		for(MediaCacheBasicVo vo: buyAlsoBuyIdsAll){
			if(!mediaId.equals(vo.getMediaId())){
				map.put(vo.getMediaId(), vo);
			}
		}
		buyAlsoBuyIdsAll.clear();
		buyAlsoBuyIdsAll.addAll(map.values());
	}


	private void removeBuyHistory(List<MediaCacheBasicVo> buyAlsoBuyIdsAll, LinkedHashSet<Long> history) {
		
		if(history==null || history.size()==0){
			return;
		}
		
		List<MediaCacheBasicVo> listClone = new ArrayList<MediaCacheBasicVo>(buyAlsoBuyIdsAll);
		for(MediaCacheBasicVo vo: listClone){
			
			if(history.contains(vo.getMediaId())){
				buyAlsoBuyIdsAll.remove(vo);
			}
		}
	}


	private void buildSupplementDataToList(List<MediaCacheBasicVo> buyAlsoBuyIdsAll,
			List<MediaCacheWholeVo> npList, List<MediaCacheWholeVo> vpList) {
			
			int maxIndex = npList.size()>vpList.size()?npList.size():vpList.size();
			
			for(int i=0; i<maxIndex; i++){
				
				if(i<npList.size()){
					MediaCacheWholeVo wholeVo = npList.get(i);
					buyAlsoBuyIdsAll.add(MediaCacheBeanUtils.getMediaBasicVo(wholeVo));
				}
				if(i<vpList.size()){
					MediaCacheWholeVo wholeVo = vpList.get(i);
					buyAlsoBuyIdsAll.add(MediaCacheBeanUtils.getMediaBasicVo(wholeVo));
				}
			}
		
	}
	
}
