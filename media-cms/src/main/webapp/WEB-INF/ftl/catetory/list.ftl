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
		function chgColr(id,type){
			if(type=='over'){
				$('#'+id).css('background-color','green');
			}else{
				$('#'+id).css('background-color','#DFDFDF');
			}
		}
	</script>
</head>   
  <body>
  <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->

			<div class="right">
				<div class="m-r">
  <TABLE border=0 height=600px align=left>
	<TR>
		<TD width="12%" align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:150 px; overflow:auto;"></ul>
		</TD>
		<TD width="88%" align=left valign=top>
			<IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 width="100%"  height="990px" SRC="/catetory/list.go?parentId="+<#if cateId??>${cateId}</#if>>
		</IFRAME></TD>
	</TR>
</TABLE>
</div>
</div>
</div>
</div>
<#include "../common/common-js.ftl">
<div id="rMenu">
	<ul>
		<li id="m_add" onclick="addNode();" onmouseover="chgColr('m_add','over');"
		onmouseout="chgColr('m_add','out');">添加子节点</li>
		<li id="m_del" onclick="removeTreeNode();"  onmouseover="chgColr('m_del','over');"
		onmouseout="chgColr('m_del','out');">删除</li>
		<li id="m_check" onclick="checkTreeNode();"  onmouseover="chgColr('m_check','over');"
		onmouseout="chgColr('m_check','out');">修改</li>
	</ul>
</div>
<div id="rootMenu" style="display:none;">
	<ul>
		<li onclick="addNode();">增加节点</li>
	</ul>
</div>
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
	var zTree,rMenu;
	var demoIframe;
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
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
				var zTree = $.fn.zTree.getZTreeObj("tree");
				if (treeNode.isParent && treeNode.id !=0) {
					demoIframe.attr("src","/catetory/list.go?parentId="+treeNode.id);
					zTree.expandNode(treeNode);
					return false;
				} else {
					//demoIframe.attr("src",treeNode.file + ".html");
					demoIframe.attr("src","/catetory/list.go?id="+treeNode.id);
					return true;
				}
			},
			onRightClick: OnRightClick
		}
	};


	function OnRightClick(event, treeId, treeNode) {
			if(treeNode) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY,treeNode);
			}
	}
	
	function showRMenu(type, x, y,treeNode) {
				$("#rMenu ul").show();
			
				$("#m_del").show();
				$("#m_check").show();
				$("#m_unCheck").show();
			
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
	}	
	
	function onBodyMouseDown(event){
			if(!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
	}
		
	var zNodes = ${data};
	
	$(document).ready(function(){
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		demoIframe = $("#testIframe");
		demoIframe.bind("load", loadReady);
		zTree = $.fn.zTree.getZTreeObj("tree");
		
		<#if cateId??>
			var node = zTree.getNodeByParam("id",${cateId});
			var arr = [];
			arr.push(node);
			var parentNode = node.getParentNode();
			zTree.selectNode(node);
		</#if>
		
		rMenu = $("#rMenu");
	});

	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
		htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
		maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH);
	}
	function addNode(){
		var nodes = zTree.getSelectedNodes();
		if(nodes && nodes.length>0){
			demoIframe.attr("src","/catetory/toAdd.go?parentId="+nodes[0].id);
		}
		hideRMenu();
	}
	function checkTreeNode(){
		var nodes = zTree.getSelectedNodes();
		if(nodes[0].id == 0){
			alert("根节点为虚拟节点，不允许修改！");
			return;
		}
		if(nodes && nodes.length>0){
			demoIframe.attr("src","/catetory/toEdit.go?id="+nodes[0].id);
		}
		hideRMenu();
	}
	function removeTreeNode() {
			hideRMenu();
			var nodes = zTree.getSelectedNodes();
			if(nodes[0].id == 0){
				alert("根节点为虚拟节点，不允许删除！");
				return;
			}
			if (nodes && nodes.length>0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					var msg = "要删除的节点是父节点,请先删除下边的子节点！";
					alert(msg);
					return;
				}else{
					$.post("/catetory/getMediaCountById.go", {id:nodes[0].id},
			   			function(data){
			     			if(data.count == 0){
			    				location = "/catetory/del.go?id="+nodes[0].id;
			     			}else{
			    	 			alert("当前分类下有书，不能删除！");
			     			}
			   		}, "json");
				}
			}
		}
	function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
	function reName(id,name){
		  var zTree =  $.fn.zTree.getZTreeObj("tree"),
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				alert("请先选择一个节点");
			}
		 	 nodes[0].name = name;
		 	zTree.updateNode(nodes[0]);
	}
	   </script>
  </SCRIPT>

</html>
