package com.dangdang.digital.processor.ucenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 个人发布帖子列表
 * All Rights Reserved.
 * @version 1.0  2015年5月13日 下午3:46:30  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetPostListprocessor")
public class GetPostListProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//用户id
		String custId = request.getParameter("custId");
		if(!checkParams(custId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		
		
		
	}

}
