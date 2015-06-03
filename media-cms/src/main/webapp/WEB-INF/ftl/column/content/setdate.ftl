<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改栏目</title>
</head>
<body>
<form id="add" name="add" method="post" action="/column/content/updatedate.go" onSubmit="return checkForm();" target="_parent" >
	<input type="hidden" id="contentIds" name="contentIds" value="${contentIds}">
	<input type="hidden" id="columnCode" name="columnCode" value='${RequestParameters["columnCode"]}'>
	<table class="table2" width="80%" id="addtable" name="addtable">
		<tr id="tr_startDate">
		<td>开始时间:</td><td> <input id="startDate" name="startDate" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\')}'})"/> <span id="startDateInfo"></span></td>
		</tr>
		<tr id="tr_endDate">
		<td>结束时间:</td><td><input type="text" id="endDate" name="endDate" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startDate\')}'})"><span id="endDateInfo"></span></td>
		</tr>
		</div>
		<tr>
		<td colspan="2" align="center"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
		</tr>
	</table>
</form>
</body>

<script type="text/javascript">	
function checkForm(){
	var flag = true;
	var columnName = $('#name').val();
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	if(columnName == null || columnName == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#nameInfo').focus();
			flag = false;
	}
	if(startDate == null || startDate == ""){
			$('#startDateInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#startDateInfo').focus();
			flag = false;
	}
	if(endDate == null || endDate == ""){
		$('#endDateInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#endDateInfo').focus();
		flag = false;
	}
	return flag;
}
</script>
<#include "../../common/common-js.ftl">
</html>