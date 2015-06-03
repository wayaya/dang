package com.dangdang.digital.processor.bookbar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 删帖接口
 * All Rights Reserved.
 * @version 1.0  2015-5-23 下午6:09:31  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapidelArticleprocessor")
public class DelArticleProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		sender.success(response);

	}

}
