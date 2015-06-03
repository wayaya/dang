<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		//校验数字
	   	function validNumber(id){
	   		var value = $('#'+id).val();
	   		if (value.search("^-?\\d+$") != 0) {
	   			$("#"+id+"Info").html('<img src="/images/wrong.jpg"/ style="width: 20px;"><span>请输入数字!</span>');
	   		}else{
	   			$("#"+id+"Info").html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   	}
		
		function addForm(){
	   		var flag = true;
	   		var username = $('#username').val();
	   		if(username == null || username == ""){
	   			$('#usernameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#usernameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var custId = $('#custId').val();
	   		if(custId == null || custId == ""){
	   			$('#custIdInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#custIdInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var mediaId = $('#mediaId').val();
	   		if(mediaId == null || mediaId == ""){
	   			$('#mediaIdInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#mediaIdInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var mediaName = $('#mediaName').val();
	   		if(mediaName == null || mediaName == ""){
	   			$('#mediaNameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#mediaNameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var mediaUrl = $('#mediaUrl').val();
	   		if(mediaUrl == null || mediaUrl == ""){
	   			$('#mediaUrlInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#mediaUrlInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var showCons = $('#showCons').val();
	   		if(showCons == null || showCons == ""|| showCons.length>10){
	   			$('#showConsInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#showConsInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		
	   		var type = $('#type').val();
	   		if(type == null || type == ""){
	   			$('#typeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
   				if(type!=1&&type!=2&&type!=3&&type!=4){
   					$("#typeInfo").html('<img src="/images/wrong.jpg" style="width: 20px;">');
   					return;
   				}
	   			$('#typeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		
	   		
	   		if(flag){
	   			$('#rankForm').submit();
	   		}else{
	   			return;
	   		}
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>榜单管理&gt;&gt;添加榜单记录</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
										<#if successFlag??>
					    					<#if successFlag == 0>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/rankConsToBook/addPre.go?lefttab=ul7" style="height: 20px; font-size: 20px;">继续添加</a></#if>
					    				</#if>
									</td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div>
				    <form action="/rankConsToBook/add.go" id="rankForm" name="rankForm" method="post">
				      <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
				      	<#--><tr><td class="tdright">标识&nbsp;&nbsp;</td><td><input name="code" id="code" value="<#if code??>${one.code}</#if>" onblur="validInput('code')"></td><td class="tdleft" style="width:1100px" id="codeInfo"></td></td></tr>-->
		            	<tr><td class="tdright">用户ID&nbsp;&nbsp;</td><td><input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="custId" id="custId" value="<#if custId??>${one.custId?c}</#if>" onblur="validNumber('custId')"></td><td class="tdleft" style="width:1100px" id="custIdInfo"></td></td></tr>
		            	<tr><td class="tdright">用户昵称&nbsp;&nbsp;</td><td><input  name="username" id="username" value="<#if username??>${one.username}</#if>" onblur="validInput('username')"></td><td class="tdleft" style="width:1100px" id="usernameInfo"></td></td></tr>
		            	<tr><td class="tdright">用户头像&nbsp;&nbsp;</td><td><input name="userImgUrl" id="userImgUrl" value="<#if userImgUrl??>${one.userImgUrl}</#if>" onblur="validInput('userImgUrl')"></td><td class="tdleft" style="width:1100px" id="userImgUrlInfo"></td></td></tr>
		            	<tr><td class="tdright" colspan="3"><DIV style="BORDER-TOP: #00686b 1px dashed; OVERFLOW: hidden; width:20%;HEIGHT: 1px"></DIV></td></tr>
		            	<tr><td class="tdright">作品ID&nbsp;&nbsp;</td><td><input name="mediaId" id="mediaId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="<#if mediaId??>${one.mediaId?c}</#if>" onblur="validNumber('mediaId')"></td><td class="tdleft" style="width:1100px" id="mediaIdInfo"></td></td></tr>
		            	<tr><td class="tdright">销售主体的ID</td><td><input name="saleId" id="saleId" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="<#if saleId??>${one.saleId?c}</#if>" onblur="validNumber('saleId')"></td><td class="tdleft" style="width:1100px" id="saleIdInfo"></td></td></tr>
		            	<tr><td class="tdright">作品名&nbsp;&nbsp;</td><td><input name="mediaName" id="mediaName" value="<#if mediaName??>${one.mediaName}</#if>" onblur="validInput('mediaName')"></td><td class="tdleft" style="width:1100px" id="mediaNameInfo"></td></td></tr>
		            	<tr><td class="tdright">作品封面&nbsp;&nbsp;</td><td><input name="mediaUrl" id="mediaUrl" value="<#if mediaUrl??>${one.mediaUrl}</#if>" onblur="validInput('mediaUrl')"></td><td class="tdleft" style="width:1100px" id="mediaUrlInfo"></td></td></tr>
		            	<tr><td class="tdright" colspan="3"><DIV style="BORDER-TOP: #00686b 1px dashed; OVERFLOW: hidden; width:20%;HEIGHT: 1px"></DIV></td></tr>
		            	<tr><td class="tdright">显示金币&nbsp;&nbsp;</td><td><input name="showCons" id="showCons"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="<#if showCons??>${one.showCons}</#if>" onblur="validNumber('showCons')"></td><td class="tdleft" style="width:800px" id="showConsInfo"></td></td></tr>
		            	<tr><td class="tdright">榜单类型&nbsp;&nbsp; </td><td>
		            	   <#-- 
		            	     <input name="type" id="type" value="<#if type??>${one.type}</#if>" onblur="validInput('type')"></td>
		            	   -->
		            	      <select id="type" name="type">
			    					<option value="1">日</option>
			    					<option value="2">周</option>
			    					<option value="3">月</option>
			    					<option value="4">总</option>
							  </select>
							 </td> 
							<td></td></td></tr>  
	               	    </tr>
	               	    <tr><td class="tdright">频道类型&nbsp;&nbsp; </td><td>
		            	      <select id="channel" name="channel">
			    					<option value="NP">男生站</option>
			    					<option value="VP">女生站</option>
							  </select>
							 </td> 
							<td></td></td></tr>  
	               	    </tr>
		           
	               	    </tr>
	               	    <tr>
					      <td colspan="2" style="padding-left:150px">
					    	  <#if successFlag??>
					    		<#if successFlag == 1><image src="/images/save.jpg" onclick="javascript:addForm()" /></#if>
					    			<#else>
					    			<image src="/images/save.jpg" onclick="javascript:addForm()" />
					    			</#if>
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
	<#include "../../common/common-js.ftl">
  </body>
</html>
