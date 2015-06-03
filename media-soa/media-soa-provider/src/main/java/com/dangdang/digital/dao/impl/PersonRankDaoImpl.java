package com.dangdang.digital.dao.impl;

import java.util.List;


import com.dangdang.digital.dao.IPersonRankDao;
import com.dangdang.digital.model.RankConsToBook;

public class PersonRankDaoImpl extends BaseDaoImpl<RankConsToBook> implements IPersonRankDao{

	@Override
	public List<RankConsToBook> findListForTopAmount(String code, Integer amount) {
//		HashMap<String, Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("code", code);
//		paramMap.put("amount", amount);
		this.selectList("RankConsToBookMapper.findListForTopAmount", map("code", code,"amount", amount));
		return null;
	}
	
	

}
