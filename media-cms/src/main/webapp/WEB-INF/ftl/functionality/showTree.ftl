<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		
		var treeContainer = "functiontree";
		var setting = {
			view: {
				selectedMulti: false
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
				onClick: zTreeOnClick
			}
		};
	
		function zTreeOnClick(event, treeId, treeNode) {
		    $(".content_wrap > .right").load( "${rc.contextPath}/functionality/show.go", { id: treeNode.id }, function(){} );
		};
		
		function addRootFuc(){
			$(".content_wrap > .right").load("${rc.contextPath}/functionality/create.go", {parentId: '0'}, function(){});	
		}
		
		<#if userSessionInfo?? && userSessionInfo.f['9']?? >
			$(document).ready(function(){
				
				var zNodes =[];
				
				$.ajax({
					   type: "POST",
					   url: "${rc.contextPath}/functionality/initTree.go",
					   async: false,
					   cache: false,
					   data: "timestamp="+new Date().getTime(),
					   dataType:"json",
					   success: function(msg){
						   zNodes = msg;
					   }
					});
				
				$.fn.zTree.init($("#"+treeContainer), setting, zNodes);
				
				$('#addRoot').click(addRootFuc);
				
			});
		</#if>
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>权限管理</h3>
					<div class="content_wrap">
						<div class="zTreeDemoBackground left">
						  	<br />
							<#if userSessionInfo?? && userSessionInfo.f['5']?? >
							
							<ul><li><button id="addRoot" class="button1 blue">添加根权限</button></li></ul>
							</#if>
							<ul id="functiontree" class="ztree"></ul>
						</div>
						<div class="right">
						</div>
					</div>
				</div>
		    </div>
		</div>
	</div>
  </body>
</html>