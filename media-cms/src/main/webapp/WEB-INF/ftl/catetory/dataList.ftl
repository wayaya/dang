<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="/style/common/index.css" rel="stylesheet" />
	<link href="/style/common/page.css" rel="stylesheet" />
	<script type="text/javascript" src="/script/jquery/jquery-1.7.js"></script>
	<script src="/script/jquery/jquery-1.7.js" type="text/javascript" ></script>
	<script src="/script/jquery/jquery.pagination.js" type="text/javascript" ></script>
	<script src="/script/common/left.js" type="text/javascript" ></script>

	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		var condition ="";
		<#if id??>
			condition="&id="+${id};
		</#if>
		<#if parentId??>
			condition="&parentId="+${parentId};
		</#if>
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize?c},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/catetory/list.go?page=__id__'+condition)
		    });
	   	});
	   	
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	   	
	   	function saveOrder(){
	   		var arr = document.getElementsByName("indexOrder");
	   		if(arr.length == 0){
	   			alert("没有需要排序的分类");
	   			return;
	   		}
	   		var arrs = "[";
	   		for(var i=0;i<arr.length;i++){
	   			
	   			var indexOrder = arr[i].value;
	   		
	   			if($.trim(indexOrder) =='' || isNaN(indexOrder)){
	   				alert("第"+(i+1)+"行分类请输入数字排序码！");
	   				return;
	   			}
	   			if(indexOrder.indexOf(".")>=0){
	   				alert("第"+(i+1)+"行分类排序码请输入整数！");
	   				return;
	   			}
	   			arrs+="{\"id\":"+arr[i].id+",\"indexOrder\":"+indexOrder+"}";
	   			if(i!=arr.length-1){
	   				arrs+=",";
	   			}
	   		}
	   		arrs+="]";
	   		
	   		$.post("/catetory/saveOrder.go", {order:arrs},
			   function(data){
			     if(data.success){
			     	alert("保存成功");
			     }else{
			    	 alert(data.msg);
			     }
			   }, "json");
	   	}
	</script>
</head>   
  <body>
	 <div class="page" style="width: 99%; height: 900px;"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
			<#if userSessionInfo?? && userSessionInfo.f['178']?? >
				<input type="button" value="保存排序" onclick="saveOrder();">
			</#if>
			<table>
			<tr>
			<td style="width:100%;">
				<div class="m-r">
				    <div>
				    	<table class="table2">
			            	<tr>
			            		<th style="width:15%">序号</th>
			                    <th style="width:15%">名称</th>
			                    <th style="width:15%">编码</th>
			                    <th style="width:15%">path</th>
			                    <th style="width:8%">排序码</th>
			                    <th style="width:7%">状态</th>
			                    <th >操作</th>
		               	    </tr>
	               	 <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as cate>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				<td>${i}</td>
				    			<td><#if cate.name??>${cate.name}</#if></td>
					      		<td><#if cate.code??>${cate.code}</#if></td>
					      		<td><#if cate.path??>${cate.path}</#if></td>
					      		<td><input name="indexOrder" id="${cate.id?c}" value="<#if cate.indexOrder??>${cate.indexOrder?c}</#if>"></td>
					      		<td><#if cate.status??><#if cate.status??><#if (cate.status == 0)><font color='red'>禁用</font></#if></#if><#if cate.status??><#if (cate.status == 1)>启用</#if></#if></#if></td>
					      		<td>
					      		<#if userSessionInfo?? && userSessionInfo.f['179']?? >
				      				<a href="javascript:location='/catetory/toEdit.go?id=${cate.id?c}'">更新</a>
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
  </body>
  <script>
  $(document).ready(function(){
	  var id = '<#if RequestParameters.id??>${RequestParameters["id"]}</#if>';
	  var name = '<#if RequestParameters.name??>${RequestParameters["name"]}</#if>';
	  if(id!='' && name !=''){
		  window.parent.reName(id,name);
	  }
	});
  </script>
</html>
