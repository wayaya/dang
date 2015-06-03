<!DOCTYPE html>
<html style="width:96%">
<head>
<meta charset="UTF-8">
<title>新增公告类型</title>
<#include "../../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>公告管理&gt;&gt;添加公告类型</h3>
					<form id="add" name="add" method="post" onSubmit="return checkForm();" action="/notice/type/save.go" target="_parent">
					<table class="table2" id="addtable" name="addtable">
						<tr>
							<td width="20%" class="tdright">公告类型名称:</td>
							<td width="80%" class="tdleft">
							<input type="text" id="name" name="name" ><span id="nameInfo"></span></td>
						</tr>
						<tr>
							<td width="20%" class="tdright">公告类型值:</td>
							<td width="80%" class="tdleft"> <input type="text" id="type" name="type" ><span id="typeInfo"></span></td>
						</tr>
						<!--  
						<tr>
							<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
						</tr>
						-->
					</table>
				</form>
				<input type="hidden" id="existCodes" name="existCodes"   value="${existCodes}" titile="已经存的公告类型" />
				</div>
		</div>
</div>
</body>
<#include "../../common/common-js.ftl">
<script type="text/javascript">	
	var api = frameElement.api, W = api.opener;
	form = $('#add');
// 操作对话框
	// 自定义按钮
	api.button(
		{
			name: '确定',
			callback: function(){
				if (checkForm())
					form.submit();
				return false;
			},
			focus: true
		},
		{
			name: '取消'
		}
		/*, 更多按钮.. */
	)
	// 锁屏
	.lock();

function checkForm(){
	var flag = true;
	var name = $('#name').val();
	var type = $('#type').val();
	if(name == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#name').focus();
			flag =  false;
	}
	if(type == "" ||isNaN(type)|| type >=1000000){
			$('#typeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">必须是小于1000000的数字');
			flag =  false;
	}else{
		//判断类型是否存在
		var existCodes = $('#existCodes').val();
		if(existCodes!=''){
			var codeArray = existCodes.split(",");
			for(var id in codeArray){
				if(codeArray[id] == type){
					alert("已经存在公告类型:"+type);
					return false;
				}
			}
		}
	}
	return flag;
}
</script>

</html>