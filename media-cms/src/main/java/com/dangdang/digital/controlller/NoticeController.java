package com.dangdang.digital.controlller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.controlller.BaseController;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.Notice;
import com.dangdang.digital.model.NoticeType;
import com.dangdang.digital.model.SpecialTopic;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.INoticeService;
import com.dangdang.digital.service.INoticeTypeService;
import com.dangdang.digital.service.ISpecialTopicService;
import com.dangdang.digital.service.ISysPropertiesService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;


/**
 * Notice Controller.
 */
@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource INoticeService noticeService;
	
	@Resource INoticeTypeService noticeTypeService;
	
	@Resource ISysPropertiesService sysPropertiesService;
	
	@Resource
	private IMediaSaleService mediaSaleService;
	
	@Resource 
	private IActivityUserService activityService;
	
	//专题
	@Resource 
	private ISpecialTopicService specialTopicService;
	
	//media分类
	@Resource 
	private ICatetoryService catetoryService;

	
	@RequestMapping(value="/media")
	public String getMedia(Query query,final Model model,final MediaSale mediaSale){

		PageFinder<MediaSale> pageFinder = mediaSaleService.findPageFinderObjs(mediaSale,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("mediaSale", mediaSale);
		return "notice/media";
	}
	
	@RequestMapping("users")
	public String list(Model model, String page,ActivityUser activityUser){
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		
		PageFinder<ActivityUser> pageFinder = activityService.findPageFinderObjs(activityUser, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("one", activityUser);
		return "notice/users";
	}
	//special
	
	/**
	 * Description: 
	 * @Version1.0 2015年1月8日 下午6:48:08 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param page
	 * @param block
	 * @return
	 */
	@RequestMapping("special")
	public String list(Model model, String page, SpecialTopic specialTopic){
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		PageFinder<SpecialTopic> pageFinder = specialTopicService.findPageFinderObjs(specialTopic, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("specialTopic", specialTopic);
		return "notice/speciallist";
	}
	
	//mediacategory
	@RequestMapping("mediacategory")
	public String list(Model model, Query query,Catetory catetory){
		Map<String, String> paramMap = new HashMap<String, String>();
		if(catetory.getId() != null && catetory.getId().intValue() != 0){
			paramMap.put("id", catetory.getId().toString());
		}
		if(catetory.getParentId() != null){
			paramMap.put("parentId", catetory.getParentId().toString());
		}
		
		query.setPageSize(Integer.MAX_VALUE);
		PageFinder<Catetory> pageFinder = catetoryService.findPageFinderObjs(paramMap, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "notice/categorylist";
	}
	@RequestMapping(value="/type/list")
	public String getNoticeType(String pageIndex,final Model model,final NoticeType noticeType){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<NoticeType> pageFinder = noticeTypeService.findPageFinderObjs(noticeType,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "notice/type/list";
	}
	@RequestMapping(value="/type/add")
	public String addType(final Model model){
		List<NoticeType> noticeTypeList = this.noticeTypeService.getAll();
		StringBuilder existCodes = new StringBuilder();
		if(null != noticeTypeList && noticeTypeList.size() > 0){
			for(NoticeType nt:noticeTypeList){
				existCodes.append(nt.getType()).append(",");
			}
		}
		model.addAttribute("existCodes",existCodes);
		return "notice/type/add";
	}
	@RequestMapping(value="/type/save")
	public String saveType(final NoticeType noticeType ){
		noticeType.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		this.noticeTypeService.save(noticeType);
		return "redirect:list.go";
		
	}
	
	@RequestMapping(value="/type/delete")
	public String deleteType(@RequestParam final int noticeTypeId){
		this.noticeTypeService.deleteById(noticeTypeId);
		return "redirect:list.go";
	}
	
	@RequestMapping(value="/list")
	public String getNotice(String pageIndex,final Model model,final Notice notice){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<Notice> pageFinder = noticeService.findPageFinderObjs(notice,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "notice/list";
	}
	
	@RequestMapping(value="/delete")
	public String delete(@RequestParam final long noticeId){
		this.noticeService.deleteById(noticeId);
		return "redirect:list.go";
	}
	@RequestMapping(value="/edit")
	public String edit(@RequestParam final long noticeId,final Model model){
		Notice notice = this.noticeService.get(noticeId);
		List<NoticeType> noticeTypeList = this.noticeTypeService.getAll();
		model.addAttribute("noticeTypeList",noticeTypeList);
		model.addAttribute("notice", notice);
		return "notice/edit";
	}
	@RequestMapping(value="/add")
	public String add(final Model model){
		List<NoticeType> noticeTypeList = this.noticeTypeService.getAll();
		List<Map<String,String>> type = new ArrayList<Map<String,String>>();
		model.addAttribute("noticeTypeList",noticeTypeList);
		return "notice/add";
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(final Notice notice,final Notice.Parameter parameter){
		try{
			String parameterJson = JSON.toJSONString(parameter);
			notice.setParameter(parameterJson);
			notice.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
			noticeService.save(notice);
		}catch(Exception e){
			LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+"　添加公告内容失败",e);
			return "{\"result\":\"failure\"}";
		}
		return "{\"result\":\"success\"}";
		
	}
	
	@RequestMapping(value="/update")
	public String update(Notice notice){
		notice.setModifer(UsercmsUtils.getCurrentUser().getLoginName());
		noticeService.update(notice);
		return "redirect:list.go";
	}
	public static void main(String[] args){
		List<String> list = null;
	}
}

