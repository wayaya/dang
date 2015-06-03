package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.MediaSale;

@Repository(value="mediaSaleDao")
public class MediaSaleDaoImpl extends BaseDaoImpl<MediaSale> implements
		IMediaSaleDao {
	
	@Override
	public MediaSale getSale(Map<String,Object> map){
		return selectOne("MediaSaleMapper.getSale", map);
	}
	
	@Override
	public List<Map<String, Object>> getSales(Map<String,Object> map){
		
		return (List<Map<String, Object>> )this.getSqlSessionQueryTemplate().selectList("MediaSaleMapper.getSales", map);
	}
}
