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
				link_to: encodeURI('/manualPush/list.go?page=__id__'+condition)
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
		
		function deleteManualPlan(trId, ids){
			
			$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/manualPush/delete.go",
				   async: false,
				   cache: false,
				   data: {
						"ids":ids,				
					    "timestamp":new Date().getTime()
				   },
				   dataType:"json",
				   success: function(msg){
					   
					   if(msg.result == 'success'){
						   alert("删除成功");
							$('#'+trId).hide();						   
					   }else if(msg.result == 'failure'){
						   alert("删除失败,"+msg.errorMessage);
					   }
				   }
				});
			
		} 
		
		function copyManualPlan(planId){
			window.location.href="${rc.contextPath}/manualPush/copy.go?cloudPushPlanId="+planId;
		}
		
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>手动推送&gt;&gt;推送列表</h3>
					<#if cms_error_message??>
					<div class="mrdiv">
						<span class="errorMsg">${cms_error_message}</span>
					</div>
					</#if>
				    <div>
				    <br />
				   
				    <div class="mrdiv">
		      		<form action="/manualPush/list.go" method="post" id="usercms_list_form">
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
													<option value="6"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==6>selected</#if>>当读小说Iphone版</option>
													<option value="3"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==3>selected</#if> >翻篇儿</option>
													<option value="4"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==4>selected</#if> >悦耳听书</option>
													<option value="5"<#if cloudPushPlan??&&cloudPushPlan.appId?? && cloudPushPlan.appId==5>selected</#if>>当当读书Ipad版</option>
												</select>
			        						</td>
			        					</tr>
			        				</table>
		        				</td>
								<td class="right_table_tr_td" style="width:260px">
										<table>
											<tr>
												<td>关键字：</td>
												<td><input type="text" name="keyword" id="keyword" value="<#if keyword??>${keyword}</#if>"></td>
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
											<td>发送状态：</td>
											<td>
												<select name="planStatus" id="planStatus">
													<option value="">--全部--</option> 
													<option value="0"<#if cloudPushPlan??&&cloudPushPlan.planStatus?? && cloudPushPlan.planStatus==0>selected</#if> >待发送</option>
													<option value="1"<#if cloudPushPlan??&&cloudPushPlan.planStatus?? && cloudPushPlan.planStatus==1>selected</#if> >已发送</option>
												</select>
											</td>
										</tr>
									</table>
								</td>
								
								<td class="right_table_tr_td">
									<button class="button1 blue"  type="submit" style="margin:0 0 0 0" >查询</button>
								</td>
								<td class="right_table_tr_td">
								<#if userSessionInfo?? && userSessionInfo.f['182']?? >
									 <button type="button" class="button1 blue" onclick="javascript:window.location.href='/manualPush/add.go'">新建</button>
								</#if>
								</td>
								
							</tr>
						</table>
					</form>
				    
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >ID</th>
		                    <th style="width:5%">应用</th>
		                    <th style="width:10%">发送时间</th>
		                    <th style="width:20%">消息内容</th>
		                    <th style="width:10%">设备范围</th>
		                    <th style="width:10%">用户范围</th>
		                    <th style="width:10%">后续行为</th>
		                    <th style="width:10%">推送数量</th>
		                    <th style="width:10%">打开数量</th>
		                    <th style="width:5%">发送状态</th>
		                    <th style="width:5%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if manualPushList??>
			    			<#list manualPushList as pushInList>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if></td>
					    			<td>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==1>
					    					当当读书Andorid/iphone
					    				</#if>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==2>
					    					当读小说Android
					    				</#if>
					    				<#if pushInList??&&pushInList.appId?? && pushInList.appId==6>
					    					当读小说Iphone版
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
					    			</td>
						      		<td><#if pushInList.sendTime??>${pushInList.sendTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if pushInList.messageDescription??>${pushInList.messageDescription}</#if></td>
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
							      		<#if pushInList.pushScope??&&pushInList.pushScope==1>全部用户</#if>
							      		<#if pushInList.pushScope??&&pushInList.pushScope==2>指定用户</#if>
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
						      			<#if pushInList.planStatus??&&pushInList.planStatus==0>待发送</#if>
						      			<#if pushInList.planStatus??&&pushInList.planStatus==1>已发送</#if>
						      			<#if pushInList.planStatus??&&pushInList.planStatus==2>发送错误</#if>
						      		</td>
						      		
						      		<td>
						      		<#if userSessionInfo?? && userSessionInfo.f['183']?? >
					      				<a href="/manualPush/edit.go?cloudPushPlanId=<#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if>">编辑</a>
									</#if>
									<#if userSessionInfo?? && userSessionInfo.f['184']?? >
										<a href="javascript:if(confirm('确认复制此推送?')){ copyManualPlan('<#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if>')}" >复制</a>
					      			</#if>
					      			<#if userSessionInfo?? && userSessionInfo.f['185']?? >
					      				<a href="javascript:if(confirm('是否将此推送删除?')){ deleteManualPlan('row_${i}','<#if pushInList.cloudPushPlanId??>${pushInList.cloudPushPlanId?c}</#if>')}" >删除</a>
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
