package com.dangdang.digital.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IUserDeviceApi;
import com.dangdang.digital.model.UserDevice;
import com.dangdang.digital.service.IUserDeviceService;

@Component("userDeviceApi")
public class UserDeviceApiImpl implements IUserDeviceApi {
	@Resource
	private IUserDeviceService userDeviceService;

	@Override
	public List<UserDevice> findListByParams(Object... params) {
		return userDeviceService.findListByParams(params);
	}

}
