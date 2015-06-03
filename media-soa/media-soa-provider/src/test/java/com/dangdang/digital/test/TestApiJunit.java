//package com.dangdang.digital.test;  
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.alibaba.fastjson.JSONArray;
//import com.dangdang.digital.api.IConsumeApi;
//import com.dangdang.digital.api.IConsumerDepositApi;
//import com.dangdang.digital.api.IOrderApi;
//import com.dangdang.digital.api.ISystemApi;
//import com.dangdang.digital.api.ITestApi;
//import com.dangdang.digital.model.ConsumerDeposit;
//import com.dangdang.digital.vo.CreateConsumeVo;
//import com.dangdang.digital.vo.CreateOrderVo;
//
//public class TestApiJunit {
////	private static ITestApi iTestApi;
////	private static ISystemApi systemApi;
////	private static IOrderApi orderApi;
////	private static IConsumeApi consumeApi;
//	private static IConsumerDepositApi consumerDepositApi;
//	
//	@SuppressWarnings("resource")
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[]{"config/spring_common.xml"});
////		iTestApi = (ITestApi)cxt.getBean("testApi");
////		systemApi = (ISystemApi)cxt.getBean("systemApi");
////		orderApi = (IOrderApi)cxt.getBean("orderApi");
////		consumeApi = (IConsumeApi)cxt.getBean("consumeApi");
//		consumerDepositApi = (IConsumerDepositApi)cxt.getBean("consumerDepositApi");
//	}
//	
//	@Test
//	public void test() throws Exception{
////		System.out.println(systemApi.getProperty("1", "1"));
////		CreateOrderVo createOrderVo = new CreateOrderVo();
////		List<Long> mediaIds = new ArrayList<Long>();
////		mediaIds.add(53L);
////		List<Long> chapterIds = new ArrayList<Long>();
////		for(int i=8875;i<9067;i++){
////			chapterIds.add(0L+i);
////		}
////		createOrderVo.setChanelCode("3001");
////		createOrderVo.setChapterIds(chapterIds);
////		createOrderVo.setDeviceType("android");
////		createOrderVo.setDeviceVersion("4.1");
////		createOrderVo.setMediaIds(mediaIds);
////		createOrderVo.setPayment("2");
////		createOrderVo.setPlatform("3");
////		for(int i=100;i<999;i++){
////			createOrderVo.setCustId(Long.valueOf(100L+i));
////			orderApi.createAndSaveOrder(createOrderVo);
////		}
//		
////		CreateConsumeVo createConsumeVo = new CreateConsumeVo();
////		createConsumeVo.setConsumeType(Short.valueOf("2"));
////		Map<Integer, Integer> propMap = new HashMap<Integer, Integer>();
////		propMap.put(1, 1);
////		propMap.put(2, 1);
////		propMap.put(3, 1);
////		propMap.put(4, 1);
////		createConsumeVo.setPropMap(propMap);
//		
////		createConsumeVo.setConsumeType(Short.valueOf("3"));
////		createConsumeVo.setMonthlyId(6);
//		
////		createConsumeVo.setConsumeType(Short.valueOf("4"));
////		createConsumeVo.setMediaId(73L);
////		createConsumeVo.setRewardsMoney(1111);
////		for(int i=1;i<9;i++){
////			createConsumeVo.setCustId(Long.valueOf(100L+i));
////			consumeApi.createAndSaveConsume(createConsumeVo);
////		}
//		ConsumerDeposit consumerDeposit = new ConsumerDeposit();
//		consumerDeposit.setCustId(123L);
//		consumerDeposit.setCreationDate(new Date());
//		consumerDeposit.setIsPaid(Short.valueOf("1"));
//		consumerDeposit.setMainGold(400);
//		consumerDeposit.setMoney(4);
//		consumerDeposit.setPayTime(new Date());
//		consumerDeposit.setPayment("1");
//		consumerDeposit.setDepositOrderNo("123445");
//		
//		consumerDepositApi.createAndSaveConsumerDeposit(consumerDeposit);
//		
//	}
//}