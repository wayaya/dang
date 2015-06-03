package com.dangdang.digital.processor.discovery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.dangdang.digital.api.IDigestApi;
import com.dangdang.digital.constant.DigestDayOrNightEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 精品首页-version2.0 All Rights Reserved.
 * 
 * @version 1.0 2015年3月4日 上午10:45:49 by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestHomePageListprocessor")
public class GetDigestHomePageListProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory
			.getLogger(GetDigestHomePageListProcessor.class);

	private static final Set<String> includeFields = new HashSet<String>();

	static {
		includeFields.add("id");
		includeFields.add("signList");
		includeFields.add("cardRemark");
		includeFields.add("cardType");
		includeFields.add("cardTitle");
		includeFields.add("pic1Path");
		includeFields.add("createTime");
		includeFields.add("authorList");
		includeFields.add("showStartDateYmdLong");
		includeFields.add("sortPage");
		includeFields.add("dayOrNight");
		includeFields.add("topCnt");
	}

	@Resource
	private IDigestService digestService;

	@Resource
	private IDigestApi idigestApi;

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		// 必传，需校验
		String dayOrNight = request.getParameter("dayOrNight");
		// 必传，需校验
		String action = request.getParameter("act");
		// 非必传，
		String sortPageStr = request.getParameter("sortPage");
		// 非必传
		String pageSizeStr = StringUtils.isNotBlank(request
				.getParameter("pageSize")) ? request.getParameter("pageSize")
				: String.valueOf(DIGEST_DEFAULT_PAGE_SIZE);

		Integer type = getInt(request, "type", 1);

		if (!checkParams(dayOrNight, action)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 校验dayOrNight合法性
		if (!DigestDayOrNightEnum.getDayOrNightNames().contains(dayOrNight)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 校验action合法性
		if (!(Digest.NEW_ACTION.equals(action) || Digest.OLD_ACTION
				.equals(action))) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		Integer dayNight = DigestDayOrNightEnum.getByName(dayOrNight).getKey();
		Integer pageSize = Integer.valueOf(pageSizeStr);
		Long sortPage = StringUtils.isNotBlank(sortPageStr) ? Long
				.valueOf(sortPageStr) : null;
		try {
			List<Digest> digestList = null;
			if (Digest.NEW_ACTION.equals(action)) {
				digestList = idigestApi.queryDigestsHomePage(sortPage, dayNight,
						pageSize, String.valueOf(type), "V2");
			} else if (Digest.OLD_ACTION.equals(action)) {
				// 下拉时sortPage参数必传
				if (sortPage == null) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(),
							response);
					return;
				}
				digestList = idigestApi.queryDigestsHomePage(sortPage, dayNight,
						pageSize, String.valueOf(type), "V1");
			}
			final int size = includeFields.size();
			String[] arr = (String[]) includeFields.toArray(new String[size]);
			SimplePropertyPreFilter spp = new SimplePropertyPreFilter(
					Digest.class, arr);
			String json = JSON.toJSONString(digestList, spp);
			sender.put("digestList", JSON.parseObject(json, List.class));
			sender.put("hasNext", hasNextPage(digestList, pageSize));
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "获取精品首页内容失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}

}