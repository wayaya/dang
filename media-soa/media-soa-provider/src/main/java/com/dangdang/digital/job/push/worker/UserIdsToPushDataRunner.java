package com.dangdang.digital.job.push.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.DDThreadStatus;
import com.dangdang.digital.model.CloudPushData;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.model.Notification;
import com.dangdang.digital.model.NotificationApikey;
import com.dangdang.digital.model.PushParam;
import com.dangdang.digital.service.ICloudPushDataService;
import com.dangdang.digital.service.INotificationApikeyService;
import com.dangdang.digital.service.INotificationService;
import com.dangdang.digital.system.SpringContextHolder;
import com.dangdang.digital.utils.BaiduPushUtil;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.SafeConvert;

public class UserIdsToPushDataRunner {
	
	private static int pageSize = 500;
	private static final int THREAD_POOL_KEEP_ALIVE_TIME = 300;
	private static final int THREAD_POOL_SIZE = 20;
	private ThreadPoolExecutor dataPrepareThreadPool = null;
	private static Pattern dotPattern = Pattern.compile("\\.");
	
	public List<Long> userIds;
	public Map<Long, String> userIdsDescriptionMap ;
	public CloudPushPlan cloudPushPlan;
	private INotificationService notificationService = SpringContextHolder.getBean("notificationService");
	private INotificationApikeyService notificationApikeyService = SpringContextHolder.getBean("notificationApikeyService");
	private ICloudPushDataService cloudPushDataService = SpringContextHolder.getBean("cloudPushDataService");
	private String commonPushMsg = null;
	Logger logger = LoggerFactory.getLogger(UserIdsToPushDataRunner.class);
	
	/**
	 * 
	 * @param userIds
	 * @param cloudPushPlan
	 * @param commonPushMsg 如果不为空，以commonPushMsg 为内容推送，不用cloudPushPlan的messageDescription
	 */
	public UserIdsToPushDataRunner(List<Long> userIds, CloudPushPlan cloudPushPlan, String commonPushMsg){
		this.userIds = userIds;
		this.cloudPushPlan = cloudPushPlan;
		this.commonPushMsg = commonPushMsg;
		
		if(userIds!=null && userIds.size()>0){
			initThreadPool( userIds.size());
		}
	}
	
	public UserIdsToPushDataRunner(Map<Long, String> userIdsDescriptionMap, CloudPushPlan cloudPushPlan){
		this.userIdsDescriptionMap = userIdsDescriptionMap;
		this.cloudPushPlan = cloudPushPlan;
		initThreadPool( userIdsDescriptionMap.size());
	}
	
	public void run() throws Exception {
		
		List<UserIdsToPushDataTask> taskList = new ArrayList<UserIdsToPushDataTask>();
		List<Long> userSubList = new ArrayList<Long>();
		Map<Long, String> userDescriptionSubMap = new HashMap<Long, String>();
		
		//不需要个性化推荐语的任务
		if(userIds!=null){
			for(Long userId: userIds){
				
				if(userSubList.size()==pageSize){
					UserIdsToPushDataTask task = new UserIdsToPushDataTask(new ArrayList<Long>(userSubList), (CloudPushPlan)cloudPushPlan.clone(), commonPushMsg);
					taskList.add(task);
					userSubList = new ArrayList<Long>();
				}
				
				userSubList.add(userId);
			}
			
			if(userSubList.size()>0){
				UserIdsToPushDataTask task = new UserIdsToPushDataTask(new ArrayList<Long>(userSubList), (CloudPushPlan)cloudPushPlan.clone(), commonPushMsg);
				taskList.add(task);
			}
		}else if(userIdsDescriptionMap!= null){
			//需要个性化推荐语的任务
			for(Long userId: userIdsDescriptionMap.keySet()){
				
				if(userDescriptionSubMap.size()==pageSize){
					UserIdsToPushDataTask task = new UserIdsToPushDataTask(new HashMap<Long, String>(userDescriptionSubMap), (CloudPushPlan)cloudPushPlan.clone());
					taskList.add(task);
					userDescriptionSubMap = new HashMap<Long, String>();
				}
				
				userDescriptionSubMap.put(userId, userIdsDescriptionMap.get(userId));
			}
			
			if(userDescriptionSubMap.size()>0){
				UserIdsToPushDataTask task = new UserIdsToPushDataTask(new HashMap<Long, String>(userDescriptionSubMap), (CloudPushPlan)cloudPushPlan.clone());
				taskList.add(task);
			}
			
		}
		
		for(UserIdsToPushDataTask task: taskList){
			dataPrepareThreadPool.execute(task);
		}
		
		if(taskList.size()>0){
			
			boolean falg = true;
			while(falg){
				
				boolean allFinished = true;
				for(UserIdsToPushDataTask executer: taskList){
					
					if(executer.getStatus() == DDThreadStatus.WORKING){
						allFinished = false;
						break;
					}
				}
				if(allFinished){
					falg = false;
				}else{
					Thread.sleep(200);
				}
			}
		}
	}
	

	public class UserIdsToPushDataTask implements Runnable {
		
		Logger log = LoggerFactory.getLogger(UserIdsToPushDataTask.class);
		
		private DDThreadStatus status = DDThreadStatus.WORKING;
		private List<Long> ids;
		private CloudPushPlan plan;
		private Map<Long, String> userIdsDescriptionMap ;
		private String commonPushMsg = null;
		
		
		public UserIdsToPushDataTask(List<Long> userIds, CloudPushPlan cloudPushPlan, String commonPushMsg){
			this.ids = userIds;
			this.plan = cloudPushPlan;
			this.commonPushMsg = commonPushMsg;
		}
		
		public UserIdsToPushDataTask(Map<Long, String> userIdsDescriptionMap, CloudPushPlan cloudPushPlan){
			this.userIdsDescriptionMap = userIdsDescriptionMap;
			this.plan = cloudPushPlan;
		}
		
		@Override
		public void run() {
			try{
				//这个也比较简单，条件：userIds, android/ios  查出来百度对应的 userId, channelId的List
				List<Long> usingUserIds = new ArrayList<Long>();
				if(ids != null){
					usingUserIds = ids;
				}else if(userIdsDescriptionMap!=null){
					usingUserIds.addAll(userIdsDescriptionMap.keySet());
				}
				
				if(usingUserIds.size()==0){
					log.error("planId 为："+plan.getCloudPushPlanId()+"的userIds为空");
					return;
				}
				
				//处理 手机系统
				
				List<Integer> deviceTypes = new ArrayList<Integer>();
				
				if(plan.getDeviceTypeAndroid()!=null && plan.getDeviceTypeAndroid().booleanValue()){
					deviceTypes.add(3);
				}
				if(plan.getDeviceTypeIos()!=null && plan.getDeviceTypeIos().booleanValue()){
					deviceTypes.add(4);
				}
				
				if(deviceTypes.size()==0){
					init();
					return;
				}
				
				//根据手机系统，custIds查出来对应的基础信息
				List<Notification> notificationList = notificationService.getNotificationListByCustIdsAndDeviceType(plan.getAppId(), usingUserIds, deviceTypes );
				
				NotificationApikey apiKey = notificationApikeyService.findByAppId(plan.getAppId());
				if(apiKey==null){
					log.error("AppId 为："+plan.getAppId()+"的应用ApiKey为空");
					init();
					return;
				}
				
				int messageType = 1;
				
				if(plan.getMessageType()!=null){
					messageType = plan.getMessageType();
		    	}
				
				
				Map<String, String> androidCustomContentMap = new HashMap<String, String>();
				Map<String, String> iosCustomContentMap = new HashMap<String, String>();
				
		    	if(StringUtils.isNotEmpty(plan.getCustomContent())){
		    		androidCustomContentMap = JSON.parseObject(plan.getCustomContent(), Map.class);
		    	}
		    	
		    	if(StringUtils.isNotEmpty(plan.getCustomContentIos())){
		    		iosCustomContentMap = JSON.parseObject(plan.getCustomContentIos(), Map.class);
		    	}
				
				//存任务数据结果表
				List<CloudPushData> dataListToSave = new ArrayList<CloudPushData>();
				
				for(Notification notification: notificationList){
					
					
					if(isBelowVersion(notification, plan)){
						continue;
					}
				
					PushParam param = new PushParam();
			    	param.setApiKey(apiKey.getApiKey());
			    	param.setSecret(apiKey.getSecret());
			    	//发送给1个人
			    	param.setPushType(1);
			    	param.setUserId(notification.getExtUserId());
			    	param.setChannelId(notification.getExtChannelId());
			    	
//			    	PushMessage pushMsg = new PushMessage();
			    	
			    	Map<String, Object> pushMsg = new HashMap<String, Object>();
			    	
//			    	pushMsg.setTitle(plan.getMessageTitle());
//			    	pushMsg.setOpen_type(plan.getOpenType());
//			    	pushMsg.setUrl(plan.getOpenUrl());
//			    	pushMsg.put("title", plan.getMessageTitle());
			    	
			    	String description = "";
			    	
			    	if(userIdsDescriptionMap!=null){
			    		//自动任务中的个性化推荐
			    		description=userIdsDescriptionMap.get(notification.getCustId());
			    	}else{
			    		
			    		if(StringUtils.isNotEmpty(commonPushMsg)){
			    			description = commonPushMsg;
			    		}else{
			    			description = plan.getMessageDescription();
			    		}
			    	}
			    	
			    	if(messageType==1){
			    		//通知需要根据百度的Android/IOS接口规范来开发
				    	if(notification.getDeviceType()!=null && notification.getDeviceType()==3){
				    		//安卓的才支持title、open_type、url参数
				    		if(messageType==1){
						    	pushMsg.put("open_type", plan.getOpenType());
						    	pushMsg.put("url", plan.getOpenUrl());
					    	}
				    		pushMsg.put("description", description);
				    		if(androidCustomContentMap!=null){
				    			pushMsg.put("custom_content", androidCustomContentMap);
				    		}
				    		
				    	}else if(notification.getDeviceType()!=null && notification.getDeviceType()==4){
				    		//IOS 参数特殊处理
				    		
				    		Map<String, Object> aps = new HashMap<String, Object>();
					    	aps.put("alert", description);
					    	aps.put("badge", 1);
					    	pushMsg.put("aps", aps);
					    	
					    	if(iosCustomContentMap!=null){
					    		pushMsg.putAll(iosCustomContentMap);
					    	}
				    	}
			    	}else{
			    		//透传消息，可以自定义
			    		pushMsg.put("description", description);
			    		
			    		if(notification.getDeviceType()!=null && notification.getDeviceType()==3){
			    			if(androidCustomContentMap!=null){
			    				pushMsg.putAll(androidCustomContentMap);
			    			}
			    		}else if(notification.getDeviceType()!=null && notification.getDeviceType()==4){
			    			if(iosCustomContentMap!=null){
			    				pushMsg.putAll(iosCustomContentMap);
			    			}
			    		}
			    	}
			    	
			    	//IOS透传也不给title，跟百度保持一致
			    	if(notification.getDeviceType()!=null && notification.getDeviceType()==3){
			    		pushMsg.put("title", plan.getMessageTitle());
			    	}
			    	
		    		param.setMessageType(messageType);
		    		param.setDeviceType(notification.getDeviceType());
		    		
		    		String unsigned = BaiduPushUtil.getUnsignedParameterMapString(param, pushMsg );
		    		
		    		CloudPushData pushToAllMsg = new CloudPushData();
		    		pushToAllMsg.setPlanId(plan.getCloudPushPlanId());
		    		pushToAllMsg.setAppId(plan.getAppId());
		    		pushToAllMsg.setCustId(notification.getCustId());
		    		pushToAllMsg.setDeviceNo(notification.getDeviceNo());
		    		pushToAllMsg.setCreationDate(DateUtil.getOnlyDay(new Date()));
		    		pushToAllMsg.setPlanParam(unsigned);
		    		dataListToSave.add(pushToAllMsg);
				}
				
				if(dataListToSave.size()>0){
		    		cloudPushDataService.save(dataListToSave);
				}
			
			}catch(Exception e){
				log.error("Exception occured:", e);
			}
			init();
		}

		private void init(){
			status = DDThreadStatus.FREE;
		}
	
		public DDThreadStatus getStatus() {
			return status;
		}
	}
	
	public static boolean isBelowVersion(Notification notification,
			CloudPushPlan plan) {
		
		String clientVersion = notification.getAppClientVersion();
		String versionAbove = "";
		
		if(notification.getDeviceType()!=null && notification.getDeviceType().equals(3)){
			versionAbove = plan.getVersionAbove();
		}else if ( notification.getDeviceType()!=null && notification.getDeviceType().equals(4) ){
			versionAbove = plan.getIosVersionAbove();
		}else{
			//如果记录的devicetype既不是安卓也不是ios，那么不推送
			return true;
		}
		//如果versionAbove是空，那么推送
		if(versionAbove == null || versionAbove!=null && StringUtils.isEmpty(versionAbove.trim())){
			return false;
		}
		
		//如果versionAbove非空而且clientVersion是空的，那么不推送
		if(StringUtils.isEmpty(clientVersion)){
			return true;
		}
		
		String[] clientVersionFragArray = dotPattern.split(clientVersion);
		String[] versionAboveFragArray = dotPattern.split(versionAbove);
		
		boolean isbelow = false;
		int maxCompareLength = versionAboveFragArray.length;
		
		for(int i =0; i<maxCompareLength; i++){
			
			String clientVersionFrag = "";
			if(i <= clientVersionFragArray.length-1 ){
				clientVersionFrag = clientVersionFragArray[i];
			} else {
				clientVersionFrag = "0";
			}
			
			if(SafeConvert.convertStringToInteger(clientVersionFrag, 0) <
					SafeConvert.convertStringToInteger(versionAboveFragArray[i], 0)){
				isbelow = true;
				break;
			}
		}
		return isbelow;
	}

	private void initThreadPool(int size) {
		int threadPoolSize = 1;
		threadPoolSize =getGroup(size, pageSize);
		if(threadPoolSize>THREAD_POOL_SIZE){
			threadPoolSize = THREAD_POOL_SIZE;
		}
		dataPrepareThreadPool = new ThreadPoolExecutor(threadPoolSize, threadPoolSize,
				THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
				new CallerRunsPolicy());
	}
	
	private int getGroup(int count, int page) {
		int group = 0;
		if(count%page == 0){
			group = count/page; 
		}else{
			group = count/page +1;
		}
		return group;
	}
}
