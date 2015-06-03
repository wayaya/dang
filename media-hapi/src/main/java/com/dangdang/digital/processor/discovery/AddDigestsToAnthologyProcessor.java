package com.dangdang.digital.processor.discovery;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Anthology;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 选择精品文章加入已经新建的个人文集
 * All Rights Reserved.
 * @version 1.0  2015年2月28日 下午3:35:44  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiaddDigestsToAnthologyprocessor")
public class AddDigestsToAnthologyProcessor extends BaseApiProcessor {
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IAnthologyService anthologyService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		//勾选待加入文集的精品id,逗号分隔
		String digestIdStr = request.getParameter("digestIds");
		//待加入到的文集id
		String anthologyIdStr = request.getParameter("anthologyId");
		if(!checkParams(token, anthologyIdStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		List<Long> digestIds = transferIdStrToList(digestIdStr);
		//校验是否选中文章待加入文集
		if(CollectionUtils.isEmpty(digestIds)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22028.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22028.getErrorMessage(), response);
			return;
		}
		
		//未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		Anthology anthology = anthologyService.findAnthologyById(Long.valueOf(anthologyIdStr));
		if(anthology == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22009.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22009.getErrorMessage(), response);
			return;
		}
		
		Map<String, Integer> resultMap = anthologyService.addAnthology(anthology, digestIds, false);
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
