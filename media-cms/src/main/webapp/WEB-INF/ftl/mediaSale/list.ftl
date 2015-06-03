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
			
			$('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize},
				current_page: ${pageFinder.pageNo?c}- 1,
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/mediaSale/list.go?page=__id__'+'&'+$('#activity_type_list_form').serialize())
		    });
		});
		function makecondition(){
			var name = $('#name').val();
			if(name.length  > 0){
				condition = condition + "&name="+name;
			}
			return condition;
		}
		
	   	function searchActivityTypeList(){
	   		var pId = $('#pId').val();
	   		var reg = new RegExp(/[0-9]{1,}/);
	   		
	   		if($.trim(pId).length>0 && !reg.test(pId)){
	   			alert('作品ID只能输入数字');
	   			return false;
	   		}
	   		
	   		var saleId = $('#saleId').val();
	   		if($.trim(saleId).length>0 && !reg.test(saleId)){
	   			alert('销售ID只能输入数字');
	   			return false;
	   		}
	   		$('#activity_type_list_form').submit();
	   	}
	   	function upShelf(saleId,title){
	   		if(confirm("确定要上架《"+title+"》吗？")){
	   			var ids = new Array();
	   			ids.push(saleId);
	   			$.post("/mediaSale/checkMedia.go", {ids:saleId,status:1},
			   function(data){
			   		if(!data.success){
			   			alert('请在作品管理中先上架以下作品:'+data.msg);
			   			return;
			   		}
					location='/mediaSale/toShelf.go?status=1&ids='+ids;	   				
			   }, "json");
	   		}
	   	}
	   	function downShelf(saleId,title){
	   		$.dialog.prompt('请输入下架原因',
			    function(val){
			    	var desc = $.trim(val);
			   		if(desc == ""){
			   			alert("下架原因不能为空！");
			   			return false;
			   		}
			      	if(confirm("确定要下架《"+title+"》吗？")){
					 	var ids = new Array();
					 	ids.push(saleId);
			   			location='/mediaSale/toShelf.go?status=0&ids='+ids+'&dsDesc='+encodeURIComponent(desc);
			   		 }  	
			    },
			    ''
			);
			 
	   	}
	   	function toShelf(shelfStatus){
	   		if(shelfStatus==0){
	   			$.dialog.prompt('请输入下架原因',
				    function(val){
				        var desc = $.trim(val);
				   		if(desc == ""){
				   			alert("下架原因不能为空！");
				   			return false;
				   		}
				   		toBatchShelf(shelfStatus,desc);
				    },
				    ''
				);
	   		}else{
	   			toBatchShelf(shelfStatus,'');
	   		}   		
	   		
	   	}
	   	
	   	function toUploadShelf(){
	   		$.dialog({id:"catetoryBaseDialog",
	   		lock:true,
	   		title:'上传文件',
	   		content:'url:/mediaSale/toUploadShelf.go',
	   			icon:'succeed',width:470,height:120,
	   			fixed:false,lock:true,zIndex:100,button: [
	        {
	            name: '提交',
	            callback: function () {
	           var flag =  $.dialog.list['catetoryBaseDialog'].content.check();
	          
	           
	           if(!flag){
	            	return false;
	           }
	           $.dialog({title:'提示',xButton:false,id:'dis',content: '正在处理中，请等待。。。', lock: true, parent:this});
	           var flag = true;
	           
	           $.dialog.list['catetoryBaseDialog'].content.$('#activity_type_add_form').ajaxSubmit({
				   async : false, 
                   success: function (data) {
                   		eval("data= "+data);
                       if(data.success){
                        	$.dialog.list['dis'].close();
                       		alert('操作成功!');
                       }else{
                       		flag = false;
                       		alert('操作失败!错误信息:'+data.msg);
                       }
                   },
                   error: function (xhr) {
                       alert("上传失败");
                       flag = false;
                   }
               });
	           $.dialog.list['dis'].close();
	            return flag;
	            },
	            focus: true
	        }, {
	            name: '取消',
	            callback: function () {
	                return true;
	            }
	        }]
			});
	   	}
	   	
	   	function toBatchShelf(shelfStatus,desc){
	   		var val = $('[name=saleId]:checked');
	   		if(val.length ==0){
	   			alert('请选择要上下架的销售主体！');
	   			return;
	   		}
	   		var ids = new Array();
	   		for(var i=0;i<val.length;i++){
	   			var arr = val[i].value.split(",");
	   			if(arr[1]!=shelfStatus){
	   				ids.push(arr[0]);
	   			}
	   		}
	   		if(ids.length == 0){
	   			if(shelfStatus == 1){
	   				alert('请选择已下架的销售主体进行上架,已经上架的销售主体不允许再次上架');
	   			}else{
	   				alert('请选择已上架的销售主体进行下架,已经下架的销售主体不允许再次下架');
	   			}
	   			return;
	   		}
	   		if(val){
	   		if(confirm(shelfStatus==1?"确定要上架这些销售主体吗？":"确定要下架这些销售主体吗？")){
	   			if(shelfStatus==1){
	   					$.post("/mediaSale/checkMedia.go?ids="+ids+"&status=1", {},
			   				function(data){
			   					if(!data.success){
			   						alert('请在作品管理中先上架以下作品:'+data.msg);
			   						return;
			   					}
							//location='/mediaSale/toShelf.go?status=1&ids='+ids;	  
							location = '/mediaSale/toShelf.go?ids='+ids+'&status='+shelfStatus;				
			   		}, "json");
			   }else{
	   				location = '/mediaSale/toShelf.go?ids='+ids+'&status='+shelfStatus+'&dsDesc='+encodeURIComponent(desc);
	   			}
	   		}
	   		}
	   	}
	   	
	   	function toSetIsFull(saleId){
	   		
	   		$.dialog({id:"labelDialog",title:'设置完本',content:'url:/mediaSale/toSetIsFull.go?saleId='+saleId,
	   		icon:'succeed',width:450,height:150,fixed:false,lock:true,button: [
        {
            name: '确定',
            callback: function () {
               var arr = $.dialog.list['labelDialog'].content.window.getVal();
               if(!arr[1] || arr[1]==''){
               		alert('价格不允许为空!');
               		return false;
               }
               location="/mediaSale/setIsFull.go?saleId="+saleId+"&isSupportFullBuy="+arr[0]+"&price="+arr[1];
                return true;
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
                return true;
            },
            focus: true
        }]
			});
	   	}
	</script>
	<style type="text/css">
		input {
			width: 100px;
		}
		select {
			width: 100px;
		}
	</style>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		
		<div class="main clear"><!--main开始-->
			
			<div class="right">
				<div class="m-r">
					<h3>销售主体管理&gt;&gt;销售主体管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/mediaSale/list.go" method="post" id="activity_type_list_form">
	        			<tr><input type="hidden" name="lefttab" id="lefttab" value="<#if lefttab??>${lefttab}</#if>">
							<td style="width:50px; text-align:right">名称：</td>
							<td style="width:60px; text-align:left"><input type="text" name="name" id="name" value="<#if mediaSale??><#if mediaSale.name??>${mediaSale.name}</#if></#if>"></td>
							<td style="width:50px; text-align:right">类型：</td>
						 	<td style="width:100px; text-align:left">
						 		<select name="type" id="type">
						 			<option value="">全部</option>
						 			<option value="0" <#if mediaSale??><#if mediaSale.type??><#if (mediaSale.type == 0)>selected = "selected" </#if></#if></#if>>单品</option>
						 			<option value="1"  <#if mediaSale??><#if mediaSale.type??><#if (mediaSale.type == 1)>selected = "selected" </#if></#if></#if>>打包</option>
						 		</select>
						 	</td>
						 	<td style="width:50px; text-align:right">作品ID：</td>
						 	<td style="width:60px; text-align:left"><input type="text" name="pId" id="pId" value="<#if mediaSale??><#if mediaSale.pId??>${mediaSale.pId?c}</#if></#if>"></td>
						 	<td style="width:50px; text-align:right">销售ID：</td>
						 	<td style="width:60px; text-align:left"><input type="text" name="saleId" id="saleId" value="<#if mediaSale??><#if mediaSale.saleId??>${mediaSale.saleId?c}</#if></#if>"></td>
							<td style="width:50px; text-align:right">上下架：</td>
						 	<td style="width:100px;; text-align:left">
						 		<select name="shelfStatus" id="shelfStatus">
						 			<option value="">全部</option>
						 			<option value="0" <#if mediaSale??><#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 0)>selected = "selected" </#if></#if></#if>>下架</option>
						 			<option value="1"  <#if mediaSale??><#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 1)>selected = "selected" </#if></#if></#if>>上架</option>
						 		</select>
						 	</td>
							<td style="width:100px; text-align:right">是否支持订阅：</td>
						 	<td style="width:100px; text-align:left">
						 		<select name="isSupportSubscribe" id="isSupportSubscribe">
						 			<option value="">全部</option>
						 			<option value="0" <#if mediaSale??><#if mediaSale.isSupportSubscribe??><#if (mediaSale.isSupportSubscribe == 0)>selected = "selected" </#if></#if></#if>>否</option>
						 			<option value="1"  <#if mediaSale??><#if mediaSale.isSupportSubscribe??><#if (mediaSale.isSupportSubscribe == 1)>selected = "selected" </#if></#if></#if>>是</option>
						 		</select>
						 	</td>
							<td style="width:300px; text-align:left">
								<button  onclick="return searchActivityTypeList()">查询</button>
								<#if userSessionInfo?? && userSessionInfo.f['211']?? >
								<input type="button" style="width:40px" value="上架" onclick="toShelf(1);">
								<input type="button" style="width:40px" value="下架" onclick="toShelf(0);">
								<input type="button" width="80" value="批量上下架" onclick="toUploadShelf();">
								</#if>
							</td>
						</tr>
				    </form>
				    </table>
				    
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		            		<th style="width:5%; height:28px;" ><input type="checkbox" id="chkAll" name="chkAll">全选</th>
		                    <th style="width:10%">销售ID</th>
		                    <th style="width:10%">名称</th>
		                    <th style="width:7%">类型</th>
		                    <th style="width:5%">价格</th>
		                    <th style="width:5%">创建人</th>
		                    <th style="width:7%">创建时间</th>
		                    <th style="width:5%">修改人</th>
		                    <th style="width:7%">修改时间</th>
		                    <th style="width:5%">完结状态</th>
		                    <th style="width:5%">全本购买</th>
		                    <th style="width:7%">是否支持订阅</th>
		                    <th style="width:5%">上下架状态</th>
		                    <th style="width:15%">操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
	               	 	
			    			<#list pageFinder.data as mediaSale>
			    			
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				<td><input type="checkbox" value="${mediaSale.saleId?c},${mediaSale.shelfStatus}" name='saleId'></td>
					    			<td><#if mediaSale.saleId??>${mediaSale.saleId?c}</#if></td>
					    			<td><#if mediaSale.name??>${mediaSale.name}</#if></td>
						      		<td><#if mediaSale.type??><#if (mediaSale.type == 0)>单品 <#else>打包</#if></#if></td>
						      		<td><#if mediaSale.price??>${mediaSale.price?c}</#if></td>
						      		<td><#if mediaSale.creator??>${mediaSale.creator}</#if></td>
						      		<td><#if mediaSale.creationDate??>${mediaSale.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if mediaSale.modifier??>${mediaSale.modifier}</#if></td>
						      		<td><#if mediaSale.lastModifiedDate??>${mediaSale.lastModifiedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if mediaSale.isFull??><#if (mediaSale.isFull == 1)>是 <#else>否</#if></#if></td>
						      		<td><#if mediaSale.isSupportFullBuy??><#if (mediaSale.isSupportFullBuy == 1)>是 <#else>否</#if></#if></td>
						      		<td><#if mediaSale.isSupportSubscribe??><#if (mediaSale.isSupportSubscribe == 1)>是 <#else>否</#if></#if></td>
						      		<td><#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 0)>
						      			已下架
						      		</#if></#if>
						      		<#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 1)>
						      			已上架
						      		</#if></#if>
						      		</td>
						      		<td>
									<#if userSessionInfo?? && userSessionInfo.f['211']?? >
						      		<#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 0)>
						      			<a href="javascript:upShelf(${mediaSale.saleId?c},'${mediaSale.name}');">【上架】</a>&nbsp;
						      		</#if></#if>
						      		<#if mediaSale.shelfStatus??><#if (mediaSale.shelfStatus == 1)>
						      			<a href="javascript:downShelf(${mediaSale.saleId?c},'${mediaSale.name}');">【下架】</a>&nbsp;
						      		</#if></#if>
									</#if>
									<#if userSessionInfo?? && userSessionInfo.f['210']?? >
					      				<a href="javascript:location='/mediaSale/toModify.go?saleId=${mediaSale.saleId?c}'">【更新】</a>
					      			</#if>
					      				<#if mediaSale.isFull??>
					      				<#if (mediaSale.type == 0)>
					      					<#if (mediaSale.isFull == 1)>
					      						<a href="javascript:toSetIsFull(${mediaSale.saleId?c})">【设置全本】</a>
					      					</#if>
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
				    <div class="leftPager"><input type="text" name="currentPage" id="currentPage" value="${pageFinder.pageNo?c}" style="width: 40px;"/>&nbsp;<button  onclick="javascript:goPage();"><font size="1">GO</font></button>&nbsp;&nbsp;&nbsp;总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
  <script type="text/javascript">
		$(document).ready(function(){
				$('#chkAll').click(function(){
					if(this.checked){
						 $('[name=saleId]:checkbox').attr("checked", true);
					}else{
						 $('[name=saleId]:checkbox').attr("checked", false);
					}
				});
		});
		
		function goPage(){
			var currentPage = $('#currentPage').val();
			if(currentPage > 0){
				window.location.href='/mediaSale/list.go?page='+currentPage+'&'+$('#activity_type_list_form').serialize();
			}else{
				alert("页数不合法");
			}
			
		}
	
		<#if msg??>
			alert('${msg}');
		</#if>
</script>
</html>
