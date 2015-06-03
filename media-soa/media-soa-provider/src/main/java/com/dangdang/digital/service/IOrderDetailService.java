/**
 * Description: IOrderDetailService.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:07:58  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.OrderDetail;

/**
 * Description: 订单明细实体service接口
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:07:58  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IOrderDetailService extends IBaseService<OrderDetail,Long> {
	
	public List<OrderDetail> findListWithDetailByOrderNo(String orderNO);

}
