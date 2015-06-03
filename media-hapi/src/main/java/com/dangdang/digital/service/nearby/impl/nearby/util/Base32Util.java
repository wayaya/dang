package com.dangdang.digital.service.nearby.impl.nearby.util;


import java.util.*;


public class Base32Util {
    private static final char[] DIC = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'

    };

    /**
     * 编码为base32
     *
     * @param num
     * @param treeDepth int指定的编码树的层级
     * @return
     */
    public static String encode(long num, int treeDepth) {

        int bitNum = genValidBitNum(treeDepth);

        StringBuilder sb = new StringBuilder();
        byte flag = 0;
        int i = 0;
        for (; i < bitNum / 5; i++) {
            flag = (byte) ((num >> (bitNum - (i + 1) * 5)) & 0x1F);
            sb.append(DIC[flag]);
        }
        return sb.toString();
    }


    public static final int indexDic(char c) {
        for (int i = 0; i < DIC.length; i++) {
            if (DIC[i] == c) {
                return i;
            }
        }
        throw new IllegalArgumentException("非法的base32字符[" + c + "]");
    }


    /**
     * 解析出原始的geohash
     *
     * @param geohash
     * @return
     */
    public static long decode(String geohash) {

        int flag = 0;
        for (int i = 0; i < geohash.length(); i++) {
            char c = geohash.charAt(i);
            int decodeValue = (indexDic(c) & 0x1F);
            flag = (flag << 5) | decodeValue;
        }
        return flag;
    }

    public static int genValidBitNum(int treeDepth) {
        return ((treeDepth * 2) % 5 != 0 ? ((treeDepth * 2) / 5 + 1) * 5 : treeDepth * 2);
    }


    /**
     * 组合lat和lng的位数
     * 为一个Long
     *
     * @param lngBit
     * @param latBit
     * @param lngBitNum
     * @param latBitNum
     * @return
     */
    public static long composeLatLngBits(long lngBit, long latBit, int lngBitNum, int latBitNum) {

        int total = (lngBitNum + latBitNum);
        long flag = 0;
        for (int i = 0; i < total / 2; i++) {
            byte idx_lng = (byte) (lngBit >> (lngBitNum - i - 1) & 0x01);
            byte idx_lat = (byte) (latBit >> (latBitNum - i - 1) & 0x01);
            flag = (flag << 1) | idx_lng;
            flag = (flag << 1) | idx_lat;
        }

        if (lngBitNum < latBitNum) {
            flag = flag << 1 | (latBit & 0x01);
        } else if (lngBitNum > latBitNum) {
            flag = flag << 1 | (lngBit & 0x01);
        }
        return flag;
    }

    /**
     * 通过给定的geohash
     * 获取原生lng 和 lat 对应的标志位序列
     *
     * @param geohash
     * @param treeDepth geohash编码树的深度
     * @return
     */
    public static long[] decomposeLatLng(String geohash, int treeDepth) {

        int bitNum = genValidBitNum(treeDepth);
        long compose = decode(geohash);
        long lat = 0L;
        long lng = 0L;
        for (int i = 0; i < bitNum; i++) {
            /**
             * 计算Lat和lng原生值
             */
            if (i % 2 == 0) {
                //偶数位为经度
                lng = (lng << 1) | ((compose >> (bitNum - i - 1)) & (0x01));
            } else {
                //奇数位为维度
                lat = (lat << 1) | ((compose >> (bitNum - i - 1)) & (0x01));
            }
        }
        long[] latLng = new long[]{lng, lat};
        return latLng;
    }

    /**
     * 对应的扩大的经纬度的bit位因子
     */
    private static final byte[][] EXPAND_FACOTR = new byte[][]{
        {0, 0},{-1, 1}, {0, 1}, {1, 1},
            {-1, 0}, {1, 0},
            {-1, -1}, {0, -1}, {1, -1}
    };

    /**
     * 扩大geohash
     * 找出临近自己的8个geohash
     *
     * @param level   扩大的层级
     * @param geohash
     * @return
     */
    public static List<String> expandGeoHash(String geohash, int treeDepth, int lngBitNum, int latBitNum, int level) {

        List<String> expand = new ArrayList<String>(8);
        /**
         * 解析出经度和纬度的bit
         */
        long[] bits = decomposeLatLng(geohash, treeDepth);
        for (byte[] factor : EXPAND_FACOTR) {
            long lngBit = bits[0] + factor[0] * level;
            long latBit = bits[1] + factor[1] * level;
            //如果得到的纬度小于0，则到了边界，所以直接忽略
            if (latBit < 0) {
                continue;
            }
            long compose = composeLatLngBits(lngBit, latBit, lngBitNum, latBitNum);
            String geocode = encode(compose, treeDepth);
            expand.add(geocode);
        }

        //放大的位数做一个组合
        return expand;

    }

    /**
     * 按照距离关系排序
     * @param geohash
     * @param treeDepth
     * @param hashList
     * @return
     */
    public static Map<Integer,List<String>> sortByDistanceLevel(String geohash, int treeDepth,List<String> hashList) {
        TreeMap<Integer,List<String>> map = new TreeMap<Integer, List<String>>();
        for (String hash : hashList){
            Integer level = distanceLevel(geohash,hash,treeDepth);
            List<String>  list = map.get(level);
            if(list == null){
                list = new LinkedList<String>();
                map.put(level,list);
            }
            list.add(hash);
        }
        return map;
    }

    public static int distanceLevel(String geohash1,String geohash2,int treeDepth){
        long[] bits1 = decomposeLatLng(geohash1, treeDepth);
        long[] bits2 = decomposeLatLng(geohash2, treeDepth);
        long latDelta = Math.abs(bits1[1] - bits2[1]);
        long lngDelta = Math.abs(bits1[0] - bits2[0]);
        return (int)Math.max(latDelta,lngDelta);
    }
    /**
     * 正方形的方式放大geohash，下面是level对应的格子位置
     *
     *              2   2   2   2   2
     *              2   1   1   1   2
     *              2   1   0   1   2
     *              2   1   1   1   2
     *              2   2   2   2   2
     *
     *
     * @param geohash
     * @param treeDepth
     * @param lngBitNum
     * @param latBitNum
     * @param level
     * @return
     */
    public static List<String> expandGeoHashSquare(String geohash, int treeDepth, int lngBitNum, int latBitNum, int level){
        List<String> expand = new ArrayList<String>(8*level);
        if(level == 0){
            expand.add(geohash);
        }
        long[] bits = decomposeLatLng(geohash, treeDepth);
//        long southwestLng = bits[0] -  level;
//        long southWestLat = bits[1] -  level;
//
//        long northwestLng = bits[0] -  level;
//        long northWestLat = bits[1] +  level;
//
//        long northeastLng = bits[0] +  level;
//        long northeastLat = bits[1] +  level;
//
//        long southeastLng = bits[0] +  level;
//        long southeastLat = bits[1] -  level;


        for(long lat = bits[1] -  level, lng = bits[0] -  level; lat < bits[1] +  level; lat ++){
            if( lat >= 1 << latBitNum){
                break;
            }
            long compose = composeLatLngBits(lng, lat, lngBitNum, latBitNum);
            String geocode = encode(compose, treeDepth);
            expand.add(geocode);
        }

        for(long lat = bits[1] +  level, lng = bits[0] -  level; lng < bits[0] +  level; lng ++){
            long compose = composeLatLngBits(lng, lat, lngBitNum, latBitNum);
            String geocode = encode(compose, treeDepth);
            expand.add(geocode);
        }

        for(long lat = bits[1] +  level, lng = bits[0] +  level; lat > bits[1] -  level; lat --){
            if( lat < 0){
                break;
            }
            long compose = composeLatLngBits(lng, lat, lngBitNum, latBitNum);
            String geocode = encode(compose, treeDepth);
            expand.add(geocode);
        }

        for(long lat = bits[1] -  level, lng = bits[0] +  level; lng > bits[0] -  level; lng --){
            long compose = composeLatLngBits(lng, lat, lngBitNum, latBitNum);
            String geocode = encode(compose, treeDepth);
            expand.add(geocode);
        }

        return expand;
    }

    public static void main(String[] args){
       // System.err.println(distanceLevel("dqcj","dqz0",20));
//        System.err.println(Arrays.toString(expandGeoHashSquare("upzp", 20, 10, 10, 20).toArray()));
//        System.err.println(Arrays.toString(expandGeoHashSquare("h0p0", 20, 10, 10, 20).toArray()));
//        System.err.println(Arrays.toString(expandGeoHashSquare("h1x5", 20, 10, 10, 20).toArray()));

//        for(int i = -63 ; i <= 64 ;i++){
//            System.err.print(composeLatLngBits(i, 13l, 5, 5));
//            if(i %32==0) {
//                System.err.println("\n");
//            }
//        }
//        String hash = randomHash(6);
//        List<String> hashs = new ArrayList<String>();
//
//            for(int i = 0 ; i < 200000; i++){
//                hashs.add(randomHash(6));
//            }
//
//
//        System.out.println(System.currentTimeMillis());
//        long start = System.currentTimeMillis();
//    for(int j = 0 ; j < 100; j++) {
//        for (String h : hashs){
//            distanceLevel(hash,h,30);
//        } }
//        System.out.println(System.currentTimeMillis() - start);
    	System.out.println(composeLatLngBits(4, 4, 10, 10));;
    }


    public static String randomHash(int length){
        StringBuffer buffer = new StringBuffer();
        for(int i = 0 ; i < length ; i++){
            buffer.append(DIC[(int)(System.nanoTime()%32)]);
        }
        return buffer.toString();
    }

}
         //[0000dqby, 0000dqcn, 0000dqcq, 0000dqbv, 0000dqcm, 0000dqbu, 0000dqch, 0000dqck]