<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
<meta charset="UTF-8">
<title>修改榜单</title>
<#include "../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>榜单管理&gt;&gt;修改榜单</h3>
<form id="add" name="add" method="post" action="/ranking/update.go" target="_parent" onSubmit="return checkForm();">
	<input type="hidden" id="categoryId" name="categoryId" value="${list.categoryId?c}">
	<table class="table2" id="addtable" name="addtable">
		<tr>
		<td>榜单名称:</td><td> <input type="text" id="categoryName" name="categoryName" value="${list.categoryName}"><span id="categoryNameInfo"></span></td>
		</tr>
		<tr>
		<td>榜单标识:</td><td> <input type="text" id="categoryCode" name="categoryCode" value="${list.categoryCode}" disabled="disabled"><span id="categoryCodeInfo"></span></td>
		</tr>
		<tr>
		<td>排序维度:</td><td> <input type="text" id="orederDimension" name="orederDimension" value="${list.orederDimension}"><span id="orederDimensionInfo"></span></td>
		</tr>
		<tr>
		<td>最少数量:</td><td> <input type="text" id="minRecords" name="minRecords" value="${list.minRecords?c}"><span id="minRecordsInfo"></span></td>
		</tr>
		<tr>
		<td>最多数量:</td><td> <input type="text" id="maxRecords" name="maxRecords" value="${list.maxRecords?c}"><span id="maxRecordsInfo"></span></td>
		</tr>
		<tr>
		<td>数据周期:</td><td> <input type="text" id="days" name="days" value="${list.days?c}" ><span id="daysInfo"></span></td>
		</tr>
		<tr>
		<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
		</tr>
	</table>
</form>
</div>
</body>
<script type="text/javascript">	
function checkCode(){
	$.ajax({
		type:"POST",
		url:"/ranking/checkcode.go",
		data: "categoryCode="+$('#categoryCode').val(),
		dataType:"json",
		success: function(msg) {
			   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
				   $('#categoryCodeInfo').html('<font color="red">此编码已存在</font><img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
				   $('#categoryCode').attr("value","");
				   $('#categoryCode').focus();
				   return ;
			   }
		}
	});
}


function checkForm(){
	var flag = true;
	var categoryName = $('#categoryName').val();
	var categoryCode = $('#categoryCode').val();
	
	var minRecords = $('#minRecords').val();
	var maxRecords = $('#maxRecords').val();
	var days = $('#days').val();
	
	if(categoryName == ""){
			$('#categoryNameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#categoryName').focus();
			flag =  false;
	}
	
	if(categoryCode == ""){
		$('#categoryCodeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#categoryCode').focus();
		flag =  false;
}
	if(minRecords == ""){
			$('#minRecordsInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#minRecords').focus();
			flag =  false;
	}else {
		if(isNaN(minRecords)){
			$('#minRecordsInfo').html("<font color='red'>必须是整数</font>");
			flag =  false;
		}
		
	}
	if(maxRecords == ""){
			$('#maxRecordsInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#maxRecords').focus();
			flag =  false;
	}else{
		if(isNaN(maxRecords)){
			$('#maxRecordsInfo').html("<font color='red' >必须是整数</font>");
			flag =  false;
		}
	}
	if(days == ""){
			$('#daysInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#days').focus();
			flag =  false;
	}else{
		if(isNaN(days)){
			$('#daysInfo').html("<font color='red' >必须是整数</font>");
			flag =  false;
		}
	}
	return flag;
}
</script>
<#include "../common/common-js.ftl">
</html>