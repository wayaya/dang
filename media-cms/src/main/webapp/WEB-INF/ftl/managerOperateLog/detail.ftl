<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">  
		$(function(){
			$("input,select").each(function(){
				$(this).attr("disabled","disabled");
			});	
			<#if errorMsg ?? && errorMsg != ''>
				alert(${errorMsg});
			</#if>		
		}); 				 
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>操作日志管理&gt;&gt;操作日志列表&gt;&gt;操作日志详情</h3>					
				    <div>
					     <table border="1" bordercolor="#a0c6e5" rules=none style="word-wrap:break-word;word-break:break-all;">
					     	<tr height="35" style="width:80%;">
					     		<td style="width:500px; text-align:left" colspan="4"><br/><br/></td>
					     	</tr>
		    				<tr height="35" style="width:80%;">
				    			<td style="width:100px; text-align:right">
				    				操作日志ID：
				    			</td>
				    			<td style="width:150px; text-align:left">
				    				<input type="text" name="managerOperateLogId" id="managerOperateLogId" value="<#if managerOperateLog?? && managerOperateLog.managerOperateLogId??>${managerOperateLog.managerOperateLogId?c}</#if>">
				    			</td>
				    			<td style="width:100px; text-align:right">
				    				操作对象类型：
				    			</td>
				    			<td style="width:150px; text-align:left">
				    				<select name = "operateTargetType" id="operateTargetType" style="width: 150px;" >
										<option value="">&nbsp;请选择</option>
										<#if operateTargetTypeArray ??>
											<#list operateTargetTypeArray as operateTargetType>
												<option value="${operateTargetType.getType()}" <#if managerOperateLog ?? && managerOperateLog.operateTargetType ?? && managerOperateLog.operateTargetType == operateTargetType.type>selected="selected"</#if>>${operateTargetType.getName()}</option>
											</#list>
										</#if>
									</select>
				    			</td>
					      	</tr>
					      	<tr height="35" style="width:80%;">
				    			<td style="width:100px; text-align:right">
				    				操作人：
				    			</td>
				    			<td style="width:150px; text-align:left">
				    				<input type="text" name="operator" id="operator" value="<#if managerOperateLog?? && managerOperateLog.operator??>${managerOperateLog.operator}</#if>">
				    			</td>
				    			<td style="width:100px; text-align:right">
				    				操作时间：
				    			</td>
				    			<td style="width:150px; text-align:left">
				    				<input type="text" name="custId" id="custId" value="<#if managerOperateLog?? && managerOperateLog.creationDate??>${managerOperateLog.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if>">
				    			</td>
					      	</tr>
					      	<tr height="35" style="width:80%;">
				    			<td style="width:100px; text-align:right">
				    				操作对象ID：
				    			</td>
				    			<td style="width:150px; text-align:left">
				    				<input type="text" name="operateTargetId" id="operateTargetId" value="<#if managerOperateLog?? && managerOperateLog.operateTargetId??>${managerOperateLog.operateTargetId?c}</#if>">
				    			</td>
				    			<td style="width:100px; text-align:right">
				    				操作结果：
				    			</td>
				    			<td style="width:150px; text-align:left">
				    				<select name = "operateResult" id="operateResult" style="width: 150px;" >
										<option value="">&nbsp;请选择</option>
										<option value="0" <#if managerOperateLog?? && managerOperateLog.operateResult ?? && managerOperateLog.operateResult == 0>selected="selected"</#if>>&nbsp;失败</option>
										<option value="1" <#if managerOperateLog?? && managerOperateLog.operateResult ?? && managerOperateLog.operateResult == 1>selected="selected"</#if>>&nbsp;成功</option>							
									</select>
				    			</td>
					      	</tr>
					      	<tr height="35" style="width:80%;">
				    			<td style="width:100px; text-align:right">操作描述：</td>		
				    			<td style="width:400px; text-align:left" colspan="3"><#if managerOperateLog?? && managerOperateLog.operateDescription??>${managerOperateLog.operateDescription}</#if></td>		    			
					      	</tr>					      					     					   
					      	<tr height="35" style="width:80%;"><td style="width:500px; text-align:left" colspan="4"><br/><br/></td></tr>
			            </table>
		            </div>		            
			    </div>			    
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>