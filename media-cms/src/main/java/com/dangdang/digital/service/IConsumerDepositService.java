package com.dangdang.digital.service;

import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.vo.OrderInfoVo;

public interface IConsumerDepositService extends IBaseService<ConsumerDeposit,Long> {

	/**
	 * 
	 * Description: 查询充值订单信息
	 * @Version1.0 2015年1月7日 下午4:11:11 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param depositOrderNo
	 * @return
	 */
	public OrderInfoVo findOrderInfoVoByDepositOrderNo(String depositOrderNo);
	
}
