package com.dangdang.digital.service;

import com.dangdang.digital.model.SysProperties;

public interface ISysPropertiesService extends IBaseService<SysProperties, Integer> {

	String getValue(String key);
}
