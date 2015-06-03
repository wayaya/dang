package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ChannelTypeEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.SpecialTopic;
import com.dangdang.digital.service.IBlockService;
import com.dangdang.digital.service.ISpecialTopicService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ReturnSpecialTopicVo;

/**
 * Description: 专题处理器
 * All Rights Reserved.
 * @version 1.0  2015年1月7日 下午10:05:58  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Component("hapispecialtopichistoryprocessor")
public class SpecialTopicListProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpecialTopicListProcessor.class);
	@Resource
	ISpecialTopicService specialTopicService;
	@Resource
	IBlockService blockService;
	@Resource
	ICacheApi cacheApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception{
		//入参 获取块code
		String deviceType = request.getParameter("deviceType");
		String channelType = request.getParameter("channelType");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		//验证是否登陆
		try {
			if(!checkParams(deviceType) && !checkParams(channelType)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			
			channelType = "'"+channelType + "','"+ ChannelTypeEnum.ALL.getChannelTypeNo()+"'";
			
			
			if(StringUtils.isNotBlank(start)){
				start = start.trim();
			}else{
				start = "0";
			}
			
			if(StringUtils.isNotBlank(end)){
				end = end.trim();
			}else{
				end = "9";
			}
			
			List<SpecialTopic> specialTopicList = new ArrayList<SpecialTopic>();
			List<ReturnSpecialTopicVo> returnSpecialTopicVos = new ArrayList<ReturnSpecialTopicVo>();
			Integer count = specialTopicService.getHistoryValidSpecialTopicPageCount(deviceType, channelType);
			
			if(count != null){
				if(count.intValue() > 1){
					count = count - 1;
					specialTopicList = specialTopicService.getHistoryValidSpecialTopicPageData(deviceType, channelType, (Integer.parseInt(start) + 1), (Integer.parseInt(end) -Integer.parseInt(start) + 1));
					if(specialTopicList != null && specialTopicList.size() > 0){
						for(SpecialTopic specialTopic : specialTopicList){
							ReturnSpecialTopicVo specialTopicVo = new ReturnSpecialTopicVo();
							specialTopicVo.setStId(specialTopic.getStId());
							specialTopicVo.setName(specialTopic.getName());
							specialTopicVo.setPicPath(specialTopic.getPicPath());
							specialTopicVo.setCreationDate(specialTopic.getCreationDate());
							specialTopicVo.setLastModifiedDate(specialTopic.getLastModifiedDate());
							returnSpecialTopicVos.add(specialTopicVo);
						}
					}
				}
			}
			sender.put("count", returnSpecialTopicVos.size());
			sender.put("total", count);
			sender.put("specialTopicList", returnSpecialTopicVos);
			sender.success(response);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "获取专题列表失败：", channelType, start, end);
			sender.fail(ErrorCodeEnum.ERROR_CODE_11102.getErrorCode(), ErrorCodeEnum.ERROR_CODE_11102.getErrorMessage(), response);
		}
	}

}
