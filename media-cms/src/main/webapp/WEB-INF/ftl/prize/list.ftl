<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>奖品管理&gt;&gt;奖品列表; <font color="red">当前奖品概率总和是：</font><font color="#00DB00"><#if totalPro??>${totalPro}</#if></font>，<font color="red">当前有效奖品：</font><font color="#00DB00"><#if prizeAmount??>${prizeAmount}</#if></font><font color="red">个，确保至少3个有效奖品，不然会导致客户端无法福袋无法抽奖！！！</font></h3>
					<div class="mrdiv">
		      		<form action="/prize/list.go" method="post" id="list_form" >
			      		 <table >
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">奖品名称：<input type="text" name="prizeName"  value="<#if prize??><#if prize.prizeName??>${prize.prizeName}</#if></#if>"></td>
								<td class="right_table_tr_td">奖品类型：
									<select name="prizeType">
				    					<option value=""  <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="">selected="selected"</#if></#if></#if>>请选择</option>
				    					<option value="1" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="1">selected="selected"</#if></#if></#if>>金币</option>
				    					<option value="2" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="2">selected="selected"</#if></#if></#if>>图书章节</option>
				    					<option value="3" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="3">selected="selected"</#if></#if></#if>>道具</option>
				    					<option value="4" <#if prize??><#if prize.prizeType??><#if prize.prizeType?c=="4">selected="selected"</#if></#if></#if>>包月</option>
									</select>
								</td>	
							<#--	
								<td class="right_table_tr_td">类型：
								<select name="vestType">
			    					<option value=""  <#if prize??><#if prize.vestType??><#if prize.vestType?c=="">selected="selected"</#if></#if></#if>>请选择</option>
			    					<option value="1" <#if prize??><#if prize.vestType??><#if prize.vestType?c=="1">selected="selected"</#if></#if></#if>>福袋</option>
								</select>
								</td>
						-->		
								<td class="right_table_tr_td"><button  onclick="return searchList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:3%; height:28px;" >序号</th>
		                  <#--  <th style="width:3%; height:28px;" >主键</th>-->
		                    <th style="width:8%">奖品名称</th>
		                    <th style="width:8%">奖品介绍</th>
		                    <th style="width:3%">概率</th>
		                    <th style="width:4%">日上限</th>
		                    <th style="width:4%">总上限</th>
		                    <th style="width:3%">类型</th>
		                    <th style="width:6%">奖品id</th>
		                    <th style="width:4%">奖品数量</th>
		                    <th style="width:3%">归属</th>
		                    <th style="width:8%">生效时间</th>
		                    <th style="width:8%">失效时间</th>
		                    <th style="width:8%">创建时间</th>
		                    <th style="width:8%">修改时间</th>
		                    <th style="width:5%">创建人</th>
		                    <th style="width:5%">修改人</th>
		                    <th style="width:5%">操作</th>
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
			    					<td>${i}</td>
					    			<#--<td><#if one.mediaLotteryPrizeId??>${one.mediaLotteryPrizeId?c}</#if></td>-->
						      		<td><#if one.prizeName??>${one.prizeName}</#if></td>
						      		<td><#if one.introduce??>${one.introduce}</#if></td>
						      		<td><#if one.probability??>${one.probability?c}</#if></td>
						      		<td><#if one.dayLimit??>${one.dayLimit?c}</#if></td>
						      		<td><#if one.totalLimit??>${one.totalLimit?c}</#if></td>
						      		<td><#if one.prizeType??>
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
						      		</td>
						      		<td><#if one.prizeId??>${one.prizeId?c}</#if></td>
						      		<td><#if one.amount??>${one.amount?c}</#if></td>
						      		<td><#if one.vestType??><#if one.vestType?c=="1">福袋<#else>${one.vestType?c}</#if></#if></td>
						      		<td><#if one.startDate??>${one.startDate}</#if></td>
						      		<td><#if one.endDate??>${one.endDate}</#if></td>
						      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if one.lastModifiedDate??>${one.lastModifiedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if one.creator??>${one.creator}</#if></td>
						      		<td><#if one.modifier??>${one.modifier}</#if></td>
						      		<td>
									<#if userSessionInfo?? && userSessionInfo.f['226']?? >
							      		<a href="/prize/selectOne.go?id=<#if one.mediaLotteryPrizeId??>${one.mediaLotteryPrizeId?c}</#if>">更新</a>
									</#if>
							      	<#--	
							      		&nbsp;
							      		<a onclick="return hintDelete();" href="/prize/delete.go?id=<#if one.mediaLotteryPrizeId??>${one.mediaLotteryPrizeId?c}</#if>">【删除】</a>
							      	-->	
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
<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		var condition ="";
		$(function(){
			$('#startDate').calendar({maxDate:'#endDate',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#endDate').calendar({minDate:'#startDate',format:'yyyy-MM-dd HH:mm:ss'});
			//makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/prize/list.go?page=__id__'+"&"+$("#list_form").serialize())
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
	   	function hintDelete(){
  			return window.confirm('小伙伴，确定要删除吗？删错了可是要找技术同学的。。');
		}
	</script>
  </body>
</html>

