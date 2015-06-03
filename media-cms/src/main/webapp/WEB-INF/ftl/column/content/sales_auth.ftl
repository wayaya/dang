<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		      		<form action="/column/sales/query.go" method="post" id="query" name="query">
		      		 <input type="hidden" id="columnCode" name="columnCode" value='<#if RequestParameters.columnCode??>${RequestParameters["columnCode"]}</#if>'>
		      		 <input type="hidden" id="columnId" name="columnId" value='<#if columnId??>${columnId?c}<#else>0</#if>'>
			      		 <table >
		        			<tr>
								<td>销售主体名称：<input type="text" name="name" id="name" >
								<td>作者：<input type="text" name="author_name" id="author_name" >
								<td>笔名：<input type="text" name="author_penname" id="author_penname" >
								<button  type="submit" >查询</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				<table class="table2">
		            	<tr>
		                    <th style="width:20%;" ><input type="checkbox" id="chkAll" name="chkAll">全选</th>
		                    <th style="width:20%;" ><input type="checkbox" id="auditAll" name="auditAll">审核(未选中直接导入)</th>
		                    <th style="width:20%">销售主体名称</th>
		                    <th style="width:20%">作者</th>
		                    <th style="width:20%">作者笔名</th>
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
			    					<td><input type="checkbox" id="isAudit${i}" name='isAudit'></td>
			    					<td>${mediaSale.name}</td>
			    					<td><#if mediaSale.author_name??>${mediaSale.author_name}</#if></td>
			    					<td>${mediaSale.author_penname}</td>
						      	</tr>
				      		</#list>
				      	</#if>
	               	     <tr>
		                    <td colspan="5" align="left"><button type="buttnon" id="importBtn">确定导入</button></td>
	               	    </tr>
		            </table>
</form>
  <div class="pagination rightPager"></div>
</div>
<script type="text/javascript">
$(function(){
    $('.pagination').pagination(${pageFinder.rowCount?c}, {
		items_per_page: ${pageFinder.pageSize?c},
		current_page: ${pageFinder.pageNo - 1},
		prev_show_always:false,
		next_show_always:false,
		link_to: encodeURI('/column/sales.go?pageIndex=__id__&columnCode='+$('#columnCode').val())
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
		$('#auditAll').click(function(){
			if(this.checked){
				 $('[name=isAudit]:checkbox').attr("checked", true);
			}else{
				 $('[name=isAudit]:checkbox').attr("checked", false);
			}
		});
		$('#importBtn').click(function(){
			 var chkSaleIds =[];//销售主体编号
			 var chkSaleNames =[];//销售主体名称
			 var chkAuditAry =[];//是否需要审核
			  $('input[name="saleId"]:checked').each(function(){    
				  chkSaleIds.push($(this).attr('saleId'));   
				  chkSaleNames.push($(this).attr('saleName')); 
				  var chkAudit = $(this).attr('auditChkId');
				  //选中销售主体是否需要审核
				  var chk =$(chkAudit);
				//  alert(chkAudit+","+ chk+","+chk.attr("checked"));
				   if(chk.attr("checked")=="checked"){
					   chkAuditAry.push(1);   
					}else{
						chkAuditAry.push(0); 
				   }
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
				   data: {"columnCode":$('#columnCode').val(),"columnId":$('#columnId').val(),"saleIds":chkSaleIds.join(","),"saleName":chkSaleNames.join(","),"isAudits":chkAuditAry.join(",")},
				   dataType:"text",
				   success: function(msg){
					   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
						   alert("导入失败");
					   }else{
						   alert("导入成功");
					   }
				   }
				});
		});
});

</script>
</body>
</html>