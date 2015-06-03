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
	   			$('#activity_type_add_form').submit();
	   	}

		function getVal(){
			var arr = [];
			arr.push($('#isSupportFullBuy   option:selected').val());
			arr.push($('#price').val());
			return arr;
		}
		function change(){
			var price = ${price};
			var oldPrice = ${sale.price?c};
			var isSupportFullBuy = ${sale.isSupportFullBuy};
			var isSupportFullBuyVal = $('#isSupportFullBuy   option:selected').val();
			if(isSupportFullBuyVal == 1){
				$('#price').attr('disabled',false);
			}
			if(isSupportFullBuyVal == 0){
				$('#price').attr('disabled','disabled');
				$('#price').val(oldPrice);
			}
			if(isSupportFullBuy == 0 && isSupportFullBuyVal == 1){
				$('#price').val(price);
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
					    <input type="hidden" name="saleId" value="<#if sale??><#if sale.saleId??>${sale.saleId?c}</#if></#if>">
						     <br>
						     <table border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright">&nbsp;&nbsp;&nbsp;&nbsp;是否支持全本购买：
					    			<select onchange="change();" name="isSupportFullBuy" id="isSupportFullBuy">
								 			<option value="0" <#if sale??><#if sale.isSupportFullBuy??><#if (sale.isSupportFullBuy == 0)>selected = "selected" </#if></#if></#if>>否</option>
								 			<option value="1"  <#if sale??><#if sale.isSupportFullBuy??><#if (sale.isSupportFullBuy == 1)>selected = "selected" </#if></#if></#if>>是</option>
								 		</select>
					    			</td>
						      	</tr>
						      	<tr style="height:10px;"></tr>
						      	<tr>
					    			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价钱：
					    			<input type="text" <#if sale??><#if sale.isSupportFullBuy??><#if (sale.isSupportFullBuy == 0)> disabled </#if></#if></#if> value="<#if sale??><#if sale.price??>${sale.price?c}</#if></#if>" name="price" id="price"">
					    			</td>
						      	</tr>
				            </table>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
  </body>
</html>
