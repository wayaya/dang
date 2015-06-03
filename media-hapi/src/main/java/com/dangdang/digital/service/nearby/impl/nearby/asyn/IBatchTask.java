package com.dangdang.digital.service.nearby.impl.nearby.asyn;


import com.dangdang.digital.service.nearby.impl.nearby.util.Counter;

import java.util.List;
/**
 * 
 * Description: 批量任务接口
 * All Rights Reserved.
 * @param <T>
 * @version 1.0  2014年7月21日 下午6:16:14  by 林永奇（linyongqi@dangdang.com）创建
 */

public interface IBatchTask<T>  {


    /**
     * 初始化
     * @throws Exception
     */
    public void init() throws Exception;



    /**
     * 设置clazz
     * @param clazz
     */
    public void setClazz(Class<?> clazz);


    /**
     *offer进去doc
     * @param doc
     * @return
     */
    public boolean offerDoc(T doc);


    /**
     * 批量poll
     */
    public List<T> batchPollDocs();


    /**
     * 队列长度
     * @return
     */
    public int getQueueLength();


    /**
     * 设置最大队列长度
     * @param queueLength
     */
    public void setMaxQueueLength(int queueLength);

    /**
     * 获得最大的队列长度
     * @return
     */
    public int getMaxQueueLength();


    /**
     * 设置工作线程数
     * @param
     */
    public void setWorkThreadSize(int size);


    /**
     * 申请信号量
     * @return
     */
    public boolean acquire();


    /**
     *归还一个
     */
    public void release();


    /**
     * 获取counter计数
     * @return
     */
    public Counter getCounter();
}
