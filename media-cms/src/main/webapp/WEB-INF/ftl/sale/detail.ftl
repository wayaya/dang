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
		}); 
		
		function openDetail(consumerConsumeId){
			$.dialog({title:'收入明细列表页',content:'url:/saleBase/sale/detail.go?consumerConsumeId='+consumerConsumeId+'&showFlag=1',
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
					<h3>收入管理&gt;&gt;收入管理&gt;&gt;收入详情</h3>					
				    <div>
					     <table border="1" bordercolor="#a0c6e5" rules=none>
					     <tr height="35"><td colspan="4"><br/><br/></td></tr>
		    				<tr height="35">
				    			<td align="center">消费记录ID：</td><td><input type="text" name="consumerConsumeId" id="consumerConsumeId" value="<#if consumerConsume?? && consumerConsume.consumerConsumeId??>${consumerConsume.consumerConsumeId?c}</#if>"></td>
				    			<td align="center">用户ID：</td><td><input type="text" name="custId" id="custId" value="<#if consumerConsume?? && consumerConsume.custId??>${consumerConsume.custId?c}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="center">消费类型：</td>
				    			<td>				
					    			<select name = "consumeTpye" id="consumeTpye" style="width:150px">
										<option value="">&nbsp;请选择</option>
										<option value="2" <#if consumerConsume ?? && consumerConsume.consumeTpye ?? && consumerConsume.consumeTpye == 2>selected="selected"</#if>>&nbsp;道具消费</option>
										<option value="3" <#if consumerConsume ?? && consumerConsume.consumeTpye ?? && consumerConsume.consumeTpye == 3>selected="selected"</#if>>&nbsp;包月消费</option>
										<option value="4" <#if consumerConsume ?? && consumerConsume.consumeTpye ?? && consumerConsume.consumeTpye == 4>selected="selected"</#if>>&nbsp;打赏消费</option>
										<option value="5" <#if consumerConsume ?? && consumerConsume.consumeTpye ?? && consumerConsume.consumeTpye == 5>selected="selected"</#if>>&nbsp;福袋消费</option>
									</select>
				    			</td>
				    			<td align="center">是否已结算：</td>
				    			<td>				    			
				    				<select name = "isFinalEstimate" id="isFinalEstimate" style="width:150px">
										<option value="">&nbsp;请选择</option>
										<option value="1" <#if consumerConsume ?? && consumerConsume.isFinalEstimate ?? && consumerConsume.isFinalEstimate == 1>selected="selected"</#if>>&nbsp;已结算</option>
										<option value="0" <#if consumerConsume ?? && consumerConsume.isFinalEstimate ?? && consumerConsume.isFinalEstimate == 0>selected="selected"</#if>>&nbsp;未结算</option>
									</select>
				    			</td>
					      	</tr>
					      	<tr height="35">
				    			<td align="center">主账户消费：</td><td><input type="text" name="mainGoldUsed" id="mainGoldUsed" value="<#if consumerConsume?? && consumerConsume.mainGoldUsed??>${consumerConsume.mainGoldUsed?c}</#if>"></td>
				    			<td align="center">副账户消费：</td><td><input type="text" name="subGoldUsed" id="subGoldUsed" value="<#if consumerConsume?? && consumerConsume.subGoldUsed??>${consumerConsume.subGoldUsed?c}</#if>"></td>
					      	</tr>
					      	<tr>
				    			<td align="center">消费流水号：</td><td><input type="text" name="consumeSerialNo" id="consumeSerialNo" value="<#if consumerConsume?? && consumerConsume.consumeSerialNo??>${consumerConsume.consumeSerialNo}</#if>"></td>
				    			<td align="center">包月ID：</td><td><input type="text" name="monthlyId" id="monthlyId" value="<#if consumerConsume?? && consumerConsume.monthlyId??>${consumerConsume.monthlyId?c}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="center">包月开始时间：</td><td><input type="text" name="monthlyStartTime" id="monthlyStartTime" value="<#if consumerConsume?? && consumerConsume.monthlyStartTime??>${consumerConsume.monthlyStartTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"></td>
				    			<td align="center">包月结束时间：</td><td><input type="text" name="monthlyEndTime" id="monthlyEndTime" value="<#if consumerConsume?? && consumerConsume.monthlyEndTime??>${consumerConsume.monthlyEndTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"></td>
					      	</tr>
					      	<tr height="35">
				    			<td align="center">消费时间：</td><td><input type="text" name="creationDate" id="creationDate" value="<#if consumerConsume?? && consumerConsume.creationDate??>${consumerConsume.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"></td>
				    			<td colspan="2"></td>
					      	</tr>
					      	<tr height="35"><td colspan="4"><br/><br/></td></tr>
					      	<tr height="35">
			    				<td colspan="4" align="center">
			    					<#if consumerConsume ?? && consumerConsume.consumeTpye ?? && consumerConsume.consumeTpye == 2>
			    						<button onclick="openDetail('${consumerConsume.consumerConsumeId?c}')">&nbsp;查看明细&nbsp;</button>
			    					</#if>				
									<#if consumerConsume ?? && consumerConsume.consumeTpye ?? && consumerConsume.consumeTpye == 4>
										<button onclick="openDetail('${consumerConsume.consumerConsumeId?c}')">&nbsp;查看明细&nbsp;</button>
									</#if>			    				
			    				</td>					    	
					      	</tr>
					      	<tr height="35"><td colspan="4"><br/><br/></td></tr>
			            </table>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>