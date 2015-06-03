package com.dangdang.digital.processor;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.ICoinsActivityService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description:撒金币接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午5:17:41  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapispreadCoinsprocessor")
public class SpreadCoinsProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpreadCoinsProcessor.class);
	
	@Resource
	private ICoinsActivityService coinsActivityService;
	@Resource
	private ICacheApi cacheApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		UserTradeBaseVo userVo = cacheApi.getUserWholeInfoByToken(token);
		if(null==userVo||!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		//设备类型
		String deviceType =request.getParameter("deviceType");
		String typeStr =request.getParameter("type");
		if(!checkParams(deviceType)||!checkParams(typeStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			//获取用户id
			Long custId = userVo.getCustId();
			String username = userVo.getUsername();
			String nickname = userVo.getNickname();
			int type = Integer.parseInt(typeStr);
			Map<String,Integer> resultMap = coinsActivityService.getSpreadCoins(custId,username,nickname,deviceType,type);
			int result = resultMap.get("coins") ;
			if(result == -1){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15820.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_15820.getErrorMessage(), response);
				return;
			}else if(result == -2){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15821.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_15821.getErrorMessage(), response);
				return;
			}else{
				sender.put("coins", result);
				sender.put("leftTimes", resultMap.get("leftTimes"));
				sender.success(response);
			}
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}	
	}
}
