package com.dangdang.digital.controlller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	@RequestMapping(value = "error500")
	public static String error500(Model model,Exception e){
		model.addAttribute("errorInfo", e.getMessage());
		return "order/error500";
	}
	
	@RequestMapping(value = "error404")
	public String error400(ModelMap model){
		model.addAttribute("errorInfo", "找不到该页面");
		return "order/error404";
	}
}
