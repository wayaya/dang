package com.dangdang.digital.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.model.DDClick;
import com.dangdang.digital.service.IDDClickService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.vo.DDClickValueObj;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

/**
 * ddclick数据写入mongo对应的manager.
 */
@Service("ddClickService")
public class DDClickServiceImpl implements IDDClickService{
	Logger logger = LoggerFactory.getLogger(DDClickServiceImpl.class);
	private String defaultCollectionName = "digital_click";
	
	private String defaultErrorInfoCollectionName = "clientErrorInfo";
	
	private String collectionNamePre = "click_bak_";
	@Autowired
	private MongoTemplate mongoTemplate;   
	
	@Resource
	ICacheApi cacheApi;
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#groupDayDownload(java.lang.String, java.lang.Long, java.util.Date, java.util.Date)
	 */
	
	@Deprecated
	public GroupByResults<DDClick> getListByRefActionAndPeriod(String refAction, Date startTime, Date endTime ){
		
		
		Criteria creteriaCustId = Criteria.where("custId").exists(true);
		Criteria creteriaMediaId = Criteria.where("mediaId").exists(true);
		Criteria creteriaRefAction = Criteria.where("refAction").is(refAction);
		Criteria creteriaActionTime = Criteria.where("actionTime").gt(startTime).lte(endTime);
		
		Criteria allCondition = creteriaActionTime.andOperator(creteriaRefAction, creteriaMediaId, creteriaCustId);
		
		GroupBy groupBy = GroupBy.key("custId","mediaId").initialDocument("{ count:0, latestTime:0 }").reduceFunction("function(doc, values){values.count++; if(values.latestTime<doc.actionTime){ values.latestTime=doc.actionTime; } }");
		
		GroupByResults<DDClick> ddclickResults = mongoTemplate.group(allCondition, defaultCollectionName, groupBy, DDClick.class);
		
		return ddclickResults;
	}
	
	@Override
	public List<DDClick> getListByRefActionAndPeriodMapReduce(String refAction, Date startTime, Date endTime ){
		
		logger.info("getListByRefActionAndPeriodMapReduce params:"+refAction+"  "+DateUtil.formatDateByFormat(startTime, DateUtil.DATE_PATTERN)+"  "+DateUtil.formatDateByFormat(endTime, DateUtil.DATE_PATTERN));
		
		Criteria creteriaMediaId = Criteria.where("mediaId").exists(true);
		Criteria creteriaSaleId = Criteria.where("saleId").exists(true);
		
		Criteria creteriaActionTime = Criteria.where("actionTime").gt(startTime).lte(endTime).and("refAction").is(refAction).and("custId").exists(true);
		
		Criteria allCondition = creteriaActionTime.orOperator(creteriaMediaId, creteriaSaleId);
		
		Query query = new Query(allCondition);
		
		StringBuffer map = new StringBuffer();
		map.append("function() { ");
		map.append("var key = {custId:this.custId, mediaId:this.mediaId, saleId:this.saleId}; ");
		map.append("var value = {count:1, latestTime:this.actionTime};");
		map.append("emit(key,value);");
		map.append("}"); 
		
		StringBuffer reduce = new StringBuffer();
		
		reduce.append("function(key, emits) {");
		reduce.append(" var ret = {count:0, latestTime:0};");
		reduce.append(" for(var i in emits) { ");
		reduce.append("   ret.count+=emits[i].count;");
		reduce.append("   if(emits[i].latestTime>ret.latestTime){ret.latestTime=emits[i].latestTime};  ");
		reduce.append(" }");
		reduce.append("return ret;");
		reduce.append("}");
		long from = System.currentTimeMillis();
		MapReduceResults<DDClickValueObj>  mapReduceResults = mongoTemplate.mapReduce(query, defaultCollectionName, map.toString(), reduce.toString(), DDClickValueObj.class);
		long cost = System.currentTimeMillis()-from;
		
		logger.info("mapReduce cost:"+cost+"毫秒，结果有"+mapReduceResults.getCounts()+"条记录，");
		
		List<DDClick> ddClicks = new ArrayList<DDClick>();
		
		BasicDBList list = (BasicDBList)mapReduceResults.getRawResults().get("results"); 
	    for (int i = 0; i < list.size(); i ++) { 
	    	
	          BasicDBObject obj = (BasicDBObject)list.get(i);
	          
	          BasicDBObject key = (BasicDBObject)obj.get("_id");
	          
	          DDClick ddClick = new DDClick();
	          Long custId = key.getLong("custId");
	          
	          if(key.get("mediaId")!=null){
	        	  Long mediaId = key.getLong("mediaId");
	        	  ddClick.setMediaId(mediaId);
	          }
	          
	          if(key.get("saleId")!=null){
	        	  Long saleId = key.getLong("saleId");
	        	  ddClick.setSaleId(saleId); 
	          }
	          
	          BasicDBObject value = (BasicDBObject)obj.get("value");
//	          Double count = (Double)value.getDouble("count");
	          Date latestTime = value.getDate("latestTime");
	          ddClick.setActionTime(latestTime);
	          ddClick.setCustId(custId);
	          ddClicks.add(ddClick);
	    }  
		return ddClicks;
	}
	
	private  Map<Long, Integer> getSalePageBrowseMediaBySaleIds(Date startTime, Date endTime, List<Long> saleIdList ){
		
		Map<Long, Integer> saleIdToCount = new HashMap<Long, Integer>();
		String refAction = "browse";
		String actionName = "getMediasBySaleId";
		
		logger.info("getTopNSalePageBrowseMedia params:"+JSON.toJSONString(saleIdList)+"  "+DateUtil.formatDateByFormat(startTime, DateUtil.DATE_PATTERN)+"  "+DateUtil.formatDateByFormat(endTime, DateUtil.DATE_PATTERN));
		
		Criteria allCondition = Criteria.where("actionName").is(actionName).and("refAction").is(refAction).and("actionTime").gt(startTime).lte(endTime).and("saleId").exists(true);
		
		Query query = new Query(allCondition);
		
		StringBuffer map = new StringBuffer();
		map.append("function() { ");
		map.append("var key = {saleId:this.saleId}; ");
		map.append("var value = {count:1};");
		map.append("emit(key,value);");
		map.append("}"); 
		
		StringBuffer reduce = new StringBuffer();
		
		reduce.append("function(key, emits) {");
		reduce.append(" var ret = {count:0};");
		reduce.append(" for(var i in emits) { ");
		reduce.append("   ret.count+=emits[i].count;");
		reduce.append(" }");
		reduce.append("return ret;");
		reduce.append("}");
		long from = System.currentTimeMillis();
		MapReduceResults<DDClickValueObj>  mapReduceResults = mongoTemplate.mapReduce(query, defaultCollectionName, map.toString(), reduce.toString(), DDClickValueObj.class);
		long cost = System.currentTimeMillis()-from;
		
		logger.info("mapReduce cost:"+cost+"毫秒，结果有"+mapReduceResults.getCounts()+"条记录，");
		
		
		BasicDBList list = (BasicDBList)mapReduceResults.getRawResults().get("results"); 
		
	    for (int i = 0; i < list.size(); i ++) { 
	    	
	          BasicDBObject obj = (BasicDBObject)list.get(i);
	          
	          BasicDBObject key = (BasicDBObject)obj.get("_id");
	          
        	  Long saleId = key.getLong("saleId");
	          
	          BasicDBObject value = (BasicDBObject)obj.get("value");
	          int count = value.getInt("count");
	          
	          saleIdToCount.put(saleId, count);
	    }  
	    
	    return saleIdToCount;
	}
	
	@Override
	public List<LinkedHashMap<String, Object>> getTopNSalePageBrowseMedia(Date startTime, Date endTime, int number ){
		
		String refAction = "browse";
		String actionName = "getMediasBySaleId";
		
		logger.info("getTopNSalePageBrowseMedia params:"+number+"  "+DateUtil.formatDateByFormat(startTime, DateUtil.DATE_PATTERN)+"  "+DateUtil.formatDateByFormat(endTime, DateUtil.DATE_PATTERN));
		
		Criteria allCondition = Criteria.where("actionName").is(actionName).and("refAction").is(refAction).and("actionTime").gt(startTime).lte(endTime).and("saleId").exists(true);
		
		Query query = new Query(allCondition);
		
		StringBuffer map = new StringBuffer();
		map.append("function() { ");
		map.append("var key = {saleId:this.saleId}; ");
		map.append("var value = {count:1};");
		map.append("emit(key,value);");
		map.append("}"); 
		
		StringBuffer reduce = new StringBuffer();
		
		reduce.append("function(key, emits) {");
		reduce.append(" var ret = {count:0};");
		reduce.append(" for(var i in emits) { ");
		reduce.append("   ret.count+=emits[i].count;");
		reduce.append(" }");
		reduce.append("return ret;");
		reduce.append("}");
		long from = System.currentTimeMillis();
		MapReduceResults<DDClickValueObj>  mapReduceResults = mongoTemplate.mapReduce(query, defaultCollectionName, map.toString(), reduce.toString(), DDClickValueObj.class);
		long cost = System.currentTimeMillis()-from;
		
		logger.info("mapReduce cost:"+cost+"毫秒，结果有"+mapReduceResults.getCounts()+"条记录，");
		
		List<DDClick> ddClicks = new ArrayList<DDClick>();
		
		BasicDBList list = (BasicDBList)mapReduceResults.getRawResults().get("results"); 
		
	    for (int i = 0; i < list.size(); i ++) { 
	    	
	          BasicDBObject obj = (BasicDBObject)list.get(i);
	          
	          BasicDBObject key = (BasicDBObject)obj.get("_id");
	          
	          DDClick ddClick = new DDClick();
	          
        	  Long saleId = key.getLong("saleId");
        	  ddClick.setSaleId(saleId); 
	          
	          BasicDBObject value = (BasicDBObject)obj.get("value");
	          int count = value.getInt("count");
	          ddClick.setUnionId(count);
	          ddClicks.add(ddClick);
	    }  

	    
	    Collections.sort(ddClicks, new Comparator<DDClick>() {

			@Override
			public int compare(DDClick o1, DDClick o2) {
				return o2.getUnionId()-o1.getUnionId();
			}
		});
	    
	    
	    List<LinkedHashMap<String, Object>> ddClicksTopN = new ArrayList<LinkedHashMap<String, Object>>();
	    
	    List<Long> saleIdList = new ArrayList<Long>();
	    
	    for(int i=0; i<(ddClicks.size()>number?number:ddClicks.size()); i++){
	    	
	    	DDClick click = ddClicks.get(i);
	    	LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
	    	linkedHashMap.put("saleId", click.getSaleId());
	    	linkedHashMap.put("count", click.getUnionId());
	    	ddClicksTopN.add(linkedHashMap);
	    	saleIdList.add(click.getSaleId());
	    }
	    
	  //根据saleId获得mediaId， 然后查mediaId对应的这段时期内免费试读的个数
    	try{
    		Map<Long, Long> saleIdToMediaIdMap = new HashMap<Long, Long>();
    		List<MediaSaleCacheVo> saleList = cacheApi.batchGetMediaSaleFromCache(saleIdList);
    		for(MediaSaleCacheVo vo: saleList){
    			if(vo.getMediaList()!=null && vo.getMediaList().size()>0 ){	
    				saleIdToMediaIdMap.put(vo.getSaleId(), vo.getMediaList().get(0).getMediaId());
    			}
    		}
    		
    		List<Long> mediaIdList = new ArrayList<Long>(saleIdToMediaIdMap.values());
    		Map<Long, Integer> mediaIdToFreeViewCount = this.getFreeTryBrowseMediaCountByMediaIds(startTime, endTime, mediaIdList);
    		
    		for(LinkedHashMap<String, Object> mapInTopN: ddClicksTopN){
    			
    			Integer freeViewCount = 0;
    			Long saleId = (Long)mapInTopN.get("saleId");
    			Long mediaId = saleIdToMediaIdMap.get(saleId);
    			if(mediaId != null){
    				freeViewCount = mediaIdToFreeViewCount.get(mediaId);
    				if(freeViewCount==null){
    					freeViewCount = 0;
    				}
    			}
    			mapInTopN.put("freeViewCount", freeViewCount);
    		}
    		
    	}catch(Exception e){
    		logger.error("exception:", e);
    	}
	    
		return ddClicksTopN;
	}
	
	private Map<Long, Integer> getFreeTryBrowseMediaCountByMediaIds(Date startTime, Date endTime, List<Long> mediaIdList ){
		
		Map<Long, Integer> mediaIdToCount = new HashMap<Long, Integer>();
		String refAction = "browse";
		String actionName = "getCertificate";
		
		logger.info("getFreeTryBrowseMediaCountByMediaIds params:"+JSON.toJSONString(mediaIdList)+"  "+DateUtil.formatDateByFormat(startTime, DateUtil.DATE_PATTERN)+"  "+DateUtil.formatDateByFormat(endTime, DateUtil.DATE_PATTERN));
		
		Criteria allCondition = Criteria.where("actionName").is(actionName).and("refAction").is(refAction).and("actionTime").gt(startTime).lte(endTime)
				.and("mediaId").in(mediaIdList);
		
		Query query = new Query(allCondition);
		
		StringBuffer map = new StringBuffer();
		map.append("function() { ");
		map.append("var key = {mediaId:this.mediaId}; ");
		map.append("var value = {count:1};");
		map.append("emit(key,value);");
		map.append("}"); 
		
		StringBuffer reduce = new StringBuffer();
		
		reduce.append("function(key, emits) {");
		reduce.append(" var ret = {count:0};");
		reduce.append(" for(var i in emits) { ");
		reduce.append("   ret.count+=emits[i].count;");
		reduce.append(" }");
		reduce.append("return ret;");
		reduce.append("}");
		long from = System.currentTimeMillis();
		MapReduceResults<DDClickValueObj>  mapReduceResults = mongoTemplate.mapReduce(query, defaultCollectionName, map.toString(), reduce.toString(), DDClickValueObj.class);
		long cost = System.currentTimeMillis()-from;
		
		logger.info("mapReduce cost:"+cost+"毫秒，结果有"+mapReduceResults.getCounts()+"条记录，");
		
		BasicDBList list = (BasicDBList)mapReduceResults.getRawResults().get("results"); 
		
	    for (int i = 0; i < list.size(); i ++) { 
	    	
	          BasicDBObject obj = (BasicDBObject)list.get(i);
	          
	          BasicDBObject key = (BasicDBObject)obj.get("_id");
	          
        	  Long mediaId = key.getLong("mediaId");
	          
	          BasicDBObject value = (BasicDBObject)obj.get("value");
	          int count = value.getInt("count");
	          
	          mediaIdToCount.put(mediaId, count);
	    }  
	    
		return mediaIdToCount;
	}
	
	@Override
	public List<LinkedHashMap<String, Object>> getTopNFreeTryBrowseMedia(Date startTime, Date endTime, int number ){
		
		String refAction = "browse";
		String actionName = "getCertificate";
		
		logger.info("getTopNFreeTryBrowseMedia params:"+number+"  "+DateUtil.formatDateByFormat(startTime, DateUtil.DATE_PATTERN)+"  "+DateUtil.formatDateByFormat(endTime, DateUtil.DATE_PATTERN));
		
		Criteria allCondition = Criteria.where("actionName").is(actionName).and("refAction").is(refAction).and("actionTime").gt(startTime).lte(endTime).and("mediaId").exists(true);
		
		Query query = new Query(allCondition);
		
		StringBuffer map = new StringBuffer();
		map.append("function() { ");
		map.append("var key = {mediaId:this.mediaId}; ");
		map.append("var value = {count:1};");
		map.append("emit(key,value);");
		map.append("}"); 
		
		StringBuffer reduce = new StringBuffer();
		
		reduce.append("function(key, emits) {");
		reduce.append(" var ret = {count:0};");
		reduce.append(" for(var i in emits) { ");
		reduce.append("   ret.count+=emits[i].count;");
		reduce.append(" }");
		reduce.append("return ret;");
		reduce.append("}");
		long from = System.currentTimeMillis();
		MapReduceResults<DDClickValueObj>  mapReduceResults = mongoTemplate.mapReduce(query, defaultCollectionName, map.toString(), reduce.toString(), DDClickValueObj.class);
		long cost = System.currentTimeMillis()-from;
		
		logger.info("mapReduce cost:"+cost+"毫秒，结果有"+mapReduceResults.getCounts()+"条记录，");
		
		List<DDClick> ddClicks = new ArrayList<DDClick>();
		
		BasicDBList list = (BasicDBList)mapReduceResults.getRawResults().get("results"); 
		
	    for (int i = 0; i < list.size(); i ++) { 
	    	
	          BasicDBObject obj = (BasicDBObject)list.get(i);
	          
	          BasicDBObject key = (BasicDBObject)obj.get("_id");
	          
	          DDClick ddClick = new DDClick();
	          
        	  Long mediaId = key.getLong("mediaId");
        	  ddClick.setMediaId(mediaId); 
	          
	          BasicDBObject value = (BasicDBObject)obj.get("value");
	          int count = value.getInt("count");
	          ddClick.setUnionId(count);
	          ddClicks.add(ddClick);
	    }  
	    
	    Collections.sort(ddClicks, new Comparator<DDClick>() {

			@Override
			public int compare(DDClick o1, DDClick o2) {
				return o2.getUnionId()-o1.getUnionId();
			}
	    	
		});
	    
	    List<Long> mediaIdList = new ArrayList<Long>();
	    List<LinkedHashMap<String, Object>> ddClicksTopN = new ArrayList<LinkedHashMap<String, Object>>();
	    
	    for(int i=0; i<(ddClicks.size()>number?number:ddClicks.size()); i++){
	    	
	    	DDClick click = ddClicks.get(i);
	    	LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
	    	linkedHashMap.put("mediaId", click.getMediaId());
	    	linkedHashMap.put("count", click.getUnionId());
	    	
	    	ddClicksTopN.add(linkedHashMap);
	    	mediaIdList.add(click.getMediaId());
	    }
	    
	    //根据saleId获得mediaId， 然后查mediaId对应的这段时期内免费试读的个数
    	try{
    		Map<Long, Long> mediaIdToSaleIdMap = new HashMap<Long, Long>();
    		List<MediaCacheBasicVo> mediaList = cacheApi.batchGetMediaBasicFromCache(mediaIdList);
    		
    		for(MediaCacheBasicVo vo: mediaList){
				mediaIdToSaleIdMap.put(vo.getMediaId(), vo.getSaleId());
    		}
    		
    		List<Long> saleIdList = new ArrayList<Long>(mediaIdToSaleIdMap.values());
    		Map<Long, Integer> saleIdToPageViewCount = this.getSalePageBrowseMediaBySaleIds(startTime, endTime, saleIdList);
    		
    		for(LinkedHashMap<String, Object> mapInTopN: ddClicksTopN){
    			
    			Integer pageViewCount = 0;
    			Long mediaId = (Long)mapInTopN.get("mediaId");
    			Long saleId = mediaIdToSaleIdMap.get(mediaId);
    			if(saleId != null){
    				pageViewCount = saleIdToPageViewCount.get(saleId);
    				if(pageViewCount==null){
    					pageViewCount = 0;
    				}
    			}
    			mapInTopN.put("pageViewCount", pageViewCount);
    		}
    		
    	}catch(Exception e){
    		logger.error("exception:", e);
    	}
		return ddClicksTopN;
	}
	
	
	@Deprecated
	public GroupByResults<DDClick> getListByRefActionAndPeriod(String refAction, Date startTime, Date endTime, Long custId ){
		
		
		Criteria creteriaCustId = Criteria.where("custId").is(custId);
		Criteria creteriaMediaId = Criteria.where("mediaId").exists(true);
		Criteria creteriaRefAction = Criteria.where("refAction").is(refAction);
		Criteria creteriaActionTime = Criteria.where("actionTime").gt(startTime).lte(endTime);
		
		Criteria allCondition = creteriaActionTime.andOperator(creteriaCustId, creteriaRefAction, creteriaMediaId);
		
		GroupBy groupBy = GroupBy.key("custId","mediaId").initialDocument("{ count:0, latestTime:0 }").reduceFunction("function(doc, values){values.count++; if(values.latestTime<doc.actionTime){ values.latestTime=doc.actionTime; } }");
		
		GroupByResults<DDClick> ddclickResults = mongoTemplate.group(allCondition, defaultCollectionName, groupBy, DDClick.class);
		
		return ddclickResults;
	}
	
	
	@Deprecated
	public GroupByResults<DDClick> getListByActionNamesAndPeriod(List<String> actionNameList, Date startTime, Date endTime ){
		
		BasicDBList actionNameDBList = new BasicDBList();
		actionNameDBList.addAll(actionNameList);
		
		Criteria creteriaCustId = Criteria.where("custId").exists(true);
		Criteria creteriaMediaId = Criteria.where("mediaId").exists(true);
		Criteria creteriaActionName = Criteria.where("actionName").in(actionNameDBList);
		Criteria creteriaActionTime = Criteria.where("actionTime").gt(startTime).lte(endTime);
		
		Criteria allCondition = creteriaActionTime.andOperator(creteriaActionName, creteriaMediaId, creteriaCustId);
		
		GroupBy groupBy = GroupBy.key("custId","mediaId").initialDocument("{ count:0, latestTime:0 }").reduceFunction("function(doc, values){values.count++; if(values.latestTime<doc.actionTime){ values.latestTime=doc.actionTime; } }");
		
		GroupByResults<DDClick> ddclickResults = mongoTemplate.group(allCondition, defaultCollectionName, groupBy, DDClick.class);
		
		return ddclickResults;
	}
	
	public GroupByResults<DDClick> getListByActionNamesAndPeriod(List<String> actionNameList, Date startTime, Date endTime, Long custId){
		
		BasicDBList actionNameDBList = new BasicDBList();
		actionNameDBList.addAll(actionNameList);
		
		Criteria creteriaCustId = Criteria.where("custId").is(custId);
		Criteria creteriaMediaId = Criteria.where("mediaId").exists(true);
		Criteria creteriaActionName = Criteria.where("actionName").in(actionNameList);
		Criteria creteriaActionTime = Criteria.where("actionTime").gt(startTime).lte(endTime);
		
		Criteria allCondition = creteriaCustId.andOperator(creteriaActionTime, creteriaActionName, creteriaMediaId);
		
		GroupBy groupBy = GroupBy.key("custId","mediaId").initialDocument("{ count:0, latestTime:0 }").reduceFunction("function(doc, values){values.count++; if(values.latestTime<doc.actionTime){ values.latestTime=doc.actionTime; } }");
		
		GroupByResults<DDClick> ddclickResults = mongoTemplate.group(allCondition, defaultCollectionName, groupBy, DDClick.class);
		
		return ddclickResults;
	}
	
	public BasicDBList groupDayDownload(String collectName, Long actionId, Date startDate, Date endDate) {		
		DBCollection collection = mongoTemplate.getCollection(collectName);
		//System.out.println(collection.getCount());
		BasicDBObject key = new BasicDBObject();
		key.put("productId",true);
		key.put("deviceType", true);
		BasicDBObject cond = new BasicDBObject("actionId",actionId);
		cond.put("actionId",actionId);
		cond.put("productId", new BasicDBObject("$exists", true));
		cond.put("deviceType", new BasicDBObject("$exists", true));
		cond.put("actionTime", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		BasicDBObject initial = new BasicDBObject("count",0);
		StringBuffer reduce = new StringBuffer();
		reduce.append("function Reduce(doc, out) {");
		reduce.append(" out.count += 1; ");
		reduce.append("out.productId = doc.productId; ");
		//reduce.append("out.actionTime = doc.actionTime; ");
		reduce.append("out.deviceType = doc.deviceType;}");		
		StringBuffer finalize = new StringBuffer("function Finalize(out) { return out; }");
		BasicDBList returnList = (BasicDBList)collection.group(key, cond, initial, reduce.toString(), finalize.toString());
		
		return returnList;
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#countLastDateList(java.util.Date)
	 */
	
	public long countLastDateList(Date lastDate) {
		return countLastDateList(lastDate, defaultCollectionName);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#countLastDateList(java.util.Date, java.lang.String)
	 */
	
	public long countLastDateList(Date lastDate, String collectionName) {		
		Query query = new Query(Criteria.where("actionTime").lte(lastDate));
		return mongoTemplate.count(query, collectionName);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findLastDateList(java.util.Date, int, int)
	 */
	
	public List<DDClick> findLastDateList(Date lastDate, int start, int size) {		
		return this.findLastDateList(lastDate, start, size, defaultCollectionName);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findDDClickDataList(int, int)
	 */
	
	public List<DDClick> findDDClickDataList(int start , int pageSize){
		Query query = new Query();
		query.skip(start);
		query.limit(pageSize);
		query.sort();
		return mongoTemplate.find(query, DDClick.class,defaultCollectionName);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findLastDateList(java.util.Date, int, int, java.lang.String)
	 */
	
	public List<DDClick> findLastDateList(Date lastDate, int start, int size, String collectionName) {		
		Query query = new Query(Criteria.where("actionTime").lte(lastDate));
		query.skip(start);
		query.limit(size);
		query.sort();
		return mongoTemplate.find(query, DDClick.class, collectionName);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findDDClicksByCollections(org.springframework.data.mongodb.core.query.Query, java.lang.String)
	 */
	
	public List<DDClick> findDDClicksByCollections(Query query,String collections){
		return mongoTemplate.find(query, DDClick.class,collections);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findDDClicks(int, int, java.lang.String)
	 */
	
	public List<DDClick> findDDClicks(int pageSize, int pageNum, String collectionName) {
		int start = (pageNum - 1) * pageSize;
		Query query = new Query();
		query.skip(start);
		query.limit(pageSize);
		query.sort().on("_id", Order.ASCENDING);
		return mongoTemplate.find(query, DDClick.class, collectionName);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findByActionId(int, int, int, java.lang.String)
	 */
	
	public List<DDClick> findByActionId(int start,int size,int actionId,String collections){
		Query query = new Query(Criteria.where("actionId").is(actionId));
		query.skip(start);
		query.limit(size);
		query.sort();
		return mongoTemplate.find(query, DDClick.class,collections);
	}
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findByQuery(org.springframework.data.mongodb.core.query.Query)
	 */
	
	public List<DDClick> findByQuery(Query query) {
		return mongoTemplate.find(query, DDClick.class, defaultCollectionName);
	}
			
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findAll()
	 */
	
	public List<DDClick> findAll() {   
        //return mongoTemplate.find(new Query(), DDClick.class); 
        return this.findAll(defaultCollectionName);
    }  
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findAll(java.lang.String)
	 */
	
	public List<DDClick> findAll(String collectionName){
		return mongoTemplate.find(new Query(), DDClick.class, collectionName);
	}
    
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findOne(java.lang.String)
	 */
	
	public DDClick findOne(String id) {   
        //return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), DDClick.class);  
        return this.findOne(id, defaultCollectionName); 
    }
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#findOne(java.lang.String, java.lang.String)
	 */
	
	public DDClick findOne(String id, String collectionName) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), DDClick.class, collectionName); 
	}
	
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#insert(com.dangdang.digital.model.DDClick)
	 */
    @Override
	public void insert(DDClick click) {   
    	this.insert(click, defaultCollectionName); 
    } 
    
//    public void insertErrorInfo(ClientErrorInfo clientErrorInfo) {
//    	mongoTemplate.insert(clientErrorInfo, defaultErrorInfoCollectionName);
//    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#insertBatch(java.util.List)
	 */
    @Override
	public void insertBatch(List<DDClick> ddClicks) {
    	mongoTemplate.insert(ddClicks, defaultCollectionName);
    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#insertBatch(java.util.List, java.lang.String)
	 */
    
	public void insertBatch(List<DDClick> ddClicks, String collectionName) {
    	mongoTemplate.insert(ddClicks, collectionName);
    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#insert(com.dangdang.digital.model.DDClick, java.lang.String)
	 */
    
	public void insert(DDClick click, String collectionName) {   
    	mongoTemplate.insert(click, collectionName); 
    } 
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#removeByLastDate(java.util.Date, java.lang.String)
	 */
    
	public void removeByLastDate(Date last, String collectionName) {
    	Query query = new Query(Criteria.where("actionTime").lte(last));
    	mongoTemplate.remove(query, collectionName);
    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#removeBy(java.lang.String, java.lang.String)
	 */
    
	public void removeBy(String key, String value) {
    	Query query = new Query(Criteria.where(key).is(value));
    	mongoTemplate.remove(query, defaultCollectionName);
    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#remove(com.dangdang.digital.model.DDClick)
	 */
    
	public void remove(DDClick click) {
    	//mongoTemplate.remove(click);
    	this.remove(click, defaultCollectionName);
    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#remove(com.dangdang.digital.model.DDClick, java.lang.String)
	 */
    
	public void remove(DDClick click, String collectionName) {
    	//mongoTemplate.remove(click);
    	mongoTemplate.remove(click, collectionName);
    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#remove(java.util.List)
	 */
    
	public void remove(List<String> ids) {
    	Query query = new Query(Criteria.where("_id").in(ids));
    	mongoTemplate.remove(query, defaultCollectionName);
    }
  
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#removeAll()
	 */
    
	public void removeAll() {   
        List<DDClick> list = this.findAll();   
        if(list != null){   
            for(DDClick click : list){   
            	this.remove(click);   
            }   
        }   
    } 
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#removeOne(java.lang.String)
	 */
    
	public void removeOne(String id){   
    	DDClick click = this.findOne(id);
    	if (click != null) {
    		this.remove(click);
    	}        
    } 
    
/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#getMaxCollectNames()
 */

public String getMaxCollectNames() {
	Set<String> collectNames = mongoTemplate.getCollectionNames();
	int max = 1;
	for (String string : collectNames) {
		if (string.indexOf(collectionNamePre) >= 0) {
			int temp = Integer.parseInt(string.replaceAll(collectionNamePre, ""));
			if (temp > max) {
				max = temp;
			}
		}
	}
	return collectionNamePre + max;
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#findMinDate(java.lang.String, java.lang.String)
 */

public DDClick findMinDate(String id, String collectionName) {
	return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), DDClick.class, collectionName);
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#countData(org.springframework.data.mongodb.core.query.Query, java.lang.String)
 */

public long countData(Query query, String collectionName) {
	return mongoTemplate.count(query, collectionName);
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#findByQueryForTz(org.springframework.data.mongodb.core.query.Query, java.lang.String)
 */

public List<DDClick> findByQueryForTz(Query query, String collectionName) {
	return mongoTemplate.find(query, DDClick.class, collectionName);
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#countByTjNum(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
 */

public long countByTjNum(String deviceType, Integer actionId, String yesterday, String collectionName)
		throws Exception {
	Query query = new Query();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Criteria criteriaDate = Criteria.where("actionTime").gt(sdf.parse(yesterday + " 00:00:00"))
			.lt(sdf.parse(yesterday + " 23:59:59"));
	query.addCriteria(criteriaDate);
	Criteria criteriaDeviceType = Criteria.where("deviceType").is(deviceType);
	query.addCriteria(criteriaDeviceType);
	Criteria criteriaDeviceactionId = Criteria.where("actionId").is(actionId);
	query.addCriteria(criteriaDeviceactionId);
	return countData(query, collectionName);
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#countByTjPerson(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
 */

public long countByTjPerson(String deviceType, Integer actionId, String yesterday, String collectionName)
		throws Exception {
	Query query = new Query();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Criteria criteria = Criteria.where("custId").exists(true);
	query.addCriteria(criteria);
	Criteria criteriaDate = Criteria.where("actionTime").gt(sdf.parse(yesterday + " 00:00:00"))
			.lt(sdf.parse(yesterday + " 23:59:59"));
	query.addCriteria(criteriaDate);
	Criteria criteriaDeviceType = Criteria.where("deviceType").is(deviceType);
	query.addCriteria(criteriaDeviceType);
	Criteria criteriaDeviceactionId = Criteria.where("actionId").is(actionId);
	query.addCriteria(criteriaDeviceactionId);
	return countData(query, collectionName);
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#countByTjByAppendCart(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
 */

public long countByTjByAppendCart(String deviceType, Integer actionId, String yesterday, String collectionName)
		throws Exception {
	Query query = new Query();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Criteria criteriaDate = Criteria.where("actionTime").gt(sdf.parse(yesterday + " 00:00:00"))
			.lt(sdf.parse(yesterday + " 23:59:59"));
	query.addCriteria(criteriaDate);
	Criteria criteriaDeviceType = Criteria.where("deviceType").is(deviceType);
	query.addCriteria(criteriaDeviceType);
	Criteria criteriaDeviceactionId = Criteria.where("actionId").is(actionId);
	query.addCriteria(criteriaDeviceactionId);
	return countData(query, collectionName);
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#getBuySuccessDatas(int, int, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
 */

public List<DDClick> getBuySuccessDatas(int start, int size, String deviceType, Integer actionId, String yesterday,
		String collectionName) throws Exception {
	Query query = new Query();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	Criteria criteria = Criteria.where("custId").exists(true);
//	query.addCriteria(criteria);
	Criteria criteriaDate = Criteria.where("actionTime").gt(sdf.parse(yesterday + " 00:00:00"))
			.lt(sdf.parse(yesterday + " 23:59:59"));
	query.addCriteria(criteriaDate);
	Criteria criteriaDeviceType = Criteria.where("deviceType").is(deviceType);
	query.addCriteria(criteriaDeviceType);
	Criteria criteriaDeviceactionId = Criteria.where("actionId").is(actionId);
	query.addCriteria(criteriaDeviceactionId);
	query.skip(start);
	query.limit(size);
	query.sort();
	
	return findByQueryForTz(query, collectionName);
}

/* (non-Javadoc)
 * @see com.dangdang.digital.service.impl.IDDClickService#checkIfBakToday(java.lang.String, java.lang.String)
 */

public long checkIfBakToday(String yesterday, String collectionName) throws Exception {
	 Query query = new Query();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = sdf.parse(yesterday + " 00:00:00");
	Criteria criteriaDateMin = Criteria.where("actionTime").lt(date);
	query.addCriteria(criteriaDateMin);
	return  countData(query, "click_bak");
	
}
}  