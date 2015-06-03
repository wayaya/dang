package com.dangdang.digital.utils;

import java.util.Calendar;
import java.util.Date;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.CloudPushPlan;

public class PushUtils {

	public static Date getSendTimeOfAutoPush(CloudPushPlan autoPushPlan){
		
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		Date today = new Date();
		
		Integer sendFrequency = autoPushPlan.getSendFrequency();
		
		if(sendFrequency != 1){
			
			String frequencyValue = autoPushPlan.getSendFrequencyValue();
			if(frequencyValue!=null && !frequencyValue.trim().equals("")){
				//这个array是有序的
				String[] frequencyArray = Constans.commaSpliter.split(frequencyValue);
				if(frequencyArray!=null && frequencyArray.length>0){
					
					if(sendFrequency == 2){
						
						int todayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
						
						int nextWeekFirstDay = 0;
						int thisWeekNextDay = 0;
						
						for(String day : frequencyArray){
							
							if(day==null|| day.trim().equals("")){
								continue;
							}
							
							try{
								//把星期一二三四五六日的数字  1， 2， 3， 4， 5， 6， 7 转换成 2， 3， 4， 5， 6， 7， 1
								int dayOfWeek = Integer.parseInt(day.trim());
								if(dayOfWeek==7){
									dayOfWeek=1;
								}else{
									dayOfWeek+=1;
								}
								
								if(todayOfWeek == dayOfWeek){
									
									date = getAutoPushTime(autoPushPlan, today );
									return date;
								}else{
									
									//找到本周（周日算下周）比今天大的第一天
									if((thisWeekNextDay==0 || thisWeekNextDay>dayOfWeek) && dayOfWeek>todayOfWeek ){
										thisWeekNextDay = dayOfWeek;
									}
									
									//找到下周第一天
									if((nextWeekFirstDay==0 || nextWeekFirstDay>dayOfWeek) && dayOfWeek<todayOfWeek){
										nextWeekFirstDay = dayOfWeek;
									}
								}
								
							}catch(Exception e){
							}
						}
						//如果本周的找到了
						if(thisWeekNextDay!=0){
							//距今天还有多少天
							int daysToAdd = thisWeekNextDay - todayOfWeek;
							return getAutoPushTime(autoPushPlan, DateUtil.addDate(today, daysToAdd));
						}
						
						//下周的找到了
						if(nextWeekFirstDay!=0){
							//距今天还有多少天
							int daysToAdd = (7-todayOfWeek)+nextWeekFirstDay;
							return getAutoPushTime(autoPushPlan, DateUtil.addDate(today, daysToAdd));
						}
						
					}else if(sendFrequency == 3){
						
						int todayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
						int nextMonthFirstDay = 0;
						int thisMonthNextDay = 0;
						
						for(String day : frequencyArray){
							
							try{
								
								if(day==null|| day.trim().equals("")){
									continue;
								}
								
								int dayOfMonth = Integer.parseInt(day.trim());
								if(todayOfMonth == dayOfMonth){
									
									date = getAutoPushTime(autoPushPlan, today );
									return date;
								}else{
									
									//找到本月比今天大的第一天
									if((thisMonthNextDay==0 || thisMonthNextDay>dayOfMonth) && dayOfMonth>todayOfMonth ){
										thisMonthNextDay = dayOfMonth;
									}
									
									//找到下月第一天
									if((nextMonthFirstDay==0 || nextMonthFirstDay>dayOfMonth) && dayOfMonth<todayOfMonth){
										nextMonthFirstDay = dayOfMonth;
									}
								}
								
							}catch(Exception e){
							}
						}
						//如果本月的找到了
						if(thisMonthNextDay!=0){
							
							String thisMonthNextDayStr = thisMonthNextDay<10 ? ("0"+thisMonthNextDay) : (thisMonthNextDay+"");
							String yearAndMonth = DateUtil.format(today, "yyyy-MM");
							Date pushDate = DateUtil.getdate(yearAndMonth+"-"+thisMonthNextDayStr);
							return getAutoPushTime(autoPushPlan, pushDate);
						}
						
						//下月的找到了
						if(nextMonthFirstDay!=0){
							
							 Calendar calendarToday = Calendar.getInstance();
							 //这个不会报错，比如2月份传31号进来，时间算出来会是28号或者29号
							 calendarToday.set(Calendar.DAY_OF_MONTH, nextMonthFirstDay);
							 calendarToday.add(Calendar.MONTH, 1);
					         return getAutoPushTime(autoPushPlan, calendarToday.getTime());
						}
						
					}
				}
			}
			
		}else{
			//每天
			date = getAutoPushTime(autoPushPlan, new Date());
		}
		return date;
	}

	private static Date getAutoPushTime(CloudPushPlan autoPushPlan, Date pushdate) {
		String timeBegin = autoPushPlan.getSendTimeBegin();
		String todaySendTime = DateUtil.format1(pushdate);
		todaySendTime+=(" "+timeBegin+":00");
		Date date = DateUtil.getDateByFormat(todaySendTime, DateUtil.DATE_PATTERN);
		return date;
	}
	
	public static void main(String[] args){
		
		CloudPushPlan autoPushPlan = new CloudPushPlan();
		
		autoPushPlan.setSendFrequency(3);
		autoPushPlan.setSendFrequencyValue("5,4,3,  17");
		autoPushPlan.setSendTimeBegin("09:00");
		
		System.out.println(getSendTimeOfAutoPush(autoPushPlan));
	}
	
}
