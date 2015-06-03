package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.vo.SearchReturnVo;

public class SearchUtils {

	private static final Logger logger = LoggerFactory.getLogger(SearchUtils.class);
	final static String zkHost = ConfigPropertieUtils.getString("solr.zookeeper.address");
	final static String defaultCollection = ConfigPropertieUtils.getString("search.core");
	final static String listenCollection = ConfigPropertieUtils.getString("search.listen.core");
	final static int zkClientTimeout = 10000;
	final static int zkConnectTimeout = 10000;

	private static CloudSolrServer cloudSolrServer;

	static {
		try {
			cloudSolrServer = new CloudSolrServer(zkHost);
			cloudSolrServer.setDefaultCollection(defaultCollection);
			cloudSolrServer.setZkClientTimeout(zkClientTimeout);
			cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);
			cloudSolrServer.connect();
		} catch (Exception e) {
			LogUtil.error(logger, "The URL of zkHost is not correct!! Its form must as below:\n zkHost:port");
		}
	}

	public static Map<String, Object> search(String searchContent, String start, String end, String platformSource) {
		Map<String, Object> recordMap = new HashMap<String, Object>();
		SolrQuery query = new SolrQuery();

		if (PlatformSourceEnum.DDXS_P.getSource().equals(platformSource)) {
			// 当读小说
			cloudSolrServer.setDefaultCollection(defaultCollection);
			if (StringUtils.isNotBlank(searchContent)) {
				query.set("q", "title:\"" + searchContent + "\" OR author:\"" + searchContent + "\" OR category:\""
						+ searchContent + "\"");
			}
		} else if (PlatformSourceEnum.TS_P.getSource().equals(platformSource)) {
			// 听书
			cloudSolrServer.setDefaultCollection(listenCollection);
			if (StringUtils.isNotBlank(searchContent)) {
				query.set("q", "title:\"" + searchContent + "\" OR author:\"" + searchContent + "\" OR speaker:\""
						+ searchContent + "\" OR category:\"" + searchContent + "\"");
			}
		}

		if (StringUtils.isBlank(start)) {
			start = "0";
		}
		query.add("start", start);

		String rows = "1000";
		if (StringUtils.isNotBlank(end)) {
			if (Integer.parseInt(end) >= 0 && Integer.parseInt(start) >= 0
					&& (Integer.parseInt(end) - Integer.parseInt(start)) >= 0) {
				rows = String.valueOf(Integer.parseInt(end) - Integer.parseInt(start) + 1);
			}
		}
		query.add("rows", rows);
		List<SearchReturnVo> searchReturnVoList = new ArrayList<SearchReturnVo>();
		long totalCount = 0;
		try {
			QueryResponse response = cloudSolrServer.query(query);
			SolrDocumentList docs = response.getResults();
			totalCount = docs.getNumFound();
			if (docs != null && docs.size() > 0) {
				for (SolrDocument doc : docs) {
					SearchReturnVo searchReturnVo = new SearchReturnVo();
					String mediaSaleId = (String) doc.getFieldValue("mediaSaleId");
					String mediaId = (String) doc.getFieldValue("mediaId");
					String mediaPic = (String) doc.getFieldValue("mediaPic");
					String title = (String) doc.getFieldValue("title");
					String author = (String) doc.getFieldValue("author");
					String description = (String) doc.getFieldValue("description");
					String isFull = (String) doc.getFieldValue("isFull");
					String speaker = (String) doc.getFieldValue("speaker");
					String chapterCnt = (String) doc.getFieldValue("chapterCnt");
					@SuppressWarnings("unchecked")
					List<String> category = (List<String>) doc.get("category");
					try {
						searchReturnVo.setSaleId(Long.parseLong(mediaSaleId));
						searchReturnVo.setMediaId(Long.parseLong(mediaId));
						searchReturnVo.setMediaPic(mediaPic);
						searchReturnVo.setTitle(title);
						searchReturnVo.setAuthor(author);
						searchReturnVo.setSpeaker(speaker);
						if(CollectionUtils.isNotEmpty(category)){
							searchReturnVo.setCategory(category.get(0));
						}
						if (StringUtils.isNotBlank(description)) {
							searchReturnVo.setDescription(description.trim().length() > 60 ? description.substring(0,
									60) : description.trim());
						}
						if (StringUtils.isNotBlank(isFull) && !isFull.equals("null")) {
							searchReturnVo.setIsFull(Integer.valueOf(isFull));
						}
						if (StringUtils.isNotBlank(chapterCnt) && !chapterCnt.equals("null")) {
							searchReturnVo.setChapterCnt(Long.valueOf(chapterCnt));
						}
						searchReturnVoList.add(searchReturnVo);
					} catch (Exception e) {
						logger.error("检索时发生未知异常!", e);
					}
				}
			}
		} catch (SolrServerException e) {
			logger.error("检索时发生未知异常!", e);
		} catch (Exception e) {
			logger.error("检索时发生未知异常!", e);
		}

		recordMap.put("searchReturnVoList", searchReturnVoList);
		recordMap.put("totalCount", totalCount);

		return recordMap;
	}
}
