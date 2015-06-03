package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.OrderDetailChapter;

/**
 * 
 * Description: 订单章节明细dao
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午6:21:25  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IOrderDetailChapterDao extends IBaseDao<OrderDetailChapter>{
	
	public List<OrderDetailChapter> findListByOrderDetailId(Long orderDetailId);

}