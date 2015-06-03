<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
<meta charset="UTF-8">
<title>新增栏目</title>
<#include "../common/common-css.ftl">
<#include "../common/common-js.ftl">
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
<script>
function change() {
     var pic = document.getElementById("preview");
     var file = document.getElementById("upfilename");
     var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
     // gif在IE浏览器暂时无法显示
     if(ext!='png'&&ext!='jpg'&&ext!='jpeg'){
         alert("文件必须为图片！"); return;
     }
     // IE浏览器
     if (document.all) {
 
         file.select();
         var reallocalpath = document.selection.createRange().text;
         var ie6 = /msie 6/i.test(navigator.userAgent);
         // IE6浏览器设置img的src为本地路径可以直接显示图片
         if (ie6) pic.src = reallocalpath;
         else {
             // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
             pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
             // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
             pic.witdh=20;
             pic.height=20;
             pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
         }
     }else{
         html5Reader(file);
     }
 }
 
 function html5Reader(file){
     var file = file.files[0];
     var reader = new FileReader();
     reader.readAsDataURL(file);
     reader.onload = function(e){
         var pic = document.getElementById("preview");
         pic.witdh=20;
         pic.height=20;
         pic.src=this.result;
     }
 }
</script>
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>栏目管理&gt;&gt;添加节点</h3>
					<form id="add" name="add" method="post" action="/column/save.go" enctype="multipart/form-data" target="_parent" onSubmit="return checkForm();">
						<#if RequestParameters.isCallBack??>
						<input type="hidden" id="isCallBack" name="isCallBack" value='${RequestParameters["isCallBack"]}'>
						</#if>
						<#if RequestParameters.codeId??>
						<input type="hidden" id="codeId" name="codeId" value='${RequestParameters["codeId"]}'>
						</#if>
						<#if RequestParameters.nameId??>
						<input type="hidden" id="nameId" name="nameId" value='${RequestParameters["nameId"]}'>
						</#if>
					<input type="hidden" id="parentId" name="parentId" value='${RequestParameters["parentId"]}'>
					<input type="hidden" id="path" name="path" value='<#if RequestParameters.path??>${RequestParameters["path"]}</#if>'>
					<input type="hidden" id="columnCode" name="columnCode" >
					<table class="table2" id="addtable" name="addtable">
						<tr>
							<td width="10%" class="tdright">栏目名称:</td>
							<td width="90%" class="tdleft"> <input type="text" id="name" name="name"><span id="nameInfo"></span></td>
						</tr>
						<tr>
							<td width="10%" class="tdright">栏目标识:</td>
							<td width="90%" class="tdleft"> <input type="text" id="code" name="code" onchange="checkCode()"><span id="codeInfo"></span></td>
						</tr>
						<tr>
						<td width="10%" class="tdright">男/女频:</td>
						<td  width="90%" class="tdleft" > 
								<input type="radio" id="channel" name="channel" value="${np}" onBlur="checkCode()" >男频
								<input type="radio" id="channel" name="channel" value="${vp}" onBlur="checkCode()" >女频
								<input type="radio" id="channel" name="channel" value="${all}" onBlur="checkCode()" checked="checked"  >全频
						</td>
						</tr>
					   <tr>
							<td width="10%" class="tdright">栏目图标：</td>
							<td width="90%" class="tdleft"> <input type="file" name="upfilename" id="upfilename"  class="ifile"  onchange="javascript:change();icon.value=this.value; ">
								<input type="text"  name="icon"   id="icon" size="20" readonly style="height: 25px;" />
								<span class="title" > 
								<img src="/images/column/upload.gif" width="80" height="25" align="absmiddle" onclick="upfilename.click();" style="z-index: 999;" /></span>
								<img id="preview" alt="" name="pic"  />
								<span id="txtfilenameInfo"></span></td>
							</td>
						</tr>
						<tr>
								<td width="10%" class="tdright">是否显示小喇叭:</td>
								<td width="90%" class="tdleft"> <input type="radio" id="isShowHorn" name="isShowHorn" value="1" checked>是<input type="radio" id="isShowHorn" name="isShowHorn" value="0" >否</td>
						</tr>
						<tr>
								<td width="10%" class="tdright">小喇叭内容:</td>
								<td width="90%" class="tdleft">
									<textarea id="tips" name="tips" ></textarea>
								</td>
						</tr>
						<tr>
								<td width="10%" class="tdright">栏目说明:</td>
								<td width="90%" class="tdleft">
									<textarea id="descs" name="descs" ></textarea>
								</td>
						</tr>
						<tr>
							<td colspan="2"><button type="submit">确认</button>&nbsp;&nbsp;&nbsp;<button type="reset">重置</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
</div>
</body>
<script type="text/javascript">	
function checkCode(){
	var code = $("#code").val();
	if(code==''){
		$('#codeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		return false;
	}
	var columnCode = $("input[name='channel']:checked").val()+"_"+code;
	$('#columnCode').val(columnCode);
	$.ajax({
		type:"POST",
		url:"/column/checkcode.go",
		data: "columnCode="+columnCode,
		dataType:"json",
		success: function(msg) {
			   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
				   $('#codeInfo').html('<font color="red">此编码已存在</font><img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
				   $('#code').attr("value","");
				   $('#code').focus();
				   return ;
			   }
		}
	});
}
	
function checkForm(){
	var flag = true;
	var columnName = $('#name').val();
	var columnCode = $('#code').val();
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	if(columnName == null || columnName == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#nameInfo').focus();
			flag =  false;
	}
	if(columnCode == ""){
			$('#codeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#codeInfo').focus();
			flag =  false;
	}
	return flag;
}
</script>

</html>