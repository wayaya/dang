package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.service.IDiscoveryService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.ReturnCardDiscoveryVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * 获取发现列表的接口 Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月22日 下午3:26:40 by 杜亚锋（duyafeng@dangdang.com）创建
 *
 */
@Component("hapigetDiscoveryListprocessor")
public class GetDiscoveryListProcessor extends BaseApiProcessor{

	private static final Logger LOGGER = LoggerFactory.getLogger(GetBookReviewReplyListProcessor.class);
	
	@Resource
	private IDiscoveryService discoverService;
	
	
	private String img_path = "media.resource.discover.http.path";
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private ICacheApi cacheApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		// 入参：token
		String token = request.getParameter("token");
		// 入参：发现起始
		String startStr = request.getParameter("start");
		// 入参：查询截止
		String endStr = request.getParameter("end");
		//类型  1:原创发现  2:当心好文
		String typeStr = request.getParameter("type");
		//入参: 是否是收藏 列表    	0:否          1:是
		String isCollectStr = request.getParameter("isCollect");
		
		// 平台来源
		String platformSource = request.getParameter(GlobalParamNameUtils.PLATFORM);
		
		if (!checkParams(startStr,endStr,typeStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isNotBlank(typeStr) && !"1".equals(typeStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isNotBlank(isCollectStr) && !"0".equals(isCollectStr) && !"1".equals(isCollectStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isNotBlank(isCollectStr) && "1".equals(isCollectStr)){
			if (!checkParams(token)) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			// 验证平台来源
			if (StringUtils.isEmpty(platformSource) || PlatformSourceEnum.getBySource(platformSource) == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
		}
		try {
			
			int start = Integer.valueOf(startStr);
			int end = Integer.valueOf(endStr);
			if (end < start || end < 0 || start < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			
			int pageSize = end + 1 - start;
			int pageIndex = start / pageSize + 1;
			Query query = new Query();
			query.setPageSize(pageSize);
			query.setPage(pageIndex);
			Discovery dis = new Discovery();
			dis.setShowStartEndDate(DateUtil.getDateTime(Calendar.getInstance().getTime()));
			dis.setType(Integer.valueOf(typeStr));
			if(StringUtils.isNotBlank(isCollectStr) && "1".equals(isCollectStr)){
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				dis.setCustId(Long.valueOf(custVo.getCustId()));
				dis.setPlatForm(platformSource);
			}
			PageFinder<Discovery> page = discoverService.findPageFinderObjs(dis, query);
			
			List<Discovery> list = page.getData();
			List<ReturnCardDiscoveryVo> returnList = new ArrayList<ReturnCardDiscoveryVo>();
			String path = ConfigPropertieUtils.getString(img_path);
			for(Discovery discovery : list){
				ReturnCardDiscoveryVo returnCardDiscoveryVo =  ReturnBeanUtils.getReturnCardDiscoveryVo(discovery, path);
				if(discovery.getMediaId() != null){
					
					MediaCacheBasicVo mediaCacheBasicVo = cacheApi.getMediaBasicFromCache(discovery.getMediaId());
					if(mediaCacheBasicVo!=null){
						Long saleId = mediaCacheBasicVo.getSaleId();
						returnCardDiscoveryVo.setSaleId(saleId);
					}
				}
				returnList.add(returnCardDiscoveryVo);
			}
			// 返回值：发现列表
			sender.put("contents", returnList);
			// 返回值：发现总数
			sender.put("total", page.getRowCount());
			sender.success(response);

		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,type:" + request.getParameter("type"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	
	}

}
