<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>公告内容列表</title>
	 <script type="text/javascript" src="/script/lhgdialog/lhgdialog.min.js?self=true"></script> 
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
				<h3>公告管理&gt;&gt;公告内容管理</h3>
				<div id="toolbar" title="工具条"></div>
				    <div>
				     <form id="listform" name="listform" method="post">
				    <table class="table2">
		            	<tr>
		                    <th>序号<input type="checkbox" id='chkAll' name='chkAll'></th>
		                    <th>标题</th>
		                    <th>URL</th>
		                    <th>开始时间</th>
		                    <th>结束时间</th>
		                    <th>操作者</th>
		                    <th>最后修改时间</th>
		                    <th>修改</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	    <#assign i = 0> 
	               	    <#if (pageFinder?size>0)>
	               	     <#list pageFinder.data　as notice> 
	               	     <#assign i = i+1>
								<#if i%2 == 0>
			    				<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td>
						      		<td>${i}</td>
						      		<td><#if notice.title??>${announcement.title}</#if></td>
						      		<td><#if notice.url??>${notice.url}</#if></td>
						      		<td><#if notice.startTime??>${notice.startTime}</#if></td>
						      		<td><#if notice.endTime??>${notice.endTime}</#if></td>
						      		<td><#if notice.modifer??>${notice.modifer}</#if></td>
						      		<td><#if notice.lastChangeTime??>${notice.lastChangeTime}</#if></td>
						      		<td><a href="#" onclick="javascript:editContent(${announcement.noticeId});" >修改</a></td>
						      	</tr>
				      		</#list>
				      	</#if>
				      	</#if>
		            </table>
		            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
  </body>
  	<#include "../common/common-js.ftl">
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
              text : '添加公告内容',
              bodyStyle : 'new',
              <#if RequestParameters["isParent"]??>
             	 useable : 'F',
             	 title:'非叶子节点不可添加公告',
              <#else>
       		 	  useable : 'T',
       		  	 title:'叶子节点可添加公告',
              </#if>
              handler : function(){
            	  $.dialog({title:'添加公告内容',content:'url:/announcements/addcontent.go?categoryCode='+$('#categoryCode').val(),
  			   		icon:'succeed',width:600,height:300,fixed:false,lock:true
  			    });
              }
            },'-',{
            type : 'button',
            text : '删除',
            bodyStyle : 'delete',
            title:'删除指定公告内容',
            useable : 'T',
            handler:function(){
            	var ids = getSelectedSalesId();
            	if(ids.length==0){
            		alert("请选择需要删除的公告内容");
            		return false;
            	}
            	$('#listform').attr('action','/announcements/deletecontent.go');
    			$('#listform').submit();
            }
          }],
        active : 'ALL'//激活哪个
      });
	  toolbar.render();
	  toolbar.genAZ();
    });
  //获取所有选中销售主体的编号
	var getSelectedSalesId =function(){
		var ids = [];
		 $('input[name="announcementId"]:checked').each(function(){    
			 ids.push($(this).val());   
		 });
		 return ids;
	};
	
	function editContent(id){
		 $.dialog({title:'修改公告内容',content:'url:/announcements/editcontent.go?announcementId='+id,
		   		icon:'succeed',width:600,height:300,fixed:false,lock:true
		    });
	}
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
 
	</html>
