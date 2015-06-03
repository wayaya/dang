<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script src="/bootstrap/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
			
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	   	
	   	function chgOrder(chapId,index,
	   	mediaId,maxIndex,volumeId){
	   		$.dialog({id:"labelDialog",title:'调整顺序',content:'url:/chapter/toChgOrder.go?id='+chapId+"&index="+index+"&mediaId="+mediaId,
	   		icon:'succeed',width:350,height:200,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
               var order = $.dialog.list['labelDialog'].content.window.setUp();
               if(order == ""){
               		alert('请输入要调整的章节顺序值!');
               		return false;
               }
                if(isNaN(order)){
               		alert('章节序号值请输入数字!');
               		return false;
               	}
               	if(parseInt(order) < 0){
               		alert('章节序号值请输入正数!');
               		return false;
               	}
               	if(parseInt(order) == index || parseInt(order)-1 == index){
               		alert('本章节目前已经在该位置!');
               		return false;
               	}
               	var url = "/chapter/chgOrder.go?id="+chapId+"&index="+index+"&order="+order+"&mediaId="+mediaId;
               	if(arguments[4]){
               		url += "&volumeId"+arguments[4];
               	}
               	location = url;
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
					<h3>作品管理&gt;&gt;分卷、章节管理列表    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书名：${media.title}
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作品ID:${media.mediaId?c}</h3>
					<table style="width:600px;height:25px;font-size:15px;">
						<tr>
							<td><input type="button" value="返回" onclick="javascript:history.back();"></td>
						</tr>
					</table>
					<#if vols??>
						<#list vols as vol>
						<br><br>
							 <#if vol.title??>
							<div class="mrdiv" style="width:300px;">
							<table style="width:300px;">
								<tr>	
									<td style="width:200px;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${vol.title}
									</td>
									<td style="width:100px;">
										<a href="javascript:location='/volume/toEdit.go?id=${vol.volumeId?c}'">【修改】</a>
									</td>
								</tr>
							</table>	
						    </div>
						    </#if>
						    <#if vol.list??>
								 	<#assign i = 0>
									<table style="width:1150px;">
										
											<#list vol.list as chap>
												<#assign i = i+1>
												<#if i%3 == 1>
													<tr style="height:40px;width:1100px;border-bottom:dotted 1px black; ">
												</#if>
												<td style="width:230px; border-left: 1px dotted black;">
													&nbsp;&nbsp;&nbsp;&nbsp;${chap.title}
												</td>
												<td style="width:150px; border-right: 1px dotted black;">
													<a href="javascript:location='/chapter/toEdit.go?id=${chap.id?c}'">【修改】</a>
													<!--
													<#if chap.isShow??>
														<#if chap.isShow == 1>
															<a href="javascript:location='/chapter/setIsShow.go?id=${chap.id?c}&mediaId=${chap.mediaId?c}&isShow=0'">【隐藏】</a>
														</#if>
														<#if chap.isShow == 0>
															<a href="javascript:location='/chapter/setIsShow.go?id=${chap.id?c}&mediaId=${chap.mediaId?c}&isShow=1'">【显示】</a>
														</#if>
													</#if>
													-->
												</td>
												<#if i%3 == 0>
													</tr>
												</#if>
											</#list>
											<#if i%3 == 1>
													<td style="width:230px; border-left: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:150px; border-right: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:230px; border-left: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:150px; border-right: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													</tr>
											</#if>
											<#if i%3 == 2>
													<td style="width:230px; border-left: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:150px; border-right: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>									
													</tr>
											</#if>
									</table>
								</#if>
						</#list>
				    </#if>
				    <#if chaps??>
				    	<#assign i = 0>
									<table>
										
											<#list chaps as chap>
												<#assign i = i+1>
												<#if i%3 == 1>
													<tr style="height:40px;width:1100px;border-bottom:dotted 1px black; ">
												</#if>
												<td style="width:230px; border-left: 1px dotted black;">
													&nbsp;&nbsp;&nbsp;&nbsp;${chap.title}
												</td>
												<td style="width:150px; border-right: 1px dotted black;">
													<a href="javascript:location='/chapter/toEdit.go?id=${chap.id?c}'">【修改】</a>
													<!-- 
													<#if chap.isShow??>
													<#if chap.isShow == 1>
														<a href="javascript:location='/chapter/setIsShow.go?id=${chap.id?c}&mediaId=${chap.mediaId?c}&isShow=0'">【隐藏】</a>
													</#if>
													<#if chap.isShow == 0>
														<a href="javascript:location='/chapter/setIsShow.go?id=${chap.id?c}&mediaId=${chap.mediaId?c}&isShow=1'">【显示】</a>
													</#if>
													</#if>
													<a href="javascript:chgOrder('${chap.id?c}','${chap.index?c}','${chap.mediaId?c}','${maxIndex?c}');">【调】</a> 
													-->
												</td>
												<#if i%3 == 0>
													</tr>
												</#if>
											</#list>
											<#if i%3 == 1>
													<td style="width:230px; border-left: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:150px; border-right: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:230px; border-left: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:150px; border-right: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													</tr>
											</#if>
											<#if i%3 == 2>
													<td style="width:230px; border-left: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td style="width:150px; border-right: 1px dotted black;">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</td>									
													</tr>
											</#if>
									</table>
				    </#if>
				    <#if noChapter??>
				    	${noChapter}
				    </#if>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
