package com.dangdang.digital.dao;

import java.util.Date;

import com.dangdang.digital.model.RedPacketUser;
/**
 * Description: 红包 dao 接口
 * All Rights Reserved.
 * @version 1.0  2014年12月30日 上午10:21:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IRedPacketUserDao extends IBaseDao<RedPacketUser>{
	
	/**
	 * Description: 获取红包的剩余数量
	 * @Version1.0 2014年12月30日 上午10:41:42 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param redPacketId
	 * @return
	 */
	Integer selectRedPacketLefts(Long redPacketId);
	
	/**
	 * Description: 更新红包user表， 个数-1，增加已经被领钱数的总额
	 * @Version1.0 2014年12月30日 下午2:14:14 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param redPacketId
	 * @param cons
	 */
	void updateGetted(Long redPacketId,int cons);
	
	/**
	 * Description:生成红包 
	 * @Version1.0 2014年12月30日 下午4:18:42 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	Integer insert(RedPacketUser user);
	
	/**
	 * Description: 查询该custid今天分享红包的次数
	 * @Version1.0 2014年12月30日 下午5:09:29 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Integer selectUserTodayShareRedPackets(Long custId,Date startDate,Date endDate);
	
	/**
	 * Description: 通过prizeid 从db中获取红包id
	 * @Version1.0 2015年1月9日 下午3:19:56 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prizeId
	 * @return
	 */
	Long selectRedPacketIdByPrizeIdFromDb(Long prizeId);
	
}
