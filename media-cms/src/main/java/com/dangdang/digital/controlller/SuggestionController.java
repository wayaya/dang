package com.dangdang.digital.controlller;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import org.springframework.ui.Model;

import com.dangdang.digital.controlller.BaseController;
import com.dangdang.digital.model.Suggestion;
import com.dangdang.digital.service.ISuggestionService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 
 * Description: app 意见反馈
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 下午7:36:57  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Controller
@RequestMapping("suggestion")
public class SuggestionController extends BaseController{
	@Resource ISuggestionService suggestionService;
	
	@RequestMapping(value="/list")
	public String getLabel(String pageIndex,final Model model,final Suggestion suggestion){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<Suggestion> pageFinder = suggestionService.findPageFinderObjs(suggestion,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "suggestion/list";
	}
	
	@RequestMapping(value="/delete")
	public String delete(@RequestParam final long id){
		this.suggestionService.deleteById(id);
		return "redirect:list.go";
	}
	
	}

