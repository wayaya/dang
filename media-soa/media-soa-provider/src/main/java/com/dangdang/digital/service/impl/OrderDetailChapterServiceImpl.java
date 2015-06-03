/**
 * Description: OrderDetailChapterServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:50:44  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IOrderDetailChapterDao;
import com.dangdang.digital.model.OrderDetailChapter;
import com.dangdang.digital.service.IOrderDetailChapterService;

/**
 * Description: 订单章节明细实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:50:44  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class OrderDetailChapterServiceImpl extends BaseServiceImpl<OrderDetailChapter,Long> implements
		IOrderDetailChapterService {
	
	@Resource
	IOrderDetailChapterDao orderDetailChapterDao;
	@Override
	public IBaseDao<OrderDetailChapter> getBaseDao() {
		return this.orderDetailChapterDao;
	}
	@Override
	public int batchInsert(List<OrderDetailChapter> detailChapters) {
		return orderDetailChapterDao.insert("OrderDetailChapterMapper.insertbatch", detailChapters);
	}

}
