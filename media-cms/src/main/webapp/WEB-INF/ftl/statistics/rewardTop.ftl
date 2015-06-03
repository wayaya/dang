<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>数据统计</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>数据统计&gt;&gt;打赏金额top100</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/statistics/rewardTop.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>
								开始日期：<input type="text" id="startDate" name="startDate" class="Wdate" type="text" onFocus="WdatePicker()" value="<#if RequestParameters.startDate??>${RequestParameters["startDate"]}</#if>">
								&nbsp;&nbsp;结束日期：<input type="text" id="endDate" name="endDate" class="Wdate" type="text" onFocus="WdatePicker()" value="<#if RequestParameters.endDate??>${RequestParameters["endDate"]}</#if>">
								&nbsp;&nbsp;查询数量：<input type="text" id="count" name="count" type="text" value="<#if RequestParameters.count??>${RequestParameters["count"]}</#if>">
								<button  type="submit" >查询</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
				    	<tr>
				    		<th style="width:3%">序号</th>
		                    <th>书名</th>
		                    <th>总额</th>		                 
	               	    </tr>
	               	    <#assign i = 0>
	               	    <#if rewardTopList??>
	               	    <#list rewardTopList as rewardTop>
	               	    <#assign i = i+1>
	               	    <tr>
	               	    	<td>${i}</td>     	    
					    	<td><#if rewardTop.mediaName??>${rewardTop.mediaName}</#if></td>
					    	<td><#if rewardTop.totalCons??>${rewardTop.totalCons}</#if></td>						    
						</tr>
						</#list>
						</#if>
		            </table>
		            </div>
			    </div>
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
		
	</script>

</html>
