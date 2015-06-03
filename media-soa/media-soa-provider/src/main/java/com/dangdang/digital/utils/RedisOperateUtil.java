package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisOperateUtil extends AbstractBaseRedisAdapter<String, String> {
	/**
	 * 新增 <br>
	 * @param user
	 * @return
	 */
	public void masterAdd(final String key, final String value) {
		masterRedisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getMasterStringSerializer();
				byte[] key1 = serializer.serialize(key);
				byte[] value1 = serializer.serialize(value);
				connection.set(key1, value1);
				return true;
			}
		});
	}
	
	/**
	 * 新增, 设置有效时间 <br>
	 * @param user
	 * @return
	 */
	public void masterAdd(final String key, final long liveTime, final String value) {
		masterRedisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getMasterStringSerializer();
				byte[] key1 = serializer.serialize(key);
				byte[] value1 = serializer.serialize(value);
				connection.setEx(key1, liveTime, value1);
				return true;
			}
		});
	}
	
	/**
	 * Description: 批量保存redis
	 * @Version1.0 2014年6月12日 上午9:35:15 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean masterMAdd(final Map<String, String> tuple) {
		boolean result = masterRedisTemplate
				.execute(new RedisCallback<Boolean>() {
					public Boolean doInRedis(RedisConnection connection)
							throws DataAccessException {
						connection.mSet(serialize(tuple));
						return true;
					}
				});
		return result;
	}

	/**
	 * 删除 <br>
	 * ------------------------------<br>
	 * 
	 * @param key
	 */
	public void mastarDeleteByKey(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		mastarDeleteByKeys(list);
	}

	/**
	 * 删除多个 <br>
	 * ------------------------------<br>
	 * 
	 * @param keys
	 */
	public void mastarDeleteByKeys(List<String> keys) {
		masterRedisTemplate.delete(keys);
	}

	/**
	 * 通过key获取 <br>
	 * ------------------------------<br>
	 * 
	 * @param keyId
	 * @return
	 */
	public String slaveGet(final String keyId) {
		String result = slaveRedisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getSlaveStringSerializer();
				byte[] key = serializer.serialize(keyId);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				String result = serializer.deserialize(value);
				return result;
			}
		});
		return result;
	}
	
	/**
	 * Description: 如果是一次获取多个key值得话，我们返回值有序list，key1对应的是list中索引为0的数据
	 * @Version1.0 2014年6月11日 下午6:28:19 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key1
	 * @param key2
	 * @return
	 */
	public List<String> slaveMGet(final List<String> keys) {
		List<String> results = slaveRedisTemplate.execute(new RedisCallback<List<String>>() {
			public List<String> doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<String> retults = new ArrayList<String>();
				RedisSerializer<String> serializer = getSlaveStringSerializer();
				byte[][] ret = new byte[keys.size()][];

				for (int i = 0; i < ret.length; i++) {
					ret[i] = serializer.serialize(keys.get(i));
				}
				List<byte[]> value = connection.mGet(ret);
				if (value == null) {
					return null;
				}else{
					for(int i = 0; i < value.size(); i++){
						String result = serializer.deserialize(value.get(i));
						if(StringUtils.isNotBlank(result) && !"null".equals(result)){
							retults.add(result);
						}
					}
				}
				return retults;
			}
		});
		return results;
	}
	
	/**
	 * Description: 如果是一次获取多个key值得话，我们返回值有序list，key1对应的是list中索引为0的数据
	 * @Version1.0 2014年6月11日 下午6:28:19 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key1
	 * @param key2
	 * @return
	 */
	public List<String> slaveMGet(final String... keys) {
		List<String> resultValues = new ArrayList<String>();
		String results = slaveRedisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				StringBuffer retults = new StringBuffer();
				RedisSerializer<String> serializer = getSlaveStringSerializer();
				byte[][] ret = new byte[keys.length][];

				for (int i = 0; i < ret.length; i++) {
					ret[i] = serializer.serialize(keys[i]);
				}
				List<byte[]> value = connection.mGet(ret);
				if (value == null) {
					return null;
				}else{
					for(int i = 0; i < value.size(); i++){
						String result = serializer.deserialize(value.get(i));
						if(retults.length() > 0){
							retults.append("&*&").append(result);
						}else{
							retults.append(result);
						}
					}
				}
				return retults.toString();
			}
		});
		
		if(StringUtils.isNotBlank(results)){
			String[] values = results.split("&*&");
			if(values.length > 0){
				for(String value : values){
					resultValues.add(value);
				}
				
			}
		}
		return resultValues;
	}
	
	/**
	 * Description: 设置某个key的有效时间 
	 * @Version1.0 2014年6月11日 下午1:54:29 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @param liveTime 单位是（秒）
	 */
	public void expire(List<String> keys, long liveTime){
		for(String key : keys){
			masterRedisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Description: 查询一个key是否存在
	 * @Version1.0 2014年6月11日 下午2:46:21 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return slaveRedisTemplate.hasKey(key);
	}
	
	/**
	 * Description: 查询一个key是否存在
	 * @Version1.0 2014年6月11日 下午2:46:21 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @return
	 */
	public boolean hasKeys(List<String> keys) {
		for(String key : keys){
			boolean flag = slaveRedisTemplate.hasKey(key);
			if(!flag){
				return false;
			}
		}
		return true;
	}
	
	public Map<byte[], byte[]> serialize(Map<String, String> hashes) {
		Map<byte[], byte[]> ret = new LinkedHashMap<byte[], byte[]>(hashes.size());
		RedisSerializer<String> serializer = getMasterStringSerializer();
		for (Map.Entry<String, String> entry : hashes.entrySet()) {
			ret.put(serializer.serialize(entry.getKey()), serializer.serialize(entry.getValue()));
		}

		return ret;
	}
	
	/**
	 * Description: 存放有续集合
	 * @Version1.0 2014年7月8日 上午11:08:44 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @param value
	 * @param score
	 * @return
	 */
	public boolean masterOpsForZSetAdd(String key, String value, double score) {
		return masterRedisTemplate.opsForZSet().add(key, value, score);
	}
	
	/**
	 * Description: 获取有续集合
	 * @Version1.0 2014年7月8日 上午11:08:44 by 王志伟（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @param value
	 * @param score
	 * @return
	 */
	public Set<String> slaveOpsForZSet(String key, Long start, Long end) {
		return slaveRedisTemplate.opsForZSet().range(key, start, end);
	}
}
