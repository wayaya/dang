package com.dangdang.digital.processor.discovery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.dangdang.digital.constant.DigestDayOrNightEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 发现频道首页
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年5月13日 下午6:19:51  by 王冠华（wangguanhua@dangdang.com）创建
 */
@Component("hapigetDiscoveryHomePageInfoprocessor")
public class GetDiscoveryHomePageInfoProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(GetDiscoveryHomePageInfoProcessor.class);
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IDigestService digestService;
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//必传，需校验
		String token = request.getParameter("token");
		
		String lastRequestTime = request.getParameter("lastRequestTime");
		
		if(!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		// 未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		sender.put("fpHasNew", 1);
		sender.put("qxdHasNew", 1);
		sender.put("locState", 1);
		sender.success(response);
		return;
	}
	
}
