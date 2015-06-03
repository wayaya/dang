package com.dangdang.digital.api;

import java.util.List;

import com.dangdang.digital.model.UserDevice;

public interface IUserDeviceApi {
	public List<UserDevice> findListByParams(Object... params);
}
