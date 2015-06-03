package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.service.IWorshipRecordService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 获取膜拜我的列表和被我膜拜的记录列表
 * All Rights Reserved.
 * @version 1.0  2015年3月16日 上午10:12:47  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapideleteWorshipRecordprocessor")
public class DeleteWorshipRecordProcessor  extends BaseApiProcessor{
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
			 String token  = request.getParameter(GlobalParamNameUtils.TOKEN);
			 String custId = request.getParameter(GlobalParamNameUtils.custId);
			 /**
			  * type:0,删除我膜拜过的记录(偶像) custId-我膜拜过用户 的custId,1:删除膜拜过我的(粉丝)custId-膜拜我的用户的custId
			  * 
			  */
			 String type = request.getParameter(GlobalParamNameUtils.TYPE);
			 Integer iType = 0;
			 if(null == type || type.trim().isEmpty()){
				 sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"参数不存在"+GlobalParamNameUtils.TYPE, response);
				 return ;
			 }else {
				 iType = Integer.valueOf(type);
				 if(iType != WorshipRecord.Worship_OF_ME && iType != WorshipRecord.Worship_TO_ME){
					 sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"参数不合法"+GlobalParamNameUtils.TYPE, response);
					 return ;
				 }
			 }
			 
			 if(null == custId || custId.trim().isEmpty()){
				 sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"参数不存在"+GlobalParamNameUtils.custId, response);
				 return ;
			 }
			 
			 Long custIdLong = DesUtils.decryptCustId(custId);
			 UserTradeBaseVo custVo = null;
			try {
				custVo = authorityApiFacade.getUserInfoByToken(token);
			} catch (Exception e) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_14601.getErrorCode(),"通过token获取用户基本信息出错", response);
				return;
			}
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}

			//删除膜拜记录
			String recordId = request.getParameter(GlobalParamNameUtils.WORSHIP_RECORD_ID);
			if(null == recordId || recordId.trim().isEmpty()){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"参数不存在"+GlobalParamNameUtils.WORSHIP_RECORD_ID, response);
				return ;
			}
			Long lRecordId = 0L;
			try{
				lRecordId = Long.valueOf(recordId);
			}catch(NumberFormatException e){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"参数值不对,必须为数字"+GlobalParamNameUtils.WORSHIP_RECORD_ID, response);
				return ;
			}
			WorshipRecord wr = new WorshipRecord();
			wr.setRecordId(lRecordId);
			
			//需要清除一下缓存
			//我膜拜过的
			String cacheKeyMyWorShip  = "" ;//Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(custVo.getCustId())).concat("_"+WorshipRecord.Worship_OF_ME);
			//被膜拜用户的
			String cacheKeyWorShipToMe ="" ;// Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(activityVo.getWorshipedCustId())).concat("_"+WorshipRecord.Worship_TO_ME);
			if(WorshipRecord.Worship_OF_ME == iType){
				//删除我膜拜过的记录(偶像),双方都看不到膜拜记录
			//	wr.setStatus(WorshipRecord.Worship_DEL_ALL);
				wr.setStatus(0);
				cacheKeyMyWorShip = Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(custVo.getCustId())).concat("_"+WorshipRecord.Worship_OF_ME);
				cacheKeyWorShipToMe = Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(custIdLong)).concat("_"+WorshipRecord.Worship_TO_ME);
			}else{
				//删除膜拜过我的记录(粉丝),我的粉丝列表没有该记录,但膜拜者还有这条记录
//				wr.setStatus(WorshipRecord.Worship_DEL_TO_ME);
				wr.setStatus(2);
				cacheKeyMyWorShip = Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(custVo.getCustId())).concat("_"+WorshipRecord.Worship_TO_ME);
			}
			worshipRecordService.update(wr);
			cacheApi.cleanCacheByKey(cacheKeyWorShipToMe);
			cacheApi.cleanCacheByKey(cacheKeyMyWorShip);//清除我的膜拜记录缓存
			LOGGER.info("软删除膜拜记录"+recordId);
			sender.success(response);
	}
	
}
