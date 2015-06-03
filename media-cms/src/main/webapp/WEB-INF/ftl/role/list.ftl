<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		function deleteRole(trId, ids){
			
			$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/role/delete.go",
				   async: false,
				   cache: false,
				   data: {
						"ids":ids,				
					    "timestamp":new Date().getTime()
				   },
				   dataType:"json",
				   success: function(msg){
					   
					   if(msg.result == 'success'){
						   alert("删除成功");
							$('#'+trId).hide();						   
					   }else if(msg.result == 'failure'){
						   alert("删除失败,"+msg.errorMessage);
					   }
				   }
				});
			
		} 
		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>角色管理&gt;&gt;角色列表</h3>
					<#if cms_error_message??>
					<div class="mrdiv">
						<span class="errorMsg">${cms_error_message}</span>
					</div>
					</#if>
				    <div>
				    <br />
				    <#if userSessionInfo?? && userSessionInfo.f['12']?? >
				    <button class="button1 blue" onclick="javascript:window.location.href='/role/create.go'">新建角色</button>
				    </#if>
				    <br />
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >ID</th>
		                    <th style="width:15%">角色名称</th>
		                    <th style="width:10%">创建人ID</th>
		                    <th style="width:20%">创建时间</th>
		                    <th style="width:15%">修改人ID</th>
		                    <th style="width:20%">最后修改时间</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if roleList??>
			    			<#list roleList as roleInList>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if roleInList.roleId??>${roleInList.roleId?c}</#if></td>
						      		<td><#if roleInList.name??>${roleInList.name}</#if></td>
						      		<td><#if roleInList.creator??>${roleInList.creator}</#if></td>
						      		<td><#if roleInList.creationDate??>${roleInList.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if roleInList.modifier??>${roleInList.modifier}<#else>无</#if></td>
						      		<td><#if roleInList.lastChangedDate??>${roleInList.lastChangedDate?string("yyyy-MM-dd HH:mm:ss")}<#else>无</#if></td>
						      		<td>
						      			<#if userSessionInfo?? && userSessionInfo.f['38']?? >
						      			<a href="/role/show.go?id=<#if roleInList.roleId??>${roleInList.roleId?c}</#if>">显示角色详情</a>
						      			</#if>
						      			<#if userSessionInfo?? && userSessionInfo.f['6']?? >
					      				<a href="/role/edit.go?roleId=<#if roleInList.roleId??>${roleInList.roleId?c}</#if>">编辑角色</a>
					      				</#if>
					      				<#if userSessionInfo?? && userSessionInfo.f['37']?? >
					      				<a href="javascript:if(confirm('是否将此角色删除?')){ deleteRole('row_${i}','<#if roleInList.roleId??>${roleInList.roleId?c}</#if>')}" >删除角色</a>
					      				</#if>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
