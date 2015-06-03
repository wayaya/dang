package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IEbookConsRecordService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnEbookConsVo;

/**
 * Description:  单本书的昨日打赏排行榜 有改动，改成历史总计的了。。当初对的时候
 * 就说应该是总的。。。 = =！ 不听吧！！
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午3:00:55  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapigetEbookRewardedUsersprocessor")
public class GetEbookRewardedUsersProcessor extends BaseApiProcessor {
	
	@Resource
	private IEbookConsRecordService ebookConsRecordService;
	@Resource
	private ICacheApi  cacheApi; 
	@Resource
	private ICacheService cacheService;
	
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//频道
		String channel  =  request.getParameter("channelType");
		//saleId
		String saleIdStr = request.getParameter("saleId");
		if(!checkParams(saleIdStr)||!checkParams(channel)){
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
		try {
			Long saleId = Long.parseLong(saleIdStr);
			MediaSaleCacheVo saleVo = cacheApi.getMediaSaleFromCache(saleId);
			//不是单品
			if(null==saleVo||0!=saleVo.getType()){
				sender.fail(ErrorCodeEnum.ERROR_CODE_17802.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_17802.getErrorMessage(), response);
				return;
			}
			//书的id
			Long mediaId = saleVo.getMediaList().get(0).getMediaId();
			int count = checkResult.count;//请求数据记录数
			int start = checkResult.start;//开始的条数
			List<ReturnEbookConsVo> list =  cacheService.getEbookRewardedUsersCache(mediaId,channel);
			int size = list.size();
			if(size<start){
				list = null;
			}else {
				list = list.subList(start,(start+count)>size?size:(start+count));
			}
			sender.put("ebookRewardedUsersList", list);			
			sender.put("ebookRewardedUsersTotal", size);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,channel:" + channel+",saleId:" + saleIdStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}	
	}
}
