package com.dangdang.digital.processor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Anthology;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 更新个人文集
 * All Rights Reserved.
 * @version 1.0  2015年2月6日 下午3:09:52  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiupdateAnthologyprocessor")
public class UpdateAnthologyProcessor extends BaseApiProcessor {
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IAnthologyService anthologyService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String anthologyId = request.getParameter("anthologyId");
		String anthologyName = request.getParameter("anthologyName");
		String anthologyPic = request.getParameter("anthologyPic");
		
		if(!checkParams(token, anthologyId, anthologyName)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		//校验anthologyId有效性
		Anthology anthologyOld = anthologyService.findAnthologyById(Long.parseLong(anthologyId));
		if(anthologyOld == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22009.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22009.getErrorMessage(), response);
			return;
		}else{
			//判断编辑文集权限
			Long custId = custVo.getCustId();
			if(!custId.equals(anthologyOld.getCustId())){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22012.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22012.getErrorMessage(), response);
				return;
			}
			String oldAnthologyName = anthologyOld.getAnthologyName();
			//用户修改了文集名称,做校验
			if(!anthologyName.trim().equals(oldAnthologyName)){
				Map<String, Integer> checkMap = anthologyService.checkAnthologyName(custId, anthologyName);
				if(!checkMap.isEmpty() && checkMap.containsKey("errorMsg")){
					Integer errorCode = checkMap.get("errorMsg");
					ErrorCodeEnum errorEnum = ErrorCodeEnum.getByErrorCode(errorCode);
					sender.fail(errorCode, errorEnum.getErrorMessage(), response);
					return;
				}else{
					anthologyOld.setAnthologyName(anthologyName.trim());
				}
			}
			if(StringUtils.isNotBlank(anthologyPic)){
				anthologyOld.setAnthologyPic(anthologyPic.trim());
			}
			int effectRows = anthologyService.update(anthologyOld);
			if(effectRows > 0){
				sender.success(response);
				return;
			}else{
				sender.fail(ErrorCodeEnum.ERROR_CODE_22010.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22010.getErrorMessage(), response);
				return;
			}
		}
	}

}
