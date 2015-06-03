package com.dangdang.digital.processor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.ConvertBeanToVo;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ActivityVo;
import com.dangdang.digital.vo.ReturnActivityUserVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 
 * All Rights Reserved 每天访问福袋页，赠送配置中的抽奖次数
 * @version 1.0  2015年1月5日 上午10:53:23  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetDaySendPrizeTimesprocessor")
public class GetDaySendPrizeTimesProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetDaySendPrizeTimesProcessor.class);
	
	@Resource
	private IActivityUserService activityUserService;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private ICacheService cacheService;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		UserTradeBaseVo userVo = cacheApi.getUserWholeInfoByToken(token);
		if(null==userVo||!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		try {
			//获取用户id
			Long custId = userVo.getCustId();
			String nickname = userVo.getNickname();
			//获取昵称
			if(custId==0L||!checkParams(custId+"")||!checkParams(nickname)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			ActivityVo activityVo = new ActivityVo();
			activityVo.setCustId(custId);
			activityVo.setActivityType(Constans.MEDIA_ACTIVITY_PUSHLOTS_TYPE);
			activityVo.setNickname(nickname);
			activityVo.setLottedTimes(0);
			int addTimes = activityUserService.sendLotTimes(activityVo);
			//查询用的活动属性信息
			ReturnActivityUserVo returnActivityUserVo = new ReturnActivityUserVo();
			if(addTimes>=1){//主库查询
				returnActivityUserVo = ConvertBeanToVo.getActivityUserVo(activityUserService.selectActivityUserInfoFromMaster(custId));
			}else{//缓存中获取
				returnActivityUserVo = cacheService.getActivityUserCache(custId);
			}
			sender.put("times",addTimes);
			sender.put("userActivityInfo", returnActivityUserVo);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}	
	}
}
