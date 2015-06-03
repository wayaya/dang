//package com.dangdang.digital.activity;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.dangdang.digital.api.IActivityApi;
//import com.dangdang.digital.vo.ActivityVo;
//
//public class ActivityApiJunit {
//	private static IActivityApi iActivityApi;
//	@SuppressWarnings("resource")
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[]{"config/spring_common.xml"});
//		iActivityApi = (IActivityApi)cxt.getBean("activityApi");
//	}
//	
////	膜拜
//	
//	public void testWorsh(){
//		ActivityVo activityVo = new ActivityVo();
//		activityVo.setActivityType(3);
//		activityVo.setCustid(1L);
//		activityVo.setUsername("zhouWorsh");
//		activityVo.setWorshipedCustId(2L);
//		
//		String result="errr";
//		try {
//			result = iActivityApi.worship(activityVo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("testWorsh:"+result);
//	}
//	
//	
////  分享
//	@Test
//	public void testShare(){
//		ActivityVo activityVo = new ActivityVo();
//		activityVo.setActivityType(3);
//		activityVo.setCustid(1L);
//		activityVo.setUsername("zhouShare");
//		String result="errr";
//		try {
//			result = iActivityApi.share(activityVo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("testShare:"+result);
//	}
//	
////个人活动信息	
//	
//	public void testUser(){
//		
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			map = iActivityApi.selectActivityUserInfo(1L);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("testUser:"+map.toString());
//	}
//	
//	
//}
