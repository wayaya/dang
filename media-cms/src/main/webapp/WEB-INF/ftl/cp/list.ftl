<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>CP管理</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>CP管理&gt;&gt;CP列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/cp/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>CP名称：<input type="text" name="name" id="name" >
								<button  type="submit" >查询</button>
								<button type="button" onclick="window.location='/cp/add.go'">添加CP</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>编号</th>
		                    <th>名称</th>
		                    <th>组织机构代码</th>
		                    <th>法人代表</th>
		                    <th>联系人</th>
		                    <th>地址</th>
		                    <th>团队/个人</th>
		                    <th>操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as cp>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
					    			<td><#if cp.cpId??>${cp.cpId?c}</#if></td>
						      		<td><#if cp.cnName??>${cp.cnName}</#if></td>
						      		<td><#if cp.organizationCode??>${cp.organizationCode}</#if></td>
						      		<td><#if cp.legalPerson??>${cp.legalPerson}</#if></td>
						      		<td><#if cp.contractor??>${cp.contractor}</#if></td>
						      		<td><#if cp.address??>${cp.address}</#if></td>
						      		<td><#if cp.type??>${cp.type}</#if></td>
						      		<td><a href="/cp/contract/list.go?cpId=${cp.cpId?c}">合同查阅</a>&nbsp;&nbsp;
						      		<a href="/cp/contract/add.go?cpId=${cp.cpId?c}&cnName=${cp.cnName}">添加合同</a>&nbsp;&nbsp;
						      		 <a href="/cp/detail.go?cpId=${cp.cpId?c}">详情</a>&nbsp;&nbsp;<a href="/cp/edit.go?cpId=${cp.cpId}">修改</a>&nbsp;&nbsp;<a onclick="return hintDelete();" href="/cp/delete.go?cpId=${cp.cpId}">【删除】</a>
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
  </body>
</html>
