<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>栏目列表</title>
	<#include "../common/common-css.ftl">
	<style type="text/css">
div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
div#rMenu ul li{
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}
	</style>
	<script>
	var zTree;
		function setUp(cateIds,cateNames){
			var ids = "";
			var names = "";
				var checkedNodes = zTree.getCheckedNodes();
				
				for(var i =0;i<checkedNodes.length;i++){
					ids+= checkedNodes[i].id +",";	
					names+=checkedNodes[i].name+",";
				}
				
			if(ids.length>0){
				ids=ids.substr(0,ids.length-1);
			}
			if(names.length>0){
				names=names.substr(0,names.length-1);
			}
			cateIds.value=ids;
			cateNames.value=names;
		}
		
		function getSize(){
			var checkedNodes = zTree.getCheckedNodes();
			return checkedNodes.length;
		}
	</script>
</head>   
  <body>
  <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
  <TABLE border=0 height=300px align=left>
	<TR height="200px">
		<TD width="15%" align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:150 px; heigth:180px;"></ul>
		</TD>
	</TR>
</TABLE>
</div>
</div>
</div>
</div>
<#include "../common/common-js.ftl">
</body>
	</script>
	<script type="text/javascript" src="${rc.contextPath}/script/jquery/jquery-1.7.js"></script>
	<link rel="stylesheet" href="${rc.contextPath}/script/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/common.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/funcTree.js"></script>
	<SCRIPT type="text/javascript" >
	
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		check: {
				enable: true
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				
			}
		}
	};

		
	var zNodes = ${data};
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("tree");
		
	
  </SCRIPT>

</html>
