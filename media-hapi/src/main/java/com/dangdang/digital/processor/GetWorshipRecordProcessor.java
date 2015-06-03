package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.service.IWorshipRecordService;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.vo.ReturnWorShipRecord;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 获取膜拜我的列表和被我膜拜的记录列表
 * All Rights Reserved.
 * @version 1.0  2015年3月16日 上午10:12:47  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapigetWorshipRecordprocessor")
public class GetWorshipRecordProcessor  extends BaseApiProcessor{
	private  final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Resource
	private IWorshipRecordService  worshipRecordService;
	
	@Resource
	private ICacheApi  cacheApi; 

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private ReturnBeanUtils returnBeanUtils;
	
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
			//0:膜拜我的,1:我膜拜过的
			final String type = request.getParameter(GlobalParamNameUtils.TYPE);
			//操作类型
			 String token = request.getParameter(GlobalParamNameUtils.TOKEN);
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

			//start,end请求参数判断
			ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
			if(!checkResult.success){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
				return;
			}
			if(null == type || type.trim().isEmpty()){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"参数不存在"+GlobalParamNameUtils.TYPE, response);
				return ;
			}
			Integer iType = Integer.valueOf(type);
			//查询我膜拜过的列表或者膜拜过我的列表 
			if(iType.intValue()!=WorshipRecord.Worship_TO_ME.intValue() && iType.intValue()!=WorshipRecord.Worship_OF_ME.intValue()){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"参数值不对"+GlobalParamNameUtils.TYPE, response);
				return ;
			}
			ResultTwoTuple<Integer,List<WorshipRecord>> resultTuple = this.cacheApi.getWorshipRecord(checkResult.start,checkResult.end,custVo.getCustId(),type);
			List<WorshipRecord> worshipRecordList= resultTuple.listId;
			List<ReturnWorShipRecord> rwsrList = returnBeanUtils.getReturnWorShipRecordList(custVo.getCustId(),worshipRecordList);
			sender.put("total", resultTuple.total);
			sender.put("worShipRecord", rwsrList);
			sender.success(response);
	}
	
}
