package com.dangdang.digital.processor.discovery;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Anthology;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.service.IDigestAnthologyService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 新建精品文集
 * All Rights Reserved.
 * @version 1.0  2015年2月6日 上午10:11:31  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiaddDigestAnthologyprocessor")
public class AddDigestAnthologyProcessor extends BaseApiProcessor{
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IDigestAnthologyService digestAnthologyService;
	
	@Resource
	private IAnthologyService anthologyService;
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String anthologyName = request.getParameter("anthologyName");
		//非必传递，勾选待加入文集的精品id,逗号分隔
		String digestIdStr = request.getParameter("digestIds");
		//已经上传的文集背景图片url
		String anthologyPic = request.getParameter("anthologyPic");
		
		if(!checkParams(token, anthologyName, anthologyPic)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		Long custId = custVo.getCustId();
		//校验文集名称
		Map<String, Integer> checkMap = anthologyService.checkAnthologyName(custId, anthologyName);
		if(!checkMap.isEmpty() && checkMap.containsKey("errorMsg")){
			Integer errorCode = checkMap.get("errorMsg");
			ErrorCodeEnum errorEnum = ErrorCodeEnum.getByErrorCode(errorCode);
			sender.fail(errorCode, errorEnum.getErrorMessage(), response);
			return;
		}
		
		List<Long> digestIds = transferIdStrToList(digestIdStr);
		Anthology anthologyNew = new Anthology();
		anthologyNew.setAnthologyName(anthologyName.trim());
		anthologyNew.setAnthologyPic(anthologyPic);
		anthologyNew.setCreateDate(new Date());
		anthologyNew.setCustId(custId);
		anthologyNew.setCustName(custVo.getUsername());
		anthologyNew.setPlatform(PlatformSourceEnum.FP_P.getSource());
		Map<String, Integer> resultMap = anthologyService.addAnthology(anthologyNew, digestIds, true);
		if(!resultMap.isEmpty() && resultMap.containsKey("errorMsg")){
			Integer errorCode = resultMap.get("errorMsg");
			ErrorCodeEnum errorEnum = ErrorCodeEnum.getByErrorCode(errorCode);
			sender.fail(errorCode, errorEnum.getErrorMessage(), response);
			return;
		}else{
			sender.success(response);
			return;
		}
	}
	
}
