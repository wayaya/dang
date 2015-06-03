package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.fastjson.JSON;
import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.constant.MediaSaleEnum;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.model.Label;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.Volume;
import com.dangdang.digital.service.IAuthorService;
import com.dangdang.digital.service.IBookAuthorService;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IDigestLableService;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.service.ILabelService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.ISysPropertiesService;
import com.dangdang.digital.service.IVolumeService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.Hanzi_Pinyin;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ScaleCoverImg;
import com.dangdang.digital.utils.UploadPicToCDN;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;
import com.ibm.icu.util.Calendar;

//當心好文作品管理和當讀小說作品管理功能基本一致，所以後台調用的service，dao，mapper等均為當讀小數的部分
@Controller
@RequestMapping("epub")
public class EpubController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EpubController.class);
	@Resource(name="mediaService")
	private IMediaService mediaService;
	@Resource
	private ICatetoryService catetoryService;
	@Resource
	private ILabelService labelService;
	@Resource
	private IVolumeService volumeService;
	@Resource
	private IChapterService chapterService;
	@Resource
	private IMediaSaleService mediaSaleService;
	@Resource
	private ISysPropertiesService service;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IBookReviewApi bookReviewApi;
	@Resource
	protected IAuthorService authorService;
	@Resource
	private IBookAuthorService bookAuthorService;
	@Resource
	private IDigestLableService digestLableService;
	
	private final String MEDIA_FULL_DEFAULT_PRICE_KEY = "media.full.default.price";
	//未加密文件地址
	private String FILE_PATH = "media.unencryp.path";
	@Resource
	private IDigestService digestService;
	
	@RequestMapping(value="/list")
	public String list(Query query,final Model model,final Media media){
		String parentId = getRequest().getParameter("parentId");
		String code = getRequest().getParameter("code");
		if(StringUtils.isBlank(parentId)){
			if(StringUtils.isBlank(code)){
				code = "DZS";
			}
			Catetory catetory = catetoryService.findUniqueByParams("code",code);
			parentId = catetory.getId().toString();
		}
		if(StringUtils.isNotBlank(code)){
		}
		if(parentId!=null && !"".equals(parentId)){
			Catetory cate = catetoryService.get(Integer.valueOf(parentId));
			List<Catetory> list = catetoryService.findListByParams("path",cate.getPath());
			StringBuffer sb = new StringBuffer();
			for(Catetory ca : list){
				sb.append(ca.getId()).append(",");
			}
			if(list.size() > 0){
				sb.deleteCharAt(sb.length()-1);
			}
			media.setCatetoryIds(sb.toString());
		}
		Map<String,Object> param = new HashMap();
		String shelf=getRequest().getParameter("shelfStatus");
		String orderby = getRequest().getParameter("orderby");
		if (StringUtils.isNotEmpty(orderby)) {
			param.put("orderby",orderby);
		}
		
		PageFinder<Media> pageFinder = mediaService.queryEpubMedia(media,query,param);
		Map<String,String> mediaCateMap = new HashMap<String,String>();
		List<Long> subjectIds = new ArrayList<Long>();
		//	//后添加分类名：
		for(Media m:pageFinder.getData()){
			
			StringBuffer cateIds = new StringBuffer();
			StringBuffer cateNames = new StringBuffer();
			Long mediaiId=m.getMediaId();
			subjectIds.add(mediaiId);
			List<Catetory> list = catetoryService.getCatetoryByMediaId(mediaiId);
			for(Catetory cate : list){
				cateIds.append(cate.getId()).append(",");
				cateNames.append(cate.getName()).append(",");
			}
			if(list.size() > 0){
				cateIds.deleteCharAt(cateIds.length() - 1);
				cateNames.deleteCharAt(cateNames.length() - 1);
			}
			//分类名：cateNames
			mediaCateMap.put(mediaiId.toString(), cateNames.toString());
		}
		//后添加分类名结束
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
			model.addAttribute("mediaCateMap", mediaCateMap);
		}
		//添加评论数
		Map<String, Long> bookReviewCount = bookReviewApi.queryBookReviewCount(subjectIds, BookReview.SUBJECT_TYPE_EBOOK);
		
		model.addAttribute("media", media);
		model.addAttribute("parentId", parentId);
		model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.http.path"));
		model.addAttribute("bookReviewCount",bookReviewCount);
		if(StringUtils.isNotEmpty(orderby)){
			model.addAttribute("orderby", orderby);
		}
		
		return "epub/list";
	}
	
	@RequestMapping(value="/delete")
	public String del(@RequestParam final Long id){
		try {
			this.mediaService.deleteById(id);
			LogUtil.info(LOGGER,"管理员:{0}在删除图书【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					id);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在删除图书【{1}】时出错", e,UsercmsUtils.getCurrentUser().getLoginName(),
					id);
		}
		return "redirect:list.go";
	}
	
	
	@RequestMapping(value="/toEdit")
	public String toEdit(@RequestParam final Long id,final Model model){
		Media media = this.mediaService.get(id);
		model.addAttribute("media", media);
		return "epub/modify";
	}
	
	@RequestMapping("main")
	public String main(final Model model){
		return "epub/media";
	}
	
	@RequestMapping("catetorySign")
	public String catetorySign(final Model model,Media media){
		media = this.mediaService.get(media.getMediaId());
		model.addAttribute("media", media);
		StringBuffer cateIds = new StringBuffer();
		StringBuffer cateNames = new StringBuffer();
		List<Catetory> list = catetoryService.getCatetoryByMediaId(media.getMediaId());
		for(Catetory cate : list){
			cateIds.append(cate.getId()).append(",");
			cateNames.append(cate.getName()).append(",");
		}
		if(list.size() > 0){
			cateIds.deleteCharAt(cateIds.length() - 1);
			cateNames.deleteCharAt(cateNames.length() - 1);
		}
		model.addAttribute("cateIds", cateIds);
		model.addAttribute("cateNames", cateNames);//分类名：cateNames
		model.addAttribute("signIds", media.getSignIds());
		model.addAttribute("signNames", media.getSignNames());
		return "epub/catetorysign";
	}
	
	
	@RequestMapping(value="/update")
	public String update(Media media,@RequestParam(value="cover",required=false) MultipartFile file
			,final Model model) throws FileNotFoundException, IOException{
		try {
			if(file!=null && file.getSize() > 0){
				Media m = mediaService.get(media.getMediaId());
				String path = "";
				if(m.getCoverPic() == null || "".equals(m.getCoverPic())){
					String uid = m.getUid();
					path = File.separator+uid.substring(uid.length()-4,uid.length()-2)+File.separator+uid.substring(uid.length()-2)+File.separator+uid+File.separator+"cover.jpg";
				}else{
					path = m.getCoverPic();
				}
				File f = new File(ConfigPropertieUtils.getString("media.resource.root.path")+File.separator+path);
				f.mkdirs();
				if(!f.exists()){
					f.createNewFile();
				}
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
				ScaleCoverImg sci = new ScaleCoverImg(f, media.getMediaId());
	    		UploadPicToCDN.upload(media.getMediaId(), sci.scaleCoverPic());
			}
			media.setLastModifyDate(Calendar.getInstance().getTime());
			media.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
			mediaService.update(media);
			cacheApi.setMediaCache(media);
			LogUtil.info(LOGGER,"管理员:{0}在修改图书【{1}】基本信息", UsercmsUtils.getCurrentUser().getLoginName(),
					media.getMediaId());
			model.addAttribute("msg", "操作成功!");
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在修改图书【{1}】基本信息失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					media.getMediaId());
			model.addAttribute("msg", "操作失败!");
		}
		return "epub/media";
	}
	
	@RequestMapping(value="/toSelectCate")
	public String toSelectCate(final Model model){
		model.addAttribute("cateIds", getRequest().getParameter("cateIds"));
		model.addAttribute("cateNames", getRequest().getParameter("cateNames"));
		return "epub/selCatetory";
	}
	@RequestMapping(value="/toBaseInfo")
	public String toBaseInfo(Media media,final Model model){
		media = mediaService.get(media.getMediaId()); 
		model.addAttribute("media", media);
		model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.http.path"));
		return "epub/baseInfo";
	}
	
	@RequestMapping(value="/getCover")
	public String getCover(Media media){
		media = mediaService.get(media.getMediaId());
		return "redirect:"+ConfigPropertieUtils.getString("media.resource.http.path")+media.getCoverPic();
	}
	
	@RequestMapping(value="/saveCateSign")
	public String saveCateSign(Media media){
		String cateIds = getRequest().getParameter("cateIds");
		try {
			mediaService.saveCateSign(media, cateIds);
			LogUtil.info(LOGGER,"管理员:{0}在修改图书【{1}】类别和标签信息", UsercmsUtils.getCurrentUser().getLoginName(),
					media.getMediaId());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在修改图书【{1}】类别{2}和标签{3}信息",e, UsercmsUtils.getCurrentUser().getLoginName(),
					media.getMediaId(),cateIds,media.getSignIds());
		}
		return "redirect:list.go";
	}
	@RequestMapping(value="/toSelLabel")
	public String toSelLabel(final Model model){
		model.addAttribute("id", getRequest().getParameter("id"));
		model.addAttribute("name", getRequest().getParameter("name"));
		return "epub/selLabel";
	}
	
	@RequestMapping(value="/toSetIsFull")
	public String toSetIsFull(@RequestParam(value="mediaId") Long mediaId,final Model model){
		Media media = mediaService.get(mediaId);
		model.addAttribute("media", media);
		String price = service.getValue(MEDIA_FULL_DEFAULT_PRICE_KEY);
		model.addAttribute("price", price);
		return "epub/setIsFull";
	}
	@RequestMapping(value="/setIsFull")
	public String setIsFull(Media media){
		
		return "redirect:main.go";
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
	
	@RequestMapping(value="/toChapterVolume")
	public String toChapterVolume(Query query,final Model model,Chapter chap ) throws IOException{
		
		Long mediaId=chap.getMediaId();//id:1900100991
		Media media = mediaService.get(mediaId);
		if(media!=null){
			model.addAttribute("media", media);
		}
		
		PageFinder<Chapter> pageFinderChap = chapterService.findPageFinderObjs(chap,query);
		Set<Long> digestIds = new HashSet<Long>();
		if(pageFinderChap.getData().size()>0){
			List<Chapter> lists = pageFinderChap.getData();
			List<Long> chapterIds = new ArrayList<Long>();
			for(Chapter chapter: lists){
				Long chapterId=(long) chapter.getId();
				chapterIds.add(chapterId);
			}
			List<Digest> digestList = digestService.queryDigestsByChapterIds(chapterIds);
			if(digestList!= null && digestList.size()>0){
				for(Digest digest : digestList) {
					digestIds.add(digest.getMediaChapterId());
				}
			}
			
			model.addAttribute("maxIndex", lists.get(lists.size()-1).getIndex());
		}
		model.addAttribute("digestIds" , new ArrayList<Long>(digestIds));
		if(pageFinderChap.getData().size()==0){
			model.addAttribute("noChapter", "没有章节信息");
		}
		model.addAttribute("pageFinder", pageFinderChap);
		model.addAttribute("mediaId", mediaId);
		return "epub/volumeChapter";
	}
	
	@RequestMapping(value="/findChapterInfo")
	public String findChapterInfo(Query query,Media media ,final Model model,Chapter chap){
		Long mediaId=media.getMediaId();
		media = mediaService.get(media.getMediaId());
		if(media!=null){
			model.addAttribute("media", media);
		}
		PageFinder<Chapter> pageFinderChap = chapterService.findPageFinderObjs(chap,query);
		if(pageFinderChap.getData().size()>0){
			List<Chapter> lists = pageFinderChap.getData();
			model.addAttribute("maxIndex", lists.get(lists.size()-1).getIndex());
		}
		if(pageFinderChap.getData().size()==0){
			model.addAttribute("noChapter", "没有章节信息");
		}
		model.addAttribute("pageFinder", pageFinderChap);
		return "epub/volumeChapter";
	}
	
	@RequestMapping(value="/getSaleByMediaId")
	@ResponseBody
	public void getSaleByMediaId(Media media) throws IOException{
		StringBuffer sb = new StringBuffer("");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mediaId", media.getMediaId());
		map.put("shelfStatus", 1);
		List<MediaSale> list = mediaSaleService.getSale(map);
		for(MediaSale sale : list){
			sb.append("【");
			sb.append("名称:"+sale.getName());
			sb.append(",类型:");
			if(sale.getType() == 0){
				sb.append(MediaSaleEnum.AUTO.getName());
			}else{
				sb.append(MediaSaleEnum.PACKAGE.getName());
			}
			sb.append("】,");
		}
		if(list.size() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		//getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("application/text;charset=UTF-8");
		getResponse().getWriter().write(sb.toString());
	}
	
	@RequestMapping(value="/showFile")
	public String showFile(@RequestParam Long id,final Model model) throws IOException{
		Chapter chapter = chapterService.get(id);
		File f = new File(ConfigPropertieUtils.getString(FILE_PATH)+chapter.getFilePath());
		if(f.exists()){
			chapter.setContent(FileUtils.readFileToString(f));
		}else{
			chapter.setContent("章节内容不存在！");
		}
		model.addAttribute("chapter", chapter);
		return "epub/showFile";
	}
	
	@RequestMapping(value="/queryEpubProductIdList")
	@ResponseBody
	public String queryEpubProductIdList(Query query,final Model model) throws IOException{
		AjaxResult result = new AjaxResult();
		try{
			PageFinder<Long> pageFinder = mediaService.queryEpubProductList(query);
			List<Long> list = pageFinder.getData();
			if(list != null && list.size()>0){
				String liststr = pageFinder.getData().toString();
				liststr = liststr.substring(1,liststr.length()-1);
				result.setResult(liststr);
			}else{
				result.setErrorMessage("未查询到数据");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage("格式错误");
		}
		
		return JSON.toJSONString(result);
	}
	@RequestMapping(value="/updateMediaHeat")
	@ResponseBody
	public String updateMediaHeat(@RequestParam String updatestr,final Model model){
		AjaxResult result = new AjaxResult();
		if(updatestr != null){
			List<Media> medialist = new ArrayList<Media>();
			String[] array = updatestr.split(",");
			boolean success = true;
			for(int i=0;i<array.length;i++){
				String[] temp = array[i].split(":");
				if(temp.length != 4){
					result.setErrorMessage("格式错误");
					success = false;
					break;
				} 
				try{
					Long productId = Long.valueOf(temp[0]);
					Long weekheat = Long.valueOf(temp[1]);
					Long monthheat = Long.valueOf(temp[2]);
					Long heat = Long.valueOf(temp[3]);
					Media media = new Media();
					media.setMediaId(productId);
					media.setWeekHeat(weekheat);
					media.setMonthHeat(monthheat);
					media.setHeat(heat);
					medialist.add(media);
				} catch (NumberFormatException e){
					result.setErrorCode("格式错误");
					success = false;
					break;
				}
			}
			if (success && medialist.size()>0) {
				for (Media media: medialist) {
					mediaService.update(media);
				}
				result.setResult("success");
			}
		}else{
			result.setErrorMessage("格式错误");
		}
		return JSON.toJSONString(result);
	}
	//选入功能，添加文章页面
		@RequestMapping(value="/toAddOrEdit")
		public String toAddOrEdit(Digest dis,final Model model,Chapter chap,Media media) throws IOException{
			String title = chap.getTitle();
	        String name = media.getAuthorPenname();
			Long m=media.getMediaId();
			dis.setTitle(title);
			dis.setAuthor(name);
			Chapter chapter = chapterService.get(chap.getId());
			File f = new File(ConfigPropertieUtils.getString(FILE_PATH)+chapter.getFilePath());
			if(f.exists()){
				dis.setContent(FileUtils.readFileToString(f));
			}else{
				dis.setContent("章节内容不存在！");
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
			Map<String,Object> signIdNameMap =getSignByDigestId();
			model.addAttribute("signIdNameMap", signIdNameMap);
			model.addAttribute("dis", dis);
			model.addAttribute("chapter", chapter);//chapter.getDesc();
			return  "epub/info";
		}
		
		public Map<String, Object> getSignByDigestId() {
			//获取所有标签，放到map中，提供给前端页面
			List<Map<String, Object>> list=digestService.getSignByDigestId();
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
		
	@RequestMapping(value = "/save")
	public String saveOrUpdate(Digest dis, Chapter chap, final Model model,
			HttpServletRequest request) throws IOException {
		dis.setId(null);
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
		if (StringUtils.isNotBlank(showStartDateStr)) {
			dis.setShowStartDate(DateUtil.parseStringToDate(showStartDateStr));
		}
		if (dis.getMediaId() != null) {
			Media media = mediaService.get(dis.getMediaId());
			dis.setMediaName(media.getTitle());

			if (dis.getAuthor() == null) {
				dis.setAuthor(media.getAuthorPenname());
			}
			// 分类
			StringBuffer cateIds = new StringBuffer();
			StringBuffer cateNames = new StringBuffer();
			List<Catetory> list = catetoryService.getCatetoryByMediaId(dis
					.getMediaId());
			for (Catetory cate : list) {
				cateIds.append(cate.getId()).append(",");
				cateNames.append(cate.getName()).append(",");
			}
			if (list.size() > 0) {
				cateIds.deleteCharAt(cateIds.length() - 1);
				cateNames.deleteCharAt(cateNames.length() - 1);
			}
			dis.setFirstCatetoryId(Integer.valueOf(cateIds.toString()));
			dis.setFirstCatetoryName(cateNames.toString());
		}
		dis.setMediaChapterId(chap.getId());
		// 文字模板，纯文字
		if (dis.getCardType().intValue() == 0) {
			dis.setPic1Path(null);
		}
		if(dis.getCardTitle() !=null){
			dis.setTitle(dis.getCardTitle());
		}
		dis.setCreateDate(Calendar.getInstance().getTime());
		digestService.save(dis);
		Long digestId = dis.getId();
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
				ba.setAuthorId(auId);
				bookAuthorService.save(ba);
			}
		}
//		List<BookAuthor> oldbookAuthor = bookAuthorService.findListByParams("digestId",digestId);
//		Map<Long,BookAuthor> oldMap = new HashMap<Long, BookAuthor>();
//		for(BookAuthor ba : oldbookAuthor){
//			oldMap.put(ba.getAuthorId(), ba);
//		}
//		List<Long> newList = new ArrayList<Long>();
//		for(Long auId:authorIds){
//			if ( oldMap.containsKey(auId)){
//				oldMap.remove(auId);
//			}else{
//				newList.add(auId);
//			}
//		}
//		if(!oldMap.isEmpty()){
//			for(BookAuthor ba : oldMap.values()){
//				bookAuthorService.deleteById(ba.getId());
//				ba.setId(null);
//				bookAuthorService.save(ba);
//			}
//		}
//		if(!newList.isEmpty()){
//			for(Long auId : newList){
//				BookAuthor ba = new BookAuthor();
//				ba.setDigestId(digestId);
//				ba.setAuthorId(auId);
//				bookAuthorService.save(ba);
//			}
//		}
		
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
		

		return "redirect:toChapterVolume.go?mediaId="+dis.getMediaId();
	}
	
	public static void main(String[] args) {
		String uid = "1416627725547961";
		System.out.println(uid.substring(uid.length()-4,uid.length()-2));
	}
}
