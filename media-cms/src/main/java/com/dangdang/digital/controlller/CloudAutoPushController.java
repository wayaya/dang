package com.dangdang.digital.controlller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.CloudPushCondition;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.model.CloudPushPlanStatus;
import com.dangdang.digital.model.PushConditionParam;
import com.dangdang.digital.model.PushConditionParamsDefinition;
import com.dangdang.digital.model.PushConditionVariable;
import com.dangdang.digital.model.PushStrategy;
import com.dangdang.digital.model.PushStrategyParam;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.service.ICloudPushConditionService;
import com.dangdang.digital.service.ICloudPushDataService;
import com.dangdang.digital.service.ICloudPushPlanService;
import com.dangdang.digital.service.ICloudPushPlanStatusService;
import com.dangdang.digital.service.ICloudPushStatisticService;
import com.dangdang.digital.service.IPushConditionParamService;
import com.dangdang.digital.service.IPushConditionParamsDefinitionService;
import com.dangdang.digital.service.IPushConditionVariableService;
import com.dangdang.digital.service.IPushStrategyParamService;
import com.dangdang.digital.service.IPushStrategyService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.PushUtils;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;

@Controller
@Scope("prototype")
@RequestMapping("autoPush")
public class CloudAutoPushController extends BaseController{
	Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	
	@Resource
	ICloudPushPlanService cloudPushPlanService;
	@Resource 
	ICloudPushConditionService cloudPushConditionService;
	@Resource 
	ICloudPushStatisticService cloudPushStatisticService;
	@Resource
	IPushConditionParamsDefinitionService pushConditionParamsDefinitionService;
	@Resource
	IPushConditionParamService pushConditionParamService;
	@Resource
	IPushConditionVariableService pushConditionVariableService;
	@Resource
	ICloudPushDataService cloudPushDataService;
	@Resource
	ICloudPushPlanStatusService cloudPushPlanStatusService;
	@Resource
	IPushStrategyService pushStrategyService;
	@Resource 
	IPushStrategyParamService pushStrategyParamService;
	
	/** 
	 * 执行搜索 .
	 **/
	@RequestMapping("list")
	public String list(Model model, String page, CloudPushPlan cloudPushPlan, Integer terminal, String keyword) {
		
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotEmpty(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		
		if(cloudPushPlan == null){
			cloudPushPlan = new CloudPushPlan();
		}
		
		PageFinder<CloudPushPlan> pageFinder = null;
		
		cloudPushPlan.setPlanType(2);
		if(StringUtils.isNotEmpty(keyword)){
			cloudPushPlan.setMessageDescription(keyword);
		}
		
		if(terminal!=null){
			if(terminal==1){
				cloudPushPlan.setDeviceTypeAndroid(true);
			}
			if(terminal==2){
				cloudPushPlan.setDeviceTypeIos(true);
			}
		}
		
		
		pageFinder = cloudPushPlanService.findMasterPageFinderObjs(cloudPushPlan, query);
		
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		if(terminal != null){
			model.addAttribute("terminal", terminal);
		}
		if(keyword != null){
			model.addAttribute("keyword", keyword);
		}
		
		put("autoPushList", pageFinder.getData());
		return "autoPush/list";
	}
	
	@RequestMapping("add")
	public String add(CloudPushPlan plan) {
		
		if(plan!=null && !plan.equals(new CloudPushPlan())){
			
			if(StringUtils.isNotEmpty(plan.getCustomContent()) && !"{}".equals(plan.getCustomContent())){
				
				LinkedHashMap<String, String> map = JSON.parseObject(plan.getCustomContent(), LinkedHashMap.class);
				put("customContentMap", map);
			}
			
			put("autoPlan", plan);
		}
		return "autoPush/add";
	}
	
	@RequestMapping("addSubmit")
	public String addSubmit(Model model, CloudPushPlan cloudPushPlan) throws Exception{
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		
		Map<String, String> customContentMap = new LinkedHashMap<String,String>();
		Map<String, String> customContentIOSMap = new LinkedHashMap<String,String>();
		HttpServletRequest request = AppUtil.getRequest();
		//若是传递参数
		if(cloudPushPlan.getOpenType()==0){
			getContentMap(customContentMap, customContentIOSMap, request);
		}else{
			cloudPushPlan.setPushStrategyId(null);
		}
		
		if(cloudPushPlan.getDeviceTypeAndroid()==null){
			cloudPushPlan.setDeviceTypeAndroid(false);
		}
		if(cloudPushPlan.getDeviceTypeIos()==null){
			cloudPushPlan.setDeviceTypeIos(false);
		}
		
		//跳到个人中心
		if(cloudPushPlan.getAppId()==2 && cloudPushPlan.getPushStrategyId()!=null && cloudPushPlan.getPushStrategyId().equals(11L)){
			
			if(cloudPushPlan.getDeviceTypeAndroid()!=null && cloudPushPlan.getDeviceTypeAndroid().booleanValue()){
				String custId = customContentMap.get("custId");
				Long custIdLong = SafeConvert.convertStringToLong(custId, -1L);
				if(custIdLong!=-1){
					String encryptCustId = DesUtils.encryptCustId(custIdLong);
					customContentMap.put("custId", encryptCustId);
				}
			}
			
			if(cloudPushPlan.getDeviceTypeIos()!=null && cloudPushPlan.getDeviceTypeIos().booleanValue()){
				String custId = customContentIOSMap.get("custId");
				Long custIdLong = SafeConvert.convertStringToLong(custId, -1L);
				if(custIdLong!=-1){
					String encryptCustId = DesUtils.encryptCustId(custIdLong);
					customContentIOSMap.put("custId", encryptCustId);
				}
			}
			
		}
		
		cloudPushPlan.setCustomContent(JSON.toJSONString(customContentMap));
		cloudPushPlan.setCustomContentIos(JSON.toJSONString(customContentIOSMap)); 
		
		cloudPushPlan.setStartDate(DateUtil.parseStringToDate(request.getParameter("startDateStr")));
		cloudPushPlan.setEndDate(DateUtil.parseStringToDate(request.getParameter("endDateStr")));
		
		cloudPushPlan.setCreator(currentUser.getLoginName());
		cloudPushPlan.setCreationDate(new Date());
		
		cloudPushPlan.setSendTime(PushUtils.getSendTimeOfAutoPush(cloudPushPlan));
		
		cloudPushPlanService.save(cloudPushPlan);
		
		
		List<PushConditionParam> pushConditionParamList = getPushConditionParams(cloudPushPlan, request);
		if(pushConditionParamList!=null && pushConditionParamList.size()>0){
			pushConditionParamService.save(pushConditionParamList);
		}
		
		if(cloudPushPlan.getOpenType()==0){
			customContentMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
			cloudPushPlan.setCustomContent(JSON.toJSONString(customContentMap));
			
			customContentIOSMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
			cloudPushPlan.setCustomContentIos(JSON.toJSONString(customContentIOSMap));
			cloudPushPlanService.update(cloudPushPlan);
		}
		return list(model, "1", null, null, null);
	}
	
	private List<PushConditionParam> getPushConditionParams(
			CloudPushPlan cloudPushPlan, HttpServletRequest request) {
		
		List<PushConditionParam> list = new ArrayList<PushConditionParam>();
		
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();
			if(paraName.startsWith("conditionParam_")){
				
				String paramDefinitionIdStr = paraName.substring("conditionParam_".length(), paraName.length()).trim();
				Long paramDefinitionId = SafeConvert.convertStringToLong(paramDefinitionIdStr, 0L);
				String value = request.getParameter(paraName);
				if(paramDefinitionId!=0 && StringUtils.isNotEmpty(value)){
					
					PushConditionParamsDefinition conditionParamDef = pushConditionParamsDefinitionService.getByIdAndToCache(paramDefinitionId);
					
					if(conditionParamDef!=null){
						PushConditionParam conditionParam = new PushConditionParam();
						conditionParam.setConditionId(conditionParamDef.getConditionId());
						conditionParam.setKeyInputName(conditionParamDef.getKeyInputName());
						conditionParam.setKeyName(conditionParamDef.getKeyName());
						conditionParam.setKeyValue(value);
						conditionParam.setPushPlanId(cloudPushPlan.getCloudPushPlanId());
						list.add(conditionParam);
					}
				}
			}
		}
		return list;
	}
	
	@RequestMapping("/getPlanStatus")
	@ResponseBody
	public String getPlanStatus(Long planId) {
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			if(planId==null || planId<=0){
				map.put("result", "failure");
				map.put("errorMessage", "planId can not be null");
				return JSON.toJSONString(map);
			}
			CloudPushPlanStatus cloudPushPlanStatus = new CloudPushPlanStatus();
			cloudPushPlanStatus.setCreationDate(DateUtil.getOnlyDay(new Date()));
			cloudPushPlanStatus.setPlanId(planId);
			List<CloudPushPlanStatus> statusList = cloudPushPlanStatusService.findListByParamsObjs(cloudPushPlanStatus);
			if(statusList!=null && statusList.size()>0){
				CloudPushPlanStatus status = statusList.get(0);
				map.put("result", "success");
				map.put("planStatus", status);
			}else{
				map.put("result", "success");
				map.put("planStatus", "ok");
			}

		}catch(Exception e){
			
			map.put("result", "failure");
			map.put("errorMessage", "System exception");
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping("/importUserIds")
	@ResponseBody
	public String importUserIds(@RequestParam MultipartFile uploadCsv) {
		Map<String,String> map=new HashMap<String,String>();
		try{
			InputStream is = uploadCsv.getInputStream();
			BufferedReader readCsv = new BufferedReader(new InputStreamReader(is));
			String inStr = "";
			
			StringBuffer content = new StringBuffer();
			while ((inStr = readCsv.readLine())!=null) {  
			    
			    String str[] = inStr.trim().split(",");
			    for (int i = 0; i<str.length; i++){
			    	
			    	if(StringUtils.isNotEmpty(str[i])){
			    	 content.append(str[i]);
			    	 content.append(",");
			    	}
			    }
			}
			
			if(content.length()>0){
				content=new StringBuffer(content.substring(0, content.length()-1));
			}
			
			map.put("result", "success");
			map.put("userIds", content.toString());
		}catch(Exception e){
			
			map.put("result", "failure");
			map.put("errorMessage", "System exception");
		}
		System.out.println(JSON.toJSONString(map));
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("edit")
	public String edit(Model model, CloudPushPlan plan) {
		
		if(plan.getCloudPushPlanId()!=null){
			
			plan = cloudPushPlanService.findMasterById(plan.getCloudPushPlanId());
			
			if(plan!=null){
				
				if(StringUtils.isNotEmpty(plan.getCustomContent()) && !"{}".equals(plan.getCustomContent())){
					LinkedHashMap<String, String> map = JSON.parseObject(plan.getCustomContent(), LinkedHashMap.class);
					//跳到个人中心
					if(plan.getAppId()==2 && plan.getPushStrategyId()!=null && plan.getPushStrategyId().equals(11L)){
						String custId = map.get("custId");
						Long custIdLong = -1L;
						try {
							custIdLong = DesUtils.decryptCustId(custId);
						} catch (Exception e) {
							e.printStackTrace();
						}
						map.put("custId", custIdLong+"");
					}
					put("customContentMap", map);
				}
				
				if(StringUtils.isNotEmpty(plan.getCustomContentIos()) && !"{}".equals(plan.getCustomContentIos())){
					LinkedHashMap<String, String> map = JSON.parseObject(plan.getCustomContentIos(), LinkedHashMap.class);
					//跳到个人中心
					if(plan.getAppId()==2 && plan.getPushStrategyId()!=null && plan.getPushStrategyId().equals(11L)){
						String custId = map.get("custId");
						Long custIdLong = -1L;
						try {
							custIdLong = DesUtils.decryptCustId(custId);
						} catch (Exception e) {
							e.printStackTrace();
						}
						map.put("custId", custIdLong+"");
					}
					put("customContentIOSMap", map);
				}
				
				put("autoPlan", plan);
				
				PushConditionParam param = new PushConditionParam();
				param.setPushPlanId(plan.getCloudPushPlanId());
				param.setConditionId(plan.getPlanCondition());
				
				List<PushConditionParam> paramList = pushConditionParamService.findListByParamsObjs(param);
				if(paramList!=null && paramList.size()>0){
					put("paramList", paramList);
				}
				
			}else{
				put("cms_error_message","对不起，您想要编辑的推送任务已经不存在");
				return list(model, "1", null, null, null); 
			}
		}else{
			put("cms_error_message","对不起，您想要编辑的推送任务已经不存在");
			return list(model, "1", null, null, null); 
		}
		return "autoPush/edit";
	}
	
	@RequestMapping("editSubmit")
	public String editSubmit(Model model, CloudPushPlan cloudPushPlan, Integer editMode) throws Exception{
		
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		
		Map<String, String> customContentMap = new LinkedHashMap<String,String>();
		Map<String, String> customContentIOSMap = new LinkedHashMap<String,String>();
		HttpServletRequest request = AppUtil.getRequest();
		
		//若是传递参数
		if(cloudPushPlan.getOpenType()==0){
			getContentMap(customContentMap, customContentIOSMap, request);
		}else{
			cloudPushPlan.setPushStrategyId(null);
		}
		
		if(cloudPushPlan.getDeviceTypeAndroid()==null){
			cloudPushPlan.setDeviceTypeAndroid(false);
		}
		if(cloudPushPlan.getDeviceTypeIos()==null){
			cloudPushPlan.setDeviceTypeIos(false);
		}
		
		
		//跳到个人中心
		if(cloudPushPlan.getAppId()==2 && cloudPushPlan.getPushStrategyId()!=null && cloudPushPlan.getPushStrategyId().equals(11L)){
			
			if(cloudPushPlan.getDeviceTypeAndroid()!=null && cloudPushPlan.getDeviceTypeAndroid().booleanValue()){
				String custId = customContentMap.get("custId");
				Long custIdLong = SafeConvert.convertStringToLong(custId, -1L);
				if(custIdLong!=-1){
					String encryptCustId = DesUtils.encryptCustId(custIdLong);
					customContentMap.put("custId", encryptCustId);
				}
			}
			
			if(cloudPushPlan.getDeviceTypeIos()!=null && cloudPushPlan.getDeviceTypeIos().booleanValue()){
				String custId = customContentIOSMap.get("custId");
				Long custIdLong = SafeConvert.convertStringToLong(custId, -1L);
				if(custIdLong!=-1){
					String encryptCustId = DesUtils.encryptCustId(custIdLong);
					customContentIOSMap.put("custId", encryptCustId);
				}
			}
		}
		
		
		customContentMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
		cloudPushPlan.setCustomContent(JSON.toJSONString(customContentMap));
		
		customContentIOSMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
		cloudPushPlan.setCustomContentIos(JSON.toJSONString(customContentIOSMap));
		
		
		cloudPushPlan.setStartDate(DateUtil.parseStringToDate(request.getParameter("startDateStr")));
		cloudPushPlan.setEndDate(DateUtil.parseStringToDate(request.getParameter("endDateStr")));
		
		cloudPushPlan.setModifier(currentUser.getLoginName());
		cloudPushPlan.setLastChangedDate(new Date());
		
		//更改参数，删除原来条件下关联的所有的参数值，然后再插入新的。

		PushConditionParam param = new PushConditionParam();
		param.setPushPlanId(cloudPushPlan.getCloudPushPlanId());
		pushConditionParamService.deleteByParamsObjs(param);
		
		List<PushConditionParam> pushConditionParamList = getPushConditionParams(cloudPushPlan, request);
		if(pushConditionParamList!=null && pushConditionParamList.size()>0){
			pushConditionParamService.save(pushConditionParamList);
		}
		
		cloudPushPlan.setSendTime(PushUtils.getSendTimeOfAutoPush(cloudPushPlan));
		
		//仅仅update plan， 下个发送周期生效
		if(editMode == null || (editMode!=null && editMode==1)){
			cloudPushPlanService.update(cloudPushPlan);
		}else{
			
			try {
				cloudPushPlanService.updatePlan(cloudPushPlan, editMode);
			} catch (MediaException e) {
				put("errorMessage", e.getErrorCode()+"|"+e.getErrorMsg());
				return edit(model, cloudPushPlan);
			}
		}
		
		return list(model, "1", null, null, null);
	}

	private void getContentMap(Map<String, String> customContentMap, Map<String, String> customContentIOSMap,
			HttpServletRequest request) {
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();
			if(paraName.startsWith("dd_push_key")){
				
				String key = request.getParameter(paraName);
				String valueParam = paraName.replaceAll("dd_push_key", "dd_push_value");
				String value = request.getParameter(valueParam);
				
				if(StringUtils.isNotEmpty(key)&&StringUtils.isNotEmpty(value)){
					customContentMap.put(key, value);
				}
			}
			
			if(paraName.startsWith("dd_push_ios_key")){
				
				String key = request.getParameter(paraName);
				String valueParam = paraName.replaceAll("dd_push_ios_key", "dd_push_ios_value");
				String value = request.getParameter(valueParam);
				
				if(StringUtils.isNotEmpty(key)&&StringUtils.isNotEmpty(value)){
					customContentIOSMap.put(key, value);
				}
			}
			
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(final Long[] ids) {
		AjaxResult result = new AjaxResult();
		
		try {
			
			List<Long> idsToDelete = new ArrayList<Long>();
			for(Long id:ids){
				idsToDelete.add(id);
			}
			
			cloudPushPlanService.deleteByIds(idsToDelete);
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
		} catch (Exception e) {
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
			result.setErrorMessage("删除失败，系统错误");
		}
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping("setPlanStatus")
	@ResponseBody
	public String setPlanStatus(final String ids, Integer planStatus) {
		
		String[] idsArray = Constans.commaSpliter.split(ids);
		
		AjaxResult result = new AjaxResult();
		try {
			
			List<Long> idsToSet = new ArrayList<Long>();
			for(String id:idsArray){
				idsToSet.add(SafeConvert.convertStringToLong(id, 0));
			}
			
			cloudPushPlanService.updatePushPlanStatus(idsToSet, planStatus);
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
		} catch (Exception e) {
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
			result.setErrorMessage("设置失败，系统错误");
		}
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping("getConditions")
	@ResponseBody
	public String getConditionsByAppId(Integer appId){
		
		
		List<CloudPushCondition> list = null;
		if(appId!=null){
			CloudPushCondition params = new CloudPushCondition();
			params.setAppId(appId);
			list = cloudPushConditionService.findListByParamsObjs(params);
		}
		if(list==null){
			list = new ArrayList<CloudPushCondition>();
		}
		
		return JSON.toJSONString(list);
	}
	
	
	@RequestMapping("getStrategies")
	@ResponseBody
	public String getStrategiesByAppId(Integer appId){
		
		List<PushStrategy> list = null;
		if(appId!=null){
			
			PushStrategy param = new PushStrategy();
			param.setAppId(appId);
			list = pushStrategyService.findListByParamsObjs(param);
		}
		if(list==null){
			list = new ArrayList<PushStrategy>();
		}
		
		return JSON.toJSONString(list);
	}
	
	@RequestMapping("getParamsByStrategy")
	@ResponseBody
	public String getParamsByStrategy(Long strategyId){
		
		List<PushStrategyParam> list = null;
		if(strategyId!=null){
			PushStrategyParam param = new PushStrategyParam();
			param.setStrategyId(strategyId);
			
			list = pushStrategyParamService.findListByParamsObjs(param);
		}
		if(list==null){
			list = new ArrayList<PushStrategyParam>();
		}
		return JSON.toJSONString(list);
	}
	
	
	@RequestMapping("getParamsDefByCondition")
	@ResponseBody
	public String getParamsDefinitionByCondition(Long conditionId){
		
		List<PushConditionParamsDefinition> list = null;
		if(conditionId!=null){
			PushConditionParamsDefinition params = new PushConditionParamsDefinition();
			params.setConditionId(conditionId);
			list = pushConditionParamsDefinitionService.findListByParamsObjs(params);
		}
		if(list==null){
			list = new ArrayList<PushConditionParamsDefinition>();
		}
		return JSON.toJSONString(list);
	}
	
	@RequestMapping("getVariablesByCondition")
	@ResponseBody
	public String getVariablesByCondition(Long conditionId){
		
		List<PushConditionVariable> list = null;
		if(conditionId!=null){
			PushConditionVariable params = new PushConditionVariable();
			params.setConditionId(conditionId);
			list = pushConditionVariableService.findListByParamsObjs(params);
		}
		if(list==null){
			list = new ArrayList<PushConditionVariable>();
		}
		return JSON.toJSONString(list);
	}
	
}
