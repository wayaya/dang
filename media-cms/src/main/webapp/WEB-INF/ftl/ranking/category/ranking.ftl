<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>分类榜单</title>
	<#include "../../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>榜单列表&gt;&gt;分类榜</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/ranking/categoryranking.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>Media名称：<input type="text" name="mediaName" id="mediaName" >
								<button  type="submit" >查询</button>
								&nbsp;<button  type="button" >手动添加榜单实体</button>
								&nbsp;<button  type="button" >保存排序</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th>序号</th>
		                    <th>名称</th>
		                    <th>作者笔名</th>
		                    <th>销售数量</th>
		                    <th>统计时间</th>
		                    <th>分类名称</th>
		                    <th>指定排名</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	  <#if (listTopn?size>0)>
			    			<#list listTopn as rowbean>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>${i}</td>
					    			<td><#if rowbean.mediaName??>${rowbean.mediaName}</#if></td>
					    			<td><#if rowbean.authorPenname??>${rowbean.authorPenname}</#if></td>
						      		<td><#if rowbean.saleCount??>${rowbean.saleCount}</#if></td>
						      		<td><#if rowbean.rankDate??>${rowbean.rankDate}</#if></td>
						      		<td><#if rowbean.categoryName??>${rowbean.categoryName}</#if></td>
						      		<td><input type="text" id="" name="" value="" editable="true" size="2"></td>
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
 	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	   	
	</script>

</html>
