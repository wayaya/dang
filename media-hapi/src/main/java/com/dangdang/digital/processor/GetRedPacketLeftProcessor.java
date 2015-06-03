package com.dangdang.digital.processor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IRedPacketService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description:获取红包剩余数量接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午5:17:41  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetRedPacketLeftprocessor")
public class GetRedPacketLeftProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetRedPacketLeftProcessor.class);
	
	@Resource
	private IRedPacketService redPacketService;
	@Resource
	private ICacheApi cacheApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//红包id
		String redPacketIdStr = request.getParameter("redPacketId");
		try {
			Long redPacketId = DesUtils.decryptRedPacketId(redPacketIdStr);
			int leftAmount = redPacketService.getRedPacketLeft(redPacketId);
			sender.put("amount", leftAmount);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,redPacketId:"+redPacketIdStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}	
	}
}
