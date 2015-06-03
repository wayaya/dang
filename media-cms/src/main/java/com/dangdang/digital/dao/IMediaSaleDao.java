package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaSale;

public interface IMediaSaleDao extends IBaseDao<MediaSale> {

	List<MediaSale> getSale(Map<String,Object> map);
	
	void toShelf(Map map);
}
