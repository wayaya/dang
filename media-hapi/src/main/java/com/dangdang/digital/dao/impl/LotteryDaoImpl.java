package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ILotteryDao;
import com.dangdang.digital.model.Prize;

@Repository
public class LotteryDaoImpl extends BaseDaoImpl<Prize> implements ILotteryDao{


	@Override
	public List<Prize> getPrizeList(int vestType) {
		return selectList("PrizeMapper.selectByVestType", vestType);
	}
}
