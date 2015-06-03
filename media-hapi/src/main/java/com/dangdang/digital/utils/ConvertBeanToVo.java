package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.List;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.EbookConsRecord;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.model.RankConsToBook;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnActivityUserVo;
import com.dangdang.digital.vo.ReturnActivityVo;
import com.dangdang.digital.vo.ReturnEbookConsVo;
import com.dangdang.digital.vo.ReturnPrizeVo;
import com.dangdang.digital.vo.ReturnRankConsVo;
import com.dangdang.digital.vo.ReturnUserVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: bean转化成vo
 * All Rights Reserved.
 * @version 1.0  2014年12月22日 下午4:22:52  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ConvertBeanToVo {
	

	/**
	 * Description: 获取个人活动信息的返回vo 列表
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 */
	public static ReturnActivityUserVo getActivityUserVo(ActivityUser user) {
		ReturnActivityUserVo vo = new ReturnActivityUserVo();
		vo.setLotTimes(user.getLotTimes());
		vo.setDayTopRank(user.getDayTopRank());
		vo.setMouthTopRank(user.getMouthTopRank());
		vo.setReward(user.getRewardCons());
		vo.setTotalTopRank(user.getTotalTopRank());
		vo.setNickname(user.getUsername());
		vo.setWeekTopRank(user.getWeekTopRank());
		vo.setWorshipedTimes(user.getWorshipedTimes());
		return vo;
	}
	
	/**
	 * Description: 获取活动的返回vo
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public static ReturnActivityVo getReturnActivityVo(ActivityRecord record,ICacheApi cacheApi){
		ReturnActivityVo returnActivityVo = new ReturnActivityVo();
		returnActivityVo.setNickName(record.getUsername());
		returnActivityVo.setActivityType(record.getActivityType());
		returnActivityVo.setAmount(record.getAmount());
		returnActivityVo.setPrizeName(record.getPrizeName());
		if(null!=record.getPrizeType()&&record.getPrizeType()==2){
			MediaSaleCacheVo mediaVo = new MediaSaleCacheVo();
			try {
				mediaVo = cacheApi.getMediaSaleFromCache(record.getPrizeId());
				if(1==mediaVo.getMediaList().size()){
					Long mediaId =mediaVo.getMediaList().get(0).getMediaId();
					returnActivityVo.setMediaId(mediaId);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			returnActivityVo.setPrizeId(record.getPrizeId());
		}	
		returnActivityVo.setPrizeType(record.getPrizeType());
		returnActivityVo.setCreationDate(DateUtil.getDateTime(record.getCreationDate()));
		
		return returnActivityVo;
	}
	
	/**
	 * Description: 获取活动的返回vo 列表
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 */
	public static List<ReturnActivityVo> getReturnActivityVo(List<ActivityRecord> recordList,ICacheApi cacheApi) {
		List<ReturnActivityVo> resultList = new ArrayList<ReturnActivityVo>();
		for(ActivityRecord record:recordList){
			resultList.add(getReturnActivityVo(record,cacheApi));
		}
		return resultList;
	}
	
	
	/**
	 * Description: 获取单品壕赏榜的返回vo
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public static ReturnEbookConsVo getEbookConsVo(EbookConsRecord record) throws Exception {
		ReturnEbookConsVo vo = new ReturnEbookConsVo();
		vo.setCons(record.getCons());
		vo.setUsername(record.getUsername());
		vo.setUserImgUrl(record.getUserImgUrl());
		vo.setCustId(DesUtils.encryptCustId(record.getCustId()));
		return vo;
	}
	
	/**
	 * Description:  获取单品壕赏榜的返回vo 列表 
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public static List<ReturnEbookConsVo> getEbookConsVo(List<EbookConsRecord> recordList) throws Exception {
		List<ReturnEbookConsVo> resultList = new ArrayList<ReturnEbookConsVo>();
		for(EbookConsRecord record:recordList){
			resultList.add(getEbookConsVo(record));
		}
		return resultList;
	}
	
	
	/**
	 * Description:  获取壕赏榜的返回vo 列表 
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public static List<ReturnRankConsVo> getRankConsVo(List<RankConsToBook> rankList,String code) throws Exception {
		List<ReturnRankConsVo> resultList = new ArrayList<ReturnRankConsVo>();
		for(RankConsToBook rank:rankList){
			if("TOTAL".equals(code)){
				resultList.add(getTotalRankConsVo(rank));
			}else {
				resultList.add(getRankConsVo(rank));
			}
		}
		return resultList;
	}
	
	public static List<ReturnRankConsVo> getRankConsVoFromActUser(List<ActivityUser> userList)  throws Exception {
		List<ReturnRankConsVo> resultList = new ArrayList<ReturnRankConsVo>();
		for(ActivityUser user:userList){
			resultList.add(getRankConsVoFromActUser(user));
		}
		return resultList;
	}
	
	/**
	 * Description: 获取单品壕赏榜的返回vo (日，周，月)
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public static ReturnRankConsVo getRankConsVo(RankConsToBook rank) throws Exception {
		ReturnRankConsVo vo = new ReturnRankConsVo();
		vo.setCustId(DesUtils.encryptCustId(rank.getCustId()));
		vo.setMediaId(rank.getMediaId());
		vo.setMediaName(rank.getMediaName());
		vo.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(rank.getSaleId()));
		vo.setSaleId(rank.getSaleId());
		vo.setShowCons(Long.valueOf(rank.getShowCons()));
		vo.setUsername(rank.getUsername());
		vo.setUserImgUrl(rank.getUserImgUrl());
		return vo;
	}
	
	
	/**
	 * Description: 获取单品壕赏榜的返回vo (总榜)
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public static ReturnRankConsVo getTotalRankConsVo(RankConsToBook rank) throws Exception {
		ReturnRankConsVo vo = new ReturnRankConsVo();
		vo.setCustId(DesUtils.encryptCustId(rank.getCustId()));
		vo.setShowCons(Long.valueOf(rank.getShowCons()));
		vo.setUsername(rank.getUsername());
		vo.setUserImgUrl(rank.getUserImgUrl());
		return vo;
	}
	

	/**
	 * Description: 获取单品壕赏榜的返回vo from ActivityUser
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public static ReturnRankConsVo getRankConsVoFromActUser(ActivityUser user) throws Exception {
		ReturnRankConsVo vo = new ReturnRankConsVo();
		vo.setCustId(DesUtils.encryptCustId(user.getCustId()));
		vo.setShowCons(user.getRewardCons());
		vo.setUsername(user.getUsername());
		return vo;
	}
	
	/**
	 * Description: 获取单品壕赏榜的返回vo
	 * @Version1.0 2014年12月22日 下午4:12:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param record
	 * @return
	 */
	public static ReturnUserVo getReturnUserVo(UserTradeBaseVo userVo) {
		ReturnUserVo vo = new ReturnUserVo();
		vo.setUserImgUrl(userVo.getCustImg());
		vo.setMainBalance(userVo.getMainBalance()==null?0:userVo.getMainBalance());
		vo.setSubBalance(userVo.getSubBalance()==null?0:userVo.getSubBalance());
		vo.setNickname(userVo.getNickname());
		return vo;
	}
	
	
	/**
	 * Description: 获取返回的奖品vo
	 * @Version1.0 2014年12月31日 上午11:37:12 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prize
	 * @return
	 * @throws Exception 
	 */
	public static ReturnPrizeVo getReturnPrizeVo(Prize prize) throws Exception {
		ReturnPrizeVo vo = new ReturnPrizeVo();
		vo.setAmount(prize.getAmount());
		vo.setPrizeName(prize.getPrizeName());
		vo.setPrizeType(prize.getPrizeType());
		Long id = prize.getMediaLotteryPrizeId();
		vo.setRecordId(null==id?null:DesUtils.encryptRedPacketId(id));
		vo.setSaleId(prize.getPrizeId());
		return vo;
	}
	
	/**
	 * Description: 获取返回的奖品vo列表
	 * @Version1.0 2014年12月31日 上午11:37:27 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prizeList
	 * @return
	 * @throws Exception 
	 */
	public static List<ReturnPrizeVo> getReturnPrizeVo(List<Prize> prizeList) throws Exception {
		List<ReturnPrizeVo> voList = new ArrayList<ReturnPrizeVo>();
		for(Prize prize:prizeList){
			voList.add(getReturnPrizeVo(prize));
		}
		return voList;
	}
	
	
}
