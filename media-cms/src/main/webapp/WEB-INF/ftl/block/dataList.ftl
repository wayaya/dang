<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<title>内容列表</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
			<table>
			<tr>
			<td style="width:100%;">
				<div class="m-r">
					<div class="mrdiv">
		      		<form action="/block/list.go" method="post" id="list_form" >
			      		 <table >
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
		        			<#if one.groupId??>
		        				<td>
		        				<#if userSessionInfo?? && userSessionInfo.f['140']?? >
		        					<image src="/images/delete.png" onclick="javascript:delMore()" />
		        				</#if>
		        				
		        				<#if userSessionInfo?? && userSessionInfo.f['142']?? >
		        					<a href="/block/toAdd.go?groupId=${one.groupId?c}" ><image src="/images/append.png"/></a>
		        					<a href="/block/toText.go?groupId=${one.groupId?c}" ><image src="/images/add_block.jpg"/></a>
		        				</#if>
		        				</td>
		        				<#else></#if>
		        					<input type="hidden" name="groupId" id="groupId" value="<#if one.groupId??>${one.groupId?c}</#if>">
								<td class="right_table_tr_td">名称：<input type="text" name="name" id="name" value="<#if one??><#if one.name??>${one.name}</#if></#if>"></td>
								<td class="right_table_tr_td">标识：<input type="text" name="code" id="code" value="<#if one??><#if one.code??>${one.code}</#if></#if>"></td>
								<td class="right_table_tr_td"><button  onclick="return searchMaster()">查询</button>
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
			                    <th style="width:10%">推荐位标识</th>
			                    <th style="width:10%">关联栏目标识</th>
			                    <th style="width:15%">创建时间</th>
			                    <th style="width:5%">状态</th>
			                    <th >操作</th>
		               	    </tr>
	               	 <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as one>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${one.mediaBlockId?c}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${one.mediaBlockId?c}">
			    				</#if>
			    				<td><input type="checkbox" id="blockId" name="blockId" value='${one.mediaBlockId?c}'></input></td>
			    				<td>${one.mediaBlockId?c}</td>
				    			<td><#if one.name??>${one.name}</#if></td>
					      		<td><#if one.code??>${one.code}</#if></td>
					      		<td><#if one.relationColumnCode??>${one.relationColumnCode}</#if></td>
					      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
					      		<td><#if one.status??><#if one.status == 1>有效</#if><#if one.status == 0>无效</#if></#if></td>
					      		<td>
					      		
					      		<#if userSessionInfo?? && userSessionInfo.f['143']?? >
				      				<a href="/block/editcontent.go?id=${one.mediaBlockId?c}">配置内容</a>
				      				<a href="/block/detail.go?id=${one.mediaBlockId?c}&flag=2">编辑内容</a>
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
			    </td>
			    </tr>
			    </table>
		    </div>
		</div>
	</div>
	<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<#include "../common/common-js.ftl">
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
				link_to: encodeURI('/block/list.go?<#if one.groupId??>groupId=${one.groupId?c}&</#if>page=__id__')
		    });
		    
		    
	   	});
	   	
	   	function searchMaster(){
	   		$('#list_form').submit();
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
				alert('你还没有选择任何内容！');
				return;
			}
			if(window.confirm("确实要删除吗？")){
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
			 }else{
			 	return;
			 }	
		}
		
		function showDetail(id,type){
	   		$.dialog({title:'块详情页',content:'url:/block/detail.go?id='+id+'&type='+type,
	   		icon:'success',width:1000,height:600,fixed:false,lock:true
			});
	   	}
	   	
	   	$('#chooseAll').click(function(){
			if(this.checked){
				 $('[name=blockId]:checkbox').attr("checked", true);
			}else{
				 $('[name=blockId]:checkbox').attr("checked", false);
			}
		});
		
	   	
	</script>
	<#include "../common/common-js.ftl">
  </body>
</html>
