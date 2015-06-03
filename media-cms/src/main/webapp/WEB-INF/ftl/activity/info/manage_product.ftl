<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js"></script> 
	<script type="text/javascript" src="/script/jquery/jquery.form.js"></script>
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
				link_to: encodeURI('/activity/info/manageproduct.go?page=__id__'+condition)
		    });
		});
		function makecondition(){
			var activityId = $('#activityId').val();
			if(activityId.length  > 0){
				condition = condition + "&activityId="+activityId;
			}
		}
		
	   	function searchActivityInfoList(){
	   		$('#activity_info_list_form').submit();
	   	}
	   	
        function uploadForm(){
        	var fileUpload = $('#fileUpload').val();
        	if(fileUpload == null || fileUpload == ""){
        		$.dialog({
        			icon:'error.gif',
	        		title:'出错了',
	        		content:'请选择excel文件', 
	        		width:200,
	        		height:100,
	        		fixed:false,
	        		timer:2,
	        		lock:true
        		});
        	}else{
        		var tail = fileUpload.split(".")[1];
        		if(tail != "xlsx" && tail != "xls"){
	        		$.dialog({
	        			icon:'error.gif',
		        		title:'出错了',
		        		content:'您选择的文件不是excel文件，请重新选择！', 
		        		width:200,
		        		height:100,
		        		fixed:false,
		        		timer:2,
		        		lock:true
	        		});
	        	}else{
	        		$("#uploadimg-form").ajaxSubmit({
	        			type:"post",  //提交方式  
                		dataType:"json", //数据类型  
	        			url:'/activity/info/manageproductimportexcel.go',
                        success: function(data){
                        	if(data.flag == 0){
                        		 $.dialog({
				        			icon:'success.gif',
					        		title:'上传成功',
					        		content:'上传成功！', 
					        		width:200,
					        		height:100,
					        		fixed:false,
					        		timer:5,
					        		close:function(){reloadLocation()},
					        		lock:true
				        		});
                        	}else{
                        		 $.dialog({
				        			icon:'success.gif',
					        		title:'数据冲突',
					        		content:'在其他活动中有冲突数据，如下：<br>'+data.message, 
					        		width:300,
					        		fixed:false,
					        		timer:5,
					        		close:function(){reloadLocation()},
					        		lock:true
				        		});
                        	}
                        },  
                        error: function(data) {  
                            $.dialog({
                            	icon:'error.gif',
				        		title:'出错了',
				        		content:'上传失败！', 
				        		width:200,
				        		height:100,
				        		fixed:false,
				        		timer:2,
				        		lock:true
			        		});
                        }  
                    }); 
	        	}
        	}
	   	}
	   	
	   	function reloadLocation(){
	   	 	window.location.reload();
	   	}
	   	
	   	$(function() {
            $("#checkAll").click(function() {
                $('input[name="activity_sale_checkbox"]').attr("checked",this.checked); 
            });
            var $subBox = $("input[name='activity_sale_checkbox']");
            $subBox.click(function(){
                $("#checkAll").attr("checked",$subBox.length == $("input[name='activity_sale_checkbox']:checked").length ? true : false);
            });
        });
        
        function exportCheckRow(){
	   	 	 var activitySaleArray = $("input[name='activity_sale_checkbox']:checked");
	   	 	 if(activitySaleArray.length == 0){
	   	 	 	$.dialog({
        			icon:'error.gif',
	        		title:'出错了',
	        		content:'您没有选择至少1行记录！', 
	        		width:200,
	        		height:100,
	        		fixed:false,
	        		timer:2,
	        		lock:true
        		});
	   	 	 }else{
	   	 	 	var activitySaleIds = "";
	   	 	 	for(var i = 0; i < activitySaleArray.length; i++){
		   	 	 	activitySaleIds = activitySaleIds + activitySaleArray[i].value+"|";
		   	 	}
		   	 	
		   	 	activitySaleIds = activitySaleIds.substring(0,activitySaleIds.length-1)
		   	 	var activityId = $('#activityId').val();
		   	 	if(activitySaleIds.length > 0 && activityId.length > 0){
					 var f = document.createElement("form");
					 document.body.appendChild(f);
					 var i = document.createElement("input");
					 i.type = "hidden";
					 f.appendChild(i);
					 i.value = activitySaleIds;
					 i.name = "ids";
					 var j = document.createElement("input");
					 j.type = "hidden";
					 f.appendChild(j);
					 j.value = activityId;
					 j.name = "activityId";
					 f.method = "post";
					 f.action = "/activity/info/manageproductexportexcel.go?r="+Math.random()
					 f.submit();
		   	 	}
	   	 	 }
	   	}
	   	
	   	function exportAllRow(){
			var activityId = $('#activityId').val();
			var activitySaleArray = $("input[name='activity_sale_checkbox']:checked");
   	 	 	var activitySaleIds = "";
   	 	 	for(var i = 0; i < activitySaleArray.length; i++){
	   	 	 	activitySaleIds = activitySaleIds + activitySaleArray[i].value+"|";
	   	 	}
	   	 	
	   	 	activitySaleIds = activitySaleIds.substring(0,activitySaleIds.length-1)
			if(activityId.length > 0){
				var f = document.createElement("form");
				document.body.appendChild(f);
				var j = document.createElement("input");
				j.type = "hidden";
				f.appendChild(j);
				j.value = activityId;
				j.name = "activityId";
				
				var saleId = $('#saleId').val();
				var salePrice = $('#salePrice').val();
				if(saleId != ""){
					var i = document.createElement("input");
					i.type = "hidden";
					f.appendChild(i);
					i.value = saleId;
					i.name = "saleId";
				}
				
				if(salePrice != ""){
					var k = document.createElement("input");
					k.type = "hidden";
					f.appendChild(k);
					k.value = salePrice;
					k.name = "salePrice";
				}
				
				if(activitySaleIds != ""){
					var n = document.createElement("input");
					n.type = "hidden";
					f.appendChild(n);
					n.value = activitySaleIds;
					n.name = "ids";
				}
				
				f.method = "post";
				f.action = "/activity/info/manageproductexportexcel.go?r="+Math.random()
				f.submit();
			}else{
				$.dialog({
        			icon:'error.gif',
	        		title:'出错了',
	        		content:'没有对应的促销！', 
	        		width:200,
	        		height:100,
	        		fixed:false,
	        		timer:2,
	        		lock:true
        		});
			}
	   	}
	   	
	   	function deleteRow(activitySaleId){
	   	 	 if(activitySaleId != null && activitySaleId!="" && activitySaleId > 0){
	   	 	 	$.dialog({
	        		title:'确认删除？',
	        		content:'您确定要删除？！', 
	        		width:200,
	        		height:50,
	        		fixed:false,
                    button: [
				        {
				            name: '确定',
				            height:30,
				            callback: function () {
							   	$.post("/activity/info/deleteactivitysale.go?r="+Math.random(),{ activitySaleId: activitySaleId},  function(data){
								   reloadLocation();
								 });
				            },
				            focus: true
				        }, 
				        {
				            name: '取消',
				            callback: function () {
				                return true;
				            },
				            focus: true
			        	}
			        ]
        		});
	   	 	 }else{
	   	 	 	$.dialog({
	        		title:'选行出错',
	        		content:'没有选择对应的行,请重新选择！！', 
	        		width:200,
	        		height:100,
	        		fixed:false
        		});
	   	 	 }
	   	}
	   	
	   	function updateStatus(activitySaleId, status){
	   	 	 if(activitySaleId != null && activitySaleId!="" && activitySaleId > 0){
	   	 	 	$.dialog({
	        		title:'确认修改？',
	        		content:'您确定要修改？！', 
	        		width:200,
	        		height:50,
	        		fixed:false,
                    button: [
				        {
				            name: '确定',
				            height:30,
				            callback: function () {
							   	$.post("/activity/info/updateactivitysale.go?r="+Math.random(),{ activitySaleId:activitySaleId, status:status},  function(data){
								   reloadLocation();
								 });
				            },
				            focus: true
				        }, 
				        {
				            name: '取消',
				            callback: function () {
				                return true;
				            },
				            focus: true
			        	}
			        ]
        		});
	   	 	 }else{
	   	 	 	$.dialog({
	        		title:'选行出错',
	        		content:'没有选择对应的行,请重新选择！！', 
	        		width:200,
	        		height:100,
	        		fixed:false
        		});
	   	 	 }
	   	}
	   	
	   	function batchUpdateSort(){
	   		var activityId = $('#activityId').val();
			var activitySaleArray = $("input[name='activity_sale_checkbox']:checked");
   	 	 	var activitySaleIds = "";
   	 	 	for(var i = 0; i < activitySaleArray.length; i++){
	   	 	 	activitySaleIds = activitySaleIds + activitySaleArray[i].value+"::"+$('#activity_sale_sort_'+activitySaleArray[i].value).val()+"|";
	   	 	}
	   	 	
	   	 	activitySaleIds = activitySaleIds.substring(0,activitySaleIds.length-1)
        	if(activitySaleIds.length > 0){
        		$.post("/activity/info/updateactivitysalesort.go", {activitySaleIds: activitySaleIds},function (data){
        			if(data.flag == 1){
        				reloadLocation();
        			}else{
        				$.dialog({
			        		title:'修改出错，排序值格式不正确',
			        		content:'修改出错，排序值格式不正确！！', 
			        		width:200,
			        		height:100,
			        		fixed:false
		        		});
        			}
			    }, "json");
        	}else{
        		$.dialog({
	        		title:'选行出错',
	        		content:'没有选择对应的行,请重新选择！！', 
	        		width:200,
	        		height:100,
	        		fixed:false
        		});
        	}
	   	}
	   	
    </script>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3><a href="/activity/info/list.go">促销信息管理</a>&nbsp;&gt;&gt;&nbsp;活动商品管理<font color="red">&nbsp;&nbsp;(活动商品为互斥关系，同一个销售实体只能在一个活动中存在)</font></h3>
					<div class="mrdiv">
			      		 <table >
		        			<tr>
								<td class="right_table_tr_td">促销名称：<#if activityInfo??><#if activityInfo.activityName??>${activityInfo.activityName}</#if></#if></td>
							 	<td class="right_table_tr_td">开始时间：<#if activityInfo.startTime??>${activityInfo.startTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							 	<td class="right_table_tr_td">结束时间：<#if activityInfo.endTime??>${activityInfo.endTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							 	<td class="right_table_tr_td">创建人：<#if activityInfo.creator??>${activityInfo.creator}</#if></td>
							 	<td class="right_table_tr_td">创建时间：<#if activityInfo.creationDate??>${activityInfo.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							 	<td class="right_table_tr_td">默认价格：<#if activityInfo.fixedPrice??>${activityInfo.fixedPrice}</#if></td>
								<td >&nbsp;</td>
							</tr>
						</table>
				    </div>
				    <div class="mrdiv">
			      		 <table>
			        			<tr>
									<td class="right_table_tr_td" style="width: 250px;">
										<form action="/activity/info/manageproductexcel.go" method="post" enctype="multipart/form-data" id="uploadimg-form">
											<input type="file" title="选择文件" name="fileUpload" id="fileUpload"/>&nbsp;&nbsp;
											是否使用该促销的默认价格：<input type="checkbox" value="1" name="useDefaultPrice">
											<input type="hidden" name="activityIdExcel" id="activityIdExcel" value="<#if activityInfo??><#if activityInfo.activityId??>${activityInfo.activityId?c}</#if></#if>"/>
										</form>
									</td>
								 	<td class="right_table_tr_td"><input id="fileBtn" type="button" class="btn" value="文件上传或更新" onclick="uploadForm()"/></td>
								 	<td class="right_table_tr_td"><a href="/file/activity_sale.xls" target="_blank">下载模板</a></td>
									<td width="10%">&nbsp;</td>
								</tr>
						</table>
				    </div>
				    
				    <div class="mrdiv">
			      		 <table>
		        			<tr>
		        				<form action="/activity/info/manageproduct.go" method="post" id="activity_info_list_form">
		        				<input type="hidden" name="activityId" id="activityId" value="<#if activityInfo??><#if activityInfo.activityId??>${activityInfo.activityId?c}</#if></#if>"/>
								<td class="right_table_tr_td" style="width:250px;">销售实体ID：<input type="text" name="saleId" id="saleId" value="<#if activitySale??><#if activitySale.saleId??>${activitySale.saleId?c}</#if></#if>"></td>
							 	<td class="right_table_tr_td" style="width:200px">价格：<input type="text" name="salePrice" id="salePrice" value="<#if activitySale??><#if activitySale.salePrice??>${activitySale.salePrice?c}</#if></#if>"></td>
								<td class="right_table_tr_td" style="width:50px"><button  onclick="searchActivityInfoList()">查询</button></td>
								</form>
								<td class="right_table_tr_td" style="width:100px"><button  onclick="exportCheckRow()">导出选中对象</button></td>
								<td class="right_table_tr_td" style="width:150px"><button  onclick="exportAllRow()">导出所有查询对象</button></td>
								<td class="right_table_tr_td" style="width:150px"><button  onclick="batchUpdateSort()">批量修改排序值</button></td>
								<td></td>
							</tr>
						</table>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		            		<th style="width:5%; height:28px;">全选&nbsp;<input type="checkbox" name="checkAll" id="checkAll" style="vertical-align: middle;margin-top: -4px;"></th>
		                    <th style="width:5%; height:28px;">ID</th>
		                    <th style="width:10%">销售实体ID</th>
		                    <th style="width:10%">销售实体名称</th>
		                    <th style="width:10%">开始时间</th>
		                    <th style="width:10%">结束时间</th>
		                    <th style="width:5%">价格</th>
		                    <th style="width:5%">上下架状态</th>
		                    <th style="width:5%">有效状态</th>
		                    <th style="width:5%">排序</th>
		                    <th style="width:15%">状态</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as activitySale>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    					<td><input type="checkbox" value="<#if activitySale.activitySaleId??>${activitySale.activitySaleId?c}</#if>" name="activity_sale_checkbox" id="activity_sale_"<#if activitySale.activitySaleId??>${activitySale.activitySaleId?c}</#if>></td>
					    			<td><#if activitySale.activitySaleId??>${activitySale.activitySaleId?c}</#if></td>
					    			<td><#if activitySale.saleId??>${activitySale.saleId?c}</#if></td>
						      		<td><#if activitySale.saleName??>${activitySale.saleName}</#if></td>
						      		<td><#if activitySale.startTime??>${activitySale.startTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if activitySale.endTime??>${activitySale.endTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if activitySale.salePrice??>${activitySale.salePrice?c}</#if></td>
						      		<td><#if activitySale.shelfStatus??><#if (activitySale.shelfStatus == 1)>已上架 <#else>已下架</#if></#if></td>
						      		<td><#if activitySale.status??><#if (activitySale.status == 1)>有效 <#else>无效</#if></#if></td>
						      		<td>
						      			<input style="width:30px;" type="text" value="<#if activitySale.sort??>${activitySale.sort?c}<#else>0</#if>" id="activity_sale_sort_<#if activitySale.activitySaleId??>${activitySale.activitySaleId?c}</#if>"/>
						      		</td>
						      		<td>
						      			<img src="/images/delete.png" style="cursor: pointer;" onclick="javascript:deleteRow(<#if activitySale.activitySaleId??>${activitySale.activitySaleId?c}</#if>)"/>
						      			&nbsp;
						      			<#if activitySale.status??>
							      			<#if (activitySale.status == 1)>
								      			<img src="/images/no_valid.png" style="cursor: pointer;" onclick="javascript:updateStatus(<#if activitySale.activitySaleId??>${activitySale.activitySaleId?c}</#if>, 0)"/> 
								      		<#else>
								      			<img src="/images/valid.png" style="cursor: pointer;" onclick="javascript:updateStatus(<#if activitySale.activitySaleId??>${activitySale.activitySaleId?c}</#if>, 1)"/>
								      		</#if>
								      	</#if>
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
