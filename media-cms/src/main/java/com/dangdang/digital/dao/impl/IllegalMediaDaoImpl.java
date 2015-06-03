package com.dangdang.digital.dao.impl;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IIllegalMediaDao;
import com.dangdang.digital.model.IllegalMedia;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
@Repository
public class IllegalMediaDaoImpl extends BaseDaoImpl<IllegalMedia> implements
		IIllegalMediaDao {

	@Override
	public IllegalMedia getIllegalMediaById(Integer id) {
		return  this.selectOne("IllegalMediaMapper.selectByPrimaryKey", id);

	}

	@Override
	public PageFinder<IllegalMedia> getIllegalMediaList(
			IllegalMedia illegalMedia, Query query) {
		return this.getPageFinderObjs(illegalMedia, query, "IllegalMediaMapper.pageCount", "IllegalMediaMapper.pageData");

	}
	
}
