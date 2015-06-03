<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript"></script>
</head>   
  <body>
	 <div style="width:90%;margin:0 auto;">
	 	<br/><br/>
	    <table class="table2">
        	<tr style="height:28px;">
                <th>用户消费明细ID</th>
                <th>用户消费ID</th>
                <th>作品ID</th>
                <th>作品名称</th>
                <th>章节数量</th>
                <th>道具ID</th>
                <th>道具名称</th>
                <th>主支付金额</th>
                <th>副支付金额</th>
                <th>道具数量</th>
       	    </tr>
       	    <#assign i = 0>
       	 	<#if consumerConsume?? && consumerConsume.consumeDetails??>
    			<#list consumerConsume.consumeDetails as detail>
		    		<#assign i = i+1>
    				<#if i%2 == 0>
    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
    				<#else>
    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
    				</#if>
    					<td><#if detail.consumerConsumeDeatilId??>${detail.consumerConsumeDeatilId?c}</#if></td>
		    			<td><#if detail.consumerConsumeId??>${detail.consumerConsumeId?c}</#if></td>
			      		<td><#if detail.mediaId??>${detail.mediaId?c}</#if></td>
			      		<td><#if detail.mediaName??>${detail.mediaName}</#if></td>			      		
			      		<td><#if detail.mediaConsumeChapterNum??>${detail.mediaConsumeChapterNum}</#if></td>
			      		<td><#if detail.propId??>${detail.propId?c}</#if></td>
			      		<td><#if detail.propName??>${detail.propName}</#if></td>
			      		<td><#if detail.mainGoldPrice??>${detail.mainGoldPrice?c}</#if></td>
			      		<td><#if detail.subGoldPrice??>${detail.subGoldPrice?c}</#if></td>
			      		<td><#if detail.propCount??>${detail.propCount}</#if></td>		      		
			      	</tr>
	      		</#list>
	      	</#if>
        </table>
        </div>
	<#include "../common/common-js.ftl">
  </body>
</html>
