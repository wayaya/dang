<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<#include "../common/common-js.ftl">
	<link rel="stylesheet" href="${rc.contextPath}/style/functionality/functionality.css" type="text/css">
	<link rel="stylesheet" href="${rc.contextPath}/script/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/common.js"></script>
	<script type="text/javascript">
		
		var treeContainer = "showRoleTree";
		var setting = {
			view: {
				selectedMulti: false,
				dblClickExpand: true
			},
			edit: {
				enable: false
			},
			check:{
				enable:false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				
			}
		};
		
		$(document).ready(function(){
			
			var zNodes =[];
			
			$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/functionality/showRoleTree.go?roleId=<#if role??><#if role.roleId??>${role.roleId?c}</#if></#if>",
				   async: false,
				   cache: false,
				   data: "timestamp="+new Date().getTime(),
				   dataType:"json",
				   success: function(msg){
					   zNodes = msg;
				   }
				});
			
			$.fn.zTree.init($("#"+treeContainer), setting, zNodes);
			
			$("#editButton, #editButton1").click(function(e){
				window.location.href="${rc.contextPath}/role/create.go?roleId=<#if role??><#if role.roleId??>${role.roleId?c}</#if></#if>";
			});
		});
		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
			<div class="m-r">
					<h3>角色管理&gt;&gt;角色详情</h3>
				<div class="content_wrap">
					<br>
					<#if userSessionInfo?? && userSessionInfo.f['6']?? >
					<button class="button1 blue" id="editButton1">编辑角色</button>
					</#if>
					<button class="button1 blue" onclick="window.location.href='${rc.contextPath}/role/list.go'">返回列表</button>
					<div>
						<table class="formtable2">
						<tr>	
							<td class="field" style="width:150px">角色名称:</td>	
							<td><#if role??><#if role.name??>${role.name }</#if></#if></td>
						</tr>
						<tr>	
							<td class="field">角色创建人ID:</td>	
							<td><#if role??><#if role.creator??>${role.creator }</#if></#if></td>
						</tr>
						<tr>	
							<td class="field">角色创建时间:</td>	
							<td><#if role??><#if role.creationDate??>${role.creationDate?string("yyyy-MM-dd HH:mm:ss") }</#if></#if></td>
						</tr>
						<#if role??>
							<#if role.modifier??>
								<tr>	
									<td class="field">上次修改人ID:</td>	
									<td>${role.modifier }</td>
								</tr>
								<tr>	
									<td class="field">上次修改时间:</td>	
									<td><#if role.lastChangedDate??>${role.lastChangedDate?string("yyyy-MM-dd HH:mm:ss") }</#if></td>
								</tr>
							</#if>
						</#if>
						
						</table>
						
					</div>
					<ul><li>权限列表：</li></ul>
					<div class="content_wrap">
						<div class="zTreeDemoBackground left">
							<ul id="showRoleTree" class="ztree"></ul>
						</div>
					</div>
					<#if userSessionInfo?? && userSessionInfo.f['6']?? >
					<button class="button1 blue" id="editButton">编辑角色</button>
					</#if>
					<button class="button1 blue" onclick="window.location.href='${rc.contextPath}/role/list.go'">返回列表</button>
				</div>
		    </div>
		    </div>
		</div>
	</div>
  </body>
</html>
