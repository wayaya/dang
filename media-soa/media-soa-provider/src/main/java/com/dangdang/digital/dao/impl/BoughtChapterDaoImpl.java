package com.dangdang.digital.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IBoughtChapterDao;
import com.dangdang.digital.model.BoughtChapter;

/**
 * 
 * Description: 已购章节信息dao实现 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:46:55 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Repository
public class BoughtChapterDaoImpl extends BaseDaoImpl<BoughtChapter> implements IBoughtChapterDao {


	@Override
	public Integer getCount(Map<String, Object> paramMap) {
		try {
			Object result = getSqlSessionTemplate().selectOne("BoughtChapterMapper.pageCount", paramMap);
			return (Integer) result;
		} catch (Exception e) {
			return 0;
		}
	}
}
