/**
 * Description: BuyMediaProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午10:36:03  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IConsumeApi;
import com.dangdang.digital.api.IOrderApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IConsumerConsumeService;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.CreateConsumeVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 原创阅读购买接口 All Rights Reserved.
 * 
 * @version 1.0 2014年11月25日 上午10:36:03 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("hapibuyMediaprocessor")
public class BuyMediaProcessor extends BaseApiProcessor {

	@Resource
	private IOrderMainService orderMainService;
	@Resource
	private IConsumerConsumeService consumerConsumeService;
	@Resource
	private IOrderApi orderApi;
	@Resource
	private IConsumeApi consumeApi;
	@Resource
	private ICacheApi cacheApi;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BuyMediaProcessor.class);

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		if (StringUtils.isBlank(request.getParameter("consumeType"))) {
			LogUtil.error(LOGGER, "参数：consumeType为空！");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} else if (Constans.BUY_FLAG_ORDER.equals(request
				.getParameter("consumeType"))) {
			createOrder(request, response, sender);
		} else {
			createConsume(request, response, sender);
		}
	}

	/**
	 * 
	 * Description: 订单购买逻辑
	 * 
	 * @Version1.0 2014年11月25日 上午11:22:33 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @throws Exception
	 */
	public void createOrder(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//登陆验证
		String token = request.getParameter("token");
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		// 参数校验封装
		CreateOrderVo createOrderVo = new CreateOrderVo();
		createOrderVo.setCustId(custVo.getCustId());
		createOrderVo.setUsername(custVo.getUsername());
		if (!orderMainService.checkAndEncapsulateParams(request, response, sender, createOrderVo, null)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} else {
			try {
				// 创建订单
				orderApi.createAndSaveOrder(createOrderVo);
				// 创建完毕并返回
				sender.success(response);
			} catch (MediaException me) {
				// 创建失败并返回
				LogUtil.error(LOGGER, me, "创建订单失败！");
				Integer errorCode = ErrorCodeEnum.ERROR_CODE_10000.getErrorCode();
				String errorMsg = ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage();
				if(StringUtils.isNotBlank(me.getErrorCode()) && StringUtils.isNumeric(me.getErrorCode())){
					errorCode = Integer.valueOf(me.getErrorCode());
				}
				if(StringUtils.isNotBlank(me.getErrorMsg())){
					errorMsg = me.getErrorMsg();
				}
				sender.fail(errorCode,errorMsg, response);
			} catch (Exception e) {
				// 创建失败并返回
				LogUtil.error(LOGGER, e, "创建订单失败！");				
				sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(),
						response);
			}
		}
	}

	/**
	 * 
	 * Description: 非订单购买逻辑
	 * 
	 * @Version1.0 2014年11月25日 上午11:22:39 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 */
	private void createConsume(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//登陆验证
		String token = request.getParameter("token");
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		// 参数校验封装
		CreateConsumeVo createConsumeVo = new CreateConsumeVo();
		createConsumeVo.setCustId(custVo.getCustId());
		createConsumeVo.setUsername(custVo.getUsername());
		if (!consumerConsumeService.checkAndEncapsulateParams(request,
				response, sender, createConsumeVo)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} else {
			try {
				// 创建消费记录
				if(createConsumeVo.getConsumeType().intValue() == Short.valueOf(Constans.BUY_FLAG_MONTHLY).intValue()){
					CreateConsumeVo createConsumeVoResult = consumeApi.createAndSaveConsume(createConsumeVo);
					sender.put("monthlyEndTime", createConsumeVoResult.getConsumerConsume().getMonthlyEndTime().getTime());
				}else{
					consumeApi.createAndSaveConsume(createConsumeVo);
				}
				// 创建完毕并返回
				sender.success(response);
			} catch (MediaException me) {
				// 创建失败并返回
				LogUtil.error(LOGGER, me, "创建消费记录失败！");
				Integer errorCode = ErrorCodeEnum.ERROR_CODE_10000.getErrorCode();
				String errorMsg = ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage();
				if(StringUtils.isNotBlank(me.getErrorCode()) && StringUtils.isNumeric(me.getErrorCode())){
					errorCode = Integer.valueOf(me.getErrorCode());
				}
				if(StringUtils.isNotBlank(me.getErrorMsg())){
					errorMsg = me.getErrorMsg();
				}
				sender.fail(errorCode,errorMsg, response);
			} catch (Exception e) {
				// 创建失败并返回
				LogUtil.error(LOGGER, e, "创建消费记录失败！");				
				sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(),
						response);
			}
		}
	}

}
