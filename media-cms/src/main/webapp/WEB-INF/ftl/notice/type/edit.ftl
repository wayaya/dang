<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改公告内容</title>
<#include "../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>公告管理&gt;&gt;修改公告</h3>
					<form id="add" name="add" method="post" action="/notice/update.go" onSubmit="return checkForm();">
					<input type="hidden" id="noticeId" name="noticeId" value="${notice.noticeId}">
					<table class="table2" id="addtable" name="addtable">
						<tr>
							<td width="20%" class="tdright">公告标题:</td>
							<td width="80%" class="tdleft">
							<input type="text" id="title" name="title"  value="${notice.title}"><span id="titleInfo"></span></td>
						</tr>
						<tr>
							<td width="20%" class="tdright">URL:</td>
							<td width="80%" class="tdleft"> <input type="text" id="url" name="url" value="${notice.url}"><span id="urlInfo"></span></td>
						</tr>
						<tr>
							<td width="20%" class="tdright">类型:</td>
							<td width="80%" class="tdleft"> <input type="text" id="type" name="type" ><span id="urlInfo"></span></td>
						</tr>
						<tr id="tr_startDate">
							<td width="20%" class="tdright">开始时间:</td>
							<td width="80%" class="tdleft"> <input id="startTime" name="startTime" value="${notice.startTime}" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})"/> <span id="startTimeInfo"></span></td>
						</tr>
						<tr id="tr_endDate">
							<td width="20%" class="tdright">结束时间:</td>
							<td width="80%" class="tdleft"> <input type="text" id="endTime" name="endTime" value="${notice.endTime}" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})"><span id="endTimeInfo"></span></td>
						</tr>
						<tr>
							<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
						</tr>
					</table>
				</form>
				</div>
		</div>
</div>
</body>
<#include "../common/common-js.ftl">
<script type="text/javascript">	
function checkForm(){
	var flag = true;
	var name = $('#categoryName').val();
	var code = $('#categoryCode').val();
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
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
		if(startTime == null || startTime == ""){
			$('#startTimeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#startTimeInfo').focus();
			flag =  false;
	}
	if(endTime == null || endTime == ""){
		$('#endTimeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#endTimeInfo').focus();
		flag =  false;
		}
	}
	
	return flag;
}
</script>

</html>