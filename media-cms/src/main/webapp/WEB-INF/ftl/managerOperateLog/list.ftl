<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">	
		var condition ="";
		$(function(){
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/managerOperateLog/list.go?page=__id__'+"&"+$("#master_list_form").serialize())
		    });
		    $('#start').calendar({maxDate:'#end',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#end').calendar({minDate:'#start',format:'yyyy-MM-dd HH:mm:ss'});
		});		
		function searchMaster(){
	   		var result = checkParms();
	   		if(result != ""){
	   			alert(result);
	   			return false;
	   		}
	   		$('#master_list_form').submit();
	   	}
	   	
	   	function showDetail(managerOperateLogId){
	   		$.dialog({title:'操作日志详情页',content:'url:/managerOperateLog/detail.go?managerOperateLogId='+managerOperateLogId,
	   		icon:'succeed',width:1000,height:600,fixed:false,lock:true
			});
	   	}
	   		   	
	   	function checkParms(){
	   		$('#operator').val($.trim($('#operator').val()));
	   		$('#operateTargetId').val($.trim($('#operateTargetId').val()));
			if($('#operateTargetId').val() != "" && isNaN($('#operateTargetId').val())){
				return "操作对象ID必须为数字格式！";
			}
			return "";
	   	}	   	
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
	 		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>操作日志管理&gt;&gt;操作日志列表</h3>
				<div class="mrdiv">
		      		<form action="/managerOperateLog/list.go" method="post" id="master_list_form">
			      		 <table>
							<tr style="height:30px;">
								<td style="width:80px; text-align:right">
									操作人登录名：
								</td>
								<td style="width:120px; text-align:left">
									<input type="text" style="width: 120px;" name="operator" id="operator" value="<#if vo??><#if vo.operator??>${vo.operator}</#if></#if>">
								</td>
								<td style="width:80px; text-align:right">
									操作时间开始：
								</td>
								<td style="width:120px; text-align:left">
									<input type="text" style="width: 120px;" name="start" id="start" readonly="readonly" value="<#if vo??><#if vo.start??>${vo.start}</#if></#if>">
								</td>
							 	<td style="width:80px; text-align:right">
							 		操作时间结束：
							 	</td>
							 	<td style="width:120px; text-align:left">
							 		<input type="text" style="width: 120px;" name="end" id="end" readonly="readonly" value="<#if vo??><#if vo.end??>${vo.end}</#if></#if>">
							 	</td>								
							 	<td style="width:70px; text-align:right">
							 		操作结果：
							 	</td>
							 	<td style="width:120px; text-align:left">
								 	<select name = "operateResult" id="operateResult" style="width: 120px;" >
										<option value="">&nbsp;请选择</option>
										<option value="0" <#if vo ?? && vo.operateResult ?? && vo.operateResult == 0>selected="selected"</#if>>&nbsp;失败</option>
										<option value="1" <#if vo ?? && vo.operateResult ?? && vo.operateResult == 1>selected="selected"</#if>>&nbsp;成功</option>							
									</select>
							 	</td>
							 	<td style="width:80px; text-align:right">
							 		操作对象类型：
							 	</td>
							 	<td style="width:120px; text-align:left">
								 	<select name = "operateTargetType" id="operateTargetType" style="width: 120px;" >
										<option value="">&nbsp;请选择</option>
										<#if operateTargetTypeArray ??>
											<#list operateTargetTypeArray as operateTargetType>
												<option value="${operateTargetType.getType()}" <#if vo ?? && vo.operateTargetType ?? && vo.operateTargetType == operateTargetType.type>selected="selected"</#if>>${operateTargetType.getName()}</option>
											</#list>
										</#if>
									</select>
							 	</td>
							 	<td style="width:80px; text-align:right">
									操作对象ID：
								</td>
								<td style="width:120px; text-align:left">
									<input type="text" style="width: 120px;" name="operateTargetId" id="operateTargetId" value="<#if vo??><#if vo.operateTargetId??>${vo.operateTargetId?c}</#if></#if>">
								</td>
								<td style="width:70px; text-align:right">
									<button  onclick="return searchMaster()">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr style="height:30px;">
		                    <th>日志ID</th>
		                    <th>操作人</th>
		                    <th>操作结果</th>
		                    <th>操作对象类型</th>
		                    <th>操作对象ID</th>
		                    <th>操作时间</th>
		                    <th>操作描述</th>		
		                    <th>查看</th>	                    
	               	    </tr>
	               	    <#assign i = 0>
	               	 <#if pageFinder.data??>
			    			<#list pageFinder.data as managerOperateLog>
					    		<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if managerOperateLog.managerOperateLogId??>${managerOperateLog.managerOperateLogId?c}</#if></td>
						      		<td><#if managerOperateLog.operator??>${managerOperateLog.operator}</#if></td>
						      		<td>
							      		<#if managerOperateLog.operateResult??>
							      			<#if managerOperateLog.operateResult==1>
							      				成功
							      			<#elseif managerOperateLog.operateResult==0>
							      				失败					      								
							      			<#else>
							      			</#if>
							      		</#if>
						      		</td>
						      		<td>
							      		<#if managerOperateLog.operateTargetType??>
							      			<#if operateTargetTypeArray ??>
												<#list operateTargetTypeArray as operateTargetType>
													<#if operateTargetType.type == managerOperateLog.operateTargetType>
														${operateTargetType.getName()}
													</#if>							
												</#list>
											</#if>
							      		</#if>
						      		</td>
						      		<td><#if managerOperateLog.operateTargetId??>${managerOperateLog.operateTargetId?c}</#if></td>
						      		<td><#if managerOperateLog.creationDate??>${managerOperateLog.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>						    	
						      		<td title='${managerOperateLog.operateDescription?if_exists}'>
						      			<#if managerOperateLog.operateDescription??>
						      				<#if managerOperateLog.operateDescription?length lt 18>
						      					${managerOperateLog.operateDescription}
						      				<#else>
						      					${managerOperateLog.operateDescription[0..14]}......
						      				</#if>
						      			</#if>
						      		</td>
						      		<td>
						      			<a href="#" onclick="showDetail('${managerOperateLog.managerOperateLogId?c}')">详情</a>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
			    <div>
				    <div class="pagination rightPager"></div>
				    <div class="leftPager">总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
