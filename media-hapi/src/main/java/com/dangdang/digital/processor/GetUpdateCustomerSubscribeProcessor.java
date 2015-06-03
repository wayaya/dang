package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.CustomerSubscription;
import com.dangdang.digital.service.ICustomerSubscriptionService;
import com.dangdang.digital.utils.DDClickUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 用户的device no, custId（可以为空） 追更记录关系建立
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:36:02  by 魏嵩（weisong@dangdang.com）创建
 */

@Component("hapigetUpdateCustomerSubscribeprocessor")
public class GetUpdateCustomerSubscribeProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetUpdateCustomerSubscribeProcessor.class);

	@Resource
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private ICustomerSubscriptionService customerSubscriptionService; 
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		initSubscription(request, response, sender);
	}

	/**
	 * 
	 * Description: 
	 * @Version1.0 2014年12月11日 下午4:23:33 by 魏嵩（weisong@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void initSubscription(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		//公共参数
		String deviceSerialNo = DDClickUtils.get(request, "deviceSerialNo", "");
		String tokenStr = request.getParameter("token");
		
		//接口参数
		String appIdStr = request.getParameter("appId");
		String mediaIdStr = request.getParameter("mediaId");
		
		//用户操作 1 为追更 0为取消追更
		String operationTypeStr = request.getParameter("operationType");
		
		
		Long custId = -1L;
		if(!StringUtils.isEmpty(tokenStr)){
			UserTradeBaseVo vo =  authorityApiFacade.getUserInfoByToken(tokenStr);
			if(! (vo==null || vo.getCustId()==null) ){
				custId = vo.getCustId();
			}
		}
		
		if(StringUtils.isEmpty(appIdStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21001.getErrorMessage(), response);
			return;
		}
		Integer appId = SafeConvert.convertStringToInteger(appIdStr, -1);
		if(appId==-1){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21012.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21012.getErrorMessage(), response);
			return;
		}
		
		if(StringUtils.isEmpty(deviceSerialNo)){
			
			sender.fail(ErrorCodeEnum.ERROR_CODE_21004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21004.getErrorMessage(), response);
			return;
		}
		
		if(StringUtils.isEmpty(mediaIdStr)){
			
			sender.fail(ErrorCodeEnum.ERROR_CODE_21013.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21013.getErrorMessage(), response);
			return;
		}
		
		String[] mediaIds = Constans.underlineSpliter.split(mediaIdStr);
		List<Long> mediaIdList = new ArrayList<Long>();
		for(String mediaId: mediaIds){
			
			Long mediaIdLong = SafeConvert.convertStringToLong(mediaId, -1L);
			if(mediaIdLong.equals(-1)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_21014.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_21014.getErrorMessage(), response);
				return;
			}
			mediaIdList.add(mediaIdLong);
		}

		if(StringUtils.isEmpty(operationTypeStr)){
			
			sender.fail(ErrorCodeEnum.ERROR_CODE_21015.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21015.getErrorMessage(), response);
			return;
		}
		
		Integer operationType = SafeConvert.convertStringToInteger(operationTypeStr, -1);
		if(operationType==-1 || (operationType!=0 && operationType!=1)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21016.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21016.getErrorMessage(), response);
			return;
		}
		
		//如果custId不是-1， 那么更新数据库里deviceNo 一样的，匿名追更的custId
		if(custId!=-1){
			customerSubscriptionService.updateAnonymousRecords(deviceSerialNo, custId );
		}
		
		List<CustomerSubscription> insertList = new ArrayList<CustomerSubscription>();
		
		for(Long mediaId: mediaIdList){
		
			CustomerSubscription subscription = new CustomerSubscription();
			subscription.setAppId(appId);
			subscription.setDeviceNo(deviceSerialNo);
			subscription.setSubscribeRelation(1);
			subscription.setMediaId(mediaId);
			
			//查询存不存在，如果不存在，插入，如果存在，更新
			
			List<CustomerSubscription> list = customerSubscriptionService.findMasterListByParamsObjs(subscription);
			if(list!=null && list.size()>0){
				CustomerSubscription subscriptionInDB = list.get(0);
				subscriptionInDB.setStatus(operationType);
				subscriptionInDB.setCreationDate(new Date());
				customerSubscriptionService.update(subscriptionInDB);
				
			}else{
				
				subscription.setCustId(custId);
				subscription.setStatus(operationType);
				subscription.setCreationDate(new Date());
				insertList.add(subscription);
			}
		}
		
		if(insertList.size()>0){
			customerSubscriptionService.save(insertList);
		}
		
		sender.put("updateResult", "OK");
		sender.success(response);
	}

}
