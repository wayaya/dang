/**
 * Description: ConsumerConsumeServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:26:25  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.service.IConsumerConsumeDetailService;
import com.dangdang.digital.service.IConsumerConsumeService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.CreateConsumeVo;

/**
 * Description: 用户消费记录实体service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月14日 上午10:26:25 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ConsumerConsumeServiceImpl extends
		BaseServiceImpl<ConsumerConsume, Long> implements
		IConsumerConsumeService {

	@Resource
	IConsumerConsumeDao consumerConsumeDao;
	@Resource
	IConsumerConsumeDetailService consumerConsumeDetailService;

	@Override
	public IBaseDao<ConsumerConsume> getBaseDao() {
		return this.consumerConsumeDao;
	}

	@Override
	public ConsumerConsume findWithDetailByConsumerConsumeId(
			Long consumerConsumeId) {
		ConsumerConsume consume = this.get(consumerConsumeId);
		if (consume != null && consume.getConsumerConsumeId() != null) {
			consume.setConsumeDetails(consumerConsumeDetailService
					.findListByParams("consumerConsumeId",
							consume.getConsumerConsumeId()));
		}
		return consume;
	}

	@Override
	public ConsumerConsume createConsumerConsumeInfo(
			HttpServletRequest request, HttpServletResponse response,
			ResultSender sender) throws Exception {
		return null;
	}

	@Override
	public boolean checkAndEncapsulateParams(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender,
			CreateConsumeVo createConsumeVo) {
		String fromPaltform = request.getParameter("fromPaltform");
		if(StringUtils.isNotBlank(fromPaltform)){
			if(PayFromPaltform.getBySource(fromPaltform) == null){
				return false;
			}
		}else{
			fromPaltform = PayFromPaltform.YC_ANDROID.getSource();
		}
		createConsumeVo.setFromPaltform(fromPaltform);
		String consumeType = request.getParameter("consumeType");
		if (StringUtils.isNotBlank(consumeType)
				&& StringUtils.isNumeric(consumeType)) {
			createConsumeVo.setConsumeType(Short.valueOf(consumeType));
		} else {
			return false;
		}
		String deviceType = request.getParameter("deviceType");
		if (StringUtils.isNotBlank(deviceType)) {
			createConsumeVo.setDeviceType(deviceType);
		} else {
			return false;
		}
		String propId = request.getParameter("propId");
		String propCount = request.getParameter("propCount");
		if (Constans.BUY_FLAG_PROP.equals(createConsumeVo.getConsumeType()
				.toString())
				&& StringUtils.isNotBlank(propId)
				&& StringUtils.isNumeric(propId)
				&& StringUtils.isNotBlank(propCount)
				&& StringUtils.isNumeric(propCount)) {
			createConsumeVo.getPropMap().put(Integer.valueOf(propId),
					Integer.valueOf(propCount));
		}
		String monthlyId = request.getParameter("monthlyId");
		if (Constans.BUY_FLAG_MONTHLY.equals(createConsumeVo.getConsumeType()
				.toString())
				&& StringUtils.isNotBlank(monthlyId)
				&& StringUtils.isNumeric(monthlyId)) {
			createConsumeVo.setMonthlyId(Integer.valueOf(monthlyId));
		}
		String luckyBagId = request.getParameter("luckyBagId");
		if (Constans.BUY_FLAG_LUCKYBAG.equals(createConsumeVo.getConsumeType()
				.toString())
				&& StringUtils.isNotBlank(luckyBagId)
				&& StringUtils.isNumeric(luckyBagId)) {
			createConsumeVo.setLuckyBagId(Integer.valueOf(luckyBagId));
		}
		String mediaId = request.getParameter("mediaId");
		if (Constans.BUY_FLAG_REWARDS.equals(createConsumeVo.getConsumeType()
				.toString())
				&& StringUtils.isNotBlank(mediaId)
				&& StringUtils.isNumeric(mediaId)) {
			createConsumeVo.setMediaId(Long.valueOf(mediaId));
			String rewardsMoney = request.getParameter("rewardsMoney");
			if (StringUtils.isNotBlank(rewardsMoney)
					&& StringUtils.isNumeric(rewardsMoney)
					&& Integer.valueOf(rewardsMoney) > 0) {
				createConsumeVo.setRewardsMoney(Integer.valueOf(rewardsMoney));
			} else {
				return false;
			}
		}
		String activityIds = request.getParameter("activityIds");
		if (StringUtils.isNotBlank(activityIds)) {
			String[] arrayIds = activityIds.split(",");
			for (String id : arrayIds) {
				if (StringUtils.isNumeric(id)) {
					createConsumeVo.getActivityIds().add(Integer.valueOf(id));
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ConsumerConsume saveConsumerConsumeInfo(
			ConsumerConsume consumerConsume) throws Exception {
		return null;
	}

}
