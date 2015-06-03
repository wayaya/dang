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
		
	   	function activityTypeAddForm(){
	   		var flag = true;
	   		var nameInfo = $('#name').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var codeInfo = $('#code').val();
	   		if(codeInfo == null || codeInfo == ""){
	   			$('#codeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#codeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
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
					<h3>当心好文标签管理&gt;&gt;添加标签</h3>
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
					    <form action="/goodArticleSign/add.go" method="post" target="_parent" id="activity_type_add_form">
					    <input type="hidden" name="parentId" <#if parentId??>value="${parentId?c}"</#if>>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright">名称：</td><td><input type="text" name="name" id="name" onblur="validInput('name')"></td><td style="width:1100px" id="nameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td>编号：</td><td><input type="text" name="code" id="code" onblur="validInput('code')"></td><td style="width:1100px" id="codeInfo"></td>
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
