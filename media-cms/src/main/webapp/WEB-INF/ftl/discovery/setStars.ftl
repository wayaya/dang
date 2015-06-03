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
		
		function getStars(){
			return $("#stars  option:selected").val();
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
					    			<td class="tdright">推荐评星：</td>
					    			<td class="tdleft">
					    			<select name="stars" id="stars" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="1" <#if dis??><#if dis.stars??><#if (dis.stars == 1)>selected = "selected" </#if></#if></#if>>★</option>
							 			<option value="2"  <#if dis??><#if dis.stars??><#if (dis.stars == 2)>selected = "selected" </#if></#if></#if>>★★</option>
							 			<option value="3"  <#if dis??><#if dis.stars??><#if (dis.stars == 3)>selected = "selected" </#if></#if></#if>>★★★</option>
							 			<option value="4"  <#if dis??><#if dis.stars??><#if (dis.stars == 4)>selected = "selected" </#if></#if></#if>>★★★★</option>
							 			<option value="5"  <#if dis??><#if dis.stars??><#if (dis.stars == 5)>selected = "selected" </#if></#if></#if>>★★★★★</option>
							 		</select>
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
