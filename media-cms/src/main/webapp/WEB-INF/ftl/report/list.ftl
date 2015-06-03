<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>举报列表</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>用户反馈管理&gt;&gt;举报列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/report/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr style="height: 30px;">
								<td style="width:80px; text-align:right">
									平台：
							 	</td>
							 	<td style="width:100px; text-align:left">
									<select name="platform" id="platform">
							 			<option value="">全部</option>
							 			<option value="DDXS-P" <#if report??><#if report.platform??><#if (report.platform == 'DDXS-P')>selected = "selected" </#if></#if></#if>>当读小说</option>
							 			<option value="TS-P"  <#if report??><#if report.platform??><#if (report.platform == 'TS-P')>selected = "selected" </#if></#if></#if>>听书</option>
							 			<option value="FP-P" <#if report??><#if report.platform??><#if (report.platform == 'FP-P')>selected = "selected" </#if></#if></#if>>翻篇</option>
							 			<option value="DDDS-P"  <#if report??><#if report.platform??><#if (report.platform == 'DDDS-P')>selected = "selected" </#if></#if></#if>>当当读书</option>
							 			<option value="BBTS-P"  <#if iwant??><#if iwant.platform??><#if (iwant.platform == 'BBTS-P')>selected = "selected" </#if></#if></#if>>宝贝听书</option>
							 		</select>
							 	</td>
							 	<td style="width:80px; text-align:right">
									举报类型：
							 	</td>
							 	<td style="width:100px; text-align:left">
									<select name="reportType" id="reportType">
							 			<option value="">全部</option>
							 			<option value="WGNR" <#if report??><#if report.reportType??><#if (report.reportType == 'WGNR')>selected = "selected" </#if></#if></#if>>违规内容</option>
							 			<option value="GG"  <#if report??><#if report.reportType??><#if (report.reportType == 'GG')>selected = "selected" </#if></#if></#if>>广告</option>
							 			<option value="YZ" <#if report??><#if report.reportType??><#if (report.reportType == 'YZ')>selected = "selected" </#if></#if></#if>>音质</option>
							 			<option value="BQ"  <#if report??><#if report.reportType??><#if (report.reportType == 'BQ')>selected = "selected" </#if></#if></#if>>版权</option>
							 		</select>
							 	</td>
							 	<td style="text-align:right">
									举报时间：
							 	</td>
							 	<td style="text-align:left" colspan=3>
									<input type="text" style="width: 150px;" type="text" name="createTimeStart"  value="<#if report??><#if report.createTimeStart??>${report.createTimeStart}</#if></#if>" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})" id="createTimeStart" >
									到
									<input type="text" style="width: 150px;" type="text" name="createTimeEnd" class="Wdate"  value="<#if report??><#if report.createTimeEnd??>${report.createTimeEnd}</#if></#if>"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'createTimeStart\')}'})" id="createTimeEnd">
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
		                    <th width="4%">序号</th>
		                    <th width="13%">书名</th>
		                    <th width="13%">章节名</th>
		                    <th width="6%">举报类型</th>
		                    <th width="40%">举报内容</th>
		                    <th width="6%">用户编号</th>
		                    <th width="6%">联系方式</th>
		                    <th width="6%">平台</th>
		                    <th width="6%">举报时间</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if pageFinder?? && (pageFinder?size>0)>
			    			<#list pageFinder.data as report>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
					    			<td><#if report.mediaTitle??>${report.mediaTitle}</#if></td>
						      		<td><#if report.chapterTitle??>${report.chapterTitle}</#if></td>
						      		<td><#if report.reportTypeName??>${report.reportTypeName}</#if></td>
						      		<td style="text-align:left"><#if report.reportContent??>${report.reportContent}</#if></td>
						      		<td><#if report.custIdStr??>${report.custIdStr}</#if></td>
						      		<td><#if report.contactInfo??>${report.contactInfo}</#if></td>
						      		<td><#if report.platformName??>${report.platformName}</#if></td>
						      		<td><#if report.createTime??>${report.createTime?date}</#if></td>
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
				link_to: encodeURI('/report/list.go?pageIndex=__id__&'+$("#query").serialize())
		    });
	   	});
	   	
	</script>

</html>
