package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.base.account.client.api.IAttachAccountItemsApi;
import com.dangdang.base.account.client.vo.AttachAccountActivityItemsVo;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.dao.IActivityInfoDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.vo.DSDepositPayInfoVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:13:48  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Service
public class ActivityInfoServiceImpl extends BaseServiceImpl<ActivityInfo, Integer>  implements IActivityInfoService{
	@Resource
	private IActivityInfoDao activityInfoDao;
	@Resource
	private IAttachAccountItemsApi attachAccountItemsApi;
	
	@Override
	public void insert(ActivityInfo activityInfo) {
		activityInfoDao.insert(activityInfo);
	}

	@Override
	public void delete(Integer id) {
		activityInfoDao.delete(id);
	}

	@Override
	public IBaseDao<ActivityInfo> getBaseDao() {
		return activityInfoDao;
	}

	@Override
	public List<ActivityInfo> getMonthlyForBuyView() {
		return activityInfoDao.selectList("ActivityInfoMapper.getMonthlyForBuyView");
	}

	@Override
	public List<ActivityInfo> getBuyLuckyBag() {
		return this.findListByParams("activityTypeId",
				ActivityTypeEnum.LUCKY_BAG.getKey(), "withinPeriodValidity", 1,
				"status", Constans.ACTIVITYINFO_STATUS_VALID);
	}
	
	@Override
	public List<DSDepositPayInfoVo> getDSConsumerDepositPayInfo(String fromPaltform) {
		List<DSDepositPayInfoVo> result = new ArrayList<DSDepositPayInfoVo>();
		List<ActivityInfo> activitys = activityInfoDao.selectList("ActivityInfoMapper.getDepositPayInfo", fromPaltform);
		if(!CollectionUtils.isEmpty(activitys)){
			Map<Integer,Integer> giftReadValid = this.getAttachAccountActivity(activitys, fromPaltform);
			if(!CollectionUtils.isEmpty(giftReadValid) && !CollectionUtils.isEmpty(activitys)){
				for(ActivityInfo activityInfo : activitys){
					if(activityInfo.getDepositGiftReadPrice() != 0 && giftReadValid.get(activityInfo.getDepositGiftReadPrice()) != null){
						activityInfo.setEffectiveDay(giftReadValid.get(activityInfo.getDepositGiftReadPrice()));
					}
				}
			}
			for(ActivityInfo activityInfo : activitys){
				result.add(convertToDSDepositPayInfoVo(activityInfo));
			}
		}
		return result;
	}
	
	private DSDepositPayInfoVo convertToDSDepositPayInfoVo(ActivityInfo activityInfo){
		DSDepositPayInfoVo dSDepositPayInfoVo = new DSDepositPayInfoVo();
		dSDepositPayInfoVo.setDepositGiftReadPrice(activityInfo.getDepositGiftReadPrice());
		dSDepositPayInfoVo.setDepositMoney(activityInfo.getDepositMoney());
		dSDepositPayInfoVo.setDepositReadPrice(activityInfo.getDepositReadPrice());
		dSDepositPayInfoVo.setEndTime(activityInfo.getEndTime());
		dSDepositPayInfoVo.setRelationProductId(activityInfo.getRelationProductId());
		dSDepositPayInfoVo.setStartTime(activityInfo.getStartTime());
		dSDepositPayInfoVo.setEffectiveDay(activityInfo.getEffectiveDay());
		dSDepositPayInfoVo.setDepositCode(activityInfo.getDepositCode());
		return dSDepositPayInfoVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDepositRelationProduct(List<String> productIds) {
		StringBuffer relationproductIds = new StringBuffer();
		for(String productId : productIds){
			relationproductIds.append("'").append(productId.toString()).append("',");
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("productIds", relationproductIds.substring(0, relationproductIds.length() - 1));
		return (List<String>)activityInfoDao.getSqlSessionQueryTemplate().selectList("ActivityInfoMapper.getDepositRelationProduct", map);
	}
	
	private Map<Integer,Integer> getAttachAccountActivity(List<ActivityInfo> activitys,String fromPlatform){
		if(StringUtils.isBlank(fromPlatform) || fromPlatform.startsWith("yc_") || CollectionUtils.isEmpty(activitys)){
			return null;
		}
		List<Integer> subGolds = new ArrayList<Integer>();
		for(ActivityInfo activityInfo : activitys){
			if(activityInfo.getDepositGiftReadPrice() != null && activityInfo.getDepositGiftReadPrice() > 0){
				subGolds.add(activityInfo.getDepositGiftReadPrice());
			}
		}
		if(CollectionUtils.isEmpty(subGolds)){
			return null;
		}
		String code =  null;
		if(fromPlatform.equals(PayFromPaltform.DS_ANDROID.getSource())){
			code = "chongjinsongyin_1002_android";
		}else if(fromPlatform.equals(PayFromPaltform.DS_IOS.getSource())){
			code = "chongjinsongyin_1002_ios";
		}else{
			return null;
		}
		List<AttachAccountActivityItemsVo> activityItems = null;
		try {
			activityItems = attachAccountItemsApi.queryActivityItemsByCode(code);						
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(CollectionUtils.isEmpty(activityItems)){
			return null;
		}
		Map<Integer,Integer> giftReadValid = new LinkedHashMap<Integer, Integer>();
		for(AttachAccountActivityItemsVo vo : activityItems){
			if(vo.getFaceValue() != null && subGolds.contains(vo.getFaceValue())){
				giftReadValid.put(vo.getFaceValue(), vo.getEffectiveDay());
			}
		}		
		return giftReadValid;
	}
}