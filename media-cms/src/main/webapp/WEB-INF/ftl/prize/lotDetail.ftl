<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>奖品管理&gt;&gt;抽奖记录明细</h3>
					<div class="mrdiv">
		      		<form action="/prize/lotDetail.go" method="post" id="list_form" >
			      		 <table >
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">用户ID：<input type="text" name="custId"  value="<#if limit??><#if limit.custId??>${limit.custId?c}</#if></#if>"></td>
								<td class="right_table_tr_td">名称名称<input type="text" name="prizeName"  value="<#if limit??><#if limit.prizeName??>${limit.prizeName}</#if></#if>"></td>
								<td class="right_table_tr_td">奖品ID：<input type="text" name="prizeId"  value="<#if limit??><#if limit.prizeId??>${limit.prizeId?c}</#if></#if>"></td>
								<td class="right_table_tr_td">奖品类型：
								<select name="prizeType">
			    					<option value=""  <#if limit??><#if limit.prizeType??><#if limit.prizeType?c=="">selected="selected"</#if></#if></#if>>请选择</option>
			    					<option value="1" <#if limit??><#if limit.prizeType??><#if limit.prizeType?c=="1">selected="selected"</#if></#if></#if>>金币</option>
			    					<option value="2" <#if limit??><#if limit.prizeType??><#if limit.prizeType?c=="2">selected="selected"</#if></#if></#if>>章节</option>
			    					<option value="3" <#if limit??><#if limit.prizeType??><#if limit.prizeType?c=="3">selected="selected"</#if></#if></#if>>道具</option>
								</select>
								</td>
								<td class="right_table_tr_td">归属类型：
								<select name="vestType">
			    					<option value=""  <#if limit??><#if limit.vestType??><#if limit.vestType?c=="">selected="selected"</#if></#if></#if>>请选择</option>
			    					<option value="1" <#if limit??><#if limit.vestType??><#if limit.vestType?c=="1">selected="selected"</#if></#if></#if>>福袋</option>
								</select>
								</td>
								<td class="right_table_tr_td"><button  onclick="return searchList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:3%; height:28px;" >序号</th>
		                    <th style="width:8%">用户ID</th>
		                    <th style="width:5%">奖品名称</th>
		                    <th style="width:8%">奖品ID</th>
		                    <th style="width:3%">奖品类型</th>
		                    <th style="width:4%">归属类型</th>
		                    <th style="width:9%">创建时间</th>
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
					    			<td><#if one.mediaLotteryRecordId??>${one.mediaLotteryRecordId?c}</#if></td>
						      		<td><#if one.custId??>${one.custId?c}</#if></td>
						      		<td><#if one.prizeName??>${one.prizeName}</#if></td>
						      		<td><#if one.prizeId??>${one.prizeId?c}</#if></td>
						      		<td><#if one.prizeType??>${one.prizeType?c}</#if></td>
						      		<td><#if one.vestType??>${one.vestType?c}</#if></td>
						      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
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
				link_to: encodeURI('/prize/list.go?page=__id__'+condition)
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
	   	function hintDelete(){
  			return window.confirm('小伙伴，确定要删除吗？删错了可是要找技术同学的。。');
		}
	</script>
  </body>
</html>

