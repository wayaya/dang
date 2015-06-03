<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
<meta charset="UTF-8">
<title>敏感词修改</title>
<#include "../common/common-css.ftl">
</head>
<body>
 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>敏感词管理&gt;&gt;敏感词修改</h3>
<form id="add" name="add" method="post" action="/illword/update.go" onSubmit="return checkForm();">
<input type="hidden" id="illegalWordId" name="illegalWordId" value="${illWord.illegalWordId?c}" readonly>
	<table class="table2">
		<tr>
		<td style="width: 10%" class="tdright">敏感词:</td><td class="tdleft"><input type="input" id="words" name="words" value="${illWord.words}" onBlur="checkRepeat()"><span id="wordsInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td style="width: 10%" class="tdright">敏感词类型:</td><td class="tdleft">
		<input type="radio" id="type" name="type" checked="true" value="0" <#if illWord.type=0>checked="checked"</#if>>原创内容&nbsp;
		<input type="radio" id="type" name="type"  value="1" <#if illWord.type=1>checked="checked"</#if> >其它</td>
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
<script type="text/javascript">
function checkRepeat(){
	$.ajax({
		type:"POST",
		url:"/illword/save.go",
		data: $('#add').serialize(),
		dataType:"json",
		success: function(msg) {
			   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
				   $('#wordsInfo').html('<font color="red">此敏感词已存在</font><img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
				   $('#words').attr("value","");
				   $('#words').focus();
			   }else{
				   $.dialog.tips('保存成功',2);
				   $('#words').attr("value","");
			   }
		}
	});
}
function checkForm(){
	var labelName = $('#words').val();
	if(labelName == null || labelName == ""){
		$('#wordsInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#wordsInfo').focus();
		return false;
	}
	return true;
}
</script>
</html>