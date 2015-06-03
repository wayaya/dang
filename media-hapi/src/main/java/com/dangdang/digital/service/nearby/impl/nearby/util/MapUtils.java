package com.dangdang.digital.service.nearby.impl.nearby.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUtils extends org.apache.commons.collections.MapUtils {

    private static Logger logger = Logger.getLogger(MapUtils.class);

    public static Map toMap(Object... objects) {
        Map map = new LinkedHashMap(); // here should be LinkedHashMap (mongo geoloc!!!)
        for (int i = 0; i < objects.length-1; i+=2) {
            map.put(objects[i], objects[i+1]);
        }
        return map;
    }

    public static <K, V> Map<K, V> toMap(List<K> keys, List<V> values) {
        Map<K, V> map = new LinkedHashMap<K, V>();
        for (int i = 0; i < Math.min(keys.size(), values.size()); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }

    public static void appendValueAsList(Map map, String key, Object value) {
        if (map.get(key) == null) map.put(key, new ArrayList<Object>());
        List<Object> list = (List<Object>) map.get(key);
        list.add(value);
        map.put(key, list);
    }

    /**
     * shallow clone
     */
    public static <K,V> Map<K, V> clone(Map<K, V> map) {
        Map<K, V> ret = null;
        try {
            ret = map.getClass().newInstance();
            for (K key : map.keySet()) {
                ret.put(key, map.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * <pre><code>
     * Map<Integer, Integer> map = toMap(1,3,2,4,5);
     * MapUtils.remove(map, new IMapRemovable<Integer, Integer>(){
     * 	public boolean removable(Integer k, Integer v) {
     * 		if (k % 2 == 0) return true;
     *		    return false;
     *	}
     * });
     * </code></pre>
     */
    public static <K, V> void remove(Map<K, V> map, IMapRemovable<K, V> removable) {
        List<K> keys = new ArrayList<K>();
        for (K key : map.keySet()) {
            if (removable.removable(key, map.get(key))) {
                keys.add(key);
            }
        }
        for (K key : keys) {
            map.remove(key);
        }
    }

    public static interface IMapRemovable<K, V> {
        public boolean removable(K k, V v) ;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = toMap(1,8,10,13,3,2,4,5);
        System.out.println(map);
        System.out.println(getIntValue(null, null, 0));

        Map<Integer, Integer> clone = clone(map);
        System.out.println(clone);

        remove(map, new IMapRemovable<Integer, Integer>(){
            @Override
            public boolean removable(Integer k, Integer v) {
                if (k == 2) return true;
                return false;
            }
        });
        System.out.println(map);


    }

}
