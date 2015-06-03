package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivityRecordService;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ReturnActivityVo;
import com.dangdang.digital.vo.UserTradeBaseVo;


/**
 * Description: 根据活动类型获取用户活动参与信息
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午6:56:40  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetUserActivityRecordprocessor")
public class GetUserActivityRecordProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserActivityRecordProcessor.class);
	@Resource
	private IActivityUserService activityUserService;
	@Resource
	private IActivityRecordService activityRecordService;
	@Resource
	private ICacheApi  cacheApi;
	@Resource
	private ICacheService cacheService; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		UserTradeBaseVo userVo = cacheApi.getUserInfoByToken(token);
		if(null==userVo||!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		String startStr = request.getParameter("start");
		String endStr = request.getParameter("end");
		//入参数 类型
		String typeStr = request.getParameter("type");
		String prizeTypeStr = request.getParameter("prizeType");
		if(!checkParams(startStr)||!checkParams(endStr)||!checkParams(typeStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
		Long custId = 0L;
		try {
			//获取用户id
			custId = userVo.getCustId();
			if(custId==0L||!checkParams(custId+"")){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
			if(!checkResult.success){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
				return;
			}
			int count = checkResult.count;//请求数据记录数
			int start = checkResult.start;
			int type = Integer.parseInt(typeStr);
			int prizeType = 0;
			if(checkParams(prizeTypeStr)){
				prizeType = Integer.parseInt(prizeTypeStr);
			}
			List<ReturnActivityVo>  cacheList = cacheService.getUserActivityRecordCache(custId,type,prizeType);
			int size = cacheList.size();
			if(size<start){
				cacheList = null;
			}else {
				cacheList = cacheList.subList(start,(start+count)>size?size:(start+count));
			}
			sender.put("userActivityRecordList", cacheList);
			sender.put("userActivityRecordTotal", size);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,custId:" + custId+";type:" + request.getParameter("type"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} 
	}
	
}
