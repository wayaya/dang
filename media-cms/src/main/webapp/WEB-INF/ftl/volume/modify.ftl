<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	   	function activityTypeAddForm(){
	   		var flag = true;
	   		var titleInfo = $('#title').val();
	   		if(titleInfo == null || titleInfo == ""){
	   			$('#titleInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#titleInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   	
	   		
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>作品管理&gt;&gt;分卷修改</h3>
				    <div>
					    <form action="/volume/update.go" method="post" id="activity_type_add_form">
					    <input type="hidden" name="volumeId" value="<#if volume??><#if volume.volumeId??>${volume.volumeId?c}</#if></#if>">
					    <input type="hidden" name="mediaId" value="<#if volume??><#if volume.mediaId??>${volume.mediaId?c}</#if></#if>">
						     <table class="table3" style="width:400px;" border="0" bordercolor="#a0c6e5" rules=none>
			    				<tr style="width:200px;">
					    			<td class="tdright"><font color="red">*</font>标题：</td><td><input type="text" name="title" id="title" value="<#if volume??><#if volume.title??>${volume.title}</#if></#if>" onblur="validInput('title')"></td><td style="width:100px;" id="titleInfo"></td>
						      	</tr>
						      	<tr style="width:200px;">
					    			<td>描述：</td><td><input type="text" value="<#if volume??><#if volume.descs??>${volume.descs}</#if></#if>" name="descs" id="descs"></td><td style="width:100px;" id="descsInfo"></td>
						      	</tr>
						      	<tr style="width:200px;">
					    			<td><font color="red">*</font>序号：</td><td><input readOnly="readOnly" type="text" value="<#if volume??><#if volume.volumeOrder??>${volume.volumeOrder?c}</#if></#if>" name="volumeOrder" id="volumeOrder"  onblur="validInput('volumeOrder')"></td><td style="width:100px;" id="volumeOrderInfo"></td>
						      	</tr>
						      	<tr>
					    			<td colspan="2" style="padding-left:150px">
					    				<image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" />
					    			</td>
					    			<td style="width:1100px"></td>
						      	</tr>
				            </table>
			            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
