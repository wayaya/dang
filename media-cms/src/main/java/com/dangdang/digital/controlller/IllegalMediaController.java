package com.dangdang.digital.controlller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.IllegalMedia;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IIllegalMediaService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;


@Controller
@RequestMapping("illegalmedia")
public class IllegalMediaController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Resource(name="illegalMediaService")
	private IIllegalMediaService service;
	
	@Resource
	private IMediaService mediaService;
	
	@Resource
	private IMediaSaleService mediaSaleService;
	
	@RequestMapping(value="/list")
	public String list(String pageIndex,final Model model,final IllegalMedia illegalMedia){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<IllegalMedia> pageFinder = service.findPageFinderObjs(illegalMedia,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "illegalmedia/list";
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(value="/delete")
	public String deleteIllegalMedia(@RequestParam final int illegalMediaId,final long mediaId,final long saleId,final int shelfStatus){
		if(0 == shelfStatus){
			LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 删除敏感作品,什么也不作 ,mediaId="+mediaId
					+ " saleId="+saleId
					+" illegalMediaId ="+ illegalMediaId+ " mediaId="+illegalMediaId);
		}else if(1 == shelfStatus){
			//作品资源和销售主体都上架
			Media media = mediaService.get(mediaId);
			media.setShelfStatus(Constans.MEDIA_SHELF_STATUS_UP);//上架
			this.mediaService.update(media);
			MediaSale mediaSale = new MediaSale();
			mediaSale.setSaleId(media.getSaleId());
			mediaSale.setShelfStatus(Constans.MEDIA_SHELF_STATUS_UP);//销售主体上架
			this.mediaSaleService.update(mediaSale);
			
			Map map = new HashMap();
			map.put("ids", new String[]{String.valueOf(saleId)});//章品销售主体Id
			map.put("status", 1);
			this.mediaSaleService.toShelf(map);
			LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 删除敏感作品,并设置单品销售主体,资源为上架状态 ,illegalMediaId ="+ illegalMediaId
					+ " mediaId="+mediaId
					+ " saleId="+saleId
					);
		}else if(2 == shelfStatus){
			//作品资源上架,销售主体不操作
			Media media = mediaService.get(mediaId);
			media.setShelfStatus(Constans.MEDIA_SHELF_STATUS_UP);//上架
			this.mediaService.update(media);
			LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 删除敏感作品,并设置资源为上架状态 ,illegalMediaId ="+ illegalMediaId
					+ " mediaId="+mediaId
					+ " saleId="+saleId
					);
		}
		this.service.deleteById(illegalMediaId);
		return "redirect:list.go";
	}
	
	@RequestMapping(value="/deltip")
	public String showDelTip(@RequestParam final int illegalMediaId,final Model model){
		IllegalMedia illMedia = this.service.get(illegalMediaId);
		model.addAttribute("illMedia", illMedia);
		return "illegalmedia/deltip";
	}
	
	@RequestMapping(value="/add")
	public String addIllegalMedia(){
		return "illegalmedia/add";
	}
	
	@RequestMapping(value="/addillmedia")
	public String addIllegalMediaById(long mediaId){
		
		return "illegalmedia/add";
	}
	@RequestMapping(value="/save")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String saveIllegalMedia(final IllegalMedia illegalMedia){
		illegalMedia.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		Media media = mediaService.get(illegalMedia.getMediaId());
		media.setShelfStatus(Constans.MEDIA_SHELF_STATUS_DOWN);//下架
		this.mediaService.update(media);
		MediaSale mediaSale = new MediaSale();
		mediaSale.setSaleId(media.getSaleId());
		mediaSale.setShelfStatus(Constans.MEDIA_SHELF_STATUS_DOWN);//销售主体下架
		this.mediaSaleService.update(mediaSale);
		this.service.save(illegalMedia);
		return "redirect:list.go";
	}
	
	
	@RequestMapping(value="/checkid")
	@ResponseBody
	public String checkIllegalMediaId(final IllegalMedia illegalMedia){
		long mediaId = illegalMedia.getMediaId();
		Media media = mediaService.get(mediaId);
		if(null ==media){
			//不存在的media
			return "{\"result\":\"notexist\"}";
		}
		
		IllegalMedia temp = service.findUniqueByParams("mediaId",illegalMedia.getMediaId());
		if(null !=temp){
			return "{\"result\":\"failure\"}";
		}
		illegalMedia.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		return "{\"result\":\"success\"}";
	}
	
	@RequestMapping(value="/update")
	public String updateIllegalMedia(final IllegalMedia illegalMedia){
		illegalMedia.setOperatorId(UsercmsUtils.getCurrentUser().getUsercmsId());
		this.service.update(illegalMedia);
		return "redirect:list.go";
	}
	
	
	@RequestMapping(value="/offshelf")
	public String setMediaToOffShlet(@RequestParam final long  mediaId,final Model model){
		return "illegalmedia/edit";
	}
	@RequestMapping(value="/edit")
	public String editIllegalMedia(@RequestParam final int illegalMediaId,final Model model){
		IllegalMedia illegalMedia = this.service.get(illegalMediaId);
		model.addAttribute("illegalMedia", illegalMedia);
		return "illegalmedia/edit";
	}
}
