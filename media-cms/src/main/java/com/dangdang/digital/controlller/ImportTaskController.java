package com.dangdang.digital.controlller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.model.ImportTask;
import com.dangdang.digital.service.IImportTaskService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


@Controller
@RequestMapping(value="importTask")
public class ImportTaskController extends BaseController{

	@Resource(name="importTaskService")
	private IImportTaskService importTaskService;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final ImportTask task){
		PageFinder<ImportTask> pageFinder = importTaskService.findPageFinderObjs(task,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("task", task);
		return "importTask/list";
	}
	
	@RequestMapping(value="/delete")
	public String del(@RequestParam final Long id){
		this.importTaskService.deleteById(id);
		return "redirect:list.go";
	}
	
	
	@RequestMapping(value="/toEdit")
	public String toEdit(@RequestParam final Long id,final Model model){
		ImportTask task = this.importTaskService.get(id);
		model.addAttribute("task", task);
		return "importTask/modify";
	}
	
	
	@RequestMapping(value="/repeatImport")
	public String repeatImport(@RequestParam final String ids){
		List<Long> list = new ArrayList<Long>();
		String idArr[] = ids.split(",");
		for(String str : idArr){
			list.add(Long.valueOf(str));
		}
		this.importTaskService.repeatImport(list);
		return "redirect:list.go";
	}
	
	
	
	@RequestMapping(value="/update")
	public String update(ImportTask task){
		importTaskService.update(task);
		return "redirect:list.go";
	}
}
