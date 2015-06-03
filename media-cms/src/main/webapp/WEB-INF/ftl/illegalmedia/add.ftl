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
<form id="add" name="add" method="post" action="/illegalmedia/save.go" target="_parent" onsubmit="return checkMediaId();">
	<table class="table2" >
		<tr>
			<td  style="wdith:20%;">作品编号:</td><td class="tdleft"><input type="input" id="mediaId" name="mediaId" onchange="javascript:checkMediaId();"><span id="mediaIdInfo"></span></td>
		</tr>
		<!--  
		<tr>
		<td>作品名称:</td><td><input type="input" id="mediaName" name="mediaName"><span id="mediaNameInfo"></span></td>
		</tr>
		
		<tr>
		<td>作者ID:</td><td><input type="input" id="authorId" name="authorId"><span id="authorIdInfo"></span></td>
		</tr>
		-->
		<tr>
		<td>说明:</td><td class="tdleft">
		<textarea rows="5" cols="50" id="details" name="details" title="敏感原因说明"></textarea>
		<span id="detailsInfo"></span></td>
		</tr>
	</table>
</form>
</div>
</div>
</div>
</div>
</body>
<#include "../common/common-js.ftl">
<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
form = $('#add');
//操作对话框
// 自定义按钮
api.button(
	{
		name: '确定',
		callback: function(){
			if(checkMediaId()){
				form.submit();
			}
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
function checkMediaId(){
	var flag = true; 
	var mediaId = $('#mediaId').val();
	if(mediaId=='' ||isNaN(mediaId)){
		alert("作品编号必须是数字");
		flag = false;
	}
	$.ajax({
		   type: "POST",
		   url: "/illegalmedia/checkid.go",
		   async: false,
		   cache: false,
		   data: $('#add').serialize(),
		   dataType:"json",
		   success: function(msg){
			   if(msg.result == 'notexist'){
				   alert("不存在的作品编号");
				   flag = false;
			   }
			   else if(typeof msg.result != 'undefined' && msg.result == 'failure'){
				   alert('作品已存在');
			  		flag =  false;
			   }else{
				   flag = true;
			   }
		   }
		});
	//不能在success里面return
	return flag;
	}
</script>
</html>