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
	   		
	   		
	   		var priceInfo = $('#price').val();
	   		
	   		if(priceInfo == null || priceInfo == "" || isNaN(priceInfo)){
	   			$('#priceInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#priceInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   	
	   		var iosPriceInfo = $('#iosPrice').val();
	   		if(iosPriceInfo != null && iosPriceInfo != "" &&  isNaN(iosPriceInfo)){
	   			$('#iosPriceInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#iosPriceInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	   	
	   	function showLabel(signIds,signNames){
	   		
	   		var signId = document.getElementById('signIds').value;
	   		var signName = document.getElementById('signNames').value;
	   		$.dialog({id:"labelDialog",title:'选择标签',content:'url:/media/toSelLabel.go?id='+signId+"&name="+signName,
	   		icon:'succeed',width:350,height:400,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
               $.dialog.list['labelDialog'].content.window.setUp(document.getElementById('signIds'),
               document.getElementById('signNames'));
                return true;
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
                return true;
            },
            focus: true
        }]
			});
	   	}
	   	
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>作品管理&gt;&gt;章节修改</h3>
				    <div>
					    <form action="/chapter/update.go" method="post" id="activity_type_add_form">
					    <input type="hidden" name="id" value="<#if chapter??><#if chapter.id??>${chapter.id?c}</#if></#if>">
					    <input type="hidden" name="mediaId" value="<#if chapter??><#if chapter.mediaId??>${chapter.mediaId?c}</#if></#if>">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width:100px;"><font color="red">*</font>标题：</td>
					    			<td class="tdleft" style="width:200px;"><input type="text" name="title" id="title" value="<#if chapter??><#if chapter.title??>${chapter.title}</#if></#if>" onblur="validInput('title')"></td>
					    			<td class="tdleft" style="width:100px" id="titleInfo"></td>
					    			
					    			<td class="tdright">描述：</td>
					    			<td class="tdleft"><input type="text" value="<#if chapter??><#if chapter.desc??>${chapter.desc}</#if></#if>" name="desc" id="desc" onblur="validInput('desc')"></td>
					    			<td style="width:100px" id="descInfo"></td>
						      	</tr>
						      	<tr>
						      		<td class="tdright"><font color="red">*</font>序号：</td>
					    			<td class="tdleft" style="width:200px;"><input readOnly="readOnly" type="text" value="<#if chapter??><#if chapter.index??>${chapter.index?c}</#if></#if>" name="index" id="index"  onblur="validInput('indexOrder')"></td>
					    			<td style="width:100px" id="indexInfo"></td>
					    			<td class="tdright"><font color="red">*</font>字数：</td>
					    			<td class="tdleft" style="width:200px;"><input readOnly="readOnly" type="text" value="<#if chapter??><#if chapter.wordCnt??>${chapter.wordCnt?c}</#if></#if>" name="wordCnt" id="wordCnt"  onblur="validInput('wordCnt')"></td>
					    			<td style="width:100px" id="wordCntInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright"><font color="red">*</font>价格：</td>
					    			<td class="tdleft" style="width:200px;"><input type="text" value="<#if chapter??><#if chapter.price??>${chapter.price}</#if></#if>" name="price" id="price"  onblur="validInput('price')"></td>
					    			<td style="width:100px;" id="priceInfo"></td>
					    			<td class="tdright">IOS价格：</td>
					    			<td class="tdleft" style="width:200px;"><input type="text" value="<#if chapter??><#if chapter.iosPrice??>${chapter.iosPrice}</#if></#if>" name="iosPrice" id="iosPrice"  onblur="validInput('iosPrice')"></td>
					    			<td style="width:100px;" id="iosPriceInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright"><font color="red">*</font>是否免费：</td>
					    			<td class="tdleft" style="width:200px;">
							 		<select name="isFree" id="isFree" style="width:170px;">
							 			<option value="0" <#if chapter??><#if chapter.isFree??><#if (chapter.isFree == "0")>selected = "selected" </#if></#if></#if>>否</option>
							 			<option value="1"  <#if chapter??><#if chapter.isFree??><#if (chapter.isFree == "1")>selected = "selected" </#if></#if></#if>>是</option>
							 		</select>
					    			</td>
					    			<td></td>
					    			<td class="tdright">标签：</td>
					    			<td class="tdleft" style="width:200px;">
					    			<input type="hidden" name="signIds" id="signIds" value="<#if chapter.signIds??>${chapter.signIds}</#if>">
					    			<div>
					    			<input style="width:150px;" type="text" value="<#if chapter??><#if chapter.signNames??>${chapter.signNames}</#if></#if>" name="signNames" id="signNames" onblur="validInput('signNames')">
					    			<input type="button" value="选择标签" onclick="showLabel();">
					    			</div>
					    			</td>
					    			<td style="width:100px" id="signNamesInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">推荐语：</td>
					    			<td class="tdleft" style="width:200px;"><input type="text" value="<#if chapter??><#if chapter.recommandWords??>${chapter.recommandWords}</#if></#if>" name="recommandWords" id="recommandWords"  onblur="validInput('recommandWords')"></td>
					    			<td class="tdleft" style="width:100px;" id="recommandWordsInfo"></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">内容：</td>
					    			<td class="tdleft" colspan="5" style="width:600px;">
					    			<textarea readOnly="readOnly" cols=100 rows=20><#if chapter??><#if chapter.content??>${chapter.content}</#if></#if></textarea>
					    			</td>
					    			
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
