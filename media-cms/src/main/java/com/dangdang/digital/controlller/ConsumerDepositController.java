/**
 * Description: ConsumerDepositController.java
 * All Rights Reserved.
 * @version 1.0  2015年1月6日 下午6:34:07  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.controlller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.base.account.client.commons.enums.AccountTypeEnum;
import com.dangdang.base.account.client.commons.enums.ConsumeTypeEnum;
import com.dangdang.base.account.client.vo.AccountConsumeItemsVo;
import com.dangdang.base.commons.utils.LogUtil;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.constant.OperateTargetTypeEnum;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.constant.RechargeActivityCodeEnum;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.service.IManagerOperateLogService;
import com.dangdang.digital.service.ISysPropertiesService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.UsercmsSessionInfo;
import com.dangdang.digital.vo.OrderInfoVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 用户充值记录controller All Rights Reserved.
 * 
 * @version 1.0 2015年1月6日 下午6:34:07 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Controller
@RequestMapping("consumerDeposit")
public class ConsumerDepositController extends BaseController {

	@Resource(name = "cacheApi")
	private ICacheApi cacheApi;
	@Resource
	private ISysPropertiesService sysPropertiesService;
	@Resource
	private IConsumerDepositService consumerDepositService;
	@Resource
	private IManagerOperateLogService managerOperateLogService;
	@Resource
	private IUserService userService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	//充值结果：充值成功
	private static final int CONSUME_RESULT_SUCCESS = 1;
	
	private static final String TO_DEPOSIT_USER_KEY = "to_deposit_user_loginname";
	
	private static long lastModify = 0;

	@RequestMapping("list")
	public String list(Model model, Query query, ConsumerDeposit consumerDeposit) {
		model.addAttribute("pageFinder", consumerDepositService
				.findPageFinderObjs(consumerDeposit, query));
		model.addAttribute("vo", consumerDeposit);
		return "consumerDeposit/list";
	}

	@RequestMapping("detail")
	public String detail(Model model, Long consumerDepositId) {
		String errorMsg = "";
		if (consumerDepositId != null) {
			ConsumerDeposit consumerDeposit = consumerDepositService
					.get(consumerDepositId);
			if (consumerDeposit == null) {
				errorMsg += "[充值记录信息不存在！]";
			}else{
				model.addAttribute("consumerDeposit", consumerDeposit);
				model.addAttribute("managerOperateLogs",
						managerOperateLogService.findListByParams(
								"operateTargetId", consumerDepositId,
								"operateTargetType",
								OperateTargetTypeEnum.CONSUMER_DEPOSIT
										.getType()));
			}			
		} else {
			errorMsg += "[充值记录id为null!]";
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			model.addAttribute("errorMsg", errorMsg);
		}
		return "consumerDeposit/detail";
	}

	@RequestMapping("abnormalList")
	public String abnormalList(Model model, Query query,
			ConsumerDeposit consumerDeposit) {
		consumerDeposit
				.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_CHECK_FAIL);
		model.addAttribute("pageFinder", consumerDepositService
				.findPageFinderObjs(consumerDeposit, query));
		model.addAttribute("vo", consumerDeposit);
		return "consumerDeposit/abnormalList";
	}

	@RequestMapping("abnormalDetail")
	public String abnormalDetail(Model model, Long consumerDepositId) {
		String errorMsg = "";
		if (consumerDepositId != null) {
			ConsumerDeposit consumerDeposit = consumerDepositService
					.get(consumerDepositId);
			if (consumerDeposit == null || consumerDeposit.getCustId() == null
					|| StringUtils.isBlank(consumerDeposit.getDepositNo())
					|| StringUtils.isBlank(consumerDeposit.getDepositOrderNo())) {
				errorMsg += "[充值记录信息不存在！]";
			} else {
				model.addAttribute("consumerDeposit", consumerDeposit);
				// 查询用户充值明细
				UserTradeBaseVo vo = new UserTradeBaseVo();
				vo.setCustId(consumerDeposit.getCustId());
				vo.setSourceOrderNo(consumerDeposit.getDepositNo());
				List<AccountConsumeItemsVo> accountConsumes = userService
						.queryAccountConsumeItemsList(vo);
				model.addAttribute("accountConsumes", accountConsumes);
				// 查询充值订单信息
				OrderInfoVo orderInfoVo = consumerDepositService
						.findOrderInfoVoByDepositOrderNo(consumerDeposit
								.getDepositOrderNo());
				model.addAttribute("orderInfoVo", orderInfoVo);
				//计算需要修复的账户金额
				model.addAttribute(
						"changeMoneyMap",
						calculateMoney(consumerDeposit, accountConsumes));
			}
		} else {
			errorMsg += "[充值记录id为null!]";
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			model.addAttribute("errorMsg", errorMsg);
		}
		return "consumerDeposit/abnormalDetail";
	}
	
	/**
	 * 
	 * Description: 处理异常充值记录
	 * @Version1.0 2015年1月14日 上午10:52:11 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consumerDepositId
	 * @return
	 */
	@RequestMapping("handleAbnormalDeposit")
	@ResponseBody
	public String handleAbnormalDeposit(Long consumerDepositId,
			Integer changeMainMoney, Integer changeSubMoney, Long custId,
			String depositNo,String userName,String deviceType,String fromPaltform) {
		String errorMsg = "success";
		//封装日志实体
		ManagerOperateLog managerOperateLog = new ManagerOperateLog();
		managerOperateLog.setOperateResult(Constans.MANAGER_OPERATE_RESULT_SUCCESS);
		managerOperateLog.setOperateDescription("处理异常充值记录操作");
		managerOperateLog.setOperateTargetId(consumerDepositId);
		managerOperateLog.setOperateTargetType(OperateTargetTypeEnum.CONSUMER_DEPOSIT.getType());
		managerOperateLog.setOperator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName() : "system");
		//修复账户余额
		try {
			Map<String, Integer> changeMoneyMap = new HashMap<String, Integer>();
			changeMoneyMap.put("changeMainMoney", changeMainMoney);
			changeMoneyMap.put("changeSubMoney", changeSubMoney);
			managerOperateLog.setOperateDescription(managerOperateLog
					.getOperateDescription()
					+ "，更改主账户阅读币："
					+ changeMoneyMap.get("changeMainMoney")
					+ "，更改附账户阅读币："
					+ changeMoneyMap.get("changeSubMoney"));
			consumeAccount(changeMoneyMap,custId,depositNo,userName,deviceType,fromPaltform);
			//更新充值记录状态并记录日志
			ConsumerDeposit consumerDeposit = new ConsumerDeposit();
			consumerDeposit.setConsumerDepositId(consumerDepositId);
			consumerDeposit.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_CHECK_SUCCESS);			
			consumerDepositService.update(consumerDeposit);			
		} catch (Exception e) {
			managerOperateLog.setOperateResult(Constans.MANAGER_OPERATE_RESULT_FAIL);
			errorMsg = "处理异常充值记录操作失败！";
			LogUtil.error(logger, e, "处理异常充值记录操作失败！");			
		} finally{
			boolean result = managerOperateLogService.insertOperateLog(managerOperateLog);
			if(!result){
				if(managerOperateLog.getOperateResult() == Constans.MANAGER_OPERATE_RESULT_FAIL){
					errorMsg = "处理异常充值记录操作失败！保存操作日志失败！";
				}else{
					errorMsg = "处理异常充值记录操作成功！保存操作日志失败！";
				}
			}
		}
		return errorMsg;
	}
	
	/**
	 * 
	 * Description:作废充值记录 
	 * @Version1.0 2015年1月14日 上午10:51:56 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consumerDepositId
	 * @return
	 */
	@RequestMapping("cancelAbnormalDeposit")
	@ResponseBody
	public String cancelAbnormalDeposit(Long consumerDepositId,Long custId,String depositNo,String userName,String deviceType,String fromPaltform) {
		String errorMsg = "success";
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setCustId(custId);
		vo.setSourceOrderNo(depositNo);
		//封装日志实体
		ManagerOperateLog managerOperateLog = new ManagerOperateLog();
		managerOperateLog.setOperateResult(Constans.MANAGER_OPERATE_RESULT_SUCCESS);
		managerOperateLog.setOperateDescription("作废充值记录操作");
		managerOperateLog.setOperateTargetId(consumerDepositId);
		managerOperateLog.setOperateTargetType(OperateTargetTypeEnum.CONSUMER_DEPOSIT.getType());
		//修复账户余额
		try {
			managerOperateLog.setOperator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName() : "system");
			Map<String, Integer> changeMoneyMap = calculateChangeMoney(userService.queryAccountConsumeItemsList(vo));
			changeMoneyMap.put("changeMainMoney", 0 - changeMoneyMap.get("changeMainMoney"));
			changeMoneyMap.put("changeSubMoney", 0 - changeMoneyMap.get("changeSubMoney"));
			managerOperateLog.setOperateDescription(managerOperateLog
					.getOperateDescription()
					+ "，更改主账户阅读币："
					+ changeMoneyMap.get("changeMainMoney")
					+ "，更改附账户阅读币："
					+ changeMoneyMap.get("changeSubMoney"));
			consumeAccount(changeMoneyMap,custId,depositNo,userName,deviceType,fromPaltform);
			//更新充值记录状态并记录日志
			ConsumerDeposit consumerDeposit = new ConsumerDeposit();
			consumerDeposit.setConsumerDepositId(consumerDepositId);
			consumerDeposit.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_DEPOSIT_CANCEL);
			consumerDepositService.update(consumerDeposit);	
		} catch (Exception e) {
			managerOperateLog.setOperateResult(Constans.MANAGER_OPERATE_RESULT_FAIL);
			errorMsg = "作废充值记录操作失败！";
			LogUtil.error(logger, e, "作废充值记录操作失败！");
		} finally{
			boolean result = managerOperateLogService.insertOperateLog(managerOperateLog);
			if(!result){
				if(managerOperateLog.getOperateResult() == Constans.MANAGER_OPERATE_RESULT_FAIL){
					errorMsg = "作废充值记录操作失败！保存操作日志失败！";
				}else{
					errorMsg = "作废充值记录操作成功！保存操作日志失败！";
				}
			}
		}
		return errorMsg;
	}


	/**
	 * 
	 * Description: 计算需要修复的账户金额
	 * 
	 * @Version1.0 2015年1月13日 下午2:55:44 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consumerDeposit
	 * @param accountConsumes
	 * @param orderInfoVo
	 * @return
	 */
	private Map<String, Integer> calculateMoney(
			ConsumerDeposit consumerDeposit,
			List<AccountConsumeItemsVo> accountConsumes) {
		Map<String, Integer> changeMoneyMap = calculateChangeMoney(accountConsumes);
		//计算需要修复的账户金额
		if (consumerDeposit != null) {
			changeMoneyMap.put("changeMainMoney", consumerDeposit.getMainGold()
					- changeMoneyMap.get("changeMainMoney").intValue());
			changeMoneyMap.put("changeSubMoney", consumerDeposit.getSubGold()
					- changeMoneyMap.get("changeSubMoney").intValue());
		}
		return changeMoneyMap;
	}
	
	/**
	 * 
	 * Description: 获取充值明细汇总金额
	 * @Version1.0 2015年1月14日 下午2:51:50 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param accountConsumes
	 * @return
	 */
	private Map<String, Integer> calculateChangeMoney(List<AccountConsumeItemsVo> accountConsumes){
		Map<String, Integer> changeMoneyMap = new HashMap<String, Integer>();
		//计算充值明细充值总金额
		int depositMainAccount = 0;
		int depositSubAccount = 0;
		if(!CollectionUtils.isEmpty(accountConsumes)){
			for(AccountConsumeItemsVo accountConsume : accountConsumes){
				//消费明细
				if (accountConsume.getConsumeResult() != null
						&& accountConsume.getConsumeResult() == CONSUME_RESULT_SUCCESS
						&& StringUtils.equals(accountConsume.getConsumeType(),
								ConsumeTypeEnum.CONSUME_TYPE_CONSUME
										.getConsumeType())) {
					//主账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_MASTER
							.getAccountType())){
						depositMainAccount = depositMainAccount - accountConsume.getConsumeMoney();
					}
					//副账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_ATTACH
							.getAccountType())){
						depositSubAccount = depositSubAccount - accountConsume.getConsumeMoney();
					}
				}
				//充值明细
				if (accountConsume.getConsumeResult() != null
						&& accountConsume.getConsumeResult() == CONSUME_RESULT_SUCCESS
						&& StringUtils.equals(accountConsume.getConsumeType(),
								ConsumeTypeEnum.CONSUME_TYPE_TOP_UP
										.getConsumeType())) {
					//主账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_MASTER
							.getAccountType())){
						depositMainAccount = depositMainAccount + accountConsume.getConsumeMoney();
					}
					//副账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_ATTACH
							.getAccountType())){
						depositSubAccount = depositSubAccount + accountConsume.getConsumeMoney();
					}
				}
			}
		}
		changeMoneyMap.put("changeMainMoney", depositMainAccount);
		changeMoneyMap.put("changeSubMoney", depositSubAccount);
		return changeMoneyMap;
	}
	
	/**
	 * 
	 * Description: 账户余额修复
	 * @Version1.0 2015年1月14日 下午3:03:06 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param changeMoneyMap
	 */
	private void consumeAccount(Map<String, Integer> changeMoneyMap,Long custId,String depositNo,String userName,String deviceType,String fromPaltform) throws Exception{
		Integer changeMainMoney = changeMoneyMap.get("changeMainMoney");
		Integer changeSubMoney = changeMoneyMap.get("changeSubMoney");
		if(changeMainMoney != null && changeMainMoney != 0){			
			if(changeMainMoney > 0){
				UserTradeBaseVo vo = new UserTradeBaseVo();
				vo.setCustId(custId);
				vo.setConsumeSource(deviceType);
				vo.setSourceOrderNo(depositNo);
				vo.setUsername(userName);
				vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);				
				vo.setRequireRechargeMainAmount(changeMainMoney);
				vo.setFromPaltform(fromPaltform);
				UserTradeBaseVo resultVo = userService.tradeForUser(vo);
				if(resultVo == null || !resultVo.isTradeResult()){
					throw new Exception("调用用户交易接口失败");
				}
			}else{		
				UserTradeBaseVo vo = new UserTradeBaseVo();
				vo.setCustId(custId);
				vo.setConsumeSource(deviceType);
				vo.setSourceOrderNo(depositNo);
				vo.setUsername(userName);
				vo.setTradeType(Constans.TRADE_TYPE_CONSUME);
				vo.setOnlyPayMainAmount(true);				
				vo.setRequirePayAmount(0 - changeMainMoney);
				vo.setFromPaltform(fromPaltform);
				UserTradeBaseVo resultVo = userService.tradeForUser(vo);
				if(resultVo == null || !resultVo.isTradeResult()){
					throw new Exception("调用用户交易接口失败");
				}
			}
		}
		if(changeSubMoney != null && changeSubMoney != 0){			
			if(changeSubMoney > 0){
				UserTradeBaseVo vo = new UserTradeBaseVo();
				vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
				vo.setCustId(custId);
				vo.setConsumeSource(deviceType);
				vo.setSourceOrderNo(depositNo);
				vo.setUsername(userName);
				vo.setRequireRechargeSubAmount(changeSubMoney);
				vo.setFromPaltform(fromPaltform);
				vo.setActivityCode(RechargeActivityCodeEnum.BUDAN.getCode());
				UserTradeBaseVo resultVo = userService.tradeForUser(vo);
				if(resultVo == null || !resultVo.isTradeResult()){
					throw new Exception("调用用户交易接口失败");
				}
			}else{
				UserTradeBaseVo vo = new UserTradeBaseVo();
				vo.setTradeType(Constans.TRADE_TYPE_CONSUME);
				vo.setOnlyPayMainAmount(false);
				vo.setCustId(custId);
				vo.setConsumeSource(deviceType);
				vo.setSourceOrderNo(depositNo);
				vo.setUsername(userName);
				vo.setRequirePayAmount(0 - changeSubMoney);
				vo.setFromPaltform(fromPaltform);
				UserTradeBaseVo resultVo = userService.tradeForUser(vo);
				if(resultVo == null || !resultVo.isTradeResult()){
					throw new Exception("调用用户交易接口失败");
				}
			}
		}
	}
	
	@RequestMapping("toDeposit")
	public String toDeposit() {		
		if (!this.checkAdmin()) {
			return "main";
		}		
		return "consumerDeposit/toDeposit";
	}
	
	@RequestMapping("handleDeposit")
	@ResponseBody
	public String handleDeposit(String custId,Integer mainPrice,Integer subPrice,String deviceType) {
		if (!this.checkAdmin()) {
			return "main";
		}
		String errorMsg = "";
		if(StringUtils.isBlank(custId)){
			errorMsg = "用户id不可为空！";
		}
		if(mainPrice == null && subPrice == null){
			errorMsg = "金铃铛数量和因铃铛数量不可同时为空！";
		}
		if(StringUtils.isBlank(deviceType)){
			errorMsg = "设备类型不可为空！";
		}
		if(System.currentTimeMillis() - lastModify < 5000 ){
			errorMsg = "冷却时间未到，请等待五秒钟！";
		}else{
			lastModify = System.currentTimeMillis();
		}	
		StringBuffer failCustIds = new StringBuffer();
		if(StringUtils.isBlank(errorMsg)){
			String fromPlatform = PayFromPaltform.DS_ANDROID.getSource();
			if("iphone".equals(deviceType)){
				fromPlatform = PayFromPaltform.DS_IOS.getSource();
			}
			Map<String, Integer> changeMoneyMap = new HashMap<String, Integer>();
			changeMoneyMap.put("changeMainMoney", mainPrice != null ? mainPrice : 0);
			changeMoneyMap.put("changeSubMoney", subPrice != null ? subPrice : 0);
			Set<String> tempCustIds = new HashSet<String>();
			for (String subCustId : custId.split("\n|\r")) {
				subCustId = subCustId.trim();
				if(StringUtils.isBlank(subCustId)){
					continue;
				}
				if(!StringUtils.isNumeric(subCustId)){
					failCustIds.append(subCustId).append(",");
					continue;
				}
				if(tempCustIds.contains(subCustId)){
					continue;
				}
				tempCustIds.add(subCustId);
				String userName = "temp_user_" + subCustId;
				try {
					Map<String, String> userInfo = cacheApi
							.getUserInfoByCustId(Long.valueOf(subCustId));
					if(!CollectionUtils.isEmpty(userInfo) && StringUtils.isNotBlank(userInfo.get("nickName"))){
						userName = userInfo.get("nickName");
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				String depositNo = "TE" + subCustId + System.currentTimeMillis();
				//封装日志实体
				ManagerOperateLog managerOperateLog = new ManagerOperateLog();
				managerOperateLog
						.setOperateResult(Constans.MANAGER_OPERATE_RESULT_SUCCESS);
				managerOperateLog.setOperateDescription("人工给用户账户充值操作");
				managerOperateLog.setOperateTargetId(-1L);
				managerOperateLog
						.setOperateTargetType(OperateTargetTypeEnum.CONSUMER_DEPOSIT
								.getType());
				managerOperateLog.setOperateDescription(managerOperateLog
						.getOperateDescription()
						+ "，更改主账户阅读币："
						+ changeMoneyMap.get("changeMainMoney")
						+ "，更改附账户阅读币："
						+ changeMoneyMap.get("changeSubMoney")
						+ "，设备类型："
						+ deviceType
						+ "，custId："
						+ subCustId
						+ "，操作人ip："
						+ getRemoteHost() + "，充值号：" + depositNo);
				managerOperateLog.setOperator(subCustId);
				managerOperateLog.setCreationDate(new Date());
				try {
					consumeAccount(changeMoneyMap, Long.valueOf(subCustId), depositNo, userName,
							deviceType, fromPlatform);
				} catch (Exception e) {
					e.printStackTrace();
					failCustIds.append(subCustId).append(",");
					managerOperateLog
							.setOperateResult(Constans.MANAGER_OPERATE_RESULT_FAIL);
				} finally {
					boolean result = managerOperateLogService
							.insertOperateLog(managerOperateLog);
					if (!result) {
						if (managerOperateLog.getOperateResult() == Constans.MANAGER_OPERATE_RESULT_FAIL) {
							errorMsg += "人工给用户账户充值操作失败！保存操作日志失败！";
						} else {
							errorMsg += "人工给用户账户充值操作成功！保存操作日志失败！";
						}
					}
				}
			}
			if(StringUtils.isBlank(failCustIds.toString())){
				errorMsg += "人工给用户账户充值操作成功！";
			}else{
				errorMsg += "人工给用户账户充值操作失败！失败custIds:" + failCustIds.substring(0, failCustIds.length() - 1);
			}
		}			
		return errorMsg;
	}
	
	private boolean checkAdmin(){
		UsercmsSessionInfo usercmsSessionInfo = (UsercmsSessionInfo) getRequest()
				.getSession().getAttribute(DigitalCmsConstants.CMS_USER_INFO_STORED_IN_SESSION);
		if(usercmsSessionInfo != null && usercmsSessionInfo.getUserInfo() != null){
			String loginName = usercmsSessionInfo.getUserInfo().getLoginName();
			String sysUser = sysPropertiesService.getValue(TO_DEPOSIT_USER_KEY);
			if(StringUtils.isBlank(sysUser)){
				sysUser = "admin";
			}
			for(String sysLoginName : sysUser.split(",")){
				if(StringUtils.equals(sysLoginName, loginName)){
					return true;
				}
			}
		}
		return false;
	}
	
	public String getRemoteHost(){
		HttpServletRequest request = getRequest();
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
}
