package com.dangdang.digital.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushPlanDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.model.CloudPushPlanStatus;
import com.dangdang.digital.service.ICloudPushDataService;
import com.dangdang.digital.service.ICloudPushPlanService;
import com.dangdang.digital.service.ICloudPushPlanStatusService;
import com.dangdang.digital.utils.DateUtil;

@Service
@Transactional
public class CloudPushPlanServiceImpl extends BaseServiceImpl<CloudPushPlan, Long> implements ICloudPushPlanService{

	@Resource
	ICloudPushPlanDao cloudPushPlanDao;
	@Resource
	ICloudPushDataService cloudPushDataService;
	@Resource
	ICloudPushPlanStatusService cloudPushPlanStatusService;
	
	@Override
	public IBaseDao<CloudPushPlan> getBaseDao() {
		return cloudPushPlanDao;
	}

	@Override
	public void updatePushPlanStatus(List<Long> idsToSet, Integer planStatus) {
		
		cloudPushPlanDao.updatePushPlanStatus(idsToSet, planStatus);
	}

	@Override
	public void updatePlan(CloudPushPlan cloudPushPlan, Integer editMode) throws MediaException{
		
		this.update(cloudPushPlan);
		//mode, 1 正常修改 2 清数据后修改 
		if(editMode!=null && editMode ==2){
			
			//判断状态，如果正在跑数据，那么返回错误， 如果正在发送，也返回错误
			CloudPushPlanStatus cloudPushPlanStatus = new CloudPushPlanStatus();
			cloudPushPlanStatus.setCreationDate(DateUtil.getOnlyDay(new Date()));
			cloudPushPlanStatus.setPlanId(cloudPushPlan.getCloudPushPlanId());
			List<CloudPushPlanStatus> statusList = cloudPushPlanStatusService.findListByParamsObjs(cloudPushPlanStatus);
			if(statusList!=null && statusList.size()>0){
				CloudPushPlanStatus status = statusList.get(0);
				if( status.getPlanJobStatus()!=null && status.getPlanJobStatus()==1){
					throw new MediaException("10000", "此推送消息的数据线程正在准备数据，请等待数据准备完成再进行编辑");
				}else if( status.getPlanSendStatus()!=null && status.getPlanSendStatus()==2){
					throw new MediaException("10001", "此推送消息的发送线程正在发送数据，不能强制清除，否则将会重新发送");
				}else if( status.getPlanSendStatus()!=null && status.getPlanSendStatus()==3){
					throw new MediaException("10003", "此推送消息今天的内容已经发送完毕，不能强制清除，否则将会重新发送");
				}
			}
			//清数据
			cloudPushDataService.deleteTodayDataByPlanId(new Date(), cloudPushPlan.getCloudPushPlanId());
			cloudPushPlanStatusService.deleteByParamsObjs(cloudPushPlanStatus);
		}
		
	}

}
