package com.dangdang.digital.processor.discovery.nearby;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.common.api.vo.DangdangUser;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IBookshelfBookService;
import com.dangdang.digital.service.nearby.impl.nearby.NearbyUpdateServiceImpl;
import com.dangdang.digital.service.nearby.impl.nearby.UserNearbyServiceImpl;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;
/**
 * 
 * Description: 清除地理位置
 * All Rights Reserved.
 * @version 1.0  2014年7月16日 下午2:48:35  by 林永奇（linyongqi@dangdang.com）创建
 */
@Component("mobilenearbyClearLocationprocessor")
public class NearbyClearLocationProcessor extends BaseApiProcessor{
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(NearbyClearLocationProcessor.class);
    @Resource(name="updateService")
    private NearbyUpdateServiceImpl updateService;
	@Resource(name="nearbyService")
    private UserNearbyServiceImpl searchService;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
    	String uToken=request.getParameter("uToken");
        String token=request.getParameter("token");
        if(!StringUtils.isBlank(token)){
        	uToken=token;
        }
        checkParams(uToken);   

        //未登录用户直接返回
  		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(uToken);
  		if (custVo == null || custVo.getCustId() == null) {
  			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
  			return;
  		}
  		
  		Long custId = custVo.getCustId();
//        ThirdpartyCustId thirdpartyCustId=thirdpartyCustIdManager.getByCustId(custId);
//        custId=thirdpartyCustId.getId();
        updateService.clearUserLocation(Long.toString(custId));
        LOG.info("NearbyClearLocationProcessor:uToken->{0}",uToken);
        sender.put("msg","done");
        sender.send(response);
    }
}
