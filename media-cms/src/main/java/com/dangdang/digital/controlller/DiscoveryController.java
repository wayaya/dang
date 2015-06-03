package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
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
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IDiscoveryService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;


@Controller
@RequestMapping("discovery")
public class DiscoveryController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryController.class);
	
	private static final int TYPE_DISCOVER = 1;
	
	private static final int TYPE_BOUTIQUE_GOOD_MEDIA = 2;
	
	@Resource
	private IDiscoveryService discoveryService;
	
	@Resource
	private ICatetoryService catetoryService;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IMediaService mediaService;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final Discovery dis){
		
		PageFinder<Discovery> pageFinder = discoveryService.findPageFinderObjs(dis,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("dis", dis);
		int type = Integer.valueOf(getRequest().getParameter("type"));
		model.addAttribute("type", type);
		return "discovery/list";
	}
	
	@RequestMapping(value="/toAddOrEdit")
	public String toAddOrEdit(Discovery dis,final Model model) throws IOException{
		if(dis.getId() != null){
			dis = discoveryService.get(dis.getId());
			File f = new File(ConfigPropertieUtils.getString("media.resource.discover.content.path")+File.separator+
					dis.getContent());
			if(f.exists()){
				String content = FileUtils.readFileToString(f);
				dis.setContent(content.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", ""));
			}
			model.addAttribute("dis", dis);
			model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.discover.http.path")+"/");
		}
		return dis.getType().intValue() == TYPE_DISCOVER ? "discovery/info" : "goodarticle/info";
	}
	
	@RequestMapping(value="/del")
	public String del(Discovery dis){
		try {
			if(dis.getId() != null){
				discoveryService.deleteById(dis.getId());
				cacheApi.deleteDiscoveryFromCache(dis.getId());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER,e, "删除发现数据出错", null);
		}
		return "redirect:list.go?type="+dis.getType();
	}
	
	@RequestMapping(value="/toSetTime")
	public String toSetTime(Discovery dis,final Model model){
		if(dis.getId() != null){
			dis = discoveryService.get(dis.getId());
			model.addAttribute("dis", dis);
		}
		return "discovery/settime";
	}
	
	@RequestMapping(value="/toSetStars")
	public String toSetStars(Discovery dis,final Model model){
		if(dis.getId() != null){
			dis = discoveryService.get(dis.getId());
			model.addAttribute("dis", dis);
		}
		return "discovery/setStars";
	}
	
	@RequestMapping(value="/setStars")
	public String setStars(Discovery dis) throws ParseException{
		try {
			if(dis.getId() != null){
				discoveryService.update(dis);
				cacheApi.setDiscoveryCache(dis);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER,e, "对发现数据推荐评星出错", null);
		}
		return "redirect:list.go?type=2";
	}
	@RequestMapping(value="/setTime")
	public String setTime(Discovery dis) throws ParseException{
		try {
			if(dis.getId() != null){
				Date start = null;
				String startDate = getRequest().getParameter("startDate");
				if(StringUtils.isNotEmpty(startDate)){
					start = DateUtil.parseStringToDate(startDate);
				}
				dis.setShowStartDate(start);
				discoveryService.update(dis);
				cacheApi.setDiscoveryCache(discoveryService.get(dis.getId()));
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER,e, "对发现数据设置有效时间出错", null);
		}
		return "redirect:list.go?type="+dis.getType();
	}
	
	@RequestMapping(value="/getMediaById")
	@ResponseBody
	public String getMediaById(Discovery dis){
		Media media = mediaService.get(dis.getMediaId());
		if(media==null){
			return "{\"success\":false}";
		}else{
			return "{\"success\":true}";
		}
	}
	@RequestMapping(value="/update")
	public String saveOrUpdate(Discovery dis,final Model model,
			@RequestParam("pic1") MultipartFile pic1
			,@RequestParam("pic2") MultipartFile pic2,@RequestParam("pic3") MultipartFile pic3) throws IOException{
		
		try {
			dis.setOperator(UsercmsUtils.getCurrentUser().getLoginName());
			String showStartDateStr = getRequest().getParameter("showStartDateStr");
			if(StringUtils.isNotBlank(showStartDateStr)){
				dis.setShowStartDate(DateUtil.parseStringToDate(showStartDateStr));
			}
			if(dis.getMediaId() != null){
				Media media  = mediaService.get(dis.getMediaId());
				dis.setMediaName(media.getTitle());
				//dis.setAuthor(media.getAuthorPenname());
			}
			if(dis.getSecondCatetoryId() != null){
				Catetory catetory = catetoryService.get(dis.getSecondCatetoryId());
				Catetory parent = catetoryService.get(catetory.getParentId());
				dis.setFirstCatetoryId(parent.getId());
				dis.setFirstCatetoryName(parent.getName());
			}
			String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
			
			//保存内容到本地
			File contentFile = new File(ConfigPropertieUtils.getString("media.resource.discover.content.path")+File.separator+
					month);
			String fileNameContent = UUID.randomUUID().toString().replaceAll("-", "")+".html";
			contentFile.mkdirs();
			contentFile = new File(contentFile,fileNameContent);
			if(!contentFile.exists()){
				contentFile.createNewFile();
			}else{
				contentFile.delete();
			}
			FileUtils.writeStringToFile(contentFile, dis.getContent().replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", ""));
			dis.setHtmlContent(dis.getHtmlContent());
			dis.setContent(month+"/"+fileNameContent);
			
			//文字模板
			if(dis.getCardType().intValue() == 0){
				dis.setPic1Path(null);
				dis.setPic2Path(null);
				dis.setPic3Path(null);
			}
			if(dis.getCardType().intValue() == 1 || dis.getCardType().intValue() ==2){
				dis.setPic2Path(null);
				dis.setPic3Path(null);
				if(pic1!=null && pic1.getSize()>0){
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
					File f = new File(ConfigPropertieUtils.getString("media.resource.discover.img.path")+File.separator+
							month);
					f.mkdirs();
					f = new File(f,fileName);
					if(!f.exists()){
						f.createNewFile();
					}else{
						f.delete();
					}
					FileCopyUtils.copy(pic1.getInputStream(), new FileOutputStream(f));
					dis.setPic1Path("/"+month+"/"+fileName);
				}
			}
			if(dis.getCardType().intValue() == 2  && dis.getType().intValue() != 2){
				dis.setCardRemark(null);
				
				String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
				File f = new File(ConfigPropertieUtils.getString("media.resource.discover.img.path")+File.separator+
						month);
				f.mkdirs();
				if(pic2!=null && pic2.getSize() >0){
					f = new File(f,fileName);
					if(!f.exists()){
						f.createNewFile();
					}else{
						f.delete();
					}
					FileCopyUtils.copy(pic2.getInputStream(), new FileOutputStream(f));
					dis.setPic2Path("/"+month+"/"+fileName);
				}
				if(pic3!=null && pic3.getSize()>0){
					String fileName3 = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
					
					File f3 = new File(new File(ConfigPropertieUtils.getString("media.resource.discover.img.path")+File.separator+
							month),fileName3);
					if(!f3.exists()){
						f3.createNewFile();
					}else{
						f3.delete();
					}
					FileCopyUtils.copy(pic3.getInputStream(), new FileOutputStream(f3));
					dis.setPic3Path("/"+month+"/"+fileName3);
				}
			}
			
			
			if(dis.getId() !=null){
				discoveryService.update(dis);
			}else{
				discoveryService.save(dis);
			}
			cacheApi.setDiscoveryCache(dis);
		} catch (Exception e) {
			LogUtil.error(LOGGER,e, "保存修改发现数据出错,discoverId={0}", dis.getId());
		}
		
		return "redirect:list.go?type="+dis.getType();
	}
	@RequestMapping(value="/uploadImg")
	@ResponseBody
	public void uploadImg(@RequestParam("upfile") MultipartFile file) throws IOException{
		String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
		String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
		File f = new File(ConfigPropertieUtils.getString("media.resource.discover.img.path")+File.separator+
				month);
		f.mkdirs();
		f = new File(f,fileName);
		if(!f.exists()){
			f.createNewFile();
		}
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
		Map<String,String> map = new HashMap<String, String>();
		map.put("state", "SUCCESS");
		map.put("title", fileName);
		map.put("type", ".jpg");
		map.put("original", file.getOriginalFilename());
		map.put("url", ConfigPropertieUtils.getString("media.resource.discover.http.path")+"/"+month+"/"+fileName);
		map.put("size", file.getSize()+"");
		getResponse().setHeader("Content-Type" , "text/html");
		getResponse().getWriter().write(JSONObject.toJSONString(map));
		getResponse().getWriter().flush();
	}
	/**
	 * 
	 * Description: 修改置顶排序值
	 * @Version1.0 2015年3月13日 上午12:00:33 by 杨振平  (yangzhenping@dangdang.com) 创建
	 * @param listId			记录Id集合
	 * @param topOrder		记录Id对应排序值集合
	 * @param orederDimension	
	 * @return
	 */
	@RequestMapping(value="/updateOrder")
	public String updateOrder(Long[] listId, Integer[] topOrder){
		List<Discovery> discoveries = new ArrayList<Discovery>(listId.length);
		for(int i=0,len = listId.length;i<len;i++){
			if(null!=topOrder[i]){
				Discovery discovery = new Discovery();
				discovery.setId(listId[i]);
				discovery.setTopOrder(topOrder[i]);//置顶排序值			
				discovery.setOperator(UsercmsUtils.getCurrentUser().getLoginName());
				discoveries.add(discovery);
			}			
		}
		if(discoveries.size()>0){
			discoveryService.updateListDiscovery(discoveries);
		}
		return "redirect:list.go?type="+1;
		
	}
}
