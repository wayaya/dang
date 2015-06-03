package com.dangdang.digital.dao;



/**
 * Description: 红包 dao cache 接口
 * All Rights Reserved.
 * @version 1.0  2014年12月30日 上午10:21:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IRedPacketUserCacheDao {
	
	/**
	 * Description: 获取红包剩余数量
	 * @Version1.0 2014年12月30日 上午10:41:42 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param redPacketId
	 * @return
	 */
	Integer getRedPacketLefts(Long redPacketId) throws Exception;
	
	
	/**
	 * Description: 更新红包的剩余次数到缓存
	 * @Version1.0 2014年12月30日 上午11:44:01 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param redPacketId
	 * @param lefts
	 * @throws Exception
	 */
	void setRedPacketLefts(Long redPacketId,Integer lefts) throws Exception;
	
	/**
	 * Description: 清除cache
	 * @Version1.0 2014年12月30日 上午11:44:33 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param redPacketId
	 */
	void deleteRedPacketLefts(Long redPacketId);
	
		
}
