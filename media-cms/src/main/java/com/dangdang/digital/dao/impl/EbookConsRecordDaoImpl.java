package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IEbookConsRecordDao;
import com.dangdang.digital.model.RewardTop;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年4月1日 下午7:35:31  by yangzhenping（yangzhenping@dangdang.com）创建
 */
@Repository
public class EbookConsRecordDaoImpl extends BaseDaoImpl<RewardTop> implements IEbookConsRecordDao {

	@Override
	public List<RewardTop> getRewardTop(String startDate, String endDate, Integer count) {
		return this.selectList("EbookConsRecordMapper.selectRewardTop",
				map("startDate", startDate, "endDate", endDate, "count", count));
	}

	@Override
	public List<RewardTop> getBuyChapterGlobTop(String startDate, String endDate, Integer count) {
		return this.selectList("OrderDetailMapper.selectBuyChapterGlobTop",
				map("startDate", startDate, "endDate", endDate, "count", count));
	}

	@Override
	public List<RewardTop> getBuyChapterSilverTop(String startDate, String endDate, Integer count) {
		return this.selectList("OrderDetailMapper.selectBuyChapterSilverTop",
				map("startDate", startDate, "endDate", endDate, "count", count));
	}

}
