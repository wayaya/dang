<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	
	<link rel="stylesheet" href="${rc.contextPath}/style/functionality/functionality.css" type="text/css">
	<link rel="stylesheet" href="${rc.contextPath}/script/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/script/zTree/common.js"></script>
	<script type="text/javascript">
		
		var treeContainer = "showUsercmsTree";
		var setting = {
			view: {
				selectedMulti: false,
				dblClickExpand: true
			},
			edit: {
				enable: false
			},
			check:{
				enable:false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				
			}
		};
		
		$(document).ready(function(){
			
			var zNodes =[];
			<#if usercms??><#if usercms.previledge??><#if usercms.previledge!=0>
			$.ajax({
				   type: "POST",
				   url: "${rc.contextPath}/functionality/showUsercmsTree.go?usercmsId=<#if usercms??><#if usercms.usercmsId??>${usercms.usercmsId?c}</#if></#if>",
				   async: false,
				   cache: false,
				   data: "timestamp="+new Date().getTime(),
				   dataType:"json",
				   success: function(msg){
					   zNodes = msg;
				   }
				});
			
			$.fn.zTree.init($("#"+treeContainer), setting, zNodes);
			</#if></#if></#if>
			
			$("#editButton, #editButton1").click(function(e){
				window.location.href="${rc.contextPath}/usercms/edit.go?usercmsId=<#if usercms??><#if usercms.usercmsId??>${usercms.usercmsId?c}</#if></#if>";
			});
		});
		
	</script>
	
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>后台用户管理&gt;&gt;显示用户详细信息</h3>
					<div class="mrdiv" style="display:none">
			      		 <table>
		        			<tr>
								<td style="padding-left:50px;"><span id="message"></span></td>
							</tr>
						</table>
				    </div>
				    <div>
				    	<button class="button1" id="editButton">编辑用户</button><button class="button1" onclick="window.location.href='${rc.contextPath}/usercms/list.go'">返回列表</button>
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdleft">用户ID：</td>
					    			<td class="tdleft">
					    				<#if usercms?? && usercms.loginName??>${usercms.loginName}</#if>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="loginNameInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td class="tdleft">真实姓名：</td>
					    			<td class="tdleft">
					    				<#if usercms?? && usercms.name??>${usercms.name}</#if>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="nameInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td class="tdleft">Email：</td>
					    			<td class="tdleft">
					    				<#if usercms?? && usercms.email??>${usercms.email}</#if>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="emailInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td class="tdleft">部门：</td>
					    			<td class="tdleft">
					    				<#if usercms?? && usercms.department??>${usercms.department}</#if>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="nameInfo"></td>
						      	</tr>
						      	
						      	<tr>
					    			<td class="tdleft">是否管理员 ：</td>
					    			<td class="tdleft">
					    				 <#if usercms??><#if usercms.previledge??><#if usercms.previledge==0>是<#else>否</#if></#if></#if>  
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="previledgeInfo"></td>
						      	</tr>
						      	
						      	<tr>
								  <td colspan=2>
								    <hr>
								  </td>
								</tr>
						      	
						      	<tr id="grandRoles" <#if usercms?? && usercms.previledge?? && usercms.previledge==0>style="display:none"</#if> >
					    			<td class="tdleft">角色：</td>
					    			<td class="tdleft">
					    				<table>
					    				 <#if existedRoles??&&existedRoles?size gt 0>
									          <#list existedRoles as existedRole>
								    			<tr>
								    				<td class="tdleft" style="height:25px">
								    				<span><label>${existedRole.roleName}</label></span>
									    			</td>
						    					</tr>
					    					 </#list>
										 </#if>
							    		</table>
					    			</td>
					    			<td class="tdleft" style="width:1100px" id="previledgeInfo"></td>
						      	</tr>
						      	
				            </table>
		            </div>
			    </div>
			    <#if usercms??><#if usercms.previledge??><#if usercms.previledge!=0>
			    <div class="content_wrap">
			    
			    	<ul><li>用户权限详情：</li></ul>
					<div class="content_wrap">
						<div class="zTreeDemoBackground left">
							<ul id="showUsercmsTree" class="ztree"></ul>
						</div>
					</div>
			    </div>
			    </#if></#if></#if>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
