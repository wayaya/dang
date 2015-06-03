<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/pullChapter/list.go?cpMediaId='+${cpMediaId}+'&page=__id__'+makecondition())
		    });
		});
		function makecondition(){
			var chapterName = $("#chapterName").val()
			if(chapterName.length  > 0){
				condition = condition + "&chapterName="+chapterName;
			}
			return condition;
		}
		
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	   	
	   	function showDetail(id){
	   		$.dialog({id:"cateDialog",title:'同步明细',content:'url:/pullChapter/content.go?id='+id,
	   		width:300,height:200,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
                return true;
            },
            focus: true
        }]
			});
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>CP同步书籍日志管理&gt;&gt;CP同步书籍章节日志管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/pullChapter/list.go" method="post" id="activity_type_list_form">
			      		 
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">
								标题：<input type="text" name="chapterName" id="chapterName" value="<#if log??><#if log.chapterName??>${log.chapterName}</#if></#if>">
								</td>
								<td class="right_table_tr_td"><button  onclick="return searchActivityTypeList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:10%">CP</th>
		                    <th style="width:5%">标题</th>
		                    <th style="width:5%">排序</th>
		                    <th style="width:10%">创建时间</th>
		                    <th style="width:15%">更新时间</th>
		                    <th style="width:15%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as log>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td>${i}</td>
					    			<td><#if log.cpCode??>${log.cpCode}</#if></td>
					    			<td><#if log.chapterName??>${log.chapterName}</#if></td>
					    			<td><#if log.orderNum??>${log.orderNum}</#if></td>
						      		<td><#if log.creationDate??>${log.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if log.lastModifyDate??>${log.lastModifyDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
						      			<a href="javascript:showDetail('${log.id?c}');">【明细】</a><br>
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
</html>
