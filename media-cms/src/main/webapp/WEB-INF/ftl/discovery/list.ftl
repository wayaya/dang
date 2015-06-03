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
			try{
			makecondition();
			}catch(e){}
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize?c},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/discovery/list.go?type=${type}&page=__id__'+condition)
		    });
				
		});
		function makecondition(){
			var title = $('#title').val();
			
			if(title && title.length  > 0){
				condition = condition + "&title="+title;
			}
			var cardType = ("#cardType  option:selected").val()
			if(cardType.length  > 0){
				condition = condition + "&cardType="+cardType;
			}
			<#if (type == 2)>
			var isShow = ("#isShow  option:selected").val()
			if(isShow.length  > 0){
				condition = condition + "&isShow="+isShow;
			}
			var stars = ("#stars  option:selected").val()
			if(stars.length  > 0){
				condition = condition + "&stars="+stars;
			}
			</#if>
			
			var state = ("#state  option:selected").val()
			if(state.length  > 0){
				condition = condition + "&state="+state;
			}
			
			var showStartStartDate = ("#showStartStartDate").val()
			if(showStartStartDate.length  > 0){
				condition = condition + "&showStartStartDate="+showStartStartDate;
			}
			
			var showStartEndDate = ("#showStartEndDate").val()
			if(showStartEndDate.length  > 0){
				condition = condition + "&showStartEndDate="+showStartEndDate;
			}
			
			var showEndStartDate = ("#showEndStartDate").val()
			if(showEndStartDate.length  > 0){
				condition = condition + "&showEndStartDate="+showEndStartDate;
			}
			
			var showEndEndDate = ("#showEndEndDate").val()
			if(showEndEndDate.length  > 0){
				condition = condition + "&showEndEndDate="+showEndEndDate;
			}
			
		}
		
		 
			
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	   	
	   	function setTime(id,type){
	   		$.dialog({id:"cateDialog",title:'设置时间',content:'url:/discovery/toSetTime.go?id='+id,
	   		icon:'succeed',width:400,height:100,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
             	 var startDate = $.dialog.list['cateDialog'].content.window.document.getElementById('showStartDate').value;
             	 location='/discovery/setTime.go?type='+type+'&startDate='+startDate+"&id="+id;
                return true;
            },
            focus: true
        }]
			});
	   	}
	   	
	   	function setStars(id,type){
	   		$.dialog({id:"cateDialog",title:'推荐评星',content:'url:/discovery/toSetStars.go?id='+id,
	   		icon:'succeed',width:400,height:100,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
             	 var stars = $.dialog.list['cateDialog'].content.window.getStars();
             	 location='/discovery/setStars.go?stars='+stars+"&id="+id;
                return true;
            },
            focus: true
        }]
			});
	   	}
	   
	   function del(type,id){
	   		if(window.confirm('你确定要删除吗？')){
	   			location='/discovery/del.go?type='+type+'&id='+id;
	   		}
	   }
	   
	</script>
	<script type="text/javascript">
	function updateOrder(){
	   		var ckId = getSelectedId();
         	if(ckId.length==0){
        		alert("请选择需要保存的记录");
        		return false;
        	}
        	$('#listform').submit();
       }
	   function getSelectedId(){
			var ids = [];
		 	$('input[name="listId"]:checked').each(function(){  
				//不管是否修改,全部提交数据库,由数据库判断是否更新
				ids.push($(this).val());   
		 	});
		 	return ids;
	   }
  	$(document).ready(function(){
		$('#chkAll').click(function(){
			if(this.checked){
				 $('[name=listId]:checkbox').attr("checked", true);
			}else{
				 $('[name=listId]:checkbox').attr("checked", false);
			}
		});
	});
  	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
				<#if (type == 2)>
					<h3>当心好文管理&gt;&gt;当心好文管理列表</h3>
				</#if>
				<#if (type == 1)>
					<h3>发现管理&gt;&gt;发现管理列表</h3>
				</#if>
					<div class="mrdiv">
					<table >
		      		<form action="/discovery/list.go?type=${type}" method="post" id="activity_type_list_form">
			      		 <input type="hidden" name="catetoryIds" id="catetoryIds" value="<#if media??><#if media.catetoryIds??>${media.catetoryIds}</#if></#if>">
		        			<tr>
								<td class="right_table_tr_td" heigth="28px" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标题：<input type="text" name="title" id="title" value="<#if dis??><#if dis.title??>${dis.title}</#if></#if>"></td>
								<td class="right_table_tr_td" heigth="28px" width="25%">卡片模板：
							 		<select name="cardType" id="cardType" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if dis??><#if dis.cardType??><#if (dis.cardType == 0)>selected = "selected" </#if></#if></#if>>文字</option>
							 			<option value="1"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 1)>selected = "selected" </#if></#if></#if>>图文</option>
							 			<option value="2"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 2)>selected = "selected" </#if></#if></#if>>小图</option>
							 		</select>
							 	</td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">发送时间从 ：<input type="text"  type="text" name="showStartStartDate"  value="<#if dis??><#if dis.showStartStartDate??>${dis.showStartStartDate}</#if></#if>" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'showStartEndDate\')}'})" id="showStartStartDate" ></td>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">&nbsp;&nbsp;至：<input type="text" type="text" name="showStartEndDate" class="Wdate"  value="<#if dis??><#if dis.showStartEndDate??>${dis.showStartEndDate}</#if></#if>"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'showStartStartDate\')}'})" id="showStartEndDate"></td>
								
							</tr>
							<tr height="10px">
							</tr>
							<tr>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">
							 	&nbsp;&nbsp;&nbsp;&nbsp;状态：
							 	<select name="state" id="state" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if dis??><#if dis.state??><#if (dis.state == 0)>selected = "selected" </#if></#if></#if>>未发送</option>
							 			<option value="1"  <#if dis??><#if dis.state??><#if (dis.state == 1)>selected = "selected" </#if></#if></#if>>已发送</option>
							 	</select>
							 	</td>
							 	<#if (type == 2)>
							 	<td class="right_table_tr_td" heigth="28px" width="25%">
							 	是否显示：
							 	<select name="isShow" id="isShow" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if dis??><#if dis.state??><#if (dis.state == 0)>selected = "selected" </#if></#if></#if>>不显示</option>
							 			<option value="1"  <#if dis??><#if dis.state??><#if (dis.state == 1)>selected = "selected" </#if></#if></#if>>显示</option>
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
							 	</#if>
								<td class="right_table_tr_td" heigth="28px" width="25%">
								<button  onclick="javascript:return searchActivityTypeList();">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
								<#if userSessionInfo?? && userSessionInfo.f['218']?? >
								<input type="button" value="新増"  onclick="javascript:location='/discovery/toAddOrEdit.go?type=${type}'">
								</#if>
								<#if userSessionInfo?? && userSessionInfo.f['219']?? >
								<button type="button" onclick="updateOrder()">保存排名</button>
								</#if>
							</tr>
							<tr style="height:10px;"></tr>
				    </form>
				    
				    </table>
				    
				    </div>
				    <div>
				    <form id="listform" name="listform" method="post" action="/discovery/updateOrder.go">
				    <table class="table2">
		            	<tr>
		                    <th style="width:4%; height:28px;" >序号
		                    <#if (type == 1)>
		                    <input type="checkbox" id='chkAll' name='chkAll'>
		                    </#if>
		                    </th>
		                    <th style="width:10%">正文标题</th>
		                    <th style="width:10%">卡片标题</th>
		                    <th style="width:5%">作者</th>
		                    <#if (type == 2)>
		                    	<th style="width:5%">电子书PID</th>
		                    </#if>
		                    <th style="width:10%">关联电子书名</th>
		                    <th style="width:5%">一级分类</th>
		                    <th style="width:5%">二级分类</th>
		                    <#if (type == 2)>
		                    <th style="width:5%">评星</th>
		                    </#if>
		                    <th style="width:7%">卡片模板</th>
		                    <th style="width:4%">评论数</th>
		                    <th style="width:4%">收藏数</th>
		                    <#if (type == 2)>
		                    <th style="width:4%">分享数</th>
		                    <th style="width:4%">显示状态</th>
		                    </#if>
		                    <#if (type == 1)>
		                    <th style="width:4%">置顶排名</th>
		                    </#if>
		                    <th style="width:4%">操作者</th>
		                    <th style="width:7%">发送时间</th>
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
			    					<td>
			    					<#if (type == 1)>
			    					<input type="hidden"  id="listIds" name="listIds" value="${dis.id?c}" title="不管是否选中，都提交">
			    					<input type="checkbox"  id="listId" name="listId" value="${dis.id?c}">
			    					</#if>
			    					${i}
			    					</td>
					    			<td><#if dis.title??>${dis.title}</#if></td>
					    			<td><#if dis.cardTitle??>${dis.cardTitle}</#if></td>
						      		<td><#if dis.author??>${dis.author}</#if></td>
						      		<#if (type == 2)>
						      		<td><#if dis.mediaId??>${dis.mediaId?c}</#if></td>
						      		</#if>
						      		<td><#if dis.mediaName??>${dis.mediaName}</#if></td>
						      		<td><#if dis.firstCatetoryName??>${dis.firstCatetoryName}</#if></td>
						      		<td><#if dis.secondCatetoryName??>${dis.secondCatetoryName}</#if></td>
						      		<#if (type == 2)>
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
						      		</#if>
						      		<td><#if dis.cardType??>
						      				<#if dis.cardType??><#if (dis.cardType == 0)>
						      					文字
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.cardType == 1)>
						      					图文
						      				</#if></#if>
						      				<#if dis.cardType??><#if (dis.cardType == 2)>
						      					小图
						      				</#if></#if>
						      			</#if>
						      		</td>
						      		<td>
						      			<#if dis.reviewCnt??>
			    							${dis.reviewCnt}
			    						<#else>
			    							0
			    						</#if>
						      		</td>
						      		<td>
						      			<#if dis.collectCnt??>
			    							${dis.collectCnt}
			    						<#else>
			    							0
			    						</#if>
						      		</td>
						      		<#if (type == 2)>
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
						      		</#if>
						      		<#if (type == 1)>
						      		<td><input type="text" size="2"   name="topOrder" editable="true" value="<#if dis.topOrder??>${dis.topOrder?c}</#if>"/></td>
						      		</#if>
						      		<td><#if dis.operator??>${dis.operator}</#if></td>
						      		<td><#if dis.showStartDate??>${dis.showStartDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
									<#if userSessionInfo?? && userSessionInfo.f['218']?? >
						      			<a href="javascript:location='/discovery/toAddOrEdit.go?id=${dis.id?c}'">【修改】</a>&nbsp;
						      		</#if>
									<#if userSessionInfo?? && userSessionInfo.f['220']?? >
										<a href="#" onclick="javascript:del(${type},${dis.id?c});">【删除】</a>&nbsp;
						      		</#if>
									<#if userSessionInfo?? && userSessionInfo.f['221']?? >
										<a href="javascript:setTime(${dis.id?c},${dis.type});">【设置时间】</a>&nbsp;
						      		</#if>
									<#if userSessionInfo?? && userSessionInfo.f['218']?? >
										<#if (type == 2)>
						      			<a href="javascript:setStars(${dis.id?c},${dis.type});">【推荐评星】</a>&nbsp;
						      			</#if>
									</#if>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </form>
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
  <script>
  	
  </script>
</html>
