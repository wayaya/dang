package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.service.IActivityRecordService;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

/**
 * Description: 活动用户 controller
 * All Rights Reserved.
 * @version 1.0  2014年11月26日 上午10:00:47  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Controller
@RequestMapping("activityUser")
public class ActivityUserController extends BaseController {
	
	@Resource IActivityUserService activityService;
	@Resource IActivityRecordService activityRecordService; 
	
	@RequestMapping("list")
	public String list(Model model, String page,ActivityUser activityUser){
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		
		PageFinder<ActivityUser> pageFinder = activityService.findPageFinderObjs(activityUser, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("one", activityUser);
		return "user/activity/list";
	}
	
	
	@RequestMapping("record")
	public String record(Model model, String page,ActivityRecord activityRecord){
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		
		PageFinder<ActivityRecord> pageFinder = activityRecordService.findPageFinderObjs(activityRecord, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("prize", activityRecord);
		return "user/activity/record";
	}
}