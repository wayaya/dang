package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.base.account.client.api.IAccountConsumeApi;
import com.dangdang.base.account.client.api.IAttachAccountItemsApi;
import com.dangdang.base.account.client.commons.enums.ConsumeSourceEnum;
import com.dangdang.base.account.client.vo.AttachAccountItemsVo;
import com.dangdang.base.commons.enums.DeviceTypeEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 查询银铃铛有效期接口
 * All Rights Reserved.
 * @version 1.0  2015年4月27日 下午3:22:01  by 魏嵩（weisong@dangdang.com）创建
 */
@Component("hapirechargeExpireQueryprocessor")
public class RechargeExpireQueryProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RechargeExpireQueryProcessor.class);
	@Resource
	private IAuthorityApiFacade authorityApiFacade;
	@Resource
	private IAttachAccountItemsApi attachAccountItemsApi;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		String lastAttachItemIdStr = request.getParameter("lastAttachItemId");
		String sequenceWay = request.getParameter("sequenceWay");
		String tokenStr = request.getParameter("token");
		boolean anonymous = true;
		Long custId = 0L;
			
		if(!StringUtils.isEmpty(tokenStr)){
			UserTradeBaseVo vo =  authorityApiFacade.getUserInfoByToken(tokenStr);
			if(! (vo==null || vo.getCustId()==null) ){
				custId = vo.getCustId();
				anonymous = false;
			}
		}
		
		if(anonymous){
			//未登录用户
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		String deviceType = request.getParameter("deviceType");
		if(StringUtils.isEmpty(deviceType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23010.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23010.getErrorMessage(), response);
			return;
		}
		
		String consumeSource = null;
		if(deviceType.toLowerCase().indexOf("android") != -1){
			consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_ANDROID.getConsumeSource();
		}else if(deviceType.toLowerCase().indexOf("iphone") != -1){
			consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
		}else if(deviceType.toLowerCase().indexOf("ipad") != -1){
			consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
		}
		if(consumeSource == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23010.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23010.getErrorMessage(), response);
			return;
		}
		
		if (StringUtils.isEmpty(sequenceWay) || !("ASC".equalsIgnoreCase(sequenceWay) || "DESC".equalsIgnoreCase(sequenceWay))) {
			
			sender.fail(ErrorCodeEnum.ERROR_CODE_24001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_24001.getErrorMessage(), response);
			return;
		}
		
		Long lastAttachItemId = null;
		if(!StringUtils.isEmpty(lastAttachItemIdStr)){
			lastAttachItemId = SafeConvert.convertStringToLong(lastAttachItemIdStr, -1L);
			if ( lastAttachItemId<0 ) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_24002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_24002.getErrorMessage(), response);
				return;
			}
		}		
		
		List<AttachAccountItemsVo> result = attachAccountItemsApi.queryAttachItemsListByWhere(custId, lastAttachItemId, consumeSource, sequenceWay.toUpperCase());
		
		sender.put("result", result);
		sender.success(response);
	}
}
