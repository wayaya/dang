<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我想要列表</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>用户反馈管理&gt;&gt;我想要列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/iwant/list.go" method="post" id="query" name="query">
			      		 <table >
			      		 	<tr style="height: 30px;">
		        				<td style="width:50px; text-align:right">
									书名：
								</td>
								<td style="width:60px; text-align:left">
									<input type="text" name="title" id="title" value="<#if iwant??><#if iwant.title??>${iwant.title}</#if></#if>" style="">
								</td>
								<td style="width:50px; text-align:right">
									作者：
								</td>
								<td style="width:60px; text-align:left">
									<input type="text" name="author" id="author" value="<#if iwant??><#if iwant.author??>${iwant.author}</#if></#if>">
								</td>
								<td style="width:50px; text-align:right">
									演讲者：
								</td>
								<td style="width:60px; text-align:left">
									<input type="text" name="speaker" id="speaker" value="<#if iwant??><#if iwant.speaker??>${iwant.speaker}</#if></#if>">
								</td>
								<td style="width:50px; text-align:right">
									平台：
							 	</td>
							 	<td style="width:60px; text-align:left">
									<select name="platform" id="platform">
							 			<option value="">全部</option>
							 			<option value="DDXS-P" <#if iwant??><#if iwant.platform??><#if (iwant.platform == 'DDXS-P')>selected = "selected" </#if></#if></#if>>当读小说</option>
							 			<option value="TS-P"  <#if iwant??><#if iwant.platform??><#if (iwant.platform == 'TS-P')>selected = "selected" </#if></#if></#if>>听书</option>
							 			<option value="FP-P" <#if iwant??><#if iwant.platform??><#if (iwant.platform == 'FP-P')>selected = "selected" </#if></#if></#if>>翻篇</option>
							 			<option value="DDDS-P"  <#if iwant??><#if iwant.platform??><#if (iwant.platform == 'DDDS-P')>selected = "selected" </#if></#if></#if>>当当读书</option>
							 			<option value="BBTS-P"  <#if iwant??><#if iwant.platform??><#if (iwant.platform == 'BBTS-P')>selected = "selected" </#if></#if></#if>>宝贝听书</option>
							 		</select>
							 	</td>
							</tr>
							<tr style="height: 30px;">
							 	<td style="text-align:right">
									创建时间：
							 	</td>
							 	<td style="text-align:left" colspan=3>
									<input type="text" style="width: 150px;" type="text" name="createTimeStart"  value="<#if iwant??><#if iwant.createTimeStart??>${iwant.createTimeStart}</#if></#if>" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})" id="createTimeStart" >
									到
									<input type="text" style="width: 150px;" type="text" name="createTimeEnd" class="Wdate"  value="<#if iwant??><#if iwant.createTimeEnd??>${iwant.createTimeEnd}</#if></#if>"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'createTimeStart\')}'})" id="createTimeEnd">
							 	</td>
							 	<td>
							 		&nbsp;
							 	</td>
							 	<td>
							 		&nbsp;
							 	</td>
							 	<td>
							 		&nbsp;
							 	</td>
							 	<td style="text-align:left ">
									<button type="submit">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
							 	</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>书名</th>
		                    <th>作者</th>
		                    <th>演讲者</th>
		                    <th>用户编号</th>
		                    <th>联系方式</th>
		                    <th>平台</th>
		                    <th>创建时间</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if pageFinder?? && (pageFinder?size>0)>
			    			<#list pageFinder.data as iwant>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
					    			<td><#if iwant.title??>${iwant.title}</#if></td>
						      		<td><#if iwant.author??>${iwant.author}</#if></td>
						      		<td><#if iwant.speaker??>${iwant.speaker}</#if></td>
						      		<td><#if iwant.custIdStr??>${iwant.custIdStr}</#if></td>
						      		<td><#if iwant.contactInfo??>${iwant.contactInfo}</#if></td>
						      		<td><#if iwant.platformName??>${iwant.platformName}</#if></td>
						      		<td><#if iwant.createTime??>${iwant.createTime?date}</#if></td>
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
 	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo- 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/iwant/list.go?pageIndex=__id__&'+$("#query").serialize())
		    });
	   	});
	   	
	</script>

</html>
