package com.dangdang.digital.service;


import java.util.List;

import com.dangdang.digital.model.Block;

/**
 * Description: 块模块 Service
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:19:22  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IBlockService extends IBaseService<Block,Long>{
	
	/**
	 * Description: 根据块标识获取块内容
	 * @Version1.0 2014年12月11日 下午5:32:20 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	Block selectContentByCode(String code);
	
	/**
	 * Description: 根据code串获取对应的banner列表
	 * @Version1.0 2015年1月7日 下午10:15:26 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param code
	 * @return
	 */
	List<Block> obtainBlockListByCodes(String codes);
}