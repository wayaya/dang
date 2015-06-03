package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IOrderDetailChapterDao;
import com.dangdang.digital.model.OrderDetailChapter;

/**
 * 
 * Description: 订单章节明细dao实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午6:18:38  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class OrderDetailChapterDaoImpl extends BaseDaoImpl<OrderDetailChapter> implements
		IOrderDetailChapterDao {

	@Override
	public List<OrderDetailChapter> findListByOrderDetailId(Long orderDetailId) {
		return this.selectList("OrderDetailChapterMapper.getOrderDetailChaptersListByOrderDetailId", orderDetailId);
	}

}
