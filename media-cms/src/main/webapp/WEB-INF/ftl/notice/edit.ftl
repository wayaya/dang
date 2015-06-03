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
							<td width="80%" class="tdleft">
							<select id="type" name="type"  style="width: 120px" onchange="noticeChange()">
			    				<#list noticeTypeList as noticeType>
							    	<option value="${noticeType.noticeTypeId}">${noticeType.name}</option>
							    </#list>
							</select>
						<span id="typeInfo"></span></td>
						</tr>
						<tr title="参数">
							<td width="20%" class="tdright">参数配置:</td>
							<td width="80%" class="tdleft"> 
							编号:<input type="text" id="id" name="id" /><font color='red'>(单品销量编号或打赏用户编号或专题编号)</font>
							</br>
							标识:<input type="text" id="code" name="code"/><font color='red'>(sale-畅销榜,comment_star-评星榜,update-更新榜,rewards-打赏榜)</font>
							</br>
							维度:<input type="text" id="dimension" name="dimension"/>
							<font color='red'>(sale-畅销排序,comment_star-评星排序,update-更新排序,rewards-打赏排序)</font>
							</td>
						</tr>
						<tr id="tr_startDate">
							<td width="20%" class="tdright">开始时间:</td>
							<td width="80%" class="tdleft"> <input id="startTime" name="startTime" value="${notice.startTime?substring(0,19)}" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})"/> <span id="startTimeInfo"></span></td>
						</tr>
						<tr id="tr_endDate">
							<td width="20%" class="tdright">结束时间:</td>
							<td width="80%" class="tdleft"> <input type="text" id="endTime" name="endTime" value="${notice.endTime?substring(0,19)}" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})"><span id="endTimeInfo"></span></td>
						</tr>
						<tr>
							<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
						</tr>
					</table>
				</form>
				<input type="hidden" id="paramter" name="paramter" value='${notice.parameter}'/>
				</div>
		</div>
</div>
</body>
<#include "../common/common-js.ftl">
<script type="text/javascript">	
$(document).ready(function(){
	var paramterJson = eval("(" + $('#paramter').val() + ")"); 
	$('#id').val(paramterJson.id);
	$('#code').val(paramterJson.code);
	$('#dimension').val(paramterJson.dimension);
});

function checkForm(){
	var flag = true;
	var name = $('#name').val();
	var type = $('#type').val();
	if(name == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#name').focus();
			flag =  false;
	}
	if(type == "" ||isNaN(type)){
			$('#typeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">必须是数字');
			alert(type);
			flag =  false;
	}else{
		//判断类型是否存在
		var existCodes = $('#existCodes').val();
		if(existCodes!=''){
			var codeArray = existCodes.split(",");
			for(var id in codeArray){
				if(id == type){
					alert("已经存在公告类型:"+type);
					return false;
				}
			}
		}
	}
	return flag;
}
</script>
</html>