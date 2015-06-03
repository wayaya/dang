package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IRankConsToBookService;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ReturnRankConsVo;

/**
 * Description:  壕赏榜单接口
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午3:00:55  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapirankConsToBookListprocessor")
public class RankConsToBookListProcessor extends BaseApiProcessor {
	
	@Resource
	IRankConsToBookService rankConsToBookService;  
	@Resource
	ICacheApi  cacheApi; 
	@Resource
	ICacheService cacheService; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//榜单标识
		String code  =  request.getParameter("code");
		if(!checkParams(code)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//start,end请求参数判断
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(!checkResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
			return;
		}
		int count = checkResult.count;//请求数据记录数
		int start = checkResult.start;//开始的条数
		
		List<ReturnRankConsVo> cacheList =  cacheService.getRankConsListCache(code);
		int size = cacheList.size();
		if(size<start){
			cacheList = null;
		}else {
			cacheList = cacheList.subList(start,(start+count)>size?size:(start+count));
		}
		sender.put("consRankList", cacheList);
		sender.put("consRankListTotal", size);
		sender.success(response);
	}
}
