<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>敏感词管理</title>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>敏感词管理&gt;&gt;敏感作品列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/illegalmedia/list.go" method="post" id="query" name="query" onSubmit="return checkForm();">
			      		 <table >
		        			<tr>
								<td>作品ID：<input type="text" name="mediaId" id="mediaId" value="<#if RequestParameters.mediaId??>${RequestParameters["mediaId"]}</#if>">
								<td>作品名称：<input type="text" name="mediaName" value="<#if RequestParameters.mediaName??>${RequestParameters["mediaName"]}</#if>">
								<td>作者ID：<input type="text" name="authorId"  id="authorId" value="<#if RequestParameters.authorId??>${RequestParameters["authorId"]}</#if>">
								<button  type="submit" >查询</button>
								<#if userSessionInfo?? && userSessionInfo.f['88']?? >
								<button type="button" id="addBtn" >添加敏感作品</button>
								</#if>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>作品ID</th>
		                    <th>作品名称</th>
		                    <th>作者ID</th>
		                    <th>敏感说明</th>
		                    <th>操作人ID</th>
		                    <th>操作时间</th>
		                    <th>操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	 <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as illegalMedia>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
					    			<td>${illegalMedia.mediaId?c}</td>
						      		<td><#if illegalMedia.media.title??>${illegalMedia.media.title}</#if> </td>
						      		<td><#if illegalMedia.media.authorId??>${illegalMedia.media.authorId?c}</#if> </td>
						      		<td><#if illegalMedia.details??>${illegalMedia.details}</#if></td>
						      		<td><#if illegalMedia.creator??>${illegalMedia.creator}</#if></td>
						      		<td><#if illegalMedia.lastChangeDate??>${illegalMedia.lastChangeDate}</#if></td>
						      		<td>
						      		
						      		  <#if userSessionInfo?? && userSessionInfo.f['89']?? >
						      			<a onclick="return deleteTip(${illegalMedia.illegalMediaId});" href="#">【删除】</a>
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
  </body>
  	<#include "../common/common-css.ftl">
  	<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
  	<#include "../common/common-js.ftl">
  <script type="text/javascript">
  	function checkForm(){
  		var mediaId = $('#mediaId').val();
  		var authorId = $('#authorId').val();
  		if(isNaN(mediaId)){
  			alert("作品编号必须是数字");
  			return false;
  		}
  		if(isNaN(authorId)){
  			alert("作者Id必须是数字");
  			return false;
  		}
  	}
  		//删除时的操作
		function deleteTip(illegalMediaId){
			$.dialog({title:'删除敏感作品',top:'10%',content:"url:/illegalmedia/deltip.go?illegalMediaId="+illegalMediaId,
		   		icon:'succeed',width:500,height:160,fixed:false,lock:true
		    });
		}
  		
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/label/list.go?page=__id__&'+$("#query").serialize())
		    });
	   	});
		
		$(document).ready(function(){
			//onclick="window.location='/illegalmedia/add.go'"
			$('#addBtn').click(function(){
				$.dialog({title:'添加敏感作品',top:'10%',content:"url:/illegalmedia/add.go",
			   		icon:'succeed',width:500,height:220,fixed:false,lock:true
			    });
			});
		});
	</script>
</html>
