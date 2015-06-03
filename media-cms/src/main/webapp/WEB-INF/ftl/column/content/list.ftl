<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>栏目列表</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body>
  <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				   <TABLE border=0  height=1050px align=left>
						<TR>
							<TD width="15%" align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
								<ul id="tree" class="ztree" style="width:150 px; overflow:auto;"></ul>
							</TD>
							<TD align=left valign=top><IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=no width=100%  SRC="content.go?columnId=0" height="1050px"></IFRAME></TD>
						</TR>
					</TABLE>
				</div>
			</div>
		</div>
	</div>
<#include "../../common/common-js.ftl">
</body>
	</script>
	<script type="text/javascript" src="${rc.contextPath}/script/jquery/jquery-1.7.js"></script>
	<link rel="stylesheet" href="${rc.contextPath}/script/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/common.js"></script>
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
					//是父节点
					demoIframe.attr("src",treeNode.file + ".go?columnCode="+treeNode.code+"&columnName="+treeNode.name);
					zTree.expandNode(treeNode);
					return false;
				} else {
					//demoIframe.attr("src",treeNode.file + ".html");
					var url = treeNode.file + ".go?columnCode="+treeNode.code+"&columnName="+treeNode.name;
					if(treeNode.categoryId){
						//分类
						url+="&categoryId="+treeNode.categoryId;
					}else{
						url+="&columnId="+treeNode.id;
					}
					if(treeNode.path){
						url+="&path="+treeNode.path;
					}
					demoIframe.attr("src",url);
					return true;
				}
			}
		}
	};
	 var zNodes =${treeHtml}
	

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
	}
  </SCRIPT>
</html>
