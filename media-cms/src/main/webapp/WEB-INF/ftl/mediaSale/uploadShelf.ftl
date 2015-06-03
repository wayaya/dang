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
		String.prototype.endWith=function(endStr){
  			var d=this.length-endStr.length;
  			return (d>=0&&this.lastIndexOf(endStr)==d)
		}
		
	   	function check(){
   			var file = $('#file').val();
   			if(file == ''){
   				alert('请上传excel文件');
   				return false;
   			}
   			file = file.toLowerCase();
   			if(!file.endWith('xls')&&!file.endWith('xlsx')){
   				alert('只允许 上传EXCEL文件');
   				return false;
   			}
   			
   			var shelfStatus = $("#shelfStatus  option:selected").val();
   			if(shelfStatus == 0){
   				var reason = $("#reason").val();
   				if(reason == ''){
   					alert('下架原因不能为空');
   					return false;
   				}
   			}
   			return true;
	   	}
	   	
	   	function change(){
	   		var shelfStatus = $("#shelfStatus  option:selected").val();
	   		if(shelfStatus == 0){
	   			$("#reasonTd").css('display','');
	   		}else{
	   			$("#reasonTd").css('display','none');
	   			$("#reason").val('');
	   		}
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				    <div>
					    <form action="/mediaSale/uploadShelf.go" method="post" id="activity_type_add_form" enctype="multipart/form-data">
						     <table width="100%">
			    				<tr>
					    			<td width="30%" style="text-align:right;">
					    			<font color="red">*</font>上传excel:</td>
					    			<td width="70%" style="text-align:left">
					    			<input type="file" name="file" id="file">
						    		</td>	
						      	</tr>
						      	<tr style="height:10px;"></tr>
						      	<tr>
					    			<td width="30%" style="text-align:right;">
					    			<font color="red">*</font>上下架:</td>
					    			<td width="70%" style="text-align:left">
					    			<select name="shelfStatus" id="shelfStatus" onchange="change();">
						 				<option value="1">上架</option>
						 				<option value="0">下架</option>
						 			</select>
						    		</td>
						      	</tr>
						      	<tr style="height:10px;"></tr>
						      	<tr id="reasonTd" style="display:none;">
						      		<td width="30%" style="text-align:right;">
						      		<font color="red">*</font>下架原因:</td>
						      		<td  width="70%" style="text-align:left">
						      			<input name="reason" id="reason">
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
