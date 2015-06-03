package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaSale;

public interface IMediaSaleDao extends IBaseDao<MediaSale> {

	MediaSale getSale(Map<String,Object> map);

	List<Map<String, Object>> getSales(Map<String, Object> map);
}
