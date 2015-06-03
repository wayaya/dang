<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
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
				link_to: encodeURI('/usercms/list.go?page=__id__'+condition)
		    });
		});
		function makecondition(){
			var lefttab = $('#lefttab').val();
			if(typeof lefttab !='undefined' && lefttab.length  > 0){
				condition = condition + "&lefttab="+lefttab;
			}
			var usercmsId = $('#usercmsId').val();
			if(usercmsId!=null && usercmsId.length  > 0){
				condition = condition + "&usercmsId="+usercmsId;
			}
			var name = $('#name').val();
			if(name!=null && name.length  > 0){
				condition = condition + "&name="+name;
			}
			var roleName = $('#roleName').val();
			if(roleName!=null && roleName.length  > 0){
				condition = condition + "&roleName="+roleName;
			}
			var onlineStatus = $('#onlineStatus').val();
			if(onlineStatus!=null &&onlineStatus.length  > 0){
				condition = condition + "&onlineStatus="+roleName;
			}
			var status = $('#status').val();
			if(status!=null && status.length  > 0){
				condition = condition + "&status="+status;
			}
			var previledge = $('#previledge').val();
			if(previledge!=null && previledge.length  > 0){
				condition = condition + "&previledge="+previledge;
			}
		}
		
	   	function searchusercmsList(){
	   		$('#usercms_list_form').submit();
	   	}
	   	
	 	function forbidUsercms(usercmsId, tdId, linkId){
	   		$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/usercms/forbid.go",
				   async: false,
				   cache: false,
				   data: {
						"usercmsId":usercmsId,				
					    "timestamp":new Date().getTime()
				   },
				   dataType:"json",
				   success: function(msg){
					   
					   if(msg.result == 'success'){
						   alert("操作成功");
						   
						   if(msg.errorMessage.indexOf('已禁用')>=0){
							   
							   $('#'+tdId).html('<font color="red">'+msg.errorMessage+'</font>');	
							   $('#'+linkId).text("启用");
						   }else{

							   $('#'+tdId).html(msg.errorMessage);	
							   $('#'+linkId).text("禁用");
						   }
						   
						   
					   }else if(msg.result == 'failure'){
						   alert("操作失败,"+msg.errorMessage);
					   }
				   }
				});
	   		
	   	}
	   	
	   	function deleteUsercms(usercmsId, trId){
	   		$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/usercms/delete.go",
				   async: false,
				   cache: false,
				   data: {
						"ids":usercmsId,				
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
	   	
	   	function resetPassword(usercmsId, currentStatus, statusDivId ){
	   		$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/usercms/resetPassword.go",
				   async: false,
				   cache: false,
				   data: {
						"usercmsId":usercmsId,		
						"fromUserList":'true',
					    "timestamp":new Date().getTime()
				   },
				   dataType:"json",
				   success: function(msg){
					   
					   if(msg.result == 'success'){
						   
						   if(currentStatus=='1'){
							   $('#'+statusDivId).html('已启用（没有修改密码）');
						   }else if(currentStatus=='0'){
							   $('#'+statusDivId).html('<font color="red">已禁用（没有修改密码）</font>');
						   }
						   
						   alert("重置密码成功");
					   }else if(msg.result == 'failure'){
						   alert("操作失败:"+msg.errorMessage);
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
					<h3>用户管理&gt;&gt;用户列表</h3>
					<div class="mrdiv">
					<#if userSessionInfo?? && userSessionInfo.f['29']?? >
					<button class="button1 blue" style="width:160px" onclick="javascript:window.location.href='/usercms/create.go'">添加后台新用户</button>
					</#if>
		      		<form action="/usercms/list.go" method="post" id="usercms_list_form">
			      		 <table >
		        			<tr>
		        				<td class="right_table_tr_td">
			        				<table>
			        					<tr>
			        						<td>
			        						用户ID：
			        						</td>
			        						<td>
			        							<input type="text" name="loginName" id="loginName" value="<#if usercms??><#if usercms.loginName??>${usercms.loginName}</#if></#if>">
			        						</td>
			        					</tr>
			        				</table>
		        				</td>
								<td class="right_table_tr_td" style="width:260px">
										<table>
											<tr>
												<td>真实姓名：</td>
												<td><input type="text" name="name" id="name"
													value="<#if usercms??><#if usercms.name??>${usercms.name}</#if></#if>"></td>
											</tr>
										</table>
								</td>
							 	<td class="right_table_tr_td">
						 			<table>
										<tr>
											<td>角色：</td>
											<td><select name="roleId" id="roleId">
													<option value="">全部角色</option> <#list allRoles as
													roleInList>
													<option value="${roleInList.roleId}"<#if roleId?? && roleId==roleInList.roleId>selected</#if> >${roleInList.name}</option> </#list>
											</select></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
							 	<td class="right_table_tr_td">
						 			<table>
										<tr>
											<td>上线/下线：</td>
											<td><select id="onlineStatus" name="onlineStatus">
													<option value="">全部</option>
													<option value="1"<#if usercms??><#if usercms.onlineStatus?? && usercms.onlineStatus==1> selected="selected"</#if></#if>>在线</option>
													<option value="0"<#if usercms??><#if usercms.onlineStatus?? && usercms.onlineStatus==0> selected="selected"</#if></#if>>下线</option>
											</select></td>
										</tr>
									</table>
								</td>
							 	
							 	<td class="right_table_tr_td" style="width:260px">
						 			<table>
										<tr>
											<td>启用/禁用：</td>
											<td><select id="status" name="status">
													<option value="">全部</option>
													<option value="1"<#if usercms??><#if usercms.status?? && usercms.status==1> selected="selected"</#if></#if>>正常</option>
													<option value="2"<#if usercms??><#if usercms.status?? && usercms.status==2> selected="selected"</#if></#if>>已启用（没有修改密码）</option>
													<option value="0"<#if usercms??><#if usercms.status?? && usercms.status==0> selected="selected"</#if></#if>>已禁用</option>
													<option value="3"<#if usercms??><#if usercms.status?? && usercms.status==0> selected="selected"</#if></#if>>已禁用（没有修改密码）</option>
											</select></td>
										</tr>
									</table>
								</td>
							 	
							 	<td class="right_table_tr_td">
							 		<table>
										<tr>
											<td>管理员：</td>
											<td>
										 		<select id="previledge" name="previledge" >
									 				<option value="">全部</option>
							    					<option value="1" <#if usercms??><#if usercms.previledge?? && usercms.previledge==1> selected="selected"</#if></#if>>非管理员</option>
							    					<option value="0" <#if usercms??><#if usercms.previledge?? && usercms.previledge==0> selected="selected"</#if></#if>>管理员</option>
							    				</select>
						    				</td>
					    				</tr>
				    				</table>
				    			</td>
								<td class="right_table_tr_td"><button class="button1 blue" style="margin:0 0 0 0" onclick="return searchusercmsList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >编号</th>
		                    <th style="width:7%">用户ID</th>
		                    <th style="width:7%">真实姓名</th>
		                    <th style="width:10%">email</th>
		                    <th style="width:7%">部门</th>
		                    <th style="width:7%">启用/禁用</th>
		                    <th style="width:5%">是否管理员</th>
		                    <th style="width:5%">上线/下线</th>
		                    <th style="width:7%">角色</th>
		                    <th style="width:5%">创建人ID</th>
		                    <th style="width:10%">创建时间</th>
		                    <th style="width:5%">修改人ID</th>
		                    <th style="width:15%">最后修改时间</th>
		                    <th style="width:10%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as usercmsInList>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if usercmsInList.usercmsId??>${usercmsInList.usercmsId?c}</#if></td>
					    			<td><#if usercmsInList.loginName??>${usercmsInList.loginName}</#if></td>
					    			<td><#if usercmsInList.name??>${usercmsInList.name}</#if></td>
					    			<td><#if usercmsInList.email??>${usercmsInList.email}</#if></td>
					    			<td><#if usercmsInList.department??>${usercmsInList.department}</#if></td>
					    			<td id="status_${i}">
					    				<#if usercmsInList.status??>
							    			<#if usercmsInList.status==2>
							    				已启用（没有修改密码）
							    			</#if>
							    			<#if usercmsInList.status==1>
							    				正常
							    			</#if>
							    			<#if usercmsInList.status==0>
							    				<font color="red">
								    				已禁用
								    				</font>
							    			</#if>
							    			<#if usercmsInList.status==3>
							    			<font color="red">
							    				已禁用（没有修改密码）
							    				</font>
							    			</#if>
					    				</#if>
					    			</td>
					    			<td>
					    				<#if usercmsInList.previledge??>
					    					<#if usercmsInList.previledge==0>
							    				是
							    			</#if>
							    			<#if usercmsInList.previledge==1>
							    				否
							    			</#if>
					    				</#if>
					    			</td>
					    			<td>
					    				<#if usercmsInList.onlineStatus??>
					    					<#if usercmsInList.onlineStatus==0>
							    				已下线
							    			</#if>
							    			<#if usercmsInList.onlineStatus==1>
							    				在线
							    			</#if>
										</#if>
					    			</td>
					    			<td>
					    				<!-- 管理员不显示角色 -->
					    				<#if usercmsInList.previledge?? && usercmsInList.previledge==1>
							    			<#if userRoleMapping?? && usercmsInList.usercmsId??>
							    			    <#list userRoleMapping?keys as testKey>  
														<#if testKey==usercmsInList.usercmsId?string>
															 <#assign item = userRoleMapping[testKey]>
															 <#list item as roleOfThisUser>
									    				    		<span>${roleOfThisUser.roleName }</span> </br>
															 </#list>  
														</#if>								            
											    </#list>  
											</#if>			
										</#if>
					    			</td>
					    			<td><#if usercmsInList.creator??>${usercmsInList.creator}</#if></td>
					    			<td><#if usercmsInList.creationDate??>${usercmsInList.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
					    			<td><#if usercmsInList.modifier??>${usercmsInList.modifier}<#else>无</#if></td>
					    			<td><#if usercmsInList.lastChangedDate??>${usercmsInList.lastChangedDate?string("yyyy-MM-dd HH:mm:ss")}<#else>无</#if></td>
						      		<td>
						      			<#if userSessionInfo?? && userSessionInfo.f['33']?? >
							      			<a id="forbid_a_${i}" href="javascript:void(0)" onclick="if(confirm('确认此操作？')){forbidUsercms('<#if usercmsInList??><#if usercmsInList.usercmsId??>${usercmsInList.usercmsId?c}</#if></#if>', 'status_${i}','forbid_a_${i}');}" >
							      			<#if usercmsInList.status??>
								    			<#if usercmsInList.status==0 || usercmsInList.status==3>
								    				启用
								    				<#else>
								    				禁用
								    			</#if>
								      		</#if>
							      			</a>
						      			</#if>
						      			<#if userSessionInfo?? && userSessionInfo.f['34']?? >
						      			<a href="/usercms/show.go?usercmsId=<#if usercmsInList.usercmsId??>${usercmsInList.usercmsId?c}</#if>">显示详情</a>
						      			</#if>
						      			<#if userSessionInfo?? && userSessionInfo.f['30']?? >
					      				<a href="/usercms/edit.go?usercmsId=<#if usercmsInList.usercmsId??>${usercmsInList.usercmsId?c}</#if>">编辑</a>
					      				</#if>
					      				<a href="javascript:void(0)" onclick="if(confirm('确认删除此用户？')){deleteUsercms('<#if usercmsInList??><#if usercmsInList.usercmsId??>${usercmsInList.usercmsId?c}</#if></#if>','row_${i}');}" >删除</a>
					      				<#if userSessionInfo?? && userSessionInfo.userInfo?? && userSessionInfo.userInfo.previledge??>
						    					<#if userSessionInfo.userInfo.previledge==0>
						      						<a href="javascript:void(0)" onclick="if(confirm('确认重置此用户的密码？')){resetPassword('<#if usercmsInList??><#if usercmsInList.usercmsId??>${usercmsInList.usercmsId?c}</#if></#if>', '<#if usercmsInList.status??>${usercmsInList.status}</#if>','status_${i}');}" >重置密码</a>
						      					</#if>
					      				</#if>
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
