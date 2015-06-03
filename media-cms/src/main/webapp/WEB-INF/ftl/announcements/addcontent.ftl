<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增公告内容</title>
<#include "../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
					<form id="add" name="add" method="post" action="/announcements/savecontent.go"  enctype="multipart/form-data" target="_parent" onSubmit="return checkForm();">
					<input type="hidden" id="categoryCode" name="categoryCode" value=${categoryCode}>
					<table class="table2" id="addtable" name="addtable">
						<tr>
							<td width="20%" class="tdright">公告内容:</td>
							<td width="80%" class="tdleft">
							 <textarea  rows="3" cols="50" id="content" name="content"></textarea>
							 <span id="contentInfo"></span></td>
						</tr>
						<tr>
							<td width="20%" class="tdright">URL:</td>
							<td width="80%" class="tdleft"> <input type="text" id="url" name="url" ><span id="urlInfo"></span></td>
						</tr>
						<tr id="tr_startDate">
							<td width="20%" class="tdright">开始时间:</td>
							<td width="80%" class="tdleft"> <input id="startDate" name="startDate" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\')}'})"/> <span id="startDateInfo"></span></td>
						</tr>
						<tr id="tr_endDate">
							<td width="20%" class="tdright">结束时间:</td>
							<td width="80%" class="tdleft"> <input type="text" id="endDate" name="endDate" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startDate\')}'})"><span id="endDateInfo"></span></td>
						</tr>
						<tr>
							<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
						</tr>
					</table>
				</form>
</body>
<#include "../common/common-js.ftl">
<script type="text/javascript">	
$(function(){
	$('#isactiverForever_yes').click(function(){
			//长期有效
			$('#tr_startDate').hide();
			$('#tr_endDate').hide();
		});
	$('#isactiverForever_no').click(function(){
			$('#tr_startDate').show();
			$('#tr_endDate').show();
	});
});		
function checkForm(){
	var flag = true;
	var name = $('#categoryName').val();
	var code = $('#categoryCode').val();
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	if(columnName == null || columnName == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#nameInfo').focus();
			flag =  false;
	}
	if(code == null || code == ""){
			$('#categoryCodeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#categoryCode').focus();
			flag =  false;
	}
	if($('#isactiverForever_no').attr('checked')){
		if(startDate == null || startDate == ""){
			$('#startDateInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#startDateInfo').focus();
			flag =  false;
	}
	if(endDate == null || endDate == ""){
		$('#endDateInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#endDateInfo').focus();
		flag =  false;
		}
	}
	
	return flag;
}
</script>

</html>