package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipException;

import javacommon.util.IpUtils;
import javacommon.util.ZipUtils;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.OperateTargetTypeEnum;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.model.MediaResource;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.ResourceDirectory;
import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.IManagerOperateLogService;
import com.dangdang.digital.service.IMediaResourceService;
import com.dangdang.digital.service.IResourceDirectoryService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.HttpUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.utils.ZipUtil;


@Controller
@RequestMapping("mediaResource")
public class MediaResourceController extends BaseController {

	@Resource
	private IMediaResourceService mediaResourceService;
	@Resource
	private IResourceDirectoryService resourceDirectoryService;
	
	private String CDN_INTERFACE_URL="cdn.interface.url";
	
	private String UPLOAD_TEMP_DIR="upload.temp.dir";
	
	private String SERVER_URL_PREFIX = "server.url.prefix";
	
	private String SERVER_RESOURCE_ROOT_PATH ="server.resource.root.path";
	
	private String DANGDANG_USER_TYPE = "1";
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaResourceController.class);
	
	@Resource
	private IManagerOperateLogService managerOperateLogService;
	
	@RequestMapping("add")
	public String add(ResourceDirectory dir, 
			final Model model) throws IOException {
		try {
			
			List<ResourceDirectory> list = resourceDirectoryService.findListByParams("code",dir.getCode(),"isCdn",dir.getIsCdn(),
					"parentId",dir.getParentId());
			List<ResourceDirectory> listName = resourceDirectoryService.findListByParams("name",dir.getName(),
					"isCdn",dir.getIsCdn(),"parentId",dir.getParentId());
			boolean checkSuccess = true;
			String msg = "";
			dir.setCode(dir.getName());
			if((dir.getDirId() != null && listName.size() == 1 && !listName.get(0).getDirId().equals(dir.getDirId()))
					|| (dir.getDirId()==null && listName.size()==1)){
				msg+="名称重复；";
				checkSuccess = false;
			}
			if(!checkSuccess){
				model.addAttribute("cate", dir);
				model.addAttribute("msg", msg);
				if(dir.getDirId() == null){
					if(dir.getParentId() != null){
						model.addAttribute("parentId",dir.getParentId().toString());
					}
					return "resource/add";
				}
			}
			if(dir.getParentId()!=null){
				dir.setPath(resourceDirectoryService.get(dir.getParentId()).getPath()+"/"+dir.getCode());
			}else{
				dir.setPath(dir.getCode());
			}
			resourceDirectoryService.save(dir);
			
			LogUtil.info(LOGGER,"管理员:{0}在操作资源目录【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					dir.getName());
			// 插入操作日志
			managerOperateLogService
					.insertOperateLog(new ManagerOperateLog(
							null,
							Constans.MANAGER_OPERATE_RESULT_SUCCESS,
							OperateTargetTypeEnum.RESOURCE_DIRECTORY.getType(),
							dir.getDirId().longValue(),
							UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
									.getCurrentUser().getLoginName()
									: "system", new Date(), "，修改前内容："
											+ JSON.toJSONString(dir)
											+ "，修改后内容："
											+ JSON.toJSONString(dir)));
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在操作资源目录【{1}】失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					dir.getName());
		}
		return "redirect:main.go?id="+dir.getDirId()+"&isCdn="+dir.getIsCdn();
	}
	
	@RequestMapping("main")
	public String main(final Model model,ResourceDirectory dir){
		
		StringBuffer msg = new StringBuffer("[{\"name\":\"目录\",\"open\":true,\"id\":\"0\",\"children\":[");
		List<ResourceDirectory> list = resourceDirectoryService.getTreeByParentId(dir);
		if(list.size()>0){
			getTree(list,msg);
		}
		msg.append("]}]");
		String id = getRequest().getParameter("id");
		if(StringUtils.isNotBlank(id)){
			model.addAttribute("cateId", id);
		}
		model.addAttribute("isCdn", dir.getIsCdn());
		model.addAttribute("data", msg  );
		return "resource/list";
	}
	
	private void getTree(List<ResourceDirectory> list,StringBuffer msg){
		String cateIds = getRequest().getParameter("cateIds");
		Set<String> set = new HashSet<String>();
		if(cateIds!=null && !"".equals(cateIds)){
			String arr[] = cateIds.split(",");
			for(String id :arr)
				set.add(id);
		}
		for(ResourceDirectory dir : list){
			msg.append("{\"name\":\""+dir.getName()+"\",");
			msg.append("\"id\":\""+dir.getDirId()+"\",");
			msg.append("\"code\":\""+dir.getCode()+"\",");
			msg.append("\"parentId\":\""+dir.getParentId()+"\",");
			
			List<ResourceDirectory> treeList = resourceDirectoryService.getTreeByParentId(dir);
			if(treeList.size() > 0){
				msg.append("\"isParent\":true,\"nocheck\":true,");
			}else{
				msg.append("\"isParent\":false,");
				if(set.contains(dir.getDirId().toString())){
					msg.append("\"checked\":true,");
				}
			}
			if(treeList.size() > 0){
				msg.append("\"children\":[");
				getTree(treeList,msg);
				msg.append("],");
			}
			msg.deleteCharAt(msg.length() - 1);
			msg.append("},");
		}
		msg.deleteCharAt(msg.length() - 1);
	}
	
	@RequestMapping(value="/toAdd")
	public String toAdd(Style style,final Model model){
		String parentId = getRequest().getParameter("parentId");
		if(!"0".equals(parentId)){
			model.addAttribute("parentId", parentId);
		}
		model.addAttribute("isCdn", getRequest().getParameter("isCdn"));
		return "resource/add";
	}
	
	
	@RequestMapping(value="/delDir")
	@ResponseBody
	public String delDir(ResourceDirectory dir){
		try {
			dir = resourceDirectoryService.get(dir.getDirId());
			
			if(dir.getIsCdn() == 1){
				return "{\"success\":false,\"msg\":\"非服务器目录下的文件不允许删除！\"}";
			}
			if(StringUtils.isNotBlank(dir.getPath())){
				Map map = new HashMap();
				map.put("path", dir.getPath());
				mediaResourceService.delByPath(map);
				File f = new File(ConfigPropertieUtils.getString(SERVER_RESOURCE_ROOT_PATH)+File.separator+dir.getPath());
				if(f.exists()){
					FileUtils.deleteDirectory(f);
				}
			}else{
				return "{\"success\":true}";
			}
			managerOperateLogService
			.insertOperateLog(new ManagerOperateLog(
					null,
					Constans.MANAGER_OPERATE_RESULT_SUCCESS,
					OperateTargetTypeEnum.RESOURCE_DIRECTORY.getType(),
					dir.getDirId().longValue(),
					UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
							.getCurrentUser().getLoginName()
							: "system", new Date(), "删除目录及文件:dirId:"+",fileName:"
							+",path="+dir.getPath()));
			return "{\"success\":true}";
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "删除资源文件失败,dirId={0}", dir.getDirId());
			return "{\"success\":fasle,\"msg\":\"操作失败！错误:\""+e.getMessage()+"}";
		}
	}
	@RequestMapping(value="/delFile")
	@ResponseBody
	public String delFile(MediaResource resource){
		try {
			resource = mediaResourceService.get(resource.getFileId());
			ResourceDirectory dir = resourceDirectoryService.get(resource.getDirId());
			if(dir.getIsCdn() == 1){
				return "{\"success\":false,\"msg\":\"非服务器目录下的文件不允许删除！\"}";
			}
			mediaResourceService.deleteById(resource.getFileId());
			File f = new File(ConfigPropertieUtils.getString(SERVER_RESOURCE_ROOT_PATH)+File.separator+dir.getPath()+File.separator+resource.getFileName());
			if(f.exists()){
				f.delete();
			}
			managerOperateLogService
			.insertOperateLog(new ManagerOperateLog(
					null,
					Constans.MANAGER_OPERATE_RESULT_SUCCESS,
					OperateTargetTypeEnum.RESOURCE.getType(),
					resource.getFileId(),
					UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
							.getCurrentUser().getLoginName()
							: "system", new Date(), "删除资源文件:fileId:"+resource.getFileName()+",fileName:"+
							resource.getFileName()+",path="+dir.getPath()));
			return "{\"success\":true}";
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "删除资源文件失败,fileId={0}", resource.getFileId());
			return "{\"success\":fasle,\"msg\":\"操作失败！错误:\""+e.getMessage()+"}";
		}
	}
	
	@RequestMapping("list")
	public String list(Model model, Query query,ResourceDirectory dir){
		Map<String, String> paramMap = new HashMap<String, String>();
		
		MediaResource res = new MediaResource();
		res.setIsCdn(Integer.valueOf(getRequest().getParameter("isCdn")));
		model.addAttribute("isCdn", res.getIsCdn());
		if(dir.getDirId() != null && dir.getDirId().intValue() != 0){
			paramMap.put("dirId", dir.getDirId().toString());
			res.setDirPath(resourceDirectoryService.get(dir.getDirId()).getPath());
			model.addAttribute("dirId", dir.getDirId().toString());
		}
		
		
		
		PageFinder<MediaResource> pageFinder = mediaResourceService.findPageFinderObjs(res, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		return "resource/dataList";
	}
	
	@RequestMapping(value="/toUpload")
	public String toUpload(Model model){
		model.addAttribute("dirId", getRequest().getParameter("dirId"));
		return "resource/upload";
	}
	
	/**
	 * 递归列出一个目录中所有的子目录
	 * @param root
	 * @param list
	 */
	private void recurAllFolders(File root,List<File> list) {
		  for (File file : root.listFiles()) {
		    if (file.isDirectory()) {
		    	list.add(file);
		    	recurAllFolders(file,list);
		    }
		  }
	}
	
	@RequestMapping(value="/upload")
	@ResponseBody
	public String upload(Model model,@RequestParam(value="file",required=false) MultipartFile file){

		String msg = "";
		try {
			
			boolean isZip = file.getOriginalFilename().toLowerCase().endsWith("zip");
			String dirId = getRequest().getParameter("dirId");
			ResourceDirectory dir = resourceDirectoryService.get(Integer.valueOf(dirId));
			Integer isCdn = dir.getIsCdn();
			
			if(StringUtils.isBlank(dirId)){
				msg = "{\"success\":false,msg:\""+"请选择目录再上传！"+"\"}";
				return msg;
			}
			if(file.getSize() == 0){
				msg = "{\"success\":false,msg:\""+"上传文件为空，请上传正确文件！"+"\"}";
				return msg;
			}
			if(!isZip){
				List<MediaResource> list = mediaResourceService.findListByParams("fileName",file.getOriginalFilename(),"dirId",dirId);
				MediaResource res = new MediaResource();
				if(list.size()>0){
					res = list.get(0);
				}
				res.setDirId(Integer.valueOf(dirId));
				res.setUploadDate(Calendar.getInstance().getTime());
				res.setUploader(UsercmsUtils.getCurrentUser().getLoginName());
				res.setFileSize(file.getSize());
				res.setFileName(file.getOriginalFilename());
				if(isCdn == 1){
					String cdnPath = toCDN(file.getInputStream(),dir.getPath()+"/"+file.getOriginalFilename());
					if(cdnPath == null){
						throw new Exception("上传CDN出错");
					}
					res.setCdnPath(cdnPath);
				}else{
					File parent = new File(ConfigPropertieUtils.getString(SERVER_RESOURCE_ROOT_PATH)+File.separator+dir.getPath());
					parent.mkdirs();
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(parent,file.getOriginalFilename()));
					res.setCdnPath(ConfigPropertieUtils.getString(SERVER_URL_PREFIX)+"/"+dir.getPath()+"/"+file.getOriginalFilename());
				}
				if(res.getFileId() == null){
					mediaResourceService.save(res);
				}else{
					mediaResourceService.update(res);
				}
			}else{
				File zipFile = new File(ConfigPropertieUtils.getString(UPLOAD_TEMP_DIR)+File.separator+UUID.randomUUID().toString()+".zip");
				File zipDir = new File(ConfigPropertieUtils.getString(UPLOAD_TEMP_DIR)+File.separator+UUID.randomUUID().toString());
				zipDir.mkdirs();
				FileUtils.copyInputStreamToFile(file.getInputStream(), zipFile);
				ZipUtil.unZip(zipFile, zipDir.getAbsolutePath());
				Collection<File> list = FileUtils.listFiles(zipDir, null, true);
				
				List<File> dirList = new ArrayList<File>();
				recurAllFolders(zipDir, dirList);
				
				
				StringBuffer sb = new StringBuffer();
				if(dirList.size()>0){
					for(File f : dirList){
						if(f.isDirectory()){
							String zipPath = f.getAbsolutePath().replace(zipDir.getAbsolutePath()+File.separator, "");
							zipPath = zipPath.replace(File.separator, "/");
							List exist = resourceDirectoryService.findListByParams("path",dir.getPath()+"/"+zipPath);
							
					        if(exist.size() == 0){
					        	sb.append(f.getAbsolutePath().replace(zipDir.getAbsolutePath()+File.separator, "")).append(";");
					        }
						}
					}
				}
				if(sb.length()>0){
					throw new Exception("以下目录不存在，请先创建："+sb.toString()+".");
				}
				List<MediaResource> resList = new ArrayList<MediaResource>();
				Collection<File> collection = FileUtils.listFiles(zipDir, null, true);
				Iterator<File> iter = collection.iterator();
				
				while(iter.hasNext()){
					File f = iter.next();
					if(!f.isDirectory()){
						String path = f.getParentFile().getAbsolutePath();
						
						ResourceDirectory parentDir = null;
						if(path.equals(zipDir.getAbsolutePath())){
							parentDir = dir;
						}else{
							path = path.replace(zipDir.getAbsolutePath()+File.separator, "");
							path = path.replace(File.separator, "/");
							List<ResourceDirectory> ll = resourceDirectoryService.findListByParams("path",dir.getPath()+"/"+path);
							parentDir = ll.get(0);
						}
						
						List<MediaResource> exist = mediaResourceService.findListByParams("fileName",f.getName(),"dirId",parentDir.getDirId());
						MediaResource res = new MediaResource();
						if(exist.size()>0){
							res = exist.get(0);
						}
						res.setDirId(parentDir.getDirId());
						res.setUploadDate(Calendar.getInstance().getTime());
						res.setUploader(UsercmsUtils.getCurrentUser().getLoginName());
						res.setFileSize(f.length());
						res.setFileName(f.getName());
						
						if(isCdn == 1){
							String cdnPath = toCDN(new FileInputStream(f),parentDir.getPath()+"/"+f.getName());
							if(cdnPath == null){
								throw new Exception("上传CDN出错");
							}
							res.setCdnPath(cdnPath);
						}else{
							File parent = new File(ConfigPropertieUtils.getString(SERVER_RESOURCE_ROOT_PATH)+File.separator+parentDir.getPath());
							parent.mkdirs();
							FileUtils.copyInputStreamToFile(new FileInputStream(f), new File(parent,f.getName()));
							res.setCdnPath(ConfigPropertieUtils.getString(SERVER_URL_PREFIX)+"/"+parentDir.getPath()+"/"+res.getFileName());
						}
						if(res.getFileId() == null){
							mediaResourceService.save(res);
						}else{
							mediaResourceService.update(res);
						}
					}
				}
				zipFile.delete();
				FileUtils.deleteDirectory(zipDir);
			}
			msg = "{\"success\":true}";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "{\"success\":false,msg:\""+"出错了，error:"+e.getMessage()+"\"}";
		}
		return msg;
	
	}
	
	private String toCDN(InputStream inp,String path) throws Exception{
		byte[] bytes = new byte[inp.available()];
		int length = inp.read(bytes);
		
		String uri = ConfigPropertieUtils.getString(CDN_INTERFACE_URL);
		StringBuilder sb = new StringBuilder(uri);
		//文集名后缀
		sb.append("?fullfileurl=").append(path);
		//ip
		sb.append("&upload_ip=").append(IpUtils.getIpAddr(getRequest()));
		//当当用户类型
		sb.append("&operator_type=").append(DANGDANG_USER_TYPE);
		//上传图片操作用户名
		sb.append("&operator_name=").append(UsercmsUtils.getCurrentUser().getLoginName());
		//resutlJson格式：true|文件的url 或者 false|失败原因
		String resultJson = HttpUtils.getContentByPost(sb.toString(), bytes);
		
		inp.close();
		if(StringUtils.isNotBlank(resultJson) && "true".equals(resultJson.substring(0, resultJson.indexOf("|")))){
			int start = resultJson.indexOf("|");
			String uploadUrl = resultJson.substring(start+1);
			if(uploadUrl.length()>4){
				return uploadUrl;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		File f = new File("D:\\data\\83eb119c-a9ae-4f0f-9407-96d67cef7905");
		System.out.println(f.listFiles().length);
	}
}
