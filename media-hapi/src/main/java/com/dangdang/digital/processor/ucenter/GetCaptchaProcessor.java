package com.dangdang.digital.processor.ucenter;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.ucenter.api.service.IUcenterLoginApi;
import com.dangdang.ucenter.utils.Base64Utils;
import com.dangdang.ucenter.utils.SCaptcha;

/**
 * Description: 获取登陆验证码
 * All Rights Reserved.
 * @version 1.0  2015年6月1日 下午5:09:25  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetCaptchaprocessor")
public class GetCaptchaProcessor extends BaseApiProcessor {
	
	@Autowired
	private IUcenterLoginApi ucenterLoginApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String deviceNo = request.getParameter("deviceSerialNo");
		checkParams(deviceNo);
		
		// 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg;text");  
        // 禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0); 
        String randomCode = ucenterLoginApi.getDDLoginRandomCode();
        SCaptcha instance = new SCaptcha(randomCode);  
        //获取待输入的验证码
        String code = instance.getCode();
        //待输入的验证码入缓存，登录输入时做校验
        ucenterLoginApi.putCode2Cache(deviceNo, code);
        List<String> letters = ucenterLoginApi.getCodeAndLetters(code);
        String ls = StringUtils.join(letters, ",");
        response.setHeader("codes", Base64Utils.encodeBytes(ls.getBytes("utf-8")));
        OutputStream sos = response.getOutputStream();
        instance.write(sos);
	}
	
}
