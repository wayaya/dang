<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>内容列表</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right" style="width:99%;">
			<table>
			<tr>
			<td style="width:100%;">
				<div class="m-r">
					<div class="mrdiv">
		      		<form action="/block/special/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<!-- 只有存在此参数时,才可以添加 -->
		        			<#if RequestParameters.stTypeId ??>
		      				<input type="hidden" id="stTypeId" name="stTypeId" value='${RequestParameters["stTypeId"]}'>
		      				<input type="hidden" id="channelType" name="channelType" value='${RequestParameters["channelType"]}'>
 		        			<tr>
		        				<td class="right_table_tr_td">
		        				
		        				 <#if userSessionInfo?? && userSessionInfo.f['123']?? >
		        				<a href='/block/special/toAdd.go?stTypeId=${RequestParameters["stTypeId"]}&channelType=${RequestParameters["channelType"]}'><img src="/images/add_special.jpg"/></a>
		        				</#if>
		        				&nbsp;&nbsp;<image src="/images/return.jpg" onclick="javascript:history.go(-1)" /></td>
						     </tr>
						   	</#if>	
						   	<tr>	
								<td class="right_table_tr_td">名称：<input type="text" name="name" id="name" value="<#if specialTopic??><#if specialTopic.name??>${specialTopic.name}</#if></#if>"></td>
								<td class="right_table_tr_td"><button  onclick="return searchMaster()">查询</button>
								
								 <#if userSessionInfo?? && userSessionInfo.f['124']?? >
								<button type="button" onclick="searchlist()">刷新缓存</button>
								</#if>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    	<table class="table2">
			            	<tr>
			            		<th width="50px"><input type="checkbox" id='chooseAll' name='chooseAll'>全选</input></th>
			            		<th style="width:3%">序号</th>
			            		<th style="width:10%">名称</th>
			                    <th style="width:10%">频道</th>
			                    <th style="width:10%">关联栏目名称</th>
			                    <th style="width:10%">主页显示</th>
			                    <th style="width:5%">创建人</th>
			                    <th style="width:5%">修改人</th>
			                    <th style="width:10%">创建时间</th>
			                    <th style="width:5%">状态</th>
			                    <th style="width:5%">排序值</th>
			                    <th >操作</th>
		               	    </tr>
	               	 <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as one>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${one.stId?c}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${one.stId?c}">
			    				</#if>
			    				<td><input type="checkbox" id="stId" name="stId" value='${one.stId?c}'></input></td>
			    				<td>${one.stId?c}</td>
				    			<td><#if one.name??>${one.name}</#if></td>
					      		<td><#if one.deviceType??>${one.deviceType}</#if></td>
					      		<td><#if one.column??>${one.column.name}</#if></td>
					      		<td><#if one.showHomePage??><#if one.showHomePage==1>是<#else>否</#if></#if></td>
					      		<td><#if one.creator??>${one.creator}</#if></td>
					      		<td><#if one.modifier??>${one.modifier}</#if></td>
					      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
					      		<td><#if one.status??><#if one.status == 1>有效</#if><#if one.status == 0>无效</#if></#if></td>
					      		<td><#if one.sort??>${one.sort}</#if></td>
					      		<td>
					      		
					      		<#if userSessionInfo?? && userSessionInfo.f['135']?? >
				      				<a href="/block/special/goUpdate.go?stId=${one.stId?c}">更新</a>
				      			</#if>
				      			<#if userSessionInfo?? && userSessionInfo.f['136']?? >
				      				<a onclick="return deleteTip();"href="/block/special/delete.go?stId=${one.stId?c}&stTypeId=${one.stTypeId?c}&channelType=${one.channelType}">删除</a>
					      		</#if>
					      		</td>
					      	</tr>
				      		</#list>
				      	</#if>
		            	</table>
		            </div>
			    </div>
			    <div class="pagination rightPager"></div>
			    </td>
			    </tr>
			    </table>
		    </div>
		</div>
	</div>
	<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<#include "../../common/common-js.ftl">
	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		function deleteTip(){
			 var isdelete =  window.confirm("您确认删除该专题吗?");
	  		 if(isdelete){
	  			  return true;
	  		 }
	  		 return false;
		}
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/block/special/list.go?page=__id__&'+$("#query").serialize())
		    });
		    
		    
	   	});
	   	function searchlist(){
	   		var ids = new Array();  
     		var temp = "";       		
     		var a = document.getElementsByName("stId");  
     		for ( var i = 0; i < a.length; i++) {  
     			if (a[i].checked) {  
    				temp = a[i].value;  
    				ids.push(temp);
     			}
     		}
     		
     		if(ids.length==0){
     			alert("请选择专题！");
     			return false;
     		}
	   		$.ajax({
				   type: "POST",url: "${rc.contextPath}/cache/specialTopic/clearcache.go",
				   async: false,
				   cache: false,
				   data: {"stId":ids.join(',')},
				   dataType:"json",
				   success: function(msg){
					   if(msg.result == 'success'){
						  alert("更新成功！");
						  for(var ele in ids){//遍历数组
							  $('#row_'+ids[ele]).hide();
						  } 
					   }else{
						   alert("更新失败!");
					   }
				   }
				});
	   	}
	   	function searchMaster(){
	   		$('#query').submit();
	   	}
	   	function getReferenceForm(elm) {
			while(elm && elm.tagName != 'BODY') {
				if(elm.tagName == 'FORM') return elm;
				elm = elm.parentNode;
			}
			return null;
		}
		
		function delMore(){
			var ids =[]; 
			$('input[name="blockId"]:checked').each(function(){ 
				ids.push($(this).val()); 
			});
			if(ids.length==0) {
				alert( '你还没有选择任何内容！');
				return;
			}
			
			$.ajax({
				   type: "POST",url: "${rc.contextPath}/block/deleteMore.go",
				   async: false,
				   cache: false,
				   data: {"ids":ids},
				   dataType:"json",
				   success: function(msg){
					   if(msg.result == '0'){
						  alert("删除成功");
						  for(var ele in ids){//遍历数组
							  $('#row_'+ids[ele]).hide();
						  } 
					   }else{
						   alert("删除失败!");
					   }
				   }
				});
		}
		
	   	$('#chooseAll').click(function(){
			if(this.checked){
				 $('[name=stId]:checkbox').attr("checked", true);
			}else{
				 $('[name=stId]:checkbox').attr("checked", false);
			}
		});
	   	
	</script>
	<#include "../../common/common-js.ftl">
  </body>
</html>
