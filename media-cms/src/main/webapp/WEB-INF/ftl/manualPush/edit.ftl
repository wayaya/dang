<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增敏感词</title>
<#include "../common/common-css.ftl">
<#include "../common/common-js.ftl">
<script type="text/javascript" src="/script/jquery/ajaxfileupload.js"></script>
</head>
<body>
 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>手动推送&gt;&gt;编辑</h3>
<div class="mrdiv" <#if !errorMessage??>style="display:none"</#if> >
			      		 <table>
		        			<tr>
								<td style="padding-left:50px;"><span id="message">
								<#if errorMessage??><font color="red">${errorMessage}</font></#if>
								</span></td>
							</tr>
						</table>
				    </div>
<form id="editForm" name="editForm" method="post" action="/manualPush/editSubmit.go" >
	<input type="hidden" id="cloudPushPlanId" name="cloudPushPlanId" value="<#if manualPlan?? && manualPlan.cloudPushPlanId??>${manualPlan.cloudPushPlanId}</#if>" />
	<input type="hidden" name="planType" value="1" />
	<input type="hidden" name="planStatus" value="<#if manualPlan?? && manualPlan.planStatus??>${manualPlan.planStatus}</#if>" />
	<input type="hidden" id="messageType" name="messageType" value="1" />
	<input type="hidden" id="editMode" name="editMode" value="1" />
	
	<table class="table2">
		<tr>
			<td>选择应用:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
			<select name="appId" id="appId">
				<option value="0">---请选择应用---</option>
				<option value="1" <#if manualPlan?? && manualPlan.appId?? && manualPlan.appId==1>selected</#if>>当当读书Android/iphone</option>
				<option value="2" <#if manualPlan?? && manualPlan.appId?? && manualPlan.appId==2>selected</#if>>当读小说Android</option>
				<option value="6" <#if manualPlan?? && manualPlan.appId?? && manualPlan.appId==6>selected</#if>>当读小说Iphone版</option>
				<option value="3" <#if manualPlan?? && manualPlan.appId?? && manualPlan.appId==3>selected</#if>>翻篇儿</option>
				<option value="4" <#if manualPlan?? && manualPlan.appId?? && manualPlan.appId==4>selected</#if>>悦耳听书</option>
				<option value="5" <#if manualPlan?? && manualPlan.appId?? && manualPlan.appId==5>selected</#if>>当当读书Ipad版</option>
			</select>
			</td>
		</tr>
		
		<tr>
		<td>消息标题:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
		<input type="input" id="messageTitle" name="messageTitle" maxlength="20" 
		value="<#if manualPlan?? && manualPlan.messageTitle??>${manualPlan.messageTitle}</#if>" style="width:300px" /> &nbsp;&nbsp; 限制20字，可选，标题空显示应用名称<span id="messageTitleInfo"></span></td>
		</tr>
		<tr>
		<td>消息内容<font color="red">*</font>:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
		<textarea id="messageDescription" maxlength="40" name="messageDescription" style="height:110px; width:300px"><#if manualPlan?? && manualPlan.messageDescription??>${manualPlan.messageDescription}</#if></textarea> 
		 &nbsp;&nbsp; 限制40字<span id="messageDescriptionInfo"></span></td>
		</tr>
		
		<tr>
		<td>设备范围<font color="red">*</font>:</td>
			<td style="text-align:left; padding:10px 20px 10px 20px;">
			<input type="checkbox" id="deviceTypeAndroid" name="deviceTypeAndroid" <#if (manualPlan?? && manualPlan.deviceTypeAndroid?? &&manualPlan.deviceTypeAndroid==true)|| !manualPlan??>checked</#if> /> <label for="deviceTypeAndroid">Android</label>  &nbsp;&nbsp;
		 	<input type="checkbox" id="deviceTypeIos" name="deviceTypeIos" <#if manualPlan?? && manualPlan.deviceTypeIos?? &&manualPlan.deviceTypeIos==true>checked</#if> /> <label for="deviceTypeIos">IOS</label>
		 	<span id="deviceTypeInfo"></span>
		 	</td>
		</tr>
		
		<tr>
		<td>用户范围:</td>
		<td style="text-align:left; padding:10px 20px 10px 20px;">
			<input type="radio" id="pushScope1" name="pushScope" value="1" <#if (manualPlan?? && manualPlan.pushScope?? &&manualPlan.pushScope==1)|| !manualPlan??>checked</#if> /><label for="pushScope1">所有用户</label> &nbsp;&nbsp; 
			<input type="radio" id="pushScope2" name="pushScope" value="2" <#if (manualPlan?? && manualPlan.pushScope?? &&manualPlan.pushScope==2) >checked</#if> /><label for="pushScope2">指定用户 </label>
			<div id="pushScope2Div" style="display:none">
				<br />
				<textarea id="userIds" name="userIds" style="height:110px; width:300px"><#if manualPlan?? && manualPlan.userIds?? >${manualPlan.userIds}</#if></textarea> 
				<input type="file" id="uploadCsv" name="uploadCsv" />
				<span id="userIdsInfo"></span>
			</div>
		</td>
		</tr>
		
		<tr>
		<td>发送时间:</td><td style="text-align:left; padding:10px 20px 10px 20px;">
			<input type="radio" id="sendMode1" name="sendMode" value="1" <#if (manualPlan?? && manualPlan.sendMode?? &&manualPlan.sendMode==1)|| !manualPlan??>checked</#if> /><label for="sendMode1">立即发送 </label>&nbsp;&nbsp; 
			<input type="radio" id="sendMode2" name="sendMode" value="2" <#if (manualPlan?? && manualPlan.sendMode?? &&manualPlan.sendMode==2)>checked</#if> /><label for="sendMode2">定时发送</label>
			<span id="sendTimeSpan" style="display:none"><input type="text" id="sendTimeStr" name="sendTimeStr" readonly="true" value="<#if (manualPlan?? && manualPlan.sendTime??)>${manualPlan.sendTime?string("yyyy-MM-dd HH:mm:ss")}</#if>"  /><span id="sendTimeInfo"></span></span>
		</td>
		</tr>
		
		<tr>
		<td>后续行为:</td>
		<td style="text-align:left; padding:10px 20px 10px 20px;">
		<!--
		<input type="radio" id="openType2"  onclick="controlParamModel()" name="openType" value="2" <#if (manualPlan?? && manualPlan.openType?? &&manualPlan.openType==2)|| !manualPlan??>checked</#if> /><label for="openType2">打开应用</label><br />
		<input type="radio" id="openType1"  onclick="controlParamModel()" name="openType" value="1" <#if (manualPlan?? && manualPlan.openType?? &&manualPlan.openType==1)>checked</#if>  /><label for="openType1">打开网页</label> 
		<input type="text" id="openUrl" name="openUrl" style="width:300px" value="<#if (manualPlan?? && manualPlan.openUrl??)>${manualPlan.openUrl}</#if>" /><span id="openUrlInfo"></span><br />
		<input type="radio" id="openType3"  onclick="controlParamModel()" name="openType" value="0" <#if (manualPlan?? && manualPlan.openType?? &&manualPlan.openType==0)>checked</#if> /><label for="openType3">传递参数</label><br />
		-->
		<input type="hidden" name="openType" value="0" />
		<table id="pushStrategyTb" style="width:600px;">
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
		<td colspan="2"><button type="button" onclick="checkForm()">确认</button>&nbsp;&nbsp;<button type="button"  onclick="javascript:window.location.href='/manualPush/list.go'">取消</button></td>
		</tr>
	</table>
</form>
</div>
</div>
</div>
</div>
<script type="text/javascript" >
	$(function(){
		$('#sendTimeStr').calendar({format:'yyyy-MM-dd HH:mm:ss'});
		
		$('#sendMode1').click(function(){
			$('#sendTimeSpan').hide();
		});
		$('#sendMode2').click(function(){
			$('#sendTimeSpan').show();
		});
		
		
		$('#pushScope1').click(function(){
			$('#pushScope2Div').hide();
		});
		$('#pushScope2').click(function(){
			$('#pushScope2Div').show();
		});
		
		$('#uploadCsv').change(function(){
			uploadUserIds();
		});
		
		if($('#sendMode2').is(':checked')){
			$('#sendTimeSpan').show();
		}else{
			$('#sendTimeSpan').hide();
		}
		
		if($('#pushScope2').is(':checked')){
			$('#pushScope2Div').show();
		}else{
			$('#pushScope2Div').hide();
		}
		
		$('#appId').change(initByAppId);
		initByAppId(true);
		
		$('#pushStrategyId').change(changeCustomContentByStrategyId);
		$('#deviceTypeAndroid').click(showOrHideCustomContent); 
		$('#deviceTypeIos').click(showOrHideCustomContent); 
		
		
		//controlParamModel();
		
	});
	
	function controlParamModel(){
		
		if($('#openType2').is(':checked') || $('#openType1').is(':checked')){
			
			$('#pushStrategyTb').hide();
			
		}else if($('#openType3').is(':checked')){
			
			$('#pushStrategyTb').show();
		}
	}
	
	function initByAppId(isInit){
		
		if(typeof isInit !='undefined'){
			initStrategiesByApp($('#appId').val(), isInit);
		}else{
			initStrategiesByApp($('#appId').val());
		}
		initDeviceTypeByAppId($('#appId').val());
	}
	
	function initDeviceTypeByAppId(appId){
		
		if(appId==5 || appId==6){
			$('#deviceTypeIos').attr('checked',true);
			$('#deviceTypeAndroid').removeAttr('checked');
			$('#deviceTypeAndroid').attr('disabled',true);
			showOrHideCustomContent();
		}else{
			$('#deviceTypeAndroid').removeAttr('disabled');
		}
		
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
					   $("#pushStrategyId option[value='<#if manualPlan?? && manualPlan.pushStrategyId??>${manualPlan.pushStrategyId}</#if>']").attr("selected", "selected");
					   changeCustomContentByStrategyId(isInit);
				   }
				   
			   }
			});
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
	
	function uploadUserIds(){
		
		 $.ajaxFileUpload({
		        url:'${rc.contextPath}/manualPush/importUserIds.go',
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
	
	function changeEditMode(){
		
		if(window.top.$('#editMode1').is(':checked')){
			$('#editMode').val(1);		
		}else if(window.top.$('#editMode2').is(':checked')){
			$('#editMode').val(2);		
		}
		submitForm();
	}
	
	function submitForm(){
		
		if($('#pushScope1').is(':checked')){
			if(confirm('确定发送给线上全部用户吗？如果不确定，请联系您的上级')){
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
		
		var result = validInput("messageDescription")&&validateDeviceType()&&validateUserIds()&&validateSendTime()&&validateUrl();
		if(result==false){
			alert("表单有错误，请检查后重新提交");
			return result;
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
		
		//发送同步消息给后台，获得此planId的真实status
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
				   if(msg.result=='failure'){
					   alert(msg.errorMessage);
					   return;
				   }
				   
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