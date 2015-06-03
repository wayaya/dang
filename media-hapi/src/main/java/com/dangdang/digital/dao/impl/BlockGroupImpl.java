package com.dangdang.digital.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IBlockGroupDao;
import com.dangdang.digital.model.BlockGroup;

/**
 * Description: 块组dao 实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:24:21  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class BlockGroupImpl extends BaseDaoImpl<BlockGroup> implements IBlockGroupDao {
	@Override
	public List<BlockGroup> getTreeByParentId(BlockGroup group) {
		return this.selectList("BlockGroupMapper.getTreeByParentId", group);
	}

}
