package com.dangdang.digital.service;

import java.util.Map;

/**
 * Description:  有关金币活动的 Service 接口
 * All Rights Reserved.
 * @version 1.0  2015年1月10日 下午4:57:32  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface ICoinsActivityService {
	
	
	/**
	 * Description:获取撒金币活动的接口
	 * @Version1.0 2015年1月10日 下午4:58:08 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param username
	 * @param deviceType
	 * @param redPacketId
	 * @return
	 * @throws Exception
	 */
	Map<String,Integer> getSpreadCoins(Long custId,String username,String nickname,String deviceType,int type) throws Exception;
	
	
	/**
	 * Description: 获取该日剩下的领取撒金币的时间戳间隔
	 * @Version1.0 2015年1月12日 下午2:50:43 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @return
	 */
	String getLeftTimesInterval() throws Exception;
	
}
