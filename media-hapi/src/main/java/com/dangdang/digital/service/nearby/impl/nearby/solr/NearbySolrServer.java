package com.dangdang.digital.service.nearby.impl.nearby.solr;


import com.dangdang.digital.service.nearby.api.nearby.bean.*;
import com.dangdang.digital.service.nearby.impl.nearby.asyn.TPExecutorManager;
import com.dangdang.digital.service.nearby.impl.nearby.util.GeoUtil;
import com.dangdang.digital.service.nearby.impl.nearby.util.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 
 * Description: NearbySolrServer
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:13:02  by 林永奇（linyongqi@dangdang.com）创建
 */
public class NearbySolrServer implements INearbySolrServer {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(NearbySolrServer.class);


    private CloudSolrServer solrServer;

    private String zkHosts;

    private final AtomicLong batchCounter = new AtomicLong();

    private volatile long lastBatchCount = 0;

    //最大1000的数量
    private int optimizeThreshold = 1000;

    private String coreName;

    //线程池管理注入进来
    private TPExecutorManager tpmanager;
    
    private String range;

    
    public void setRange(String range) {
		this.range = range;
	}

	@Override
    public void setOptimizeThreshold(int maxCount) {
        this.optimizeThreshold = maxCount;
    }

    @Override
    public void setZkHosts(String zkHosts) {
        this.zkHosts = zkHosts;
    }


    @Override
    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    @Override
    public void setTpmanager(TPExecutorManager tpmanager) {
        this.tpmanager = tpmanager;
    }

    @Override
    public void start() throws Exception {

        //创建cloudsolr 内建了LB 外部不需要做负载均衡
        this.solrServer = new CloudSolrServer(zkHosts);
        this.solrServer.setDefaultCollection(this.coreName);
        this.solrServer.setZkConnectTimeout(50000);
        this.solrServer.setZkClientTimeout(10 * 1000);

        //是否开启优化
        if (Boolean.parseBoolean(System.getProperty("openOptimize", "false"))) {
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    long count = batchCounter.get();
                    long countChange = count - lastBatchCount;
                    LOG.info("Schedule|OptimizeIndex|CHANGE|time:{0}|cost:{1}", new Date(), countChange);
                    if (countChange >=
                            NearbySolrServer.this.optimizeThreshold) {
                        innerOptimizeIndex();
                        LOG.info("Schedule|OptimizeIndex|OPTIMIZE|END|time:{0}|cost:{1}", new Date(), countChange);
                        lastBatchCount = count;
                    }
                }
            }, 0, 60, TimeUnit.SECONDS);
        }
        LOG.info("solr server start succ!");
    }


    @Override
    public void optimizeIndex() {
        this.tpmanager.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                innerOptimizeIndex();
            }
        });

    }

    /**
     * 内部优化索引调用
     */
    private void innerOptimizeIndex() {
        try {
            long now = System.currentTimeMillis();
            //每天4点做优化
            this.solrServer.optimize(true, false, 10);
            long eslapseTime = System.currentTimeMillis() - now;
            LOG.info("optimizeIndex|SUCC|time:{0}|cost:{1}", new Date(), eslapseTime);
        } catch (Exception e) {
            LOG.error("optimizeIndex|FAIL");
        }

    }

    @Override
    public void clearExpiredDocs(final long startmilseconds) {
        this.tpmanager.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //删除掉小于三天数据或者是隐身用户 或者 没有性别
                    NearbySolrServer.this.solrServer.deleteByQuery("active_time_min:[ 0 TO " + startmilseconds + "]");
                    //删除没有active_time的数据
                    NearbySolrServer.this.solrServer.deleteByQuery("-(active_time_min:*)");
                    
                     solrServer.commit(false, false, true);
                    //开启一次优化
                    NearbySolrServer.this.optimizeIndex();
                    LOG.info("clearExpiredDocs|SUCC");
                } catch (Exception e) {
                    LOG.error("clearExpiredDocs|FAIL|{0}", startmilseconds);
                }
            }
        });

    }

    @Override
	public void clearUserLocation(final String custId) {
    	this.tpmanager.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    NearbySolrServer.this.solrServer.deleteByQuery("uid:"+custId);
                    solrServer.commit(false, false, true);
                    LOG.info("clearUserLocation|SUCC");
                } catch (Exception e) {
                    LOG.error("clearUserLocation|FAIL|{0}", custId);
                }
            }
        });
	}

	@Override
    public void batchUpdateDocs(List<ISolrDoc> docs) {

        try {
            //转成solr的doc然后更新索引
            List<SolrInputDocument> solrDocs = new ArrayList<SolrInputDocument>(docs.size());
            for (ISolrDoc doc : docs) {
                SolrInputDocument inputDoc = null;
                try {
                    inputDoc = doc.wrap2SolrDoc();
                    solrDocs.add(inputDoc);
                } catch (Exception e) {
                    LOG.error("batchUpdateDocs|wrap2SolrDoc|FAIL|{0}", doc);
                }
            }

            if (CollectionUtils.isEmpty(docs)) {
                LOG.error("batchUpdateDocs|FAIL|NO DOCS");
                return;
            }

            UpdateResponse resp = solrServer.add(solrDocs);
            solrServer.commit(false, false, true);
            if (LOG.isDebugEnabled()) {
                LOG.debug("batchUpdateDocs|SUCC|resp:{0}|{1}", resp, docs);
            }
            //记录更新的次数
            this.batchCounter.incrementAndGet();
            this.tpmanager.getSolrCounter().inc();
        } catch (Exception e) {
        	e.printStackTrace();
            LOG.error("batchUpdateDocs|FAIL|{0}", docs);
        }
    }
    Random RAD = new Random();
    static final int MAX_PAGE = 20;
    @Override
    public DataPage pageSearch(UserQueryParams queryParams, int skip, int limit) {
          if(isWhite(queryParams.getCustId())){
              //先带上geocode查询
              DataPage  ret = pageSearchInner(queryParams,skip,limit,true);
//              if(ret.getPageData().size() <MAX_PAGE){//如果结果集太小，就不用geocode做限制了
//            	  DataPage morePage=this.searchNoFilter(queryParams, skip, MAX_PAGE);
//            	  morePage.getPageData().removeAll(ret.getPageData());
//            	  ret.getPageData().addAll(morePage.getPageData());
//              }
              return ret;
          }else{
              return pageSearchInner(queryParams,skip,limit,false);
          }
    }
    private DataPage searchNoFilter(UserQueryParams queryParams,int skip, int limit){
    	SolrQuery query = new SolrQuery();
    	query.set("q", "-uid:"+queryParams.getCustId());
        //分页
        query.setRows(limit);
        query.setStart(skip);
        query.addField("uid");
        query.addField("geoloc");
        System.out.println("query:"+query.toString());
        if (LOG.isDebugEnabled()) {
            LOG.debug("query clause :{0}", query);
        }
        List<LocationEntity> users = new ArrayList<LocationEntity>(limit);
        DataPage dataPage = new DataPage();
        dataPage.setPageData(users);
        dataPage.setLimit(limit);
        dataPage.setSkip(skip);
        try {
            QueryResponse response = solrServer.query(query);
            SolrDocumentList docList = response.getResults();
            dataPage.setTotal(docList.getNumFound());

            if(response.getQTime() > 500){
                LOG.info("query SLOW!|{1}|{2}",query,response.getQTime());
            }

            for (SolrDocument doc : docList) {
                LocationEntity entity = new LocationEntity();
                entity.setCustId((String) doc.get("uid"));
//                entity.setDistance((Double) doc.get("dist"));
                String geoloc = (String)doc.get("geoloc");
                if(StringUtils.isNotBlank(geoloc)){
                    String[] latlon = geoloc.split(",");
                    entity.setLat(Double.parseDouble(latlon[0]));
                    entity.setLng(Double.parseDouble(latlon[1]));
                    double dist =  GeoUtil.distance(queryParams.getLat(), queryParams.getLng(), Double.parseDouble(latlon[0]), Double.parseDouble(latlon[1]));
                    entity.setDistance(dist/1000);
                }
                users.add(entity);
            }

        } catch (SolrServerException e) {
            LOG.error("solr page query |FAIL|{0}", queryParams);
        } finally {
            if (LOG.isDebugEnabled()) {
                LOG.debug("solr page query |SUCC|{0}|{1}", queryParams, users);
            }
        }

        return dataPage;
    }
    public boolean isWhite(String custId){
        if(custId != null){
            if(custId.equals("25927525") || custId.equals("100777") || Long.parseLong(custId) % 2 == 1){
                return true;
            }
        }
        return true;
    };

    public DataPage pageSearchInner(UserQueryParams queryParams, int skip, int limit,boolean useMinute) {

       
        //开始拼凑条件吧
        SolrQuery query = new SolrQuery();
        query.addFilterQuery("{!geofilt}"); 
        query.set("q", "-uid:"+queryParams.getCustId()); 
        if(range!=null&&!"".equals(range)){
        	query.set("d",range); 
        }else{
        	query.set("d",String.valueOf(queryParams.getDistance())); 
        }
        query.set("sfield","geoloc");
        query.set("pt",queryParams.getLat() + "," + queryParams.getLng()); 
        query.setSort("geodist()", SolrQuery.ORDER.asc);
        //分页
        query.setRows(limit);
        query.setStart(skip);
        query.addField("uid");
        query.addField("geoloc");
        query.addField("display_id");
        System.out.println("query:"+query.toString());
        "".indexOf("");

        if (LOG.isDebugEnabled()) {
            LOG.debug("query clause :{0}", query);
        }

        List<LocationEntity> users = new ArrayList<LocationEntity>(limit);
        DataPage dataPage = new DataPage();
        dataPage.setPageData(users);
        dataPage.setLimit(limit);
        dataPage.setSkip(skip);
        try {
            QueryResponse response = solrServer.query(query);
            SolrDocumentList docList = response.getResults();
            dataPage.setTotal(docList.getNumFound());

            if(response.getQTime() > 500){
                LOG.info("query SLOW!|{1}|{2}",query,response.getQTime());
            }

            for (SolrDocument doc : docList) {
                LocationEntity entity = new LocationEntity();
                entity.setCustId((String) doc.get("uid"));
                entity.setDisplayId(doc.get("display_id")==null?null:doc.get("display_id").toString());
//                entity.setDistance((Double) doc.get("dist"));
                String geoloc = (String)doc.get("geoloc");
                if(StringUtils.isNotBlank(geoloc)){
                    String[] latlon = geoloc.split(",");
                    entity.setLat(Double.parseDouble(latlon[0]));
                    entity.setLng(Double.parseDouble(latlon[1]));
                    double dist =  GeoUtil.distance(queryParams.getLat(), queryParams.getLng(), Double.parseDouble(latlon[0]), Double.parseDouble(latlon[1]));
                    entity.setDistance(dist/1000);
                }
                users.add(entity);
            }

        } catch (SolrServerException e) {
            LOG.error("solr page query |FAIL|{0}", queryParams);
        } finally {
            if (LOG.isDebugEnabled()) {
                LOG.debug("solr page query |SUCC|{0}|{1}", queryParams, users);
            }
        }

        return dataPage;

    }

    @Override
	public boolean isExits(String custId) {
		// TODO Auto-generated method stub
    	SolrQuery query = new SolrQuery();
    	SolrDocumentList docList=null;
        StringBuffer sb=new StringBuffer();
        sb.append("uid:"+custId);
        query.setQuery(sb.toString());
        try {
        	QueryResponse response=solrServer.query(query);
        	docList = response.getResults();
        } catch (SolrServerException e) {
            LOG.error("solr page query |FAIL|{0}", query);
        } finally {
            if (LOG.isDebugEnabled()) {
                LOG.debug("solr page query |SUCC|{0}|{1}", query, custId);
            }
        }
        if(docList==null||docList.size()==0){
    		return false;
    	}
		return true;
	}

	@Override
    public void destory() throws Exception {
        this.solrServer.shutdown();

    }
}
