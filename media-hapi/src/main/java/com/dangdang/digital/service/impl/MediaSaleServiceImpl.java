package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.vo.MediaCacheSaleVo;

@Service(value="mediaSaleService")
public class MediaSaleServiceImpl extends BaseServiceImpl<MediaSale, Long>
		implements IMediaSaleService {
	
	Logger LOGGER = LoggerFactory.getLogger(MediaSaleServiceImpl.class);

	@Resource(name="mediaSaleDao")
	private IMediaSaleDao mediaSaleDao;
	@Override
	public IBaseDao<MediaSale> getBaseDao() {
		return mediaSaleDao;
	}
	@Override
	public MediaSale getSale(Map<String, Object> map) {
		return mediaSaleDao.getSale(map);
	}
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate;
	
	/**
	 * 如果缓存有的，不会再去DB查一遍
	 */
	@Override
	public Map<Long, MediaCacheSaleVo> cacheRelatedMediaSales(List<Long> mediaIdList){
		
		Map<Long, MediaCacheSaleVo> cacheMap = new HashMap<Long, MediaCacheSaleVo>();
		
		//media的id和redisKey对应关系Map
		Map<Long, String> mediaKeyMap = new HashMap<Long, String>();
		for(Long id: mediaIdList){
			String mediaKey = getMediaSaleInfoKey(id);
			mediaKeyMap.put(id, mediaKey);
		}
		
		//批量查询Redis, 并删除mediaKeyMap缓存命中的media
		List<Object> mediaCacheList = masterRedisTemplate.opsForValue().multiGet(mediaKeyMap.values());
		
		for(Object mediaCache: mediaCacheList){
			MediaCacheSaleVo basicInfo = (MediaCacheSaleVo)mediaCache;
			cacheMap.put(basicInfo.getMediaId(), basicInfo);
			
			mediaKeyMap.remove(basicInfo.getMediaId());
		}
		
		List<Long> mediasNeedQueryDB = new ArrayList<Long>(mediaKeyMap.keySet());
		
		if(mediasNeedQueryDB.size()>0){
		
			Map<String,Object> map = new HashMap<String,Object>();
		    map.put("type", 0);
		    map.put("mediaIds", mediasNeedQueryDB );
		    //TODO Media要加saleId， 应该做出相应的修改
		    List<Map<String,Object>> mediaSales = mediaSaleDao.getSales(map);
		    
		    if( mediaSales!=null && mediaSales.size()>0 ){
		    	
		    	Map<String, Object> updateRedisMap = new HashMap<String, Object>();
		    	
		    	for(Map<String,Object> ms: mediaSales){
		    		
		    		Long mediaId = (Long)ms.get("media_id");
		    		Long saleId = (Long)ms.get("sale_id");
		    		
		    		//存在的，remove掉，剩下的则是需要放空对象的media
		    		mediasNeedQueryDB.remove(mediaId);
		    		
		    		MediaCacheSaleVo mcVo = new MediaCacheSaleVo();
		    		mcVo.setSaleId(saleId);
		    		mcVo.setMediaId(mediaId);
		    		
		    		cacheMap.put(mediaId, mcVo);
		    		
			    	String mediaSaleKey = this.getMediaSaleInfoKey(mediaId);
			    	updateRedisMap.put(mediaSaleKey, mcVo);
			    	
		    	}
		    	
		    	masterRedisTemplate.opsForValue().multiSet(updateRedisMap);
		    }
		    
		    Map<String, Object> updateEmptyValueRedisMap = new HashMap<String, Object>();
		    MediaCacheSaleVo mcVo = new MediaCacheSaleVo();
		    for(Long mediaIdSaleNotExist :mediasNeedQueryDB){
		    	String mediaSaleKey = this.getMediaSaleInfoKey(mediaIdSaleNotExist);
		    	updateEmptyValueRedisMap.put(mediaSaleKey, mcVo);
		    	cacheMap.put(mediaIdSaleNotExist, mcVo);
		    }
		    masterRedisTemplate.opsForValue().multiSet(updateEmptyValueRedisMap);
		}
		
		return cacheMap;
	}
	private String getMediaSaleInfoKey(Long mediaId) {
		return Constans.RECOMMEND_MEDIA_SALE_KEY+mediaId;
	}
}
