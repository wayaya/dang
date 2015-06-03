package com.dangdang.digital.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.RankConsToBook;
import com.dangdang.digital.service.IPersonRankService;

@Service
public class PersonRankServiceImpl extends BaseServiceImpl<RankConsToBook, Long> implements
		IPersonRankService {

	@Override
	public List<RankConsToBook> findListForTopAmount(String code, Integer amount) {
		return null;
	}

	@Override
	public IBaseDao<RankConsToBook> getBaseDao() {
		return null;
	}


}
