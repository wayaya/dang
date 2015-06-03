package com.dangdang.digital.controlller;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javacommon.util.IpUtils;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.model.Label;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.service.IAuthorService;
import com.dangdang.digital.service.IBookAuthorService;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IDigestLableService;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.Hanzi_Pinyin;
import com.dangdang.digital.utils.HttpUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

@Controller
@RequestMapping("digest")
public class DigestController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DigestController.class);
	
	@Resource
	private IDigestService digestService;
	@Resource
	private ICatetoryService catetoryService;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IMediaService mediaService;
	@Resource
	protected IAuthorService authorService;
	@Resource
	private IChapterService chapterService;
	
	@Resource
	private IBookAuthorService bookAuthorService;
	@Resource
	private IDigestLableService digestLableService;
	
	//当当用户上传
	private static final Integer DANGDANG_USER_TYPE = 1;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final Digest dis,HttpServletRequest request){
		PageFinder<Digest> pageFinder = digestService.findPageFinderObjs(dis,query);
		if(pageFinder != null){
			if(!CollectionUtils.isEmpty(pageFinder.getData())){
				Map<String,String> isPublishMap = new HashMap<String,String>();
				for(Digest digest : pageFinder.getData()){
					isPublishMap.put(digest.getId().toString(), getIsPublish(digest));
				}
				model.addAttribute("isPublishMap", isPublishMap);
			}
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("dis", dis);
		int type = 1;
		model.addAttribute("type", type);
		return "digest/list";
	}
	
	private String getIsPublish(Digest digest){
		String isPublish="未发布";
		Date now = new Date(); 
		Date time=digest.getShowStartDate();
		if(time!=null){
			boolean flag = time.before(now);
			if(digest.getIsShow()==0 && flag){
				isPublish= "未发布";
			}else if(digest.getIsShow()==1 && flag){
				isPublish= "已发布";
			}
		}else{
			isPublish="未发布";
		}
		return isPublish;
	}
	
	@RequestMapping(value="/toSetTime")
	public String toSetTime(@RequestParam final String ids,final Model model){
		if(StringUtils.isEmpty(ids)){
			throw new RuntimeException("参数错误");
		}
		String[] idarray = ids.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for(String idstr : idarray){
			idlist.add(Long.valueOf(idstr));
		}
		
		List<Digest> digestlist = digestService.findByIds(idlist);
		if(digestlist == null || digestlist.size()==0){
			throw new RuntimeException("参数错误");
		}
		StringBuilder idsb = new StringBuilder();
		for(Digest digest : digestlist){
			if(Digest.DIGEST_ISSHOW_STATUS_TRUE.equals(digest.getIsShow())){
				throw new RuntimeException("已展示文章不允许修改生效时间");
			}
			if(idsb.length() !=0){
				idsb.append(",");
			}
			idsb.append(digest.getId());
		}
		model.addAttribute("ids",idsb.toString());
		
		return "digest/settime";
	}
	
	@RequestMapping(value="/setTime")
	public String setTime(Digest dis, @RequestParam final String ids) throws ParseException{
		try {
			
			Date start = null;
			String startDate = getRequest().getParameter("startDate");
			
			if(StringUtils.isNotEmpty(startDate)){
				start = DateUtil.parseStringToDate(startDate);
			}
			String[] idarray = ids.split(",");
			List<Long> idlist = new ArrayList<Long>();
			for(String idstr : idarray){
				idlist.add(Long.valueOf(idstr));
			}
			List<Digest> digestlist = digestService.findByIds(idlist);
			for(Digest digest : digestlist){
				if(Digest.DIGEST_ISSHOW_STATUS_TRUE.equals(digest.getIsShow())){
					throw new RuntimeException("已展示文章不允许修改生效时间");
				}
				if(Digest.DIGEST_ISSHOW_STATUS_TRUE.equals(dis.getIsShow())){
					digest.setIsShow(Digest.DIGEST_ISSHOW_STATUS_TRUE);
				}
				digest.setId(digest.getId());
				digest.setShowStartDate(start);
				digest.setOperator(UsercmsUtils.getCurrentUser().getLoginName());
				digest.setLastUpdateDate(new Date());
				digestService.update(digest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(LOGGER,e, "对发现数据设置有效时间出错", null);
		}
		return "redirect:list.go";
	}

	@RequestMapping(value="/toAddOrEdit")
	public String toAddOrEdit(Digest dis,final Model model,Chapter chap,Media media) throws IOException{
		String title = chap.getTitle();
        String name = media.getAuthorPenname();
		Long m=media.getMediaId();
		dis.setTitle(title);
		dis.setAuthor(name);
		Chapter chapter = chapterService.get(chap.getId());
		if(dis.getId() != null){
	//		dis = digestService.get(dis.getId());
			Long id=dis.getId();
			dis=digestService.getDigestContentById(id);
			File f = new File(ConfigPropertieUtils.getString("media.resource.digest.content.path")+File.separator+
					dis.getContent());
			if(f.exists()){
				String content = FileUtils.readFileToString(f);
				dis.setContent(content);
			}
			String aName=null;
			String[] splitNames =null;
			String[] allSplitNames=null;
			if(dis.getAuthor().contains(";")){
				splitNames = dis.getAuthor().split(";");
				for(int i=0;i<splitNames.length ;i++){
					String first = splitNames[i];
					if(first.contains(":")){
						allSplitNames =first.split(":");
						String inner=allSplitNames[allSplitNames.length-1];
						if(aName==null){
							aName= inner;
						}else{
							aName= aName+","+inner;
						}
						
					}
				}
				dis.setAuthor(aName);
			}else if(dis.getAuthor().contains(":")){
				allSplitNames =dis.getAuthor().split(":");
				aName=allSplitNames[allSplitNames.length-1];
				dis.setAuthor(aName);
			}
			/**修改标签**/
			String allIds=null;
			if(dis.getSignIds()!=null){
			String aSign=null;
			String[] splitSignNames =null;
			String[] allSplitSignNames=null;
			if(dis.getSignIds().contains(";")){
				splitSignNames = dis.getSignIds().split(";");
				for(int i=0;i<splitSignNames.length ;i++){
					String first = splitSignNames[i];
					if(first.contains(":")){
						allSplitSignNames =first.split(":");
						String inner=allSplitSignNames[allSplitSignNames.length-1];
						String outer=allSplitSignNames[0];
						if(aSign==null){
							aSign= inner;
							allIds=outer;
						}else{
							aSign= aSign+","+inner;
							allIds=allIds+","+outer;
						}
						
					}
				}
				dis.setSignIds(aSign);
				
			}else if(dis.getSignIds().contains(":")){
				allSplitSignNames =dis.getSignIds().split(":");
				aSign=allSplitSignNames[allSplitSignNames.length-1];
				dis.setSignIds(aSign);
				allIds=allSplitSignNames[0];
			}
			}
			model.addAttribute("allSignIds", allIds);
			
			/**修改标签结束**/
			model.addAttribute("dis", dis);
			model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.digest.http.path")+"/");
			Map<String,Object> signIdNameMap =getSignByDigestId();
			model.addAttribute("signIdNameMap", signIdNameMap);
			model.addAttribute("chapter", chapter);
		}else{
			//获取所有标签数据
			Map<String,Object> signIdNameMap = getAllSign();
			model.addAttribute("signIdNameMap", signIdNameMap);
			model.addAttribute("dis", dis);
			model.addAttribute("chapter", chapter);
		}
		return  "digest/info";
	}
	
	@RequestMapping(value="/del")
	public String del(Digest dis){
		try {
			if(dis.getId() != null){
				digestService.deleteById(dis.getId());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER,e, "删除发现数据出错", null);
		}
		return "redirect:list.go";
	}
	@RequestMapping(value="/updateIsShow")
	public String updateIsShow(Digest dis){
		try {
			
			Digest digest = digestService.get(dis.getId());
			if (digest != null) {
				if(Digest.DIGEST_ISSHOW_STATUS_FALSE.equals(digest.getIsShow())){
					throw new RuntimeException("文章未生效");
				}
				digest.setIsShow(Digest.DIGEST_ISSHOW_STATUS_FALSE);
				digest.setOperator(UsercmsUtils.getCurrentUser().getLoginName());
				digest.setLastUpdateDate(new Date());
				digestService.update(digest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(LOGGER,e, "对发现数据设置有效时间出错", null);
		}
		return "redirect:list.go";
	}
	
	@RequestMapping(value="/getMediaById")
	@ResponseBody
	public String getMediaById(Digest dis){
		Media media = mediaService.get(dis.getMediaId());
		if(media==null){
			return "{\"success\":false}";
		}else{
			return "{\"success\":true}";
		}
	}
	@RequestMapping(value="/update")
	public String saveOrUpdate(Digest dis,final Model model,HttpServletRequest request) throws IOException{
			dis.setOperator(UsercmsUtils.getCurrentUser().getLoginName());
			// 作者
			List<Long> authorIds = new ArrayList<Long>();
			String authors = dis.getAuthor();
			String[] names = null;
			StringBuilder containers = new StringBuilder();
			names = authors.split(",|，|;");
			for (int i = 0; i < names.length && i < 3; i++) {
				String name = names[i].trim();
				List<Author> authorlist = authorService.findListByParams(
						"name", name, "cpType", "dianzishu");
				if(authorlist==null || authorlist.size() == 0){
					Author author = new Author();
					author.setCpType("dianzishu");
					author.setPseudonym(name);
					author.setName(name);
					authorService.save(author);
					authorIds.add(author.getAuthorId());
					if (containers.length() == 0) {
						containers.append(author.getAuthorId() + ":" + author.getName());
					} else {
						containers.append(";" + author.getAuthorId() + ":" + author.getName());
					}
				}else{
					Author author = authorlist.get(0);
					authorIds.add(author.getAuthorId());
					if (containers.length() == 0) {
						containers.append(author.getAuthorId() + ":" + author.getName());
					} else {
						containers.append(";" + author.getAuthorId() + ":" + author.getName());
					}
				}
			}
			dis.setAuthor(containers.toString());// 作者的信息：1:韩寒;2:周立波
			
			//标签
			String signIdNames=null;
			String signIdName=null;
			String allSignNames=request.getParameter("signNames");//小清新,健康养生,都市情感
			List<Long> signList=new ArrayList<Long>();
			String allSignIds=dis.getSignIds();
			if(allSignNames!=null && allSignNames != "" &&allSignNames.length()!=0){
				String[] signNames = null;
				String[] signIds = null;
				signNames = allSignNames.split(",");
				signIds = allSignIds.split(",");
				for(int i=0;i<signNames.length && i < 3;i++){
					String signName = signNames[i];
					String signId = signIds[i];
					signList.add(Long.valueOf(signId));
					signIdName=signId+":"+signName;
					if(signIdNames==null){
						signIdNames=signIdName;
					}else{
						signIdNames=signIdNames.concat(";"+signIdName);
					}
				}
				dis.setSignIds(signIdNames);
			}
			/**修改标签结束**/
			String showStartDateStr = getRequest().getParameter("showStartDateStr");
			if(StringUtils.isNotBlank(showStartDateStr)){
				dis.setShowStartDate(DateUtil.parseStringToDate(showStartDateStr));
			}
			if(dis.getMediaId() != null){
				Media media  = mediaService.get(dis.getMediaId());
				dis.setMediaName(media.getTitle());
				//分类
				StringBuffer cateNames = new StringBuffer();
				List<Catetory> list = catetoryService.getCatetoryByMediaId(dis.getMediaId());
				for(Catetory cate : list){
					cateNames.append(cate.getName()).append(",");
				}
				if(list.size() > 0){
					cateNames.deleteCharAt(cateNames.length() - 1);
				}
				dis.setFirstCatetoryName(cateNames.toString());
			}
			//文字模板，纯文字
			if(dis.getCardType().intValue() == 0){
				dis.setPic1Path(null);
			}
			if(dis.getCardTitle() !=null){
				dis.setTitle(dis.getCardTitle());
			}
			Long digestId=null;
			if(dis.getId() !=null){
				dis.setLastUpdateDate(Calendar.getInstance().getTime());
				digestService.update(dis);
				digestId=dis.getId();
			}else{
				dis.setCreateDate(Calendar.getInstance().getTime());
				digestService.save(dis);
				digestId=dis.getId();
			}
			
			//存入作者文章关联表
			List<BookAuthor> oldbookAuthor = bookAuthorService.findListByParams("digestId",digestId);//库(只能有一条信息)
			if(oldbookAuthor.size()==0){
				BookAuthor ba=new BookAuthor();
				ba.setDigestId(digestId);
				for(Long auId:authorIds){
					ba.setAuthorId(auId);
					bookAuthorService.save(ba);
				}
			}else{
				for(BookAuthor bookauthor : oldbookAuthor){
					bookAuthorService.deleteById(bookauthor.getId());
				}
				BookAuthor ba=new BookAuthor();
				ba.setDigestId(digestId);
				for(Long auId:authorIds){
					ba.setAuthorId(auId);//set
					bookAuthorService.save(ba);
				}
			}
			
//			Map<Long,BookAuthor> oldMap = new HashMap<Long, BookAuthor>();
//			for(BookAuthor ba : oldbookAuthor){
//				oldMap.put(ba.getAuthorId(), ba);
//			}
//			List<Long> newList = new ArrayList<Long>();
//			for(Long auId:authorIds){//前台
//				if ( oldMap.containsKey(auId)){
//					oldMap.remove(auId);//??????
//				}else{
//					newList.add(auId);
//				}
//			}
//			if(!oldMap.isEmpty()){//作者表里的数据不需要清空下吗？多条重复数据
//				for(BookAuthor ba : oldMap.values()){
//					bookAuthorService.deleteById(ba.getId());
//					ba.setId(null);
//					for(Long auId:authorIds){
//						ba.setAuthorId(auId);
//						bookAuthorService.save(ba);
//					}
//					
//				}
//			}
//			if(!newList.isEmpty()){
//				for(Long auId : newList){
//					BookAuthor ba = new BookAuthor();
//					ba.setDigestId(digestId);
//					ba.setAuthorId(auId);
//					bookAuthorService.save(ba);
//				}
//			}
			
			//文章标签关联表
			List<DigestLable> oldDigestLable = digestLableService.findListByParams("digestId",digestId);
			Map<Long,DigestLable> oldLableMap = new HashMap<Long, DigestLable>();
			for(DigestLable dl:oldDigestLable){
				oldLableMap.put(dl.getSignId(), dl);
			}
			List<Long> newSignList = new ArrayList<Long>();
			for(Long sId:signList){
				if(oldLableMap.containsKey(sId)){
					oldLableMap.remove(sId);
				}else{
					newSignList.add(sId);
				}
			}
			if(!oldLableMap.isEmpty()){
				for(DigestLable dl:oldLableMap.values()){
					digestLableService.deleteById(dl.getId());
					dl.setId(null);
					digestService.saveDigestLable(dl);
				}
			}
			if(!newSignList.isEmpty()){
				for(Long aSignId : newSignList){
					DigestLable dl=new DigestLable();
					dl.setDigestId(digestId);
					dl.setSignId(aSignId);
					digestService.saveDigestLable(dl);
				}
			}
			
			
		return "redirect:list.go?type="+dis.getType();
	}
	
	@RequestMapping(value="/uploadImg")
	@ResponseBody
	public void uploadImg(@RequestParam("upfile") MultipartFile file, HttpServletRequest request ) throws IOException{
		String month =DateUtil.getDate(Calendar.getInstance().getTime(), "yyyy-MM");
		String fileName = month + UUID.randomUUID().toString().replaceAll("-", "");
		String fileType = getImageType(file.getContentType());
		InputStream inputStream = file.getInputStream();
		Map<String,String> map = new HashMap<String, String>();
		try {
			byte[] bytes = new byte[inputStream.available()];
			int length = inputStream.read(bytes);
			if(length != -1){
				String uri = ConfigPropertieUtils.getString("anthology.background.suffix.upload");
				StringBuilder sb = new StringBuilder(uri);
				//文集名后缀
				sb.append("?filesuffix=").append(fileName + "." + fileType);
				//ip
				sb.append("&upload_ip=").append(IpUtils.getIpAddr(request));
				//当当用户类型
				sb.append("&operator_type=").append(DANGDANG_USER_TYPE);
				//上传图片操作用户名
				sb.append("&operator_name=").append(UsercmsUtils.getCurrentUser().getLoginName());
				//resutlJson格式：true|文件的url 或者 false|失败原因
				String resultJson = HttpUtils.getContentByPost(sb.toString(), bytes);
				if(StringUtils.isNotBlank(resultJson) && "true".equals(resultJson.substring(0, resultJson.indexOf("|")))){
					int start = resultJson.indexOf("|");
					String uploadUrl = resultJson.substring(start+1);
					LOGGER.info(uploadUrl);
					map.put("state", "SUCCESS");
					map.put("title", fileName);
					map.put("type", ".jpg");
					map.put("original", file.getOriginalFilename());
					map.put("url", uploadUrl);
					map.put("size", file.getSize()+"");
					map.put("width", "100%");
				}else{
					LOGGER.error("文件上传失败");
					map.put("state", "FAIL");
					return;
				}
			}else{
				LOGGER.error("文件数据为空");
				map.put("state", "FAIL");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			map.put("state", "FAIL");
			return;
		}finally{
			if(inputStream != null){
				inputStream.close();
			}
		}
		
		getResponse().setHeader("Content-Type" , "text/html");
		getResponse().getWriter().write(JSONObject.toJSONString(map));
		getResponse().getWriter().flush();
	}
	
	public Map<String, Object> getAllSign() {
		List<Map<String, Object>> list=digestService.getSign();
		Map<String,Object> signIdNameMap = new HashMap<String,Object>();
		for( Map<String, Object> b : list ) {
			String name=null;
			Object sign_id=null;
			for (Iterator iterator = b .keySet().iterator(); iterator.hasNext();) {
	            String key =  (String)iterator.next();
	            if(key.contentEquals("NAME")){
	            	name=(String) b.get(key);
	            }else if(key.contentEquals("sign_id")){
	            	sign_id=b.get(key);
	            }
	        }
			signIdNameMap.put(name, sign_id);
		}
		return signIdNameMap;
		
	}
	
	public Map<String, Object> getSignByDigestId() {
		List<Map<String, Object>> list=digestService.getSignByDigestId();
		Map<String,Object> signIdNameMap = new HashMap<String,Object>();
		for( Map<String, Object> b : list ) {
			String name=null;
			Object sign_id=null;
			for (Iterator iterator = b .keySet().iterator(); iterator.hasNext();) {
	            String key =  (String)iterator.next();
	            System.out.println(key+"="+b .get(key));
	            if(key.contentEquals("NAME")){
	            	name=(String) b.get(key);
	            }else if(key.contentEquals("sign_id")){
	            	sign_id=b.get(key);
	            }
	        }
			signIdNameMap.put(name, sign_id);
			System.out.println(signIdNameMap);
		}
		return signIdNameMap;
		
	}
	
	@RequestMapping(value="/findDigestByAuthorId")
	public String findDigestByAuthorId(Query query,final Model model,@RequestParam final Long authorId){
		PageFinder<Digest> pageFinder = digestService.findDigestByAuthorId(query, authorId, null);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("authorId", authorId);
		return "digest/listByAuthor";
	}
	
	@RequestMapping(value="/findDigestBySignId")
	public String findDigestBySignId(Query query,final Model model,@RequestParam final Long signId){
		PageFinder<Digest> pageFinder = digestService.findDigestBySignId(query, signId, null);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("signId", signId);
		return "digest/listBySign";
	}
	
	private String getImageType(String contentType){
		if(StringUtils.isBlank(contentType)){
			return null;
		}
		if(contentType.indexOf("jp")>=0){
			return "jpg";
		} else if (contentType.indexOf("gif")>=0){
			return "gif";
		} else if (contentType.indexOf("png")>=0){
			return "png";
		}
		return null;
	}
	
	@RequestMapping(value="/toSelLabel")
	public String toSelLabel(final Model model){
		model.addAttribute("id", getRequest().getParameter("id"));
		model.addAttribute("name", getRequest().getParameter("name"));
		return "digest/selLabel";
	}
	@RequestMapping(value="/getLabel")
	public void getLabel() throws IOException{
		String chk = getRequest().getParameter("chk");
		Map map = new HashMap();
		if(chk!=null && !"".equals(chk)){
			String chkArr[] = chk.split(",");
			for(String str : chkArr){
				map.put(str, "exist");
			}
		}
		Label label = new Label();
		label.setType(0);
		label.setIsenabled(true);
		Query query = new Query();
		query.setPage(1);
		query.setPageSize(Integer.MAX_VALUE);
		StringBuffer sBuff = new StringBuffer();
		sBuff.append("<info><peoples dbId=\"-1\" leaf=\"false\" checked=\"false\" text=\""
				+ ("全部") + "\">");
		
		List<Map<String, Object>> list=digestService.getSign();
		for( Map<String, Object> b : list ) {
			String name=null;
			Object sign_id=null;
			for (Iterator iterator = b .keySet().iterator(); iterator.hasNext();) {
	            String key =  (String)iterator.next();
	            if(key.contentEquals("NAME")){
	            	name=(String) b.get(key);
	            }
	            else if(key.contentEquals("sign_id")){
	            	sign_id=b.get(key);
	            }
	        }
			sBuff.append("<name dbId=\""
					+ sign_id
					+ "\" checked=\""
					+ map.containsKey(sign_id.toString())
					+ "\" py=\""
					+ (Hanzi_Pinyin
							.getHeaderByChar(name, true))
					+ "\" text=\""
					+ (name)
					+ "\" leaf=\"true\"/>");
		}

		sBuff.append("</peoples></info>");
		toJson(sBuff);
		
	}
}
