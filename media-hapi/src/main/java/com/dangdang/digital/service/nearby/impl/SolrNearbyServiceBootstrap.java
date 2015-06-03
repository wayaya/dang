package com.dangdang.digital.service.nearby.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.digital.service.nearby.api.nearby.INearbyUpdateService;
import com.dangdang.digital.service.nearby.api.nearby.IUserNearbyService;
import com.dangdang.digital.service.nearby.api.nearby.bean.LocationEntity;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserLocation;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserQueryParams;
import com.dangdang.digital.service.nearby.impl.nearby.NearbyUpdateServiceImpl;
import com.dangdang.digital.service.nearby.impl.nearby.UserNearbyServiceImpl;
import com.dangdang.digital.service.nearby.impl.nearby.util.GeoUtil;

/**
 * 
 * Description: 测试的启动，忽略之
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:18:59  by 林永奇（linyongqi@dangdang.com）创建
 */
public class SolrNearbyServiceBootstrap {

    public static void main(String[] args) throws Exception{
    	ApplicationContext ac= new ClassPathXmlApplicationContext("spring/snb-book-service.xml");
//    	INearbyUpdateService updateService=(NearbyUpdateServiceImpl)ac.getBean("updateService");
    	IUserNearbyService searchService=(UserNearbyServiceImpl)ac.getBean("nearbyService");
    	UserQueryParams param=new UserQueryParams();
    	param.setLat(41.0567573582);
    	param.setLng(116.32777692);
    	param.setDistance(150);
    	List<LocationEntity> result=searchService.searchNearbyCustId(param, 0, 10);
    	System.out.println("result size:"+result.size());
    	for(LocationEntity entity:result){
    		System.out.println("id:"+entity.getCustId()+";geo:"+entity.getLat()+","+entity.getLng()+";distance:"+entity.getDistance());
    	}
//    	UserLocation location=new UserLocation(40.0567533552,116.3277660200,0,new Date());
//    	UserLocation location1=new UserLocation(40.0567533552,116.3277660200,0,new Date());
        //updateService.updateSolrUserLocation("1002589", location);
        //updateService.updateSolrUserLocation("1002588", location1);
    	//new CountDownLatch(1).await();
    }

}
