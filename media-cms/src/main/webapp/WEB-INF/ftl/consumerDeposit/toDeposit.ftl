<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">	
				function deposit(){
					$.post("/consumerDeposit/handleDeposit.go", {custId:$("#custId").val(),deviceType:$("#deviceType").val(),mainPrice:$("#mainPrice").val(),subPrice:$("#subPrice").val()},
					   function(data){
					   		alert(data);				   				
					   });
				}		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
	 		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>充值管理&gt;&gt;人工充值</h3>
				<div class="mrdiv">
		      		<form action="" method="post" id="master_list_form">
			      		 <table>
			      		 	<br/>
							<tr>
								<td style="width:80px; text-align:right">
									用户ID：
								</td>
								<td style="text-align:left" colspan="6">										
									<textarea cols=150 rows=6 name="custId" id="custId"></textarea>
								</td>								
							</tr>
							<tr><td colspan="7">&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
							<tr>
								<td style="width:80px; text-align:right">
							 		设备类型：
							 	</td>
							 	<td style="width:120px; text-align:left">
								 	<select name = "deviceType" id="deviceType" style="width: 120px;" >
										<option value="android">&nbsp;安卓</option>
										<option value="iphone">&nbsp;IOS</option>
									</select>
							 	</td>
								<td style="width:80px; text-align:right">
									金铃铛充值：
								</td>
								<td style="width:120px; text-align:left">
									<input type="text" style="width: 120px;" name="mainPrice" id="mainPrice" value="">
								</td>
							 	<td style="width:80px; text-align:right">
							 		银铃铛充值：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="subPrice" id="subPrice" value="">
							 	</td>
								<td style="width:120px; text-align:left">
									<input type="button" value="&nbsp;&nbsp;走&nbsp;你&nbsp;&nbsp;" onclick="return deposit();"/>
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
