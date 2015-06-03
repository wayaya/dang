<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>销售主体excel批量导入</title>
<#include "../../common/common-css.ftl">
<style type="text/css">
.ifile {
	position: absolute;
	opacity: 0;
	filter: alpha(opacity = 0);
	-moz-opacity: 0;
	margin-top: 4px;
	display:none;
}
</style>
</head>
<body>
<form id="add" name="add" method="post" action="/column/content/excel.go"  enctype="multipart/form-data" target="_parent" onSubmit="return checkForm();">
	<input type="hidden" id="columnId" name="columnId" value='${RequestParameters["columnId"]}'>
	<table class="table2" id="addtable" name="addtable">
		<tr>
		<td>Excel文件:</td><td> <!-- txtfilename和upfilename的size应该一样，完全遮盖 -->
		<input type="file" name="upfilename" id="upfilename"  class="ifile"  onchange="txtfilename.value=this.value; ">
		<input type="text"  name="txtfilename"   id="txtfilename" size="20" readonly style="height: 25px;" />
		<span class="title" > <img src="/images/column/upload.gif" width="80" height="25" align="absmiddle" onclick="upfilename.click();" style="z-index: 999;" /></span><span id="txtfilenameInfo"></span></td>
		</tr>
		<tr>
		<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
		</tr>
	</table>
</form>
</body>
<#include "../../common/common-js.ftl">
<script type="text/javascript">	
function checkForm(){
	var flag = true;
	var columnName = $('#txtfilename').val();
	if(columnName == null || columnName == ""){
			$('#txtfilenameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#txtfilenameInfo').focus();
			flag =  false;
	}
	return flag;
}
</script>

</html>