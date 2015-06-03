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
   				alert('请选择上传文件');
   				return false;
   			}
   			return true;
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				    <div>
					    <form action="/mediaResource/upload.go?dirId=${dirId}" method="post" id="activity_type_add_form" enctype="multipart/form-data">
						     <table width="100%">
			    				<tr style="height:30px;"></tr>
			    				<tr>
					    			<td width="30%" style="text-align:right;">
					    			<font color="red">*</font>上传:</td>
					    			<td width="70%" style="text-align:left">
					    			<input type="file" name="file" id="file">
						    		</td>	
						      	</tr>
						      	<tr style="height:10px;"></tr>
						      	<tr>
						      		<td><div id="msg">
						      		<text></text>
						      		</div></td>
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
