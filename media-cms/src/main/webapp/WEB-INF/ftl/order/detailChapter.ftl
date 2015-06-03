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
                <th>订单章节明细ID</th>
                <th>订单明细ID</th>
                <th>销售实体关联id</th>
                <th style="width=18%">作品名称</th>
                <th>作品ID</th>
                <th style="width=6%">章节号</th>
                <th style="width=6%">章节价格</th>
                <th style="width=6%">主支付金额</th>
                <th style="width=6%">副支付金额</th>
                <th style="width=6%">供应商参与分成金额</th>
                <th style="width=6%">优惠金额</th>
                <th style="width=6%">赠送积分</th>
                <th>活动ID</th>
       	    </tr>
       	    <#assign i = 0>
       	 	<#if detailChapters??>
    			<#list detailChapters as detailChapter>
		    		<#assign i = i+1>
    				<#if i%2 == 0>
    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
    				<#else>
    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
    				</#if>
    					<td><#if detailChapter.orderDetailChapterId??>${detailChapter.orderDetailChapterId?c}</#if></td>
		    			<td><#if detailChapter.orderDetailId??>${detailChapter.orderDetailId?c}</#if></td>
			      		<td><#if detailChapter.saleInfoRelationId??>${detailChapter.saleInfoRelationId?c}</#if></td>
			      		<td><#if detailChapter.mediaName??>${detailChapter.mediaName}</#if></td>
			      		<td><#if detailChapter.mediaId??>${detailChapter.mediaId?c}</#if></td>
			      		<td><#if detailChapter.chapterNo??>${detailChapter.chapterNo}</#if></td>
			      		<td><#if detailChapter.chapterPrice??>${detailChapter.chapterPrice?c}</#if></td>
			      		<td><#if detailChapter.payMainPrice??>${detailChapter.payMainPrice?c}</#if></td>
			      		<td><#if detailChapter.paySubPrice??>${detailChapter.paySubPrice?c}</#if></td>
			      		<td><#if detailChapter.vspPrice??>${detailChapter.vspPrice?c}</#if></td>
			      		<td><#if detailChapter.prePrice??>${detailChapter.prePrice?c}</#if></td>
			      		<td><#if detailChapter.givingPoint??>${detailChapter.givingPoint?c}</#if></td>
			      		<td><#if detailChapter.activeId??>${detailChapter.activeId?c}</#if></td>		      		
			      	</tr>
	      		</#list>
	      	</#if>
        </table>
        </div>
	<#include "../common/common-js.ftl">
  </body>
</html>
