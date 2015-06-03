<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript" charset="utf-8" src="/script/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/script/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/script/ueditor/lang/zh-cn/zh-cn.js"></script>

    <style type="text/css">
        div{
            width:100%;
        }
    </style>
	
	<script type="text/javascript">
		$(document).ready(function(){
				$('#addBtn').click(function(){
					$.dialog({
						title:'封面图展示',
						content:"url:/epubImage/list.go?<#if dis??><#if dis.mediaId??>mediaId=${dis.mediaId?c}</#if></#if>",
				   		icon:'succeed',
				   		width:500,
				   		height:150,
				   		fixed:false,
				   		lock:true
				    });
				});
		   	
			});
		function showCardContentNum(){
			var cardRemark = $("#cardRemark").val();
			if(cardRemark.length>0){
				$("#contentNum").html('（ 摘要长度约为：'+ cardRemark.length +'字 ）');
			}
		}
		$(function(){
			show();
			showCardContentNum();
			$("#cardRemark").change(function(){
				showCardContentNum();
			});
			$("#pic1Path").change(function(){
			  pic1Path = $("#pic1Path").val();
			  if(pic1Path.length>0) {
			  	$("#pic1Show").html('<img width=\'50%\' id=\'inputPic\' src=\''+pic1Path+'\'/>');
			  }
		  	});
			$("input[name='signs']").each(function(){
				<#if dis ?? && dis.signIds ??>
					var signIds = '${dis.signIds}';
					var signIdList = signIds.split(";");
					for(var index = 0;index<signIdList.length;index++){
						if(signIdList[index].split(":")[1] == $(this).val().split(":")[1]){
							$(this).attr("checked","checked");
						}
					}
				</#if>
			});
			
			UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
			UE.Editor.prototype.getActionUrl = function(action) {
    				if (action == 'uploadimage') {
        				return '/digest/uploadImg.go';
    			}else {
        			return this._bkGetActionUrl.call(this, action);
    			}
			}
			UE.getEditor('contents');
		});
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	   	function activityTypeAddForm(){
	   		var picPath=$("#pic1Path").val();
	   		var flag = true;
	   		var titleInfo = $("#title").val();
	   		if(titleInfo == null || titleInfo == "" || titleInfo.length>50){
	   			$('#titleInfo').html('不能为空，长度最长为50个汉字。<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#titleInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var signNames=$("#signNames").val();
	   		if(signNames.indexOf(",")!=-1){
	   			var n=(signNames.split(',')).length-1;
	   			if(n>2){
		   			alert("最多选择三个标签！"); 
					flag= false; 
				}else{
					flag= true; 
				}
	   		}else{
	   			flag= true; 
	   		}
			
			
			var startDate=$("#showStartDateStr").val();
			if(startDate==null||startDate==""){
				$('#startTimeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;"> ( 时间为必须项。)');
	   			flag = false;
			}else{
				$('#startTimeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
			}
			
	   		var cardType = $("#cardType  option:selected").val();
	   		if(cardType == null || cardType == ""){
	   			$('#cardTypeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#cardTypeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		if(cardType == 0 || cardType == 1){
		   		var cardTitle = $("#cardTitle").val();
		   		
		   		if(cardTitle == null || cardTitle == ""){
		   			$('#cardTitleInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
		   			flag = false;
		   		}else{
		   			$('#cardTitleInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
		   		}
	   		}
	   		var mediaIdInfo = $("#mediaId").val();
	   		var reg = new RegExp(/^[0-9]{1,}$/);
	   		if(mediaIdInfo != null && mediaIdInfo != "" && !reg.test(mediaIdInfo)){
	   			$('#mediaIdInfo').html('只能输入数字。<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			//$('#mediaIdInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		var secondCatetoryId = $("#secondCatetoryId").val();
	   		if(secondCatetoryId != null && secondCatetoryId == ""){
	   			$('#secondCatetoryIdInfo').html('必须且只能选择一个分类。<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#secondCatetoryIdInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
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
	   			var pic1ShowEl = document.getElementById('pic1Show');
	   			
	   			if(pic1Path.length==0 && pic1ShowEl==null){
	   				flag = false;
	   				$('#pic1PathInfo').html('必须上传图片。<img src="/images/wrong.jpg"/ style="width: 20px;">');
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
	   		var contents = UE.getEditor('contents').getContent();
	   		if(contents.length == 0){
	   			flag = false;
	   			alert('内容不能为空!');
	   		}else{
	   			$('#content').val(contents);
	   		}
	   		var mediaId = $("#mediaId").val();
	   		
	   		if(flag && mediaId!=null && $.trim(mediaId)!=''){
	   				$.ajax({  
   				 		type : "post",  
    			 		url : "/digest/getMediaById.go",  
    			 		data : "mediaId=" + mediaId,  
    			 		async : false,//取消异步  
    			 		success : function(data){  
        					data = eval("(" + data + ")");  
        					if(!data.success){
        						alert("关联作品不存在!");
        						flag = false;
        					} 
    			 		}  
					});
	   		}
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	   	function show(){
				var cardType = $("#cardType  option:selected").val();
				if(cardType == 0){
			  		$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','');
					$('.pic1PathTr').css('display','none');
					
				}
				if(cardType == 1){
				   $('#inputPic').removeAttr("src");
					$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','');
					$('.pic1PathTr').css('display','');
					pic1Path = $("#pic1Path").val();
					if(pic1Path.length>0) {
					$("#pic1Show").html('<img width="50%" id="inputPic" src="'+pic1Path+'"/>');
					}
				}
				if(cardType == 2){
				   $('#inputPic').removeAttr("src");
					$('#cardTitleTr').css('display','');
					$('#cardRemarkTr').css('display','');
					$('.pic1PathTr').css('display','');
					pic1Path = $("#pic1Path").val();
					if(pic1Path.length>0) {
					$("#pic1Show").html('<img width="50%" id="inputPic" src="'+pic1Path+'"/>');
					}
				}
			}
		function showDemo(){
			$.dialog({
				title:'预览',
				content:UE.getEditor('contents').getContent(),
		   		width:250,
		   		height:600,
		   		fixed:false,
		   		lock:true
		    });
		}
		function showLabel(signIds,signNames){
	   		var signId = document.getElementById('signIds').value;
	   		var signName = document.getElementById('signNames').value;
	   		$.dialog({id:"labelDialog",title:'选择标签',content:'url:/digest/toSelLabel.go?id='+signId+"&name="+signName,
	   		icon:'succeed',width:500,height:400,fixed:false,lock:true,button: [
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
					<h3>文章管理&gt;&gt;文章信息新增/修改</h3>
				    <div>
					    <form action="/digest/update.go" method="post" id="activity_type_add_form" enctype="multipart/form-data">
					    <input type="hidden" name="id" value="<#if dis??><#if dis.id??>${dis.id?c}</#if></#if>">
					    <input type="hidden" name="chapterId" value="<#if chapter??><#if chapter.id??>${chapter.id?c}</#if></#if>">
					    <input type="hidden" id="content" name="content">
					    <input type="hidden" id="type" name="type" value=1>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
						     <tr id="cardTitleTr">
					    			<td><font color="red">*</font>标题：</td><td class="tdleft">
					    			<input style="width:400px" type="text" 
					    			value="<#if dis??><#if dis.cardTitle??>${dis.cardTitle}</#if></#if>" name="cardTitle" id="cardTitle" onblur="validInput('cardTitle')">
					    			&nbsp;&nbsp;<span id="cardTitleInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td><font color="red">*</font>作者：</td><td class="tdleft"><input style="width:250px" type="text" 
					    			  value="<#if dis??><#if dis.author??>${dis.author}</#if></#if>" name="author" id="author" onblur="validInput('author')">&nbsp;&nbsp;<span id="authorPennameInfo"></span>(<font color="red">*添加多位作者请用“,（英文输入法）”隔开，编辑作者名时，名字内不要出现“，”“：”“；”（中英文输入法两种情况下）等符号</font>)</td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td><font color="red">*</font>内容：</td><td class="tdleft">
					    			<div>
					    			<textarea id="contents" style="width:1000px;height:800px">
					    				<#if dis??><#if dis.content??>
											${dis.content}
										</#if></#if>
					    			</textarea> 
					    			</div>
					    			&nbsp;&nbsp;<span id="contentInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						        <tr>
					    			<td>当当电子书ID：</td><td class="tdleft"><input type="text" onblur="resetAuthor();"  value="<#if dis??><#if dis.mediaId??>${dis.mediaId?c}</#if></#if>" name="mediaId" id="mediaId">&nbsp;&nbsp;<span id="mediaIdInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
						      	<td class="left_table_tr_td" heigth="28px" width="25%">
							 	评星：
							 	<td><div style="text-align:left">
							 		<select name="stars" id="stars" style="width:50%" text-align:left>
							 			<option value="">全部</option>
							 			<option value="1" <#if dis??><#if dis.stars??><#if (dis.stars == 1)>selected = "selected" </#if></#if></#if>>★</option>
							 			<option value="2"  <#if dis??><#if dis.stars??><#if (dis.stars == 2)>selected = "selected" </#if></#if></#if>>★★</option>
							 			<option value="3"  <#if dis??><#if dis.stars??><#if (dis.stars == 3)>selected = "selected" </#if></#if></#if>>★★★</option>
							 			<option value="4"  <#if dis??><#if dis.stars??><#if (dis.stars == 4)>selected = "selected" </#if></#if></#if>>★★★★</option>
							 			<option value="5"  <#if dis??><#if dis.stars??><#if (dis.stars == 5)>selected = "selected" </#if></#if></#if>>★★★★★</option>
							 		</select>
							 	</td>
							 	</td>
							 	</tr>
							    <tr>
					    			<td>标签(多选)：</td>
					    			<td>
					    			<div style="text-align:left">
					    			<input style="width:300px;" readOnly="readOnly" type="text" <#if dis??><#if dis.signIds??>value="${dis.signIds}"</#if></#if> name="signNames" id="signNames" onblur="validInput('signNames')">
					    			<input type="button" value="选择标签" onclick="showLabel();">
						            <input type="hidden" name="signIds" id="signIds" 
							            <#if allSignIds??>
							           		 value="${allSignIds}"
							  		    </#if>
							  		>
					    			</td>
						      	</tr>
								<tr class="left_table_tr_td" heigth="28px" width="25%">
							 	<td>浏览模式：
									<td><div style="text-align:left">
								 		<select name="dayOrNight" id="dayOrNight" style="width:50%" text-align:left>
								 			<option value="0" <#if dis??><#if dis.dayOrNight??><#if (dis.dayOrNight == 0)>selected = "selected" </#if></#if></#if>>白天</option>
								 			<option value="1"  <#if dis??><#if dis.dayOrNight??><#if (dis.dayOrNight == 1)>selected = "selected" </#if></#if></#if>>黑夜</option>
								 		</select>
								 		</td>
								</td>
								</tr>
								<tr class="left_table_tr_td" heigth="28px" width="25%">
								<td>
							 	心情：
								<td>
									<div style="text-align:left">
								 		<select name="mood" id="mood" style="width:50%" text-align:left>
								 			<option value="0" <#if dis??><#if dis.mood??><#if (dis.mood == 0)>selected = "selected" </#if></#if></#if>>无</option>
								 			<option value="1"  <#if dis??><#if dis.mood??><#if (dis.mood == 1)>selected = "selected" </#if></#if></#if>>振奋（肌肉图标，励志类文章）：打点鸡血，提神又醒脑!</option>
								 			<option value="2" <#if dis??><#if dis.mood??><#if (dis.mood == 2)>selected = "selected" </#if></#if></#if>>明朗（灯泡图标，哲学宗教类文章）：迷惘滴人儿必读！</option>
								 			<option value="3"  <#if dis??><#if dis.mood??><#if (dis.mood == 3)>selected = "selected" </#if></#if></#if>>开心（啤酒图标，幽默类文章）：木事儿乐一个！</option>
								 			<option value="4" <#if dis??><#if dis.mood??><#if (dis.mood == 4)>selected = "selected" </#if></#if></#if>>放松（茶杯图标，心理安抚类文章）：开会儿小差，休息，休息！</option>
								 			<option value="5" <#if dis??><#if dis.mood??><#if (dis.mood == 5)>selected = "selected" </#if></#if></#if>>惊喜（魔棒图标，奇闻怪谈）：给生活加点料！</option>
								 			<option value="6" <#if dis??><#if dis.mood??><#if (dis.mood == 6)>selected = "selected" </#if></#if></#if>>虐心（骷髅图标，悲伤类文章） ：就爱虐心文字怎么着！</option>
								 		</select>
								</td>
								</td>
								</tr>
								<tr class="left_table_tr_td" heigth="28px" width="25%">
							 	<td>权重设置：
								<td>
									<div style="text-align:left">
								 		<select name="weight" id="weight" style="width:50%" text-align:left>
								 			<option value="5" <#if dis??><#if dis.weight??><#if (dis.weight == 5)>selected = "selected" </#if></#if></#if>>5</option>
								 			<option value="0" <#if dis??><#if dis.weight??><#if (dis.weight == 0)>selected = "selected" </#if></#if></#if>>0</option>
								 			<option value="1"  <#if dis??><#if dis.weight??><#if (dis.weight == 1)>selected = "selected" </#if></#if></#if>>1</option>
								 			<option value="2" <#if dis??><#if dis.weight??><#if (dis.weight == 2)>selected = "selected" </#if></#if></#if>>2</option>
								 			<option value="3"  <#if dis??><#if dis.weight??><#if (dis.weight == 3)>selected = "selected" </#if></#if></#if>>3</option>
								 			<option value="4" <#if dis??><#if dis.weight??><#if (dis.weight == 4)>selected = "selected" </#if></#if></#if>>4</option>
								 			<option value="6" <#if dis??><#if dis.weight??><#if (dis.weight == 6)>selected = "selected" </#if></#if></#if>>6</option>
								 			<option value="7"  <#if dis??><#if dis.weight??><#if (dis.weight == 7)>selected = "selected" </#if></#if></#if>>7</option>
								 			<option value="8" <#if dis??><#if dis.weight??><#if (dis.weight == 8)>selected = "selected" </#if></#if></#if>>8</option>
								 			<option value="9"  <#if dis??><#if dis.weight??><#if (dis.weight == 9)>selected = "selected" </#if></#if></#if>>9</option>
								 		</select>
								</td>
								</td>
								</tr>
						      	
						      	<tr>
					    			<td class="tdright">
					    			<font color="red">*</font>卡片模板：</td>
					    			<td class="tdleft">
							 		<select onchange="javascript:show();" name="cardType" id="cardType" style="width:50%">
							 			<option value="0" <#if dis??><#if dis.cardType??><#if (dis.cardType == 0)>selected = "selected" </#if></#if></#if>>文字</option>
							 			<option value="1"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 1)>selected = "selected" </#if></#if></#if>>图文</option>
							 			<option value="2"  <#if dis??><#if dis.cardType??><#if (dis.cardType == 2)>selected = "selected" </#if></#if></#if>>大图</option>
							 		</select>
					    			&nbsp;&nbsp;<span id="cardTypeInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr  id="cardRemarkTr" style="display:none;">
					    			<td><font color="red">*</font>卡片摘要：</td><td class="tdleft">
					    			<textarea cols=80 rows=10 name="cardRemark" id="cardRemark" onblur="validInput('cardRemark')"><#if dis??><#if dis.cardRemark??>${dis.cardRemark}</#if></#if></textarea>
					    			&nbsp;&nbsp;<span id="cardRemarkInfo"></span><span id="contentNum"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr  class="pic1PathTr" style="display:none;">
					    			<td><font color="red">*</font>请输入封面图地址：</td>
					    			<td class="tdleft" style="width:50px"><input id="pic1Path" type="text" name="pic1Path" value="<#if dis??><#if dis.pic1Path??>${dis.pic1Path}</#if></#if>">
					    			<#if dis??><#if dis.mediaId??><button type="button" id="addBtn" >查看封面图</button></#if></#if></td>
						      	</tr>
						      	<tr class="pic1PathTr">
					    			<td id="pic1Show" class="tdleft" colspan=2></td>
						      	</tr>
						      	<tr>
								<td>生效状态：
									 <td><div style="text-align:left">
								 		<select name="isShow" id="isShow" style="width:50%" text-align:left>
								 			<option value="0" <#if dis??><#if dis.isShow??><#if (dis.isShow == 0)>selected = "selected" </#if></#if></#if>>未生效</option>
								 			<option value="1"  <#if dis??><#if dis.isShow??><#if (dis.isShow == 1)>selected = "selected" </#if></#if></#if>>已生效</option>
								 		</select>
								 	 </td>
								</td>
								</tr>
								<tr>
					    			<td class="tdright" style="width: 150px;">生效时间：</td>
					    			<td class="tdleft">
					    			<input type="text" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="showStartDateStr" id="showStartDateStr" readonly="readonly" value="<#if dis??><#if dis.showStartDate??>${dis.showStartDate?string("yyyy-MM-dd HH:mm:ss")}</#if></#if>">
					    			&nbsp;&nbsp;<span id="startTimeInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td colspan=2 class="tdleft">
					    				<image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" />&nbsp;
					    				<a href="javascript:showDemo();">预览</a>&nbsp;
					    				
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
