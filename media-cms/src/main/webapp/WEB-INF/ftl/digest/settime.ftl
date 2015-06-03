<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">

    </style>
	
	<script type="text/javascript">
	
	
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				    <div>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width: 150px;">发送时间：</td>
					    			<td class="tdleft">
					    			<input type="text" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="showStartDate" id="showStartDate" readonly="readonly" value="">
					    			</td>
						      	</tr>
			    				<tr>
					    			<td class="tdright" style="width: 150px;">生效该文章：</td>
					    			<td class="tdleft">
					    			<input type="checkbox" name="isShow" id="isShow" value="1">
					    			</td>
						      	</tr>
				            </table>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
