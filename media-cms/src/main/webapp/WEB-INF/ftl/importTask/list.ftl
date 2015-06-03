<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
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
			
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c}-1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/importTask/list.go?page=__id__'+makecondition())
		    });
		});
		function makecondition(){
			var lefttab = $('#lefttab').val();
			if(lefttab.length  > 0){
				condition = condition + "&lefttab="+lefttab;
			}
			var chapterName = $('#chapterName').val();
			if(chapterName.length  > 0){
				condition = condition + "&chapterName="+chapterName;
			}
			var status = $('#status').val();
			if(status.length  > 0){
				condition = condition + "&status="+status;
			}
			var mediaName = $('#mediaName').val();
			if(mediaName.length  > 0){
				condition = condition + "&mediaName="+mediaName;
			}
			
			var uid = $('#uid').val();
			if(uid.length  > 0){
				condition = condition + "&uid="+uid;
			}
			return condition;
		}
		
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	   	function repeatImport(){
	   		var val = $('[name=taskId]:checked');
	   		if(val.length ==0){
	   			alert('请选择要重新入库的章节！');
	   			return;
	   		}
	   		var ids = new Array();
	   		for(var i=0;i<val.length;i++){
	   			ids.push(val[i].value);
	   		}
	   		if(val){
	   			location = '/importTask/repeatImport.go?ids='+ids;
	   		}
	   	}
	   	
	   	function showDetail(mediaId){
	   		$.dialog({title:'',content:'url:/media/toBaseInfo.go?mediaId='+mediaId,
	   		icon:'succeed',width:1000,height:700,fixed:false,lock:true
			});
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>入库任务管理&gt;&gt;入库任务管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/importTask/list.go" method="post" id="activity_type_list_form">
			      		 
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td" style="width:250px;">章节名称：<input type="text" name="chapterName" id="chapterName" value="<#if task??><#if task.chapterName??>${task.chapterName}</#if></#if>"></td>
								<td class="right_table_tr_td">书名：<input type="text" name="mediaName" id="mediaName" value="<#if task??><#if task.mediaName??>${task.mediaName}</#if></#if>"></td>
							 	<td class="right_table_tr_td">UID：<input type="text" name="uid" id="uid" value="<#if task??><#if task.uid??>${task.uid}</#if></#if>"></td>
							 	<td class="right_table_tr_td">状态：
							 		<select name="status" id="status">
							 			<option value="">全部</option>
							 			<option value="0" <#if task??><#if task.status??><#if (task.status == "0")>selected = "selected" </#if></#if></#if>>未执行</option>
							 			<option value="1"  <#if task??><#if task.status??><#if (task.status == "1")>selected = "selected" </#if></#if></#if>>等待执行</option>
							 			<option value="2"  <#if task??><#if task.status??><#if (task.status == "2")>selected = "selected" </#if></#if></#if>>执行中</option>
							 			<option value="3"  <#if task??><#if task.status??><#if (task.status == "3")>selected = "selected" </#if></#if></#if>>已完成</option>
							 			<option value="-1"  <#if task??><#if task.status??><#if (task.status == "-1")>selected = "selected" </#if></#if></#if>>执行失败</option>
							 		</select>
							 	</td>
								<td class="right_table_tr_td"><button  onclick="return searchActivityTypeList()">查询</button>
								<#if userSessionInfo?? && userSessionInfo.f['144']?? >
									<input type="button"  onclick="javascript:repeatImport();" value="重新入库">
								</#if>
								<td >&nbsp;</td>
							</tr>
						
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" ><input type="checkbox" id="chkAll" name="chkAll">全选</th>
		                    <th style="width:5%">标题</th>
		                    <th style="width:5%">卷名</th>
		                    <th style="width:10%">章节名</th>
		                    <th style="width:5%">UID</th>
		                    <th style="width:10%">路径</th>
		                    <th style="width:5%">状态</th>
		                    <th style="width:15%">错误信息</th>
		                    <th style="width:10%">创建日期</th>
		                    <th style="width:10%">完成日期</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as task>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td><input type="checkbox" value="${task.taskId?c}" name='taskId'></td>
					    			<td><#if task.mediaName??>${task.mediaName}</#if></td>
						      		<td><#if task.volumeName??>${task.volumeName}</#if></td>
						      		<td><#if task.chapterName??>${task.chapterName}</#if></td>
						      		<td><#if task.uid??>${task.uid}</#if></td>
						      		<td><#if task.path??>${task.path}</#if></td>
						      		<td><#if task.status??><#if (task.status == "0")>未执行 
						      		<#elseif (task.status =="1")>等待执行
						      		<#elseif (task.status =="2")>执行中
						      		<#elseif (task.status =="3")>已完成
						      		<#elseif (task.status =="4")>重复
						      		<#else>执行失败</#if>
						      		</#if></td>
						      		<td><#if task.error??>${task.error}</#if></td>
						      		<td><#if task.creationDate??>${task.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if task.finishDate??>${task.finishDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
					      				<a href="javascript:void(0)" onclick="showDetail(${task.mediaId?c});">【查看图书】</a>
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
$(document).ready(function(){
		$('#chkAll').click(function(){
			if(this.checked){
				 $('[name=taskId]:checkbox').attr("checked", true);
			}else{
				 $('[name=taskId]:checkbox').attr("checked", false);
			}
		});
});
</script>
</html>
