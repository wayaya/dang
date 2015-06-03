package com.dangdang.digital.dao;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.WorshipRecord;

public interface IWorshipRecordDao extends IBaseDao<WorshipRecord> {
	
	/**
	 * 
	 * Description: 获取我膜拜过的用户列表
	 * @Version1.0 2015年3月16日 下午7:11:27 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param paramObj
	 * @return
	 */
	List<WorshipRecord> getMyWorShipRecordList(Map<String,Object> paramObj);
	
	/**
	 * 
	 * Description: 获取膜拜过我的用户列表
	 * @Version1.0 2015年3月16日 下午7:12:02 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param paramObj
	 * @return
	 */
	List<WorshipRecord> getWorShipedMeRecordList(Map<String,Object> paramObj);
}