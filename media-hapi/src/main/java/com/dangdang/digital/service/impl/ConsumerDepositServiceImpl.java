/**
 * Description: ConsumerDepositServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:27:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.DeviceTypeEnum;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerDepositDao;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.model.DsPayProduct;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.service.IDsPayProductService;
import com.dangdang.digital.utils.Base64Utils;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.HttpUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ConsumerDepositReturnVo;

/**
 * Description: 用户充值记录实体service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月14日 上午10:27:32 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ConsumerDepositServiceImpl extends
		BaseServiceImpl<ConsumerDeposit, Long> implements
		IConsumerDepositService {
	@Resource
	private ISystemApi systemApi;
	@Resource
	IActivityInfoService activityInfoService;
	@Resource
	IDsPayProductService dsPayProductService;
	@Resource
	IConsumerDepositDao consumerDepositDao;
	private static final String IPADIAP_VALIDATE_URL = "iap.validate.url";
	private static final String SANDBOX_VALIDATE_URL = "sandbox.validate.url";
	private static final String CONSUME_DEPOSIT_MD5_KEY = "consume_deposit_md5_key";
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public IBaseDao<ConsumerDeposit> getBaseDao() {
		return this.consumerDepositDao;
	}

	@Override
	public boolean checkAndEncapsulateParams(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender,
			ConsumerDeposit consumerDeposit) {
		String deviceType = request.getParameter("deviceType");
		if (StringUtils.isNotBlank(deviceType)) {
			consumerDeposit.setDeviceType(deviceType);
		} else {
			LOGGER.info("充值参数校验失败【deviceType】" + deviceType);
			return false;
		}
		String depositOrderNo = request.getParameter("depositOrderNo");
		if (StringUtils.isNotBlank(depositOrderNo)) {
			consumerDeposit.setDepositOrderNo(depositOrderNo);
		}else if(deviceType.toLowerCase().indexOf("android") != -1){
			LOGGER.info("充值参数校验失败【deviceType】" + deviceType);
			return false;
		}
		if(StringUtils.isBlank(request.getParameter("relationProductId"))){
			return false;
		}
		String paymentId = request.getParameter("paymentId");
		if (StringUtils.isNotBlank(paymentId)) {
			if(!StringUtils.isNumeric(paymentId)){
				return false;
			}
			int activityId = Integer.valueOf(paymentId);
			if(activityId == ActivityTypeEnum.ALIPAY_RECHARGE.getKey()
					|| activityId == ActivityTypeEnum.TENPAY_RECHARGE.getKey()
					|| activityId == ActivityTypeEnum.WECHAT_RECHARGE.getKey()
					|| activityId == ActivityTypeEnum.ALIPAY_RECHARGE_1.getKey()
					|| activityId == ActivityTypeEnum.TENPAY_RECHARGE_1.getKey()
					|| activityId == ActivityTypeEnum.WECHAT_RECHARGE_1.getKey()
					|| activityId == ActivityTypeEnum.IOS_RECHARGE.getKey() 
					|| activityId == ActivityTypeEnum.IPAD_RECHARGE.getKey()){
				consumerDeposit.setPayment(paymentId);
			}else{
				return false;
			}
		}
		consumerDeposit.setProductCount(1);
		consumerDeposit.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_CHECK_NOT);
		try {
			encapsulateDepositMoney(request,consumerDeposit);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String payTime = request.getParameter("payTime");
		if (StringUtils.isNotBlank(payTime) && StringUtils.isNumeric(payTime)) {
			consumerDeposit.setPayTime(new Date(Long.valueOf(payTime)));
		} else {
			LOGGER.info("充值参数校验失败【payTime】" + payTime);
			return false;
		}
		return true;
	}

	@Override
	public ConsumerDeposit findLastTimePayment(Long custId, String fromPaltform) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("custId", custId);
		params.put("fromPaltform", fromPaltform);
		return this.consumerDepositDao.selectOne(
				"ConsumerDepositMapper.findLastTimePayment", params);
	}

	@Override
	public List<ConsumerDepositReturnVo> findConsumerDepositPageByCust(
			Long custId, Integer start, Integer count, String fromPaltform) {
		List<ConsumerDepositReturnVo> returnVo = new ArrayList<ConsumerDepositReturnVo>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("custId", custId);
		params.put("start", start);
		params.put("count", count);
		params.put("fromPaltform", fromPaltform);
		List<ConsumerDeposit> deposits = this.consumerDepositDao.selectList(
				"ConsumerDepositMapper.getPageList", params);
		if (!CollectionUtils.isEmpty(deposits)) {
			for (ConsumerDeposit consumerDeposit : deposits) {
				returnVo.add(convertToReturnVo(consumerDeposit));
			}
		}
		return returnVo;
	}

	@Override
	public Integer findConsumerDepositCountByCust(Long custId, String fromPaltform) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("custId", custId);
		params.put("fromPaltform", fromPaltform);
		return (Integer) this.consumerDepositDao.getSqlSessionQueryTemplate()
				.selectOne("ConsumerDepositMapper.pageCount", params);
	}

	private ConsumerDepositReturnVo convertToReturnVo(ConsumerDeposit deposit) {
		ConsumerDepositReturnVo returnVo = new ConsumerDepositReturnVo();
		returnVo.setConsumerDepositId(deposit.getConsumerDepositId());
		returnVo.setCreationDate(deposit.getCreationDate() != null ? deposit
				.getCreationDate().getTime() : null);
		returnVo.setCustId(deposit.getCustId());
		returnVo.setDepositNo(deposit.getDepositNo());
		returnVo.setDepositOrderNo(deposit.getDepositOrderNo());
		returnVo.setMainGold(deposit.getMainGold());
		returnVo.setMoney(deposit.getMoney());
		returnVo.setPayment(ActivityTypeEnum.getByKey(
				Integer.valueOf(deposit.getPayment())).getName());
		returnVo.setPayTime(deposit.getPayTime() != null ? deposit.getPayTime()
				.getTime() : null);
		returnVo.setSubGold(deposit.getSubGold());
		return returnVo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> getValidateResult(String receiptData)
			throws Exception {
		String url = null;
		Map<String, Object> result = new HashMap<String, Object>();
		//检查支付环境
		if (this.checkSandbox(receiptData)) {
			url = ConfigPropertieUtils.getString(SANDBOX_VALIDATE_URL,"https://sandbox.itunes.apple.com/verifyReceipt");
			result.put("isSandbox", "1");
		} else {
			url = ConfigPropertieUtils.getString(IPADIAP_VALIDATE_URL,"https://buy.itunes.apple.com/verifyReceipt");
			result.put("isSandbox", "0");
		}
		result.put("receipt-data", Base64Utils.encodeBytes(receiptData.getBytes("utf-8")));
		String validateResult = HttpUtils.getContentByPost(url, JSON.toJSONString(result).getBytes());
		
		if(StringUtils.isBlank(validateResult)){
			LogUtil.info(LOGGER, "validateResult is null :" + validateResult);
		}
		
		result.put("validateResult", validateResult);
		Map validateResultMap = (Map) JSON.parseObject(validateResult, Map.class);
		
		if (validateResultMap.get("receipt") != null) {
			validateResultMap.putAll((Map) JSON.parseObject(validateResultMap.get("receipt").toString(), Map.class));
		}
		result.putAll(validateResultMap);
		return result;
	}

	@Override
	public String getOrderNo(String receiptData) {
		Matcher m = Pattern.compile("purchase-info\"[^\"]+\"([^\"]+)\"",
				Pattern.CASE_INSENSITIVE).matcher(receiptData);
		if (m.find()) {
			String purchaseInfo = m.group(1);
			try {
				purchaseInfo = new String(Base64.decodeBase64(purchaseInfo), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			Matcher m2 = Pattern.compile("original-transaction-id\"[^\"]+\"(\\d+)\"", Pattern.CASE_INSENSITIVE).matcher(purchaseInfo);
			if (m2.find()) {
				return m2.group(1);
			}
		}
		return null;
	}

	@Override
	public boolean checkSandbox(String receiptData) {
		Matcher m = Pattern.compile("environment\"[^\"]+\"([^\"]+)\"",
				Pattern.CASE_INSENSITIVE).matcher(receiptData);
		if (m.find()) {
			String environment = m.group(1);
			if(StringUtils.isNotBlank(environment) && environment.trim().toLowerCase().equals("sandbox")){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getProductId(String receiptData) {
		Matcher m = Pattern.compile("purchase-info\"[^\"]+\"([^\"]+)\"",
				Pattern.CASE_INSENSITIVE).matcher(receiptData);
		if (m.find()) {
			String purchaseInfo = m.group(1);
			try {
				purchaseInfo = new String(Base64.decodeBase64(purchaseInfo),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			Matcher m2 = Pattern.compile(
					"product-id\"[^\"]+\"([^_]+_ebook_[\\w]+_(\\d+))\"",
					Pattern.CASE_INSENSITIVE).matcher(purchaseInfo);
			if (m2.find()) {
				return m2.group(1);
			}
		}
		return null;
	}

	@Override
	public boolean checkDepositMD5Sign(HttpServletRequest request) {
		String md5Sign = request.getParameter("md5Sign");
		String deviceType = request.getParameter("deviceType");
		if(StringUtils.isBlank(deviceType)){
			return false;
		}
		if (StringUtils.isBlank(md5Sign)) {
			if(StringUtils.equals(deviceType.toLowerCase(),DeviceTypeEnum.YC_ANDROID.getDeviceTypeNo().toLowerCase())){
				return true;
			}else{
				return false;
			}			
		}
		String md5SingKey = systemApi.getProperty(CONSUME_DEPOSIT_MD5_KEY);
		if (StringUtils.isBlank(md5SingKey)) {
			return true;
		}
		StringBuffer paramStr = new StringBuffer("");
		paramStr.append(
				StringUtils.isNotBlank(request.getParameter("token")) ? request
						.getParameter("token") : "")
				.append(StringUtils.isNotBlank(request
						.getParameter("depositOrderNo")) ? request
						.getParameter("depositOrderNo") : "")
				.append(StringUtils.isNotBlank(request.getParameter("payTime")) ? request
						.getParameter("payTime") : "")
				.append(StringUtils.isNotBlank(request
						.getParameter("deviceType")) ? request
						.getParameter("deviceType") : "")
				.append(StringUtils.isNotBlank(request
						.getParameter("relationProductId")) ? request
						.getParameter("relationProductId") : "")
				.append(StringUtils.isNotBlank(request
						.getParameter("paymentId")) ? request
						.getParameter("paymentId") : "")
				.append(StringUtils.isNotBlank(request
						.getParameter("fromPaltform")) ? request
						.getParameter("fromPaltform") : "")
				.append(StringUtils.isNotBlank(request
						.getParameter("relationProductCount")) ? request
						.getParameter("relationProductCount") : "");
		if (md5Sign.equals(DigestUtils.md5Hex(paramStr.append(md5SingKey).toString()))) {
			return true;
		}
		return false;
	}
	
	private boolean encapsulateDepositMoney(HttpServletRequest request,
			ConsumerDeposit consumerDeposit) throws Exception {
		String fromPaltform = request.getParameter("fromPaltform");
		if (StringUtils.isNotBlank(fromPaltform)) {
			if (PayFromPaltform.getBySource(fromPaltform) == null) {
				LogUtil.info(LOGGER, "fromPaltform :" + fromPaltform);
				return false;
			}
		} else {
			fromPaltform = PayFromPaltform.YC_ANDROID.getSource();
		}
		consumerDeposit.setFromPaltform(fromPaltform);
		String relationProductId = request.getParameter("relationProductId");
		
		LogUtil.info(LOGGER, "relationProductId :" + relationProductId);
		
		if (StringUtils.isNotBlank(relationProductId)) {
			consumerDeposit.setRelationProductId(relationProductId.trim());
			String[] productIds = consumerDeposit.getRelationProductId().split(",");
			int money = 0;
			int mainGold = 0;
			int subGold = 0;
			boolean hasActivity = false;
			if (productIds.length == 1) {
				Integer payment = consumerDeposit.getPayment() != null ? Integer.valueOf(consumerDeposit.getPayment()) : null;
				ActivityInfo activityInfo = activityInfoService.getConsumerDepositInfo(relationProductId, payment,fromPaltform);
				
				if (activityInfo != null && activityInfo.getActivityTypeId() != null 
						&& 
						(activityInfo.getActivityTypeId() == ActivityTypeEnum.ALIPAY_RECHARGE.getKey()
						|| activityInfo.getActivityTypeId() == ActivityTypeEnum.TENPAY_RECHARGE.getKey()
						|| activityInfo.getActivityTypeId() == ActivityTypeEnum.WECHAT_RECHARGE.getKey()
						|| activityInfo.getActivityTypeId() == ActivityTypeEnum.ALIPAY_RECHARGE_1.getKey()
						|| activityInfo.getActivityTypeId() == ActivityTypeEnum.TENPAY_RECHARGE_1.getKey()
						|| activityInfo.getActivityTypeId() == ActivityTypeEnum.WECHAT_RECHARGE_1.getKey()
						|| activityInfo.getActivityTypeId() == ActivityTypeEnum.IOS_RECHARGE.getKey() 
						|| activityInfo.getActivityTypeId() == ActivityTypeEnum.IPAD_RECHARGE.getKey())) {
					
					mainGold = activityInfo.getDepositReadPrice() != null ? activityInfo.getDepositReadPrice() : 0;
					subGold = activityInfo.getDepositGiftReadPrice() != null ? activityInfo.getDepositGiftReadPrice() : 0;
					money = activityInfo.getDepositMoney() != null ? activityInfo.getDepositMoney() : 0;
					hasActivity = true;
				}
			}
			if (!hasActivity) {
				for (String productId : productIds) {
					DsPayProduct dsPayProduct = dsPayProductService.findUniqueByParams("productId", productId);
					if (dsPayProduct == null) {
						return false;
					}
					int productMoney = dsPayProduct.getMoney() != null&& dsPayProduct.getMoney() > 0 ? dsPayProduct.getMoney() : 0;
					money += productMoney;
				}
				mainGold = money;
			}
			consumerDeposit.setSubGold(subGold);
			consumerDeposit.setMoney(money);
			consumerDeposit.setMainGold(mainGold);
			consumerDeposit.setGiveMainGold(0);
			return true;
		} else {
			LogUtil.info(LOGGER, "relationProductId :" + relationProductId);
			return false;
		}
	}

}
