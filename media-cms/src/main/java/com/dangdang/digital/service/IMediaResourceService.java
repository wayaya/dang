package com.dangdang.digital.service;

import java.util.Map;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.MediaResource;

public interface IMediaResourceService extends IBaseService<MediaResource, Long> {

	public void delByPath(Map map);
}
