package com.dangdang.digital.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivityCacheDao;
import com.dangdang.digital.dao.IActivitySaleCacheDao;
import com.dangdang.digital.dao.IAuthorCacheDao;
import com.dangdang.digital.dao.IChapterCacheDao;
import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.dao.IDiscoveryCacheDao;
import com.dangdang.digital.dao.IMediaCacheDao;
import com.dangdang.digital.dao.IMediaSaleCacheDao;
import com.dangdang.digital.dao.IUserAuthorityCacheDao;
import com.dangdang.digital.dao.IUserMonthlyCacheDao;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.model.UserAuthorityDetail;
import com.dangdang.digital.vo.AddCacheVo;
import com.dangdang.digital.vo.UserAuthorityCacheVo;
import com.dangdang.digital.vo.UserMonthlyCacheVo;

public class SetCacheJob implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(SetCacheJob.class);
	private IMediaCacheDao mediaCacheDao;
	private IChapterCacheDao chapterCacheDao;
	private IMediaSaleCacheDao mediaSaleCacheDao;
	private IAuthorCacheDao authorCacheDao;
	private IUserAuthorityCacheDao userAuthorityCacheDao;
	private IUserMonthlyCacheDao userMonthlyCacheDao;
	private ISystemApi systemApi;
	private RedisTemplate<String, Object> masterRedisTemplate;
	private IChapterDao chapterDao;
	private RedisTemplate<String, Long> masterRedisTemplateForContents;
	private AddCacheVo addCacheVo;
	private IDiscoveryCacheDao discoveryCacheDao;
	private IActivityCacheDao activityCacheDao;
	private IActivitySaleCacheDao activitySaleCacheDao;

	public SetCacheJob(IMediaCacheDao mediaCacheDao, IChapterCacheDao chapterCacheDao,
			IMediaSaleCacheDao mediaSaleCacheDao, IAuthorCacheDao authorCacheDao,
			IUserAuthorityCacheDao userAuthorityCacheDao, IUserMonthlyCacheDao userMonthlyCacheDao,
			ISystemApi systemApi, RedisTemplate<String, Object> masterRedisTemplate, AddCacheVo addCacheVo,
			IChapterDao chapterDao, RedisTemplate<String, Long> masterRedisTemplateForContents,
			IDiscoveryCacheDao discoveryCacheDao, IActivityCacheDao activityCacheDao,
			IActivitySaleCacheDao activitySaleCacheDao) {
		this.mediaCacheDao = mediaCacheDao;
		this.chapterCacheDao = chapterCacheDao;
		this.mediaSaleCacheDao = mediaSaleCacheDao;
		this.authorCacheDao = authorCacheDao;
		this.userMonthlyCacheDao = userMonthlyCacheDao;
		this.systemApi = systemApi;
		this.masterRedisTemplate = masterRedisTemplate;
		this.addCacheVo = addCacheVo;
		this.chapterDao = chapterDao;
		this.masterRedisTemplateForContents = masterRedisTemplateForContents;
		this.discoveryCacheDao = discoveryCacheDao;
		this.activityCacheDao = activityCacheDao;
		this.activitySaleCacheDao = activitySaleCacheDao;
	}

	public SetCacheJob() {

	}

	@Override
	public void run() {
		switch (addCacheVo.getAddType()) {
		case AddCacheVo.ADD_TYPE_MEDIA_SALE: {
			MediaSale mediaSale = addCacheVo.getMediaSale();
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE));
			try {
				mediaSaleCacheDao.setMediaSaleCacheVo(mediaSale);
			} catch (Exception e) {
				LOGGER.error("新线程增加销售主体缓存出错", e);
			}
			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_SALE + mediaSale.getSaleId(),
					Integer.valueOf(expireTime), TimeUnit.SECONDS);
			break;
		}
		case AddCacheVo.ADD_TYPE_MEDIA_SALE_BATCH: {
			List<MediaSale> mediaSaleList = addCacheVo.getMediaSaleList();
			if (!CollectionUtils.isEmpty(mediaSaleList)) {
				try {
					mediaSaleCacheDao.mSetMediaSaleCacheVo(mediaSaleList);
				} catch (Exception e) {
					LOGGER.error("新线程批量增加销售主体缓存出错", e);
				}
			}
			break;
		}
		case AddCacheVo.ADD_TYPE_MEDIA: {
			Media media = addCacheVo.getMedia();
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
			mediaCacheDao.setMediaCacheBasicVo(media);
			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_BASIC + media.getMediaId(),
					Integer.valueOf(expireTime), TimeUnit.SECONDS);
			mediaCacheDao.setMediaCacheWholeVo(media);
			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_WHOLE + media.getMediaId(),
					Integer.valueOf(expireTime), TimeUnit.SECONDS);
			break;
		}
		case AddCacheVo.ADD_TYPE_MEDIA_BATCH: {
			List<Media> mediaList = addCacheVo.getMediaList();
			if (!CollectionUtils.isEmpty(mediaList)) {
				mediaCacheDao.mSetMediaCacheBasicVo(mediaList);
				mediaCacheDao.mSetMediaCacheWholeVo(mediaList);
			}
			break;
		}
		case AddCacheVo.ADD_TYPE_CHAPTER: {
			Chapter chapter = addCacheVo.getChapter();
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
			chapterCacheDao.setChapterCacheBasicVo(chapter);
			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_CHAPTER_BASIC + chapter.getId(),
					Integer.valueOf(expireTime), TimeUnit.SECONDS);
			chapterCacheDao.setChapterCacheWholeVo(chapter);
			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_CHAPTER_WHOLE + chapter.getId(),
					Integer.valueOf(expireTime), TimeUnit.SECONDS);
			break;
		}
		case AddCacheVo.ADD_TYPE_CHAPTER_BATCH: {
			List<Chapter> chapterList = addCacheVo.getChapterList();

			if (!CollectionUtils.isEmpty(chapterList)) {
				chapterCacheDao.mSetChapterCacheBasicVo(chapterList);
				chapterCacheDao.mSetChapterCacheWholeVo(chapterList);
			}
			break;
		}

		case AddCacheVo.ADD_TYPE_AUTHOR: {
			Author author = addCacheVo.getAuthor();
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
			authorCacheDao.setAuthorCacheVo(author);
			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_AUTHOR + author.getAuthorId(),
					Integer.valueOf(expireTime), TimeUnit.SECONDS);
			break;
		}
		case AddCacheVo.ADD_TYPE_AUTHOR_BATCH: {
			List<Author> authorList = addCacheVo.getAuthorList();

			if (!CollectionUtils.isEmpty(authorList)) {
				authorCacheDao.mSetAuthorCacheVo(authorList);
			}
			break;
		}
		case AddCacheVo.ADD_TYPE_DISCOVERY: {

			if (addCacheVo.getDiscovery() != null) {
				try {
					discoveryCacheDao.setDiscovery(addCacheVo.getDiscovery());
				} catch (Exception e) {
					LOGGER.info("新线程增加发现缓存");
				}
			}
			break;
		}
		case AddCacheVo.ADD_TYPE_CONTENTS: {
			Long mediaId = addCacheVo.getMediaId();
			List<Chapter> chapterList = chapterDao.getAllChapterByMediaId(mediaId);
			// 如果有缓存则删除
			if (masterRedisTemplateForContents.hasKey(Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId)) {
				masterRedisTemplateForContents.delete(Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId);
			}
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_CONTENTS_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_CONTENTS));
			// 重新插入有序集
			for (Chapter chapter : chapterList) {
				masterRedisTemplateForContents.opsForZSet().add(Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId,
						chapter.getId(), chapter.getIndex());
				masterRedisTemplateForContents.expire(Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId,
						Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}
			break;
		}

		case AddCacheVo.ADD_TYPE_AUTHORITY: {
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_AUTHORITY_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_AUTHORITY));
			UserAuthority userAuthority = addCacheVo.getUserAuthority();
			Long custId = userAuthority.getCustId();
			Long mediaId = userAuthority.getMediaId();
			if(mediaId != null){
				userAuthorityCacheDao.deleteUserAuthorityCacheVo(custId, mediaId);
				UserAuthorityCacheVo userAuthorityCacheVo = userAuthorityCacheDao
						.getUserAuthorityCacheVoByCustIdAndMediaId(custId, mediaId,"master");
				if (userAuthorityCacheVo != null) {
					masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_AUTHORITY + custId + "_" + mediaId,
							Integer.valueOf(expireTime), TimeUnit.SECONDS);
				}
			}else{			
				if(!CollectionUtils.isEmpty(userAuthority.getUserAuthorityDetails())){
					Map<Long,Long> mediaIds = new HashMap<Long,Long>();
					for(UserAuthorityDetail detail : userAuthority.getUserAuthorityDetails()){
						if(!mediaIds.containsKey(detail.getMediaId())){
							mediaIds.put(detail.getMediaId(), detail.getMediaId());
							userAuthorityCacheDao.deleteUserAuthorityCacheVo(custId, detail.getMediaId());
							UserAuthorityCacheVo userAuthorityCacheVo = userAuthorityCacheDao
									.getUserAuthorityCacheVoByCustIdAndMediaId(custId, detail.getMediaId(),"master");
							if (userAuthorityCacheVo != null) {
								masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_AUTHORITY + custId + "_" + detail.getMediaId(),
										Integer.valueOf(expireTime), TimeUnit.SECONDS);
							}
						}
					}
				}
			}
			break;
		}
		case AddCacheVo.ADD_TYPE_MONTHLY: {
			UserMonthlyCacheVo userMonthlyCacheVo = addCacheVo.getUserMonthlyCacheVo();
			Long custId = null;
			for (String monthlyPaymentRelation : userMonthlyCacheVo.getUserMonthlys().keySet()) {
				custId = userMonthlyCacheVo.getUserMonthlys().get(monthlyPaymentRelation).getCustId();
			}
			userMonthlyCacheDao.deleteUserMonthlyCacheVo(custId);
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MONTHLY_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MONTHLY));
			UserMonthlyCacheVo nowUserMonthlyCacheVo = userMonthlyCacheDao.getUserMonthlyCacheVoByCustId(custId,"master");
			if (nowUserMonthlyCacheVo != null) {
				masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MONTHLY + custId, Integer.valueOf(expireTime),
						TimeUnit.SECONDS);
			}
			break;
		}

		case AddCacheVo.ADD_TYPE_ACTIVITY: {
			Integer activityId = addCacheVo.getActivityId();
			activityCacheDao.deleteActivityCacheVo(activityId);
			activityCacheDao.getActivityCacheVo(activityId);
			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_ACTIVITY_KEY,
					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_ACTIVITY));
			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_ACTIVITY_INFO + activityId,
					Integer.valueOf(expireTime), TimeUnit.SECONDS);
			activitySaleCacheDao.setActivitySaleCacheByActivityId(activityId);
			break;
		}
		
		case AddCacheVo.ADD_TYPE_ACTIVITY_BATCH: {
			List<Integer> activityIdList = addCacheVo.getActivityIdList();
			if (!CollectionUtils.isEmpty(activityIdList)) {
				activityCacheDao.mDeleteCacheBasicVo(activityIdList);
				activityCacheDao.mSetActivityCacheVo(activityIdList);
			}
			activitySaleCacheDao.mSetActivitySaleCacheByActivityId(activityIdList);
			break;
		}

//		case AddCacheVo.REFRESH_TYPE_MEDIA_SALE: {
//			Long mediaSaleId = addCacheVo.getMediaSaleId();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE));
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_SALE + mediaSaleId, Integer.valueOf(expireTime),
//					TimeUnit.SECONDS);
//			break;
//		}
//
//		case AddCacheVo.REFRESH_TYPE_MEDIA_SALE_BATCH: {
//			List<Long> mediaSaleIdList = addCacheVo.getMediaSaleIdList();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE));
//			for (Long mediaSaleId : mediaSaleIdList) {
//				masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_SALE + mediaSaleId,
//						Integer.valueOf(expireTime), TimeUnit.SECONDS);
//			}
//			break;
//		}
//
//		case AddCacheVo.REFRESH_TYPE_MEDIA: {
//			Long mediaId = addCacheVo.getMediaId();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_BASIC + mediaId, Integer.valueOf(expireTime),
//					TimeUnit.SECONDS);
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_WHOLE + mediaId, Integer.valueOf(expireTime),
//					TimeUnit.SECONDS);
//			break;
//		}
//		case AddCacheVo.REFRESH_TYPE_MEDIA_BATCH: {
//			List<Long> mediaIdList = addCacheVo.getMediaIdList();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
//			for (Long mediaId : mediaIdList) {
//				masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_BASIC + mediaId,
//						Integer.valueOf(expireTime), TimeUnit.SECONDS);
//				masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MEDIA_WHOLE + mediaId,
//						Integer.valueOf(expireTime), TimeUnit.SECONDS);
//			}
//			break;
//		}
//		case AddCacheVo.REFRESH_TYPE_CHAPTER: {
//			Long chapterId = addCacheVo.getChapterId();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_CHAPTER_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_CHAPTER));
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_CHAPTER_BASIC + chapterId,
//					Integer.valueOf(expireTime), TimeUnit.SECONDS);
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_CHAPTER_WHOLE + chapterId,
//					Integer.valueOf(expireTime), TimeUnit.SECONDS);
//			break;
//		}
//		
//		case AddCacheVo.REFRESH_TYPE_CHAPTER_BATCH: {
//			List<Long> chapterIdList = addCacheVo.getChapterIdList();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_CHAPTER_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_CHAPTER));
//			for (Long chapterId : chapterIdList) {
//				masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_CHAPTER_BASIC + chapterId,
//						Integer.valueOf(expireTime), TimeUnit.SECONDS);
//				masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_CHAPTER_WHOLE + chapterId,
//						Integer.valueOf(expireTime), TimeUnit.SECONDS);
//			}
//			break;
//		}
//
//		case AddCacheVo.REFRESH_TYPE_AUTHOR: {
//			Long authorId = addCacheVo.getAuthorId();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_AUTHOR_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_AUTHOR));
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_AUTHOR + authorId, Integer.valueOf(expireTime),
//					TimeUnit.SECONDS);
//			break;
//		}
//
//		case AddCacheVo.REFRESH_TYPE_AUTHOR_BATCH: {
//			List<Long> authorIdList = addCacheVo.getChapterIdList();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_AUTHOR_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_AUTHOR));
//			for (Long authorId : authorIdList) {
//				masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_AUTHOR + authorId, Integer.valueOf(expireTime),
//						TimeUnit.SECONDS);
//			}
//			break;
//		}
//		case AddCacheVo.REFRESH_TYPE_AUTHORITY: {
//			Long custId = addCacheVo.getCustId();
//			Long mediaId = addCacheVo.getMediaId();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_AUTHORITY_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_AUTHORITY));
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_AUTHORITY + custId + "_" + mediaId,
//					Integer.valueOf(expireTime), TimeUnit.SECONDS);
//			break;
//		}
//		case AddCacheVo.REFRESH_TYPE_MONTHLY: {
//			Long custId = addCacheVo.getCustId();
//			String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MONTHLY_KEY,
//					String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MONTHLY));
//			masterRedisTemplate.expire(Constans.CACHE_KEY_PREFIX_MONTHLY + custId, Integer.valueOf(expireTime),
//					TimeUnit.SECONDS);
//			break;
//		}

		}
	}

	public IMediaCacheDao getMediaCacheDao() {
		return mediaCacheDao;
	}

	public void setMediaCacheDao(IMediaCacheDao mediaCacheDao) {
		this.mediaCacheDao = mediaCacheDao;
	}

	public IChapterCacheDao getChapterCacheDao() {
		return chapterCacheDao;
	}

	public void setChapterCacheDao(IChapterCacheDao chapterCacheDao) {
		this.chapterCacheDao = chapterCacheDao;
	}

	public IMediaSaleCacheDao getMediaSaleCacheDao() {
		return mediaSaleCacheDao;
	}

	public void setMediaSaleCacheDao(IMediaSaleCacheDao mediaSaleCacheDao) {
		this.mediaSaleCacheDao = mediaSaleCacheDao;
	}

	public IAuthorCacheDao getAuthorCacheDao() {
		return authorCacheDao;
	}

	public void setAuthorCacheDao(IAuthorCacheDao authorCacheDao) {
		this.authorCacheDao = authorCacheDao;
	}

	public IUserAuthorityCacheDao getUserAuthorityCacheDao() {
		return userAuthorityCacheDao;
	}

	public void setUserAuthorityCacheDao(IUserAuthorityCacheDao userAuthorityCacheDao) {
		this.userAuthorityCacheDao = userAuthorityCacheDao;
	}

	public IUserMonthlyCacheDao getUserMonthlyCacheDao() {
		return userMonthlyCacheDao;
	}

	public void setUserMonthlyCacheDao(IUserMonthlyCacheDao userMonthlyCacheDao) {
		this.userMonthlyCacheDao = userMonthlyCacheDao;
	}

	public ISystemApi getSystemApi() {
		return systemApi;
	}

	public void setSystemApi(ISystemApi systemApi) {
		this.systemApi = systemApi;
	}

	public RedisTemplate<String, Object> getMasterRedisTemplate() {
		return masterRedisTemplate;
	}

	public void setMasterRedisTemplate(RedisTemplate<String, Object> masterRedisTemplate) {
		this.masterRedisTemplate = masterRedisTemplate;
	}

	public IChapterDao getChapterDao() {
		return chapterDao;
	}

	public void setChapterDao(IChapterDao chapterDao) {
		this.chapterDao = chapterDao;
	}

	public RedisTemplate<String, Long> getMasterRedisTemplateForContents() {
		return masterRedisTemplateForContents;
	}

	public void setMasterRedisTemplateForContents(RedisTemplate<String, Long> masterRedisTemplateForContents) {
		this.masterRedisTemplateForContents = masterRedisTemplateForContents;
	}

	public AddCacheVo getAddCacheVo() {
		return addCacheVo;
	}

	public void setAddCacheVo(AddCacheVo addCacheVo) {
		this.addCacheVo = addCacheVo;
	}

	public IDiscoveryCacheDao getDiscoveryCacheDao() {
		return discoveryCacheDao;
	}

	public void setDiscoveryCacheDao(IDiscoveryCacheDao discoveryCacheDao) {
		this.discoveryCacheDao = discoveryCacheDao;
	}

	public IActivityCacheDao getActivityCacheDao() {
		return activityCacheDao;
	}

	public void setActivityCacheDao(IActivityCacheDao activityCacheDao) {
		this.activityCacheDao = activityCacheDao;
	}

	public IActivitySaleCacheDao getActivitySaleCacheDao() {
		return activitySaleCacheDao;
	}

	public void setActivitySaleCacheDao(IActivitySaleCacheDao activitySaleCacheDao) {
		this.activitySaleCacheDao = activitySaleCacheDao;
	}

}
