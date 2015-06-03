package com.dangdang.digital.controlller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import org.springframework.ui.Model;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ConstantEnum;
import com.dangdang.digital.controlller.BaseController;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.ListCategory;
import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IListCategoryService;
import com.dangdang.digital.service.IListRankingService;
import com.dangdang.digital.service.IMediaStatisticsService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;


/**
 * 
 * Description: 榜单分类信息控制器
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 上午10:30:45  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Controller
@RequestMapping("ranking")
public class ListRankingController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private ICacheApi  cacheApi;
	
	@Resource ICatetoryService catetoryService;
	
	@Resource IListCategoryService listCategoryService;
	
	//销售榜单
	@Resource IListRankingService listRankingService;
	
	//Media统计数据
	@Resource IMediaStatisticsService  mediaStatisticsService;
	
	
	
	@RequestMapping(value="/checkcode")
	@ResponseBody
	public String checkColumnCode(String categoryCode){
		ListCategory listCategory = listCategoryService.findUniqueByParams("categoryCode",categoryCode);
		if(null !=listCategory){
			return "{\"result\":\"failure\"}";
		}else{
			//编号已存在
			return "{\"result\":\"success\"}";
		}
	}
	@RequestMapping(value="/sales/query")
	public String listSingleSales(String pageIndex,final Model model,
			@RequestParam final String listType,
			 final Long mediaId,
			 final Long saleId,
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
		
		if(StringUtils.isNotBlank(mediaCategoryIds)){
			paramMap.put("mediaCategoryIds", mediaCategoryIds);
		}
		
		if(StringUtils.isNotBlank(listType)){
			paramMap.put("list_type", listType);
		}
		if(StringUtils.isNotBlank(mediaName)){
			paramMap.put("mediaName", mediaName);
		}
		if(StringUtils.isNotBlank(path)){
			int index = path.indexOf("-");
			if(index > 0 ){
				path = path.substring(0,index);
			}
			paramMap.put("path", path);
		}
		
		if(null!= mediaId ){
			paramMap.put("mediaId", mediaId);
		}
		if(null!= saleId ){
			paramMap.put("saleId", saleId);
		}
		if(StringUtils.isNotBlank(authorPenname)){
			paramMap.put("authorPenname", authorPenname);
		}
		PageFinder<Map<?,?>> pageFinder = listRankingService.getSingelSales(paramMap, query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("mediaCategoryTree", getMediaCategoryTree(path));
		model.addAttribute("listType", listType);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" path="+path+" 查询单品销售主体[销售主体名称="+mediaName+",mediaId="+mediaId+" author_penname="+authorPenname);
		return "ranking/sales";
	}
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
	
	/**
	 * 
	 * Description: 判断节点 lc是不是非叶子节点
	 * @Version1.0 2015年3月9日 下午1:42:26 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param lc
	 * @param listCategory
	 * @return
	 */
	private boolean isParent(ListCategory lc,List<ListCategory> listCategory){
		for(ListCategory temp:listCategory){
			if(temp.getParentId()==lc.getCategoryId()){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * Description: 分类榜单
	 * @Version1.0 2015年3月9日 下午2:09:43 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param listCategoryPath 榜单标识path
	 * @param query
	 * @param model
	 * @param lr
	 * @return
	 */
	@RequestMapping("categoryranking")
	public String categoryRanking(final String listCategoryPath,final Integer pageIndex,final Model model,ListRanking lr){
		String mediaPath = listCategoryPath;
		//查询出该分类下的榜单,只支持三级节点
		Map<String,String> map = new HashMap<String,String>();
		if(null != mediaPath){
			ListCategory lcParam =  new ListCategory();
			lcParam.setPath(mediaPath);
			List<ListCategory> listCategory = this.listCategoryService.findListByParamsObjs(lcParam);
			List<ListCategory> leafCategory = new LinkedList<ListCategory>();
			//删除非叶子节点
			for(ListCategory lc:listCategory){
				if(!isParent(lc, listCategory)){
					leafCategory.add(lc);
					map.put(lc.getCategoryCode(), lc.getCategoryName());
				}
			}
			model.addAttribute("listCategory", leafCategory);
		}
		Query  query = new Query();
		if(null == pageIndex){
			query.setPage(1);
		}else{
			query.setPage(pageIndex);
		}
		//需要查询出最近一期的记录
		PageFinder<ListRanking> pageFinder = listRankingService.getCategoryListRanking(lr,query);
		List<ListRanking> listData =  pageFinder.getData();
		for(ListRanking temp:listData){
			temp.setListType(map.get(temp.getListType()));
		}
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("listType", lr.getListType());
		model.addAttribute("on_shelf", Constans.MEDIA_SHELF_STATUS_UP);
		model.addAttribute("off_shelf", Constans.MEDIA_SHELF_STATUS_DOWN);
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("force_valid", ConstantEnum.SALE_STATUS_FORCE_VALID.getValue());
		model.addAttribute("force_invalid", ConstantEnum.SALE_STATUS_FORCE_INVALID.getValue());
		return "ranking/categoryranking";
	}
	@RequestMapping(value="/category")
	public String listMediaCategoryTree(final Model model){
		List<Catetory> list = catetoryService.findListByParamsObjs(new Catetory());
		StringBuilder treeHtml = new StringBuilder("[{\"id\":\"0\",\"name\":\"资源分类\",\"open\":true,},");
		for(Catetory cate : list){
			treeHtml.append("{\"id\":\""+cate.getId()+"\",");
			treeHtml.append("\"name\":\""+cate.getName()+"\",");
			treeHtml.append("\"code\":\""+cate.getCode()+"\",");
			treeHtml.append("\"path\":\""+cate.getPath()+"\",");
			treeHtml.append("\"pId\":\"").append(cate.getParentId()==null?"0":cate.getParentId()).append("\"},");
		}
		if(null != list && list.size()>0){
			treeHtml.deleteCharAt(treeHtml.length()-1);
		}
		treeHtml.append("]");
		System.out.println(treeHtml);
		model.addAttribute("treeHtml", treeHtml);
		return "ranking/category";
	}
	/**
	 * 
	 * Description: 查询出所有的榜单信息
	 * @Version1.0 2014年12月2日 下午3:30:45 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param pageIndex
	 * @param model
	 * @param listCategory
	 * @return
	 */
	@RequestMapping(value="/listinfo")
	public String getListCategoryInfo(String pageIndex,final Model model,final ListCategory listCategory){
		model.addAttribute("listCategory", listCategoryService.findListByParams("path",listCategory.getPath()));
		return "ranking/listinfo";
	}
	
	/**
	 * 
	 * Description: 查询出更新了Media信息
	 * @Version1.0 2014年12月9日 下午4:12:19 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param pageIndex
	 * @param model
	 * @param listCategory
	 * @return
	 */
	@RequestMapping(value="/mediaupdate")
	public String getUpdatedMedias(String pageIndex,final Model model,final ListRanking listRanking){
		ListCategory listCategory = listCategoryService.getListCategoryByCategoryCode(listRanking.getListType());
		listRanking.setIssue(listCategory.getLastListIssue());
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		PageFinder<ListRanking> pageFinder = listRankingService.findPageFinderObjs(listRanking,query);
		Map<String,Object> paramObj = new HashMap<String,Object>();
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("listType", listRanking.getListType());
		model.addAttribute("on_shelf", Constans.MEDIA_SHELF_STATUS_UP);
		model.addAttribute("off_shelf", Constans.MEDIA_SHELF_STATUS_DOWN);
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("force_valid", ConstantEnum.SALE_STATUS_FORCE_VALID.getValue());
		model.addAttribute("force_invalid", ConstantEnum.SALE_STATUS_FORCE_INVALID.getValue());
		//榜单日期
		model.addAttribute("orederDimension",listCategory.getOrederDimension());
		return "ranking/updatedmedia";
	}
	
	@RequestMapping(value="/medianewest")
	public String getNewestMedias(String pageIndex,final Model model,String sexChannel,final MediaStatistics mediaStatistics){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		Map<String,Object> paramObj = new HashMap<String,Object>();
		paramObj.put("sexChannel", sexChannel);
		paramObj.put("order_column", "media_creation_date");
		PageFinder<MediaStatistics> pageFinder = mediaStatisticsService.findPageFinderObjs(paramObj,query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("sexChannel", sexChannel);
		return "ranking/newestmedia";
	}
	
	@RequestMapping(value="/list")
	public String getListCategory(String pageIndex,final Model model,final ListCategory listCategory){
		model.addAttribute("listCategory", listCategoryService.getAll());
		return "ranking/list";
	}
	
	@RequestMapping(value="/rank")
	public String getListRanking(String pageIndex,final Model model,final ListRanking listRanking){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		ListCategory listCategory = listCategoryService.getListCategoryByCategoryCode(listRanking.getListType());
		listRanking.setIssue(listCategory.getLastListIssue());
		PageFinder<ListRanking> pageFinder = listRankingService.findPageFinderObjs(listRanking,query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("listType", listRanking.getListType());
		model.addAttribute("on_shelf", Constans.MEDIA_SHELF_STATUS_UP);
		model.addAttribute("off_shelf", Constans.MEDIA_SHELF_STATUS_DOWN);
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("force_valid", ConstantEnum.SALE_STATUS_FORCE_VALID.getValue());
		model.addAttribute("force_invalid", ConstantEnum.SALE_STATUS_FORCE_INVALID.getValue());
		//榜单日期
		model.addAttribute("orederDimension",listCategory.getOrederDimension());
		return "ranking/rank";
	}
	/**
	 * 
	 * Description: 评星榜
	 * @Version1.0 2014年12月29日 下午3:20:00 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param pageIndex
	 * @param model
	 * @param listRanking
	 * @return
	 */
	@RequestMapping(value="/commentsrank")
	public String getCommentsListRanking(String pageIndex,final Model model,final ListRanking listRanking){
		Query query = new Query();
		if(StringUtils.isNotBlank(pageIndex)){
			query.setPage(Integer.parseInt(pageIndex));
		}else{
			query.setPage(1);
		}
		ListCategory listCategory = listCategoryService.getListCategoryByCategoryCode(listRanking.getListType());
		listRanking.setIssue(listCategory.getLastListIssue());
		PageFinder<ListRanking> pageFinder = listRankingService.findPageFinderObjs(listRanking,query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("listType", listRanking.getListType());
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("force_valid", ConstantEnum.SALE_STATUS_FORCE_VALID.getValue());
		model.addAttribute("force_invalid", ConstantEnum.SALE_STATUS_FORCE_INVALID.getValue());
		model.addAttribute("orederDimension",listCategory.getOrederDimension());
		return "ranking/commentsrank";
	}
	
	@RequestMapping(value="/delete")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String delete(@RequestParam final Integer categoryId,final String categoryCode){
		this.listCategoryService.deleteById(categoryId);
		this.listRankingService.deleteByByParams("listType",categoryCode);
		//更新缓存
		cacheApi.cleanCacheByKey(Constans.RANKING_LIST_CACHE_KEY+categoryCode);
		return "redirect:list.go";
	}
	@RequestMapping(value="/deleterecord")
	public  String deleteListRankRecord(final Long[] listId,String listType){
		this.listRankingService.deleteByIds(Arrays.asList(listId));
		return "redirect:rank.go?listType="+listType;
	}
	@RequestMapping(value="/edit")
	public String edit(@RequestParam final int id,final Model model){
		ListCategory listCategory = this.listCategoryService.get(id);
		model.addAttribute("list", listCategory);
		return "ranking/edit";
	}
	@RequestMapping(value="/add")
	public String add(final Model model){
		model.addAttribute(ConstantEnum.CHANNEL_ALL.getCode(), ConstantEnum.CHANNEL_ALL.getCode());
		model.addAttribute(ConstantEnum.CHANNEL_NP.getCode(), ConstantEnum.CHANNEL_NP.getCode());
		model.addAttribute(ConstantEnum.CHANNEL_VP.getCode(), ConstantEnum.CHANNEL_VP.getCode());
		return "ranking/add";
	}
	
	@RequestMapping(value="/save")
	public String save(final ListCategory listCategory){
		//第一次添加榜单时,设置其榜单已分析记录号为0
		listCategory.setLastRecordId(0L);
		if(null != listCategory.getPath() && !listCategory.getPath().trim().isEmpty()){
			//路径
			listCategory.setPath(listCategory.getPath().concat("-").concat(listCategory.getListCode()));
		}
		listCategory.setCreator(UsercmsUtils.getCurrentUser().getLoginName());
		listCategory.setCreateDate(DateUtil.format(new Date(), DateUtil.DATE_PATTERN));
		listCategoryService.save(listCategory);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 创建榜单 "+listCategory);
		return "redirect:list.go";
		
	}
	
	/**
	 * 
	 * Description: 修改榜单排序值
	 * @Version1.0 2014年12月23日 上午10:00:33 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param listId			记录Id集合
	 * @param appointOrder		记录Id对应排序值集合
	 * @param listType			榜单类型
	 * @param orederDimension	
	 * @return
	 */
	@RequestMapping(value="/updateorder")
	public String updateOrder(Long[] listIds,Integer[] appointOrder,String listType){
		List<ListRanking> rankingList = new ArrayList<ListRanking>(listIds.length);
		for(int i=0,len = listIds.length;i<len;i++){
			if(null!=appointOrder[i]){
				ListRanking lr = new ListRanking();
				lr.setListId(listIds[i]);
				lr.setAppointOrder(appointOrder[i]);//指定排序值
				lr.setLastChangeDate(DateUtil.format(new Date(), DateUtil.DATE_PATTERN));
				lr.setOperator(UsercmsUtils.getCurrentUser().getLoginName());
				rankingList.add(lr);
			}
			
		}
		if(rankingList.size()>0){
			listRankingService.updateListRanking(rankingList);
		}
		return "redirect:rank.go?listType="+listType;
		
	}
	
	
	@RequestMapping(value="/update")
	public String update(ListCategory listCategory){
		listCategory.setModifer(UsercmsUtils.getCurrentUser().getLoginName());
		listCategory.setLastChangeDate(DateUtil.format(new Date(), DateUtil.DATE_PATTERN));
		listCategoryService.update(listCategory);
		//更新缓存
		cacheApi.cleanCacheByKey(Constans.RANKING_LIST_CACHE_KEY+listCategory.getCategoryCode());
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 修改榜单 "+listCategory);
		return "redirect:list.go";
		
	}
	
	/**
	 * 
	 * Description: 修改榜单记录状态
	 * @Version1.0 2014年12月23日 上午10:02:46 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param contentIds
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/editstatus")
	public String redircetEditsSaleContentStatus(@RequestParam String listIds,@RequestParam String listType,final Model model){
		model.addAttribute("listIds", listIds);
		model.addAttribute("listType", listType);
		model.addAttribute("normal", ConstantEnum.SALE_STATUS_NORMAL.getValue());
		model.addAttribute("force_valid", ConstantEnum.SALE_STATUS_FORCE_VALID.getValue());
		model.addAttribute("force_invalid", ConstantEnum.SALE_STATUS_FORCE_INVALID.getValue());
		return "/ranking/editstatus";//+ids;
	}
	
	/**
	 * 
	 * Description: 修改榜单数据状态
	 * @Version1.0 2014年12月24日 上午11:23:32 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param listIds	
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updatestatus")
	public String updateSaleContentStatus(@RequestParam int status,@RequestParam String listType, @RequestParam String listIds){
		String modifer = UsercmsUtils.getCurrentUser().getLoginName();
		listRankingService.updateListRankingStatus(status,listIds,modifer);
		return "redirect:rank.go?listType="+listType;//+ids;
	}
	

	
	/**
	 * 
	 * Description: 导入榜单销售主体
	 * @Version1.0 2014年11月20日 下午2:47:38 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/content/import")
	@ResponseBody
	public String importColumnContent(final String listType,@RequestParam final  String saleIds,final Model model){
		Map<String,Object> paramObj = new HashMap<String,Object>();
		ListCategory listCategory = this.listCategoryService.getListCategoryByCategoryCode(listType);
		paramObj.put("list_type", listType);
		paramObj.put("sale_ids", saleIds);
		paramObj.put("issue", listCategory.getLastListIssue());
		paramObj.put("status",ConstantEnum.SALE_STATUS_NORMAL.getValue());
		paramObj.put("operator", UsercmsUtils.getCurrentUser().getLoginName());
		this.listRankingService.insertBatch(paramObj);
		LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" ");
		return "{\"result\":\"success\"}";
	}
	
	
}

