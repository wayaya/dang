package com.dangdang.digital.api;

import java.util.List;

import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.vo.UserRradeInfoVo;

/**
 * Description: 用户相关的api
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午9:42:36  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IUserApi {

	/**
	 * 
	 * Description: 获取用户当前的包月信息
	 * @Version1.0 2014年12月17日 下午2:09:21 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public List<UserMonthly> selectUserMonthly(Long custId); 
	
	/**
	 * 
	 * Description: 合并用户的交易信息
	 * @Version1.0 2015年3月23日 上午10:44:11 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param oldCustId
	 * @param newCustId
	 */
	public void mergeUserTradeInfo(Long oldCustId,Long newCustId) throws MediaException;
	
	/**
	 * 
	 * Description: 回滚合并用户交易信息
	 * @Version1.0 2015年5月11日 下午5:17:38 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param oldCustId
	 * @param newCustId
	 * @throws MediaException
	 */
	public void rollbackForMergeUserTradeInfo(Long oldCustId,Long newCustId) throws MediaException;
	
	/**
	 * 
	 * Description: 查询用户的交易信息
	 * @Version1.0 2015年4月7日 下午3:23:37 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public UserRradeInfoVo findUserTradeInfo(Long custId); 
	
}
