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
		
	   	function activityTypeAddForm(){
	   		var flag = true;
	   		var nameInfo = $('#name').val();
	   		if(nameInfo == null || nameInfo == ""){
	   			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#nameInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var priceInfo = $('#price').val();
	   		if(priceInfo == null || priceInfo == ""){
	   			$('#priceInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#priceInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		var isSupportSubscribeInfo = $('#isSupportSubscribe   option:selected').val();
	   		if(isSupportSubscribeInfo == null || isSupportSubscribeInfo == ""){
	   			$('#isSupportSubscribeInfo').html('<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#isSupportSubscribeInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   		}
	   		
	   		
	   		/**
	   		var file = $('#cover').val();
	   		
	   		if(file!=null && file.length > 0){
	   			var re = /JPG$/;  
	   			if(!re.test(file.toUpperCase())){
	   				flag = false;
	   				$('#coverInfo').html('请上传JPG封面文件.<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			}else{
	   				$('#coverInfo').html('<img src="/images/right.jpg"/ style="width: 20px;">');
	   			}
	   		}
	   		**/
	   		if(flag){
	   			$('#activity_type_add_form').submit();
	   		}
	   	}
	   	
	   	function validInput(id){
	   		var value = $('#'+id).val();
	   		if(value == null || value == "" || value.length >100){
	   			$('#'+id+"Info").html('<img src="/images/wrong.jpg" style="width: 20px;">');
	   		}else{
	   			$('#'+id+"Info").html('<img src="/images/right.jpg" style="width: 20px;"/>');
	   		}
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>销售主体管理&gt;&gt;销售主体修改</h3>
				    <div>
					    <form action="/mediaSale/update.go" method="post" id="activity_type_add_form" enctype="multipart/form-data">
					    <input type="hidden" name="saleId" value="<#if sale??><#if sale.saleId??>${sale.saleId?c}</#if></#if>">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width: 150px;">
					    			<font color="red">*</font>标题：</td><td class="tdleft"><input type="text" name="name" id="name" value="<#if sale??><#if sale.name??>${sale.name}</#if></#if>" onblur="validInput('name')">&nbsp;&nbsp;<span id="nameInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>类型：</td><td class="tdleft">
					    			<select name="type" disabled id="type" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if sale??><#if sale.type??><#if (sale.type == 0)>selected = "selected" </#if></#if></#if>>单品</option>
							 			<option value="1"  <#if sale??><#if sale.type??><#if (sale.type == 1)>selected = "selected" </#if></#if></#if>>打包</option>
							 		</select>
					    			</td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td><font color="red">*</font>价格：</td><td class="tdleft"><input type="text" disabled value="<#if sale??><#if sale.price??>${sale.price?c}</#if></#if>" name="price" id="price" onblur="validInput('price')">&nbsp;&nbsp;<span id="priceInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td><font color="red">*</font>是否支持订阅：</td><td class="tdleft">
					    			<select name="isSupportSubscribe" id="isSupportSubscribe" style="width:50%">
							 			<option value="0" <#if sale??><#if sale.isSupportSubscribe??><#if (sale.isSupportSubscribe == 0)>selected = "selected" </#if></#if></#if>>否</option>
							 			<option value="1"  <#if sale??><#if sale.isSupportSubscribe??><#if (sale.isSupportSubscribe == 1)>selected = "selected" </#if></#if></#if>>是</option>
							 		</select>
					    			&nbsp;&nbsp;<span id="isSupportSubscribeInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	<tr>
					    			<td>上下架：</td><td class="tdleft">
					    			<select name="shelfStatus" disabled id="shelfStatus" style="width:50%">
							 			<option value="0" <#if sale??><#if sale.shelfStatus??><#if (sale.shelfStatus == 0)>selected = "selected" </#if></#if></#if>>下架</option>
							 			<option value="1"  <#if sale??><#if sale.shelfStatus??><#if (sale.shelfStatus == 1)>selected = "selected" </#if></#if></#if>>上架</option>
							 		</select>
					    			&nbsp;&nbsp;<span id="chapterCntInfo"></span></td><td style="width:1100px" id=""></td>
						      	</tr>
						      	
						      	<tr>
					    			<td>是否支持全本购买：</td><td class="tdleft">
						    			<select name="isSupportFullBuy" disabled id="isSupportFullBuy" style="width:50%">
								 			<option value="0" <#if sale??><#if sale.isSupportFullBuy??><#if (sale.isSupportFullBuy == 0)>selected = "selected" </#if></#if></#if>>否</option>
								 			<option value="1"  <#if sale??><#if sale.isSupportFullBuy??><#if (sale.isSupportFullBuy == 1)>selected = "selected" </#if></#if></#if>>是</option>
								 		</select>&nbsp;&nbsp;<span id="isSupportFullBuyInfo"></span>
					    			</td><td style="width:1100px" ></td>
						      	</tr>
						      	<tr>
					    			<td>描述：</td><td class="tdleft">
					    			<textarea cols=40 rows=10 name="desc" id="desc" onblur="validInput('desc')"><#if sale??><#if sale.desc??>${sale.desc}</#if></#if></textarea></td><td style="width:1100px" id="descInfo"></td>
						      	</tr>
						      	<tr>
					    			<td colspan="2" style="padding-left:150px">
					    				<image src="/images/save.jpg" onclick="javascript:activityTypeAddForm()" />
					    			</td>
					    			<td style="width:1100px"></td>
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
