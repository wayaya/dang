package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Block;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.vo.ReturnActivityUserVo;
import com.dangdang.digital.vo.ReturnActivityVo;
import com.dangdang.digital.vo.ReturnEbookConsVo;
import com.dangdang.digital.vo.ReturnPrizeVo;
import com.dangdang.digital.vo.ReturnRankConsVo;

/**
 * Description: hapi cache
 * All Rights Reserved.
 * @version 1.0  2014年12月30日 上午11:31:33  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface ICacheService {
	/**
	 * Description: 从cache中通过归属类型，获取奖品列表
	 * 
	 * @Version1.0 2014年12月23日 下午5:06:28 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 * @return
	 */
	List<Prize> getPrizeListCache(int vestType);

	/**
	 * Description: 从cache 中获取今天存放的活动状态：是否膜拜超次数，分享超次数等。。
	 * 
	 * @Version1.0 2014年12月24日 上午11:10:10 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	// Map<String,String> getTodayActivityUserCache(Long custId);

	/**
	 * Description: 从cache中获取书城个人中心页数据
	 * 
	 * @Version1.0 2014年12月24日 上午11:16:04 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	ReturnActivityUserVo getActivityUserCache(Long custId) throws Exception;

	/**
	 * Description: 从cache中获取块
	 * 
	 * @Version1.0 2014年12月24日 上午11:21:26 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	Block getBlockCache(String code);

	/**
	 * Description: 从cache中获取壕赏榜，榜单 [日，周，月]
	 * 
	 * @Version1.0 2014年12月24日 上午11:24:21 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	List<ReturnRankConsVo> getRankConsListCache(String code) throws Exception;
	
	

	/**
	 * Description: 从cache中获取壕赏榜，榜单  总榜
	 * 
	 * @Version1.0 2014年12月24日 上午11:24:21 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	List<ReturnRankConsVo> getRewardTotalRankCache() throws Exception;

	/**
	 * Description: 从cache中获取单品的打赏信息
	 * 
	 * @Version1.0 2014年12月24日 上午11:28:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaId
	 * @return
	 */
	List<ReturnEbookConsVo> getEbookRewardedUsersCache(Long mediaId,
			String channel) throws Exception;

	/**
	 * Description: 从cache中获取用户的活动参与信息
	 * 
	 * @Version1.0 2014年12月26日 下午2:37:44 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param type
	 * @return
	 */
	List<ReturnActivityVo> getUserActivityRecordCache(Long custId, int type,int prizeType);

	/**
	 * Description: 从cache中获取用户的活动参与信息
	 * 
	 * @Version1.0 2014年12月26日 上午10:30:12 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<ReturnPrizeVo> getUserLotteryRecordCache(Long custId);

	/**
	 * Description: 从cache中,根据活动类型获取前100的活动参与信息（比如中奖名单）
	 * 
	 * @Version1.0 2014年12月26日 下午4:02:02 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prizeType
	 * @return
	 */
	List<ReturnActivityVo> getLatestActivityRecord(int activityType);

	/**
	 * Description: 从cache 中获取用户打赏的书ids
	 * 
	 * @Version1.0 2014年12月26日 下午5:40:40 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custID
	 * @return
	 */
	List<Long> getUserRewardSaleIdsCache(Long custId);

	/**
	 * Description: 从cache 中获取用户账户信息
	 * 
	 * @Version1.0 2014年12月27日 下午6:47:48 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	// ReturnUserVo getUserCache(Long custId);
	
	/**
	 * Description: 判断是否是首次领首次送包月   1：需要弹框
	 * @Version1.0 2015年1月5日 下午3:26:30 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	String judgeFirstLoginPushMonthlyFlagCache(Long custId);
}
