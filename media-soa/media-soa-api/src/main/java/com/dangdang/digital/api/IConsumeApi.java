/**
 * Description: IConsumeApi.java
 * All Rights Reserved.
 * @version 1.0  2014年11月29日 下午5:01:35  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.vo.ConsumerConsumeShowVo;
import com.dangdang.digital.vo.CreateConsumeVo;

/**
 * Description: 消费接口
 * All Rights Reserved.
 * @version 1.0  2014年11月29日 下午5:01:35  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumeApi {

	/**
	 * 
	 * Description: 创建消费记录
	 * @Version1.0 2014年11月29日 下午5:06:00 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param createConsumeVo
	 * @return
	 * @throws Exception
	 */
	public CreateConsumeVo createAndSaveConsume(CreateConsumeVo createConsumeVo) throws Exception;
	
	/**
	 * 
	 * Description: 获取用户购买包月页面数据
	 * @Version1.0 2014年12月31日 上午11:16:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public ConsumerConsumeShowVo getConsumerConsumeShowVoByCustId(Long custId,String buyFlag,String userName) throws Exception;

	/**
	 * 
	 * Description: 获取其它消费列表
	 * @Version1.0 2015年1月5日 上午9:40:40 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param count
	 * @return  consumeType 消费类型
	 * 			consumeContent 消费内容
	 * 			consumeTime 消费时间
	 * 			mainPrice 主账户消费金额
	 * 			subPrice 子账户消费金额			
	 * 
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
