package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaResourceDao;
import com.dangdang.digital.model.MediaResource;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Repository
public class MediaResourceDaoImpl extends BaseDaoImpl<MediaResource> implements
		IMediaResourceDao {

	@Override
	public void deleteResByPath(Map map) {
		this.delete("MediaResourceMapper.delResByPath", map);
	}
	
	
}
