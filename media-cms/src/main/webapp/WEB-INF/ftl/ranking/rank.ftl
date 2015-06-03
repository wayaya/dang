<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>榜单内容列表</title>
	 <script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>榜单管理&gt;&gt;榜单排名<font color='red'><#if RequestParameters.listType??>[${RequestParameters["listType"]}]</#if></font></h3>
				<div id="toolbar" title="工具条"></div>
				<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/ranking/rank.go" method="post" id="query" name="query" onsubmit="return checkForm();">
		      		<input type="hidden" id ="orederDimension" name="orederDimension" value='<#if RequestParameters.orederDimension??>${RequestParameters["orederDimension"]}<#else>0</#if>'>
		      		<input type="hidden" id ="listType" name="listType" value='<#if RequestParameters.listType??>${RequestParameters["listType"]}<#else>0</#if>'>
		      		<input type="hidden" id ="path" name="path" value='<#if RequestParameters.path??>${RequestParameters["path"]}</#if>'>
			      		 <table >
		        			<tr>
								<td>
								作品Id：<input type="text" name="mediaId" id="mediaId" value="<#if RequestParameters.mediaId??>${RequestParameters["mediaId"]}</#if>">
								销售主体Id：<input type="text" name="saleId" id="saleId" value="<#if RequestParameters.saleId??>${RequestParameters["saleId"]}</#if>">
								作品名：<input type="text" name="mediaName" id="mediaName" value="<#if RequestParameters.mediaName??>${RequestParameters["mediaName"]}</#if>">
								<button  type="submit" >查询</button>
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				     <form id="listform" name="listform" method="post">
				    <table class="table2">
		            	<tr>
		                    <th>序号<input type="checkbox" id='chkAll' name='chkAll'></th>
		                    <th>作品ID</th>
		                    <th>销售主体ID</th>
		                    <th>作品名</th>
		                    <th>作家笔名</th>
		                    <th>指定排名</th>
		                    <th>操作者</th>
		                    <th>操作时间</th>
		                    <th>上下架</th>
		                    <th>状态</th>
	               	    </tr>
	               	    <#assign i = 0>
	              	 	 <#if pageFinder??>
	              	 	 <#if (pageFinder?size>0)>
			    			<#list pageFinder.data as column>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
			    				    
					    			<td><input type="hidden"  id="listIds" name="listIds" value="${column.listId?c}" title="不管是否选中，都提交"><input type="checkbox"  id="listId" name="listId" value="${column.listId?c}">${i}</td>
						      		<td><#if column.mediaId??>${column.mediaId?c}</#if></td>
						      		<td><#if column.saleId??>${column.saleId?c}</#if></td>
						      		<td><#if column.mediaName??>${column.mediaName}</#if></td>
						      		<td><#if column.penName??>${column.penName}</#if></td>
						      		<td><input type="text" size="2"   name="appointOrder" editable="true" value="<#if column.appointOrder??>${column.appointOrder?c}</#if>"/></td>
						      		<td><#if column.operator??>${column.operator}</#if></td>
						      		<td><#if column.operateTime??>${column.operateTime}</#if></td>
						      		<td><#if column.shelfStatus??>
									<#if '${column.shelfStatus}'=='${on_shelf}'>上架 <#else>下架</#if></#if></td>
						      		<td><#if column.status??>
						      		<#if '${column.status}'=='${normal}'>正常显示
						      			<#elseif '${column.status}'=='${force_invalid?c}'>强制无效
						      			<#elseif '${column.status}'=='${force_valid?c}'>强制推荐
						      			<#else>没有状态</#if> 
						      		</#if></td>
						      	</tr>
				      		</#list>
				      	</#if>
				      	</#if>
		            </table>
		            </form>
		            </div>
			    </div>
			   <div class="pagination rightPager"></div>
			   <div class="leftPager">总数：${pageFinder.rowCount?c}&nbsp;&nbsp;当前行数：${pageFinder.currentNumber?c}&nbsp;&nbsp;</div>
		    </div>
		</div>
	</div>
  </body>
  	<#include "../common/common-js.ftl">
  	<link href="/style/toolbar/core.css" rel="stylesheet" type="text/css" />
<link href="/style/toolbar/toolbar.css" rel="stylesheet" type="text/css" />
<script src="/script/toolbar.js" type="text/javascript"></script>
<script type="text/javascript">
 	function checkForm(){
 		var mediaId = $('#mediaId').val();
 		if(isNaN(mediaId)){
 			alert("作品Id必须是数字");
 			return false;
 		}
 		
 		var saleId = $('#saleId').val();
 		if(isNaN(saleId)){
 			alert("销售主体必须是数字");
 			return false;
 		}
 		
 	}
	var toolbar;
    //js的注释与html的注释放开,再看一下效果
    $(document).ready(function(){
      toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [
          <#if userSessionInfo?? && userSessionInfo.f['116']?? >       
                 {
          type : 'button',
          text : '添加作品',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
        	   var columnId = $('#columnId').val();
  	  			if(columnId==0){
  	  			alert("请先选择左侧栏目,单击左侧栏目");
  	  			return false;
  	  		}
  	  		window.location='/ranking/sales/query.go?path='+$('#path').val()+'&listType='+$('#listType').val()+"&columnName="+$('#columnId').attr('colName')+"&columnCode="+$('#columnCode').val();
  	  		return true;
          }
        },
		</#if>

        <#if userSessionInfo?? && userSessionInfo.f['117']?? > 
        {
            type : 'button',
            text : '删除',
            bodyStyle : 'delete',
            useable : 'T',
            title:'删除此记录',
            handler : function(){
            	var ckId = getSelectedSalesId();
    			if(ckId.length==0){
    				alert("请选择需要删除的记录");
    				return false;
    			}
       		 var flag = window.confirm("你确认删除该销售主体?");
       		   if(flag){
       				$('#listform').attr('action','/ranking/deleterecord.go');
       				$('#listform').submit();
       			}
            }
          },'-',
          </#if>
          <#if userSessionInfo?? && userSessionInfo.f['118']?? > 
          {
       	 type : 'button',
         text : '更新缓存',
         bodyStyle :'send',
         title:'将修改变化推送到客户端',
         handler : function(){
        	 $.ajax({
        			type:"POST",
        			url:"/cache/ranking/clearcache.go",
        			data: "listType="+$('#listType').val(),
        			dataType:"json",
        			success: function(msg) {
        				   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
        					   alert("发布失败!");
        				   }else{
        					   alert("发布成功!");
        				   }
        			}
        		});
      }
 	 },'-',
	</#if>
 	 <#if userSessionInfo?? && userSessionInfo.f['119']?? > 
 	 {
         	  type : 'button',
        	  text : '保存排序',
        	  bodyStyle : 'order',
         	  useable : 'T',
         	  handler : function(){
         			var ckId = getSelectedSalesId();
        			if(ckId.length==0){
        				alert("请选择需要保存的记录");
        				return false;
        			}
        			$('#listform').attr('action','/ranking/updateorder.go?listType='+$('#listType').val()+"&orederDimension="+$('#orederDimension').val());
        			$('#listform').submit();
        	  }
        },'-',
	</#if>
       <#if userSessionInfo?? && userSessionInfo.f['120']?? > 
        {
          type : 'button',
          text : '设置状态',
          bodyStyle : 'op',
          useable : 'T',
          handler:function(){
        	  if(getSelectedSalesId().length==0){
  				alert("请先选择需要相应的销售主体");
  				return false;
  			}
  			//alert($('#listform').serialize());
  			$.dialog({title:'设置状态',content:'url:/ranking/editstatus.go?listIds='+getSelectedSalesId().join(',')+'&listType='+$('#listType').val(),
  		   		icon:'succeed',width:450,height:100,fixed:false,lock:true
  		   		
  		    });
          }
        }
	</#if>
        ],
        active : 'ALL'//激活哪个
      });
	  toolbar.render();
	  toolbar.genAZ();
    });
  //获取所有选中销售主体的编号
	var getSelectedSalesId =function(){
		var ids = [];
		 $('input[name="listId"]:checked').each(function(){    
			 ids.push($(this).val());   
		 });
		 return ids;
	};
	
    </script>
  	
  	<script type="text/javascript">
  	$(document).ready(function(){
		$('#chkAll').click(function(){
			if(this.checked){
				 $('[name=listId]:checkbox').attr("checked", true);
			}else{
				 $('[name=listId]:checkbox').attr("checked", false);
			}
		});
	});
  	</script>
  	<script type="text/javascript">
  	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize},
			current_page: ${pageFinder.pageNo - 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/ranking/rank.go?pageIndex=__id__&'+$('#query').serialize())
	    });
   	});
  	</script>
 
	</html>
