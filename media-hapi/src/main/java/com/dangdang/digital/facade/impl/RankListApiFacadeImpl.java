package com.dangdang.digital.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IRankingListApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.facade.IRankListApiFacade;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;

@Service
public class RankListApiFacadeImpl implements IRankListApiFacade{

	@Resource
	IRankingListApi rankingListApi;
	@Resource
	ICacheApi cacheApi;
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, ResultTwoTuple<Long, List<Long>>> masterRedisTemplate;
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, ResultTwoTuple<Long, List<Long>>> slaveRedisTemplate;
	
	@Override
	public ResultTwoTuple<Long, List<Long>> getAllSaleIdListByType(String type){
		
		String key = Constans.MEDIA_LIST_RANKING_SALE_CACHE_KEY+type;
		ResultTwoTuple<Long, List<Long>> result = (ResultTwoTuple<Long, List<Long>>)slaveRedisTemplate.opsForValue().get(key);
		
		if(result==null){
			result = rankingListApi.getSaleIdListByType(0, 500, type);
			if(result!=null&&result.listId!=null&&result.listId.size()>0){
				masterRedisTemplate.opsForValue().set(key, result);
				masterRedisTemplate.expire(key, 12, TimeUnit.HOURS);
			}
		}
		return result;
	}
	
	
	@Override
	public List<MediaCacheWholeVo> getAllMediaListByType(String type) throws Exception{
		
		List<MediaCacheWholeVo> resultList = new ArrayList<MediaCacheWholeVo>();
		
		ResultTwoTuple<Long, List<Long>> resultWithSaleIds = this.getAllSaleIdListByType(type);
		
		if(resultWithSaleIds!=null && resultWithSaleIds.listId!=null&& resultWithSaleIds.listId.size()>0){
			
			List<MediaSaleCacheVo> saleList = cacheApi.batchGetMediaSaleFromCache(resultWithSaleIds.listId);
			for(MediaSaleCacheVo saleCacheVo: saleList){
				if(saleCacheVo.getMediaList()!=null){
					resultList.addAll(saleCacheVo.getMediaList());
				}
			}
			
		}
		
		return resultList;
	}
	
	@Override
	public List<MediaCacheWholeVo> getRandomMediaListByTypeAndNumber(String type, int number) throws Exception{
		
		if(number<=0){
			number = 100;
		}
		
		List<MediaCacheWholeVo> resultList = new ArrayList<MediaCacheWholeVo>();
		
		String key = Constans.MEDIA_LIST_RANKING_SALE_RANDOM_DAILY_CACHE_KEY+type;
		//在缓存里取当天的从前500里随机取出来的100条，如果没有，随机取100放缓存
		ResultTwoTuple<Long, List<Long>> randomResult = (ResultTwoTuple<Long, List<Long>>)slaveRedisTemplate.opsForValue().get(key);
		
		if(randomResult==null){
			
			ResultTwoTuple<Long, List<Long>> resultWithSaleIds = this.getAllSaleIdListByType(type);
			
			if( resultWithSaleIds!=null && resultWithSaleIds.listId!=null&& resultWithSaleIds.listId.size()>0 ){
				//如果以后逻辑需要变化，比如在前200里随机取100，可以改动下面这条语句
				List<Long> randomList = getRandomList(resultWithSaleIds.listId, resultWithSaleIds.listId.size());
				int maxNumber = randomList.size()>100?100:randomList.size();
				randomResult = new ResultTwoTuple<Long, List<Long>>((long)maxNumber, new ArrayList(randomList.subList(0, maxNumber)));
				masterRedisTemplate.opsForValue().set(key, randomResult);
				masterRedisTemplate.expire(key, 24, TimeUnit.HOURS);
			}
		}
		
		if(randomResult!=null && randomResult.listId!=null&& randomResult.listId.size()>0){
			
			number = randomResult.listId.size()> number?number:randomResult.listId.size();
			List<MediaSaleCacheVo> saleList = cacheApi.batchGetMediaSaleFromCache(randomResult.listId.subList(0, number));
			for(MediaSaleCacheVo saleCacheVo: saleList){
				if(saleCacheVo.getMediaList()!=null){
					//判断是否下架
					if(saleCacheVo.getShelfStatus().equals(Constans.MEDIA_SHELF_STATUS_UP)){
						resultList.addAll(saleCacheVo.getMediaList());
					}
				}
			}
		}
		
		return resultList;
	}


	private List<Long> getRandomList(List<Long> list, int number) {
		
		List<Long> cloneList = new ArrayList<Long>(list);
		if(number==0){
			return cloneList;
		}
		if(number>list.size()){
			number = list.size();
		}
		List<Long> resultList = new ArrayList<Long>();
		Random random = new Random();
		while(resultList.size()<number){
			int randomIndex = Math.abs(random.nextInt()%cloneList.size());
			resultList.add(cloneList.remove(randomIndex));
		}
		
		return resultList;
	}
	
}
