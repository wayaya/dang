package com.dangdang.digital.service;

import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description:  用户交易相关service
 * All Rights Reserved.
 * @version 1.0  2015年1月4日 上午10:16:29  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IUserService {
	
	/**
	 * 
	 * Description: 用户交易公共方法
	 * @Version1.0 2014年12月3日 下午3:54:23 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param vo
	 * @return
	 */
	public UserTradeBaseVo tradeForUser(UserTradeBaseVo vo) throws Exception; 
	
	/**
	 * 
	 * Description: 生成交易号
	 * @Version1.0 2014年11月25日 下午3:16:03 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param prefix
	 * @return
	 */
	public String createTradeNo(String prefix,Long custId);
}
