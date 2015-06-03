package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaRelationDao;
import com.dangdang.digital.model.MediaRelation;
import com.dangdang.digital.service.IMediaRelationService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service
public class MediaRelationServiceImpl extends
		BaseServiceImpl<MediaRelation, Long> implements IMediaRelationService {

	@Resource
	private IMediaRelationDao mediaRelationDao;
	@Override
	public IBaseDao<MediaRelation> getBaseDao() {
		return mediaRelationDao;
	}


}
