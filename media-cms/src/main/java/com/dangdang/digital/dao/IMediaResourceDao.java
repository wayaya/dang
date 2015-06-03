package com.dangdang.digital.dao;

import java.util.Map;

import com.dangdang.digital.model.MediaResource;

public interface IMediaResourceDao extends IBaseDao<MediaResource> {

	public void deleteResByPath(Map map);
}
