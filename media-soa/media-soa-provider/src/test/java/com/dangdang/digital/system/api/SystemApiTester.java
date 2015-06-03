//package com.dangdang.digital.system.api;
//
//
//import java.util.List;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.dangdang.digital.api.ICacheApi;
//import com.dangdang.digital.api.ISystemApi;
//import com.dangdang.digital.model.Notice;
//
//public class SystemApiTester {
//	private static ISystemApi systemApi;
//	private static ICacheApi cacheApi;
//	@SuppressWarnings("resource")
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[]{"config/spring_common.xml"});
//		systemApi = (ISystemApi)cxt.getBean("systemApi");
//		cacheApi = (ICacheApi)cxt.getBean("cacheApi");
//	}
//	
//	@Test
//	public void test() {
//		List<Notice> list = cacheApi.getNoticeList(System.currentTimeMillis());
//		System.out.println(list);
//	}
//	
//
//}
