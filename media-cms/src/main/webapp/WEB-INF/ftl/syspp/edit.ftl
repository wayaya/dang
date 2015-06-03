<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
<meta charset="UTF-8">
<title>标签修改</title>
<#include "../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>系统参数管理&gt;&gt;参数修改</h3>
<form id="edit" name="edit" method="post" action="/syspp/update.go" onSubmit="return checkForm();">
<input type="hidden" id="propertyId" name="propertyId" value="${SysProperties.propertyId?c}">
 	<table class="table2">
		<tr>
		<td class="tdright">参数编码:</td><td class="tdleft"><input type="text" id="keyName" name="keyName" value="${SysProperties.keyName}" disabled="disabled"><span id="keyNameInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td class="tdright">参数值:</td><td class="tdleft"><input type="text" id="keyValue" name="keyValue" value="${SysProperties.keyValue}" ><span id="keyValueInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td class="tdright">备注说明:</td><td class="tdleft"><input type="text" id="comment" name="comment" value="<#if SysProperties.comment??>${SysProperties.comment}</#if>"><span id="commentInfo" style="dispaly:inline"></span></td>
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