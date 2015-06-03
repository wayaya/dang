package com.dangdang.digital.listenbook.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.dangdang.digital.listenbook.handler.PicUpCdnRejectedExecutionHandler;

public class PicUpCdnThreadPoolExecutorService {

	private int  corePoolSize;
    private int  maxPoolSize; 
    private long keepAliveTime;
    private int  queueCapacity;
    PicUpCdnRejectedExecutionHandler picUpCdnRejectedExecutionHandler;
    private static ThreadPoolExecutor executor = null;
 
    public PicUpCdnThreadPoolExecutorService(int corePoolSize, int maxPoolSize,
			long keepAliveTime, int queueCapacity,
			PicUpCdnRejectedExecutionHandler picUpCdnRejectedExecutionHandler) {
		super();
		this.corePoolSize = corePoolSize;
		this.maxPoolSize = maxPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.queueCapacity = queueCapacity;
		this.picUpCdnRejectedExecutionHandler = picUpCdnRejectedExecutionHandler;
		
		executor = new ThreadPoolExecutor(this.corePoolSize,
				this.maxPoolSize, this.keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue(this.queueCapacity),
				this.picUpCdnRejectedExecutionHandler);
	}

	public ThreadPoolExecutor getPicUpCdnThreadPoolExecutor() {
		return executor;
    }

}
