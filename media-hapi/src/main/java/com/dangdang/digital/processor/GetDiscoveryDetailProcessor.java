package com.dangdang.digital.processor;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.service.IDiscoveryService;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnDiscoveryVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 通过发现ID获取发现明细 All Rights Reserved.
 * 
 * @version 1.0 2014年12月22日 下午6:28:57 by 杜亚锋（duyafeng@dangdang.com）创建
 */
@Component("hapigetDiscoveryDetailprocessor")
public class GetDiscoveryDetailProcessor extends BaseApiProcessor{
	private static final Logger LOGGER = LoggerFactory.getLogger(GetDiscoveryDetailProcessor.class);

	@Resource
	private ICacheApi cacheApi;
	
	@Resource
	private IDiscoveryService discoveryService;
	
	@Resource
	private IStoreUpService storeUpService;
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	private String content_path = "media.resource.discover.content.path";

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：token
		String token = request.getParameter("token");
				
		// 入参： 发现id
		String discoveryIdStr = request.getParameter("discoveryId");
		
		if (!checkParams(discoveryIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		try {
			Long discoveryId = Long.valueOf(discoveryIdStr);
			Discovery discovery = cacheApi.getDisCoveryFromCache(discoveryId);
			if (discovery == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_20001.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_20001.getErrorMessage(), response);
				return;
			}
			if(discovery.getHtmlContent() == null  || "".equals(discovery.getHtmlContent())){
				File content = new File(ConfigPropertieUtils.getString(content_path)+File.separator+discovery.getContent());
				String htmlContent = FileUtils.readFileToString(content, "UTF-8");
				discovery.setHtmlContent(htmlContent);
				cacheApi.setDiscoveryCache(discovery);
			}
			// 转换vo
			ReturnDiscoveryVo returnDiscoveryVo = ReturnBeanUtils.getReturnDiscoveryVo(discovery);
			if(discovery.getMediaId() != null){
				if(StringUtils.isNotBlank(token)){
					UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
					boolean isStoreup = storeUpService.isStoreUp(custVo.getCustId(),discoveryId, Constans.STORE_UP_DISCOVER);
					returnDiscoveryVo.setIsStore(isStoreup?1:0);
				}
				
				MediaCacheWholeVo mediaVo = cacheApi.getMediaWholeFromCache(discovery.getMediaId());
				Integer shelfStatus = mediaVo.getShelfStatus();
				if(mediaVo != null && (shelfStatus != null && shelfStatus.intValue() == 1)){
					MediaSaleCacheVo saleCache = cacheApi.getMediaSaleFromCache(mediaVo.getSaleId());
					returnDiscoveryVo.setSale(ReturnBeanUtils.getReturnSaleDetailVo(saleCache));
				}
			}
			sender.put("discovery", returnDiscoveryVo);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,discoveryId:" + request.getParameter("discoveryId"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
