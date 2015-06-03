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
		      		<form action="/column/sub.go" method="post" id="query" name="query">
		      		<#if RequestParameters.isCallBack??>
		      			<input type="hidden" id ="isCallBack" name="isCallBack" value='1'>
		      			<input type="hidden" id ="codeId" name="codeId" value='<#if RequestParameters.codeId??>${RequestParameters["codeId"]}</#if>'>
		      			<input type="hidden" id ="nameId" name="nameId" value='<#if RequestParameters.nameId??>${RequestParameters["nameId"]}</#if>'>
  					</#if>
  		
  	
		      		<input type="hidden" id ="parentId" name="parentId" value='<#if RequestParameters.parentId??>${RequestParameters["parentId"]}<#else>0</#if>'>
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
		                    <th>操作</th>
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
						      		<td><a href="/column/edit.go?columnId=${column.columnId?c}">【修改】</a></td>
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
  		<#if RequestParameters.isCallBack??>
  		var code='${RequestParameters["codeId"]}';
  		var name='${RequestParameters["nameId"]}';
  		if(code==''){
  			code="id";
  		}
  		if(name==''){
  			name="name";
  		}
  		//多个弹出层,需要parent层次关系
  		var api = parent.frameElement.api, W = api.opener;
  		W.document.getElementById(code).value = columnCode;
  		W.document.getElementById(name).value = columnName;
  		api.close();
  	  	</#if> 
  	  }
  	  
  	  
  	  
  	  function hintDelete(){
  		  var hasson = $('#deleteBtn').attr('hasson');
  		  if(hasson==1){
  			  alert("请先删除子栏目");
  			  return false;
  		  }
  		 var isdelete =  window.confirm("您确认删除该栏目吗?");
  		 if(isdelete){
  			  return true;
  		 }
  	  }
  	  
  	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize},
			current_page: ${pageFinder.pageNo - 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/column/sub.go?pageIndex=__id__&'+$("#query").serialize())
	    });
   	});
  	</script>
	</html>
