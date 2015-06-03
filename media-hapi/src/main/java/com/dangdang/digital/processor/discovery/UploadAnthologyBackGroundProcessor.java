package com.dangdang.digital.processor.discovery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.HttpUtils;
import com.dangdang.digital.utils.IpUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 上传文集背景图片
 * All Rights Reserved.
 * @version 1.0  2015年2月10日 下午3:13:36  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiuploadAnthologyBackGroundprocessor")
public class UploadAnthologyBackGroundProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadAnthologyBackGroundProcessor.class);
	
	private static final List<String> fileSuffixs = new ArrayList<String>();
	
	static{
		fileSuffixs.add("BMP");
		fileSuffixs.add("JPG");
		fileSuffixs.add("JPEG");
		fileSuffixs.add("PNG");
		fileSuffixs.add("GIF");
		fileSuffixs.add("bmp");
		fileSuffixs.add("jpg");
		fileSuffixs.add("jpeg");
		fileSuffixs.add("gif");
	}
	
	//当当用户上传
	private static final Integer DANGDANG_USER_TYPE = 1;
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IAnthologyService anthologyService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		
		if(!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//未登录用户直接返回
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		//上传或编辑文集背景图片权限判断
		Long custId = custVo.getCustId();
		try {
			MultipartHttpServletRequest msr =  (MultipartHttpServletRequest)request;
			MultipartFile file = msr.getFile("fileName");
			//获取上传文件名
			String originalFilename = file.getOriginalFilename();
			int index = originalFilename.lastIndexOf(".");
			//获取文件后缀名
			String fileSuffix = originalFilename.substring(index+1);
			//校验上传文件是图片
			if(!fileSuffixs.contains(fileSuffix)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22026.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22026.getErrorMessage(), response);
				return;
			}
			byte[] bytes = file.getBytes();
			if(bytes != null && bytes.length > 0){
				String uri = ConfigPropertieUtils.getString("anthology.background.suffix.upload");
				StringBuilder sb = new StringBuilder(uri);
				//文集名后缀
				sb.append("?filesuffix=").append(custId + "_" + System.currentTimeMillis());
				//ip
				sb.append("&upload_ip=").append(IpUtils.getIpAddr(request));
				//当当用户类型
				sb.append("&operator_type=").append(DANGDANG_USER_TYPE);
				//上传图片操作用户名
				sb.append("&operator_name=").append(custVo.getUsername());
				//resutlJson格式：true|文件的url 或者 false|失败原因
				String resultJson = HttpUtils.getContentByPost(sb.toString(), bytes);
				if(StringUtils.isNotBlank(resultJson) && "true".equals(resultJson.substring(0, resultJson.indexOf("|")))){
					int start = resultJson.indexOf("|");
					String uploadUrl = resultJson.substring(start+1);
					sender.put("uploadUrl", uploadUrl);
					sender.success(response);
					return;
				}else{
					sender.fail(ErrorCodeEnum.ERROR_CODE_22013.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22013.getErrorMessage(), response);
					return;
				}
			}else{
				sender.fail(ErrorCodeEnum.ERROR_CODE_22014.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22014.getErrorMessage(), response);
				return;
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "上传文集背景图片失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_22013.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22013.getErrorMessage(), response);
			return;
		}
	}
	
}
