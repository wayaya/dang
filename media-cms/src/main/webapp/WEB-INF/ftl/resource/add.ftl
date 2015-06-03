<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		function validName(){
			var nameInfo = $('#name').val();
	   		var nameReg = /^[0-9A-Za-z_]{1,30}$/;
	   		
	   		if(nameInfo == null || nameInfo == "" || !nameReg.test(nameInfo)){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
		}
		
		
	   	function activityTypeAddForm(){
	   		var flag = true;
	   		var nameInfo = $('#name').val();
	   		var nameReg = /^[0-9A-Za-z_]{1,30}$/;
	   		
	   		if(nameInfo == null || nameInfo == "" || !nameReg.test(nameInfo)){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	   	function resetForm(){
	   		$('#name').val('');
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right" style="width:99%;">
				<div class="m-r">
					<h3>目录管理&gt;&gt;添加目录</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
										<#if successFlag??>
					    					<#if successFlag == 0>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/activity/type/add.go?lefttab=ul2" style="height: 20px; font-size: 20px;">继续添加</a></#if>
					    				</#if>
									</td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div>
					    <form action="/mediaResource/add.go" method="post" target="_parent" id="activity_type_add_form" enctype="multipart/form-data">
					    <input type="hidden" name="parentId" <#if parentId??>value="${parentId}"</#if>>
					    <input type="hidden" name="isCdn" <#if isCdn??>value="${isCdn}"</#if>>
					    	<#if msg??><div><font color="red">${msg}</font></div></#if>
					    	
						     <table class="table3" style="width:700px;" border="0" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width:15%"><font color="red">*</font>名称：</td>
					    			<td class="tdleft"  style="width:50%"><input type="text" onblur="javascript:validName()" name="name" id="name" <#if cate??><#if cate.name??>value="${cate.name}"</#if></#if> >&nbsp;&nbsp;只能输入数字、大小写字母</td>
					    			<td class="tdleft" id="nameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td colspan="2" style="text-align:center">
					    				<image src="/images/save.jpg"  onclick="javascript:activityTypeAddForm()" />&nbsp;&nbsp;
					    				<image src="/images/reset.jpg" onclick="javascript:resetForm();" />
					    			</td>
					    			<td>&nbsp;</td>
						      	</tr>
				            </table>
			            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
</html>
