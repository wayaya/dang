<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">	
		$(function(){
			$("#serch").click(
				function(){
					$('#master_list_form').submit();
				}				
			);
			<#if errorMsg ??>
				alert('${errorMsg}');
				return;
			</#if>
		});		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
	 		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>数据库管理&gt;&gt;数据库查询</h3>
				<div class="mrdiv">
		      		<form action="/jdbcSql/list.go" method="post" id="master_list_form">
		      			<input type="hidden" name="operateFlag" id="operateFlag" value="1">
			      		 <table>
							<tr>
								<td style="text-align:left">									
									<textarea cols=100 rows=5 name="sql" id="sql"><#if vo??>${vo}</#if></textarea>
								</td>
								<td style="width:700px; text-align:left">
									<button id="serch">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">		
	               	    <#assign i = 0>
	               	 	<#if resultList??>
			    			<#list resultList as resultMap>
					    		<#assign i = i+1>					    		
			    				<#if i == 1>
			    					<tr style="height:28px;">
				    					<#list resultMap?keys as columnName>			    						
						                    <th>${columnName}</th>				               	    
				    					</#list> 
			    					</tr>			    	
			    				</#if>
			    				<tr>
						    		<#list resultMap?keys as columnName>			    						
					                    <td>${resultMap[columnName]}</td>				               	    
			    					</#list> 
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
			    <div>
				    <div class="pagination rightPager"></div>
				    <div class="leftPager">总数：
				    	<#if resultList ??>
				    		${resultList?size}
				    	<#else>
				    		0
				    	</#if>
				    </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
