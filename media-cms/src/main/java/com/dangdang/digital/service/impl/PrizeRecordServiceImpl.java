package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPrizeRecordDao;
import com.dangdang.digital.model.PrizeRecord;
import com.dangdang.digital.service.IPrizeRecordService;

/**
 * Description: 抽奖记录Service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:24:02  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class PrizeRecordServiceImpl extends BaseServiceImpl<PrizeRecord,Long> implements IPrizeRecordService {

	@Resource
	IPrizeRecordDao prizeRecordDao;
	
	@Override
	public IBaseDao<PrizeRecord> getBaseDao() {
		// TODO Auto-generated method stub
		return prizeRecordDao;
	}

}
