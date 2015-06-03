package com.dangdang.digital.service;

import java.util.Map;

import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.vo.ActivityVo;

/**
 * Description: 活动模块的用户表Service接口
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:17:48  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityUserService extends IBaseService<ActivityUser, Long>{
		

	/**
	 * Description:分享
	 * @Version1.0 2014年12月3日 上午10:12:36 by 周伟洋（zhouweiyang@dangdang.com）创建
	 */
	String share(ActivityVo activityVo) throws Exception;
	
	
	/**
	 * Description: 获取用户的活动用户信息（壕赏榜的信息，以及头像信息）
	 * @Version1.0 2014年12月3日 上午10:13:48 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @return
	 */
	ActivityUser selectActivityUserInfo(Long custId);
	
	
	/**
	 * Description: 从主库获取用户的活动用户信息（壕赏榜的信息，以及头像信息）
	 * @Version1.0 2015年1月10日 下午7:34:53 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	ActivityUser selectActivityUserInfoFromMaster(Long custId);
	
	
	/**
	 * Description: 膜拜
	 * @Version1.0 2014年12月3日 上午11:34:57 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @throws Exception 
	 */
	Map<String, String> worship(ActivityVo activityVo) throws Exception;
	
	
	/**
	 * Description: 增加福袋次数
	 * @Version1.0 2015年1月5日 下午2:26:27 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param activityVo
	 */
	int sendLotTimes(ActivityVo activityVo) throws Exception;

	/**
	 * 
	 * Description: 福袋转账
	 * @Version1.0 2015年3月19日 下午2:20:42 by 魏嵩（weisong@dangdang.com）创建
	 * @param custId
	 * @param receiver
	 * @param amount
	 */
	void transferLuckyPacket(Long custId, Long receiver, int amount) throws MediaException;
	
}
