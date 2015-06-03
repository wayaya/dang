package com.dangdang.digital.controlller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.alibaba.fastjson.JSONArray;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ConstantEnum;
import com.dangdang.digital.constant.OperateTargetTypeEnum;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IManagerOperateLogService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

@Controller
@RequestMapping("catetory")
public class CatetoryController extends BaseController{
	@Resource ICatetoryService catetoryService;
	@Resource ICacheApi  cacheApi;
	private static final Logger LOGGER = LoggerFactory.getLogger(CatetoryController.class);
	
	@Resource
	private IManagerOperateLogService managerOperateLogService;
	@RequestMapping("saveOrder")
	@ResponseBody
	public void saveOrder() throws IOException{
		String order = getRequest().getParameter("order");
		try {
			JSONArray arr = JSONArray.parseArray(order);
			if(arr.size() > 0){
				catetoryService.saveOrder(arr);
				//清除分类一级的缓存,排序有变化
				cacheApi.cleanCacheByKey(Constans.MEDIA_CATEGORY_CACHE_KEY+ConstantEnum.CHANNEL_NP.getCode());
				cacheApi.cleanCacheByKey(Constans.MEDIA_CATEGORY_CACHE_KEY+ConstantEnum.CHANNEL_VP.getCode());
			}
		LogUtil.info(LOGGER,"管理员:{0}在操作图书分类【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
				order);
		toJson("{\"success\":true}");
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在操作图书分类【{1}】失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					order);
			toJson("{\"success\":false,msg:\"保存排序失败!\"}");
		}
	}
	@RequestMapping("add")
	public String add(Catetory catetory, @RequestParam(value = "imagefile", required = false) MultipartFile file,
			final Model model) throws IOException {
		try {
		    if (file != null && file.getSize() > 0) {
				// 使用栏目图标路径
				File rootDir = new File(ConfigPropertieUtils.getString("media.resource.images.column.path"));
				String path = "catetory_" + catetory.getCode() + ".jpg";
				try {
					if (!rootDir.exists()) {
						rootDir.mkdirs();
					}
					File imagefile = new File(rootDir, path);
					if (!imagefile.exists()) {
						imagefile.createNewFile();
					}
					FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(imagefile));
					catetory.setImage(path);
				} catch (Exception e) {
					LOGGER.error("保存图片出错", e);
				}
			}
			List<Catetory> list = catetoryService.findListByParams("code",catetory.getCode());
			List<Catetory> listName = catetoryService.findListByParams("name",catetory.getName());
			boolean checkSuccess = true;
			String msg = "";
			if((catetory.getId() != null && list.size() == 1 && !list.get(0).getId().equals(catetory.getId()))
					|| (catetory.getId()==null && list.size()==1)){
				msg+="编码重复；";
				checkSuccess = false;
			}
			if((catetory.getId() != null && listName.size() == 1 && !listName.get(0).getId().equals(catetory.getId()))
					|| (catetory.getId()==null && listName.size()==1)){
				msg+="名称重复；";
				checkSuccess = false;
			}
			if(!checkSuccess){
				model.addAttribute("cate", catetory);
				model.addAttribute("msg", msg);
				if(catetory.getId() == null){
					if(catetory.getParentId() != null){
						model.addAttribute("parentId",catetory.getParentId().toString());
					}
					return "catetory/add";
				}else{
					return "catetory/modify";
				}
			}
			catetoryService.insertOrUpdate(catetory);
			// 清楚缓存
			cacheApi.cleanCacheByKey(Constans.MEDIA_CATEGORY_CACHE_KEY + catetory.getCode().toLowerCase());
			LogUtil.info(LOGGER,"管理员:{0}在操作图书分类【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					catetory.getName());
			// 插入操作日志
			managerOperateLogService
					.insertOperateLog(new ManagerOperateLog(
							null,
							Constans.MANAGER_OPERATE_RESULT_SUCCESS,
							OperateTargetTypeEnum.CATETORY.getType(),
							catetory.getId().longValue(),
							UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
									.getCurrentUser().getLoginName()
									: "system", new Date(), "，修改前内容："
											+ JSON.toJSONString(catetory)
											+ "，修改后内容："
											+ JSON.toJSONString(catetory)));
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在操作图书分类【{1}】失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					catetory.getName());
		}
		//		return "redirect:main.go";
//		StringBuffer param = new StringBuffer("");
//		if(catetory.getId() != null){
//			param.append("?id=").append(catetory.getId()).append("&name=").append(catetory.getName());
//		}
		return "redirect:main.go?id="+catetory.getId();
		//return "redirect:list.go"+param.toString();
	}
	@RequestMapping("tree")
	@ResponseBody
	public void tree(Catetory catetory) throws IOException{
		StringBuffer msg = new StringBuffer("[{\"text\":\"分类\",\"id\":\"0\",\"children\":[");
		List<Catetory> list = catetoryService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		toJson(msg);
	}
	
	private void getTree(List<Catetory> list,StringBuffer msg){
		String cateIds = getRequest().getParameter("cateIds");
		Set<String> set = new HashSet<String>();
		if(cateIds!=null && !"".equals(cateIds)){
			String arr[] = cateIds.split(",");
			for(String id :arr)
				set.add(id);
		}
		for(Catetory cate : list){
			msg.append("{\"name\":\""+cate.getName()+"\",");
			msg.append("\"id\":\""+cate.getId()+"\",");
			msg.append("\"code\":\""+cate.getCode()+"\",");
			msg.append("\"parentId\":\""+cate.getParentId()+"\",");
			
			List<Catetory> treeList = catetoryService.getTreeByParentId(cate);
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
		StringBuffer msg = new StringBuffer("[{\"name\":\"分类\",\"open\":true,\"id\":\"0\",\"children\":[");
		List<Catetory> list = catetoryService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		String id = getRequest().getParameter("id");
		if(StringUtils.isNotBlank(id)){
			model.addAttribute("cateId", id);
		}
		model.addAttribute("data", msg  );
		return "catetory/list";
	}
	
	@RequestMapping("getCatetory")
	@ResponseBody
	public void getCatetory(final Model model,Catetory catetory) throws IOException{
		StringBuffer msg = new StringBuffer("[{\"name\":\"分类\",\"open\":true,\"id\":\"0\",\"children\":[");
		List<Catetory> list = catetoryService.getTreeByParentId(catetory);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		toJson(msg);
	}
	
	@RequestMapping("getSelCatetory")
	public String getSelCatetory(final Model model) throws IOException{
		StringBuffer msg = new StringBuffer("[{\"name\":\"分类\",\"open\":true,\"nocheck\":true,\"id\":\"0\",\"children\":[");
		List<Catetory> list = catetoryService.getTreeByParentId(null);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		model.addAttribute("data", msg.toString());
		return "media/selCatetory";
	}
	@RequestMapping(value="/toEdit")
	public String toEdit(@RequestParam final int id,final Model model){
		Catetory cate = this.catetoryService.get(id);
		model.addAttribute("cate", cate);
		model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.images.column.http.path"));
		return "catetory/modify";
	}
	
	@RequestMapping(value="/toAdd")
	public String toAdd(Style style,final Model model){
		String parentId = getRequest().getParameter("parentId");
		if(!"0".equals(parentId)){
			model.addAttribute("parentId", parentId);
		}
		return "catetory/add";
	}
	
	
	@RequestMapping("del")
	public String del(Catetory catetory) throws IOException{
		try {
			catetoryService.delete(catetory.getId());
			LogUtil.info(LOGGER,"管理员:{0}在删除图书分类【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					catetory.getName());
					managerOperateLogService
			.insertOperateLog(new ManagerOperateLog(
					null,
					Constans.MANAGER_OPERATE_RESULT_SUCCESS,
					OperateTargetTypeEnum.CATETORY.getType(),
					catetory.getId().longValue(),
					UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
							.getCurrentUser().getLoginName()
							: "system", new Date(), "，删除前内容："
									+ JSON.toJSONString(catetory)
									));
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在删除图书分类【{1}】失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					catetory.getName());
		}
		return "redirect:main.go";
	}
	
	@RequestMapping("list")
	public String list(Model model, Query query,Catetory catetory){
		Map<String, String> paramMap = new HashMap<String, String>();
		if(catetory.getId() != null && catetory.getId().intValue() != 0){
			paramMap.put("id", catetory.getId().toString());
			model.addAttribute("id", catetory.getId().toString());
		}
		if(catetory.getParentId() != null){
			paramMap.put("parentId", catetory.getParentId().toString());
			model.addAttribute("parentId", catetory.getParentId().toString());
		}
		
		PageFinder<Catetory> pageFinder = catetoryService.findPageFinderObjs(paramMap, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "catetory/dataList";
	}
	@RequestMapping("getMediaCountById")
	@ResponseBody
	public String getMediaCountById(Catetory catetory){
		Integer count = catetoryService.getMediaCountByCatetoryId(catetory);
		return "{\"count\":"+count+"}";
	}
	
}