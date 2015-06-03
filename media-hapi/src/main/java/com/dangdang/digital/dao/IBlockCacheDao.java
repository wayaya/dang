package com.dangdang.digital.dao;


import com.dangdang.digital.model.Block;

/**
 * Description: 块模块 dao
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:19:22  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IBlockCacheDao {
	
	/**
	 * Description: 从cache中获取block
	 * @Version1.0 2014年12月25日 上午11:56:05 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	Block getBlockCache(String code);
	
	/**
	 * Description: 保存block 到cache
	 * @Version1.0 2014年12月25日 上午11:56:20 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param block
	 */
	void setBlockCache(String code,Block block);
	
	/**
	 * Description: 删除cache中的block 
	 * @Version1.0 2014年12月25日 上午11:56:31 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 */
	void deleteBlockCache(String code);
}