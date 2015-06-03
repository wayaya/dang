package com.dangdang.digital.controlller;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.digital.model.Label;
import com.dangdang.digital.service.ILabelService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Controller
@RequestMapping("label")
public class LabelController extends BaseController{
	@Resource ILabelService labelService;
	
	
	
	@RequestMapping(value="/list")
	public String getLabel(String pageIndex,final Model model,final Label label){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<Label> pageFinder = labelService.findPageFinderObjs(label,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "label/list";
	}
	
	@RequestMapping(value="/delete")
	public String deleteLabel(@RequestParam final int id){
		this.labelService.deleteById(id);
		return "redirect:list.go";
	}
	@RequestMapping(value="/edit")
	public String editLabel(@RequestParam final int id,final Model model){
		Label label = this.labelService.get(id);
		model.addAttribute("label", label);
		return "label/editabel";
	}
	@RequestMapping(value="/add")
	public String addLabel(final Model model){
		return "label/addlabel";
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public String saveLabel(Label label){
		Label temp = labelService.findUniqueByParams("type",label.getType(),"name",label.getName());
		if(null == temp){
			labelService.save(label);
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"failure\"}";
		}
		
	}
	
	@RequestMapping(value="/update")
	public String updateLabel(Label label){
		labelService.update(label);
		return "redirect:list.go";
		
	}
	
	
}