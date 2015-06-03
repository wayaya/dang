package com.dangdang.digital.service.nearby.impl.nearby.solr;

import com.dangdang.digital.service.nearby.api.nearby.bean.DataPage;
import com.dangdang.digital.service.nearby.api.nearby.bean.ISolrDoc;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserQueryParams;
import com.dangdang.digital.service.nearby.impl.nearby.asyn.TPExecutorManager;

import java.util.List;

/**
 * 
 * Description: SolrServer接口
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:18:13  by 林永奇（linyongqi@dangdang.com）创建
 */
public interface INearbySolrServer {


    /**
     * 设置thread pool manager
     *
     * @param tpmanager
     */
    public void setTpmanager(TPExecutorManager tpmanager);


    void setOptimizeThreshold(int maxCount);


    void setZkHosts(String zkHosts);


    void setCoreName(String coreName);


    /**
     * 启动solr server
     *
     * @throws Exception
     */
    void start() throws Exception;


    void optimizeIndex();

    /**
     * 清理掉过期的docs
     *
     * @param minute
     */
    void clearExpiredDocs(long minute);

    /**
     * 
     * Description: 清除用户地理位置
     * @Version1.0 2014年7月18日 下午2:39:32 by 林永奇（linyongqi@dangdang.com）创建
     * @param custId
     */
    void clearUserLocation(final String custId);
    
    /**
     * 批量更新doc
     *
     * @param docs
     */
    void batchUpdateDocs(List<ISolrDoc> docs);

    /**
     * 分页查询
     *
     * @param queryParams
     * @return
     */
    DataPage pageSearch(UserQueryParams queryParams, int skip, int limit);
    
    boolean isExits(String custId);


    void destory() throws Exception;
}
