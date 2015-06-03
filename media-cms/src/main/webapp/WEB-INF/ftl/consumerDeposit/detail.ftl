<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">  
		$(function(){
			$("input,select").each(function(){
				$(this).attr("disabled","disabled");
			});	
			<#if errorMsg ?? && errorMsg != ''>
				alert(${errorMsg});
			</#if>		
		}); 				 
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>充值管理&gt;&gt;充值记录列表&gt;&gt;充值记录详情</h3>					
				    <div>
					     <table border="1" bordercolor="#a0c6e5" rules=none>
					     <tr height="35"><td colspan="4"><br/><br/></td></tr>
		    				<tr height="35">
				    			<td align="right">用户充值记录ID：</td><td><input type="text" name="consumerDepositId" id="consumerDepositId" value="<#if consumerDeposit?? && consumerDeposit.consumerDepositId??>${consumerDeposit.consumerDepositId?c}</#if>"></td>
				    			<td align="right">充值金额（分）：</td><td><input type="text" name="money" id="money" value="<#if consumerDeposit?? && consumerDeposit.money??>${consumerDeposit.money?c}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">用户名：</td><td><input type="text" name="userName" id="userName" value="<#if consumerDeposit?? && consumerDeposit.userName??>${consumerDeposit.userName}</#if>"></td>
				    			<td align="right">用户ID：</td><td><input type="text" name="custId" id="custId" value="<#if consumerDeposit?? && consumerDeposit.custId??>${consumerDeposit.custId?c}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">充值号：</td><td><input type="text" name="depositNo" id="depositNo" value="<#if consumerDeposit?? && consumerDeposit.depositNo??>${consumerDeposit.depositNo}</#if>"></td>
				    			<td align="right">充值订单号：</td><td><input type="text" name="depositOrderNo" id="depositOrderNo" value="<#if consumerDeposit?? && consumerDeposit.depositOrderNo??>${consumerDeposit.depositOrderNo}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">充值方式：</td>
				    			<td>
				    				<input type="text" name="payment" id="payment" value="<#if consumerDeposit.payment??><#if consumerDeposit.payment=='1010'>支付宝<#elseif consumerDeposit.payment=='1011'>财付通<#elseif consumerDeposit.payment=='1012'>微信支付<#else></#if></#if>">				
				    			</td>
				    			<td align="right">充值状态：</td>
				    			<td>
				    				<input type="text" name="isPaid" id="isPaid" value="<#if consumerDeposit.isPaid??><#if consumerDeposit.isPaid==0>校验失败<#elseif consumerDeposit.isPaid==1>未校验<#elseif consumerDeposit.isPaid==2>校验成功<#elseif consumerDeposit.isPaid==3>充值异常<#elseif consumerDeposit.isPaid==4>已作废<#else></#if></#if>">				
				    			</td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">主账户阅读币：</td><td><input type="text" name="mainGold" id="mainGold" value="<#if consumerDeposit?? && consumerDeposit.mainGold??>${consumerDeposit.mainGold?c}</#if>"></td>
				    			<td align="right">副账户阅读币：</td><td><input type="text" name="subGold" id="subGold" value="<#if consumerDeposit?? && consumerDeposit.subGold??>${consumerDeposit.subGold?c}</#if>"></td>
					      	</tr>			   
					      	<tr height="35">
				    			<td align="right">充值时间：</td><td><input type="text" name="payTime" id="payTime" value="<#if consumerDeposit?? && consumerDeposit.payTime??>${consumerDeposit.payTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"></td>
				    			<td align="right">支付虚拟商品ID：</td><td><input type="text" name="relationProductId" id="relationProductId" value="<#if consumerDeposit?? && consumerDeposit.relationProductId??>${consumerDeposit.relationProductId}</#if>"></td>
					      	</tr>	
					      	<tr height="35">
				    			<td align="right">虚拟商品数量：</td><td><input type="text" name="productCount" id="productCount" value="<#if consumerDeposit?? && consumerDeposit.productCount??>${consumerDeposit.productCount?c}<#else>1</#if>"></td>
				    			<td align="right">平台来源：</td>
				    			<td>
				    				<input type="text" name="fromPaltform" id="fromPaltform" value="<#if consumerDeposit?? && consumerDeposit.fromPaltform??><#if consumerDeposit.fromPaltform=="yc_ios">当读小说ios平台<#elseif consumerDeposit.fromPaltform=="ds_android">当当读书安卓平台<#elseif consumerDeposit.fromPaltform=="ds_ios">当当读书ios平台<#else>当读小说安卓平台</#if><#else>当读小说安卓平台</#if>">
							    </td>
					      	</tr>					     					   
					      	<tr height="35"><td colspan="4"><br/><br/></td></tr>
			            </table>
		            </div>
		            <#if managerOperateLogs ?? && managerOperateLogs?size gt 0>
		            <div><br/><h3 style="height:33px; border-bottom:3px; line-height:32px; text-indent:20px; background: no-repeat 0 5px;">操作日志详情：</h3><br/>
					     <table border="1" bordercolor="#a0c6e5" rules=none>
					     		<tr height="35"><td colspan="4"><br/></td></tr>
			    				<tr><td colspan="4">						    
							      	<table class="table1" border="1" bordercolor="#a0c6e5" rules=none style="width:90%" align="right">
						        	<tr style="height:28px;">
						                <th>操作人</th>
						                <th>操作描述</th>
						                <th>操作结果</th>
						                <th>操作时间</th>						               				    
						       	    </tr>
						       	    <#assign i = 0>
						       	 	<#if managerOperateLogs??>
						    			<#list managerOperateLogs as managerOperateLog>
								    		<#assign i = i+1>
						    				<#if i%2 == 0>
						    					<tr style="background-color: #E4EAF6;height:28px;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
						    				<#else>
						    					<tr style="height:28px;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
						    				</#if>
								    			<td><#if managerOperateLog.operator??>${managerOperateLog.operator}</#if></td>
									      		<td title='${managerOperateLog.operateDescription?if_exists}'>
									      			<#if managerOperateLog.operateDescription??>
									      				<#if managerOperateLog.operateDescription?length lt 15>
									      					${managerOperateLog.operateDescription}
									      				<#else>
									      					${managerOperateLog.operateDescription[0..11]}......
									      				</#if>
									      			</#if>
									      		</td>
									      		<td>
									      			<#if managerOperateLog.operateResult??>
									      				<#if managerOperateLog.operateResult==1>
									      					成功
									      				<#elseif managerOperateLog.operateResult==0>
									      					失败
									      				<#else>
									      					${managerOperateLog.operateResult?c}
									      				</#if>
									      			</#if>
									      		</td>
									      		<td><#if managerOperateLog.creationDate??>${managerOperateLog.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>									      									
									      	</tr>
							      		</#list>
							      	</#if>
						        </table>
					        </td></tr>	
					        <tr height="35"><td colspan="4"><br/></td></tr>			     					   
			            </table>			          
		            </div>
		            </#if>
			    </div>			    
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>