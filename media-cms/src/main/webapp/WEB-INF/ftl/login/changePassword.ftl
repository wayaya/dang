<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		var passwordRegex = /^((\d+[a-zA-Z]+[0-9a-zA-Z-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]*)|(\d+[-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]+[0-9a-zA-Z-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]*)|([a-zA-Z]+\d+[0-9a-zA-Z-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]*)|([a-zA-Z]+[-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]+[0-9a-zA-Z-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]*)|([-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]+\d+[0-9a-zA-Z-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]*)|([-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]+[a-zA-Z]+[0-9a-zA-Z-`=\\\[\];',./~!@#$%^&*()_+|{}:\"<>?]*))$/
 
			
		function validPassword(id){
			
			if($('#'+id).val()==null || $('#'+id).val().length==0 ){
				$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;"><span>请输入密码</span>');
				return false;
			}else if($('#'+id).val().length<8){
				$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;"><span>密码必须大于八位,请重新核对后再输入</span>');
				return false;
			}else if(!passwordRegex.test($('#'+id).val())){
				$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;"><span>您输入的密码不符合规范,请重新核对后再输入</span>');
				return false;
			}else{
				 $('#'+id+"Info").html('<img src="/images/right.jpg" style="width: 20px;"/>');
				return true;
			}
		}
		
		function validConfirm(confirmId, passwordId){
			
			if($('#'+confirmId).val()==null || $('#'+confirmId).val().length==0  || ($('#'+confirmId).val()!=$('#'+passwordId).val())){
				$('#'+confirmId+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;"><span>两次密码输入不一致，请重新核对后再输入</span>');
				return false;
			}else{
				$('#'+confirmId+"Info").html('<img src="/images/right.jpg" style="width: 20px;"/>');
				return true;
			}
		}
		
		function submitChangePassword(confirmId, passwordId){
			
			if(validPassword(passwordId) && validConfirm(confirmId, passwordId)){
				 $('#errorDiv').hide();
				 $('#errorSpan').html('');
				
				$.ajax({
					   type: "POST",
					   url: "${rc.contextPath}/usercms/resetOwnPassword.go",
					   async: false,
					   cache: false,
					   data: {
						   "usercmsId":$('#usercmsId').val(),
						   "newPassword": $('#newPassword').val(),
						   "timestamp":new Date().getTime()
					   },
					   dataType:"json",
					   success: function(msg){
						   
						   if( typeof msg.result != 'undefined' && msg.result == 'failure'){
							   
							   $('#errorDiv').show();
							   $('#errorSpan').html(msg.errorMessage);
						   }else{
							   
							   $.ajax({
								   type: "POST",
								   url: "${rc.contextPath}/login/logout.go",
								   async: false,
								   cache: false,
								   data:{},
								   dataType:'html',
								   success: function(msg){
									    alert("操作已成功,请点击按钮跳转到登陆页面重新登录");	
										window.location.href="${rc.contextPath}/login/index.go";
								   }
							   });
								
						   }
					   }
					});
			}
			
		}
		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>首次登录修改密码</h3>
					<div id="errorDiv" class="mrdiv" style="display:none" >
			      		 <table>
		        			<tr>
								<td style="padding-left:50px;"><span id="errorSpan"></span></td>
							</tr>
						</table>
				    </div>
				    <div>
					    	<#if usercmsForEdit?? && usercmsForEdit.usercmsId??>
					    		<input type="hidden" name="usercmsId" id="usercmsId" value="${ usercmsForEdit.usercmsId }" />
					    	</#if>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
						      	
						      	<#if usercmsForEdit??>
						      	
						      	<tr>
					    			<td class="tdright">新密码：</td>
					    			<td class="tdleft">
					    				<input type="password" id="newPassword" onblur="validPassword('newPassword')">
					    			</td>
					    			<td class="tdleft" style="width:200px" id="newPasswordInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">确认密码：</td>
					    			<td class="tdleft">
					    				<input type="password" id="newPasswordConfirm" onblur="validConfirm('newPasswordConfirm','newPassword')">
					    			</td>
					    			<td class="tdleft" style="width:200px" id="newPasswordConfirmInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">密码规则：</td>
					    			<td class="tdleft">
					    				规则：8位以上，必须包含数字、字母和符号中的任意两种， 大小写敏感，接受特殊字符（即：!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~）
					    			</td>
					    			<td class="tdleft" style="width:200px" ></td>
						      	</tr>
						      	</#if>
				            </table>
				         <div style="text-align:center">
		            			<button class="button1" onclick="javascript:submitChangePassword('newPasswordConfirm','newPassword')">提交</button>
		            	 </div>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
