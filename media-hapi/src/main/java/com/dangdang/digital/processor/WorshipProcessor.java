package com.dangdang.digital.processor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ActivityEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ActivityVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 膜拜接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午2:23:53  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapiworshipprocessor")
public class WorshipProcessor extends BaseApiProcessor {
	private static final Logger logger = LoggerFactory.getLogger(WorshipProcessor.class);
	
	@Resource
	private IActivityUserService activityUserService;
	@Resource
	private ICacheApi cacheApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
 		UserTradeBaseVo userVo = cacheApi.getUserWholeInfoByToken(token);
		
		String worshipedCustIdStr = request.getParameter("worshipedCustId");
		String worshipedUsernameStr = request.getParameter("worshipedUsername");
		//验证是否登陆
		if(!checkParams(worshipedCustIdStr)||!checkParams(worshipedUsernameStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		if(null==userVo||!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		String deviceType = request.getParameter("deviceType");
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			//获取用户id
			Long custId = userVo.getCustId();
			//获取昵称
			String nickname = userVo.getNickname();
			String username = userVo.getUsername();
			if(!checkParams(nickname)||custId==0L||!checkParams(deviceType)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			
			//不能膜拜自己
			if(custId.longValue()==DesUtils.decryptCustId(worshipedCustIdStr).longValue()){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15302.getErrorCode(),ErrorCodeEnum.ERROR_CODE_15302.getErrorMessage(), response);
				return;
			}
			ActivityVo activityVo = new ActivityVo(); 
			activityVo.setCustId(custId);
			activityVo.setNickname(nickname);
			activityVo.setUsername(username);
			activityVo.setWorshipedCustId(DesUtils.decryptCustId(worshipedCustIdStr));
			activityVo.setActivityType(ActivityEnum.WORSHIP_TYPE.getKey());
			activityVo.setWorshipedUsername(worshipedUsernameStr);
			activityVo.setDeviceType(deviceType);
			resultMap = activityUserService.worship(activityVo);
			String result = resultMap.get("result");
			if(!checkParams(result)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15400.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_15400.getErrorMessage(), response);
				return;
			}else if("day_limt".equals(result)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15303.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_15303.getErrorMessage(), response);
				return;
			}else {
				sender.put("coins", resultMap.get("cons"));
				sender.success(response);
			}
			
		} catch (NumberFormatException e) {
			LogUtil.error(logger, e, "参数错误,custId:" + request.getParameter("custId")+";worshipedCustId:" + request.getParameter("worshipedCustId"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
