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
<h3>敏感词管理&gt;&gt;敏感词添加</h3>
<form id="add" name="add" method="post" action="/illword/save.go"  onSubmit="return checkForm();">
	<table class="table2">
		<tr>
		<td style="width: 10%" class="tdright">敏感词:</td><td class="tdleft"><input type="input" id="words" name="words"><span id="wordsInfo"></span></td>
		</tr>
		<tr>
		<td style="width: 10%" class="tdright">敏感词类型:</td><td class="tdleft"><input type="radio" id="type" name="type" checked="true" value="0">原创内容&nbsp;<input type="radio" id="type" name="type" checked="true" value="1">评论或其它</td>
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
	var labelName = $('#words').val(); 
	var labelName=labelName.replace(/(^\s+)|(\s+$)/g,"");
	if(labelName == null || labelName == ""){
		$('#wordsInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#wordsInfo').focus();
		return false;
	}
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
	return false;
}
</script>
</html>