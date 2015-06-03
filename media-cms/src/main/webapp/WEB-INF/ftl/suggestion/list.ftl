<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>反馈意见列表</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>建议管理&gt;&gt;建议列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/suggestion/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>
								客户端版本号：<input type="text" id="clientVersion" name="clientVersion"  type="text"  value="<#if RequestParameters.clientVersion??>${RequestParameters["clientVersion"]}</#if>">
								&nbsp;&nbsp;日期：<input type="text" id="suggestDate" name="suggestDate" class="Wdate" type="text" onFocus="WdatePicker()" value="<#if RequestParameters.suggestDate??>${RequestParameters["suggestDate"]}</#if>">
								<button  type="submit" >查询</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>用户编号</th>
		                    <th>用户名称</th>
		                    <th>日期</th>
		                    <th>建议内容</th>
		                    <th>设备标识</th>
		                    <th>客户端版本号</th>
		                    <th>客户端操作系统</th>
		                    <th>联系方式</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as suggestion>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
					    			<td><#if suggestion.custId??>${suggestion.custId?c}</#if></td>
						      		<td><#if suggestion.userName??>${suggestion.userName}</#if></td>
						      		<td><#if suggestion.suggestDate??>${suggestion.suggestDate?substring(0,19)}</#if></td>
						      		<td><#if suggestion.advice??>${suggestion.advice}</#if></td>
						      		<td><#if suggestion.deviceType??>${suggestion.deviceType}</#if></td>
						      		<td><#if suggestion.clientVersion??>${suggestion.clientVersion}</#if></td>
						      		<td><#if suggestion.clientOs??>${suggestion.clientOs}</#if></td>
						      		<td><#if suggestion.contactWay??>${suggestion.contactWay}</#if></td>
						      	</tr>
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
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo- 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/suggestion/list.go?pageIndex=__id__&'+$("#query").serialize())
		    });
	   	});
	   	
	</script>

</html>
