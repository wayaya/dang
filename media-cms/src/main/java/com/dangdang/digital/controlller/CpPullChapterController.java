package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.digital.model.CpPullChapter;
import com.dangdang.digital.model.CpPullMedia;
import com.dangdang.digital.service.ICpPullChapterService;
import com.dangdang.digital.service.ICpPullMediaService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


@Controller
@RequestMapping("pullChapter")
public class CpPullChapterController extends BaseController {

private static final Logger LOGGER = LoggerFactory.getLogger(CpPullMediaController.class);
	

	@Resource 
	private ICpPullChapterService cpPullChapterService;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final CpPullChapter pullChapter){
		
		PageFinder<CpPullChapter> pageFinder = cpPullChapterService.findPageFinderObjs(pullChapter,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("log", pullChapter);
		model.addAttribute("cpMediaId", pullChapter.getCpMediaId());
		return "pullChapter/list";
	}
	@RequestMapping(value="/content")
	@ResponseBody
	public String getContent(CpPullChapter pullChapter){
		pullChapter = cpPullChapterService.get(pullChapter.getId());
		return pullChapter.getContent();
	}
}
