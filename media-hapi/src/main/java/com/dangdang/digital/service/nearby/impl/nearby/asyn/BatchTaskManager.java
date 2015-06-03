package com.dangdang.digital.service.nearby.impl.nearby.asyn;


import com.dangdang.digital.service.nearby.api.nearby.bean.ISolrDoc;
import com.dangdang.digital.service.nearby.impl.nearby.solr.INearbySolrServer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description: 批量任务管理
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:15:44  by 林永奇（linyongqi@dangdang.com）创建
 */
public class BatchTaskManager {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(BatchTaskManager.class);

    private TPExecutorManager tpmanager;

    private volatile boolean isRunning = true;

    //solr索引服务器
    private INearbySolrServer solrServer;


    /**
     * 批量的任务
     */
    private Map<Class<?>, IBatchTask<ISolrDoc>> batchTask = Collections.emptyMap();


    public void setSolrServer(INearbySolrServer solrServer) {
        this.solrServer = solrServer;
    }


    /**
     * 设置thread pool manager
     *
     * @param tpmanager
     */
    public void setTpmanager(TPExecutorManager tpmanager) {
        this.tpmanager = tpmanager;
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
    public void init() throws Exception {

        //开启批量更新solr索引的操作
        for (final Map.Entry<Class<?>, IBatchTask<ISolrDoc>> entry : this.batchTask.entrySet()) {
            //初始化每一个批量任务
            entry.getValue().init();
            this.tpmanager.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    while (isRunning) {
                        //开始poll数据批量
                        final IBatchTask<ISolrDoc> task = entry.getValue();
                        final List<ISolrDoc> docs = task.batchPollDocs();
                        //如果获得的数据不为空，则写入solr
                        if (CollectionUtils.isNotEmpty(docs)) {
                            final boolean succ = task.acquire();
                            //提交给指定的线程
                            BatchTaskManager.this.tpmanager.getThreadPool().execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        BatchTaskManager.this.solrServer.batchUpdateDocs(docs);
                                        if (log.isDebugEnabled()) {
                                            log.debug("batchUpdateDocs|{0}|SUCC|{1}", entry.getKey(), docs);
                                        }
                                       task.getCounter().inc(docs.size());
                                    } finally {
                                        if (succ) {
                                            task.release();
                                        }
                                    }
                                }
                            });


                        }
                    }
                    log.info("batchUpdateDocs|{0}|STOPPED", entry.getKey());
                }
            });
        }

        log.info("batch task manager init succ ! ...");

    }

    /**
     * 向队列里offerdoc
     *
     * @param doc
     */
    public boolean offer(ISolrDoc doc) {
        IBatchTask<ISolrDoc> task = this.batchTask.get(doc.getClass());
        return task.offerDoc(doc);
    }


    /**
     * 设置批量任务
     *
     * @param batchTask
     */
    public void setBatchTask(Map<Class<?>, IBatchTask<ISolrDoc>> batchTask) {
        this.batchTask = batchTask;
    }


    public void destory() {
        this.isRunning = false;
    }

}
