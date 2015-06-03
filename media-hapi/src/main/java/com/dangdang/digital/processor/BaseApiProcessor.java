package com.dangdang.digital.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.DDClick;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DDClickUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MD5Utils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ZipUtil;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;

/**
 * Description: 移动Api接口的基类 All Rights Reserved.
 * 
 * @version 1.0 2015年1月15日 上午9:22:06 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public abstract class BaseApiProcessor extends ApiProcessor {

	private final String isLogSave = ConfigPropertieUtils.getString("ddclick.mongo.save");

	@Resource(name = "masterRedisTemplate")
	protected RedisTemplate<String, Object> masterRedisTemplate;
	
	//精品默认分页大小
	protected static final Integer DIGEST_DEFAULT_PAGE_SIZE = ConfigPropertieUtils.getInteger("digest.default.page.size", 10);

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 证书格式.
	 */
	public static final String CERTI_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<rights version=\"1.0\"><agreement><asset>" + "<uid><![CDATA[%s]]></uid>"
			+ "<key><![CDATA[%s]]></key></asset>" + "<permission><play/></permission></agreement></rights>";

	/**
	 * 通过app.env配置项判断运行环境，测试环境根据API_CONFIG表的配置，可以返回假数据.
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, ResultSender sender, String action)
			throws Exception {
		process(request, response, sender);
		if (!"multiAction".equals(action)) {
			
			DDClick click = getDDClickData(action, request);
			//区分翻篇儿和当读小说click
			String uri = request.getRequestURI();
			if(StringUtils.isNotBlank(uri) && uri.contains("api2")){
				saveDDClickData(click, sender, Constans.HAPI_DDCLICK_DIGEST_SAVE_QUEUE);
			}else{
				saveDDClickData(click, sender, Constans.HAPI_DDCLICK_SAVE_QUEUE);
			}
		}
	}

	private void saveDDClickData(DDClick click, ResultSender sender, String key) {
		if (click != null) {
			if (click.getCustId() == null && sender.getCustId() != null) {
				click.setCustId(sender.getCustId());
			}
			
			if("search".equals(click.getActionName())&&StringUtils.isEmpty(click.getDeviceSerialNo())){
				return;
			}
			
			if (StringUtils.isNotEmpty(sender.getResultContent())) {
				click.setStatusCode(sender.getStatusCode());
				click.setResultContent(sender.getResultContent());
			}
			masterRedisTemplate.opsForList().leftPush(key, click);
		}
	}

	private DDClick getDDClickData(String action, HttpServletRequest request) {
		if (StringUtils.isEmpty(isLogSave) || !"true".equals(isLogSave)) {
			return null;
		}
		return DDClickUtils.getCommonDDClick(action, action, request);
	}

	/**
	 * 
	 * Description: 下载单章文件
	 * 
	 * @Version1.0 2014年12月3日 下午6:53:35 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param chapter
	 * @param sender
	 * @throws Exception
	 */
	protected void downloadChapter(HttpServletRequest request, HttpServletResponse response,
			ChapterCacheBasicVo chapter, ResultSender sender, Date deadline) throws Exception {
		if (chapter == null || StringUtils.isBlank(chapter.getFilePath())) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
			return;
		}
		
		// 拆分出文件名
		String[] pathArray = StringUtils.split(chapter.getFilePath(), File.separator);
		String filename = pathArray[pathArray.length - 1];
		
		// 文件下载路径
		String filepath = null;
		if(filename.toLowerCase().endsWith("zip")){
			filepath = ConfigPropertieUtils.getString("media.chapter.resource.zipdownload.path") + "/"
					+ chapter.getFilePath();
		}else{
			filepath = ConfigPropertieUtils.getString("media.chapter.resource.download.path") + "/"
					+ chapter.getFilePath();
		}
				
		LogUtil.info(LOGGER, "filepath:" + filepath);
		// 文件在服务器完整的存储路径
		String serverPath = ConfigPropertieUtils.getString("media.encryp.path") + File.separator
				+ chapter.getFilePath();
		LogUtil.info(LOGGER, "serverPath:" + serverPath);
		
		// 获取文件长度
		long length = new File(serverPath).length();
		LogUtil.info(LOGGER, "length:" + length);
		// 获取断点续传起始位置
		String range = request.getHeader("Range");
		// 调用通用方法下载
		sender.download(response, filename, filepath, length, range, deadline);
	}

	/**
	 * 
	 * Description: 打包多个章节并下载
	 * 
	 * @Version1.0 2014年12月4日 上午9:46:37 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param chapterList
	 * @param sender
	 * @throws Exception
	 */
	protected void downloadChapter(HttpServletRequest request, HttpServletResponse response,
			List<ChapterCacheBasicVo> chapterList, ResultSender sender, Date deadline) throws Exception {
		List<File> fileList = new ArrayList<File>();
		List<Long> chapterIds = new ArrayList<Long>();
		String range = request.getHeader("Range");
		for (ChapterCacheBasicVo chapter : chapterList) {
			if (chapter == null || StringUtils.isBlank(chapter.getFilePath())) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			File file = new File(ConfigPropertieUtils.getString("media.encryp.path") + File.separator
					+ chapter.getFilePath());
			chapterIds.add(chapter.getId());
			fileList.add(file);
		}
		String zipFileName = MD5Utils.getInstance().cell32(chapterIds.toString()) + ".zip";
		File zipFile = new File(ConfigPropertieUtils.getString("media.encryp.path") + File.separator
				+ Constans.TEMP_FILE_PATH + File.separator + zipFileName);
		// 文件下载路径
		String filepath = ConfigPropertieUtils.getString("media.chapter.resource.download.path") + "/"
				+ Constans.TEMP_FILE_PATH + "/" + zipFileName;
		log.info("下载文件路径请求:" + filepath);
		if (zipFile.exists()) {
			log.info("下载文件已经存在直接下载:" + zipFile.getPath());
			sender.download(response, zipFileName, filepath, zipFile.length(), range, deadline);
			return;
		}
		try {
			log.info("下载文件不存在，进行压缩:" + zipFile.getPath());
			ZipUtil.ZipFiles(fileList, zipFile);
			sender.download(response, zipFileName, filepath, zipFile.length(), range, deadline);
		} catch (IOException e) {
			LogUtil.error(LOGGER, e, "压缩文件出错");
			return;
		}
	}

	/**
	 * 
	 * Description: 下载单章文件
	 * 
	 * @Version1.0 2014年12月3日 下午6:53:35 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param chapter
	 * @param sender
	 * @throws Exception
	 */
	protected void downloadChapter(HttpServletRequest request, HttpServletResponse response, MediaCacheBasicVo media,
			ResultSender sender, Date deadline) throws Exception {
		if (media == null || StringUtils.isBlank(media.getFilePath())) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
			return;
		}
		// 文件下载路径
		String filepath = ConfigPropertieUtils.getString("media.chapter.resource.download.path") + "/"
				+ media.getFilePath();
		LogUtil.info(LOGGER, "filepath:" + filepath);
		// 文件在服务器完整的存储路径
		String serverPath = ConfigPropertieUtils.getString("media.encryp.path") + File.separator
				+ media.getFilePath();
		LogUtil.info(LOGGER, "serverPath:" + serverPath);
		// 文件名使用mediaId
		String filename = media.getMediaId() + ".zip";
		// 获取文件长度
		long length = new File(serverPath).length();
		LogUtil.info(LOGGER, "length:" + length);
		// 获取断点续传起始位置
		String range = request.getHeader("Range");
		// 调用通用方法下载
		sender.download(response, filename, filepath, length, range, deadline);
	}
	
	/**
	 * Description: 分页处理是否还有下一页
	 * @Version1.0 2015年1月22日 下午8:05:19 by 代鹏（daipeng@dangdang.com）创建
	 * @param collections
	 * @param size
	 * @return
	 */
	protected boolean hasNextPage(Collection<?> collections, int size){
		if(CollectionUtils.isEmpty(collections)){
			return false;
		}else if(collections.size() < size){
			return false;
		}
		return true;
	}
	
	/**
	 * Description: 转换客户端传过来的ids成集合类型(逗号分隔)
	 * @Version1.0 2015年2月5日 下午3:51:39 by 代鹏（daipeng@dangdang.com）创建
	 * @param ids
	 * @return
	 */
	protected List<Long> transferIdStrToList(String ids){
		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			if(idArr != null && idArr.length > 0){
				List<Long> idList = new ArrayList<Long>();
				for(String id:idArr){
					idList.add(Long.parseLong(id));
				}
				return idList;
			}
		}
		return new ArrayList<Long>();
	}
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected String get(HttpServletRequest request, String key, String defaultValue) {
		if ("deviceSerialNo".equals(key) || "deviceSn".equals(key)) {
			if (StringUtils.isNotBlank(request.getParameter("deviceSn"))) {
				return request.getParameter("deviceSn");
			} else if (StringUtils.isNotBlank(request.getParameter("deviceSerialNo"))) {
				return request.getParameter("deviceSerialNo");
			} else if (StringUtils.isNotBlank(request.getParameter("macAddr"))) {
				return request.getParameter("macAddr");
			} else {
				return defaultValue;
			}
		}
		return StringUtils.isBlank(request.getParameter(key)) ? defaultValue : request.getParameter(key);
	}
	
	
}
