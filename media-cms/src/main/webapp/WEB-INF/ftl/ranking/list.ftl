<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>榜单类型树</title>
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
div#rMenu ul li:hover{ background: #4f69a0;} 
	</style>
</head>   
  <body>
  <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->

			<div class="right">
				<div class="m-r">
  <TABLE border=0 height=100% align=left>
	<TR>
		<TD width="12%" align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:150 px; overflow:auto;"></ul>
		</TD>
		<TD width="87%" align=left valign=top>
		<IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO onload="this.height=this.contentWindow.document.body.scrollHeight" width=100%  height=100% SRC='/ranking/listinfo.go'>
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
	
	 <#if userSessionInfo?? && userSessionInfo.f['111']?? > 
		<li id="m_add" onclick="addNode();">增加节点</li>
	 </#if>
	 <#if userSessionInfo?? && userSessionInfo.f['112']?? > 
		<li id="m_del" onclick="removeTreeNode();">删除节点</li>
	 </#if>
	  <#if userSessionInfo?? && userSessionInfo.f['113']?? > 
		<li id="m_edit" onclick="editTreeNode();">修改节点</li>
	 </#if>
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
					//跟级节点,显示榜单信息
					demoIframe.attr("src","/ranking/listinfo.go");
					return true;
				}else if(treeNode.isParent){
					demoIframe.attr("src","/ranking/listinfo.go?path="+treeNode.path);
				}else {
						if(treeNode.categoryCode=='np_update'||treeNode.categoryCode=='vp_update'){
							//更新
							demoIframe.attr("src","/ranking/mediaupdate.go?path="+treeNode.path+"&listType="+treeNode.categoryCode+"&sexChannel="+treeNode.sexChannle);
						}else{
							demoIframe.attr("src","/ranking/rank.go?path="+treeNode.path+"&listType="+treeNode.categoryCode+"&orederDimension="+treeNode.orederDimension);
						}
					return true;
				}
			},
			onRightClick: OnRightClick
		}
	};


	function OnRightClick(event, treeId, treeNode) {
			if(treeNode) {
				zTree.selectNode(treeNode);
				showRMenu("node",event.clientX, event.clientY);
			}
	}
	
	function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			$("#m_del").show();
			$("#m_edit").show();
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
	}	
	
	function onBodyMouseDown(event){
			if(!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
	}
		
	var zNodes =[
					{id:0, pId:0, name:"榜单分类",open:true,file:"sub"},
					 <#if listCategory??>
					  <#assign i = 0>
		             <#if (listCategory?size>0)>
		             <#assign i = i+1>
		    			<#list listCategory as column>
		    			 {id:${column.categoryId?c},pId:${column.parentId?c},level:${i},name:"${column.categoryName}",
		    				 sexChannle:"${column.categoryCode?substring(0,2)}",
		    				 path:"<#if column.path??>${column.path}</#if>",
		    				 categoryCode:"<#if column.categoryCode??>${column.categoryCode}</#if>",
		    				 orederDimension:"${column.orederDimension}",file:"rank"}
		    			 <#if column_has_next >${","}</#if>
			      		</#list>
			      	</#if>
			      	</#if>
		];
	$(document).ready(function(){
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		demoIframe = $("#testIframe");
		demoIframe.bind("load", loadReady);
		zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.selectNode(zTree.getNodeByParam("id", 0));
		rMenu = $("#rMenu");
	});

	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
		htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
		maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
		h = demoIframe.height() >= maxH ? minH:maxH ;
		if (h < 530) h = 530;
		demoIframe.height(h);
	}
	function addNode(){
		var nodes = zTree.getSelectedNodes();
		if(nodes && nodes.length>0){
			var url = "/ranking/add.go?parentId="+nodes[0].id;
			if(nodes[0].categoryCode){
				url+="&path="+nodes[0].categoryCode;
			}
			demoIframe.attr("src",url);
			hideRMenu();
		}
	}
	function editTreeNode(){
			var nodes = zTree.getSelectedNodes();
			var url = "/ranking/edit.go?id="+nodes[0].id;
			demoIframe.attr("src",url);
			hideRMenu();
	}
	function removeTreeNode() {
			hideRMenu();
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length>0) {
				//alert(nodes[0].level);
				if(nodes[0].id==0){
					alert("根节点,不可以删除!");
					return false;
				}else if(nodes[0].isParent&&nodes[0].id !=0){
					alert( "要删除的节点是父节点,请先删除下边的子节点！");
					return false;
				}else{
					  var flag =  window.confirm("该操作不会删除榜单关联的销售主体,你确认删除吗?")
					 if(flag){
					   location = "/ranking/delete.go?categoryId="+nodes[0].id+"&parentId="+nodes[0].pId+"&categoryCode="+nodes[0].categoryCode;
					   }
					 return false;
				}
			}
		}
	function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
	
  </SCRIPT>

</html>
