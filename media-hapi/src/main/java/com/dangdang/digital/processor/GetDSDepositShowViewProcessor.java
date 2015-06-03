/**
 * Description: GetDepositShowViewProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 下午3:31:51  by 张宪斌（zhangxianbin@dangdang.com）创建
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
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 获取用户充值选择界面数据
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 下午3:31:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("hapigetDSDepositShowViewprocessor")
public class GetDSDepositShowViewProcessor extends BaseApiProcessor {

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	@Resource
	private IActivityInfoService activityInfoService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetDSDepositShowViewProcessor.class);
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
			fromPaltform = PayFromPaltform.DS_ANDROID.getSource();
		}else if(PayFromPaltform.getBySource(fromPaltform) == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			sender.put("dSDepositPayInfoVo", activityInfoService.getDSConsumerDepositPayInfo(fromPaltform));
			sender.success(response);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "获取充值方式选择列表失败！");
			sender.fail(ErrorCodeEnum.ERROR_CODE_18006.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_18006.getErrorMessage(), response);
		}
	}

}
