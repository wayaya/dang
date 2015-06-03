package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.MediaSaleEnum;
import com.dangdang.digital.constant.OperateTargetTypeEnum;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.IllegalMedia;
import com.dangdang.digital.model.Label;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.Volume;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IIllegalMediaService;
import com.dangdang.digital.service.ILabelService;
import com.dangdang.digital.service.IManagerOperateLogService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.ISysPropertiesService;
import com.dangdang.digital.service.IVolumeService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.Hanzi_Pinyin;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.ScaleCoverImg;
import com.dangdang.digital.utils.UploadPicToCDN;
import com.dangdang.digital.utils.UsercmsUtils;
import com.ibm.icu.util.Calendar;


@Controller
@RequestMapping("media")
public class MediaController extends BaseController {

	@Resource(name="mediaService")
	private IMediaService mediaService;
	@Resource
	private ICatetoryService catetoryService;
	@Resource
	private ILabelService labelService;
	@Resource
	private IVolumeService volumeService;
	@Resource
	private IChapterService chapterService;
	
	@Resource
	private IMediaSaleService mediaSaleService;
	@Resource
	private ISysPropertiesService service;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IManagerOperateLogService managerOperateLogService;
	@Resource
	private IIllegalMediaService illegalMediaService;
	
	private final String MEDIA_FULL_DEFAULT_PRICE_KEY = "media.full.default.price";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final Media media){
		String parentId = getRequest().getParameter("parentId");
		if(parentId!=null && !"".equals(parentId)){
			Catetory cate = catetoryService.get(Integer.valueOf(parentId));
			List<Catetory> list = catetoryService.findListByParams("path",cate.getPath());
			StringBuffer sb = new StringBuffer();
			for(Catetory ca : list){
				sb.append(ca.getId()).append(",");
			}
			if(list.size() > 0){
				sb.deleteCharAt(sb.length()-1);
			}
			media.setCatetoryIds(sb.toString());
		}
		
		PageFinder<Media> pageFinder = mediaService.findPageFinderObjs(media,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("media", media);
		model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.http.path"));
		model.addAttribute("time",System.currentTimeMillis());
		model.addAttribute("parentId",parentId);
		return "media/list";
	}
	
	@RequestMapping(value="/delete")
	public String del(@RequestParam final Long id){
		try {
			this.mediaService.deleteById(id);
			LogUtil.info(LOGGER,"管理员:{0}在删除图书【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					id);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在删除图书【{1}】时出错", e,UsercmsUtils.getCurrentUser().getLoginName(),
					id);
		}
		return "redirect:list.go";
	}
	
	
	@RequestMapping(value="/toEdit")
	public String toEdit(@RequestParam final Long id,final Model model){
		Media media = this.mediaService.get(id);
		model.addAttribute("media", media);
		return "media/modify";
	}
	
	@RequestMapping("main")
	public String main(final Model model){
		return "media/media";
	}
	
	@RequestMapping("catetorySign")
	public String catetorySign(final Model model,Media media){
		media = this.mediaService.get(media.getMediaId());
		model.addAttribute("media", media);
		StringBuffer cateIds = new StringBuffer();
		StringBuffer cateNames = new StringBuffer();
		List<Catetory> list = catetoryService.getCatetoryByMediaId(media.getMediaId());
		for(Catetory cate : list){
			cateIds.append(cate.getId()).append(",");
			cateNames.append(cate.getName()).append(",");
		}
		if(list.size() > 0){
			cateIds.deleteCharAt(cateIds.length() - 1);
			cateNames.deleteCharAt(cateNames.length() - 1);
		}
		model.addAttribute("cateIds", cateIds);
		model.addAttribute("cateNames", cateNames);
		model.addAttribute("signIds", media.getSignIds());
		model.addAttribute("signNames", media.getSignNames());
		return "media/catetorysign";
	}
	
	
	@RequestMapping(value="/update")
	public String update(Media media,@RequestParam(value="cover",required=false) MultipartFile file
			,final Model model,String redirect,String dsDesc) throws FileNotFoundException, IOException{
		Media m = mediaService.get(media.getMediaId());
		if(StringUtils.isNotBlank(dsDesc)){
			dsDesc = "，下架原因：" + dsDesc;
		}else{
			dsDesc = "";
		}
		try {
			if(file!=null && file.getSize() > 0){				
				String path = "";
				String uid = m.getUid();
				if(m.getCoverPic() == null || "".equals(m.getCoverPic())){
					path = File.separator+uid.substring(uid.length()-4,uid.length()-2)+
							File.separator+uid.substring(uid.length()-2)+File.separator+uid+File.separator+uid+".jpg";
				}else{
					path = m.getCoverPic();
				}
				File f = new File(ConfigPropertieUtils.getString("media.resource.root.path")+File.separator+uid.substring(uid.length()-4,uid.length()-2)+
						File.separator+uid.substring(uid.length()-2)+File.separator+uid+File.separator);
				f.mkdirs();
				f = new File(f,uid+".jpg");
				if(!f.exists()){
					f.createNewFile();
				}
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
				m.setCoverPic(path);
				ScaleCoverImg sci = new ScaleCoverImg(f, media.getMediaId());
	    		UploadPicToCDN.upload(media.getMediaId(), sci.scaleCoverPic());
			}
			media.setLastModifyDate(Calendar.getInstance().getTime());
			media.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
			mediaService.update(media);
			// 插入操作日志
			managerOperateLogService
					.insertOperateLog(new ManagerOperateLog(
							null,
							Constans.MANAGER_OPERATE_RESULT_SUCCESS,
							OperateTargetTypeEnum.MEDIA.getType(),
							media.getMediaId(),
							UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
									.getCurrentUser().getLoginName()
									: "system", new Date(), "修改作品，修改前内容："
											+ JSON.toJSONString(m)
											+ "，修改后内容："
											+ JSON.toJSONString(media) + dsDesc));
			cacheApi.setMediaCache(media);
			LogUtil.info(LOGGER,"管理员:{0}在修改图书【{1}】基本信息", UsercmsUtils.getCurrentUser().getLoginName(),
					media.getMediaId());
			model.addAttribute("msg", "操作成功!");
			model.addAttribute("successFlag", 0);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(LOGGER,"管理员:{0}在修改图书【{1}】基本信息失败",e, UsercmsUtils.getCurrentUser().getLoginName(),media.getMediaId());
			// 插入操作日志
			managerOperateLogService
			.insertOperateLog(new ManagerOperateLog(
					null,
					Constans.MANAGER_OPERATE_RESULT_FAIL,
					OperateTargetTypeEnum.MEDIA.getType(),
					media.getMediaId(),
					UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
							.getCurrentUser().getLoginName()
							: "system", new Date(), "修改作品，修改前内容："
									+ JSON.toJSONString(m)
									+ "，修改后内容："
									+ JSON.toJSONString(media) + dsDesc));
			model.addAttribute("msg", "操作失败!");
			model.addAttribute("successFlag", 1);
		}
		
		media = mediaService.get(media.getMediaId()); 
		model.addAttribute("media", media);
		model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.http.path"));
		model.addAttribute("time",System.currentTimeMillis());
		if(StringUtils.equals(redirect, "1")){
			return "redirect:list.go";
		}else{
			return "media/baseInfo";
		}
		
	}
	
	@RequestMapping(value="/toSelectCate")
	public String toSelectCate(final Model model){
		model.addAttribute("cateIds", getRequest().getParameter("cateIds"));
		model.addAttribute("cateNames", getRequest().getParameter("cateNames"));
		return "media/selCatetory";
	}
	@RequestMapping(value="/toBaseInfo")
	public String toBaseInfo(Media media,final Model model){
		media = mediaService.get(media.getMediaId()); 
		model.addAttribute("media", media);
		model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.http.path"));
		model.addAttribute("time",System.currentTimeMillis());
		return "media/baseInfo";
	}
	
	@RequestMapping(value="/getCover")
	public String getCover(Media media){
		media = mediaService.get(media.getMediaId());
		return "redirect:"+ConfigPropertieUtils.getString("media.resource.http.path")+media.getCoverPic();
	}
	
	@ResponseBody
	@RequestMapping(value="/saveCateSign")
	public void saveCateSign(Media media){
		String cateIds = getRequest().getParameter("cateIds");
		try {
			mediaService.saveCateSign(media, cateIds);
			LogUtil.info(LOGGER,"管理员:{0}在修改图书【{1}】类别和标签信息", UsercmsUtils.getCurrentUser().getLoginName(),
					media.getMediaId());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在修改图书【{1}】类别{2}和标签{3}信息",e, UsercmsUtils.getCurrentUser().getLoginName(),
					media.getMediaId(),cateIds,media.getSignIds());
		}
	}
	@RequestMapping(value="/toSelLabel")
	public String toSelLabel(final Model model){
		model.addAttribute("id", getRequest().getParameter("id"));
		model.addAttribute("name", getRequest().getParameter("name"));
		return "media/selLabel";
	}
	@RequestMapping(value="/toSetIsFull")
	public String toSetIsFull(@RequestParam(value="mediaId") Long mediaId,final Model model){
		Media media = mediaService.get(mediaId);
		model.addAttribute("media", media);
		String price = service.getValue(MEDIA_FULL_DEFAULT_PRICE_KEY);
		model.addAttribute("price", price);
		return "media/setIsFull";
	}
	@RequestMapping(value="/setIsFull")
	public String setIsFull(Media media){
		
		return "redirect:main.go";
	}
	@RequestMapping(value="/getLabel")
	public void getLabel() throws IOException{
		String chk = getRequest().getParameter("chk");
		Map map = new HashMap();
		if(chk!=null && !"".equals(chk)){
			String chkArr[] = chk.split(",");
			for(String str : chkArr){
				map.put(str, "exist");
			}
		}
		Label label = new Label();
		label.setType(0);
		label.setIsenabled(true);
		Query query = new Query();
		query.setPage(1);
		query.setPageSize(Integer.MAX_VALUE);
		
		PageFinder<Label> pageFinder = labelService.findPageFinderObjs(label,query);
		
		StringBuffer sBuff = new StringBuffer();

		List<Label> list = pageFinder.getData();
		sBuff.append("<info><peoples dbId=\"-1\" leaf=\"false\" checked=\"false\" text=\""
				+ ("全部") + "\">");
		
		for (Label lab : list) {
			sBuff.append("<name dbId=\""
						+ lab.getLableId()
						+ "\" checked=\""
						+ map.containsKey(lab.getLableId().toString())
						+ "\" py=\""
						+ (Hanzi_Pinyin
								.getHeaderByChar(lab.getName(), true))
						+ "\" text=\""
						+ (lab.getName())
						+ "\" leaf=\"true\"/>");
		}

		sBuff.append("</peoples></info>");
		toJson(sBuff);
		
	}
	
	@RequestMapping(value="/toChapterVolume")
	public String toChapterVolume(Media media ,final Model model) throws IOException{
		Query query = new Query();
		query.setPage(1);
		query.setPageSize(Integer.MAX_VALUE);
		
		Volume v = new Volume();
		v.setMediaId(media.getMediaId());
		media = mediaService.get(media.getMediaId());
		model.addAttribute("media", media);
		PageFinder<Volume> pageFinder = volumeService.findPageFinderObjs(v,query);
		if(pageFinder != null && pageFinder.getData().size() > 0){
			Chapter chapterAll = new Chapter();
			chapterAll.setMediaId(media.getMediaId());
			
			PageFinder<Chapter> page = chapterService.findPageFinderObjs(chapterAll,query);
			List<Chapter> noVolumeList = new ArrayList<Chapter>(); 
			for(Chapter c : page.getData()){
				if(c.getVolumeId() == null){
					noVolumeList.add(c);
					
				}
			}
			Volume vol = new Volume();
			
			if(noVolumeList.size()>0){
				vol.setList(noVolumeList);
				vol.setMaxIndex(noVolumeList.get(0).getIndex());
			}
			
			List<Volume> list = pageFinder.getData();
			for(Volume volume : list){
				Chapter chapter = new Chapter();
				chapter.setMediaId(media.getMediaId());
				chapter.setVolumeId(volume.getVolumeId());
				PageFinder<Chapter> pageFinderVo = chapterService.findPageFinderObjs(chapter,query);
				volume.setList(pageFinderVo.getData());
				if(pageFinderVo.getData().size() > 0){
					List<Chapter> lists = pageFinderVo.getData();
					volume.setMaxIndex(lists.get(lists.size()-1).getIndex());
				}else{
					volume.setMaxIndex(0);
				}
			}
			if(vol.getList()!=null && vol.getList().size()>0){
				List<Volume> newList = new ArrayList<Volume>();
				newList.add(vol);
				newList.addAll(list);
				list = newList;
			}
			model.addAttribute("vols", list);
		}else{
			Chapter chap = new Chapter();
			chap.setMediaId(media.getMediaId());
			PageFinder<Chapter> pageFinderChap = chapterService.findPageFinderObjs(chap,query);
			if(pageFinderChap.getData().size()>0){
				List<Chapter> lists = pageFinderChap.getData();
				model.addAttribute("maxIndex", lists.get(lists.size()-1).getIndex());
			}
			if(pageFinderChap.getData().size()==0){
				model.addAttribute("noChapter", "没有章节信息");
			}
			model.addAttribute("chaps", pageFinderChap.getData());
		}
		return "media/volumeChapter";
	}
	
	@RequestMapping(value="/isIllMedia")
	@ResponseBody
	public String isIllMedia(Media media){
		IllegalMedia illMedia = illegalMediaService.findUniqueByParams("mediaId",media.getMediaId());
		if(null != illMedia){
			LogUtil.info(LOGGER,"敏感作品不可上架 mediaId = "+media.getMediaId());
			return "{\"result\":\"failure\"}";
		}
		return "{\"result\":\"success\"}";
	}
	
	@RequestMapping(value="/getSaleByMediaId")
	@ResponseBody
	public void getSaleByMediaId(Media media) throws IOException{
		StringBuffer sb = new StringBuffer("");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mediaId", media.getMediaId());
		map.put("shelfStatus", 1);
		List<MediaSale> list = mediaSaleService.getSale(map);
		for(MediaSale sale : list){
			sb.append("【");
			sb.append("名称:"+sale.getName());
			sb.append(",类型:");
			if(sale.getType() == 0){
				sb.append(MediaSaleEnum.AUTO.getName());
			}else{
				sb.append(MediaSaleEnum.PACKAGE.getName());
			}
			sb.append("】,");
		}
		if(list.size() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		//getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("application/text;charset=UTF-8");
		getResponse().getWriter().write(sb.toString());
	}
	public static void main(String[] args) {
		String uid = "1416627725547961";
		System.out.println(uid.substring(uid.length()-4,uid.length()-2));
	}
}
