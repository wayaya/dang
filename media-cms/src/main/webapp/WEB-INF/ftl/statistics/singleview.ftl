<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>运营统计</title>
	<#macro  changeToChannel catetoryPaths>
   		<#if catetoryPaths?contains('NP')&&catetoryPaths?contains('VP')>
         ${"男女频"}	
        <#elseif  catetoryPaths?contains('NP')> ${"男频"}	
        <#elseif  catetoryPaths?contains('VP')> ${"女频"}	
        </#if>
   </#macro>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>运营数据统计&gt;&gt;单品浏览</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/statistics/singleview.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
							<td>开始时间:</td>
							<td> <input id="startTime" name="startTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'})"/> <span id="startTimeInfo"></span></td>
							<td>结束时间:</td>
							<td> <input type="text" id="endTime" name="endTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})"><span id="endTimeInfo"></span></td>
								<td>统计数量：
								<input type="text" name="topn" id="topn">
								<font color='red' size=2>(大数量会很慢)</font></td>
								<td>
								<button  type="submit" >查询</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>	<th>序号</th>
								<th>浏览次数</th>
								<th>免费试读次数</th>
								<th>书名</th>
								<th>作者名</th>
								<th>作者笔名</th>
								<th>分类</th>
								<th>男/女频</th>
								<th>总字数</th>
								<th>收藏次数</th>
								<th>打赏次数</th>
								<th>打赏人数</th>
								<th>打赏总金额</th>
								<th>全本购买次数</th>
								<th>全本购买人数</th>
								<th>全本购买主账户消费</th>
								<th>全本购买辅账户消费</th>
								<th>章节购买次数</th>
								<th>章节购买人数</th>
								<th>章节购买总章节数</th>
								<th>章节购买主账户消费</th>
								<th>章节购买辅账户消费</th>
								<th>全部购买主账户消费</th>
								<th>全部购买辅账户消费</th>
								<th>全部购买全部消费</th>
							</tr>
	               	    <#assign i = 0>
	              	 	 <#if (dataList?size>0)>
			    			<#list dataList as bean>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr  style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr  onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td>${i}</td>
					    			<td><#if bean.count??>${bean.count}</#if></td>
					    			<td><#if bean.freeViewCount??>${bean.freeViewCount}</#if></td>
					    			<td><#if bean.title??>${bean.title}</#if></td>
					    			<td><#if bean.author_name??>${bean.author_name}</#if></td>
					    			<td><#if bean.author_penname??>${bean.author_penname}</#if></td>
					    			<td><#if bean.catetory_name??>${bean.catetory_name}</#if></td>
					    			<td><#if bean.catetory_paths??><@changeToChannel '${bean.catetory_paths}'></@changeToChannel></#if></td>
					    			<td><#if bean.word_cnt??>${bean.word_cnt}</#if></td>
					    			<td><#if bean.store_count??>${bean.store_count}</#if></td>
					    			<td><#if bean.reward_count??>${bean.reward_count}</#if></td>
					    			<td><#if bean.reward_persons??>${bean.reward_persons}</#if></td>
					    			<td><#if bean.reward_sum??>${bean.reward_sum}</#if></td>
					    			<td><#if bean.whole_sale_count??>${bean.whole_sale_count}</#if></td>
					    			<td><#if bean.whole_sale_persons??>${bean.whole_sale_persons}</#if></td>
					    			<td><#if bean.whole_sale_main_money??>${bean.whole_sale_main_money}</#if></td>
					    			<td><#if bean.whole_sale_sub_money??>${bean.whole_sale_sub_money}</#if></td>
					    			<td><#if bean.chapter_sale_count??>${bean.chapter_sale_count}</#if></td>
					    			<td><#if bean.chapter_sale_persons??>${bean.chapter_sale_persons}</#if></td>
					    			<td><#if bean.chapter_sale_chapter_count??>${bean.chapter_sale_chapter_count}</#if></td>
					    			<td><#if bean.chapter_sale_main_money??>${bean.chapter_sale_main_money}</#if></td>
					    			<td><#if bean.chapter_sale_sub_money??>${bean.chapter_sale_sub_money}</#if></td>
					    			<td><#if bean.all_sale_main_money??>${bean.all_sale_main_money}</#if></td>
					    			<td><#if bean.all_sale_sub_money??>${bean.all_sale_sub_money}</#if></td>
					    			<td><#if bean.all_sale_all_money??>${bean.all_sale_all_money}</#if></td>
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
  </body>
	<script type="text/javascript" src="${rc.contextPath}/script/jquery/jquery-1.7.js"></script>
  	<#include "../common/common-js.ftl">
  	<#include "../common/common-css.ftl">
  	</script>
	</html>
