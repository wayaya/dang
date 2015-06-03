package com.dangdang.digital.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.ResultSender;

@Component
public class UnknownApiProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		String action = request.getParameter("action");
		sender.fail(ErrorCodeEnum.ERROR_CODE_10011.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10011.getErrorMessage()
				+ ": " + action, response);
	}
}
