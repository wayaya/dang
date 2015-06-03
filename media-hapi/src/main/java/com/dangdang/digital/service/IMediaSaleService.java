package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.vo.MediaCacheSaleVo;

public interface IMediaSaleService extends IBaseService<MediaSale, Long> {

	public MediaSale getSale(Map<String,Object> map);

	/**
	 * 
	 * Description:检查缓存，缓存有的 id，不会再去DB查，缓存没有的会从DB查出来放缓存 
	 * @Version1.0 2014年12月8日 下午5:10:57 by 魏嵩（weisong@dangdang.com）创建
	 * @param mediaIdList
	 * @return 
	 */
	Map<Long, MediaCacheSaleVo> cacheRelatedMediaSales(List<Long> mediaIdList);
	
}
