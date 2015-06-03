package com.dangdang.digital.dao.impl;



import java.util.Date;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IRedPacketUserDao;
import com.dangdang.digital.model.RedPacketUser;

@Repository
public class RedPacketUserDaoImpl extends BaseDaoImpl<RedPacketUser> implements IRedPacketUserDao{

	@Override
	public Integer selectRedPacketLefts(Long redPacketId) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("RedPacketUserMapper.getLefts", redPacketId);
	}

	@Override
	public void updateGetted(Long redPacketId, int cons) {
		update("RedPacketUserMapper.updateGetted", map("mediaRedPacketUserId",redPacketId,"putCons",cons));
	}

	@Override
	public Integer insert(RedPacketUser user) {
		return insert("RedPacketUserMapper.insertSelective",user);
	}

	@Override
	public Integer selectUserTodayShareRedPackets(Long custId, Date startDate,Date endDate) {
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("RedPacketUserMapper.selectCountByCustId", map("custId",custId,"creationDateStart",startDate,"creationDateEnd",endDate));
	}

	@Override
	public Long selectRedPacketIdByPrizeIdFromDb(Long prizeId) {
		return (Long)super.getSqlSessionQueryTemplate().selectOne("RedPacketUserMapper.selectRedPacketIdByPrizeIdFromDb",prizeId);
	}

}
