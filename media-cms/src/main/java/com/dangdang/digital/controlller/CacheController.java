package com.dangdang.digital.controlller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.SpecialTopic;
import com.dangdang.digital.service.ISpecialTopicService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.UsercmsUtils;
/**
 * 
 * Description: 用于异步更新cache
 * All Rights Reserved.
 * @version 1.0  2015年1月8日 上午10:16:35  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Controller
@RequestMapping("cache")
public class CacheController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private ICacheApi  cacheApi;
	
	@Resource
	private ISpecialTopicService  specialTopicService;
	/**
	 * 
	 * Description: 根据栏目或者分类编号,清除相应的内容缓存信息
	 * @Version1.0 2015年1月8日 上午10:19:57 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code 
	 * @return
	 */
	@RequestMapping(value="/systempp/clearcache")
	@ResponseBody
	public String cleanSystemPropertyCache(String keyName){
		try {
			cacheApi.cleanSystemPropertyCache(keyName);
		} catch (Exception e) {
			LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 清除 系统参数,key=" + keyName ,e);
			return "{\"result\":\"failure\"}";
		}
		return "{\"result\":\"success\"}";
	}
	
	/**
	 * 
	 * Description: 根据栏目或者分类编号,清除相应的内容缓存信息
	 * @Version1.0 2015年1月8日 上午10:19:57 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code 
	 * @return
	 */
	@RequestMapping(value="/column/clearcache")
	@ResponseBody
	public String cleanColumnCache(String code){
		try {
			cacheApi.cleanColumnCachce(code);
		} catch (Exception e) {
			LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 清除 栏目或分类缓存失败,code=" + code ,e);
			return "{\"result\":\"failure\"}";
		}
		return "{\"result\":\"success\"}";
	}
	
	/**
	 * 
	 * Description: 根据榜单标识,清除相应的内容缓存信息
	 * @Version1.0 2015年1月8日 上午10:19:57 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code 榜单标识
	 * @return
	 */
	@RequestMapping(value="/ranking/clearcache")
	@ResponseBody
	public String cleanListRankingCache(String listType){
		try {
			cacheApi.cleanListRankingCache(listType);
		} catch (Exception e) {
			LogUtil.info(logger, UsercmsUtils.getCurrentUser().getLoginName()+" 清除 榜单,code=" + listType ,e);
			return "{\"result\":\"failure\"}";
		}
		return "{\"result\":\"success\"}";
	}
	/**
	 * Description: 清楚专题缓存
	 * All Rights Reserved.
	 * @version 1.0  2015年3月20日 下午5:18:30  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param listStId
	 * @return
	 */
	@RequestMapping(value="/specialTopic/clearcache")
	@ResponseBody
	public String cleanListSpecialTopicCache(String stId){
		String [] specialIds = stId.split(",");
		List<Integer> specialIntegerIds =  new ArrayList<Integer>();
		for(String id: specialIds){
			specialIntegerIds.add(Integer.parseInt(id));
		}
		List<SpecialTopic> listSpecialTopic = specialTopicService.findByIds(specialIntegerIds);
		
		if(null != listSpecialTopic ){
			Set<String> cacheKeys = new HashSet<String>(listSpecialTopic.size());
			for(SpecialTopic st: listSpecialTopic){
				String cacheKey = null;
				if(1==st.getShowHomePage().intValue()){
					//主页显示专题key的缓存
					cacheKey = Constans.HOME_PAGE_ST_LIST_CACHE_KEY+(st.getDeviceType()+"_"+st.getChannelType()).toLowerCase();
					cacheKeys.add(cacheKey);
					//如果设备是通用的三种设备缓存都要清理一下，写死了，之后找个好办法（要相信我们会越来越聪明的！）
					if(st.getChannelType().toLowerCase().equals("yc_all")){
						cacheKeys.add(Constans.HOME_PAGE_ST_LIST_CACHE_KEY+("yc_android_"+st.getChannelType()).toLowerCase());
						cacheKeys.add(Constans.HOME_PAGE_ST_LIST_CACHE_KEY+("yc_iphone_"+st.getChannelType()).toLowerCase());
					}
				}
				String	singleSpecialCacheKey = Constans.SPECIAL_TOPIC_CACHE_KEY+st.getStId();
				cacheKeys.add(singleSpecialCacheKey);
			}
			try{
				cacheApi.cleanSpecialTopicCache(cacheKeys);
			} catch (Exception e){
				logger.error("清除专题id="+Arrays.asList(stId)+"失败！", e);
				return "{\"result\":\"failure\"}";
			}
		}
		return "{\"result\":\"success\"}";
	}
}
