package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.model.ActivityUser;

/**
 * Description:福袋用户信息dao实现类 
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:22:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class ActivityUserDaoImpl extends BaseDaoImpl<ActivityUser>implements IActivityUserDao {

	@Override
	public void addOneWorshipTime(Long custId,String nickname) {
		int update = this.update("ActivityUserMapper.addOneWorshipTimeByCustId", custId);
		if(0==update){
			ActivityUser user = new ActivityUser();
			user.setCustId(custId);
			user.setWorshipTimes(1l);
			user.setCreationDate(new Date());
			user.setUsername(nickname);
			this.insert("ActivityUserMapper.insertSelective", user);
		}
	}

	@Override
	public void addOneWorshipedTime(Long custId,String nickname) {
		int update = this.update("ActivityUserMapper.addOneWorshipedTimeByCustId", custId);
		if(0==update){
			ActivityUser user = new ActivityUser();
			user.setCustId(custId);
			user.setWorshipedTimes(1l);
			user.setCreationDate(new Date());
			user.setUsername(nickname);
			this.insert("ActivityUserMapper.insertSelective", user);
		}
	}


	@Override
	public void addOneShareTime(Long custId,String nickname) {
		int update = this.update("ActivityUserMapper.addOneShareTimeByCustId", custId);
		if(0==update){
			ActivityUser user = new ActivityUser();
			user.setCustId(custId);
			user.setShareTimes(1);
			user.setCreationDate(new Date());
			user.setUsername(nickname);
			this.insert("ActivityUserMapper.insertSelective", user);
		}
	}



	@Override
	public ActivityUser selectUserByCustID(Long custId) {
		return selectOne("ActivityUserMapper.selectByCustId",custId);
	}

	@Override
	public void addLotTime(Long custId, Integer times, Integer lottedTime,String nickname) {
		int update = this.update("ActivityUserMapper.addLotTimeByCustId", map("custId",custId,"lotTimes",times,"lottedTimes",lottedTime));
		if(0==update){
			ActivityUser user = new ActivityUser();
			user.setCustId(custId);
			user.setLotTimes(times);
			user.setCreationDate(new Date());
			user.setUsername(nickname);
			this.insert("ActivityUserMapper.insertSelective", user);
		}
	}

	@Override
	public int updateByCustId(ActivityUser user) {
		return update("ActivityUserMapper.updateByCustIdSelective", user);
	}

	@Override
	public void addRewardCoins(Long custId,String nickname, int coins) {
		int update = this.update("ActivityUserMapper.addRewardCoinsByCustId", map("custId",custId,"coins",coins));
		if(0==update){
			ActivityUser user = new ActivityUser();
			user.setCustId(custId);
			user.setRewardCons((long) coins);
			user.setCreationDate(new Date());
			user.setUsername(nickname);
			this.insert("ActivityUserMapper.insertSelective", user);
		}
	}

	@Override
	public List<ActivityUser> selectRewardRank(int amount) {
		return selectList("ActivityUserMapper.selectRewardRank", amount);
	}

	@Override
	public void insert(ActivityUser user) {
		insert("ActivityUserMapper.insertSelective", user);
	}
}
