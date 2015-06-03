package com.dangdang.digital.controlller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class IndexController {
	
	@RequestMapping("login")
	public String login(Model model){
		return "main";
	}
	
	@RequestMapping("welcome")
	public String welcome(Model model){
		return "welcome";
	}
}