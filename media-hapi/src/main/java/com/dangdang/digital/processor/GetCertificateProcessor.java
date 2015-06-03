package com.dangdang.digital.processor;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.encrypt.EncodeFactory;
import com.dangdang.digital.encrypt.EncodeFactory.Version;
import com.dangdang.digital.encrypt.IEncode;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.Base64Certificate;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.RsaUtils;
import com.dangdang.digital.vo.MediaCacheBasicVo;

/**
 * 
 * Description: 验证是否是最后一章 All Rights Reserved.
 * 
 * @version 1.0 2014年12月2日 上午10:07:02 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetCertificateprocessor")
public class GetCertificateProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetCertificateProcessor.class);

	@Resource
	private IMediaService mediaService;

	@Resource(name = "systemApi")
	private ISystemApi systemApi;

	@Resource(name = "cacheApi")
	private ICacheApi cacheApi;

	private static String DEFAULT_CERTIFICATE_VERSION = "1.1";

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：书籍id
		String mediaIdStr = request.getParameter("mediaId");
		// 入参：加密公钥
		String publicKeyStr = request.getParameter("publicKey");

		if (!checkParams(mediaIdStr, publicKeyStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long mediaId = Long.valueOf(mediaIdStr);
			MediaCacheBasicVo mediaCache = cacheApi.getMediaBasicFromCache(mediaId);
			if (mediaCache == null || StringUtils.isBlank(mediaCache.getEncrypkey())) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
			}
			try {
				// 1、获取加密版本
				String version = systemApi.getProperty(Constans.RESOURCE_ENCODE_VERSION, DEFAULT_CERTIFICATE_VERSION);
				// 2、获取RSA公钥
				Key publicKey = RsaUtils.getPublicKey(publicKeyStr);
				
				// 3、对AES秘钥进行标准base64解码
				byte[] keyDecode = Base64.decodeBase64(mediaCache.getEncrypkey().getBytes("UTF-8"));
				// 4、用RSA公钥对AES秘钥加密
				byte[] keyEncrypt = RsaUtils.encrypt(publicKey, keyDecode);
				// 5、根据version做四种加密方式其中一种
				boolean encodeResult = false;
				if (EncodeFactory.Version.VERSION_1.getVersion().equals(version)) {
					IEncode encoder = EncodeFactory.getEncode(Version.VERSION_1);
					encodeResult = encoder.encode(keyEncrypt, keyEncrypt.length);
				} else if (EncodeFactory.Version.VERSION_2.getVersion().equals(version)) {
					IEncode encoder = EncodeFactory.getEncode(Version.VERSION_2);
					encodeResult = encoder.encode(keyEncrypt, keyEncrypt.length);
				} else if (EncodeFactory.Version.VERSION_3.getVersion().equals(version)) {
					IEncode encoder = EncodeFactory.getEncode(Version.VERSION_3);
					encodeResult = encoder.encode(keyEncrypt, keyEncrypt.length);
				} else if (EncodeFactory.Version.VERSION_4.getVersion().equals(version)) {
					IEncode encoder = EncodeFactory.getEncode(Version.VERSION_4);
					encodeResult = encoder.encode(keyEncrypt, keyEncrypt.length);
				}
				if (!encodeResult) {
					LogUtil.error(LOGGER, "加密失败");
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				}
				// 6、base64编码
				byte[] keyBase64 = Base64Certificate.encode(keyEncrypt);
				// 7、制作证书
				String certificate = this.buildXml(mediaId, new String(keyBase64, "UTF-8"), version);
				// 8、将证书返回客户端
				sender.put("certificate", certificate);
			} catch (Exception e) {
				LogUtil.error(LOGGER, e, "加密失败");
				sender.fail(ErrorCodeEnum.ERROR_CODE_12003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12003.getErrorMessage(), response);
			}

			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,chapterId:" + request.getParameter("chapterId"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}

	}

	/**
	 * 
	 * Description: 制作证书
	 * 
	 * @Version1.0 2014年12月9日 下午5:36:46 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @param key
	 * @return
	 * @throws IOException
	 */
	private String buildXml(Long mediaId, String key, String version) throws IOException {
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<rights version=\"");
		sb.append(version);
		sb.append("\"><agreement><asset><uid><![CDATA[");
		sb.append(mediaId);
		sb.append("]]></uid><key><![CDATA[");
		sb.append(key);
		sb.append("\r\n]]></key></asset><permission><play /></permission></agreement></rights>\r\n");
		return sb.toString();
	}

}
