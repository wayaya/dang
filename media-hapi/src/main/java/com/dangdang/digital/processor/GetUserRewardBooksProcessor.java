package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnSaleVo;
import com.dangdang.digital.vo.UserTradeBaseVo;


/**
 * Description: 获取用户打赏的书的信息
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午6:56:40  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetUserRewardBooksprocessor")
public class GetUserRewardBooksProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserRewardBooksProcessor.class);
	@Resource
	private IActivityUserService activityUserService;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private ICacheService cacheService; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String custIdStr = request.getParameter("custId");
		String startStr = request.getParameter("start");
		String endStr = request.getParameter("end");
		if(!checkParams(startStr)||!checkParams(endStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
		//客户端传入custId为+号,先这样处理一下,到时客户端处理后发版了就不需要这个逻辑了
		if(custIdStr.contains(" ")){
			custIdStr = custIdStr.replaceAll(" ", "+");
		}
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
			ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
			if(!checkResult.success){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
				return;
			}
			int count = checkResult.count;//请求数据记录数
			int start = checkResult.start;
			
			List<Long>  cacheSaleIds = cacheService.getUserRewardSaleIdsCache(custId);
			int size = cacheSaleIds.size();
			if(size<start){
				cacheSaleIds = null;
			}else {
				cacheSaleIds = cacheSaleIds.subList(start,(start+count)>size?size:(start+count));
			}
			
			List<MediaSaleCacheVo> mediaList = new ArrayList<MediaSaleCacheVo>();
			if(null!=cacheSaleIds&&cacheSaleIds.size()>0){
				mediaList = cacheApi.batchGetMediaSaleFromCache(cacheSaleIds);
			}
			//批量处理
			List<ReturnSaleVo> resultList = ReturnBeanUtils.getReturnSaleDetailVo(mediaList);
			sender.put("saleList", resultList);
			sender.put("total", size);
			sender.success(response);
			
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,custId:" + custIdStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
