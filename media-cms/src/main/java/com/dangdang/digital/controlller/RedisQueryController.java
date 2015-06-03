/**
 * Description: JdbcSqlController.java
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:57:04  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.service.IRedisQueryService;
import com.dangdang.digital.utils.UsercmsUtils;

/**
 * Description: 自定sql查询
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:57:04  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Controller
@RequestMapping("redisQuery")
public class RedisQueryController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(RedisQueryController.class);

	@Resource
	private IRedisQueryService redisQueryService;
	
	@RequestMapping("master")
	public String master(Model model, String key) {
		
		if(!StringUtils.isEmpty(key)){
			logger.info(UsercmsUtils.getCurrentUser().getName()+"查看了主库 key:"+key);
			String result = JSON.toJSONString(redisQueryService.getMasterValue(key.trim()), true);
			result = getHtmlResult(result);
			
			if(StringUtils.isNotEmpty(result)){
				model.addAttribute("result", result);
			}
			if(StringUtils.isNotEmpty(key)){
				model.addAttribute("key", key);
			}
		}
		return "redisQuery/result";
	}
	
	@RequestMapping("slave")
	public String slave(Model model, String key) {
		
		if(!StringUtils.isEmpty(key)){
			logger.info(UsercmsUtils.getCurrentUser().getName()+"查看了从库 key:"+key);
			String result = JSON.toJSONString(redisQueryService.getSlaveValue(key.trim()), true);
			result = getHtmlResult(result);
			
			if(StringUtils.isNotEmpty(result)){
				model.addAttribute("result", result);
			}
			if(StringUtils.isNotEmpty(key)){
				model.addAttribute("key", key);
			}
		}
		return "redisQuery/result";
	}

	private String getHtmlResult(String result) {
		result = result.replaceAll("\\n", "<br />");
		result = result.replaceAll("\\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		return result;
	}
}
