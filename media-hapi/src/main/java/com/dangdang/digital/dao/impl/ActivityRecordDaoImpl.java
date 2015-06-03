package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.model.ActivityRecord;

/**
 * Description: 活动参与记录 dao接口实现类 
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午2:34:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class ActivityRecordDaoImpl extends BaseDaoImpl<ActivityRecord> implements IActivityRecordDao{

	@Override
	public int selectUserShareTimes(Long custId,int activityType, Date startDate,
			Date endDate) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.pageCount", map("custId",custId,"activityType",activityType,"creationDateStart",startDate,"creationDateEnd",endDate));
	}

	@Override
	public int selectTodayWorshipTimes(Long custId) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.selectTodayWorshipTimes", custId);
	}

	@Override
	public int selectUserParticipateTimesByType(Long custId,
			int activityType, Date startDate, Date endDate) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.selectCountByCustId", map("custId",custId,"activityType",activityType,"creationDateStart",startDate,"creationDateEnd",endDate));
	}

	@Override
	public List<ActivityRecord> selectUserActivityRecord(Long custId,Integer activityType,Integer prizeType,int start,int count) {
		if(0==prizeType){
			return this.selectList("ActivityRecordMapper.selectByCustId",map("custId",custId,"activityType",activityType,"start",start,"count",count));
		}
		return this.selectList("ActivityRecordMapper.selectByCustId",map("custId",custId,"activityType",activityType,"prizeType",prizeType,"start",start,"count",count));
		
	}

	@Override
	public List<ActivityRecord> selectLatestActivityRecord(int activityType,
			int amount) {
		if(0>=activityType){
			return this.selectList("ActivityRecordMapper.selectByCustId",map("amount",amount));
		}else {
			return this.selectList("ActivityRecordMapper.selectByActivityType",map("activityType",activityType,"amount",amount));
		}
	}

	@Override
	public int selectPrizeTodayPutCount(Long prizeId) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.prizeTodayPutCount", prizeId);
	}

	@Override
	public int selectPrizeTotalPutCount(Long prizeId) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.prizeTotalPutCount", prizeId);
	}

	@Override
	public boolean judgeFirstLot(Long custId) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.selectCountByCustId", map("custId",custId))<=0;
	}

	@Override
	public int selectUserActivityRecordCount(Long custId, int activityType) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.selectUserActivityRecordCount", map("custId",custId,"activityType",activityType));
	}

	@Override
	public void insert(ActivityRecord record) {
		insert("ActivityRecordMapper.insertSelective",record);
	}

	@Override
	public ActivityRecord selectById(Long recordId) {
		return selectOne("ActivityRecordMapper.selectByPrimaryKey", recordId);
	}

	@Override
	public ActivityRecord selectTodayGetSpreadByType(Long custId, int prizeType) {
		return selectOne("ActivityRecordMapper.selectTodayGetSpreadByType",map("custId",custId,"prizeType",prizeType) );
	}
	
	@Override
	public Integer selectGettedCoinsByRedPacketId(Long custId, Long redPacketId) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("ActivityRecordMapper.selectGettedCoinsByRedPacketId", map("custId",custId,"prize_id",redPacketId));
	}
	

}
