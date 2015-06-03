<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增CP</title>
<#include "../../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>CP管理&gt;&gt;合同修改</h3>
<form id="add" name="add" method="post" action="/cp/contract/update.go">
	<input type="hidden" id="cpId" name="cpId" value='${contract.cpId?c}'>
	<table class="table2">
		<tr>
		<td>CP名称:</td><td><input type="input" id="cp_name" name="cp_name" value='<#if contract.cpName??>${contract.cpName}</#if>'  disabled="true"　readOnly="true" >
		</td>
		</tr>
		<tr>
		<td>合同编号:</td><td><input type="input" id="contractCode" name="contractCode" value='<#if contract.contractCode??>${contract.contractCode}</#if>'>
		</td>
		</tr>
		<tr>
		<td>合同类型:</td><td>
		<select id="type" name="type">
		<option value="0">独家</option>	
		<option value="1">非独家</option>	
		</select>
		</td>
		</tr>
		<tr >
		<td>合同开始日期:</td><td> <input id="startDate" name="startDate" class="Wdate" type="text" value='<#if contract.startDate??>${contract.startDate}</#if>' onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\')}'})"/> <span id="startDateInfo"></span></td>
		</tr>
		<tr >
		<td>合同结束日期:</td><td><input type="text" id="endDate" name="endDate" class="Wdate"  value='<#if contract.endDate??>${contract.endDate}</#if>' type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startDate\')}'})"><span id="endDateInfo"></span></td>
		</tr>
		<tr>
		<td>内容所得分成比例:</td><td><input type="input" id="contentRatio" name="contentRatio"  value='<#if contract.contentRatio??>${contract.contentRatio}</#if>'><span id="loginAccountInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td>运营扣减比例:</td><td><input type="input" id="manageRatio" name="manageRatio"  value='<#if contract.manageRatio??>${contract.manageRatio}</#if>'><span id="organizationCodeInfo"  style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td>其它扣减比例:</td><td><input type="radio" id="otherRadtio" name="otherRadtio" checked="true" value="1">有&nbsp;<input type="radio" id="hasBranch" name="hasBranch"  value="0">无</td>
		</tr>
		<tr>
		<td>内容免费比例:</td><td><input type="input" id="freeRatio" name="freeRatio"  value='<#if contract.freeRatio??>${contract.freeRatio}</#if>' ><span id="companyInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td>支付周期:</td><td>
		<select id="payCycle" name="payCycle">
			<option value="1">月度</option>
			<option value="2">季度</option>
			<option value="3">年度</option>
		</select>
		</td>
		</tr>
		<tr>
		<td>是否独家:</td><td>
		<input type="radio" id="isExclusive" name="isExclusive" value="1"　checked>是
		<input type="radio" id="isExclusive" name="isExclusive" value="0">否
		</td>
		</tr>
		<tr>
		<td>合同备注:</td><td>
		<textarea rows="3" id="comments"　name="comments" >
		<#if contract.comments??>${contract.comments}</#if>
	   </textarea> 
		</td>
		</tr>
		<tr>
		<td colspan="2"><button type="submit" >确认</button>&nbsp;&nbsp;<button type="reset">重置</button></td>
		</tr>
	</table>
</form>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
function checkForm(){
	var labelName = $('#name').val();
		if(labelName == null || labelName == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 10px;height: 10 px">请填写标签名称');
			$('#nameInfo').focus();
			return false;
		}
}
</script>
</html>