package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.IStyleService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Controller
@RequestMapping("style")
public class StyleController extends BaseController{

	@Resource 
	private IStyleService styleService;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final Style style){
		
		PageFinder<Style> pageFinder = styleService.findPageFinderObjs(style,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("style", style);
		return "style/list";
	}
	
	@RequestMapping(value="/delete")
	public String del(@RequestParam final int id){
		this.styleService.deleteById(id);
		return "redirect:list.go";
	}
	@RequestMapping(value="/toEdit")
	public String toEdit(@RequestParam final int id,final Model model){
		Style style = this.styleService.get(id);
		model.addAttribute("style", style  );
		return "style/modify";
	}
	@RequestMapping(value="/toAdd")
	public String toAdd(Style style,final Model model){
		return "style/add";
	}
	
	@RequestMapping(value="/save")
	public String save(Style style){
		style.setCreationDate(java.util.Calendar.getInstance().getTime());
		styleService.save(style);
		return "redirect:list.go";
		
	}
	
	@RequestMapping(value="/update")
	public String update(Style label){
		styleService.update(label);
		return "redirect:list.go";
		
	}
}
