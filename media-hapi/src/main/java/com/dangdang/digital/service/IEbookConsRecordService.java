package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.EbookConsRecord;

/**
 * Description: 电子书金币消费壕记录表的Service接口
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:03:29  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IEbookConsRecordService extends IBaseService<EbookConsRecord,Long>{

	/**
	 * Description: 查询，打赏该书的用户总数
	 * @Version1.0 2014年12月15日 下午7:14:03 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaId 书的id
	 * @param channel 频道 
	 * @return
	 */
	int selectCountEbookConsUser(Long mediaId,String channel);
	
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
	 * Description: 打赏
	 * @Version1.0 2014年12月22日 下午2:26:28 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 */
	 void reward(EbookConsRecord record,String username) throws Exception;
	
}
