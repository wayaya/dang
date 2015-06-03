package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.dao.IConsumerDepositDao;
import com.dangdang.digital.dao.IEbookConsRecordDao;
import com.dangdang.digital.dao.IOrderMainDao;
import com.dangdang.digital.model.RewardTop;
import com.dangdang.digital.service.IStatisticsService;
import com.dangdang.digital.vo.IncomeVo;

/**
 * Description: 数据统计实现类
 * All Rights Reserved.
 * @version 1.0  2015年3月31日 下午2:54:30  by yangzhenping（yangzhenping@dangdang.com）创建
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
	@Resource
	IActivityRecordDao activityRecordDao;
	@Resource
	IConsumerConsumeDao consumerConsumeDao;
	@Resource
	IConsumerDepositDao consumerDepositDao;
	@Resource
	IOrderMainDao orderMainDao;
	@Resource
	IEbookConsRecordDao ebookConsRecordDao;
	@Override
	public IncomeVo getIncomeVo(String startDate, String endDate) {
		IncomeVo incomeVo = new IncomeVo();
		incomeVo.setUserCountSend(activityRecordDao.selectUserCountSend(startDate, endDate));
		incomeVo.setUserCountBuyMonthly(consumerConsumeDao.getUserCountBuyMonthly(startDate, endDate));
		incomeVo.setTimesBuyMonthly(consumerConsumeDao.getTimesBuyMonthly(startDate, endDate));
		incomeVo.setTotalBuyMonthly(consumerConsumeDao.getTotalBuyMonthly(startDate, endDate));
		incomeVo.setUserCountReward(consumerConsumeDao.getUserCountReward(startDate, endDate));
		incomeVo.setTimesReward(consumerConsumeDao.getTimesReward(startDate, endDate));
		incomeVo.setTotalReward(consumerConsumeDao.getTotalReward(startDate, endDate));
		incomeVo.setUserCountDeposit(consumerDepositDao.getUserCountDeposit(startDate, endDate));
		incomeVo.setTimesDeposit(consumerDepositDao.getTimesDeposit(startDate, endDate));
		incomeVo.setTotalDeposit(consumerDepositDao.getTotalDeposit(startDate, endDate));
		incomeVo.setUserCountBuyChapter(orderMainDao.getUserCountBuyChapter(startDate, endDate));
		incomeVo.setTimesBuyChapter(orderMainDao.getTimesBuyChapter(startDate, endDate));
		incomeVo.setTotalGoldBuyChapter(orderMainDao.getTotalGoldBuyChapter(startDate, endDate));
		incomeVo.setTotalSilverBuyChapter(orderMainDao.getSilverBuyChapter(startDate, endDate));
		incomeVo.setUserCountBuyLuckyBag(consumerConsumeDao.getUserCountBuyLuckyBag(startDate, endDate));
		incomeVo.setTimesBuyLuckyBag(consumerConsumeDao.getTimesBuyLuckyBag(startDate, endDate));
		incomeVo.setTotalCoinBuyLuckyBag(consumerConsumeDao.getTotalCoinBuyLuckyBag(startDate, endDate));
		incomeVo.setUserCountLottery(activityRecordDao.selectUserCountLottery(startDate, endDate));
		incomeVo.setTimesLottery(activityRecordDao.selectTimesLottery(startDate, endDate));
		incomeVo.setTotalSilverLottery(activityRecordDao.selectTotalSilverLottery(startDate, endDate));
		incomeVo.setUserCountWorship(activityRecordDao.selectUserCountWorship(startDate, endDate));
		incomeVo.setTimesWorship(activityRecordDao.selectTimesWorship(startDate, endDate));
		incomeVo.setTotalSilverWorship(activityRecordDao.selectTotalSilverWorship(startDate, endDate));
		incomeVo.setUserCountSpreadCoins(activityRecordDao.selectUserCountSpreadCoins(startDate, endDate));
		incomeVo.setTimesSpreadCoins(activityRecordDao.selectTimesSpreadCoins(startDate, endDate));
		incomeVo.setTotalSilverSpreadCoins(activityRecordDao.selectTotalSilverSpreadCoins(startDate, endDate));
		return incomeVo;
	}
	@Override
	public List<RewardTop> getRewardTop(String startDate, String endDate, Integer count) {
		return ebookConsRecordDao.getRewardTop(startDate, endDate, count);
	}
	@Override
	public List<RewardTop> getBuyChapterGlobTop(String startDate, String endDate, Integer count) {
		return ebookConsRecordDao.getBuyChapterGlobTop(startDate, endDate, count);
	}
	@Override
	public List<RewardTop> getBuyChapterSilverTop(String startDate, String endDate, Integer count) {
		return ebookConsRecordDao.getBuyChapterSilverTop(startDate, endDate, count);
	}

}
