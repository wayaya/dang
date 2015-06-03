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
		var condition ="&isCdn=${isCdn}";
		var dirId = "";
		<#if dirId??>
			dirId=${dirId};
			condition+="&dirId="+${dirId};
		</#if>
		<#if parentId??>
			condition+="&parentId="+${parentId};
		</#if>
		$(function(){
		    $('.pagination').pagination(${pageFinder.rowCount?c}, {
				items_per_page: ${pageFinder.pageSize?c},
				current_page: ${pageFinder.pageNo - 1},
				prev_show_always:false,
				next_show_always:false,
				link_to: encodeURI('/mediaResource/list.go?page=__id__'+condition)
		    });
	   	});
	   	
	   	function searchMaster(){
	   		$('#master_list_form').submit();
	   	}
	   	
	   	function delFile(fileId){
	   	
	   		if(window.confirm('你确定要删除吗？')){
				$.ajax({ 
	          type : "post", 
	          url : "/mediaResource/delFile.go", 
	          data : "fileId=" + fileId, 
	          async : false, 
	          success : function(data){ 
	          	eval("data = "+data);
	            if(data.success){
		   			location='/mediaResource/list.go?dirId='+'<#if dirId??>${dirId}</#if>'+"&isCdn=${isCdn}";
				}else{
					alert(data.msg);
				}
	          } 
	         });
			}
	   	}
	   	function upload(){
	   		if(dirId==0){
	   			alert("不能在根目录下上传东西！");
	   			return;
	   		}
	   		if(dirId==''){
	   			alert("请先在右边选择目录，再进行上传！");
	   			return;
	   		}
	   		$.dialog({id:"catetoryBaseDialog",
	   		title:'上传文件',lock:true,
	   		content:'url:/mediaResource/toUpload.go?'<#if dirId??>+'dirId='+${dirId}</#if>,
	   			width:470,height:120,
	   			ok: function () {
       					var flag =  $.dialog.list['catetoryBaseDialog'].content.check();
	           			if(!flag){
	            			return false;
	           			}
	           			
	           			var own = this;
	           			this.DOM.buttons[0].getElementsByTagName('input')[0].setAttribute('disabled','true');
	           			
	           			var flag = true;
	           				$.dialog.list['catetoryBaseDialog'].content.$('#activity_type_add_form').ajaxSubmit({
				  	 		async : false, 
                   			success: function (data) {
                   				eval("data= "+data);
                       			if(data.success){
                       				$.dialog.list['catetoryBaseDialog'].close();
                       				alert('操作成功!');
                       				<#if dirId??>
                       					location='/mediaResource/list.go?dirId='+'${dirId}'+"&isCdn=${isCdn}";
                       				</#if>
                       			}else{
                       				flag = false;
                       				own.DOM.buttons[0].getElementsByTagName('input')[0].removeAttribute('disabled');
                       				alert('操作失败!错误信息:'+data.msg);
                      	 		}
                       		    $.dialog.list['dis'].close();
                   },
                   		error: function (xhr) {
                       		alert("上传失败");
                       		flag = false;
                   		}
               		});
               		
	            	return false;
    			},
    			cancel: true
			});
	   	}
	</script>
</head>   
  <body>
	 <div class="page" style="width: 99%; height: 900px;"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
			<#if userSessionInfo?? && userSessionInfo.f['231']?? >
				<input type="button" value="上传文件" onclick="upload();">
			</#if>
			<font color="red">支持zip上传。zip上传时，请在左边树菜单中建好对应的文件夹再上传。</font>
			<table>
			<tr>
			<td style="width:100%;">
				<div class="m-r">
				    <div>
				    	<table class="table2">
			            	<tr>
			            		<th style="width:5%">序号</th>
			                    <th style="width:17%">CDN路径</th>
			                    <th style="width:6%">文件名称</th>
			                    <th style="width:3%">文件大小</th>
			                    <th style="width:15%">路径</th>
			                    <th style="width:7%">上传时间</th>
			                    <th style="width:8%">上传人</th>
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
				    			<td><#if cate.cdnPath??>${cate.cdnPath}</#if></td>
					      		<td><#if cate.fileName??>${cate.fileName}</#if></td>
					      		<td><#if cate.fileSize??>${cate.fileSize?c}</#if></td>
					      		<td><#if cate.dirPath??>${cate.dirPath}</#if></td>
					      		<td><#if cate.uploadDate??>${cate.uploadDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
					      		<td><#if cate.uploader??>${cate.uploader}</#if></td>
					      		<td>
				      				<a href="javascript:window.open('${cate.cdnPath}');">查看</a>
				      				<#if userSessionInfo?? && userSessionInfo.f['232']?? >
					      				<#if isCdn==0>
					      					<a href="javascript:delFile('${cate.fileId}');">删除</a>
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
  <#include "../common/common-js.ftl">
</html>
