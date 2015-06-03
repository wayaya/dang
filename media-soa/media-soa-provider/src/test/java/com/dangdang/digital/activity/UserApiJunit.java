//package com.dangdang.digital.activity;
//
//import java.util.List;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.dangdang.digital.api.IUserApi;
//import com.dangdang.digital.model.ActivityRecord;
//import com.dangdang.digital.model.Media;
//
//public class UserApiJunit {
//	private static IUserApi iUserApi;
//	@SuppressWarnings("resource")
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[]{"config/spring_common.xml"});
//		iUserApi = (IUserApi)cxt.getBean("userApi");
//	}
//	
//	
////  获取用户壕赏图书
//	public void testSeclectBooks(){
//		String result="errr";
//		try {
//			List<Media> list = iUserApi.selectUserconsumeBooks(111L);
//			for(Media m:list){
//				System.out.println("testSeclectBooks:"+m.getMediaId());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
////  获取用户的活动信息【抽奖，膜拜，分享。。】
//	public void testSelectUserRecord(){
//		String result="errr";
//		try {
//			List<ActivityRecord> list = iUserApi.selectUserActivityRecord(1L, 3);
//			for(ActivityRecord a:list){
//				System.out.println("ActivityRecord:"+a.toString());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("testSelectUserRecord===");
//	}
//	
////  获取最近活动记录【比如:中奖名单】
//	@Test
//	public void testSelectLastedRecord(){
//		String result="errr";
//		try {
//			List<ActivityRecord> list = iUserApi.selectLatestActivityRecord(3, 5);
//			for(ActivityRecord a:list){
//				System.out.println("ActivityRecord:"+a.toString());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("testSelectLastedRecord===");
//	}
//	
//	
//}
