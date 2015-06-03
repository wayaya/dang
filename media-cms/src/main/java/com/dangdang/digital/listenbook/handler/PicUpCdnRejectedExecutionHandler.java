package com.dangdang.digital.listenbook.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PicUpCdnRejectedExecutionHandler implements
		RejectedExecutionHandler {
	private static Logger logger = LoggerFactory.getLogger(PicUpCdnRejectedExecutionHandler.class);

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		// TODO Auto-generated method stub
		logger.debug(r.getClass().getName()+ " : has been rejected , maybe the ThreadPoolExecutor is full !");
		logger.error(r.getClass().getName()+ " : has been rejected , maybe the ThreadPoolExecutor is full !");
		if (!executor.isShutdown()) {
            try {
            	executor.getQueue().put(r);
            } catch (InterruptedException e) {
            	logger.error(r.getClass().getName()+ " : rejectedExecution put error : ",e);
            }
		}
	}

}
