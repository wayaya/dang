<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>入库任务管理&gt;&gt;入库任务管理列表</h3>
					<div class="mrdiv">
					<table class="grid" valign="top">
					<form id="importFile" action="/epubImport/importFile.go" method="post" enctype="multipart/form-data">
						<tr align="center">
							<td align="right">上传文件：</td>
							<td align="left"> <input type="file" id="fileName" name="fileUpload" class="finput required" style="width:150px;"/></td>
							<td colspan="2"><input type="button" class="btn" style="width: 90px" value="批量入库" id="import" /></td>
						</tr>
					</form>
					<td>
					</tr>
					</table>
		            </div>
		            
		            <div>
		            <table class="table2">
				        <tr>
			               		<th style="width:15%">入库失败的ProductId</th>
			               		<th style="width:15%">入库成功的数量</th>
			               		<th style="width:15%">与数据库中重复的数据的数量</th>
				        </tr>
				        <tr>
				            <#assign i = 0>
				            
				            <#if failFtlList??>
		           			    <td>
					            <#list failFtlList as epubImport>
				    				<#if epubImport??>
				    					${epubImport?c},
						      		</#if>
						      	</#list>
						      	</td>
				      		<#else>
				      	    		<td>0条数据</td>
				      	    </#if>
					            <#if successNum??>
				    					<td>${successNum?c}</td>
					      	    <#else>
					      	    		<td>0条数据</td>
					      	    </#if>
					            <#if sameNum??>
				    					<td>${sameNum?c}</td>
					      	    <#else>
					      	    		<td>0条数据</td>
					      	    </#if>
					      </tr>
		            </table>
		            </div>
			    </div>
			    <div class="pagination rightPager"></div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
		<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<script type="text/javascript" src="/script/jquery/jquery.form.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	$('#import').click(function(){
		if ($("#fileName").val()=='') {
			alert("请选择文件！");
			return;
		}
		if (confirm("确认执行批量重新入库操作？")) {
					$("#importFile").submit();
				}
		});
	});
	</script>
	
  </body>
</html>
