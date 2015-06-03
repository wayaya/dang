/**
 * Description: OrderDetailServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:55:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IOrderDetailChapterDao;
import com.dangdang.digital.dao.IOrderDetailDao;
import com.dangdang.digital.model.OrderDetail;
import com.dangdang.digital.service.IOrderDetailService;

/**
 * Description: 订单明细实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:55:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail,Long> implements
		IOrderDetailService {
	
	@Resource
	IOrderDetailDao orderDetailDao;
	@Resource
	IOrderDetailChapterDao orderDetailChapterDao;
	
	@Override
	public IBaseDao<OrderDetail> getBaseDao() {
		return this.orderDetailDao;
	}

	@Override
	public List<OrderDetail> findListWithDetailByOrderNo(String orderNO) {
		List<OrderDetail> details = orderDetailDao.selectList("OrderDetailMapper.getOrderDetailsListByOrderNo", orderNO);
		if(details != null && !details.isEmpty()){
			for(OrderDetail detail : details){
				detail.setOrderDetailChapters(orderDetailChapterDao.findListByOrderDetailId(detail.getOrderDetailId()));
			}
		}
		return details;
	}

}
