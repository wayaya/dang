package com.dangdang.digital.service.nearby.api.nearby;

import com.dangdang.digital.service.nearby.api.nearby.bean.DataPage;
import com.dangdang.digital.service.nearby.api.nearby.bean.LocationEntity;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserQueryParams;

import java.util.List;

/**
 * 
 * Description: 查询接口
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:15:10  by 林永奇（linyongqi@dangdang.com）创建
 */
public interface IUserNearbyService {


    /**
     * 查找附近的用户
     * @param queryParams
     * @param skipIndex
     * @param limit
     * @return
     */
    public DataPage searchNearbyUsers(UserQueryParams queryParams,int skipIndex, int limit);

    /**
     * 查找附近的用户
     * @param queryParams
     * @param skipIndex
     * @param limit
     * @return
     */
    public List<LocationEntity> searchNearbyCustId(UserQueryParams queryParams,int skipIndex, int limit);
    
    boolean isExits(String custId);

}
