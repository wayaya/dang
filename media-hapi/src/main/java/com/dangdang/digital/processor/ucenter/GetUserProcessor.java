package com.dangdang.digital.processor.ucenter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.constants.ApiConstant;
import com.dangdang.ucenter.enums.UCErrorCodeEnum;
import com.dangdang.ucenter.model.DangdangUser;
import com.dangdang.ucenter.vo.UserAllInfo;
import com.dangdang.ucenter.vo.UserBaseInfo;

/**
 * Description: 个人信息接口
 * All Rights Reserved.
 * @version 1.0  2015年5月13日 下午3:22:52  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetUserprocessor")
public class GetUserProcessor extends BaseApiProcessor {
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Autowired
	private IUcenterApi ucenterApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//我/他类型区分
		String selfType = request.getParameter("selfType");
		if(!checkParams(selfType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		Long custId = null;
		if(ApiConstant.MY_SELF.equals(selfType)){
			String token = request.getParameter("token");
			// 校验个人登录状态
			DangdangUser user = ucenterApi.getDangdangUserByToken(token);
			if(user == null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
//			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
//			if (custVo == null || custVo.getCustId() == null) {
//				
//			}
			custId = user.getId();
		}else if(ApiConstant.HIS_SELF.equals(selfType)){
			// 假用户id
			String pubId = request.getParameter("pubId");
			if(!checkParams(pubId)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			Long reallyCustId = ucenterApi.getUserIdByPubId(Long.parseLong(pubId));
			if(reallyCustId == null){
				sender.fail(UCErrorCodeEnum.ERR_60012.getCode(), UCErrorCodeEnum.ERR_60012.getMsg(), response);
				return;
			}
			custId = reallyCustId;
		}else{
			sender.fail(UCErrorCodeEnum.ERR_60011.getCode(), UCErrorCodeEnum.ERR_60011.getMsg(), response);
			return;
		}
		
		UserAllInfo userInfo = new UserAllInfo();
		// 个人全部信息接口调用
		if(ApiConstant.MY_SELF.equals(selfType)){
			userInfo = ucenterApi.getUserAllInfo(custId);
		}
		
		// 个人基本信息接口调用
		if(ApiConstant.HIS_SELF.equals(selfType)){
			UserBaseInfo userBaseInfo = ucenterApi.getUserBaseInfo(custId);
			if(userBaseInfo != null){
				userInfo.setCustId(custId);
				userInfo.setNickName(userBaseInfo.getNickName());
				userInfo.setCustImg(userBaseInfo.getCustImg());
				userInfo.setIntroduct(userBaseInfo.getIntroduct());
				userInfo.setGender(userBaseInfo.getGender());
				userInfo.setBindPhoneNum(userBaseInfo.getBindPhoneNum());
			}
		}
		sender.put("userInfo", userInfo);
		sender.success(response);
		return;
	}

}
