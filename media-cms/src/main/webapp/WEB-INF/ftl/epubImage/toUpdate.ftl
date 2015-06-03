<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>上传图片</h3>
					<div class="mrdiv">
					<table class="grid" valign="top">
					<form id="importFile" action="/epubImage/uploadImage.go" method="post" enctype="multipart/form-data">
						<tr align="center">
							<input type="hidden" name="mediaId" id="mediaId" value="${mediaId?c}">
							<td align="left">上传文件：</td>
						</tr>
						<tr>
							<td align="left"> <input type="file" id="fileName" name="fileUpload" class="finput required" style="width:150px;"/></td>
						</tr>
						<tr>
							<td align="left"><input type="button" class="btn" style="width: 90px" value="上传文件" id="import" /></td>
						</tr>
					</form>
					<td>
					</tr>
					</table>
		            </div> 
			    </div>
			    <div class="pagination rightPager"></div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
		<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<script type="text/javascript" src="/script/jquery/jquery.form.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	$('#import').click(function(){
		if ($("#fileName").val()=='') {
			alert("请选择文件！");
			return;
		}
		if (confirm("确认执行批量重新入库操作？")) {
					$("#importFile").submit();
				}
		});
	});
	</script>
	
  </body>
</html>
