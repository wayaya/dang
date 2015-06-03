<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>携手金融--后台登陆</title>
	<#include "../common/common-css.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/label/list.go?page=__id__')
		    });
	   	});
	   	
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<#include "../common/top.ftl">
		<div class="main clear"><!--main开始-->
			<#include "../common/left.ftl">
			<div class="right">
				<div class="m-r">
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/manage/manage_obtainAllMasterList.shtml" method="post" id="master_list_form">
			      		 <table >
		        			<tr>
								<td>账号：<input type="text" name="masterDTO.account" id="account" value="<#if masterDTO??><#if masterDTO.account??>${masterDTO.account}</#if></#if>"></td>
							 	<td>姓名：<input type="text" name="masterDTO.name" id="name" value="<#if masterDTO??><#if masterDTO.name??>${masterDTO.name}</#if></#if>"></td>
								<td><button  onclick="return searchMaster()">查询</button>
								<td width="540px;">&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:15%; height:28px;" >序号</th>
		                    <th style="width:15%">姓名</th>
		                    <th >操作</th>
	               	    </tr>
	               	 <#if pageFinder.data??>
			    			<#list pageFinder.data as test>
					    		<tr>
					    			<td><#if test.id??>${test.id?c}</#if></td>
						      		<td><#if test.name??>${test.name}</#if></td>
						      		<td>
					      				<a href="">更新</a>
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
  </body>
</html>
