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
				link_to: encodeURI('/orderBase/list.go?page=__id__'+"&"+$("#master_list_form").serialize())
		    });
		    $('#creationDateStart').calendar({maxDate:'#creationDateEnd',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#creationDateEnd').calendar({minDate:'#creationDateStart',format:'yyyy-MM-dd HH:mm:ss'});
		});		
		
		function goPage(){
			var currentPage = $('#currentPage').val();
			if(currentPage > 0){
				window.location.href='/orderBase/list.go?page='+currentPage+condition;
			}else{
				alert("页数不合法");
			}
			
		}
		
	   	function searchMaster(){
	   		var result = checkParms();
	   		if(result != ""){
	   			alert(result);
	   			return false;
	   		}
	   		$('#master_list_form').submit();
	   	}
	   	
	   	function showDetail(orderId){
	   		$.dialog({title:'订单详情页',content:'url:/orderBase/detail.go?orderId='+orderId,
	   		icon:'succeed',width:900,height:550,fixed:false,lock:true
			});
	   	}
	   	
	   	function checkParms(){
	   		$('#orderNo').val($.trim($('#orderNo').val()));
	   		$('#custId').val($.trim($('#custId').val()));
	   		$('#saleInfoId').val($.trim($('#saleInfoId').val()));
	   		$('#saleInfoName').val($.trim($('#saleInfoName').val()));
	   		$('#mediaName').val($.trim($('#mediaName').val()));
			if($('#saleInfoId').val() != "" && isNaN($('#saleInfoId').val())){
				return "销售实体ID必须为数字格式！";
			}
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
				<h3>订单管理&gt;&gt;订单列表</h3>
				<div class="mrdiv">
		      		<form action="/orderBase/list.go" method="post" id="master_list_form">
			      		 <table >
		        			<tr style="height:30px;">
								<td style="width:70px; text-align:right">
									订单号：
								</td>
								<td style="width:100px; text-align:left">
									<input type="text" style="width: 100px;" name="orderNo" id="orderNo" value="<#if vo??><#if vo.orderNo??>${vo.orderNo}</#if></#if>">
								</td>
							 	<td style="width:70px; text-align:right">
							 		用户ID：
							 	</td>
							 	<td style="width:100px; text-align:left">
							 		<input type="text" style="width: 100px;" name="custId" id="custId" value="<#if vo??><#if vo.custId??>${vo.custId?c}</#if></#if>">
							 	</td>
							 	<td style="width:80px; text-align:right">
							 		销售实体ID：
							 	</td>
							 	<td style="width:100px; text-align:left">
							 		<input type="text" style="width: 100px;" name="saleInfoId" id="saleInfoId" value="<#if vo??><#if vo.saleInfoId??>${vo.saleInfoId?c}</#if></#if>">
							 	</td>
							 	<td style="width:80px; text-align:right">
							 		销售实体名称：
							 	</td>
							 	<td style="width:100px; text-align:left">
							 		<input type="text" style="width: 100px;" name="saleInfoName" id="saleInfoName" value="<#if vo??><#if vo.saleInfoName??>${vo.saleInfoName}</#if></#if>">
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		开始时间：
							 	</td>
							 	<td style="width:100px; text-align:left">
							 		<input type="text" style="width: 100px;" name="creationDateStart" id="creationDateStart" readonly="readonly" value="<#if vo??><#if vo.creationDateStart??>${vo.creationDateStart}</#if></#if>">
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		结束时间：
							 	</td>
							 	<td style="width:100px; text-align:left">
							 		<input type="text" style="width: 100px;" name="creationDateEnd" id="creationDateEnd" readonly="readonly" value="<#if vo??><#if vo.creationDateEnd??>${vo.creationDateEnd}</#if></#if>">
							 	</td>
							</tr>
							<tr style="height:30px;">
								<td style="width:70px; text-align:right">
									作品名称：
								</td>
								<td style="width:100px; text-align:left">
									<input type="text" style="width: 100px;" name="mediaName" id="mediaName" value="<#if vo??><#if vo.mediaName??>${vo.mediaName}</#if></#if>">
								</td>
							 	<td style="width:70px; text-align:right">
							 		支付方式：
							 	</td>
							 	<td style="width:100px; text-align:left">
								 	<select name = "payment" id="payment" style="width:100px">
										<option value="">&nbsp;请选择</option>
										<option value="1" <#if vo ?? && vo.payment ?? && vo.payment == "1">selected="selected"</#if>>&nbsp;主账户支付</option>
										<option value="2" <#if vo ?? && vo.payment ?? && vo.payment == "2">selected="selected"</#if>>&nbsp;副账户支付</option>
										<option value="3" <#if vo ?? && vo.payment ?? && vo.payment == "3">selected="selected"</#if>>&nbsp;混合类支付</option>
										<option value="4" <#if vo ?? && vo.payment ?? && vo.payment == "4">selected="selected"</#if>>&nbsp;其他类支付</option>
									</select>
							 	</td>
							 	<td style="width:80px; text-align:right">
							 		订单状态：
							 	</td>
							 	<td style="width:100px; text-align:left">
								 	<select name = "orderStatus" id="orderStatus" style="width:100px">
										<option value="">&nbsp;请选择</option>
										<option value="1" <#if vo ?? && vo.orderStatus ?? && vo.orderStatus == 1>selected="selected"</#if>>&nbsp;新建</option>
										<option value="2" <#if vo ?? && vo.orderStatus ?? && vo.orderStatus == 2>selected="selected"</#if>>&nbsp;已支付</option>
										<option value="3" <#if vo ?? && vo.orderStatus ?? && vo.orderStatus == 3>selected="selected"</#if>>&nbsp;已取消</option>
									</select>
							 	</td>
							 	<td style="width:80px; text-align:right">
							 		订单类型：
							 	</td>
							 	<td style="width:100px; text-align:left">
								 	<select name = "orderType" id="orderType" style="width:100px">
										<option value="">&nbsp;请选择</option>
										<option value="1" <#if vo ?? && vo.orderType ?? && vo.orderType == 1>selected="selected"</#if>>&nbsp;图书类订单</option>
										<option value="2" <#if vo ?? && vo.orderType ?? && vo.orderType == 2>selected="selected"</#if>>&nbsp;音频频订单</option>
										<option value="3" <#if vo ?? && vo.orderType ?? && vo.orderType == 3>selected="selected"</#if>>&nbsp;视频类订单</option>
										<option value="4" <#if vo ?? && vo.orderType ?? && vo.orderType == 4>selected="selected"</#if>>&nbsp;其他类订单</option>
									</select>
							 	</td>
							 	<td style="width:70px; text-align:right">&nbsp;</td>
								<td style="width:100px; text-align:left">
									<button  onclick="return searchMaster()">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
								</td>
								<td style="width:70px; text-align:right">&nbsp;</td>
								<td style="width:100px; text-align:left">&nbsp;</td>
							</tr>					
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr style="height:28px;">
		                    <th>订单ID</th>
		                    <th>订单号</th>
		                    <th>用户ID</th>
		                    <th>总金额</th>
		                    <th>主账户支付金额</th>
		                    <th>副账户支付金额</th>
		                    <th>优惠金额</th>
		                    <th>创建时间</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 <#if pageFinder.data??>
			    			<#list pageFinder.data as orderMain>
					    		<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if orderMain.orderId??>${orderMain.orderId?c}</#if></td>
						      		<td><#if orderMain.orderNo??>${orderMain.orderNo}</#if></td>
						      		<td><#if orderMain.custId??>${orderMain.custId?c}</#if></td>
						      		<td><#if orderMain.totalPrice??>${orderMain.totalPrice?c}</#if></td>
						      		<td><#if orderMain.payMainPrice??>${orderMain.payMainPrice?c}</#if></td>
						      		<td><#if orderMain.paySubPrice??>${orderMain.paySubPrice?c}</#if></td>
						      		<td><#if orderMain.prePrice??>${orderMain.prePrice?c}</#if></td>
						      		<td><#if orderMain.creationDate??>${orderMain.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						      		<td>
						      			<a href="#" onclick="showDetail('${orderMain.orderId?c}')">详情</a>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
			    <div>
				    <div class="pagination rightPager"></div>
				    <div class="leftPager"><input type="text" name="currentPage" id="currentPage" value="${pageFinder.pageNo?c}" style="width: 40px;"/>&nbsp;<button  onclick="javascript:goPage();"><font size="1">GO</font></button>&nbsp;&nbsp;&nbsp;总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
