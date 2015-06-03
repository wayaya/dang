package com.dangdang.digital.service.nearby.impl.nearby.util;



public class GeoUtil {

    private static final double EARTH_RADIUS = 6378137;

    /**
     * 计算距离
     * @param lat
     * @param lng
     * @param lat2
     * @param lng2
     * @return
     */
    public static double distance(double lat, double lng, double lat2, double lng2) {
        double radLat1 = deg2rad(lat2);
        double radLat2 = deg2rad(lat);
        double radLng1 = deg2rad(lng2);
        double radLng2 = deg2rad(lng);

        double a = radLat1 - radLat2;//两纬度之差,纬度<90
        double b = radLng1 - radLng2;//两经度之差纬度<180

        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) *
                Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))) * EARTH_RADIUS;
        return distance;
    }


    private static double deg2rad(double d) {
        return Math.toRadians(d);
    }


    /**
     * 中国的边界
     * bounds: {
     * northeast: {
     * lat: 53.56097399999999,
     * lng: 134.7728099
     * },
     * southwest: {
     * lat: 18.1535216,
     * lng: 73.4994136
     * }
     * },
     */

    private static final Pair<Double, Double> LAT = new Pair<Double, Double>(18.1535216, 53.56097399999999);

    private static final Pair<Double, Double> LNG = new Pair<Double, Double>(73.4994136, 134.7728099);

    /**
     * 只有在中国
     *
     * @param lat
     * @param lng
     * @return
     */
    public static boolean isChina(double lat, double lng) {
        if (lat >= LAT.left && lat <= LAT.right &&
                lng >= LNG.left && lng <= LNG.right) {
            return true;
        }
        return false;
    }
    
   public static Pair<Double, Double> randomInChina(){
	   Double lat = Math.random()*(LAT.right-LAT.left)+LAT.left;
	   Double lng= Math.random()*(LNG.right-LNG.left)+LNG.left;
	   return new Pair<Double, Double>(lat,lng);
   }
   public static void main(String[] args){
	   Pair<Double, Double> value=randomInChina();
	   System.out.println("randomLat:"+value.left+",random:Lng:"+value.right);
   }
}
