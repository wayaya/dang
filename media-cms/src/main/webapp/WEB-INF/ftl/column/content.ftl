<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>栏目内容管理</title>
	<#include "../common/common-css.ftl">
	<#include "../common/common-js.ftl">
	<link rel="stylesheet" href="${rc.contextPath}/script/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/common.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/funcTree.js"></script>
	  <style>
	body {
	background-color: white;
	margin:0; padding:0;
	text-align: center;
	}
	div, p, table, th, td {
		list-style:none;
		margin:0; padding:0;
		color:#333; font-size:12px;
		font-family:dotum, Verdana, Arial, Helvetica, AppleGothic, sans-serif;
	}
	#testIframe {margin-left: 10px;}
  </style>
	 <SCRIPT type="text/javascript" >
	var zTree;
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
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					return false;
				} else {
					demoIframe.attr("src",treeNode.file + ".html");
					return true;
				}
			}
		}
	};

	var zNodes =[
	];

	$(document).ready(function(){
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		demoIframe = $("#testIframe");
		demoIframe.bind("load", loadReady);
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.selectNode(zTree.getNodeByParam("id", 101));

	});

	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
		htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
		maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
		h = demoIframe.height() >= maxH ? minH:maxH ;
		if (h < 530) h = 530;
		demoIframe.height(h);
	}

  </SCRIPT>
  </HEAD>

<BODY>
<TABLE border=0 height=600px align=left>
	<TR>
		<TD width=260px align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
		</TD>
		<TD width=770px align=left valign=top><IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=600px SRC="core/standardData.html"></IFRAME></TD>
	</TR>
</TABLE>

</BODY>
</HTML>

<TABLE border=0 height=600px align=left>
	<TR>
		<TD width=260px align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
		</TD>
		<TD width=770px align=left valign=top><IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=600px SRC="core/standardData.html"></IFRAME></TD>
	</TR>
</TABLE>
</BODY>
</HTML>