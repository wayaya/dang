<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>添加专题分类节点</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body> 
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right" style="width:99%;">
				<div class="m-r">
					<h3>专题管里&gt;&gt;添加专题分类&nbsp;&nbsp;<image src="/images/return.jpg" onclick="javascript:history.go(-1)" /></h3>
				    <div>
					    <form action="/block/special/savecategory.go" method="post" id="add" onsubmit="return checkForm();">
					    <input type="hidden" id="parentId" name="parentId" value="${parentId}"></input>
					    <input type="hidden" id="path" name="path" value="${path}"></input>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules="none">
					     	    <tr>
					    			<td class="tdright">名称：</td><td><input type="text" name="categoryName" id="categoryName" ></td><td style="width:1100px;text-align:left" id="categoryNameInfo"  name="categoryNameInfo"></td>
						      	</tr>
					     	    <tr>
					    			<td class="tdright">标识：</td><td><input type="text" name="categoryCode" id="categoryCode" ></td><td style="width:1100px;text-align:left" id="categoryCodeInfo"  name="categoryCodeInfo"></td>
						      	</tr>
					     	   <tr>
					    			<td class="tdright">频道类型：</td>
					    			<td>
					    				<select id="type" name="channel" >
					    					<option value="ALL" >全频</option>
					    					<option value="NP">男频</option>
					    					<option value="VP" >女频</option>
					    				</select>
					    			</td>
					    			<td style="width:1100px;text-align:left" id="typeInfo" name="channelInfo"></td>
						      	</tr>
						      	<tr>
						      		<td colspan="2"><button type="submit">确定</button></td>
						      	</tr>
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
	   		var nameInfo = $('#categoryName').val();
	   		var codeInfo = $('#categoryCode').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#categoryNameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#categoryNameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		if(codeInfo == null || codeInfo == ""){
	   			$('#categoryCodeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#categoryCodeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		if(flag){
	   			$('#add_form').submit();
	   		}
	   	}
	</script>
	<#include "../../common/common-js.ftl">
  </body>
</html>
