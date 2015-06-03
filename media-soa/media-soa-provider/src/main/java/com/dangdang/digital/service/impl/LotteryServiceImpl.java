package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ILotteryDao;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.service.IActivityRecordService;
import com.dangdang.digital.service.ILotteryService;

/**
 * Description: 抽奖service接口的实现类
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:34:21  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class LotteryServiceImpl extends BaseServiceImpl<Prize, Long> implements ILotteryService {
	
	@Resource
	ILotteryDao lotteryDao;  
	@Resource
	IActivityUserDao activityUserDao;
	@Resource
	IActivityRecordDao activityRecordDao;
	@Resource
	IActivityRecordService activityRecordService;
	
	
	@Override
	public IBaseDao<Prize> getBaseDao() {
		return lotteryDao;
	}

	@Override
	@Transactional
	public List<Prize> lotPrize(Long custId, String username) throws Exception {
		Prize lottedPrize = new Prize();
		List<Prize> prizeList = new ArrayList<Prize>();
		List<Prize> resultList = new ArrayList<Prize>();
		//获取用的抽奖次数
		ActivityUser user = new ActivityUser();
		user = activityUserDao.selectUserByCustID(custId);
		int chances = user.getLotTimes();
		if(chances<=0){
			//return "times is 0";
		}
		// 判定是否是第一次抽奖
		if(activityRecordDao.judgeFirstLot(custId)){
			//给第一次抽奖的奖励。
			return null;
		}
		//获取有效的奖品list
		prizeList = lotteryDao.getPrizeList(1);
		//获取抽中的奖品
		lottedPrize = getLottedPrize(prizeList);
		//判定日限
		int dayLimit = lottedPrize.getDayLimit();
		if(dayLimit>0){
			int dayPuts = activityRecordDao.selectPrizeTodayPutCount(lottedPrize.getPrizeId());
			if(dayPuts>=dayLimit){
				//超过日限
				return null;
			}
		}
		//判定总限
		int totalLimit = lottedPrize.getTotalLimit();
		if(totalLimit>0){
			int totalPuts = activityRecordDao.selectPrizeTotalPutCount(lottedPrize.getPrizeId());
			if(totalPuts>=totalLimit){
				//超过总限
				return null;
			}
		}
		//增加一条dblog 抽奖记录
		ActivityRecord record = new ActivityRecord(1,1,custId,username,lottedPrize.getPrizeId(),lottedPrize.getPrizeName(),20,lottedPrize.getVestType(),new Date());
		activityRecordService.save(record);
		//更新user信息 减少一次抽奖次数，增加一次使用次数
		activityUserDao.addLotTime(custId, -1,1,username);
	//返回奖品信息
		//增加抽中的奖品
		resultList.add(lottedPrize);
		//增加两个随机的奖品【可用的】
		resultList = addRandomPrize(prizeList,resultList,2);
		return resultList;
	}
	
	/**
	 * Description: 从奖品列表中获取抽中的奖品
	 * @Version1.0 2014年12月8日 上午11:29:29 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prizeList
	 * @return
	 */
	private Prize getLottedPrize(List<Prize> prizeList) {
		Prize  lottedPrize = new Prize();
		double random = Math.random();
		for (Prize prize:prizeList) {
			double value = prize.getProbability();
			random = random - value;
			if (random <= 0) {
				lottedPrize = prize;
				break;
			}
		}
		return lottedPrize;
	}
	
	/**
	 * Description: 增加amouont个随机的奖品
	 * @Version1.0 2014年12月8日 下午5:05:02 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param list
	 * @param amount
	 * @return
	 */
	private List<Prize> addRandomPrize(List<Prize> prizeList,List<Prize> resultlist,int amount){
		int size = prizeList.size();
		if(size<=amount){
			return null;
		}else {
			//生成随机的第一个
			int prize1 = getRandomInteger(prizeList.size());
			resultlist.add(prizeList.get(prize1));
			prizeList.remove(prize1);
			//生成随机的第二个
			int prize2 = getRandomInteger(prizeList.size());
			resultlist.add(prizeList.get(prize1));
			prizeList.remove(prize2);
		}
		return resultlist;
	}

	/**
	 * Description:生成一个随机的正整数 
	 * @Version1.0 2014年12月8日 下午5:16:13 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param max
	 * @return
	 */
	private int getRandomInteger(int max){
		Random random = new Random();
		return random.nextInt(max);
	}
}
