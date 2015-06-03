package com.dangdang.digital.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.service.ILotteryService;
import com.dangdang.digital.utils.ConvertBeanToVo;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 抽奖接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午2:23:53  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapilotteryprocessor")
public class LotteryProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryProcessor.class);
	@Resource
	private ILotteryService lotteryService;
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
		//入参 归属类型
		String vestTypeStr = request.getParameter("vestType");
		String deviceType = request.getParameter("deviceType");
		if(!checkParams(vestTypeStr)||!checkParams(deviceType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
		//验证是否登陆
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			//获取用户id
			Long custId = userVo.getCustId();
			//获取昵称
			String nickname = userVo.getNickname();
			String username = userVo.getUsername();
			if(!checkParams(nickname)||custId==0L||!checkParams(custId+"")){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			int vestType = Integer.parseInt(vestTypeStr);
			resultMap = lotteryService.lotPrize(custId, username,nickname,vestType,deviceType);
			String result = (String) resultMap.get("result");
			if("success".equals(resultMap.get("result"))){
				sender.put("lotteryList", ConvertBeanToVo.getReturnPrizeVo((List<Prize>)resultMap.get("prizeList")));
				sender.success(response);
			}else {
				dealErrorResult(result,sender,response);
			}
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}catch (MediaException me) {
			LogUtil.error(LOGGER, me, "发奖失败！");
			sender.fail(ErrorCodeEnum.ERROR_CODE_15010.getErrorCode(),me.getErrorMsg(), response);				
		}
	}
	
	private void dealErrorResult(String result,ResultSender sender,HttpServletResponse response) throws Exception{
		if(!checkParams(result)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_15200.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15200.getErrorMessage(), response);
		}else if("user_not_exit".equals(result)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_15008.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15008.getErrorMessage(), response);
		}else if("no_chances".equals(result)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_15001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15001.getErrorMessage(), response);
		}else if("day_limt".equals(result)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_15002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15002.getErrorMessage(), response);
		}else if("total_limit".equals(result)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_15003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15003.getErrorMessage(), response);
		}else if("prize_size_short_of_3".equals(result)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_15009.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_15009.getErrorMessage(), response);
		}
	}

}
