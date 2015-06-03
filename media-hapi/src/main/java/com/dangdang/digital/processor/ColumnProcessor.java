package com.dangdang.digital.processor;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.mock.model.Channel;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ThreeTuple;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnSaleVo;

/**
 * 
 * Description:　栏目接口
 * All Rights Reserved.
 * @version 1.0  2014年12月11日 上午9:36:20  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapicolumnprocessor")
public class ColumnProcessor extends BaseApiProcessor {
	
	@Resource
	ICacheApi  cacheApi; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//栏目类型标识
		ThreeTuple<Boolean,String,String> checkTypeResult = RequestParamCheckerUtils.checkParam("columnType",request);
		if(!checkTypeResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkTypeResult.errorMsg, response);
			return;
		}
		//start,end请求参数判断
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(!checkResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
			return;
		}
		
		String columnCodeIsFull = request.getParameter(GlobalParamNameUtils.COLUMN_CODE_ISFULL);
		String columnCode = null;
		if(null!= columnCodeIsFull && "1".equals(columnCodeIsFull)){
			//栏目全标识请求
			columnCode = checkTypeResult.value.toLowerCase();
		}else{
			String channelType = request.getParameter(GlobalParamNameUtils.CHANNEL_TYPE);
			if(RequestParamCheckerUtils.isNullOrEmpty(channelType)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入频道参数channelType", response);
				return;
			}
		   //根据频道和栏目后半分部标识拼接
			 columnCode = RequestParamCheckerUtils.getFullCodeByChannelTypeAndShortCode(channelType,checkTypeResult.value);
		}
		//是不是栏目
		Column column = cacheApi.getColumnFromCache(columnCode);
		if(null==column){
			//非法栏目,不存在该栏目类型
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"目类型参数columnType非法,不存在该栏目类型", response);
			return;
		}else{
			long columnEndTime = 0L;
			if(!column.getIsactiverForever()){
				//如果栏目不是长期有效
				  sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"栏目无效,不可访问", response);
				  return;
				}else{
					//如果是长期有效
					columnEndTime = DateUtil.parseStringToDate("9999-09-09 09:09:09").getTime();
				}
				sender.put("columnEndTime",columnEndTime);
				sender.put("icon", column.getIcon());
			}
		ResultTwoTuple<Long,List<MediaSaleCacheVo>> resultTuple = cacheApi.getMediaSaleByColumnCode(checkResult.start,checkResult.end,columnCode);
		List<MediaSaleCacheVo> listMediaSingelSales = resultTuple.listId;
		
		if(null == listMediaSingelSales||listMediaSingelSales.size()==0){
			LogUtil.warn(LOGGER, "该栏目下没有单品销售实体数据");
			sender.fail(ErrorCodeEnum.ERROR_CODE_11003.getErrorCode(),"该栏目下没有单品销售实体数据", response);
			return ;
		}
		List<ReturnSaleVo> returnSalelist = ReturnBeanUtils.batchgetReturnSaleListVo(listMediaSingelSales);
		long total = resultTuple.total;
		//如果有isChannel参数,则认为是频道栏目
		String isChannel = request.getParameter("isChannel");
		if(null != isChannel){
			//模拟频图片
			List<Channel> channelList = new ArrayList<Channel>();
			Channel channel1 = new Channel();
			channel1.setChannelId(1L);
			channel1.setTitle("你说空灵，我说心境");
			channel1.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
			channel1.setSubNumber(999);
			channel1.setDescription("我知道风里的声音，时间并没有教会我");
			channelList.add(channel1);
			
			Channel channel2 = new Channel();
			channel2.setChannelId(2L);
			channel2.setTitle("黄云的书单");
			channel2.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
			channel2.setSubNumber(0);
			channel2.setDescription("我是一个动漫爱好者");
			channelList.add(channel2);
			
			Channel channel3 = new Channel();
			channel3.setChannelId(3L);
			channel3.setTitle("白云的书单");
			channel3.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
			channel3.setSubNumber(3);
			channel3.setDescription("我是一个白云爱好者");
			channelList.add(channel3);
			
			Channel channel4 = new Channel();
			channel4.setChannelId(4L);
			channel4.setTitle("乌云的书单");
			channel4.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
			channel4.setSubNumber(4);
			channel4.setDescription("我是一个乌云爱好者");
			channelList.add(channel4);
			if(checkResult.end < channelList.size()){
				sender.put("channelList", channelList.subList(checkResult.start, checkResult.end+1));
			}else{
				sender.put("channelList", channelList.subList(checkResult.start,channelList.size()));
			}
			sender.put("count", checkResult.end+1-checkResult.start);//本次返回media数量
			sender.put("total", channelList.size());//本次返回media数量
		}else{
			sender.put("saleList", returnSalelist);//media数据信息
			sender.put("total", total);//本次返回media数量
			sender.put("count", listMediaSingelSales.size());//本次返回media数量
		}
	
	
		sender.put("isShowHorn", column.getIsShowHorn());
		sender.put("tips", column.getTips());
		sender.put("name", column.getName());
		sender.put("columnCode", checkTypeResult.value);
		sender.success(response);
		}

}
