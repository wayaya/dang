package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.dangdang.digital.controlller.BaseController;
import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.AnnouncementsContent;
import com.dangdang.digital.service.IAnnouncementsCategoryService;
import com.dangdang.digital.service.IAnnouncementsContentService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.UsercmsUtils;


/**
 * 
 * Description: 公告
 * All Rights Reserved.
 * @version 1.0  2015年1月5日 下午2:21:28  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Controller
@RequestMapping("announcements")
public class AnnouncementsController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource IAnnouncementsCategoryService announcementsCategoryService;
	
	
	@Resource IAnnouncementsContentService announcementsContentService;
	
	@RequestMapping(value="/checkcode")
	@ResponseBody
	public String checkColumnCode(String categoryCode){
		AnnouncementsCategory ac=	announcementsCategoryService.findUniqueByParams("categoryCode",categoryCode);
		if(null !=ac){
			return "{\"result\":\"failure\"}";
		}else{
			//编号已存在
			return "{\"result\":\"success\"}";
		}
	}
	
	
	@RequestMapping(value="/list")
	public String getCategory(String pageIndex,final Model model,final AnnouncementsCategory announcementsCategory){
		model.addAttribute("announcementsCategory", announcementsCategoryService.getAll());
		return "announcements/list";
	}
	
	/**
	 * 显示公告类型
	 * Description: 
	 * @Version1.0 2015年1月5日 下午4:52:18 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param model
	 * @param announcementsCategory
	 * @return
	 */
	@RequestMapping(value="/nodes")
	public String getAnnouncementsCategory(final Model model,final AnnouncementsCategory announcementsCategory){
		model.addAttribute("announcementsCategory", announcementsCategoryService.findListByParams("parentId",announcementsCategory.getCategoryId()));
		model.addAttribute("categoryCode",announcementsCategory.getCategoryCode());
		return "announcements/nodes";
	}
	/**
	 * 显示公告内容
	 * Description: 
	 * @Version1.0 2015年1月5日 下午4:52:18 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param pageIndex
	 * @param model
	 * @param label
	 * @param pageIndex
	 * @param model
	 * @param announcementsCategory
	 * @return
	 */
	@RequestMapping(value="/content")
	public String getAnnouncementsCategoryContent(final Model model,final AnnouncementsCategory announcementsCategory){
		model.addAttribute("announcementsContent", announcementsContentService.findListByParams("categoryCode",announcementsCategory.getCategoryCode()));
		model.addAttribute("categoryCode",announcementsCategory.getCategoryCode());
		return "announcements/content";
	}

	
	@RequestMapping(value="/delete")
	public String delete(final AnnouncementsCategory announcementsCategory){
		this.announcementsCategoryService.deleteAnnouncementsCategory(announcementsCategory);
		return "redirect:list.go";
	}
	
	/**
	 * 
	 * Description: 修改类型内容
	 * @Version1.0 2015年1月6日 下午2:40:15 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param categoryId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String edit(@RequestParam final long  categoryId,final Model model){
		model.addAttribute("category", announcementsCategoryService.get(categoryId));
		return "announcements/edit";
	}
	
	/**
	 * 
	 * Description: 更新公告类型信息
	 * @Version1.0 2015年1月6日 下午2:42:37 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param categoryId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(final AnnouncementsCategory ac,final Model model){
		model.addAttribute("parentId", ac.getParentId());
		announcementsCategoryService.update(ac);
		return "announcements/nodes";
	}
	
	
	@RequestMapping(value="/editcontent")
	public String editContent(@RequestParam final long  announcementId,final Model model){
		AnnouncementsContent ac = announcementsContentService.get(announcementId);
		model.addAttribute("announcementsContent", ac);
		return "announcements/editcontent";
	}
	@RequestMapping(value="/updatecontent")
	public String updateContent(final AnnouncementsContent ac){
		announcementsContentService.update(ac);
		ac.setModifer(UsercmsUtils.getCurrentUser().getLoginName());
		return "redirect:content.go?categoryCode="+ac.getCategoryCode();
	}
	
	@RequestMapping(value="/add")
	public String add(final Model model){
		return "announcements/add";
	}
	
	/**
	 * 
	 * Description: 保存公告内容
	 * @Version1.0 2015年1月5日 下午7:05:08 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	@RequestMapping(value="/savecontent")
	public String saveContent(final AnnouncementsContent announcementsContent){
		announcementsContent.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		this.announcementsContentService.save(announcementsContent);
		return "redirect:content.go?categoryCode="+announcementsContent.getCategoryCode();
	}
	
	/**
	 * 
	 * Description: 删除公告内容
	 * @Version1.0 2015年1月6日 上午10:34:07 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param announcementsContent
	 * @return
	 */
	@RequestMapping(value="/deletecontent")
	public String deleteContent(final AnnouncementsContent announcementsContent,Long[] announcementId){
		this.announcementsContentService.deleteByIds(Arrays.asList(announcementId));
		return "redirect:content.go?categoryCode="+announcementsContent.getCategoryCode();
	}
	/**
	 * 
	 * Description: 
	 * @Version1.0 2015年1月5日 下午5:54:19 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param categoryCode 公告分类标识
	 * @param model	
	 * @return
	 */
	@RequestMapping(value="/addcontent")
	public String addContent(final String categoryCode,final Model model){
		model.addAttribute("categoryCode", categoryCode);
		return "announcements/addcontent";
	}
	@RequestMapping(value="/save")
	public String save(AnnouncementsCategory announcementsCategory,@RequestParam(value="upfilename",required=false) MultipartFile file){
		String icon =  announcementsCategory.getIcon();
		if(null!=icon &&(file!=null && file.getSize() > 0)){
			String path = System.currentTimeMillis()+"_"+icon;
			File rootDir = new File(ConfigPropertieUtils.getString("media.resource.root.path")+File.separator+"column");
			try{
				if(!rootDir.exists()){
					rootDir.mkdirs();
				}
				File iconFile = new File(rootDir,path);
				if(!iconFile.exists()){
					iconFile.createNewFile();
				}
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(iconFile));
			} catch (Exception e) {
				LogUtil.error(logger,"{0}新公告时,保存图标出错",e, UsercmsUtils.getCurrentUser().getLoginName());
			}
			announcementsCategory.setIcon(path);
		}
		announcementsCategory.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		announcementsCategory.setCreateDate(DateUtil.format(new Date(),DateUtil.DATE_PATTERN));
		this.announcementsCategoryService.save(announcementsCategory);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 创建公告:"+announcementsCategory.getCategoryName()+" 成功");
		return "redirect:list.go?parentId="+announcementsCategory.getParentId();
	}
}

