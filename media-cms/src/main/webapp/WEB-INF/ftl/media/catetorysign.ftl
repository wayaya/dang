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
   			var cateIds = document.getElementById('cateIds').value;
   			if(cateIds.length == 0){
   				alert('分类不能为空！');
   				return false;
   			}
   			var signNames = document.getElementById('signNames').value;
   			if(signNames.length == 0){
   				alert('标签不能为空！');
   				return false;
   			}
   			$('#activity_type_add_form').submit();
	   	}
	   	
	   	function showCate(cateIds){
	   		
	   		$.dialog({id:"cateDialog",title:'选择分类',content:'url:/catetory/getSelCatetory.go?cateIds='+cateIds,
	   		icon:'succeed',width:300,height:400,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
               $.dialog.list['cateDialog'].content.window.setUp(document.getElementById('cateIds'),
               document.getElementById('cateNames'));
                return true;
            },
            focus: true
        }]
			});
			}
			function showLabel(signIds,signNames){
	   		
	   		var signId = document.getElementById('signIds').value;
	   		var signName = document.getElementById('signNames').value;
	   		$.dialog({id:"labelDialog",title:'选择标签',content:'url:/media/toSelLabel.go?id='+signId+"&name="+signName,
	   		icon:'succeed',width:500,height:400,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
               $.dialog.list['labelDialog'].content.window.setUp(document.getElementById('signIds'),
               document.getElementById('signNames'));
                return true;
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
                return true;
            },
            focus: true
        }]
			});
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				    <div>
					    <form action="/media/saveCateSign.go" method="post" id="activity_type_add_form" enctype="multipart/form-data">
					    <input type="hidden" name="mediaId" value="<#if media??><#if media.mediaId??>${media.mediaId?c}</#if></#if>">
						     <table width="100%">
			    				<tr>
					    			<td style="text-align:right;width:100px;height:60px;">分类(多选)：</td>
					    			<td width="60%" style="text-align:left">
						    			<input type="hidden" name="cateIds" id="cateIds" value="<#if cateIds??>${cateIds}</#if>">
						    			<input type="text" readOnly="readOnly" style="width:300px;" name="cateNames" id="cateNames" value="<#if cateNames??>${cateNames}</#if>" onblur="validInput('cateNames')">
						    		</td>	
					    			<td style="text-align:left"><input type="button" value="选择分类" onclick="showCate('${cateIds}');"></td>
						      	</tr>
						      	<tr>
					    			<td style="text-align:right">标签(多选)：</td>
					    			<td style="text-align:left">
						    			<input type="hidden" name="signIds" id="signIds" value="<#if signIds??>${signIds}</#if>">
						    			<input style="width:300px;" readOnly="readOnly" type="text" value="<#if media??><#if media.signNames??>${media.signNames}</#if></#if>" name="signNames" id="signNames" onblur="validInput('signNames')">
						    		</td>
					    			<td style="text-align:left"><input type="button" value="选择标签" onclick="showLabel();"></td>
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
