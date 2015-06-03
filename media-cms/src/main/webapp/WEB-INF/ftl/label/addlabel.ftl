<!DOCTYPE html>
<html>
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
<form id="add" name="add" method="post" action="/label/save.go" onSubmit="return checkForm();">
	<table class="table2">
		<tr>
		<td>标签名称:</td><td><input type="input" id="name" name="name" ><span id="nameInfo" style="dispaly:inline"></span></td>
		</tr>
		<tr>
		<td>标签类型:</td><td><input type="radio" id="type" name="type" checked="true" value="1">作者&nbsp;<input type="radio" id="type" name="type" checked="true" value="1">书籍</td>
		</tr>
		<tr>
		<td>是否启用:</td><td><input type="radio" id="isenabled" name="isenabled" checked="true">启用&nbsp;<input type="radio" id="isenabled" name="isenabled">禁用</td>
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
<#include "../common/common-js.ftl">
<script type="text/javascript">
function checkForm(){
	var labelName = $('#name').val();
		if(labelName == null || labelName == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 10px;height: 10 px">请填写标签名称');
			$('#nameInfo').focus();
			return false;
		}
		$.ajax({
			type:"POST",
			url:"/label/save.go",
			data: $('#add').serialize(),
			dataType:"json",
			success: function(msg) {
				   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
					   $('#nameInfo').html('<font color="red">此标签已存在</font><img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
					   $('#name').attr("value","");
					   $('#name').focus();
				   }else{
					   $.dialog.tips('保存成功',2);
					   $('#name').attr("value","");
				   }
			}
		});
		return false;
}
</script>
</html>