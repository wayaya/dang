package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IResourceDirectoryDao;
import com.dangdang.digital.model.ResourceDirectory;
import com.dangdang.digital.service.IResourceDirectoryService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service
public class ResourceDirectoryServiceImpl extends
		BaseServiceImpl<ResourceDirectory, Integer> implements
		IResourceDirectoryService {

	@Resource
	private IResourceDirectoryDao resourceDirectoryDao;
	@Override
	public IBaseDao<ResourceDirectory> getBaseDao() {
		return this.resourceDirectoryDao;
	}
	@Override
	public List<ResourceDirectory> getTreeByParentId(ResourceDirectory dir) {
		return resourceDirectoryDao.getTreeByParentId(dir);
	}


}
