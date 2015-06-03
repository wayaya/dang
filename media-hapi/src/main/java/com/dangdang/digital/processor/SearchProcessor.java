/**
 * Description: BuyMediaProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午10:36:03  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.ISearchAssociateService;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SearchUtils;
import com.dangdang.digital.vo.SearchReturnVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午11:46:33 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Component("hapisearchprocessor")
public class SearchProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchProcessor.class);
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private ISearchAssociateService searchAssociateService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutorForSearch;
	@Resource
	private IStoreUpService  storeUpService;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@SuppressWarnings("unchecked")
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		try {
			// 平台来源
			final String platformSource = request.getParameter("platformSource");
			if (StringUtils.isBlank(request.getParameter("searchContent")) || StringUtils.isBlank(platformSource)) {
				LogUtil.info(LOGGER, "参数：searchContent为空！");
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			}

			// 验证平台来源
			if (PlatformSourceEnum.getBySource(platformSource) == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			final String searchContent = request.getParameter("searchContent").trim();

			String start = "0";
			if (StringUtils.isNotBlank(request.getParameter("start"))) {
				start = request.getParameter("start");
			}

			String end = String.valueOf(Integer.parseInt(start) + 15);

			if (StringUtils.isNotBlank(request.getParameter("end"))) {
				end = request.getParameter("end");
			}

			Map<String, Object> map = SearchUtils.search(searchContent, start, end,platformSource);
			if (map != null) {
				List<SearchReturnVo> searchReturnVoList = (List<SearchReturnVo>) map.get("searchReturnVoList");
				
				final Integer totalCount = Integer.parseInt(map.get("totalCount").toString());
				// 修改联想词长度应该大于1
				if (StringUtils.isNotBlank(searchContent) && totalCount != null && totalCount.intValue() > 0) {
					try {
						// 新线程保存联想词，不影响搜索结果返回
						taskExecutorForSearch.execute(new Runnable() {
							@Override
							public void run() {
								searchAssociateService.saveSearchUpshot(searchContent, totalCount, platformSource);
							}
						});
					} catch (Exception e) {
						LOGGER.error("新线程保存联想词异常", e);
					}

				}
				
				// 听书搜索需要增加收藏
				// 入参：token
				String token = request.getParameter("token");
				if (CollectionUtils.isNotEmpty(searchReturnVoList)
						&& PlatformSourceEnum.TS_P.getSource().equals(platformSource) && StringUtils.isNotBlank(token)) {
					// 获取token
					UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
					if (custVo != null) {
						List<Long> saleIds = new ArrayList<Long>();
						for (SearchReturnVo searchReturnVo : searchReturnVoList) {
							saleIds.add(searchReturnVo.getSaleId());
						}
						List<Long> storeUpSaleIds = storeUpService.getStoreUpObjectIdListByCustIdAndTargetIds(
								custVo.getCustId(), "media", platformSource, saleIds);
						for (SearchReturnVo searchReturnVo : searchReturnVoList) {
							if (storeUpSaleIds.contains(searchReturnVo.getSaleId())) {
								searchReturnVo.setIsStore(1);
							} else {
								searchReturnVo.setIsStore(0);
							}
						}
					}
				}
				
				sender.put("totalCount", map.get("totalCount"));
				sender.put("searchList", searchReturnVoList);
			}
			sender.success(response);
		} catch (Exception e) {
			LOGGER.error("未知错误", e);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
		}
	}
	
}
