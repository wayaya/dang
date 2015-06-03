<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>CP合同管理</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>CP管理&gt;&gt;CP列表</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/cp/contract/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>CP名称：<input type="text" name="cpName" id="cpName" >
								<button  type="submit" >查询</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>合同编号</th>
		                    <th>CP名称</th>
		                    <th>合同开始时间</th>
		                    <th>合同结束时间</th>
		                    <th>内容分成比例</th>
		                    <th>运营扣减比例</th>
		                    <th>其它扣减比例</th>
		                    <th>支付周期(月)</th>
		                    <th>内容免费比例</th>
		                    <th>创建人</th>
		                    <th>状态</th>
		                    <th>操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as contract>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
					    			<td><#if contract.contractCode??>${contract.contractCode}</#if></td>
						      		<td><#if contract.cpName??>${contract.cpName}</#if></td>
						      		<td><#if contract.startDate??>${contract.startDate}</#if></td>
						      		<td><#if contract.endDate??>${contract.endDate}</#if></td>
						      		<td><#if contract.contentRatio??>${contract.contentRatio?c}</#if></td>
						      		<td><#if contract.manageRatio??>${contract.manageRatio?c}</#if></td>
						      		<td><#if contract.otherRadtio??>${contract.otherRadtio?c}</#if></td>
						      		<td><#if contract.payCycle??>${contract.payCycle?c}</#if></td>
						      		<td><#if contract.freeRatio??>${contract.freeRatio?c}</#if></td>
						      		<td><#if contract.creator??>${contract.creator}</#if></td>
						      		<td><#if contract.status??>${contract.status}</#if></td>
						      		<td>
						      		<a href="/cp/contract/detail.go?contractId=${contract.contractId?c}">合同详情</a>&nbsp;&nbsp; 
						      		<a href="/cp/contract/audit.go?contractId=${contract.contractId?c}">合同审核</a>&nbsp;&nbsp; 
						      		<a href="/cp/contract/edit.go?contractId=${contract.contractId?c}">修改</a>&nbsp;&nbsp;
						      		<a onclick="return hintDelete();" href="/cp/contract/delete.go?contractId=${contract.contractId?c}">【删除】</a></td>
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
	<#include "../../common/common-js.ftl">
  </body>
</html>
