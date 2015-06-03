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
			var title = $('#title').val();
			if(title && title.length  > 0){
				condition = condition + "&title="+title;
			}
			
			var authorPenname = $('#authorPenname').val();
			if(authorPenname.length  > 0){
				condition = condition + "&authorPenname="+authorPenname;
			}
			var shelfStatus = $("#shelfStatus  option:selected").val()
			
			if(shelfStatus.length  > 0){
				condition = condition + "&shelfStatus="+shelfStatus;
			}
			var uid = $("#uid").val()
			if(uid.length  > 0){
				condition = condition + "&uid="+uid;
			}
			
			var creationDateStart = $("#creationDateStart").val()
			if(creationDateStart.length  > 0){
				condition = condition + "&creationDateStart="+creationDateStart;
			}
			var creationDateEnd = $("#creationDateEnd").val()
			if(creationDateEnd.length  > 0){
				condition = condition + "&creationDateEnd="+creationDateEnd;
			}
			var parentId = $("#parentId").val()
			if( parentId.length >0 ){
				condition = condition+ "&parentId=" + parentId;
			}
			
		}
		$(function(){
			makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/epub/list.go?page=__id__'+condition)
		    });
		});
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	   	
	   	function upShelf(mediaId,title){
	   		if(confirm("确定要上架作品《"+title+"》吗？")){
	   			parent.location='/epub/update.go?shelfStatus=1&mediaId='+mediaId;
	   		}
	   	}
	   	function downShelf(mediaId,title){
	   		$.post("/epub/getSaleByMediaId.go", {mediaId:mediaId},
			   function(data){
			   		if(data!=''){
			   			alert('请先下架以下销售主体:'+data);
			   			return;
			   		}
	   				parent.location='/epub/update.go?shelfStatus=0&mediaId='+mediaId;
	   				
			   }, "text");
	   	}
	   	
	   	
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>作品管理&gt;&gt;作品管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/epub/list.go" method="post" id="activity_type_list_form">
			      		
		        			<tr> <input type="hidden" name="parentId" id="parentId" value="<#if parentId??>${parentId}</#if>">
								<td class="right_table_tr_td" heigth="28px" width="25%">
								作品名： <input type="text" name="title" id="title" value="<#if media??><#if media.title??>${media.title}</#if></#if>"></td>
							 	<!--<td class="right_table_tr_td" heigth="28px"  width="25%">
							 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;副标题：<input type="text" name="subTitle" id="subTitle" value="<#if media??><#if media.subTitle??>${media.subTitle}</#if></#if>">
							 	</td> -->
								<td class="right_table_tr_td" heigth="28px"  width="25%">
								&nbsp;作家笔名：<input type="text" name="authorPenname" id="authorPenname" value="<#if media??><#if media.authorPenname??>${media.authorPenname}</#if></#if>"></td>
								<td class="right_table_tr_td" heigth="28px" width="25%">下架状态：
							 		<select name="shelfStatus" id="shelfStatus" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if media??><#if media.shelfStatus??><#if (media.shelfStatus == 0)>selected = "selected" </#if></#if></#if>>已下架</option>
							 			<option value="1"  <#if media??><#if media.shelfStatus??><#if (media.shelfStatus == 1)>selected = "selected" </#if></#if></#if>>已上架</option>
							 		</select>
							 	</td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%" heigth="28px"  width="25%">书号：<input type="text" name="uid" id="uid" value="<#if media??><#if media.uid??>${media.uid}</#if></#if>"></td>
							</tr>
							<tr height="10px">
							</tr>
							<tr style="height:10px;"></tr>
							<tr>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">创建时间从 ：<input type="text"  type="text" name="creationDateStart"  value="<#if media??><#if media.creationDateStart??>${media.creationDateStart}</#if></#if>" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'creationDateEnd\')}'})" id="creationDateStart" ></td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">&nbsp;&nbsp;至：<input type="text" type="text" name="creationDateEnd" class="Wdate"  value="<#if media??><#if media.creationDateEnd??>${media.creationDateEnd}</#if></#if>"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'creationDateStart\')}'})" id="creationDateEnd"></td>
							    <td class="right_table_tr_td" heigth="28px" width="25%">
							 	  热度排序：
								 	<select name="orderby" id="orderby" style="width:50%" text-align:left>
								 			<option value="">无排序</option>
								 			<option value="week_heat desc"  <#if orderby??><#if orderby='week_heat desc'>selected = "selected"</#if></#if> >周热度</option>
								 			<option value="month_heat desc" <#if orderby??><#if orderby='month_heat desc'>selected = "selected"</#if></#if> >月热度</option>
								 			<option value="heat desc" <#if orderby??><#if orderby='heat desc'>selected = "selected"</#if></#if> >总热度</option>
								 	</select>
							 	</td>
							<td class="right_table_tr_td" heigth="28px" width="25%"><button  onclick="javascript:return searchActivityTypeList();">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
								
							</tr>
							<tr style="height:10px;"></tr>
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:2%; height:28px;" >序号</th>
		                    <th style="width:5%">作品ID</th>
		                    <th style="width:10%">作品名</th>
		                    <th style="width:10%">书号</th>
		                    <th style="width:10%">作家笔名</th>
		                    <th style="width:5%">作者ID</th>
		                    <th style="width:10%">作品分类</th>
		                    <th style="width:5%">章节数</th>
		                    <th style="width:5%">总评论</th>
		                    <th style="width:5%">周热度</th>
		                    <th style="width:5%">月热度</th>
		                    <th style="width:5%">总热度</th>
		                    <th style="width:5%">显示上下架</th>
		                    <th style="width:7%">创建时间</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as media>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td>${i}</td>
					    			<td><#if media.mediaId??>${media.mediaId?c}</#if></td>
					    			<td><#if media.title??>${media.title}</#if></td>
						      		<td><#if media.uid??>${media.uid}</#if></td>
						      		<td><#if media.authorPenname??>${media.authorPenname}</#if></td>
						      		<td><#if media.authorId??>${media.authorId?c}</#if></td>
						      		<td><#if mediaCateMap??>${mediaCateMap[media.mediaId?c]}</#if></td>
						      		<td><#if media.chapterCnt??>${media.chapterCnt}</#if></td>
						      		<td><#if bookReviewCount??>${bookReviewCount[media.mediaId?c]}</#if></td>
						      		<td><#if media.weekHeat??>${media.weekHeat?c}</#if></td>
						      		<td><#if media.monthHeat??>${media.monthHeat?c}</#if></td>
						      		<td><#if media.heat??>${media.heat?c}</#if></td>
						      		<td><#if media.shelfStatus??><#if (media.shelfStatus == 0)>已下架&nbsp;</#if></#if>
						      			<#if media.shelfStatus??><#if (media.shelfStatus == 1)>已上架&nbsp;</#if></#if></td>
						      		<td><#if media.creationDate??>${media.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
						      		<#if userSessionInfo?? && userSessionInfo.f['191']?? >
					      				<a href="javascript:location='/epub/toChapterVolume.go?mediaId=${media.mediaId?c}'">【查看章节】</a>
					      			</#if>
					      			<#if userSessionInfo?? && userSessionInfo.f['192']?? >
					      				<a href="javascript:location='/epubImage/list.go?mediaId=${media.mediaId?c}'">【查看图片】</a>
						      		</#if>
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
  <script>
  	
		
  </script>
</html>
