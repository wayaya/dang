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
import com.dangdang.digital.service.ICloudPushPlanService;
import com.dangdang.digital.service.ICloudPushStatisticService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;

/**
 * 
 * Description: 用户点击通知时，客户端需要回调的接口，用来统计打开数量
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:36:02  by 魏嵩（weisong@dangdang.com）创建
 */

@Component("hapigetOpenNotificationprocessor")
public class GetOpenNotificationProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetOpenNotificationProcessor.class);

	@Resource
	private ICloudPushPlanService cloudPushPlanService;
	
	@Resource
	ICloudPushStatisticService cloudPushStatisticService ;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		openNotification(request, response, sender);
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
	private void openNotification(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		String planIdStr = request.getParameter("planId");
		if(StringUtils.isEmpty(planIdStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21010.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21010.getErrorMessage(), response);
			return;
		}
		
		List<Long> planIdList = new ArrayList<Long>();
		
		if(planIdStr.indexOf("_")!=-1){
			
			String[] planIds = Constans.underlineSpliter.split(planIdStr);
			for(String planIdTmp: planIds){
				Long planId = SafeConvert.convertStringToLong(planIdTmp, -1);
				if(planId != -1){
					planIdList.add(planId);
				}
			}
			
			if(planIdList.size()==0){
				sender.fail(ErrorCodeEnum.ERROR_CODE_21011.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_21011.getErrorMessage(), response);
				return;
			}
			
		}else{
			
			Long planId = SafeConvert.convertStringToLong(planIdStr, -1);
			if(planId == -1){
				sender.fail(ErrorCodeEnum.ERROR_CODE_21011.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_21011.getErrorMessage(), response);
				return;
			}
			planIdList.add(planId);
		}
		
		for(Long planIdInList: planIdList){
			cloudPushPlanService.addOpenNumber(planIdInList, 1);
			cloudPushStatisticService.addOpenNumber(planIdInList, 1, new Date());
		}
		
		sender.put("openResult", "OK");
		sender.success(response);
	}

}
