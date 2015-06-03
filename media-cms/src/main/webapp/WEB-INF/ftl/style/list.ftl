<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
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
				link_to: encodeURI('/style/list.go?page=__id__'+condition)
		    });
		});
		function makecondition(){
			var lefttab = $('#lefttab').val();
			if(lefttab.length  > 0){
				condition = condition + "&lefttab="+lefttab;
			}
			var name = $('#name').val();
			if(name.length  > 0){
				condition = condition + "&name="+name;
			}
			var code = $('#code').val();
			if(code.length  > 0){
				condition = condition + "&code="+code;
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
					<h3>样式管理&gt;&gt;样式管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/style/list.go" method="post" id="activity_type_list_form">
			      		 
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">名称：<input type="text" name="name" id="name" value="<#if style??><#if style.name??>${style.name}</#if></#if>"></td>
							 	<td class="right_table_tr_td">编码：<input type="text" name="code" id="code" value="<#if style??><#if style.code??>${style.code}</#if></#if>"></td>
								<td class="right_table_tr_td"><button  onclick="return searchActivityTypeList()">查询</button>
								<input type="button"  onclick="javascript:location='/style/toAdd.go'" value="新増样式">
								<td >&nbsp;</td>
							</tr>
						
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:10%">名称</th>
		                    <th style="width:5%">编码</th>
		                    <th style="width:10%">描述</th>
		                    <th style="width:15%">创建时间</th>
		                    <th style="width:15%">状态</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as style>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td>${i}</td>
					    			<td><#if style.name??>${style.name}</#if></td>
						      		<td><#if style.code??>${style.code}</#if></td>
						      		<td><#if style.desc??>${style.desc}</#if></td>
						      		<td><#if style.creationDate??>${style.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if style.status??><#if (style.status == 1)>有效 <#else>无效</#if></#if></td>
						      		<td>
					      				<a href="javascript:location='/style/toEdit.go?id=<#if style.styleId??>${style.styleId?c}</#if>'">更新</a>
					      				<a href="javascript:location='/style/delete.go?id=<#if style.styleId??>${style.styleId?c}</#if>'">删除</a>
						      		</td>
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
</html>
