package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.service.IAuthorService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping("author")
public class AuthorController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

	private final String AUTHOR_PATH = "media.resource.author.path";

	@Resource
	private IAuthorService authorService;
	@Resource
	private ICacheApi cacheApi;

	private SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/list")
	public String list(Query query, final Model model, final Author author) {

		PageFinder<Author> pageFinder = authorService.findPageFinderObjs(author, query);
		if (pageFinder != null) {
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("author", author);
		return "author/list";
	}

	@RequestMapping(value = "/toEdit")
	public String toEdit(@RequestParam final Long authorId, final Model model) {
		Author author = this.authorService.get(authorId);
		model.addAttribute("author", author);
		model.addAttribute("picPath", ConfigPropertieUtils.getString("media.resource.author.http.path"));
		return "author/modify";
	}

	@RequestMapping(value = "/update")
	public String update(Author author, @RequestParam(value = "cover", required = false) MultipartFile file)
			throws FileNotFoundException, IOException {
		try {
			if (file != null && file.getSize() > 0) {
				String uuid = UUID.randomUUID().toString().replace("-", "");

				File f = new File(ConfigPropertieUtils.getString(AUTHOR_PATH) + File.separator + author.getAuthorId());
				f.mkdirs();
				File headPic = new File(f, uuid + ".jpg");
				if (!headPic.exists()) {
					headPic.createNewFile();
				}
				author.setHeadPic(author.getAuthorId() + File.separator + uuid + ".jpg");
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(headPic));
			}
			String birth = getRequest().getParameter("births");
			if (StringUtils.isNotBlank(birth)) {
				author.setBirth(sdf10.parse(birth));
			}
			author.setLastModifiedDate(Calendar.getInstance().getTime());
			author.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
			authorService.update(author);
			cacheApi.setAuthorCache(author);
			LogUtil.info(LOGGER, "管理员:{0}在修改作者【{1}】基本信息", UsercmsUtils.getCurrentUser().getLoginName(),
					author.getAuthorId());
		} catch (Exception e) {
			LOGGER.error(
					"管理员:{0}在修改作者【{1}】基本信息失败" + UsercmsUtils.getCurrentUser().getLoginName() + author.getAuthorId(), e);
		}
		return "redirect:list.go";
	}
}
