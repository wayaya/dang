package com.dangdang.digital.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RecommendUtils {

	public static TreeMap<Long, Long> mergeBehaviorMap(TreeMap<Long, Long> oldMap, TreeMap<Long, Long> periodMap){
		// key是时间， value是 mediaId
		TreeMap<Long, Long> result = new TreeMap<Long, Long>();
		
		if(oldMap == null || oldMap.size()==0){
			return periodMap;
		}
		
		if(periodMap == null || periodMap.size()==0){
			return oldMap;
		}
		
		//反过来存，最后再翻
		Map<Long, Long> mediaToTime = new HashMap<Long, Long>();
		
		for(Long time : oldMap.keySet()){
			mediaToTime.put(oldMap.get(time), time);
		}
		for(Long time : periodMap.keySet()){
			
			Long mediaId = periodMap.get(time);
			if(mediaToTime.containsKey(mediaId)){
				if(mediaToTime.get(mediaId)<time){
					mediaToTime.put(mediaId, time);
				}
			}else{
				mediaToTime.put(mediaId, time);
			}
		}
		
		for(Long mediaId: mediaToTime.keySet()){
			result.put(mediaToTime.get(mediaId), mediaId);
		}
		
		return result;
	}
	
	public static void main(String[] args)throws Exception {
		
		TreeMap<Long, Long> old = new TreeMap<Long, Long>();
		TreeMap<Long, Long> newMap = new TreeMap<Long, Long>();
		old.put(30000L, 1L);
		old.put(20000L, 2L);
		old.put(10000L, 3L);
		
		newMap.put(35000L, 1L);
		newMap.put(40000L, 2L);
		newMap.put(5000L, 3L);
		newMap.put(50000L, 4L);
		
		TreeMap<Long, Long> result = mergeBehaviorMap(newMap, old);
		
		for(Long key: result.descendingKeySet()){
			System.out.println(key+"|"+result.get(key));
		}
		
	}
}
