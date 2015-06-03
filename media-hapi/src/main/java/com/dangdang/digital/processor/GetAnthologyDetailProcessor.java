package com.dangdang.digital.processor;

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
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Anthology;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestAnthology;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.service.IDigestAnthologyService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 文集详情
 * All Rights Reserved.
 * @version 1.0  2015年2月9日 下午6:52:37  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetAnthologyDetailprocessor")
public class GetAnthologyDetailProcessor extends BaseApiProcessor {
	
	private static final List<String> includeFields = new ArrayList<String>();
	static{
		includeFields.add("id");
		includeFields.add("cardRemark");
		includeFields.add("cardType");
		includeFields.add("cardTitle");
		includeFields.add("pic1Path");
		includeFields.add("createTime");
	}
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IAnthologyService anthologyService;
	
	@Resource
	private IDigestAnthologyService digestAnthologyService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String anthologyId = request.getParameter("anthologyId");
		String pageSizeStr = StringUtils.isNotBlank(request.getParameter("pageSize"))?request.getParameter("pageSize"): String.valueOf(DIGEST_DEFAULT_PAGE_SIZE);
		//每一页最后一条数据对应long形时间
		String lastDateStr = request.getParameter("createTime");
		//标示请求来源，区分返回数据格式
		String from = request.getParameter("from");
		
		if(!checkParams(anthologyId)){ 
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		Anthology anthology = anthologyService.findAnthologyById(Long.valueOf(anthologyId));
		if(anthology == null){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22009.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22009.getErrorMessage(), response);
			return;
		}
		
		//app端请求需校验用户登录以及文集查看权限，反之h5不check
		if(StringUtils.isBlank(from) || !from.equalsIgnoreCase("h5")){
			//未登录用户直接返回
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			
			//判断查看文集权限
			Long custId = custVo.getCustId();
			if(!custId.equals(anthology.getCustId())){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22016.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22016.getErrorMessage(), response);
				return;
			}
		}
		
		Date lastDate = null;
		if(StringUtils.isNotBlank(lastDateStr)){
			lastDate = new Date(Long.parseLong(lastDateStr));
		}
		Integer pageSize = Integer.valueOf(pageSizeStr);
		List<DigestAnthology> digestList = digestAnthologyService.queryDigestsByAnthologyId(Long.parseLong(anthologyId), lastDate, pageSize);
		//过滤无用的字段信息
		final int size = includeFields.size();
		String[] arr = (String[])includeFields.toArray(new String[size]);
		SimplePropertyPreFilter spp = new SimplePropertyPreFilter(Digest.class, arr);
		String json = JSON.toJSONString(digestList, spp);
		sender.put("digestList", JSON.parseObject(json, List.class));
		sender.put("anthology", anthology);
		sender.put("hasNext", hasNextPage(digestList, pageSize));
		sender.success(response);
		return;
	}

}
