package com.dangdang.digital.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.CreateConsumeVo;

/**
 * 
 * Description: 用户消费记录实体servie接口
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:00:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumerConsumeService extends IBaseService<ConsumerConsume,Long> {

	public ConsumerConsume findWithDetailByConsumerConsumeId(Long consumerConsumeId);
	
	/**
	 * 
	 * Description: 生成消费记录实体
	 * @Version1.0 2014年11月26日 上午11:48:23 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @return
	 */
	public ConsumerConsume createConsumerConsumeInfo(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception;
	
	/**
	 * 
	 * Description: 创建消费记录时参数校验封装
	 * @Version1.0 2014年11月26日 上午11:54:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @return
	 */
	public boolean checkAndEncapsulateParams(HttpServletRequest request,HttpServletResponse response, ResultSender sender,CreateConsumeVo createConsumeVo);
	
	/**
	 * 
	 * Description: 持久化消费记录实体
	 * @Version1.0 2014年11月26日 下午2:27:37 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consumerConsume
	 * @return
	 * @throws Exception
	 */
	public ConsumerConsume saveConsumerConsumeInfo(ConsumerConsume consumerConsume) throws Exception;
}
