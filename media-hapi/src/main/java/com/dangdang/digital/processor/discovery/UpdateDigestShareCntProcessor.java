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
 * Description: 更新精品分享次数
 * All Rights Reserved.
 * @version 1.0  2015年1月31日 下午4:08:58  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiupdateDigestShareCntprocessor")
public class UpdateDigestShareCntProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateDigestShareCntProcessor.class);
	
	@Resource
	private IDigestService digestService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//精品id
		String digestId = request.getParameter("digestId");
				
		//校验digestId必传参数
		if(!checkParams(digestId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		try {
			Digest digest = digestService.findDigestById(Long.parseLong(digestId));
			if(digest != null){
				Integer shareCnt = digest.getShareCnt();
				if(shareCnt == null){
					shareCnt = 0;
				}
				digest.setShareCnt(shareCnt + 1);
				digestService.update(digest);
			}
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "更新精品分享次数请求失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}

}
