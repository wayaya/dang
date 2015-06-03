package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.dangdang.base.comment.client.vo.BarrageCommentVo;
import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.bookreview.api.bean.BookReviewPriase;
import com.dangdang.bookreview.api.bean.BookReviewReply;
import com.dangdang.bookreview.api.bean.MobileIsPriase;
import com.dangdang.bookreview.api.vo.BookReviewReplyVo;
import com.dangdang.common.api.ICommonApi;
import com.dangdang.common.api.vo.UserBaseInfo;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.AnnouncementsContent;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.model.Notice;
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.service.IDiscoveryService;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.vo.AuthorCacheVo;
import com.dangdang.digital.vo.CatetoryMediaSaleVo;
import com.dangdang.digital.vo.CatetoryVo;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.ChapterCacheWholeVo;
import com.dangdang.digital.vo.ChapterContentsVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.RetrunDigestDetailVo;
import com.dangdang.digital.vo.RetrunDigestDetailVo.EbookInfo;
import com.dangdang.digital.vo.ReturnAnnouncementVo;
import com.dangdang.digital.vo.ReturnAuthorVo;
import com.dangdang.digital.vo.ReturnBarrageCommentVo;
import com.dangdang.digital.vo.ReturnBookReviewReplyVo;
import com.dangdang.digital.vo.ReturnBookReviewVo;
import com.dangdang.digital.vo.ReturnBuyInfo;
import com.dangdang.digital.vo.ReturnCardDiscoveryVo;
import com.dangdang.digital.vo.ReturnDigestBookReviewVo;
import com.dangdang.digital.vo.ReturnDiscoveryMediaVo;
import com.dangdang.digital.vo.ReturnDiscoveryVo;
import com.dangdang.digital.vo.ReturnMediaVo;
import com.dangdang.digital.vo.ReturnNoticeVo;
import com.dangdang.digital.vo.ReturnNoticeVo.ReturnParameter;
import com.dangdang.digital.vo.ReturnSaleVo;
import com.dangdang.digital.vo.ReturnWorShipRecord;
import com.dangdang.digital.vo.ShoppingViewForChapterVo;
import com.dangdang.digital.vo.UserTradeBaseVo;
import com.dangdang.ebook.api.api.IEbookApi;
import com.dangdang.ebook.api.vo.EbookAllInfoVo;
import com.dangdang.ebook.api.vo.EbookInfoVo;
import com.google.common.collect.Lists;

@Service
public class ReturnBeanUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReturnBeanUtils.class);

	@Resource
	private ICommonApi commonApi;

	@Resource
	private ICacheApi cacheApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IDiscoveryService discoveryService;

	@Resource
	private IBookReviewApi bookReviewApi;
	
	@Resource
	private IStoreUpService storeUpService;
	
	@Resource
	private IEbookApi iEbookApi;


	
	public  List<ReturnWorShipRecord> getReturnWorShipRecordList(Long custId,List<WorshipRecord> worshipRecordList) throws Exception{
		List<ReturnWorShipRecord> rwsrList = new LinkedList<ReturnWorShipRecord>();
		if(null == worshipRecordList || worshipRecordList.size()==0){
			return rwsrList;
		}
		for(WorshipRecord wr : worshipRecordList){
			ReturnWorShipRecord temp = new ReturnWorShipRecord();
			/**
			 * 被膜拜用户的custId
			 */
			Long worShipedCustId = wr.getCustId().longValue()==custId.longValue()?wr.getWorshipedCustId():wr.getCustId();
			if(wr.getCustId().longValue()==custId.longValue()){
				//我膜拜过用户列表
				temp.setCustId(DesUtils.encryptCustId(wr.getWorshipedCustId()));
				temp.setNickName(wr.getWorshipedUsername());
			}else{
				//膜拜过我的用户列表
				temp.setCustId(DesUtils.encryptCustId(wr.getCustId()));
				temp.setNickName(wr.getUsername());
			}
			
			temp.setRecordId(wr.getRecordId());
			//反馈给客户端的时间统一成毫秒
			temp.setLastTime(DateUtil.parseStringToDate(wr.getLastTime()).getTime());
			temp.setTimes(wr.getTimes());
			UserTradeBaseVo userTradeBaseVo = cacheApi.getUserWholeInfoByCustId(worShipedCustId);
			if(null!=userTradeBaseVo ){
				temp.setCustImg(userTradeBaseVo.getCustImg());
				temp.setNickName(userTradeBaseVo.getNickname());
			}else{
				LOGGER.info("通过custId无法获取图像信息" + wr.getCustId() );
			}
			rwsrList.add(temp);
		}
		return rwsrList;
	}
	public static List<ReturnNoticeVo> getReturnNoticeVoList(List<Notice> noticeList) throws Exception {
		if (null == noticeList || noticeList.size() == 0) {
			return null;
		}
		List<ReturnNoticeVo> returnNoticeVoList = new ArrayList<ReturnNoticeVo>(noticeList.size());
		for (Notice notice : noticeList) {
			String parameterJson = notice.getParameter();
			ReturnNoticeVo rnv = new ReturnNoticeVo();
			rnv.setNoticeId(notice.getNoticeId());
			rnv.setTitle(notice.getTitle());
			rnv.setType(notice.getType());
			rnv.setUrl(notice.getUrl());
			rnv.setChannelType(notice.getChannelType());
			rnv.setCreateTime(DateUtil.parseStringToDate(notice.getCreateTime()).getTime());
			rnv.setStartTime(DateUtil.parseStringToDate(notice.getStartTime()).getTime());
			rnv.setEndTime(DateUtil.parseStringToDate(notice.getEndTime()).getTime());
			if (null != parameterJson && !parameterJson.trim().isEmpty()) {
				try {
					Notice.Parameter parameter = JSON.parse(notice.getParameter(), Notice.Parameter.class);
					ReturnParameter  returnParameter = new ReturnParameter();
					if(2== notice.getType()){
						//打赏,需要加密用户Id
						returnParameter.setId(DesUtils.encryptCustId(parameter.getId()));
					}else{
						returnParameter.setId(String.valueOf(parameter.getId()));
					}
					returnParameter.setCode(parameter.getCode());
					returnParameter.setName(parameter.getName());
					returnParameter.setDimension(parameter.getDimension());
					rnv.setParameter(returnParameter);
				} catch (ParseException e) {
					LOGGER.info("转换parameter　josn字符串出错[" + parameterJson + "]");
				}
			}
			returnNoticeVoList.add(rnv);
		}
		return returnNoticeVoList;
	}

	/**
	 * 
	 * Description: 将原始的分类和关联的mediaSale转换为前端app需要的数据结构
	 * 
	 * @Version1.0 2015年1月8日 下午2:09:36 by 吕翔 (lvxiang@dangdang.com) 创建
	 * @param catetoryVoList
	 * @return
	 */
	public static List<CatetoryVo> getReturnCatetoryVo(List<CatetoryMediaSaleVo> catetoryVoList) {
		List<CatetoryVo> cvList = new ArrayList<CatetoryVo>();
		if (null == catetoryVoList || catetoryVoList.size() == 0) {
			return cvList;
		}
		for (CatetoryMediaSaleVo cmv : catetoryVoList) {
			CatetoryVo cv = new CatetoryVo();
			cv.setId(cmv.getId());
			cv.setCode(cmv.getCode());
			cv.setName(cmv.getName());
			cv.setParentId(cmv.getParentId());
			cv.setImage(cmv.getImage());
			if (null != cmv.getMediaSaleList()) {
				cv.setSaleList(batchgetReturnSaleListVo(cmv.getMediaSaleList()));
			}
			cvList.add(cv);
		}
		return cvList;
	}

	/**
	 * 
	 * Description: 将公告类型和其内容转成前端需要的数据结构
	 * 
	 * @Version1.0 2015年1月6日 下午9:23:20 by 吕翔 (lvxiang@dangdang.com) 创建
	 * @param ac
	 * @param acList
	 * @return
	 */
	public static ReturnAnnouncementVo getReturnAnnouncementVo(AnnouncementsCategory ac,
			List<AnnouncementsContent> acList) {
		ReturnAnnouncementVo rav = new ReturnAnnouncementVo();
		rav.setCategoryCode(ac.getCategoryCode());
		rav.setCategoryName(ac.getCategoryName());
		rav.setIcon(ac.getIcon());
		rav.setPosition(ac.getPosition());
		rav.setAnnouncementList(acList);
		return rav;
	}

	/**
	 * 
	 * Description: 获取单品sale vo
	 * 
	 * @Version1.0 2014年12月16日 下午6:20:00 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param returnSaleVo
	 * @param mediaSaleCacheVo
	 */
	public static ReturnSaleVo getReturnSaleDetailVo(MediaSaleCacheVo mediaSaleCacheVo) {
		ReturnSaleVo returnSaleVo = new ReturnSaleVo();
		returnSaleVo.setType(mediaSaleCacheVo.getType());
		returnSaleVo.setSaleId(mediaSaleCacheVo.getSaleId());
		returnSaleVo.setPrice(mediaSaleCacheVo.getPrice());
		// 单品销售主体减少传输信息
		if (mediaSaleCacheVo.getType() != 0) {
			returnSaleVo.setDesc(mediaSaleCacheVo.getDesc());
			returnSaleVo.setName(mediaSaleCacheVo.getName());
			returnSaleVo.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(mediaSaleCacheVo.getSaleId()));
		}
		if (mediaSaleCacheVo.getMediaList() != null && mediaSaleCacheVo.getMediaList().size() > 0) {
			List<ReturnMediaVo> mediaList = new ArrayList<ReturnMediaVo>();
			for (MediaCacheWholeVo mediaCacheWholeVo : mediaSaleCacheVo.getMediaList()) {
				mediaList.add(getReturnMediaDetailVo(mediaCacheWholeVo));
			}
			returnSaleVo.setMediaList(mediaList);
		}
		return returnSaleVo;
	}

	/**
	 * Description: 批量获取获取单品sale vo
	 * 
	 * @Version1.0 2015年1月12日 下午4:43:53 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaSaleCacheVo
	 * @return
	 */
	public static List<ReturnSaleVo> getReturnSaleDetailVo(List<MediaSaleCacheVo> cacheList) {
		List<ReturnSaleVo> resultList = new ArrayList<ReturnSaleVo>();
		for (MediaSaleCacheVo mediaSaleCacheVo : cacheList) {
			ReturnSaleVo returnSaleVo = getReturnSaleDetailVo(mediaSaleCacheVo);
			resultList.add(returnSaleVo);
		}
		return resultList;
	}

	public static ReturnDiscoveryVo getReturnDiscoveryVo(Discovery discovery) {
		ReturnDiscoveryVo vo = new ReturnDiscoveryVo();
		vo.setAuthor(discovery.getAuthor());
		vo.setCollectCnt(discovery.getCollectCnt());
		vo.setColumnId(discovery.getColumnId());
		vo.setColumnName(discovery.getColumnName());
		vo.setContent(discovery.getHtmlContent());
		vo.setFirstCatetoryName(discovery.getFirstCatetoryName());
		vo.setId(discovery.getId());
		vo.setReviewCnt(discovery.getReviewCnt());
		vo.setSecondCatetoryName(discovery.getSecondCatetoryName());
		vo.setShareCnt(discovery.getShareCnt());
		vo.setSignName(discovery.getSignName());
		vo.setStars(discovery.getStars());
		vo.setTitle(discovery.getTitle());
		vo.setCardRemark(discovery.getCardRemark());
		vo.setCardTitle(discovery.getCardTitle());
		return vo;
	}

	public static ReturnDiscoveryMediaVo getReturnDiscoveryMediaVo(MediaCacheWholeVo wvo) {
		ReturnDiscoveryMediaVo vo = new ReturnDiscoveryMediaVo();
		vo.setAuthor(wvo.getAuthorName());
		vo.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(wvo.getMediaId()));
		vo.setDescs(wvo.getDescs());
		vo.setMediaId(wvo.getMediaId());
		vo.setRecommandWords(wvo.getRecommandWords());
		vo.setSubTitle(wvo.getSubTitle());
		vo.setTitle(wvo.getTitle());
		return vo;
	}

	/**
	 * 
	 * Description: 获取单品 media vo
	 * 
	 * @Version1.0 2014年12月16日 下午6:37:16 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param returnMediaVo
	 * @param mediaCacheWholeVo
	 */
	public static ReturnMediaVo getReturnMediaDetailVo(MediaCacheWholeVo mediaCacheWholeVo) {
		ReturnMediaVo returnMediaVo = new ReturnMediaVo();
		returnMediaVo.setAuthorId(mediaCacheWholeVo.getAuthorId());
		returnMediaVo.setAuthorPenname(mediaCacheWholeVo.getAuthorPenname());
		returnMediaVo.setChapterCnt(mediaCacheWholeVo.getChapterCnt());
		returnMediaVo.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(mediaCacheWholeVo.getMediaId()));
		returnMediaVo.setDescs(mediaCacheWholeVo.getDescs());
		returnMediaVo.setHasNew(mediaCacheWholeVo.getHasNew());
		returnMediaVo.setIsFull(mediaCacheWholeVo.getIsFull());
		returnMediaVo.setIsSupportFullBuy(mediaCacheWholeVo.getIsSupportFullBuy());
		returnMediaVo.setMediaId(mediaCacheWholeVo.getMediaId());
		returnMediaVo.setRecommandWords(mediaCacheWholeVo.getRecommandWords());
		returnMediaVo.setSubTitle(mediaCacheWholeVo.getSubTitle());
		returnMediaVo.setTitle(mediaCacheWholeVo.getTitle());
		returnMediaVo.setSaleId(mediaCacheWholeVo.getSaleId());
		if (mediaCacheWholeVo.getLastPullChapterDate() != null) {
			returnMediaVo.setLastPullChapterDate(mediaCacheWholeVo.getLastPullChapterDate().getTime());
		}
		returnMediaVo.setLastIndexOrder(mediaCacheWholeVo.getLastIndexOrder());
		returnMediaVo.setLastUpdateChapter(mediaCacheWholeVo.getLastUpdateChapter());
		returnMediaVo.setCategoryIds(mediaCacheWholeVo.getCategoryIds());
		returnMediaVo.setCategorys(mediaCacheWholeVo.getCategorys());
		returnMediaVo.setCpShortName(mediaCacheWholeVo.getCpShortName());
		returnMediaVo.setWordCnt(mediaCacheWholeVo.getWordCnt());
		returnMediaVo.setPriceUnit(mediaCacheWholeVo.getPriceUnit());
		returnMediaVo.setShelfStatus(mediaCacheWholeVo.getShelfStatus());
		returnMediaVo.setSpeaker(mediaCacheWholeVo.getSpeaker());
		return returnMediaVo;

	}

	/**
	 * 
	 * Description: 批量获取列表sale vo
	 * 
	 * @Version1.0 2014年12月16日 下午8:49:30 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaSaleCacheVoList
	 * @return
	 */
	public static List<ReturnSaleVo> batchgetReturnSaleListVo(List<MediaSaleCacheVo> mediaSaleCacheVoList) {
		List<ReturnSaleVo> returnSaleVoList = new ArrayList<ReturnSaleVo>();
		for (MediaSaleCacheVo mediaSaleCacheVo : mediaSaleCacheVoList) {
			returnSaleVoList.add(getReturnSaleListVo(mediaSaleCacheVo));
		}
		return returnSaleVoList;
	}

	/**
	 * 
	 * Description: 获取列表 sale vo
	 * 
	 * @Version1.0 2014年12月16日 下午6:20:00 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param returnSaleVo
	 * @param mediaSaleCacheVo
	 */
	public static ReturnSaleVo getReturnSaleListVo(MediaSaleCacheVo mediaSaleCacheVo) {
		ReturnSaleVo returnSaleVo = new ReturnSaleVo();
		returnSaleVo.setType(mediaSaleCacheVo.getType());
		returnSaleVo.setSaleId(mediaSaleCacheVo.getSaleId());
		returnSaleVo.setPrice(mediaSaleCacheVo.getPrice());
		// 单品销售主体减少传输信息
		if (mediaSaleCacheVo.getType() != 0) {
			returnSaleVo.setDesc(mediaSaleCacheVo.getDesc());
			returnSaleVo.setName(mediaSaleCacheVo.getName());
			returnSaleVo.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(mediaSaleCacheVo.getSaleId()));
		}
		if (mediaSaleCacheVo.getMediaList() != null && mediaSaleCacheVo.getMediaList().size() > 0) {
			List<ReturnMediaVo> mediaList = new ArrayList<ReturnMediaVo>();
			for (MediaCacheWholeVo mediaCacheWholeVo : mediaSaleCacheVo.getMediaList()) {
				mediaList.add(getReturnMediaListVo(mediaCacheWholeVo));
			}
			returnSaleVo.setMediaList(mediaList);
		}
		return returnSaleVo;
	}

	/**
	 * 
	 * Description: 获取列表media vo
	 * 
	 * @Version1.0 2014年12月16日 下午6:37:16 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param returnMediaVo
	 * @param mediaCacheWholeVo
	 */
	public static ReturnMediaVo getReturnMediaListVo(MediaCacheWholeVo mediaCacheWholeVo) {
		ReturnMediaVo returnMediaVo = new ReturnMediaVo();
		returnMediaVo.setAuthorId(mediaCacheWholeVo.getAuthorId());
		returnMediaVo.setAuthorPenname(mediaCacheWholeVo.getAuthorPenname());
		returnMediaVo.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(mediaCacheWholeVo.getMediaId()));
		returnMediaVo.setDescs(mediaCacheWholeVo.getDescs());
		returnMediaVo.setHasNew(mediaCacheWholeVo.getHasNew());
		returnMediaVo.setIsFull(mediaCacheWholeVo.getIsFull());
		returnMediaVo.setMediaId(mediaCacheWholeVo.getMediaId());
		returnMediaVo.setRecommandWords(mediaCacheWholeVo.getRecommandWords());
		returnMediaVo.setSubTitle(mediaCacheWholeVo.getSubTitle());
		returnMediaVo.setTitle(mediaCacheWholeVo.getTitle());
		returnMediaVo.setSaleId(mediaCacheWholeVo.getSaleId());
		returnMediaVo.setCategoryIds(mediaCacheWholeVo.getCategoryIds());
		returnMediaVo.setCategorys(mediaCacheWholeVo.getCategorys());
		returnMediaVo.setSpeaker(mediaCacheWholeVo.getSpeaker());
		returnMediaVo.setChapterCnt(mediaCacheWholeVo.getChapterCnt());
		if (null == mediaCacheWholeVo.getRecommandWords()) {
			// add by lvxiang如果没有就给空串
			returnMediaVo.setRecommandWords("");
		} else {
			returnMediaVo.setRecommandWords(mediaCacheWholeVo.getRecommandWords());
		}
		return returnMediaVo;
	}

	/**
	 * 
	 * Description: 批量获取列表media vo
	 * 
	 * @Version1.0 2014年12月16日 下午9:50:31 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaCacheWholeVoList
	 * @return
	 */
	public static List<ReturnMediaVo> batchGetReturnMediaListVo(List<MediaCacheWholeVo> mediaCacheWholeVoList) {
		List<ReturnMediaVo> mediaList = new ArrayList<ReturnMediaVo>();
		for (MediaCacheWholeVo mediaCacheWholeVo : mediaCacheWholeVoList) {
			mediaList.add(getReturnMediaListVo(mediaCacheWholeVo));
		}
		return mediaList;
	}

	/**
	 * 
	 * Description: 获取目录vo列表
	 * 
	 * @Version1.0 2014年12月17日 下午2:46:42 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterCacheWholeVoList
	 * @return
	 */
	public static List<ChapterContentsVo> getReturnContentsList(List<ChapterCacheWholeVo> chapterCacheWholeVoList) {
		List<ChapterContentsVo> chapterList = new ArrayList<ChapterContentsVo>();
		for (ChapterCacheWholeVo chapterCacheWholeVo : chapterCacheWholeVoList) {
			chapterList.add(getReturnContents(chapterCacheWholeVo));
		}
		return chapterList;
	}

	/**
	 * 
	 * Description: 获取目录vo
	 * 
	 * @Version1.0 2014年12月17日 下午2:43:57 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterCacheWholeVo
	 * @return
	 */
	public static ChapterContentsVo getReturnContents(ChapterCacheWholeVo chapterCacheWholeVo) {
		ChapterContentsVo returnChapterVo = new ChapterContentsVo();
		returnChapterVo.setId(chapterCacheWholeVo.getId());
		returnChapterVo.setIndex(chapterCacheWholeVo.getIndex());
		returnChapterVo.setIsFree(chapterCacheWholeVo.getIsFree());
		returnChapterVo.setTitle(chapterCacheWholeVo.getTitle());
		returnChapterVo.setSubTitle(chapterCacheWholeVo.getSubTitle());
		return returnChapterVo;
	}

	/**
	 * 
	 * Description: 批量获取书评
	 * 
	 * @Version1.0 2014年12月18日 下午8:23:10 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReviewList
	 * @return
	 * @throws Exception
	 */
	public List<ReturnBookReviewVo> getReturnBookReviewVoList(List<BookReview> bookReviewList, Long custId)
			throws Exception {
		List<ReturnBookReviewVo> returnBookReviewVoList = new ArrayList<ReturnBookReviewVo>();
		// custId集合用于获取用户信息
		
		List<String> custIdList = new ArrayList<String>();
		List<Long> bookReviewIdList = new ArrayList<Long>();
		List<Long> saleIdList = new ArrayList<Long>();
		List<Long> discoveryIdList = new ArrayList<Long>();
		for (BookReview bookReview : bookReviewList) {
			custIdList.add(String.valueOf(bookReview.getCustomerId()));
			bookReviewIdList.add(bookReview.getReviewId());
			if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA) {
				saleIdList.add(bookReview.getSubjectId());
			} else if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA_ARTICAL) {
				discoveryIdList.add(bookReview.getSubjectId());
			}
		}
		Map<Long, String> saleTitleMap = new HashMap<Long, String>();
		Map<Long, Discovery> disvoveryMap = new HashMap<Long, Discovery>();
		Map<Long, String> descMap = new HashMap<Long, String>();
		//add lvxiang
		Map<Long, String> mediaDescMap = new HashMap<Long, String>();
		Map<Long, String> authorMap = new HashMap<Long, String>();
		// 获取销售主体的名字
		if (!CollectionUtils.isEmpty(saleIdList)) {
			List<MediaSaleCacheVo> saleList = cacheApi.batchGetMediaSaleFromCache(saleIdList);
			if (!CollectionUtils.isEmpty(saleList)) {
				for (MediaSaleCacheVo mediaSale : saleList) {
					saleTitleMap.put(mediaSale.getSaleId(), mediaSale.getName());
				}
			}
		}
		// 获取发现的名字
		if (!CollectionUtils.isEmpty(discoveryIdList)) {
			List<Discovery> discoveryList = discoveryService.findByIds(discoveryIdList);
			if (!CollectionUtils.isEmpty(discoveryList)) {
				for (Discovery discovery : discoveryList) {
					disvoveryMap.put(discovery.getId(), discovery);
				}
			}
		}
		//获取作者和书描述信息
		if (!CollectionUtils.isEmpty(saleIdList)) {
			List<MediaSaleCacheVo> mediaSale = cacheApi.batchGetMediaSaleFromCache(saleIdList);
			if (!CollectionUtils.isEmpty(mediaSale)) {
				for (MediaSaleCacheVo ms : mediaSale) {
					if(ms!=null && !CollectionUtils.isEmpty(ms.getMediaList())){
					descMap.put(ms.getSaleId(), ms.getMediaList().get(0).getDescs());
					}
					if(ms!=null && !CollectionUtils.isEmpty(ms.getMediaList())){
						authorMap.put(ms.getSaleId(), ms.getMediaList().get(0).getAuthorPenname());
					}
					mediaDescMap.put(ms.getSaleId(), ms.getDesc());//
				}
			}
		}
		// 调用接口批量获取用户信息
		Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(custIdList, 60 * 60 * 2);
		// 批量获取点赞信息
		List<Long> hasPraiseList = new LinkedList<Long>();
		if (custId != null) {
			List<BookReviewPriase> reviewPraises = bookReviewApi.getBookReviewPriasesByReviewIds(
					StringUtils.join(bookReviewIdList, ","), String.valueOf(custId));
			if (!CollectionUtils.isEmpty(reviewPraises)) {
				for (BookReviewPriase reviewPraise : reviewPraises) {
					hasPraiseList.add(reviewPraise.getReviewId());
				}

			}
		}
		for (BookReview bookReview : bookReviewList) {
			ReturnBookReviewVo returnBookReview = getReturnBookReviewVo(bookReview);
			
			// 设置用户信息
			if (returnMap != null && returnMap.containsKey(String.valueOf(bookReview.getCustomerId()))
					&& returnMap.get(String.valueOf(bookReview.getCustomerId())) != null) {
				returnBookReview.setHeadPic(returnMap.get(String.valueOf(bookReview.getCustomerId())).get("custImg"));
				returnBookReview.setNickName(returnMap.get(String.valueOf(bookReview.getCustomerId())).get("nickName"));
			}
			if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA
					&& saleTitleMap.containsKey(bookReview.getSubjectId())) {
			//	returnBookReview.setDescription();
				returnBookReview.setDescription(descMap.get(bookReview.getSubjectId()));
				returnBookReview.setSaleName(saleTitleMap.get(bookReview.getSubjectId()));
				returnBookReview.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(bookReview.getSubjectId()));
			} else if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA_ARTICAL
					&& disvoveryMap.containsKey(bookReview.getSubjectId())) {
				//mediaDescMap
				returnBookReview.setDescription(disvoveryMap.get(bookReview.getSubjectId()).getCardRemark());
				returnBookReview.setSaleName(disvoveryMap.get(bookReview.getSubjectId()).getTitle());
				if (StringUtils.isNotBlank(disvoveryMap.get(bookReview.getSubjectId()).getPic1Path())) {
					returnBookReview.setCoverPic(ConfigPropertieUtils.getString("media.resource.discover.http.path")
							+ disvoveryMap.get(bookReview.getSubjectId()).getPic1Path());
				}
			}
			// 如果已经点过赞,设置不可点赞
			if (hasPraiseList.contains(bookReview.getReviewId())) {
				returnBookReview.setCanPraise(0);
			}
//			if(descMap!=null){
//				returnBookReview.setDescription(descMap.get(bookReview.getSubjectId()));
//			}
			if(authorMap!=null){
				returnBookReview.setAuthor(authorMap.get(bookReview.getSubjectId()));
			}
			
			returnBookReviewVoList.add(returnBookReview);
		}
		return returnBookReviewVoList;
	}

	/**
	 * 
	 * Description: 获取单个书评
	 * 
	 * @Version1.0 2014年12月30日 上午11:17:02 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReview
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public ReturnBookReviewVo getReturnBookReviewVoSingle(BookReview bookReview, Long custId, String title,
			String coverPic) throws Exception {
		ReturnBookReviewVo returnBookReview = getReturnBookReviewVo(bookReview);
		// custId集合用于获取用户信息
		List<String> custIdList = new ArrayList<String>();
		custIdList.add(String.valueOf(bookReview.getCustomerId()));
		List<Long> bookReviewIdList = new ArrayList<Long>();
		bookReviewIdList.add(bookReview.getReviewId());
		// 调用接口批量获取用户信息
		Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(custIdList, 60 * 60 * 2);
		// 批量获取点赞信息
		List<Long> hasPraiseList = new LinkedList<Long>();
		if (custId != null) {
			List<BookReviewPriase> reviewPraises = bookReviewApi.getBookReviewPriasesByReviewIds(
					StringUtils.join(bookReviewIdList, ","), String.valueOf(custId));
			if (!CollectionUtils.isEmpty(reviewPraises)) {
				for (BookReviewPriase reviewPraise : reviewPraises) {
					hasPraiseList.add(reviewPraise.getReviewId());
				}

			}
		}
		// 设置用户信息
		if (returnMap != null && returnMap.containsKey(String.valueOf(bookReview.getCustomerId()))
				&& returnMap.get(String.valueOf(bookReview.getCustomerId())) != null) {
			returnBookReview.setHeadPic(returnMap.get(String.valueOf(bookReview.getCustomerId())).get("custImg"));
			returnBookReview.setNickName(returnMap.get(String.valueOf(bookReview.getCustomerId())).get("nickName"));
		}
		// 如果已经点过赞,设置不可点赞
		if (hasPraiseList.contains(bookReview.getReviewId())) {
			returnBookReview.setCanPraise(0);
		}
		returnBookReview.setSaleName(title);
		returnBookReview.setCoverPic(coverPic);
		return returnBookReview;
	}

	/**
	 * 
	 * Description: 转换vo
	 * 
	 * @Version1.0 2014年12月30日 上午11:15:44 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReview
	 * @return
	 * @throws Exception
	 */
	private ReturnBookReviewVo getReturnBookReviewVo(BookReview bookReview) throws Exception {
		ReturnBookReviewVo returnBookReviewVo = new ReturnBookReviewVo();
		returnBookReviewVo.setComment(bookReview.getComment());
		if (bookReview.getCreateTime() != null) {
			returnBookReviewVo.setCreateTime(bookReview.getCreateTime().getTime());
		}
		returnBookReviewVo.setCustId(DesUtils.encryptCustId(bookReview.getCustomerId()));
		returnBookReviewVo.setPraiseCount(bookReview.getPraiseCount());
		returnBookReviewVo.setReplyCount(bookReview.getReplyCount());
		returnBookReviewVo.setReviewType(bookReview.getReviewType());
		returnBookReviewVo.setSaleId(bookReview.getSubjectId());
		returnBookReviewVo.setScore(bookReview.getScore());
		returnBookReviewVo.setTitle(bookReview.getTitle());
		returnBookReviewVo.setBookReviewId(bookReview.getReviewId());
		returnBookReviewVo.setSubjectType(bookReview.getSubjectType());
		
		return returnBookReviewVo;
	}

	/**
	 * 
	 * Description: 批量获取带回复的书评
	 * 
	 * @Version1.0 2014年12月18日 下午8:23:10 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReviewList
	 * @return
	 * @throws Exception
	 */
	public List<ReturnBookReviewVo> getReturnBookReviewVoWithReplyList(List<BookReviewReplyVo> bookReviewList,
			Long custId) throws Exception {
		List<ReturnBookReviewVo> returnBookReviewVoList = new ArrayList<ReturnBookReviewVo>();
		List<Long> saleIdList = new ArrayList<Long>();
		List<Long> discoveryIdList = new ArrayList<Long>();
		List<Long> bookReviewIdList = new ArrayList<Long>();
		for (BookReviewReplyVo bookReview : bookReviewList) {
			bookReviewIdList.add(bookReview.getReviewId());
			if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA) {
				saleIdList.add(bookReview.getSubjectId());
			} else if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA_ARTICAL) {
				discoveryIdList.add(bookReview.getSubjectId());
			}
		}
		// 批量获取点赞信息
		List<Long> hasPraiseList = new LinkedList<Long>();
		if (custId != null) {
			List<BookReviewPriase> reviewPraises = bookReviewApi.getBookReviewPriasesByReviewIds(
					StringUtils.join(bookReviewIdList, ","), String.valueOf(custId));
			if (!CollectionUtils.isEmpty(reviewPraises)) {
				for (BookReviewPriase reviewPraise : reviewPraises) {
					hasPraiseList.add(reviewPraise.getReviewId());
				}

			}
		}

		Map<Long, String> saleTitleMap = new HashMap<Long, String>();
		Map<Long, String> disvoveryTitleMap = new HashMap<Long, String>();
		// 获取销售主体的名字
		if (!CollectionUtils.isEmpty(saleIdList)) {
			List<MediaSaleCacheVo> saleList = cacheApi.batchGetMediaSaleFromCache(saleIdList);
			if (!CollectionUtils.isEmpty(saleList)) {
				for (MediaSaleCacheVo mediaSale : saleList) {
					saleTitleMap.put(mediaSale.getSaleId(), mediaSale.getName());
				}
			}
		}
		// 获取发现的名字
		if (!CollectionUtils.isEmpty(discoveryIdList)) {
			List<Discovery> discoveryList = discoveryService.findByIds(discoveryIdList);
			if (!CollectionUtils.isEmpty(discoveryList)) {
				for (Discovery discovery : discoveryList) {
					disvoveryTitleMap.put(discovery.getId(), discovery.getTitle());
				}
			}
		}

		for (BookReviewReplyVo bookReview : bookReviewList) {
			ReturnBookReviewVo returnBookReviewVo = getReturnBookReviewVoWithReply(bookReview);
			if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA
					&& saleTitleMap.containsKey(bookReview.getSubjectId())) {
				returnBookReviewVo.setSaleName(saleTitleMap.get(bookReview.getSubjectId()));
			} else if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA_ARTICAL
					&& disvoveryTitleMap.containsKey(bookReview.getSubjectId())) {
				returnBookReviewVo.setSaleName(disvoveryTitleMap.get(bookReview.getSubjectId()));
			}
			// 如果已经点过赞,设置不可点赞
			if (hasPraiseList.contains(bookReview.getReviewId())) {
				returnBookReviewVo.setCanPraise(0);
			}
			returnBookReviewVoList.add(returnBookReviewVo);
		}
		return returnBookReviewVoList;
	}

	/**
	 * 
	 * Description: 获取带会回复的书评
	 * 
	 * @Version1.0 2014年12月24日 下午4:08:14 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReview
	 * @return
	 * @throws Exception
	 */
	public static ReturnBookReviewVo getReturnBookReviewVoWithReply(BookReviewReplyVo bookReview) throws Exception {
		ReturnBookReviewVo returnBookReviewVo = new ReturnBookReviewVo();
		ReturnBookReviewReplyVo replyVo = new ReturnBookReviewReplyVo();
		returnBookReviewVo.setComment(bookReview.getComment());
		returnBookReviewVo.setCustId(DesUtils.encryptCustId(bookReview.getCustomerId()));
		returnBookReviewVo.setNickName(bookReview.getUserNickName());
		returnBookReviewVo.setHeadPic(bookReview.getUserImgUrl());
		returnBookReviewVo.setBookReviewId(bookReview.getReviewId());
		returnBookReviewVo.setSubjectType(bookReview.getSubjectType());
		if (bookReview.getReplyDate() != null) {
			replyVo.setCreateTime(bookReview.getReplyDate().getTime());
		}
		replyVo.setReplyContent(bookReview.getReplyContent());
		replyVo.setNickName(bookReview.getReplyUserNickName());
		returnBookReviewVo.setReply(replyVo);
		return returnBookReviewVo;
	}

	/**
	 * 
	 * Description: 获取书评回复列表
	 * 
	 * @Version1.0 2014年12月19日 下午4:27:13 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReviewReplyList
	 * @return
	 * @throws Exception
	 */
	public List<ReturnBookReviewReplyVo> getReturnBookReviewReplyVoList(List<BookReviewReply> bookReviewReplyList,
			Long custId) throws Exception {
		List<ReturnBookReviewReplyVo> returnBookReviewReplyVoList = new ArrayList<ReturnBookReviewReplyVo>();
		// custId集合用于获取用户信息
		List<String> custIdList = new ArrayList<String>();
		for (BookReviewReply bookReviewReply : bookReviewReplyList) {
			custIdList.add(String.valueOf(bookReviewReply.getCustId()));

		}
		// 调用接口批量获取用户信息
		Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(custIdList, 60 * 60 * 2);

		for (BookReviewReply bookReviewReply : bookReviewReplyList) {
			ReturnBookReviewReplyVo returnBookReviewReplyVo = getReturnBookReviewReplyVo(bookReviewReply);
			// 设置用户信息
			if (returnMap != null && returnMap.containsKey(String.valueOf(bookReviewReply.getCustId()))
					&& returnMap.get(String.valueOf(bookReviewReply.getCustId())) != null) {
				returnBookReviewReplyVo.setHeadPic(returnMap.get(String.valueOf(bookReviewReply.getCustId())).get(
						"custImg"));
				returnBookReviewReplyVo.setNickName(returnMap.get(String.valueOf(bookReviewReply.getCustId())).get(
						"nickName"));
			}
			returnBookReviewReplyVoList.add(returnBookReviewReplyVo);
		}

		return returnBookReviewReplyVoList;
	}

	/**
	 * 
	 * Description: 获取书评回复
	 * 
	 * @Version1.0 2014年12月30日 上午11:43:29 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReviewReply
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public ReturnBookReviewReplyVo getReturnBookReviewReplyVoSingle(BookReviewReply bookReviewReply, Long custId)
			throws Exception {
		// custId集合用于获取用户信息
		List<String> custIdList = new ArrayList<String>();
		custIdList.add(String.valueOf(bookReviewReply.getCustId()));

		// 调用接口批量获取用户信息
		Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(custIdList, 60 * 60 * 2);

		ReturnBookReviewReplyVo returnBookReviewReplyVo = getReturnBookReviewReplyVo(bookReviewReply);
		// 设置用户信息
		if (returnMap != null && returnMap.containsKey(String.valueOf(bookReviewReply.getCustId()))
				&& returnMap.get(String.valueOf(bookReviewReply.getCustId())) != null) {
			returnBookReviewReplyVo.setHeadPic(returnMap.get(String.valueOf(bookReviewReply.getCustId()))
					.get("custImg"));
			returnBookReviewReplyVo.setNickName(returnMap.get(String.valueOf(bookReviewReply.getCustId())).get(
					"nickName"));
		}
		return returnBookReviewReplyVo;
	}

	/**
	 * 
	 * Description: 转换vo
	 * 
	 * @Version1.0 2014年12月30日 上午11:42:08 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReviewReply
	 * @return
	 * @throws Exception
	 */
	private ReturnBookReviewReplyVo getReturnBookReviewReplyVo(BookReviewReply bookReviewReply) throws Exception {
		ReturnBookReviewReplyVo returnBookReviewReplyVo = new ReturnBookReviewReplyVo();
		returnBookReviewReplyVo.setReplyId(bookReviewReply.getId());
		returnBookReviewReplyVo.setBookReviewId(bookReviewReply.getReviewId());
		if (bookReviewReply.getCreateTime() != null) {
			returnBookReviewReplyVo.setCreateTime(bookReviewReply.getCreateTime().getTime());
		}
		returnBookReviewReplyVo.setCustId(DesUtils.encryptCustId(bookReviewReply.getCustId()));
		returnBookReviewReplyVo.setReplyContent(bookReviewReply.getReplyContent());
		returnBookReviewReplyVo.setStar(bookReviewReply.isStar());
		return returnBookReviewReplyVo;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @Version1.0 2014年12月22日 下午1:46:31 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bookReviewReply
	 * @return
	 */
	public static ReturnAuthorVo getReturnAuthorVo(AuthorCacheVo authorCacheVo) {
		ReturnAuthorVo returnAuthorVo = new ReturnAuthorVo();
		returnAuthorVo.setAuthorId(authorCacheVo.getAuthorId());
		returnAuthorVo.setAuthorPenname(authorCacheVo.getPseudonym());
		if (authorCacheVo.getBirth() != null) {
			returnAuthorVo.setBirth(authorCacheVo.getBirth().getTime());
		}
		if(authorCacheVo.getHeadPic()!=null){
			returnAuthorVo.setHeadPic(ConfigPropertieUtils.getString("media.resource.author.http.path") + "/"
					+ authorCacheVo.getHeadPic());
		}
		returnAuthorVo.setIntroduction(authorCacheVo.getIntroduction());
		returnAuthorVo.setSex(authorCacheVo.getSex());
		return returnAuthorVo;

	}

	public static ReturnCardDiscoveryVo getReturnCardDiscoveryVo(Discovery discovery, String path) {
		ReturnCardDiscoveryVo vo = new ReturnCardDiscoveryVo();
		vo.setId(discovery.getId());
		vo.setCardRemark(discovery.getCardRemark());
		vo.setCardTitle(discovery.getTitle());
		vo.setCardType(discovery.getCardType());
		vo.setFirstCatetoryName(discovery.getFirstCatetoryName());
		vo.setSecondCatetoryName(discovery.getSecondCatetoryName());

		if (discovery.getShowStartDate() != null) {
			vo.setShowTime(DateUtil.format1(discovery.getShowStartDate()));
		}

		Integer reviewCnt = discovery.getReviewCnt();
		if (reviewCnt == null) {
			reviewCnt = 0;
		}
		Integer topOrder = discovery.getTopOrder();
		if(topOrder == null){
			topOrder = 0;
		}
		vo.setTopOrder(topOrder);
		vo.setReviewCnt(reviewCnt);
		int cardType = discovery.getCardType();
		if (cardType == Discovery.BIG_PIC || cardType == Discovery.SMALL_PIC) {
			vo.setPic1(path + discovery.getPic1Path());
		}
		if (cardType == Discovery.SMALL_PIC) {
			vo.setPic2(path + discovery.getPic2Path());
			vo.setPic3(path + discovery.getPic3Path());
		}
		return vo;
	}

	/**
	 * 
	 * Description: 获取购买信息
	 * 
	 * @Version1.0 2014年12月29日 下午1:31:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param authorCacheVo
	 * @return
	 * @throws Exception
	 */
	public static ReturnBuyInfo getReturnBuyInfo(ShoppingViewForChapterVo shoppingViewForChapterVo) throws Exception {
		ReturnBuyInfo returnBuyInfo = new ReturnBuyInfo();
		returnBuyInfo.setChapterId(shoppingViewForChapterVo.getMediaChapterId());
		returnBuyInfo.setChapterPrice(shoppingViewForChapterVo.getMediaChapterPrice());
		returnBuyInfo.setChapterTitle(shoppingViewForChapterVo.getMediaChapterTitle());
		returnBuyInfo.setCustId(DesUtils.encryptCustId(shoppingViewForChapterVo.getCustId()));
		returnBuyInfo.setMainBalance(shoppingViewForChapterVo.getMainBalance());
		returnBuyInfo.setMediaId(shoppingViewForChapterVo.getMediaId());
		returnBuyInfo.setSubBalance(shoppingViewForChapterVo.getSubBalance());
		returnBuyInfo.setWordCnt(shoppingViewForChapterVo.getWordCnt());
		returnBuyInfo.setSaleId(shoppingViewForChapterVo.getSaleId());
		returnBuyInfo.setSaleName(shoppingViewForChapterVo.getSaleName());
		returnBuyInfo.setSalePrice(shoppingViewForChapterVo.getSalePrice());
		returnBuyInfo.setIsSupportFullBuy(shoppingViewForChapterVo.getIsSupportFullBuy());
		returnBuyInfo.setMediaWordCnt(shoppingViewForChapterVo.getMediaWordCnt());
		returnBuyInfo.setMainBalanceIOS(shoppingViewForChapterVo.getMainBalanceIOS());
		returnBuyInfo.setSubBalanceIOS(shoppingViewForChapterVo.getSubBalanceIOS());
		return returnBuyInfo;
	}

	/**
	 * 
	 * Description: 获取交互弹幕vo
	 * 
	 * @Version1.0 2015年1月6日 下午12:00:40 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param BarrageCommentVo
	 * @param headPic
	 * @return
	 * @throws Exception
	 */
	public static ReturnBarrageCommentVo getReturnBarrageCommentVo(BarrageCommentVo barrageCommentVo, String headPic)
			throws Exception {
		ReturnBarrageCommentVo returnBarrageCommentVo = new ReturnBarrageCommentVo();
		returnBarrageCommentVo.setBarrageCommentId(barrageCommentVo.getBarrageCommentId());
		returnBarrageCommentVo.setChapterId(barrageCommentVo.getChapterId());
		returnBarrageCommentVo.setCharacterEndIndex(barrageCommentVo.getCharacterEndIndex());
		returnBarrageCommentVo.setCharacterStartIndex(barrageCommentVo.getCharacterStartIndex());
		returnBarrageCommentVo.setContent(barrageCommentVo.getContent());
		returnBarrageCommentVo.setCustId(DesUtils.encryptCustId(barrageCommentVo.getCustId()));
		returnBarrageCommentVo.setDeviceType(barrageCommentVo.getDeviceType());
		returnBarrageCommentVo.setMediaId(barrageCommentVo.getMediaId());
		returnBarrageCommentVo.setPublishDate(barrageCommentVo.getPublishDate());
		returnBarrageCommentVo.setReviewFailReason(barrageCommentVo.getReviewFailReason());
		returnBarrageCommentVo.setReviewStatus(barrageCommentVo.getReviewStatus());
		returnBarrageCommentVo.setStatus(barrageCommentVo.getStatus());
		returnBarrageCommentVo.setIsAnonymous(barrageCommentVo.getIsAnonymous());
		if (StringUtils.isNotBlank(headPic)) {
			returnBarrageCommentVo.setHeadPic(headPic);
		}
		return returnBarrageCommentVo;
	}

	/**
	 * 
	 * Description: 获取交互弹幕列表
	 * 
	 * @Version1.0 2015年1月6日 下午12:12:04 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param barrageCommentVoList
	 * @param userInfoMap
	 * @return
	 * @throws Exception
	 */
	public static List<ReturnBarrageCommentVo> getReturnBarrageCommentVoList(
			List<BarrageCommentVo> barrageCommentVoList, Map<String, Map<String, String>> userInfoMap,
			Map<Long, MediaCacheBasicVo> mediaMap, Map<Long, ChapterCacheBasicVo> chapterMap) throws Exception {
		List<ReturnBarrageCommentVo> returnBarrageCommentVoList = new ArrayList<ReturnBarrageCommentVo>();
		for (BarrageCommentVo barrageCommentVo : barrageCommentVoList) {
			ReturnBarrageCommentVo returnBarrageCommentVo = getReturnBarrageCommentVo(barrageCommentVo, null);
			if (userInfoMap != null && userInfoMap.containsKey(String.valueOf(barrageCommentVo.getCustId()))
					&& userInfoMap.get(String.valueOf(barrageCommentVo.getCustId())) != null) {
				returnBarrageCommentVo.setHeadPic(userInfoMap.get(String.valueOf(barrageCommentVo.getCustId())).get(
						"custImg"));
				returnBarrageCommentVo.setNickName(userInfoMap.get(String.valueOf(barrageCommentVo.getCustId())).get(
						"nickName"));
			}

			if (mediaMap != null && mediaMap.containsKey(barrageCommentVo.getMediaId())) {
				returnBarrageCommentVo.setMediaTitle(mediaMap.get(barrageCommentVo.getMediaId()).getTitle());
			}

			if (chapterMap != null && chapterMap.containsKey(barrageCommentVo.getChapterId())) {
				returnBarrageCommentVo.setChapterTitle(chapterMap.get(barrageCommentVo.getChapterId()).getTitle());
				returnBarrageCommentVo.setVolumeTitle(chapterMap.get(barrageCommentVo.getChapterId()).getVolumeName());
			}
			returnBarrageCommentVoList.add(returnBarrageCommentVo);
		}
		return returnBarrageCommentVoList;
	}
	
	public RetrunDigestDetailVo getRetrunDigestDetailVo(Digest digest, UserTradeBaseVo custVo){
		if(digest != null){
			RetrunDigestDetailVo digestVo = new RetrunDigestDetailVo();
			Long mediaId = digest.getMediaId();
			digestVo.setDigestId(digest.getId());
			digestVo.setCardTitle(digest.getCardTitle());
			digestVo.setMood(digest.getMood());
			digestVo.setAuthorList(digest.getAuthorList());
			digestVo.setSignList(digest.getSignList());
			digestVo.setCardType(digest.getCardType());
			digestVo.setCardUrl("/media/api2.go?action=getDigestContentForH5&digestId=" + digest.getId());
			digestVo.setPic1Path(digest.getPic1Path());
			digestVo.setCardRemark(digest.getCardRemark());
			digestVo.setTopCnt(digest.getTopCnt());
			//判断是否已经收藏
			if(custVo !=null && custVo.getCustId() != null){
				boolean alreayMark = storeUpService.isStoreUp(custVo.getCustId(), digest.getId(), Constans.STORE_UP_DIGEST);
				digestVo.setAlreayMark(alreayMark);
			}else{//未登录时，显示可以收藏
				digestVo.setAlreayMark(false);
			}
			//文章评论数
			Long digestId = digest.getId();
			List<Long> subjectIds = Lists.newArrayList(digestId);
			Map<String, Long> countMap = bookReviewApi.queryBookReviewCount(subjectIds, BookReview.SUBJECT_TYPE_ESSENCE);
			if(MapUtils.isNotEmpty(countMap)){
				Long reallyCount = countMap.get(String.valueOf(digestId));
				if(reallyCount != null && reallyCount != 0L){
					digestVo.setReviewCnt(Integer.parseInt(reallyCount.toString()));
				}
			}
			
			//获取精品对应单品的基本信息
			digestVo.setMediaId(mediaId);
			try {
				EbookAllInfoVo ebookAllInfoVo = iEbookApi.ebookAllInfoVoByProductId(mediaId);
				if(ebookAllInfoVo != null){
					EbookInfoVo ebookInfoVo = ebookAllInfoVo.getEbookInfoVo();
					if(ebookInfoVo != null){
						EbookInfo ebookInfo = digestVo.getEbookInfo();
						String editorRecommend = FormatUtils.Html2Text(ebookAllInfoVo.getEditorRecommend());
						//编辑推荐语
						if(StringUtils.isNotBlank(editorRecommend)){
							ebookInfo.setEditorRecommend(editorRecommend);
						}else{//编辑推荐语为空时，使用摘要
							ebookInfo.setEditorRecommend(FormatUtils.Html2Text(ebookInfoVo.getDescn()));
						}
						ebookInfo.setProductId(mediaId);
						ebookInfo.setBookName(BookUtil.filterEbookForBookName(ebookInfoVo.getTitle()));
						ebookInfo.setBookAuthor(AuthorUtils.getAuthors(ebookInfoVo.getAuthor()));
						ebookInfo.setBookImgUrl(MediaCoverPicUrlUtil.K.getEpubUrl(mediaId));
						digestVo.setEbookInfo(ebookInfo);
					}
				}
			} catch (Exception e) {
				LOGGER.info("获取精品对应单品基本信息失败, 单品mediaId:" + mediaId);
			}
			return digestVo;
		}
		return null;
	}
	
	/**
	 * Description: 获取精品文章对应评论列表
	 * @Version1.0 2015年2月28日 上午10:43:27 by 代鹏（daipeng@dangdang.com）创建
	 * @param bookReviews
	 * @param custId
	 * @return
	 */
	public List<ReturnDigestBookReviewVo> getReturnDigestBookReviewVoList(List<BookReview> bookReviews, String custId){
		List<ReturnDigestBookReviewVo> digestBookReviewVoList = new ArrayList<ReturnDigestBookReviewVo>();
		if(!CollectionUtils.isEmpty(bookReviews)){
			List<BookReview> wrapPriaseBookReviews = bookReviewApi.bookReviewBatchSetPriaseInfo(bookReviews, custId);
			//发表评论用户id集合
			List<Long> custIds = new ArrayList<Long>();
			for(BookReview bookReview:wrapPriaseBookReviews){
				custIds.add(bookReview.getCustomerId());
				ReturnDigestBookReviewVo vo = new ReturnDigestBookReviewVo();
				vo.setBookReviewId(bookReview.getReviewId());
				vo.setCustId(String.valueOf(bookReview.getCustomerId()));
				vo.setComment(bookReview.getComment());
				vo.setCreateDateLong(bookReview.getCreateTime().getTime());
				Integer priaseCount = bookReview.getPraiseCount();
				if(priaseCount != null){
					vo.setPriaseCount(priaseCount);
				}else{
					vo.setPriaseCount(0);
				}
				MobileIsPriase isPriase = bookReview.getMobileIsPriase();
				if(isPriase != null){
					vo.setCanPriase(isPriase.isPriase());
				}else{
					vo.setCanPriase(true);
				}
				digestBookReviewVoList.add(vo);
			}
			//批量获取用户信息
			Map<String, UserBaseInfo> userMap = commonApi.getBatchUserBaseInfoFromCache(custIds, ConfigPropertieUtils.getInteger("user.base.info.expire.time", 7200));
			if(MapUtils.isNotEmpty(userMap)){
				for(ReturnDigestBookReviewVo vo:digestBookReviewVoList){
					String customerId = vo.getCustId();
					UserBaseInfo userBaseInfo = userMap.get(customerId);
					if(userBaseInfo != null){
						vo.setUserImg(userBaseInfo.getCustImg());
						vo.setUserNickName(userBaseInfo.getNickName());
					}
				}
			}
		}
		return digestBookReviewVoList;
	}
	
	
}
