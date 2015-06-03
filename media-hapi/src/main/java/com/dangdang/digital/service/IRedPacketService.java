package com.dangdang.digital.service;

import java.util.Map;

/**
 * Description: 红包 Service 接口
 * All Rights Reserved.
 * @version 1.0  2014年12月30日 上午10:21:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IRedPacketService {
	
	
	/**
	 * Description: 获取红包
	 * @Version1.0 2014年12月30日 上午11:25:30 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	Map<String,Integer> getRedPacket(Long custId,String username,String nickname,String deviceType,Long redPacketId) throws Exception;
	
	/**
	 * Description: 生成红包
	 * @Version1.0 2014年12月30日 下午4:17:51 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	String creatRedPacket(Long custId,Long prizeId) throws Exception;
	
	
	/**
	 * Description: 判定红包是否有数量
	 * @Version1.0 2015年1月7日 下午3:34:03 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param redPacketId
	 * @return
	 * @throws Exception
	 */
	Integer getRedPacketLeft(Long redPacketId) throws Exception;
	
	/**
	 * Description: 获取用户获取该红包的钱
	 * @Version1.0 2015年2月5日 下午4:13:53 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param redPacketId
	 * @return
	 */
	Integer getUserGettedRedPacketCoins(Long custId,Long redPacketId);
}
