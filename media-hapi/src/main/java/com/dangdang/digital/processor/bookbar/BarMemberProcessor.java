package com.dangdang.digital.processor.bookbar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 加入吧/退出吧/申请吧主接口
 * All Rights Reserved.
 * @version 1.0  2015-5-21 下午4:52:11  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapibarMemberprocessor")
public class BarMemberProcessor extends BaseApiProcessor{

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		sender.success(response);
	}

}
