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
//import com.dangdang.digital.api.IIllegalWordApi;
//import com.dangdang.digital.api.ISystemApi;
//import com.dangdang.digital.model.Notice;
//
//public class IllegalWordApiTester {
//	private static IIllegalWordApi illegalWordApi;
//	@SuppressWarnings("resource")
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[]{"config/spring_common.xml"});
//		illegalWordApi = (IIllegalWordApi)cxt.getBean("illegalWordApi");
//	}
//	
//	@Test
//	public void test() {
//		List<String> listContent = illegalWordApi.getAllIllWordContent();
//		System.out.println(illegalWordApi.contentsIllegalWords("我靠"));
//	//	List<Notice> list = cacheApi.getNoticeList(System.currentTimeMillis());
//		System.out.println(listContent);
//	}
//	
//
//}
