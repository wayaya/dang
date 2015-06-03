<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
<meta charset="UTF-8">
<title>新增敏感词</title>
<#include "../common/common-css.ftl">
</head>
<body>
 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>添加敏感作品</h3>
<form id="add" name="add" method="post" action="/illegalmedia/update.go" onSubmit="return checkForm();">
	<input type="hidden" id="illegalMediaId" name="illegalMediaId"  value="${illegalMedia.illegalMediaId?c}">
	<table class="table2">
		<tr>
			<td style="width: 10%" class="tdright">作品编号:</td><td class="tdleft"><input type="input" id="mediaId" name="mediaId" value="${illegalMedia.mediaId?c}"><span id="mediaIdInfo"></span></td>
		</tr>
		
		<tr>
		<td style="width: 10%" class="tdright">作品名称:</td><td class="tdleft"><input type="input" id="mediaName" name="mediaName" value="${illegalMedia.mediaName}"><span id="mediaNameInfo"></span></td>
		</tr>
		
		<tr>
		<td style="width: 10%" class="tdright">作者ID:</td><td class="tdleft"><input type="input" id="authorId" name="authorId" value="${illegalMedia.authorId?c}"><span id="authorIdInfo"></span></td>
		</tr>
		<tr>
		<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;<button type="reset">重置</button></td>
		</tr>
	</table>
</form>
</div>
</div>
</div>
</div>
</body>
<#include "../common/common-js.ftl">
<script type="text/javascript">
function checkForm(){
	var flag = true;
	var mediaId = $('#mediaId').val();
	if(mediaId == null || mediaId == ""){
		$('#mediaIdInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#mediaIdInfo').focus();
		flag = false;
	}
	var mediaName = $('#mediaName').val();
	if(mediaName == null || mediaName == ""){
		$('#mediaNameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#mediaNameInfo').focus();
		flag = false;
	}
	
	var authorId = $('#authorId').val();
	if(authorId == null || authorId == ""){
		$('#authorIdInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#authorIdInfo').focus();
		flag = false;
	}
	
	return flag;
}
</script>
</html>