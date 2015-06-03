/**
 * Description: UpdateDigestShareCntProcessor.java
 * All Rights Reserved.
 * @version 1.0  2015年1月31日 下午4:08:58  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.processor.discovery;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * 更新精品点赞次数
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年5月20日 上午11:20:21  by 王冠华（wangguanhua@dangdang.com）创建
 */
@Component("hapiupdateDigestTopCntprocessor")
public class UpdateDigestTopCntProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateDigestTopCntProcessor.class);
	
	@Resource
	private IDigestService digestService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//精品id
		String digestId = request.getParameter("digestId");
		
		String deviceNo = request.getParameter("deviceNo");
				
		//校验digestId必传参数
		if(!checkParams(digestId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		try {
			Digest digest = digestService.findDigestById(Long.parseLong(digestId));
			if(digest != null){
				Integer topCnt = digest.getShareCnt();
				if(topCnt == null){
					topCnt = 0;
				}
				digest.setTopCnt(topCnt + 1);
				digestService.update(digest);
			}
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "更新精品点赞次数请求失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}

}