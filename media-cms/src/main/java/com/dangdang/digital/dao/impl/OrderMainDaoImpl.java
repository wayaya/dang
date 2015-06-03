package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IOrderMainDao;
import com.dangdang.digital.model.OrderMain;

/**
 * 
 * Description: 主订单dao实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午6:18:16  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class OrderMainDaoImpl extends BaseDaoImpl<OrderMain> implements
		IOrderMainDao {

	@Override
	public Long getUserCountBuyChapter(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("OrderMainMapper.selectUserCountBuyChapter",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTimesBuyChapter(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("OrderMainMapper.selectTimesBuyChapter",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getTotalGoldBuyChapter(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("OrderMainMapper.selectTotalGoldBuyChapter",
				map("startDate", startDate, "endDate", endDate));
	}

	@Override
	public Long getSilverBuyChapter(String startDate, String endDate) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("OrderMainMapper.selectSilverBuyChapter",
				map("startDate", startDate, "endDate", endDate));
	}

}
