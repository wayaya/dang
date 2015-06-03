<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">
		function searchActivityInfoList(){
	   		$('#activity_info_list_form').submit();
	   	}
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		var condition ="";
		$(function(){
			makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/activity/info/list.go?page=__id__'+condition)
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
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3><a href="/activity/info/list.go">促销信息管理</a>&nbsp;&gt;&gt;&nbsp;促销信息列表</h3>
					<div class="mrdiv">
		      		<form action="/activity/info/list.go" method="post" id="activity_info_list_form">
			      		 <table >
		        			<tr>
								<td class="right_table_tr_td">添加人：<input type="text" name="creator" id="creator" value="<#if activityInfo??><#if activityInfo.creator??>${activityInfo.creator}</#if></#if>"></td>
							 	<td class="right_table_tr_td">修改人：<input type="text" name="modifier" id="modifier" value="<#if activityInfo??><#if activityInfo.modifier??>${activityInfo.modifier}</#if></#if>"></td>
							 	<td class="right_table_tr_td">
							 		促销方案：<select id="activityTypeId" name="activityTypeId" >
					    					<option value="">请选择</option>
					    					<#if activityTypeList??>
			    								<#list activityTypeList as activityType>
							    					<option value="${activityType.activityTypeId?c}" <#if activityInfo??><#if activityInfo.activityTypeId??><#if activityInfo.activityTypeId == activityType.activityTypeId>selected="selected"</#if></#if></#if>>${activityType.activityTypeName}</option>
							    				</#list>
				      						</#if>
					    				</select>
							 	</td>
								<td class="right_table_tr_td"><button  onclick="return searchActivityInfoList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:3%; height:28px;" >ID</th>
		                    <th style="width:12%">名称</th>
		                    <th style="width:10%">开始时间</th>
		                    <th style="width:10%">结束时间</th>
		                    <th style="width:5%">方案名称</th>
		                    <th style="width:10%">方案编号</th>
		                    <th style="width:5%">包月类型</th>
		                    <th style="width:3%">包月渠道</th>
		                    <th style="width:5%">创建人</th>
		                    <th style="width:10%">创建时间</th>
		                    <th style="width:5%">修改人</th>
		                    <th style="width:10%">修改时间</th>
		                    <th style="width:3%">状态</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as activityInfo>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if activityInfo.activityId??>${activityInfo.activityId?c}</#if></td>
						      		<td><#if activityInfo.activityName??>${activityInfo.activityName}</#if></td>
						      		<td><#if activityInfo.startTime??>${activityInfo.startTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if activityInfo.endTime??>${activityInfo.endTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
						      		<#list activityTypeList as activityType>
				    					<#if activityInfo??><#if activityInfo.activityTypeId??><#if activityInfo.activityTypeId == activityType.activityTypeId>${activityType.activityTypeName}</#if></#if></#if>
				    				</#list>
						      		</td>
						      		<td><#if activityInfo.activityTypeCode??>${activityInfo.activityTypeCode}</#if></td>
						      		<td>
							      		<#if activityInfo??><#if activityInfo.monthlyPaymentType??><#if activityInfo.monthlyPaymentType=="1001">全场包月</#if></#if></#if>
							      		<#if activityInfo??><#if activityInfo.monthlyPaymentType??><#if activityInfo.monthlyPaymentType=="1002">按栏目包月</#if></#if></#if>
							      		<#if activityInfo??><#if activityInfo.monthlyPaymentType??><#if activityInfo.monthlyPaymentType=="1003">按分类包月</#if></#if></#if>
						      		</td>
						      		<td>
						      			<#if activityInfo??><#if activityInfo.monthlyBuyOrGive??><#if activityInfo.monthlyBuyOrGive==0>购买</#if></#if></#if>
						      			<#if activityInfo??><#if activityInfo.monthlyBuyOrGive??><#if activityInfo.monthlyBuyOrGive==1>赠送</#if></#if></#if>
						      		</td>
						      		<td><#if activityInfo.creator??>${activityInfo.creator}</#if></td>
						      		<td><#if activityInfo.creationDate??>${activityInfo.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if activityInfo.modifier??>${activityInfo.modifier}</#if></td>
						      		<td><#if activityInfo.lastChangedDate??>${activityInfo.lastChangedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if activityInfo.status??><#if (activityInfo.status == 1)>有效 <#else>无效</#if></#if></td>
						      		<td>
									<#if userSessionInfo?? && userSessionInfo.f['237']?? >
					      				<a href="/activity/info/modify.go?activityId=<#if activityInfo.activityId??>${activityInfo.activityId?c}</#if>">更新</a>
									</#if>
					      				&nbsp;&nbsp;
					      				<#if activityInfo.activityTypeId??><#if activityInfo.activityTypeId == 1006 || activityInfo.activityTypeId == 1013><a href="/activity/info/manageproduct.go?activityId=<#if activityInfo.activityId??>${activityInfo.activityId?c}</#if>">管理商品</a></#if></#if>
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
