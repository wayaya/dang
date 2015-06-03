package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IMessageDao;
import com.dangdang.digital.model.Block;
import com.dangdang.digital.model.Message;
import com.dangdang.digital.vo.ReturnMessageVo;
@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message> implements IMessageDao {

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<Message>> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<Message>> slaveRedisTemplate;
	
	@Override
	public List<Message> getMessageCache(Long custId) {
		return slaveRedisTemplate.opsForValue().get(Constans.MEDIA_MESSAGE_CACHE_KEY+custId);
	}

	@Override
	public void setMessageCache(Long custId,List<Message> messageCacheList) {
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_MESSAGE_CACHE_KEY+custId, messageCacheList, 3, TimeUnit.DAYS);
	}
	
	@Override
	public List<Message> getMessageList(Long custId,String platformSource,String deviceType,
			int start, int end) {
		return this.selectList("MessageMapper.findListByCustId",map("custId",custId,"appId",platformSource,"deviceType",deviceType,"start",start,"count",end));
	}

	@Override
	public void updateIsRead(Long custId,String platformSource,String deviceType) {
		Date openDate = new Date();
		this.getSqlSessionTemplate().update("MessageMapper.updateIsRead",map("custId",custId,"appId",platformSource,"deviceType",deviceType,"openDate",openDate));
	}
	@Override
	public int getMessageNum(Long custId,String platformSource,String deviceType) throws Exception{
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("MessageMapper.selectMessageCount",map("custId",custId,"appId",platformSource,"deviceType",deviceType));
		
	}

	@Override
	public int getAllMassageNum(Long custId,String platformSource,String deviceType) {
		// TODO Auto-generated method stub
		return (Integer)super.getSqlSessionQueryTemplate().selectOne("MessageMapper.selectAllMessageCount",map("custId",custId,"appId",platformSource,"deviceType",deviceType));
		
	}
}
