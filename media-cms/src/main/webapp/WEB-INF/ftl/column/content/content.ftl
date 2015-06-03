<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>栏目内容管理</title>

</head>
<body>
	<div class="page">
		<!--page开始-->
		<div class="main clear">
			<!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>栏目内容&gt;&gt;内容列表&gt;&gt;<font color='red'><#if RequestParameters.columnCode??>[${RequestParameters["columnCode"]}]</#if></font></h3>
					<div id="toolbar" title="工具条"></div>
					<div
						style="padding: 5px; background: none repeat scroll 0 0 #E4EAF6; margin-bottom: 5px; border: 1px solid #4F69A0">
						<input type="hidden" id="categoryId" name="categoryId" title="media分类标识"
								value='<#if RequestParameters.categoryId??>${RequestParameters["categoryId"]}</#if>'>
						<form action="/column/content/content.go" method="post" id="query" name="query" onsubmit="return checkQuery();">
							<input type="hidden" id="columnId" name="columnId"
								colName='<#if RequestParameters.columnName??>${RequestParameters["columnName"]}</#if>'
								value='<#if RequestParameters.columnId??>${RequestParameters["columnId"]}</#if>'>
							<input type="hidden" id="columnCode" name="columnCode"
								value='<#if RequestParameters.columnCode??>${RequestParameters["columnCode"]}</#if>'>
							<input type="hidden" id="path" name="path"
								value='<#if RequestParameters.path??>${RequestParameters["path"]}</#if>'>
							<table>
								<tr>
									<td>
									作品Id：<input type="text" name="media.mediaId" id="mediaId" value='<#if RequestParameters["media.mediaId"]??>${RequestParameters["media.mediaId"]}</#if>'>
									销售主体Id：<input type="text" name="saleId" id="saleId" value='<#if RequestParameters.saleId??>${RequestParameters["saleId"]}</#if>'>
									销售名称：<input type="text" name="saleName" id="saleName" value='<#if RequestParameters.saleName??>${RequestParameters["saleName"]}</#if>'>
									<button type="submit">查询</button>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div>
						<form id="listform" name="listform" method="post">
							<table class="table2">
								<tr>
									<th><input type="checkbox" id='chkAll' name='chkAll'>序号</th>
									<th>作品Id</th>
									<th>销售主体Id</th>
									<th>销售名称</th>
									<th>作者笔名</th>
									<th>创建时间</th>
									<th>创建人</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>排序值</th>
									<th>状态</th>
									<th>上下架</th>
								</tr>
								<#assign i = 0> <#if (pageFinder?size>0)> <#list pageFinder.data
								as content> <#assign i = i+1> <#if i%2 == 0>
								<tr style="background-color: #E4EAF6;"
									onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})"
									id="row_${i}">
									<#else>
									<tr onmouseover="overCurrent(${i})"
										onmouseout="outCurrent(${i})" id="row_${i}">
										</#if>
										<input type="hidden" id="cId" name="cId"
											value="${content.contentId?c}">
										<td><input type="checkbox" id="contentId"
											name="contentId" value="${content.contentId?c}">${i}</td>
										<td>${content.media.mediaId?c}</td>
										<td>${content.saleId?c}</td>
										<td>${content.saleName}</td>
										<td><#if content.media.authorPenname??>${content.media.authorPenname}</#if></td>
										<td><#if
											content.creationDate??>${content.creationDate?substring(0,19)}</#if></td>
										<td><#if content.creator??>${content.creator}</#if></td>
										<td><#if content.startDate??>${content.startDate?substring(0,19)}</#if></td>
										<td><#if content.endDate??>${content.endDate?substring(0,19)}</#if></td>
										<td><input type="text" id="orderValue" name="orderValue"
											size="2" contentId="${content.contentId?c}" editable="true"
											value="<#if content.orderValue??>${content.orderValue?c}</#if>"></input></td>
										<td><#if content.status??> <#if
											'${content.status}'=='${force_valid}'>强制有效 <#elseif
											'${content.status}'=='${force_invalid}'>强制无效 <#elseif
											'${content.status}'=='${normal}'>正常显示 <#else>无效 </#if> </#if></td>
											
									<td>
									<#if content.mediaSale??>
										<#if content.mediaSale.shelfStatus??>
										<#if '${content.mediaSale.shelfStatus}'=='${on_shelf}'>上架 <#else>下架</#if>
										</#if>
									</#if>
									</td>
									</tr>
									</#list> </#if>
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

<#include "../../common/common-css.ftl">
<script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script>
<#include "../../common/common-js.ftl"> 
<link href="/style/toolbar/core.css" rel="stylesheet" type="text/css" />
<link href="/style/toolbar/toolbar.css" rel="stylesheet" type="text/css" />
<script src="/script/toolbar.js" type="text/javascript"></script>
<script type="text/javascript">
	var toolbar;
    //js的注释与html的注释放开,再看一下效果
    $(document).ready(function(){
      toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加作品',
          bodyStyle : 'new',
          useable : 'T',
          title:'添加作品',
          handler : function(){
        	  var columnCode = $('#columnCode').val();
		  		if(columnCode==null){
		  			alert("请先选择左侧栏目,单击左侧栏目");
		  			return false;
		  		}
		  		window.location='/column/sales/query.go?columnId='+$('#columnId').val()+'&columnName='+$('#columnId').attr('colName')
		  				+"&columnCode="+$('#columnCode').val()+"&mediaCategoryIds="+$('#categoryId').val()
		  				+"&path="+$('#path').val();
		  		return true;
          }
        },'-',{
            type : 'button',
            text : '删除',
            bodyStyle :'delete',
            useable : 'T',
            title:'删除此记录',
            handler : function(){
             if(getSelSalesId().length==0){
    			  alert("请先选择需要删除的销售主体");
    			  return false;
    			}
    		 var flag = window.confirm("你确认删除该销售主体?");
    		   if(flag){
    				$('#listform').attr('action','/column/content/delete.go?columnCode='+$('#columnCode').val());
    				$('#listform').submit();
    			}
              }
            },'-',{
            	 type : 'button',
                 text : '更新缓存',
                 bodyStyle :'send',
                 title:'将修改变化推送到客户端',
                 handler : function(){
                	 $.ajax({
                			type:"POST",
                			url:"/cache/column/clearcache.go",
                			data: "code="+$('#columnCode').val(),
                			dataType:"json",
                			success: function(msg) {
                				   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
                					   alert("更新缓存失败!");
                				   }else{
                					   alert("更新缓存成功!");
                				   }
                			}
                		});
              }
            
          },'-',{
         	  type : 'button',
        	  text : '保存排序',
        	  bodyStyle : 'order',
         	  useable : 'T',
         	  title:'保存排序',
         	  handler : function(){
         		 if(getSelSalesId().length==0){
 					alert("请先选择需要保存排序的销售主体");
 					return false;
 				}
 				$('#listform').attr('action','/column/content/updateorder.go?columnCode='+$('#columnCode').val());
 				$('#listform').submit();
        	  }
        },'-',{
          type : 'button',
          text : '设置有效期',
          bodyStyle : 'date',
          useable : 'T',
          handler : function(){
        	  if(getSelSalesId().length==0){
					alert("请先选择需要相应的销售主体");
					return false;
				}
				$.dialog({title:'设置有效期',top:'10%',content:'url:/column/content/setdate.go?contentIds='+getSelSalesId().join(',')+'&columnCode='+$('#columnCode').val(),
			   		icon:'succeed',width:500,height:150,lock:true
			    });
          }
        },'-',{
          type : 'button',
          text : '操作',
          bodyStyle : 'op',
          useable : 'T',
          handler:function(){
        	  if(getSelSalesId().length==0){
					alert("请先选择需要相应的销售主体");
					return false;
				}
				$.dialog({title:'设置状态',top:'10%',content:'url:/column/content/editstatus.go?contentIds='+getSelSalesId().join(',')+'&columnCode='+$('#columnCode').val(),
			   		icon:'succeed',width:500,height:100,fixed:false,lock:true
			   		
			    });
          }
        }],
        active : 'ALL'//激活哪个
      });
	  toolbar.render();
	  toolbar.genAZ();
    });
    </script>

<script type="text/javascript">
function checkQuery(){
	var mediaId = $('#mediaId').val();
	var saleId = $('#saleId').val();
	if(isNaN(mediaId)){
		alert("作品Id必须是数字");
		return false;
	}
	if(isNaN(saleId)){
		alert("销售主体Id必须是数字");
		return false;
	}
	return true;
}
//获取所有选中销售主体的编号
var getSelSalesId =function(){
	var ids = [];
	 $('input[name="contentId"]:checked').each(function(){    
		 ids.push($(this).val());   
	 });
	 return ids;
};
	$(document).ready(function(){
			$('#chkAll').click(function(){
				if(this.checked){
					 $('[name=contentId]:checkbox').attr("checked", true);
				}else{
					 $('[name=contentId]:checkbox').attr("checked", false);
				}
			});
	});
  	  function hintDelete(){
  		  var hasson = $('#deleteBtn').attr('hasson');
  		  if(hasson==1){
  			  alert("请先删除子栏目");
  			  return false;
  		  }
  		 var isdelete =  window.confirm("将删除此栏目下所有子栏目,您确认删除该栏目吗?");
  		 if(isdelete){
  		  return true;
  		 }
  	  }
  	$(function(){
	    $('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize?c},
			current_page: ${pageFinder.pageNo - 1},
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/column/content/content.go?pageIndex=__id__&'+$("#query").serialize())
	    });
   	});
  	</script>
</html>
