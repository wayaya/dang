package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.BlockGroup;

/**
 * Description: 块组的service接口
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 下午2:53:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IBlockGroupDao extends IBaseDao<BlockGroup>{
	/**获取树节点
	 * @param id
	 * @return
	 */
	public List<BlockGroup> getTreeByParentId(BlockGroup group);
}