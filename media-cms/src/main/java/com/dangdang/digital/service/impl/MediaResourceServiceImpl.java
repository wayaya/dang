package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaResourceDao;
import com.dangdang.digital.dao.IResourceDirectoryDao;
import com.dangdang.digital.model.MediaResource;
import com.dangdang.digital.service.IMediaResourceService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service
public class MediaResourceServiceImpl extends
		BaseServiceImpl<MediaResource, Long> implements IMediaResourceService {

	
	@Resource
	private IMediaResourceDao mediaResourceDao;
	@Resource
	private IResourceDirectoryDao resourceDirectoryDao;
	
	@Override
	public IBaseDao<MediaResource> getBaseDao() {
		return mediaResourceDao;
	}

	@Override
	public void delByPath(Map map) {
		mediaResourceDao.deleteResByPath(map);
		resourceDirectoryDao.deleteByPath(map);
	}}

	