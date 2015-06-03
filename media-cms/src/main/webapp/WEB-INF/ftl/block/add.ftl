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
				    <input type="hidden" name="id" id="id">
						    <table class="table2" border="1"  >
						        <th colspan="2" class="tdleft">Banner内容配置</th>
						      	<tr>
						      		<td class="tdright">标题:</td>
						      		<td class="tdleft"><input type="text" id="title" name="title"><span id="titleInfo"></td>
						      	</tr>
						      	<tr>
						      		<td class="tdright">图标:</td>
						      		<td class="tdleft"><input type="text" id="icon" name="icon" size="60"><span id="iconInfo"></td>
						      	</tr>
						      	<tr>	
						      		<td class="tdright">类型:</td>
						      			<td class="tdleft">
						      				<select id="type" name="type" onchange="chooseParam()">
							      				<option value="dsddw">大神等等我</option>
							      				<option value="detail">单品</option>
							      				<option value="subject">专题</option>
							      				<option value="classification">频道分类二级</option>
							      				<option value="monthlyPayment">包月页面</option>
							      				<option value="column">栏目</option>
							      				<option value="haoShangBang">壕赏榜</option>
							      				<option value="ranklist">榜单</option>
							      				<option value="charge">充值页面</option>
						      				</select>
						      			</td>
						      		</tr>
						      		<tr id="param" name="param" style="display:none">
									</tr>
						      		<tr>
						      		<td colspan="2" align="center" >
						      			<button type="button" id="addBannerBtn" name="addBannerBtn" onclick="addBanner();">确定</button>
						      		</td>
						      	</tr>
						 </table>
						  </br>
						  </br>   	
			            <form action="/block/add.go" title="只更新块内容" method="post" id="add_form" onsubmit="return checkForm();">
			            <input type="hidden" name="mediaBlockId" value="<#if block??><#if block.mediaBlockId??>${block.mediaBlockId?c}</#if></#if>">
			          	<input type="hidden" name="groupId" value="<#if block??><#if block.groupId??>${block.groupId?c}</#if></#if>">
			            <table  class="table2" border="1" id="contentTable" bordercolor="#a0c6e5" rules="none">
			          	<th colspan="6" align="left">Banner信息列表</th>
			            <tr>
			            <td>块名称：</td>
			            <td colspan="5"  class="tdleft"><input type="text" name="name" id="name"  onblur="validInput('name')"><span style="width:1100px;text-align:left" id="nameInfo"  name="nameInfo"></span></td>
			            </tr>
			            <tr>
			            <td>块标识：</td>
			            <td colspan="5"  class="tdleft"><input type="text" name="code" id="code" onblur="checkCode(this.value)"  ><span style="width:1100px;text-align:left" id="codeInfo"  name="codeInfo"></span></td>
			            </tr>
			            <tr>
			            <td>块状态：</td>
			            <td colspan="5"  class="tdleft">
			            <input type="radio" name="status" id="status"  value="1" checked="true">有效
			             	<input type="radio" name="status" id="status"  value="0" >无效
			             	<span style="width:1100px;text-align:left" id="statusInfo"  name="codeInfo"></span>
			           </td>
			            </tr>
			            
			            	<tr>
						      	<th>标题</th>
						      	<th>图标</th>
						      	<th>类型</th>
						      	<th>参数</th>
						      	<th>跳转Url地址</th>
						      	<th>操作</th>
						     </tr>
						    
						 <#if returnInfo??>
							<#else>
							   <tr>
						     	<td colspan='6' align="center" ><button type="submit" id="submitBtn">确定</button></td>
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
	
	function checkForm(){
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
   		return flag;
	}
	/**
	 * 将当前配置的Banner加入到已以内容中去
	 *此处没有显示参数类型名,因为逆转时,没有保存参数类型名称
	 */
	function addBanner(){
		var title= $("#title").val();
		var icon= $("#icon").val();
		var typeName= $("#type").find("option:selected").text();
		var typeCode= $("#type").val();
		var paramCode= $("#id").val();//参数
		var paramName= $("#name").val();//参数
		var url= $("#url").val();
		if(title==''){
			$('#titleInfo').html('<img src="/images/wrong.jpg" style="width: 10px;height: 10 px">');
			$('#titleInfo').focus();
			return false;
		}
		var trHtml ="<tr>"
					+"<td><input type='hidden' id ='btitle' name='btitle' value='"+title+"' >"+title+"</td>"
					+"<td><input type='hidden' id ='bicon' name='bicon' value='"+icon+"' >"+icon+"</td>"
					+"<td><input type='hidden' id ='btype' name='btype' value='"+typeCode+"' >"+typeCode+"</td>";
		if($("#id").length>0&&$("#name").length>0){
			//不需要url地址
			trHtml +="<td><input type='hidden' id ='bcode' name='bcode' value='"+paramCode+"' >"+paramCode+"</td>"
			+"<td><input type='hidden' id ='burl' name='burl'></td>"
		}else if($("#url").length>0){
			//只需要url地址,不需要其它参数
			trHtml +="<td><input type='hidden' id ='bcode' name='bcode'></td><td><input type='hidden' id ='burl' name='burl' value='"+url+"'>"+url+"</td>"
		}else{
			trHtml +="<td><input type='hidden' id ='bcode' name='bcode'></td><td><input type='hidden' id ='burl' name='burl'></td>"
		}
		trHtml +="<td><a href='#' onclick='delRow(this)'>删除</td></td></tr>";
		//var table = $("#contentTable tr:last");//得到内容表格的最后一行
		var table = $("#contentTable tr").eq(-2); //获取table倒数第二行 $("#tab tr");//得到内容表格的最后一行
		table.after(trHtml);
	}
	
	/**
		 *根据类型选择不同的参数
		 */
		function chooseParam(){
			var type=  $("#type").val();
			var trHtml="";
			switch(type){
				case "detail":
					//单品
					trHtml = '<td width="20%" class="tdright">作品名称:</td><td width="80%" class="tdleft">'
					+'<input type="text" id="name" name="name"  readonly="true" class="txtInput searchInput" onclick="javascript:chooseMedia();"><span id="idInfo"></span></td>';
			break;
					break;
				case "subject":
					//专题
					trHtml ='<td width="20%" class="tdright">专题名称:</td><td width="80%" class="tdleft">'
					+'<input type="text" id="name" name="name"  readonly="true" class="txtInput searchInput" onclick="javascript:chooseSpecial();"><span id="idInfo"></span></td>';
					break;
				case "classification":
					//频道分类
					break;
				case "monthlyPayment":
					//包月
					break;
				case "haoShangBang":
					//壕赏榜
					break;
				case "ranklist":
					//榜单
					trHtml ='<td width="20%" class="tdright">榜单标识:</td><td width="80%" class="tdleft"> '
					+ '<input type="radio" id="id" name="id" value="sale" checked="checked">销量榜'
					+'<input type="radio" id="id" name="id" value="comment_star" >评星榜'
					+'<input type="radio" id="id" name="id" value="update" >更新榜'
					+'<input type="radio" id="id" name="id" value="rewards">打赏榜'
					+'<span id="urlInfo"></span></td>';
					break;
				case "dsddw":
					//大神等等我
					break;
				case "charge":
					//充值
					break;
				case "column":
					//栏目
					trHtml = '<td width="20%" class="tdright">栏目名称:</td><td width="80%" class="tdleft">'
					+'<input type="text" id="name" name="name"  readonly="true" class="txtInput searchInput" onclick="javascript:chooseColumn();"><span id="idInfo"></span></td>';
					break;
			}
			document.getElementById('param').outerHTML ="<tr id='param'>"+trHtml+"</tr>";
			return true;
		}
		
	

	function chooseMedia(){
		//复用添加分告页面功能
		$.dialog({title:'选择作品',top:'top',content:'url:/notice/media.go',
	   		icon:'succeed',width:850,height:600,lock:true
	    });
	}
	function chooseSpecial(){
		//复用添加分告页面功能
		$.dialog({title:'选择专题',top:'top',content:'url:/notice/special.go',
	   		icon:'succeed',width:850,height:600,lock:true
	    });
	}
	function chooseColumn(){
		$.dialog({title:'选择栏目',top:'top',content:'url:/column/list.go?isCallBack=1&codeId=id&nameId=name',
	   		icon:'succeed',width:850,height:600,lock:true
	    });
	}
	
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
						 	 $("#submitBtn").attr("disabled", false); 
						 	return;
					   }else{
						   $("#codeInfo").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;"/><span id="codeError">该code已被使用。</span>');
						   $("#submitBtn").attr("disabled", true); 
						   return;
					   }
				   }
				});
		}
	</script>
	<#include "../common/common-js.ftl">
  </body>
</html>
