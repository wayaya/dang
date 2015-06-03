package com.dangdang.digital.processor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.ResultSender;

/**
 * 
 * Description: 微信开发者平台URL验证接口
 * All Rights Reserved.
 * @version 1.0  2015年3月17日 下午3:22:01  by 魏嵩（weisong@dangdang.com）创建
 */
@Component("hapidevAuthprocessor")
public class WechatDeveloperAuthProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatDeveloperAuthProcessor.class);
	
	@SuppressWarnings("unchecked")
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		String token = ConfigPropertieUtils.getString("media.wechat.developer.validationtoken","f9d2b06640b47");
		
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		LOGGER.info("signature:"+signature);
		LOGGER.info("timestamp:"+timestamp);
		LOGGER.info("nonce:"+nonce);
		LOGGER.info("echostr:"+echostr);
		
		
		String[] parameters = {token, timestamp, nonce };
		Arrays.sort(parameters);
		
		StringBuilder sb = new StringBuilder();
		for(String parameter: parameters){
			sb.append(parameter);
		}
		
		LOGGER.info("sorted string after append:"+sb.toString());
		
		String signatureGenerated = new String(DigestUtils.shaHex(sb.toString()));
		
		LOGGER.info("signatureGenerated:"+signatureGenerated);
		
		if(StringUtils.isNotEmpty(signature)&& signature.equals(signatureGenerated)){
			response.getWriter().write(echostr);
		}else{
			response.getWriter().write("");
		}
		
		response.getWriter().flush();
		response.getWriter().close();
	}
}
