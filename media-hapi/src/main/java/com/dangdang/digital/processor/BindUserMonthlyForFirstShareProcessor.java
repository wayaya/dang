/**
 * Description: BindUserMonthlyForFirstShareProcessor.java
 * All Rights Reserved.
 * @version 1.0  2015年1月16日 上午11:32:07  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.BindUserMediaAuthorityResultVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 首次分享送包月
 * All Rights Reserved.
 * @version 1.0  2015年1月16日 上午11:32:07  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("hapibindUserMonthlyForFirstShareprocessor")
public class BindUserMonthlyForFirstShareProcessor extends BaseApiProcessor {

	@Resource(name = "userAuthorityApi")
	private IUserAuthorityApi userAuthorityApi;
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BindUserMonthlyForFirstShareProcessor.class);
	
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
		try {
			BindUserMediaAuthorityResultVo result = userAuthorityApi.bindUserMonthlyForFirstShare(custVo.getCustId(),custVo.getNickname());
			if(result != null && result.getUserMonthly() != null && result.getUserMonthly().getMonthlyEndTime() != null){
				sender.put("monthlyEndTime", result.getUserMonthly().getMonthlyEndTime().getTime());
				if(result.getUserMonthly().getFirstShareGivingDays() != null){
					sender.put("firstShareGivingDays", result.getUserMonthly().getFirstShareGivingDays());
				}
				if(result.getUserMonthly().getTimeShareGivingDays() != null){
					sender.put("timeShareGivingDays", result.getUserMonthly().getTimeShareGivingDays());
				}
				sender.success(response);
			}else{
				sender.fail(ErrorCodeEnum.ERROR_CODE_18009.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_18009.getErrorMessage(), response);
			}			
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "分享送包月失败！custId:{0}", custVo.getCustId());
			sender.fail(ErrorCodeEnum.ERROR_CODE_18009.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_18009.getErrorMessage(), response);
		}
	}

}
