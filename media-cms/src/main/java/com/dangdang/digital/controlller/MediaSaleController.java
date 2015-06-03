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

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.dangdang.digital.constant.OperateTargetTypeEnum;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IManagerOperateLogService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.ISysPropertiesService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.ScaleCoverImg;
import com.dangdang.digital.utils.UploadPicToCDN;
import com.dangdang.digital.utils.UsercmsUtils;
import com.ibm.icu.util.Calendar;

@Controller
@RequestMapping("mediaSale")
public class MediaSaleController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MediaSaleController.class);
	
	@Resource
	private IMediaSaleService mediaSaleService;
	
	@Resource
	private ISysPropertiesService service;
	
	@Resource
	private IMediaService mediaService;
	
	@Resource
	private ICacheApi cacheApi;
	
	@Resource
	private IManagerOperateLogService managerOperateLogService;
	
	private final String MEDIA_FULL_DEFAULT_PRICE_KEY = "media.full.default.price";
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final MediaSale mediaSale){
		
		PageFinder<MediaSale> pageFinder = mediaSaleService.findPageFinderObjs(mediaSale,query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("mediaSale", mediaSale);
		String success = getRequest().getParameter("msg");
		if(StringUtils.isNotBlank(success)){
			if("1".equals(success)){
				model.addAttribute("msg", "操作成功");
			}else{
				model.addAttribute("msg", "操作失败");
			}
		}
		return "mediaSale/list";
	}
	
	@RequestMapping(value="/toUploadShelf")
	public String toUploadShelf(){
		return "mediaSale/uploadShelf";
	}
	
	public String getCellValue(Cell cell){
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			return (int)cell.getNumericCellValue()+"";
		}else{
			return cell.getStringCellValue();
		}
	}
	@RequestMapping(value="/uploadShelf")
	@ResponseBody
	public String uploadShelf(@RequestParam(value="file",required=false) MultipartFile file) throws NumberFormatException, Exception{
		String msg = "";
		try {
			List<String> saleIdList = new ArrayList<String>();
			List<String> mediaIdList = new ArrayList<String>();
			String shelfStatus = getRequest().getParameter("shelfStatus");
			String reason = getRequest().getParameter("reason");
			String name = file.getOriginalFilename();
			if(name.toLowerCase().endsWith("xlsx")){
				XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
				XSSFSheet sheet = wb.getSheetAt(0);
				int row = sheet.getPhysicalNumberOfRows();
				if(row==1){
					msg = "{\"success\":false,msg:\""+"上传的excel表格没有数据！"+"\"}";
					return msg;
				}
				for(int i=1;i<row;i++){
					XSSFRow hssfrow = sheet.getRow(i);
					String saleId = getCellValue(hssfrow.getCell(0));
					String mediaId = getCellValue(hssfrow.getCell(1));
					if(StringUtils.isNotBlank(saleId)){
						MediaSale sale = mediaSaleService.get(Long.valueOf(saleId));
						if(sale!=null && sale.getShelfStatus().intValue() != Integer.valueOf(shelfStatus).intValue()){
							saleIdList.add(saleId);
							if(StringUtils.isNotBlank(mediaId)){
								mediaIdList.add(mediaId);
							}
						}
					}
				}
			}else{
				HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
				HSSFSheet sheet = wb.getSheetAt(0);
				int row = sheet.getPhysicalNumberOfRows();
				if(row==1){
					msg = "{\"success\":false,msg:\""+"上传的excel表格没有数据！"+"\"}";
					return msg;
				}
				for(int i=1;i<row;i++){
					HSSFRow hssfrow = sheet.getRow(i);
					String saleId = getCellValue(hssfrow.getCell(0));
					String mediaId = getCellValue(hssfrow.getCell(1));
					if(StringUtils.isNotBlank(saleId)){
						MediaSale sale = mediaSaleService.get(Long.valueOf(saleId));
						if(sale.getShelfStatus().intValue() != Integer.valueOf(shelfStatus).intValue()){
							saleIdList.add(saleId);
							if(StringUtils.isNotBlank(mediaId)){
								mediaIdList.add(mediaId);
							}
						}
					}
				}
			}
			if(saleIdList.size()>0){
				mediaSaleService.uploadShelf(saleIdList, mediaIdList, shelfStatus);
				
				if(mediaIdList.size()>0){
					for(String mediaId : mediaIdList){
						cacheApi.setMediaCache(mediaService.get(Long.valueOf(mediaId)));
					}
				}
				if(saleIdList.size()>0){
					for(String saleId : saleIdList){
						cacheApi.setMediaSaleCache(mediaSaleService.get(Long.valueOf(saleId)));
					}
				}
				if("0".equals(shelfStatus)){
					for(String saleId : saleIdList){
						managerOperateLogService
						.insertOperateLog(new ManagerOperateLog(
								null,
								Constans.MANAGER_OPERATE_RESULT_SUCCESS,
								OperateTargetTypeEnum.MEDIA_SALE.getType(),
								Long.valueOf(saleId),
								UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
										.getCurrentUser().getLoginName()
										: "system", new Date(),"下架原因："+reason));
					}
					for(String mediaId : mediaIdList){
						managerOperateLogService
						.insertOperateLog(new ManagerOperateLog(
								null,
								Constans.MANAGER_OPERATE_RESULT_SUCCESS,
								OperateTargetTypeEnum.MEDIA.getType(),
								Long.valueOf(mediaId),
								UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
										.getCurrentUser().getLoginName()
										: "system", new Date(),"下架原因："+reason));
					}
				}
			}
			msg = "{\"success\":true}";
		} catch (IOException e) {
			msg = "{\"success\":false,msg:\""+"出错了，error:"+e.getMessage()+"\"}";
		}
		return msg;
	}
	@RequestMapping(value="/update")
	public String update(MediaSale sale) throws FileNotFoundException, IOException{
		try {
			sale.setLastModifiedDate(Calendar.getInstance().getTime());
			sale.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
			mediaSaleService.update(sale);
			LogUtil.info(LOGGER,"管理员:{0}在修改销售主体【{1}】基本信息", UsercmsUtils.getCurrentUser().getLoginName(),
					sale.getSaleId());
			cacheApi.setMediaSaleCache(sale);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在修改销售主体【{1}】基本信息失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					sale.getSaleId());
		}
		return "redirect:list.go";
	}
	
	@RequestMapping(value="/toSetIsFull")
	public String toSetIsFull(@RequestParam(value="saleId") Long saleId,final Model model){
		MediaSale sale = mediaSaleService.get(saleId);
		model.addAttribute("sale", sale);
		String price = service.getValue(MEDIA_FULL_DEFAULT_PRICE_KEY);
		model.addAttribute("price", price);
		return "mediaSale/setIsFull";
	}
	@RequestMapping(value="/setIsFull")
	public String setIsFull(MediaSale sale,final Model model){
		int success = 0;
		try{
			mediaSaleService.setIsFull(sale);
			cacheApi.setMediaSaleCache(sale);
			Media media = mediaService.findMasterUniqueByParams("saleId",sale.getSaleId());
			cacheApi.setMediaCache(media);
			LogUtil.info(LOGGER,"管理员:{0}在设置销售主体【{1}】为完本", UsercmsUtils.getCurrentUser().getLoginName(),
					sale.getSaleId());
			success = 1;
		}catch(Exception e){
			LogUtil.error(LOGGER,"管理员:{0}在设置销售主体【{1}】为完本失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					sale.getSaleId());
		}
		return "redirect:list.go?msg="+success;
	}
	
	
	@RequestMapping(value="/toModify")
	public String toModify(MediaSale sale,final Model model){
		sale = mediaSaleService.get(sale.getSaleId());
		model.addAttribute("sale", sale);
		return "mediaSale/modify";
	}
	
	@RequestMapping("/checkMedia")
	@ResponseBody
	public String checkMedia(@RequestParam(value="ids") String ids,
			@RequestParam(value="status") Integer status){
		StringBuffer sb = new StringBuffer("");
		String arr[] = ids.split(",");
		if(status.intValue() == 1){
			for(String id : arr){
				Media media = mediaService.findMasterUniqueByParams("saleId",Long.valueOf(id));
				if(media.getShelfStatus().intValue()==0){
					sb.append("["+media.getMediaId()+","+media.getTitle()+"]").append(";");
				}
			}
			if(sb.length() > 1){
				sb.deleteCharAt(sb.length()-1);
			}
		}
		if(sb.length() == 0){
			return "{\"success\":true}";
		}else{
			return "{\"success\":false,\"msg\":\""+sb.toString()+"\"}";
		}
	}
	@RequestMapping(value="/toShelf")
	public String toShelf(@RequestParam(value="ids") String ids,
			@RequestParam(value="status") Integer status,final Model model,String dsDesc){
		int success = 0;
		if(StringUtils.isNotBlank(dsDesc)){
			dsDesc = "，下架原因：" + dsDesc;
		}else{
			dsDesc = "";
		}
		String arr[] = ids.split(",");
		try {
			if(arr!=null && arr.length>0 &&StringUtils.isNotBlank(arr[0]) && status != null){
				if(status == 1){
					dsDesc = "上架销售主体" + dsDesc;
				}else{
					dsDesc = "下架销售主体" + dsDesc;
				}
				Map map = new HashMap();
				map.put("ids", arr);
				map.put("status", status);
				mediaSaleService.toShelf(map);
				// 插入操作日志
				for(String id : arr){
					managerOperateLogService
					.insertOperateLog(new ManagerOperateLog(
							null,
							Constans.MANAGER_OPERATE_RESULT_SUCCESS,
							OperateTargetTypeEnum.MEDIA_SALE.getType(),
							Long.valueOf(id),
							UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
									.getCurrentUser().getLoginName()
									: "system", new Date(),dsDesc));
				}
			}
			LogUtil.info(LOGGER,"管理员:{0}在批量上下架销售主体【{1}】,status:【{2}】", UsercmsUtils.getCurrentUser().getLoginName(),
					ids,status);
			success = 1;
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在批量上下架销售主体【{1}】基本信息失败,status:【{2}】",e, UsercmsUtils.getCurrentUser().getLoginName(),
					ids,status);
			// 插入操作日志
			for(String id : arr){
				managerOperateLogService
				.insertOperateLog(new ManagerOperateLog(
						null,
						Constans.MANAGER_OPERATE_RESULT_FAIL,
						OperateTargetTypeEnum.MEDIA_SALE.getType(),
						Long.valueOf(id),
						UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
								.getCurrentUser().getLoginName()
								: "system", new Date(),dsDesc));
			}
		}
		return "redirect:list.go?msg="+success;
	}
}
