<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
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
					<h3>用户列表</h3>
					<div class="mrdiv">
		      		<form action="/notice/users.go" method="post" id="list_form" >
			      		 <table >
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">昵称：<input type="text" name="username" id="username" value="<#if one??><#if one.username??>${one.username}</#if></#if>"></td>
								<td class="right_table_tr_td">用户ID：<input type="text" name="custId" id="custId" value="<#if one??><#if one.custId??>${one.custId?c}</#if></#if>"  <input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
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
		                    <th style="width:5%">用户昵称</th>
		                    <th style="width:15%">用户id</th>
		                    <th style="width:5%">分享次数</th>
		                    <th style="width:5%">被膜拜次数</th>
		                    <th style="width:5%">日榜最高</th>
		                    <th style="width:5%">总榜最高</th>
		                    <th style="width:12%">创建时间</th>
		                    <th style="width:10%">更新时间</th>
	               	    </tr>
	               	   <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as one>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr ondblclick="javascript:setCustId(${one.custId?c});" style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr ondblclick="javascript:setCustId(${one.custId?c});" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if one.mediaActivityUserId??>${one.mediaActivityUserId?c}</#if></td>
						      		<td><#if one.username??>${one.username}</#if></td>
						      		<td><#if one.custId??>${one.custId?c}</#if></td>
						      		<td><#if one.shareTimes??>${one.shareTimes?c}</#if></td>
						      		<td><#if one.worshipedTimes??>${one.worshipedTimes?c}</#if></td>
						      		<td><#if one.dayTopRank??>${one.dayTopRank?c}</#if></td>
						      		<td><#if one.totalTopRank??>${one.totalTopRank?c}</#if></td>
						      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if one.lastModifiedDate??>${one.lastModifiedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
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
  	function setCustId(custId){
  		var api = frameElement.api, W = api.opener;
  		W.document.getElementById('id').value = custId;
  		api.close();
  	}
  </script>
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
				link_to: encodeURI('/notice/users.go?page=__id__'+condition)
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
  </body>
</html>

