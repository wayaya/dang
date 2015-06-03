<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>新品榜单</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>榜单管理&gt;&gt;新品榜单<font color='red'><#if RequestParameters.listType??>[${RequestParameters["listType"]}]</#if></font></h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>作品ID</th>
		                    <th>作品名</th>
		                    <th>作家笔名</th>
		                    <th>作品添加时间</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	 <#if pageFinder??>
			    			<#list pageFinder.data as bean>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
						      		<td><#if bean.mediaId??>${bean.mediaId?c}</#if></td>
						      		<td><#if bean.mediaTitle??>${bean.mediaTitle}</#if></td>
						      		<td><#if bean.mediaAuthorPenname??>${bean.mediaAuthorPenname}</#if></td>
						      		<td><#if bean.mediaCreationDate??>${bean.mediaCreationDate}</#if></td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
			    <div class="pagination rightPager"></div>
		       <div class="leftPager">总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
		    </div>
		</div>
	</div>
  </body>
	<script type="text/javascript" src="${rc.contextPath}/script/jquery/jquery-1.7.js"></script>
  	<#include "../common/common-js.ftl">
  	<script type="text/javascript">
  	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize},
			current_page: ${pageFinder.pageNo - 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/ranking/medianewest.go?pageIndex=__id__&sexChannel=${sexChannel}')
	    });
   	});
  	</script>
	</html>
