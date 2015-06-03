package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.vo.CreateConsumeVo;

/**
 * 
 * Description: 用户消费记录实体servie接口 All Rights Reserved.
 * 
 * @version 1.0 2014年11月14日 上午10:00:32 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumerConsumeService extends IBaseService<ConsumerConsume, Long> {

	public ConsumerConsume findWithDetailByConsumerConsumeId(Long consumerConsumeId);

	/**
	 * 
	 * Description: 封装消费记录数据
	 * 
	 * @Version1.0 2014年11月26日 上午11:48:23 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @return
	 */
	public ConsumerConsume encapsulateConsumerConsumeInfo(CreateConsumeVo createConsumeVo) throws Exception;

	/**
	 * 
	 * Description: 创建消费记录时参数校验
	 * 
	 * @Version1.0 2014年11月26日 上午11:54:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @return
	 */
	public boolean checkParamsForCreateConsumerConsume(ConsumerConsume consumerConsume);

	/**
	 * 
	 * Description: 持久化消费记录实体
	 * 
	 * @Version1.0 2014年11月26日 下午2:27:37 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consumerConsume
	 * @return
	 * @throws Exception
	 */
	public ConsumerConsume saveConsumerConsumeInfo(CreateConsumeVo createConsumeVo) throws Exception;

	/**
	 * 
	 * Description: 生成消费流水号
	 * 
	 * @Version1.0 2014年12月9日 下午6:19:26 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public String createConsumeSerialNo(Long custId);

	public void createAndSaveConsumeOperation(CreateConsumeVo createConsumeVo) throws Exception;

	/**
	 * 
	 * Description: 获取其它消费列表
	 * 
	 * @Version1.0 2015年1月5日 上午9:40:40 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Map<String, Object>> getOtherConsumeList(Long custId, Integer start, Integer count);

	/**
	 * 
	 * Description:根据custId获取消费数量
	 * 
	 * @Version1.0 2015年1月5日 下午2:58:33 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public Integer getOtherConsumeCount(Long custId);

}
