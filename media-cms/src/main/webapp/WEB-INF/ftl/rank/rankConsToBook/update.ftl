<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>壕赏排行榜&gt;&gt;修改榜单</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
										<#if successFlag??>
					    					<#if successFlag == 0>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/rankConsToBook/list.go?lefttab=ul7" style="height: 20px; font-size: 20px;">修改成功,点击返回</a>
					    					</#if>
					    				</#if>
									</td>
								</tr>
							</table>
					    </div>
					<#else>    
				    
				 <div>
				    <table class="table2">
				       <form action="/rankConsToBook/update.go" name="rankForm" metdod="post">
				        <tr><td style="width:15%" class="tdright">榜单标识</td><td class="tdleft"><input name="code" value='${rank.code}'></td></tr>
		            	<tr><td class="tdright">昵称</td><td class="tdleft"><input name="username" value='${rank.username}'></td></tr>
		            	<tr><td class="tdright">头像</td><td class="tdleft"><input name="userImgUrl" value='${rank.userImgUrl}'></td></tr>
		            	<tr><td class="tdright">用户id</td><td class="tdleft"><input name="custId" value='${rank.custId?c}'></td></tr>
		            	<tr><td class="tdright">作品ID</td><td class="tdleft"><input name="mediaId" value='${rank.mediaId?c}'></td></tr>
		            	<tr><td class="tdright">作品名</td><td class="tdleft"><input name="mediaName" value='${rank.mediaName}'></td></tr>
		            	<tr><td class="tdright">作品封面地址</td><td class="tdleft"><input name="mediaUrl" value='${rank.mediaUrl}'></td></tr>
		            	<tr><td class="tdright">显示金币</td><td class="tdleft"><input name="showCons" value='${rank.showCons?c}'></td></tr>
		            	<tr><td class="tdright">频道类型 </td>
		            	  <td class="tdleft">
		            	    <select id="channel" name="channel">
			    					<option value="NP" <#if rank??><#if rank.channel??><#if rank.channel=="NP">selected="selected"</#if></#if></#if>>男频</option>
			    					<option value="VP" <#if rank??><#if rank.channel??><#if rank.channel=="VP">selected="selected"</#if></#if></#if>>女频</option>
						    </select>
						   </td> 
		            	</tr>
		            	<tr><td class="tdright">榜单类型 1:日;2:周;3:月;4:年</td>
		            	  <td class="tdleft">
		            	    <select id="type" name="type">
			    					<option value=""  <#if rank??><#if rank.type??><#if rank.type?c=="">selected="selected"</#if></#if></#if>>请选择</option>
			    					<option value="1" <#if rank??><#if rank.type??><#if rank.type?c=="1">selected="selected"</#if></#if></#if>>日</option>
			    					<option value="2" <#if rank??><#if rank.type??><#if rank.type?c=="2">selected="selected"</#if></#if></#if>>周</option>
			    					<option value="3" <#if rank??><#if rank.type??><#if rank.type?c=="3">selected="selected"</#if></#if></#if>>月</option>
			    					<option value="4" <#if rank??><#if rank.type??><#if rank.type?c=="4">selected="selected"</#if></#if></#if>>总</option>
						    </select>
						   </td> 
		            	</tr>
		            	    <input type="hidden" name="mediaEbookConsRanklistId" value=${rank.mediaEbookConsRanklistId?c}>
	               	    </tr>
	               	    <tr><td colspan=2><button>提交</button></td></tr>
	               	    </form>
		            </table>
		            </div>
		           </#if> 
			    </div>
			    <div class="pagination rightPager"></div>
		    </div>
		</div>
	</div>
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	   	
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	   	
	</script>
  </body>
</html>
