package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivityRecordCacheDao;
import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBlockCacheDao;
import com.dangdang.digital.dao.IBlockDao;
import com.dangdang.digital.dao.IEbookConsRecordCacheDao;
import com.dangdang.digital.dao.IEbookConsRecordDao;
import com.dangdang.digital.dao.ILotteryCacheDao;
import com.dangdang.digital.dao.ILotteryDao;
import com.dangdang.digital.dao.IRankConsToBookCacheDao;
import com.dangdang.digital.dao.IRankConsToBookDao;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.Block;
import com.dangdang.digital.model.EbookConsRecord;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.model.RankConsToBook;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.ConvertBeanToVo;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.vo.ReturnActivityUserVo;
import com.dangdang.digital.vo.ReturnActivityVo;
import com.dangdang.digital.vo.ReturnEbookConsVo;
import com.dangdang.digital.vo.ReturnPrizeVo;
import com.dangdang.digital.vo.ReturnRankConsVo;

/**
 * Description:  缓存service实现
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 下午4:54:03  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class CacheServiceImpl implements ICacheService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

	@Resource(name = "systemApi")
	private ISystemApi systemApi;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Object> masterRedisTemplate;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Object> slaveRedisTemplate;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private ILotteryCacheDao lotteryCacheDao;
	@Resource
	private ILotteryDao lotteryDao;
	@Resource
	private IActivityUserCacheDao activityUserCacheDao;
	@Resource
	private IActivityUserDao activityUserDao;
	@Resource
	private IEbookConsRecordCacheDao ebookConsRecordCacheDao;
	@Resource
	private IEbookConsRecordDao ebookConsRecordDao;
	@Resource
	private IActivityRecordDao activityRecordDao;
	@Resource
	private IActivityRecordCacheDao activityRecordCacheDao;
	@Resource
	private IRankConsToBookDao rankConsToBookDao;
	@Resource
	private IRankConsToBookCacheDao rankConsToBookCacheDao;
	
	@Resource
	private IBlockDao blockDao;
	@Resource
	private IBlockCacheDao blockCacheDao; 
	@Resource private ICacheApi cacheApi;
	
	@Override
	public List<Prize> getPrizeListCache(final int vestType) {
		// 首先从缓存获取
		List<Prize> prizeList = lotteryCacheDao.getPrizeListCache(vestType);
		// 如果缓存为空或者缓存不足
		if (this.isCacheNotEnough(prizeList, 3)) {
			final List<Prize> listCache = lotteryDao.getPrizeList(vestType);
			prizeList = listCache;
			taskExecutor.execute(new Runnable() {
				public void run() {
					lotteryCacheDao.setPrizeListCache(listCache, vestType);
					LOGGER.info("异步添加奖品配置信息，归属为：(" + vestType + ")到缓存");
				}
			});
		}
		return prizeList;
	}
	
	
	
	/**
	 * Description: 判断缓存是否是不充足的，需要从数据库里读取
	 * @Version1.0 2014年12月23日 下午7:24:03 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param objectList
	 * @param length
	 * @return
	 */
	private boolean isCacheNotEnough(Collection<?> objectList, int length) {
		if (objectList == null) {
			return true;
		}
		if (objectList.size() < length) {
			return true;
		}
		for (Object obj : objectList) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}



	@Override
	public ReturnActivityUserVo getActivityUserCache(final Long custId){
		// 首先从缓存获取
		ReturnActivityUserVo returnActivityUserVo= activityUserCacheDao.getActivityUserVoCache(custId);
		//获取用户的头像地址
		Map<String, String> userMap = cacheApi.getUserInfoByCustId(custId);
		String custImg = "";
		String nickName = "";
		if(null!=userMap&&userMap.containsKey("custImg")){
			custImg = userMap.get("custImg");
			nickName = userMap.get("nickName");
		}
		// 如果缓存为空
		if (null==returnActivityUserVo) {
			//哎，有变动，需要去掉了！！
//			ActivityUser user = getUpdateUserRank(custId);
//			if(null!=user){
//				//更新该用户最新的榜单名次信息
//				user.setCustId(custId);
//				activityUserDao.updateByCustId(user);
//			}
			//从库里获取用户的活动信息
			ActivityUser dbUser = activityUserDao.selectUserByCustID(custId);
			if(null==dbUser){
				if(null==returnActivityUserVo){
					returnActivityUserVo = new ReturnActivityUserVo();
				}
				returnActivityUserVo.setUserImgUrl(custImg);
				returnActivityUserVo.setNickname(nickName);
				return returnActivityUserVo;
			}
			final ReturnActivityUserVo cacheVo = ConvertBeanToVo.getActivityUserVo(dbUser);
			returnActivityUserVo = cacheVo;
			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						activityUserCacheDao.setActivityUserVoCache(custId,cacheVo);
					} catch (Exception e) {
						LOGGER.info("异步添加用户活动基本信息，custId为：(" + custId + ")到缓存 失败");
						e.printStackTrace();
					}
					LOGGER.info("异步添加用户活动基本信息，custId为：(" + custId + ")到缓存");
				}
			});
		}
		returnActivityUserVo.setUserImgUrl(custImg);
		returnActivityUserVo.setNickname(nickName);
		return returnActivityUserVo;
	}


	@Override
	public Block getBlockCache(final String code) {
		// 首先从缓存获取
		Block cacheBlock= blockCacheDao.getBlockCache(code);
		// 如果缓存为空
		if (null==cacheBlock) {
			final Block block = blockDao.selectContentByCode(code);
			cacheBlock = block;
			taskExecutor.execute(new Runnable() {
				public void run() {
					blockCacheDao.setBlockCache(code, block);
					LOGGER.info("异步添加用块信息到缓存，块标识为：(" + code + ")");
				}
			});
		}
		return cacheBlock;
	}





	@Override
	public List<ReturnEbookConsVo> getEbookRewardedUsersCache(final Long mediaId,final String channel) throws Exception {
		// 首先从缓存获取
		List<ReturnEbookConsVo> cacheList= ebookConsRecordCacheDao.getEbookRewardedUsersCache(mediaId, channel);
																	
		// 如果缓存为空
		if (null==cacheList) {
			//默认的条数
			int count = Constans.MEDIA_RANK_CONS_ONE_BOOK_AMOUNT;
			//List<EbookConsRecord> list = ebookConsRecordDao.selectYesterdayUserEbookConsByMediaId(mediaId, channel, start, count);
			//有改动，改成历史的了，加一个方法吧！！
			List<EbookConsRecord> list = ebookConsRecordDao.selectHistoryUserEbookConsByMediaId(mediaId, channel,count);
			final List<ReturnEbookConsVo> voList = ConvertBeanToVo.getEbookConsVo(list);
			cacheList = voList;
			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						ebookConsRecordCacheDao.setEbookRewardedUsersCache(mediaId, channel, voList);
					} catch (Exception e) {
						e.printStackTrace();
					}
					LOGGER.info("异步添加单品壕赏信息到缓存，块标识为：(" +mediaId + ")");
				}
			});
		}
		return cacheList;
	}
	
	@Override
	public List<ReturnActivityVo> getUserActivityRecordCache(final Long custId,final int type,final int prizeType) {
		// 首先从缓存获取
		List<ReturnActivityVo> cacheList= activityRecordCacheDao.getUserActivityRecordCache(custId,type,prizeType);
		// 如果缓存为空
		if (null==cacheList) {
			//默认的条数
			int count = Constans.MEDIA_USER_ACTIVITYS_AMOUNT;
			List<ActivityRecord> list = activityRecordDao.selectUserActivityRecord(custId, type, prizeType,0, count);
			//把prizeId(media_id)转成saleId
			final List<ReturnActivityVo> voList = ConvertBeanToVo.getReturnActivityVo(list,cacheApi);
			cacheList = voList;
			taskExecutor.execute(new Runnable() {
				public void run() {
					activityRecordCacheDao.setUserActivityRecordCache(custId,type,prizeType,voList);
					LOGGER.info("异步添加个人的活动信息到缓存，custId为：(" +custId + ")");
				}
			});
		}
		return cacheList;
	}

	@Override
	public List<ReturnPrizeVo> getUserLotteryRecordCache(Long custId) {
		return null;
	}

	@Override
	public List<ReturnActivityVo> getLatestActivityRecord(final int activityType) {
		// 首先从缓存获取
		List<ReturnActivityVo> cacheList= activityRecordCacheDao.getLatestActivityRecordCache(activityType);
		// 如果缓存为空
		if (null==cacheList) {
			//默认的条数
			int amount = Constans.MEDIA_ACTIVITY_RECORDS_AMOUNT_CACHE_KEY;
			List<ActivityRecord> list = activityRecordDao.selectLatestActivityRecord(activityType, amount);
			final List<ReturnActivityVo> voList = ConvertBeanToVo.getReturnActivityVo(list,cacheApi);
			cacheList = voList;
			taskExecutor.execute(new Runnable() {
				public void run() {
					activityRecordCacheDao.setLatestActivityRecordCache(activityType, voList);
					LOGGER.info("异步添加活动的名单信息到缓存，activityType为：(" +activityType + ")");
				}
			});
		}
		return cacheList;
	}



	@Override
	public List<Long> getUserRewardSaleIdsCache(final Long custId) {
		// 首先从缓存获取
		List<Long> cacheList= ebookConsRecordCacheDao.getUserRewardBooksIdsCache(custId);
		// 如果缓存为空
		if (null==cacheList) {
			//默认的条数
			int amount = Constans.MEDIA_REWARDED_USER_AMOUNT;
			final List<Long> list = ebookConsRecordDao.selectUserRewardBooksIds(custId,0,amount);
			cacheList = list;
			taskExecutor.execute(new Runnable() {
				public void run() {
					ebookConsRecordCacheDao.setUserRewardBooksIdsCache(custId, list);
					LOGGER.info("异步用户打赏名单信息到缓存，activityType为：(" +custId + ")");
				}
			});
		}
		return cacheList;
	}



	@Override
	public List<ReturnRankConsVo> getRankConsListCache(final String code) throws Exception {
		// 首先从缓存获取 
		List<ReturnRankConsVo> cacheList= rankConsToBookCacheDao.getRankConsToBookByCodeCache(code);
		// 如果缓存为空
		if (null==cacheList) {
			//默认的条数
			int amount = Constans.MEDIA_RANK_CONS_BOOK_AMOUNT;
			List<RankConsToBook> list = rankConsToBookDao.selectRankConsToBookByCode(code, 0, amount);
			final List<ReturnRankConsVo> voList = ConvertBeanToVo.getRankConsVo(list,code);
			cacheList = voList;
			taskExecutor.execute(new Runnable() {
				public void run() {
					rankConsToBookCacheDao.setRankConsToBookByCodeCache(code, voList);
					LOGGER.info("异步更新壕赏榜单到缓存，标识为：(" +code + ")");
				}
			});
		}
		return cacheList;
	}
	
	@Override
	public List<ReturnRankConsVo> getRewardTotalRankCache() throws Exception {
		// 首先从缓存获取
		List<ReturnRankConsVo> cacheList= rankConsToBookCacheDao.getRewardTotalRankCache();
		// 如果缓存为空
		if (null==cacheList) {
			//默认的条数
			int amount = Constans.MEDIA_RANK_CONS_BOOK_AMOUNT;
			List<ActivityUser> list = activityUserDao.selectRewardRank(amount);
			final List<ReturnRankConsVo> voList = ConvertBeanToVo.getRankConsVoFromActUser(list);
			cacheList = getUsersInfoCache(voList);
			//批量获取头像信息
			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						rankConsToBookCacheDao.setRewardTotalRankCache(voList);
					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.error("异步更新壕赏榜单总榜到缓存出错！");
					}
					LOGGER.info("异步更新壕赏榜单总榜到缓存");
				}
			});
		}
		return cacheList;
	}



	@Override
	public String judgeFirstLoginPushMonthlyFlagCache(Long custId) {
		Object redisValue = masterRedisTemplate.opsForValue().get("first_login_giving_flag_" + custId);
		if(null==redisValue){
			return "0";
		}else {
			masterRedisTemplate.delete("first_login_giving_flag_" + custId);
			return "1";
		}
	}



	
	/**
	 * Description:获取用户各个榜单的最新最高排名 
	 * @Version1.0 2015年1月6日 下午9:16:51 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @return
	 */
	private ActivityUser getUpdateUserRank(Long custId){
		ActivityUser user = null;
		//查询该用户的最高榜单名次
		Integer dayTopRank = rankConsToBookDao.selectTopRankOfCustIdByCode(custId,1);
		if(null!=dayTopRank&&dayTopRank>0) {
			if(null==user){
				user = new ActivityUser();
			}
			user.setDayTopRank(dayTopRank);
		}
		Integer weekTopRank = rankConsToBookDao.selectTopRankOfCustIdByCode(custId,2);
		if(null!=weekTopRank&&weekTopRank>0) {
			if(null==user){
				user = new ActivityUser();
			}
			user.setWeekTopRank(weekTopRank);
		}
		Integer mouthTopRank = rankConsToBookDao.selectTopRankOfCustIdByCode(custId,3);
		if(null!=mouthTopRank&&mouthTopRank>0) {
			if(null==user){
				user = new ActivityUser();
			}
			user.setMouthTopRank(mouthTopRank);
		}
		Integer totalTopRank = rankConsToBookDao.selectTopRankOfCustIdByCode(custId,4);
		if(null!=totalTopRank&&totalTopRank>0) {
			if(null==user){
				user = new ActivityUser();
			}
			user.setTotalTopRank(totalTopRank);
		}
		return user;
		
	}

	
	/**
	 * Description: 批量获取活动用户的头像信息
	 * @Version1.0 2015年1月21日 上午11:53:57 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 * @throws Exception 
	 */
	public List<ReturnRankConsVo> getUsersInfoCache(List<ReturnRankConsVo> list) throws Exception {
		List<ReturnRankConsVo> resultList = new ArrayList<ReturnRankConsVo>();
		for(ReturnRankConsVo user:list){
			Long enCustId = DesUtils.decryptCustId(user.getCustId());
			Map<String, String> custInfoMap = cacheApi.getUserInfoByCustId(enCustId);
			if(custInfoMap.containsKey("custImg")){
				user.setUserImgUrl(custInfoMap.get("custImg"));
				resultList.add(user);
			}
		}
		return resultList;
	}
	
}
