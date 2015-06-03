package com.dangdang.digital.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBlockGroupDao;
import com.dangdang.digital.model.BlockGroup;
import com.dangdang.digital.service.IBlockGroupService;

/**
 * Description: 块组Service 实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:24:21  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class BlockGroupServiceImpl extends BaseServiceImpl<BlockGroup,Long> implements IBlockGroupService {

	@Resource IBlockGroupDao blockGroupDao;
	
	@Override
	public IBaseDao<BlockGroup> getBaseDao() {
		// TODO Auto-generated method stub
		return blockGroupDao;
	}


	@Override
	public List<BlockGroup> getTreeByParentId(BlockGroup group) {
		return blockGroupDao.getTreeByParentId(group);
	}

}
