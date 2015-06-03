/**
 * Description: GetDigestListByAuthorProcessor.java
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午9:04:55  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.processor.discovery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IBookAuthorService;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 精品作者列表页
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午9:04:55  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestListByAuthorprocessor")
public class GetDigestListByAuthorProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(GetDigestListByAuthorProcessor.class);
	
	private static final List<String> includeFields = new ArrayList<String>();
	
	static{
		includeFields.add("id");
		includeFields.add("signList");
		includeFields.add("cardRemark");
		includeFields.add("cardType");
		includeFields.add("cardTitle");
		includeFields.add("pic1Path");
		includeFields.add("createTime");
	}
	
	@Resource
	private IDigestService digestService;
	
	@Resource
	private IBookAuthorService bookAuthorService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String authorId = request.getParameter("authorId");
		String pageSizeStr = StringUtils.isNotBlank(request.getParameter("pageSize"))?request.getParameter("pageSize"): String.valueOf(DIGEST_DEFAULT_PAGE_SIZE);
		//每一页最后一条数据对应long形时间
		String lastDateStr = request.getParameter("createTime");
		
		if (!checkParams(authorId)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		try {
			Integer pageSize = Integer.valueOf(pageSizeStr);
			Date lastDate = null;
			if(StringUtils.isNotBlank(lastDateStr)){
				lastDate = new Date(Long.parseLong(lastDateStr));
			}
			
			List<Digest> digestList = digestService.queryDigestsByAuthorId(Long.parseLong(authorId), lastDate, pageSize);
			//过滤无用的字段信息
			final int size = includeFields.size();
			String[] arr = (String[])includeFields.toArray(new String[size]);
			SimplePropertyPreFilter spp = new SimplePropertyPreFilter(Digest.class, arr);
			String json = JSON.toJSONString(digestList, spp);
			sender.put("digestList", JSON.parseObject(json, List.class));
			sender.put("hasNext", hasNextPage(digestList, pageSize));
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "获取精品作者列表页失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}
}
