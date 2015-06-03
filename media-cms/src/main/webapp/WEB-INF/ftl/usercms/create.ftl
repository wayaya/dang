<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<#include "../common/common-js.ftl">
	<script type="text/javascript"> 
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	   	function activityTypeAddForm(){
	   		var flag = true;
	   		
	   		
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	   	
	   	function validateLoginName(){
	   		var id = 'loginName';
	   		var reg = /^[0-9a-zA-Z\u4E00-\u9FA5]{3,}$/
	   		 if(!reg.test($('#'+id).val())){
    	    	$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;">');
    	      	return false;
    	      }
   	   	   $('#'+id+"Info").html('<img src="/images/right.jpg" style="width: 20px;"/>');
   	       return true;
	   	}
	   	
	   	function validateName(){
	   	  var id = 'name';
   	      var reg = /^[\u4E00-\u9FA5]{2,4}$/
   	      if(!reg.test($('#'+id).val())){
   	    	$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;">');
   	      	return false;
   	      }
   	   	  $('#'+id+"Info").html('<img src="/images/right.jpg" style="width: 20px;"/>');
   	      return true;
	   	}
	   	
	   	function validConfirm(confirmId, pwdId){
	   		
	   		validInput(confirmId);
	   		if($('#'+confirmId).val() != $('#'+pwdId).val()){
	   			$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;"><span>两次密码输入不一致</span>');
	   		}
	   	}
	   	
	    function checkEmail(id)
	    {
	     
	     if (!isEmail($('#'+id).val()))
	     {
	    	$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;"><span>您输入的邮箱有误,请重新核对后再输入</span>');
	    	return false;
	     }else{
	    	 $('#'+id+"Info").html('<img src="/images/right.jpg" style="width: 20px;"/>');
	    	 return true;
	     }
	    }
	    
	    function isEmail(str){
	        var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
	        return reg.test(str);
        }
	   	
	   	function changePreviledge(ckbox){
	   		
	   		if(ckbox.is(':checked')==true){
	   			$('#previledge').val("0");
	   			$('#grandRoles').hide();
	   		}else{
	   			$('#previledge').val("1");
	   			$('#grandRoles').show();
	   		}
	   	} 
	   	
	   	function changeRoleIds(){
	   		
	   		var roleIdsValue = '';
	   		$('#grandRoles input[type="checkbox"]').each(function(){
	   			
	   			if( $(this).is(':checked') ){
					
	   				var roleId = $(this).attr('id').substring(5);
	   				roleIdsValue+=(roleId+',')
	   			}
	   		});
	   		if(roleIdsValue.length>0){
	   			roleIdsValue=roleIdsValue.substring(0, roleIdsValue.length-1);
	   		}
	   		$('#roleIds').val(roleIdsValue);
	   	}
	   	
	   	function submitValidation(){
	   		
	   		if(<#if !usercmsForEdit?? || !usercmsForEdit.usercmsId??> !validateLoginName() ||</#if> !validateName() || !checkEmail('email') || !validInput('department') ){
	   			return;
	   		}else{
	   			$('#usercms_add_form').submit();
	   		}
	   	}
	   	
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>后台用户管理&gt;&gt;<#if usercmsForEdit?? && usercmsForEdit.usercmsId??>编辑<#else>添加</#if>后台用户</h3>
					<div class="mrdiv" <#if !errorMessage??>style="display:none"</#if> >
			      		 <table>
		        			<tr>
								<td style="padding-left:50px;"><span id="message">
								<#if errorMessage??>${errorMessage}</#if>
								</span></td>
							</tr>
						</table>
				    </div>
				    <div>
					    <form action="/usercms/<#if usercmsForEdit?? && usercmsForEdit.usercmsId??>editSave.go<#else>addSave.go</#if>?lefttab=ul2" method="post" id="usercms_add_form" >
					    	<#if usercmsForEdit?? && usercmsForEdit.usercmsId??>
					    		<input type="hidden" name="usercmsId" value="${ usercmsForEdit.usercmsId }" />
					    	</#if>
					    
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
						     	<!-- 不允许修改用户名 -->
						     	<#if !usercmsForEdit?? || !usercmsForEdit.usercmsId??>
				    				<tr>
						    			<td class="tdright">用户ID：</td>
						    			<td class="tdleft">
						    				<input type="text" name="loginName" id="loginName" value="<#if usercmsForEdit?? && usercmsForEdit.loginName?? >${usercmsForEdit.loginName}</#if>" 
						    				onblur="validateLoginName()"> (汉字、字母、数字)
						    			</td>
						    			<td class="tdleft" style="width:1100px" id="loginNameInfo"></td>
							      	</tr>
						      	</#if>
						      	
						      	<#if !usercmsForEdit?? || !usercmsForEdit.usercmsId??>
						      	<tr>
					    			<td class="tdright">密码：</td>
					    			<td class="tdleft">
					    				<span> 新建用户默认密码  dang@dang </span>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="passwordInfo"></td>
						      	</tr>
						      	<!-- <tr>
					    			<td class="tdright">确认密码：</td>
					    			<td class="tdleft">
					    				<input type="password" id="passwordConfirm" value="" onblur="validConfirm('passwordConfirm','password')">
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="passwordInfo"></td>
						      	</tr> -->
						      	</#if>
						      	
						      	<tr>
					    			<td class="tdright">真实姓名：</td>
					    			<td class="tdleft">
					    				<input type="text" name="name" id="name" value="<#if usercmsForEdit?? && usercmsForEdit.name??>${usercmsForEdit.name}</#if>" 
					    				onblur="validateName()">(汉字2-4个)
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="nameInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td class="tdright">Email：</td>
					    			<td class="tdleft">
					    				<input type="text" name="email" id="email" value="<#if usercmsForEdit?? && usercmsForEdit.email??>${usercmsForEdit.email}</#if>" 
					    				onblur="checkEmail('email')">
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="emailInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td class="tdright">部门：</td>
					    			<td class="tdleft">
					    				<select name="department" id="department" onblur="validInput('department')">
					    					<option value="">请选择...</option>
					    					<option value="数字阅读业务部" <#if usercmsForEdit?? && usercmsForEdit.department?? &&usercmsForEdit.department=='数字阅读业务部' > selected </#if> >数字阅读业务部</option>
					    					<option value="数字阅读开发部" <#if usercmsForEdit?? && usercmsForEdit.department?? &&usercmsForEdit.department=='数字阅读开发部' > selected </#if> >数字阅读开发部</option>
					    				</select>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="departmentInfo"></td>
						      	</tr>
						      	<#if userSessionInfo?? && userSessionInfo.userInfo?? && userSessionInfo.userInfo.previledge?? && userSessionInfo.userInfo.previledge==0>
						      	<tr>
					    			<td class="tdright">是否管理员：</td>
					    			<td class="tdleft">
					    				<input type="checkbox" onClick="changePreviledge($(this))" <#if usercmsForEdit?? ><#if usercmsForEdit.previledge??><#if usercmsForEdit.previledge==0>checked</#if></#if></#if>  >
					    				<input type="hidden" id="previledge" name="previledge" value="<#if usercmsForEdit??><#if usercmsForEdit.previledge??>${ usercmsForEdit.previledge }</#if></#if><#if !usercmsForEdit??||!usercmsForEdit.previledge?? >1</#if>" />
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="previledgeInfo"></td>
						      	</tr>
						      	</#if>
						      	<#if (userSessionInfo?? && userSessionInfo.userInfo?? && userSessionInfo.userInfo.previledge?? && userSessionInfo.userInfo.previledge==1)|| !userSessionInfo?? >
						      		<input type="hidden" id="previledge" name="previledge" value="1" />
						      	</#if>
						      	
						      	<tr>
								  <td colspan=2>
								    <hr>
								  </td>
								</tr>
						      	
						      	<tr id="grandRoles" <#if usercmsForEdit?? && usercmsForEdit.previledge?? && usercmsForEdit.previledge==0>style="display:none"</#if> >
					    			<td class="tdright">角色：</td>
					    			<td class="tdleft">
					    				<table>
						    				<#assign i = 0>
						               	 	<#if roles??>
								    			<#list roles as roleInList>
								    			<tr>
								    				<td class="tdleft" style="height:25px" >
								    				<span>
								    					<input id="role_${roleInList.roleId?c}" type="checkbox" name="roleIds" value="${roleInList.roleId}" 
								    					 <#if existedRoles??&&existedRoles?size gt 0>
														          <#list existedRoles as existedRoleId>
																		<#if existedRoleId==roleInList.roleId>
																			checked
																			<#break>
																		</#if>									          	
														          </#list>
														 </#if>
								    					>
								    					<label for="role_${roleInList.roleId?c}">${roleInList.name}</label>
								    				</span>
									    			</td>
						    					</tr>
								    			</#list>
								    		</#if>
							    		</table>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="previledgeInfo"></td>
						      	</tr>
						      	
				            </table>
			            </form>
			            <#if (usercmsForEdit?? &&usercmsForEdit.usercmsId?? && userSessionInfo?? && userSessionInfo.f['32']??) || ((!usercmsForEdit??||!usercmsForEdit.usercmsId??) && userSessionInfo?? && userSessionInfo.f['31']??) >
		            	<button class="button1" onclick="submitValidation()">提交</button>
		            	</#if>
		            	<button class="button1" onclick="window.location.href='${rc.contextPath}/usercms/list.go'">返回</button>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
  </body>
</html>
