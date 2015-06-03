package com.dangdang.digital.service;

import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.model.IOSDepositFail;


/**
 * 
 * Description: IOS充值支付校验失败记录service
 * All Rights Reserved.
 * @version 1.0  2015年3月20日 上午11:13:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IIOSDepositFailService extends IBaseService<IOSDepositFail, Long> {
	
	public void saveFromConsumerDeposit(ConsumerDeposit consumerDeposit,String receiptData);
}
