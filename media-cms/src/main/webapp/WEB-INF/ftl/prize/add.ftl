<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
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
					<h3>奖品管理&gt;&gt;添加奖品</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
										<#if successFlag??>
					    					<#if successFlag == 1>&nbsp;&nbsp;&nbsp;&nbsp;json格式错误</#if>
					    				</#if>
									</td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div>
				       <form action="/prize/add.go" id="form" name="form" method="post">
				       <input type="hidden" value="<#if total??>${total}</#if>" id="totalPro"></input>
				       <input type="hidden" value="<#if prizeAmount??>${prizeAmount}</#if>" id="prizeAmount"></input>
				       <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
		            	<tr><td class="tdright">奖品名称&nbsp;&nbsp;</td><td><input id="prizeName" name="prizeName" value="<#if prizeName??>${one.prizeName}</#if>" onblur="validInput('prizeName')"></td><td class="tdleft" style="width:1100px" id="prizeNameInfo"></td></td></tr>
		            	<tr><td class="tdright">奖品介绍&nbsp;&nbsp;</td><td><input id="introduce" name="introduce" value="<#if introduce??>${one.introduce}</#if>" onblur="validInput('introduce')"></td><td class="tdleft" style="width:1100px" id="introduceInfo"></td></td></tr>
		            	<tr><td class="tdright">奖品ID【如果是铃铛币，请填0】</td><td><input id="prizeId" name="prizeId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="<#if prizeId??>${one.prizeId?c}</#if>" onblur="validNumber('prizeId')"></td><td class="tdleft" style="width:1100px" id="prizeIdInfo"></td></td></tr>
		            	<tr><td class="tdright">数量&nbsp;&nbsp;</td><td><input id="amount" name="amount" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="<#if amount??>${one.amount}</#if>" onblur="validNumber('amount')"></td><td class="tdleft" style="width:1100px" id="amountInfo"></td></td></tr>
		            	<tr><td class="tdright">概率【0-1之间小数】&nbsp;&nbsp;</td><td><input id="probability" name="probability"  value="<#if probability??>${one.probability}</#if>" onblur="validPro('probability')"></td><td class="tdleft" style="width:1100px" id="probabilityInfo"></td></td></tr>
		            	<tr><td class="tdright">每日上限【无限制填0】&nbsp;&nbsp;</td><td><input id="dayLimit" name="dayLimit" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="<#if dayLimit??>${one.dayLimit}</#if>" onblur="validNumber('dayLimit')"></td><td class="tdleft" style="width:1100px" id="dayLimitInfo"></td></td></tr>
		            	<tr><td class="tdright">总上限【无限制填0】&nbsp;&nbsp;</td><td><input id="totalLimit" name="totalLimit" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="<#if totalLimit??>${one.totalLimit}</#if>" onblur="validNumber('totalLimit')"></td><td class="tdleft" style="width:1100px" id="totalLimitInfo"></td></td></tr>
		            	<tr><td class="tdright">奖品归属&nbsp;&nbsp; </td><td>
		            	      <select id="vestType" name="vestType">
			    					<option value="1">福袋</option>
							  </select>
							 </td> 
							<td></td></td></tr>  
							
	               	    <tr><td class="tdright">奖品类型&nbsp;&nbsp; </td><td>
		            	      <select name="prizeType">
			    					<option value="1">金币</option>
			    					<option value="2">图书章节</option>
			    					<#--<option value="3">道具</option>-->
			    					<option value="4">包月</option>
							  </select>
							 </td> 
							<td></td></td></tr>  
	               	    <tr><td class="tdright">生效时间：&nbsp;&nbsp;</td><td><input type="text" name="startDate" id="startDate" readonly="readonly" value="<#if one??><#if one.startDate??>${one.startDate}</#if></#if>" ></td><td class="tdleft" id="startDateInfo"></td></td></tr>
		           		<tr><td class="tdright">失效时间：&nbsp;&nbsp;</td><td><input type="text" name="endDate" id="endDate" readonly="readonly" value="<#if one??><#if one.endDate??>${one.endDate}</#if></#if>"></td><td class="tdleft" id="endDateInfo"></td></td></tr>
	               	    <tr>
					      <td colspan="2" style="padding-left:150px">
								<#if userSessionInfo?? && userSessionInfo.f['225']?? >
					    	 		 <#if successFlag??>
					    			 <#if successFlag == 1><image src="/images/save.jpg" onclick="javascript:addForm()" /></#if>
									 <#else>
					    			<image src="/images/save.jpg" onclick="javascript:addForm()" />
					    			</#if>
					    		</#if>
								</td>
					    			<td style="width:1100px"></td>
						      	</tr>
		            		</table>
		            	</form>
		            </div>
			    </div>
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
		});	
			
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
	   		    var prot = (parseFloat($('#totalPro').val())+parseFloat(probability));
	   		    prot = prot.toFixed(5)
	   		   
	   		    if(prot<1){
	   		    	alert("奖品概率总和不能小于1！！");
	   		    	return;
	   		    }else {
	   		    	var prizeTotal = $("#prizeAmount").val();
	   		    	if(prizeTotal<3){
	   		    		alert("请注意当前有效奖品个数为:"+prizeTotal+",不足3个,此次提交1个奖品，请继续添加奖品，确保有效奖品最少是3个！！");
	   		    	}
	   				if(window.confirm("当前概率总和是:"+prot+"，确认要提交吗？")){
	   					$('#form').submit();
	   				}else{
	   					return false;
	   				}
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
