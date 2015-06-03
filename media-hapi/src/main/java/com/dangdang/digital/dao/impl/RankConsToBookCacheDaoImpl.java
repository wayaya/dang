package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IRankConsToBookCacheDao;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.vo.ReturnRankConsVo;

/**
 * Description:壕赏榜单cache dao 实现类
 * 
 * @version 1.0 2014年11月19日 下午2:22:34 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class RankConsToBookCacheDaoImpl implements IRankConsToBookCacheDao {

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<ReturnRankConsVo>> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<ReturnRankConsVo>> slaveRedisTemplate;
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<ReturnRankConsVo>> masterRedisTemplateUser;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<ReturnRankConsVo>> slaveRedisTemplateUser;


	@Override
	public List<ReturnRankConsVo> getRankConsToBookByCodeCache(String code) {
		return slaveRedisTemplate.opsForValue().get(Constans.MEDIA_RANK_CONS_BOOK_KEY + code);
	}

	@Override
	public void setRankConsToBookByCodeCache(String code,
			List<ReturnRankConsVo> list) {
		int expireTime = Constans.CACHE_EXPIRE_TIME_OF_RANK_CONS_BOOK;
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_RANK_CONS_BOOK_KEY + code, list, expireTime,TimeUnit.SECONDS);
	}

	@Override
	public void deleteRankConsToBookByCodeCache(String code) {
		masterRedisTemplate.delete(Constans.MEDIA_RANK_CONS_BOOK_KEY + code);
	}

	@Override
	public List<ReturnRankConsVo> getRewardTotalRankCache() {
		//System.out.println(slaveRedisTemplateUser.getExpire(Constans.MEDIA_RANK_CONS_BOOK_TOTAL_KEY));
		return slaveRedisTemplateUser.opsForValue().get(Constans.MEDIA_RANK_CONS_BOOK_TOTAL_KEY);
	}

	@Override
	public void setRewardTotalRankCache(List<ReturnRankConsVo> list) throws Exception {
		//总榜修改了。 改成直接从壕赏用户库里取
		//int expireTime = Constans.CACHE_EXPIRE_TIME_OF_RANK_CONS_BOOK;
		int expireTime = DateUtil.getSecondsToNextDay();
		masterRedisTemplateUser.opsForValue().set(Constans.MEDIA_RANK_CONS_BOOK_TOTAL_KEY, list, expireTime,TimeUnit.SECONDS);
	}

	@Override
	public void deleteRewardTotalRankCache() {
		masterRedisTemplateUser.delete(Constans.MEDIA_RANK_CONS_BOOK_TOTAL_KEY);
	}
	

	
}
