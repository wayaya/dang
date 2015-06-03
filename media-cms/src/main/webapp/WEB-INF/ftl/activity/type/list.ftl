<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		var condition ="";
		$(function(){
			makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/activity/type/list.go?page=__id__'+condition)
		    });
		});
		function makecondition(){
			var creator = $('#creator').val();
			if(creator.length  > 0){
				condition = condition + "&creator="+creator;
			}
			var modifier = $('#modifier').val();
			if(modifier.length  > 0){
				condition = condition + "&modifier="+modifier;
			}
		}
		
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
					<h3><a href="/activity/info/list.go">促销类型管理</a>&nbsp;&gt;&gt;&nbsp;促销类型列表</h3>
					<div class="mrdiv">
		      		<form action="/activity/type/list.go" method="post" id="activity_type_list_form">
			      		 <table >
		        			<tr>
								<td class="right_table_tr_td">创建人：<input type="text" name="creator" id="creator" value="<#if activityType??><#if activityType.creator??>${activityType.creator}</#if></#if>"></td>
							 	<td class="right_table_tr_td">修改人：<input type="text" name="modifier" id="modifier" value="<#if activityType??><#if activityType.modifier??>${activityType.modifier}</#if></#if>"></td>
								<td class="right_table_tr_td"><button  onclick="return searchActivityTypeList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:10%; height:28px;" >促销类型ID</th>
		                    <th style="width:10%">类型名称</th>
		                    <th style="width:10%">生效状态</th>
		                    <th style="width:10%">促销类型编号</th>
		                    <th style="width:6%">创建人</th>
		                    <th style="width:15%">创建时间</th>
		                    <th style="width:6%">修改人</th>
		                    <th style="width:15%">最后修改时间</th>
		                    <th style="width:10%">是否为实时到账</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as activityType>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if activityType.activityTypeId??>${activityType.activityTypeId?c}</#if></td>
						      		<td><#if activityType.activityTypeName??>${activityType.activityTypeName}</#if></td>
						      		<td><#if activityType.status??><#if (activityType.status == 1)>是 <#else>否</#if></#if></td>
						      		<td><#if activityType.activityTypeCode??>${activityType.activityTypeCode}</#if></td>
						      		<td><#if activityType.creator??>${activityType.creator}</#if></td>
						      		<td><#if activityType.creationDate??>${activityType.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if activityType.modifier??>${activityType.modifier}</#if></td>
						      		<td><#if activityType.lastChangedDate??>${activityType.lastChangedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if activityType.isPromptlyToAccount??><#if (activityType.isPromptlyToAccount == 0)>是 <#else>否</#if></#if></td>
						      		<td>
									<#if userSessionInfo?? && userSessionInfo.f['230']?? >
										<a href="/activity/type/modify.go?activityTypeId=<#if activityType.activityTypeId??>${activityType.activityTypeId?c}</#if>">更新</a>
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
	<#include "../../common/common-js.ftl">
  </body>
</html>
