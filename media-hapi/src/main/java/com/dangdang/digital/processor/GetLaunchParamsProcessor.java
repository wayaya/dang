package com.dangdang.digital.processor;



import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.ResultSender;


/**
 * Description:  获取启动的配置参数
 * All Rights Reserved.
 * @version 1.0  2015年1月23日 下午2:45:01  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetLaunchParamsprocessor")
public class GetLaunchParamsProcessor extends BaseApiProcessor {
	
	private static Pattern firstPattern = Pattern.compile("\\|");
	private static Pattern secondPattern = Pattern.compile("::");
	
	@Resource ISystemApi systemApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String launchParams = systemApi.getProperty("launchParams");
		
		String deviceType = request.getParameter("deviceType");
		
		if(!StringUtils.isEmpty(deviceType)&&(deviceType!=null && deviceType.toLowerCase().indexOf("iphone")!=-1||
												deviceType!=null && deviceType.toLowerCase().indexOf("ipad")!=-1)||
												deviceType!=null && deviceType.toLowerCase().indexOf("ios")!=-1){
			
			try{
				Map<String, String> lauchParamMap = new HashMap<String, String>();
				
				String[] lanchParamArray = firstPattern.split(launchParams);
				for(String lanchParam:lanchParamArray){
					String[] lanchParamSplit = secondPattern.split(lanchParam);
					lauchParamMap.put(lanchParamSplit[0], lanchParamSplit[1]);
				}
				sender.put("launchParams", lauchParamMap);
				
			}catch(Exception e){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
				return;
			}
			
		}else{
			sender.put("launchParams", launchParams);
		}
		sender.success(response);
	}

}
