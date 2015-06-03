<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		$(function(){
			showDetail();
		});
		function showDetail(){
			$("[name='showDetail']").each(function(){
				var uq = {}; 
				var str = '';
				<#if orderMain?? && orderMain.orderDetails??>
					<#list orderMain.orderDetails as detail>
						if("${detail.orderDetailId?c}"==$(this).attr("id")){
							<#list detail.orderDetailChapters as detailChapter>
								if(!uq['${detailChapter.mediaId?c}']){
									uq['${detailChapter.mediaId?c}'] = true; 
									if(str!=''){
										str += '<br/>';
									} 
									str += '<a href="#" onclick="showDetailChapter(${detailChapter.orderDetailId?c},${detailChapter.mediaId?c})">${detailChapter.mediaName!""}</a>';
								}
							</#list>
						} 
					</#list>
				</#if>
				$(this).append(str);
			});			
		}
		
		function showDetailChapter(orderDetailId,mediaId){
			$.dialog({title:'订单章节列表页',content:'url:/orderBase/detailChapter.go?orderDetailId='+orderDetailId+'&mediaId='+mediaId,
	   		icon:'succeed',width:1000,height:600,fixed:false,lock:true,zIndex:2000
			});
		}
	</script>
</head>   
  <body>	
		<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>订单管理&gt;&gt;订单列表&gt;&gt;订单详情</h3>					
				    <div><br/><h2>订单基本信息：</h2><br/>
					     <table border="1" bordercolor="#a0c6e5" rules=none>
		    				<tr height="35">
		    					<td align="right">用户ID：</td><td align="left"><#if orderMain.custId??>${orderMain.custId?c}</#if></td>
				    			<td align="right">订单号：</td><td align="left"><#if orderMain.orderNo??>${orderMain.orderNo}</#if></td>
				    			<td align="right">渠道编号：</td><td align="left"><#if orderMain.chanelCode??>${orderMain.chanelCode}</#if></td>
					      	</tr>
					      	<tr height="35">
					      		<td align="right">订单总金额：</td><td align="left"><#if orderMain.totalPrice??>${orderMain.totalPrice?c}</#if></td>
				    			<td align="right">主账户支付总金额：</td><td align="left"><#if orderMain.payMainPrice??>${orderMain.payMainPrice?c}</#if></td>
				    			<td align="right">副账户支付总金额：</td><td align="left"><#if orderMain.paySubPrice??>${orderMain.paySubPrice?c}</#if></td>
					      	</tr>
					      	<tr height="35">
					      		<td align="right">下单时间：</td>
				    			<td align="left">
				    				<#if orderMain.creationDate??>${orderMain.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if>
				    			</td>
				    			<td align="right">订单状态：</td>
				    			<td align="left">				    							    				
					    			<#if orderMain.orderStatus ??>
					    				<#if orderMain.orderStatus == 1>
					    					新建
					    				<#elseif orderMain.orderStatus == 2>
					    					已支付
					    				<#elseif orderMain.orderStatus == 3>
					    					已取消
										<#else>
					    				</#if>
					    			</#if>
				    			</td>
					      		<td align="right">支付时间：</td><td align="left"><#if orderMain.payTime??>${orderMain.payTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
					      	</tr>
					      	<tr height="35">
					      		<!--
					      		<td align="right">订单类型：</td>
				    			<td align="left">
					    			<#if orderMain.orderType ??>
					    				<#if orderMain.orderType == 1>
					    					图书类订单
					    				<#elseif orderMain.orderType == 2>
					    					音频频订单
					    				<#elseif orderMain.orderType == 3>
					    					视频类订单
					    				<#elseif orderMain.orderType == 4>
					    					其他类订单
										<#else>
					    				</#if>
					    			</#if>
	    						</td>
	    						-->
				    			<td align="right">订单优惠总金额：</td><td align="left"><#if orderMain.prePrice??>${orderMain.prePrice?c}</#if></td>
				    			<td align="right">使用优惠券金额：</td><td align="left"><#if orderMain.couponPrice??>${orderMain.couponPrice?c}</#if></td>
				    			<td align="right">供应商参与分成金额：</td>
				    			<td align="left">				    				
					    			 <#if orderMain.vspPrice??>${orderMain.vspPrice?c}</#if>
				    			</td>
					      	</tr>
					      	<tr height="35">
					      		<td align="right">购买作品数量：</td><td align="left"><#if orderMain.mediaCount??>${orderMain.mediaCount?c}</#if></td>
				    			<td align="right">购买章节数量：</td><td align="left"><#if orderMain.chapterCount??>${orderMain.chapterCount?c}</#if></td>
				    			<td align="right">赠送积分：</td><td align="left"><#if orderMain.givingPoint??>${orderMain.givingPoint?c}</#if></td>
					      	</tr>	
					      	<tr height="35">
					      		<td align="right">参加活动ID：</td><td align="left"><#if orderMain.activeId??>${orderMain.activeId}</#if></td>
				    			<td align="right">设备版本号：</td><td align="left"><#if orderMain.deviceVersion??>${orderMain.deviceVersion}</#if></td>
				    			<td align="right">支付方式：</td>
				    			<td align="left">				    				
					    			<#if orderMain.payment ??>
					    				<#if orderMain.payment == "1">
					    					主账户支付
					    				<#elseif orderMain.payment == "2">
					    					副账户支付
					    				<#elseif orderMain.payment == "3">
					    					混合类支付
					    				<#elseif orderMain.payment == "4">
					    					其他类支付
										<#else>
					    				</#if>
					    			</#if>
				    			</td>
					      	</tr>
					      		
			            </table>
					 	<br/><h2>订单明细信息：</h2>
					 	<br/>
					    <table class="table2">
				        	<tr style="height:28px;">
				                <th>订单明细ID</th>
				                <th>销售实体ID</th>
				                <th>销售实体名称</th>
				                <th>总金额</th>
				                <th>主支付金额</th>
				                <th>副支付金额</th>
				                <th>优惠金额</th>
				                <th>赠送积分</th>
				                <th>购买章节数</th>
				                <th>活动ID</th>
				                <th style="width:15%">章节明细</th>
				       	    </tr>
				       	    <#assign i = 0>
				       	 	<#if orderMain?? && orderMain.orderDetails??>
				    			<#list orderMain.orderDetails as orderDetail>
						    		<#assign i = i+1>
				    				<#if i%2 == 0>
				    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
				    				<#else>
				    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
				    				</#if>
						    			<td><#if orderDetail.orderDetailId??>${orderDetail.orderDetailId?c}</#if></td>
							      		<td><#if orderDetail.saleInfoId??>${orderDetail.saleInfoId?c}</#if></td>
							      		<td><#if orderDetail.saleInfoName??>${orderDetail.saleInfoName}</#if></td>
							      		<td><#if orderDetail.totalPrice??>${orderDetail.totalPrice?c}</#if></td>
							      		<td><#if orderDetail.payMainPrice??>${orderDetail.payMainPrice?c}</#if></td>
							      		<td><#if orderDetail.paySubPrice??>${orderDetail.paySubPrice?c}</#if></td>
							      		<td><#if orderDetail.prePrice??>${orderDetail.prePrice?c}</#if></td>
							      		<td><#if orderDetail.givingPoint??>${orderDetail.givingPoint?c}</#if></td>
							      		<td><#if orderDetail.chapterCount??>${orderDetail.chapterCount?c}</#if></td>
							      		<td><#if orderDetail.activeId??>${orderDetail.activeId}</#if></td>
							      		<td id="${orderDetail.orderDetailId?c}" name ="showDetail"></td>
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
</html>
