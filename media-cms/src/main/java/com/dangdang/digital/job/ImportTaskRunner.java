package com.dangdang.digital.job;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.digital.model.ImportTask;
import com.dangdang.digital.service.IImportTaskService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


public class ImportTaskRunner {

	private static Logger log = LoggerFactory.getLogger(ImportTaskRunner.class);
	
	private static final int THREAD_POOL_KEEP_ALIVE_TIME = 300;

	private static final int THREAD_POOL_QUEUE_SIZE = 100;

	private static final int THREAD_POOL_SIZE = 5;
	
	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,
			THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE),
			new CallerRunsPolicy());
	
	
	@Resource(name="importTaskService")
	private IImportTaskService importTaskService;
	
	
	public void run() {
		Query query = new Query();
		query.setPage(1);
		query.setPageSize(THREAD_POOL_QUEUE_SIZE);
		ImportTask it = new ImportTask();
		it.setStatus(ImportTask.STATUS_WAITING.toString());
		
		PageFinder<ImportTask> pageFinder = importTaskService.findPageFinderObjs(it, query);
		
		List<ImportTask> tasks = pageFinder.getData();
		for (ImportTask task : tasks) {
			task.setStatus(ImportTask.STATUS_RUNNING.toString());
			importTaskService.update(task);
			
			//new TaskExecutor(task).run();
		}
	}
	
}
