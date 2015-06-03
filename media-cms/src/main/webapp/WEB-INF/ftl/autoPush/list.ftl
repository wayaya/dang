<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		var condition ="";
		$(function(){
			makecondition();
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize?c},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/autoPush/list.go?page=__id__'+condition)
		    });
		});
		
		function makecondition(){
			
			var lefttab = $('#lefttab').val();
			if(typeof lefttab !='undefined' && lefttab.length  > 0){
				condition = condition + "&lefttab="+lefttab;
			}
			var appId = $('#appId').val();
			if(appId!=null && appId.length  > 0){
				condition = condition + "&appId="+appId;
			}
			var keyword = $('#keyword').val();
			if(keyword!=null && keyword.length  > 0){
				condition = condition + "&keyword="+keyword;
			}
			var terminal = $('#terminal').val();
			if(terminal!=null && terminal.length  > 0){
				condition = condition + "&terminal="+terminal;
			}
			var planStatus = $('#planStatus').val();
			if(planStatus!=null &&planStatus.length  > 0){
				condition = condition + "&planStatus="+planStatus;
			}
		}
		
		function setPlanStatus(planStatus){
			
			var ids='';
			
			$('input[type=checkbox][name=autoid]').each(function(){
				
				if($(this).is(':checked')){
				
					ids+=$(this).val();
					ids+=',';
				}
			});
			
			if(ids.length>0){
				ids=ids.substring(0, ids.length-1);
			}else{
				alert("请选择至少一个push来进行操作");
				return;
			}
			
			$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/autoPush/setPlanStatus.go",
				   async: false,
				   cache: false,
				   data: {
						"ids":ids,	
						"planStatus":planStatus,
					    "timestamp":new Date().getTime()
				   },
				   dataType:"json",
				   success: function(msg){
					   
					   if(msg.result == 'success'){
						   
						   var spanDealArray = ids.split(',');
						   
						   for(var j = 0; j<spanDealArray.length; j++){
							   
							   if(planStatus==1000){
								   $('#planStatus_'+spanDealArray[j]).html('<font color="green">进行中</font>');
							   }
							   if(planStatus==1001){
								   $('#planStatus_'+spanDealArray[j]).html('<font color="red">停止</font>');
							   }
						   }
						   
						   alert("操作成功");
						   
					   }else if(msg.result == 'failure'){
						   alert("操作失败,"+msg.errorMessage);
					   }
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
					<h3>自动推送&gt;&gt;推送列表</h3>
					<#if cms_error_message??>
					<div class="mrdiv">
						<span class="errorMsg">${cms_error_message}</span>
					</div>
					</#if>
				    <div>
				    <br />
				   
				    <div class="mrdiv">
		      		<form action="/autoPush/list.go" method="post" id="usercms_list_form">
			      		 <table >
		        			<tr>
		        				<td class="right_table_tr_td">
			        				<table>
			        					<tr>
			        						<td>
			        						应用：
			        						</td>
			        						<td>
			        							<select name="appId" id="appId">
													<option value="">--全部--</option> 
													<option value="1"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==1>selected</#if> >当当读书Android/iphone</option>
													<option value="2"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==2>selected</#if> >当读小说Android</option>
													<option value="6"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==6>selected</#if> >当读小说Iphone版</option>
													<option value="3"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==3>selected</#if> >翻篇儿</option>
													<option value="4"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==4>selected</#if> >悦耳听书</option>
													<option value="5"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==5>selected</#if> >当当读书Ipad版</option>
												</select>
			        						</td>
			        					</tr>
			        				</table>
		        				</td>
								
							 	<td class="right_table_tr_td">
						 			<table>
										<tr>
											<td>客户端：</td>
											<td>
												<select name="terminal" id="terminal">
													<option value="">--全部--</option> 
													<option value="1"<#if terminal?? && terminal==1>selected</#if> >Android</option>
													<option value="2"<#if terminal?? && terminal==2>selected</#if> >IOS</option>
												</select>
											</td>
										</tr>
									</table>
								</td>
								
								<td class="right_table_tr_td">
						 			<table>
										<tr>
											<td>计划状态：</td>
											<td>
												<select name="planStatus" id="planStatus">
													<option value="">--全部--</option> 
													<option value="1000"<#if cloudPushPlan??&&cloudPushPlan.planStatus?? && cloudPushPlan.planStatus==1000>selected</#if> >进行中</option>
													<option value="1001"<#if cloudPushPlan??&&cloudPushPlan.planStatus?? && cloudPushPlan.planStatus==1001>selected</#if> >停止</option>
												</select>
											</td>
										</tr>
									</table>
								</td>
								
								<td class="right_table_tr_td">
									<button class="button1 blue"  type="submit" style="margin:0 0 0 0" >查询</button>
								</td>
								
							</tr>
						</table>
					</form>
					<br/><hr /><br/>
					<#if userSessionInfo?? && userSessionInfo.f['186']?? >
  						<button type="button" class="button1 blue" onclick="javascript:window.location.href='/autoPush/add.go'">新建</button>
  					</#if>
  					<#if userSessionInfo?? && userSessionInfo.f['187']?? >
  						<button type="button" class="button1 blue" onclick="setPlanStatus(1001)">停止</button>
  						<button type="button" class="button1 blue" onclick="setPlanStatus(1000)">启动</button> 
  					</#if>
				    <table class="table2">
		            	<tr>
		            		<th style="width:5%; height:28px;" ></th>
		                    <th style="width:5%; height:28px;" >ID</th>
		                    <th style="width:5%">应用</th>
		                    <th style="width:10%">计划名称</th>
		                    <th style="width:10%">开始时间</th>
		                    <th style="width:10%">结束时间</th>
		                    <th style="width:10%">设备范围</th>
		                    <th style="width:15%">用户范围</th>
		                    <th style="width:10%">后续行为</th>
		                    <th style="width:5%">推送数量</th>
		                    <th style="width:5%">打开数量</th>
		                    <th style="width:5%">计划状态</th>
		                    <th style="width:5%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if autoPushList??>
			    			<#list autoPushList as pushInList>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td><input type="checkbox" name="autoid" value="<#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if>" /></td>
					    			<td><#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if></td>
					    			<td>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==1>
					    					当当读书Android/iphone
					    				</#if>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==2>
					    					当读小说Android
					    				</#if>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==3>
					    					翻篇儿
					    				</#if>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==4>
					    					悦耳听书
					    				</#if>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==5>
					    					当当读书Ipad版
					    				</#if>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==6>
					    					当读小说Iphone版
					    				</#if>
					    			</td>
					    			<td><#if pushInList.planName??> ${pushInList.planName} </#if></td>
						      		<td><#if pushInList.startDate??>${pushInList.startDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if pushInList.endDate??>${pushInList.endDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
						      		<#if pushInList.deviceTypeAndroid?? && pushInList.deviceTypeAndroid==true>
						      			Android
						      		</#if>
						      		&nbsp;
						      		<#if pushInList.deviceTypeAndroid?? && pushInList.deviceTypeAndroid==true && pushInList.deviceTypeIos?? && pushInList.deviceTypeIos==true>
						      		|
						      		</#if>
						      		&nbsp;
						      		<#if pushInList.deviceTypeIos?? && pushInList.deviceTypeIos==true>
						      			IOS
						      		</#if>
						      		
						      		</td>
						      		<td>
							      		<#if pushInList.pushScope??&&pushInList.pushScope==1>满足触发条件的所有用户</#if>
							      		<#if pushInList.pushScope??&&pushInList.pushScope==2>无需触发条件的指定用户</#if>
						      		</td>
						      		<td>
						      			<#if pushInList.openType??&&pushInList.openType==2>打开应用</#if>
						      			<#if pushInList.openType??&&pushInList.openType==1>打开网页</#if>
						      			<#if pushInList.openType??&&pushInList.openType==0>传递参数</#if>
						      		</td>
						      		
						      		<td>
						      			<#if pushInList.pushedNumber??>${pushInList.pushedNumber}</#if>
						      		</td>
						      		
						      		<td>
						      			<#if pushInList.openedNumber??>${pushInList.openedNumber}</#if>
						      		</td>
						      		
						      		<td>
						      			<#if pushInList.planStatus??&&pushInList.planStatus==1000><span id="planStatus_<#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if>"><font color="green">进行中</font></span></#if>
						      			<#if pushInList.planStatus??&&pushInList.planStatus==1001><span id="planStatus_<#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if>"><font color="red">停止</font></span></#if>
						      		</td>
						      		
						      		<td>
						      		<#if userSessionInfo?? && userSessionInfo.f['188']?? >
					      				<a href="/autoPush/edit.go?cloudPushPlanId=<#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if>">修改</a>
						      		</#if>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
		    </div>
		    <div>
			    <div class="pagination rightPager"></div>
			    <div class="leftPager">总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
