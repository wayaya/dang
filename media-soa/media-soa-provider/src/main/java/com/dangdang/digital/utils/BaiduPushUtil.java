package com.dangdang.digital.utils;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.model.PushParam;
 
public class BaiduPushUtil {
	private static Integer deployStatus = ConfigPropertieUtils.getInteger("media.push.baidu.ios.deploystatus", 2);
    public static String getUnsignedParameterMapString(PushParam param, Map<String, Object> pushMessage ){
    	
    	Map<String, String> parameters = new TreeMap<String, String>();
        parameters.put("method", "push_msg");
        // 访问令牌
        parameters.put("apikey", param.getApiKey());
        // 推送类型，取值范围为：1～3
        // 1：单个人，必须指定user_id 和 channel_id （指定用户的指定设备）或者user_id（指定用户的所有设备）
        // 2：一群人，必须指定 tag
        // 3：所有人，无需指定tag、user_id、channel_id
        parameters.put("push_type", param.getPushType() + "");
        // 设备类型
        // 1：浏览器设备；
        // 2：PC设备；
        // 3：Android设备；
        // 4：iOS设备；
        // 5：Windows Phone设备；
        parameters.put("device_type", param.getDeviceType() + "");
        
        // 推送类型，取值范围为：1～3
        // 1：单个人，必须指定user_id 和 channel_id （指定用户的指定设备）或者user_id（指定用户的所有设备）
        // 2：一群人，必须指定 tag
        // 3：所有人，无需指定tag、user_id、channel_id
        if(param.getPushType()==1){
	        parameters.put("user_id", param.getUserId());
            parameters.put("channel_id", param.getChannelId());
        }
        
        // 消息类型
        // 0：消息（透传给应用的消息体）
        // 1：通知（对应设备上的消息通知）
        // 默认值为0。
        int message_type = param.getMessageType();
        parameters.put("message_type", message_type + "");
    	parameters.put("messages", JSON.toJSONString(pushMessage));
        
        // 消息标识。
        // 指定消息标识，必须和messages一一对应。相同消息标识的消息会自动覆盖。
        String msg_keys = UUID.randomUUID().toString();
        parameters.put("msg_keys", msg_keys);
        parameters.put("secret", param.getSecret());
        //IOS 用的 1开发环境 2 线上环境 
        parameters.put("deploy_status", deployStatus+"");
    	
    	return JSON.toJSONString(parameters);
    }
    
    /**
     * MD5加密
     * 
     * @param s
     * @return
     */
    private static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
 
    /**
     * Map排序
     * 
     * @param unsort_map
     * @return
     */
 
    private static SortedMap<String, String> mapSortByKey(Map<String, String> unsort_map) {
        TreeMap<String, String> result = new TreeMap<String, String>();
        Object[] unsort_key = unsort_map.keySet().toArray();
        Arrays.sort(unsort_key);
        for (int i = 0; i < unsort_key.length; i++) {
            result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
        }
        return result.tailMap(result.firstKey());
    }
 
    /**
     * 获取签名
     * 
     * @param url
     * @param parameters
     * @param secret
     * @return
     */
    private static String getSignature(String url, Map<String, String> parameters, String secret) {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new HashMap<String, String>(parameters);
        sortedParams = mapSortByKey(sortedParams);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder baseString = new StringBuilder();
        baseString.append("POST");
        baseString.append(url);
        for (String key : sortedParams.keySet()) {
            if (null != key && !"".equals(key)) {
                baseString.append(key).append("=").append(sortedParams.get(key));
            }
            sortedParams.get(key);
        }
        baseString.append(secret);
        
        String encodeString = URLEncoder.encode(baseString.toString());
        String sign = MD5(encodeString);
        return sign;
    }
 
}