package com.dangdang.digital.service.nearby.impl.nearby.asyn;


import com.dangdang.digital.service.nearby.impl.nearby.util.Counter;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/**
 * 
 * Description: 批量人无实现类
 * All Rights Reserved.
 * @param <T>
 * @version 1.0  2014年7月21日 下午6:16:02  by 林永奇（linyongqi@dangdang.com）创建
 */
public class DefaultBatchTask<T> implements IBatchTask<T> {


    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultBatchTask.class);

    //批量提交的大小
    private int batchSize = 100;

    private int queueLength = 2000;

    //同步到solr中的时间
    private int syncSecTimeout = 1;

    private int workThreadSize = 2;

    private Semaphore semaphore;

    private LinkedBlockingQueue<T> docQueue;

    private final Counter counter = new Counter();

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setSyncSecTimeout(int syncSecTimeout) {
        this.syncSecTimeout = syncSecTimeout;
    }

    private Class<?> clazz;

    /**
     * 初始话
     *
     * @throws Exception
     */
    public void init() throws Exception {

        //信号量
        this.semaphore = new Semaphore(this.workThreadSize);
        docQueue = new LinkedBlockingQueue<T>(this.queueLength);
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }


    /**
     * 向队列里增加这个
     *
     * @param doc
     */
    public boolean offerDoc(T doc) {

        try {
            return this.docQueue.offer(doc, 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOG.error("{0}|offerDoc|{1}", clazz, doc);
        }
        return false;
    }


    @Override
    public List<T>  batchPollDocs() {

        List<T> docs = new ArrayList<T>(this.batchSize);
        /**
         * 循环遍历从队列中poll 数据
         * 如果poll时超过了指定的时间
         * 则认为超时，强制将数据刷入到solr中
         */
        while (docs.size() < this.batchSize) {
            T doc = null;
            try {
                //${syncSecTimeout}没有poll出数据就直接，返回数据已经获得的数据
                doc = this.docQueue.poll(this.syncSecTimeout * 1000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                LOG.error("{0}|docQueue poll|{1}", clazz.getSimpleName(), docs.size());
            } finally {
                if (null == doc) {
                    break;
                } else {
                    docs.add(doc);
                }
            }

        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("{0}|docQueue poll|{1}", clazz, docs.size());
        }

        return docs;
    }

    @Override
    public int getQueueLength() {
        return this.docQueue.size();
    }

    @Override
    public int getMaxQueueLength() {
        return this.queueLength;
    }

    @Override
    public void setMaxQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    @Override
    public void setWorkThreadSize(int size) {
        this.workThreadSize = size;
    }

    @Override
    public boolean acquire() {
        try {
            return this.semaphore.tryAcquire(1,10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void release() {
        this.semaphore.release();
    }

    @Override
    public Counter getCounter() {
        return this.counter;
    }
}
