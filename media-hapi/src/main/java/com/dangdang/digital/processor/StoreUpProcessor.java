package com.dangdang.digital.processor;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.StoreUp;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnSaleVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 收藏接口
 * All Rights Reserved. media收藏,发现收藏,获取收藏信息
 * @version 1.0  2014年12月23日 下午1:59:33  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapistoreUpprocessor")
public class StoreUpProcessor extends BaseApiProcessor {
	
	/**
	 * 收藏类型,media或者发现
	 */
	private static final String DISCOVER_TYPE=Constans.STORE_UP_DISCOVER;//"discover";
	
	private static final String MEDIA_TYPE=Constans.STORE_UP_MEDIA;//"media";
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IStoreUpService storeUpService;
	@Resource
	private ICacheApi  cacheApi; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		// TODO Auto-generated method stub
		String type = request.getParameter(GlobalParamNameUtils.TYPE);//收藏类型,media,discover收藏还是发现收藏
		String op = request.getParameter(GlobalParamNameUtils.OP);//操作类型,get,save
		String token = request.getParameter(GlobalParamNameUtils.TOKEN);
		// 平台来源
		String platformSource = request.getParameter(GlobalParamNameUtils.PLATFORM);
		UserTradeBaseVo custVo = null;
		try{
			custVo = authorityApiFacade.getUserInfoByToken(token);
		}catch(Exception e){
			sender.fail(ErrorCodeEnum.ERROR_CODE_14601.getErrorCode(),"通过token获取用户基本信息出错", response);
			return;
		}
		// 未登录用户直接返回
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		// 验证平台来源
		if (StringUtils.isEmpty(platformSource) || PlatformSourceEnum.getBySource(platformSource) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		if(RequestParamCheckerUtils.isNullOrEmpty(type)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"缺少收藏类型参数type", response);
			return;
		}
		if(RequestParamCheckerUtils.isNullOrEmpty(op)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"缺少操作类型参数op", response);
			return;
		}
		if(!type.equalsIgnoreCase(DISCOVER_TYPE)&& !type.equalsIgnoreCase(MEDIA_TYPE)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"不存在的发现类型:type="+type, response);
			return;
		}
		Long custId = custVo.getCustId();
		if(op.equalsIgnoreCase("save")){
			//保存收藏
			String userName = custVo.getUsername();
			String nickName = custVo.getNickname();
			//收藏media或者发现的编号
			String targetId = request.getParameter("id");
			if(RequestParamCheckerUtils.isNullOrEmpty(targetId)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入参数发现对象编号,id参数值", response);
				return;
			}
			StoreUp su = new StoreUp();
			su.setCustId(custId);
			su.setTargetId(Long.valueOf(targetId));
			su.setType(type.toLowerCase());
			su.setPlatform(platformSource);
			boolean exist = storeUpService.findUniqueByParamsObjs(su)!=null;
			if(exist){
				//已收藏
				sender.fail(ErrorCodeEnum.ERROR_CODE_11006.getErrorCode(),ErrorCodeEnum.ERROR_CODE_11006.getErrorMessage(), response);
				return;
			}
			su.setUserName(userName);
			su.setNickName(nickName);
			//收藏对象的编号
			storeUpService.save(su);
			LogUtil.warn(LOGGER, "添加收藏custId={0},targetId={1}",custId,targetId);
			sender.success(response);
		}else if(op.equalsIgnoreCase("get")){
			//start,end请求参数判断
			ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
			if(!checkResult.success){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
				return;
			}
			
			//查询收藏列表
			if(type.equalsIgnoreCase(DISCOVER_TYPE)){
				//调用发现列表
			}else{
				//得到media列表
				int total = storeUpService.getTotalCount(custId, type,platformSource);
				List<Long> saleIdList = storeUpService.getStoreUpObjectIdListByCustId(checkResult.start,
						checkResult.count, custId, type, platformSource);
				List<MediaSaleCacheVo> listMediaSaleCacheVo = cacheApi.batchGetMediaSaleFromCache(saleIdList);
				if(null == listMediaSaleCacheVo||listMediaSaleCacheVo.size()==0){
					LogUtil.warn(LOGGER, "节目收藏没有数据");
					sender.fail(ErrorCodeEnum.ERROR_CODE_10007.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10007.getErrorMessage(), response);
					return ;
				}
				List<ReturnSaleVo> returnSalelist = ReturnBeanUtils.batchgetReturnSaleListVo(listMediaSaleCacheVo);
				sender.put("saleList", returnSalelist);//销售主体简要内容
				sender.put("total",total);//总的数据量
				sender.success(response);
			}
			
		}else if(op.equalsIgnoreCase("delete")){
			//删除收藏
			String targetId = request.getParameter("id");
			if(RequestParamCheckerUtils.isNullOrEmpty(targetId)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入参数发现对象编号,id参数值", response);
				return;
			}
			int deleteCount = storeUpService.deleteByByParams("custId",custId,"targetId",targetId,"platform",platformSource);
			if(1==deleteCount){
				LogUtil.warn(LOGGER, "删除收藏custId={0},targetId={1}",custId,targetId);
				sender.success(response);
			}else if(0==deleteCount){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
				return ;
			}
		}else{
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"不存在的请求类型:op="+op, response);
			return;
		}
	}

}
