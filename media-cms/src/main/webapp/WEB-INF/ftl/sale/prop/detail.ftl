<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">  
		var reg1 =  /^\d+$/;
		$(function(){
			<#if operateFlag?? && operateFlag != '0'>
				<#if operateMsg?? && operateMsg == "ok">
					reset();
				<#else>	
				</#if>
			<#else>
				$("input[]").each(function(){
					$(this).attr("disabled","disabled");
				});
			</#if>
		}); 	
	   	
	   	function mergeForm(){
	   		var checkFlag = true;
	   		$("input").each(function(){
	   			if($(this).attr("type")!="hidden" && $(this).attr("type")!="file"){
					validInput($(this).attr("id"));
		   			if($(this).val()==""){
		   				checkFlag = false;
		   			}
		   			var objId = $(this).attr("id");
		   			if(objId=="propMainGoldPrice" || objId=="propSubGoldPrice" || objId=="propPurposeId"){
		   				if(!reg1.test($(this).val())){
		   					checkFlag = false;
		   				}
		   			}   
	   			}	   			
			});
			if(checkFlag){
				$("#master_list_form").submit();
			}   		
	   	}
	   	
	   	function reset(){
			$("input").each(function(){
				if($(this).attr("type")!="hidden"){
						$(this).val("");
		   			}	  					
				});
	   	}

	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>收入管理&gt;&gt;道具管理&gt;&gt;道具详情</h3>
					<#if operateFlag?? && operateFlag != '0' && operateMsg??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">
									<#if operateMsg=="ok">
										<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>
									<#else>
										<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>:${operateMsg}
									</#if>
									</span></td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div>
					    <form action="/saleBase/prop/merge.go" method="post" id="master_list_form" enctype="multipart/form-data">
					    	 <input type="hidden" name="propId" id="propId" value="<#if prop??><#if prop.propId??>${prop.propId?c}</#if></#if>">
					    	 <input type="hidden" name="operateFlag" id="operateFlag" value="<#if operateFlag??>${operateFlag}</#if>">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright">道具名称：</td><td><input type="text" name="propName" id="propName" value="<#if prop?? && prop.propName??>${prop.propName}</#if>" onblur="validInput('propName')"></td>
					    			<td class="tdleft" style="width:1100px" id="propNameInfo"></td>
						      	</tr>
						      	<tr>
					    			<td>主账户购买价格：</td><td><input type="text" name="propMainGoldPrice" id="propMainGoldPrice" value="<#if prop?? && prop.propMainGoldPrice??>${prop.propMainGoldPrice?c}</#if>"  onblur="validInput('propMainGoldPrice')"></td>
					    			<td class="tdleft" style="width:1100px" id="propMainGoldPriceInfo"></td>
						      	</tr>
						      	<tr>
					    			<td>副账户购买价格：</td><td><input type="text" name="propSubGoldPrice" id="propSubGoldPrice" value="<#if prop?? && prop.propSubGoldPrice??>${prop.propSubGoldPrice?c}</#if>"  onblur="validInput('propSubGoldPrice')"></td>
					    			<td class="tdleft" style="width:1100px" id="propSubGoldPriceInfo"></td>
						      	</tr>
						      	<tr>
					    			<td>道具功能id：</td><td><input type="text" name="propPurposeId" id="propPurposeId" value="<#if prop?? && prop.propPurposeId??>${prop.propPurposeId?c}</#if>"  onblur="validInput('propPurposeId')"></td>
					    			<td class="tdleft" style="width:1100px" id="propPurposeIdInfo"></td>
						      	</tr>
						      	<tr>
					    			<td>封面图：</td><td><input type="file" name="cover" id="cover">
					    			<#if prop?? && prop.coverPic??>
					    				<img width="140" height="200" src="<#if coverPath ??>${coverPath}</#if>${prop.coverPic}"/>
					    			</#if>
					    			</td><td style="width:1100px" id="coverInfo"></td>
						      	</tr>	
						      	<tr>
					    			<td>道具功能描述：</td><td><input type="text" name="propPurposeDesc" id="propPurposeDesc" value="<#if prop?? && prop.propPurposeDesc??>${prop.propPurposeDesc}</#if>"  onblur="validInput('propPurposeDesc')"></td>
					    			<td class="tdleft" style="width:1100px" id="propPurposeDescInfo"></td>
						      	</tr>	      	
						      	<tr>
						      		<#if operateFlag?? && operateFlag != '0'>
				    					<td>
				    						<#if operateFlag == "1">
					    						&nbsp;&nbsp;<a href="/saleBase/prop/detail.go?propId=<#if prop??><#if prop.propId??>${prop.propId?c}</#if></#if>&operateFlag=1"><image src="/images/reset.jpg"/></a>
					    					<#elseif operateFlag == "2">
					    						&nbsp;&nbsp;<a href="#" onclick="reset()"><image src="/images/clear.jpg"/></a>
					    					<#else>
					    					</#if>
					    					&nbsp;&nbsp;
				    					</td>
			    						<td class="tdleft"><a href="#" onclick="mergeForm()"><image src="/images/save.jpg"/></a></td>
					    			<#else>
					    				<td colspan="2" style="padding-left:150px"></td>
				    				</#if>					    	
					    			<td style="width:1100px"></td>
						      	</tr>
				            </table>
			            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../../common/common-js.ftl">
  </body>
</html>