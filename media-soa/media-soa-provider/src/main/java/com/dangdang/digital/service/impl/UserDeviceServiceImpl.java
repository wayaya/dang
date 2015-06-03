package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUserDeviceDao;
import com.dangdang.digital.model.UserDevice;
import com.dangdang.digital.service.IUserDeviceService;

@Service
public class UserDeviceServiceImpl extends BaseServiceImpl<UserDevice, Long>  implements IUserDeviceService {

	@Resource
	private IUserDeviceDao userDeviceDao;
	
	@Override
	public IBaseDao<UserDevice> getBaseDao() {
		return userDeviceDao;
	}

	

}
