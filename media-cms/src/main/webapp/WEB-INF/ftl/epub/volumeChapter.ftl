<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script src="/bootstrap/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var condition ="";
		function makecondition(){
			var mediaId = $('#mediaId').val();
			if(mediaId&& mediaId.length>0){
			condition = condition + "&mediaId="+mediaId;
			}
			
		}
		$(function(){
			makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/epub/toChapterVolume.go?page=__id__'+condition)
		    });
			
		});
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
					<h3>作品管理&gt;&gt;分卷、章节管理列表</h3>
					<div>
				    <table class="table2">
		            	<tr>
		            		<th style="width:2%; height:28px;" >序号</th>
		            		<th style="width:20%">章节名</th>
		                    <th style="width:10%">作者（作家笔名）</th>
		                    <th style="width:20%">作品</th>
		                    <th style="width:10%">字数</th>
		                    <th style="width:10%">入选状态</th>
		                    <th style="width:10%">标签</th>
		                    <th >操作</th>
		                    <input type="hidden" name="mediaId" id="mediaId" value="${mediaId?c}">
		                    
	               	    </tr>
	               	    <#assign i = 0>
				      	<#if pageFinder?? && pageFinder.data??>
				      	 <#list pageFinder.data as chap>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				<td>${i}</td>
				    			<td><#if chap.title??>${chap.title}</#if></td>
					      		<td><#if media.authorPenname??>${media.authorPenname}
					      		<#else>
					      			${media.authorName}
					      		</#if></td>
					      		<td><#if media.title??>${media.title}</#if></td>
					      		<td><#if chap.wordCnt??>${chap.wordCnt}</#if></td>
					      		<td>
					      		  <#if digestIds?? && digestIds?size gt 0>
					      		  	<#assign index = 0>
					      		  	<#assign isRu = 0>
						      		<#list digestIds as dig>
						      			<#assign index = index + 1>
									     <#if dig == chap.id>
									     	<#assign isRu = 1>
									    		  已选入							           	  
								 		<#elseif index == digestIds?size && isRu == 0>
								 			 未选入
								 		<#else>
								 		</#if>
									</#list>
								 <#else>
									           	  未选入
								 </#if>
					      		</td>
					      		<td><#if media.signNames??>${media.signNames}</#if></td>
					      		<td>
					      		<#if userSessionInfo?? && userSessionInfo.f['193']?? >
					      			<a href="javascript:location='/epub/toAddOrEdit.go?mediaId=${media.mediaId?c}&title=${chap.title}&authorPenname=${media.authorPenname}&id=${chap.id?c}'">【选入】</a>
				      			</#if>
				      			</td>
				      	 </#list>
				      	</#if>
				      	<#if noChapter??>${noChapter}</#if>
	               	</table>
	               	</div>

			    </div>
			    <div class="pagination rightPager"></div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
   <script>
  </script>
</html>
