/**
 * Description: ConsumerConsumeServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:26:25  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.service.IConsumerConsumeDetailService;
import com.dangdang.digital.service.IConsumerConsumeService;

/**
 * Description: 用户消费记录实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:26:25  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ConsumerConsumeServiceImpl extends BaseServiceImpl<ConsumerConsume,Long> implements
		IConsumerConsumeService {

	@Resource
	IConsumerConsumeDao consumerConsumeDao;
	@Resource
	IConsumerConsumeDetailService consumerConsumeDetailService;
	
	@Override
	public IBaseDao<ConsumerConsume> getBaseDao() {
		return this.consumerConsumeDao;
	}

	@Override
	public ConsumerConsume findWithDetailByConsumerConsumeId(
			Long consumerConsumeId) {
		ConsumerConsume consume = this.get(consumerConsumeId);
		if(consume != null && consume.getConsumerConsumeId() != null){
			consume.setConsumeDetails(consumerConsumeDetailService.findListByParams("consumerConsumeId",consume.getConsumerConsumeId()));
		}
		return consume;
	}

}
