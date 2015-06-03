package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IVolumeDao;
import com.dangdang.digital.model.Volume;
import com.dangdang.digital.service.IVolumeService;


@Service
public class VolumeServiceImpl extends BaseServiceImpl<Volume, Long> implements
		IVolumeService {

	@Resource
	private IVolumeDao volumeDao;
	
	@Override
	public IBaseDao<Volume> getBaseDao() {
		return volumeDao;
	}
	
}
