package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.EbookConsRecord;

/**
 * Description: 电子书金币消费壕记录表的dao接口
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:03:29  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IEbookConsRecordDao extends IBaseDao<EbookConsRecord>{

	/**
	 * Description: 查询，打赏该书的用户总数
	 * @Version1.0 2014年12月15日 下午7:14:03 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaId 书的id
	 * @param channel 频道 
	 * @return
	 */
	int selectCountEbookConsUser (Long mediaId,String channel);
	
	/**
	 * Description: 查询，昨日打赏该书的用户列表
	 * @Version1.0 2014年12月15日 下午7:30:14 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaId
	 * @param start
	 * @param count
	 * @return
	 */
	List<EbookConsRecord> selectYesterdayUserEbookConsByMediaId(Long mediaId,String channel,int start,int count);
	
	/**
	 * Description: 查询，历史打赏该书的用户列表
	 * @Version1.0 2014年12月15日 下午7:30:14 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaId
	 * @param start
	 * @param count
	 * @return
	 */
	List<EbookConsRecord> selectHistoryUserEbookConsByMediaId(Long mediaId,String channel,int count);
	

	/**
	 * Description: 获取用户打赏的书的id(sale_id)列表
	 * @Version1.0 2014年12月6日 上午11:15:30 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<Long> selectUserRewardBooksIds(Long custId,int start,int end);
	
	/**
	 * Description: 获取用户打赏书的总量
	 * @Version1.0 2014年12月13日 下午3:02:46 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param end
	 * @return
	 */
	int selectCountUserRewardBooks(Long custId);
	
}
