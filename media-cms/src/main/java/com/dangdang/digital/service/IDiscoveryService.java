package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Discovery;

public interface IDiscoveryService extends IBaseService<Discovery, Long> {
	public int updateListDiscovery(final List<Discovery> discoveries);
}
