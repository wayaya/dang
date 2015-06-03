package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICpPullMediaDao;
import com.dangdang.digital.model.CpPullMedia;
import com.dangdang.digital.service.ICpPullMediaService;

@Service
public class CpPullMediaServiceImpl extends BaseServiceImpl<CpPullMedia, Long>
		implements ICpPullMediaService {

	@Resource
	private ICpPullMediaDao cpPullMediaDao;
	@Override
	public IBaseDao<CpPullMedia> getBaseDao() {
		return cpPullMediaDao;
	}
	
}
