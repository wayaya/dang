package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IEbookConsRecordDao;
import com.dangdang.digital.model.EbookConsRecord;

/**
 * Description: 电子书金币消费壕记录表的 dao接口 实现类
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:06:37  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class EbookConsRecordDaoImpl extends BaseDaoImpl<EbookConsRecord> implements IEbookConsRecordDao {

	@Override
	public int selectCountEbookConsUser(Long mediaId,String channel) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("EbookConsRecordMapper.selectListCount",map("mediaId",mediaId,"channel",channel));
	}

	@Override
	public List<EbookConsRecord> selectYesterdayUserEbookConsByMediaId(Long mediaId,String channel,
			int start, int count) {
		return this.selectList("EbookConsRecordMapper.selectLimitList",map("mediaId",mediaId,"channel",channel,"start",start,"count",count));
	}
	
	@Override
	public List<EbookConsRecord> selectHistoryUserEbookConsByMediaId(Long mediaId,String channel,int count) {
		return this.selectList("EbookConsRecordMapper.selectHistoryLimitList",map("mediaId",mediaId,"channel",channel,"count",count));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> selectUserRewardBooksIds(Long custId,int start,int count) {
		return (List<Long>) super.getSqlSessionQueryTemplate().selectList("EbookConsRecordMapper.selectUserRewardEbooks", map("custId",custId,"start",start,"count",count));
	}

	@Override
	public int selectCountUserRewardBooks(Long custId) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("EbookConsRecordMapper.selectUserRewardEbooksAcount", custId);
	}
	
}
