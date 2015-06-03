package com.dangdang.digital.processor;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.common.api.ICommonApi;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ReturnActivityUserVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**	
 * Description: 获取用户的活动基本信息接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午6:31:45  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetActivityUserInfoprocessor")
public class GetActivityUserInfoProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetActivityUserInfoProcessor.class);
	@Resource
	private IActivityUserService activityUserService;
	@Resource
	private ICacheApi cacheApi;  
	@Resource
	private ICacheService cacheService;
	@Resource
	private ICommonApi commonApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		String custIdStr = request.getParameter("custId");
		//验证是否登陆
		Long custId = 0L;
		try {
			if(!checkParams(custIdStr)){
				//从token中取
				String token = request.getParameter("token");
				UserTradeBaseVo userVo = cacheApi.getUserInfoByToken(token);
				if(null==userVo||!checkParams(token)){
					sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
					return;
				}
				custId = userVo.getCustId();
			}else {
				//获取用户id[解密]
				custId = DesUtils.decryptCustId(custIdStr);
				if(custId==0L||null==custId){
					sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
					return;
				}
			}
			ReturnActivityUserVo returnActivityUserVo = cacheService.getActivityUserCache(custId);
			sender.put("userActivityInfo", returnActivityUserVo);
			sender.success(response);
			
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,custId:" +custIdStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
	

}
