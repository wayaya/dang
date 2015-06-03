<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>栏目列表</title>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>栏目管理&gt;&gt;栏目列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/column/choosecolumn.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>栏目名称：<input type="text" name="name" id="name">
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
		                    <th>栏目名称</th>
		                    <th>栏目标识</th>
		                    <th>栏目图标</th>
		                    <th>是否显示小喇叭</th>
		                    <th>小喇叭内容</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	 <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as column>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr ondblclick="javascript:setColumnId('${column.columnCode}','${column.name}');" style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr ondblclick="javascript:setColumnId('${column.columnCode}','${column.name}');" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
						      		<td>${column.name}</td>
						      		<td><#if column.columnCode??>${column.columnCode}</#if></td>
						      		<td><#if column.icon??><img src="${column_base_dir}${column.icon}" width=20 height=20 alt="栏目图标"/></#if></td>
						      		<td>${column.isShowHorn?string('是','否')}</td>
						      		<td><#if column.tips??>${column.tips}</#if></td>
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
  </body>
	<script type="text/javascript" src="${rc.contextPath}/script/jquery/jquery-1.7.js"></script>
  	<#include "../common/common-js.ftl">
  	<#include "../common/common-css.ftl">
  	<script>
  	function setColumnId(columnCode,columnName){
  		var code='${code}';
  		var name='${name}';
  		if(code==''){
  			code="id";
  		}
  		if(name==''){
  			name="name";
  		}
  		alert(code+","+name);
  		var api = frameElement.api, W = api.opener;
  		W.document.getElementById(code).value = columnCode;
  		W.document.getElementById(name).value = columnName;
  		api.close();
  	}
  	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize},
			current_page: ${pageFinder.pageNo - 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/column/choosecolumn.go?pageIndex=__id__')
	    });
   	});
  	</script>
	</html>
