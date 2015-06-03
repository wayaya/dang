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
					<h3>组管里&gt;&gt;修改组</h3>
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
					    <form target="_parent" action="/block/updateGroup.go" method="post" id="add_form">
					    <input type="hidden" name="mediaBlockGroupId" value="<#if group??><#if group.mediaBlockGroupId??>${group.mediaBlockGroupId?c}</#if></#if>">
					    <input type="hidden" name="parentId" value="<#if group??><#if group.parentId??>${group.parentId?c}</#if></#if>">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright">名称：</td><td><input type="text" name="name" id="name" value="<#if group??><#if group.name??>${group.name}</#if></#if>" onblur="validInput('name')"></td><td style="width:1100px" id="nameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td>描述：</td><td><input type="text" value="<#if group??><#if group.descn??>${group.descn}</#if></#if>" name="descn" id="descn"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td colspan="2" style="padding-left:150px">
					    				<image src="/images/save.jpg" onclick="javascript:addForm()" />
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
	   		
	   		if(flag){
	   			$('#add_form').submit();
	   		}
	   	}
	</script>
  </body>
</html>
