package com.dangdang.digital.service;

public interface IRedisQueryService {

	Object getMasterValue(String key);
	Object getSlaveValue(String key);
}
