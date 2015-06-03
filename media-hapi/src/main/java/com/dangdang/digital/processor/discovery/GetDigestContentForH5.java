/**
 * Description: GetDigestContentForH5.java
 * All Rights Reserved.
 * @version 1.0  2015年1月31日 下午3:18:02  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.processor.discovery;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IDigestApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 精品阅读详情页，获取
 * All Rights Reserved.
 * @version 1.0  2015年1月31日 下午3:18:02  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestContentForH5processor")
public class GetDigestContentForH5 extends BaseApiProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(GetDigestContentForH5.class);
	
	@Resource
	private IDigestApi idigestApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//精品id
		String digestId = request.getParameter("digestId");
		//标示请求来源，区分返回数据格式
		String from = request.getParameter("from");
				
		//校验digestId必传参数
		if(!checkParams(digestId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		try {
			Digest digest = idigestApi.findDigestContentById(Long.parseLong(digestId));
			if(digest == null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22001.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22001.getErrorMessage(), response);
				return;
			}
			String content = digest.getContent();
			if(StringUtils.isNotBlank(from) && from.equalsIgnoreCase("h5")){
				final String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
				Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
				Matcher m_style = p_style.matcher(content);
				content = m_style.replaceAll("");
				sender.put("content", content);
				sender.success(response);
				return;
			}else{//请求来自手机app端
				String filterContent = this.filterHtmlBrAndPTag(content);
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Content-Length", String.valueOf(filterContent.getBytes("utf-8").length));
				PrintWriter writer = response.getWriter();
				writer.write(filterContent);
				writer.flush();
				writer.close();
				return;
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "给H5提供精品内容请求失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}
	
	private String filterHtmlBrAndPTag(String content){
		//过滤br
		String regex_br = "<br>|<br/>";
		Pattern pattern_br = Pattern.compile(regex_br, Pattern.CASE_INSENSITIVE);
		Matcher matcher_br = pattern_br.matcher(content);
	    String filterBr = matcher_br.replaceAll("");
	    //中间内容为空的p标签
	    String regex_p = "<p>\\s*<\\/p>";
	    Pattern pattern_p = Pattern.compile(regex_p, Pattern.CASE_INSENSITIVE);
	    Matcher matcher_p = pattern_p.matcher(filterBr);
	    return matcher_p.replaceAll("");
	}
	
}
