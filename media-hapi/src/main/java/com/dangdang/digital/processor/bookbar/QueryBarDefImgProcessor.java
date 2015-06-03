package com.dangdang.digital.processor.bookbar;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 获取吧默认背景图片接口
 * All Rights Reserved.
 * @version 1.0  2015-5-28 下午5:12:08  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapiqueryBarDefImgprocessor")
public class QueryBarDefImgProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		list.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		list.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		sender.put( "barDefImg", list);
		sender.success(response);
	}

}
