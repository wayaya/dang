package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

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

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.Block;
import com.dangdang.digital.model.BlockGroup;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.IBlockGroupService;
import com.dangdang.digital.service.IBlockService;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IColumnService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;

/**
 * Description: 块管理controller
 * All Rights Reserved.s
 * @version 1.0  2014年11月26日 上午10:01:05  by 周伟洋（zhouweiyang@dangdang.com）创建
 */

@Controller
@RequestMapping("block")
public class BlockController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BlockController.class);
	
	@Resource private IBlockService blockService;
	@Resource private IBlockGroupService blockGroupService;
	@Resource private ICatetoryService catetoryService;
	@Resource private IColumnService  columnService;
	@Resource private ICacheApi cacheApi;
	
	/**
	 * Description: 块主页面
	 * @Version1.0 2014年11月26日 上午11:44:16 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @return
	 */
	@RequestMapping("main")
	public String main(final Model model){
		StringBuffer msg = new StringBuffer("[{\"name\":\"推荐管理分组\",\"open\":true,\"id\":\"0\",\"children\":[");
		List<BlockGroup> list = blockGroupService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		model.addAttribute("data", msg  );
		return "block/list";
	}
	
	@RequestMapping(value="/toAddGroup")
	public String toAdd(Style style,final Model model){
		String parentId = getRequest().getParameter("parentId");
		if(!"0".equals(parentId)){
			model.addAttribute("parentId", parentId);
		}
		return "block/groupAdd";
	}
	
	@RequestMapping(value="/addText")
	public String addText(final Model model){
		String parentId = getRequest().getParameter("parentId");
		if(!"0".equals(parentId)){
			model.addAttribute("parentId", parentId);
		}
		return "block/groupAdd";
	}
	
	@RequestMapping("addGroup")
	public String addGroup(BlockGroup group) throws IOException, MediaException{
		int i = blockGroupService.save(group);
		if(i!=1){
			throw new MediaException("add_group is error. MediaBlockGroupId:"+group.getMediaBlockGroupId());
		}
		return "redirect:main.go";
	}
	/**
	 * Description: 更新快组
	 * @Version1.0 2014年11月26日 上午11:44:06 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param group
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping("updateGroup")
	public String updateGroup(BlockGroup group) throws  MediaException{
		int i = blockGroupService.update(group);
		if(i!=1){
			throw new MediaException("update_group is error. MediaBlockGroupId:"+group.getMediaBlockGroupId());
		}
		return "redirect:list.go";
	}
	
	/**
	 * Description:获取左侧的tree 
	 * @Version1.0 2014年11月26日 上午11:43:55 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param list
	 * @param msg
	 */
	private void getTree(List<BlockGroup> list,StringBuffer msg){
		for(BlockGroup group : list){
			msg.append("{\"name\":\""+group.getName()+"\",");
			msg.append("\"id\":\""+group.getMediaBlockGroupId()+"\",");
			msg.append("\"parentId\":\""+group.getParentId()+"\",");
			
			List<BlockGroup> treeList = blockGroupService.getTreeByParentId(group);
			if(treeList.size() > 0){
				msg.append("\"isParent\":true,");
			}else{
				msg.append("\"isParent\":false,");
			}
			if(treeList.size() > 0){
				msg.append("\"children\":[");
				getTree(treeList,msg);
				msg.append("],");;
			}
			msg.deleteCharAt(msg.length() - 1);
			msg.append("},");
		}
		msg.deleteCharAt(msg.length() - 1);
	}
	
	/**
	 * Description: 跳转页
	 * @Version1.0 2014年11月26日 上午11:43:39 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toEditGroup")
	public String toEditGroup(@RequestParam final Long id,final Model model){
		BlockGroup group = blockGroupService.get(id);
		model.addAttribute("group", group);
		return "block/groupModify";
	}
	
	
	/**
	 * Description: 删除块组
	 * @Version1.0 2014年11月26日 上午11:43:30 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param group
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping("delGroup")
	public String delGroup(BlockGroup group) throws MediaException{
		int i = blockGroupService.deleteById(group.getMediaBlockGroupId());
		if(i!=1){
			throw new MediaException("del_group is error. MediaBlockGroupId:"+group.getMediaBlockGroupId());
		}
		return "redirect:main.go";
	}
	
	/**
	 * Description: 获取该分组下块列表
	 * @Version1.0 2014年11月22日 下午6:11:14 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param page
	 * @param block
	 * @return
	 */
	@RequestMapping("list")
	public String list(Model model, String page,Block block){
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		PageFinder<Block> pageFinder = blockService.findPageFinderObjs(block, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("one", block);
		
		return "block/dataList";
	}
	
	
	
	/**
	 * 进入添加选择页面.
	 **/
	@RequestMapping(value="/toAdd")
	public String createPre(Block block,final Model model){
		model.addAttribute("block", block);
		List<Column> columnList = columnService.findListByParams("isactiverForever", true);
				
		StringBuffer columnListOption = new StringBuffer();
		if(columnList != null && columnList.size() > 0){
			int i = 1;
			for(Column column : columnList){
				if(column.getParentId().intValue() == 0){
					columnListOption.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" >"+column.getName()+"</option>");
					columnListOption.append(recursionColumnList(columnList, column.getColumnId(), i, null));
				}
			}
		}
		model.addAttribute("columnListOption", columnListOption.toString());
		return "/block/add";
	}
	
	@RequestMapping(value="/toText")
	public String toText(Block block,final Model model){
		model.addAttribute("block", block);
		List<Column> columnList = columnService.findListByParams("isactiverForever", true);
				
		StringBuffer columnListOption = new StringBuffer();
		if(columnList != null && columnList.size() > 0){
			int i = 1;
			for(Column column : columnList){
				if(column.getParentId().intValue() == 0){
					columnListOption.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" >"+column.getName()+"</option>");
					columnListOption.append(recursionColumnList(columnList, column.getColumnId(), i, null));
				}
			}
		}
		model.addAttribute("columnListOption", columnListOption.toString());
		return "/block/addtext";
	}
	
	
	public StringBuffer recursionColumnList(List<Column> columnList, int columnId, int i, Integer selectColumnId) {
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
		if(columnList != null && columnList.size() > 0){
			boolean c = false;
			for(Column column : columnList){
				if(column.getParentId().intValue() == columnId){
					c = true;
					if(selectColumnId != null){
						if(column.getColumnId().intValue() == selectColumnId){
							columnListOptionTemp.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" selected='selected'>"+prix+column.getName()+"</option>");
						}else{
							columnListOptionTemp.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" >"+prix+column.getName()+"</option>");
						}
					}else{
						columnListOptionTemp.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" >"+prix+column.getName()+"</option>");
					}
					
					columnListOptionTemp.append(recursionColumnList(columnList, column.getColumnId(), i+1, selectColumnId));
				}
			}
			if(!c){
				return columnListOptionTemp;
			}
		}
		return columnListOptionTemp;
	}
	
	/**
	 * Description:添加 
	 * @Version1.0 2014年11月26日 上午11:43:08 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param block
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping(value="/add")
	public String add(final String[] btitle,final String[]bicon,final String[] btype,
			final String[]bcode,final String[]burl,final Model model,Block block, @RequestParam(value="upfilename",required=false) MultipartFile file) throws MediaException{
		//拼接Banner内容
		if(null != btitle && btitle.length > 0 ){
			StringBuilder sbBanner = new StringBuilder();
			sbBanner.append("{\"banner\":[");
			int length = btitle.length;
			for(int i=0 ; i< length; i++ ){
				sbBanner.append("{");
				sbBanner.append("\"title\":\"").append(btitle[i]).append("\",");
				sbBanner.append("\"coverPic\":\"").append(bicon[i]).append("\",");
				sbBanner.append("\"type\":\"").append(btype[i]).append("\",");
				sbBanner.append("\"code\":\"").append(bcode[i]).append("\",");
				sbBanner.append("\"url\":\"").append(burl[i]).append("\"},");
			}	
			sbBanner.deleteCharAt(sbBanner.length()-1);
			sbBanner.append("]}");
			//显示
			//还需要验证下json格式是否正确
			block.setContent(sbBanner.toString());
			}
		String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
		block.setCreationDate(new Date());
		block.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
		block.setStatus(1);
		block.setType(0);//现在所有Banner的内容均为Json格式
		if(block.getType() == 0 &&(null != block.getContent() && !block.getContent().trim().isEmpty())){
			try{
				JSON.parse(block.getContent());
			}catch(com.alibaba.fastjson.JSONException je){
				model.addAttribute("returnInfo", "<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>json格式错误！");
				model.addAttribute("successFlag", 1);
				model.addAttribute("block", block);
				return "/block/add";
			}
		}
		
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
				block.setPicPath(ConfigPropertieUtils.getString("media.resource.images.http.path") + File.separator + month+"/"+fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String relationColumnCode = block.getRelationColumnCode();
		if(StringUtils.isNotBlank(relationColumnCode)){
			block.setRelationColumnId(Integer.parseInt(relationColumnCode.split("::")[0]));
			block.setRelationColumnCode(relationColumnCode.split("::")[1]);
		}
		
		blockService.save(block);
		model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>恭喜，添加成功！<a href='/block/list.go?groupId="+block.getGroupId()+"'>查看列表</a>");
		model.addAttribute("successFlag", 0);
		model.addAttribute("block", block);
		return "/block/add";
	}
	
	
	
	
	/**
	 * Description:添加 
	 * @Version1.0 2014年11月26日 上午11:43:08 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param block
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping(value="/savetext")
	public String add(final Model model,Block block, @RequestParam(value="upfilename",required=false) MultipartFile file) throws MediaException{
		String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
		
		block.setCreationDate(new Date());
		block.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
		block.setStatus(1);
		
		if(block.getType() == 0){
			try{
				JSON.parse(block.getContent());
			}catch(com.alibaba.fastjson.JSONException je){
				model.addAttribute("returnInfo", "<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>json格式错误！");
				model.addAttribute("successFlag", 1);
				model.addAttribute("block", block);
				return "/block/addtext";
			}
		}
		
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
				block.setPicPath(ConfigPropertieUtils.getString("media.resource.images.http.path") + File.separator + month+"/"+fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String relationColumnCode = block.getRelationColumnCode();
		if(StringUtils.isNotBlank(relationColumnCode)){
			block.setRelationColumnId(Integer.parseInt(relationColumnCode.split("::")[0]));
			block.setRelationColumnCode(relationColumnCode.split("::")[1]);
		}
		
		blockService.save(block);
		updateCache(block);
		model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>恭喜，添加成功！<a href='/block/list.go?groupId="+block.getGroupId()+"'>查看列表</a>");
		model.addAttribute("successFlag", 0);
		model.addAttribute("block", block);
		//return "/block/add";
		return "redirect:list.go?groupId="+block.getGroupId();
	}
	
	/**
	 * Description:校验code是否存在
	 * @Version1.0 2014年11月26日 上午10:10:37 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping("checkCode")
	@ResponseBody
	public String checkCode(Model model,@RequestParam(value = "code") String code){
		AjaxResult result = new AjaxResult();
		Block dbBlock = blockService.findUniqueByParams("code",code);
		if(null!=dbBlock){
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
		}else {
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
		}
		return  JSON.toJSONString(result);
	}

	/**
	 * 
	 * Description: 更新块内容
	 * @Version1.0 2015年3月6日 上午10:33:36 by 吕翔  (lvxiang@dangdang.com) 创建
	 * Banner 内容结构 :
	 * { "banner": [
     *   {
     *       "title": "双十二特惠，1元专场",
     *       "coverPic": "http://img3.ddimg.cn/00467/ipad/shuang12-2048-500.jpg",
     *       "code": "nslj",
	 *					"type":"column",//
     *       "url": ""
     *   },
     *   .....
     *   }
	 * @param btitle 
	 * @param bicon
	 * @param btype
	 * @param bcode
	 * @param burl
	 * @param block
	 * @return
	 */
	@RequestMapping(value="/updatecontent")
	public String updateContent(final String[] btitle,final String[]bicon,final String[] btype,
				final String[]bcode,final String[]burl,final Block block){
		if(null != btitle  && btitle.length > 0){
				StringBuilder sbBanner = new StringBuilder();
				sbBanner.append("{\"banner\":[");
				int length = btitle.length;
				for(int i=0 ; i< length; i++ ){
					sbBanner.append("{");
					sbBanner.append("\"title\":\"").append(btitle[i]).append("\",");
					sbBanner.append("\"coverPic\":\"").append(bicon[i]).append("\",");
					sbBanner.append("\"type\":\"").append(btype[i]).append("\",");
					sbBanner.append("\"code\":\"").append(bcode[i]).append("\",");
					sbBanner.append("\"url\":\"").append(burl[i]).append("\"},");
				}	
				sbBanner.deleteCharAt(sbBanner.length()-1);
				sbBanner.append("]}");
				//显示
				//还需要验证下json格式是否正确
				block.setContent(sbBanner.toString());
		}else{
			//没有值.不能为空值,不然页面需要和xml需要作相应修改
			block.setContent("{}");
		}
		this.blockService.update(block);
		//更新
		updateCache(block);
		return "redirect:list.go?groupId="+block.getGroupId();
	}
	/**
	 * 更新缓存
	 * Description: 
	 * @Version1.0 2015年3月18日 下午5:22:14 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param block
	 */
	private void updateCache(Block block){
		try {
			cacheApi.deleteBlockCache(block.getCode());
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "删除推荐位缓存失败："+block.getMediaBlockId()+";名称"+block.getName(), UsercmsUtils.getCurrentUser().getLoginName());
		}
	}
	/**
	 * Description: 更新页面
	 * @Version1.0 2014年11月26日 上午11:42:43 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param block
	 * @return
	 * @throws MediaException
	 */
	@RequestMapping(value="/update")
	public String update(final Model model,Block block, @RequestParam(value="upfilename",required=false) MultipartFile file) throws MediaException{
		try {
			String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
			
			Block existBlock = blockService.get(block.getMediaBlockId());
			
			if(existBlock != null){
				existBlock.setLastModifiedDate(new Date());
				existBlock.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
				
				
				List<Column> columnList = columnService.findListByParams("isactiverForever", true);
				
				StringBuffer columnListOption = new StringBuffer();
				if(columnList != null && columnList.size() > 0){
					int i = 1;
					for(Column column : columnList){
						if(column.getParentId().intValue() == 0){
							if(StringUtils.isNotBlank(block.getRelationColumnCode()) && column.getColumnId().intValue() == Integer.parseInt(block.getRelationColumnCode().split("::")[0])){
								columnListOption.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" selected='selected'>"+column.getName()+"</option>");
							}else{
								columnListOption.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" >"+column.getName()+"</option>");
							}
							
							String relationColumnCode = block.getRelationColumnCode();
							Integer relationColumnCodeInteger = null; 
							if(StringUtils.isNotBlank(relationColumnCode)){
								relationColumnCodeInteger = Integer.parseInt(block.getRelationColumnCode().split("::")[0]);
							}
							columnListOption.append(recursionColumnList(columnList, column.getColumnId(), i,relationColumnCodeInteger));
						}
					}
				}
				model.addAttribute("columnListOption", columnListOption.toString());
				
				if(block.getType() == 0){
					try{
						JSON.parse(block.getContent());
					}catch(com.alibaba.fastjson.JSONException je){
						model.addAttribute("returnInfo", "<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>json格式错误！");
						model.addAttribute("successFlag", 1);
						model.addAttribute("one", block);
						return "/block/block_modify";
					}	
				}
				
				existBlock.setContent(block.getContent());
				
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
						existBlock.setPicPath(ConfigPropertieUtils.getString("media.resource.images.http.path") + File.separator + month+"/"+fileName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				String relationColumnCode = block.getRelationColumnCode();
				if(StringUtils.isNotBlank(relationColumnCode)){
					existBlock.setRelationColumnId(Integer.parseInt(relationColumnCode.split("::")[0]));
					existBlock.setRelationColumnCode(relationColumnCode.split("::")[1]);
				}else{
					existBlock.setRelationColumnId(null);
					existBlock.setRelationColumnCode("");
				}
				
				existBlock.setGroupId(block.getGroupId());
				existBlock.setCode(block.getCode());
				existBlock.setName(block.getName());
				existBlock.setStartDate(block.getStartDate());
				existBlock.setEndDate(block.getEndDate());
				existBlock.setStatus(block.getStatus());
				existBlock.setTargetUrl(block.getTargetUrl());
				existBlock.setType(block.getType());
				
				blockService.update(existBlock);
				try {
					cacheApi.deleteBlockCache(block.getCode());
				} catch (Exception e) {
					LogUtil.error(LOGGER, e, "删除推荐位缓存失败："+block.getMediaBlockId()+";名称"+block.getName(), UsercmsUtils.getCurrentUser().getLoginName());
				}
				
				model.addAttribute("showPageType", "1");
				model.addAttribute("block", existBlock);
				model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>恭喜，修改成功！<a href='/block/list.go?groupId="+block.getGroupId()+"'><img src='/images/show_list.jpg' style='padding-bottom: 0px;'></a>&nbsp;&nbsp;<a href='/block/detail.go?id="+block.getMediaBlockId()+"&flag=2'><img src='/images/repeat_modify.jpg' style='padding-bottom: 0px;'></a>");
				model.addAttribute("successFlag", 0);
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, e, "更新块信息异常，更新id："+block.getMediaBlockId()+";名称"+block.getName(), UsercmsUtils.getCurrentUser().getLoginName());
		}
		return "/block/block_modify";
	}
	
	/**
	 * Description:详情页 
	 * @Version1.0 2014年11月26日 上午11:42:35 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(Model model,Long id,int flag){
		String errorMsg = "";
		Block block = new Block();
		block = blockService.get(id);
		if(null == block){
			errorMsg += "[该块信息不存在！]";
		}else {
			model.addAttribute("block", block);
		}
		if(StringUtils.isNotBlank(errorMsg)){
			model.addAttribute("errorMsg", errorMsg);
		}
		
		List<Column> columnList = columnService.findListByParams("isactiverForever", true);
		
		StringBuffer columnListOption = new StringBuffer();
		if(columnList != null && columnList.size() > 0){
			int i = 1;
			for(Column column : columnList){
				if(column.getParentId().intValue() == 0){
					if(block.getRelationColumnId() != null && column.getColumnId().intValue() == block.getRelationColumnId().intValue()){
						columnListOption.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" selected='selected'>"+column.getName()+"</option>");
					}else{
						columnListOption.append("<option value="+column.getColumnId()+"::"+column.getColumnCode()+" >"+column.getName()+"</option>");
					}
					
					columnListOption.append(recursionColumnList(columnList, column.getColumnId(), i, block.getRelationColumnId()));
				}
			}
		}
		model.addAttribute("columnListOption", columnListOption.toString());
		model.addAttribute("showPageType",flag);
		if(flag == 1){
			return "block/block_detail";
		}else if(flag == 2){
			return "block/block_modify";
		}else{
			return "block/block_detail";
		}
	}
	
	/**
	 * 
	 * Description: 编辑块内容信息
	 * @Version1.0 2015年3月5日 下午2:54:06 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param id	块编号
	 * @param model
	 * @return	
	 */
	@RequestMapping("editcontent")
	public String editBlockContent(final Long id, final Model model){
		Block block = blockService.get(id);
		String content = block.getContent();
		if(null != content && !content.trim().isEmpty()){
			
		}
		model.addAttribute("block", block);
		return "block/editcontent";
	}
	
	/**
	 * Description:批量删除 
	 * @Version1.0 2014年11月26日 上午10:10:37 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteMore")
	@ResponseBody
	public String deleteMore(Model model,@RequestParam(value = "ids[]") List<Long> ids){
		AjaxResult result = new AjaxResult();
		blockService.deleteByIds(ids);
		LogUtil.info(LOGGER,"批量删除【块信息】", UsercmsUtils.getCurrentUser().getLoginName(), ids.toString());
		result.setResult("0");
		return  JSON.toJSONString(result);
	}
}