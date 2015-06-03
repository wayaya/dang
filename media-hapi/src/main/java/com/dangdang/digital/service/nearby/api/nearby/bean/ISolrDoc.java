package com.dangdang.digital.service.nearby.api.nearby.bean;

import org.apache.solr.common.SolrInputDocument;

/**
 * solrDoc接口
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:13:35  by 林永奇（linyongqi@dangdang.com）创建
 */
public interface ISolrDoc {

    /**
     * bean 2  solr的doc
     * @return
     */
    public SolrInputDocument wrap2SolrDoc();

}
