<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>公告内容列表</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>公告管理&gt;&gt;公告内容管理</h3>
				      <div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
				     <form id="query" name="query" method="post" action="/notice/list.go">
				      <table >
		        			<tr>
		        			<td>公告名称:<input type="text" id="title" name="title" value="<#if RequestParameters.title??>${RequestParameters["title"]}</#if>" />&nbsp;&nbsp;<button type="submit">确定</button></td>
		        		</tr>
		        	</table>
				     </form>
				     </div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>内容</th>
		                    <th>频道</th>
		                    <th>URL</th>
		                    <th>参数内容</th>
		                    <th>操作者</th>
		                    <th>最后修改时间</th>
		                    <th>结束时间</th>
		                    
		                    <th>操作</th>
	               	    </tr>
	               	    
	               	    <#assign i = 0> 
	               	    <#if (pageFinder?size>0)>
	               	     <#list pageFinder.data as notice> 
	               	     <#assign i = i+1>
	               	      <#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				    
					    			<td>${i}</td>
						      		<td><#if notice.title??>${notice.title}</#if></td>
						      		<td><#if notice.channelType??><#if '${notice.channelType}'=='np'>男频<#else>女频</#if></#if></td>
						      		<td><#if notice.url??>${notice.url}</#if></td>
						      		<td><#if notice.parameter??>${notice.parameter}</#if></td>
						      		<td><#if notice.modifer??>${notice.modifer}</#if></td>
						      		<td><#if notice.lastChangeTime??>${notice.lastChangeTime?substring(0,19)}</#if></td>
						      		<td><#if notice.endTime??>${notice.endTime?substring(0,19)}</#if></td>
						      		<td>
						      		 <#if userSessionInfo?? && userSessionInfo.f['109']?? > 
						      			&nbsp;&nbsp;<a onclick="return delTips();" href="/notice/delete.go?noticeId=${notice.noticeId}">删除</a>
						      		</#if>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		       	</div>
				</div>
				<div>
				    <div class="pagination rightPager"></div>
				    <div class="leftPager">总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
			    </div>
			</div>
		</div>
	</div>
</body>
  	<#include "../common/common-js.ftl">
  	<script type="text/javascript">
  	function delTips(){
  		return window.confirm("您确认删除该公告吗?");
  	}
  	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize?c},
			current_page: ${pageFinder.pageNo- 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/notice/list.go?pageIndex=__id__&'+$("#query").serialize())
	    });
   	});
  	</script>
 
	</html>
