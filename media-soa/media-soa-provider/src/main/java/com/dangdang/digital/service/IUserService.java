/**
 * Description: IUserService.java
 * All Rights Reserved.
 * @version 1.0  2014年12月3日 下午2:54:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.base.account.client.vo.AccountConsumeItemsVo;
import com.dangdang.digital.vo.UserRradeInfoVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 用户交易相关service
 * All Rights Reserved.
 * @version 1.0  2014年12月3日 下午2:54:51  by 张宪斌（zhangxianbin@dangdang.com）创建
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
	 * Description: 合并用户的交易信息
	 * @Version1.0 2015年3月23日 上午10:53:45 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param oldCustId
	 * @param newCustId
	 */
	public void mergeUserTradeInfo(Long oldCustId,Long newCustId);
	
	public void rollbackForMergeUserTradeInfo(Long oldCustId, Long newCustId,String consumerDepositId,String boughtId);
	
	public UserRradeInfoVo findUserTradeInfo(Long custId);
	
	/**
	 * 
	 * Description: 查询消费记录接口
	 * @Version1.0 2014年12月26日 下午12:12:48 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param vo custId 用户ID（不能为空）lastConsumeItemId 上次获取的最小消费明细ID（用于分页） （可为0）consumeType 消费类型 （可为空）
	 * @param platformNo 平台编号 （可为空）
	 * @param sourceOrderNo 外部订单号 （可为空）
	 * @return
	 */
	public List<AccountConsumeItemsVo> queryAccountConsumeItemsList(UserTradeBaseVo vo);
	
}
