/**
 * Description: GetDigestDetailProcessor.java
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午3:47:34  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.processor.discovery;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IDigestApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.RetrunDigestDetailVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 精品详情页
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午3:47:34  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestDetailprocessor")
public class GetDigestDetailProcessor extends BaseApiProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(GetDigestDetailProcessor.class);
	
	@Resource
	private IDigestService digestService;
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private ReturnBeanUtils returnBeanUtils;
	
	@Resource
	private IDigestApi idigestApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//精品id
		String digestId = request.getParameter("digestId");
		String token = request.getParameter("token");
		
		//校验digestId必传参数
		if(!checkParams(digestId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//判断精品是否存在
		Digest digest = idigestApi.findDigestById(Long.parseLong(digestId));
		if(digest == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22001.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22001.getErrorMessage(), response);
			return;
		}
		
		//增加点击数
		Integer clickCnt = digest.getClickCnt();
		if (clickCnt == null) {
			clickCnt = 0;
		}
		clickCnt ++;
		digest.setClickCnt(clickCnt);
		digestService.update(digest);
		
		try {
			//根据token获取登录用户信息
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			RetrunDigestDetailVo digestDetail = returnBeanUtils.getRetrunDigestDetailVo(digest, custVo);
			sender.put("digestDetail", digestDetail);
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "获取精品详情页失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}
}
