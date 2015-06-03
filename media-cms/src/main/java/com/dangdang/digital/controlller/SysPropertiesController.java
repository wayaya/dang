package com.dangdang.digital.controlller;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.model.SysProperties;
import com.dangdang.digital.service.ISysPropertiesService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
/**
 * 
 * Description: 
 * All Rights Reserved.系统参数控制器
 * @version 1.0  2014年11月24日 下午4:10:22  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Controller
@RequestMapping("syspp")
public class SysPropertiesController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource ISysPropertiesService service;
	@Resource
	private ICacheApi  cacheApi;
	
	@RequestMapping(value="/checkcode")
	@ResponseBody
	public String checkColumnCode(String keyName){
		SysProperties sysPp=	service.findUniqueByParams("keyName",keyName);
		if(null !=sysPp){
			return "{\"result\":\"failure\"}";
		}else{
			//编号已存在
			return "{\"result\":\"success\"}";
		}
	}
	
	
	@RequestMapping(value="/list")
	public String getList(String pageIndex,final Model model,final SysProperties sysProperties){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<SysProperties> pageFinder = service.findPageFinderObjs(sysProperties,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("sysProperties", sysProperties);
		return "syspp/list";
	}
	
	@RequestMapping(value="/delete")
	public String delete(@RequestParam final int id,final String keyName){
		this.service.deleteById(id);
		try {
			cacheApi.cleanSystemPropertyCache(keyName);
		} catch (Exception e) {
			LogUtil.error(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 清除 系统参数,key=" + keyName ,e);
			return "{\"result\":\"failure\"}";
		}
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 清除 系统参数并更新缓存,key=" + keyName);
		return "redirect:list.go";
	}
	@RequestMapping(value="/edit")
	public String edit(@RequestParam final int id,final Model model){
		SysProperties sysProperties = this.service.get(id);
		model.addAttribute("SysProperties", sysProperties);
		return "syspp/edit";
	}
	@RequestMapping(value="/add")
	public String addLabel(final Model model){
		return "syspp/add";
	}
	
	@RequestMapping(value="/save")
	public String save(final SysProperties sysProperties){
		service.save(sysProperties);
		return "redirect:list.go";
		
	}
	
	@RequestMapping(value="/update")
	public String update(final SysProperties sysProperties){
		service.update(sysProperties);
		return "redirect:list.go";
		
	}
	
	
}