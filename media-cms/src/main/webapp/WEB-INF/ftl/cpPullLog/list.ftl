<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
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
				link_to: encodeURI('/pullLog/list.go?page=__id__'+condition)
		    });
		});
		function makecondition(){
			var cpCode = $("#cpCode  option:selected").val()
			if(cpCode.length  > 0){
				condition = condition + "&cpCode="+cpCode;
			}
			var flag = $("#flag  option:selected").val()
			if(flag.length  > 0){
				condition = condition + "&flag="+flag;
			}
			
		}
		
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>CP同步数据日志管理&gt;&gt;CP同步数据日志管理列表</h3>
					<div class="mrdiv">
						<table >
				      		<form action="/pullLog/list.go" method="post" id="activity_type_list_form">
				        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
										<td class="right_table_tr_td">CP：
										<select name="cpCode" id="cpCode" style="width:50%">
									 			<option value="">全部</option>
									 			<option value="zongheng" <#if log??><#if log.cpCode??><#if (log.cpCode == "zongheng")>selected = "selected" </#if></#if></#if>>纵横</option>
									 			<option value="jinjiang" <#if log??><#if log.cpCode??><#if (log.cpCode == "jinjiang")>selected = "selected" </#if></#if></#if>>晋江</option>
									 		</select></td>
									 	<td class="right_table_tr_td">状态：
									 	<select name="flag" id="flag" style="width:50%">
									 			<option value="">全部</option>
									 			<option value="0" <#if log??><#if log.flag??><#if (log.flag == 0)>selected = "selected" </#if></#if></#if>>失败</option>
									 			<option value="1"  <#if log??><#if log.flag??><#if (log.flag == 1)>selected = "selected" </#if></#if></#if>>成功</option>
									 		</select>
									 	</td>
										<td class="right_table_tr_td"><button  onclick="return searchActivityTypeList()">查询</button>
										<td >&nbsp;</td>
									</tr>
						    </form>
					    </table>
					    </div>
					 <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:10%">CP</th>
		                    <th style="width:5%">日志类型</th>
		                    <th style="width:10%">开始时间</th>
		                    <th style="width:15%">结束时间</th>
		                    <th style="width:15%">状态</th>
		                    <th style="width:15%">信息</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as log>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td>${i}</td>
					    			<td><#if log.cpCode??>${log.cpCode}</#if></td>
						      		<td><#if log.logType??><#if (log.logType == 1)>同步书 <#else>同步章节</#if></#if></td>
						      		<td><#if log.startDate??>${log.startDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if log.endDate??>${log.endDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if log.flag??><#if (log.flag == 1)>成功 <#else>失败</#if></#if></td>
						      		<td><#if log.msg??>${log.msg}</#if></td>
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
	<#include "../common/common-js.ftl">
  </body>
</html>
