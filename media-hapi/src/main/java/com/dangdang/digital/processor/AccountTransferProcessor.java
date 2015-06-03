package com.dangdang.digital.processor;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.IConsumeApi;
import com.dangdang.digital.api.IMessageApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.CustomerGiftRecord;
import com.dangdang.digital.model.Message;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICustomerGiftRecordService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.vo.CreateConsumeVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 转账接口
 * All Rights Reserved.
 * @version 1.0  2015年3月17日 下午3:22:01  by 魏嵩（weisong@dangdang.com）创建
 */
@Component("hapitransferAccountprocessor")
public class AccountTransferProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountTransferProcessor.class);
	
	@Resource
	IMessageApi messageApi;
	@Resource
	IUserService userService;
	@Resource
	IConsumeApi consumeApi;
	@Resource
	IActivityUserService activityUserService;
	@Resource
	private IAuthorityApiFacade authorityApiFacade;
	@Resource
	private ICustomerGiftRecordService customerGiftRecordService;
	@Resource
	private RabbitTemplate rabbitTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 平台来源
		String platformSource = request.getParameter("platformSource");
		
		if (StringUtils.isBlank(platformSource)) {
			LogUtil.info(LOGGER, "参数：platformSource为空！");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 验证平台来源
		if (PlatformSourceEnum.getBySource(platformSource) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		String deviceType = request.getParameter("deviceType");
		if(StringUtils.isEmpty(deviceType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23010.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23010.getErrorMessage(), response);
			return;
		}
		
		String tokenStr = request.getParameter("token");
		Long custId = -1L;
		String userName = "";
		String nickname = "";
		if(!StringUtils.isEmpty(tokenStr)){
			UserTradeBaseVo vo =  authorityApiFacade.getUserWholeInfoByToken(tokenStr);
			if(! (vo==null || vo.getCustId()==null) ){
				custId = vo.getCustId();
				userName = vo.getUsername();
				nickname = vo.getNickname();
			}
		}
		
		if(custId.equals(-1L)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		String receiverStr = request.getParameter("receiver");
		if(StringUtils.isEmpty(receiverStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23001.getErrorMessage(), response);
			return;
		}
		
		Long receiver = -1L;
		try{
			receiver = DesUtils.decryptCustId(receiverStr);
		}catch(Exception e){
		}
		if(receiver==null || receiver.longValue() == -1L){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23001.getErrorMessage(), response);
			return;
		}
		
		if(receiver.equals(custId)){
			
			sender.fail(ErrorCodeEnum.ERROR_CODE_23002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23002.getErrorMessage(), response);
			return;
		}
		
		
		String typeStr = request.getParameter("type");
		if(StringUtils.isEmpty(typeStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23003.getErrorMessage(), response);
			return;
		}

		int type = SafeConvert.convertStringToInteger(typeStr, -1);
		if(type==-1){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23003.getErrorMessage(), response);
			return;
		}
		
		String amountStr = request.getParameter("amount");
		if(StringUtils.isEmpty(amountStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23004.getErrorMessage(), response);
			return;
		}
		long amount = SafeConvert.convertStringToLong(amountStr, -1L);
		int amountInt = SafeConvert.convertStringToInteger(amountStr, -1);
		if(amount<=0 || amountInt<=0){
			sender.fail(ErrorCodeEnum.ERROR_CODE_23004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_23004.getErrorMessage(), response);
			return;
		}
		
		String content = request.getParameter("message");
		
		if(StringUtils.isEmpty(content)){
			content = "我这么任性，只因你颜值爆表";
		}else {
			
			content = new String(URLDecoder.decode(content, "UTF-8"));
			if(content.length()>20){
				content = content.substring(0, 20);
			}
		}
		
		String title = "";
		if(type==1){
			title = "给亲封了"+amount+"个福袋的红包";
		}else if(type==2){
			title = "给亲封了"+amount+"金铃铛的特大红包";
		}
		
		CustomerGiftRecord record = new CustomerGiftRecord();
		record.setContent(content);
		record.setAmount(amount);
		record.setType(type);
		record.setCreationDate(new Date());
		record.setPlatformSource(platformSource);
		record.setReceiverCustId(receiver);
		record.setSenderCustId(custId);
		record.setStatus(1);
		record.setDeviceType(deviceType);
		customerGiftRecordService.save(record);
		
		CustomerGiftRecord recordForUpdate = new CustomerGiftRecord();
		recordForUpdate.setCustomerGiftRecordId(record.getCustomerGiftRecordId());
		Message message = new Message();
		if(type==2){
			message.setIsAllDevice(0);
			//消费
			try{
				CreateConsumeVo createConsumeVo = new CreateConsumeVo();
				createConsumeVo.setCustId(custId);
				createConsumeVo.setUsername(userName);
				createConsumeVo.setRewardsMoney(amountInt);
				createConsumeVo.setDeviceType(deviceType);
				createConsumeVo.setConsumeType(Short.valueOf(Constans.BUY_FLAG_BALANCETRANSFER));
				
				consumeApi.createAndSaveConsume(createConsumeVo);
				
			}catch(Exception e){
				LOGGER.error("转账消费失败：", e);
				sender.fail(ErrorCodeEnum.ERROR_CODE_23005.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_23005.getErrorMessage(), response);
				return;
			}
			
			//更新状态为消费成功
			recordForUpdate.setStatus(2);
			customerGiftRecordService.update(recordForUpdate);
			
			//充值
			try{
				UserTradeBaseVo vo = new UserTradeBaseVo();
				vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
				vo.setCustId(receiver);
				vo.setConsumeSource(deviceType);
				// 唯一单号
				String depositNo = userService.createTradeNo("TRANS",receiver);
				vo.setSourceOrderNo(depositNo);
				vo.setUsername(userName);
				vo.setRequireRechargeMainAmount(amountInt);
				vo.setRequireRechargeSubAmount(0);
				userService.tradeForUser(vo);
				if (!vo.isTradeResult()) {
					LOGGER.error("转账充值失败："+record.getCustomerGiftRecordId());
					sender.fail(ErrorCodeEnum.ERROR_CODE_23006.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_23006.getErrorMessage(), response);
					return;
				}
			}catch(Exception e){
				LOGGER.error("转账充值失败：", e);
				sender.fail(ErrorCodeEnum.ERROR_CODE_23006.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_23006.getErrorMessage(), response);
				return;
			}
		}
		
		if(type==1){
			message.setIsAllDevice(1);
			// 扣福袋、充福袋
			try{
				activityUserService.transferLuckyPacket(custId, receiver, SafeConvert.convertStringToInteger(amount+"", Integer.MAX_VALUE));
			}catch(MediaException e){
				ErrorCodeEnum errorInfo = ErrorCodeEnum.getByErrorCode(SafeConvert.convertStringToInteger(e.getErrorCode(), 22008));
				sender.fail(errorInfo.getErrorCode(),
						errorInfo.getErrorMessage(), response);
				return;
			}
		}
		
		//更新状态为转账交易成功
		recordForUpdate.setStatus(3);
		customerGiftRecordService.update(recordForUpdate);
		
		//组织参数、包括跳转参数、红包参数（已砍掉）等，调用dubbo接口存消息 
		
		message.setAppId(platformSource);
		message.setAmount(amount);
		message.setCreationDate(new Date());
		message.setDescription(content);
		message.setIsRead(0);
		message.setOpenShareType(type);
		message.setTitle(title);
		message.setReceiverCustId(receiver);
		message.setSenderCustId(custId);
		message.setDeviceType(deviceType);
		
		Map<String, Object> customContent = new HashMap<String, Object>();
		
		if(type==1){
			customContent.put("type", "2");
			customContent.put("custId", receiverStr);
		}		
		message.setCustomContent(JSON.toJSONString(customContent));		
		messageApi.saveMessageInfo(message);
		masterRedisTemplate.delete(Constans.MEDIA_MESSAGE_CACHE_KEY+receiver);
		
		//通知被塞红包的用户
		if(nickname != null){
			message.setTitle(nickname + message.getTitle());
		}
		rabbitTemplate.convertAndSend("media.remind.user.transfer.queue", message);
		
		sender.put("updateResult", "OK");
		sender.success(response);
	}
}
