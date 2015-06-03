<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="/style/common/index.css" rel="stylesheet" />
	<link href="/style/common/page.css" rel="stylesheet" />
	<script type="text/javascript" src="/script/jquery/jquery-1.7.js"></script>
	<script src="/script/jquery/jquery-1.7.js" type="text/javascript" ></script>
	<script src="/script/jquery/jquery.pagination.js" type="text/javascript" ></script>
	<script src="/script/common/left.js" type="text/javascript" ></script>
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
				link_to: encodeURI('/notice/mediacategory.go?page=__id__')
		    });
	   	});
	   	
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	</script>
</head>   
  <body>
	 <div class="page" style="width: 99%; height: 900px;"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
			<table>
			<tr>
			<td style="width:100%;">
				<div class="m-r">
				    <div>
				    	<table class="table2">
			            	<tr>
			            		<th style="width:15%">序号</th>
			                    <th style="width:15%">名称</th>
			                    <th style="width:15%">编码</th>
			                    <th style="width:15%">path</th>
			                    <th style="width:15%">排序码</th>
		               	    </tr>
	               	 <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as cate>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr ondblclick="javascript:setCategoryId('${cate.code}','<#if cate.name??>${cate.name}</#if>');" style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr ondblclick="javascript:setCategoryId('${cate.code}','<#if cate.name??>${cate.name}</#if>');" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				<td>${i}</td>
				    			<td><#if cate.name??>${cate.name}</#if></td>
					      		<td><#if cate.code??>${cate.code}</#if></td>
					      		<td><#if cate.path??>${cate.path}</#if></td>
					      		<td><#if cate.indexOrder??>${cate.indexOrder}</#if></td>
					      	</tr>
				      		</#list>
				      	</#if>
		            	</table>
		            </div>
			    </div>
			    <div class="pagination rightPager"></div>
			    </td>
			    </tr>
			    </table>
		    </div>
		</div>
	</div>
	 <script>
  	function setCategoryId(categoryCode,categoryName){
  		var api = frameElement.api, W = api.opener;
  		W.document.getElementById('code').value = categoryCode;
  		W.document.getElementById('name').value = categoryName;
  		api.close();
  	}
  </script>
  </body>
</html>
