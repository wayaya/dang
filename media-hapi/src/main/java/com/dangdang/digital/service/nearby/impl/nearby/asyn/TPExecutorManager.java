package com.dangdang.digital.service.nearby.impl.nearby.asyn;


import com.dangdang.digital.service.nearby.api.nearby.bean.ISolrDoc;
import com.dangdang.digital.service.nearby.impl.monitor.PoolConfig;
import com.dangdang.digital.service.nearby.impl.nearby.util.Counter;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * Description: 管理器
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:16:44  by 林永奇（linyongqi@dangdang.com）创建
 */
public class TPExecutorManager {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultBatchTask.class);


    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPool;


    /**
     * 监控管理
     */
    //private MonitorManager monitorManager;


    /**
     * 批量的任务
     */
    private Map<Class<?>, IBatchTask<ISolrDoc>> batchTask = Collections.emptyMap();


    /**
     * 线程池配置
     */
    private PoolConfig poolConfig = new PoolConfig();

    /**
     * solr server的提交
     */
    private final Counter solrCounter = new Counter();


    /**
     * 设置线程池大小
     *
     * @param poolConfig
     */
    public void setPoolConfig(PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }


    /**
     * 设置批量任务
     *
     * @param batchTask
     */
    public void setBatchTask(Map<Class<?>, IBatchTask<ISolrDoc>> batchTask) {
        this.batchTask = batchTask;
    }


    /**
     * profile命中率统计
     * *
     */
    private AtomicLong expiredProfile;

    private AtomicLong locationTotal;

    public void setExpiredProfile(AtomicLong expiredProfile) {
        this.expiredProfile = expiredProfile;
    }

    public void setLocationTotal(AtomicLong locationTotal) {
        this.locationTotal = locationTotal;
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
    public void init() throws Exception {

        //线程池
        this.threadPool
                = new ThreadPoolExecutor(this.poolConfig.getCorePoolSize(),
                this.poolConfig.getMaxPoolSize(), 10 * 1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(this.poolConfig.getQueueSize()),
                new NamedThreadFactory(this.poolConfig.getPoolName()),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        LOG.info("thread pool :{0}|config{1}", poolConfig.getPoolName(), poolConfig);


        //开启监控线程池
        /** 监控先不实现吧
        String logPath = System.getProperty("moaLogPath", "./logs");
        this.monitorManager = new MonitorManager(poolConfig.getPoolName(), logPath, true);
        ServiceAlarm alarm = new ServiceAlarm();
        alarm.init();
        PoolMonitor monitor = new PoolMonitor(
                this.threadPool, this.batchTask, "solr-nearby", alarm,this.expiredProfile,this.locationTotal);
        monitor.setQlthreshold(poolConfig.getQlthreshold());;
        this.monitorManager.registerMonitor(monitor);
        this.monitorManager.start();
         **/
        LOG.info("thread pool monitor init succ !");

    }


    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    /**
     * 放弃的更新
     * @return
     */
    public Counter getSolrCounter() {
        return solrCounter;
    }

    public void destory() {
        do {
            this.threadPool.shutdown();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOG.error("waiting task finish fail!");
            }
        } while (!this.threadPool.isTerminated());


        LOG.info("thread pool manager stopped ! ....");
    }
}
