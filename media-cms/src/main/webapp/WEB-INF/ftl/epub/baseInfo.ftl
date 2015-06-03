<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
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
	   		var titleInfo = $('#title').val();
	   		if(titleInfo == null || titleInfo == ""){
	   			$('#titleInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#titleInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		
	   		
	   		
	   		var file = $('#cover').val();
	   		
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
					<h3>作品管理&gt;&gt;基本信息修改</h3>
				    <div>
					    <form action="/epub/update.go" method="post" id="activity_type_add_form" enctype="multipart/form-data">
					    <input type="hidden" name="mediaId" value="<#if media??><#if media.mediaId??>${media.mediaId?c}</#if></#if>">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width: 80px;"><font color="red">*</font>书名：</td><td class="tdleft" style="width: 400px;" style="width: 400px;"><input type="text" name="title" id="title" value="<#if media??><#if media.title??>${media.title}</#if></#if>" onblur="validInput('title')">&nbsp;&nbsp;<span id="titleInfo"></span></td>
					    			<td class="tdright" style="width: 80px;">副标题：</td><td class="tdleft" style="width: 400px;"><input type="text" value="<#if media??><#if media.subTitle??>${media.subTitle}</#if></#if>" name="subTitle" id="subTitle" onblur="validInput('subTitle')">&nbsp;&nbsp;<span id="subTitleInfo"></span></td>
					    			<td width="400px">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 80px;">作家笔名：</td><td class="tdleft" style="width: 400px;"><input type="text" readOnly="readOnly"  value="<#if media??><#if media.authorPenname??>${media.authorPenname}</#if></#if>" name="authorPenname" id="authorPenname" onblur="validInput('authorPenname')">&nbsp;&nbsp;<span id="authorPennameInfo"></span></td>
					    			<td>CP名称：</td><td class="tdleft" style="width: 400px;"><input type="text" readOnly="readOnly"  value="<#if media??><#if media.providerName??>${media.providerName}</#if></#if>" name="providerName" id="providerName" onblur="validInput('providerName')">&nbsp;&nbsp;<span id="providerNameInfo"></span></td>
					    			<td width="400px">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 80px;">章节数：</td><td class="tdleft" style="width: 400px;"><input type="text" readOnly="readOnly" value="<#if media??><#if media.chapterCnt??>${media.chapterCnt}</#if></#if>" name="chapterCnt" id="chapterCnt" onblur="validInput('chapterCnt')">&nbsp;&nbsp;<span id="chapterCntInfo"></span></td>
					    			<td class="tdright" style="width: 80px;">书号：</td><td class="tdleft" style="width: 400px;"><input type="text" readOnly="readOnly" value="<#if media??><#if media.uid??>${media.uid}</#if></#if>" name="uid" id="uid" onblur="validInput('uid')">&nbsp;&nbsp;<span id="uidInfo"></span></td>
					    			<td width="400px">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 80px;">角色：</td><td class="tdleft" style="width: 400px;"><input type="text" value="<#if media??><#if media.role??>${media.role}</#if></#if>" name="role" id="role">&nbsp;&nbsp;<span id="roleInfo"></span></td>
					    			<td class="tdright" style="width: 80px;">关键字：</td><td class="tdleft" style="width: 400px;"><input type="text" value="<#if media??><#if media.keyWords??>${media.keyWords}</#if></#if>" name="keyWords" id="keyWords" onblur="validInput('keyWords')">&nbsp;&nbsp;<span id="keyWordsInfo"></span></td>
					    			<td width="400px">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 80px;">显示状态：</td><td class="tdleft" style="width: 400px;">
						    			<select name="isShow" id="isShow" style="width:50%">
								 			<option value="0" <#if media??><#if media.isShow??><#if (media.isShow == 0)>selected = "selected" </#if></#if></#if>>隐藏</option>
								 			<option value="1"  <#if media??><#if media.isShow??><#if (media.isShow == 1)>selected = "selected" </#if></#if></#if>>显示</option>
								 		</select>&nbsp;&nbsp;<span id="isShowInfo"></span>
					    			</td>
					    			<td class="tdright" style="width: 80px;">是否支持全本购买：</td><td class="tdleft" style="width: 400px;">
						    			<select name="isSupportFullBuy" disabled id="isSupportFullBuy" style="width:50%">
								 			<option value="0" <#if media??><#if media.isSupportFullBuy??><#if (media.isSupportFullBuy == 0)>selected = "selected" </#if></#if></#if>>否</option>
								 			<option value="1"  <#if media??><#if media.isSupportFullBuy??><#if (media.isSupportFullBuy == 1)>selected = "selected" </#if></#if></#if>>是</option>
								 		</select>&nbsp;&nbsp;<span id="isSupportFullBuyInfo"></span>
					    			</td>
					    			<td width="400px">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 80px;">封面图：</td><td class="tdleft" style="width: 400px;"><input type="file" name="cover" id="cover">
					    			<span id="coverInfo"></span>
					    			</td>
					    			<td colspan="2" id="descsInfo"></td>
					    			<td width="400px">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 80px;">封面图展示：</td><td class="tdleft" style="width: 400px;">
					    			<#if media??><#if media.coverPic??>
					    				<img width="140" height="200" src="${picPath}${media.coverPic}" style="margin-top: 10px; margin-bottom: 10px;"/>
					    			</#if></#if>
					    			</td>
					    			<td class="tdleft" colspan="4">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright" style="width: 80px;">作品简介：</td>
					    			<td class="tdleft" colspan="4">
					    			<textarea cols=40 rows=10 name="descs" id="descs" onblur="validInput('descs')"><#if media??><#if media.descs??>${media.descs}</#if></#if></textarea></td>
						      	</tr>
						      	<tr>
					    			<td colspan="5" style="padding-left:150px" class="tdleft">
					    				<image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" />
					    			</td>
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
