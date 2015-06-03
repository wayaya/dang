<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>公告类型列表</title>
	 <script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>公告管理&gt;&gt;公告类型节点列表</h3>
				    <div>
				     <form id="listform" name="listform" method="post">
				     <input type="hidden" id="categoryCode" name="categoryCode" value="${categoryCode}"/>
				    <table class="table2">
		            	<tr>
		                    <th>序号<input type="checkbox" id='chkAll' name='chkAll'></th>
		                    <th>名称</th>
		                    <th>标识</th>
		                    <th>显示位置</th>
		                    <th>开始时间</th>
		                    <th>结束时间</th>
		                    <th>创建人</th>
		                    <th>创建时间</th>
		                    <th>操作者</th>
		                    <th>最后修改时间</th>
		                    <th>修改</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	 <#if announcementsCategory??>
	              	 	 <#if (announcementsCategory?size>0)>
			    			<#list announcementsCategory as category>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>
					    			<input type="checkbox"  id="categoryId" name="categoryId" value="${category.categoryId?c}">${i}</td>
						      		<td><#if category.categoryName??>${category.categoryName}</#if></td>
						      		<td><#if category.categoryCode??>${category.categoryCode}</#if></td>
						      		<td><#if category.position??>${category.position}</#if></td>
						      		<td><#if category.startDate??>${category.startDate}</#if></td>
						      		<td><#if category.endDate??>${category.endDate}</#if></td>
						      		<td><#if category.creator??>${category.creator}</#if></td>
						      		<td><#if category.createDate??>${category.createDate}</#if></td>
						      		<td><#if category.lastChangeDate??>${category.lastChangeDate}</#if></td>
						      		<td><#if category.modifer??>${category.modifer}</#if></td>
						      		<td><a href="#" onclick="javascript:edit(${category.categoryId?c});" >修改</a></td>
						      	</tr>
				      		</#list>
				      	</#if>
				      	</#if>
		            </table>
		            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
  </body>
</html>
