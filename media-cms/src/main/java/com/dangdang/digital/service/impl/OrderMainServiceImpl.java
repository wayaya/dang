/**
 * Description: OrderMainServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:59:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IOrderMainDao;
import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.vo.OrderQueryVo;

/**
 * Description: 主订单实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:59:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class OrderMainServiceImpl extends BaseServiceImpl<OrderMain,Long> implements
		IOrderMainService {
	
	@Resource
	IOrderMainDao orderMainDao;
	
	@Override
	public IBaseDao<OrderMain> getBaseDao() {
		return this.orderMainDao;
	}

	@Override
	public PageFinder<OrderMain> findPageFinderByOrderQueryVo(OrderQueryVo vo,
			Query query) {
		Map<String, Object> map = convertBeanToMap(vo);
		if(!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName") && !map.containsKey("mediaName") && !map.containsKey("chapterNo")){
			return this.findPageFinderObjs(map, query);
		}
		return orderMainDao.getPageFinderObjs(map, query, "OrderMainMapper.pageCountByOrderQuery", "OrderMainMapper.pageDataByOrderQuery");
	}

	@Override
	public List<OrderMain> findListByOrderQueryVo(OrderQueryVo vo) {
		Map<String, Object> map = convertBeanToMap(vo);
		if(!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName") && !map.containsKey("mediaName") && !map.containsKey("chapterNo")){
			return this.findListByParamsObjs(map);
		}
		return orderMainDao.selectList("OrderMainMapper.getAllByOrderQuery", map);
	}

	@Override
	public OrderMain findUniqueByOrderQueryVo(OrderQueryVo vo) {
		Map<String, Object> map = convertBeanToMap(vo);
		if(!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName") && !map.containsKey("mediaName") && !map.containsKey("chapterNo")){
			return this.findUniqueByParamsObjs(map);
		}
		return orderMainDao.selectOne("OrderMainMapper.getAllByOrderQuery", map);
	}

}
