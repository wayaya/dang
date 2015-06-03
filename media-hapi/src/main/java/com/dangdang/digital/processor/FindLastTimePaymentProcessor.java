/**
 * Description: FindLastTimePaymentProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年12月11日 下午6:20:38  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 获取用户最后一次充值方式
 * All Rights Reserved.
 * @version 1.0  2014年12月11日 下午6:20:38  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("hapifindLastTimePaymentprocessor")
public class FindLastTimePaymentProcessor extends BaseApiProcessor {

	@Resource
	private IConsumerDepositService consumerDepositService;
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FindLastTimePaymentProcessor.class);
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
		String fromPaltform = request.getParameter("fromPaltform");
		if(StringUtils.isBlank(fromPaltform)){
			fromPaltform = PayFromPaltform.YC_ANDROID.getSource();
		}else if(PayFromPaltform.getBySource(fromPaltform) == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			ConsumerDeposit consumerDeposit = consumerDepositService.findLastTimePayment(custVo.getCustId(),fromPaltform);
			if(consumerDeposit != null){
				if (StringUtils.isNotBlank(consumerDeposit.getPayment())
						&& StringUtils.isNumeric(consumerDeposit.getPayment())) {					
					sender.put("payment", activityInfoService.getConsumerDepositPayMent(Integer
							.valueOf(consumerDeposit.getPayment()),fromPaltform));
				}else{
					LogUtil.error(LOGGER,"获取最近一次支付方式失败,充值记录数据不完整！custId:{0}", custVo.getCustId());
					sender.fail(ErrorCodeEnum.ERROR_CODE_18003.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_18003.getErrorMessage(), response);
					return;
				}				
			}
			sender.success(response);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e,"获取最近一次支付方式失败！custId:{0}", custVo.getCustId());
			sender.fail(ErrorCodeEnum.ERROR_CODE_18003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_18003.getErrorMessage(), response);
		}
	}

}
