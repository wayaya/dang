package com.dangdang.digital.controlller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.GoodArticleSign;
import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IGoodArticleSignService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

@Controller
@RequestMapping("goodArticleSign")
public class GoodArticleSignController extends BaseController{
	@Resource IGoodArticleSignService goodArticleSignService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodArticleSignController.class);
	
	@RequestMapping("add")
	public String add(GoodArticleSign sign) throws IOException{
		try {
			goodArticleSignService.insertOrUpdate(sign);
			LogUtil.info(LOGGER,"管理员:{0}在操作图书分类【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					sign.getName());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在操作图书分类【{1}】失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					sign.getName());
		}
		return "redirect:main.go";
	}
	@RequestMapping("tree")
	@ResponseBody
	public void tree(GoodArticleSign sign) throws IOException{
		StringBuffer msg = new StringBuffer("[{\"text\":\"标签\",\"id\":\"0\",\"children\":[");
		List<GoodArticleSign> list = goodArticleSignService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		toJson(msg);
	}
	
	private void getTree(List<GoodArticleSign> list,StringBuffer msg){
		String cateIds = getRequest().getParameter("cateIds");
		Set<String> set = new HashSet<String>();
		if(cateIds!=null && !"".equals(cateIds)){
			String arr[] = cateIds.split(",");
			for(String id :arr)
				set.add(id);
		}
		for(GoodArticleSign cate : list){
			msg.append("{\"name\":\""+cate.getName()+"\",");
			msg.append("\"id\":\""+cate.getId()+"\",");
			msg.append("\"code\":\""+cate.getCode()+"\",");
			msg.append("\"parentId\":\""+cate.getParentId()+"\",");
			
			List<GoodArticleSign> treeList = goodArticleSignService.getTreeByParentId(cate);
			if(treeList.size() > 0){
				msg.append("\"isParent\":true,\"nocheck\":true,");
			}else{
				msg.append("\"isParent\":false,");
				if(set.contains(cate.getId().toString())){
					msg.append("\"checked\":true,");
				}
			}
			if(treeList.size() > 0){
				msg.append("\"children\":[");
				getTree(treeList,msg);
				msg.append("],");
			}
			msg.deleteCharAt(msg.length() - 1);
			msg.append("},");
		}
		msg.deleteCharAt(msg.length() - 1);
	}
	@RequestMapping("main")
	public String main(final Model model){
		StringBuffer msg = new StringBuffer("[{\"name\":\"标签\",\"open\":true,\"id\":\"0\",\"children\":[");
		List<GoodArticleSign> list = goodArticleSignService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		model.addAttribute("data", msg  );
		return "goodArticleSign/list";
	}
	
	@RequestMapping("getCatetory")
	@ResponseBody
	public void getCatetory(final Model model) throws IOException{
		StringBuffer msg = new StringBuffer("[{\"name\":\"分类\",\"open\":true,\"id\":\"0\",\"children\":[");
		List<GoodArticleSign> list = goodArticleSignService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		toJson(msg);
	}
	
	@RequestMapping("getSelSign")
	public String getSelSign(final Model model) throws IOException{
		StringBuffer msg = new StringBuffer("[{\"name\":\"分类\",\"open\":true,\"nocheck\":true,\"id\":\"0\",\"children\":[");
		List<GoodArticleSign> list = goodArticleSignService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		model.addAttribute("data", msg.toString());
		return "media/selCatetory";
	}
	@RequestMapping(value="/toEdit")
	public String toEdit(@RequestParam final int id,final Model model){
		GoodArticleSign cate = this.goodArticleSignService.get(id);
		model.addAttribute("cate", cate);
		return "goodArticleSign/modify";
	}
	
	@RequestMapping(value="/toAdd")
	public String toAdd(Style style,final Model model){
		String parentId = getRequest().getParameter("parentId");
		if(!"0".equals(parentId)){
			model.addAttribute("parentId", parentId);
		}
		return "goodArticleSign/add";
	}
	
	
	@RequestMapping("del")
	public String del(GoodArticleSign sign) throws IOException{
		try {
			goodArticleSignService.delete(sign.getId());
			LogUtil.info(LOGGER,"管理员:{0}在删除图书分类【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					sign.getName());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在删除图书分类【{1}】失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					sign.getName());
		}
		return "redirect:main.go";
	}
	
	@RequestMapping("list")
	public String list(Model model, Query query,GoodArticleSign sign){
		Map<String, String> paramMap = new HashMap<String, String>();
		if(sign.getId() != null && sign.getId().intValue() != 0){
			paramMap.put("id", sign.getId().toString());
		}
		if(sign.getParentId() != null){
			paramMap.put("parentId", sign.getParentId().toString());
		}
		
		
		PageFinder<GoodArticleSign> pageFinder = goodArticleSignService.findPageFinderObjs(paramMap, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "goodArticleSign/dataList";
	}
	
}