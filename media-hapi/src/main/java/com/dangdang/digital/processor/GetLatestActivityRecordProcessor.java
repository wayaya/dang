package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivityRecordService;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ReturnActivityVo;
/**
 * Description: 根据活动类型获取用户活动参与信息
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午6:56:40  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetLatestActivityRecordprocessor")
public class GetLatestActivityRecordProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetLatestActivityRecordProcessor.class);
	@Resource
	private IActivityUserService activityUserService;
	@Resource
	private IActivityRecordService activityRecordService;
	@Resource
	private ICacheService cacheService;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//入参 获取数量
		String amountStr = request.getParameter("amount");
		//入参 获取活动类型
		String typeStr = request.getParameter("type");
		//验证是否登陆
		String startStr = request.getParameter("start");
		if(!checkParams(amountStr)||!checkParams(typeStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			int amount = Integer.parseInt(amountStr);
			if(amount<=0||amount>500){
				sender.fail(ErrorCodeEnum.ERROR_CODE_17801.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_17801.getErrorMessage(), response);
				return;
			}
			int type = Integer.parseInt(typeStr);
			int start = Integer.parseInt(startStr);
			List<ReturnActivityVo>  cacheList = cacheService.getLatestActivityRecord(type);
			int size = cacheList.size();
			if(size<start){
				cacheList = null;
			}else {
				cacheList = cacheList.subList(start,(start+amount)>size?size:(start+amount));
			}
			sender.put("latestActivityRecordList", cacheList);
			sender.put("latestActivityRecordListTotal", size);
			sender.success(response);
			
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,amount:" + request.getParameter("amount")+";type:" + request.getParameter("type"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
