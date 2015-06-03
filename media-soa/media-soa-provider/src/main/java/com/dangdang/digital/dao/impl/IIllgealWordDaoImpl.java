package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IIllWordDao;
import com.dangdang.digital.model.IllWord;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Repository
public class IIllgealWordDaoImpl extends BaseDaoImpl<IllWord> implements IIllWordDao {

	public IllWord getIllWordById(Integer id) {
		// TODO Auto-generated method stub
		return  this.selectOne("IllWordMapper.selectByPrimaryKey", id);

	}

	public List<IllWord> getIllWordList() {
		return  this.selectList("IllWordMapper.getAll");
	}


	@Override
	public PageFinder<IllWord> getIllWordList(IllWord illWord, Query query) {
		return this.getPageFinderObjs(illWord, query, "IllWordMapper.selectCount", "IllWordMapper.pageData");
	}


}
