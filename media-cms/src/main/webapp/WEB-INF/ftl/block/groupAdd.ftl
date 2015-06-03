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
					<h3>块组管理&gt;&gt;添加组</h3>
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
					    <form action="/block/addGroup.go" method="post" target="_parent" id="add_form">
					    <input type="hidden" name="parentId" <#if parentId??>value="${parentId}"</#if>>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright">组名称：</td><td><input type="text" name="name" id="name" onblur="validInput('name')"></td><td style="width:1100px" id="nameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td>描述：</td><td><input type="text" name="descn" id="descn" onblur="validInput('descn')"></td><td style="width:1100px" id="descnInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td colspan="2" style="padding-left:150px">
					    				<image src="/images/save.jpg" onclick="javascript:subForm()" />
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
		
	   	function subForm(){
	   		var flag = true;
	   		var nameInfo = $('#name').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var descnInfo = $('#descn').val();
	   		
	   		if(flag){
	   			$('#add_form').submit();
	   		}
	   	}
	</script>
  </body>
</html>
