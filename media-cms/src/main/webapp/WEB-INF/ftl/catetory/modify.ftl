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
	   		var nameReg = /^[\u4E00-\u9FA5\/A-Za-z]{1,10}$/;
	   		if(nameInfo == null || nameInfo == "" || !nameReg.test(nameInfo)){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var codeInfo = $('#code').val();
	   		var reg = new RegExp(/[A-Z]{1,}/);
	   		if(codeInfo == null || codeInfo == "" || !reg.test(codeInfo)){
	   			$('#codeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#codeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var indexOrderInfo = $('#indexOrder').val();
	   		if($.trim(indexOrderInfo)==''  ||  isNaN(indexOrderInfo) || indexOrderInfo.indexOf(".")>=0){
	   			$('#indexOrderInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#indexOrderInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var file = $('#imagefile').val();
	   		if(file!=null && file.length > 0){
	   			var re = /JPG$/;  
	   			if(!re.test(file.toUpperCase())){
	   				flag = false;
	   				$('#coverInfo').html('请上传JPG封面文件.<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			}else{
	   				$('#coverInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
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
					<h3>分类管理&gt;&gt;修改分类</h3>
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
					    <form  action="/catetory/add.go" target="_parent" method="post" id="activity_type_add_form" enctype="multipart/form-data">
					    <input type="hidden" name="id" value="<#if cate??><#if cate.id??>${cate.id?c}</#if></#if>">
					    <input type="hidden" name="parentId" value="<#if cate??><#if cate.parentId??>${cate.parentId?c}</#if></#if>">
						     <#if msg??><div><font color="red">${msg}</font></div></#if>
						     <table class="table3" style="width:700px;" border="0" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width:15%"><font color="red">*</font>名称：</td>
					    			<td class="tdleft"  style="width:50%"><input type="text" name="name" id="name" value="<#if cate??><#if cate.name??>${cate.name}</#if></#if>" onblur="validInput('name')">&nbsp;&nbsp;只能输入中文、/、大小写字母</td>
					    			<td class="tdleft" id="nameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright"><font color="red">*</font>编码：</td>
					    			<td class="tdleft"><input type="text" value="<#if cate??><#if cate.code??>${cate.code}</#if></#if>" name="code" id="code" onblur="validInput('code')">&nbsp;&nbsp;只能输入大写字母</td>
					    			<td class="tdleft" id="codeInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright"><font color="red">*</font>排序码：</td>
					    			<td class="tdleft"><input type="text" value="<#if cate??><#if cate.indexOrder??>${cate.indexOrder?c}</#if></#if>" name="indexOrder" id="indexOrder">&nbsp;&nbsp;只能输入数字且为整数</td>
					    			<td class="tdleft" id="indexOrderInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td class="tdright" style="width: 100px;">封面图：</td><td class="tdleft" style="width: 200px;"><input type="file" name="imagefile" id="imagefile">
					    				<span id="coverInfo"></span>
					    			</td>
					    			<td  colspan="3">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 100px;">封面图展示：</td><td class="tdleft" style="width: 200px;">
						    			<#if cate??><#if cate.image??>
						    				<img width="140px" height="200px" src="${picPath}${cate.image}" style="margin-top:10px;margin-bottom: 10px;"/>
						    			</#if></#if>
					    			</td>
					    			<td class="tdleft" colspan="3">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">是否启用：</td>
					    			<td class="tdleft" style="width: 200px;">
						    			<select name="status" id="status" style="width: 150px;">
								 			<option value="0" <#if cate??><#if cate.status??><#if (cate.status == 0)>selected = "selected" </#if></#if></#if>>否</option>
								 			<option value="1"  <#if cate??><#if cate.status??><#if (cate.status == 1)>selected = "selected" </#if></#if></#if>>是</option>
								 		</select>&nbsp;&nbsp;<span id="isSupportFullBuyInfo"></span>
					    			</td>
					    			<td class="tdleft" id="statusInfo"></td>
						      	</tr>
						      	<tr>
					    			<td colspan="2" style="text-align:center">
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
