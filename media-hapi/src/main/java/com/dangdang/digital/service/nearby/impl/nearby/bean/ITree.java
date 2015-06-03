package com.dangdang.digital.service.nearby.impl.nearby.bean;



import com.dangdang.digital.service.nearby.impl.nearby.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * 
 * Description: 树接口
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:17:37  by 林永奇（linyongqi@dangdang.com）创建
 */
public interface ITree {


    /**
     * 初始化init
     */
    void init();

    /**
     * 设置树的层级
     *
     * @return
     */
    public void setTreeDepth(int level);

    /**
     * 获取树的层级
     *
     * @return
     */
    public int getTreeDepth();


    /**
     * 编码为geoHash
     * 返回业务名称前缀的geocode
     *
     * @param lat
     * @param lng
     * @param businessType
     * @return
     */
    public String encodeGeoHash(String businessType, double lat, double lng) throws Exception;

    /**
     * 去掉geohashcode的前缀字符串
     * 得到lat lng 对应的原生得geocode
     *
     * @param geohash
     * @param businessType
     * @return
     */
    public String unmarshalGeoHash(String businessType, String geohash);


    /**
     * 加上geohashcode的前缀字符串
     *
     * @param geohash
     * @param businessType
     * @return
     */
    public String marshalGeoHash(String businessType, String geohash);


    /**
     * 扩大geohash
     * 获得最近的8个
     *
     * @param geohash
     * @param level   //分裂的层级，决定扩大的倍数
     * @return
     */
    public List<String> expandGeoHash(String geohash, int level);

    public List<String> expandGeoHashSquare(String geohash, int level);

    public Map<Integer,List<String>> sortGeoHashByDistance(String geohash, List<String> list);



    /**
     * 定位一个geocode 对应的中心点坐标
     *
     * @param geocode
     * @param businessType
     * @return left 纬度 right 经度
     */
    public Pair<Double, Double> locateCentralPoint(String businessType, String geocode);
}
