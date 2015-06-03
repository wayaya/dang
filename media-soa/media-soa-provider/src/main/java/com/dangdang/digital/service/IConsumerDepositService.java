package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dangdang.base.account.client.vo.AccountConsumeItemsVo;
import com.dangdang.digital.model.ConsumerDeposit;

public interface IConsumerDepositService extends IBaseService<ConsumerDeposit,Long> {

	/**
	 * 
	 * Description: 创建充值记录
	 * @Version1.0 2014年12月8日 下午2:41:57 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consumerDeposit
	 * @throws Exception
	 */
	public void createAndSaveDepositInfo(ConsumerDeposit consumerDeposit) throws Exception;
	
	/**
	 * 
	 * Description: 生成充值号
	 * @Version1.0 2014年12月9日 下午6:12:16 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public String createDepositNo(Long custId);
	
	public void createAndSaveDepositOperation(ConsumerDeposit consumerDeposit) throws Exception;
	
	public String mergeUserDeposit(Long oldCustId,Long newCustId,String consumerDepositId);
	
	/**
	 * 
	 * Description: 根据商品id查询订单购买的充值虚拟商品
	 * @Version1.0 2015年4月13日 下午3:23:16 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param productIds
	 * @return
	 */
	public Set<String> getDepositRelationProduct(List<String> productIds);
	
	public void rollbackForDeposit(ConsumerDeposit consumerDeposit);
	
	/**
	 * 
	 * Description: 获取充值明细汇总金额
	 * @Version1.0 2015年5月18日 上午10:08:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param accountConsumes
	 * @return
	 */
	public Map<String, Integer> calculateChangeMoney(List<AccountConsumeItemsVo> accountConsumes);
}
