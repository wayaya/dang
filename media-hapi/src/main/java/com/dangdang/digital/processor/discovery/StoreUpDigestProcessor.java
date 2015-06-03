package com.dangdang.digital.processor.discovery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.StoreUp;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 精品阅读收藏接口
 * All Rights Reserved.
 * @version 1.0  2015年1月31日 下午4:33:34  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapistoreUpDigestprocessor")
public class StoreUpDigestProcessor extends BaseApiProcessor {
	//收藏
	private static final String SAVE = "save";
	//获取收藏列表
	private static final String GET = "get";
	//取消收藏
	private static final String CANCEL = "cancel";
	
	private static final Set<String> includeFields = new HashSet<String>();
	static{
		includeFields.add("id");
		includeFields.add("cardTitle");
		includeFields.add("cardType");
		includeFields.add("cardRemark");
		includeFields.add("pic1Path");
	}
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IStoreUpService storeUpService;
	
	@Resource
	private IDigestService digestService;
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//操作类型:get;save;cancel
		String op = request.getParameter("op");
		String token = request.getParameter("token");
		//多个精品id，用逗号分隔
		String digestIdStr = request.getParameter("digestIds");
		
		//获取个人收藏列表分页使用
		String pageIndexStr = StringUtils.isNotBlank(request.getParameter("pageIndex"))?request.getParameter("pageIndex"):"1";
		String pageSizeStr = StringUtils.isNotBlank(request.getParameter("pageSize"))?request.getParameter("pageSize"): String.valueOf(DIGEST_DEFAULT_PAGE_SIZE);
		
		if(!checkParams(op, token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		// 未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		Long custId = custVo.getCustId();
		if(op.equalsIgnoreCase(SAVE)){//添加收藏
			if(!checkParams(digestIdStr)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			Long digestId = Long.valueOf(digestIdStr);
			//判断精品digestId是否有效
			Digest digest = digestService.findDigestById(digestId);
			if(digest == null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22017.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22017.getErrorMessage(), response);
				return;
			}
			boolean isStoreUp = storeUpService.isStoreUp(custId, digestId, Constans.STORE_UP_DIGEST);
			if(isStoreUp){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22003.getErrorMessage(), response);
				return;
			}
			
			Integer clickCnt = digest.getClickCnt();
			if (clickCnt == null) {
				clickCnt = 0;
			}
			clickCnt ++;
			digest.setClickCnt(clickCnt);
			digestService.update(digest);
			
			StoreUp su = new StoreUp();
			su.setCustId(custId);
			su.setTargetId(digestId);
			su.setType(Constans.STORE_UP_DIGEST);
			su.setPlatform(PlatformSourceEnum.FP_P.getSource());
			su.setUserName(custVo.getUsername());
			su.setNickName(custVo.getNickname());
			storeUpService.save(su);
			sender.success(response);
			return;
		}else if(op.equalsIgnoreCase(GET)){//获取个人收藏列表
			Integer pageSize = Integer.parseInt(pageSizeStr);
			Integer offSet = (Integer.parseInt(pageIndexStr)-1)*pageSize;
			List<Digest> digestList = digestService.queryDigestsByStoreUpAndCustId(custId, Constans.STORE_UP_DIGEST, PlatformSourceEnum.FP_P.getSource(), offSet, pageSize);
			//过滤无用的字段信息
			final int size = includeFields.size();
			String[] arr = (String[])includeFields.toArray(new String[size]);
			SimplePropertyPreFilter spp = new SimplePropertyPreFilter(Digest.class, arr);
			String json = JSON.toJSONString(digestList, spp);
			sender.put("digestList", JSON.parseObject(json, List.class));
			sender.put("hasNext", hasNextPage(digestList, pageSize));
			sender.success(response);
			return;
		}else if(op.equalsIgnoreCase(CANCEL)){//取消收藏
			if(!checkParams(digestIdStr)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			List<Long> digestIdList = transferIdStrToList(digestIdStr);
			int effectRows = storeUpService.deleteByByParams("custId", custId, "targetIds", digestIdList, "platform", PlatformSourceEnum.FP_P.getSource());
			if(effectRows > 0){
				sender.success(response);
				return;
			}else{
				sender.fail(ErrorCodeEnum.ERROR_CODE_22004.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22004.getErrorMessage(), response);
				return;
			}
		}else{
			sender.fail(ErrorCodeEnum.ERROR_CODE_22002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22002.getErrorMessage(), response);
			return;
		}
	}
	
}
