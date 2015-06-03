<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>榜单管理&gt;&gt;榜单列表</h3>
					<div class="mrdiv">
		      		<form action="/rankConsToBook/list.go" method="post" id="list_form" >
			      		 <table >
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">昵称：<input type="text" name="username" id="username" value="<#if rankOne??><#if rankOne.username??>${rankOne.username}</#if></#if>"></td>
								<td class="right_table_tr_td">作品名：<input type="text" name="mediaName" id="mediaName" value="<#if rankOne??><#if rankOne.mediaName??>${rankOne.mediaName}</#if></#if>"></td>
								<td class="right_table_tr_td">频道：
									 <select id="channel" name="channel">
										    <option value=""  <#if rankOne??><#if rankOne.channel??><#if rankOne.channel=="">selected="selected"</#if></#if></#if>>全部</option>
			    							<option value="NP" <#if rankOne??><#if rankOne.channel??><#if rankOne.channel=="NP">selected="selected"</#if></#if></#if>>男生站</option>
			    							<option value="VP" <#if rankOne??><#if rankOne.channel??><#if rankOne.channel=="VP">selected="selected"</#if></#if></#if>>女生站</option>
									  </select>
								
								</td>
								<td class="right_table_tr_td">数据周期：<#if rankOne??><#if rankOne.type??><#if rankOne.type?c=="2"></#if></#if></#if>
								<select id="type" name="type">
			    					<option value=""  <#if rankOne??><#if rankOne.type??><#if rankOne.type?c=="">selected="selected"</#if></#if></#if>>请选择</option>
			    					<option value="1" <#if rankOne??><#if rankOne.type??><#if rankOne.type?c=="1">selected="selected"</#if></#if></#if>>日</option>
			    					<option value="2" <#if rankOne??><#if rankOne.type??><#if rankOne.type?c=="2">selected="selected"</#if></#if></#if>>周</option>
			    					<option value="3" <#if rankOne??><#if rankOne.type??><#if rankOne.type?c=="3">selected="selected"</#if></#if></#if>>月</option>
			    					<option value="4" <#if rankOne??><#if rankOne.type??><#if rankOne.type?c=="4">selected="selected"</#if></#if></#if>>总</option>
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
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:10%">榜单标识</th>
		                    <th style="width:10%">昵称</th>
		                    <th style="width:10%">作品名</th>
		                    <th style="width:7%">消费金币</th>
		                    <th style="width:7%">显示金币</th>
		                    <th style="width:5%">数据周期</th>
		                    <th style="width:5%">频道</th>
		                    <th style="width:12%">操作时间</th>
		                    <th style="width:10%">操作者</th>
		                    <th>操作</th>
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
					    			<#--<td><#if one.mediaEbookConsRanklistId??>${one.mediaEbookConsRanklistId?c}</#if></td>-->
					    			<td>${i}</td>
						      		<td><#if one.code??>${one.code}</#if></td>
						      		<td><#if one.username??>${one.username}</#if></td>
						      		<td><#if one.mediaName??>${one.mediaName}</#if></td>
						      		<td><#if one.cons??>${one.cons?c}</#if></td>
						      		<td><#if one.showCons??>${one.showCons?c}</#if></td>
						      		<td><#if one.type??><#if one.type?c=="1">日<#elseif one.type?c=="2">周<#elseif one.type?c=="3">月<#elseif one.type?c=="4">总</#if></#if></td>
						      		<td><#if one.channel??>
						      		       <#if one.channel=="NP">男生站</option><#elseif one.channel=="VP">女生站</option></#if></#if></td>
								       </select>
						   			</td>
						      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if one.modifier??>${one.modifier}</#if></td>
						      		<td>
						      		   <#if userSessionInfo?? && userSessionInfo.f['114']?? > 
							      		<a href="/rankConsToBook/selectOne.go?id=<#if one.mediaEbookConsRanklistId??>${one.mediaEbookConsRanklistId?c}</#if>">【编辑】</a>
							      		</#if>
							      		
							      		&nbsp;
							      		<#if userSessionInfo?? && userSessionInfo.f['115']?? > 
							      		<a onclick="return hintDelete();" href="/rankConsToBook/delete.go?id=<#if one.mediaEbookConsRanklistId??>${one.mediaEbookConsRanklistId?c}</#if>&code=<#if one.code??>${one.code}</#if>">【删除】</a>
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
	
<#include "../../common/common-js.ftl">
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
				link_to: encodeURI('/rankConsToBook/list.go?page=__id__'+condition)
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

