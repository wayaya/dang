/**
 * Description: BuyMediaProcessor.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午10:36:03  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.model.SearchAssociate;
import com.dangdang.digital.service.ISearchAssociateService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.SearchAssociateVo;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午11:46:33 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Component("hapisearchAssociateprocessor")
public class SearchAssociateProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAssociateProcessor.class);
	@Resource
	private ISearchAssociateService searchAssociateService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		try {
			if (StringUtils.isBlank(request.getParameter("searchContent"))
					|| StringUtils.isBlank(request.getParameter("platformSource"))) {
				LogUtil.info(LOGGER, "参数：searchContent为空！");
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			}
			// 验证平台来源
			if (PlatformSourceEnum.getBySource(request.getParameter("platformSource")) == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}

			List<SearchAssociate> searchAssociateList = searchAssociateService.associateSearch(
					request.getParameter("searchContent").trim(), request.getParameter("platformSource").trim());

			List<SearchAssociateVo> searchAssociateVoList = new ArrayList<SearchAssociateVo>();
			if (searchAssociateList != null && searchAssociateList.size() > 0) {
				for (SearchAssociate searchAssociate : searchAssociateList) {
					SearchAssociateVo associateVo = new SearchAssociateVo();
					associateVo.setKeyword(searchAssociate.getKeyword());
					associateVo.setCount(searchAssociate.getCount());
					searchAssociateVoList.add(associateVo);
				}
			}

			sender.put("searchList", searchAssociateVoList);
			sender.success(response);
		} catch (Exception e) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
		}
	}
}
