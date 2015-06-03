package com.dangdang.digital.job.push.worker;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dangdang.digital.model.CloudPushPlan;

public class AutoPushDataWorker {
	
	Logger logger = LoggerFactory.getLogger(AutoPushDataWorker.class);
	CloudPushPlan plan;
	List<Long> custIds;

	public AutoPushDataWorker(CloudPushPlan plan, List<Long> custIds) {
		this.plan = plan;
		this.custIds = custIds;
	}

	public void work() {
		
		logger.info("PlanId: "+plan.getCloudPushPlanId()+" PlanName:"+plan.getPlanName()+" start working 开始运行");

		if( custIds == null ){
			logger.error("PlanId: "+plan.getCloudPushPlanId()+" PlanName:"+plan.getPlanName()+" custIds null，结束运行");
		}
		
		int total = custIds.size();
		if(total==0){
			logger.error("PlanId: "+plan.getCloudPushPlanId()+" PlanName:"+plan.getPlanName()+" custIds empty，结束运行");
			return;
		}
		
		String message = plan.getMessageDescription();
		UserIdsToPushDataRunner runner = new UserIdsToPushDataRunner(custIds, plan, message);
		try {
			runner.run();
		} catch (Exception e) {
			logger.error("AutoPushDataRunner --> AutoPushDataRunner exception ", e);
			return;
		}
		logger.info("PlanId: "+plan.getCloudPushPlanId()+" PlanName:"+plan.getPlanName()+" 运行结束");
	}

}
