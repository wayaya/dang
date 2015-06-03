package com.dangdang.digital.processor;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.base.account.client.api.IAccountApi;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IUserApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.ConvertBeanToVo;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ReturnUserVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 获取用户的活动基本信息接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午6:31:45  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetUserInfoprocessor")
public class GetUserInfoProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserInfoProcessor.class);
	@Resource
	private ICacheApi cacheApi;  
	@Resource
	private ICacheService cacheService; 
	@Resource
	private IUserService userService;
	@Resource
	private IUserApi userApi;
	@Resource
	private IAccountApi accountApi;
	@Resource
	private IActivityUserService activityUserService;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		String token = request.getParameter("token");
		UserTradeBaseVo userVo = cacheApi.getUserWholeInfoByToken(token);
		
		if(null==userVo||!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		Long custId = 0L;
		try {
			//cacheApi.cleanUserWholeInfoFromCache(token);
			//获取用户id
			custId = userVo.getCustId();
			if(custId==0L){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			ReturnUserVo returnUserVo = ConvertBeanToVo.getReturnUserVo(userVo);
			//获取包月剩余时间
			//Long monthlyLeftTime = userApi.selectUserMonthly(custId);
			List<UserMonthly> listMonthly = userApi.selectUserMonthly(custId);
			if(null!=listMonthly&&listMonthly.size()>0){
				UserMonthly userMonthly = listMonthly.get(0);
				Date monthlyEndTime = userMonthly.getMonthlyEndTime();
				returnUserVo.setMonthlyEndTime(monthlyEndTime.getTime());
				String firstLoginFalg = cacheService.judgeFirstLoginPushMonthlyFlagCache(custId);
				returnUserVo.setFirstLoginGiving(firstLoginFalg);
				if("1".equals(firstLoginFalg)){
					returnUserVo.setFirstLoginGiving("1");
				}
				returnUserVo.setFirstLoginGivingDays(userMonthly.getFirstLoginGivingDays());
				returnUserVo.setFirstShareGivingDays(userMonthly.getFirstShareGivingDays());
			}
			//获取主辅账户余额
			UserTradeBaseVo userTradeBaseVo = new UserTradeBaseVo();
			userTradeBaseVo.setCustId(custId);
			userTradeBaseVo.setUsername(userVo.getUsername());
			userTradeBaseVo.setTradeType(Constans.TRADE_TYPE_QUERY);

			userTradeBaseVo = userService.tradeForUser(userTradeBaseVo);
//			AccountInfoVo accountInfoVo = accountApi.queryAccountInfoByCustId(custId);
//			if(null==accountInfoVo){
//				returnUserVo.setMainBalance(0);
//				returnUserVo.setSubBalance(0);
//			}else {
//				returnUserVo.setMainBalance(accountInfoVo.getMasterAccountMoney());
//				returnUserVo.setSubBalance(accountInfoVo.getAttachAccountMoney());
//			}
			
			
			ActivityUser activityUserInfo = activityUserService.selectActivityUserInfoFromMaster(custId);
			if(activityUserInfo!=null && activityUserInfo.getMediaActivityUserId()!=null){
				returnUserVo.setLotTimes(activityUserInfo.getLotTimes());
			}else{
				returnUserVo.setLotTimes(0);
			}
			
			
			returnUserVo.setMainBalance(userTradeBaseVo.getMainBalance());
			returnUserVo.setSubBalance(userTradeBaseVo.getSubBalance());
			returnUserVo.setMainBalanceIOS(userTradeBaseVo.getMainBalanceIOS());
			returnUserVo.setSubBalanceIOS(userTradeBaseVo.getSubBalanceIOS());
			returnUserVo.setNickname(null);
			sender.put("userInfo", returnUserVo);
			sender.success(response);
			
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,custId:" +custId);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
