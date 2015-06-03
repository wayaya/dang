<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	   	function activityTypeAddForm(){
	   		var flag = true;
	   		var reg1 =  /^\d+$/;
	   		var reg2 =  /^[A-Za-z0-9-]+$/;
	   		var activityTypeIdInfo = $('#activityTypeId').val();
	   		if(activityTypeIdInfo == null || activityTypeIdInfo == "" || !reg1.test(activityTypeIdInfo)){
	   			$('#activityTypeIdInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#activityTypeIdInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var activityTypeNameInfo = $('#activityTypeName').val();
	   		if(activityTypeNameInfo == null || activityTypeNameInfo == ""){
	   			$('#activityTypeNameInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#activityTypeNameInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var activityTypeCodeInfo = $('#activityTypeCode').val();
	   		if(activityTypeCodeInfo == null || activityTypeCodeInfo == "" || !reg2.test(activityTypeCodeInfo)){
	   			$('#activityTypeCodeInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#activityTypeCodeInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var isPromptlyToAccountInfo = $('#isPromptlyToAccount').val();
	   		if(isPromptlyToAccountInfo == null || isPromptlyToAccountInfo == ""){
	   			$('#isPromptlyToAccountInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#isPromptlyToAccountInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var statusInfo = $('#status').val();
	   		if(statusInfo == null || statusInfo == ""){
	   			$('#statusInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#statusInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3><a href="/activity/info/list.go">促销类型管理</a>&nbsp;&gt;&gt;&nbsp;添加促销类型</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
										<#if successFlag??>
					    					<#if successFlag == 0>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/activity/type/add.go" style="height: 20px; font-size: 20px;">继续添加</a>&nbsp;&nbsp;<a href="/activity/type/list.go" style="height: 20px; font-size: 20px;"><img src='/images/show_list.jpg' style='padding-bottom: 0px;'></a></#if>
					    				</#if>
									</td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div>
					    <form action="/activity/type/goadd.go" method="post" id="activity_type_add_form">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
						     	<tr>
					    			<td class="tdright" style="width:15%"><font color="red">(数字组合*)&nbsp;</font>促销类型ID：</td>
					    			<td class="tdleft" style="width:10%">
					    				<input type="text" name="activityTypeId" id="activityTypeId" value="<#if activityType??>${activityType.activityTypeId?c}</#if>" onblur="validInputMustNumber('activityTypeId')">
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="activityTypeIdInfo"></td>
					    			<td class="tdright" style="width:15%">促销类型名称：</td>
					    			<td class="tdleft" style="width:10%">
					    				<input type="text" name="activityTypeName" id="activityTypeName" value="<#if activityType??>${activityType.activityTypeName}</#if>" onblur="validInput('activityTypeName')">
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="activityTypeNameInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width:15%"><font color="red">(数字字母-组合*)&nbsp;</font>促销类型编号：</td>
					    			<td class="tdleft" style="width:10%">
					    				<input type="text" name="activityTypeCode" id="activityTypeCode" value="<#if activityType??>${activityType.activityTypeCode}</#if>"  onblur="validInputMustNumberOrZimu('activityTypeCode')">
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="activityTypeCodeInfo"></td>
					    			<td class="tdright" style="width:15%">是否实时到账：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="isPromptlyToAccount" name="isPromptlyToAccount" onblur="validInput('isPromptlyToAccount')">
					    					<option value="1" <#if activityType??><#if activityType.isPromptlyToAccount??><#if activityType.isPromptlyToAccount==1>selected="selected"</#if></#if></#if>>非实时到账</option>
					    					<option value="0" <#if activityType??><#if activityType.isPromptlyToAccount??><#if activityType.isPromptlyToAccount==0>selected="selected"</#if></#if></#if>>实时到账</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="isPromptlyToAccountInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width:15%">是否有效：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="status" name="status" onblur="validInput('status')">
					    					<option value="1" <#if activityType??><#if activityType.status??><#if activityType.status==1>selected="selected"</#if></#if></#if>>有效</option>
					    					<option value="0" <#if activityType??><#if activityType.status??><#if activityType.status==0>selected="selected"</#if></#if></#if>>无效</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="statusInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td colspan="9" style="text-align:center;">
					    				<#if successFlag??>
					    					<#if successFlag == 1><image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" /></#if>
					    				<#else>
											<#if userSessionInfo?? && userSessionInfo.f['233']?? >
					    					<image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" />
											</#if>
					    				</#if>
					    				&nbsp;&nbsp;<image src="/images/back.png" onclick="javascript:history.go(-1)" />
					    			</td>
						      	</tr>
				            </table>
			            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../../common/common-js.ftl">
  </body>
</html>
