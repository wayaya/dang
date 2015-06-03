<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>活动参与明细</title>
	<#include "../../common/common-css.ftl">
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
				link_to: encodeURI('/activityUser/record.go?page=__id__'+"&"+$("#list_form").serialize())
		    });
		});
		function makecondition(){
			var lefttab = $('#lefttab').val();
			if(lefttab.length  > 0){
				condition = condition + "&lefttab="+lefttab;
			}
		}
		
	   	function searchList(){
	   		$('#list_form').submit();
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>活动用户管理&gt;&gt;参与明细</h3>
					<div class="mrdiv">
		      		<form action="/activityUser/record.go" method="post" id="list_form" >
			      		 <table >	
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">用户昵称：<input type="text" name="username" id="username" value="<#if prize??><#if prize.username??>${prize.username}</#if></#if>"></td>
								<td class="right_table_tr_td">用户ID：<input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" name="custId" id="custId" value="<#if prize??><#if prize.custId??>${prize.custId?c}</#if></#if>"></td>
								<td class="right_table_tr_td">奖品ID：<input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" name="prizeId" id="prizeId" value="<#if prize??><#if prize.prizeId??>${prize.prizeId?c}</#if></#if>"></td>
								<td class="right_table_tr_td">活动类型：
									<select name="activityType">
				    					<option value=""  <#if prize??><#if prize.activityType??><#if prize.activityType?c=="">selected="selected"</#if></#if></#if>>请选择</option>
				    					<option value="1" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="1">selected="selected"</#if></#if></#if>>抽奖</option>
				    					<option value="2" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="2">selected="selected"</#if></#if></#if>>膜拜</option>
				    					<option value="3" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="3">selected="selected"</#if></#if></#if>>分享</option>
				    					<option value="4" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="4">selected="selected"</#if></#if></#if>>红包</option>
				    					<option value="5" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="5">selected="selected"</#if></#if></#if>>每日送福袋</option>
				    					<option value="6" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="6">selected="selected"</#if></#if></#if>>掉钱袋啦</option>
				    					<option value="7" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="7">selected="selected"</#if></#if></#if>>登陆送3天包月</option>
				    					<option value="8" <#if prize??><#if prize.activityType??><#if prize.activityType?c=="8">selected="selected"</#if></#if></#if>>分享送3天包月</option>
								 	</select>
								
							</tr>
							<tr>	
								<td class="right_table_tr_td">奖品类型：
								 	<select name="prizeType">
				    					<option value=""  <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="">selected="selected"</#if></#if></#if>>请选择</option>
				    					<option value="1" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="1">selected="selected"</#if></#if></#if>>金币</option>
				    					<option value="2" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="2">selected="selected"</#if></#if></#if>>图书章节</option>
				    					<option value="3" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="3">selected="selected"</#if></#if></#if>>道具</option>
				    					<option value="4" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="4">selected="selected"</#if></#if></#if>>包月</option>
								 	</select>
								</td>
								<td class="right_table_tr_td">奖品名：<input type="text" name="prizeName" id="prizeName" value="<#if select??><#if select.prizeName??>${select.prizeName}</#if></#if>"></td>
						<#--		<td class="right_table_tr_td">归属：
									<select name="prizeVestType" id="prizeVestType">
										<option value="<#if select??><#if select.prizeVestType??>${select.prizeVestType}</#if></#if>">福袋</option>
									<select>
								</td>
						-->			
							</tr>
							<tr>										
								<td class="right_table_tr_td"  rowspan="2">被膜拜用户昵称：<input type="text" name="worshipedUsername" id="worshipedUsername" value="<#if select??><#if select.worshipedUsername??>${select.worshipedUsername}</#if></#if>"></td>
								<td class="right_table_tr_td">被膜拜用户ID：<input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" name="worshipedCustId" id="worshipedCustId" value="<#if select??><#if select.worshipedCustId??>${select.worshipedCustId?c}</#if></#if>"></td>
								<td class="right_table_tr_td"><button  onclick="return searchList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:5%">用户昵称</th>
		                    <th style="width:5%">用户ID</th>
		                    <th style="width:5%">奖品ID</th>
		                    <th style="width:5%">活动类型</th>
		                    <th style="width:5%">奖品类型</th>
		                    <th style="width:5%">奖品名</th>
		                    <th style="width:5%">数量</th>
		                    <th style="width:5%">归属</th>
		                    <th style="width:10%">被膜拜用户昵称</th>
		                    <th style="width:10%">被膜拜用户ID</th>
		                    <th style="width:12%">创建时间</th>
	               	    </tr>
	               	   <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as one>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if one.mediaActivityRecordId??>${one.mediaActivityRecordId?c}</#if></td>
						      		<td><#if one.username??>${one.username}</#if></td>
						      		<td><#if one.custId??>${one.custId?c}</#if></td>
						      		<td><#if one.prizeId??>${one.prizeId?c}</#if></td>
						      		<td><#if one.activityType??>
						      			<#if one.activityType?c=="1">
						      				抽奖
						      			<#elseif one.activityType?c=="2">
						      				膜拜
						      			<#elseif one.activityType?c=="3">
						      				分享
						      			<#elseif one.activityType?c=="4">
						      				红包
						      			<#elseif one.activityType?c=="5">
						      				每日送福袋	
						      			<#elseif one.activityType?c=="6">
						      				天降鸿运	
					      				<#elseif one.activityType?c=="7">
					      					登陆送3天包月
					      				<#elseif one.activityType?c=="8">
					      					分享送3天包月		
						      			<#else>
						      			   ${one.activityType?c}
						      			</#if>
						      		  </#if>
						      		</td>
						      		<td><#if one.prizeType??&&one.activityType??>
						      		  <#if one.activityType?c=="1">
						      			<#if one.prizeType?c=="1">
						      				金币
						      			<#elseif one.prizeType?c=="2">
						      				图书章节
						      			<#elseif one.prizeType?c=="3">
						      				道具
						      			<#elseif one.prizeType?c=="4">
						      				包月
						      			<#else>
						      			   ${one.prizeType?c}
						      			</#if>
						      		  </#if>
						      		  </#if>
						      		</td>
						      		<td><#if one.prizeName??>${one.prizeName}</#if></td>
						      		<td><#if one.amount??>${one.amount?c}</#if></td>
						      		<td><#if one.prizeVestType??><#if one.prizeVestType?c=="1">福袋<#else>${one.prizeVestType?c}</#if></#if></td>
						      		<td><#if one.worshipedUsername??>${one.worshipedUsername}</#if></td>
						      		<td><#if one.worshipedCustId??>${one.worshipedCustId?c}</#if></td>
						      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
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

