<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>图书列表</title>
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
</head>   
  <body>

		
		
  <TABLE border=0 height="100%" align=left>
	<TR>
		<TD width="12%" height="1050" align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:150 px; "></ul>
		</TD>
		<TD align=left valign=top height="100%">
		<IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0  width="100%"  height="1100px;" SRC="/media/list.go">
		</IFRAME></TD>
	</TR>
</TABLE>


<#include "../common/common-js.ftl">
<div id="rMenu">
	<ul>
		<li id="m_add" onclick="addNode();">增加节点</li>
		<li id="m_del" onclick="removeTreeNode();">删除节点</li>
		<li id="m_check" onclick="checkTreeNode();">修改</li>
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
				if(treeNode.id ==0){
					demoIframe.attr("src","/media/list.go");
					zTree.expandNode(treeNode);
					return false;
				}
				if (treeNode.isParent && treeNode.id !=0) {
					demoIframe.attr("src","/media/list.go?parentId="+treeNode.id);
					zTree.expandNode(treeNode);
					return false;
				} else {
					demoIframe.attr("src","/media/list.go?parentId="+treeNode.id);
					return true;
				}
			}
		}
	};

		
	var zNodes;
	
	
	$.post("/catetory/getCatetory.go", null,
			   function(data){
			     var t = $("#tree");
			     zNodes = data;
		t = $.fn.zTree.init(t, setting, data);
		demoIframe = $("#testIframe");
		demoIframe.bind("load", loadReady);
		zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.selectNode(zTree.getNodeByParam("id", 101));
			   }, "json");
			   

	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
		htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
		maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
		h = demoIframe.height() >= maxH ? minH:maxH ;
	}
	demoIframe = $("#testIframe");
	<#if msg??>
			alert('${msg}');
		</#if>
  </SCRIPT>

</html>
