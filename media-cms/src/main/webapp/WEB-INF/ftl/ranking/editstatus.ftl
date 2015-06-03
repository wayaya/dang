<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>改销售主体状态</title>
</head>
<body>
<form id="add" name="add" method="post" action="/ranking/updatestatus.go" target="_parent" >
    <input type="hidden" id="listIds" name="listIds" value="${listIds}">
    <input type="hidden" id="listType" name="listType" value="${listType}">
	<table class="table2" width="80%" id="addtable" name="addtable">
		<tr>
		<td align="center">
			<input type="radio" id="status" name="status"  value="${normal}" checked>正常显示
			<input type="radio" id="status" name="status" value="${force_invalid?c}" >强制无效
			<input type="radio" id="status" name="status" value="${force_valid?c}" >强制推荐
		 </td>
		 <tr>
		 <td align="center">
		 <button type="submit">确认</button>
		</td>
		</tr>
	</table>
</form>
</body>

<#include "../common/common-js.ftl">
</html>