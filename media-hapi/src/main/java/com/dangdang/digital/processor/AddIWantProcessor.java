package com.dangdang.digital.processor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.IWant;
import com.dangdang.digital.service.IIWantService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 添加我想要信息 All Rights Reserved.
 * 
 * @version 1.0 2015年1月28日 下午9:21:17 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiaddIWantprocessor")
public class AddIWantProcessor extends BaseApiProcessor {

	@Resource
	private IIWantService iwantService;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 书名
		String title = request.getParameter("title");
		// 入参：token
		String token = request.getParameter("token");
		// 平台来源
		String platformSource = request.getParameter("platformSource");
		// 作者 非必填
		String author = request.getParameter("author");
		// 演讲者 非必填
		String speaker = request.getParameter("speaker");

		// 联系方式 预留 非必填
		String contactInfo = request.getParameter("contactInfo");
		if (!checkParams(title)) {
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

		// 获取token
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);

		IWant iwant = new IWant();
		iwant.setAuthor(author);
		iwant.setContactInfo(contactInfo);
		iwant.setTitle(title);
		iwant.setSpeaker(speaker);
		iwant.setCreateTime(new Date());
		iwant.setPlatform(platformSource);
		if (custVo != null) {
			iwant.setCustId(custVo.getCustId());
		}
		iwantService.save(iwant);
		sender.success(response);
	}

}
