package com.dangdang.digital.controlller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.digital.model.EpubImage;
import com.dangdang.digital.model.EpubImport;
import com.dangdang.digital.service.IEpubImageService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.ebook.api.vo.EbookBaseInfoVo;
import com.dangdang.ebook.api.vo.EbookInfoVo;

@Controller
@RequestMapping("epubImage")
public class EpubImageController extends BaseController {
	@Resource
	private IEpubImageService epubImageService;
	
	@RequestMapping(value="/list")
	public String list(Query query,Model model,EpubImage epubImage){
		PageFinder<EpubImage> pageFinder=epubImageService.findPageFinderObjs(epubImage, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("epubImage", epubImage);
		
		return "epubImage/list";
	}
	
	@RequestMapping(value="/toUpload")
	public String toUpload(EpubImage epubImage, Model model) {
		
		model.addAttribute("mediaId", epubImage.getMediaId());
		return "epubImage/toUpdate";
	}
	
	@RequestMapping(value="/uploadImage")
	public String uploadImage(Model model, @RequestParam MultipartFile fileUpload, EpubImage epubImage) throws Exception{
		epubImageService.uploadImage(epubImage.getMediaId(), fileUpload);
		model.addAttribute("epubImage", epubImage);
        return "redirect:list.go?mediaId="+epubImage.getMediaId();
	}
	
}
