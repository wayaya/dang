package com.dangdang.digital.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.ResultSender;

@Component("hapitestprocessor")
public class TestProcessor extends BaseApiProcessor {
	private final String jdbc_driver = ConfigPropertieUtils.getString("jdbc.driver"); // 需要在header中增加错误码接口
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		sender.put("test","test");
		sender.put("jdbc.driver",jdbc_driver);
		sender.success(response);
	}
}
