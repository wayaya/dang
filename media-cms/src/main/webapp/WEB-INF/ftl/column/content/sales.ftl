<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
<meta charset="UTF-8">
<title>导入销售主体</title>
	<#include "../../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>栏目管理&gt;&gt;添加[<font color='red'><#if RequestParameters.columnName??>${RequestParameters["columnName"]}</#if></font>]栏目内容</h3>
				<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/column/sales/query.go" method="post" id="query" name="query" onsubmit="return checkForm();">
		      		 <input type="hidden" id="columnId" name="columnId" value='<#if columnId??>${columnId?c}<#else></#if>'>
		      		 <input type="hidden" id="columnCode" name="columnCode" value='<#if RequestParameters.columnCode??>${RequestParameters["columnCode"]}</#if>'>
		      		 <input type="hidden" id="saleIds" name="saleIds" value='<#if RequestParameters.saleIds??>${RequestParameters["saleIds"]}</#if>'>
		      		 <input type="hidden" id="columnName" name="columnName" value='<#if RequestParameters.columnName??>${RequestParameters["columnName"]}</#if>'>
		      		 <input type="hidden" id="path" name="path" value='<#if RequestParameters.path??>${RequestParameters["path"]}</#if>'>
			      	 <input type="hidden" id="mediaCategoryIds"  name="mediaCategoryIds" title="media分类Ids" value='<#if RequestParameters.mediaCategoryIds??>${RequestParameters["mediaCategoryIds"]}</#if>'>
			      		 <table >
		        			<tr>
								<td>作品Id：<input type="text" name="mediaId" id="mediaId"  value='<#if RequestParameters.mediaId??>${RequestParameters["mediaId"]}</#if>'></td>
								<td>销售主体Id：<input type="text" name="mediaSaleId" id="mediaSaleId"  value='<#if RequestParameters.mediaSaleId??>${RequestParameters["mediaSaleId"]}</#if>'>	</td>
								<td>作品名称：<input type="text"  name="mediaName" id="mediaName" value='<#if RequestParameters.mediaName??>${RequestParameters["mediaName"]}</#if>'></td>
							</tr>
								<tr>
								<td>笔  名  ：<input type="text"  name="authorPenname" id="authorPenname" value='<#if RequestParameters.authorPenname??>${RequestParameters["authorPenname"]}</#if>'></td>
								<!-- media分类,只能查询指定分类下面的书 -->
								
								<td>作 品 分 类：<input type="text"  id="mediaCategoryNames" name="mediaCategoryNames" readonly  style="width:120px;" onclick="showMenu();" value='<#if RequestParameters.mediaCategoryNames??>${RequestParameters["mediaCategoryNames"]}</#if>'/></td>
								<td><button  type="submit" >查询</button></td>
							</tr>
							 <tr>
			      		 	<td colspan="3" valign="top"> 批  量 ：<textarea cols="60" rows="1" id="saleNames" name="saleNames" readonly><#if RequestParameters.saleNames??>${RequestParameters["saleNames"]}</#if></textarea>
			      		 	<button type="button" id="batchImportBtn" onclick="javascript:batchImport()">批量导入</button>
			      		 	<button type="button"　id="batchClearBtn" name="batchClearBtn" onclick="javascript:clearBatch()">批量清空</button>
			      		 	</td>
			      		 </tr>
						</table>
				    </form>
				    </div>
				<table class="table2">
		            	<tr>
		                    <th><input type="checkbox" id="chkAll" name="chkAll">全选</th>
		                    <th >作品Id</th>
		                    <th >销售主体Id</th>
		                    <th >销售主体名称</th>
		                    <th >作者笔名</th>
		                     <th >完结状态</th>
		                    <th >VIP状态</th>
		                    <th >黑名单状态</th>
		                    <th >上下架状态</th>
	               	    </tr>
	               	     <#assign i = 0>
	              	 	 <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as mediaSale>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
						<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
						<td><input type="checkbox"  name='saleId' saleId="${mediaSale.sale_id?c}" saleName='${mediaSale.name}'  auditChkId="#isAudit${i}"></td>
			    					<td>${mediaSale.media_id?c}</td>
			    					<td>${mediaSale.sale_id?c}</td>
			    					<td>${mediaSale.name}</td>
			    					<td><#if mediaSale.author_penname??>${mediaSale.author_penname}</#if></td>
			    					<td><#if mediaSale.is_full??>
			    					<#if '${mediaSale.is_full}'=='1'>已完结
			    					<#else>未完结
			    					</#if></#if></td>
			    					
			    					<td><#if mediaSale.is_vip??>
			    					<#if '${mediaSale.is_vip}'=='1'>是
			    					<#else>否
			    					</#if></#if></td>
			    					
			    					<td><#if mediaSale.is_black??>
			    					<#if '${mediaSale.is_black}'=='1'>是
			    					<#else>否
			    					</#if></#if></td>
			    					
			    					<td><#if mediaSale.shelf_status??>
			    					<#if '${mediaSale.shelf_status}'=='1'>上架
			    					<#else>下架
			    					</#if></#if></td>
						      	</tr>
				      		</#list>
				      	</#if>
	               	     <tr>
		                    <td colspan="9" align="left">
		                    <button type="buttnon" id="importBtn">确定导入</button>
		                     <button type="buttnon" id="saveBtn" title="将选中销售主体放到比量导入中">加入待导入</button>&nbsp;
		                    <button type="buttnon" onclick="javascript:history.go(-1)">返回</button>
		                    </td>
	               	    </tr>
		            </table>
</form>
  <div class="pagination rightPager"></div>
   <div class="leftPager">总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;" title="media分类树">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>
	<#include "../../common/common-js.ftl">
	<link rel="stylesheet" href="${rc.contextPath}/script/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/common.js"></script>

<script type="text/javascript">
var setting = {
	check: {
		enable: true,
		chkboxType: {"Y":"", "N":""}
	},
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onCheck: onCheck
	}
};

var zNodes =${mediaCategoryTree}

function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	if(treeNode.isParent){
		//非叶子节点不可选中
		treeNode.checked =false;
		return ;
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true),
	mediaCategoryNames = "",mediaCategoryIds="";
	for (var i=0, l=nodes.length; i<l; i++) {
		mediaCategoryNames += nodes[i].name + ",";
		mediaCategoryIds += nodes[i].id + ",";
	}
	if (mediaCategoryNames.length > 0 ) {
		mediaCategoryNames = mediaCategoryNames.substring(0, mediaCategoryNames.length-1);
		mediaCategoryIds = mediaCategoryIds.substring(0, mediaCategoryIds.length-1);
	}
	var mediaCategoryNamesObj = $("#mediaCategoryNames");
	mediaCategoryNamesObj.attr("value", mediaCategoryNames);
	$("#mediaCategoryIds").attr("value", mediaCategoryIds);
}

function showMenu() {
	var cityObj = $("#mediaCategoryNames");
	var cityOffset = $("#mediaCategoryNames").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "mediaCategoryNames" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

$(document).ready(function(){
	 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	 $("#treeDemo").css("background","#E4EAF6").css({ opacity:0.9 });
});
</script>
<script type="text/javascript">
function checkForm(){
	var mediaId = $('#mediaId').val();
	if(isNaN(mediaId)){
		alert("作品ID只能是数字!");
		return false;
	}
	return true;
}
//批量导入选中的销售主体
function batchImport(){
	var saleIds = $('#saleIds').val();
	if(saleIds==''){
		alert("没有需要导入销售主体");
		return false;
	}
	
	$.ajax({
		   type: "POST",
		   url: "/column/content/import.go",
		   async: false,
		   cache: false,
		   data: {"columnCode":$('#columnCode').val(),"columnId":$('#columnId').val(),"saleIds":$('#saleIds').val(),"saleName":$('#saleNames').val()},
		   dataType:"text",
		   success: function(msg){
			   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
				   alert("导入失败");
			   }else{
				   alert("导入成功");
				   $('#saleIds').val("");
				   $('#saleNames').val("");
			   }
		   }
		});
}

function clearBatch(){
	  $('#saleIds').val("");
	  $('#saleNames').val("");
}

$(function(){
    $('.pagination').pagination(${pageFinder.rowCount?c}, {
		items_per_page: ${pageFinder.pageSize?c},
		current_page: ${pageFinder.pageNo - 1},
		prev_show_always:false,
		next_show_always:false,
		link_to: encodeURI('/column/sales/query.go?pageIndex=__id__&'+decodeURIComponent($('#query').serialize(),true))
    });
	});
$(document).ready(function(){
		$('#chkAll').click(function(){
			if(this.checked){
				 $('[name=saleId]:checkbox').attr("checked", true);
			}else{
				 $('[name=saleId]:checkbox').attr("checked", false);
			}
		});
		
		$('#saveBtn').click(function(){
			 var chkSaleIds =[];//销售主体编号
			 var chkSaleNames =[];//销售主体名称
			 var existSaleIds =  $('#saleIds').val();
			  $('input[name="saleId"]:checked').each(function(){    
				  var saleId = $(this).attr('saleId');
				  if(existSaleIds.indexOf(saleId)<0){
					  chkSaleIds.push($(this).attr('saleId'));   
					  chkSaleNames.push($(this).attr('saleName')); 
				  }else{
					  alert("此销售主体已存在!");
					  this.checked=false;
					  return ;
				  }
			  }); 
			  if(chkSaleIds.length==0){
				  alert("请选择需要导入的销售主体");
				  return false;
			  }else{
				  var saleIds =  $('#saleIds').val();
				  var saleNames =  $('#saleNames').val();
				  if(saleIds!=''){
					  $('#saleIds').val(saleIds+","+chkSaleIds.join(","));
					  $('#saleNames').val(saleNames+","+chkSaleNames.join(","));
				  }else{
					  $('#saleIds').val(chkSaleIds.join(","));
					  $('#saleNames').val(chkSaleNames.join(","));
				  }
				 //修改后需要重新设置查询条件,不然开始的那个查询条件是在网页加载后获取查询条件的,那时查询条件还不存在
				  $('.pagination').pagination(${pageFinder.rowCount?c}, {
						items_per_page: ${pageFinder.pageSize?c},
						current_page: ${pageFinder.pageNo - 1},
						prev_show_always:false,
						next_show_always:false,
						link_to: encodeURI('/column/sales/query.go?pageIndex=__id__&'+decodeURIComponent($('#query').serialize(),true))
				    });
			  }
			});
		
		$('#importBtn').click(function(){
			 var chkSaleIds =[];//销售主体编号
			 var chkSaleNames =[];//销售主体名称
			  $('input[name="saleId"]:checked').each(function(){    
				  chkSaleIds.push($(this).attr('saleId'));   
				  chkSaleNames.push($(this).attr('saleName')); 
			  }); 
			  if(chkSaleIds.length==0){
				  alert("请选择需要导入的销售主体");
				  return false;
			  }
			 
			 // alert("chkSaleIds="+chkSaleIds.join(",")+" : "+"chkSaleNames="+chkSaleNames.join(",")+" : "+"chkAuditAry="+chkAuditAry.join(","));
			$.ajax({
				   type: "POST",
				   url: "/column/content/import.go",
				   async: false,
				   cache: false,
				   data: {"columnCode":$('#columnCode').val(),"columnId":$('#columnId').val(),"saleIds":chkSaleIds.join(","),"saleName":chkSaleNames.join(",")},
				   dataType:"text",
				   success: function(msg){
					   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
						   alert("导入失败");
					   }else{
						   alert("导入成功");
						  $("#importBtn").attr("disabled", true);
					   }
				   }
				});
		});
});

</script>
</body>
</html>