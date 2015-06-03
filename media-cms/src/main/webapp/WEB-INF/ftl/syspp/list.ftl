<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
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
					<h3>系统参数&gt;&gt;参数列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/syspp/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>参数编码：<input type="text" name="keyName" id="keyName" value="<#if sysProperties??><#if sysProperties.keyName??>${sysProperties.keyName}</#if></#if>" >
									参数备注：<input type="text" name="comment" id="comment" value="<#if sysProperties??><#if sysProperties.comment??>${sysProperties.comment}</#if></#if>" >
								<button  type="submit" >查询</button>
								<#if userSessionInfo?? && userSessionInfo.f['75']?? >
									<button type="button" onclick="window.location='/syspp/add.go'">新增参数</button>
								</#if>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:20%">参数编码</th>
		                    <th style="width:20%">参数值</th>
		                    <th style="width:50%">说明备注</th>
		                    <th style="width:10%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as property>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
						      		<td>${property.keyName}</td>
						      		<td>${property.keyValue}</td>
						      		<td><#if property.comment??>${property.comment}</#if></td>
						      		<td>
						      		<#if userSessionInfo?? && userSessionInfo.f['147']?? >
						      			<a href="/syspp/edit.go?id=${property.propertyId?c}">修改</a>
						      		</#if>
						      		<#if userSessionInfo?? && userSessionInfo.f['148']?? >
						      			&nbsp;&nbsp;<a onclick="return hintDelete();" href="/syspp/delete.go?id=${property.propertyId}&keyName=${property.keyName}">删除</a>
						      		</#if>
						      		<#if userSessionInfo?? && userSessionInfo.f['149']?? >
						      			&nbsp;&nbsp;<a onclick="javascript:updateCache('${property.keyName}')" href="#">更新缓存</a>
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
	<#include "../common/common-js.ftl">
  </body>
  <script type="text/javascript">
  	    function hintDelete(){
  	    	return window.confirm("您确认删除配置项吗?");
  	    }
  		function updateCache(keyName){
  			 $.ajax({
     			type:"POST",
     			url:"/cache/systempp/clearcache.go",
     			data: "keyName="+keyName,
     			dataType:"json",
     			success: function(msg) {
     				   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
     					   alert("更新缓存失败!");
     				   }else{
     					   alert("更新缓存成功!");
     				   }
     			}
     		});
  		}
  		
  		
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/syspp/list.go?pageIndex=__id__')
		    });
	   	});
	   	
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	</script>
</html>
