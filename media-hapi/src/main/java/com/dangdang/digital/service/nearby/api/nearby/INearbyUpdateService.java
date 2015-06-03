package com.dangdang.digital.service.nearby.api.nearby;


import com.dangdang.digital.service.nearby.api.nearby.bean.UserLocation;

/**
 * 
 * Description: 索引更新接口
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:15:00  by 林永奇（linyongqi@dangdang.com）创建
 */
public interface INearbyUpdateService {




    /**
     * 更新用户的地理位置
     *
     * @param custId
     * @param location
     * @return
     */
    public boolean updateSolrUserLocation(String custId,String displayId, UserLocation location);



    /**
     * 清理3天之外的用户
     *
     * @param custId
     * @param seconds
     * @return
     */
    public boolean clearExpiredUsers();
    
    /**
     * 
     * Description: 清理用户地理位置
     * @Version1.0 2014年7月18日 下午2:37:09 by 林永奇（linyongqi@dangdang.com）创建
     * @param custId
     * @return
     */
    public boolean clearUserLocation(String custId);


    /**
     * 调用优化索引
     * @return
     */
    public boolean invokeOptimizeIndex();


}
