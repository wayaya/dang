<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		var condition ="";
		$(document).ready(function(){

			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c}-1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/epubImage/list.go?mediaId=${epubImage.mediaId?c}&page=__id__'+condition)
		    });
		    $('#addBtn').click(function(){
				location='/epubImage/toUpload.go?mediaId=${epubImage.mediaId?c}';
			});
	   	
		});
	   	function searchList(){
	   		$('#search_list_form').submit();
	   	}
	   	
		function copyToClipboard(maintext){
			if (window.clipboardData){
		    	window.clipboardData.setData("Text", maintext);
		  		alert("以下内容已经复制到剪贴板\n" + maintext);
		    }else if (window.netscape){
		    	alert("该浏览器不支持一键复制！\n请手工复制文本框链接地址～");
		  }
		}
		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>图片管理</h3>
					<div class="mrdiv">
					<table >
		      		<table >
		      		<form action="/epubImage/list.go" method="post" id="search_list_form">
			      		 
		        			<tr><input type="hidden" name="mediaId" id="mediaId" value="<#if mediaId??>${mediaId}</#if>">
								
								<td class="right_table_tr_td">
								<#if userSessionInfo?? && userSessionInfo.f['194']?? >
									<button type="button" id="addBtn" >上传图片</button>
								</#if>
								<td >&nbsp;</td>
							</tr>
						
				    </form>
				    </table>
				    
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		               <!--     <th style="width:5%; height:28px;" ><input type="checkbox" id="chkAll" name="chkAll">全选</th> -->
		               		<th style="width:10%">id</th>
		                    <th style="width:15%">预览</th>
		                    <th style="width:15%">url地址</th>
		                    <th style="width:10%">状态</th>
		                    <th style="width:10%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as epubImage>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td><#if epubImage.id??>${epubImage.id?c}</#if></td>
					    			<td><#if epubImage.url??><img width="100%" src="${epubImage.url}"/></#if></td>
					    			<td><#if epubImage.url??>${epubImage.url}</#if></td>
						      		<td><#if epubImage.statusString??>${epubImage.statusString}</#if></td>
						      		<td><a href="javascript:copyToClipboard('${epubImage.url}');">【复制url】</a>&nbsp</td>
						      		
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
<script type="text/javascript">
</html>
