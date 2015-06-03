<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增公告内容</title>
<#include "../common/common-css.ftl">
<style type="text/css">
		.dispalynone {
			display: none;
		}
</style>
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>栏目管理&gt;&gt;添加栏目</h3>
					<form id="add" name="add" method="post" action="/notice/save.go" onSubmit="return checkForm();">
					<table class="table2" id="addtable" name="addtable">
						<tr>
							<td width="20%" class="tdright">公告标题:</td>
							<td width="80%" class="tdleft">
							<input type="text" id="title" name="title" ><span id="titleInfo"></span></td>
						</tr>
						<tr>
							<td width="20%" class="tdright">URL:</td>
							<td width="80%" class="tdleft"> <input type="text" id="url" name="url" ><span id="urlInfo"></span></td>
						</tr>
						<tr>
							<td width="20%" class="tdright">类型:</td>
							<td width="80%" class="tdleft"> 
							<select id="type" name="type"  style="width: 120px" onchange="noticeChange()">
			    				<#list noticeTypeList as noticeType>
							    	<option value="${noticeType.noticeTypeId}">${noticeType.name}</option>
							    </#list>
							</select>
							<span id="urlInfo"></span>
							</td>
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
						<tr id="tr_listtype" >
							<td width="20%" class="tdright">榜单类型:</td>
							<td width="80%" class="tdleft"> 
							<!-- sale,comment_star,update,rewards -->
							<select id="code" name="code"  style="width: 120px">
							    	<option value="sale">畅销榜</option>
							    	<option value="comment_star">评星榜</option>
							    	<option value="update">更新榜</option>
							    	<option value="rewards">打赏榜</option>
							</select>
							<span id="codeInfo"></span></td>
						</tr>
						<tr id="tr_dimension" >
							<td width="20%" class="tdright">分类维度:</td>
							<td width="80%" class="tdleft"> 
							<select id="code" name="code"  style="width: 120px">
							    	<option value="sale">畅销排名</option>
							    	<option value="comment_star">评星排名</option>
							    	<option value="update">更新排名</option>
							    	<option value="rewards">打赏排名</option>
							</select>
							<span id="codeInfo"></span></td>
						</tr>
						<tr id="tr_startDate">
							<td width="20%" class="tdright">开始时间:</td>
							<td width="80%" class="tdleft"> <input id="startTime" name="startTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})"/> <span id="startTimeInfo"></span></td>
						</tr>
						<tr id="tr_endDate">
							<td width="20%" class="tdright">结束时间:</td>
							<td width="80%" class="tdleft"> <input type="text" id="endTime" name="endTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})"><span id="endTimeInfo"></span></td>
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
function hidden(){
	$("#tr_listtype").addClass("dispalynone");
	$("#tr_dimension").addClass("dispalynone");
}
hidden();
//
function noticeChange(){
	var type =  $("#type").val();
	switch(type){
		case 6: //榜单公告
		$("#tr_listtype").removeClass("dispalynone");
			break;
		case 7: //分类公告
			$("#tr_dimension").removeClass("dispalynone");
			break;
	}
}
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