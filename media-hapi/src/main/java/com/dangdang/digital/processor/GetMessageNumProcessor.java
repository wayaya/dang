package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.DeviceTypeEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IMessageService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

@Component("hapigetMessageNumprocessor")
public class GetMessageNumProcessor extends BaseApiProcessor {
	@Resource
	private IAuthorityApiFacade authorityApiFacade;
	@Resource
	IMessageService messageService;
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		String tokenStr = request.getParameter("token");
		final String platformSource = request.getParameter("platformSource");
		String deviceType = request.getParameter("deviceType");
		Long custId = 0L;
		//参数非空判断
		if (!checkParams(tokenStr, platformSource, deviceType)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 验证平台来源
		if (PlatformSourceEnum.getBySource(platformSource) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//验证设备类型
		if (DeviceTypeEnum.getDeviceType(deviceType) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//token处理
		if(!StringUtils.isEmpty(tokenStr)){
			UserTradeBaseVo vo=null;
			try {
				vo=  authorityApiFacade.getUserInfoByToken(tokenStr);
				if(! (vo==null || vo.getCustId()==null) ){
					//获取用户id
					custId = vo.getCustId();
				}
			} catch (Exception e) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_14601.getErrorCode(),"通过token获取用户基本信息出错", response);
				return;
			}	
		}
		try{
			int messageNum=messageService.getMessageNum(custId, platformSource,deviceType);
			sender.put("messageNum", messageNum);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
