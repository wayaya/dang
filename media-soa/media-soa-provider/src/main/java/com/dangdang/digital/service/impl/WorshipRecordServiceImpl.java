package com.dangdang.digital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.dao.IWorshipRecordDao;
import com.dangdang.digital.service.IWorshipRecordService;
/**
 * WorshipRecord Manager.
 */
@Service("worshipRecordService")
public class WorshipRecordServiceImpl extends BaseServiceImpl<WorshipRecord, Long> implements IWorshipRecordService {

	@Resource
	IWorshipRecordDao dao;
	
	public IBaseDao<WorshipRecord> getBaseDao() {
		return dao;
	}

	@Override
	public List<WorshipRecord> getWorshipRecord(Long custId, String type) {
		Map<String,Object> paramObj = new HashMap<String,Object>();
		if(WorshipRecord.Worship_OF_ME.intValue()==Integer.valueOf(type).intValue()){
			//查询我膜拜过的用户列表
			paramObj.put("cust_id", custId);
			return this.dao.getMyWorShipRecordList(paramObj);
		}else if(WorshipRecord.Worship_TO_ME.intValue()==Integer.valueOf(type).intValue()){
			//查询膜拜过我的用户列表
			paramObj.put("worshiped_cust_id", custId);
			return this.dao.getWorShipedMeRecordList(paramObj);
		}
		return null;
	}
	
}
