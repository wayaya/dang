<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<script type="text/javascript">
		var condition ="";
		$(function(){
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c}-1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/epubImport/list.go?page=__id__'+condition)
		    });
		});
		$(document).ready(function(){
			$('#addBtn').click(function(){
				$.dialog({
					title:'excel批量导入',
					content:"url:/epubImport/addImportInfo.go",
			   		icon:'succeed',
			   		width:500,
			   		height:150,
			   		fixed:false,
			   		lock:true, 
			   		cancel:function(){searchList();}
			    });
			});
	   	
		});
	   	function searchList(){
	   		$('#search_list_form').submit();
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>入库任务管理&gt;&gt;入库任务管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/epubImport/list.go" method="post" id="search_list_form">
			      		 
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">标题：<input type="text" name="title" id="title" value="<#if epubImport??><#if epubImport.title??>${epubImport.title}</#if></#if>"></td>
								<td class="right_table_tr_td">ProductId：<input type="text" name="productId" id="productId" value="<#if epubImport??><#if epubImport.productId??>${epubImport.productId?c}</#if></#if>"></td>
							 	<td class="right_table_tr_td">UID：<input type="text" name="uid" id="uid" value="<#if epubImport??><#if epubImport.uid??>${epubImport.uid}</#if></#if>"></td>
							 	<td class="right_table_tr_td">状态：
							 		<select name="status">
							 			<option value="">全部</option>
							 			<option value="0" <#if epubImport??><#if epubImport.status??><#if (epubImport.status?c == "0")>selected = "selected" </#if></#if></#if>>未执行</option>
							 			<option value="1"  <#if epubImport??><#if epubImport.status??><#if (epubImport.status?c == "1")>selected = "selected" </#if></#if></#if>>执行中</option>
							 			<option value="2"  <#if epubImport??><#if epubImport.status??><#if (epubImport.status?c == "2")>selected = "selected" </#if></#if></#if>>已完成</option>
							 			<option value="-1"  <#if epubImport??><#if epubImport.status??><#if (epubImport.status?c == "-1")>selected = "selected" </#if></#if></#if>>执行失败</option>
							 		</select>
							 	</td>
								<td class="right_table_tr_td"><button  onclick="return searchList()">查询</button>
								<#if userSessionInfo?? && userSessionInfo.f['190']?? >
									<button type="button" id="addBtn" >导入Excel</button>
								</#if>
								<td >&nbsp;</td>
							</tr>
						
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		               <!--     <th style="width:5%; height:28px;" ><input type="checkbox" id="chkAll" name="chkAll">全选</th> -->
		               		<th style="width:5%">id</th>
		                    <th style="width:10%">标题</th>
		                    <th style="width:5%">EbookId</th>
		                    <th style="width:5%">ProductId</th>
		                    <th style="width:10%">UID</th>
		                    <th style="width:10%">创建日期</th>
		                    <th style="width:10%">导入日期</th>
		                    <th style="width:5%">状态</th>
		                    <th style="width:10%">错误信息</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as epubImport>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td><#if epubImport.id??>${epubImport.id?c}</#if></td>
					    			<td><#if epubImport.title??>${epubImport.title}</#if></td>
					    			<td><#if epubImport.ebookId??>${epubImport.ebookId?c}</#if></td>
					    			<td><#if epubImport.productId??>${epubImport.productId?c}</#if></td>
						      		<td><#if epubImport.uid??>${epubImport.uid}</#if></td>
						      		<td><#if epubImport.createTime??>${epubImport.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if epubImport.importTime??>${epubImport.importTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if epubImport.status??><#if (epubImport.status?c =="0")>未执行 
						      		<#elseif (epubImport.status?c =="1")>执行中
						      		<#elseif (epubImport.status?c =="2")>已完成
						      		<#else>执行失败</#if>
						      		</#if></td>
						      		<td>
						      		<#if epubImport.error??>
						      		<#if epubImport.error?string?length gt 15>
						      		${epubImport.error?string?substring(0,15)}
						      		<#else>
						      		${epubImport.error?string}
						      		</#if>
						      		</#if></td>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
			    <div class="pagination rightPager"></div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
<script type="text/javascript">
</html>
