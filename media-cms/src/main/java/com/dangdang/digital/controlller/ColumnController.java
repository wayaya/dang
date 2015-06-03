package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ConstantEnum;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.ColumnContent;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IColumnContentService;
import com.dangdang.digital.service.IColumnService;
import com.dangdang.digital.service.ISysPropertiesService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
/**
 * 
 * Description: 栏目管理控制器
 * All Rights Reserved.
 * @version 1.0  2014年11月18日 下午3:18:20  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Controller
@RequestMapping("column")
public class ColumnController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	IColumnService service;
	
	@Resource
	ISysPropertiesService sysPropertiesService;
	//media分类信息
	@Resource 
	ICatetoryService catetoryService;
	
	
	@Resource
	IColumnContentService contentService;
	
	
	
	@RequestMapping(value="/checkcode")
	@ResponseBody
	public String checkColumnCode(String columnCode){
		Column column = service.findMasterUniqueByParams("columnCode",columnCode);
		if(null !=column){
			return "{\"result\":\"failure\"}";
		}else{
			//编号已存在
			return "{\"result\":\"success\"}";
		}
	}
	/**
	 * 
	 * Description: 显示单品销售主体
	 * @Version1.0 2014年11月19日 下午5:53:24 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sales")
	public String listSingleSales(String pageIndex,final Model model,String columnCode,final Map paramMap){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		Column temp = this.service.findUniqueByParams("columnCode",columnCode);
		if(null!=temp){
			//栏目,否则是分类
			paramMap.put("columnId", temp.getColumnId());
		}
		
		paramMap.put("columnCode", columnCode);
		PageFinder<Map<?,?>> pageFinder = contentService.getSingelSales(paramMap, query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("mediaCategoryTree", getMediaCategoryTree("YC"));
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 查看单品销售主体");
		return "column/content/sales";
	}
	@RequestMapping(value="/sales/query")
	public String listSingleSales(String pageIndex,final Model model,
			 final Long columnId,
			 final String columnCode,
			 final Long mediaId,
			 final Long mediaSaleId,
			 final String mediaName,
			 final String authorPenname,
			 final String mediaCategoryIds,
			  String path
			){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		if(StringUtils.isNotBlank(mediaName)){
			paramMap.put("mediaName", mediaName);
			model.addAttribute("mediaName", mediaName);
		}
		if(StringUtils.isNotBlank(path)){
			int index  = path.indexOf("-");
			if(index > 0){
				path = path.substring(0,index);
			}
			
			paramMap.put("path", path);
			
		}
		if(StringUtils.isNotBlank(mediaCategoryIds)){
			paramMap.put("mediaCategoryIds", mediaCategoryIds);
		}
		
		if(null!= mediaId ){
			paramMap.put("mediaId", mediaId);
		}
		if(null!= mediaSaleId ){
			paramMap.put("saleId", mediaSaleId);
		}
		if(StringUtils.isNotBlank(authorPenname)){
			paramMap.put("authorPenname", authorPenname);
		}
		paramMap.put("columnCode", columnCode);
		PageFinder<Map<?,?>> pageFinder = contentService.getSingelSales(paramMap, query);
		
		model.addAttribute("columnCode", columnCode);
		model.addAttribute("columnId", columnId);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("mediaCategoryTree", getMediaCategoryTree(path));
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()
				+" 节点分类 path="+path
				+" 查询单品销售主体[销售主体名称="
				+mediaName+",mediaId="+mediaId+" author_penname="+authorPenname);
		return "column/content/sales";
	}
	
	
	@RequestMapping(value="/content/excelimport")
	public String importColumnContentByExcel(){
		return "column/content/excelimport";
	}
	
	@RequestMapping(value="content/excel")
	public String importColumnContent(@RequestParam final int columnId,@RequestParam MultipartFile upfilename){
		Workbook workBook=null;
		try{
			workBook=new HSSFWorkbook(upfilename.getInputStream());
		}catch(Exception e){
			try {
				workBook=new XSSFWorkbook(upfilename.getInputStream());
			} catch (IOException e1) {
				LogUtil.error(logger, e,UsercmsUtils.getCurrentUser().getLoginName()+" excel导入销售主体出错[columnId="+columnId+",upfilename="+upfilename.getName());
			}
		}
		Sheet sheet = workBook.getSheetAt(0);
		final int lastRow = sheet.getLastRowNum();
		List<ColumnContent> list = new ArrayList<ColumnContent>();
		//第一行表头
		for(int i=1;i<=lastRow;i++){
			Row row = sheet.getRow(i);
			String saleId = getCellValue(row.getCell(1));
			String saleName =  getCellValue(row.getCell(2));
			String mediaId =getCellValue(row.getCell(3)); 
			System.out.println("第"+i+"行,saleId="+saleId+",saleName="+saleName+" mediaId="+mediaId);
			ColumnContent cc = new ColumnContent();
			cc.setColumnId(columnId);
			cc.setCreator(UsercmsUtils.getCurrentUser().getLoginName());//当前用户的编号
			cc.setSaleId(Long.valueOf(saleId));
			cc.setSaleName(saleName);
			cc.setStatus(ConstantEnum.SALE_STATUS_NORMAL.getValue());
			list.add(cc);
		}
		this.contentService.insertBatch(list);
		LogUtil.info(logger,UsercmsUtils.getCurrentUser().getLoginName()+" 导入单品销售主体成功[columnId="+columnId
				+",upfilename="+upfilename.getName()+" 数量="+list.size());

		return "redirect:/column/content/content.go?columnId="+columnId;
	}
	
	private  String getCellValue(Cell cell){
		switch(cell.getCellType()){
			case 0:
				String value = String.valueOf(cell.getNumericCellValue());
				int dot = value.indexOf(".");
				if(dot!=-1){
					return value.substring(0,dot);
				}else{
					return value;
				}
			case 1:
				return cell.getStringCellValue();
			default:
				return cell.getStringCellValue();
		}
	}
	/**
	 * 
	 * Description: 导入栏目销售主体
	 * @Version1.0 2014年11月20日 下午2:47:38 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/content/import")
	@ResponseBody
	public String importColumnContent(@RequestParam(required=false) final  Integer columnId,final String columnCode,@RequestParam final  String saleIds,@RequestParam final  String saleName,final Model model){
		final String[] saleIdAry =  saleIds.split(",");
		final String[] saleNameAry =  saleName.split(",");
		List<ColumnContent> listContent = new ArrayList<ColumnContent>(saleIdAry.length);
		for(int i=0,len = saleIdAry.length;i<len;i++){
			long id = Long.valueOf(saleIdAry[i]);
			String name = saleNameAry[i];
			ColumnContent cc = new ColumnContent();
			cc.setSaleId(id);
			cc.setOrderValue(0);
			cc.setSaleName(name);
			cc.setColumnCode(columnCode);
			cc.setStatus(ConstantEnum.SALE_STATUS_NORMAL.getValue());
			cc.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
			cc.setColumnId(columnId);
			listContent.add(cc);
		}
		this.contentService.insertBatch(listContent);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 导入单品销售主体[销售主体名称="+saleName+",导入栏目编号="+columnId);
		return "{\"result\":\"success\"}";
	}
	
	
	/**
	 * 
	 * Description: 原创分类树(分类销量榜单),点击每个分类叶子节点,显示该节点下的销量排名榜
	 * @Version1.0 2015年3月6日 下午4:49:37 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/content/list")
	public String listColumn(final Model model) {
		StringBuilder treeHtml = new StringBuilder("[");
		List<Catetory> catetoryYuanChuangList = catetoryService.getCatetoryByParentCode("YC");
		for (Catetory tt : catetoryYuanChuangList) {
				// 非原创跟节点,原创分类节点
				treeHtml.append("{id:").append(tt.getId()).append(",");
				treeHtml.append("pId:").append(tt.getParentId()).append(",");
				treeHtml.append("categoryId:\"").append(tt.getId()).append("\",");
				treeHtml.append("code:\"").append(tt.getCode()).append("\",");
				treeHtml.append("name:\"").append(tt.getName()).append("\",");
				treeHtml.append("type:\"category\",");
				treeHtml.append("file:\"content\"}");
				treeHtml.append(",");
		}
		treeHtml.deleteCharAt(treeHtml.length() - 1);
		treeHtml.append("]");
		model.addAttribute("treeHtml", treeHtml.toString());
		return "column/content/list";
	}
	
	/**
	 * 
	 * Description: 删除栏目内销售主体
	 * @Version1.0 2014年11月21日 上午10:44:01 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/content/delete")
	public String deleteColumnContentByIds(Integer[] contentId,final String columnCode,final Model model){
		this.contentService.deleteByIds(Arrays.asList(contentId));
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 删除销售主体[销售主体名称="+Arrays.asList(contentId)+",栏目标识="+columnCode);
		return "redirect:/column/content/content.go?columnCode="+columnCode;
	}
	
	/**
	 * 
	 * Description: 修改栏目内容的有效时间
	 * @Version1.0 2014年11月21日 上午11:10:28 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param contentId
	 * @return
	 */
	@RequestMapping(value="/content/updatedate")
	public String updateColumnContentDateByIds(String  columnCode, @RequestParam final String contentIds,
			@RequestParam final String startDate,@RequestParam final String endDate){
		List<String> listIds = Arrays.asList(contentIds.split(","));
		Map mapParam = new HashMap(4);
		mapParam.put("contentIds", listIds);
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);
		this.contentService.updateEffectiveDate(mapParam);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 设置销售主体有效期[销售主体编号="+Arrays.asList(contentIds.split(","))+",栏目编号="+columnCode);
		return "redirect:/column/content/content.go?columnCode="+columnCode;
	}
	
	@RequestMapping(value="/content/editstatus")
	public String redircetEditsSaleContentStatus(@RequestParam String contentIds,final Model model){
		model.addAttribute("contentIds", contentIds);
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("force_valid", ConstantEnum.SALE_STATUS_FORCE_VALID.getValue());
		model.addAttribute("force_invalid", ConstantEnum.SALE_STATUS_FORCE_INVALID.getValue());
		model.addAttribute("to_history", ConstantEnum.SALE_HISTORY_STATUS.getValue());
		return "/column/content/editstatus";//+ids;
	}
	
	
	
	@RequestMapping(value="/content/updatestatus")
	public String updateColumnContentStatusByIds(final String  columnCode, @RequestParam final String contentIds,@RequestParam final String status){
		List<String> listIds = Arrays.asList(contentIds.split(","));
		Map  paramMap  = new HashMap(4);
		paramMap.put("status",status);
		paramMap.put("contentIds",listIds);
		if(Integer.valueOf(status) == ConstantEnum.SALE_STATUS_FORCE_VALID.getValue()){
			paramMap.put("status",status);
		}
		this.contentService.updateStatusByIds(paramMap);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 设置销售主体状态[销售主体编号="+Arrays.asList(contentIds.split(","))
				+" status=" + status +",栏目标识="+columnCode);
		return "redirect:/column/content/content.go?columnCode="+columnCode;
	}
	
	/**
	 * 
	 * Description: 批量审核
	 * @Version1.0 2014年11月21日 上午11:10:28 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param contentId
	 * @return
	 */
	@RequestMapping(value="/content/audit")
	public String auditColumnContentByIds(final int columnId,Integer[] contentId){
		Map paramMap = new HashMap(4);
		paramMap.put("auditor", UsercmsUtils.getCurrentUser().getLoginName());
		paramMap.put("contentIds",Arrays.asList(contentId));
		paramMap.put("status",ConstantEnum.SALE_STATUS_NORMAL.getValue());
		this.contentService.auditByIds(paramMap);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 审核销售主体[销售主体编号="+Arrays.asList(contentId)
				+",栏目编号="+columnId);
		return "redirect:/column/content/content.go?columnId="+columnId;
	}
	
	/**
	 * Description: 设置销售主体有效期
	 * @Version1.0 2014年11月21日 上午11:56:15 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/content/setdate")
	public String setSaleContentDate(@RequestParam String contentIds,final Model model){
		model.addAttribute("contentIds", contentIds);
		return "/column/content/setdate";//+ids;
	}
	
	@RequestMapping(value="/content/updateorder")
	public String updateColumnContentOrder(final Integer[]cId,final Integer[] orderValue,final String columnCode) {
		List<ColumnContent> listColumnContent = new ArrayList<ColumnContent>(cId.length);
		int n=0;
		String lastChangeDate = DateUtil.format(new Date(),DateUtil.DATE_PATTERN);
		for(Integer id:cId){
			if(null!=orderValue[n]){
				ColumnContent cc = new ColumnContent();
				cc.setColumnCode(columnCode);
				cc.setContentId(id);
				cc.setOrderValue(orderValue[n]);
				cc.setModifer(UsercmsUtils.getCurrentUser().getLoginName());
				cc.setLastChangeDate(lastChangeDate);
				
				listColumnContent.add(cc);
			}
			n++;
		}
		if(listColumnContent.size()>0){
			contentService.updateBatch(listColumnContent);
		}
		return "redirect:/column/content/content.go?columnCode="+columnCode;

	}
	
	
	@RequestMapping(value="/content/content")
	public String listColumnContent(String pageIndex,final Model model,final ColumnContent cc){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<ColumnContent> pageFinder = contentService.findPageFinderObjs(cc,query);
		model.addAttribute("pageFinder", pageFinder);
//		model.addAttribute("waitaudited", ConstantEnum.SALE_CONTENT_STATUS_WAITINGFOR_AUDITED.getValue());
//		model.addAttribute("audited", ConstantEnum.SALE_CONTENT_STATUS_AUDITED.getValue());
		model.addAttribute("on_shelf", Constans.MEDIA_SHELF_STATUS_UP);
		model.addAttribute("off_shelf", Constans.MEDIA_SHELF_STATUS_DOWN);
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("force_valid", ConstantEnum.SALE_STATUS_FORCE_VALID.getValue());
		model.addAttribute("force_invalid", ConstantEnum.SALE_STATUS_FORCE_INVALID.getValue());
	
		return "column/content/content";
	}
	
	@RequestMapping(value="/list")
	public String list(final Model model){
		// 自己创建的栏目
		List<Column> columnList = service.getAll();
		StringBuffer treeHtml = new StringBuffer("[{id:0, pId:0,name:\"栏目列表\",open:true,file:\"sub\"},");
		for (Column column : columnList) {
			treeHtml.append("{id:").append(column.getColumnId()).append(",");
			treeHtml.append("pId:").append(column.getParentId()).append(",");
			treeHtml.append("path:\"").append(column.getPath()).append("\",");
			treeHtml.append("code:\"").append(column.getCode()).append("\",");
			treeHtml.append("columnCode:\"").append(column.getColumnCode()).append("\",");
			treeHtml.append("name:\"").append(column.getName()).append("\",");
			treeHtml.append("file:\"sub\"}");
			treeHtml.append(",");
		}
		treeHtml.deleteCharAt(treeHtml.length() - 1);
		treeHtml.append("]");
		model.addAttribute("treeHtml", treeHtml.toString());
		return "column/list";
	}
	
	/**
	 * 
	 * Description: 获取原创下面的media的分类树
	 * @Version1.0 2015年1月28日 上午10:57:10 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	private String getMediaCategoryTree(String root){
		StringBuilder treeHtml = new StringBuilder("[");
		List<Catetory> catetoryYuanChuangList = catetoryService.getCatetoryByParentCode(root);
		for (Catetory tt : catetoryYuanChuangList) {
			if (tt.getCode().equalsIgnoreCase(root)) {
				// 原创直接放到栏目下面
				treeHtml.append("{id:").append(tt.getId()).append(",");
				treeHtml.append("pId:0,");
				treeHtml.append("code:\"").append(tt.getCode()).append("\",");
				treeHtml.append("name:\"").append(tt.getName()).append("\",");
				treeHtml.append("type:\"category\",");
				treeHtml.append("open:true,");
				treeHtml.append("file:\"content\"}");
				treeHtml.append(",");
			} else {
				// 非原创跟节点
				treeHtml.append("{id:").append(tt.getId()).append(",");
				treeHtml.append("pId:").append(tt.getParentId()).append(",");
				treeHtml.append("code:\"").append(tt.getCode()).append("\",");
				treeHtml.append("name:\"").append(tt.getName()).append("\",");
				treeHtml.append("type:\"category\",");
				treeHtml.append("file:\"content\"}");
				treeHtml.append(",");
			}
		}
		treeHtml.deleteCharAt(treeHtml.length() - 1);
		treeHtml.append("]");
		return treeHtml.toString();
		
	}
	@RequestMapping(value="/sub")
	public String getSubColumn(String pageIndex,final Model model,final Column column){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<Column> pageFinder = service.findPageFinderObjs(column,query);
		String  columnBaseDir = ConfigPropertieUtils.getString("media.resource.images.column.http.path");
		model.addAttribute("column_base_dir", columnBaseDir);
		model.addAttribute("pageFinder", pageFinder);
		return "column/sub";
	}
	
	/**
	 * 
	 * Description: 显示所有栏目列表,由其它页面选择调用
	 * @Version1.0 2015年3月5日 下午5:19:35 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param pageIndex
	 * @param model
	 * @param column
	 * @return
	 */
	@RequestMapping(value="/choosecolumn")
	public String getColumnList(String pageIndex,String codeId,String nameId,final Model model,final Column column){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<Column> pageFinder = service.findPageFinderObjs(column,query);
		String  columnBaseDir = ConfigPropertieUtils.getString("media.resource.images.column.http.path");
		model.addAttribute("column_base_dir", columnBaseDir);
		model.addAttribute("pageFinder", pageFinder);
		if(null != codeId  && !codeId.trim().isEmpty()){
			model.addAttribute("code", codeId);
		}
		if(null != nameId  && !nameId.trim().isEmpty()){
			model.addAttribute("name", nameId);
		}
		return "column/choosecolumn";
	}
	@RequestMapping(value="/delete")
	public String delete(@RequestParam final int columnId,@RequestParam final int parentId,
			@RequestParam(required=false)final Integer isCallBack,@RequestParam(required=false)final String codeId,
			@RequestParam(required=false)final String nameId){
		Column column =service.get(columnId);
		int delCount = this.contentService.deleteByByParams("columnCode",column.getColumnCode());
		LogUtil.info(logger," 删除栏目:"+columnId +" 内容条数:"+delCount);
		this.service.deleteById(columnId);
		File rootDir = new File(ConfigPropertieUtils.getString("media.resource.images.column.path")+File.separator);
		File oldIcon = new File(rootDir,column.getIcon());
		if(oldIcon.exists()){
			boolean flag = oldIcon.delete();
			LogUtil.info(logger," 删除栏目:"+columnId +" 图标: "+ oldIcon.getPath()+(flag?" 成功":"失败"));
		}
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 删除栏目:"+columnId+" 成功");
		if(null != isCallBack){
			return "redirect:list.go?parentId="+column.getParentId()+"&isCallBack="+isCallBack
					+"&codeId="+codeId+"&nameId="+nameId;
		}else{
			return "redirect:list.go?parentId="+column.getParentId();
		}
	}
	
	@RequestMapping(value="/edit")
	public String edit(final Column col,final Model model){
		Column column = this.service.get(col.getColumnId());
		model.addAttribute("column", column);
		String  columnBaseDir = ConfigPropertieUtils.getString("media.resource.images.column.http.path");
		model.addAttribute("column_base_dir", columnBaseDir);
		return "column/edit";
	}
	@RequestMapping(value="/add")
	public String add(final Model model){
		model.addAttribute(ConstantEnum.CHANNEL_ALL.getCode(), ConstantEnum.CHANNEL_ALL.getCode());
		model.addAttribute(ConstantEnum.CHANNEL_NP.getCode(), ConstantEnum.CHANNEL_NP.getCode());
		model.addAttribute(ConstantEnum.CHANNEL_VP.getCode(), ConstantEnum.CHANNEL_VP.getCode());
		return "column/add";
	}
	
	@RequestMapping(value="/save")
	public String save(Column column,@RequestParam(value="upfilename",required=false) MultipartFile file,
			@RequestParam(required=false)final Integer isCallBack,@RequestParam(required=false)final String codeId,
			@RequestParam(required=false)final String nameId ){
		String icon =  column.getIcon();
		if(null!=icon &&(file!=null && file.getSize() > 0)){
			if(icon.indexOf("\\")>0){
				//File.separator不用能,因为在linux File.separator="/"
				//针对IE,Chrome路径问题
				icon = icon.substring(icon.lastIndexOf("\\")+1);
			}
			String path = UUID.randomUUID().toString().replaceAll("-", "")+"_"+icon;
			File rootDir = new File(ConfigPropertieUtils.getString("media.resource.images.column.path"));
			try{
				if(!rootDir.exists()){
					rootDir.mkdirs();
				}
				File iconFile = new File(rootDir,path);
				if(!iconFile.exists()){
					iconFile.createNewFile();
				}
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(iconFile));
			} catch (Exception e) {
				LogUtil.error(logger,"{0}新建栏目时,保存栏目图标出错",e, UsercmsUtils.getCurrentUser().getLoginName());
			}
			column.setIcon(path);
		}
		String path = column.getPath();
		if(null != path && path.trim().length()>0){
			column.setPath(path.concat("-").concat(column.getCode()));
		}else{
			column.setPath(column.getCode());
		}
		column.setIsactiverForever(true);//长期有效
		column.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		column.setCreateDate(DateUtil.format(new Date(),DateUtil.DATE_PATTERN));

		this.service.save(column);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 创建栏目:"+column.getName()+" 成功");
		if(null != isCallBack){
			return "redirect:list.go?parentId="+column.getParentId()+"&isCallBack="+isCallBack
					+"&codeId="+codeId+"&nameId="+nameId;
		}else{
			return "redirect:list.go?parentId="+column.getParentId();
		}
		
	}
	
	@RequestMapping(value="/update")
	public String update(Column column,@RequestParam final  String iconOld, @RequestParam(value="upfilename",required=false) MultipartFile file,
			@RequestParam(required=false)final Integer isCallBack,@RequestParam(required=false)final String codeId,
			@RequestParam(required=false)final String nameId) {
		String icon =  column.getIcon();
		if(null!=icon &&(file!=null && file.getSize() > 0)){
			if((null!=iconOld&&!iconOld.isEmpty())&&icon.equals(iconOld)){
				//图标没有修改
			}else{
				//
				if(icon.indexOf("\\")>0){
					//针对IE,Chrome路径问题
					icon = icon.substring(icon.lastIndexOf("\\")+1);
				}
				String path = UUID.randomUUID().toString().replaceAll("-", "")+"_"+icon;
				File rootDir = new File(ConfigPropertieUtils.getString("media.resource.images.column.path"));
				
				File oldIcon = new File(rootDir,iconOld);
				if(oldIcon.exists()){
					oldIcon.delete();
					LogUtil.info(logger,"删除栏目旧图标:"+oldIcon.getPath()+" 栏目:"+column);
				}
				try{
					if(!rootDir.exists()){
						rootDir.mkdirs();
					}
					File iconFile = new File(rootDir,path);
					if(!iconFile.exists()){
						iconFile.createNewFile();
					}
					FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(iconFile));
				} catch (Exception e) {
					LogUtil.error(logger,"{0}修改栏目时,保存栏目图标出错",e, UsercmsUtils.getCurrentUser().getLoginName());
				}
				column.setIcon(path);
			}
		}
		column.setModifer(UsercmsUtils.getCurrentUser().getLoginName());
		column.setModifer(DateUtil.format(new Date(),DateUtil.DATE_PATTERN));
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 修改栏目:"+column.getName()+" 成功");
		service.update(column);
		if(null != isCallBack){
			return "redirect:sub.go?parentId="+column.getParentId()+"&isCallBack="+isCallBack
					+"&codeId="+codeId+"&nameId="+nameId;
		}else{
			return "redirect:sub.go?parentId="+column.getParentId();
		}

	}
	

}