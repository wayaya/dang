package com.dangdang.digital.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.IOSDepositFailStatusEnum;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IIOSDepositFailDao;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.model.IOSDepositFail;
import com.dangdang.digital.service.IIOSDepositFailService;
/**
 * 
 * Description: IOS充值支付校验失败记录service实现类
 * All Rights Reserved.
 * @version 1.0  2015年3月20日 上午11:12:16  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class IOSDepositFailServiceImpl extends BaseServiceImpl<IOSDepositFail, Long> implements IIOSDepositFailService {

	@Resource
	IIOSDepositFailDao dao;
	
	public IBaseDao<IOSDepositFail> getBaseDao() {
		return dao;
	}

	@Override
	public void saveFromConsumerDeposit(ConsumerDeposit consumerDeposit,String receiptData) {
		this.save(this.convertFromConsumerDeposit(consumerDeposit,receiptData));		
	}
	
	private IOSDepositFail convertFromConsumerDeposit(ConsumerDeposit consumerDeposit,String receiptData){
		IOSDepositFail iosDepositFail = new IOSDepositFail();
		iosDepositFail.setCreationDate(new Date());
		iosDepositFail.setCustId(consumerDeposit.getCustId());
		iosDepositFail.setDepositOrderNo(consumerDeposit.getDepositOrderNo());
		iosDepositFail.setDeviceType(consumerDeposit.getDeviceType());
		iosDepositFail.setFromPaltform(consumerDeposit.getFromPaltform());
		iosDepositFail.setGiveMainGold(consumerDeposit.getGiveMainGold());
		iosDepositFail.setMainGold(consumerDeposit.getMainGold());
		iosDepositFail.setMoney(consumerDeposit.getMoney());
		iosDepositFail.setOperateCount(0);
		iosDepositFail.setPayment(consumerDeposit.getPayment());
		iosDepositFail.setPayTime(consumerDeposit.getPayTime());
		iosDepositFail.setProductCount(consumerDeposit.getProductCount());
		iosDepositFail.setReceiptData(receiptData);
		iosDepositFail.setRelationProductId(consumerDeposit.getRelationProductId());
		iosDepositFail.setStatus(Short.valueOf(IOSDepositFailStatusEnum.UNTREATED.getKey() + ""));
		iosDepositFail.setSubGold(consumerDeposit.getSubGold());
		iosDepositFail.setUserName(consumerDeposit.getUserName());
		return iosDepositFail;
	}
	
}
