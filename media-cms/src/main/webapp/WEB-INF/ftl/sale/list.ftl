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
				link_to: encodeURI('/saleBase/sale/list.go?page=__id__'+"&"+$("#master_list_form").serialize())
		    });
		    $('#creationDateStart').calendar({maxDate:'#creationDateEnd',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#creationDateEnd').calendar({minDate:'#creationDateStart',format:'yyyy-MM-dd HH:mm:ss'});
		});		
		function searchMaster(){
	   		var result = checkParms();
	   		if(result != ""){
	   			alert(result);
	   			return false;
	   		}
	   		$('#master_list_form').submit();
	   	}
	   	
	   	function openDetail(consumerConsumeId){
	   		$.dialog({title:'',content:'url:/saleBase/sale/detail.go?consumerConsumeId='+consumerConsumeId,
	   		icon:'succeed',width:1280,height:720,fixed:false,lock:true
			});
	   	}
	   	
	   	function checkParms(){
	   		$('#consumerConsumeId').val($.trim($('#consumerConsumeId').val()));
	   		$('#custId').val($.trim($('#custId').val()));
	   		$('#monthlyId').val($.trim($('#monthlyId').val()));
	   		$('#consumeSerialNo').val($.trim($('#consumeSerialNo').val()));
	   		$('#userName').val($.trim($('#userName').val()));
			if($('#consumerConsumeId').val() != "" && isNaN($('#consumerConsumeId').val())){
				return "消费记录id必须为数字格式！";
			}
			if($('#custId').val() != "" && isNaN($('#custId').val())){
				return "用户ID必须为数字格式！";
			}
			if($('#monthlyId').val() != "" && isNaN($('#monthlyId').val())){
				return "包月ID必须为数字格式！";
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
				<h3>收入管理&gt;&gt;收入管理</h3>
				<div class="mrdiv">
		      		<form action="/saleBase/sale/list.go" method="post" id="master_list_form">
			      		 <table>
		        			<tr style="height:30px;">
								<td style="width:70px; text-align:right">
									消费记录ID：
								</td>
								<td style="width:120px; text-align:left">
									<input type="text" style="width: 120px;" name="consumerConsumeId" id="consumerConsumeId" value="<#if vo??><#if vo.consumerConsumeId??>${vo.consumerConsumeId?c}</#if></#if>">
								</td>
							 	<td style="width:70px; text-align:right">
							 		用户ID：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="custId" id="custId" value="<#if vo??><#if vo.custId??>${vo.custId?c}</#if></#if>">
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		消费流水号：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="consumeSerialNo" id="consumeSerialNo" value="<#if vo??><#if vo.consumeSerialNo??>${vo.consumeSerialNo}</#if></#if>">
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		是否已结算：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<select name = "isFinalEstimate" id="isFinalEstimate" style="width:120px">
										<option value="">&nbsp;请选择</option>
										<option value="1" <#if vo ?? && vo.isFinalEstimate ?? && vo.isFinalEstimate == 1>selected="selected"</#if>>&nbsp;已结算</option>
										<option value="0" <#if vo ?? && vo.isFinalEstimate ?? && vo.isFinalEstimate == 0>selected="selected"</#if>>&nbsp;未结算</option>
									</select>
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		包月ID：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="monthlyId" id="monthlyId" value="<#if vo??><#if vo.monthlyId??>${vo.monthlyId?c}</#if></#if>">
							 	</td>
							</tr>
							<tr style="height:30px;">
								<td style="width:70px; text-align:right">
							 		用户名：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="userName" id="userName" value="<#if vo??><#if vo.userName??>${vo.userName}</#if></#if>">
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		消费类型：
							 	</td>
							 	<td style="width:120px; text-align:left">
								 	<select name = "consumeTpye" id="consumeTpye" style="width: 120px;" >
										<option value="">&nbsp;请选择</option>
										<option value="2" <#if vo ?? && vo.consumeTpye ?? && vo.consumeTpye == 2>selected="selected"</#if>>&nbsp;道具消费</option>
										<option value="3" <#if vo ?? && vo.consumeTpye ?? && vo.consumeTpye == 3>selected="selected"</#if>>&nbsp;包月消费</option>
										<option value="4" <#if vo ?? && vo.consumeTpye ?? && vo.consumeTpye == 4>selected="selected"</#if>>&nbsp;打赏消费</option>
										<option value="5" <#if vo ?? && vo.consumeTpye ?? && vo.consumeTpye == 5>selected="selected"</#if>>&nbsp;福袋消费</option>
									</select>
							 	</td>					 	
							 	<td style="width:70px; text-align:right">
							 		开始时间：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="creationDateStart" id="creationDateStart" readonly="readonly" value="<#if vo??><#if vo.creationDateStart??>${vo.creationDateStart}</#if></#if>">
							 	</td>
							 	<td style="width:70px; text-align:right">
							 		结束时间：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="creationDateEnd" id="creationDateEnd" readonly="readonly" value="<#if vo??><#if vo.creationDateEnd??>${vo.creationDateEnd}</#if></#if>">
							 	</td>
								<td style="width:70px; text-align:right">&nbsp;</td>
								<td style="width:120px; text-align:left">
									<button  onclick="return searchMaster()">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr style="height:28px;">
		                    <th>消费记录ID</th>
		                    <th>消费流水号</th>
		                    <th>消费类型</th>
		                    <th>用户ID</th>
		                    <th>包月ID</th>		                    
		                    <th>主账户支付金额</th>
		                    <th>副账户支付金额</th>
		                    <th>是否已结算</th>
		                    <th>创建时间</th>
		                    <th>包月结束时间</th>
		                    <th>操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 <#if pageFinder?? && pageFinder.data??>
			    			<#list pageFinder.data as consumerConsume>
					    		<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if consumerConsume.consumerConsumeId??>${consumerConsume.consumerConsumeId?c}</#if></td>
						      		<td><#if consumerConsume.consumeSerialNo??>${consumerConsume.consumeSerialNo}</#if></td>
						      		<td>
							      		<#if consumerConsume.consumeTpye??>
							      			<#if consumerConsume.consumeTpye==2>
							      				道具消费
							      			<#elseif consumerConsume.consumeTpye==3>
							      				包月消费
							      			<#elseif consumerConsume.consumeTpye==4>
							      				打赏消费
							      			<#elseif consumerConsume.consumeTpye==5>
							      				福袋消费
							      			<#else>
							      				其他消费
							      			</#if>
							      		</#if>
						      		</td>
						      		<td><#if consumerConsume.custId??>${consumerConsume.custId?c}</#if></td>
						      		<td><#if consumerConsume.monthlyId??>${consumerConsume.monthlyId?c}</#if></td>
						      		<td><#if consumerConsume.mainGoldUsed??>${consumerConsume.mainGoldUsed?c}</#if></td>
						      		<td><#if consumerConsume.subGoldUsed??>${consumerConsume.subGoldUsed?c}</#if></td>
						      		<td>
							      		<#if consumerConsume.isFinalEstimate??>
							      			<#if consumerConsume.isFinalEstimate==1>
							      				是
							      			<#elseif consumerConsume.isFinalEstimate==0>
							      				否
							      			<#else>
							      			</#if>
							      		</#if>
						      		</td>
						      		<td><#if consumerConsume.creationDate??>${consumerConsume.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						      		<td><#if consumerConsume.monthlyEndTime??>${consumerConsume.monthlyEndTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						      		<td>
						      			<a href="#" onclick="openDetail('${consumerConsume.consumerConsumeId?c}')">详情</a>
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
	<#include "../common/common-js.ftl">
  </body>
</html>
