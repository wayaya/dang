<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">  
		$(function(){
			$("input,select").each(function(){
				if($(this).attr("name")!="changeMainMoney" && $(this).attr("name")!="changeSubMoney"){
					$(this).attr("disabled","disabled");
				}				
			});	
			<#if consumerDeposit.isPaid??>
				<#if consumerDeposit.isPaid==2 || consumerDeposit.isPaid==4>
					$("#handleAbnormalDepositButton").hide();
					$("#cancelAbnormalDepositButton").hide();
				</#if>
			</#if>
			<#if errorMsg ?? && errorMsg != ''>
				alert(${errorMsg});
			</#if>		
		}); 
		
		function handleAbnormalDeposit(){
			$.post("/consumerDeposit/handleAbnormalDeposit.go", {consumerDepositId:$("#consumerDepositId").val(),changeMainMoney:$("#changeMainMoney").val(),changeSubMoney:$("#changeSubMoney").val(),custId:$("#custId").val(),depositNo:$("#depositNo").val(),userName:$("#userName").val(),deviceType:$("#deviceType").val(),fromPaltform:$("#fromPaltformVal").val()},
			   function(data){
			   		if(data!="success"){
			   			alert(data);
			   			return;
			   		}else{	
			   			$.dialog.alert('操作成功！',function(){
						    $.dialog({id:'abnormalDetail'}).reload();						    
						});		   			 			
			   		}
	   				
			   });
		}
		
		function cancelAbnormalDeposit(){
			$.post("/consumerDeposit/cancelAbnormalDeposit.go", {consumerDepositId:$("#consumerDepositId").val(),custId:$("#custId").val(),depositNo:$("#depositNo").val(),userName:$("#userName").val(),deviceType:$("#deviceType").val(),fromPaltform:$("#fromPaltformVal").val()},
			   function(data){
			   		if(data!="success"){
			   			alert(data);
			   			return;
			   		}else{
			   			$.dialog.alert('操作成功！',function(){
						    $.dialog({id:'abnormalDetail'}).reload();
						});
			   		}
	   				
			   }, "text");
		}				 
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>
						充值管理&gt;&gt;异常充值记录列表&gt;&gt异常充值记录详情					
					</h3>					
				    <div><br/><h2>充值记录详情：</h2><br/>
					     <table border="1" bordercolor="#a0c6e5" rules=none>
					     	<tr height="40">
				    			<td align="center" colspan="6">
				    				主账户差额：<input type="text" name="changeMainMoney" id="changeMainMoney" value="<#if changeMoneyMap??><#if changeMoneyMap["changeMainMoney"]??>${changeMoneyMap["changeMainMoney"]?c}</#if></#if>">&nbsp;&nbsp;&nbsp;&nbsp;
									附账户差额：<input type="text" name="changeSubMoney" id="changeSubMoney" value="<#if changeMoneyMap??><#if changeMoneyMap["changeSubMoney"]??>${changeMoneyMap["changeSubMoney"]?c}</#if></#if>">&nbsp;&nbsp;&nbsp;&nbsp;
									<button id="handleAbnormalDepositButton" onclick="return handleAbnormalDeposit()">&nbsp;处理&nbsp;</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button id="cancelAbnormalDepositButton" onclick="return cancelAbnormalDeposit()">&nbsp;作废&nbsp;</button>
									<input type="hidden" name="deviceType" id="deviceType" value="<#if consumerDeposit?? && consumerDeposit.deviceType??>${consumerDeposit.deviceType}</#if>">
									<input type="hidden" name="fromPaltformVal" id="fromPaltformVal" value="<#if consumerDeposit?? && consumerDeposit.fromPaltform??>${consumerDeposit.fromPaltform}</#if>">
				    			</td>				    	
					      	</tr>
		    				<tr height="35">
		    					<td align="right">用户名：</td><td><input type="text" name="userName" id="userName" value="<#if consumerDeposit?? && consumerDeposit.userName??>${consumerDeposit.userName}</#if>"></td>
		    					<td align="right">用户ID：</td><td><input type="text" name="custId" id="custId" value="<#if consumerDeposit?? && consumerDeposit.custId??>${consumerDeposit.custId?c}</#if>"></td>
				    			<td align="right">充值记录ID：</td><td><input type="text" name="consumerDepositId" id="consumerDepositId" value="<#if consumerDeposit?? && consumerDeposit.consumerDepositId??>${consumerDeposit.consumerDepositId?c}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">充值号：</td><td><input type="text" name="depositNo" id="depositNo" value="<#if consumerDeposit?? && consumerDeposit.depositNo??>${consumerDeposit.depositNo}</#if>"></td>
				    			<td align="right">充值订单号：</td><td><input type="text" name="depositOrderNo" id="depositOrderNo" value="<#if consumerDeposit?? && consumerDeposit.depositOrderNo??>${consumerDeposit.depositOrderNo}</#if>"></td>
				    			<td align="right">充值金额（分）：</td><td><input type="text" name="money" id="money" value="<#if consumerDeposit?? && consumerDeposit.money??>${consumerDeposit.money?c}</#if>"></td>
					      	</tr>
					      	<tr height="35">
					      		<td align="right">充值时间：</td><td><input type="text" name="payTime" id="payTime" value="<#if consumerDeposit?? && consumerDeposit.payTime??>${consumerDeposit.payTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"></td>
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
				    			<td align="right">主账户：</td><td><input type="text" name="mainGold" id="mainGold" value="<#if consumerDeposit?? && consumerDeposit.mainGold??>${consumerDeposit.mainGold?c}</#if>"></td>
				    			<td align="right">副账户：</td><td><input type="text" name="subGold" id="subGold" value="<#if consumerDeposit?? && consumerDeposit.subGold??>${consumerDeposit.subGold?c}</#if>"></td>
				    			<td align="right">商品ID：</td><td><input type="text" name="relationProductId" id="relationProductId" value="<#if consumerDeposit?? && consumerDeposit.relationProductId??>${consumerDeposit.relationProductId}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">虚拟商品数量：</td><td><input type="text" name="productCount" id="productCount" value="<#if consumerDeposit?? && consumerDeposit.productCount??>${consumerDeposit.productCount?c}<#else>1</#if>"></td>
				    			<td align="right">平台来源：</td><td><input type="text" name="fromPaltform" id="fromPaltform" value="<#if consumerDeposit?? && consumerDeposit.fromPaltform??><#if consumerDeposit.fromPaltform=="yc_ios">当读小说ios平台<#elseif consumerDeposit.fromPaltform=="ds_android">当当读书安卓平台<#elseif consumerDeposit.fromPaltform=="ds_ios">当当读书ios平台<#else>当读小说安卓平台</#if><#else>当读小说安卓平台</#if>"></td>
				    			<td align="right"></td><td></td>
					      	</tr>			   
					      	<tr height="35"><td colspan="4"><br/></td></tr>					     					   
			            </table>
		            </div>
		            <div>
		            	<div><h2>充值订单详情：</h2></div>
		            	<div>
					     <table border="1" bordercolor="#a0c6e5" rules=none>
		    				<tr height="35">
				    			<td align="right">用户邮箱：</td><td><input type="text" name="custEmail" value="<#if orderInfoVo?? && orderInfoVo.custEmail??>${orderInfoVo.custEmail}</#if>"></td>
				    			<td align="right">用户ID：</td><td><input type="text" name="custId"  value="<#if orderInfoVo?? && orderInfoVo.custId??>${orderInfoVo.custId?c}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">来源平台：</td><td><input type="text" name="fromPlatform" value="<#if orderInfoVo?? && orderInfoVo.fromPlatform??>${orderInfoVo.fromPlatform?c}</#if>"></td>
				    			<td align="right">创建时间：</td><td><input type="text" name="orderCreationDate" value="<#if orderInfoVo?? && orderInfoVo.orderCreationDate??>${orderInfoVo.orderCreationDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="right">订单ID：</td><td><input type="text" name="orderId" value="<#if orderInfoVo?? && orderInfoVo.orderId??>${orderInfoVo.orderId?c}</#if>"></td>
				    			<td align="right">订单来源：</td><td><input type="text" name="orderSource" value="<#if orderInfoVo?? && orderInfoVo.orderSource??>${orderInfoVo.orderSource}</#if>"></td>
					      	</tr>			      	
					      	<tr height="35">
				    			<td align="right">订单状态：</td><td><input type="text" name="orderStatus" value="<#if orderInfoVo?? && orderInfoVo.orderStatus??>${orderInfoVo.orderStatus?c}</#if>"></td>
				    			<td align="right">订单类型：</td><td><input type="text" name="orderType" value="<#if orderInfoVo?? && orderInfoVo.orderType??>${orderInfoVo.orderType?c}</#if>"></td>
					      	</tr>			   					     					   
					      	<tr><td colspan="4">
					      		<h3 style="height:33px; border-bottom:3px; line-height:32px; text-indent:20px; background: no-repeat 0 5px;">充值订单明细信息：</h3><br/>
						      	<table class="table1" border="1" bordercolor="#a0c6e5" rules=none style="width:90%">
					        	<tr style="height:28px;">
					                <th>商品名称</th>
					                <th>商品ID</th>
					                <th>原价</th>
					                <th>销售价格</th>
					                <th>商品数量</th>					    
					       	    </tr>
					       	    <#assign i = 0>
					       	 	<#if orderInfoVo?? && orderInfoVo.orderDetailInfoVos??>
					    			<#list orderInfoVo.orderDetailInfoVos as orderDetail>
							    		<#assign i = i+1>
					    				<#if i%2 == 0>
					    					<tr style="background-color: #E4EAF6;height:28px;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
					    				<#else>
					    					<tr style="height:28px;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
					    				</#if>
							    			<td><#if orderDetail.productName??>${orderDetail.productName}</#if></td>
								      		<td><#if orderDetail.productId??>${orderDetail.productId?c}</#if></td>
								      		<td><#if orderDetail.originalPrice??>${orderDetail.originalPrice?c}</#if></td>
								      		<td><#if orderDetail.barginPrice??>${orderDetail.barginPrice?c}</#if></td>
								      		<td><#if orderDetail.orderQuantity??>${orderDetail.orderQuantity?c}</#if></td>							
								      	</tr>
						      		</#list>
						      	</#if>
					        </table>
				        </td></tr>
				        <tr height="35"><td colspan="4"><br/></td></tr>
			            </table>
			            </div>
		            </div>
		            <div>
		            	<div><h2>充值明细详情：</h2></div>
		            	<div>
					     <table border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr><td colspan="4">						    
							      	<table class="table1" border="1" bordercolor="#a0c6e5" rules=none style="width:90%" align="center">
						        	<tr style="height:28px;">
						                <th>用户ID</th>
						                <th>消费单号</th>
						                <th>消费金额</th>
						                <th>账户类型</th>
						                <th>消费类型</th>
						                <th>创建时间</th>
						                <th>更新时间</th>
						                <th>消费来源</th>
						                <th>平台编号</th>
						                <th>外部订单号</th>						    
						       	    </tr>
						       	    <#assign i = 0>
						       	 	<#if accountConsumes??>
						    			<#list accountConsumes as accountConsume>
								    		<#assign i = i+1>
						    				<#if i%2 == 0>
						    					<tr style="background-color: #E4EAF6;height:28px;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
						    				<#else>
						    					<tr style="height:28px;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
						    				</#if>
								    			<td><#if accountConsume.custId??>${accountConsume.custId?c}</#if></td>
									      		<td><#if accountConsume.consumeNo??>${accountConsume.consumeNo}</#if></td>
									      		<td><#if accountConsume.consumeMoney??>${accountConsume.consumeMoney?c}</#if></td>
									      		<td><#if accountConsume.accountType??>${accountConsume.accountType}</#if></td>
									      		<td>
									      			<#if accountConsume.consumeType??>
									      				<#if accountConsume.consumeType=="1000">
									      					充值
									      				<#elseif accountConsume.consumeType=="2000">
									      					消费
									      				<#else>
									      					${accountConsume.consumeType}
									      				</#if>
									      			</#if>
									      		</td>	
									      		<td><#if accountConsume.creationDate??>${accountConsume.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
									      		<td><#if accountConsume.lastModifyedDate??>${accountConsume.lastModifyedDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
									      		<td><#if accountConsume.consumeSource??>${accountConsume.consumeSource}</#if></td>
									      		<td>
									      			<#if accountConsume.platformNo??>
									      				<#if accountConsume.platformNo=="1001">
									      					原创
									      				<#elseif accountConsume.platformNo=="1002"> 
									      					读书
									      				<#else>
									      					${accountConsume.platformNo}
									      				</#if>								      				
									      			</#if>
									      		</td>
									      		<td><#if accountConsume.sourceOrderNo??>${accountConsume.sourceOrderNo}</#if></td>						
									      	</tr>
							      		</#list>
							      	</#if>
						        </table>
					        </td></tr>	
					        <tr height="35"><td colspan="4"><br/></td></tr>			     					   
			            </table>	
			            </div>		          
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>