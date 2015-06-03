package com.dangdang.digital.controlller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.UsercmsUtils;
import com.ibm.icu.util.Calendar;

@Controller
@RequestMapping("chapter")
public class ChapterController extends BaseController {

	@Resource
	private IChapterService chapterService;
	@Resource
	private ICacheApi cacheApi;
	
	//未加密文件地址
	private String FILE_PATH = "media.unencryp.path";
	
	
	@RequestMapping("toEdit")
	public String toEdit(@RequestParam Long id,final Model model) throws IOException{
		Chapter chapter = chapterService.get(id);
		File f = new File(ConfigPropertieUtils.getString(FILE_PATH)+chapter.getFilePath());
		if(f.exists()){
			chapter.setContent(FileUtils.readFileToString(f));
		}else{
			chapter.setContent("章节内容不存在！");
		}
		model.addAttribute("chapter", chapter);
		return "chapter/modify";
	}
	
	@RequestMapping("setIsShow")
	public String setIsShow(Chapter chapter){
		chapter.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
		chapter.setLastModifyedDate(Calendar.getInstance().getTime());
		chapterService.update(chapter);
		refreshCache(chapter);
		return "redirect:/media/toChapterVolume.go?mediaId="+chapter.getMediaId();
	}
	@RequestMapping("update")
	public String update(Chapter chapter){
		chapter.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
		chapter.setLastModifyedDate(Calendar.getInstance().getTime());
		chapterService.update(chapter);
		refreshCache(chapter);
		return "redirect:/media/toChapterVolume.go?mediaId="+chapter.getMediaId();
	}
	
	@RequestMapping("chgOrder")
	public String chgOrder(Chapter chapter) throws Exception{
		if(chapter.getMediaId()==null && chapter.getVolumeId() == null){
			throw new Exception("调整章节顺序时不允许卷ID和mediaId同时为空");
		}
		chapter.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
		chapter.setLastModifyedDate(Calendar.getInstance().getTime());
		chapterService.chgOrder(chapter);
		refreshCache(chapter);
		return  "redirect:/media/toChapterVolume.go?mediaId="+chapter.getMediaId();
	}
	
	@RequestMapping("toChgOrder")
	public String toChgOrder(Chapter chapter,final Model model){
		model.addAttribute("chapter", chapter);
		return "chapter/chgOrder";
	}
	/**
	 * 
	 * Description: 刷新章节和目录缓存
	 * @Version1.0 2015年2月4日 上午11:58:47 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param chapter
	 */
	private void refreshCache(Chapter chapter){
		try {
			//清除目录缓存
			if(chapter != null && chapter.getMediaId() != null){
				cacheApi.cleanCacheByKey(Constans.CACHE_KEY_PREFIX_CONTENTS + chapter.getMediaId());
			}
			//清除章节缓存
			if(chapter != null && chapter.getId() != null){
				cacheApi.setChapterCache(chapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
