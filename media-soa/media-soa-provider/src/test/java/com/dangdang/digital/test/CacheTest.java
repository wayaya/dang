package com.dangdang.digital.test;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.dangdang.common.api.ICommonApi;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IConsumeApi;
import com.dangdang.digital.api.IOrderApi;
import com.dangdang.digital.dao.IActivitySaleDao;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;

public class CacheTest {

	private static IOrderApi orderApi;
	private static ICacheApi cacheApi;
	private static ICacheService cacheService;
	private static IConsumeApi consumeApi;
	private static ICommonApi commonApi;

	private static RabbitTemplate rabbitTemplate;

	private static ThreadPoolTaskExecutor taskExecutor;

	private static RedisTemplate<String, String> slaveRedisTemplateForWholeVo;

	private static IActivitySaleDao activitySaleDao;

	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[] { "config/spring_common.xml" });
		orderApi = (IOrderApi) cxt.getBean("orderApi");
		cacheService = (ICacheService) cxt.getBean("cacheServiceImpl");
		cacheApi = (ICacheApi) cxt.getBean("cacheApi");
		consumeApi = (IConsumeApi) cxt.getBean("consumeApi");
		commonApi = (ICommonApi) cxt.getBean("commonApi");

		rabbitTemplate = (RabbitTemplate) cxt.getBean("rabbitTemplate");
		taskExecutor = (ThreadPoolTaskExecutor) cxt.getBean("taskExecutor");
		slaveRedisTemplateForWholeVo = (RedisTemplate<String, String>) cxt.getBean("slaveRedisTemplate");
		activitySaleDao = (IActivitySaleDao) cxt.getBean("activitySaleDaoImpl");
		
	}

	@Test
	public void test() throws Exception {
//		cacheApi.getContentsFromCache(1900627906l, null, null);
//		MediaCacheBasicVo vo = cacheApi.getMediaBasicFromCache(1900627906l);
//		System.out.println(12312);
		
		ChapterCacheBasicVo chapter = cacheApi.getChapterBasicFromCache(4269943L);
		System.out.println(chapter);
	}

	// @Test
	// public void test() throws Exception {
	// for (int j = 0; j < 500; j++) {
	// System.out.println("线程" + j + "执行");
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// for (int i = 0; i < 100; i++) {
	// System.out.println("调用" + i + "次");
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpGet httpgets = new HttpGet(
	// "http://10.255.223.149/media/api.go?action=mediaCategoryLeaf&category=YXJJ&dimension=update&start=0&end=9&returnType=json&deviceType=YC_Android&channelId=40000&clientVersionNo=1.0&serverVersionNo=1.2.1&permanentId=20150122110919267530728577270387609&deviceSerialNo=869630011091729&macAddr=c4%3A6a%3Ab7%3Aa3%3A58%3A07&resolution=720*1280&clientOs=4.1.1&channelType=NP&token=");
	// HttpResponse response;
	// try {
	// response = httpclient.execute(httpgets);
	// if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
	// System.out.println("请求异常:" + response.getStatusLine());
	// }
	// } catch (ClientProtocolException e) {
	// System.out.println("链接异常" + e);
	// } catch (IOException e) {
	// System.out.println("链接异常" + e);
	// }
	//
	// }
	// }
	// }).start();
	// }
	// Thread.sleep(600000l);
	// }
}
