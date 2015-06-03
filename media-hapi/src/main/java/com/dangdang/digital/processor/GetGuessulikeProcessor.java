package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.digital.api.IRankingListApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.facade.IRankListApiFacade;
import com.dangdang.digital.model.CustomerBehaviorRecord;
import com.dangdang.digital.model.CustomerMediaHistory;
import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.ICustomerBehaviorRecordService;
import com.dangdang.digital.service.ICustomerMediaHistoryService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.IMediaStatisticsService;
import com.dangdang.digital.service.IRecommendRelationService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.utils.MediaCoverPicUrlUtil;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.RecommendUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.view.CustomerMediaData;
import com.dangdang.digital.view.MediaCategoryData;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 根据客户端传来的 mediaId 获得买了此商品的人都还买了哪些商品
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:36:02  by 魏嵩（weisong@dangdang.com）创建
 */

@Component("hapigetGuessulikeprocessor")
public class GetGuessulikeProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetGuessulikeProcessor.class);

	private static List<String> defaultReturnFields = new ArrayList<String>();
	
	private static int behaviorRecommendNumber = 8;
	
	private static int buyRecommendNumber = 6;
	
	private static int browseRecommendNumber = 6;
	
	private static int maxRecommendNumber = 100;
	
	@Resource
	IRankingListApi rankingListApi;
	
	static{
		defaultReturnFields.add("coverPic");
		defaultReturnFields.add("authorPenname");
		defaultReturnFields.add("title");
		defaultReturnFields.add("subTitle");
		defaultReturnFields.add("saleId");
	}

	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate; 
	
	@Resource
	private IMediaService mediaService;
	
	@Resource
	private IMediaSaleService mediaSaleService;
	
	@Resource
	private IRecommendRelationService recommendRelationService;
	
	@Resource
	private ICustomerBehaviorRecordService customerBehaviorRecordService;
	
	@Resource
	private ICustomerMediaHistoryService customerMediaHistoryService;
	
	@Resource
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IMediaStatisticsService mediaStatisticsService;
	
	@Resource
	private IRankListApiFacade rankListApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		getGuessulike(request, response, sender);
	}

	/**
	 * 
	 * Description: 
	 * @Version1.0 2014年12月11日 下午4:23:33 by 魏嵩（weisong@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void getGuessulike(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		String startStr = request.getParameter("start");
		String endStr = request.getParameter("end");
		String tokenStr = request.getParameter("token");
		boolean anonymous = true;
		Long custId = 0L;
			
		if(!StringUtils.isEmpty(tokenStr)){
			UserTradeBaseVo vo =  authorityApiFacade.getUserInfoByToken(tokenStr);
			if(! (vo==null || vo.getCustId()==null) ){
				custId = vo.getCustId();
				anonymous = false;
			}
//			custId = Long.parseLong(request.getParameter("custId"));
		}
		
		if (!StringUtils.isNumeric(startStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_14602.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14602.getErrorMessage(), response);
			return;
		}
		if (!StringUtils.isNumeric(endStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_14603.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14603.getErrorMessage(), response);
			return;
		}
		int start = 0;
		int end = 0;
		try{
			start = Integer.valueOf(startStr);
			if(start<0){
				start=0;
			}
			if(start>(Constans.RECOMMEND_GUESSULIKE_MAX_NUMBER-1)){
				start=(Constans.RECOMMEND_GUESSULIKE_MAX_NUMBER-1);
			}
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,start:" + startStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_14002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14002.getErrorMessage(), response);
			return;
		}
		
		try{
			end = Integer.valueOf(endStr);
			if(end<0){
				end=0;
			}
			if(end>(Constans.RECOMMEND_GUESSULIKE_MAX_NUMBER-1)){
				end = Constans.RECOMMEND_GUESSULIKE_MAX_NUMBER-1;
			}
			if(end<start){
				start=0;
				end=29;
			}
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,end:" + endStr);
			sender.fail(ErrorCodeEnum.ERROR_CODE_14003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_14003.getErrorMessage(), response);
			return;
		}
		
		//需要return书的哪些可选字段，值为空表示返回所有 （mediaId必返回）
		String returnFields = request.getParameter("returnFields");
		List<String> returnFieldList = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(returnFields)){
			String[] array = Constans.commaSpliter.split(returnFields);
			for(String field: array){
				returnFieldList.add(field);
			}
		}else{
			returnFieldList.addAll(defaultReturnFields);
		}
		
		//最终参考的书的列表
		List<Long> customerBooks = new ArrayList<Long>();
		List<Long> recommendBooksResult = new ArrayList<Long>();
		if(!anonymous){
		
			//若是0-4点，自动读取昨天和今天的用户行为，综合在一起推荐； 若0-4点，用户购买列表和浏览列表都会取前一天的数据
			Date buyBrowseHistoryDate = DateUtil.getOnlyDay(new Date());
			if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)<=4){
				buyBrowseHistoryDate = DateUtil.addDate(buyBrowseHistoryDate, -1);
			}
			
			Date todayMorning = DateUtil.getOnlyDay(new Date());
			
			TreeMap<Long, Long> customerBehaviorMap = getCurrentCustomerBehaviorMap(custId, todayMorning);
			if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)<=4){
				TreeMap<Long, Long>  customerBehaviorMapYesterDay = getCurrentCustomerBehaviorMap(custId, DateUtil.addDate(todayMorning, -1));
				customerBehaviorMap = RecommendUtils.mergeBehaviorMap(customerBehaviorMap, customerBehaviorMapYesterDay);
			}
			
			// 当天浏览记录取前behaviorRecommendNumber个
			int customerBehaviorNumber = customerBehaviorMap.size()>behaviorRecommendNumber?behaviorRecommendNumber:customerBehaviorMap.size();
			
			int i = 0;
			for(Long timeKey: customerBehaviorMap.descendingKeySet()){
				if(i<customerBehaviorNumber){
					customerBooks.add(customerBehaviorMap.get(timeKey));
					i++;
				}else{
					break;
				}
			}
			
			//用户购买记录50天 取前buyRecommendNumber个	// 若是0-4点，那么50天记录选昨天分析结果	
			
			LinkedList<Long> buyList = new LinkedList<Long>();
			LinkedList<Long> buyListBak = new LinkedList<Long>();
			
			CustomerMediaHistory customerMediaHistory = new CustomerMediaHistory();
			customerMediaHistory.setCustId(custId);
			customerMediaHistory.setCreationDate(buyBrowseHistoryDate);
			customerMediaHistory.setType((byte)1);
			
			CustomerMediaHistory history = customerMediaHistoryService.getHistory(customerMediaHistory);
			CustomerMediaData custBoughtData = new CustomerMediaData();
			if(history!=null){
				custBoughtData = JSON.parseObject(history.getMediaData(), CustomerMediaData.class );
				if(custBoughtData!=null && custBoughtData.getHistory() !=null){
					buyList.addAll(custBoughtData.getHistory());
					buyListBak.addAll(custBoughtData.getHistory());
				}
			}
			//购买列表去掉当天的浏览记录
			buyList.removeAll(customerBehaviorMap.values());
			
			//用户浏览记录50天 去掉购买去掉当天浏览取browseRecommendNumber个 // 若是0-4点，那么50天记录选昨天分析结果	
			LinkedList<Long> browseList = new LinkedList<Long>();
			customerMediaHistory.setType((byte)2);
			history = customerMediaHistoryService.getHistory(customerMediaHistory);
			if(history!=null){
				CustomerMediaData custData = JSON.parseObject(history.getMediaData(), CustomerMediaData.class );
				if(custData!=null && custData.getHistory() !=null){
					browseList.addAll(custData.getHistory());
				}
			}
			//浏览历史去掉今天的浏览记录、购买历史
			browseList.removeAll(customerBehaviorMap.values());
			browseList.removeAll(buyList);
			
			int buyListNumber = buyList.size()>buyRecommendNumber?buyRecommendNumber:buyList.size();
			i=0;
			// buyList和browseList都是用户行为最新的在前面
			for(Iterator<Long> it=buyList.iterator(); it.hasNext()&&i<buyListNumber; i++){
				customerBooks.add(it.next());
			}
			
			int browseListNumber = browseList.size()>browseRecommendNumber?browseRecommendNumber:browseList.size();
			i=0;
			for(Iterator<Long> it=browseList.iterator(); it.hasNext()&&i<browseListNumber; i++){
				customerBooks.add(it.next());
			}
			
			
			List<String> redisKeyList = new ArrayList<String>();
			//统计分类
			for(Long mediaId: customerBooks){
				redisKeyList.add(Constans.RECOMMEND_MEDIA_CATEGORIES_KEY+mediaId);
			}
			
			List<Object> valuesFromRedis = slaveRedisTemplate.opsForValue().multiGet(redisKeyList);
			Map<Long, List<String>> mediaToCategoryMap = new HashMap<Long, List<String>>();
			
			int totalCategoryNumber = 0;
			if(valuesFromRedis!=null){
				for(Object obj:valuesFromRedis){
					MediaCategoryData mediaCategoryData = (MediaCategoryData)obj;
					if(mediaCategoryData!=null && mediaCategoryData.getCategoryList()!=null){
						mediaToCategoryMap.put(mediaCategoryData.getMediaId(), mediaCategoryData.getCategoryList());
						totalCategoryNumber+=mediaCategoryData.getCategoryList().size();
					}
				}
			}
			
			Set<String> categoriesSet = new HashSet<String>();
			
			for(Long mediaId: customerBooks){
				
				if(mediaToCategoryMap.get(mediaId)==null){
					//如果缓存里没有，去DB取完放缓存
					MediaStatistics statistic = new MediaStatistics();
					statistic.setStatisticsDay(DateUtil.getOnlyDay(new Date()));
					statistic.setMediaId(mediaId);
					List<String> categories = mediaStatisticsService.getMediaCategoryListToCache(statistic);
					if(categories!=null && categories.size()>0){
						mediaToCategoryMap.put(mediaId, categories);
						//这里不判断重复，只是category的"人次"
						totalCategoryNumber+=categories.size();
						categoriesSet.addAll(categories);
					}
				}else{
					categoriesSet.addAll(mediaToCategoryMap.get(mediaId));
				}
			}
			
			//开始推荐
			
			Query query = new Query();
			query.setPage(1);
			query.setPageSize(200);
			
			if(totalCategoryNumber>0){
			
				//算法，每个分类推几本书（包括重复）
				int eachCategoryRecommendNumber = SafeConvert.convertDoubleToInteger(Math.ceil((double)maxRecommendNumber/(double)totalCategoryNumber));
				
				//拿到每个分类下的热销书（调度）
				Map<String, List<Long>> categoryHotBooks = mediaStatisticsService.getMultiCategoryHotBooks(DateUtil.addDate(DateUtil.getOnlyDay(new Date()), -7), categoriesSet, query);
				
				if(categoryHotBooks == null){
					categoryHotBooks = new HashMap<String, List<Long>>();
				}
				
				for(String key: categoryHotBooks.keySet()){
					
					List<Long> categoryHotBookList = categoryHotBooks.get(key);
					if(categoryHotBookList !=null){
						//去掉购买记录
						if(custBoughtData!=null && custBoughtData.getHistory()!=null){
							categoryHotBookList.removeAll(custBoughtData.getHistory());
						}
						categoryHotBooks.put(key, categoryHotBookList);
					}
				}
				
				
				
				Map<String, Integer> categoryHotBooksListIndex = new HashMap<String, Integer>();
				
				
				for(Long mediaId: customerBooks){
					
					List<String> categories = mediaToCategoryMap.get(mediaId);
					
					if(categories!=null){
						for(String category: categories){
							
							List<Long> categoryHotBookList = categoryHotBooks.get(category);
							// 如果缓存没有，去DB查 此category对应的热销书
							if(categoryHotBookList == null){
								
								categoryHotBookList = mediaStatisticsService.getBestSellerOfSpecifiedCategory(DateUtil.addDate(DateUtil.getOnlyDay(new Date()), -7), category, query);
								
								if(categoryHotBookList !=null){
									//去掉购买记录
									if(custBoughtData!=null && custBoughtData.getHistory()!=null){
										categoryHotBookList.removeAll(custBoughtData.getHistory());
									}
									categoryHotBooks.put(category, categoryHotBookList);
								}
								
							}
							if(categoryHotBookList!=null && categoryHotBookList.size()>0){
								
								Integer minIndex = categoryHotBooksListIndex.get(category);
								if(minIndex == null){
									minIndex = 0;
									categoryHotBooksListIndex.put(category, minIndex);
								}
								
								if(minIndex > categoryHotBookList.size()-1){
									continue;
								}
								
								int maxIndex = minIndex+eachCategoryRecommendNumber-1;
								
								if(categoryHotBookList.size()-1 < maxIndex){
									maxIndex = categoryHotBookList.size()-1;
								}
								
								for(int it= minIndex; it<=maxIndex && maxIndex<=categoryHotBookList.size()-1; it++){
									
									Long recommendBookId = categoryHotBookList.get(it);
									//去重, 并且去掉购买过的
									if(recommendBooksResult.contains(recommendBookId) || buyListBak.contains(recommendBookId)){ 
										maxIndex++;
										continue;
									}else{
										recommendBooksResult.add(recommendBookId);
									}
								}
								categoryHotBooksListIndex.put(category, maxIndex+1);
							}
						}
					}
				}
			}
		}
		
		int total = 0;
		LinkedHashMap<Long, MediaCacheBasicVo> mediaBasicInfoCacheMap = new LinkedHashMap<Long, MediaCacheBasicVo>(); 
		
		//第一种情况，完全不用补
		if(recommendBooksResult.size()>=(end+1)){
			List<Long> recommendBooks = recommendBooksResult.subList(start, (end+1));
			mediaBasicInfoCacheMap = (LinkedHashMap<Long, MediaCacheBasicVo>)mediaService.getMediaBasicMap(recommendBooks);
			total = 100;
			
		}else if(((recommendBooksResult.size()-1)<start)  || (start<=(recommendBooksResult.size()-1)&&end>(recommendBooksResult.size()-1))){
			//第二、三种情况，完全靠补 或者两部分数据都占用
			
			//取男频热销榜、女频热销榜补齐
			int supplementNumber = maxRecommendNumber - recommendBooksResult.size();
			int eachSupplementNumber = SafeConvert.convertDoubleToInteger(Math.ceil((double)supplementNumber/2D));
			//从男频、女频热销榜各取出eachSupplementNumber个补齐
			//拿到男频女频的热销saleIds，根据saleId去查书的ids, 再补齐
			List<MediaCacheWholeVo> npList = rankListApiFacade.getRandomMediaListByTypeAndNumber("np_sale", eachSupplementNumber);
			List<MediaCacheWholeVo> vpList = rankListApiFacade.getRandomMediaListByTypeAndNumber("vp_sale", eachSupplementNumber);
			LinkedHashMap<Long, MediaCacheBasicVo> mediaBasicInfoCacheMapBak = new LinkedHashMap<Long, MediaCacheBasicVo>(); 
			buildSupplementDataToCacheMap(mediaBasicInfoCacheMapBak, npList, vpList);
			List<Long> list = new ArrayList<Long>(mediaBasicInfoCacheMapBak.keySet());
			
			//第二种情况，完全靠补 
			if(list.size()>0 && (recommendBooksResult.size()-1)<start){
				
				int newStart = start-recommendBooksResult.size();
				int newEnd = end-recommendBooksResult.size();
				//做保护
				newStart = (list.size()-1)<newStart?0:newStart;
				newEnd = (list.size()-1)<newEnd?(list.size()-1):newEnd;
				list = list.subList(newStart, (newEnd+1));
				
				for(Long mediaId:list){
					mediaBasicInfoCacheMap.put(mediaId, mediaBasicInfoCacheMapBak.get(mediaId));
				}
			}else if(start<=(recommendBooksResult.size()-1)&&end>(recommendBooksResult.size()-1)){
				//第三种情况，两部分数据都占用
				int newEnd = end-recommendBooksResult.size();
				
				List<Long> recommendBooks = recommendBooksResult.subList(start, recommendBooksResult.size());
				mediaBasicInfoCacheMap = (LinkedHashMap<Long, MediaCacheBasicVo>)mediaService.getMediaBasicMap(recommendBooks);
				
				if(list.size()>0){
					newEnd = (list.size()-1)<newEnd?(list.size()-1):newEnd;
					list = list.subList(0, (newEnd+1));
					for(Long mediaId:list){
						mediaBasicInfoCacheMap.put(mediaId, mediaBasicInfoCacheMapBak.get(mediaId));
					}
				}
			}
			total = mediaBasicInfoCacheMapBak.size()+recommendBooksResult.size();
			total = total>Constans.RECOMMEND_GUESSULIKE_MAX_NUMBER?Constans.RECOMMEND_GUESSULIKE_MAX_NUMBER:total;
		}
		
	/*	//推荐完毕，如果不够，那么补齐
		if(recommendBooksResult.size()<maxRecommendNumber){
			
			//取男频热销榜、女频热销榜补齐
			int supplementNumber = maxRecommendNumber - recommendBooksResult.size();
			int eachSupplementNumber = SafeConvert.convertDoubleToInteger(Math.ceil((double)supplementNumber/2D));
			//从男频、女频热销榜各取出eachSupplementNumber个补齐
			//拿到男频女频的热销saleIds，根据saleId去查书的ids, 再补齐
			List<MediaCacheWholeVo> npList = rankListApiFacade.getRandomMediaListByTypeAndNumber("np_sale", eachSupplementNumber);
			List<MediaCacheWholeVo> vpList = rankListApiFacade.getRandomMediaListByTypeAndNumber("vp_sale", eachSupplementNumber);
			buildSupplementDataToCacheMap(mediaBasicInfoCacheMap, npList, vpList);
		}*/
		
		List<Map<String, Object>> guessulikeList = new ArrayList<Map<String, Object>>();
		
		for (Long alsoBuyMediaId : mediaBasicInfoCacheMap.keySet()) {
			
			MediaCacheBasicVo media = mediaBasicInfoCacheMap.get(alsoBuyMediaId);
			if(media == null || media.getMediaId() == null){
				LogUtil.error(LOGGER, "Media:{0} 在缓存不存在", alsoBuyMediaId);
				continue;
			}
			
			if(media.getSaleId() == null){
				LogUtil.error(LOGGER, "MediaId为:{0} 的MediaSale信息在缓存不存在", alsoBuyMediaId);
				continue;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mediaId", media.getMediaId());
			
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("coverPic"))){
				map.put("coverPic", MediaCoverPicUrlUtil.getMediaCoverPic(media.getMediaId()));
			}
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("authorPenname"))){
				map.put("authorPenname", media.getAuthorPenname());
			}
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("title"))){
				map.put("title", media.getTitle());
			}
			if(returnFieldList.size()==0 || (returnFieldList.size()>0 && returnFieldList.contains("subTitle"))){
				map.put("subTitle", media.getSubTitle());
			}
			map.put("saleId", media.getSaleId());
			
			guessulikeList.add(map);
		}

		// 返回值：买了又买列表
		sender.put("mediaList", guessulikeList);
		sender.put("total", total);
		sender.put("timeCost", System.currentTimeMillis()-startTime);
		sender.success(response);
		
	}

	private TreeMap<Long, Long> getCurrentCustomerBehaviorMap(Long custId, Date date) {
		CustomerBehaviorRecord queryObj = new CustomerBehaviorRecord();
		queryObj.setCustId(custId);
		queryObj.setCreateDate(date);
		queryObj.setBehaviorType((byte)1);
		CustomerBehaviorRecord record = customerBehaviorRecordService.getRecord(queryObj);
		
		TreeMap<Long, Long> customerBehaviorMap = new TreeMap<Long, Long>();
		
		if(record!=null){
			customerBehaviorMap = JSON.parseObject(record.getCustBehaviorData(), new TypeReference<TreeMap<Long, Long>>(){});
			if(customerBehaviorMap == null){
				customerBehaviorMap = new TreeMap<Long, Long>();
			}
		}
		return customerBehaviorMap;
	}

	private void buildSupplementDataToCacheMap(
			Map<Long, MediaCacheBasicVo> mediaBasicInfoCacheMap,
			List<MediaCacheWholeVo> npList, List<MediaCacheWholeVo> vpList) {
		
		int maxIndex = npList.size()>vpList.size()?npList.size():vpList.size();
		
		for(int i=0; i<maxIndex; i++){
			
			if(i<npList.size()){
				MediaCacheWholeVo wholeVo = npList.get(i);
				mediaBasicInfoCacheMap.put(wholeVo.getMediaId(), MediaCacheBeanUtils.getMediaBasicVo(wholeVo));
			}
			if(i<vpList.size()){
				MediaCacheWholeVo wholeVo = vpList.get(i);
				mediaBasicInfoCacheMap.put(wholeVo.getMediaId(), MediaCacheBeanUtils.getMediaBasicVo(wholeVo));
			}
		}
	}
}
