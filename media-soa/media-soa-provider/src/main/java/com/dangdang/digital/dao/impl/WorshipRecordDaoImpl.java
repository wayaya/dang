package com.dangdang.digital.dao.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IWorshipRecordDao;
import com.dangdang.digital.dao.impl.BaseDaoImpl;
import com.dangdang.digital.model.WorshipRecord;

/**
 * WorshipRecord DAO.
 */
@Repository
public class WorshipRecordDaoImpl extends BaseDaoImpl<WorshipRecord> implements IWorshipRecordDao{

	@Override
	public List<WorshipRecord> getMyWorShipRecordList(
			Map<String, Object> paramObj) {
		return this.selectList("WorshipRecordMapper.getMyWorShipRecordList", paramObj);
	}

	@Override
	public List<WorshipRecord> getWorShipedMeRecordList(
			Map<String, Object> paramObj) {
		return this.selectList("WorshipRecordMapper.getWorShipedMeRecordList", paramObj);
	}

}