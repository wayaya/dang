<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>携手金融--后台登陆</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>标签管理&gt;&gt;标签列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/label/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>标签名称：<input type="text" name="name" id="name" >
								<button  type="submit" >查询</button>
								<button type="button" onclick="window.location='/label/add.go'">新增标签</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:20%;" >序号</th>
		                    <th style="width:20%">标签名称</th>
		                    <th style="width:20%">标签类型</th>
		                    <th style="width:20%">状态</th>
		                    <th style="width:20%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as label>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${label.lableId?c}</td>
						      		<td>${label.name}</td>
						      		<td><#if label.type=1>作者<#else>书籍</#if></td>
						      		<td>${label.isenabled?string('启用','禁用')}</td>
						      		<td><a href="/label/update.go?lableId=${label.lableId?c}&isenabled=${label.isenabled?string('1','0')}">【${label.isenabled?string('禁用','启用')}】</a>&nbsp;<a href="/label/edit.go?id=${label.lableId}">【修改】</a>&nbsp;<a onclick="return hintDelete();" href="/label/delete.go?id=${label.lableId}">【删除】</a></td>
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
 	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize?c},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/label/list.go?pageIndex=__id__')
		    });
	   	});
	   	
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	</script>

</html>
