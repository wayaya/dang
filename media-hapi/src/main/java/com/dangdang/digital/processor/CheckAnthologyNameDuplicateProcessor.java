package com.dangdang.digital.processor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 校验同一个账号文集名称重复
 * All Rights Reserved.
 * @version 1.0  2015年3月10日 下午3:40:54  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapicheckAnthologyNameDuplicateprocessor")
public class CheckAnthologyNameDuplicateProcessor extends BaseApiProcessor {
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IAnthologyService anthologyService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String anthologyName = request.getParameter("anthologyName");
		
		if(!checkParams(token, anthologyName)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		Map<String, Integer> checkMap = anthologyService.checkAnthologyName(custVo.getCustId(), anthologyName.trim());
		if(!checkMap.isEmpty() && checkMap.containsKey("errorMsg")){
			Integer errorCode = checkMap.get("errorMsg");
			ErrorCodeEnum errorEnum = ErrorCodeEnum.getByErrorCode(errorCode);
			sender.fail(errorCode, errorEnum.getErrorMessage(), response);
			return;
		}else{
			sender.success(response);
			return;
		}
	}

}
