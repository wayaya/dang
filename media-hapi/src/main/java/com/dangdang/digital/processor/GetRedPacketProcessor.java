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
import com.dangdang.digital.service.IRedPacketService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description:获取红包接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午5:17:41  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetRedPacketprocessor")
public class GetRedPacketProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetRedPacketProcessor.class);
	
	@Resource
	private IRedPacketService redPacketService;
	@Resource
	private ICacheApi cacheApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		UserTradeBaseVo userVo = cacheApi.getUserInfoByToken(token);
		if(null==userVo||!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		//红包id
		String redPacketIdStr = request.getParameter("redPacketId");
		//设备类型
		String deviceType = request.getParameter("deviceType");
		if(!checkParams(deviceType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		Long custId = 0L;
		try {
			//获取用户id
			custId = userVo.getCustId();
			Long redPacketId = DesUtils.decryptRedPacketId(redPacketIdStr);
			//获取昵称
			if(custId==0L||!checkParams(custId+"")){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			Map<String,Integer> resultMap = redPacketService.getRedPacket(custId,userVo.getUsername(),userVo.getNickname(),deviceType,redPacketId);
			int result = resultMap.get("coin");
			int flag = 0;
			if(resultMap.containsKey("flag")){
				flag = resultMap.get("flag");
			}
			
			if(result == -1){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15511.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_15511.getErrorMessage(), response);
				return;
			}else if(result == -2){
				sender.fail(ErrorCodeEnum.ERROR_CODE_15512.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_15512.getErrorMessage(), response);
				return;
			}else{
				sender.put("cons", result);
				if(1==flag){
					sender.put("flag", flag);
				}
				sender.success(response);
			}
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,custId:" + custId+"redPacketId:"+redPacketIdStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}	
	}
}
