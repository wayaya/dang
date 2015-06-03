package com.dangdang.digital.controlller;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.model.ContentProvider;
import com.dangdang.digital.model.CpContract;
import com.dangdang.digital.service.IContentProviderService;
import com.dangdang.digital.service.ICpContractService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;


/**
 * ContentProvider Controller.
 */
@Controller
@RequestMapping("cp")
public class ContentProviderController extends BaseController{
	

	@Resource IContentProviderService contentProviderService;
	
	//合同服务
	@Resource ICpContractService cpContractServicer;
	
	
	
	
	@RequestMapping(value="/contract/list")
	public String getCpContract(String pageIndex,final Model model,final CpContract cpContract){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<CpContract> pageFinder = cpContractServicer.findPageFinderObjs(cpContract,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "cp/contract/list";
	}
	
	@RequestMapping(value="/contract/delete")
	public String deleteContract(@RequestParam final int contractId){
		this.cpContractServicer.deleteById(contractId);
		return "redirect:list.go";
	}
	
	@RequestMapping(value="/contract/edit")
	public String editContract(@RequestParam final int contractId,final Model model){
		CpContract cpContract = this.cpContractServicer.get(contractId);
		model.addAttribute("contract", cpContract);
		return "cp/contract/edit";
	}
	@RequestMapping(value="/contract/detail")
	public String detailContract(@RequestParam final int contractId,final Model model){
		CpContract cpContract = this.cpContractServicer.get(contractId);
		model.addAttribute("contract", cpContract);
		return "contract/detail";
	}
	@RequestMapping(value="/contract/add")
	public String addContract(final Model model){
		return "cp/contract/add";
	}
	
	@RequestMapping(value="/contract/save")
	public String saveContract(final CpContract cpContract){
		cpContract.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		cpContract.setCreationDate(DateUtil.format(new Date(),DateUtil.DATE_PATTERN));
		cpContractServicer.save(cpContract);
		return "redirect:list.go";
		
	}
	
	@RequestMapping(value="/contract/update")
	public String updateContract(final CpContract cpContract){
		cpContractServicer.update(cpContract);
		return "redirect:list.go";
	}
	
	
	@RequestMapping(value="/list")
	public String get(String pageIndex,final Model model,final ContentProvider contentProvider){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<ContentProvider> pageFinder = contentProviderService.findPageFinderObjs(contentProvider,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "cp/list";
	}
	
	@RequestMapping(value="/delete")
	public String delete(@RequestParam final int cpId){
		this.contentProviderService.deleteById(cpId);
		return "redirect:list.go";
	}
	
	@RequestMapping(value="/edit")
	public String edit(@RequestParam final int cpId,final Model model){
		ContentProvider contentProvider = this.contentProviderService.get(cpId);
		model.addAttribute("cp", contentProvider);
		return "cp/edit";
	}
	
	
	@RequestMapping(value="/detail")
	public String detail(@RequestParam final int cpId,final Model model){
		ContentProvider contentProvider = this.contentProviderService.get(cpId);
		model.addAttribute("cp", contentProvider);
		return "cp/detail";
	}
	@RequestMapping(value="/add")
	public String add(final Model model){
		return "cp/add";
	}
	
	@RequestMapping(value="/save")
	public String save(final ContentProvider contentProvider){
		contentProviderService.save(contentProvider);
		return "redirect:list.go";
		
	}
	
	@RequestMapping(value="/update")
	public String update(final ContentProvider contentProvider){
		contentProviderService.update(contentProvider);
		return "redirect:list.go";
	}
	}

