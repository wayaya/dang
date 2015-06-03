<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>榜单分类内容列表</title>
	 <script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>榜单管理&gt;&gt;榜单排名<font color='red'><#if RequestParameters.listType??>[${RequestParameters["listType"]}]</#if></font></h3>
				<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/ranking/categoryranking.go" method="post" id="query" name="query" onsubmit="return checkForm();">
		      			<input type="hidden" id ="listCategoryPath" name="listCategoryPath" value='<#if RequestParameters.listCategoryPath??>${RequestParameters["listCategoryPath"]}</#if>'>
		      			<input type="hidden" id ="mediaPaths" name="mediaPaths" value='<#if RequestParameters.mediaPaths??>${RequestParameters["mediaPaths"]}</#if>'>
			      		 <table >
		        			<tr>
								<td>
								作品Id：<input type="text" name="mediaId" id="mediaId" value='<#if RequestParameters.mediaId??>${RequestParameters["mediaId"]}</#if>'>
								销售主体Id：<input type="text" name="saleId" id="saleId" value='<#if RequestParameters.saleId??>${RequestParameters["saleId"]}</#if>'>
								作品名：<input type="text" name="mediaName" id="mediaName" value='<#if RequestParameters.mediaName??>${RequestParameters["mediaName"]}</#if>'>
								<#if listCategory??> 
								<#if (listCategory?size>0)>
								榜单分类：
								<select id="listType" name="listType">
									<option value="">请选择...</option>
									<#list listCategory as lc>
										<option value="${lc.categoryCode}" <#if listType??><#if "${listType}"=="${lc.categoryCode}">selected="selected"</#if></#if> > ${lc.categoryName}</option>
									</#list>
								</select>
								</#if>
								</#if>
								<button  type="submit" >查询</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				     <form id="listform" name="listform" method="post">
				    <table class="table2">
		            	<tr>
		                    <th>序号<input type="checkbox" id='chkAll' name='chkAll'></th>
		                    <th>作品ID</th>
		                    <th>销售主体ID</th>
		                    <th>作品名</th>
		                    <th>作家笔名</th>
		                    <th>榜单类型</th>
		                    <th>指定排名</th>
		                    <th>操作者</th>
		                    <th>操作时间</th>
		                    <th>上下架</th>
		                    <th>状态</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	 <#if pageFinder??>
	              	 	 <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as column>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				    
					    			<td><input type="hidden"  id="listIds" name="listIds" value="${column.listId?c}" title="不管是否选中，都提交"><input type="checkbox"  id="listId" name="listId" value="${column.listId?c}">${i}</td>
						      		<td><#if column.mediaId??>${column.mediaId?c}</#if></td>
						      		<td><#if column.saleId??>${column.saleId?c}</#if></td>
						      		<td><#if column.mediaName??>${column.mediaName}</#if></td>
						      		<td><#if column.penName??>${column.penName}</#if></td>
						      		<td><#if column.listType??>${column.listType}</#if></td>
						      		<td>${column.appointOrder?c}</td>
						      		<td><#if column.operator??>${column.operator}</#if></td>
						      		<td><#if column.operateTime??>${column.operateTime}</#if></td>
						      		<td><#if column.shelfStatus??>
									<#if '${column.shelfStatus}'=='${on_shelf}'>上架 <#else>下架</#if></#if></td>
						      		<td><#if column.status??>
						      		<#if '${column.status}'=='${normal}'>正常显示
						      			<#elseif '${column.status}'=='${force_invalid?c}'>强制无效
						      			<#elseif '${column.status}'=='${force_valid?c}'>强制推荐
						      			<#else>没有状态</#if> 
						      		</#if></td>
						      	</tr>
				      		</#list>
				      	</#if>
				      	</#if>
		            </table>
		            </form>
		            </div>
			    </div>
			   <div class="pagination rightPager"></div>
			   <div class="leftPager">总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
		    </div>
		</div>
	</div>
  </body>
  	<#include "../common/common-js.ftl">
  	<script type="text/javascript">
  	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize},
			current_page: ${pageFinder.pageNo - 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/ranking/categoryranking.go?pageIndex=__id__&'+$('#query').serialize())
	    });
   	});
  	</script>
 
	</html>
