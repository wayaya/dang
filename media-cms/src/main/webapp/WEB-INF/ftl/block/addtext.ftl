<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>添加块</title>
	<#include "../common/common-css.ftl">
	<style type="text/css">
		.ifile {
			position: absolute;
			opacity: 0;
			filter: alpha(opacity = 0);
			-moz-opacity: 0;
			margin-top: 4px;
			display:none;
		}
	</style>
	<script type="text/javascript">
	   	
	   	function change() {
		     var pic = document.getElementById("preview");
		     var file = document.getElementById("upfilename");
		     var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
		     // gif在IE浏览器暂时无法显示
		     if(ext!='png'&&ext!='jpg'&&ext!='jpeg'){
		         alert("文件必须为图片！"); return;
		     }
		     // IE浏览器
		     if (document.all) {
		         file.select();
		         var reallocalpath = document.selection.createRange().text;
		         var ie6 = /msie 6/i.test(navigator.userAgent);
		         // IE6浏览器设置img的src为本地路径可以直接显示图片
		         if (ie6) pic.src = reallocalpath;
		         else {
		             // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
		             pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
		             // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
		             pic.witdh=20;
		             pic.height=20;
		             pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
		         }
		     }else{
		         html5Reader(file);
		     }
		 }
				 
		 function html5Reader(file){
		     var file = file.files[0];
		     var reader = new FileReader();
		     reader.readAsDataURL(file);
		     reader.onload = function(e){
		         var pic = document.getElementById("preview");
		         pic.width=500;
		         pic.height=80;
		         pic.src=this.result;
		     }
		 }
   </script>
   <style type="text/css">
		.dispalynone {
			display: none;
		}
	</style>
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
					    <form action="/block/savetext.go" method="post" id="add_form" enctype="multipart/form-data">
					    	<input type="hidden" name="groupId" value="<#if block??><#if block.groupId??>${block.groupId?c}</#if></#if>">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules="none">
					     	    <tr>
					    			<td class="tdright">块名称：</td><td><input type="text" name="name" id="name" value="<#if block??><#if block.name??>${block.name}</#if></#if>" onblur="validInput('name')"></td><td style="width:1100px;text-align:left" id="nameInfo"  name="messageInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">标识：</td><td><input type="text" value="" name="code" id="code"  onblur="checkCode(this.value)"></td><td style="width:1100px;text-align:left" id="codeInfo" name="messageInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">类型：</td>
					    			<td>
					    				<select id="type" name="type" onblur="validInput('type')">
					    					<option value="0" <#if block??><#if block.type??><#if block.type==0>selected="selected"</#if></#if></#if>>JSON格式</option>
					    					<option value="1" <#if block??><#if block.type??><#if block.type==1>selected="selected"</#if></#if></#if>>纯文本格式</option>
					    				</select>
					    			</td>
					    			<td style="width:1100px;text-align:left" id="typeInfo" name="messageInfo"></td>
						      	</tr>
						      	<tr id="zhuanti_3">
					    			<td class="tdright">目标URL：</td>
					    			<td>
					    				<input type="text" name="targetUrl" id="targetUrl" value="<#if block??><#if block.targetUrl??>${block.targetUrl}</#if></#if>">
					    			</td>
					    			<td style="width:1100px;text-align:left" id="targetUrlInfo" name="messageInfo"></td>
						      	</tr>
						      	<tr id="zhuanti_4">
					    			<td class="tdright">主图上传：</td>
					    			<td colspan="2" style="text-align:left">
					    				<input type="file" title="选择文件" name="upfilename" id="upfilename" class="ifile" onchange="javascript:change();icon.value=this.value; "/>
					    				<input type="text"  name="icon"   id="icon" size="20" readonly style="height: 25px;" />
					    				<img src="/images/column/upload.gif" width="80" height="25" align="absmiddle" onclick="upfilename.click();" style="z-index: 999;" /></span>
					    			</td>
						      	</tr>
						      	<tr id="zhuanti_5" style="height: 80px;">
					    			<td class="tdright">主图展示：</td>
					    			<td colspan="2" style="text-align:left">
					    				<img id="preview" alt="" name="pic"  />
					    			</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">关联栏目：</td>
					    			<td>
					    				<select id="relationColumnCode" name="relationColumnCode">
					    					<option value="">不关联</option>
					    					<#if columnListOption??>${columnListOption}</#if>
					    				</select>
					    			</td>
					    			<td style="width:1100px;text-align:left" id="relationColumnCodeInfo" name="messageInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">状态：</td>
					    			<td colspan=2 style="text-align: left;">
										<select id="status" name="status">
					    					<option value="1" <#if block??><#if block.status??><#if block.status==1>selected="selected"</#if></#if></#if>>有效</option>
					    					<option value="0" <#if block??><#if block.status??><#if block.status==0>selected="selected"</#if></#if></#if>>无效</option>
					    				</select>
									</td>
						      	</tr>
								<tr>
					    			<td class="tdright">内容：</td>
					    			<td colspan=2><textarea name="content" id="content" style="width: 709px; height: 193px; float: left;"  onblur="validInput('content')"><#if block??><#if block.content??>${block.content}</#if></#if> </textarea></td>
						      	</tr>
						      	
						      	<#if returnInfo??>
							    <#else>
							    	<tr>
						    			<td colspan="2" style="padding-left:150px">
						    				<input type="button" value="提交" onclick="javascript:addForm()" /></input>
						    			</td>
						    			<td style="width:1100px"></td>
							      	</tr>
							    </#if>
						      	
				            </table>
			            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
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
	   		var code =$('#code').val()
	   		if(code == ""){
	   			$('#codeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#codeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
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
				$("#codeInfo").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;"/>');
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
						 	$("#codeInfo").html('&nbsp;&nbsp;<img src="/images/right.jpg" style="width: 20px;"/>');
						 	return;
					   }else{
						   $("#codeInfo").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;"/><span id="codeError">该code已被使用。</span>');
						   return;
					   }
				   }
				});
		}
	</script>
	<#include "../common/common-js.ftl">
  </body>
</html>
