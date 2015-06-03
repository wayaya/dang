<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
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
	   		var nameInfo = $('#name').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var pseudonymInfo = $('#pseudonym').val();
	   		if(pseudonymInfo == null || pseudonymInfo == ""){
	   			$('#pseudonymInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#pseudonymInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var birthInfo = $('#births').val();
	   		if(birthInfo == null || birthInfo == ""){
	   			$('#birthInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#birthInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		
	   		var signInfo = $('#sign').val();
	   		
	   		if(signInfo == null || $.trim(signInfo).length == 0){
	   			$('#signInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#signInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var introductionInfo = $('#introduction').val();
	   		
	   		if(introductionInfo == null || $.trim(introductionInfo).length == 0){
	   			$('#introductionInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#introductionInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var file = $('#headPic').val();
	   		
	   		if(file!=null && file.length > 0){
	   			var re = /JPG$/;  
	   			if(!re.test(file.toUpperCase())){
	   				flag = false;
	   				$('#headPicInfo').html('请上传JPG头像文件.<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			}else{
	   				$('#headPicInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   			}
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
					<h3>作者管理&gt;&gt;作者信息修改</h3>
				    <div>
					    <form action="/author/update.go" method="post" id="activity_type_add_form"  enctype="multipart/form-data">
					    <input type="hidden" name="authorId" value="<#if author??><#if author.authorId??>${author.authorId?c}</#if></#if>">
						     <table class="table3" style="width:600px;" border="0" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width:100px;"><font color="red">*</font>姓名：</td>
					    			<td class="tdleft"  style="width:100px;"><input type="text" name="name" id="name" value="<#if author??><#if author.name??>${author.name}</#if></#if>" onblur="validInput('name')"></td>
					    			<td style="width:100px" id="nameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td style="width:100px;"><font color="red">*</font>作家笔名：</td>
					    			<td class="tdleft" style="width:100px;"><input type="text" value="<#if author??><#if author.pseudonym??>${author.pseudonym}</#if></#if>" name="pseudonym" id="pseudonym" onblur="validInput('pseudonym')"></td>
					    			<td style="width:100px" id="pseudonymInfo"></td>
						      	</tr>
						      	<tr>
					    			<td style="width:100px;"><font color="red">*</font>标签：</td>
					    			<td class="tdleft" style="width:100px;"><input type="text" value="<#if author??><#if author.sign??>${author.sign}</#if></#if>" name="sign" id="sign" onblur="validInput('sign')"></td>
					    			<td style="width:100px" id="signInfo"></td>
						      	</tr>
						      	<tr>
					    			<td style="width:100px;">CP：</td>
					    			<td class="tdleft" style="width:100px;"><input type="text" readOnly="readOnly"  value="<#if author??><#if author.cpType??>${author.cpType}</#if></#if>" name="cpType" id="cpType" onblur="validInput('cpType')"></td>
					    			<td style="width:100px" id="cpTypeInfo"></td>
						      	</tr>
						      	<tr>
					    			<td style="width:100px;">性别：</td>
					    			<td class="tdleft" style="width:100px;">
					    			<select name="sex" id="sex" style="width:50%">
								 			<option value="0" <#if author??><#if author.sex??><#if (author.sex == 0)>selected = "selected" </#if></#if></#if>>男</option>
								 			<option value="1"  <#if author??><#if author.sex??><#if (author.sex == 1)>selected = "selected" </#if></#if></#if>>女</option>
								 		</select>
					    			</td>
					    			<td style="width:100px" id="sexInfo">
					    			</td>
						      	</tr>
						      	<tr>
						      	<td style="width:100px;"><font color="red">*</font>头像：</td>
						      	<td class="tdleft" style="width:100px;"><input type="file" name="cover" id="headPic">
					    			<#if author??><#if author.headPic??>
					    				<img width="140" height="200" src="${picPath}/${author.headPic}"/>
					    			</#if></#if>&nbsp;&nbsp;<span id="headPicInfo"></span>
					    			</td><td style="width:100px" ></td>
						      	</tr>
						      	<tr>
					    			<td style="width:100px;"><font color="red">*</font>出生年月：</td>
					    			<td class="tdleft" style="width:100px;">
					    			<input type="text" class="Wdate" value="<#if author.birth??>${author.birth?string("yyyy-MM-dd")}</#if>" name="births" id="births"
					    			 onblur="validInput('birth')"  readonly="readonly"
					    			   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					    			</td>
					    			<td style="width:100px" id="birthInfo"></td>
						      	</tr>
						      	<tr>
					    			<td style="width:100px;"><font color="red">*</font>作家描述：</td>
					    			<td class="tdleft" style="width:100px;">
					    			<textarea cols=40 rows=10 name="introduction" id="introduction" onblur="validInput('introduction')"><#if author??><#if author.introduction??>${author.introduction}</#if></#if></textarea>
					    			</td><td style="width:100px" id="introductionInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td colspan="2" style="padding-left:150px">
					    				<image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" />
					    			</td>
					    			<td style="width:100px"></td>
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
