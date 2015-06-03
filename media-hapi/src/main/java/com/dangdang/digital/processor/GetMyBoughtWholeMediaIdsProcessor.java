package com.dangdang.digital.processor;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.IOrderApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 获取购买全本的mediaId集合 All Rights Reserved.
 * 
 * @version 1.0 2015年2月6日 下午5:52:59 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMyBoughtWholeMediaIdsprocessor")
public class GetMyBoughtWholeMediaIdsProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetMyBoughtWholeMediaIdsProcessor.class);

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource
	private IOrderApi orderApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： token
		String token = request.getParameter("token");
		// 入参： 开始
		String lastRequestTimeStr = request.getParameter("lastRequestTime");

		if (!checkParams(token)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		String fromPaltform = request.getParameter("fromPaltform");		
		if(StringUtils.isBlank(fromPaltform)){
			fromPaltform = PayFromPaltform.YC.getSource();
		}else if(PayFromPaltform.getBySource(fromPaltform) == null){
			LOGGER.error("获取已购书的列表是参数错误，fromPaltform：" + fromPaltform);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Date updateTime=null; 
			if (StringUtils.isNotEmpty(lastRequestTimeStr)) {
				Long lastRequestTimeLong = Long.valueOf(lastRequestTimeStr);
				if (lastRequestTimeLong == null) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
					return;
				}
				if (!lastRequestTimeLong.equals(0l)) {
					updateTime = new Date(lastRequestTimeLong);
				}
			}
			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			List<Long> mediaIdList = orderApi.getMyBoughtWholeMediaIds(custVo.getCustId(),
					updateTime,fromPaltform);
			sender.put("mediaIdList", mediaIdList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
