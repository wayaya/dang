<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>膜拜明细</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		var condition ="";
		$(function(){
			makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/worshipRecord/list.go?page=__id__'+condition)
		    });
		});
		function makecondition(){
			var lefttab = $('#lefttab').val();
			if(lefttab.length  > 0){
				condition = condition + "&lefttab="+lefttab;
			}
		}
		
	   	function searchList(){
	   		$('#list_form').submit();
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>活动用户管理&gt;&gt;膜拜明细</h3>
					<div class="mrdiv">
		      		<form action="/worshipRecord/list.go" method="post" id="list_form" >
			      		 <table >
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">膜拜用户名：<input type="text" name="worshipUsername" id="worshipUsername" value="<#if select??><#if select.worshipUsername??>${select.worshipUsername}</#if></#if>"></td>
								<td class="right_table_tr_td">膜拜用户ID：<input type="text" name="worshipCustId" id="worshipCustId" value="<#if select??><#if select.worshipCustId??>${select.worshipCustId?c}</#if></#if>"></td>
								<td class="right_table_tr_td">被膜拜用户名：<input type="text" name="worshipedUsername" id="worshipedUsername" value="<#if select??><#if select.worshipedUsername??>${select.worshipedUsername}</#if></#if>"></td>
								<td class="right_table_tr_td">被膜拜用户ID：<input type="text" name="worshipedCustId" id="worshipedCustId" value="<#if select??><#if select.worshipedCustId??>${select.worshipedCustId?c}</#if></#if>"></td>
								<td class="right_table_tr_td"><button  onclick="return searchList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:10%">膜拜用户名</th>
		                    <th style="width:10%">膜拜用户id</th>
		                    <th style="width:10%">被膜拜用户名</th>
		                    <th style="width:10%">被膜拜用户id</th>
		                    <th style="width:12%">创建时间</th>
	               	    </tr>
	               	   <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as one>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if one.mediaWorshipRecordId??>${one.mediaWorshipRecordId?c}</#if></td>
						      		<td><#if one.worshipUsername??>${one.worshipUsername}</#if></td>
						      		<td><#if one.worshipCustId??>${one.worshipCustId?c}</#if></td>
						      		<td><#if one.worshipedUsername??>${one.worshipedUsername}</#if></td>
						      		<td><#if one.worshipedCustId??>${one.worshipedCustId?c}</#if></td>
						      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
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
<#include "../../common/common-js.ftl">
  </body>
</html>

