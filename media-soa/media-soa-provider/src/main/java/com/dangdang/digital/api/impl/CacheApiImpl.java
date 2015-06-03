package com.dangdang.digital.api.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.AnnouncementsContent;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.model.ListCategory;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.Notice;
import com.dangdang.digital.model.SpecialTopic;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.profile.LoggerProfile;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.vo.AuthorCacheVo;
import com.dangdang.digital.vo.CatetoryMediaSaleVo;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.ChapterCacheWholeVo;
import com.dangdang.digital.vo.ContentsVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.UserAuthorityCacheVo;
import com.dangdang.digital.vo.UserMonthlyCacheVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 缓存api实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年12月8日 下午4:27:20 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("cacheApi")
public class CacheApiImpl implements ICacheApi {
	@Resource
	ICacheService cacheService;

	public List<Notice> getNoticeList(long lastRequestTime) {
		return cacheService.getNoticeList(lastRequestTime);
	}

	public Catetory getCatetoryFromCache(String code) {
		return cacheService.getCatetoryFromCache(code);
	}

	@Override
	@LoggerProfile(methodNote = "设置销售主体缓存", recordTime = true)
	public void setMediaSaleCache(MediaSale mediaSale) throws Exception {
		cacheService.setMediaSaleCache(mediaSale);

	}

	@Override
	@LoggerProfile(methodNote = "批量设置销售主体缓存", recordTime = true)
	public void batchSetMediaSaleCache(List<MediaSale> mediaSaleList) throws Exception {
		cacheService.batchSetMediaSaleCache(mediaSaleList);

	}

	@Override
	@LoggerProfile(methodNote = "设置media缓存", recordTime = true)
	public void setMediaCache(Media media) throws Exception {
		cacheService.setMediaCache(media);

	}

	@Override
	@LoggerProfile(methodNote = "批量设置media主体缓存", recordTime = true)
	public void batchSetMediaCache(List<Media> mediaList) throws Exception {
		cacheService.batchSetMediaCache(mediaList);

	}

	@Override
	@LoggerProfile(methodNote = "设置章节缓存", recordTime = true)
	public void setChapterCache(Chapter chapter) throws Exception {
		cacheService.setChapterCache(chapter);

	}

	@Override
	@LoggerProfile(methodNote = "批量设置章节缓存", recordTime = true)
	public void batchSetChapterCache(List<Chapter> chapterList) throws Exception {
		cacheService.batchSetChapterCache(chapterList);

	}

	@Override
	public MediaSaleCacheVo getMediaSaleFromCache(Long saleId) throws Exception {
		return cacheService.getMediaSaleFromCache(saleId);
	}

	@Override
	public List<MediaSaleCacheVo> batchGetMediaSaleFromCache(List<Long> saleIdList) throws Exception {
		return cacheService.batchGetMediaSaleFromCache(saleIdList);
	}

	@Override
	public MediaCacheBasicVo getMediaBasicFromCache(Long mediaId) throws Exception {
		return cacheService.getMediaBasicFromCache(mediaId);
	}

	@Override
	public List<MediaCacheBasicVo> batchGetMediaBasicFromCache(List<Long> mediaIdList) throws Exception {
		return cacheService.batchGetMediaBasicFromCache(mediaIdList);
	}

	@Override
	public MediaCacheWholeVo getMediaWholeFromCache(Long mediaId) throws Exception {
		return cacheService.getMediaWholeFromCache(mediaId);
	}

	@Override
	public List<MediaCacheWholeVo> batchGetMediaWholeFromCache(List<Long> mediaIdList) throws Exception {
		return cacheService.batchGetMediaWholeFromCache(mediaIdList);
	}

	@Override
	public ChapterCacheBasicVo getChapterBasicFromCache(Long chapterId) throws Exception {
		return cacheService.getChapterBasicFromCache(chapterId);
	}

	@Override
	public List<ChapterCacheBasicVo> batchGetChapterBasicFromCache(List<Long> chapterIdList) throws Exception {
		return cacheService.batchGetChapterBasicFromCache(chapterIdList);
	}

	@Override
	public ChapterCacheWholeVo getChapterWholeFromCache(Long chapterId) throws Exception {
		return cacheService.getChapterWholeFromCache(chapterId);
	}

	@Override
	public List<ChapterCacheWholeVo> batchGetChapterWholeFromCache(List<Long> chapterIdList) throws Exception {
		return cacheService.batchGetChapterWholeFromCache(chapterIdList);
	}

	@Override
	public void deleteMediaSaleCache(Long saleId) throws Exception {
		cacheService.deleteMediaSaleCache(saleId);
	}

	@Override
	public void batchDeleteMediaSaleCache(List<Long> saleIdList) throws Exception {
		cacheService.batchDeleteMediaSaleCache(saleIdList);
	}

	@Override
	public void cleanMediaSaleCache() throws Exception {
		cacheService.cleanMediaSaleCache();
	}

	@Override
	public void deleteMediaBasicCache(Long mediaId) throws Exception {
		cacheService.deleteMediaBasicCache(mediaId);
	}

	@Override
	public void batchDeleteMediaBasicCache(List<Long> mediaIdList) throws Exception {
		cacheService.batchDeleteMediaBasicCache(mediaIdList);
	}

	@Override
	public void cleanMediaBasicCache() throws Exception {
		cacheService.cleanMediaBasicCache();
	}

	@Override
	public void deleteMediaWholeCache(Long saleId) throws Exception {
		cacheService.deleteMediaWholeCache(saleId);
	}

	@Override
	public void batchDeleteMediaWholeCache(List<Long> saleIdList) throws Exception {
		cacheService.batchDeleteMediaWholeCache(saleIdList);
	}

	@Override
	public void cleanMediaWholeCache() throws Exception {
		cacheService.cleanMediaWholeCache();
	}

	@Override
	public void deleteChapterBasicCache(Long chapterId) throws Exception {
		cacheService.deleteChapterBasicCache(chapterId);
	}

	@Override
	public void batchDeleteChapterBasicCache(List<Long> chapterIdList) throws Exception {
		cacheService.batchDeleteChapterBasicCache(chapterIdList);
	}

	@Override
	public void cleanChapterBasicCache() throws Exception {
		cacheService.cleanChapterBasicCache();
	}

	@Override
	public void deleteChapterWholeCache(Long chapterId) throws Exception {
		cacheService.deleteChapterWholeCache(chapterId);
	}

	@Override
	public void batchDeleteChapterWholeCache(List<Long> chapterIdList) throws Exception {
		cacheService.batchDeleteChapterWholeCache(chapterIdList);
	}

	@Override
	public void cleanChapterWholeCache() throws Exception {
		cacheService.cleanChapterWholeCache();
	}

	@Override
	public UserTradeBaseVo getUserInfoByToken(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return cacheService.getUserInfoByToken(token);
	}

	@Override
	public UserTradeBaseVo getUserWholeInfoByToken(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return cacheService.getUserWholeInfoByToken(token);
	}

	@Override
	public Column getColumnFromCache(String columnType) {
		return cacheService.getColumnFromCache(columnType);
	}

	public ListCategory getListCategoryFromCache(String listType) {
		return cacheService.getListCategoryFromCache(listType);

	}

	@Override
	public void setAuthorCache(Author author) throws Exception {
		cacheService.setAuthorCache(author);

	}

	@Override
	public void batchSetAuthorCache(List<Author> authorList) throws Exception {
		cacheService.batchSetAuthorCache(authorList);

	}

	@Override
	public AuthorCacheVo getAuthorFromCache(Long authorId) throws Exception {
		return cacheService.getAuthorFromCache(authorId);
	}

	@Override
	public List<AuthorCacheVo> batchGetAuthorFromCache(List<Long> authorIdList) throws Exception {
		return cacheService.batchGetAuthorFromCache(authorIdList);
	}

	@Override
	public void deleteAuthorCache(Long authorId) throws Exception {
		cacheService.deleteAuthorCache(authorId);

	}

	@Override
	public void batchDeleteAuthorCache(List<Long> authorIdList) throws Exception {
		cacheService.batchDeleteAuthorCache(authorIdList);

	}

	@Override
	public void cleanAuthorCache() throws Exception {
		cacheService.cleanAuthorCache();

	}

	@Override
	public List<Long> getChapterIdsFromCache(Long mediaId, Integer start, Integer end) {
		return cacheService.getChapterIdsFromCache(mediaId, start, end);
	}

	@Override
	public int getChapterCountFromCache(Long mediaId) {
		return cacheService.getChapterCountFromCache(mediaId);
	}

	@Override
	public UserAuthorityCacheVo getUserAuthorityCacheVoByCustIdAndMediaId(Long custId, Long mediaId) {
		return cacheService.getUserAuthorityCacheVoByCustIdAndMediaId(custId, mediaId);
	}

	@Override
	public void setUserAuthorityCacheVo(UserAuthority userAuthority) {
		cacheService.setUserAuthorityCacheVo(userAuthority);
	}

	@Override
	public UserMonthlyCacheVo getUserMonthlyCacheVoByCustId(Long custId) {
		return cacheService.getUserMonthlyCacheVoByCustId(custId);
	}

	@Override
	public void updateUserMonthlyCacheVo(List<UserMonthly> userMonthlys) {
		if (CollectionUtils.isEmpty(userMonthlys)) {
			return;
		}
		UserMonthlyCacheVo userMonthlyCacheVo = new UserMonthlyCacheVo();
		for (UserMonthly userMonthly : userMonthlys) {
			userMonthlyCacheVo.getUserMonthlys().put(userMonthly.getMonthlyPaymentRelation(), userMonthly);
		}
		cacheService.updateUserMonthlyCacheVo(userMonthlyCacheVo);
	}

	@Override
	public Map<Long, ContentsVo> getContentsFromCache(Long mediaId, Integer start, Integer end) throws Exception {
		return cacheService.getContentsFromCache(mediaId, start, end);
	}

	@Override
	public void setDiscoveryCache(Discovery dis) {
		cacheService.setDiscoveryCache(dis);
	}

	@Override
	public Discovery getDisCoveryFromCache(Long discoveryId) {
		return cacheService.getDiscoveryFromCache(discoveryId);
	}

	@Override
	public void deleteDiscoveryFromCache(Long discoveryId) {
		cacheService.deleteDiscoveryFromCache(discoveryId);
	}

	@Override
	public List<UserAuthorityCacheVo> getUserAuthorityCacheVoByCustId(Long custId) {
		return cacheService.getUserAuthorityCacheVoByCustId(custId);
	}

	@Override
	public List<Catetory> getCatetoryListFromCache(final String parentCode) {
		return cacheService.getCatetoryListFromCache(parentCode);
	}

	@Override
	public ResultTwoTuple<Long, List<MediaSaleCacheVo>> getMediaSaleByColumnCode(final int start, final int end, final String columnType)
			throws Exception {
		return cacheService.getMediaSaleByColumnCode(start, end, columnType);
	}

	@Override
	public long getColumnContentCount(final String columnCode) {
		return cacheService.getCacheListSize(Constans.COLUMN_CONTENT_CACHE_KEY + columnCode);
	}

	@Override
	public List<MediaSaleCacheVo> getMediaSaleByRankingListCode(final int start, final int end, final String listType) throws Exception {
		return cacheService.getMediaSaleByRankingListCode(start, end, listType);
	}

	@Override
	public long getRankingListCount(final String listType) {
		return cacheService.getCacheListSize(Constans.RANKING_LIST_CACHE_KEY + listType);
	}

	@Override
	public long getHotFreeEndTimeFromCache(final int activityId) {
		return cacheService.getHotFreeEndTimeFromCache(activityId);
	}

	@Override
	public List<MediaSaleCacheVo> getHotFreeFromCache(final int start, final int end, final int activityId) throws Exception {
		return cacheService.getHotFreeFromCache(start, end, activityId);
	}

	@Override
	public List<MediaSaleCacheVo> getCategoryMediaSaleByCodeFromCache(final int start, final int end, final String categoryCode,
			final String dimension) throws Exception {
		return cacheService.getCategoryMediaSaleByCodeFromCache(start, end, categoryCode, dimension);
	}

	@Override
	public long getCategoryCount(String categoryCode, String dimension) {
		return cacheService.getMediaCountByCategoryAndDimension(categoryCode, dimension);
	}

	@Override
	public List<Long> getOnShelfSaleIdList(List<Long> saleIdList) {
		return cacheService.getOnShelfSaleIdList(saleIdList);
	}

	@Override
	public void cleanColumnCachce() throws Exception {
		cacheService.cleanCacheByKeyPattern(Constans.COLUMN_CONTENT_CACHE_KEY + "*");

	}

	@Override
	public void cleanColumnCachce(String columnCode) throws Exception {
		cacheService.cleanCacheByKey(Constans.COLUMN_CACHE_KEY + columnCode.toLowerCase());
		cacheService.cleanCacheByKey(Constans.COLUMN_CONTENT_CACHE_KEY + columnCode.toLowerCase());
	}

	@Override
	public void cleanSystemPropertyCache(String key) throws Exception {
		cacheService.cleanCacheByKey(Constans.SYSTEM_PROPERTY_KEY + key.toLowerCase());
	}

	@Override
	public void cleanListRankingCache() throws Exception {
		cacheService.cleanCacheByKeyPattern(Constans.RANKING_LIST_CACHE_KEY + "*");
	}

	@Override
	public void cleanListRankingCache(String listType) throws Exception {
		cacheService.cleanCacheByKey(Constans.RANKING_LIST_CACHE_KEY + listType.toLowerCase());
		cacheService.cleanCacheByKey(Constans.LISTCATEGORY_CACHE_KEY + listType.toLowerCase());
	}

	@Override
	public void cleanRewardRankCache() throws Exception {
		cacheService.cleanCacheByKeyPattern(Constans.MEDIA_RANK_CONS_BOOK_KEY + "*");
	}

	@Override
	public void cleanRewardRankCache(String listType) throws Exception {
		cacheService.cleanCacheByKey(Constans.MEDIA_RANK_CONS_BOOK_KEY + listType);

	}

	@Override
	public void cleanMediaCategoryCache() throws Exception {
		cacheService.cleanCacheByKeyPattern(Constans.MEDIA_CATEGORY_DATA_CACHE_KEY + "*");
	}

	@Override
	public void cleanMediaCategoryCache(String categoryDimension) throws Exception {
		cacheService.cleanCacheByKey(Constans.MEDIA_CATEGORY_DATA_CACHE_KEY + categoryDimension.toLowerCase());
	}

	@Override
	public void deleteTodayActivityUserCache(Long custId) {
		cacheService.deleteTodayActivityUserCache(custId);
	}

	@Override
	public void deleteActivityUserVoCache(Long custId) {
		cacheService.deleteActivityUserVoCache(custId);
	}

	@Override
	public void cleanActivityUserVoCache() {
		cacheService.cleanActivityUserVoCache();
	}

	@Override
	public void deleteBlockCache(String code) {
		cacheService.deleteBlockCache(code);
	}

	@Override
	public void deleteEbookRewardedUsersCache(Long mediaId, String channel) {
		cacheService.deleteEbookRewardedUsersCache(mediaId, channel);
	}

	@Override
	public void deleteUserRewardBooksIdsCache(Long custId) {
		cacheService.deleteUserRewardBooksIdsCache(custId);
	}

	@Override
	public void deletePrizeListCache(int vestType) {
		cacheService.deletePrizeListCache(vestType);
	}

	@Override
	public void deleteRankConsToBookByCodeCache(String code) {
		cacheService.deleteRankConsToBookByCodeCache(code);
	}

	@Override
	public List<AnnouncementsContent> getAnnouncementsContentFromCache(String categoryCode) {
		// TODO Auto-generated method stub
		return cacheService.getAnnouncementsContentFromCache(categoryCode);
	}

	@Override
	public AnnouncementsCategory getAnnouncementsCategoryFromCache(String categoryCode) {
		return cacheService.getAnnouncementsCategoryFromCache(categoryCode);
	}

	@Override
	public List<CatetoryMediaSaleVo> getMediaSaleByCatetoryCode(boolean withData, int start, int end, String catetoryCode) throws Exception {
		return cacheService.getMediaSaleByCatetoryCode(withData, start, end, catetoryCode);
	}

	@Override
	public void refreshActivityCache(Integer activityId) {
		cacheService.refreshActivityCache(activityId);

	}

	@Override
	public ActivityCacheVo getActivityFromCache(Integer activityId) {
		return cacheService.getActivityFromCache(activityId);
	}

	@Override
	public void deleteActivityCache(Integer activityId) {
		cacheService.deleteActivityCache(activityId);

	}

	@Override
	public void batchRefreshActivityCache(List<Integer> activityIdList) {
		cacheService.batchRefreshActivityCache(activityIdList);

	}

	@Override
	public List<ActivityCacheVo> batchGetActivityFromCache(List<Integer> activityIdList) {
		return cacheService.batchGetActivityFromCache(activityIdList);
	}

	@Override
	public void batchDeleteActivityCache(List<Integer> activityIdList) {
		cacheService.batchDeleteActivityCache(activityIdList);

	}

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheByActivityId(Integer activityId) {
		return cacheService.getActivitySaleCacheByActivityId(activityId);
	}

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheBySaleId(Long saleId) {
		return cacheService.getActivitySaleCacheBySaleId(saleId);
	}

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheWithActivityBySaleId(Long saleId) {
		return cacheService.getActivitySaleCacheWithActivityBySaleId(saleId);
	}

	@Override
	public void deleteActivitySaleCacheBySaleId(Long saleId) {
		cacheService.deleteActivitySaleCacheBySaleId(saleId);

	}

	@Override
	public void mDeleteActivitySaleCacheBySaleId(List<Long> saleIdList) {
		cacheService.mDeleteActivitySaleCacheBySaleId(saleIdList);

	}

	@Override
	public void cleanActivityCache() {
		cacheService.cleanActivityCache();

	}

	@Override
	public void cleanCacheByKey(String cacheKey) {
		cacheService.cleanCacheByKey(cacheKey);
	}

	@Override
	public Map<String, String> getUserInfoByCustId(Long custId) {
		return cacheService.getUserInfoByCustId(custId);
	}

	@Override
	public ResultTwoTuple<Integer, List<WorshipRecord>> getWorshipRecord(final int start, final int end, Long custId, String type) {
		return cacheService.getWorshipRecord(start, end, custId, type);
	}

	@Override
	public List<SpecialTopic> getSpecialTopicFromCache(String stId, String deviceType, String channelType) {
		return cacheService.getSpecialTopicFromCache(stId, deviceType, channelType);
	}

	@Override
	public void cleanSpecialTopicCache(Set<String> cacheKeys) {
		cacheService.cleanSpecialTopicCache(cacheKeys);
	}

	@Override
	public Map<Long, UserTradeBaseVo> getUserInfoByCustIds(List<Long> custIdList) {
		return cacheService.getUserInfoByCustIds(custIdList);
	}

	@Override
	public UserTradeBaseVo getUserWholeInfoByCustId(Long custId) {
		return cacheService.getUserWholeInfoByCustId(custId);
	}

}
