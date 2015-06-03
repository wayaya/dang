<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<style>
	.updateWidth{width:60%} 
</style>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>奖品管理&gt;&gt;修改奖品</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
										<#if successFlag??>
					    					<#if successFlag == 0>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/prize/list.go?lefttab=ul8" style="height: 20px; font-size: 20px;">更新成功，点击返回</a></#if>
					    				</#if>
									</td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div class="updateWidth">
				    <table class="table2" >
				       <form action="/prize/update.go" id="updateForm" name="updateForm" method="post">
				       <input type="hidden" value="${total}" id="totalPro"></input>
				       <input type="hidden" value="<#if prize??>${prize.probability?c}<#else>0</#if>" id="originalPro"></input>
		            	<tr><th width="30%">奖品名称&nbsp;&nbsp;</th><td><input id="prizeName" name="prizeName" value="<#if prize??>${prize.prizeName}</#if>"></td><td id="prizeNameInfo"></td></tr>
		            	<tr><td width="30%">奖品介绍&nbsp;&nbsp;</td><td><input id="introduce" name="introduce" value="<#if prize??>${prize.introduce}</#if>"> </td><td id="introduceInfo"></td></tr>
		            	<tr><th >奖品ID&nbsp;&nbsp;</th><td><input id="prizeId" name="prizeId" value="<#if prize??>${prize.prizeId?c}</#if>"></td><td id="prizeIdInfo"></td></tr>
		            	<tr><td >数量&nbsp;&nbsp;</td><td><input id="amount" name="amount" value="<#if prize??>${prize.amount?c}</#if>"></td><td id="amountInfo"></td></tr>
		            	<tr><th >概率【0-1之间小数】&nbsp;&nbsp;</th><td><input id="probability" name="probability"  value="<#if prize??>${prize.probability?c}</#if>"></td><td id="probabilityInfo"></td></tr>
		            	<tr><td >每日上限【无限制填0】&nbsp;&nbsp;</td><td><input id="dayLimit" name="dayLimit" value="<#if prize??>${prize.dayLimit?c}</#if>"></td><td id="dayLimitInfo"></td></tr>
		            	<tr><th >总上限【无限制填0】&nbsp;&nbsp;</th><td><input id="totalLimit" name="totalLimit" value="<#if prize??>${prize.totalLimit?c}</#if>"></td><td id="totalLimitInfo"></td></tr>
		            	<tr><td >奖品归属&nbsp;&nbsp; </td><td>
		            	      <select id="vestType" name="vestType">
			    					<option value="1">福袋</option>
							  </select>
							 </td> 
							 <td id="vestTypeInfo"></td>
						</tr>  
	               	    <tr><th >奖品类型&nbsp;&nbsp; </th><td>
		            	      <select name="prizeType">
			    					<option value="1" <#if prize.prizeType??><#if prize.prizeType?c=="1">selected="selected"</#if></#if>>金币</option>
			    					<option value="2" <#if prize.prizeType??><#if prize.prizeType?c=="2">selected="selected"</#if></#if>>图书章节</option>
			    					<option value="3" <#if prize.prizeType??><#if prize.prizeType?c=="3">selected="selected"</#if></#if>>道具</option>
			    					<option value="4" <#if prize.prizeType??><#if prize.prizeType?c=="4">selected="selected"</#if></#if>>包月</option>
							  </select>
							 </td> 
							 <td id="prizeTypeInfo"></td>
						</tr>  
	               	    <tr><td >生效时间：&nbsp;&nbsp;</td><td><input type="text" name="startDate" id="startDate" readonly="readonly" value="<#if prize??><#if prize.startDate??>${prize.startDate}</#if></#if>"></td><td id="startDateInfo"></td></tr>
		           		<tr><th >失效时间：&nbsp;&nbsp;</th><td><input type="text" name="endDate" id="endDate" readonly="readonly" value="<#if prize??><#if prize.endDate??>${prize.endDate}</#if></#if>"></td><td id="endDateInfo"></td></tr>
		           		<input type="hidden" name="mediaLotteryPrizeId" value=${prize.mediaLotteryPrizeId?c}>
	               	    <tr>
					      <td colspan="2" style="padding-left:150px">
					    	  <#if successFlag??>
			    				<#if successFlag == 1><image src="/images/save.jpg" onclick="javascript:addForm()" /></#if>
			    					<#else>
			    						<image src="/images/save.jpg" onclick="javascript:addForm();" />
			    				</#if>
			    			</td>
					   </tr> 
	               	    </form>
		            </table>
		            </div>
			    </div>
			    <div class="pagination rightPager"></div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	    $(function(){
			$('#startDate').calendar({maxDate:'#endDate',format:'yyyy-MM-dd HH:mm:ss'}); 
	   		$('#endDate').calendar({minDate:'#startDate',format:'yyyy-MM-dd HH:mm:ss'});
		})
		
		function addForm(){
	   		var flag = true;
	   		var prizeName = $('#prizeName').val();
	   		if(prizeName == null || prizeName == ""){
	   			$('#prizeNameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#prizeNameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var introduce = $('#introduce').val();
	   		if(introduce == null || introduce == ""){
	   			$('#introduceInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#introduceInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var prizeId = $('#prizeId').val();
	   		if (prizeId.search("^-?\\d+$") != 0) {
                  alert("请输入数字!");
		   		  $('#prizeIdInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
		   		  flag = false;
		   		  return;
	   		}else{
	   			$('#prizeIdInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var amount = $('#amount').val();
	   		if (amount.search("^-?\\d+$") != 0) {
                alert("请输入数字!");
	   			$('#amountInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   			return;
	   		}else{
	   			$('#amountInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var probability = $('#probability').val();
	   		if (""===probability||isNaN(probability)||probability<0||probability>1) { 
			　　　　$("#probabilityInfo").html('<img src="/images/wrong.jpg"/ style="width: 20px;"><span>请输入0-1之间的数字!</span>');
			　　　　return false;
	   		}else{
	   			$('#probabilityInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var dayLimit = $('#dayLimit').val();
	   		if(dayLimit == null || dayLimit == ""){
	   			$('#dayLimitInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#dayLimitInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var totalLimit = $('#totalLimit').val();
	   		if(totalLimit == null || totalLimit == ""){
	   			$('#totalLimitInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#totalLimitInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var vestType = $('#vestType').val();
	   		if(vestType == null || vestType == ""){
	   			$('#vestTypeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#vestTypeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var startDate = $('#startDate').val();
	   		if(startDate == null || startDate == ""){
	   			$('#startDateInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#startDateInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var endDate = $('#endDate').val();
	   		if(endDate == null || endDate == ""){
	   			$('#endDateInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#endDateInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		if(flag){
	   			var prot = (parseFloat($('#totalPro').val())-parseFloat($('#originalPro').val())+parseFloat(probability));
	   		    if(prot<1){
	   		    	alert("奖品概率总和不能小于1！！");
	   		    	return;
	   		    }
	   		    if(window.confirm("当前概率总和是:"+prot+"，确认要提交吗？")){
	   				$('#updateForm').submit();
				}	   				
	   		}else{
	   			return;
	   		}
	   	}
	   	
	   	//校验数字
	   	function validNumber(id){
	   		var value = $('#'+id).val();
	   		if (value.search("^-?\\d+$") != 0) {
	   			$("#"+id+"Info").html('<img src="/images/wrong.jpg"/ style="width: 20px;"><span>请输入数字!</span>');
	   		}else{
	   			$("#"+id+"Info").html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   	}
	   	
	    //校验概率
	   	function validPro(id){
	   		var value = $('#'+id).val();
	   		if (""===value||isNaN(value)) { 
			　　　　$("#"+id+"Info").html('<img src="/images/wrong.jpg"/ style="width: 20px;"><span>请输入0-1之间的数字!</span>');
			　　　　return false;
			　　} 
	   		if (value<0||value>1) {
	   			$("#"+id+"Info").html('<img src="/images/wrong.jpg"/ style="width: 20px;"><span>请输入0-1之间的数字!</span>');
	   		}else{
	   			$("#"+id+"Info").html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   	}
	   	
	</script>
  </body>
</html>
