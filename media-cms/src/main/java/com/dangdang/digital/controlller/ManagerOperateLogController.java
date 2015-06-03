/**
 * Description: ManagerOperateLogController.java
 * All Rights Reserved.
 * @version 1.0  2014年11月15日 下午4:25:50  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.controlller;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.constant.OperateTargetTypeEnum;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.service.IManagerOperateLogService;
import com.dangdang.digital.utils.Query;

/**
 * Description: 后台用户操作日志controller
 * All Rights Reserved.
 * @version 1.0  2014年11月15日 下午4:25:50  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Controller
@RequestMapping("managerOperateLog")
public class ManagerOperateLogController extends BaseController {
	@Resource
	private IManagerOperateLogService managerOperateLogService;
	
	@RequestMapping("list")
	public String list(Model model,Query query,ManagerOperateLog managerOperateLog){
		model.addAttribute("pageFinder", managerOperateLogService.findPageFinderObjs(managerOperateLog, query));
		model.addAttribute("operateTargetTypeArray", Arrays.asList(OperateTargetTypeEnum.values()));
		model.addAttribute("vo",managerOperateLog);
		return "managerOperateLog/list";
	}
	
	@RequestMapping("detail")
	public String detail(Model model, Long managerOperateLogId) {
		String errorMsg = "";
		if (managerOperateLogId != null) {
			ManagerOperateLog managerOperateLog = managerOperateLogService
					.get(managerOperateLogId);
			if (managerOperateLog == null) {
				errorMsg += "[操作日志信息不存在！]";
			}else{
				model.addAttribute("managerOperateLog", managerOperateLog);
			}			
		} else {
			errorMsg += "[操作日志id为null!]";
		}
		if (StringUtils.isNotBlank(errorMsg)) {
			model.addAttribute("errorMsg", errorMsg);
		}
		model.addAttribute("operateTargetTypeArray", Arrays.asList(OperateTargetTypeEnum.values()));
		return "managerOperateLog/detail";
	}

}
