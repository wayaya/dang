<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
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
		function setUp(signIds,signNames){
			signIds.value = thisMovie('myMovieName').getSelectId();
			signNames.value = thisMovie('myMovieName').getSelectName();
		}
	</script>
</head>   
  <body>
  <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
  <TABLE border=0 height=300px align=left>
	<TR height="200px">
		<TD width="100%" align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:300 px; heigth:180px;">
				<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
					codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0"
					WIDTH="300px" HEIGHT="180px" id="myMovieName">
					<PARAM NAME=quality VALUE=high>
					<PARAM NAME=bgcolor VALUE=#FFFFFF>
					<EMBED src="/script/treeTest.swf?dataURL=/epub/getLabel.go?chk=${id}&idValue=${id}&nameValue=${name}&allowSelectParent=false&singleSelect=false" quality=high bgcolor=#FFFFFF WIDTH="450px" HEIGHT="400px"
					NAME="myMovieName" ALIGN="" TYPE="application/x-shockwave-flash"
					PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">
					</EMBED>
				</OBJECT>
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
