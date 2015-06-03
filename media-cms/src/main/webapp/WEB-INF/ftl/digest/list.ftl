<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		var condition ="";
		$(function(){
			makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize?c},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/digest/list.go?page=__id__'+condition)
		    });
				
		});
		function makecondition(){
			var title = $('#title').val();
			
			if(title && title.length  > 0){
				condition = condition + "&title="+title;
			}
			var cardType = $("#cardType  option:selected").val();
			if(cardType.length  > 0){
				condition = condition + "&cardType="+cardType;
			}
			
			var showStartStartDate = $("#showStartStartDate").val();
			if(showStartStartDate.length  > 0){
				condition = condition + "&showStartStartDate="+showStartStartDate;
			}
			
			var showStartEndDate = $("#showStartEndDate").val();
			if(showStartEndDate.length  > 0){
				condition = condition + "&showStartEndDate="+showStartEndDate;
			}
			
			var isShow = $("#isShow  option:selected").val();
			if(isShow.length  > 0){
				condition = condition + "&isShow="+isShow;
			}
			var stars = $("#stars  option:selected").val();
			if(stars.length  > 0){
				condition = condition + "&stars="+stars;
			}
			
			var dayOrNight = $("#dayOrNight").val();
			if(dayOrNight.length  > 0){
				condition = condition + "&dayOrNight="+dayOrNight;
			}
			
		}
		
		 
			
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	   	function batchSetTime(){
	   	
	   		var val = $('[name=disId]:checked');
	   		if(val.length ==0){
	   			alert('请选择要修改的文章');
	   			return;
	   		}
	   		
	   		var ids = []
	   		for(var i=0;i<val.length;i++){
	   			ids.push(val[i].value);
	   		}
	   		if(ids.length == 0){
	   			alert('请选择要修改的文章');
	   			return;
	   		}
			setTime(ids);
	   	}
	   	function setTime(ids){
			console.log(ids)
	   		$.dialog({
	   			id:"cateDialog",
	   			title:'设置时间',
	   			content:'url:/digest/toSetTime.go?ids='+ids,
	   			icon:'succeed',
	   			width:400,
	   			height:100,
	   			fixed:false,
	   			lock:true,
	   			button: [{
	            name: '确定',
	            callback: function () {
	            	 var isShow = $.dialog.list['cateDialog'].content.window.document.getElementById('isShow');
	            	 if($(isShow).attr("checked")){
	            	 	isShow=1;
	            	 } else {
	            	 	isShow=0;
	            	 }
	             	 var startDate = $.dialog.list['cateDialog'].content.window.document.getElementById('showStartDate').value;
	             	 location='/digest/setTime.go?startDate='+startDate+'&ids='+ids + '&isShow=' + isShow;
	                return true;
	            },
	            focus: true
	        }]
			});
		
	   	}
	   	
	   	function updateIsShow(id){
	   		if(confirm("确认取消生效该文章吗？")) {
	   			location='/digest/updateIsShow.go?id=' + id;
	   		}
	   	}
	   	function del(id){
	   		if(confirm("确认删除该文章吗？")) {
	   			location='/digest/del.go?id=' + id;
	   		}
	   	}
	   
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>当心好文管理&gt;&gt;当心好文管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/digest/list.go" method="post" id="activity_type_list_form">
		        			<tr>
								<td class="right_table_tr_td" heigth="28px" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标题：<input type="text" name="title" id="title" value="<#if dis??><#if dis.title??>${dis.title}</#if></#if>"></td>
								<td class="right_table_tr_td" heigth="28px" width="25%">卡片模板：
							 		<select name="cardType" id="cardType" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if dis??><#if dis.cardType??><#if (dis.cardType == 0)>selected = "selected" </#if></#if></#if>>文字</option>
							 			<option value="1"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 1)>selected = "selected" </#if></#if></#if>>图文</option>
							 			<option value="2"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 2)>selected = "selected" </#if></#if></#if>>大图</option>
							 		</select>
							 	</td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">生效时间从 ：<input type="text"  type="text" name="showStartStartDate"  value="<#if dis??><#if dis.showStartStartDate??>${dis.showStartStartDate}</#if></#if>" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'showStartEndDate\')}'})" id="showStartStartDate" ></td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">&nbsp;&nbsp;至：<input type="text" type="text" name="showStartEndDate" class="Wdate"  value="<#if dis??><#if dis.showStartEndDate??>${dis.showStartEndDate}</#if></#if>"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'showStartStartDate\')}'})" id="showStartEndDate"></td>
								
							</tr>
							<tr height="10px">
							</tr>
							<tr>
						
							 	<td class="right_table_tr_td" heigth="28px" width="25%">
							 	生效状态：
							 	<select name="isShow" id="isShow" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="1" <#if dis??><#if dis.isShow??><#if (dis.isShow == 1)>selected = "selected" </#if></#if></#if>>已生效</option>
							 			<option value="0" <#if dis??><#if dis.isShow??><#if (dis.isShow == 0)>selected = "selected" </#if></#if></#if>>未生效</option>
							 	</select>
							 	</td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">
							 	评星：
							 	<select name="stars" id="stars" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="1" <#if dis??><#if dis.stars??><#if (dis.stars == 1)>selected = "selected" </#if></#if></#if>>★</option>
							 			<option value="2"  <#if dis??><#if dis.stars??><#if (dis.stars == 2)>selected = "selected" </#if></#if></#if>>★★</option>
							 			<option value="3"  <#if dis??><#if dis.stars??><#if (dis.stars == 3)>selected = "selected" </#if></#if></#if>>★★★</option>
							 			<option value="4"  <#if dis??><#if dis.stars??><#if (dis.stars == 4)>selected = "selected" </#if></#if></#if>>★★★★</option>
							 			<option value="5"  <#if dis??><#if dis.stars??><#if (dis.stars == 5)>selected = "selected" </#if></#if></#if>>★★★★★</option>
							 	</select>
							 	</td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">
							 	浏览模式：
							 	<select name="dayOrNight" id="dayOrNight" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if dis??><#if dis.dayOrNight??><#if (dis.dayOrNight == 0)>selected = "selected" </#if></#if></#if>>白天</option>
							 			<option value="1" <#if dis??><#if dis.dayOrNight??><#if (dis.dayOrNight == 1)>selected = "selected" </#if></#if></#if>>黑夜</option>
							 	</select>
							 	</td>
								<td class="right_table_tr_td" heigth="28px" width="25%">
								<button  onclick="javascript:return searchActivityTypeList();">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
								
							</tr>
							<tr style="height:10px;"></tr>
							<tr>
								<td>
								<#if userSessionInfo?? && userSessionInfo.f['195']?? >
									<input type="button" value="新増"  onclick="javascript:location='/digest/toAddOrEdit.go'">
								</#if>
								<#if userSessionInfo?? && userSessionInfo.f['196']?? >
									<input type="button" onclick="javascript:batchSetTime();" value="批量设置时间">	</td>
								</#if>
							</tr>
				    </form>
				    
				    </table>
				    
				    </div>
				    <div>	
				    <table class="table2">
		            	<tr>
		            		<th style="width:3%; height:28px;" ><input type="checkbox" id="chkAll" name="chkAll">全选</th>
		                    <th style="width:2%; height:28px;" >序号</th>
		                    <th style="width:10%">标题</th>
		                    <th style="width:5%">作者</th>
		                    <th style="width:10%">关联电子书名</th>
		                    <th style="width:5%">分类</th>
		                    <th style="width:4%">卡片模板</th>
		                    <th style="width:4%">浏览模式</th>
		                    <th style="width:5%">电子书PID</th>
		                    <th style="width:3%">文章ID</th>
		                    <th style="width:5%">标签</th>
		                    <th style="width:4%">收藏数</th>
		                    <th style="width:4%">评论数</th>
		                    <th style="width:4%">分享数</th>
		                    <th style="width:5%">评星</th>
		                    <th style="width:4%">操作者</th>
		                    <th style="width:7%">入选时间</th>
		                    <th style="width:3%">发布状态</th>
		                    <th style="width:3%">生效状态</th>
		                    <th style="width:7%">生效时间</th>
		                    <th >操作</th>
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
			    					<td><#if dis.isShow??><#if dis.isShow==0><input type="checkbox" value="${dis.id?c}" name='disId' /></#if></#if></td>
			    					<td>${i}</td>
					    			<td><#if dis.title??>${dis.title}</#if></td>
						      		<td><#if dis.authorList??>
						      			<#list dis.authorList as author>
						      				<a href="javascript:location='/digest/findDigestByAuthorId.go?authorId=${author.authorId?c}'">${author.name}</a>&nbsp;
						      			</#list>
						      		</#if></td>
						      		<td><#if dis.mediaName??>${dis.mediaName}</#if></td>
						      		<td><#if dis.firstCatetoryName??>${dis.firstCatetoryName}</#if></td>
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
						      				<#if dis.dayOrNight??><#if (dis.dayOrNight == 0)>
						      					白天
						      				</#if></#if>
						      				<#if dis.dayOrNight??><#if (dis.dayOrNight == 1)>
						      					黑夜
						      				</#if></#if>
						      			</#if>
						      		</td>
						      		<td><#if dis.mediaId??>${dis.mediaId?c}</#if></td>
						      		<td><#if dis.id??>${dis.id?c}</#if></td>
						      		<td><#if dis.signList??>
						      			<#list dis.signList as sign>
						      				<a href="javascript:location='/digest/findDigestBySignId.go?signId=${sign.id?c}'">${sign.name}</a>&nbsp;
						      			</#list></#if></td>
						      		<td><#if dis.collectCnt??>${dis.collectCnt}</#if></td>
						      		<td><#if dis.reviewCnt??>${dis.reviewCnt}</#if></td>
						      		<td><#if dis.shareCnt??>${dis.shareCnt}</#if></td>
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
						      		<td><#if dis.operator??>${dis.operator}</#if></td>
						      		<td><#if dis.createDate??>${dis.createDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if isPublishMap??>${isPublishMap[dis.id?c]}</#if></td>
						      		<td><#if dis.isShow??>
						      				<#if dis.isShow??><#if (dis.isShow == 0)>
						      					未生效
						      				</#if></#if>
						      				<#if dis.isShow??><#if (dis.isShow == 1)>
						      					已生效
						      				</#if></#if>
						      			</#if>
						      		</td>
						      		<td><#if dis.showStartDate??>${dis.showStartDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
						      		<#if userSessionInfo?? && userSessionInfo.f['197']?? >
						      		<a href="javascript:location='/digest/toAddOrEdit.go?id=${dis.id?c}'">【修改】</a>&nbsp;
						      		</#if>	
						      		<#if userSessionInfo?? && userSessionInfo.f['198']?? >
						      		<#if dis.isShow??><#if dis.isShow==1><a href=# onclick="updateIsShow(${dis.id?c})">【取消生效】</a>&nbsp;</#if></#if>
						      		</#if>	
						      		<#if userSessionInfo?? && userSessionInfo.f['199']?? >
						      		<#if dis.isShow??><#if dis.isShow==0><a href=# onclick="del(${dis.id?c})">【删除】</a>&nbsp;</#if></#if>
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
  	$(document).ready(function(){
		$('#chkAll').click(function(){
			if(this.checked){
				 $('[name=disId,disabled=false]:checkbox').attr("checked", true);
			}else{
				 $('[name=disId,disabled=false]:checkbox').attr("checked", false);
			}
		});
});
  </script>
</html>