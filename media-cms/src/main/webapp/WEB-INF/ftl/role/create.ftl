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
		
		var treeContainer = "roletree";
		var setting = {
			view: {
				selectedMulti: false,
				dblClickExpand: false
			},
			edit: {
				enable: false
			},
			check:{
				enable:true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
	
		function zTreeOnClick(event, treeId, treeNode) {
			getTree().checkNode(treeNode, !treeNode.checked, true, false);
		};
		
		$(document).ready(function(){
			
			var zNodes =[];
			
			$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/functionality/initTree.go?roleId=<#if roleForEdit??><#if roleForEdit.roleId??>${roleForEdit.roleId?c}</#if></#if>",
				   async: false,
				   cache: false,
				   data: "timestamp="+new Date().getTime(),
				   dataType:"json",
				   success: function(msg){
					   zNodes = msg;
				   }
				});
			
			$.fn.zTree.init($("#"+treeContainer), setting, zNodes);
			
			$('#saveRole').click(function(e){
				
				var ids = "";
				var checkedNodes = getTree().getCheckedNodes();
				checkedNodes.forEach(function(eachNode){
					ids+= eachNode.id +",";					
				});
				if(ids.length>0){
					ids = ids.substring(0,ids.length-1);
				}
				$("#ids").val(ids);
				
				$.ajax({
					   type: "POST",
					   url: "${rc.contextPath}/role/save<#if roleForEdit??>Edit</#if>.go",
					   async: false,
					   cache: false,
					   data: $('#myform').serialize()+"&timestamp="+new Date().getTime(),
					   dataType:"json",
					   success: function(msg){
						   
						   if( typeof msg.result != 'undefined' && msg.result == 'failure'){
							    alert(msg.errorMessage);
						   }else{
								alert("操作成功");	
								window.location.href="${rc.contextPath}/role/show.go?id="+msg.roleId;
						   }
					   }
					});
				
			});
			
		});
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>角色管理&gt;&gt;<#if roleForEdit??>编辑<#else>新建</#if>角色</h3>
				<div class="content_wrap">
					
					<br>
					<div>
						<form id="myform" action="${rc.contextPath}/role/save.go" method="post">
							角色名称：<input id="name" name="name" class="finput {required:true}" value="<#if roleForEdit??><#if roleForEdit.name??>${roleForEdit.name}</#if></#if>" maxlength="255"/>
							<input id="ids" type="hidden" name="ids" value="<#if ids??>${ids}</#if>" />
							<input id="roleId" name="roleId" type="hidden" value="<#if roleForEdit??><#if roleForEdit.roleId??>${roleForEdit.roleId?c}</#if></#if>" />
						</form>
					</div>
					<div class="content_wrap">
						<div class="zTreeDemoBackground left">
							<ul id="roletree" class="ztree"></ul>
						</div>
					</div>
					
					<#if (roleForEdit?? && userSessionInfo?? && userSessionInfo.f['15']??) || (!roleForEdit?? && userSessionInfo?? && userSessionInfo.f['13']??) >
					<button class="button1 blue" id="saveRole">提交</button> 
					</#if>
					<button class="button1 blue" onclick="window.location.href='${rc.contextPath}/role/list.go'">返回</button>
					
				</div>
		    </div>
		    </div>
		</div>
	</div>
  </body>
</html>
