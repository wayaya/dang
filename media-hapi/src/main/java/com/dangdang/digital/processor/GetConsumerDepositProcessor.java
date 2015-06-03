/**
 * Description: GetConsumerDepositProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年12月17日 下午2:29:53  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 获取用户的充值记录
 * All Rights Reserved.
 * @version 1.0  2014年12月17日 下午2:29:53  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("hapigetConsumerDepositprocessor")
public class GetConsumerDepositProcessor extends BaseApiProcessor {
	@Resource
	private IConsumerDepositService consumerDepositService;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetConsumerDepositProcessor.class);
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//登陆验证
		String token = request.getParameter("token");
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}	
		//start,end请求参数判断
		//栏目类型标识
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		if (!StringUtils.isNumeric(start)
				|| !StringUtils.isNumeric(end)
				|| Integer.valueOf(start) < 0
				|| Integer.valueOf(end).intValue() < Integer.valueOf(start)
						.intValue()) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		Integer startInt = null;
		Integer endInt = null;
		Integer count = null;
		if (StringUtils.isNumeric(start)
				&& StringUtils.isNumeric(end)
				&& Integer.valueOf(start) >= 0
				&& Integer.valueOf(end).intValue() >= Integer.valueOf(start)
						.intValue()) {
			startInt = Integer.valueOf(start);
			endInt = Integer.valueOf(end);
			count = endInt - startInt + 1;
		}
		String fromPaltform = request.getParameter("fromPaltform");
		if(StringUtils.isBlank(fromPaltform)){
			fromPaltform = PayFromPaltform.YC_ANDROID.getSource();
		}else if(PayFromPaltform.getBySource(fromPaltform) == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			sender.put("consumerDeposits", consumerDepositService.findConsumerDepositPageByCust(custVo.getCustId(),startInt,count,fromPaltform));
			sender.put("totalCount",consumerDepositService.findConsumerDepositCountByCust(custVo.getCustId(),fromPaltform));
			sender.success(response);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "查询用户充值记录失败！custId:{0}", custVo.getCustId());
			sender.fail(ErrorCodeEnum.ERROR_CODE_18004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_18004.getErrorMessage(), response);
		}
	}

}
