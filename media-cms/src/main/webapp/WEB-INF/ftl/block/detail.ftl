<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>块详情页</title>
	<#include "../common/common-css.ftl">
</head>   
<body>
	<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right" style="width:99%;">
				<div class="m-r">
					<h3>推荐管理&gt;&gt;<#if showPageType??><#if showPageType == 1>查看详情</#if></#if><#if showPageType??><#if showPageType == 2>修改</#if></#if></h3>
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
				 		<form  action="/block/update.go" method="post" id="add_form" onSubmit="return checkForm();">
							<table class="table3">
							   <input type="hidden" name="groupId" id="groupId" value="<#if one??>${one.groupId?c}</#if>">
					           <tr>
					           		<td class="tdright" width="80px">块id：</td><td><input id="mediaBlockId" name="mediaBlockId" readonly="readonly" value="<#if one.mediaBlockId??>${one.mediaBlockId?c}</#if>"/></td><td style="width:1100px;text-align:left" id="mediaBlockIdInfo"></td>
					           </tr>
					           <tr> 
					           		<td class="tdright">块名称：</td><td><input id="name" name="name" value="<#if one.name??>${one.name}</#if>" onblur="validInput('name')"/></td><td style="width:1100px;text-align:left" id="nameInfo"></td>
					           </tr>  
					           <tr>
					           		<td class="tdright">标识：</td><td><input name="code"  readonly="readonly" value="<#if one.code??>${one.code}</#if>"/></td><td style="width:1100px;text-align:left" id="codeInfo"></td>
					           </tr> 
					           <tr>
					           		<td class="tdright">创建人：</td><td><input name="creator" readonly="readonly" value="<#if one.creator??>${one.creator}</#if>"/></td><td style="width:1100px;text-align:left" id="creatorInfo"></td>
					           </tr> 
					           <tr>
					           		<td class="tdright">创建时间：</td><td><input name="creationDate"   readonly="readonly"  value="<#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if>"/></td><td style="width:1100px;text-align:left" id="creationDateInfo"></td>
					           </tr> 
					           <tr>
					           		<td class="tdright">修改人：</td><td><input  name="modifier"  readonly="readonly" input id="modifier" value="<#if one.modifier??>${one.modifier}</#if>"/></td><td style="width:1100px;text-align:left" id="modifierInfo"></td>
					           </tr> 
					           <tr>
					           		<td class="tdright">修改时间：</td><td><input id="lastModifiedDateInput" name="lastModifiedDate"  readonly="readonly" value="<#if one.lastModifiedDate??>${one.lastModifiedDate?string("yyyy-MM-dd HH:mm:ss")}</#if>"/></td><td style="width:1100px;text-align:left" id="lastModifiedDateInfo"></td>
					           </tr> 
					           <tr>
					           		<td class="tdright">内容：</td>
					    			<td colspan=2><textarea <#if block??><#if block.content??>${block.content}</#if></#if> name="content" id="content" style="width: 709px; height: 193px; float: left;"  onblur="validInput('content')"><#if one.content??>${one.content}</#if></textarea></td>
					           </tr> 
					           <tr>
					       			<td colspan="3" style="padding-left:150px; text-align: left;">
										&nbsp;
									</td>
								</tr>	
					           <tr>
					       			<td colspan="3" style="padding-left:150px; text-align: left;">
										<input id="subButton" type="submit" style="width:80px;height:30px;" value="更改" /></input>
									</td>
								</tr>	
					 		</table>
				 		</form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	
      <script type="text/javascript">
		$(function(){
		<#if showPageType??>
			if(${showPageType}==1){
				$("#name").attr("readOnly","true");
				$("#content").attr("readOnly","true");
				$("#subButton").hide();
				$("#nameInfo").hide();
			}else {
				$("#lastModifiedDateInput").attr("name","");
			}
		</#if>	
		});
		
		function checkForm(){
	   		var flag = true;
	   		var nameInfo = $('#name').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		return flag;
	   	}
	</script>
  </body>
   <#include "../common/common-js.ftl">
</html>
