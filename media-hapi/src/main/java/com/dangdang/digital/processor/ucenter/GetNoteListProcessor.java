package com.dangdang.digital.processor.ucenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 个人笔记列表
 * All Rights Reserved.
 * @version 1.0  2015年5月13日 下午3:49:07  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetNoteListprocessor")
public class GetNoteListProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		// TODO Auto-generated method stub

	}

}
