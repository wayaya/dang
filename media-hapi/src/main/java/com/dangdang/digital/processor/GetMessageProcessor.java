package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.constant.DeviceTypeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Message;
import com.dangdang.digital.service.IMessageService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.vo.ReturnMessageVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

@Component("hapigetMessageprocessor")
public class GetMessageProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetMessageProcessor.class);
	@Resource
	private IAuthorityApiFacade authorityApiFacade;
	@Resource
	IMessageService messageService;
	@Resource
	private ICacheApi cacheApi;
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String tokenStr = request.getParameter("token");
		String platformSource = request.getParameter("platformSource");
		String deviceType = request.getParameter("deviceType");
		Long custId = 0L;
		//参数非空判断
		if (!checkParams(tokenStr, platformSource, deviceType, request.getParameter("start"), request.getParameter("end"))) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 验证平台来源
		if (PlatformSourceEnum.getBySource(platformSource) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//验证设备类型
		if (DeviceTypeEnum.getDeviceType(deviceType) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			//token处理
			if(!StringUtils.isEmpty(tokenStr)){
				UserTradeBaseVo vo=null;
				try {
				vo =  authorityApiFacade.getUserInfoByToken(tokenStr);
					if(! (vo==null || vo.getCustId()==null) ){
						//获取用户id
						custId = vo.getCustId();
					}
				} catch (Exception e) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_14601.getErrorCode(),"通过token获取用户基本信息出错", response);
					return;
				}
			}
			
			List<ReturnMessageVo> returnMessageList=new ArrayList<ReturnMessageVo>();
			//start,end请求参数判断
			ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
			if(!checkResult.success){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
				return;
			}
			int startPage=Integer.parseInt(start);
			int endPage=Integer.parseInt(end);
			int count = checkResult.count;//请求数据记录数
	//		int start = checkResult.start;//开始的条数
			List<Message> messageList=messageService.getMessageList(custId,platformSource,deviceType,startPage,endPage);
			if(messageList.size()>0){
				for(Message mv:messageList){
					Map<String,String> mapUserInfo = cacheApi.getUserInfoByCustId(mv.getSenderCustId());
					if(mapUserInfo.size()!=0&&mapUserInfo.containsKey("custImg")&&mapUserInfo.containsKey("nickName")){
						//查询消息详情数据
						ReturnMessageVo returnMessageVo = new ReturnMessageVo();
						Long cust=mv.getSenderCustId();
						returnMessageVo.setNickname(mapUserInfo.get("nickName"));
						returnMessageVo.setImagUrl(mapUserInfo.get("custImg"));
						returnMessageVo.setSenderCustId(DesUtils.encryptCustId(cust));
						returnMessageVo.setAppId(mv.getAppId());
						returnMessageVo.setTitle(mv.getTitle());
						returnMessageVo.setDescription(mv.getDescription());
						returnMessageVo.setOpenSharetype(mv.getOpenShareType());
						returnMessageVo.setIsRead(mv.getIsRead());
						returnMessageVo.setCustomContent(mv.getCustomContent());
						returnMessageVo.setCreationDate(mv.getCreationDate());
						returnMessageVo.setOpenDate(mv.getOpenDate());
						returnMessageVo.setAmount(mv.getAmount());
						returnMessageVo.setDeviceType(mv.getDeviceType());
						returnMessageVo.setIsAllDevice(mv.getIsAllDevice());
						returnMessageList.add(returnMessageVo);
					}
				}
				messageService.updateIsRead(custId,platformSource,deviceType);
			}
			int num=messageService.getAllMassageNum(custId, platformSource, deviceType);
			int size = returnMessageList.size();
			sender.put("messageList", returnMessageList);
			sender.put("getMessagesNum", size);
			sender.put("messageListTotal", num);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
		
	}

}
