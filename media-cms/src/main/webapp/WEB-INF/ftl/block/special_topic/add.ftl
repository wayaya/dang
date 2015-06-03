<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>添加块</title>
	<#include "../../common/common-css.ftl">
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
		
		.table3 td {
		    height: 40px;
		    text-align: left;
		    width: 90px;
		}
	</style>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right" style="width:99%;">
				<div class="m-r">
					<h3>块管里&gt;&gt;添加专题&nbsp;&nbsp;<image src="/images/return.jpg" onclick="javascript:history.go(-1)" /></h3>
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
					    <form action="/block/special/add.go" method="post" id="add_form" enctype="multipart/form-data">
					    <input type="hidden" id="stTypeId" name="stTypeId" title="专题所属类型编号" value="${specialTopic.stTypeId?c}">
					    <input type="hidden" id="columnCode" name="columnCode">
					    <input type="hidden" id="channelType" name="channelType" value="${specialTopic.channelType}">
						     <table class="table2" border="1" bordercolor="#a0c6e5" rules="none">
					     	    <tr>
					    			<td class="tdright" width="20%">专题名称：</td>
					    			<td class="tdleft"><input type="text" name="name" id="name" value="<#if specialTopic??><#if specialTopic.name??>${specialTopic.name}</#if></#if>" onblur="validInput('name')">
					    			<span style="text-align:left" id="nameInfo"  name="messageInfo"></span>
					    			</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">渠道类型：</td>
					    			<td class="tdleft">
					    				<select id="type" name="deviceType" onblur="validInput('deviceType')">
					    					<option value="YC_All" <#if specialTopic??><#if specialTopic.deviceType??><#if specialTopic.deviceType=='YC_All'>selected="selected"</#if></#if></#if>>通用</option>
					    					<option value="YC_Android" <#if specialTopic??><#if specialTopic.deviceType??><#if specialTopic.deviceType=='YC_Android'>selected="selected"</#if></#if></#if>>原创android</option>
					    					<option value="YC_iphone" <#if specialTopic??><#if specialTopic.deviceType??><#if specialTopic.deviceType=='YC_Iphone'>selected="selected"</#if></#if></#if>>原创iphone</option>
					    				</select>
					    			</td>
						      	</tr>
						      	<tr id="zhuanti_4">
					    			<td class="tdright">主图上传：</td>
					    			<td class="tdleft" style="text-align:left">
					    				<input type="file" title="选择文件" name="upfilename" id="upfilename" class="ifile" onchange="javascript:change();icon.value=this.value; "/>
					    				<input type="text"  name="icon"   id="icon" size="20" readonly style="height: 25px;" />
					    				<img src="/images/column/upload.gif" width="80" height="25" align="absmiddle" onclick="upfilename.click();" style="z-index: 999;" /></span>
					    			</td>
						      	</tr>
						      	<tr id="zhuanti_5" style="height: 80px;">
					    			<td class="tdright">主图展示：</td>
					    			<td class="tdleft"  style="text-align:left">
					    				<img id="preview" alt="" name="pic"  />
					    			</td>
						      	</tr>
						      	<tr>
						      	<td class="tdright">栏目名称:</td>
						      	<td class="tdleft">
									<br><input type="text" id="columnName" name="columnName"  readonly="true" ><button type="button" id="column" name="column">关联栏目</button>
									<span id="idInfo"></span>
									</td>
								</tr>
								<tr>
					    			<td class="tdright">排序值：</td><td class="tdleft"><input type="text" name="sort" id="sort" value="0">
					    			<span style="width:1100px;text-align:left" id="sortInfo"  name="sortInfo">该数字必须为数字，且该值越大专题越靠前</span>
					    			</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">是否首页显示：</td>
					    			<td class="tdleft" style="text-align: left;">
					    			<input type="radio" id="showHomePage" name="showHomePage" value="1" checked="true">是
					    			<input type="radio" id="showHomePage" name="showHomePage" value="0" >否
									</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">状态：</td>
					    			<td class="tdleft" style="text-align: left;">
					    			<input type="radio" id="status" name="status" value="1" checked="true">有效
					    			<input type="radio" id="status" name="status" value="0" >无效
									</td>
						      	</tr>
						      	<tr>
						      	<td class="tdright">说明:</td>
						      	<td class="tdleft">
						      	<textarea id="description" name="description" rows="3" cols="60"></textarea>
						      	</td>
						      	</tr>
						      	
						      	<#if returnInfo??>
							    <#else>
							    	<tr>
						    			<td colspan="2" style="padding-left:150px">
						    				<input type="button" value="提交" onclick="javascript:addForm()" /></input>
						    			</td>
							      	</tr>
							    </#if>
						      	
				            </table>
			            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript">
		 $(function () {
       	  $("#column").click(function () {
       			 //新建栏目 
       			  //新建栏目,跳转到栏目管理页面
       			 $.dialog({title:'选择栏目',top:'top',content:'url:/column/list.go?isCallBack=1&codeId=columnCode&nameId=columnName',
			   		icon:'succeed',width:1000,height:600,lock:true
			    });
         })
        });
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	   	function addForm(){
	   		var flag = true;
	   		var nameInfo = $('#name').val();
	   		var sort = $('#sort').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var reNum=/^\d+$/; 
	   		if(sort == ""||!reNum.test(sort)){
	   			$('#sortInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">该数字必须为非负整数，且该值越大专题越靠前');
	   			flag = false;
	   		}else{
	   			$('#sortInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		
	   		
	   		if(flag){
	   			$('#add_form').submit();
	   		}
	   	}
	</script>
	<#include "../../common/common-js.ftl">
  </body>
</html>
