package com.dangdang.digital.controlller;



import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.IllWord;
import com.dangdang.digital.service.IIllWordService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Controller
@RequestMapping("illword")
public class IllWordController extends BaseController{
	@Resource
	IIllWordService illWordService;
	
	@Resource
	ICacheApi cacheApi;
	
	@RequestMapping(value="/list")
	public String getLabel(String pageIndex,final Model model,final IllWord illWord){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<IllWord> pageFinder = illWordService.findPageFinderObjs(illWord,query);
		model.addAttribute("pageFinder", pageFinder);
		return "illword/list";
	}
	
	@RequestMapping(value="/delete")
	public String deleteIllWord(@RequestParam final int id){
		this.illWordService.deleteById(id);
		cacheApi.cleanCacheByKey(Constans.ILL_WORD_CACHE_KEY);
		return "redirect:list.go";
	}
	@RequestMapping(value="/edit")
	public String editLabel(@RequestParam final int id,final Model model){
		IllWord illWord = this.illWordService.get(id);
		model.addAttribute("illWord", illWord);
		return "illword/edit";
	}
	@RequestMapping(value="/add")
	public String addIllWord(){
		return "illword/add";
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public String saveIllword(IllWord illWord){
		IllWord temp = illWordService.findUniqueByParams("type",illWord.getType(),"words",illWord.getWords());
		if(null ==temp){
			this.illWordService.save(illWord);
			cacheApi.cleanCacheByKey(Constans.ILL_WORD_CACHE_KEY);
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"failure\"}";
		}
	}
	
	@RequestMapping(value="/update")
	public String updateIllWord(IllWord illWord){
		illWordService.update(illWord);
		cacheApi.cleanCacheByKey(Constans.ILL_WORD_CACHE_KEY);
		return "redirect:list.go";
		
	}
	
	
}