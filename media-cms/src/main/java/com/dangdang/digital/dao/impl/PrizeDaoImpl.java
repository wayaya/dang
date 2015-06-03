package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IPrizeDao;
import com.dangdang.digital.model.Prize;

/**
 * Description: 奖品配置dao实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:23:12  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class PrizeDaoImpl extends BaseDaoImpl<Prize> implements IPrizeDao {

	@Override
	public double selectTotalPro(int vestType) {
		return (Double)getSqlSessionQueryTemplate().selectOne("PrizeMapper.selectSumProByVestType", vestType);
	}

	@Override
	public Integer selectPrizeAmounts(int vestType) {
		return (Integer)getSqlSessionQueryTemplate().selectOne("PrizeMapper.selectPrizeAmountVestType", vestType);
	}

}
