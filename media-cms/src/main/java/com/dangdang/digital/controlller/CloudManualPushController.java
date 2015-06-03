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
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.service.ICloudPushConditionService;
import com.dangdang.digital.service.ICloudPushPlanService;
import com.dangdang.digital.service.ICloudPushStatisticService;
import com.dangdang.digital.service.IPushConditionParamService;
import com.dangdang.digital.service.IPushConditionParamsDefinitionService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;


@Controller
@Scope("prototype")
@RequestMapping("manualPush")
public class CloudManualPushController extends BaseController{
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
		
		cloudPushPlan.setPlanType(1);
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
		
		put("manualPushList", pageFinder.getData());
		return "manualPush/list";
	}
	
	@RequestMapping("add")
	public String add(CloudPushPlan plan) {
		
		if(plan!=null && !plan.equals(new CloudPushPlan())){
			
			if(StringUtils.isNotEmpty(plan.getCustomContent()) && !"{}".equals(plan.getCustomContent())){
				
				LinkedHashMap<String, String> map = JSON.parseObject(plan.getCustomContent(), LinkedHashMap.class);
				put("customContentMap", map);
			}
			
			if(StringUtils.isNotEmpty(plan.getCustomContentIos()) && !"{}".equals(plan.getCustomContentIos())){
				
				LinkedHashMap<String, String> map = JSON.parseObject(plan.getCustomContentIos(), LinkedHashMap.class);
				put("customContentIOSMap", map);
			}
			
			put("manualPlan", plan);
		}
		return "manualPush/add";
	}
	
	@RequestMapping("addSubmit")
	public String addSubmit(Model model, CloudPushPlan cloudPushPlan) throws Exception{
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		
		Map<String, String> customContentMap = new LinkedHashMap<String,String>();
		Map<String, String> customContentIOSMap = new LinkedHashMap<String,String>();
		HttpServletRequest request = AppUtil.getRequest();
		//若是传递参数
		if(cloudPushPlan.getOpenType()==0){
			getContentMap(customContentMap,customContentIOSMap, request);
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
		
		if(cloudPushPlan.getSendMode()==2){
			cloudPushPlan.setSendTime(DateUtil.parseStringToDate(request.getParameter("sendTimeStr")));
		}else if(cloudPushPlan.getSendMode()==1){
			cloudPushPlan.setSendTime(new Date());
		}
		
		cloudPushPlan.setCreator(currentUser.getLoginName());
		cloudPushPlan.setCreationDate(new Date());
		
		cloudPushPlanService.save(cloudPushPlan);
		
		if(cloudPushPlan.getOpenType()==0){
			customContentMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
			cloudPushPlan.setCustomContent(JSON.toJSONString(customContentMap));
			
			customContentIOSMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
			cloudPushPlan.setCustomContentIos(JSON.toJSONString(customContentIOSMap));
			cloudPushPlanService.update(cloudPushPlan);
		}
		return list(model, "1", null, null, null);
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
	public String edit(Model model, CloudPushPlan plan) throws Exception {
		
		if(plan.getCloudPushPlanId()!=null){
			
			plan = cloudPushPlanService.findMasterById(plan.getCloudPushPlanId());
			
			if(plan!=null){
				
				if(StringUtils.isNotEmpty(plan.getCustomContent()) && !"{}".equals(plan.getCustomContent())){
					LinkedHashMap<String, String> map = JSON.parseObject(plan.getCustomContent(), LinkedHashMap.class);
					
					//跳到个人中心
					if(plan.getAppId()==2 && plan.getPushStrategyId()!=null && plan.getPushStrategyId().equals(11L)){
						String custId = map.get("custId");
						Long custIdLong = DesUtils.decryptCustId(custId);
						map.put("custId", custIdLong+"");
					}
					
					put("customContentMap", map);
				}
				
				if(StringUtils.isNotEmpty(plan.getCustomContentIos()) && !"{}".equals(plan.getCustomContentIos())){
					LinkedHashMap<String, String> map = JSON.parseObject(plan.getCustomContentIos(), LinkedHashMap.class);
					//跳到个人中心
					if(plan.getAppId()==2 && plan.getPushStrategyId()!=null && plan.getPushStrategyId().equals(11L)){
						String custId = map.get("custId");
						Long custIdLong = DesUtils.decryptCustId(custId);
						map.put("custId", custIdLong+"");
					}
					put("customContentIOSMap", map);
				}
				
				put("manualPlan", plan);
			}else{
				put("cms_error_message","对不起，您想要编辑的推送任务已经不存在");
				return list(model, "1", null, null, null); 
			}
		}else{
			put("cms_error_message","对不起，您想要编辑的推送任务已经不存在");
			return list(model, "1", null, null, null); 
		}
		return "manualPush/edit";
	}
	
	@RequestMapping("copy")
	public String copy(Model model, CloudPushPlan manualPlan) {
		
		if(manualPlan.getCloudPushPlanId()!=null){
			
			manualPlan = cloudPushPlanService.findMasterById(manualPlan.getCloudPushPlanId());
			
			if(manualPlan!=null){
				put("manualPlan", manualPlan);
				return add(manualPlan);
			}else{
				put("cms_error_message","对不起，您想要复制的推送任务已经不存在");
				return list(model, "1", null, null, null); 
			}
		}else{
			put("cms_error_message","对不起，您想要复制的推送任务已经不存在");
			return list(model, "1", null, null, null); 
		}
		
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
		
		if(cloudPushPlan.getSendMode()==2){
			cloudPushPlan.setSendTime(DateUtil.parseStringToDate(request.getParameter("sendTimeStr")));
		}else if(cloudPushPlan.getSendMode()==1){
			cloudPushPlan.setSendTime(new Date());
		}
		
		cloudPushPlan.setModifier(currentUser.getLoginName());
		cloudPushPlan.setLastChangedDate(new Date());
		
		if(cloudPushPlan.getOpenType()==0){
			customContentMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
			cloudPushPlan.setCustomContent(JSON.toJSONString(customContentMap));
			
			customContentIOSMap.put("planId", cloudPushPlan.getCloudPushPlanId()+"");
			cloudPushPlan.setCustomContentIos(JSON.toJSONString(customContentIOSMap));
		}
		
		//仅仅update plan
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
	
	@RequestMapping("validateIosLength")
	@ResponseBody
	public String validateIosLength(Model model, CloudPushPlan cloudPushPlan) {
		AjaxResult result = new AjaxResult();
		
		try {
			
			Map<String, String> customContentMap = new LinkedHashMap<String,String>();
			Map<String, String> customContentIOSMap = new LinkedHashMap<String,String>();
			HttpServletRequest request = AppUtil.getRequest();
			//若是打开应用
			getContentMap(customContentMap,customContentIOSMap, request);
			
			//simulate planId
			customContentIOSMap.put("planId", "000000");
			
			
			Integer messageType = cloudPushPlan.getMessageType();
			Map<String, Object> pushMsg = new HashMap<String, Object>();
			
			if(messageType!=null && messageType==1){
				
				Map<String, Object> aps = new HashMap<String, Object>();
		    	aps.put("alert", cloudPushPlan.getMessageDescription());
		    	aps.put("badge", 1);
		    	pushMsg.put("aps", aps);

		    	if(customContentIOSMap!=null){
		    		pushMsg.putAll(customContentIOSMap);
		    	}
			}else if(messageType!=null && messageType==0){
				
				pushMsg.put("description", cloudPushPlan.getMessageDescription());
				if(customContentIOSMap!=null){
		    		pushMsg.putAll(customContentIOSMap);
		    	}
			}
			
			String messages = JSON.toJSONString(pushMsg);
			
			int length = messages.getBytes().length;
			if(length<=256){
				result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
			}else{
				result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
				result.setErrorMessage("验证失败，IOS消息过长，期待长度：256字节之内；实际长度："+length+"字节, 请简化推送消息内容或者IOS参数后重新提交");
			}
			
		} catch (Exception e) {
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
			result.setErrorMessage("验证失败，系统错误");
			LOGGER.error("validate exception:", e );
		}
		return JSON.toJSONString(result);
	}
	
	
	
}
