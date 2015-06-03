package com.dangdang.digital.service.nearby.impl.nearby;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.LoggerFactory;

import com.dangdang.digital.service.nearby.api.nearby.INearbyUpdateService;
import com.dangdang.digital.service.nearby.api.nearby.bean.ISolrDoc;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserLocation;
import com.dangdang.digital.service.nearby.impl.nearby.asyn.BatchTaskManager;
import com.dangdang.digital.service.nearby.impl.nearby.bean.ITree;
import com.dangdang.digital.service.nearby.impl.nearby.bean.SolrLocation;
import com.dangdang.digital.service.nearby.impl.nearby.solr.INearbySolrServer;

/**
 * 
 * Description: 索引更新实现
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:18:30  by 林永奇（linyongqi@dangdang.com）创建
 */
public class NearbyUpdateServiceImpl implements INearbyUpdateService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(NearbyUpdateServiceImpl.class);



    //搜索服务
    private INearbySolrServer solrServer;



    private ITree geoTree;


    private AtomicLong expiredProfile;

    private AtomicLong locationTotal;

    //cache的容量
    private long profileExpiredDays = 3;

    private boolean enableClear=false;
    
    

    public void setEnableClear(boolean enableClear) {
		this.enableClear = enableClear;
	}

	public void setLocationTotal(AtomicLong locationTotal) {
		this.locationTotal = locationTotal;
	}

	
	public void setProfileExpiredDays(long profileExpiredDays) {
		this.profileExpiredDays = profileExpiredDays;
	}

	private Date addDate(Date date,int days){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
    public void init() throws Exception {
    	if(enableClear){
    		Calendar calendar=Calendar.getInstance();
    		calendar.set(Calendar.HOUR_OF_DAY, 1);
    		calendar.set(Calendar.MINUTE, 0);
    		calendar.set(Calendar.SECOND, 0);
    		Date date=calendar.getTime();
    		Date currentDate=new Date();
    		if(date.before(currentDate)){
    			date=addDate(date,1);
    		}
    		long currentTime=currentDate.getTime();
    		long startoffset=date.getTime();
    		long initialDelay=startoffset-currentTime;
    		ScheduledExecutorService service=Executors.newScheduledThreadPool(2);
    		service.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					clearExpiredUsers();
				}
			}, initialDelay, 24*60*60*1000, TimeUnit.MILLISECONDS);
    	}
    }


    public void setSolrServer(INearbySolrServer solrServer) {
        this.solrServer = solrServer;
    }

    public void setGeoTree(ITree geoTree) {
        this.geoTree = geoTree;
    }


    //批量任务的管理
    private BatchTaskManager taskManager;

    public void setTaskManager(BatchTaskManager taskManager) {
        this.taskManager = taskManager;
    }




    @Override
    public boolean updateSolrUserLocation(String custId,String displayId, UserLocation location) {

        Throwable t = null;
        ISolrDoc doc = null;
        try {

            if (null == location) {
                return true;
            }
            //用指定的编码来编码当前的经纬度，返回经纬度
            String geocode = this.geoTree.encodeGeoHash("SNB", location.getLat(), location.getLng());
            geocode = this.geoTree.unmarshalGeoHash("SNB", geocode);
            doc = new SolrLocation(custId,displayId, location, geocode);
            this.locationTotal.incrementAndGet();
            return this.taskManager.offer(doc);

        } catch (Exception e) {
        	e.printStackTrace();
            LOG.error( "updateSolrUserLocation|FAIL|{0}|{1}", custId, location);
            t = e;
        } finally {
            if (null == t && LOG.isDebugEnabled()) {
                LOG.debug("updateSolrUserLocation|SUCC|{0}|{1}", custId, location);
            }
        }
        return false;
    }

    @Override
    public boolean clearExpiredUsers() {
        long startTime = System.currentTimeMillis() - profileExpiredDays*24*60*60*1000;
        Throwable t = null;
        try {
        	startTime=startTime/60000;
            this.solrServer.clearExpiredDocs(startTime);
            LOG.info( "清理过期的索引成功:{0}|{1}",profileExpiredDays, new Date(startTime));
        } catch (Exception e) {
            LOG.error("clearExpiredUsers|FAIL|{0}");
            t = e;
        } finally {
            if (null == t && LOG.isDebugEnabled()) {
                LOG.debug("clearExpiredUsers|SUCC|{0}");
            }
        }

        return false;
    }

    
    @Override
	public boolean clearUserLocation(String custId) {
    	this.solrServer.clearUserLocation(custId);
		return false;
	}

	@Override
    public boolean invokeOptimizeIndex() {
        this.solrServer.optimizeIndex();

        return true;
    }

    public void destory() throws Exception {
        this.solrServer.destory();
    }


}
