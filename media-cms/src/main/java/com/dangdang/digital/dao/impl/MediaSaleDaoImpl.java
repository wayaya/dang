package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.MediaSale;

@Repository(value="mediaSaleDao")
public class MediaSaleDaoImpl extends BaseDaoImpl<MediaSale> implements
		IMediaSaleDao {
	
	public List<MediaSale> getSale(Map<String,Object> map){
		return selectList("MediaSaleMapper.getSale", map);
	}

	@Override
	public void toShelf(Map map) {
		update("MediaSaleMapper.toShelf", map);
	}
}
