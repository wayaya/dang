package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.MediaSale;

@Repository(value="mediaSaleDao")
public class MediaSaleDaoImpl extends BaseDaoImpl<MediaSale> implements
		IMediaSaleDao {
	
}
