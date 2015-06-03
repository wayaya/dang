package com.dangdang.digital.service;

import com.dangdang.digital.model.ConsumerConsume;

/**
 * 
 * Description: 用户消费记录实体servie接口
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:00:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumerConsumeService extends IBaseService<ConsumerConsume,Long> {

	public ConsumerConsume findWithDetailByConsumerConsumeId(Long consumerConsumeId);
}
