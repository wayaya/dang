<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>添加块</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right" style="width:99%;">
				<div class="m-r">
					<h3>块管里&gt;&gt;添加块</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
									</td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div>
					    <form action="/block/add.go" method="post" id="add_form">
					    	<input type="hidden" name="groupId" value="<#if block??><#if block.groupId??>${block.groupId?c}</#if></#if>">
						    <input type="hidden" name="type" value="0">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules="none">
					     	    <tr>
					    			<td class="tdright">块名称：</td><td><input type="text" name="name" id="name" value="<#if block??><#if block.name??>${block.name}</#if></#if>" onblur="validInput('name')"></td><td style="width:1100px;text-align:left" id="nameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">标识：</td><td><input type="text" value="" name="code" id="code"  onblur="checkCode(this.value)"></td><td style="width:1100px;text-align:left" id="codeInfo"></td>
						      	</tr>
								<tr>
					    			<td class="tdright">内容：</td>
					    			<td colspan=2><textarea <#if block??><#if block.content??>${block.content}</#if></#if> name="content" id="content" style="width: 709px; height: 193px; float: left;"  onblur="validInput('content')"></textarea></td>
						      	</tr>
						      	<tr>
					    			<td colspan="2" style="padding-left:150px">
					    				<input type="button" value="提交" onclick="javascript:addForm()" /></input>
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
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	   	function addForm(){
	   		var flag = true;
	   		var nameInfo = $('#name').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		if($("#codeError").text()=="该code已被使用。"){
	   		 	flag = false;
	   		}
	   		
	   		if(flag){
	   			$('#add_form').submit();
	   		}
	   	}
	   	
	   	//验证code是否存在
	   	function checkCode(code){
			if(code.length<=0) {
				$("#codeInfo").html('<img src="/images/wrong.jpg" style="width: 20px;"/>');
				return;
			}
			
			$.ajax({
				   type: "POST",url: "${rc.contextPath}/block/checkCode.go",
				   async: false,
				   cache: false,
				   data: {"code":code},
				   dataType:"json",
				   success: function(msg){
					   if(msg.result == 'success'){
						 $("#codeInfo").html('<img src="/images/right.jpg" style="width: 20px;"/>');
						 return;
					   }else{
						   $("#codeInfo").html('<img src="/images/wrong.jpg" style="width: 20px;"/><span id="codeError">该code已被使用。</span>');
						   return;
					   }
				   }
				});
		}
	   	
	   	
	</script>
  </body>
</html>
