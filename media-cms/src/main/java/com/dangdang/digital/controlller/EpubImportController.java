package com.dangdang.digital.controlller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javacommon.util.DateTimeUtils;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.digital.model.EpubImport;
import com.dangdang.digital.service.IEpubImportService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.ebook.api.api.IEbookApi;
import com.dangdang.ebook.api.vo.EbookBaseInfoVo;
import com.dangdang.ebook.api.vo.EbookInfoVo;


@Controller
@RequestMapping("epubImport")
public class EpubImportController extends BaseController {

	@Resource(name="epubImportService")
	private IEpubImportService epubImportService;
	@Resource
	private IEbookApi ebookApi;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EpubImportController.class);
	String batchId=null;
	@RequestMapping(value="/list")
	public String list(Query query,Model model,EpubImport epubImport){
		PageFinder<EpubImport> pageFinder=epubImportService.findPageFinderObjs(epubImport, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("epubImport", epubImport);
		return "epubImport/list";
	}
	
	@RequestMapping(value="/addImportInfo")
	public String addImportInfo(Model model){
		batchId = DateTimeUtils.format(new Date(), "yyyyMMdd_HH");
		model.addAttribute("batchId",batchId);
		return "epubImport/import";
	}
	
	private Long getCellValue(Cell cell){
		int cellType = cell.getCellType();
		if(XSSFCell.CELL_TYPE_BLANK == cellType){
			return null;
		}else if(XSSFCell.CELL_TYPE_STRING == cellType){
			return Long.valueOf(cell.getStringCellValue());
		}else if(XSSFCell.CELL_TYPE_NUMERIC == cellType){
			return Long.valueOf((long)cell.getNumericCellValue());
		}
		return null;
	}
	@RequestMapping(value="/importFile")
	public String importFile(Query query,Model model, @RequestParam MultipartFile fileUpload) throws Exception{
		EpubImport epubImport = new EpubImport();
				//@RequestParam("fileName") 
		//1.对Excel进行处理
		Workbook workBook=null;
		try{
			workBook=new HSSFWorkbook(fileUpload.getInputStream());
		}catch(Exception e){
			try {
				workBook=new XSSFWorkbook(fileUpload.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Sheet sheet = workBook.getSheetAt(0);
		final int lastRow = sheet.getLastRowNum();
		//2.set 为导入exl的product_id字段
		Set<Long> excelSet=new HashSet<Long>();//Excel進來數據
		Set<Long> sqlSet=new HashSet<Long>();//数据库中的productId数据
		for(int i=0; i<=lastRow; i++){
        	Row row = sheet.getRow(i);
        	Cell cell= row.getCell(0);
        	Long productId=getCellValue(cell);
          	if(productId!=null){
          		excelSet.add(productId);
          	}
        }
		
		int sameNum = 0;
		List<Long> setList = new ArrayList<Long>();
		for( Long productId : excelSet){
			List<EpubImport> sqlProductId=epubImportService.findListByParams("productId", productId);
			if(sqlProductId != null && sqlProductId.size() > 0) {
				sameNum ++;
			} else {
				setList.add(productId);
			}
		}
        //5.从接口获取ebook信息，存入数据库中
        if(setList!= null){
        	List<Long> failFtlList=null;
        	List<EbookBaseInfoVo> importList=ebookApi.ebookBaseInfoVoListByProductIds(setList);
        	if(importList!=null && !importList.isEmpty()){
        		if(importList.size()<setList.size()){
            		List<Long> apiList=new ArrayList<Long>();//查询到的id
            		failFtlList=new ArrayList<Long>(setList);//失败id
            		//1.查询不到结果，ebookApi查询失败，failFtlList为失败的数据，条数： failFtlList.size();
            		if(importList.size()==0){
            			    apiList=null;
            			    model.addAttribute("failFtlList",failFtlList);
            			    model.addAttribute("failNum", failFtlList.size());
            			    
            		}else{
            			//2.查询部分id，apiList为查询到的数据，failFtlList为查询失败的数据，条数：failFtlList.size()
            			for(EbookBaseInfoVo ev:importList){
                			EbookInfoVo ei=ev.getEbookInfoVo();
                			//存入數據庫
                  		    Long ebookId=ei.getId();
                  			Long productId=ei.getProductId();
                  			String title=ei.getTitle();
                  			String uid=ei.getUid();
                  			epubImport.setBatchId(batchId);
                  	        epubImport.setEbookId(ebookId);
                  	        epubImport.setProductId(productId);
                  	        epubImport.setTitle(title);
                  	        epubImport.setUid(uid);
                  	        epubImport.setCreateTime(new Date());
                  	        epubImport.setStatus(epubImport.getStatus());
                  	        epubImportService.save(epubImport);
                  	        apiList.add(productId);
                		}
            			failFtlList.removeAll(apiList);
            			model.addAttribute("failFtlList", failFtlList);
            			model.addAttribute("failNum", failFtlList.size());
            			model.addAttribute("successNum", apiList.size());
            		}
            	}else{
            		//全部添加成功
            		for(EbookBaseInfoVo ev:importList){
             		    EbookInfoVo ei=ev.getEbookInfoVo();
             		    Long ebookId=ei.getId();
             			Long productId=ei.getProductId();
             			String title=ei.getTitle();
             			String uid=ei.getUid();
             			epubImport.setBatchId(batchId);
             	        epubImport.setEbookId(ebookId);
             	        epubImport.setProductId(productId);
             	        epubImport.setTitle(title);
             	        epubImport.setUid(uid);
             	        epubImport.setCreateTime(new Date());
             	        epubImport.setStatus(epubImport.getStatus());
             	        epubImportService.save(epubImport);
            		}
            		model.addAttribute("successNum", importList.size());
               }
        	}else{
        		failFtlList=setList;
        		model.addAttribute("failFtlList", failFtlList);
        		model.addAttribute("failNum", failFtlList.size());
        	}
          
        }
        //重複數據
        model.addAttribute("sameNum", sameNum);
        return "epubImport/import";
	}
}
