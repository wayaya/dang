<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增公告内容</title>
<#include "../common/common-css.ftl">
<style type="text/css">
	input.txtInput {  
		background: fff;  
		background-repeat: no-repeat;  
		background-position: right center; 
		border:1px solid 999;  
		padding:2px 2px 2px 20px;  
	} 
input.searchInput {
	background-image: url(/images/toolbar/search.gif);
	}

</style>
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>公告管理&gt;&gt;添加公告</h3>
					<form id="add" name="add" method="post" action="/notice/save.go" onSubmit="return checkForm();">
					<input type="hidden" name="name" id="name">
					<table class="table2" id="addtable" name="addtable">
						<tr>
							<td width="20%" class="tdright">公告标题:</td>
							<td width="80%" class="tdleft">
							<input type="text" id="title" name="title" ><span id="titleInfo"></span></td>
						</tr>
						<tr>
							<td width="20%" class="tdright">类型:</td>
							<td width="80%" class="tdleft"> 
							<select id="type" name="type"  style="width: 120px" onchange="noticeChange()">
			    				<#list noticeTypeList as noticeType>
							    	<option value="${noticeType.type}">${noticeType.name}</option>
							    </#list>
							</select>
							<span id="typeInfo"></span>
							</td>
						</tr>
						<tr id="channelTr" name="channelTr" style="display:none">
						</tr>
						<tbody id="param" name="param" >
						<tr >
							<td width="20%" class="tdright">URL:</td>
							<td width="80%" class="tdleft"> 
							<input type="text" id="url" name="url" ><span id="urlInfo"></span>
						</tr>
						</tbody>
						<tr id="param" name="param" >
						<tr id="tr_startDate">
							<td width="20%" class="tdright">开始时间:</td>
							<td width="80%" class="tdleft"> <input id="startTime" name="startTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})"/> <span id="startTimeInfo"></span></td>
						</tr>
						<tr id="tr_endDate">
							<td width="20%" class="tdright">结束时间:</td>
							<td width="80%" class="tdleft"> <input type="text" id="endTime" name="endTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})"><span id="endTimeInfo"></span></td>
						</tr>
						<tr>
							<td colspan="2"><button type="button" onclick="javascript:checkForm();">确认</button>&nbsp;&nbsp;&nbsp;<button id="resetBtn" type="reset">重置</button></td>
						</tr>
					</table>
				</form>
				</div>
		</div>
</div>

<div id="url" name="url" style="display:none">
	<tr>
		<td width="20%" class="tdright">URL:</td>
		<td width="80%" class="tdleft"> <input type="text" id="url" name="url" ><span id="urlInfo"></span></td>
	</tr>
	</div>
	
	
	
</body>
<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script>
<#include "../common/common-js.ftl">
<script type="text/javascript">	
function chooseMedia(){
	$.dialog({title:'选择作品',top:'top',content:'url:/notice/media.go',
   		icon:'succeed',width:850,height:600,lock:true
    });
}

function chooseUsers(){
	$.dialog({title:'选择打赏用户',top:'top',content:'url:/notice/users.go',
   		icon:'succeed',width:850,height:600,lock:true
    });
}
//选择专题
function chooseSpecialTopic(){
	$.dialog({title:'选择专题',top:'top',content:'url:/notice/special.go',
   		icon:'succeed',width:850,height:600,lock:true
    });
}
//选择media分类
function chooseMediaCategory(){
	$.dialog({title:'选择分类',top:'top',content:'url:/notice/mediacategory.go',
   		icon:'succeed',width:850,height:600,lock:true
    });
}
//
function noticeChange(){
	var type =  $("#type").val();
	$("#param").html('');//先清除上次数据
	switch(type){
		case "0":
			// url 跳转
			$("#param").html('<tr><td width="20%" class="tdright">URL:</td><td width="80%" class="tdleft"> <input type="text" id="url" name="url" ><span id="urlInfo"></span></td></tr>');
			break;
		case "1":
			$("#param").html('<tr><td width="20%" class="tdright">作品ID:</td><td width="80%" class="tdleft">'
			+'<input type="text" id="id" name="id"  readonly="true" class="txtInput searchInput" onclick="javascript:chooseMedia();"><span id="idInfo"></span></td></tr>');
			break;
		case "2":
			$("#param").html('<tr><td width="20%" class="tdright">打赏用户ID:</td><td width="80%" class="tdleft">'
			+'<input type="text" id="id" name="id"  readonly="true"  class="txtInput searchInput" onclick="javascript:chooseUsers();" ><span id="idInfo"></span></td></tr>');
			break;
		case "3":
		case "4":
		case "8":
		case "9":
			//不插入tr　显示格式有问题
			$("#param").html('<tr></tr>');
			break;
		case "5":
			$("#param").html('<tr><td width="20%" class="tdright">专题ID:</td><td width="80%" class="tdleft">'
			+'<input type="text" id="id" name="id"  readonly="true" class="txtInput searchInput" onclick="javascript:chooseSpecialTopic();" ><span id="idInfo"></span></td></tr>');
			break;
		case "6": //榜单公告
			$("#param").html('<tr><td width="20%" class="tdright">榜单标识:</td><td width="80%" class="tdleft"> '
					+ '<input type="radio" id="code" name="code" value="sale" checked="checked">销量榜'
					+'<input type="radio" id="code" name="code" value="comment_star" >评星榜'
					+'<input type="radio" id="code" name="code" value="update" >更新榜'
					+'<input type="radio" id="code" name="code" value="rewards">打赏榜'
					+'<span id="urlInfo"></span></td></tr>');
			break;
		case "7": //分类公告
			$("#param").html('<tr><td width="20%" class="tdright">分类></td>'
					+'<td width="80%" class="tdleft"><input type="text" id="code" name="code"  readonly="true" class="txtInput searchInput" onclick="javascript:chooseMediaCategory();" > </td></tr>'
					+'<tr><td width="20%" class="tdright">分类排序维度:</td><td width="80%" class="tdleft"> '
					+ '<input type="radio" id="code" name="dimension" value="sale" checked="checked">销量榜'
					+'<input type="radio" id="code" name="dimension" value="comment_star" >评星榜'
					+'<input type="radio" id="code" name="dimension" value="update" >更新榜'
					+'<input type="radio" id="code" name="dimension" value="rewards">打赏榜'
					+'<span id="urlInfo"></span></td></tr>');
		
			break;
	}
	//控制是否显示男女频
	switch(type){
			case "0": //url跳转类型
			case "1"://单品公告
			case "2"://打赏公告
			case "3"://包月推广公告
			case "4"://升级公告
				$("#channelTr").hide();
			break;
			default:
				var content='<td width="20%" class="tdright">频道:</td>'
					content+='<td width="80%" class="tdleft"> '
					content+='<select id="channelType" name="channelType"  style="width: 120px" disable="true">'
					content+='<option value="np">男频</option>'
					content+='<option value="vp">女频</option>'
					content+='</select></td>';
				$("#channelTr").html(content);
				$("#channelTr").show();
	}
	//alert(document.getElementById("param").innerHTML);
}
function checkForm(){
	var flag = true;
	var title = $('#title').val();
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if(title== ""){
			$('#titleInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#titleInfo').focus();
			flag =  false;
	}
	
	if(startTime == ""){
			$('#startTimeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#startTimeInfo').focus();
			flag =  false;
	}
	if(endTime == ""){
		$('#endTimeInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
		$('#endTimeInfo').focus();
		flag =  false;
	}
	if(!flag) return false;
	$.ajax({
		type:"POST",
		url:"/notice/save.go",
		data: $("#add").serialize(),//"columnCode="+$('#columnCode').val(),
		dataType:"json",
		success: function(msg) {
			   if(typeof msg.result != 'undefined' && msg.result == 'success'){
				   alert("添加成功");
				   $("#resetBtn").trigger("click");
				   if($("#name")){
					   $("#name").attr("value","");
				   }
				   if($("#code")){
					   $("#code").attr("value","");
				   }
				   flag = true; 
			   }else{
				   alert("添加失败");
				   flag = false; 
			   }
		}
	});
	return flag;
}
</script>

</html>