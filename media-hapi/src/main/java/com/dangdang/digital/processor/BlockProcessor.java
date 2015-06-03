package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Block;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 获取块
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午6:31:45  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapiblockprocessor")
public class BlockProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlockProcessor.class);
	@Resource
	ICacheService cacheService;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception   {
		//入参 获取块code
		String code = request.getParameter("code");
		//验证是否登陆
		try {
			if(!checkParams(code)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			Block block = cacheService.getBlockCache(code);
			sender.put("block", block.getContent());
			sender.put("mainImg", block.getPicPath());
			sender.success(response);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "获取块内容失败！块标识：",code);
			sender.fail(ErrorCodeEnum.ERROR_CODE_11102.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_11102.getErrorMessage(), response);
		}
	}

}
