package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.digital.model.CpPullMedia;
import com.dangdang.digital.model.CpPulllog;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.ICpPullMediaService;
import com.dangdang.digital.service.ICpPulllogService;
import com.dangdang.digital.service.IStyleService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
import com.ibm.icu.util.Calendar;


@Controller
@RequestMapping("pullMedia")
public class CpPullMediaController extends BaseController {


	private static final Logger LOGGER = LoggerFactory.getLogger(CpPullMediaController.class);
	

	@Resource 
	private ICpPullMediaService cpPullMediaService;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final CpPullMedia pullMedia){
		
		PageFinder<CpPullMedia> pageFinder = cpPullMediaService.findPageFinderObjs(pullMedia,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("pullMedia", pullMedia);
		return "pullMedia/list";
	}
	
	@RequestMapping(value="/update")
	public String update(CpPullMedia media){
		try {
			if(media.getId() != null && media.getStatus() != null){
				cpPullMediaService.update(media);
			}
			LogUtil.info(LOGGER,"管理员:{0}在修改CP图书【{1}】状态", UsercmsUtils.getCurrentUser().getLoginName(),
					media.getId());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在修改CP图书【{1}】状态失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					media.getId());
		}
		return "redirect:list.go";
	}
	
	@RequestMapping(value="/content")
	@ResponseBody
	public String getContent(CpPullMedia pullMedia){
		pullMedia = cpPullMediaService.get(pullMedia.getId());
		return pullMedia.getContent();
	}

}
