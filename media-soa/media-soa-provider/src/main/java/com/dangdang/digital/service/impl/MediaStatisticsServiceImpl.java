package com.dangdang.digital.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICatetoryDao;
import com.dangdang.digital.dao.IMediaStatisticsDao;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IMediaStatisticsService;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;


/**
 * MediaStatistics Manager.
 */
@Service
public class MediaStatisticsServiceImpl extends BaseServiceImpl<MediaStatistics, Long> implements IMediaStatisticsService {
	
	Logger logger = LoggerFactory.getLogger(MediaStatisticsServiceImpl.class);

	@Resource
	IMediaStatisticsDao dao;
	@Resource
	ICacheService cacheService;
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate; 
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate; 	
	
	@Resource
	ICatetoryDao catetoryDao;
	
	public List<MediaStatistics>  getBySaleIds(List<Long> saleId){
		return dao.getBySaleIds(saleId);
	}
	public IBaseDao<MediaStatistics> getBaseDao() {
		return dao;
	}
	public List<MediaStatistics> getUpdatedMedia(Map<String,Object> paramObj){
		return dao.getMedia(paramObj);
	}
	public List<MediaStatistics> getNewestMedias(Map<String,Object> paramObj){
		return dao.getMedia(paramObj);
	}
	
	public List<Long> getUpdatedSaleIdList(Map<String, Object> paramObj) {
		return dao.getSaleIdList(paramObj);
	}
	
	public List<Long> getNewestSaleIdList(Map<String, Object> paramObj) {
		return dao.getSaleIdList(paramObj);
	}
	
	public Long getUpdatedSaleTotalCount(Map<String, Object> paramObj) {
		return dao.getSaleTotalCount(paramObj);
	}
	@Override
	public Long getNewestSaleTotalCount(Map<String, Object> paramObj) {
		return dao.getSaleTotalCount(paramObj);
	}
	/**
	 * 
	 * Description: 根据分类标识和维度标识,查询该分类下该维度下指定数量的SaleId编号
	 * @Version1.0 2014年12月12日 上午10:42:19 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param offset 查询limit 偏移量
	 * @param count
	 * @param categoryCode
	 * @param dimension
	 * @return
	 */
	public List<Long> getSaleIdListByCategoryCodeAndDimension(int offset,int count,String categoryCode,String dimension){
		return dao.getSaleIdListByCategoryCodeAndDimension(offset,count,categoryCode,dimension);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void batchUpdateCategoryHotSellCache(List<Long> saleIds) throws Exception{
		
		logger.info("batchUpdateCategoryHotSellCache called, deal with saleIds"+saleIds);
		
		List<Catetory> categoryList = (List<Catetory>)slaveRedisTemplate.opsForValue().get(Constans.RECOMMEND_MEDIA_YUANCHUANG_CATEGORYPATHS);
		if(categoryList==null||categoryList.size()==0){
			logger.info("batchUpdateCategoryHotSellCache categoryList cache is empty");
			categoryList = catetoryDao.getCatetoryListByPathPrifix("YC-");
			masterRedisTemplate.opsForValue().set(Constans.RECOMMEND_MEDIA_YUANCHUANG_CATEGORYPATHS, categoryList);
			masterRedisTemplate.expire(Constans.RECOMMEND_MEDIA_YUANCHUANG_CATEGORYPATHS, 3, TimeUnit.DAYS);
		}
		List<MediaSaleCacheVo> mediaSaleList = cacheService.batchGetMediaSaleFromCache(saleIds);
		
		if(mediaSaleList!=null && mediaSaleList.size()>0){
			
			List<Long> mediaShelfDownList = new ArrayList<Long>();
			
			for(MediaSaleCacheVo vo:mediaSaleList){
				if(vo.getMediaList()!=null){
					for(MediaCacheWholeVo tmp:vo.getMediaList()){
						mediaShelfDownList.add(tmp.getMediaId());
					}
				}
			}
			
			for(Catetory category:categoryList){
			
				List<MediaCacheBasicVo> categoryHotBookList = (List<MediaCacheBasicVo>)slaveRedisTemplate.opsForValue().get(Constans.RECOMMEND_CATEGORIES_BEST_SELLER_SOLO_KEY+category.getPath());
				if(categoryHotBookList==null||categoryHotBookList.size()==0){
					continue;
				}
				
				List<MediaCacheBasicVo> categoryHotBookListClone = new ArrayList<MediaCacheBasicVo>(categoryHotBookList);
				
				for(MediaCacheBasicVo cloneVo:categoryHotBookListClone){
					if(mediaShelfDownList.contains(cloneVo.getMediaId())){
						logger.info("batchUpdateCategoryHotSellCache removing from category:"+category.getPath()+" saleId:"+cloneVo.getSaleId());
						categoryHotBookList.remove(cloneVo);
					}
				}
				
				masterRedisTemplate.delete(Constans.RECOMMEND_CATEGORIES_BEST_SELLER_SOLO_KEY+category.getPath());
				masterRedisTemplate.opsForValue().set(Constans.RECOMMEND_CATEGORIES_BEST_SELLER_SOLO_KEY+category.getPath(), categoryHotBookList);
				masterRedisTemplate.expire(Constans.RECOMMEND_CATEGORIES_BEST_SELLER_SOLO_KEY+category.getPath(), 24 * 2, TimeUnit.HOURS);
			}
			
		}
		
	}
}
