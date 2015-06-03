package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.Block;
import com.dangdang.digital.model.BlockGroup;
import com.dangdang.digital.model.SpecialTopic;
import com.dangdang.digital.model.SpecialTopicCategory;
import com.dangdang.digital.service.IBlockGroupService;
import com.dangdang.digital.service.IBlockService;
import com.dangdang.digital.service.ISpecialTopicCategoryService;
import com.dangdang.digital.service.ISpecialTopicService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

/**
 * Description: 专题管理
 * All Rights Reserved.
 * @version 1.0  2015年1月9日 上午9:29:20  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Controller
@RequestMapping("block/special")
public class SpecialTopicController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpecialTopicController.class);
	
	@Resource private ISpecialTopicService specialTopicService;
	@Resource private IBlockGroupService blockGroupService;
	@Resource private IBlockService blockService;
	
	@Resource private ISpecialTopicCategoryService specialTopicCategoryService;
	
	/**
	 * 
	 * Description: 专题主页面,由左侧类型树和右侧专题内容组成
	 * @Version1.0 2015年3月4日 下午6:10:44 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	@RequestMapping("main")
	public String main(final Model model){
		List<SpecialTopicCategory> listSpecialTopicCategory = specialTopicCategoryService.getAll();
		StringBuffer treeHtml = new StringBuffer("[{id:0, pId:0,path:'',name:\"专题分类\",open:true},");
		if(null !=listSpecialTopicCategory){
			for(SpecialTopicCategory stc: listSpecialTopicCategory){
				treeHtml.append("{id:").append(stc.getStTypeId()).append(",");
				treeHtml.append("pId:").append(stc.getParentId()).append(",");
				treeHtml.append("code:\"").append(stc.getCategoryCode()).append("\",");
				treeHtml.append("channel:\"").append(stc.getChannel()).append("\",");
				treeHtml.append("path:\"").append(stc.getPath()).append("\",");
				treeHtml.append("name:\"").append(stc.getCategoryName()).append("\",");
				treeHtml.append("},");
			}
		}
		treeHtml.deleteCharAt(treeHtml.length() - 1);
		treeHtml.append("]");
		model.addAttribute("treeHtml", treeHtml.toString());
		return "block/special_topic/main";
	}
	
	/**
	 * 
	 * Description: 添加专题分类节点
	 * @Version1.0 2015年3月5日 上午9:43:07 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param parentId	
	 * @param model
	 * @return
	 */
	@RequestMapping("addcategory")
	public String addSpecialTopicCategory(Long  parentId,String path,final Model model){
		model.addAttribute("parentId", parentId);
		model.addAttribute("path", path);//父节点的path
		return "block/special_topic/addcategory";
	}
	
	/**
	 * 
	 * Description: 删除专题类型,同时删除该类型下的专题列表
	 * @Version1.0 2015年3月5日 上午11:20:24 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param path
	 * @param model
	 * @return
	 */
	@RequestMapping("delcategory")
	public String delSpecialTopicCategory(Long  stTypeId,final Model model){
		this.specialTopicCategoryService.deleteById(stTypeId);
		return "redirect:main.go?stTypeId="+stTypeId;
	}
	
	/**
	 * 
	 * Description:保存专题分类 
	 * @Version1.0 2015年3月5日 上午10:05:05 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param stc
	 * @return
	 */
	@RequestMapping("savecategory")
	public String saveSpecialTopicCategory(final SpecialTopicCategory stc){
		String path = stc.getPath();
		if(null == path  || path.trim().isEmpty()){
			//一级节点
			stc.setPath(stc.getCategoryCode());
		}else{
			stc.setPath(path.concat("-").concat(stc.getCategoryCode()));
		}
		stc.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
//		stc.setCreator("test");
		specialTopicCategoryService.save(stc);
		return "redirect:list.go";
	}
	/**

	 * Description: 
	 * @Version1.0 2015年1月8日 下午6:48:08 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param page
	 * @param block
	 * @return
	 */
	@RequestMapping("list")
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
		
		return "block/special_topic/dataList";
	}
	
	
	
	/**
	 * Description: 去添加专题页面
	 * @Version1.0 2015年1月8日 下午6:57:33 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param specialTopic
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public String add(SpecialTopic specialTopic, final Model model){
		model.addAttribute("specialTopic", specialTopic);
		List<BlockGroup> blockGroupList = blockGroupService.findListByParams();
		
		StringBuffer specialTopicListOption = new StringBuffer();
		if(blockGroupList != null && blockGroupList.size() > 0){
			int i = 1;
			for(BlockGroup blockGroup : blockGroupList){
				if(blockGroup.getParentId().intValue() == 0){
					specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" >"+blockGroup.getName()+"</option>");
					specialTopicListOption.append(recursionColumnList(blockGroupList, blockGroup.getMediaBlockGroupId(), i, null));
				}
			}
		}
		
		model.addAttribute("specialTopicListOption", specialTopicListOption.toString());
		
		return "/block/special_topic/add";
	}
	

	public StringBuffer recursionColumnList(List<BlockGroup> blockGroupList, long mediaBlockGroupId, int i, Integer selectBlockGroupId) {
		String prix = "";
		switch (i) {
		case 1:
			prix = "|--";
			break;
		case 2:
			prix = "|--|--";
			break;
		case 3:
			prix = "|--|--|--";
			break;
		case 4:
			prix = "|--|--|--|--";
			break;
		case 5:
			prix = "|--|--|--|--|--";
			break;
		default:
			break;
		}
		StringBuffer columnListOptionTemp = new StringBuffer();
		if(blockGroupList != null && blockGroupList.size() > 0){
			boolean c = false;
			for(BlockGroup blockGroup : blockGroupList){
				if(blockGroup.getParentId().intValue() == mediaBlockGroupId){
					c = true;
					if(selectBlockGroupId != null){
						if(blockGroup.getMediaBlockGroupId().longValue() == selectBlockGroupId){
							columnListOptionTemp.append("<option value="+blockGroup.getMediaBlockGroupId()+" selected='selected'>"+prix+blockGroup.getName()+"</option>");
						}else{
							columnListOptionTemp.append("<option value="+blockGroup.getMediaBlockGroupId()+" >"+prix+blockGroup.getName()+"</option>");
						}
					}else{
						columnListOptionTemp.append("<option value="+blockGroup.getMediaBlockGroupId()+" >"+prix+blockGroup.getName()+"</option>");
					}
					
					columnListOptionTemp.append(recursionColumnList(blockGroupList, blockGroup.getMediaBlockGroupId(), i+1, selectBlockGroupId));
				}
			}
			if(!c){
				return columnListOptionTemp;
			}
		}
		return columnListOptionTemp;
	}
	
	/**
	 * Description: 新增
	 * @Version1.0 2015年1月9日 上午9:28:36 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param specialTopic
	 * @param codes
	 * @param file
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping(value="/add")
	public String add(final Model model,SpecialTopic specialTopic, String[] codes, @RequestParam(value="upfilename",required=false) MultipartFile file) throws MediaException{
		String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
		
		specialTopic.setCreationDate(new Date());
		specialTopic.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
		specialTopic.setLastModifiedDate(new Date());
		specialTopic.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
		
		if(file!=null && file.getSize()>0){
			try {
				String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
				File f = new File(ConfigPropertieUtils.getString("media.resource.images.path") + File.separator + month);
				f.mkdirs();
				f = new File(f,fileName);
				if(!f.exists()){
					f.createNewFile();
				}else{
					f.delete();
				}
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
				specialTopic.setPicPath(ConfigPropertieUtils.getString("media.resource.images.http.path") + File.separator + month+"/"+fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		if(codes != null && codes.length > 0){
//			StringBuffer codesb = new StringBuffer();
//			for(String code : codes){
//				codesb.append(code).append("::");
//			}
//			String relationBlockCodes = "";
//			if(codesb.length() > 0){
//				relationBlockCodes = codesb.substring(0, codesb.length() - 2);
//			}
//			specialTopic.setRelationBlockCodes(relationBlockCodes);
//		}
		
		specialTopicService.save(specialTopic);
		
//		List<BlockGroup> blockGroupList = blockGroupService.findListByParams();
//		
//		StringBuffer specialTopicListOption = new StringBuffer();
//		if(blockGroupList != null && blockGroupList.size() > 0){
//			int i = 1;
//			for(BlockGroup blockGroup : blockGroupList){
//				if(blockGroup.getParentId().intValue() == 0){
//					if(specialTopic.getBlockGroupId() != null && blockGroup.getMediaBlockGroupId().intValue() == specialTopic.getBlockGroupId().intValue()){
//						specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" selected='selected'>"+blockGroup.getName()+"</option>");
//					}else{
//						specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" >"+blockGroup.getName()+"</option>");
//					}
//					specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" >"+blockGroup.getName()+"</option>");
//					specialTopicListOption.append(recursionColumnList(blockGroupList, blockGroup.getMediaBlockGroupId(), i, null));
//				}
//			}
//		}
//		
//		List<Block> blockList = blockService.findListByParams();
//		model.addAttribute("specialTopicListOption", specialTopicListOption.toString());
//		model.addAttribute("blockList", blockList);
		
		
		model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>恭喜，添加成功！");
		model.addAttribute("successFlag", 0);
		model.addAttribute("specialTopic", specialTopic);
		return "/block/special_topic/add";
	}
	
	/**
	 * 
	 * Description: 删除指定专题
	 * @Version1.0 2015年3月10日 下午4:41:58 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param specialTopic 
	 * @return
	 */
	@RequestMapping(value="/delete")
	public String deleteSpecial(final SpecialTopic specialTopic){
		this.specialTopicService.deleteById(specialTopic.getStId());
		//同时还需要删除相应图标.图标地址?
		return "redirect:list.go?stTypeId="+specialTopic.getStTypeId()+"&channelType="+specialTopic.getChannelType();
	}
	/**
	 * Description: 修改
	 * @Version1.0 2015年1月9日 上午9:27:57 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param specialTopic
	 * @param codes
	 * @param file
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping(value="/goUpdate")
	public String goUpdate(final Model model, Integer stId) throws MediaException{
		SpecialTopic specialTopic= specialTopicService.get(stId);
		
		List<BlockGroup> blockGroupList = blockGroupService.findListByParams();
		
		StringBuffer specialTopicListOption = new StringBuffer();
		if(blockGroupList != null && blockGroupList.size() > 0){
			int i = 1;
			for(BlockGroup blockGroup : blockGroupList){
				if(blockGroup.getParentId().intValue() == 0){
					if(specialTopic.getBlockGroupId() != null && blockGroup.getMediaBlockGroupId().intValue() == specialTopic.getBlockGroupId().intValue()){
						specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" selected='selected'>"+blockGroup.getName()+"</option>");
					}else{
						specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" >"+blockGroup.getName()+"</option>");
					}
					
					specialTopicListOption.append(recursionColumnList(blockGroupList, blockGroup.getMediaBlockGroupId(), i, specialTopic.getBlockGroupId()));
				}
			}
		}
		
		if(specialTopic.getBlockGroupId() != null){
			List<Block> blockList = blockService.findListByParams("groupId", specialTopic.getBlockGroupId());
			StringBuffer blockInfo = new StringBuffer();
			StringBuffer blockInfoBak = new StringBuffer();
			if(blockList != null && blockList.size() > 0){
				if(StringUtils.isNotBlank(specialTopic.getRelationBlockCodes())){
					String[] blockCodes = specialTopic.getRelationBlockCodes().split("::");
					for(Block block : blockList){
						boolean flag = false;
						if(StringUtils.isNotBlank(specialTopic.getRelationBlockCodes())){
							for(String blockCode : blockCodes){
								if(blockCode.trim().equals(block.getCode().trim())){
									flag = true;
								}
							}
						}
						if(flag){
							blockInfo.append("<span><input type=\"checkbox\" id=\"codes\" name=\"codes\" checked=\"checked\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
							blockInfoBak.append("<span><input type=\"checkbox\" checked=\"checked\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
						}else{
							blockInfo.append("<span><input type=\"checkbox\" id=\"codes\" name=\"codes\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
							blockInfoBak.append("<span><input type=\"checkbox\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
						}
					}
				}else{
					for(Block block : blockList){
						blockInfo.append("<span><input type=\"checkbox\" id=\"codes\" name=\"codes\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
						blockInfoBak.append("<span><input type=\"checkbox\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
					}
				}
			}
			model.addAttribute("blockInfo", blockInfo.toString());
			model.addAttribute("blockInfoBak", blockInfoBak.toString());
		}
		
		model.addAttribute("specialTopicListOption", specialTopicListOption.toString());
		
		model.addAttribute("specialTopic", specialTopic);
		return "/block/special_topic/modify";
	}
	
	/**
	 * Description: 修改
	 * @Version1.0 2015年1月9日 上午9:27:57 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param specialTopic
	 * @param codes
	 * @param file
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping(value="/detail")
	public String detail(final Model model, Integer stId) throws MediaException{
		SpecialTopic specialTopic= specialTopicService.get(stId);
		
		List<BlockGroup> blockGroupList = blockGroupService.findListByParams();
		
		StringBuffer specialTopicListOption = new StringBuffer();
		if(blockGroupList != null && blockGroupList.size() > 0){
			int i = 1;
			for(BlockGroup blockGroup : blockGroupList){
				if(blockGroup.getParentId().intValue() == 0){
					if(specialTopic.getBlockGroupId() != null && blockGroup.getMediaBlockGroupId().intValue() == specialTopic.getBlockGroupId().intValue()){
						specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" selected='selected'>"+blockGroup.getName()+"</option>");
					}else{
						specialTopicListOption.append("<option value="+blockGroup.getMediaBlockGroupId()+" >"+blockGroup.getName()+"</option>");
					}
					
					specialTopicListOption.append(recursionColumnList(blockGroupList, blockGroup.getMediaBlockGroupId(), i, specialTopic.getBlockGroupId()));
				}
			}
		}
		
		if(specialTopic.getBlockGroupId() != null){
			List<Block> blockList = blockService.findListByParams("groupId", specialTopic.getBlockGroupId());
			StringBuffer blockInfo = new StringBuffer();
			if(blockList != null && blockList.size() > 0){
				String[] blockCodes = specialTopic.getRelationBlockCodes().split("::");
				for(Block block : blockList){
					boolean flag = false;
					if(StringUtils.isNotBlank(specialTopic.getRelationBlockCodes())){
						for(String blockCode : blockCodes){
							if(blockCode.trim().equals(block.getCode().trim())){
								flag = true;
							}
						}
					}
					if(flag){
						blockInfo.append("<span><input type=\"checkbox\" id=\"codes\" name=\"codes\" checked=\"checked\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
					}
				}
			}
			model.addAttribute("blockInfo", blockInfo.toString());
		}
		
		model.addAttribute("specialTopicListOption", specialTopicListOption.toString());
		
		model.addAttribute("specialTopic", specialTopic);
		return "/block/special_topic/detail";
	}
	
	/**
	 * Description: 修改
	 * @Version1.0 2015年1月9日 上午9:27:57 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param specialTopic
	 * @param codes
	 * @param file
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping(value="/update")
	public String update(final Model model, SpecialTopic specialTopic, String[] codes, @RequestParam(value="upfilename",required=false) MultipartFile file) throws MediaException{
		try {
			String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
			specialTopic.setLastModifiedDate(new Date());
			specialTopic.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
				if(file!=null && file.getSize()>0){
					try {
						String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
						File f = new File(ConfigPropertieUtils.getString("media.resource.images.path") + File.separator + month);
						f.mkdirs();
						f = new File(f,fileName);
						if(!f.exists()){
							f.createNewFile();
						}else{
							f.delete();
						}
						FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
						specialTopic.setPicPath(ConfigPropertieUtils.getString("media.resource.images.http.path") + File.separator + month+"/"+fileName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				specialTopicService.update(specialTopic);
				//更新缓存
				
				model.addAttribute("showPageType", "1");
				model.addAttribute("specialTopic", specialTopic);
				model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>恭喜，修改成功！&nbsp;&nbsp;<a href='/block/special/goUpdate.go?stId="+specialTopic.getStId()+"'><img src='/images/repeat_modify.jpg' style='padding-bottom: 0px;'></a>&nbsp;&nbsp;");
				model.addAttribute("successFlag", 0);
		}catch(Exception e){
			LogUtil.error(LOGGER, e, "更新专题，更新id："+specialTopic.getStId()+";名称"+specialTopic.getName(), UsercmsUtils.getCurrentUser().getLoginName());
		}
		return "/block/special_topic/modify";
	}
	
	@RequestMapping(value="/getSelectBlockByGroupId")
	@ResponseBody
	public void getSelectBlockByGroupId(final Model model, Integer groupId, Integer stId) throws MediaException{
		try {
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			StringBuffer blockInfo = new StringBuffer();
			if(groupId != null && groupId.intValue() > 0){
				List<Block> blockList = blockService.findListByParams("groupId", groupId);
				if(blockList != null && blockList.size() > 0){
					for(Block block : blockList){
						blockInfo.append("<span><input type=\"checkbox\" id=\"codes\" name=\"codes\" value=\""+block.getCode()+"\">&nbsp;"+block.getName()+"</input>&nbsp;&nbsp;</span>");
					}
				}
			}
			JSONObject json=new JSONObject();
			json.put("blockInfo", blockInfo);
			json.put("flag", 1);
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}