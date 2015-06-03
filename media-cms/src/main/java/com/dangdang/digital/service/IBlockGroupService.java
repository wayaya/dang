package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.BlockGroup;

/**
 * Description: 块组 Service 接口
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:26:06  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IBlockGroupService extends IBaseService<BlockGroup,Long>{
	
	/**获取树节点
	 * @param id
	 * @return
	 */
	public List<BlockGroup> getTreeByParentId(BlockGroup group);
}