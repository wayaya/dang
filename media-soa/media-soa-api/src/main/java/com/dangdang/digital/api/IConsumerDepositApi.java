/**
 * Description: IConsumerDepositApi.java
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午2:15:25  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api;

import java.util.List;
import java.util.Set;

import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.vo.CreateDepositVo;
import com.dangdang.digital.vo.DSDepositPayInfoVo;

/**
 * Description: 用户充值接口
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午2:15:25  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumerDepositApi {

	/**
	 * 充值
	 * Description: 
	 * @Version1.0 2014年12月8日 下午2:25:19 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @throws Exception
	 */
	public void createAndSaveConsumerDeposit(ConsumerDeposit consumerDeposit) throws Exception;
	
	/**
	 * 
	 * Description: 充值 （for 扫漏）
	 * @Version1.0 2015年3月27日 下午6:10:35 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param createDepositVo
	 * @throws Exception
	 */
	public void createAndSaveConsumerDepositForMiss(CreateDepositVo createDepositVo) throws Exception;
	
	/**
	 * 
	 * Description: 获取充值选择页信息
	 * @Version1.0 2015年3月18日 下午6:08:02 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param fromPaltform  PayFromPaltform
	 * @return
	 */
	public List<DSDepositPayInfoVo> getDSConsumerDepositPayInfo(String fromPaltform) throws MediaException;
	
	/**
	 * 
	 * Description: 根据需要支付的金额（单位：分）获取对应的虚拟商品id集合
	 * @Version1.0 2015年3月30日 下午6:45:49 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param money
	 * @return
	 * @throws MediaException
	 */
	public List<String> getDSPayRelationProductIdByMoney(Integer money) throws MediaException;
	
	/**
	 * 
	 * Description: 根据商品id查询订单购买的充值虚拟商品
	 * @Version1.0 2015年4月13日 下午2:49:52 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param productIds
	 * @return
	 * @throws MediaException
	 */
	public Set<String> getDepositRelationProduct(List<String> productIds) throws MediaException;
	
	/**
	 * 
	 * Description: 充值回滚接口
	 * @Version1.0 2015年5月15日 下午6:13:30 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param orderNo
	 */
	public void rollbackForDeposit(String orderNo);
}
