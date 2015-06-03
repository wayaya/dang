package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.api.IConsumeApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IEbookConsRecordCacheDao;
import com.dangdang.digital.dao.IEbookConsRecordDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.EbookConsRecord;
import com.dangdang.digital.processor.RewardProcessor;
import com.dangdang.digital.service.IEbookConsRecordService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.CreateConsumeVo;
/**
 * Description: 电子书金币消费壕记录表的Service 实现类
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:09:51  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class IEbookConsRecordServiceImpl extends BaseServiceImpl<EbookConsRecord, Long> implements IEbookConsRecordService{
	private static final Logger logger = LoggerFactory.getLogger(RewardProcessor.class);
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IEbookConsRecordDao ebookConsRecordDao;
	
	@Resource
	private IEbookConsRecordCacheDao ebookConsRecordCacheDao;
	@Resource
	private IActivityUserCacheDao activityUserCacheDao;
	@Resource
	private IActivityUserDao activityUserDao;
	@Resource
	private IConsumeApi consumeApi;
	
	@Override
	public IBaseDao<EbookConsRecord> getBaseDao() {
		return ebookConsRecordDao;
	}

	@Override
	public int selectCountEbookConsUser(Long mediaId, String channel) {
		return ebookConsRecordDao.selectCountEbookConsUser(mediaId, channel);
	}

	@Override
	public List<EbookConsRecord> selectYesterdayUserEbookConsByMediaId(Long mediaId,String channel,
			int start, int count) {
		return ebookConsRecordDao.selectYesterdayUserEbookConsByMediaId(mediaId,channel, start, count);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void reward(EbookConsRecord record,String username) throws Exception {
		//插入一条打赏log\
		try {
			Long custId = record.getCustId();
			int coins = record.getCons();
			activityUserDao.addRewardCoins(custId, record.getUsername(), coins);
			this.save(record);
			//创建打赏的订单
			// 参数校验封装
			CreateConsumeVo createConsumeVo = new CreateConsumeVo();
			createConsumeVo.setCustId(custId);
			createConsumeVo.setUsername(username);
			createConsumeVo.setConsumeType(Short.parseShort(Constans.BUY_FLAG_REWARDS));
			createConsumeVo.setDeviceType(record.getDeviceType());
			createConsumeVo.setRewardsMoney(coins);
			createConsumeVo.setMediaId(record.getMediaId());
			// 创建消费记录
			consumeApi.createAndSaveConsume(createConsumeVo);
			
			activityUserCacheDao.deleteActivityUserVoCache(custId);
			ebookConsRecordCacheDao.deleteUserRewardBooksIdsCache(custId);
			ebookConsRecordCacheDao.deleteEbookRewardedUsersCache(record.getMediaId(),record.getChannel());
		} catch (MediaException me) {
			// 创建失败并返回
			LogUtil.error(logger, me, "创建打赏消费记录失败！");
			Integer errorCode = ErrorCodeEnum.ERROR_CODE_10000.getErrorCode();
			String errorMsg = ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage();
			if(StringUtils.isNotBlank(me.getErrorCode()) && StringUtils.isNumeric(me.getErrorCode())){
				errorCode = Integer.valueOf(me.getErrorCode());
			}
			if(StringUtils.isNotBlank(me.getErrorMsg())){
				errorMsg = me.getErrorMsg();
			}
			throw new MediaException(errorCode+"",errorMsg);
		}catch(Exception e){
			LogUtil.error(logger, e, "创建打赏记录失败，参数信息为："+record.toString());				
			throw e;
		}
//		//用户主账户扣减阅读币
//		Long custId = record.getCustId();
//		UserTradeBaseVo vo = new UserTradeBaseVo();
//		vo.setTradeType(Constans.TRADE_TYPE_CONSUME);
//		vo.setOnlyPayMainAmount(true);
//		vo.setCustId(custId);
//		vo.setRequirePayAmount(record.getCons());
//		vo.setConsumeSource(record.getDeviceType());
//		//唯一单号
//		String depositNo = "__act_reword_"+record.getMediaEbookConsRecordId();
//		vo.setSourceOrderNo(depositNo);
//		vo.setUsername(record.getUsername());
//		userService.tradeForUser(vo);
//		if(!vo.isTradeResult()){
//			LogUtil.error(logger, "创建充值记录失败:打赏扣钱，custId：{0}",custId);
//			throw new MediaException(ErrorCodeEnum.ERROR_CODE_15800.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_15800.getErrorMessage());
//		}
		
	}
}
