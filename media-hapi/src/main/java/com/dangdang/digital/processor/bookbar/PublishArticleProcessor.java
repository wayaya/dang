package com.dangdang.digital.processor.bookbar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 发帖接口
 * All Rights Reserved.
 * @version 1.0  2015-5-23 下午6:07:42  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapipublishArticleprocessor")
public class PublishArticleProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		
		sender.success(response);

	}

}
