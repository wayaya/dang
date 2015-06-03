package com.dangdang.digital.service.nearby.impl.nearby;


import com.dangdang.digital.service.nearby.api.nearby.IUserNearbyService;
import com.dangdang.digital.service.nearby.api.nearby.bean.DataPage;
import com.dangdang.digital.service.nearby.api.nearby.bean.LocationEntity;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserQueryParams;
import com.dangdang.digital.service.nearby.impl.nearby.bean.ITree;
import com.dangdang.digital.service.nearby.impl.nearby.solr.INearbySolrServer;

import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 
 * Description: 地理位置查询实现
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:18:44  by 林永奇（linyongqi@dangdang.com）创建
 */
public class UserNearbyServiceImpl implements IUserNearbyService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(UserNearbyServiceImpl.class);

    //搜索服务
    private INearbySolrServer solrServer;

    public void setSolrServer(INearbySolrServer solrServer) {
        this.solrServer = solrServer;
    }



    /**
     * 用于计算编码的树
     */
    private ITree geoTree;

    public void setGeoTree(ITree geoTree) {
        this.geoTree = geoTree;
    }

    @Override
    public List<LocationEntity> searchNearbyCustId(UserQueryParams queryParams, int skipIndex, int limit) {

        List<LocationEntity> custIds = null;
        Throwable t = null;
        try {
            //用指定的编码来编码当前的经纬度，返回经纬度
            String geocode = this.geoTree.encodeGeoHash("SNB", queryParams.getLat(),queryParams.getLng());
            geocode = this.geoTree.unmarshalGeoHash("SNB", geocode);
            queryParams.setGeocode(geocode);
            DataPage dataPage = this.solrServer.pageSearch(queryParams, skipIndex, limit);
            return dataPage.getPageData();
        } catch (Exception e) {
            LOG.error("searchNearbyCustId|FAIL|{0}", queryParams);
            t = e;
        } finally {
            if (null == t && LOG.isDebugEnabled()) {
                LOG.debug("searchNearbyCustId|SUCC|{0}|", queryParams);
            }
        }
        return custIds;
    }

    @Override
    public DataPage searchNearbyUsers(UserQueryParams queryParams, int skipIndex, int limit) {

        Throwable t = null;
        try {
            //用指定的编码来编码当前的经纬度，返回经纬度
            String geocode = this.geoTree.encodeGeoHash("SNB", queryParams.getLat(),queryParams.getLng());
            geocode = this.geoTree.unmarshalGeoHash("SNB", geocode);
            queryParams.setGeocode(geocode);
            return this.solrServer.pageSearch(queryParams, skipIndex, limit);
        } catch (Exception e) {
            LOG.error("searchNearbyUsers|FAIL|{0}", queryParams);
            t = e;
        } finally {
            if (null == t && LOG.isDebugEnabled()) {
                LOG.debug("searchNearbyUsers|SUCC|{0}", queryParams);
            }
        }
        return EMPTY_PAGE;
    }

    @Override
	public boolean isExits(String custId) {
		// TODO Auto-generated method stub
		return this.solrServer.isExits(custId);
	}



	private static final DataPage EMPTY_PAGE = new DataPage();


    public void destory() throws Exception {

        this.solrServer.destory();
    }
}
