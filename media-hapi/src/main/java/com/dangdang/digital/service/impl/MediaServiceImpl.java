package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.model.JobRecord;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.service.IJobRecordService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.vo.MediaCacheBasicVo;

@Service(value = "mediaService")
public class MediaServiceImpl extends BaseServiceImpl<Media, Long> implements IMediaService {

	@Resource(name = "mediaDao")
	private IMediaDao mediaDao;

	@Resource
	ICacheApi cacheApi;

	@Resource(name = "masterRedisTemplate")
	protected RedisTemplate<String, Object> masterRedisTemplate;

	@Resource
	IJobRecordService jobRecordService;

	@Override
	public IBaseDao<Media> getBaseDao() {
		return mediaDao;
	}

	@Override
	public void saveCateSign(Media media, String cateIds) {
		this.update(media);
		if (cateIds != null && !"".equals(cateIds)) {
			String arr[] = cateIds.split(",");
			Media m = new Media();
			for (String str : arr) {
				m.setMediaId(media.getMediaId());
				m.setCatetoryId(Integer.valueOf(str));
				mediaDao.saveCatetorys(m);
			}
		}
	}

	@Override
	public Map<Long, MediaCacheBasicVo> getMediaBasicMap(List<Long> buyAlsoBuyIds) {

		Map<Long, MediaCacheBasicVo> mediaBasicInfoCacheMap = new LinkedHashMap<Long, MediaCacheBasicVo>();
		try {

			List<MediaCacheBasicVo> mediaCacheList = cacheApi.batchGetMediaBasicFromCache(buyAlsoBuyIds);
			for (MediaCacheBasicVo mediaCache : mediaCacheList) {
				mediaBasicInfoCacheMap.put(mediaCache.getMediaId(), mediaCache);
			}
		} catch (Exception e) {
			return getMediaBasicCache(buyAlsoBuyIds);
		}
		return mediaBasicInfoCacheMap;
	}

	@Override
	public Map<Long, MediaCacheBasicVo> getMediaBasicCache(List<Long> buyAlsoBuyIds) {

		Map<Long, MediaCacheBasicVo> mediaBasicInfoCacheMap = new HashMap<Long, MediaCacheBasicVo>();

		// media的id和redisKey对应关系Map
		Map<Long, String> mediaKeyMap = new HashMap<Long, String>();

		for (Long id : buyAlsoBuyIds) {
			String mediaKey = getMediaBasicInfoKey(id);
			mediaKeyMap.put(id, mediaKey);
		}

		// 批量查询Redis, 并删除mediaKeyMap缓存命中的media
		List<Object> mediaCacheList = masterRedisTemplate.opsForValue().multiGet(mediaKeyMap.values());

		for (Object mediaCache : mediaCacheList) {
			MediaCacheBasicVo basicInfo = (MediaCacheBasicVo) mediaCache;
			mediaBasicInfoCacheMap.put(basicInfo.getMediaId(), basicInfo);

			mediaKeyMap.remove(basicInfo.getMediaId());
		}

		// mediaKeyMap现在全是没有缓存的media, 去数据库查询，查询结果放缓存，不存在的放一个空的对象在缓存里
		List<Long> needQueryMediaBasicInfoDB = new ArrayList<Long>(mediaKeyMap.keySet());

		if (needQueryMediaBasicInfoDB.size() > 0) {
			List<Media> medias = this.findByIds(needQueryMediaBasicInfoDB);

			Map<String, Object> updateRedisMap = new HashMap<String, Object>();

			for (Media mediaDB : medias) {

				String mediaKey = getMediaBasicInfoKey(mediaDB.getMediaId());
				MediaCacheBasicVo mediaVo = new MediaCacheBasicVo();
				try {
					PropertyUtils.copyProperties(mediaVo, mediaDB);
				} catch (Exception e) {
				}
				updateRedisMap.put(mediaKey, mediaVo);

				mediaBasicInfoCacheMap.put(mediaDB.getMediaId(), mediaVo);
				needQueryMediaBasicInfoDB.remove(mediaDB.getMediaId());
			}

			masterRedisTemplate.opsForValue().multiSet(updateRedisMap);
		}

		Map<String, Object> updateRedisNotExistsMap = new HashMap<String, Object>();
		MediaCacheBasicVo emptyVo = new MediaCacheBasicVo();

		for (Long doNotExistMediaId : needQueryMediaBasicInfoDB) {
			String mediaKey = getMediaBasicInfoKey(doNotExistMediaId);
			updateRedisNotExistsMap.put(mediaKey, emptyVo);
			mediaBasicInfoCacheMap.put(doNotExistMediaId, emptyVo);
		}

		if (updateRedisNotExistsMap.size() > 0) {
			masterRedisTemplate.opsForValue().multiSet(updateRedisNotExistsMap);
		}

		return mediaBasicInfoCacheMap;
	}

	private String getMediaBasicInfoKey(Long id) {
		return "media_basic_" + id;
	}

	@Override
	public List<Long> getMediasByAuthorExceptThis(Long authorId, String channelType, Long mediaId, Integer start,
			Integer count) throws ApiException {
		return this.getAllMediasByAuthor(authorId, channelType, start, count, mediaId);
	}

	@Override
	public List<Long> getAllMediasByAuthor(Long authorId, String channelType, Integer start, Integer count, Long mediaId)
			throws ApiException {
		if (authorId == null) {
			throw ApiException.invalidParams();
		}
		if (start == null) {
			start = 0;
		}
		if (count == null) {
			count = Integer.MAX_VALUE;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(channelType)) {
			paramMap.put("channelType", channelType);
		}
		paramMap.put("authorId", authorId);

		JobRecord mediaStatisticJob = jobRecordService.findMasterById(10L);
		paramMap.put("statisticsDay", DateUtils.format(new Date(), "yyyy-MM-dd"));
		// 若当天的media statistic任务没跑完，获取昨天的
		if (mediaStatisticJob == null
				|| mediaStatisticJob.getLastChangedDate().getTime() < DateUtil.getOnlyDay(new Date()).getTime()) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			paramMap.put("statisticsDay", DateUtils.format(cal.getTime(), "yyyy-MM-dd"));
		}
		if (mediaId != null) {
			paramMap.put("mediaId", mediaId);
		}
		List<Long> mediaIdList = mediaDao.getAllMediasByAuthor(paramMap, start, count);
		return mediaIdList;

	}

	@Override
	public Long getMediasCountByAuthor(Long authorId, String channelType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(channelType)) {
			paramMap.put("channelType", channelType);
		}
		paramMap.put("authorId", authorId);
		paramMap.put("statisticsDay", DateUtils.format(new Date(), "yyyy-MM-dd"));
		Long total = mediaDao.getMediasCountByAuthor(paramMap);
		if (total == null || total == 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			paramMap.put("statisticsDay", DateUtils.format(cal.getTime(), "yyyy-MM-dd"));
			total = mediaDao.getMediasCountByAuthor(paramMap);
		}
		return total;
	}
}
