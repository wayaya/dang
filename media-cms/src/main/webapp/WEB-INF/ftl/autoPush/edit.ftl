<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自动推送</title>
<#include "../common/common-css.ftl">
<#include "../common/common-js.ftl">
<script type="text/javascript" src="/script/jquery/ajaxfileupload.js"></script>
</head>
<body>
 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>自动推送&gt;&gt;编辑</h3>
<div class="mrdiv" <#if !errorMessage??>style="display:none"</#if> >
			      		 <table>
		        			<tr>
								<td style="padding-left:50px;"><span id="message">
								<#if errorMessage??><font color="red">${errorMessage}</font></#if>
								</span></td>
							</tr>
						</table>
				    </div>
<form id="editForm" name="editForm" method="post" action="/autoPush/editSubmit.go" >
	<input type="hidden" id="cloudPushPlanId" name="cloudPushPlanId" value="<#if autoPlan?? && autoPlan.cloudPushPlanId??>${autoPlan.cloudPushPlanId}</#if>" />
	<input type="hidden" name="planType" value="2" />
	<input type="hidden" id="editMode" name="editMode" value="1" />
	<input type="hidden" id="messageType" name="messageType" value="1" />
	
	<table class="table2">
	
		<tr>
			<td>选择应用:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
			<select name="appId" id="appId">
				<option value="0">---请选择应用---</option>
				<option value="1" <#if autoPlan?? && autoPlan.appId?? && autoPlan.appId==1>selected</#if>>当当读书Android/iphone</option>
				<option value="2" <#if autoPlan?? && autoPlan.appId?? && autoPlan.appId==2>selected</#if>>当读小说Android</option>
				<option value="6" <#if autoPlan?? && autoPlan.appId?? && autoPlan.appId==6>selected</#if>>当读小说Iphone版</option>
				<option value="3" <#if autoPlan?? && autoPlan.appId?? && autoPlan.appId==3>selected</#if>>翻篇儿</option>
				<option value="4" <#if autoPlan?? && autoPlan.appId?? && autoPlan.appId==4>selected</#if>>悦耳听书</option>
				<option value="5" <#if autoPlan?? && autoPlan.appId?? && autoPlan.appId==5>selected</#if>>当当读书Ipad版</option>
			</select>
			<span id="appIdInfo"></span>
			</td>
		</tr>
		
		<tr>
			<td>计划名称<font color="red">*</font>:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
				<input type="input" id="planName" name="planName" maxlength="20" 
						value="<#if autoPlan?? && autoPlan.planName??>${autoPlan.planName}</#if>" style="width:300px" />
						<span id="planNameInfo"></span>
			</td>
		</tr>
		
		<tr>
			<td>触发条件:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
				<select name="planCondition" id="planCondition">
					<option value="">---请选择---</option>
										
				</select>
				<span id="planConditionInfo">
				</span>
				<br />
				<span id="planConditionParamsSpan">
				</span>
				
			</td>
		</tr>
		
		<tr>
		<td>消息标题:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
		<input type="input" id="messageTitle" name="messageTitle" maxlength="20" 
		value="<#if autoPlan?? && autoPlan.messageTitle??>${autoPlan.messageTitle}</#if>" style="width:300px" /> &nbsp;&nbsp; 限制20字，可选，标题空显示应用名称<span id="messageTitleInfo"></span></td>
		</tr>
		<tr>
		<td>消息内容<font color="red">*</font>:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
		<textarea id="messageDescription" maxlength="40" name="messageDescription" style="height:110px; width:300px"><#if autoPlan?? && autoPlan.messageDescription??>${autoPlan.messageDescription}</#if></textarea> 
		 &nbsp;&nbsp; 限制40字<span id="messageDescriptionInfo"></span>
		 <br />
		 	<span>
				<select id="conditionVariableSelect">
					<option value="">---请选择---</option>
				</select> <button type="button" id="addVariable" >添加</button>
		 	</span>
		 </td>
		</tr>
		
		<tr>
		<td>设备范围<font color="red">*</font>:</td>
			<td style="text-align:left; padding:10px 20px 10px 20px;">
			<input type="checkbox" id="deviceTypeAndroid" name="deviceTypeAndroid" <#if (autoPlan?? && autoPlan.deviceTypeAndroid?? &&autoPlan.deviceTypeAndroid==true)|| !autoPlan??>checked</#if> /> <label for="deviceTypeAndroid">Android</label>  &nbsp;&nbsp;
		 	<input type="checkbox" id="deviceTypeIos" name="deviceTypeIos" <#if autoPlan?? && autoPlan.deviceTypeIos?? &&autoPlan.deviceTypeIos==true>checked</#if> /> <label for="deviceTypeIos">IOS</label>
		 	<span id="deviceTypeInfo"></span>
		 	</td>
		</tr>
		
		<tr>
			<td>安卓应用版本高于(包含)：</td>
			<td style="text-align:left; padding:10px 20px 10px 20px;">
				<input type="text" id="versionAbove" name="versionAbove" value="<#if (autoPlan?? && autoPlan.versionAbove??)>${autoPlan.versionAbove}</#if>"  />
			</td>
		</tr>
		
		<tr>
			<td>IOS应用版本高于(包含)：</td>
			<td style="text-align:left; padding:10px 20px 10px 20px;">
				<input type="text" id="iosVersionAbove" name="iosVersionAbove" value="<#if (autoPlan?? && autoPlan.iosVersionAbove??)>${autoPlan.iosVersionAbove}</#if>"  />
			</td>
		</tr>
		
		<tr>
		<td>用户范围:</td>
		<td style="text-align:left; padding:10px 20px 10px 20px;">
			<input type="radio" id="pushScope1" name="pushScope" value="1" <#if (autoPlan?? && autoPlan.pushScope?? &&autoPlan.pushScope==1)|| !autoPlan??>checked</#if> /><label for="pushScope1">满足触发条件的所有用户</label> &nbsp;&nbsp; 
			<input type="radio" id="pushScope2" name="pushScope" value="2" <#if (autoPlan?? && autoPlan.pushScope?? &&autoPlan.pushScope==2) >checked</#if> /><label for="pushScope2">无需触发条件的指定用户 </label>
			<div id="pushScope2Div" style="display:none">
				<br />
				<textarea id="userIds" name="userIds" style="height:110px; width:300px"><#if autoPlan?? && autoPlan.userIds?? >${autoPlan.userIds}</#if></textarea> 
				<input type="file" id="uploadCsv" name="uploadCsv" />
				<span id="userIdsInfo"></span>
			</div>
		</td>
		</tr>
		
		<tr>
			<td>开始时间<font color="red">*</font>:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
				<input type="text" id="startDateStr" name="startDateStr" readonly="true" value="<#if (autoPlan?? && autoPlan.startDate??)>${autoPlan.startDate?string("yyyy-MM-dd HH:mm:ss")}</#if>"  /><span id="startDateStrInfo"></span>
			</td>
		</tr>
		
		<tr>
			<td>结束时间<font color="red">*</font>:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
				<input type="text" id="endDateStr" name="endDateStr" readonly="true" value="<#if (autoPlan?? && autoPlan.endDate??)>${autoPlan.endDate?string("yyyy-MM-dd HH:mm:ss")}</#if>"  /><span id="endDateStrInfo"></span>
			</td>
		</tr>
		
		<tr>
			<td>推送频率:</td>
			<td style="text-align:left; padding:10px 20px 10px 20px;">
				
				<table style="width:800px;">
					<tr>
						<td style="border:none" width="10%">
							<input type="radio" id="sendFrequency1" onclick="changeFrequencyValue(1)" name="sendFrequency" value="1" <#if (autoPlan?? && autoPlan.sendFrequency?? &&autoPlan.sendFrequency==1)|| !autoPlan??>checked</#if> />
							<label for="sendFrequency1">按天</label>
						</td>
						<td style="border:none"></td>
					</tr>
					
					<tr>
						<td style="border:none" colspan="2">
							<input type="hidden" id="sendFrequencyValue" name="sendFrequencyValue" value="<#if autoPlan?? && autoPlan.sendFrequencyValue??>${autoPlan.sendFrequencyValue}</#if>" /> 
						</td>
					</tr>
					
					<tr>
						<td style="border:none">
							<input type="radio" id="sendFrequency2" onclick="changeFrequencyValue(2)" name="sendFrequency" value="2" <#if autoPlan?? && autoPlan.sendFrequency?? &&autoPlan.sendFrequency==2>checked</#if> />
							<label for="sendFrequency2">按周</label>
						</td>
						<td style="border:none" id="weektd">
							
							<input type="checkbox" id="week1" onclick="changeFrequencyValue(2)" value="1" ><label for="week1">周一</label> &nbsp;&nbsp;
							<input type="checkbox" id="week2" onclick="changeFrequencyValue(2)" value="2" ><label for="week2">周二</label> &nbsp;&nbsp;
							<input type="checkbox" id="week3" onclick="changeFrequencyValue(2)" value="3" ><label for="week3">周三</label> &nbsp;&nbsp;
							<input type="checkbox" id="week4" onclick="changeFrequencyValue(2)" value="4" ><label for="week4">周四</label> &nbsp;&nbsp;
							<input type="checkbox" id="week5" onclick="changeFrequencyValue(2)" value="5" ><label for="week5">周五</label> &nbsp;&nbsp;
							<input type="checkbox" id="week6" onclick="changeFrequencyValue(2)" value="6" ><label for="week6">周六</label> &nbsp;&nbsp;
							<input type="checkbox" id="week7" onclick="changeFrequencyValue(2)" value="7" ><label for="week7">周日</label> &nbsp;&nbsp;
							
						</td>
					</tr>
					<tr>
						<td style="border:none" colspan="2">
						</td>
					</tr>
					<tr>
						<td style="border:none">
							<input type="radio" id="sendFrequency3" onclick="changeFrequencyValue(3)"  name="sendFrequency" value="3" <#if autoPlan?? && autoPlan.sendFrequency?? &&autoPlan.sendFrequency==3>checked</#if> />
							<label for="sendFrequency3">按月</label>
						</td>
						<td style="border:none" id="daytd">
							
							<input type="checkbox" id="day1" onclick="changeFrequencyValue(3)" value="1" >1 &nbsp;&nbsp;
							<input type="checkbox" id="day2" onclick="changeFrequencyValue(3)" value="2" >2 &nbsp;&nbsp;
							<input type="checkbox" id="day3" onclick="changeFrequencyValue(3)" value="3" >3 &nbsp;&nbsp;
							<input type="checkbox" id="day4" onclick="changeFrequencyValue(3)" value="4" >4 &nbsp;&nbsp;
							<input type="checkbox" id="day5" onclick="changeFrequencyValue(3)" value="5" >5 &nbsp;&nbsp;
							<input type="checkbox" id="day6" onclick="changeFrequencyValue(3)" value="6" >6 &nbsp;&nbsp;
							<input type="checkbox" id="day7" onclick="changeFrequencyValue(3)" value="7" >7 &nbsp;&nbsp;
							<input type="checkbox" id="day8" onclick="changeFrequencyValue(3)" value="8" >8 &nbsp;&nbsp;
							<input type="checkbox" id="day9" onclick="changeFrequencyValue(3)" value="9" >9 &nbsp;&nbsp;
							<input type="checkbox" id="day10" onclick="changeFrequencyValue(3)" value="10" >10 &nbsp;&nbsp;
							<input type="checkbox" id="day11" onclick="changeFrequencyValue(3)" value="11"  >11 &nbsp;&nbsp;
							<input type="checkbox" id="day12" onclick="changeFrequencyValue(3)" value="12" >12 &nbsp;&nbsp;
							<input type="checkbox" id="day13" onclick="changeFrequencyValue(3)" value="13" >13 &nbsp;&nbsp;
							<input type="checkbox" id="day14" onclick="changeFrequencyValue(3)" value="14" >14 &nbsp;&nbsp;
							<input type="checkbox" id="day15" onclick="changeFrequencyValue(3)" value="15" >15 &nbsp;&nbsp;
							<input type="checkbox" id="day16" onclick="changeFrequencyValue(3)" value="16" >16 &nbsp;&nbsp;<br/>
							<input type="checkbox" id="day17" onclick="changeFrequencyValue(3)" value="17" >17 &nbsp;&nbsp;
							<input type="checkbox" id="day18" onclick="changeFrequencyValue(3)" value="18" >18 &nbsp;&nbsp;
							<input type="checkbox" id="day19" onclick="changeFrequencyValue(3)" value="19" >19 &nbsp;&nbsp;
							<input type="checkbox" id="day20" onclick="changeFrequencyValue(3)" value="20" >20 &nbsp;&nbsp;
							<input type="checkbox" id="day21" onclick="changeFrequencyValue(3)" value="21" >21 &nbsp;&nbsp;
							<input type="checkbox" id="day22" onclick="changeFrequencyValue(3)" value="22" >22 &nbsp;&nbsp;
							<input type="checkbox" id="day23" onclick="changeFrequencyValue(3)" value="23" >23 &nbsp;&nbsp;
							<input type="checkbox" id="day24" onclick="changeFrequencyValue(3)" value="24" >24 &nbsp;&nbsp;
							<input type="checkbox" id="day25" onclick="changeFrequencyValue(3)" value="25" >25 &nbsp;&nbsp;
							<input type="checkbox" id="day26" onclick="changeFrequencyValue(3)" value="26" >26 &nbsp;&nbsp;
							<input type="checkbox" id="day27" onclick="changeFrequencyValue(3)" value="27" >27 &nbsp;&nbsp;
							<input type="checkbox" id="day28" onclick="changeFrequencyValue(3)" value="28" >28 &nbsp;&nbsp;
							<input type="checkbox" id="day29" onclick="changeFrequencyValue(3)" value="29" >29 &nbsp;&nbsp;
							<input type="checkbox" id="day30" onclick="changeFrequencyValue(3)" value="30" >30 &nbsp;&nbsp;
							<input type="checkbox" id="day31" onclick="changeFrequencyValue(3)" value="31" >31 &nbsp;&nbsp;
						
						</td>
					</tr>
					
				</table>
			</td>
		</tr>
		
		
		<tr>
			<td>推送时段:</td>
			<td style="text-align:left; padding:10px 20px 10px 20px;">
				<select name="sendTimeBegin" id="sendTimeBegin">
					<option value="00:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='00:00'>selected</#if>>00:00</option>
					<option value="01:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='01:00'>selected</#if>>01:00</option>
					<option value="08:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='08:00'>selected</#if>>08:00</option>
					<option value="08:30" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='08:30'>selected</#if>>08:30</option>
					<option value="09:00" <#if (autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='09:00')|| !autoPlan??>selected</#if>>09:00</option>
					<option value="09:30" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='09:30'>selected</#if>>09:30</option>
					<option value="10:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='10:00'>selected</#if>>10:00</option>
					<option value="10:30" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='10:30'>selected</#if>>10:30</option>
					<option value="11:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='11:00'>selected</#if>>11:00</option>
					<option value="11:30" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='11:30'>selected</#if>>11:30</option>
					<option value="12:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='12:00'>selected</#if>>12:00</option>
					<option value="23:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='23:00'>selected</#if>>23:00</option>
					<option value="24:00" <#if autoPlan?? && autoPlan.sendTimeBegin?? && autoPlan.sendTimeBegin=='24:00'>selected</#if>>24:00</option>
				</select>
				至
				<select name="sendTimeEnd" id="sendTimeEnd">
					<option value="00:00" <#if autoPlan?? && autoPlan.sendTimeEnd?? && autoPlan.sendTimeEnd=='00:00'>selected</#if>>00:00</option>
					<option value="01:00" <#if autoPlan?? && autoPlan.sendTimeEnd?? && autoPlan.sendTimeEnd=='01:00'>selected</#if>>01:00</option>
					<option value="09:00" <#if autoPlan?? && autoPlan.sendTimeEnd?? && autoPlan.sendTimeEnd=='09:00'>selected</#if>>09:00</option>
					<option value="12:00" <#if autoPlan?? && autoPlan.sendTimeEnd?? && autoPlan.sendTimeEnd=='12:00'>selected</#if>>12:00</option>
					<option value="23:00" <#if (autoPlan?? && autoPlan.sendTimeEnd?? && autoPlan.sendTimeEnd=='23:00')|| !autoPlan??>selected</#if>>23:00</option>
					<option value="24:00" <#if autoPlan?? && autoPlan.sendTimeEnd?? && autoPlan.sendTimeEnd=='24:00'>selected</#if>>24:00</option>
				</select>
			</td>
		</tr>
		
		
		<tr>
		<td>后续行为:</td>
		<td style="text-align:left; padding:10px 20px 10px 20px;">
		<!--
		<input type="radio" id="openType2" onclick="controlParamModel()" name="openType" value="2" <#if (autoPlan?? && autoPlan.openType?? &&autoPlan.openType==2)|| !autoPlan??>checked</#if> /><label for="openType2">打开应用</label><br />
		<input type="radio" id="openType1" onclick="controlParamModel()" name="openType" value="1" <#if (autoPlan?? && autoPlan.openType?? &&autoPlan.openType==1)>checked</#if>  /><label for="openType1">打开网页</label> 
		<input type="text" id="openUrl" name="openUrl" style="width:300px" value="<#if (autoPlan?? && autoPlan.openUrl??)>${autoPlan.openUrl}</#if>" /><span id="openUrlInfo"></span><br />
		<input type="radio" id="openType3" onclick="controlParamModel()" name="openType" value="0" <#if (autoPlan?? && autoPlan.openType?? &&autoPlan.openType==0)>checked</#if> /><label for="openType3">传递参数</label><br />
		-->
		<input type="hidden" name="openType" value="0" />
		<table id="pushStrategyTb" style="width:600px; ">
			<tr id="pushStrategyTr"><td style="border:none; text-align:left" colspan=5>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="pushStrategyId" id="pushStrategyId">
					<option value='0'>---请选择行为模板---</option>
				</select>
			</td></tr>
			
			<tr style="display:none" id="custom_android" >
				<td colspan="5" style="border:none">安卓参数</td>
			</tr>
			
			<tr style="display:none" id="custom_tr_key1"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_key1" name="dd_push_key1" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_value1" name="dd_push_value1" /></td><td style="border:none"><span id="dd_push_desc1"></span></td></tr>
			<tr style="display:none" id="custom_tr_key_br1"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_tr_key2"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_key2" name="dd_push_key2" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_value2" name="dd_push_value2" /></td><td style="border:none"><span id="dd_push_desc2"></span></td></tr>
			<tr style="display:none" id="custom_tr_key_br2"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_tr_key3"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_key3" name="dd_push_key3" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_value3" name="dd_push_value3" /></td><td style="border:none"><span id="dd_push_desc3"></span></td></tr>
			<tr style="display:none" id="custom_tr_key_br3"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_tr_key4"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_key4" name="dd_push_key4" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_value4" name="dd_push_value4" /></td><td style="border:none"><span id="dd_push_desc4"></span></td></tr>
			<tr style="display:none" id="custom_tr_key_br4"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_tr_key5"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_key5" name="dd_push_key5" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_value5" name="dd_push_value5" /></td><td style="border:none"><span id="dd_push_desc5"></span></td></tr>
			<tr style="display:none" id="custom_tr_key_br5"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_tr_key6"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_key6" name="dd_push_key6" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_value6" name="dd_push_value6" /></td><td style="border:none"><span id="dd_push_desc6"></span></td></tr>
			<tr style="display:none" id="custom_tr_key_br6"><td style="border:none; text-align:left" colspan=5><hr /></td></tr>
			
			
			<tr style="display:none"  id="custom_ios" >
				<td colspan="5" style="border:none">IOS参数</td>
			</tr>
			
			<tr style="display:none" id="custom_ios_tr_key1"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_ios_key1" name="dd_push_ios_key1" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_ios_value1" name="dd_push_ios_value1" /></td><td style="border:none"><span id="dd_push_ios_desc1"></span></td></tr>
			<tr style="display:none" id="custom_ios_tr_key_br1"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_ios_tr_key2"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_ios_key2" name="dd_push_ios_key2" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_ios_value2" name="dd_push_ios_value2" /></td><td style="border:none"><span id="dd_push_ios_desc2"></span></td></tr>
			<tr style="display:none" id="custom_ios_tr_key_br2"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_ios_tr_key3"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_ios_key3" name="dd_push_ios_key3" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_ios_value3" name="dd_push_ios_value3" /></td><td style="border:none"><span id="dd_push_ios_desc3"></span></td></tr>
			<tr style="display:none" id="custom_ios_tr_key_br3"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_ios_tr_key4"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_ios_key4" name="dd_push_ios_key4" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_ios_value4" name="dd_push_ios_value4" /></td><td style="border:none"><span id="dd_push_ios_desc4"></span></td></tr>
			<tr style="display:none" id="custom_ios_tr_key_br4"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_ios_tr_key5"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_ios_key5" name="dd_push_ios_key5" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_ios_value5" name="dd_push_ios_value5" /></td><td style="border:none"><span id="dd_push_ios_desc5"></span></td></tr>
			<tr style="display:none" id="custom_ios_tr_key_br5"><td style="border:none; text-align:left" colspan=5></td></tr>
			
			<tr style="display:none" id="custom_ios_tr_key6"><td style="border:none">键：</td><td style="border:none"><input type="text" id="dd_push_ios_key6" name="dd_push_ios_key6" /></td>
				<td style="border:none">值：</td><td style="border:none"><input type="text" id="dd_push_ios_value6" name="dd_push_ios_value6" /></td><td style="border:none"><span id="dd_push_ios_desc6"></span></td></tr>
			<tr style="display:none" id="custom_ios_tr_key_br6"><td style="border:none; text-align:left" colspan=5></td></tr>
			
		</table>
		
		
		<br />
		</td>
		</tr>
		
		<tr>
		<td>活动状态:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
			<input type="radio" id="planStatus1" name="planStatus" value="1000" <#if (autoPlan?? && autoPlan.planStatus?? &&autoPlan.planStatus==1000)>checked</#if> /><label for="planStatus1">进行中 </label>&nbsp;&nbsp; 
			<input type="radio" id="planStatus2" name="planStatus" value="1001" <#if (autoPlan?? && autoPlan.planStatus?? &&autoPlan.planStatus==1001)|| !autoPlan?? >checked</#if> /><label for="planStatus2">停止中</label>
		</td>
		</tr>
		<tr>
		<td colspan="2">
		<#if userSessionInfo?? && userSessionInfo.f['188']?? >
		<button type="button" onclick="checkForm()">确认</button>
		</#if>
		&nbsp;&nbsp;<button type="button"  onclick="javascript:window.location.href='/autoPush/list.go'">取消</button></td>
		</tr>
	</table>
</form>
</div>
</div>
</div>
</div>
<script type="text/javascript" >
	$(function(){
		$('#startDateStr').calendar({format:'yyyy-MM-dd HH:mm:ss'});
		$('#endDateStr').calendar({format:'yyyy-MM-dd HH:mm:ss'});
		
		$('#pushScope1').click(function(){
			$('#pushScope2Div').hide();
		});
		$('#pushScope2').click(function(){
			$('#pushScope2Div').show();
		});
		
		$('#uploadCsv').change(function(){
			uploadUserIds();
		});
		
		if($('#pushScope2').is(':checked')){
			$('#pushScope2Div').show();
		}else{
			$('#pushScope2Div').hide();
		}
		
		<#if autoPlan?? && autoPlan.sendFrequencyValue?? && autoPlan.sendFrequency??>
		
			$('#sendFrequency${autoPlan.sendFrequency}').attr('checked','true');
			
			var sendFrequencyValue = '${autoPlan.sendFrequencyValue}';
			
			if(sendFrequencyValue!=''){
				
				var valueArray = sendFrequencyValue.split(',');
				
				for(var j=0; j<valueArray.length; j++){
					
					if('${autoPlan.sendFrequency}'=='2'){
						$('#week'+valueArray[j]).attr('checked','true');
					}
					if('${autoPlan.sendFrequency}'=='3'){
						$('#day'+valueArray[j]).attr('checked','true');
					}
				}
			}
		</#if>
		
		
		
		$('#appId').change(initByAppId);
		initByAppId(true);
		
		$('#planCondition').change(changeParamsAndVariablesByConditionId);
		changeParamsAndVariablesByConditionId();
		
		$('#addVariable').click(function(){
			
			if($('#conditionVariableSelect').val()!=null && $('#conditionVariableSelect').val()!=''){
				$('#messageDescription').val($('#messageDescription').val()+$('#conditionVariableSelect').val());
			}
		});
		
		$('#pushStrategyId').change(changeCustomContentByStrategyId);
		$('#deviceTypeAndroid').click(showOrHideCustomContent); 
		$('#deviceTypeIos').click(showOrHideCustomContent); 
		
		controlParamModel();
	});
	
	var paramMap = {};
	<#if paramList?? >
		<#list paramList as paramInList>  
			paramMap['${paramInList.keyName}'] = '${paramInList.keyValue}';
		</#list>  
	</#if>
	
	function controlParamModel(){
		
		if($('#openType2').is(':checked') || $('#openType1').is(':checked')){
			
			$('#pushStrategyTb').hide();
			
		}else if($('#openType3').is(':checked')){
			
			$('#pushStrategyTb').show();
		}
	}
	
	
	function changeParamsAndVariablesByConditionId(){
		initParamsByConditionId($('#planCondition').val());
		initVariablesByConditionId($('#planCondition').val());
	}
	
	function initParamsByConditionId(conditionId, isInit){
		
		$('#planConditionParamsSpan').html('<br />');
		if(conditionId==null || conditionId==''){
			return;
		}
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/autoPush/getParamsDefByCondition.go",
			   async: true,
			   cache: false,
			   data: {
					"conditionId":conditionId,				
				    "timestamp":new Date().getTime()
			   },
			   dataType:"json",
			   success: function(msg){
				   
				   for(var j=0; j<msg.length; j++){
					   $('#planConditionParamsSpan').append('<span>'+msg[j].keyInputName+':</span><input type="text" defName="'+msg[j].keyName+'" defType="'+msg[j].keyType+'" id="conditionParam_'+msg[j].pushConditionParamsDefinitionId+'" name="conditionParam_'+msg[j].pushConditionParamsDefinitionId+'" /><br /><br />');
					   
					   if(typeof isInit !='undefinied' && isInit==true){
						   $('#conditionParam_'+msg[j].pushConditionParamsDefinitionId).val(paramMap[msg[j].keyName]);
					   }
				   }
			   }
			});
		
	}
	
	function initVariablesByConditionId(conditionId){
		
		$("#conditionVariableSelect option[value!='']").remove();
		if(conditionId==null || conditionId==''){
			return;
		}
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/autoPush/getVariablesByCondition.go",
			   async: true,
			   cache: false,
			   data: {
					"conditionId":conditionId,				
				    "timestamp":new Date().getTime()
			   },
			   dataType:"json",
			   success: function(msg){
				   
				   for(var j=0; j<msg.length; j++){
					   $("#conditionVariableSelect").append("<option value='"+msg[j].variableName+"'>"+msg[j].variableName+":"+msg[j].variableDescription+"</option>");
				   }
			   }
			});
		
	}
	
	function clearCustomParam(){
		
		$('#custom_android').hide();
		$('#custom_ios').hide();
		
		for(var trIndex=1; trIndex<=6; trIndex++){
			
		   $('#custom_tr_key'+trIndex).hide();
		   $('#custom_ios_tr_key'+trIndex).hide();
		   
		   $('#custom_tr_key_br'+trIndex).hide();
		   $('#custom_ios_tr_key_br'+trIndex).hide();
		   
		   $('#dd_push_key'+trIndex).val('');
		   $('#dd_push_ios_key'+trIndex).val('');
		   
		   $('#dd_push_value'+trIndex).val('');
		   $('#dd_push_ios_value'+trIndex).val('');
		   
		   $('#dd_push_desc'+trIndex).html('');
		   $('#dd_push_ios_desc'+trIndex).html('');
		}
		
		$('[id^="custom_tr_key"]').removeAttr('manualHide');
		$('[id^="custom_ios_tr_key"]').removeAttr('manualHide');
		
	}
	
	function showOrHideCustomContent(){
		
		if($('#deviceTypeIos').is(':checked')){
			
			if($('#pushStrategyId').val()!=0){
				$('[id="custom_ios"]').show();
			}
			
			$('[id^="custom_ios_tr_key"][manualHide=true]').show();
			
		}else{
			$('#custom_ios').hide();
			$('[id^="custom_ios_tr_key"]:visible').attr('manualHide',true);
			$('[id^="custom_ios_tr_key"]:visible').hide();
			
		}
		
		if($('#deviceTypeAndroid').is(':checked')){
			
			if($('#pushStrategyId').val()!=0){
				$('[id="custom_android"]').show();
			}
			$('[id^="custom_tr_key"][manualHide=true]').show();
			
		}else{
			$('#custom_android').hide();
			$('[id^="custom_tr_key"]:visible').attr('manualHide',true);
			$('[id^="custom_tr_key"]:visible').hide();
			
		}
		
	}
	
	function getIndexByStrategyParamName(paramName){
		
		var resultIndex = '';
		
		$('input:visible[id^="dd_push_key"]').each(function(){
			
			if(paramName == $(this).val()){
				
				resultIndex = $(this).attr('id').substring('dd_push_key'.length, $(this).attr('id').length);
				return;
			}
		});
		
		return resultIndex;
	}
	
	function changeCustomContentByStrategyId(isInit){
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/autoPush/getParamsByStrategy.go",
			   async: true,
			   cache: false,
			   data: {
					"strategyId":$('#pushStrategyId').val(),				
				    "timestamp":new Date().getTime()
			   },
			   dataType:"json",
			   success: function(msg){
				   
				   clearCustomParam();   
				   
				   var android_index=0;
				   var ios_index=0;
				   
				   for(var j=0; j<msg.length; j++){
					   
					   if(msg[j].deviceType==3){
						   android_index++;
						   if($('#deviceTypeAndroid').is(':checked')){
							   $('#custom_tr_key'+android_index).show();
							   $('#custom_tr_key_br'+android_index).show();
						   }else{
							   $('#custom_tr_key'+android_index).attr('manualHide',true);
							   $('#custom_tr_key_br'+android_index).attr('manualHide',true);
						   }
						   $('#dd_push_key'+android_index).val(msg[j].paramName);
						   $('#dd_push_value'+android_index).val(msg[j].paramDefaultValue);
						   $('#dd_push_desc'+android_index).html(msg[j].paramDescription);
					   }
					   
					   if(msg[j].deviceType==4){
						   ios_index++;
						   if($('#deviceTypeIos').is(':checked')){
							   $('#custom_ios_tr_key'+ios_index).show();
							   $('#custom_ios_tr_key_br'+ios_index).show();
						   }else{
							   $('#custom_ios_tr_key'+ios_index).attr('manualHide',true);
							   $('#custom_ios_tr_key_br'+ios_index).attr('manualHide',true);
						   }
						   $('#dd_push_ios_key'+ios_index).val(msg[j].paramName);
						   $('#dd_push_ios_value'+ios_index).val(msg[j].paramDefaultValue);
						   $('#dd_push_ios_desc'+ios_index).html(msg[j].paramDescription);
					   }
					   
				   }
				   
				   if(android_index>0 && $('#deviceTypeAndroid').is(':checked')){
					   $('#custom_android').show();
				   }
				   
				   if(ios_index>0 && $('#deviceTypeIos').is(':checked')){
				   	   $('#custom_ios').show();
				   }
				   
				   if(typeof isInit !='undefined' && isInit==true){
						
						<#if customContentMap?? >
						    <#list customContentMap?keys as testKey>  
						    		if('planId' != '${testKey}'){
						    			var strategy_index = getIndexByStrategyParamName('${testKey}')
										$('#dd_push_value'+strategy_index).val('${customContentMap[testKey]}');
						    		}
						    </#list>  
						</#if>
						
						<#if customContentIOSMap?? >
						    <#list customContentIOSMap?keys as testIOSKey>  
						    		if('planId' != '${testIOSKey}'){
						    			var strategy_index = getIndexByStrategyIOSParamName('${testIOSKey}');
										$('#dd_push_ios_value'+strategy_index).val('${customContentIOSMap[testIOSKey]}');
						    		}
						    </#list>  
						</#if>
				   }
			   }
			});
		
	}
	
	function getIndexByStrategyIOSParamName(paramName){
		
		var resultIndex = '';
		
		$('input:visible[id^="dd_push_ios_key"]').each(function(){
			
			if(paramName == $(this).val()){
				
				resultIndex = $(this).attr('id').substring('dd_push_ios_key'.length, $(this).attr('id').length);
				return;
			}
		});
		
		return resultIndex;
	}
	
	function initByAppId(isInit){
		
		if(typeof isInit !='undefined'){
			initConditionsByApp($('#appId').val(), isInit);
			initStrategiesByApp($('#appId').val(), isInit);
			
		}else{
			initConditionsByApp($('#appId').val());
			initStrategiesByApp($('#appId').val());
		}
		initDeviceTypeByAppId($('#appId').val());
	}
	
	function initDeviceTypeByAppId(appId){
		
		if(appId==5||appId==6){
			$('#deviceTypeIos').attr('checked',true);
			$('#deviceTypeAndroid').removeAttr('checked');
			$('#deviceTypeAndroid').attr('disabled',true);
			showOrHideCustomContent();
		}else{
			$('#deviceTypeAndroid').removeAttr('disabled');
		}
	}
	
	function initStrategiesByApp(appId, isInit){
		
		$("#pushStrategyId option[value!=0]").remove();
		
		//clear content
		changeCustomContentByStrategyId();
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/autoPush/getStrategies.go",
			   async: true,
			   cache: false,
			   data: {
					"appId":appId,				
				    "timestamp":new Date().getTime()
			   },
			   dataType:"json",
			   success: function(msg){
				   
				   for(var j=0; j<msg.length; j++){
					   $("#pushStrategyId").append("<option type="+msg[j].strategyType+" value='"+msg[j].cloudPushStrategyId+"'>"+msg[j].strategyName+"</option>");
				   }
				   
				   if(typeof isInit !='undefined' && isInit==true){
					   $("#pushStrategyId option[value='<#if autoPlan?? && autoPlan.pushStrategyId??>${autoPlan.pushStrategyId}</#if>']").attr("selected", "selected");
					   changeCustomContentByStrategyId(isInit);
				   }
				   
			   }
			});
		
	}
	
	function initConditionsByApp(appId, isInit){
		
		$("#planCondition option[value!='']").remove();
		$("#conditionVariableSelect option[value!='']").remove();
		$('#planConditionParamsSpan').html('<br />');
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/autoPush/getConditions.go",
			   async: true,
			   cache: false,
			   data: {
					"appId":appId,				
				    "timestamp":new Date().getTime()
			   },
			   dataType:"json",
			   success: function(msg){
				   
				   for(var j=0; j<msg.length; j++){
					   $("#planCondition").append("<option value='"+msg[j].cloudPushConditionId+"'>"+msg[j].conditionName+"</option>");
				   }
				   
				   if(typeof isInit !='undefined' && isInit==true){
					   $("#planCondition option[value='<#if autoPlan?? && autoPlan.planCondition??>${autoPlan.planCondition}</#if>']").attr("selected", "selected");
					   
					   initParamsByConditionId('<#if autoPlan?? && autoPlan.planCondition??>${autoPlan.planCondition}</#if>', true);
					   initVariablesByConditionId('<#if autoPlan?? && autoPlan.planCondition??>${autoPlan.planCondition}</#if>');
				   }
				   
			   }
			});
		
	}
	
	function changeFrequencyValue(frequencymode){
		
		
		$('#sendFrequency'+frequencymode).attr('checked','true');
		
		if(2==frequencymode){
						
			var frequencyValue = '';
			
			$('#daytd input[type="checkbox"]').each(function(){
				$(this).removeAttr('checked');
			});
			
			$('#weektd input[type="checkbox"]').each(function(){
				
				if($(this).is(':checked') && ''!=$(this).val()){
					frequencyValue +=$(this).val();
					frequencyValue +=','
				}
			  });
			
			if(frequencyValue.length>0){
				frequencyValue= frequencyValue.substring(0,frequencyValue.length-1);
			}
			
			$('#sendFrequencyValue').val(frequencyValue);
			
		}else if(3==frequencymode){
			
			var frequencyValue = '';
			$('#weektd input[type="checkbox"]').each(function(){
				$(this).removeAttr('checked');
			});
			
			$('#daytd input[type="checkbox"]').each(function(){
				
				if($(this).is(':checked') && ''!=$(this).val()){
					frequencyValue +=$(this).val();
					frequencyValue +=','
				}
			  });
			
			if(frequencyValue.length>0){
				frequencyValue= frequencyValue.substring(0,frequencyValue.length-1);
			}
			
			$('#sendFrequencyValue').val(frequencyValue);
			
		}else if(1==frequencymode){
			
			$('#daytd input[type="checkbox"]').each(function(){
				$(this).removeAttr('checked');
			});
			$('#weektd input[type="checkbox"]').each(function(){
				$(this).removeAttr('checked');
			});
			$('#sendFrequencyValue').val('');
		}
	}
	
	
	function uploadUserIds(){
		
		 $.ajaxFileUpload({
		        url:'${rc.contextPath}/autoPush/importUserIds.go',
		        secureuri:false,
		        fileElementId:'uploadCsv',
		        dataType: 'json',
		        success: function(data,status){
		        	
		        	if(typeof(data.result) != 'undefined'){
		                if(data.result=='success'){
		                	$('#userIds').val(data.userIds);
		                }else{
		                   alert("上传失败，文件格式错误");
		                }
		            }
		        	
		        },
		        error: function(data,status,e){
		        	
		        	$('#uploadCsv').change(function(){
		    			uploadUserIds();
		    		});
		        }
		    });
	}
	
	function validateDeviceType(){
		
		if(!$('#deviceTypeAndroid').is(':checked') && !$('#deviceTypeIos').is(':checked')){
			$('#deviceTypeInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;">');
			return false;
		}else{
			$('#deviceTypeInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg" style="width: 20px;"/>');
			return true;
		}
		
	}
	
	function validateUserIds(){
		
		if($('#pushScope2').is(':checked')){
			return validInput("userIds");
		}else{
			return true;
		}
	}
	
	function validateSendTime(){
		
		if($('#sendMode2').is(':checked')){
			return validInput("sendTimeStr");
		}else{
			return true;
		}
	}
	
	function validateUrl(){
		if($('#openType1').is(':checked')){
			return validInput("openUrl");
		}else{
			return true;
		}
	}
	function validateFrequency(){
		
		if(($('#sendFrequency2').is(':checked')||$('#sendFrequency3').is(':checked')) && $('#sendFrequencyValue').val()==''){
			$('#sendFrequencyInfo').html('<font color="red">请至少勾选一天</font>');
			return false;
		}
		$('#sendFrequencyInfo').html('');
		return true;
	}
	
	function changeEditMode(){
		
		if(window.top.$('#editMode1').is(':checked')){
			$('#editMode').val(1);		
		}else if(window.top.$('#editMode2').is(':checked')){
			$('#editMode').val(2);		
		}
		submitForm();
	}
	
	function isStartEndDate(startDate, endDate) {
	  if (startDate.length > 0 && endDate.length > 0) {
	    var startDateTemp = startDate.split(' ');
	    var endDateTemp = endDate.split(' ');
	    var arrStartDate = startDateTemp[0].split('-');
	    var arrEndDate = endDateTemp[0].split('-');
	    var arrStartTime = startDateTemp[1].split(':');
	    var arrEndTime = endDateTemp[1].split(':');
	    var allStartDate = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);
	    var allEndDate = new Date(arrEndDate[0], arrEndDate[1], arrEndDate[2], arrEndTime[0], arrEndTime[1], arrEndTime[2]);
	    if (allStartDate.getTime() > allEndDate.getTime()) {
	      return false;
	    }
	  }
	  return true;
	}
	
	function submitForm(){
		
		if($('#pushScope1').is(':checked')){
			if(confirm('确定发送给线上满足条件的全部用户吗？如果不确定，请联系您的上级')){
				$('#editForm').submit(); 
			}
		}else{
			if(confirm('确认提交？')){
				$('#editForm').submit(); 
			}
		}
	}
	
	function checkForm(){
		
		if($("#appId").val()==0){
			
			alert("请选择应用");
			return false;
		}
		
//		if($('#openType3').is(':checked')){
			var selectedOption = $('#pushStrategyId').find("option:selected");
			var type = selectedOption.attr('type');
			if(type==1){
				
				if($('#deviceTypeAndroid').is(':checked')){
					
					var channelCodeIndex = getIndexByStrategyParamName('channelCode');
					if(channelCodeIndex==''){
						alert("安卓参数channelCode不能为空，请填写");
						return false;
					}
					var channelCodeInput = $('#dd_push_value'+channelCodeIndex);
					if(jQuery.trim(channelCodeInput.val())==''){
						alert("安卓参数channelCode不能为空，请填写");
						return false;
					}else if(jQuery.trim(channelCodeInput.val())!='np' && jQuery.trim(channelCodeInput.val())!='vp'){
						alert("安卓参数channelCode非法，请填写np或者vp");
						return false;
					}
					
				}
				
				if($('#deviceTypeIos').is(':checked')){
					
					var channelCodeIndex = getIndexByStrategyIOSParamName('channelCode');
					if(channelCodeIndex==''){
						alert("IOS参数channelCode不能为空，请填写");
						return false;
					}
					var channelCodeInput = $('#dd_push_ios_value'+channelCodeIndex);
					if(jQuery.trim(channelCodeInput.val())==''){
						alert("IOS参数channelCode不能为空，请填写");
						return false;
					}else if(jQuery.trim(channelCodeInput.val())!='np' && jQuery.trim(channelCodeInput.val())!='vp'){
						alert("IOS参数channelCode非法，请填写np或者vp");
						return false;
					}
					
				}
				
				$('#messageType').val('0');
			}else{
				$('#messageType').val('1');
			}
//		}else{
//			$('#messageType').val('1');
//		}
		
		//听书全是透传
		if($("#appId").val()==4){
			$('#messageType').val('0');
		}
		
		var result = validInput("planName")&&validInput("planCondition")&&validInput("messageDescription")&&validInput("startDateStr")&&validInput("endDateStr")&&validateFrequency()&&validateDeviceType()&&validateUserIds()&&validateUrl();
		if(result == false){
			alert("表单有错误，请检查后重新提交");
			return result;
		}
		
		if(!isStartEndDate($('#startDateStr').val(),$('#endDateStr').val())){
			alert("结束时间不能早于开始时间，请您重新填写");
			return false;
		}
		
		var iosLengthValid = false;
		//若勾选了IOS，则验证IOS消息长度
		if($('#deviceTypeIos').is(':checked')){
			
			$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/manualPush/validateIosLength.go?"+$("#editForm").serialize(),
				   async: false,
				   cache: false,
				   data: {
					    "timestamp":new Date().getTime()
				   },
				   dataType:"json",
				   success: function(msg){
						if('success'!=msg.result){
							alert(msg.errorMessage);
						}else{
							iosLengthValid=true;
						}
				   }
			});
		}
		
		if($('#deviceTypeIos').is(':checked')&&!iosLengthValid){
			return;
		}
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/autoPush/getPlanStatus.go",
			   async: false,
			   cache: false,
			   data: {
					"planId":$('#cloudPushPlanId').val(),				
				    "timestamp":new Date().getTime()
			   },
			   dataType:"json",
			   success: function(msg){
				   
				   var forceClearDataAllowed = true;
				   var statusErrorMsg = '';
				   if(msg.result=='success'){
					   
					   if(msg.planStatus=='ok'){
						  //今天还没有准备数据，直接提交
						   submitForm(); 
						   return;
					   }else if(msg.planStatus.planJobStatus==1){
						   forceClearDataAllowed = false;
						   statusErrorMsg="此推送消息的数据线程正在准备数据，请等待数据准备完成再进行编辑";
					   }else if(msg.planStatus.planSendStatus==2){
						   forceClearDataAllowed = false;
						   statusErrorMsg="此推送消息的发送线程正在发送数据，不能强制清除，否则将会重新发送";
					   }else if(msg.planStatus.planSendStatus==3){
						   forceClearDataAllowed = false;
						   statusErrorMsg="此推送消息今天的内容已经发送完毕，不能强制清除，否则将会重新发送";
					   }
				   }   
				   
				   var htmlContent = '<div><font color="red">'+statusErrorMsg+'</font><br/> <input type="radio" value="1" name="editModeCode" checked id="editMode1" /><label for="editMode1">仅修改推送内容，不影响今天已经准备好的数据(自动推送会在下个发送周期生效)</label> <br />';
				   
				   htmlContent+='<input type="radio" value="2" '+(forceClearDataAllowed?'':' disabled="true" ')+' name="editModeCode" id="editMode2" /><label for="editMode2">强制清除今天已经准备好的数据并修改推送内容</label> <br />';
				   htmlContent+='  <div style="text-align:center"><button onclick="frames[\'rightIframe\'].changeEditMode()">提交</button></div> <br />';
				   htmlContent+='</div>';
				   
				   $.dialog({title:'设置状态',top:'10%',content:htmlContent,
				   		icon:'succeed',width:500,height:100,fixed:false,lock:true
				    });				  
				   
			   }
			});
		
	}
</script>
</body>
</html>