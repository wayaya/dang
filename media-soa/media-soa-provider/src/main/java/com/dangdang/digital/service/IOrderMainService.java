/**
 * Description: IOrderMainService.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:10:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.OrderQueryVo;

/**
 * Description: 主订单实体service接口
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:10:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IOrderMainService extends IBaseService<OrderMain,Long> {

	/**
	 * 
	 * Description: OrderQueryVo分页查询
	 * @Version1.0 2014年11月17日 下午3:19:21 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @param query
	 * @return
	 */
	public PageFinder<OrderMain> findPageFinderByOrderQueryVo(OrderQueryVo vo, Query query);
	
	/**
	 * 
	 * Description: OrderQueryVo查询
	 * @Version1.0 2014年11月17日 下午3:19:49 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public List<OrderMain> findListByOrderQueryVo(OrderQueryVo vo);
	
	/**
	 * 
	 * Description: OrderQueryVo查询
	 * @Version1.0 2014年11月17日 下午3:20:12 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param params
	 * @return
	 */
	public OrderMain findUniqueByOrderQueryVo(OrderQueryVo vo);
	
	/**
	 * 
	 * Description: 生成订单号
	 * @Version1.0 2014年11月25日 下午3:16:03 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param platform
	 * @return
	 */
	public String createOrderNo(String platform,Long custId);
	
	/**
	 * 
	 * Description: 封装订单数据
	 * @Version1.0 2014年11月26日 上午11:48:23 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @return
	 */
	public OrderMain encapsulateOrderInfo(CreateOrderVo createOrderVo) throws Exception;
	
	/**
	 * 
	 * Description: 创建订单时参数校验
	 * @Version1.0 2014年11月26日 上午11:54:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @return
	 */
	public boolean checkParamsForCreateOrder(CreateOrderVo createOrderVo);
	
	/**
	 * 
	 * Description: 持久化订单实体
	 * @Version1.0 2014年11月26日 下午2:27:37 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param orderMain
	 * @return
	 * @throws Exception
	 */
	public OrderMain saveOrderInfo(CreateOrderVo createOrderVo) throws Exception;
	
	public UserAuthority createAndSaveOrderOperation(CreateOrderVo createOrderVo) throws Exception;
}
