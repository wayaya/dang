package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ActivitySale;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.model.UserAuthorityDetail;
import com.dangdang.digital.vo.AuthorCacheVo;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.ChapterCacheWholeVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.UserAuthorityCacheVo;

/**
 * 
 * Description: 缓存vo复制工具类 All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 下午5:44:34 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class MediaCacheBeanUtils {

	/**
	 * 
	 * Description: 复制meida字段到meida缓存basicvo
	 * 
	 * @Version1.0 2014年12月6日 下午6:52:06 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param media
	 * @param mediaCacheBasicVo
	 */
	public static void copyMediaToBasicVo(Media media, MediaCacheBasicVo mediaCacheBasicVo) {
		mediaCacheBasicVo.setAuthorId(media.getAuthorId());
		mediaCacheBasicVo.setAuthorName(media.getAuthorName());
		mediaCacheBasicVo.setAuthorPenname(media.getAuthorPenname());
		mediaCacheBasicVo.setCatalog(media.getCatalog());
		mediaCacheBasicVo.setCatetoryId(media.getCatetoryId());
		mediaCacheBasicVo.setCatetoryIds(media.getCatetoryIds());
		mediaCacheBasicVo.setChapterCnt(media.getChapterCnt());
		mediaCacheBasicVo.setCoverPic(media.getCoverPic());
		mediaCacheBasicVo.setCpResourceId(media.getCpResourceId());
		mediaCacheBasicVo.setCreationDate(media.getCreationDate());
		mediaCacheBasicVo.setCreator(media.getCreator());
		mediaCacheBasicVo.setEncrypkey(media.getEncrypkey());
		mediaCacheBasicVo.setFilePath(media.getFilePath());
		mediaCacheBasicVo.setHasNew(media.getHasNew());
		mediaCacheBasicVo.setHdfsPath(media.getHdfsPath());
		mediaCacheBasicVo.setIsFull(media.getIsFull());
		mediaCacheBasicVo.setIsShow(media.getIsShow());
		mediaCacheBasicVo.setIsSupportFullBuy(media.getIsSupportFullBuy());
		mediaCacheBasicVo.setKeyWords(media.getKeyWords());
		mediaCacheBasicVo.setLastModifyDate(media.getLastModifyDate());
		mediaCacheBasicVo.setMediaId(media.getMediaId());
		mediaCacheBasicVo.setModifier(media.getModifier());
		mediaCacheBasicVo.setProductId(media.getProductId());
		mediaCacheBasicVo.setProvideId(media.getProvideId());
		mediaCacheBasicVo.setProviderName(media.getProviderName());
		mediaCacheBasicVo.setRole(media.getRole());
		mediaCacheBasicVo.setShelfStatus(media.getShelfStatus());
		mediaCacheBasicVo.setSignIds(media.getSignIds());
		mediaCacheBasicVo.setSignNames(media.getSignNames());
		mediaCacheBasicVo.setSubTitle(media.getSubTitle());
		mediaCacheBasicVo.setTitle(media.getTitle());
		mediaCacheBasicVo.setUid(media.getUid());
		mediaCacheBasicVo.setSaleId(media.getSaleId());
		mediaCacheBasicVo.setPriceUnit(media.getPriceUnit());
		mediaCacheBasicVo.setLastIndexOrder(media.getLastIndexOrder());
		mediaCacheBasicVo.setLastUpdateChapter(media.getLastUpdateChapter());
		mediaCacheBasicVo.setLastPullChapterDate(media.getLastPullChapterDate());

		mediaCacheBasicVo.setWordCnt(media.getWordCnt());
		mediaCacheBasicVo.setCpShortName(media.getCpShortName());
		mediaCacheBasicVo.setSpeaker(media.getSpeaker());
		mediaCacheBasicVo.setDocType(media.getDocType());
	}

	/**
	 * 
	 * Description: 复制media字段到media缓存wholevo
	 * 
	 * @Version1.0 2014年12月6日 下午6:53:00 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param media
	 * @param mediaCacheWholeVo
	 */
	public static void copyMediaToWholeVo(Media media, MediaCacheWholeVo mediaCacheWholeVo) {
		mediaCacheWholeVo.setAuthorId(media.getAuthorId());
		mediaCacheWholeVo.setAuthorName(media.getAuthorName());
		mediaCacheWholeVo.setAuthorPenname(media.getAuthorPenname());
		mediaCacheWholeVo.setCatalog(media.getCatalog());
		mediaCacheWholeVo.setCatetoryId(media.getCatetoryId());
		mediaCacheWholeVo.setCatetoryIds(media.getCatetoryIds());
		mediaCacheWholeVo.setChapterCnt(media.getChapterCnt());
		mediaCacheWholeVo.setCoverPic(media.getCoverPic());
		mediaCacheWholeVo.setCpResourceId(media.getCpResourceId());
		mediaCacheWholeVo.setCreationDate(media.getCreationDate());
		mediaCacheWholeVo.setCreator(media.getCreator());
		mediaCacheWholeVo.setEncrypkey(media.getEncrypkey());
		mediaCacheWholeVo.setFilePath(media.getFilePath());
		mediaCacheWholeVo.setHasNew(media.getHasNew());
		mediaCacheWholeVo.setHdfsPath(media.getHdfsPath());
		mediaCacheWholeVo.setIsFull(media.getIsFull());
		mediaCacheWholeVo.setIsShow(media.getIsShow());
		mediaCacheWholeVo.setIsSupportFullBuy(media.getIsSupportFullBuy());
		mediaCacheWholeVo.setKeyWords(media.getKeyWords());
		mediaCacheWholeVo.setLastModifyDate(media.getLastModifyDate());
		mediaCacheWholeVo.setMediaId(media.getMediaId());
		mediaCacheWholeVo.setModifier(media.getModifier());
		mediaCacheWholeVo.setProductId(media.getProductId());
		mediaCacheWholeVo.setProvideId(media.getProvideId());
		mediaCacheWholeVo.setProviderName(media.getProviderName());
		mediaCacheWholeVo.setRole(media.getRole());
		mediaCacheWholeVo.setShelfStatus(media.getShelfStatus());
		mediaCacheWholeVo.setSignIds(media.getSignIds());
		mediaCacheWholeVo.setSignNames(media.getSignNames());
		mediaCacheWholeVo.setSubTitle(media.getSubTitle());
		mediaCacheWholeVo.setTitle(media.getTitle());
		mediaCacheWholeVo.setUid(media.getUid());
		mediaCacheWholeVo.setLastPullChapterDate(media.getLastPullChapterDate());
		// 以下是扩展字段
		mediaCacheWholeVo.setDescs(media.getDescs());
		mediaCacheWholeVo.setRecommandWords(media.getRecommandWords());

		mediaCacheWholeVo.setSaleId(media.getSaleId());

		mediaCacheWholeVo.setPriceUnit(media.getPriceUnit());
		mediaCacheWholeVo.setLastIndexOrder(media.getLastIndexOrder());
		mediaCacheWholeVo.setLastUpdateChapter(media.getLastUpdateChapter());

		mediaCacheWholeVo.setWordCnt(media.getWordCnt());
		mediaCacheWholeVo.setCpShortName(media.getCpShortName());
		mediaCacheWholeVo.setSpeaker(media.getSpeaker());
		mediaCacheWholeVo.setDocType(media.getDocType());
	}

	/**
	 * 
	 * Description: 复制chapter字段到chapter缓存basicvo
	 * 
	 * @Version1.0 2014年12月6日 下午6:53:05 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapter
	 * @param chapterCacheBasicVo
	 */
	public static void copyChapterToBasicVo(Chapter chapter, ChapterCacheBasicVo chapterCacheBasicVo) {
		chapterCacheBasicVo.setCreationDate(chapter.getCreationDate());
		chapterCacheBasicVo.setCreator(chapter.getCreator());
		chapterCacheBasicVo.setEncrypkey(chapter.getEncrypkey());
		chapterCacheBasicVo.setFilePath(chapter.getFilePath());
		chapterCacheBasicVo.setId(chapter.getId());
		chapterCacheBasicVo.setIndex(chapter.getIndex());
		chapterCacheBasicVo.setIosPrice(chapter.getIosPrice());
		chapterCacheBasicVo.setIsFree(chapter.getIsFree());
		chapterCacheBasicVo.setIsShow(chapter.getIsShow());
		chapterCacheBasicVo.setLastModifyedDate(chapter.getLastModifyedDate());
		chapterCacheBasicVo.setMediaId(chapter.getMediaId());
		chapterCacheBasicVo.setModifier(chapter.getModifier());
		chapterCacheBasicVo.setPrice(chapter.getPrice());
		chapterCacheBasicVo.setSignIds(chapter.getSignIds());
		chapterCacheBasicVo.setSignNames(chapter.getSignNames());
		chapterCacheBasicVo.setSubTitle(chapter.getSubTitle());
		chapterCacheBasicVo.setTitle(chapter.getTitle());
		chapterCacheBasicVo.setVolumeId(chapter.getVolumeId());
		chapterCacheBasicVo.setWordCnt(chapter.getWordCnt());
		chapterCacheBasicVo.setVolumeName(chapter.getVolumeName());
		chapterCacheBasicVo.setDuration(chapter.getDuration());
	}

	/**
	 * 
	 * Description: 复制chapter字段到chapter缓存wholevo
	 * 
	 * @Version1.0 2014年12月6日 下午6:53:08 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapter
	 * @param chapterCacheWholeVo
	 */
	public static void copyChapterToWholeVo(Chapter chapter, ChapterCacheWholeVo chapterCacheWholeVo) {
		chapterCacheWholeVo.setCreationDate(chapter.getCreationDate());
		chapterCacheWholeVo.setCreator(chapter.getCreator());
		chapterCacheWholeVo.setEncrypkey(chapter.getEncrypkey());
		chapterCacheWholeVo.setFilePath(chapter.getFilePath());
		chapterCacheWholeVo.setId(chapter.getId());
		chapterCacheWholeVo.setIndex(chapter.getIndex());
		chapterCacheWholeVo.setIosPrice(chapter.getIosPrice());
		chapterCacheWholeVo.setIsFree(chapter.getIsFree());
		chapterCacheWholeVo.setIsShow(chapter.getIsShow());
		chapterCacheWholeVo.setLastModifyedDate(chapter.getLastModifyedDate());
		chapterCacheWholeVo.setMediaId(chapter.getMediaId());
		chapterCacheWholeVo.setModifier(chapter.getModifier());
		chapterCacheWholeVo.setPrice(chapter.getPrice());
		chapterCacheWholeVo.setSignIds(chapter.getSignIds());
		chapterCacheWholeVo.setSignNames(chapter.getSignNames());
		chapterCacheWholeVo.setSubTitle(chapter.getSubTitle());
		chapterCacheWholeVo.setTitle(chapter.getTitle());
		chapterCacheWholeVo.setVolumeId(chapter.getVolumeId());
		chapterCacheWholeVo.setWordCnt(chapter.getWordCnt());
		chapterCacheWholeVo.setVolumeName(chapter.getVolumeName());
		chapterCacheWholeVo.setDuration(chapter.getDuration());

		// 以下是扩展字段
		chapterCacheWholeVo.setDescs(chapter.getRecommandWords());
		chapterCacheWholeVo.setRecommandWords(chapter.getRecommandWords());
	}

	/**
	 * 
	 * Description: 通过前缀和id获取缓存key
	 * 
	 * @Version1.0 2014年12月8日 上午10:42:17 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param prefix
	 * @param ids
	 * @return
	 */
	public static List<String> getKeysByPrefix(String prefix, List<Long> ids) {
		List<String> keys = new ArrayList<String>();
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				keys.add(prefix + id);
			}
			return keys;
		}
		return null;
	}
	
	/**
	 * 
	 * Description: 通过前缀和id获取缓存key
	 * 
	 * @Version1.0 2014年12月8日 上午10:42:17 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param prefix
	 * @param ids
	 * @return
	 */
	public static List<String> getKeysByPrefixIneger(String prefix, List<Integer> ids) {
		List<String> keys = new ArrayList<String>();
		if (ids != null && ids.size() > 0) {
			for (Integer id : ids) {
				keys.add(prefix + id);
			}
			return keys;
		}
		return null;
	}
	/**
	 * 
	 * Description: 复制销售主体信息到销售主体缓存vo
	 * 
	 * @Version1.0 2014年12月8日 下午3:10:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param sale
	 * @param mediaSaleCacheVo
	 */
	public static void copyMediaSaleToVo(MediaSale sale, MediaSaleCacheVo mediaSaleCacheVo) {
		mediaSaleCacheVo.setCreationDate(sale.getCreationDate());
		mediaSaleCacheVo.setCreator(sale.getCreator());
		mediaSaleCacheVo.setDesc(sale.getCreator());
		mediaSaleCacheVo.setLastModifiedDate(sale.getLastModifiedDate());
		mediaSaleCacheVo.setModifier(sale.getModifier());
		mediaSaleCacheVo.setName(sale.getName());
		mediaSaleCacheVo.setPrice(sale.getPrice());
		mediaSaleCacheVo.setSaleId(sale.getSaleId());
		mediaSaleCacheVo.setType(sale.getType());
		mediaSaleCacheVo.setCoverPic(sale.getCoverPic());
		mediaSaleCacheVo.setShelfStatus(sale.getShelfStatus());
		mediaSaleCacheVo.setIsSupportFullBuy(sale.getIsSupportFullBuy());
		mediaSaleCacheVo.setIsFull(sale.getIsFull());
	}

	/**
	 * 
	 * Description: 拷贝作者信息缓存vo
	 * 
	 * @Version1.0 2014年12月13日 上午10:36:08 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param author
	 * @param authorCacheVo
	 */
	public static void copyAuthorToCacheVo(Author author, AuthorCacheVo authorCacheVo) {
		authorCacheVo.setAuthorId(author.getAuthorId());
		authorCacheVo.setBirth(author.getBirth());
		authorCacheVo.setCpAuthorId(author.getCpAuthorId());
		authorCacheVo.setCpType(author.getCpType());
		authorCacheVo.setCreationDate(author.getCreationDate());
		authorCacheVo.setCreator(author.getCreator());
		authorCacheVo.setIntroduction(author.getIntroduction());
		authorCacheVo.setLastModifiedDate(author.getLastModifiedDate());
		authorCacheVo.setModifier(author.getModifier());
		authorCacheVo.setName(author.getName());
		authorCacheVo.setPseudonym(author.getPseudonym());
		authorCacheVo.setSex(author.getSex());
		authorCacheVo.setSign(author.getSign());
		authorCacheVo.setHeadPic(author.getHeadPic());
	}

	public static void copyUserAuthorityToCacheVo(UserAuthority userAuthority,
			UserAuthorityCacheVo oldUserAuthorityCacheVo, UserAuthorityCacheVo userAuthorityCacheVo) {
		userAuthorityCacheVo.setAuthorityType(userAuthority.getAuthorityType());
		userAuthorityCacheVo.setCustId(userAuthority.getCustId());
		userAuthorityCacheVo.setLastModifiedDate(userAuthority.getLastModifiedDate());
		userAuthorityCacheVo.setMediaId(userAuthority.getMediaId());
		userAuthorityCacheVo.setMediaType(userAuthority.getMediaType());
		userAuthorityCacheVo.setPayMainPrice(userAuthority.getPayMainPrice());
		userAuthorityCacheVo.setPaySubPrice(userAuthority.getPaySubPrice());
		Map<Long, Long> chapterIdsMap = null;
		if (oldUserAuthorityCacheVo != null && oldUserAuthorityCacheVo.getChapterIdsMap() != null) {
			chapterIdsMap = oldUserAuthorityCacheVo.getChapterIdsMap();
		}
		if (!userAuthority.getUserAuthorityDetails().isEmpty()) {
			if (chapterIdsMap == null) {
				chapterIdsMap = new HashMap<Long, Long>();
			}
			for (UserAuthorityDetail detail : userAuthority.getUserAuthorityDetails()) {
				chapterIdsMap.put(detail.getMediaChapterId(), detail.getCreationDate().getTime());
			}
			userAuthorityCacheVo.setChapterIdsMap(chapterIdsMap);
		}
	}

	/**
	 * 
	 * Description: 复制mediaCacheWholeVo字段到mediaCacheBasicVo
	 * 
	 * @Version1.0 2014年12月19日 下午6:52:06 by 魏嵩（weisong@dangdang.com）创建
	 * @param mediaCacheWholeVo
	 * @param mediaCacheBasicVo
	 */
	private static void copyMediaToBasicVo(MediaCacheWholeVo mediaCacheWholeVo, MediaCacheBasicVo mediaCacheBasicVo) {
		mediaCacheBasicVo.setAuthorId(mediaCacheWholeVo.getAuthorId());
		mediaCacheBasicVo.setAuthorName(mediaCacheWholeVo.getAuthorName());
		mediaCacheBasicVo.setAuthorPenname(mediaCacheWholeVo.getAuthorPenname());
		mediaCacheBasicVo.setCatalog(mediaCacheWholeVo.getCatalog());
		mediaCacheBasicVo.setCatetoryId(mediaCacheWholeVo.getCatetoryId());
		mediaCacheBasicVo.setCatetoryIds(mediaCacheWholeVo.getCatetoryIds());
		mediaCacheBasicVo.setChapterCnt(mediaCacheWholeVo.getChapterCnt());
		mediaCacheBasicVo.setCoverPic(mediaCacheWholeVo.getCoverPic());
		mediaCacheBasicVo.setCpResourceId(mediaCacheWholeVo.getCpResourceId());
		mediaCacheBasicVo.setCreationDate(mediaCacheWholeVo.getCreationDate());
		mediaCacheBasicVo.setCreator(mediaCacheWholeVo.getCreator());
		mediaCacheBasicVo.setEncrypkey(mediaCacheWholeVo.getEncrypkey());
		mediaCacheBasicVo.setFilePath(mediaCacheWholeVo.getFilePath());
		mediaCacheBasicVo.setHasNew(mediaCacheWholeVo.getHasNew());
		mediaCacheBasicVo.setHdfsPath(mediaCacheWholeVo.getHdfsPath());
		mediaCacheBasicVo.setIsFull(mediaCacheWholeVo.getIsFull());
		mediaCacheBasicVo.setIsShow(mediaCacheWholeVo.getIsShow());
		mediaCacheBasicVo.setIsSupportFullBuy(mediaCacheWholeVo.getIsSupportFullBuy());
		mediaCacheBasicVo.setKeyWords(mediaCacheWholeVo.getKeyWords());
		mediaCacheBasicVo.setLastModifyDate(mediaCacheWholeVo.getLastModifyDate());
		mediaCacheBasicVo.setMediaId(mediaCacheWholeVo.getMediaId());
		mediaCacheBasicVo.setModifier(mediaCacheWholeVo.getModifier());
		mediaCacheBasicVo.setProductId(mediaCacheWholeVo.getProductId());
		mediaCacheBasicVo.setProvideId(mediaCacheWholeVo.getProvideId());
		mediaCacheBasicVo.setProviderName(mediaCacheWholeVo.getProviderName());
		mediaCacheBasicVo.setRole(mediaCacheWholeVo.getRole());
		mediaCacheBasicVo.setShelfStatus(mediaCacheWholeVo.getShelfStatus());
		mediaCacheBasicVo.setSignIds(mediaCacheWholeVo.getSignIds());
		mediaCacheBasicVo.setSignNames(mediaCacheWholeVo.getSignNames());
		mediaCacheBasicVo.setSubTitle(mediaCacheWholeVo.getSubTitle());
		mediaCacheBasicVo.setTitle(mediaCacheWholeVo.getTitle());
		mediaCacheBasicVo.setUid(mediaCacheWholeVo.getUid());
		mediaCacheBasicVo.setSaleId(mediaCacheWholeVo.getSaleId());
		mediaCacheBasicVo.setPriceUnit(mediaCacheWholeVo.getPriceUnit());
		mediaCacheBasicVo.setLastIndexOrder(mediaCacheWholeVo.getLastIndexOrder());
		mediaCacheBasicVo.setLastUpdateChapter(mediaCacheWholeVo.getLastUpdateChapter());
		mediaCacheBasicVo.setLastPullChapterDate(mediaCacheWholeVo.getLastPullChapterDate());

		mediaCacheBasicVo.setWordCnt(mediaCacheWholeVo.getWordCnt());
		mediaCacheBasicVo.setCpShortName(mediaCacheWholeVo.getCpShortName());
	}

	/**
	 * 
	 * Description: 复制mediaCacheWholeVo字段到mediaCacheBasicVo,
	 * 然后返回mediaCacheBasicVo
	 * 
	 * @Version1.0 2014年12月19日 下午6:52:06 by 魏嵩（weisong@dangdang.com）创建
	 * @param mediaCacheWholeVo
	 */
	public static MediaCacheBasicVo getMediaBasicVo(MediaCacheWholeVo mediaCacheWholeVo) {

		MediaCacheBasicVo mediaCacheBasicVo = new MediaCacheBasicVo();
		copyMediaToBasicVo(mediaCacheWholeVo, mediaCacheBasicVo);
		return mediaCacheBasicVo;
	}

	/**
	 * 
	 * Description: 复制活动信息到活动缓存vo
	 * 
	 * @Version1.0 2015年1月10日 下午3:01:46 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityInfo
	 * @param activityCacheVo
	 */
	public static void copyActivityInfoToActivityCacheVo(ActivityInfo activityInfo, ActivityCacheVo activityCacheVo) {
		activityCacheVo.setActivityId(activityInfo.getActivityId());
		activityCacheVo.setActivityName(activityInfo.getActivityName());
		activityCacheVo.setActivityTypeCode(activityInfo.getActivityTypeCode());
		activityCacheVo.setActivityTypeId(activityInfo.getActivityTypeId());
		if(ActivityTypeEnum.getByKey(activityInfo.getActivityTypeId())!=null){
			activityCacheVo.setActivityTypeName(ActivityTypeEnum.getByKey(activityInfo.getActivityTypeId()).getName());
		}
		activityCacheVo.setConsumeChapter(activityInfo.getConsumeChapter());
		activityCacheVo.setDiscount(activityInfo.getDiscount());
		activityCacheVo.setEndTime(activityInfo.getEndTime());
		activityCacheVo.setFixedPrice(activityInfo.getFixedPrice());
		activityCacheVo.setIsFixedPrice(activityInfo.getIsFixedPrice());
		activityCacheVo.setIsWholeMedia(activityInfo.getIsWholeMedia());
		activityCacheVo.setStartTime(activityInfo.getStartTime());
		activityCacheVo.setStatus(activityInfo.getStatus());
	}
	
	/**
	 * 
	 * Description: 复制活动销售主体关系到缓存vo
	 * @Version1.0 2015年1月10日 下午3:39:33 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activitySale
	 * @param activitySaleCacheVo
	 */
	public static void copyActivitySaleToActivitySaleCacheVo(ActivitySale activitySale,ActivitySaleCacheVo activitySaleCacheVo){
		activitySaleCacheVo.setActivityId(activitySale.getActivityId());
		activitySaleCacheVo.setActivitySaleId(activitySale.getActivitySaleId());
		activitySaleCacheVo.setEndTime(activitySale.getEndTime());
		activitySaleCacheVo.setSaleId(activitySale.getSaleId());
		activitySaleCacheVo.setSalePrice(activitySale.getSalePrice());
		activitySaleCacheVo.setStartTime(activitySale.getStartTime());
		activitySaleCacheVo.setStatus(activitySale.getStatus());
	}

}
