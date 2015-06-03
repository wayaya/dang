<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">	
		$(function(){
			$("#serchSlave").click(
				function(){
					$('#redisForm').submit();
				}				
			);
			
			$("#serchMaster").click(
				function(){
					
					$('#redisForm').attr("action","/redisQuery/master.go");
					$('#redisForm').submit();
				}				
			);
			
		});		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
	 		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>Redis管理&gt;&gt;Redis查询</h3>
				<div class="mrdiv">
		      		<form id="redisForm" action="/redisQuery/slave.go" method="post" >
			      		 <table>
							<tr>
								<td style="text-align:left">									
									<textarea cols=100 rows=5 name="key"><#if key??>${key}</#if></textarea>
								</td>
								<td style="width:700px; text-align:left">
									<button id="serchSlave" type="button">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;从&nbsp;&nbsp;库</button>
									<button id="serchMaster" type="button">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;主&nbsp;&nbsp;库</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
	               	 	<#if result??>
			    			${result}
				      	</#if>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
