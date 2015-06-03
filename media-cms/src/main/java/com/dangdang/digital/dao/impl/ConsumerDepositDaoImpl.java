package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IConsumerDepositDao;
import com.dangdang.digital.model.ConsumerDeposit;

/**
 * 
 * Description: 用户充值记录dao实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午6:18:59  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class ConsumerDepositDaoImpl extends BaseDaoImpl<ConsumerDeposit> implements
		IConsumerDepositDao {

	@Override
	public Long getUserCountDeposit(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerDepositMapper.selectUserCountDeposit",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTimesDeposit(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerDepositMapper.selectTimesDeposit",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTotalDeposit(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerDepositMapper.selectTotalDeposit",
				map("startDate", startDate, "endDate", endDate));
	}

}
