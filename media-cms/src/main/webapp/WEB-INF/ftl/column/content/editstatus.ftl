<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改销售主体状态</title>
</head>
<body>
<form id="add" name="add" method="post" action="/column/content/updatestatus.go" target="_parent" >
	<input type="hidden" id="contentIds" name="contentIds" value="${contentIds}">
	<input type="hidden" id="columnCode" name="columnCode" value='${RequestParameters["columnCode"]}'>
	<table class="table2" width="80%" id="addtable" name="addtable">
		<tr>
		<td align="center">
			<input type="radio" id="status" name="status" value="${force_valid?c}" checked>强制有效
			<input type="radio" id="status" name="status" value="${force_invalid?c}">强制无效
			<input type="radio" id="status" name="status" value="${normal}">正常显示
		 </td>
		 <tr>
		 <td align="center">
		 <button type="submit">确认</button>
		</td>
		</tr>
	</table>
</form>
</body>

<#include "../../common/common-js.ftl">
</html>