<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
<meta charset="UTF-8">
<title>删除敏感作品提示窗</title>
<#include "../common/common-css.ftl">
</head>
<body>
 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>删除敏感作品</h3>
<form id="del" name="del" method="post" action="/illegalmedia/delete.go" target="_parent">
	<input type="hidden" name="saleId" value="${illMedia.media.saleId?c}" />
	<input type="hidden" name="mediaId" value="${illMedia.mediaId?c}" />
	<input type="hidden" name="illegalMediaId" value="${illMedia.illegalMediaId?c}" />
	<table class="table2" width="70%">
		<tr>
			<td>
			<input type="radio" id="shelfStatus" name="shelfStatus" value="0" checked="checked">作品资源和销售主体都不操作
			</td>
			</tr>
			<tr>
			<td>
			<input type="radio" id="shelfStatus" name="shelfStatus" value="1"  title="需要发消息">作品资源和销售主体都上架
			</td>
			</tr>
			<tr>
			<td>
			<input type="radio" id="shelfStatus" name="shelfStatus" value="2" >作品资源上架,销售主体不操作
			</td>
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
$(document).ready(function(){
	var api = frameElement.api;
	var form = $('#del');
	api.button(
			{
				name: '删除',
				callback: function(){
					form.submit();
					return false;
				},
				focus: true
			},
			{
				name: '取消'
			})
});
	
</script>
</html>