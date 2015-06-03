package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.model.ConsumerConsume;

/**
 * 
 * Description: 用户消费记录dao实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月13日 下午6:19:13 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class ConsumerConsumeDaoImpl extends BaseDaoImpl<ConsumerConsume> implements IConsumerConsumeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> selectUserconsumeBooksIds(Long custId) {
		return (List<Long>) getSqlSessionQueryTemplate().selectList("ConsumerConsumeMapper.selectByConsumerConsumeId",
				custId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getOtherConsumeList(Long custId, Integer start, Integer count) {
		return (List<Map<String, Object>>) getSqlSessionQueryTemplate().selectList("ConsumerConsumeMapper.getOtherConsumeList", custId,
				new RowBounds(start, count));
	}
	
	@Override
	public Integer getCount(Map<String, Object> paramMap) {
		try {
			Object result = getSqlSessionTemplate().selectOne("ConsumerConsumeMapper.pageCount", paramMap);
			return (Integer) result;
		} catch (Exception e) {
			return 0;
		}
	}

}
