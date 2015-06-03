package com.dangdang.digital.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.system.SpringContextHolder;
import com.dangdang.digital.vo.MediaSaleCacheVo;

public class SearchUtils {

	private static final Logger logger = LoggerFactory.getLogger(SearchUtils.class);
	final static String zkHost = ConfigPropertieUtils.getString("solr.zookeeper.address");
	final static String defaultCollection = ConfigPropertieUtils.getString("search.core");
	final static int zkClientTimeout = 10000;
	final static int zkConnectTimeout = 10000;

	private static CloudSolrServer cloudSolrServer;
	private static ICacheService cacheService;

	static {
		try {
			cloudSolrServer = new CloudSolrServer(zkHost);
			cloudSolrServer.setDefaultCollection(defaultCollection);
			cloudSolrServer.setZkClientTimeout(zkClientTimeout);
			cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);
			cloudSolrServer.connect();
			cacheService = (ICacheService) SpringContextHolder.getBean("cacheServiceImpl");
		} catch (Exception e) {
			LogUtil.error(logger, "The URL of zkHost is not correct!! Its form must as below:\n zkHost:port");
		}
	}

	/**
	 * 
	 * Description: 根据saleId集合删除索引
	 * 
	 * @Version1.0 2015年1月21日 上午10:41:46 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleIdList
	 */
	public static void deleteDoucument(List<Long> saleIdList) {
		if (CollectionUtils.isEmpty(saleIdList)) {
			return;
		}
		List<String> docIdList = new ArrayList<String>();
		for (int i = 0; i < saleIdList.size(); i++) {
			docIdList.add(String.valueOf(saleIdList.get(i)));
		}
		try {
			cloudSolrServer.deleteById(docIdList);
			cloudSolrServer.commit();
			logger.info("删除索引成功,saleIdList:" + saleIdList);
		} catch (SolrServerException e) {
			logger.error("solr集群链接异常", e);
		} catch (IOException e) {
			logger.error("删除索引异常", e);
		} catch (Exception e) {
			logger.error("删除索引异常", e);
		}

	}

	/**
	 * 
	 * Description: 根据saleId集合添加索引
	 * 
	 * @Version1.0 2015年1月21日 上午10:42:01 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleIdList
	 */
	public static void addDocument(List<Long> saleIdList) {
		try {
			if (CollectionUtils.isEmpty(saleIdList)) {
				return;
			}
			StringBuffer queryString = new StringBuffer("");
			for (int i = 0; i < saleIdList.size(); i++) {
				queryString.append("mediaSaleId:" + saleIdList.get(i));
				if (i < saleIdList.size() - 1) {
					queryString.append(" or ");
				}
			}
			List<MediaSaleCacheVo> mediaSaleList = cacheService.batchGetMediaSaleFromCache(saleIdList);
			if (CollectionUtils.isEmpty(mediaSaleList)) {
				return;
			}
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			for (MediaSaleCacheVo mediaSale : mediaSaleList) {
				SolrInputDocument doc = new SolrInputDocument();
				String sale_id = String.valueOf(mediaSale.getSaleId());
				String media_id = String.valueOf(mediaSale.getMediaList().get(0).getMediaId());
				String author_penname = String.valueOf(mediaSale.getMediaList().get(0).getAuthorPenname())
						.replace("\r\n", "").replace(" ", "");
				String title = String.valueOf(mediaSale.getMediaList().get(0).getTitle()).replace("\r\n", "")
						.replace(" ", "");
				String description = String.valueOf(mediaSale.getMediaList().get(0).getDescs()).replace("\r\n", "")
						.replace(" ", "");
				String cover_pic = MediaCoverPicUrlUtil.getMediaCoverPic(mediaSale.getMediaList().get(0).getMediaId());
				String category = String.valueOf(mediaSale.getMediaList().get(0).getCatalog()).replace("\r\n", "")
						.replace(" ", "");

				doc.addField("id", sale_id);
				doc.addField("mediaSaleId", sale_id);
				doc.addField("mediaId", media_id);
				doc.addField("title", title);
				doc.addField("author", author_penname);
				doc.addField("mediaPic", cover_pic);
				doc.addField("category", category);
				doc.addField("description", description);
				docs.add(doc);
			}
			cloudSolrServer.deleteByQuery(queryString.toString());
			cloudSolrServer.add(docs);
			cloudSolrServer.commit();
			logger.info("新增索引成功,saleIdList:" + saleIdList);
		} catch (SolrServerException e) {
			logger.error("solr集群链接异常", e);
		} catch (Exception e) {
			logger.error("删除索引异常", e);
		}

	}
}
