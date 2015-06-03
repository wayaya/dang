package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.api.ICommonApi;
import com.dangdang.digital.api.IRankingListApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivityCacheDao;
import com.dangdang.digital.dao.IActivitySaleCacheDao;
import com.dangdang.digital.dao.IActivitySaleDao;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.dao.IAnnouncementCacheDao;
import com.dangdang.digital.dao.IAuthorCacheDao;
import com.dangdang.digital.dao.ICatetoryCacheDao;
import com.dangdang.digital.dao.ICatetoryDao;
import com.dangdang.digital.dao.IChapterCacheDao;
import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.dao.IColumnCacheDao;
import com.dangdang.digital.dao.IColumnContentDao;
import com.dangdang.digital.dao.IColumnDao;
import com.dangdang.digital.dao.IDiscoveryCacheDao;
import com.dangdang.digital.dao.IListCategoryCacheDao;
import com.dangdang.digital.dao.IListCategoryDao;
import com.dangdang.digital.dao.IMediaCacheDao;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.dao.IMediaSaleCacheDao;
import com.dangdang.digital.dao.IMediaStatisticsDao;
import com.dangdang.digital.dao.INoticeDao;
import com.dangdang.digital.dao.ISpecialTopicCacheDao;
import com.dangdang.digital.dao.IUserAuthorityCacheDao;
import com.dangdang.digital.dao.IUserMonthlyCacheDao;
import com.dangdang.digital.dao.IVolumeDao;
import com.dangdang.digital.job.SetCacheJob;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ActivitySale;
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
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IAuthorService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.IVolumeService;
import com.dangdang.digital.service.IWorshipRecordService;
import com.dangdang.digital.utils.CatetoryUtils;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.vo.AddCacheVo;
import com.dangdang.digital.vo.AuthorCacheVo;
import com.dangdang.digital.vo.CatetoryMediaSaleVo;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.ChapterCacheWholeVo;
import com.dangdang.digital.vo.ChapterContentsVo;
import com.dangdang.digital.vo.ContentsVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.UserAuthorityCacheVo;
import com.dangdang.digital.vo.UserMonthlyCacheVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 缓存service实现 All Rights Reserved.
 * 
 * @version 1.0 2014年12月8日 下午5:02:24 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Service
public class CacheServiceImpl implements ICacheService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

	@Resource(name = "specialTopicCacheDao")
	private ISpecialTopicCacheDao specialTopicCacheDao;
	@Resource(name = "mediaCacheDao")
	private IMediaCacheDao mediaCacheDao;
	@Resource(name = "chapterCacheDao")
	private IChapterCacheDao chapterCacheDao;
	@Resource(name = "authorCacheDao")
	private IAuthorCacheDao authorCacheDao;
	@Resource(name = "mediaSaleCacheDao")
	private IMediaSaleCacheDao mediaSaleCacheDao;
	@Resource(name = "activityCacheDao")
	private IActivityCacheDao activityCacheDao;
	@Resource(name = "activitySaleCacheDao")
	private IActivitySaleCacheDao activitySaleCacheDao;
	@Resource
	private IUserAuthorityCacheDao userAuthorityCacheDao;
	@Resource
	private IUserMonthlyCacheDao userMonthlyCacheDao;
	@Resource(name = "mediaSaleService")
	private IMediaSaleService mediaSaleService;
	@Resource(name = "mediaService")
	private IMediaService mediaService;
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource(name = "chapterService")
	private IChapterService chapterService;
	@Resource(name = "authorService")
	private IAuthorService authorService;
	@Resource(name = "systemApi")
	private ISystemApi systemApi;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Object> masterRedisTemplate;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Object> slaveRedisTemplate;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<MediaSaleCacheVo>> masterRedisTemplateForMediaSaleBatch;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<MediaSaleCacheVo>> slaveRedisTemplateForMediaSaleBatch;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Long> masterRedisTemplateForContents;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Long> slaveRedisTemplateForContents;

	/**
	 * add by lvxiang ,放入Id列表操作
	 */
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<Long>> masterRedisTemplateForId;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<Long>> slaveRedisTemplateForId;

	@Resource
	IVolumeService volumeService;
	@Resource
	private IVolumeDao volumeDao;
	@Resource
	private IChapterDao chapterDao;
	@Resource
	private ICommonApi commonApi;

	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IMediaDao mediaDao;
	@Resource
	private IDiscoveryCacheDao discoveryCacheDao;

	// 栏目缓存
	@Resource
	private IColumnCacheDao columnCacheDao;

	// 栏目
	@Resource
	private IColumnDao columnDao;
	// 栏目内容
	@Resource
	private IColumnContentDao columnContentDao;

	// 榜单基本信息缓存
	@Resource
	private IListCategoryCacheDao listCategoryCacheDao;

	// 榜单基本信息
	@Resource
	private IListCategoryDao listCategoryDao;

	@Resource
	private IRankingListApi rankingListApi;

	// 原公告缓存(暂时没有用到)
	@Resource
	private IAnnouncementCacheDao announcementCacheDao;

	// 新公告
	@Resource
	private INoticeDao noticeDao;

	@Resource
	private IWorshipRecordService worshipRecordService;
	// 活动
	@Resource
	private IActivitySaleDao activitySaleDao;

	// media统计
	@Resource
	private IMediaStatisticsDao mediaStatisticsDao;
	// 图书分类信息
	@Resource
	private ICatetoryDao catetoryDao;

	@Resource
	private ICatetoryCacheDao catetoryCacheDao;

	@Resource
	private IActivityUserCacheDao activityUserCacheDao;

	@Override
	public void setMediaSaleCache(MediaSale mediaSale) throws Exception {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_MEDIA_SALE);
		mediaSale = mediaSaleService.findMasterById(mediaSale.getSaleId());
		if (mediaSale == null) {
			return;
		}
		addCacheVo.setMediaSale(mediaSale);

		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);

	}

	@Override
	public void batchSetMediaSaleCache(List<MediaSale> mediaSaleList) throws Exception {
		if (!CollectionUtils.isEmpty(mediaSaleList)) {
			List<Long> saleIdList = new ArrayList<Long>();
			for (MediaSale sale : mediaSaleList) {
				saleIdList.add(sale.getSaleId());
			}
			mediaSaleList = mediaSaleService.findMasterByIds(saleIdList);
			if (CollectionUtils.isEmpty(mediaSaleList)) {
				return;
			}
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.ADD_TYPE_MEDIA_SALE_BATCH);
			addCacheVo.setMediaSaleList(mediaSaleList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}
	}

	@Override
	public void setMediaCache(Media media) throws Exception {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_MEDIA);
		media = mediaService.findMasterById(media.getMediaId());
		if (media == null) {
			return;
		}
		addCacheVo.setMedia(media);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		// 删除单品销售主体缓存，需要时再获取
		this.deleteMediaSaleCache(media.getSaleId());
	}

	@Override
	public void batchSetMediaCache(List<Media> mediaList) throws Exception {
		if (!CollectionUtils.isEmpty(mediaList)) {
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.ADD_TYPE_MEDIA_BATCH);
			List<Long> mediaIdList = new ArrayList<Long>();
			for (Media media : mediaList) {
				mediaIdList.add(media.getMediaId());
			}
			mediaList = mediaService.findMasterByIds(mediaIdList);
			if (CollectionUtils.isEmpty(mediaList)) {
				return;
			}
			addCacheVo.setMediaList(mediaList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
			// 删除单品销售主体缓存，需要时再获取
			List<Long> saleIdList = new ArrayList<Long>();
			for (Media media : mediaList) {
				saleIdList.add(media.getSaleId());
			}
			this.batchDeleteMediaSaleCache(saleIdList);
		}
	}

	@Override
	public void setChapterCache(Chapter chapter) throws Exception {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_CHAPTER);
		chapter = chapterService.findMasterById(chapter.getId());
		if (chapter == null) {
			return;
		}
		addCacheVo.setChapter(chapter);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
	}

	@Override
	public void batchSetChapterCache(List<Chapter> chapterList) throws Exception {
		if (!CollectionUtils.isEmpty(chapterList)) {
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.ADD_TYPE_CHAPTER_BATCH);
			List<Long> chapterIdList = new ArrayList<Long>();
			for (Chapter chapter : chapterList) {
				chapterIdList.add(chapter.getId());
			}
			chapterList = chapterService.findMasterByIds(chapterIdList);
			if (CollectionUtils.isEmpty(chapterList)) {
				return;
			}
			addCacheVo.setChapterList(chapterList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}
	}

	@Override
	public MediaSaleCacheVo getMediaSaleFromCache(Long saleId) throws Exception {
		MediaSaleCacheVo mediaSaleCacheVo = mediaSaleCacheDao.getMediaSaleCacheVo(saleId);
		// 更新缓存过期时间
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_MEDIA_SALE);
		addCacheVo.setMediaSaleId(saleId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		return mediaSaleCacheVo;
	}

	@Override
	public List<MediaSaleCacheVo> batchGetMediaSaleFromCache(final List<Long> saleIdList) throws Exception {
		if (CollectionUtils.isEmpty(saleIdList)) {
			return null;
		}
		// // 是否存在批量缓存
		// String batchKey = Constans.CACHE_KEY_PREFIX_MEDIA_SALE_BATCH
		// + MD5Utils.getInstance().cell16(saleIdList.toString());
		// if (slaveRedisTemplateForMediaSaleBatch.hasKey(batchKey)) {
		// return
		// slaveRedisTemplateForMediaSaleBatch.opsForValue().get(batchKey);
		// }

		// 如果没有查询单个缓存
		List<MediaSaleCacheVo> mediaSaleCacheVoList = mediaSaleCacheDao.mGetMediaSaleCacheVo(saleIdList);
		// 如果缓存为空或者缓存不足
		if (this.isCacheNotEnough(mediaSaleCacheVoList, saleIdList.size())) {
			mediaSaleCacheVoList = new ArrayList<MediaSaleCacheVo>();
			List<MediaSale> mediaSales = mediaSaleService.findByIds(saleIdList);
			// 按照传入参数排序
			Collections.sort(mediaSales, new Comparator<MediaSale>() {
				public int compare(MediaSale o1, MediaSale o2) {
					if (o1.getSaleId() == null) {
						return 1;
					}
					if (o2.getSaleId() == null) {
						return -1;
					}
					return (saleIdList.indexOf(o1.getSaleId()) - saleIdList.indexOf(o2.getSaleId()));
				}
			});

			if (mediaSales != null && mediaSales.size() > 0) {
				for (MediaSale mediaSale : mediaSales) {
					if (mediaSale == null) {
						continue;
					}
					MediaSaleCacheVo vo = new MediaSaleCacheVo();
					MediaCacheBeanUtils.copyMediaSaleToVo(mediaSale, vo);
					List<Long> mediaIdList = mediaDao.getMediaListBySaleId(mediaSale.getSaleId());
					List<MediaCacheWholeVo> mediaList = this.batchGetMediaWholeFromCache(mediaIdList);
					vo.setMediaList(mediaList);
					mediaSaleCacheVoList.add(vo);
				}
				// 异步设置缓存
				this.batchSetMediaSaleCache(mediaSales);
			}

		} else {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_MEDIA_SALE_BATCH);
			addCacheVo.setMediaSaleIdList(saleIdList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}

		return mediaSaleCacheVoList;
	}

	@Override
	public MediaCacheBasicVo getMediaBasicFromCache(Long mediaId) throws Exception {
		MediaCacheBasicVo mediaCacheBasicVo = mediaCacheDao.getMediaCacheBasicVo(mediaId);
		// 更新缓存过期时间
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_MEDIA);
		addCacheVo.setMediaId(mediaId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		return mediaCacheBasicVo;
	}

	@Override
	public List<MediaCacheBasicVo> batchGetMediaBasicFromCache(final List<Long> mediaIdList) throws Exception {
		if (CollectionUtils.isEmpty(mediaIdList)) {
			return null;
		}
		// 首先从缓存获取
		List<MediaCacheBasicVo> mediaCacheBasicVoList = mediaCacheDao.mGetMediaCacheBasicVo(mediaIdList);
		// 如果缓存为空或者缓存不足
		if (this.isCacheNotEnough(mediaCacheBasicVoList, mediaIdList.size())) {
			mediaCacheBasicVoList = new ArrayList<MediaCacheBasicVo>();
			List<Media> medias = mediaService.findByIds(mediaIdList);
			// 按照传入参数排序
			Collections.sort(medias, new Comparator<Media>() {
				public int compare(Media o1, Media o2) {
					if (o1.getMediaId() == null) {
						return 1;
					}
					if (o2.getMediaId() == null) {
						return -1;
					}
					return (mediaIdList.indexOf(o1.getMediaId()) - mediaIdList.indexOf(o2.getMediaId()));
				}
			});

			// 获取分类
			List<Map<String, Object>> catList = mediaDao.getCategorysByMediaId(mediaIdList);
			Map<Long, Map<String, Object>> allCatMap = new HashMap<Long, Map<String, Object>>();
			for (Map<String, Object> catMap : catList) {
				if (catMap != null && catMap.containsKey("mediaId") && catMap.get("mediaId") != null) {
					allCatMap.put(Long.valueOf(catMap.get("mediaId").toString()), catMap);
				}
			}
			if (medias != null && medias.size() > 0) {
				for (Media media : medias) {
					if (media == null) {
						continue;
					}
					MediaCacheBasicVo vo = new MediaCacheBasicVo();
					MediaCacheBeanUtils.copyMediaToBasicVo(media, vo);
					// 设置分类缓存
					if (allCatMap.containsKey(media.getMediaId())) {
						Map<String, Object> catMap = allCatMap.get(media.getMediaId());
						if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
							vo.setCategorys(catMap.get("categorys").toString());
						}
						if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
							vo.setCategoryIds(catMap.get("categoryIds").toString());
						}
					}
					mediaCacheBasicVoList.add(vo);
				}
			}
			// 异步设置缓存
			this.batchSetMediaCache(medias);

		} else {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_MEDIA_BATCH);
			addCacheVo.setMediaIdList(mediaIdList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}

		return mediaCacheBasicVoList;
	}

	@Override
	public MediaCacheWholeVo getMediaWholeFromCache(Long mediaId) throws Exception {
		MediaCacheWholeVo mediaCacheWholeVo = mediaCacheDao.getMediaCacheWholeVo(mediaId);
		// 更新缓存过期时间
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_MEDIA);
		addCacheVo.setMediaId(mediaId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		return mediaCacheWholeVo;
	}

	@Override
	public List<MediaCacheWholeVo> batchGetMediaWholeFromCache(final List<Long> mediaIdList) throws Exception {
		if (CollectionUtils.isEmpty(mediaIdList)) {
			return null;
		}
		// 首先从缓存获取
		List<MediaCacheWholeVo> mediaCacheWholeVoList = mediaCacheDao.mGetMediaCacheWholeVo(mediaIdList);
		// 如果缓存为空或者缓存不足
		if (this.isCacheNotEnough(mediaCacheWholeVoList, mediaIdList.size())) {
			mediaCacheWholeVoList = new ArrayList<MediaCacheWholeVo>();
			List<Media> medias = mediaService.findByIds(mediaIdList);
			// 按照传入参数排序
			Collections.sort(medias, new Comparator<Media>() {
				public int compare(Media o1, Media o2) {
					if (o1.getMediaId() == null) {
						return 1;
					}
					if (o2.getMediaId() == null) {
						return -1;
					}
					return (mediaIdList.indexOf(o1.getMediaId()) - mediaIdList.indexOf(o2.getMediaId()));
				}
			});
			// 获取分类
			List<Map<String, Object>> catList = mediaDao.getCategorysByMediaId(mediaIdList);
			Map<Long, Map<String, Object>> allCatMap = new HashMap<Long, Map<String, Object>>();
			for (Map<String, Object> catMap : catList) {
				if (catMap != null && catMap.containsKey("mediaId") && catMap.get("mediaId") != null) {
					allCatMap.put(Long.valueOf(catMap.get("mediaId").toString()), catMap);
				}
			}
			if (medias != null && medias.size() > 0) {
				for (Media media : medias) {
					if (media == null) {
						continue;
					}
					MediaCacheWholeVo vo = new MediaCacheWholeVo();
					MediaCacheBeanUtils.copyMediaToWholeVo(media, vo);
					// 设置分类缓存
					if (allCatMap.containsKey(media.getMediaId())) {
						Map<String, Object> catMap = allCatMap.get(media.getMediaId());
						if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
							vo.setCategorys(catMap.get("categorys").toString());
						}
						if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
							vo.setCategoryIds(catMap.get("categoryIds").toString());
						}
					}
					mediaCacheWholeVoList.add(vo);
				}
			}
			// 异步设置缓存
			this.batchSetMediaCache(medias);

		} else {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_MEDIA_BATCH);
			addCacheVo.setMediaIdList(mediaIdList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}
		return mediaCacheWholeVoList;

	}

	@Override
	public ChapterCacheBasicVo getChapterBasicFromCache(Long chapterId) throws Exception {
		ChapterCacheBasicVo chapterCacheBasicVo = chapterCacheDao.getChapterCacheBasicVo(chapterId);
		// 更新缓存过期时间
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_CHAPTER);
		addCacheVo.setChapterId(chapterId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		return chapterCacheBasicVo;
	}

	@Override
	public List<ChapterCacheBasicVo> batchGetChapterBasicFromCache(final List<Long> chapterIdList) throws Exception {
		if (CollectionUtils.isEmpty(chapterIdList)) {
			return null;
		}
		// 首先从缓存获取
		List<ChapterCacheBasicVo> chapterCacheBasicVoList = chapterCacheDao.mGetChapterCacheBasicVo(chapterIdList);
		// 如果缓存为空或者缓存不足
		if (this.isCacheNotEnough(chapterCacheBasicVoList, chapterIdList.size())) {
			chapterCacheBasicVoList = new ArrayList<ChapterCacheBasicVo>();
			List<Chapter> chapters = chapterService.findByIds(chapterIdList);
			// 按照传入参数排序
			Collections.sort(chapters, new Comparator<Chapter>() {
				public int compare(Chapter o1, Chapter o2) {
					if (o1.getId() == null) {
						return 1;
					}
					if (o2.getId() == null) {
						return -1;
					}
					return (chapterIdList.indexOf(o1.getId()) - chapterIdList.indexOf(o2.getId()));
				}
			});
			if (chapters != null && chapters.size() > 0) {
				for (Chapter chapter : chapters) {
					if (chapter == null) {
						continue;
					}
					ChapterCacheBasicVo vo = new ChapterCacheBasicVo();
					MediaCacheBeanUtils.copyChapterToBasicVo(chapter, vo);
					chapterCacheBasicVoList.add(vo);
				}
			}
			// 异步设置缓存
			this.batchSetChapterCache(chapters);
		} else {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_CHAPTER_BATCH);
			addCacheVo.setChapterIdList(chapterIdList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}
		return chapterCacheBasicVoList;
	}

	@Override
	public ChapterCacheWholeVo getChapterWholeFromCache(Long chapterId) throws Exception {
		ChapterCacheWholeVo chapterCacheWholeVo = chapterCacheDao.getChapterCacheWholeVo(chapterId);
		// 更新缓存过期时间
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_CHAPTER);
		addCacheVo.setChapterId(chapterId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		return chapterCacheWholeVo;
	}

	@Override
	public List<ChapterCacheWholeVo> batchGetChapterWholeFromCache(final List<Long> chapterIdList) throws Exception {
		if (CollectionUtils.isEmpty(chapterIdList)) {
			return null;
		}
		// 首先从缓存获取
		List<ChapterCacheWholeVo> chapterCacheWholeVoList = chapterCacheDao.mGetChapterCacheWholeVo(chapterIdList);
		// 如果缓存为空或者缓存不足
		if (this.isCacheNotEnough(chapterCacheWholeVoList, chapterIdList.size())) {
			chapterCacheWholeVoList = new ArrayList<ChapterCacheWholeVo>();
			List<Chapter> chapters = chapterService.findByIds(chapterIdList);
			// 按照传入参数排序
			Collections.sort(chapters, new Comparator<Chapter>() {
				public int compare(Chapter o1, Chapter o2) {
					if (o1.getId() == null) {
						return 1;
					}
					if (o2.getId() == null) {
						return -1;
					}
					return (chapterIdList.indexOf(o1.getId()) - chapterIdList.indexOf(o2.getId()));
				}
			});
			if (chapters != null && chapters.size() > 0) {
				for (Chapter chapter : chapters) {
					if (chapter == null) {
						continue;
					}
					ChapterCacheWholeVo vo = new ChapterCacheWholeVo();
					MediaCacheBeanUtils.copyChapterToWholeVo(chapter, vo);
					chapterCacheWholeVoList.add(vo);
				}
			}
			// 异步设置缓存
			this.batchSetChapterCache(chapters);
		} else {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_CHAPTER_BATCH);
			addCacheVo.setChapterIdList(chapterIdList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}
		return chapterCacheWholeVoList;
	}

	@Override
	public void deleteMediaSaleCache(Long saleId) throws Exception {
		mediaSaleCacheDao.deleteCacheVo(saleId);
	}

	@Override
	public void batchDeleteMediaSaleCache(List<Long> saleIdList) throws Exception {
		mediaSaleCacheDao.mDeleteCacheVo(saleIdList);
	}

	@Override
	public void cleanMediaSaleCache() throws Exception {
		mediaSaleCacheDao.cleanMediaSaleCache();
	}

	@Override
	public void deleteMediaBasicCache(Long mediaId) throws Exception {
		mediaCacheDao.deleteCacheBasicVo(mediaId);
	}

	@Override
	public void batchDeleteMediaBasicCache(List<Long> mediaIdList) throws Exception {
		mediaCacheDao.mDeleteCacheBasicVo(mediaIdList);
	}

	@Override
	public void cleanMediaBasicCache() throws Exception {
		mediaCacheDao.cleanBasicVo();
	}

	@Override
	public void deleteMediaWholeCache(Long mediaId) throws Exception {
		mediaCacheDao.deleteCacheWholeVo(mediaId);
	}

	@Override
	public void batchDeleteMediaWholeCache(List<Long> mediaIdList) throws Exception {
		mediaCacheDao.mDeleteCacheWholeVo(mediaIdList);
	}

	@Override
	public void cleanMediaWholeCache() throws Exception {
		mediaCacheDao.cleanWholeVo();
	}

	@Override
	public void deleteChapterBasicCache(Long chapterId) throws Exception {
		chapterCacheDao.deleteCacheBasicVo(chapterId);
	}

	@Override
	public void batchDeleteChapterBasicCache(List<Long> chapterIdList) throws Exception {
		chapterCacheDao.mDeleteCacheBasicVo(chapterIdList);
	}

	@Override
	public void cleanChapterBasicCache() throws Exception {
		chapterCacheDao.cleanBasicVo();
	}

	@Override
	public void deleteChapterWholeCache(Long chapterId) throws Exception {
		chapterCacheDao.deleteCacheWholeVo(chapterId);
	}

	@Override
	public void batchDeleteChapterWholeCache(List<Long> chapterIdList) throws Exception {
		chapterCacheDao.mDeleteCacheWholeVo(chapterIdList);
	}

	@Override
	public void cleanChapterWholeCache() throws Exception {
		chapterCacheDao.cleanWholeVo();
	}

	@Override
	public UserTradeBaseVo getUserInfoByToken(String token) {
		if (StringUtils.isBlank(token)) {
			LOGGER.info("token为空 ");
			return null;
		}
		String json = commonApi.getDangdangUser(token);
		if (StringUtils.isBlank(json)) {
			LOGGER.warn("获取用户信息为空，token：" + token);
			return null;
		}
		UserTradeBaseVo userInfo = this.getUserVoByJson(json);
		if (userInfo.getCustId() == null) {
			return null;
		}
		return userInfo;
	}

	@Override
	public UserTradeBaseVo getUserWholeInfoByToken(String token) {
		if (StringUtils.isBlank(token)) {
			LogUtil.error(LOGGER, "token为空 ");
			return null;
		}
		String json = commonApi.getDangdangUser(token);
		if (StringUtils.isBlank(json)) {
			LogUtil.warn(LOGGER, "获取用户信息为空，token：" + token);
			return null;
		}
		UserTradeBaseVo userInfo = this.getUserVoByJson(json);
		if (userInfo.getCustId() == null) {
			return null;
		}
		List<String> userIds = new ArrayList<String>();
		userIds.add(String.valueOf(userInfo.getCustId()));
		// 获取全部信息
		Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(userIds, 60 * 60 * 2);
		if (returnMap != null && returnMap.containsKey(String.valueOf(userInfo.getCustId()))) {
			Map<String, String> userInfoMap = returnMap.get(String.valueOf(userInfo.getCustId()));
			if (userInfoMap.containsKey("nickName")) {
				userInfo.setNickname(userInfoMap.get("nickName"));
			}
			if (userInfoMap.containsKey("introduct")) {
				userInfo.setIntroduct(userInfoMap.get("introduct"));
			}
			if (userInfoMap.containsKey("custImg")) {
				userInfo.setCustImg(userInfoMap.get("custImg"));
			}
			if (userInfoMap.containsKey("custType")) {
				userInfo.setCustType(userInfoMap.get("custType"));
			}
		}
		return userInfo;
	}

	@Override
	public UserTradeBaseVo getUserWholeInfoByCustId(Long custId) {

		List<String> userIds = new ArrayList<String>();
		String custIdStr = custId + "";
		userIds.add(custIdStr);

		UserTradeBaseVo userInfo = new UserTradeBaseVo();
		userInfo.setCustId(custId);
		// 获取全部信息
		Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(userIds, 60 * 60 * 2);
		if (returnMap != null && returnMap.containsKey(custIdStr)) {
			Map<String, String> userInfoMap = returnMap.get(custIdStr);
			if (userInfoMap.containsKey("nickName")) {
				userInfo.setNickname(userInfoMap.get("nickName"));
			}
			if (userInfoMap.containsKey("introduct")) {
				userInfo.setIntroduct(userInfoMap.get("introduct"));
			}
			if (userInfoMap.containsKey("custImg")) {
				userInfo.setCustImg(userInfoMap.get("custImg"));
			}
			if (userInfoMap.containsKey("custType")) {
				userInfo.setCustType(userInfoMap.get("custType"));
			}
		}
		return userInfo;
	}

	@Override
	public Map<Long, UserTradeBaseVo> getUserInfoByCustIds(List<Long> custIdList) {

		List<String> userIds = new ArrayList<String>();
		for (Long custId : custIdList) {
			userIds.add(custId + "");
		}

		Map<Long, UserTradeBaseVo> result = new LinkedHashMap<Long, UserTradeBaseVo>();

		// 获取全部信息
		Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(userIds, 60 * 60 * 2);

		for (String userId : userIds) {

			UserTradeBaseVo userInfo = new UserTradeBaseVo();

			if (returnMap != null && returnMap.containsKey(userId)) {

				Map<String, String> userInfoMap = returnMap.get(userId);

				if (userInfoMap.containsKey("nickName")) {
					userInfo.setNickname(userInfoMap.get("nickName"));
				}
				if (userInfoMap.containsKey("introduct")) {
					userInfo.setIntroduct(userInfoMap.get("introduct"));
				}
				if (userInfoMap.containsKey("custImg")) {
					userInfo.setCustImg(userInfoMap.get("custImg"));
				}
				if (userInfoMap.containsKey("custType")) {
					userInfo.setCustType(userInfoMap.get("custType"));
				}
				Long userIdLong = Long.valueOf(userId);
				userInfo.setCustId(userIdLong);
				result.put(userIdLong, userInfo);
			}
		}
		return result;
	}

	@Override
	public void setAuthorCache(Author author) throws Exception {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_AUTHOR);
		author = this.authorService.findMasterById(author.getAuthorId());
		if (author == null) {
			return;
		}
		addCacheVo.setAuthor(author);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);

	}

	@Override
	public void batchSetAuthorCache(List<Author> authorList) throws Exception {
		if (!CollectionUtils.isEmpty(authorList)) {
			List<Long> authorIdList = new ArrayList<Long>();
			for (Author author : authorList) {
				authorIdList.add(author.getAuthorId());
			}
			authorList = this.authorService.findMasterByIds(authorIdList);
			if (CollectionUtils.isEmpty(authorList)) {
				return;
			}
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.ADD_TYPE_AUTHOR_BATCH);
			addCacheVo.setAuthorList(authorList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}

	}

	@Override
	public AuthorCacheVo getAuthorFromCache(Long authorId) throws Exception {
		AuthorCacheVo authorCacheVo = authorCacheDao.getAuthorCacheVo(authorId);
		// 更新缓存过期时间
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_AUTHOR);
		addCacheVo.setAuthorId(authorId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		return authorCacheVo;
	}

	@Override
	public List<AuthorCacheVo> batchGetAuthorFromCache(List<Long> authorIdList) throws Exception {
		if (CollectionUtils.isEmpty(authorIdList)) {
			return null;
		}

		// 如果没有查询单个缓存
		List<AuthorCacheVo> authorCacheVoList = authorCacheDao.mGetAuthorCacheVo(authorIdList);
		// 如果缓存为空或者缓存不足
		if (this.isCacheNotEnough(authorCacheVoList, authorIdList.size())) {
			LOGGER.info("缓存中数据不足，从数据库中读取");
			authorCacheVoList = new ArrayList<AuthorCacheVo>();
			List<Author> authors = authorService.findByIds(authorIdList);
			if (authors != null && authors.size() > 0) {
				for (Author author : authors) {
					if (author == null) {
						continue;
					}
					AuthorCacheVo vo = new AuthorCacheVo();
					MediaCacheBeanUtils.copyAuthorToCacheVo(author, vo);
					authorCacheVoList.add(vo);
				}
				// 异步设置缓存
				this.batchSetAuthorCache(authors);
			}

		} else {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_AUTHOR_BATCH);
			addCacheVo.setAuthorIdList(authorIdList);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}

		return authorCacheVoList;
	}

	@Override
	public void deleteAuthorCache(Long authorId) throws Exception {
		authorCacheDao.deleteCacheVo(authorId);
	}

	@Override
	public void batchDeleteAuthorCache(List<Long> authorIdList) throws Exception {
		authorCacheDao.mDeleteCacheVo(authorIdList);
	}

	@Override
	public void cleanAuthorCache() throws Exception {
		authorCacheDao.cleanAuthorCache();
	}

	@Override
	public List<Long> getChapterIdsFromCache(Long mediaId, Integer start, Integer end) {
		if (start == null) {
			start = 0;
		}
		if (end == null) {
			end = -1;
		}
		if (slaveRedisTemplateForContents.hasKey(Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId)) {
			LOGGER.info("从缓存中读取目录信息");
			Set<Long> contentsSet = slaveRedisTemplateForContents.opsForZSet().range(Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId, start,
					end);
			return new ArrayList<Long>(contentsSet);
		} else {
			int count = end - start + 1;
			if (end == -1) {
				count = Integer.MAX_VALUE;
			}
			List<Long> chapterIds = chapterDao.getAllChapterIdsByMediaId(mediaId, start, count);
			// 更新目录缓存
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.ADD_TYPE_CONTENTS);
			addCacheVo.setMediaId(mediaId);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			setCacheJob.run();
			return chapterIds;
		}
	}

	@Override
	public Map<Long, ContentsVo> getContentsFromCache(Long mediaId, Integer start, Integer end) throws Exception {
		List<Long> chapterIdList = this.getChapterIdsFromCache(mediaId, start, end);
		Map<Long, ContentsVo> contentsMap = new LinkedHashMap<Long, ContentsVo>();
		List<ChapterCacheWholeVo> chapterList = this.batchGetChapterWholeFromCache(chapterIdList);
		if (CollectionUtils.isEmpty(chapterList)) {
			return null;
		}
		for (ChapterCacheWholeVo chapter : chapterList) {
			if (contentsMap.containsKey(chapter.getVolumeId())) {
				ChapterContentsVo chapterContentsVo = new ChapterContentsVo();
				chapterContentsVo.setId(chapter.getId());
				chapterContentsVo.setIndex(chapter.getIndex());
				chapterContentsVo.setIsFree(chapter.getIsFree());
				chapterContentsVo.setSubTitle(chapter.getSubTitle());
				chapterContentsVo.setTitle(chapter.getTitle());
				contentsMap.get(chapter.getVolumeId()).addChapterList(chapterContentsVo);
			} else {
				ContentsVo contentsVo = new ContentsVo();
				contentsVo.setVolumeId(chapter.getVolumeId());
				contentsVo.setTitle(chapter.getVolumeName());
				List<ChapterContentsVo> chapterContentsVoList = new ArrayList<ChapterContentsVo>();
				ChapterContentsVo chapterContentsVo = new ChapterContentsVo();
				chapterContentsVo.setId(chapter.getId());
				chapterContentsVo.setIndex(chapter.getIndex());
				chapterContentsVo.setIsFree(chapter.getIsFree());
				chapterContentsVo.setSubTitle(chapter.getSubTitle());
				chapterContentsVo.setTitle(chapter.getTitle());
				chapterContentsVoList.add(chapterContentsVo);
				contentsVo.setChapterList(chapterContentsVoList);
				contentsMap.put(chapter.getVolumeId(), contentsVo);
			}
		}
		return contentsMap;
	}

	@Override
	public int getChapterCountFromCache(Long mediaId) {
		// 如果存在缓存直接返回有序集数量
		if (slaveRedisTemplateForContents.hasKey(Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId)) {
			LOGGER.info("从缓存中读取目录信息");
			return Integer.valueOf((String.valueOf(slaveRedisTemplateForContents.opsForZSet().size(
					Constans.CACHE_KEY_PREFIX_CONTENTS + mediaId))));
		} else {
			// 更新目录缓存
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.ADD_TYPE_CONTENTS);
			addCacheVo.setMediaId(mediaId);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			setCacheJob.run();
			// 没有缓存直接从数据库里读
			return chapterDao.getCountByMediaId(mediaId);
		}
	}

	private UserTradeBaseVo getUserVoByJson(String json) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		UserTradeBaseVo userVo = new UserTradeBaseVo();
		JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
		if (jsonObject.containsKey("id")) {
			userVo.setCustId(jsonObject.getLongValue("id"));
		}
		if (jsonObject.containsKey("userName")) {
			userVo.setUsername(jsonObject.getString("userName"));
		}
		if (jsonObject.containsKey("email")) {
			userVo.setEmail(jsonObject.getString("email"));
		}
		return userVo;
	}

	/**
	 * 
	 * Description: 判断缓存是否是不充足的，需要从数据库里读取
	 * 
	 * @Version1.0 2014年12月11日 下午12:05:30 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param objectList
	 * @param length
	 * @return
	 */
	private boolean isCacheNotEnough(Collection<?> objectList, int length) {
		if (objectList == null) {
			return true;
		}
		if (objectList.size() < length) {
			return true;
		}
		for (Object obj : objectList) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从缓存中读取栏目信息
	 */
	@Override
	public Column getColumnFromCache(final String columnType) {
		Column column = columnCacheDao.getColumnByCode(columnType);
		if (null == column) {
			// 缓存中没有,从数据库加载
			column = columnDao.getColumnByCode(columnType);
			if (null == column) {
				return null;
			}
			// String columnBaseUrl =
			// systemApi.getProperty("column_icon_root","");
			String columnBaseUrl = ConfigPropertieUtils.getString("media.resource.images.column.http.path");
			LOGGER.info("columnBaseUrl=" + columnBaseUrl);
			column.setIcon(columnBaseUrl + column.getIcon());
			final Column columnToCache = column;
			taskExecutor.execute(new Runnable() {
				public void run() {
					columnCacheDao.setColumnCache(columnToCache);
					LOGGER.info("异步添加栏目(" + columnType + ")到缓存");
				}
			});
		}
		taskExecutor.execute(new Runnable() {
			public void run() {
				masterRedisTemplate.expire(Constans.COLUMN_CACHE_KEY + columnType.toLowerCase(),
						Integer.valueOf(Constans.CACHE_EXPIRE_TIME_OF_COLUMN), TimeUnit.SECONDS);
				LOGGER.info("异步刷新栏目(" + columnType + ".key=" + Constans.COLUMN_CACHE_KEY + columnType.toLowerCase() + " )缓存时间");
			}
		});
		return column;
	}

	@Override
	public UserAuthorityCacheVo getUserAuthorityCacheVoByCustIdAndMediaId(Long custId, Long mediaId) {
		UserAuthorityCacheVo userAuthorityCacheVo = userAuthorityCacheDao.getUserAuthorityCacheVoByCustIdAndMediaId(custId, mediaId, null);
		if (userAuthorityCacheVo != null) {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_AUTHORITY);
			addCacheVo.setMediaId(mediaId);
			addCacheVo.setCustId(custId);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}
		return userAuthorityCacheVo;
	}

	@Override
	public List<UserAuthorityCacheVo> getUserAuthorityCacheVoByCustId(Long custId) {
		List<UserAuthorityCacheVo> cacheList = userAuthorityCacheDao.getUserAuthorityCacheVoByCustId(custId);
		Collections.sort(cacheList, new Comparator<UserAuthorityCacheVo>() {
			public int compare(UserAuthorityCacheVo o1, UserAuthorityCacheVo o2) {
				if (o1.getLastModifiedDate() == null) {
					return 1;
				}
				if (o2.getLastModifiedDate() == null) {
					return -1;
				}
				return (o2.getLastModifiedDate().after(o1.getLastModifiedDate()) ? 1 : -1);
			}
		});
		return cacheList;
	}

	@Override
	public void setUserAuthorityCacheVo(UserAuthority userAuthority) {
		if (userAuthority == null || userAuthority.getCustId() == null || userAuthority.getMediaId() == null) {
			return;
		}
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_AUTHORITY);
		addCacheVo.setUserAuthority(userAuthority);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
	}

	@Override
	public ListCategory getListCategoryFromCache(final String listType) {
		ListCategory listCategory = listCategoryCacheDao.getListCategoryByCode(listType);
		if (null == listCategory) {
			// 缓存中没有,从数据库加载
			listCategory = listCategoryDao.getListCategoryByCode(listType);
			if (null == listCategory) {
				LOGGER.info("不存在榜单标识：" + listType);
				return null;
			}
			final ListCategory listCategoryToCache = listCategory;
			taskExecutor.execute(new Runnable() {
				public void run() {
					listCategoryCacheDao.setListCategoryCache(listCategoryToCache);
					LOGGER.info("异步添加榜单基本信息(" + listType + ")到缓存");
				}
			});
		}
		taskExecutor.execute(new Runnable() {
			public void run() {
				String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_CHAPTER_KEY,
						String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_COLUMN));
				masterRedisTemplate.expire(Constans.COLUMN_CACHE_KEY + listType, Integer.valueOf(expireTime), TimeUnit.SECONDS);
				LOGGER.info("异步刷新栏目(" + listType + ")缓存时间");
			}
		});
		return listCategory;
	}

	@Override
	public Catetory getCatetoryFromCache(final String code) {
		Catetory catetory = catetoryCacheDao.getCatetoryByCode(code);
		if (null == catetory) {
			// 缓存中没有,从数据库加载
			catetory = catetoryDao.getCatetoryByCode(code);
			final Catetory categoryToCache = catetory;
			if (null == categoryToCache) {
				LOGGER.info("不存在分类标识：" + code);
				return null;
			}
			taskExecutor.execute(new Runnable() {
				public void run() {
					catetoryCacheDao.setCatetoryCache(categoryToCache);
					LOGGER.info("异步添加media分类信息(" + code + ")到缓存");
				}
			});
		}
		return catetory;

	}

	@Override
	public UserMonthlyCacheVo getUserMonthlyCacheVoByCustId(Long custId) {
		UserMonthlyCacheVo userMonthlyCacheVo = userMonthlyCacheDao.getUserMonthlyCacheVoByCustId(custId, null);
		if (userMonthlyCacheVo != null) {
			// 更新缓存过期时间
			AddCacheVo addCacheVo = new AddCacheVo();
			addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_MONTHLY);
			addCacheVo.setCustId(custId);
			SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao,
					userAuthorityCacheDao, userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao,
					masterRedisTemplateForContents, discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
			taskExecutor.execute(setCacheJob);
		}
		return userMonthlyCacheVo;
	}

	@Override
	public void updateUserMonthlyCacheVo(UserMonthlyCacheVo userMonthlyCacheVo) {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_MONTHLY);
		addCacheVo.setUserMonthlyCacheVo(userMonthlyCacheVo);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
	}

	@Override
	public void deleteUserAuthorityCacheVo(Long custId, Long mediaId) {
		userAuthorityCacheDao.deleteUserAuthorityCacheVo(custId, mediaId);

	}

	@Override
	public void deleteUserMonthlyCacheVo(Long custId) {
		userMonthlyCacheDao.deleteUserMonthlyCacheVo(custId);

	}

	@Override
	public void setDiscoveryCache(Discovery discovery) {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_DISCOVERY);
		addCacheVo.setDiscovery(discovery);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
	}

	@Override
	public Discovery getDiscoveryFromCache(Long discoveryId) {
		return discoveryCacheDao.getDiscovery(discoveryId);
	}

	@Override
	public void deleteDiscoveryFromCache(Long discoveryId) {
		discoveryCacheDao.deleteCacheVo(discoveryId);
	}

	@Override
	public List<Catetory> getCatetoryListFromCache(String parentCode) {
		Set<String> categoryCacheKeySet = catetoryCacheDao.getCatetoryListByParentCode(parentCode);
		List<Catetory> catetoryList = new ArrayList<Catetory>();
		// 如果缓存中没有,则从数据库加载
		if (null == categoryCacheKeySet || categoryCacheKeySet.size() == 0) {
			catetoryList = catetoryDao.getCatetoryListByPathPrifix(parentCode);
			if (null != catetoryList && catetoryList.size() > 0) {
				for (Catetory cate : catetoryList) {
					catetoryCacheDao.setCatetoryCache(cate);
				}

			}
		} else {
			// 从缓存中读取,正则匹配
			for (String cacheKey : categoryCacheKeySet) {
				// 得到key
				String code = cacheKey.substring(cacheKey.lastIndexOf("_"));
				Catetory category = getCatetoryFromCache(code);
				catetoryList.add(category);
			}
		}
		return catetoryList;
	}

	@Override
	public ResultTwoTuple<Long, List<MediaSaleCacheVo>> getMediaSaleByColumnCode(int start, int end, final String columnType)
			throws Exception {
		List<Long> saleIdList = new LinkedList<Long>();
		String lowerColumnType = null;
		if (StringUtils.isNotEmpty(columnType)) {
			lowerColumnType = columnType.toLowerCase();
		}
		final String cacheKey = Constans.COLUMN_CONTENT_CACHE_KEY + lowerColumnType;
		if (slaveRedisTemplateForId.hasKey(cacheKey)) {
			saleIdList = slaveRedisTemplateForId.opsForValue().get(cacheKey);
			LOGGER.info("从缓存中读取栏目" + columnType + " key=" + cacheKey + " 内容信息:saleId=" + saleIdList);
		} else {
			// 更新栏目内容缓存
			final List<Long> cacheSaleIdList = columnContentDao.getSaleIdsByColumnCode(lowerColumnType);
			if (null == cacheSaleIdList || cacheSaleIdList.size() == 0) {
				return  new ResultTwoTuple<Long,List<MediaSaleCacheVo>>(0L,new LinkedList<MediaSaleCacheVo>());
			}
			saleIdList.addAll(cacheSaleIdList);
			taskExecutor.execute(new Runnable() {
				public void run() {
					masterRedisTemplateForId.opsForValue().set(cacheKey, cacheSaleIdList);
					masterRedisTemplateForId.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_COLUMN_CONTENT, TimeUnit.SECONDS);
					LOGGER.info("异步添加栏目 " + columnType + "key=" + cacheKey + " 内容,saleId=" + cacheSaleIdList + "到缓存", columnType,
							cacheSaleIdList);
				}
			});
		}
		int size = saleIdList.size();
		int startIndex = start;
		int endIndex = 0;
		if (size <= end) {
			endIndex = size;
		} else {
			endIndex = end + 1;
		}
		saleIdList = saleIdList.subList(startIndex, endIndex);
		return new ResultTwoTuple<Long, List<MediaSaleCacheVo>>(Long.valueOf(size), batchGetMediaSaleFromCache(saleIdList));
	}

	@Override
	public long getCacheListSize(String cacheKey) {
		if (slaveRedisTemplateForId.hasKey(cacheKey)) {
			long keySize = slaveRedisTemplateForId.opsForValue().size(cacheKey);
			int size = slaveRedisTemplateForId.opsForValue().get(cacheKey).size();
			System.out.println("keySize=" + keySize + " size=" + size);
			return slaveRedisTemplateForId.opsForValue().get(cacheKey).size();
		}
		return 0L;
	}

	@Override
	public List<MediaSaleCacheVo> getMediaSaleByRankingListCode(final int start, final int end, final String listType) throws Exception {
		List<Long> saleIdList = null;
		final String cacheKey = Constans.RANKING_LIST_CACHE_KEY + listType;
		if (slaveRedisTemplateForId.hasKey(cacheKey)) {
			saleIdList = slaveRedisTemplateForId.opsForValue().get(cacheKey);
			LOGGER.info("从缓存中读取榜单" + listType + " key= " + cacheKey);// saleId="
																		// +
																		// saleIdList
		} else {
			// 从数据库中更新榜单内容到缓存中
			ListCategory listCategory = getListCategoryFromCache(listType);
			// 查询邮榜单规定的最记录条数
			ResultTwoTuple<Long, List<Long>> resultTuple = rankingListApi.getSaleIdListByType(0, listCategory.getMaxRecords(), listType);
			final List<Long> cacheSaleIdList = resultTuple.listId;
			if (null == cacheSaleIdList || cacheSaleIdList.size() == 0) {
				LOGGER.info("从数据库中读取榜单" + listType + " 内容为空");
				return null;
			}
			saleIdList = new ArrayList<Long>(cacheSaleIdList);
			taskExecutor.execute(new Runnable() {
				public void run() {
					masterRedisTemplateForId.opsForValue().set(cacheKey, cacheSaleIdList);
					masterRedisTemplateForId.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_RANKING_LIST, TimeUnit.SECONDS);
					LOGGER.info("异步添加榜单 " + listType + " 内容,到缓存", listType, cacheSaleIdList);// saleId=" + cacheSaleIdList + "
				}
			});
		}
		int size = saleIdList.size();

		int startIndex = start;
		int endIndex = 0;
		if (size <= end) {
			endIndex = size;
		} else {
			endIndex = end + 1;
		}
		saleIdList = saleIdList.subList(startIndex, endIndex);

		return batchGetMediaSaleFromCache(saleIdList);
	}

	@Override
	public List<MediaSaleCacheVo> getHotFreeFromCache(final int start, final int end, final int activityId) throws Exception {
		long hotFreeEndTime = getHotFreeEndTimeFromCache(activityId);
		final String cacheKey = Constans.HOT_FREE_CACHE_KEY + activityId;
		List<Long> saleIdList = slaveRedisTemplateForId.opsForValue().get(cacheKey);
		if (hotFreeEndTime <= System.currentTimeMillis() || null == saleIdList) {
			// 栏目已过期或者缓存中没有,需要从数据库重新加载
			final List<Long> cacheSaleIdList = activitySaleDao.getHotFreeSaleIdList(activityId);
			final String endTime = activitySaleDao.getHotFreeExpireTime(activityId);
			if (null != cacheSaleIdList && cacheSaleIdList.size() > 0) {
				saleIdList = new ArrayList<Long>(cacheSaleIdList);
				taskExecutor.execute(new Runnable() {
					public void run() {
						masterRedisTemplateForId.opsForValue().set(cacheKey, cacheSaleIdList);
						masterRedisTemplateForId.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_HOT_FREE, TimeUnit.SECONDS);
						LOGGER.info("异步添加火辣限免 " + activityId + " 内容,saleId=" + cacheSaleIdList + "到缓存", activityId, cacheSaleIdList);
					}
				});
			} else {
				// 没有数据
				return null;
			}
			if (null != endTime) {
				final long activeEndTime = DateUtil.parseStringToDate(endTime).getTime();
				taskExecutor.execute(new Runnable() {
					public void run() {
						masterRedisTemplateForContents.opsForValue().set(Constans.HOT_FREE_CACHE_KEY + activityId + "_endtime",
								activeEndTime);
						masterRedisTemplateForContents.expire(Constans.HOT_FREE_CACHE_KEY + activityId + "_endtime",
								Constans.CACHE_EXPIRE_TIME_OF_HOT_FREE, TimeUnit.SECONDS);
						LOGGER.info("异步添加火辣限免 " + activityId + " 截止时间,endtime=" + endTime + "到缓存");
					}
				});
			}

		} else {
			// 从缓存中读取
			saleIdList = slaveRedisTemplateForId.opsForValue().get(cacheKey);
			LOGGER.info("从缓存中读取火辣限免信息:" + activityId + " key=" + Constans.HOT_FREE_CACHE_KEY + activityId + " 内容信息:saleId=" + saleIdList);

		}
		if (null == saleIdList) {
			LOGGER.info("火辣限免信息为空:" + activityId + " key=" + Constans.HOT_FREE_CACHE_KEY + activityId + " 内容信息:saleId=" + saleIdList);
			return null;
		}
		List<Long> onShelfSaleIdList = getOnShelfSaleIdList(saleIdList);
		// 从0开始,因为前面saleIdList已经是start截取
		saleIdList = onShelfSaleIdList.subList(start, onShelfSaleIdList.size() > end ? end + 1 : onShelfSaleIdList.size());
		return batchGetMediaSaleFromCache(saleIdList);
	}

	@Override
	public long getHotFreeEndTimeFromCache(int activityId) {
		if (slaveRedisTemplateForContents.hasKey(Constans.HOT_FREE_CACHE_KEY + activityId + "_endtime")) {
			return slaveRedisTemplateForContents.opsForValue().get(Constans.HOT_FREE_CACHE_KEY + activityId + "_endtime");
		} else {
			return 0;
		}
	}

	private List<Long> getSaleList(final int sectionIndex, final int mediaCategoryQueryCount, final String dimension,
			final String cacheKey, Catetory catetory) {
		List<Long> saleIdList = null;
		String catetoryPathLowerCase = catetory.getPath().toLowerCase();
		final String categoryCodeLowerCase = catetory.getCode().toLowerCase();
		if (slaveRedisTemplateForId.hasKey(cacheKey)) {
			saleIdList = slaveRedisTemplateForId.opsForValue().get(cacheKey);
			if (null == saleIdList || saleIdList.size() == 0) {
				LOGGER.error("从缓存中读取分类" + cacheKey + " 内容为空");
				return null;
			}
			LOGGER.info("从缓存中读取分类  key=" + cacheKey);// 内容信息:saleId="
		} else {
			// 从数据库中更新榜单内容到缓存中
			final List<Long> cacheSaleIdList = mediaStatisticsDao.getSaleIdListByCategoryCodeAndDimension(sectionIndex,
					mediaCategoryQueryCount, catetoryPathLowerCase, dimension);
			if (null == cacheSaleIdList || cacheSaleIdList.size() == 0) {
				LOGGER.info("从数据库中读取分类" + catetoryPathLowerCase + " 维度 " + dimension + " 内容为空");
				return null;
			}
			taskExecutor.execute(new Runnable() {
				public void run() {
					masterRedisTemplateForId.opsForValue().set(cacheKey, cacheSaleIdList);
					masterRedisTemplateForId.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_MEDIA_CATEGORY_DATA, TimeUnit.SECONDS);
					LOGGER.info("##################$$$$$$$$$$$异步添加分类 " + categoryCodeLowerCase + " 内容,到缓存");// saleId=" + cacheSaleIdList + "
				}
			});
			saleIdList = new ArrayList<Long>(cacheSaleIdList);
		}
		return saleIdList;
	}

	/**
	 * 下架的消息那儿处理
	 */
	@Override
	public List<MediaSaleCacheVo> getCategoryMediaSaleByCodeFromCache(final int start, int end, final String categoryCode,
			final String dimension) throws Exception {
		final String categoryCodeLowerCase = categoryCode.toLowerCase();
		Catetory catetory = getCatetoryFromCache(categoryCodeLowerCase);
		if (null == catetory) {
			LOGGER.info("不存在的分类信息 " + catetory);
			return null;
		}
		long total = getMediaCountByCategoryAndDimension(categoryCodeLowerCase, dimension);
		if (0 == total) {
			LOGGER.info(catetory + " 下media_statistics表没有数据");
			return null;
		}
		end = (int) (end >= total ? total : end);
		int mediaCategoryQueryCount = Integer.valueOf(systemApi.getProperty("mediaCategoryBatchCount", "600"));// 分类每批查询的数量
		// 0:0-299=1-300,1:301-600,2:601-900
		final int startIndex = (start - 1) / mediaCategoryQueryCount;
		final int endIndex = (end - 1) / mediaCategoryQueryCount;

		List<Long> saleIdList = new LinkedList<Long>();
		int tempStart = 0;
		int temEnd = 0;
		if (startIndex == endIndex) {
			// 在一个分段里面
			final String cacheKey = Constans.MEDIA_CATEGORY_DATA_CACHE_KEY + categoryCodeLowerCase + "_" + dimension.toLowerCase() + "_"
					+ startIndex;
			saleIdList = getSaleList(startIndex, mediaCategoryQueryCount, dimension, cacheKey, catetory);
			tempStart = start % mediaCategoryQueryCount;
			temEnd = end + 1;
			saleIdList = saleIdList.subList(tempStart, saleIdList.size() > temEnd ? temEnd : saleIdList.size());

		} else {
			// 在多个分段里面
			for (int i = startIndex; i <= endIndex; i++) {
				final String cacheKey = Constans.MEDIA_CATEGORY_DATA_CACHE_KEY + categoryCodeLowerCase + "_" + dimension.toLowerCase()
						+ "_" + i;
				List<Long> tempList = getSaleList(i, mediaCategoryQueryCount, dimension, cacheKey, catetory);
				if (null != saleIdList) {
					saleIdList.addAll(tempList);
				}
			}
			tempStart = start % mediaCategoryQueryCount;
			temEnd = (endIndex - startIndex) * mediaCategoryQueryCount + (end % mediaCategoryQueryCount);
			saleIdList = saleIdList.subList(tempStart, saleIdList.size() > (end - start) ? (temEnd + 1) : saleIdList.size());
		}
		// 从0开始,因为前面saleIdList已经是start截取
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("二级分类查询:catetory=").append(catetory).append("start=").append(start).append(start).append(end).append(",tempStart=")
				.append(tempStart).append(" temEnd=").append(temEnd).append(",saleIdList=").append(saleIdList.size());
		LOGGER.info(sbLog.toString());
		return batchGetMediaSaleFromCache(saleIdList);
	}

	@Override
	public List<AnnouncementsContent> getAnnouncementsContentFromCache(String categoryCode) {
		AnnouncementsCategory ac = announcementCacheDao.getAnnouncementsCategoryByCode(categoryCode);
		if (null == ac) {
			LOGGER.info("不存在的公告类型标识:" + categoryCode);
			return null;
		}
		return announcementCacheDao.getAnnouncementsContentByCode(categoryCode);
	}

	@Override
	public AnnouncementsCategory getAnnouncementsCategoryFromCache(String categoryCode) {
		return announcementCacheDao.getAnnouncementsCategoryByCode(categoryCode);
	}

	@Override
	public List<Long> getOnShelfSaleIdList(List<Long> saleIdList) {
		if (CollectionUtils.isEmpty(saleIdList)) {
			return null;
		}
		List<MediaSaleCacheVo> saleList;
		try {
			saleList = this.batchGetMediaSaleFromCache(saleIdList);
			if (CollectionUtils.isEmpty(saleList)) {
				return null;
			}
			List<Long> onShelfSaleIdList = new ArrayList<Long>();
			for (MediaSaleCacheVo mediaSaleCacheVo : saleList) {
				if (mediaSaleCacheVo.getShelfStatus() != null && mediaSaleCacheVo.getShelfStatus() == 1) {
					onShelfSaleIdList.add(mediaSaleCacheVo.getSaleId());
				}
			}
			return onShelfSaleIdList;
		} catch (Exception e) {
			LOGGER.error("获取缓存异常", e);
			return saleIdList;
		}
	}

	@Override
	public void cleanCacheByKey(String cacheKey) {
		if (masterRedisTemplate.hasKey(cacheKey)) {
			masterRedisTemplate.delete(cacheKey);
			LOGGER.info("消除缓存key[" + cacheKey + " ]的缓存,keys=" + cacheKey);
		} else {
			LOGGER.info("没有对应的缓存key[" + cacheKey + " ]的缓存,keys=" + cacheKey);
		}
	}

	@Override
	public void cleanCacheByKeyPattern(String cacheKeyPattern) {
		Set<String> keys = masterRedisTemplate.keys(cacheKeyPattern);
		if (null != keys && keys.size() > 0) {
			masterRedisTemplate.delete(keys);
			LOGGER.info("清除缓存　keys=" + keys);
		} else {
			LOGGER.info("缓存中没有对应的keys=" + keys);
		}
	}

	@Override
	public void insertfirstLoginGivingFlagToRedis(Long custId) {
		try {
			masterRedisTemplate.opsForValue().set("first_login_giving_flag_" + custId, 1, Constans.CACHE_EXPIRE_TIME_OF_MEDIA_LIST_RANKING,
					TimeUnit.SECONDS);
		} catch (Exception e) {
			LOGGER.error("插入缓存异常！key[" + "first_login_giving_flag_" + custId + " ]", e);
		}
	}

	@Override
	public void deleteTodayActivityUserCache(Long custId) {
		masterRedisTemplate.delete(Constans.MEDIA_ACTIVITY_TODAY_USER_CACHE_KEY + custId);
	}

	@Override
	public void deleteActivityUserVoCache(Long custId) {
		masterRedisTemplate.delete(Constans.MEDIA_ACTIVITY_USER_KEY + custId);
	}

	@Override
	public void cleanActivityUserVoCache() {
		Set<String> userKeys = masterRedisTemplate.keys(Constans.MEDIA_ACTIVITY_USER_KEY + "*");
		if (!CollectionUtils.isEmpty(userKeys)) {
			masterRedisTemplate.delete(userKeys);
		}
	}

	@Override
	public void deleteBlockCache(String code) {
		masterRedisTemplate.delete(Constans.MEDIA_BLOCK_CACHE_KEY + code);
	}

	@Override
	public void deleteEbookRewardedUsersCache(Long mediaId, String channel) {
		masterRedisTemplate.delete(Constans.MEDIA_REWARDED_USER_CACHE_KEY + channel + "_" + mediaId);
	}

	@Override
	public void deleteUserRewardBooksIdsCache(Long custId) {
		masterRedisTemplate.delete(Constans.MEDIA_REWARD_SALE_IDS_CACHE_KEY + custId);
	}

	@Override
	public void deletePrizeListCache(int vestType) {
		masterRedisTemplate.delete(Constans.MEDIA_PRIZE_VEST_TYPE_KEY + vestType);
	}

	@Override
	public void deleteRankConsToBookByCodeCache(String code) {
		masterRedisTemplate.delete(Constans.MEDIA_RANK_CONS_BOOK_KEY + code);
	}

	@Override
	public long getMediaCountByCategoryAndDimension(String categoryCode, String dimension) {
		final String cacheKey = Constans.MEDIA_CATEGORY_DATA_CACHE_KEY + (categoryCode + "_" + dimension).toLowerCase();
		Catetory category = getCatetoryFromCache(categoryCode);
		if (slaveRedisTemplateForId.hasKey(cacheKey)) {
			return slaveRedisTemplateForContents.opsForValue().get(cacheKey);
		} else {
			// 查询该分类下所有的media总数
			Map<String, Object> mapParam = new HashMap<String, Object>();
			mapParam.put("sexChannel", category.getPath());// path
			mapParam.put("order_column", "sale_count");
			final long totalCount = mediaStatisticsDao.getSaleTotalCount(mapParam);
			taskExecutor.execute(new Runnable() {
				public void run() {
					masterRedisTemplateForContents.opsForValue().set(cacheKey, totalCount);
					masterRedisTemplateForId.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_MEDIA_CATEGORY_DATA, TimeUnit.SECONDS);
					LOGGER.info("异步添加分类 " + cacheKey + " 内容,总数=" + totalCount);// saleId=" + cacheSaleIdList + "
				}
			});
			return totalCount;
		}
	}

	@Override
	public List<CatetoryMediaSaleVo> getMediaSaleByCatetoryCode(boolean withData, int start, int end, String catetoryCode) throws Exception {
		Catetory selfCatetory = getCatetoryFromCache(catetoryCode);
		if (null == selfCatetory) {
			LOGGER.info("不存在的media分类标识[" + catetoryCode + " ]");
			return null;
		}
		List<Catetory> mediaCatetoryList = getCatetoryListFromCache(selfCatetory.getPath());
		if (null == mediaCatetoryList || mediaCatetoryList.size() == 0) {
			LOGGER.info("media分类标识[" + catetoryCode + " 下没有子节点]");
			return null;
		}
		long e1 = System.currentTimeMillis();
		List<CatetoryMediaSaleVo> catetoryVoList = CatetoryUtils.parse(selfCatetory, mediaCatetoryList,
				ConfigPropertieUtils.getString("media.resource.images.column.http.path"));
		long e2 = System.currentTimeMillis();
		int mediaCategoryTopN = Integer.parseInt(systemApi.getProperty("mediaCategoryTopN", "3"));
		LogUtil.info(LOGGER, "解析树时间" + (e2 - e1));
		if (withData) {
			// 读取分类信息和叶子节点上的数据
			for (CatetoryMediaSaleVo cv : catetoryVoList) {
				if (cv.isLeaf()) {
					// 叶子节点,添数据
					String code = cv.getCode();
					long e3 = System.currentTimeMillis();
					List<MediaSaleCacheVo> listMediaSingelSales = getMediaSaleByColumnCode(start, end, code).listId;
					long e4 = System.currentTimeMillis();
					LogUtil.info(LOGGER, "添加叶子media时间" + (e4 - e3));
					if (null == listMediaSingelSales || listMediaSingelSales.size() == 0) {
						LogUtil.warn(LOGGER, "分类标识[" + code + "]没有单品销售实体数据");
						continue;
					} else if (listMediaSingelSales.size() < mediaCategoryTopN) {
						LogUtil.warn(LOGGER, "分类标识[" + code + "]单品销售实体数据小于指定数据" + mediaCategoryTopN);
						continue;
					}
					cv.setMediaSaleList(listMediaSingelSales);
				}
			}
		}
		return catetoryVoList;
	}

	@Override
	public void refreshActivityCache(Integer activityId) {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_ACTIVITY);
		addCacheVo.setActivityId(activityId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
	}

	@Override
	public ActivityCacheVo getActivityFromCache(Integer activityId) {
		ActivityCacheVo activityCacheVo = activityCacheDao.getActivityCacheVo(activityId);
		// 刷新缓存过期时间
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.REFRESH_TYPE_ACTIVITY);
		addCacheVo.setActivityId(activityId);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
		return activityCacheVo;
	}

	@Override
	public void deleteActivityCache(Integer activityId) {
		// 删除活动缓存
		activityCacheDao.deleteActivityCacheVo(activityId);
		// 删除活动销售主体关系
		activitySaleCacheDao.deleteActivitySaleCacheByActivityId(activityId);
	}

	@Override
	public void batchRefreshActivityCache(List<Integer> activityIdList) {
		AddCacheVo addCacheVo = new AddCacheVo();
		addCacheVo.setAddType(AddCacheVo.ADD_TYPE_ACTIVITY_BATCH);
		addCacheVo.setActivityIdList(activityIdList);
		SetCacheJob setCacheJob = new SetCacheJob(mediaCacheDao, chapterCacheDao, mediaSaleCacheDao, authorCacheDao, userAuthorityCacheDao,
				userMonthlyCacheDao, systemApi, masterRedisTemplate, addCacheVo, chapterDao, masterRedisTemplateForContents,
				discoveryCacheDao, activityCacheDao, activitySaleCacheDao);
		taskExecutor.execute(setCacheJob);
	}

	@Override
	public List<ActivityCacheVo> batchGetActivityFromCache(List<Integer> activityIdList) {
		if (CollectionUtils.isEmpty(activityIdList)) {
			return null;
		}
		// 首先从缓存读取
		List<ActivityCacheVo> activityCacheList = activityCacheDao.mGetActivityCacheVo(activityIdList);
		if (!this.isCacheNotEnough(activityCacheList, 1)) {
			return activityCacheList;
		}
		List<ActivityInfo> activeInfoList = activityInfoService.findMasterByIds(activityIdList);
		if (CollectionUtils.isEmpty(activeInfoList)) {
			return null;
		}
		List<ActivityCacheVo> activeCacheList = new ArrayList<ActivityCacheVo>();
		for (ActivityInfo activityInfo : activeInfoList) {
			ActivityCacheVo activityCacheVo = new ActivityCacheVo();
			MediaCacheBeanUtils.copyActivityInfoToActivityCacheVo(activityInfo, activityCacheVo);
			activeCacheList.add(activityCacheVo);
		}
		// 异步刷新缓存
		this.batchRefreshActivityCache(activityIdList);
		return activeCacheList;
	}

	@Override
	public void batchDeleteActivityCache(List<Integer> activityIdList) {
		activityCacheDao.mDeleteCacheBasicVo(activityIdList);
		activitySaleCacheDao.mDeleteActivitySaleCacheByActivityId(activityIdList);
	}

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheByActivityId(Integer activityId) {
		List<ActivitySaleCacheVo> activitySaleCacheList = activitySaleCacheDao.getActivitySaleCacheByActivityId(activityId);
		if (!this.isCacheNotEnough(activitySaleCacheList, 1)) {
			return activitySaleCacheList;
		}
		List<ActivitySale> activitySaleList = activitySaleDao.getByActivityIdInUse(activityId);
		activitySaleCacheList = new ArrayList<ActivitySaleCacheVo>();
		for (ActivitySale activitySale : activitySaleList) {
			ActivitySaleCacheVo activitySaleCacheVo = new ActivitySaleCacheVo();
			MediaCacheBeanUtils.copyActivitySaleToActivitySaleCacheVo(activitySale, activitySaleCacheVo);
			activitySaleCacheList.add(activitySaleCacheVo);
		}
		// 刷新缓存
		this.refreshActivityCache(activityId);
		return activitySaleCacheList;
	}

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheBySaleId(Long saleId) {
		List<ActivitySaleCacheVo> activitySaleCacheList = activitySaleCacheDao.getActivitySaleCacheBySaleId(saleId);
		if (!this.isCacheNotEnough(activitySaleCacheList, 1)) {
			return activitySaleCacheList;
		}
		List<ActivitySale> activitySaleList = activitySaleDao.getBySaleIdInUse(saleId);
		activitySaleCacheList = new ArrayList<ActivitySaleCacheVo>();
		List<Integer> activityIdList = new ArrayList<Integer>();
		for (ActivitySale activitySale : activitySaleList) {
			ActivitySaleCacheVo activitySaleCacheVo = new ActivitySaleCacheVo();
			MediaCacheBeanUtils.copyActivitySaleToActivitySaleCacheVo(activitySale, activitySaleCacheVo);
			activitySaleCacheList.add(activitySaleCacheVo);
			activityIdList.add(activitySale.getActivityId());
		}
		// 刷新缓存
		this.batchRefreshActivityCache(activityIdList);
		return activitySaleCacheList;
	}

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheWithActivityBySaleId(Long saleId) {
		List<ActivitySaleCacheVo> activitySaleCacheList = activitySaleCacheDao.getActivitySaleCacheBySaleId(saleId);
		if (!this.isCacheNotEnough(activitySaleCacheList, 1)) {
			for (ActivitySaleCacheVo activitySaleCacheVo : activitySaleCacheList) {
				activitySaleCacheVo.setActivityCacheVo(this.getActivityFromCache(activitySaleCacheVo.getActivityId()));
			}
			return activitySaleCacheList;
		}
		List<ActivitySale> activitySaleList = activitySaleDao.getBySaleIdInUse(saleId);
		activitySaleCacheList = new ArrayList<ActivitySaleCacheVo>();
		List<Integer> activityIdList = new ArrayList<Integer>();
		for (ActivitySale activitySale : activitySaleList) {
			ActivitySaleCacheVo activitySaleCacheVo = new ActivitySaleCacheVo();
			MediaCacheBeanUtils.copyActivitySaleToActivitySaleCacheVo(activitySale, activitySaleCacheVo);
			activitySaleCacheVo.setActivityCacheVo(this.getActivityFromCache(activitySale.getActivityId()));
			activitySaleCacheList.add(activitySaleCacheVo);
			activityIdList.add(activitySale.getActivityId());
		}
		// 刷新缓存
		this.batchRefreshActivityCache(activityIdList);
		return activitySaleCacheList;
	}

	@Override
	public void deleteActivitySaleCacheBySaleId(Long saleId) {
		activitySaleCacheDao.deleteActivitySaleCacheBySaleId(saleId);
	}

	@Override
	public void mDeleteActivitySaleCacheBySaleId(List<Long> saleIdList) {
		activitySaleCacheDao.mDeleteActivitySaleCacheBySaleId(saleIdList);
	}

	@Override
	public void cleanActivityCache() {
		Set<String> infoKeys = masterRedisTemplate.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_INFO + "*");
		if (!CollectionUtils.isEmpty(infoKeys)) {
			masterRedisTemplate.delete(infoKeys);
		}
		Set<String> saleKeys = masterRedisTemplate.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + "*");
		if (!CollectionUtils.isEmpty(saleKeys)) {
			masterRedisTemplate.delete(saleKeys);
		}
	}

	/**
	 * 
	 * Description: 返回上次请求之后有效的公告列表
	 * 
	 * @Version1.0 2015年1月17日 上午9:26:02 by 吕翔 (lvxiang@dangdang.com) 创建
	 * @param lastRequestTime
	 *            上次请求时间
	 * @return
	 */
	public List<Notice> getNoticeList(long lastRequestTime) {
		List<Notice> listNotice = noticeDao.getNoticeList(lastRequestTime);
		return listNotice;
	}

	@Override
	public Map<String, String> getUserInfoByCustId(Long custId) {
		List<String> custIdList = new ArrayList<String>();
		custIdList.add(custId + "");
		Map<String, Map<String, String>> custInfoMap = commonApi.getBatchCustInfos(custIdList, Constans.CACHE_EXPIRE_TIME_OF_USERIMG);
		if (null != custInfoMap && custInfoMap.containsKey(custId + "")) {
			Map<String, String> map = custInfoMap.get(custId + "");
			if (null != map) {
				return map;
			}
		}
		return null;
	}

	public ResultTwoTuple<Integer, List<WorshipRecord>> getWorshipRecord(final int start, final int end, Long custId, String type) {
		final String cacheKey = Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(custId)).concat("_").concat(type);
		List<WorshipRecord> worshipRecordList = null;
		if (this.slaveRedisTemplate.hasKey(cacheKey)) {
			worshipRecordList = (List<WorshipRecord>) this.slaveRedisTemplate.opsForValue().get(cacheKey);
		} else {
			worshipRecordList = this.worshipRecordService.getWorshipRecord(custId, type);
			final List<WorshipRecord> cacheWorshipRecordList = worshipRecordList;
			taskExecutor.execute(new Runnable() {
				public void run() {
					masterRedisTemplate.opsForValue().set(cacheKey, cacheWorshipRecordList);
					masterRedisTemplate.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_CUST_WORSHIP_CACHE, TimeUnit.SECONDS);
					LOGGER.info("添加用户" + cacheKey + "的膜拜列表关系");
				}
			});
		}
		int total = worshipRecordList != null ? worshipRecordList.size() : 0;
		List<WorshipRecord> resultList = worshipRecordList.subList(start, end >= worshipRecordList.size() ? worshipRecordList.size()
				: end + 1);
		return new ResultTwoTuple<Integer, List<WorshipRecord>>(total, resultList);
	}

	@Override
	public List<SpecialTopic> getSpecialTopicFromCache(String stId, String deviceType, String channelType) {
		List<SpecialTopic> listSpecialTopic = new LinkedList<SpecialTopic>();
		if (StringUtils.isNotBlank(stId)) {
			// 如果存在stId,则根据stId直接请求指定专题
			SpecialTopic specialTopic = specialTopicCacheDao.getSpecialTopicFromCache(Integer.parseInt(stId));
			if(specialTopic != null){
				listSpecialTopic.add(specialTopic);
			}
		} else {
			// 则请求首页专题
			List<SpecialTopic> listHomePageSpecialTopic = specialTopicCacheDao.getHomePageSTListFromCache(deviceType, channelType);// 获取首页专题列表
			listSpecialTopic.addAll(listHomePageSpecialTopic);
		}
		return listSpecialTopic;
	}

	@Override
	public void cleanSpecialTopicCache(Set<String> cacheKeys) {
		specialTopicCacheDao.deleteSpecialTopicFromCache(cacheKeys);
	}

}
