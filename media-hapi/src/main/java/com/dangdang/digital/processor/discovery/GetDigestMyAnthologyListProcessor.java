package com.dangdang.digital.processor.discovery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Anthology;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 个人私家文集列表
 * All Rights Reserved.
 * @version 1.0  2015年2月6日 上午11:43:32  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestMyAnthologyListprocessor")
public class GetDigestMyAnthologyListProcessor extends BaseApiProcessor {
	
	private static final List<String> includeFields = new ArrayList<String>();
	static{
		includeFields.add("anthologyId");
		includeFields.add("anthologyName");
		includeFields.add("anthologyPic");
		includeFields.add("createTime");
	}
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IAnthologyService anthologyService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String pageSizeStr = StringUtils.isNotBlank(request.getParameter("pageSize"))?request.getParameter("pageSize"): String.valueOf(DIGEST_DEFAULT_PAGE_SIZE);
		//每一页最后一条数据对应long形时间
		String lastDateStr = request.getParameter("createTime");
		
		// 未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		Date lastDate = null;
		if(StringUtils.isNotBlank(lastDateStr)){
			lastDate = new Date(Long.parseLong(lastDateStr));
		}
		
		Integer pageSize = Integer.valueOf(pageSizeStr);
		List<Anthology> anthologyList = anthologyService.queryAnthologyListByCustId(custVo.getCustId(), PlatformSourceEnum.FP_P.getSource(), lastDate, pageSize);
		//过滤无用的字段信息
		final int size = includeFields.size();
		String[] arr = (String[])includeFields.toArray(new String[size]);
		SimplePropertyPreFilter spp = new SimplePropertyPreFilter(Anthology.class, arr);
		String json = JSON.toJSONString(anthologyList, spp);
		sender.put("anthologyList", JSON.parseObject(json, List.class));
		sender.put("hasNext", hasNextPage(anthologyList, pageSize));
		sender.success(response);
		return;
	}

}
