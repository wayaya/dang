<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>公告类型列表</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>公告管理&gt;&gt;公告类开管理</h3>
				    <div>
				    <div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/notice/type/list.go" method="post" id="query" name="query" onSubmit="return checkForm();">
			      		 <table >
		        			<tr>
								<td>类型名称：<input type="text" name="name" value="<#if RequestParameters.name??>${RequestParameters["name"]}</#if>">
								<button  type="submit" >查询</button>
								
								<#if userSessionInfo?? && userSessionInfo.f['110']?? > 
								<button type="button" id="addBtn"  >添加公告类型</button>
								</#if>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    
				    <form id="listform" name="listform" method="post">
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>类型名称</th>
		                    <th>类型值</th>
		                    <th>创建人</th>
		                    <th>创建时间</th>
		                    <th>操作</th>
	               	    </tr>
	               	    
	               	    <#assign i = 0> 
	               	    <#if (pageFinder?size>0)>
	               	     <#list pageFinder.data as noticeType> 
	               	     <#assign i = i+1>
	               	      <#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				    
					    			<td>${i}</td>
						      		<td><#if noticeType.name??>${noticeType.name}</#if></td>
						      		<td><#if noticeType.type??>${noticeType.type}</#if></td>
						      		<td><#if noticeType.creator??>${noticeType.creator}</#if></td>
						      		<td><#if noticeType.createDate??>${noticeType.createDate?substring(0,19)}</#if></td>
						      		<td>
						      		<a onclick="javascript:delTips();" href="/notice/type/delete.go?noticeTypeId=${noticeType.noticeTypeId}">删除</a>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </form>
		           	</div>
				</div>
				<div class="pagination rightPager"></div>
			</div>
		</div>
	</div>
  </body>
  <script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script>
  <#include "../../common/common-js.ftl">
<script type="text/javascript">
$(document).ready(function(){
	$('#addBtn').click(function(){
		var api = $.dialog({title:'添加公告类型',top:'10%',content:"url:/notice/type/add.go",
			icon:'succeed',width:400,height:150,fixed:false,lock:true});
	});
});
	
  	function delTips(){
  		return window.confirm("您确认删除该公告类型吗?");
  	}
	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize?c},
			current_page: ${pageFinder.pageNo- 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/notice/type/list.go?pageIndex=__id__&'+$("#query").serialize())
	    });
   	});
  	</script>
</html>
