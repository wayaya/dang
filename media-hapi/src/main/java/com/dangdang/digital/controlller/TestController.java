package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.model.Test;
import com.dangdang.digital.service.ITestService;

@Controller
@RequestMapping("test")
public class TestController {
	@Resource ITestService testService;
	
	@RequestMapping("test")
	public String test(Model model){
		Test test = testService.getTestById(1);
		if(test != null){
			model.addAttribute("name", test.getName());
		}
		return "test";
	}
}