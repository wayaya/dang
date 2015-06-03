<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	
	
	<script type="text/javascript" charset="utf-8" src="/script/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/script/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/script/ueditor/lang/zh-cn/zh-cn.js"></script>

    <style type="text/css">
        div{
            width:100%;
        }
    </style>
	
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
	   		var cardType = $("#cardType  option:selected").val();
	   		
	   		if(cardType == null || cardType == ""){
	   			$('#cardTypeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#cardTypeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var cardTitle = $("#cardTitle").val();
	   		
	   		if(cardTitle == null || cardTitle == ""){
	   			$('#cardTitleInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#cardTitleInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var secondCatetoryId = $("#secondCatetoryId").val();
	   		
	   		if(secondCatetoryId != null && secondCatetoryId != "" && secondCatetoryId.split(',').length > 1){
	   			$('#secondCatetoryIdInfo').html('只能选择一个分类。<img src="/images/right.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#secondCatetoryIdInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		if(cardType == 0 || cardType ==1){
	   			var cardRemark = $("#cardRemark").val();
	   			if(cardRemark == null || cardRemark == ""){
	   				$('#cardRemarkInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   				flag = false;
	   			}else{
	   				$('#cardRemarkInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   			}
	   		}
	   		if(cardType == 1 || cardType ==2){
	   			var pic1Path = $('#pic1Path').val();
	   			if(pic1Path==null && pic1Path.length==0){
	   				flag = false;
	   				$('#pic1PathInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			}
	   			if(pic1Path!=null && pic1Path.length > 0){
	   			var re = /JPG$/;  
	   			if(!re.test(pic1Path.toUpperCase())){
	   				flag = false;
	   				$('#pic1PathInfo').html('请上传JPG封面文件.<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			}else{
	   				$('#pic1PathInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   			}
	   			}
	   		}
	   		/**
	   		if(cardType ==2){
	   			var pic2Path = $('#pic2Path').val();
	   			if(pic2Path==null && pic2Path.length==0){
	   				flag = false;
	   				$('#pic2PathInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			}
	   			if(pic2Path!=null && pic2Path.length > 0){
	   				var re = /JPG$/;  
	   				if(!re.test(pic2Path.toUpperCase())){
	   					flag = false;
	   					$('#pic2PathInfo').html('请上传JPG封面文件.<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   				}else{
	   					$('#pic2PathInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   				}
	   			}
	   			var pic3Path = $('#pic3Path').val();
	   			if(pic3Path==null && pic3Path.length==0){
	   				flag = false;
	   				$('#pic3PathInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			}
	   			if(pic3Path!=null && pic3Path.length > 0){
	   				var re = /JPG$/;  
	   				if(!re.test(pic3Path.toUpperCase())){
	   					flag = false;
	   					$('#pic3PathInfo').html('请上传JPG封面文件.<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   				}else{
	   					$('#pic3PathInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   				}
	   			}
	   		}
	   		*/
	   		var contents = UE.getEditor('contents').getContent();
	   		if(contents.length == 0){
	   			flag = false;
	   			alert('内容不能为空!');
	   		}else{
	   			$('#content').val(contents);
	   		}
	   		
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	   	
	   		function showCate(cateIds){
	   		var url = 'url:/catetory/getSelCatetory.go?';
	   		if(cateIds){
	   			url+='cateIds='+cateIds;
	   		}
	   		$.dialog({id:"cateDialog",title:'选择分类',content:url,
	   		icon:'succeed',width:300,height:400,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
               $.dialog.list['cateDialog'].content.window.setUp(document.getElementById('secondCatetoryId'),
               document.getElementById('secondCatetoryName'));
               
                return true;
            },
            focus: true
        }]
			});
			}
			
			function showSign(signIds){
	   		var url = 'url:/goodArticleSign/getSelSign.go?';
	   		if(signIds){
	   			url+='signIds='+signIds;
	   		}
	   		$.dialog({id:"cateDialog",title:'选择标签',content:url,
	   		icon:'succeed',width:300,height:400,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
               $.dialog.list['cateDialog'].content.window.setUp(document.getElementById('signId'),
               document.getElementById('signName'));
               
                return true;
            },
            focus: true
        }]
			});
			}
			
			$(function(){
			UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
				UE.Editor.prototype.getActionUrl = function(action) {
    				if (action == 'uploadimage') {
        				return '/discovery/uploadImg.go';
    			}else {
        			return this._bkGetActionUrl.call(this, action);
    			}
			}
			<#if dis??><#if dis.cardType??>
				var cardType = ${dis.cardType};
				if(cardType == 0){
					$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','');
				}
				if(cardType == 1){
					$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','');
					
					$('#pic1PathTr').css('display','');
				}
				if(cardType == 2){
					$('#cardTitleTr').css('display','');
					$('#pic1PathTr').css('display','');
					$('#pic2PathTr').css('display','');
					$('#pic3PathTr').css('display','');
				}
			</#if></#if>
			UE.getEditor('contents');
			setTimeout(function(){
			<#if dis??><#if dis.content??>
			
			UE.getEditor('contents').setContent('${dis.content}',true);
			</#if></#if>
			},500);
			});
			
			function show(){
				var cardType = $("#cardType  option:selected").val();
				
				if(cardType == ''){
					$('#cardTitleTr').css('display','none');
					$('#cardRemarkTr').css('display','none');
					$('#pic1PathTr').css('display','none');
					$('#pic1PathTr').css('display','none');
					$('#pic1PathTr').css('display','none');
				}
				if(cardType == 0){
					$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','');
					
					$('#pic1PathTr').css('display','none');
					$('#pic1PathTr').css('display','none');
					$('#pic1PathTr').css('display','none');
				}
				if(cardType == 1){
					$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','');
					
					$('#pic1PathTr').css('display','');
					$('#pic2PathTr').css('display','none');
					$('#pic3PathTr').css('display','none');
				}
				if(cardType == 2){
					$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','none');
					
					$('#pic1PathTr').css('display','');
					//$('#pic2PathTr').css('display','');
					//$('#pic3PathTr').css('display','');
				}
				
				$('#cardTitle').val('');
				$('#cardRemark').val('');
				$('#pic1Path').val('');
				$('#pic2Path').val('');
				$('#pic3Path').val('');
			}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>当心好文管理&gt;&gt;当心好文信息增加/修改</h3>
				    <div>
					    <form action="/discovery/update.go" method="post" id="activity_type_add_form" enctype="multipart/form-data">
					    <input type="hidden" name="id" value="<#if dis??><#if dis.id??>${dis.id?c}</#if></#if>">
					    <input type="hidden" id="content" name="content">
					    <input type="hidden" id="type" name="type" value=2>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td  style="width: 180px;">标题：</td><td class="tdleft"><input type="text" name="title" id="title" value="<#if dis??><#if dis.title??>${dis.title}</#if></#if>" onblur="validInput('title')">&nbsp;&nbsp;<span id="titleInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>作者：</td><td class="tdleft"><input type="text"  value="<#if dis??><#if dis.author??>${dis.author}</#if></#if>" name="author" id="author" onblur="validInput('author')">&nbsp;&nbsp;<span id="authorPennameInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>关联作品ID：</td><td class="tdleft"><input type="text"  value="<#if dis??><#if dis.mediaId??>${dis.mediaId?c}</#if></#if>" name="mediaId" id="mediaId" onblur="validInput('mediaId')">&nbsp;&nbsp;<span id="mediaIdInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>关联作品名称：</td><td class="tdleft"><input type="text"  value="<#if dis??><#if dis.mediaName??>${dis.mediaName}</#if></#if>" name="mediaName" id="mediaName" onblur="validInput('mediaName')">&nbsp;&nbsp;<span id="mediaNameInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>推荐评星：</td><td class="tdleft">
					    			<select name="stars" id="stars">
							 			<option value="">全部</option>
							 			<option value="1" <#if dis??><#if dis.stars??><#if (dis.stars == 0)>selected = "selected" </#if></#if></#if>>★</option>
							 			<option value="2"  <#if dis??><#if dis.stars??><#if (dis.stars == 1)>selected = "selected" </#if></#if></#if>>★★</option>
							 			<option value="3"  <#if dis??><#if dis.stars??><#if (dis.stars == 1)>selected = "selected" </#if></#if></#if>>★★★</option>
							 			<option value="4"  <#if dis??><#if dis.stars??><#if (dis.stars == 1)>selected = "selected" </#if></#if></#if>>★★★★</option>
							 			<option value="5"  <#if dis??><#if dis.stars??><#if (dis.stars == 1)>selected = "selected" </#if></#if></#if>>★★★★★</option>
							 	</select>
					    			&nbsp;&nbsp;<span id="starsInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>栏目：</td><td class="tdleft">
					    			<input type="hidden" name="columnName" value="当心好文">
					    			<select name="columnId" disabled id="columnId">
							 			<option value="">全部</option>
							 			<option value="1" selected = "selected">当心好文</option>
							 	</select>
					    			&nbsp;&nbsp;<span id="starsInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>是否显示：</td><td class="tdleft">
					    		<select name="isShow" id="isShow">
							 			<option value="">全部</option>
							 			<option value="0" <#if dis??><#if dis.state??><#if (dis.state == 0)>selected = "selected" </#if></#if></#if>>不显示</option>
							 			<option value="1"  <#if dis??><#if dis.state??><#if (dis.state == 1)>selected = "selected" </#if></#if></#if>>显示</option>
							 	</select>
					    			&nbsp;&nbsp;<span id="isShowInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">分类：</td>
					    			<td  class="tdleft">
					    			<input type="text"  readOnly="readOnly" name="secondCatetoryName" id="secondCatetoryName" value="<#if dis??><#if dis.secondCatetoryName??>${dis.secondCatetoryName}</#if></#if>" onblur="validInput('secondCatetoryName')">
					    			<input type="button"  value="选择分类" onclick="showCate('<#if dis??><#if dis.secondCatetoryId??>${dis.secondCatetoryId?c}</#if></#if>');">
					    			<input type="hidden" name="secondCatetoryId" id="secondCatetoryId" value="<#if dis??><#if dis.secondCatetoryId??>${dis.secondCatetoryId?c}</#if></#if>">
					    			</td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">标签：</td>
					    			<td  class="tdleft">
					    			<input type="text"  name="signName" id="signName" value="<#if dis??><#if dis.signName??>${dis.signName}</#if></#if>" onblur="validInput('signName')">
					    			<input type="button" value="选择标签" onclick="showSign('<#if dis??><#if dis.signId??>${dis.signId?c}</#if></#if>');">
					    			<input type="hidden" name="signId" id="signId" value="<#if dis??><#if dis.signId??>${dis.signId?c}</#if></#if>">
					    			</td>
						      	</tr>
						      	<tr>
					    			<td>内容：</td><td class="tdleft">
					    			<div>
					    			<script id="contents" type="text/plain" style="width:800px;height:400px;"></script>
					    			</div>
					    			&nbsp;&nbsp;<span id="contentInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td class="tdright">
					    			卡片模板：</td>
					    			<td class="tdleft">
							 		<select onchange="javascript:show();" name="cardType" id="cardType" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if dis??><#if dis.cardType??><#if (dis.cardType == 0)>selected = "selected" </#if></#if></#if>>文字</option>
							 			<option value="1"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 1)>selected = "selected" </#if></#if></#if>>图文</option>
							 			<option value="2"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 2)>selected = "selected" </#if></#if></#if>>小图</option>
							 		</select>
					    			&nbsp;&nbsp;<span id="cardTypeInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr id="cardTitleTr" style="display:none;">
					    			<td>文章标题：</td><td class="tdleft">
					    			<input type="text" 
					    			value="<#if dis??><#if dis.cardTitle??>${dis.cardTitle}</#if></#if>" name="cardTitle" id="cardTitle" onblur="validInput('cardTitle')">
					    			&nbsp;&nbsp;<span id="cardTitleInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr  id="cardRemarkTr" style="display:none;">
					    			<td>文章摘要：</td><td class="tdleft">
					    			<textarea cols=80 rows=10
					    			name="cardRemark" id="cardRemark" onblur="validInput('cardRemark')">
					    			<#if dis??><#if dis.cardRemark??>${dis.cardRemark}</#if></#if>
					    			</textarea>&nbsp;&nbsp;<span id="cardRemarkInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr  id="pic1PathTr" style="display:none;">
					    			<td>图片1：</td><td class="tdleft"><input type="file" name="pic1" id="pic1Path">
					    			<#if dis??><#if dis.pic1Path??>
					    				<img width="140" height="200" src="${picPath}${dis.pic1Path}"/>
					    			</#if></#if>&nbsp;&nbsp;<span id="coverInfo"></span>
					    			</td><td style="width:1100px" ></td>
						      	</tr>
						      	<tr  id="pic2PathTr" style="display:none;">
					    			<td>图片2：</td><td class="tdleft"><input type="file" name="pic2" id="pic2Path">
					    			<#if dis??><#if dis.pic2Path??>
					    				<img width="140" height="200" src="${picPath}${dis.pic2Path}"/>
					    			</#if></#if>&nbsp;&nbsp;<span id="coverInfo"></span>
					    			</td><td style="width:1100px" ></td>
						      	</tr>
						      	<tr  id="pic3PathTr" style="display:none;">
					    			<td>图片3：</td><td class="tdleft"><input type="file" name="pic3" id="pic3Path">
					    			<#if dis??><#if dis.pic3Path??>
					    				<img width="140" height="200" src="${picPath}${dis.pic3Path}"/>
					    			</#if></#if>&nbsp;&nbsp;<span id="coverInfo"></span>
					    			</td><td style="width:1100px" ></td>
						      	</tr>
						      	<tr>
					    			<td colspan="2" style="padding-left:150px">
					    			<#if userSessionInfo?? && userSessionInfo.f['189']?? >
					    				<image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" />
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
	<#include "../common/common-js.ftl">
  </body>
</html>
