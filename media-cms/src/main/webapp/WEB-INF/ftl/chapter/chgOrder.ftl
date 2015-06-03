<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>栏目列表</title>
	<#include "../common/common-css.ftl">
	<script>
	function thisMovie(movieName) { 
		if (navigator.appName.indexOf("Microsoft") != -1) { 
			return window[movieName] 
		} 
		else { 
			return document[movieName] 
		} 
	} 
		function setUp(){
			return $('#order').val();
		}
	</script>
</head>   
  <body>
  <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
  <TABLE border=0 height=160px align=left>
	<TR height="150px">
		<TD width="100%" align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:150 px; heigth:140px;">
				<br><br><br><br>
				将本章移动到第<input type="text" id="order" name="order" value="<#if chapter??><#if chapter.index??>${chapter.index}</#if></#if>">章之前
			</ul>
		</TD>
	</TR>
</TABLE>
</div>
</div>
</div>
</div>
<#include "../common/common-js.ftl">
</body>
	</script>
	<script type="text/javascript" src="${rc.contextPath}/script/jquery/jquery-1.7.js"></script>
</html>
