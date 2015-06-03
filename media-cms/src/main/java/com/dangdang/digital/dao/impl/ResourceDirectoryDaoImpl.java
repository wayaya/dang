package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IResourceDirectoryDao;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.ResourceDirectory;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


@Repository
public class ResourceDirectoryDaoImpl extends BaseDaoImpl<ResourceDirectory>
		implements IResourceDirectoryDao {

	
	@Override
	public List<ResourceDirectory> getTreeByParentId(ResourceDirectory dir) {
		return this.selectList("ResourceDirectoryMapper.getTreeByParentId", dir);
	}

	@Override
	public void deleteByPath(Map map) {
		this.delete("ResourceDirectoryMapper.deleteByPath", map);
	}
}
