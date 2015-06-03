package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPrizeDao;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.service.IPrizeService;

/**
 * Description: 福袋奖品配置Service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:23:12  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class PrizeServiceImpl extends BaseServiceImpl<Prize,Long> implements IPrizeService {

	@Resource
	IPrizeDao prizeDao;
	
	@Override
	public IBaseDao<Prize> getBaseDao() {
		return prizeDao;
	}

	@Override
	public double getTotalPro(int vestType) {
		return prizeDao.selectTotalPro(vestType);
	}

	@Override
	public Integer getPrizeAmounts(int vestType) {
		return prizeDao.selectPrizeAmounts(vestType);
	}

}
