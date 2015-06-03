package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.model.ConsumerConsume;

/**
 * 
 * Description: 用户消费记录dao实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月13日 下午6:19:13 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class ConsumerConsumeDaoImpl extends BaseDaoImpl<ConsumerConsume> implements IConsumerConsumeDao {

	@Override
	public Long getUserCountBuyMonthly(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectUserCountBuyMonthly",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTimesBuyMonthly(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectTimesBuyMonthly",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTotalBuyMonthly(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectTotalBuyMonthly",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getUserCountReward(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectUserCountReward",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTimesReward(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectTimesReward",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTotalReward(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectTotalReward",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getUserCountBuyLuckyBag(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectUserCountBuyLuckyBag",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTimesBuyLuckyBag(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectTimesBuyLuckyBag",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTotalCoinBuyLuckyBag(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("ConsumerConsumeMapper.selectTotalCoinBuyLuckyBag",
				map("startDate", startDate, "endDate", endDate));
	}

}
