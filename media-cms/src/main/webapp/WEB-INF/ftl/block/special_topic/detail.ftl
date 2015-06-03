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
					<h3>块管里&gt;&gt;专题详情 &nbsp;&nbsp;<image src="/images/return.jpg" onclick="javascript:history.go(-1)" /></h3>
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
					     <table class="table3" border="1" bordercolor="#a0c6e5" rules="none">
				     	    <tr>
				    			<td class="tdright">专题名称：</td><td><input type="text" name="name" id="name" value="<#if specialTopic??><#if specialTopic.name??>${specialTopic.name}</#if></#if>" onblur="validInput('name')"></td><td style="width:1100px;text-align:left" id="nameInfo"  name="messageInfo"></td>
					      	</tr>
					      	<tr>
				    			<td class="tdright">渠道类型：</td>
				    			<td>
				    				<select id="type" name="deviceType" onblur="validInput('deviceType')">
				    					<option value="YC_common" <#if specialTopic??><#if specialTopic.deviceType??><#if specialTopic.deviceType=='YC_common'>selected="selected"</#if></#if></#if>>通用</option>
				    					<option value="YC_Android" <#if specialTopic??><#if specialTopic.deviceType??><#if specialTopic.deviceType=='YC_Android'>selected="selected"</#if></#if></#if>>原创android</option>
				    					<option value="YC_iphone" <#if specialTopic??><#if specialTopic.deviceType??><#if specialTopic.deviceType=='YC_iphone'>selected="selected"</#if></#if></#if>>原创iphone</option>
				    				</select>
				    			</td>
				    			<td style="width:1100px;text-align:left" id="typeInfo" name="messageInfo"></td>
					      	</tr>
					      	<tr>
				    			<td class="tdright">频道类型：</td>
				    			<td>
				    				<select id="type" name="channelType" onblur="validInput('channelType')">
				    					<option value="NP" <#if specialTopic??><#if specialTopic.channelType??><#if specialTopic.channelType=='NP'>selected="selected"</#if></#if></#if>>男频</option>
				    					<option value="VP" <#if specialTopic??><#if specialTopic.channelType??><#if specialTopic.channelType=='VP'>selected="selected"</#if></#if></#if>>女频</option>
				    					<option value="ALL" <#if specialTopic??><#if specialTopic.channelType??><#if specialTopic.channelType=='ALL'>selected="selected"</#if></#if></#if>>全频</option>
				    				</select>
				    			</td>
				    			<td style="width:1100px;text-align:left" id="typeInfo" name="messageInfo"></td>
					      	</tr>
					      	
					      	<#if specialTopic??>
						      	<#if specialTopic.picPath??><#if specialTopic.picPath != "">
							      	<tr style="height: 80px;">
						    			<td class="tdright">(原)主图：</td>
						    			<td colspan="2" style="text-align:left">
						    				<img src="${specialTopic.picPath}" width="500" height="80"/>
						    			</td>
							      	</tr>
							    </#if></#if>
						    </#if>
						    
					      	<tr>
				    			<td class="tdright">选择推荐分类：</td>
				    			<td>
				    				<select id="blockGroupId" name="blockGroupId" onchange="changBlock()">
				    					<option>不关联</option>
				    					<#if specialTopicListOption??>${specialTopicListOption}</#if>
				    				</select>
				    			</td>
				    			<td style="width:1100px;text-align:left" id="relationColumnCodeInfo" name="messageInfo"></td>
					      	</tr>
					      	<tr>
				    			<td class="tdright">关联推荐位：</td>
				    			<td id="getSelectBlockByGroupId" colspan="2">
				    				<#if blockInfo??>${blockInfo}</#if>
				    			</td>
					      	</tr>
					      	<tr>
				    			<td class="tdright">状态：</td>
				    			<td colspan=2 style="text-align: left;">
									<select id="status" name="status">
				    					<option value="1" <#if specialTopic??><#if specialTopic.status??><#if specialTopic.status==1>selected="selected"</#if></#if></#if>>有效</option>
				    					<option value="0" <#if specialTopic??><#if specialTopic.status??><#if specialTopic.status==0>selected="selected"</#if></#if></#if>>无效</option>
				    				</select>
								</td>
					      	</tr>
					      	<tr>
				    			<td class="tdright">排序：</td><td><input type="text" name="sort" id="sort" value="<#if specialTopic??><#if specialTopic.sort??>${specialTopic.sort}</#if></#if>"></td><td style="width:1100px;text-align:left" id="nameInfo"  name="messageInfo">该数字必须为正整数，且该值越大专题月靠前</td>
					      	</tr>
			            </table>
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
		
		function changBlock(){
			var groupId = $("#blockGroupId").val();
			$.ajax({
			   type: "POST",url: "/block/special/getSelectBlockByGroupId.go?r="+Math.random(),
			   data: {"groupId":groupId},
			   dataType:"json",
			   success: function(data){
			   		$('#getSelectBlockByGroupId').html(data.blockInfo);
			   }
			});
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
	<#include "../../common/common-js.ftl">
  </body>
</html>
