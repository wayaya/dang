/**
 * Description: GetBuyLuckyBagShowVoProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年12月17日 下午4:00:17  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IConsumeApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ConsumerConsumeShowVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月17日 下午4:00:17  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("hapigetBuyLuckyBagShowVoprocessor")
public class GetBuyLuckyBagShowVoProcessor extends BaseApiProcessor {
	@Resource
	private IConsumeApi consumeApi;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetBuyLuckyBagShowVoProcessor.class);
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
		ConsumerConsumeShowVo consumerConsumeShowVo = null;
		try {
			consumerConsumeShowVo = consumeApi.getConsumerConsumeShowVoByCustId(custVo.getCustId(),Constans.BUY_FLAG_LUCKYBAG,custVo.getUsername());
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "获取福袋道具列表失败！custId:{0}", custVo.getCustId());
			sender.fail(ErrorCodeEnum.ERROR_CODE_15011.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15011.getErrorMessage(), response);
		}
		if(consumerConsumeShowVo != null){
			consumerConsumeShowVo.setCustId(custVo.getCustId());
			consumerConsumeShowVo.setUsername(custVo.getUsername());
			sender.put("consumerConsumeShowVo", consumerConsumeShowVo);
			sender.success(response);
		}else{
			LogUtil.error(LOGGER, "获取福袋道具列表失败！custId:{0}", custVo.getCustId());
			sender.fail(ErrorCodeEnum.ERROR_CODE_15011.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15011.getErrorMessage(), response);
		}
	}

}
