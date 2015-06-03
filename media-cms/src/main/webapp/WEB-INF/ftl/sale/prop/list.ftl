<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">	
		var condition ="";
		$(function(){
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/saleBase/prop/list.go?page=__id__'+"&"+$("#master_list_form").serialize())
		    });
		    $('#creationDateStart').calendar({maxDate:'#creationDateEnd',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#creationDateEnd').calendar({minDate:'#creationDateStart',format:'yyyy-MM-dd HH:mm:ss'});
		});		
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	   	
	   	function openDetail(propId,operateFlag){
	   		$.dialog({title:'',content:'url:/saleBase/prop/detail.go?propId='+propId+'&operateFlag='+operateFlag,
	   		icon:'succeed',width:1280,height:720,fixed:false,lock:true
			});
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
	 		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>收入管理&gt;&gt;道具管理</h3>
				<div class="mrdiv">
		      		<form action="/saleBase/prop/list.go" method="post" id="master_list_form">
			      		 <table >
		        			<tr style="height:28px;">
		        				<td>&nbsp;&nbsp;&nbsp;</td>
								<td>道具ID：&nbsp;&nbsp;<input type="text" name="propId" id="propId" value="<#if vo??><#if vo.propId??>${vo.propId?c}</#if></#if>"></td>
							 	<td>道具名称：&nbsp;&nbsp;<input type="text" name="propName" id="propName" value="<#if vo??><#if vo.propName??>${vo.propName}</#if></#if>"></td>
							 	<td>道具功能ID：<input type="text" name="propPurposeId" id="propPurposeId" value="<#if vo??><#if vo.propPurposeId??>${vo.propPurposeId?c}</#if></#if>"></td>
							 	<td>修改人：<input type="text" name="modifier" id="modifier" value="<#if vo??><#if vo.modifier??>${vo.modifier}</#if></#if>"></td>
							</tr>
							<tr style="height:28px;">
								<td>&nbsp;&nbsp;&nbsp;</td>
								<td>创建人：&nbsp;&nbsp;<input type="text" name="creator" id="creator" value="<#if vo??><#if vo.creator??>${vo.creator}</#if></#if>"></td>
							 	<td>开始时间：<input type="text" name="creationDateStart" id="creationDateStart" readonly="readonly" value="<#if vo??><#if vo.creationDateStart??>${vo.creationDateStart}</#if></#if>"></td>
							 	<td>结束时间：&nbsp;&nbsp;<input type="text" name="creationDateEnd" id="creationDateEnd" readonly="readonly" value="<#if vo??><#if vo.creationDateEnd??>${vo.creationDateEnd}</#if></#if>"></td>
								<td><button  onclick="return searchMaster()"><image src="/images/bottom_01.gif"/></button>&nbsp;&nbsp;<a href="#" onclick="openDetail('','2')"><image src="/images/botto_04.gif"/></a></td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr style="height:28px;">
		                    <th>道具id</th>
		                    <th>道具名称</th>
		                    <th>主账户购买价格</th>
		                    <th>副账户购买价格</th>
		                    <th>道具功能id</th>
		                    <th>创建时间</th>
		                    <th>创建人</th>
		                    <th>修改时间</th>
		                    <th>修改人</th>
		                    <th>操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 <#if pageFinder.data??>
			    			<#list pageFinder.data as prop>
					    		<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if prop.propId??>${prop.propId?c}</#if></td>
						      		<td><#if prop.propName??>${prop.propName}</#if></td>
						      		<td><#if prop.propMainGoldPrice??>${prop.propMainGoldPrice?c}</#if></td>
						      		<td><#if prop.propSubGoldPrice??>${prop.propSubGoldPrice?c}</#if></td>
						      		<td><#if prop.propPurposeId??>${prop.propPurposeId?c}</#if></td>
						      		<td><#if prop.creationDate??>${prop.creationDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						      		<td><#if prop.creator??>${prop.creator}</#if></td>
						      		<td><#if prop.lastModifiedDate??>${prop.lastModifiedDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						      		<td><#if prop.modifier??>${prop.modifier}</#if></td>
						      		<td>
						      			<a href="#" onclick="openDetail('${prop.propId?c}','0')">详情</a>
						      			&nbsp;
						      			<a href="#" onclick="openDetail('${prop.propId?c}','1')">更新</a>
						      		</td>
						      	</tr>
				      		</#list>
				      	</#if>
		            </table>
		            </div>
			    </div>
			    <div class="pagination rightPager"></div>
		    </div>
		</div>
	</div>
	<#include "../../common/common-js.ftl">
  </body>
</html>
