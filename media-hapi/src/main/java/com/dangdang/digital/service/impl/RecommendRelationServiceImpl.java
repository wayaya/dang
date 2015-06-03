package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IRecommendRelationDao;
import com.dangdang.digital.model.JobRecord;
import com.dangdang.digital.model.RecommendRelation;
import com.dangdang.digital.service.IJobRecordService;
import com.dangdang.digital.service.IRecommendRelationService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.vo.MediaCacheBasicVo;

@Service("recommendRelationService")
@Transactional
public class RecommendRelationServiceImpl extends BaseServiceImpl<RecommendRelation, Long>
						implements IRecommendRelationService{

	@Resource
	IRecommendRelationDao recommendRelationDao;
	
	@Resource
	IJobRecordService jobRecordService;
	
	@Resource
	ICacheApi cacheApi;
	
	@Override
	public IBaseDao<RecommendRelation> getBaseDao() {
		return recommendRelationDao;
	}
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, List<MediaCacheBasicVo>> masterRedisTemplate;

	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, List<MediaCacheBasicVo>> slaveRedisTemplate;

	@Override
	public List<Long> getMediaList(RecommendRelation queryObj) {
		return recommendRelationDao.getMediaList(queryObj);
	}

	private List<Long> getRelatedMediaList(RecommendRelation recommendRelation,
			Query query) {
		return recommendRelationDao.getRelatedMediaList(recommendRelation, query);
	}
	
	@Override
	public List<MediaCacheBasicVo> getRelatedMediaListAndToCache(RecommendRelation recommendRelation,
			Query query) throws Exception {
		
		//去买了又买缓存取书id的list
		String redisKey = Constans.RECOMMEND_MEDIA_RELATED_KEY+"_"+recommendRelation.getRelationType()+"_"+recommendRelation.getMediaId();
		List<MediaCacheBasicVo> result = (List<MediaCacheBasicVo>)slaveRedisTemplate.opsForValue().get(redisKey);
		List<Long> resultIds = new ArrayList<Long>();
		
		if(result == null || result.size()==0){
		
			//如果请求是今天，而今天的订单任务调度还没跑完，那么返回昨天的数据,以免出现断流情况
			Date date = DateUtil.getOnlyDay(new Date());
			if(date.getTime() == DateUtil.getOnlyDay(recommendRelation.getCreationDate()).getTime()){
				Long dailyTaskId = 2L;
				if(recommendRelation.getRelationType()==2){
					dailyTaskId = 7L;
				}
				JobRecord dailyDataJob = jobRecordService.findMasterById(dailyTaskId);
				if(dailyDataJob!=null && (date.getTime() > DateUtil.getOnlyDay(dailyDataJob.getLastChangedDate()).getTime())){
					recommendRelation.setCreationDate(DateUtil.addDate(recommendRelation.getCreationDate(), -1));
				}
			}
			
			resultIds = this.getRelatedMediaList(recommendRelation, query);
		}else{
			
			for(MediaCacheBasicVo vo :result){
				resultIds.add(vo.getMediaId());
			}
		}
		
		if(resultIds.size()==0){
			return new ArrayList<MediaCacheBasicVo>();
		}
		
		List<MediaCacheBasicVo> basicInfoList = cacheApi.batchGetMediaBasicFromCache(resultIds);
		
		Map<Long, List<MediaCacheBasicVo>> saleMap = new LinkedHashMap<Long, List<MediaCacheBasicVo>>();
		
		for(MediaCacheBasicVo basicInfo:basicInfoList){
			
			List<MediaCacheBasicVo> list = saleMap.get(basicInfo.getSaleId());
			if(list == null){
				list = new ArrayList<MediaCacheBasicVo>();
			}
			list.add(basicInfo);
			saleMap.put(basicInfo.getSaleId(), list);
		}
		
		List<Long> filteredSaleIds = cacheApi.getOnShelfSaleIdList(new ArrayList<Long>(saleMap.keySet()) );
		List<MediaCacheBasicVo> bookListFiltered = new ArrayList<MediaCacheBasicVo>();
		
		if(filteredSaleIds!=null && filteredSaleIds.size()>0){
			
			for(Long saleId: filteredSaleIds){
				List<MediaCacheBasicVo> list = saleMap.get(saleId);
				bookListFiltered.addAll(list);
			}
			
			if((result == null || result.size()==0) && bookListFiltered.size()>0){
				masterRedisTemplate.delete(redisKey);
				masterRedisTemplate.opsForValue().set(redisKey, bookListFiltered);
				masterRedisTemplate.expire(redisKey, 30, TimeUnit.MINUTES);
			}
		}
		return bookListFiltered;
	}

	@Override
	public Set<Long> changeRecommendRelation(
			Map<String, Double> relationScoreMap, Date endDate) {
		
		Set<Long> mediaIdsNeedUpdate = new HashSet<Long>();
		for(String key: relationScoreMap.keySet()){
			
			String[] array = Constans.underlineSpliter.split(key);
			
			if(array.length<3){
				continue;
			}
			
			Long mediaId = SafeConvert.convertStringToLong(array[0], 0L);
			mediaIdsNeedUpdate.add(mediaId);
			Long relatedMediaId = SafeConvert.convertStringToLong(array[1], 0L);
			byte relationType = (byte)SafeConvert.convertStringToInteger(array[2], 0);
			
			RecommendRelation relation = new RecommendRelation();
			relation.setMediaId(mediaId);
			relation.setRelatedMediaId(relatedMediaId);
			relation.setRelationType(relationType);
			Date today = DateUtil.getOnlyDay(endDate);
			relation.setCreationDate(today);
			
			List<RecommendRelation> existedRelations =  findMasterListByParamsObjs(relation);
			
			if(existedRelations!=null && existedRelations.size()>0){
				
				RecommendRelation existedRelation = existedRelations.get(0);
				Double score = existedRelation.getScore();
				score+=relationScoreMap.get(key);
				existedRelation.setScore(score);
//				existedRelation.setLastUpdatedDate(endDate);
				update(existedRelation);
			}else{
				relation.setScore(relationScoreMap.get(key));
				relation.setCreationDate(today);
//				relation.setLastUpdatedDate(endDate);
				save(relation);
			}
		}
		return mediaIdsNeedUpdate;
	}

}
