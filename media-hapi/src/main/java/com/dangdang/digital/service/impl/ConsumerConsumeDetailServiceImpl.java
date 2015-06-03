/**
 * Description: ConsumerConsumeDetailServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:24:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerConsumeDetailDao;
import com.dangdang.digital.model.ConsumerConsumeDetail;
import com.dangdang.digital.service.IConsumerConsumeDetailService;

/**
 * Description: 用户消费明细实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:24:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ConsumerConsumeDetailServiceImpl extends BaseServiceImpl<ConsumerConsumeDetail,Long>
		implements IConsumerConsumeDetailService {

	@Resource 
	IConsumerConsumeDetailDao consumerConsumeDetailDao;
	
	@Override
	public IBaseDao<ConsumerConsumeDetail> getBaseDao() {
		return this.consumerConsumeDetailDao;
	}

}
