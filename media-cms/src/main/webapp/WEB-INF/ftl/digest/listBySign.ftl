<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	
	<script type="text/javascript">

		
		var condition ="";
		$(function(){
			try{
			makecondition();
			}catch(e){}
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize?c},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/digest/findDigestBySignId.go?page=__id__'+condition)
		    });
				
		});
		function makecondition(){
			var signId = $("#signId").val();
			if(signId.length  > 0){
				condition = condition + "&signId="+signId;
			}
			
		}


	   
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>当心好文管理&gt;&gt;标签文章列表页</h3>
				    <div>	
				    <table class="table2">
			      		 <input type="hidden" name="signId" id="signId" value="${signId?c}">
		            	<tr>
		                    <th style="width:2%; height:28px;" >序号</th>
		                    <th style="width:10%">标题</th>
		                    <th style="width:5%">作者</th>
		                    <th style="width:5%">电子书PID</th>
		                    <th style="width:10%">关联电子书名</th>
		                    <th style="width:5%">分类</th>
		                    <th style="width:5%">评星</th>
		                    <th style="width:4%">卡片模板</th>
		                    <th style="width:4%">浏览模式</th>
		                    <th style="width:7%">标签</th>
		                    <th style="width:4%">评论数</th>
		                    <th style="width:4%">收藏数</th>
		                    <th style="width:4%">分享数</th>
		                    <th style="width:4%">显示状态</th>
		                    <th style="width:4%">操作者</th>
		                    <th style="width:7%">发送时间</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as dis>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td>${i}</td>
					    			<td><#if dis.title??>${dis.title}</#if></td>
						      		<td><#if dis.author??>${dis.author}</#if></td>
						      		<td><#if dis.mediaId??>${dis.mediaId?c}</#if></td>
						      		<td><#if dis.mediaName??>${dis.mediaName}</#if></td>
						      		<td><#if dis.firstCatetoryName??>${dis.firstCatetoryName}</#if></td>
						      		<td><#if dis.stars??>
						      				<#if dis.cardType??><#if (dis.stars == 1)>
						      					★
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.stars == 2)>
						      					★★
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.stars == 3)>
						      					★★★
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.stars == 4)>
						      					★★★★
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.stars == 5)>
						      					★★★★★
						      				</#if></#if>
						      			</#if>
						      		</td>
						      		<td><#if dis.cardType??>
						      				<#if dis.cardType??><#if (dis.cardType == 0)>
						      					文字
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.cardType == 1)>
						      					图文 
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.cardType == 2)>
						      					大图
						      				</#if></#if>
						      			</#if>
						      		</td>
						      		<td><#if dis.dayOrNight??>
						      				<#if dis.dayOrNight??><#if (dis.cardType == 0)>
						      					白天
						      				</#if></#if>
						      				<#if dis.dayOrNight??><#if (dis.cardType == 1)>
						      					黑夜
						      				</#if></#if>
						      			</#if>
						      		</td>
						      		<td><#if dis.signIds??>${dis.signIds}</#if></td>
						      		<td><#if dis.reviewCnt??>${dis.reviewCnt}</#if></td>
						      		<td><#if dis.collectCnt??>${dis.collectCnt}</#if></td>
						      		<td><#if dis.shareCnt??>${dis.shareCnt}</#if></td>
						      		<td><#if dis.isShow??>
						      				<#if dis.isShow??><#if (dis.isShow == 0)>
						      					不显示
						      				</#if></#if>
						      				<#if dis.isShow??><#if (dis.isShow == 1)>
						      					显示
						      				</#if></#if>
						      			</#if>
						      		</td>
						      		<td><#if dis.operator??>${dis.operator}</#if></td>
						      		<td><#if dis.showStartDate??>${dis.showStartDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
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
