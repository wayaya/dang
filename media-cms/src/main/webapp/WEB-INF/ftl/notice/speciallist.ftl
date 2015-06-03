<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>内容列表</title>
	<#include "../common/common-css.ftl">
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
		      		<form action="/notice/special.go" method="post" id="list_form" >
			      		 <table >
		        			<tr>
								<td class="right_table_tr_td">名称：<input type="text" name="name" id="name" value="<#if specialTopic??><#if specialTopic.name??>${specialTopic.name}</#if></#if>"></td>
								<td class="right_table_tr_td"><button  onclick="return searchMaster()">查询</button>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    	<table class="table2">
			            	<tr>
			            		<th >序号</th>
			            		<th >名称</th>
			                    <th >设备类型</th>
			                    <th >频道</th>
			                    <th >创建时间</th>
			                    <th>状态</th>
		               	    </tr>
	               	 <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as one>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr  ondblclick="javascript:setSpecialId(${one.stId?c},'<#if one.name??>${one.name}</#if>');" style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${one.stId?c}">
			    				<#else>
			    					<tr  ondblclick="javascript:setSpecialId(${one.stId?c},'<#if one.name??>${one.name}</#if>');"  onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${one.stId?c}">
			    				</#if>
			    				<td>${one.stId?c}</td>
				    			<td><#if one.name??>${one.name}</#if></td>
					      		<td><#if one.deviceType??>${one.deviceType}</#if></td>
					      		<td><#if one.channelType??>${one.channelType}</#if></td>
					      		<td><#if one.creationDate??>${one.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
					      		<td><#if one.status??><#if one.status == 1>有效</#if><#if one.status == 0>无效</#if></#if></td>
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
	<#include "../common/common-js.ftl">
	<script>
  	function setSpecialId(specialId,specialName){
  		var api = frameElement.api, W = api.opener;
  		W.document.getElementById('id').value = specialId;
  		W.document.getElementById('name').value = specialName;
  		api.close();
  	}
  </script>
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
				link_to: encodeURI('/notice/special.go?page=__id__')
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
		
	</script>
  </body>
</html>
