<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;height:100%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>榜单列表</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>榜单管理&gt;&gt;榜单列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
				    </div>
				    <div>
				    <table class="table2" style="hegiht:90%">
		            	<tr>
		                    <th>序号</th>
		                    <th>榜单名称</th>
		                    <th>榜单标识</th>
		                    <th>最少数量</th>
		                    <th>最多数量</th>
		                    <th>统计周期(单位/天)</th>
		                    <th>创建人</th>
		                    <th>创建日期</th>
		                    <th>操作者</th>
		                    <th>修改日期</th>
		                    <th>操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	 <#if listCategory??>
			    			<#list listCategory as bean>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
						      		<td><#if bean.categoryName??>${bean.categoryName}</#if></td>
						      		<td><#if bean.categoryCode??>${bean.categoryCode}</#if></td>
						      		<td><#if bean.minRecords??>${bean.minRecords}</#if></td>
						      		<td><#if bean.maxRecords??>${bean.maxRecords}</#if></td>
						      		<td><#if bean.days??>${bean.days}</#if></td>
						      		<td><#if bean.creator??>${bean.creator}</#if></td>
						      		<td><#if bean.createDate??>${bean.createDate?substring(0,19)}</#if></td>
						      		<td><#if bean.modifer??>${bean.modifer}</#if></td>
						      		<td><#if bean.lastChangeDate??>${bean.lastChangeDate?substring(0,19)}</#if></td>
						      		<td><a href="/ranking/edit.go?id=${bean.categoryId?c}"> 重命名</a>&nbsp;
						      		<!-- <a href="/ranking/edit.go?id=${bean.categoryId?c}"> 作品管理</a> -->
						      		</td>
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
  	<#include "../common/common-js.ftl">
	</html>
