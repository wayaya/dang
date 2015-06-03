<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
<meta charset="UTF-8">
<title>新增标签</title>
<#include "../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>标签管理&gt;&gt;标签添加</h3>

<form id="add" name="add" method="post" action="/syspp/save.go" onSubmit="return checkForm();">
	<table class="table2">
		<tr>
		<td class="tdright">参数编码:</td><td class="tdleft" width="90%"><input type="text" id="keyName" name="keyName" onchange="checkCode()">
		<span id="keyNameInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td class="tdright">参数值:</td><td class="tdleft"><input type="text" id="keyValue" name="keyValue"  ><span id="keyValueInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td class="tdright">备注说明:</td><td class="tdleft"><input type="text" id="comment" name="comment" ><span id="commentInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td colspan="2">
		<#if userSessionInfo?? && userSessionInfo.f['75']?? >
			<button type="submit" >确认</button>&nbsp;&nbsp;<button type="reset">重置</button></td>
		</#if>
		</tr>
	</table>
</form>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
function checkCode(){
	$.ajax({
		type:"POST",
		url:"/syspp/checkcode.go",
		data: "keyName="+$('#keyName').val(),
		dataType:"json",
		success: function(msg) {
			   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
				   $('#keyNameInfo').append('<font color="red">此编码已存在</font><img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
				   $('#keyName').attr("value","");
				   $('#keyName').focus();
				   return ;
			   }else{
				   $('#keyNameInfo').html('<font color="green">此编码可以</font><img src="/images/right.jpg" style="width: 10px;height: 10 px">');
			   }
			   
		}
	});
}

function checkForm(){
	var flag = true;
	var keyName = $('#keyName').val();
	var keyValue = $('#keyValue').val();
	if(keyName == null || keyName == ""){
		$('#keyNameInfo').html('<img src="/images/wrong.jpg"/ style="width: 10px;height: 10 px">请填写标签名称');
		$('#keyNameInfo').focus();
		flag =  false;
	 }
		
	if(keyValue == null || keyValue == ""){
		$('#keyValueInfo').html('<img src="/images/wrong.jpg"/ style="width: 10px;height: 10 px">请填写标签名称');
		$('#keyValueInfo').focus();
		flag =  false;
	 }
	return flag;
		
}
</script>
</html>