package com.dangdang.digital.api.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IDigestApi;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.service.IAuthorService;
import com.dangdang.digital.service.IBookAuthorService;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.DateUtil;

@Component("digestApi")
public class DigestApiImpl implements IDigestApi {
	@Resource
	protected IAuthorService authorService;
	@Resource
	private IDigestService digestService;
	@Resource
	private IBookAuthorService bookAuthorService;
	@Resource
	private ICatetoryService catetoryService;
	@Resource
	private IMediaService mediaService;

	public void saveDigest(Digest dis) throws Exception {
		// 作者
		List<Long> authorIds = new ArrayList<Long>();
		String authors = dis.getAuthor();
		String[] names = null;
		StringBuilder containers = new StringBuilder();
		names = authors.split(",|，|;");
		for (int i = 0; i < names.length && i < 3; i++) {
			String name = names[i].trim();
			List<Author> authorlist = authorService.findListByParams("name",
					name, "cpType", "dianzishu");
			if (authorlist == null || authorlist.size() == 0) {
				Author author = new Author();
				author.setCpType("dianzishu");
				author.setPseudonym(name);
				author.setName(name);
				authorService.save(author);
				authorIds.add(author.getAuthorId());
				if (containers.length() == 0) {
					containers.append(author.getAuthorId() + ":"
							+ author.getName());
				} else {
					containers.append(";" + author.getAuthorId() + ":"
							+ author.getName());
				}
			} else {
				Author author = authorlist.get(0);
				authorIds.add(author.getAuthorId());
				if (containers.length() == 0) {
					containers.append(author.getAuthorId() + ":"
							+ author.getName());
				} else {
					containers.append(";" + author.getAuthorId() + ":"
							+ author.getName());
				}
			}
		}
		dis.setAuthor(containers.toString());// 作者的信息：1:韩寒;2:周立波

		if (dis.getMediaId() != null) {
			Media media = mediaService.get(dis.getMediaId());
			dis.setMediaName(media.getTitle());
			// 分类
			StringBuffer cateNames = new StringBuffer();
			List<Catetory> list = catetoryService.getCatetoryByMediaId(dis
					.getMediaId());
			for (Catetory cate : list) {
				cateNames.append(cate.getName()).append(",");
			}
			if (list.size() > 0) {
				cateNames.deleteCharAt(cateNames.length() - 1);
			}
			dis.setFirstCatetoryName(cateNames.toString());
		}
		// 文字模板，纯文字

		if (dis.getCardTitle() != null) {
			dis.setTitle(dis.getCardTitle());
		}
		Long digestId = null;
		if (dis.getId() != null) {
			dis.setLastUpdateDate(Calendar.getInstance().getTime());
			digestService.update(dis);
			digestId = dis.getId();
		} else {
			dis.setCreateDate(Calendar.getInstance().getTime());
			digestService.save(dis);
			digestId = dis.getId();
		}

		// 存入作者文章关联表
		List<BookAuthor> oldbookAuthor = bookAuthorService.findListByParams(
				"digestId", digestId);// 库(只能有一条信息)
		if (oldbookAuthor.size() == 0) {
			BookAuthor ba = new BookAuthor();
			ba.setDigestId(digestId);
			for (Long auId : authorIds) {
				ba.setAuthorId(auId);
				bookAuthorService.save(ba);
			}
		} else {
			for (BookAuthor bookauthor : oldbookAuthor) {
				bookAuthorService.deleteById(bookauthor.getId());
			}
			BookAuthor ba = new BookAuthor();
			ba.setDigestId(digestId);
			for (Long auId : authorIds) {
				ba.setAuthorId(auId);// set
				bookAuthorService.save(ba);
			}
		}
	}

	@Override
	public List<Digest> queryDigestsHomePage(Long sortPage, Integer dayNight, Integer pageSize, String types, String version) {
		List<Digest> digestList = digestService.queryDigestsHomePage(
				sortPage, dayNight, pageSize, types, version);
		return digestList;
	}

	@Override
	public Digest findDigestById(Long id) {
		return digestService.findDigestById(id);
	}

	@Override
	public Digest findDigestContentById(Long id) {
		return digestService.findDigestContentById(id);
	}
	
	@Override
	public List<Digest> queryChannelDigests(Map<String, Object> param) {
		List<Digest> digestList = digestService.queryChannelDigests(param);
		return digestList;
	}
	
	@Override
	public List<Digest> queryBarDigests(Map<String, Object> param) {
		List<Digest> digestList = digestService.queryBarDigests(param);
		return digestList;
	}
}
