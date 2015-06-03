package com.dangdang.digital.processor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.ICoinsActivityService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description:获取今日撒金币时间间隔接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午5:17:41  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetSpreadCoinsTodayTimeprocessor")
public class GetSpreadCoinsTodayTimeProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetSpreadCoinsTodayTimeProcessor.class);
	
	@Resource
	private ICoinsActivityService coinsActivityService;
	@Resource
	private ISystemApi systemApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		try {
			String result = coinsActivityService.getLeftTimesInterval();
			String delay = systemApi.getProperty("spread_coins_first_tme", "60");
			sender.put("delay", System.currentTimeMillis()+Integer.parseInt(delay)*1000);
			sender.put("result", result);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}	
	}
}
