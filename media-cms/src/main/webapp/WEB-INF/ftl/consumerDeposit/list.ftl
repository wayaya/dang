<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">	
		var condition ="";
		$(function(){
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/consumerDeposit/list.go?page=__id__'+"&"+$("#master_list_form").serialize())
		    });
		    $('#creationDateStart').calendar({maxDate:'#creationDateEnd',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#creationDateEnd').calendar({minDate:'#creationDateStart',format:'yyyy-MM-dd HH:mm:ss'});
			$('#payTimeStart').calendar({maxDate:'#payTimeEnd',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#payTimeEnd').calendar({minDate:'#payTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
		});		
		function searchMaster(){
	   		var result = checkParms();
	   		if(result != ""){
	   			alert(result);
	   			return false;
	   		}
	   		$('#master_list_form').submit();
	   	}
	   	
	   	function showDetail(consumerDepositId){
	   		$.dialog({title:'充值记录详情页',content:'url:/consumerDeposit/detail.go?consumerDepositId='+consumerDepositId,
	   		icon:'succeed',width:900,height:350,fixed:false,lock:true
			});
	   	}
	   		   	
	   	function checkParms(){
	   		$('#depositNo').val($.trim($('#depositNo').val()));
	   		$('#custId').val($.trim($('#custId').val()));
	   		$('#depositOrderNo').val($.trim($('#depositOrderNo').val()));
			if($('#custId').val() != "" && isNaN($('#custId').val())){
				return "用户ID必须为数字格式！";
			}
			return "";
	   	}	   	
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
	 		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>充值管理&gt;&gt;充值记录列表</h3>
				<div class="mrdiv">
		      		<form action="/consumerDeposit/list.go" method="post" id="master_list_form">
			      		 <table>
							<tr style="height:30px;">
								<td style="width:80px; text-align:right">
									用户ID：
								</td>
								<td style="width:120px; text-align:left">
									<input type="text" style="width: 120px;" name="custId" id="custId" value="<#if vo??><#if vo.custId??>${vo.custId?c}</#if></#if>">
								</td>
								<td style="width:80px; text-align:right">
									充值号：
								</td>
								<td style="width:120px; text-align:left">
									<input type="text" style="width: 120px;" name="depositNo" id="depositNo" value="<#if vo??><#if vo.depositNo??>${vo.depositNo}</#if></#if>">
								</td>
							 	<td style="width:80px; text-align:right">
							 		充值订单号：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="depositOrderNo" id="depositOrderNo" value="<#if vo??><#if vo.depositOrderNo??>${vo.depositOrderNo}</#if></#if>">
							 	</td>
							 	<td style="width:80px; text-align:right">
							 		充值状态：
							 	</td>
							 	<td style="width:120px; text-align:left">
								 	<select name = "isPaid" id="isPaid" style="width: 120px;" >
										<option value="">&nbsp;请选择</option>
										<option value="0" <#if vo ?? && vo.isPaid ?? && vo.isPaid == 0>selected="selected"</#if>>&nbsp;校验失败</option>
										<option value="1" <#if vo ?? && vo.isPaid ?? && vo.isPaid == 1>selected="selected"</#if>>&nbsp;未校验</option>
										<option value="2" <#if vo ?? && vo.isPaid ?? && vo.isPaid == 2>selected="selected"</#if>>&nbsp;校验成功</option>
										<option value="3" <#if vo ?? && vo.isPaid ?? && vo.isPaid == 3>selected="selected"</#if>>&nbsp;充值异常</option>	
										<option value="4" <#if vo ?? && vo.isPaid ?? && vo.isPaid == 4>selected="selected"</#if>>&nbsp;已作废</option>								
									</select>
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		充值方式：
							 	</td>
							 	<td style="width:120px; text-align:left">
								 	<select name = "payment" id="payment" style="width: 120px;" >
										<option value="">&nbsp;请选择</option>
										<option value="1010" <#if vo ?? && vo.payment ?? && vo.payment == "1010">selected="selected"</#if>>&nbsp;支付宝充值</option>
										<option value="1011" <#if vo ?? && vo.payment ?? && vo.payment == "1011">selected="selected"</#if>>&nbsp;财付通充值</option>
										<option value="1012" <#if vo ?? && vo.payment ?? && vo.payment == "1012">selected="selected"</#if>>&nbsp;微信支付充值</option>
									</select>
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		平台来源：
							 	</td>
							 	<td style="width:120px; text-align:left">
								 	<select name = "fromPaltform" id="fromPaltform" style="width: 120px;" >
										<option value="">&nbsp;请选择</option>
										<option value="yc_android" <#if vo ?? && vo.fromPaltform ?? && vo.fromPaltform == "yc_android">selected="selected"</#if>>当读小说安卓平台</option>
										<option value="yc_ios" <#if vo ?? && vo.fromPaltform ?? && vo.fromPaltform == "yc_ios">selected="selected"</#if>>当读小说ios平台</option>
										<option value="ds_android" <#if vo ?? && vo.fromPaltform ?? && vo.fromPaltform == "ds_android">selected="selected"</#if>>当当读书安卓平台</option>
										<option value="ds_ios" <#if vo ?? && vo.fromPaltform ?? && vo.fromPaltform == "ds_ios">selected="selected"</#if>>当当读书ios平台</option>
									</select>
							 	</td>
							</tr>
							<tr style="height:30px;">
								<td style="text-align:right">
									支付时间开始：
								</td>
								<td style="text-align:left">
									<input type="text" style="width: 120px;" name="payTimeStart" id="payTimeStart" readonly="readonly" value="<#if vo??><#if vo.payTimeStart??>${vo.payTimeStart}</#if></#if>">
								</td>
							 	<td style="text-align:right">
							 		支付时间结束：
							 	</td>
							 	<td style="text-align:left">
							 		<input type="text" style="width: 120px;" name="payTimeEnd" id="payTimeEnd" readonly="readonly" value="<#if vo??><#if vo.payTimeEnd??>${vo.payTimeEnd}</#if></#if>">
							 	</td>
							 	<td style="text-align:right">
							 		创建时间开始：
							 	</td>
							 	<td style="text-align:left">
							 		<input type="text" style="width: 120px;" name="creationDateStart" id="creationDateStart" readonly="readonly" value="<#if vo??><#if vo.creationDateStart??>${vo.creationDateStart}</#if></#if>">
							 	</td>
							 	<td style="text-align:right">
							 		创建时间结束：
							 	</td>
							 	<td style="text-align:left">
							 		<input type="text" style="width: 120px;" name="creationDateEnd" id="creationDateEnd" readonly="readonly" value="<#if vo??><#if vo.creationDateEnd??>${vo.creationDateEnd}</#if></#if>">
							 	</td>
							 	<td style="text-align:right"></td>
								<td style="text-align:left"><button  onclick="return searchMaster()">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button></td>	
								<td style="text-align:right"></td>
								<td style="text-align:left"></td>	
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr style="height:28px;">
		                    <th>充值记录ID</th>
		                    <th>充值号</th>
		                    <th>用户ID</th>
		                    <th>充值订单号</th>
		                    <th>充值金额</th>
		                    <th>主账户阅读币</th>
		                    <th>副账户阅读币</th>
		                    <th>状态</th>
		                    <th>充值方式</th>
		                    <th>平台来源</th>
		                    <th>充值时间</th>
		                    <th>创建时间</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 <#if pageFinder.data??>
			    			<#list pageFinder.data as consumerDeposit>
					    		<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if consumerDeposit.consumerDepositId??>${consumerDeposit.consumerDepositId?c}</#if></td>
						      		<td><#if consumerDeposit.depositNo??>${consumerDeposit.depositNo}</#if></td>
						      		<td><#if consumerDeposit.custId??>${consumerDeposit.custId?c}</#if></td>
						      		<td><#if consumerDeposit.depositOrderNo??>${consumerDeposit.depositOrderNo}</#if></td>
						      		<td><#if consumerDeposit.money??>${consumerDeposit.money?c}</#if></td>
						      		<td><#if consumerDeposit.mainGold??>${consumerDeposit.mainGold?c}</#if></td>
						      		<td><#if consumerDeposit.subGold??>${consumerDeposit.subGold?c}</#if></td>
						      		<td>
							      		<#if consumerDeposit.isPaid??>
							      			<#if consumerDeposit.isPaid==0>
							      				校验失败
							      			<#elseif consumerDeposit.isPaid==1>
							      				未校验
							      			<#elseif consumerDeposit.isPaid==2>
							      				校验成功
							      			<#elseif consumerDeposit.isPaid==3>
							      				充值异常
							      			<#elseif consumerDeposit.isPaid==4>
							      				已作废
							      			<#else>
							      			</#if>
							      		</#if>
						      		</td>
						      		<td>
							      		<#if consumerDeposit.payment??>
							      			<#if consumerDeposit.payment=="1010">
							      				支付宝
							      			<#elseif consumerDeposit.payment=="1011">
							      				财付通
							      			<#elseif consumerDeposit.payment=="1012">
							      				微信支付						
							      			<#else>
							      			</#if>
							      		</#if>
						      		</td>
						      		<td>
							      		<#if consumerDeposit.fromPaltform??>
							      			<#if consumerDeposit.fromPaltform=="yc_ios">
							      				当读小说ios平台
							      			<#elseif consumerDeposit.fromPaltform=="ds_android">
							      				当当读书安卓平台
							      			<#elseif consumerDeposit.fromPaltform=="ds_ios">
							      				当当读书ios平台					
							      			<#else>
							      				当读小说安卓平台
							      			</#if>
							      		<#else>
							      			当读小说安卓平台	
							      		</#if>
						      		</td>
						      		<td><#if consumerDeposit.payTime??>${consumerDeposit.payTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						      		<td><#if consumerDeposit.creationDate??>${consumerDeposit.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						      		<td>
						      			<a href="#" onclick="showDetail('${consumerDeposit.consumerDepositId?c}')">详情</a>
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
