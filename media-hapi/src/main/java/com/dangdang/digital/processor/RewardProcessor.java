package com.dangdang.digital.processor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.base.account.client.api.IAccountApi;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.EbookConsRecord;
import com.dangdang.digital.service.IConsumerConsumeService;
import com.dangdang.digital.service.IEbookConsRecordService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 打赏 All Rights Reserved.
 * 
 * @version 1.0 2014年12月9日 下午5:17:41 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapirewardprocessor")
public class RewardProcessor extends BaseApiProcessor {
	private static final Logger logger = LoggerFactory.getLogger(RewardProcessor.class);

	@Resource private IConsumerConsumeService consumerConsumeService;
	@Resource private IEbookConsRecordService ebookConsRecordService;
	@Resource private ICacheApi cacheApi;
	@Resource private IAccountApi accountApi; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String saleIdStr = request.getParameter("saleId");
		// 获取频道
		String channelType = request.getParameter("channelType");
		// 获取参数打赏数
	//	String platform = request.getParameter("platform");
		String consStr = request.getParameter("cons");
		UserTradeBaseVo userVo = cacheApi.getUserWholeInfoByToken(token);
		if (null == userVo || !checkParams(token)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		String deviceType = request.getParameter("deviceType");
		if(!checkParams(channelType)||!checkParams(deviceType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
		
		Long custId = 0L;
		String nickname = "";
		try {
			int coins = Integer.parseInt(consStr);
			if(coins<=0){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			
			// 获取用户id
			custId = userVo.getCustId();
			nickname = userVo.getNickname();
			String username = userVo.getUsername();
			if (!checkParams(nickname) || custId == 0L|| !checkParams(custId + "")) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(),
						response);
				return;
			}
			//获取账户余额，看是否钱够用。
			int masterMoney = accountApi.queryAccountInfoByCustId(custId).getMasterAccountMoney();
			if(masterMoney<coins){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15801.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_15801.getErrorMessage(),
						response);
				return;
			}
			Long saleId = Long.parseLong(saleIdStr);
			MediaSaleCacheVo saleVo = cacheApi.getMediaSaleFromCache(saleId);
			// 不是单品
			if (null==saleVo||0 != saleVo.getType()||null==saleVo.getMediaList()||saleVo.getMediaList().size()!=1) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_17802.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_17802.getErrorMessage(),
						response);
				return;
			}
			Long mediaId = saleVo.getMediaList().get(0).getMediaId();
			
			EbookConsRecord record = getEbookConsRecord(saleVo,userVo,saleId, coins, channelType, deviceType);
			record.setMediaId(mediaId);
			ebookConsRecordService.reward(record,username);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(logger, e, "参数错误,custId:" + custId + ",nickname:"
					+ nickname + ",cons:" + consStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} catch (MediaException me) {
			// 创建失败并返回
			LogUtil.error(logger, me, "创建打赏消费记录失败！");
			Integer errorCode = ErrorCodeEnum.ERROR_CODE_15802.getErrorCode();
			String errorMsg = ErrorCodeEnum.ERROR_CODE_15802.getErrorMessage();
			if(StringUtils.isNotBlank(me.getErrorCode()) && StringUtils.isNumeric(me.getErrorCode())){
				errorCode = Integer.valueOf(me.getErrorCode());
			}
			
			if(StringUtils.isNotBlank(me.getErrorMsg())){
				errorMsg = me.getErrorMsg();
			}
			sender.fail(errorCode,errorMsg, response);
		} catch (Exception e) {
			// 创建失败并返回
			LogUtil.error(logger, e, "创建打赏消费记录失败！");				
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(),
					response);
		}
	}
			
	
	/**
	 * Description:获取封装后的record 
	 * @Version1.0 2015年1月13日 上午11:34:56 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param saleVo
	 * @param userVo
	 * @param saleId
	 * @param cons
	 * @param channelType
	 * @param deviceType
	 * @return
	 */
	private EbookConsRecord getEbookConsRecord(MediaSaleCacheVo saleVo,UserTradeBaseVo userVo,Long saleId,int cons,String channelType,String deviceType){
		MediaCacheWholeVo mediaCache = saleVo.getMediaList().get(0);
		// 书的id
		Long mediaId = mediaCache.getMediaId();
		// 书的封面地址
		String mediaUrl = mediaCache.getCoverPic();
		// 书的名字
		String mediaName = mediaCache.getTitle();
		EbookConsRecord record = new EbookConsRecord(userVo.getCustId(), userVo.getNickname(),
				mediaId, saleId, mediaName, mediaUrl, cons, channelType.toUpperCase(),
				new Date(), userVo.getCustImg(),deviceType);
		return record;
	}
	
}
