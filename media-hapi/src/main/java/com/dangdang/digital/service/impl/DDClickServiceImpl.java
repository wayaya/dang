package com.dangdang.digital.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.model.DDClick;
import com.dangdang.digital.service.IDDClickService;
import com.dangdang.digital.utils.DateUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

/**
 * ddclick数据写入mongo对应的manager.
 */
@Service("ddClickService")
@Transactional
public class DDClickServiceImpl implements IDDClickService {
	private String defaultCollectionName = "digital_click";
	
	private String defaultErrorInfoCollectionName = "clientErrorInfo";
	
	private String collectionNamePre = "click_bak_";
	@Autowired
	private MongoTemplate mongoTemplate;   
	
	/* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#groupDayDownload(java.lang.String, java.lang.Long, java.util.Date, java.util.Date)
	 */
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
    
	public void insert(DDClick click) {   
    	this.insert(click, defaultCollectionName); 
    } 
    
//    public void insertErrorInfo(ClientErrorInfo clientErrorInfo) {
//    	mongoTemplate.insert(clientErrorInfo, defaultErrorInfoCollectionName);
//    }
    
    /* (non-Javadoc)
	 * @see com.dangdang.digital.service.impl.IDDClickService#insertBatch(java.util.List)
	 */
    
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
	Criteria criteriaDate = Criteria.where("actionTime").gt(DateUtil.getdate(yesterday + " 00:00:00"))
			.lt(DateUtil.getdate(yesterday + " 23:59:59"));
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
	Criteria criteria = Criteria.where("custId").exists(true);
	query.addCriteria(criteria);
	Criteria criteriaDate = Criteria.where("actionTime").gt(DateUtil.getdate(yesterday + " 00:00:00"))
			.lt(DateUtil.getdate(yesterday + " 23:59:59"));
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
	Criteria criteriaDate = Criteria.where("actionTime").gt(DateUtil.getdate(yesterday + " 00:00:00"))
			.lt(DateUtil.getdate(yesterday + " 23:59:59"));
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
//	Criteria criteria = Criteria.where("custId").exists(true);
//	query.addCriteria(criteria);
		Criteria criteriaDate = Criteria.where("actionTime").gt(DateUtil.getdate(yesterday + " 00:00:00"))
			.lt(DateUtil.getdate(yesterday + " 23:59:59"));
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
	Date date =DateUtil.getdate(yesterday + " 00:00:00");
	Criteria criteriaDateMin = Criteria.where("actionTime").lt(date);
	query.addCriteria(criteriaDateMin);
	return  countData(query, "click_bak");
	
}
}  