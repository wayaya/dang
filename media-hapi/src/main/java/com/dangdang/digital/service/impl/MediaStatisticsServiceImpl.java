package com.dangdang.digital.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaStatisticsDao;
import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.IMediaStatisticsService;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.view.MediaCategoryData;
import com.dangdang.digital.vo.MediaCacheBasicVo;


/**
 * 
 * Description: Media 二级分类各个维度统计数据服务
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 上午10:14:14  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Service
public class MediaStatisticsServiceImpl extends BaseServiceImpl<MediaStatistics, Long> implements IMediaStatisticsService {

	@Resource
	IMediaStatisticsDao dao;
	
	@Resource
	ICacheApi cacheApi;
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate; 
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate; 
	
	public IBaseDao<MediaStatistics> getBaseDao() {
		return dao;
	}
	
	@Override
	public int getMediaCategoryMappingCount(Date date) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statisticsDay", date);
		return dao.getMediaCategoryMappingCount(map);
	}

	@Override
	public List<Map<String, Object>> getMediaCategoryMapping(
			MediaStatistics statistic, Query query) {
		
		Map<String, Object> params = SafeConvert.convertBeanToMap(statistic);
		return dao.getMediaCategoryMapping(params, query);
	}
	
	@Override
	public List<String> getMediaCategoryListToCache(
			MediaStatistics statistic) {
		
		List<MediaStatistics> list  = this.findListByParamsObjs(statistic);
		
		List<String> categoryList = new ArrayList<String>();
		if(list!=null && list.size()>0){
			
			MediaStatistics statisticFromDB = list.get(0);
			
			if(statisticFromDB.getMediaId()!=null && StringUtils.isNotEmpty(statisticFromDB.getMediaCategoryIds()) 
					&& !StringUtils.isBlank(statisticFromDB.getMediaCategoryIds())){
				
				String[] categoryArray = Constans.commaSpliter.split(statisticFromDB.getMediaCategoryIds());
				for(String category: categoryArray){
					category = category.trim();
					if(StringUtils.isNotEmpty(category)){
						categoryList.add(category);
					}
				}
				
				if(categoryList.size()>0){
					masterRedisTemplate.opsForValue().set(Constans.RECOMMEND_MEDIA_CATEGORIES_KEY+statisticFromDB.getMediaId(), new MediaCategoryData(statisticFromDB.getMediaId(),categoryList));
					masterRedisTemplate.expire(Constans.RECOMMEND_MEDIA_CATEGORIES_KEY+statisticFromDB.getMediaId(), 3, TimeUnit.DAYS);
				}
			}
		}
		return categoryList;
	}
	
	@Override
	public List<Long> getBestSellerOfSpecifiedCategory( Date fromDate, String categoryPath, Query query) {
		
		List<MediaCacheBasicVo> categoryHotBookList = (List<MediaCacheBasicVo>)slaveRedisTemplate.opsForValue().get(Constans.RECOMMEND_CATEGORIES_BEST_SELLER_SOLO_KEY+categoryPath);
		List<Long> filteredMediaIds = new ArrayList<Long>();
		
		if(categoryHotBookList==null||categoryHotBookList.size()==0){
			//一般不会走到这个分支
			categoryHotBookList = new ArrayList<MediaCacheBasicVo>();
		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromDate", fromDate);
			params.put("categoryPath", categoryPath);
			List<Map<String, Object>> list = dao.getBestSellerOfSpecifiedCategory(params, query);
			
			for(Map<String, Object> mediaCategories : list){
				Long mediaId = (Long)mediaCategories.get("media_id");
				Long saleId = (Long)mediaCategories.get("sale_id");
				
				MediaCacheBasicVo mediaBasicVo = new MediaCacheBasicVo();
				mediaBasicVo.setSaleId(saleId);
				mediaBasicVo.setMediaId(mediaId);
				categoryHotBookList.add(mediaBasicVo);
			}
			
			//去掉下架的
			LinkedHashMap<Long, List<MediaCacheBasicVo>> hashMap = new LinkedHashMap<Long, List<MediaCacheBasicVo>>();
			
			for(MediaCacheBasicVo vo: categoryHotBookList){
				
				List<MediaCacheBasicVo> medialist = hashMap.get(vo.getSaleId());
				if(medialist == null){
					medialist = new ArrayList<MediaCacheBasicVo>();
				}
				medialist.add(vo);
				hashMap.put(vo.getSaleId(), medialist);
			}
			
			Set<Long> saleIdsSet = hashMap.keySet();
			List<Long> filteredSaleIds = cacheApi.getOnShelfSaleIdList(new ArrayList<Long>(saleIdsSet));
			
			List<MediaCacheBasicVo> categoryHotBookListFiltered = new ArrayList<MediaCacheBasicVo>();
			
			if(filteredSaleIds!=null ){
				for(Long saleId: filteredSaleIds){
					
					List<MediaCacheBasicVo> medialist = hashMap.get(saleId);
					categoryHotBookListFiltered.addAll(medialist);
					
					for(MediaCacheBasicVo vo: medialist){
						filteredMediaIds.add(vo.getMediaId());
					}
				}
			}
			
			masterRedisTemplate.opsForValue().set(Constans.RECOMMEND_CATEGORIES_BEST_SELLER_SOLO_KEY+categoryPath, categoryHotBookListFiltered);
			masterRedisTemplate.expire(Constans.RECOMMEND_CATEGORIES_BEST_SELLER_SOLO_KEY+categoryPath, 24 * 2, TimeUnit.HOURS);
		}else{
			//去掉下架的(这里不再调用cacheapi, 而是通过通知更新)
			for(MediaCacheBasicVo vo: categoryHotBookList){
				filteredMediaIds.add(vo.getMediaId());
			}
		}
		
		return filteredMediaIds;
		
	}


	@Override
	public Map<String, List<Long>> getMultiCategoryHotBooks(Date date, Set<String> categoriesSet, Query query) {
		
		Map<String, List<Long>> result = new HashMap<String, List<Long>>();
		for(String category: categoriesSet){
			result.put(category, this.getBestSellerOfSpecifiedCategory(date, category, query));
		}
		
		return result;
	}
	
}
