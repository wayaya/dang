<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
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
			
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c} - 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/author/list.go?page=__id__'+makecondition())
		    });
		});
		function makecondition(){
			var name = $('#name').val();
			if(name.length  > 0){
				condition = condition + "&name="+name;
			}
			var pseudonym = $('#pseudonym').val();
			if(pseudonym.length  > 0){
				condition = condition + "&pseudonym="+pseudonym;
			}
			var cpType = $("#cpType  option:selected").val()
			if(cpType.length  > 0){
				condition = condition + "&cpType="+cpType;
			}
			return condition;
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
					<h3>作者管理&gt;&gt;作者管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/author/list.go" method="post" id="activity_type_list_form">
			      		 
		        			<tr>
								<td class="right_table_tr_td">姓名：<input type="text" name="name" id="name" value="<#if author??><#if author.name??>${author.name}</#if></#if>"></td>
								<td class="right_table_tr_td">笔名：<input type="text" name="pseudonym" id="pseudonym" value="<#if author??><#if author.pseudonym??>${author.pseudonym}</#if></#if>"></td>
								<td class="right_table_tr_td">CP：
							 		<select name="cpType" id="cpType" style="width:50%">
							 			<option value="">全部</option>
							 			<option value="zongheng" <#if author??><#if author.cpType??><#if (author.cpType == "zongheng")>selected = "selected" </#if></#if></#if>>纵横</option>
							 			<option value="jinjiang" <#if author??><#if author.cpType??><#if (author.cpType == "jinjiang")>selected = "selected" </#if></#if></#if>>晋江</option>
							 		</select>
							 	</td>
								<td class="right_table_tr_td"><button  onclick="return searchActivityTypeList()">查询</button>
								<td >&nbsp;</td>
							</tr>
						
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;" >序号</th>
		                    <th style="width:10%">姓名</th>
		                    <th style="width:10%">笔名</th>
		                    <th style="width:10%">性别</th>
		                    <th style="width:10%">出生</th>
		                    <th style="width:10%">标签</th>
		                    <th style="width:10%">CP</th>
		                    <th style="width:5%">创建人</th>
		                    <th style="width:7%">创建时间</th>
		                    <th style="width:5%">修改人</th>
		                    <th style="width:7%">修改时间</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as author>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td>${i}</td>
					    			<td><#if author.name??>${author.name}</#if></td>
						      		<td><#if author.pseudonym??>${author.pseudonym}</#if></td>
						      		<td><#if author.sex??><#if (author.sex == 0)>男 <#else>女</#if></#if></td>
						      		<td><#if author.birth??>${author.birth?string("yyyy-MM-dd")}</#if></td>
						      		<td><#if author.sign??>${author.sign}</#if></td>
						      		<td><#if author.cpType??><#if (author.cpType == "zongheng")>纵横 <#else>晋江</#if></#if></td>
						      		<td><#if author.creator??>${author.creator}</#if></td>
						      		<td><#if author.creationDate??>${author.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if author.modifier??>${author.modifier}</#if></td>
						      		<td><#if author.lastModifiedDate??>${author.lastModifiedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td>
									<#if userSessionInfo?? && userSessionInfo.f['214']?? >
						      			<#if author.authorId??>
					      				<a href="javascript:location='/author/toEdit.go?authorId=#{author.authorId}'">更新</a>
					      				</#if>
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
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
</html>
