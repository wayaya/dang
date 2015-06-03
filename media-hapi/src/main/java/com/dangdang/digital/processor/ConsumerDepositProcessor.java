/**
 * Description: ConsumerDepositProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午4:35:50  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.IConsumerDepositApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.service.IIOSDepositFailService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 用户充值 All Rights Reserved.
 * 
 * @version 1.0 2014年12月9日 下午4:35:50 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("hapiconsumerDepositprocessor")
public class ConsumerDepositProcessor extends BaseApiProcessor {
	@Resource
	private IConsumerDepositApi consumerDepositApi;
	@Resource
	private ISystemApi systemApi;
	@Resource
	private IConsumerDepositService consumerDepositService;
	@Resource
	private IIOSDepositFailService iosDepositFailService;
	@Resource
	private IUserService userService;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConsumerDepositProcessor.class);
	private static final String CONSUME_DEPOSIT_MD5_CHECK = "consume_deposit_md5_check";

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		createConsumerDeposit(request, response, sender);
	}

	private void createConsumerDeposit(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		LogUtil.error(LOGGER, "充值开始，订单号：{0}",request.getParameter("depositOrderNo"));
		// 登陆验证
		String token = request.getParameter("token");
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		// 检查MD5参数签名
		// 获取开关
		String sysValue = systemApi.getProperty(CONSUME_DEPOSIT_MD5_CHECK);
		if (StringUtils.equals(sysValue, "1")
				&& !consumerDepositService.checkDepositMD5Sign(request)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_18011.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_18011.getErrorMessage(), response);
			return;
		}
		// 参数校验封装
		ConsumerDeposit consumerDeposit = new ConsumerDeposit();
		consumerDeposit.setCustId(custVo.getCustId());
		consumerDeposit.setUserName(custVo.getUsername());
		if (!consumerDepositService.checkAndEncapsulateParams(request,response, sender, consumerDeposit)) {
			LOGGER.error("充值参数校验失败，consumerDeposit=" + JSON.toJSONString(consumerDeposit));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} else {
			// 校验支付状态
			if (PayFromPaltform.YC_IOS.getSource().equals(consumerDeposit.getFromPaltform())
					|| PayFromPaltform.DS_IOS.getSource().equals(consumerDeposit.getFromPaltform())) {
				String receiptData = request.getParameter("receiptData");
				if (StringUtils.isBlank(receiptData)) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(),
							response);
					return;
				}
				String receiptOrderNo = "";
				try {
					receiptOrderNo = consumerDepositService
							.getOrderNo(receiptData);
				} catch (Exception e) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_18010.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_18010.getErrorMessage(),
							response);
					return;
				}
				if (StringUtils.isBlank(receiptOrderNo)) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_18010.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_18010.getErrorMessage(),
							response);
					return;
				} else {
					consumerDeposit.setDepositOrderNo("I" + receiptOrderNo);
				}
				Map<String, Object> result = consumerDepositService .getValidateResult(receiptData);
				LOGGER.info("IOS校验支付，result=" + JSON.toJSONString(result));
				if (result.get("status") == null || !"0".equals(result.get("status").toString())) {
					// 校验失败保存充值信息
					iosDepositFailService.saveFromConsumerDeposit(consumerDeposit, receiptData);
					sender.fail(ErrorCodeEnum.ERROR_CODE_18010.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_18010.getErrorMessage(),
							response);
					return;
				}
				consumerDeposit.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_CHECK_SUCCESS);
				Object isSanbox = result.get("isSandbox");
				if(isSanbox != null){
					consumerDeposit.setIsSandbox(Short.valueOf(isSanbox.toString()));
				}
			}
			// 验证充值记录是否已存在
			boolean isHas = false;
			try {
				List<ConsumerDeposit> result = consumerDepositService.findMasterListByParams("depositOrderNo",consumerDeposit.getDepositOrderNo());
				if (!CollectionUtils.isEmpty(result)) {
					isHas = true;
				}
			} catch (Exception e1) {
				LOGGER.info("验证充值记录是否已存在失败！", e1);
			}
			try {
				if (!isHas) {
					// 创建充值记录
					consumerDepositApi.createAndSaveConsumerDeposit(consumerDeposit);
				}
				// 查询账户余额
				UserTradeBaseVo vo = new UserTradeBaseVo();
				vo.setCustId(custVo.getCustId());
				vo.setUsername(custVo.getUsername());
				vo.setTradeType(Constans.TRADE_TYPE_QUERY);
				UserTradeBaseVo resultVo = null;
				try {
					resultVo = userService.tradeForUser(vo);
					if (resultVo == null || !resultVo.isTradeResult()) {
						throw new MediaException(
								ErrorCodeEnum.ERROR_CODE_18005.getErrorCode()
										+ "",
								ErrorCodeEnum.ERROR_CODE_18005
										.getErrorMessage());
					}
					sender.put("mainBalance", resultVo.getMainBalance());
					sender.put("subBalance", resultVo.getSubBalance());
					sender.put("mainBalanceIOS", resultVo.getMainBalanceIOS());
					sender.put("subBalanceIOS", resultVo.getSubBalanceIOS());
				} catch (Exception e) {
					LogUtil.error(LOGGER, e, "充值成功后查询余额失败！");
				}
				// 创建完毕并返回
				sender.success(response);
			} catch (MediaException me) {
				me.printStackTrace();
				LogUtil.error(LOGGER, me, "创建充值记录失败！");
				Integer errorCode = ErrorCodeEnum.ERROR_CODE_10000
						.getErrorCode();
				String errorMsg = ErrorCodeEnum.ERROR_CODE_10000
						.getErrorMessage();
				if (StringUtils.isNotBlank(me.getErrorCode())
						&& StringUtils.isNumeric(me.getErrorCode())) {
					errorCode = Integer.valueOf(me.getErrorCode());
				}
				if (StringUtils.isNotBlank(me.getErrorMsg())) {
					errorMsg = me.getErrorMsg();
				}
				sender.fail(errorCode, errorMsg, response);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.error(LOGGER, e, "创建充值记录失败！");
				sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(),
						response);
			}

		}
		LogUtil.error(LOGGER, "充值结束，订单号：{0}", request.getParameter("depositOrderNo"));
	}

}
