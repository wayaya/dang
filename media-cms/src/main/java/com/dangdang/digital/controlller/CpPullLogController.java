package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.model.CpPulllog;
import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.ICpPulllogService;
import com.dangdang.digital.service.IStyleService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


@Controller
@RequestMapping("pullLog")
public class CpPullLogController extends BaseController {



	@Resource 
	private ICpPulllogService cpPullLogService;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final CpPulllog log){
		
		PageFinder<CpPulllog> pageFinder = cpPullLogService.findPageFinderObjs(log,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("log", log);
		return "cpPullLog/list";
	}
	

}
