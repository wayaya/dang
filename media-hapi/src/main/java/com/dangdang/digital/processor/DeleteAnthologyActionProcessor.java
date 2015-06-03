package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Anthology;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 删除文集Action
 * All Rights Reserved.
 * @version 1.0  2015年2月9日 下午5:53:57  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapideleteAnthologyActionprocessor")
public class DeleteAnthologyActionProcessor extends BaseApiProcessor {
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IAnthologyService anthologyService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String anthologyId = request.getParameter("anthologyId");
		//文集下一个或者多个精品ids
		String digestIdStr = request.getParameter("digestIds");
		
		if(!checkParams(token, anthologyId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		Anthology anthology = anthologyService.findAnthologyById(Long.parseLong(anthologyId));
		if(anthology == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22009.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22009.getErrorMessage(), response);
			return;
		}else{
			//判断删除文集权限
			Long custId = custVo.getCustId();
			if(!custId.equals(anthology.getCustId())){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22015.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22015.getErrorMessage(), response);
				return;
			}
			
			boolean flag = false;
			List<Long> digestIds = this.transferIdStrToList(digestIdStr);
			if(CollectionUtils.isEmpty(digestIds)){
				flag = anthologyService.deleteAnthology(Long.parseLong(anthologyId));
			}else{
				flag = anthologyService.deleteDigestsForAnthology(Long.parseLong(anthologyId), digestIds);
			}
			
			if(flag){
				sender.success(response);
				return;
			}else{
				sender.fail(ErrorCodeEnum.ERROR_CODE_22011.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22011.getErrorMessage(), response);
				return;
			}
		}
	}

}
