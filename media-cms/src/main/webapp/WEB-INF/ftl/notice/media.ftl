<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
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
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/notice/media.go?page=__id__'+condition)
		    });
		});
		function makecondition(){
			var name = $('#name').val();
			if(name.length  > 0){
				condition = condition + "&name="+name;
			}
		}
		
	   	function searchActivityTypeList(){
	   		$('#activity_type_list_form').submit();
	   	}
	</script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>销售主体管理&gt;&gt;销售主体管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/notice/media.go" method="post" id="activity_type_list_form">
		        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
								<td class="right_table_tr_td">名称：<input type="text" name="name" id="name" value="<#if mediaSale??><#if mediaSale.name??>${mediaSale.name}</#if></#if>"></td>
								<td class="right_table_tr_td">类型：
							 		<select name="type" id="type" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if mediaSale??><#if mediaSale.type??><#if (mediaSale.type == 0)>selected = "selected" </#if></#if></#if>>单品</option>
							 			<option value="1"  <#if mediaSale??><#if mediaSale.type??><#if (mediaSale.type == 1)>selected = "selected" </#if></#if></#if>>打包</option>
							 		</select>
							 	</td>
							 	<td class="right_table_tr_td">
							 	pId：<input type="text" name="pId" id="pId" value="<#if mediaSale??><#if mediaSale.pId??>${mediaSale.pId?c}</#if></#if>"></td>
							 	<td >&nbsp;</td>
							</tr>
							<tr height="10px"></tr>
							<tr>
							<td class="right_table_tr_td">上下架：
							 		<select name="shelfStatus" id="shelfStatus" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if mediaSale??><#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 0)>selected = "selected" </#if></#if></#if>>下架</option>
							 			<option value="1"  <#if mediaSale??><#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 1)>selected = "selected" </#if></#if></#if>>上架</option>
							 		</select>
							 	</td>
							<td class="right_table_tr_td">是否支持订阅：
							 		<select name="isSupportSubscribe" id="isSupportSubscribe" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="0" <#if mediaSale??><#if mediaSale.isSupportSubscribe??><#if (mediaSale.isSupportSubscribe == 0)>selected = "selected" </#if></#if></#if>>是</option>
							 			<option value="1"  <#if mediaSale??><#if mediaSale.isSupportSubscribe??><#if (mediaSale.isSupportSubscribe == 1)>selected = "selected" </#if></#if></#if>>否</option>
							 		</select>
							 	</td>
								<td class="right_table_tr_td">
								<button  onclick="return searchActivityTypeList()">查询</button>
							</tr>
						
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:10%">销量主体Id</th>
		                    <th style="width:10%">名称</th>
		                    <th style="width:10%">类型</th>
		                    <th style="width:5%">价格</th>
		                    <th style="width:7%">创建时间</th>
		                    <th style="width:5%">最后一次更新章节</th>
		                    <th style="width:5%">状态</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
	               	 	
			    			<#list pageFinder.data as mediaSale>
			    			
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr ondblclick="javascript:setSaleId(${mediaSale.saleId?c},'${mediaSale.name}');" style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr ondblclick="javascript:setSaleId(${mediaSale.saleId?c},'${mediaSale.name}');" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					
			    					<td>${i}</td>
			    					<td>${mediaSale.saleId?c}
			    					<input type="hidden" value="${mediaSale.saleId?c}" id="saleId" name='saleId'></td>
					    			<td><#if mediaSale.name??>${mediaSale.name}</#if></td>
						      		<td><#if mediaSale.type??><#if (mediaSale.type == 0)>单品 <#else>打包</#if></#if></td>
						      		<td><#if mediaSale.price??>${mediaSale.price?c}</#if></td>
						      		<td><#if mediaSale.creationDate??>${mediaSale.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if mediaSale.lastUpdateChapter??>${mediaSale.lastUpdateChapter}</#if></td>
						      		<td><#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 0)>
						      			已下架
						      		</#if></#if>
						      		<#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 1)>
						      			已上架
						      		</#if></#if>
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
	<#include "../common/common-js.ftl">
  </body>
  <script >
  	function setSaleId(saleId,saleName){
  		var api = frameElement.api, W = api.opener;
  		W.document.getElementById('id').value = saleId;
  		W.document.getElementById('name').value = saleName;
  		api.close();
  	}
  </script>
</html>
