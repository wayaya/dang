<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../common/common-css.ftl">
	<script src="/bootstrap/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var condition ="";
		var lastForm ="";
		
		function makecondition(){
			var title = $('#title').val();
			
			if(title && title.length  > 0){
				condition = condition + "&title="+title;
			}
			
			var parentId = $('#parentId').val();
			
			if(parentId && parentId.length  > 0){
				condition = condition + "&parentId="+parentId;
			}
			
			var authorPenname = $('#authorPenname').val();
			if(authorPenname.length  > 0){
				condition = condition + "&authorPenname="+authorPenname;
			}
			var isFull = $("#isFull  option:selected").val()
			if(isFull.length  > 0){
				condition = condition + "&isFull="+isFull;
			}
			
			
			var shelfStatus = $("#shelfStatus  option:selected").val();
			
			if(shelfStatus.length  > 0){
				condition = condition + "&shelfStatus="+shelfStatus;
			}
			var isVip = $("#isVip  option:selected").val()
			
			if(isVip.length  > 0){
				condition = condition + "&isVip="+isVip;
			}
			var uid = $("#uid").val()
			if(uid.length  > 0){
				condition = condition + "&uid="+uid;
			}
			
			var isShow = $("#isShow").val()
			if(isShow.length  > 0){
				condition = condition + "&isShow="+isShow;
			}
			
			var creationDateStart = $("#creationDateStart").val()
			if(creationDateStart.length  > 0){
				condition = condition + "&creationDateStart="+creationDateStart;
			}
			var creationDateEnd = $("#creationDateEnd").val()
			if(creationDateEnd.length  > 0){
				condition = condition + "&creationDateEnd="+creationDateEnd;
			}
			
			var lastModifyDateStart = $("#lastModifyDateStart").val()
			if(lastModifyDateStart.length  > 0){
				condition = condition + "&lastModifyDateStart="+lastModifyDateStart;
			}
			
			var lastModifyDateEnd = $("#lastModifyDateEnd").val()
			if(lastModifyDateEnd.length  > 0){
				condition = condition + "&lastModifyDateEnd="+lastModifyDateEnd;
			}
			var cpCode = $("#cpCode option:selected").val()
			if(cpCode.length  > 0){
				condition = condition + "&cpCode="+cpCode;
			}
			
			var picCdnStatus = $("#picCdnStatus option:selected").val()
			if(picCdnStatus.length  > 0){
				condition = condition + "&picCdnStatus="+picCdnStatus;
			}
			return condition;
		}
		
	   	function searchActivityTypeList(){
	   		var mediaId = $('#mediaId').val();
	   		var reg = new RegExp(/[0-9]{1,}/);
	   		
	   		if($.trim(mediaId).length>0 && !reg.test(mediaId)){
	   			alert('作品ID只能输入数字');
	   			return false;
	   		}
	   		$('#activity_type_list_form').submit();
	   	}
	   	
	   	
	   	function upShelf(mediaId,title,picCdnStatus){
	   		//判断是否在敏感作品中
	   		var flag = false;
	   		$.ajax({ 
	          type : "post", 
	          url : "/media/isIllMedia.go", 
	          data : "mediaId=" + mediaId, 
	          async : false, 
	          success : function(data){ 
	            if(data.result  == 'failure'){
		   			alert('敏感作品,不可上架!');
		   			flag = false;
		   			return;
				}else{
					flag = true;
				}
	          } 
	         }); 
	   		 if(flag){
	   			if(confirm("确定要上架作品《"+title+"》吗？")){
	   				$.ajax({ 
			          type : "post", 
			          url : '/media/update.go?shelfStatus=1&redirect=1&mediaId='+mediaId, 
			          async : false, 
			          success : function(data){ 
			          	lhgdialog.tips("上架作品成功！",2,"success.gif",goPage);
			          },
			          error: function(data) {
			          	lhgdialog.tips("上架作品失败！",2,"error.gif",goPage);
			          }
			        }); 
	   			}   			
	   		 }
	   	 }
	   	function downShelf(mediaId,title,desc){
	   		desc = $.trim(desc);
	   		if(desc == ""){
	   			alert("下架原因不能为空！");
	   			return false;
	   		}
	   		if(confirm("确定要下架作品《"+title+"》吗？")){
	   			$.post("/media/getSaleByMediaId.go", {mediaId:mediaId},
			   function(data){
			   		if(data!=''){
			   			alert('请先下架以下销售主体:'+data);
			   			return;
			   		}
			   		$.ajax({ 
			          type : "post", 
			          url : '/media/update.go?shelfStatus=0&redirect=1&mediaId='+mediaId+'&dsDesc='+encodeURIComponent(desc), 
			          async : false, 
			          success : function(data){ 
			          	lhgdialog.tips("下架作品成功！",2,"success.gif",goPage);
			          },
			          error: function(data) {
			          	lhgdialog.tips("下架作品失败！",2,"error.gif",goPage);
			          }
			        }); 
	   				
			   }, "text");
	   		}	   		
	   	}
	   	
	   	function catetorySign(mediaId){
	   		$.dialog({id:"catetoryBaseDialog",title:'分类标签',content:'url:/media/catetorySign.go?mediaId='+mediaId,
	   			icon:'succeed',width:600,height:120,fixed:false,lock:true,zIndex:100,button: [
	        {
	            name: '保存',
	            callback: function () {
	            	var cateIds = $.dialog.list['catetoryBaseDialog'].content.$("#cateIds").val();
		   			if(cateIds.length == 0){
		   				alert('分类不能为空！');
		   				return false;
		   			}
		   			var signNames = $.dialog.list['catetoryBaseDialog'].content.$("#signNames").val();
		   			if(signNames.length == 0){
		   				alert('标签不能为空！');
		   				return false;
		   			}
	            
	            $.ajax({ 
		          type : "post", 
		          url : "/media/saveCateSign.go", 
		          data : $.dialog.list['catetoryBaseDialog'].content.$('#activity_type_add_form').serialize(), 
		          async : false, 
		          success : function(data){ 
		          	lhgdialog.tips("保存分类标签成功！",2,"success.gif");
		          },
		          error: function(data) {
		          	lhgdialog.tips("保存分类标签失败！",2,"error.gif"); 
		          }
		        }); 
	            return true;
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
	   	
	   	function downShelfView(mediaId,title){
	   		$.dialog.prompt('请输入下架原因',
			    function(val){
			        downShelf(mediaId,title,val);
			    },
			    ''
			);
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
					<h3>作品管理&gt;&gt;作品管理列表</h3>
					<div class="mrdiv">
					<table >
		      		<form action="/media/list.go" method="post" id="activity_type_list_form">
			      		<input type="hidden" name="parentId" id="parentId" value="<#if parentId??>${parentId}</#if>">
	        			<tr style="height: 30px;">
	        				<td style="width:50px; text-align:right">
								作品ID：
							</td>
							<td style="width:60px; text-align:left">
								<input type="text" name="mediaId" id="mediaId" value="<#if media??><#if media.mediaId??>${media.mediaId?c}</#if></#if>" style="">
							</td>
							<td style="width:50px; text-align:right">
								书名：
							</td>
							<td style="width:60px; text-align:left">
								<input type="text" name="title" id="title" value="<#if media??><#if media.title??>${media.title}</#if></#if>">
							</td>
							<td style="width:50px; text-align:right">
								作家笔名：
							</td>
							<td style="width:60px; text-align:left">
								<input type="text" name="authorPenname" id="authorPenname" value="<#if media??><#if media.authorPenname??>${media.authorPenname}</#if></#if>">
							</td>
							<td style="width:50px; text-align:right">
								下架状态：
						 	</td>
						 	<td style="width:60px; text-align:left">
								<select name="shelfStatus" id="shelfStatus">
						 			<option value="">全部</option>
						 			<option value="0" <#if media??><#if media.shelfStatus??><#if (media.shelfStatus == 0)>selected = "selected" </#if></#if></#if>>已下架</option>
						 			<option value="1"  <#if media??><#if media.shelfStatus??><#if (media.shelfStatus == 1)>selected = "selected" </#if></#if></#if>>已上架</option>
						 		</select>
						 	</td>
						 	<td style="width:50px; text-align:right">
								完结状态：
							</td>
							<td style="width:60px; text-align:left">
								<select name="isFull" id="isFull">
						 			<option value="">全部</option>
						 			<option value="0" <#if media??><#if media.isFull??><#if (media.isFull == 0)>selected = "selected" </#if></#if></#if>>连载中</option>
						 			<option value="1"  <#if media??><#if media.isFull??><#if (media.isFull == 1)>selected = "selected" </#if></#if></#if>>已完结</option>
						 		</select>
							</td>
						</tr>
						<tr style="height: 30px;">
							
							<td style="text-align:right">
								UID：
							</td>
							<td style="text-align:left">
								<input type="text" name="uid" id="uid" value="<#if media??><#if media.uid??>${media.uid}</#if></#if>">
							</td>
							<td style="text-align:right">
								收费状态：
						 	</td>
						 	<td style="text-align:left">
								<select name="isVip" id="isVip">
						 			<option value="">全部</option>
						 			<option value="0" <#if media??><#if media.isVip??><#if (media.isVip == 0)>selected = "selected" </#if></#if></#if>>免费</option>
						 			<option value="1"  <#if media??><#if media.isVip??><#if (media.isVip == 1)>selected = "selected" </#if></#if></#if>>VIP</option>
						 		</select>
						 	</td>
							<td style="text-align:right">
								显示状态：
						 	</td>
						 	<td style="text-align:left">
								<select name="isShow" id="isShow">
						 			<option value="">全部</option>
						 			<option value="0" <#if media??><#if media.isShow??><#if (media.isShow == 0)>selected = "selected" </#if></#if></#if>>隐藏</option>
						 			<option value="1"  <#if media??><#if media.isShow??><#if (media.isShow == 1)>selected = "selected" </#if></#if></#if>>显示</option>
						 		</select>
						 	</td>
						 	<td style="text-align:right">
								CP名称：
						 	</td>
						 	<td style="text-align:left">
								<select name="cpCode" id="cpCode">
						 			<option value="">全部</option>
						 			<option value="zongheng" <#if media??><#if media.cpCode??><#if (media.cpCode == "zongheng")>selected = "selected" </#if></#if></#if>>纵横</option>
						 			<option value="jinjiang" <#if media??><#if media.cpCode??><#if (media.cpCode == "jinjiang")>selected = "selected" </#if></#if></#if>>晋江</option>
						 			<option value="uploadbycustomer" <#if media??><#if media.cpCode??><#if (media.cpCode == "uploadbycustomer")>selected = "selected" </#if></#if></#if>>用户上传</option>
						 			<option value="520tingshu" <#if media??><#if media.cpCode??><#if (media.cpCode == "520tingshu")>selected = "selected" </#if></#if></#if>>520听书网</option>
						 			<option value="haokan5" <#if media??><#if media.cpCode??><#if (media.cpCode == "haokan5")>selected = "selected" </#if></#if></#if>>好看听书网</option>
						 			<option value="lanrents" <#if media??><#if media.cpCode??><#if (media.cpCode == "lanrents")>selected = "selected" </#if></#if></#if>>懒人听书</option>
						 		</select>
						 	</td>
						 	<td style="text-align:right">
								图片上传CDN：
						 	</td>
						 	<td style="text-align:left">
						 		<select name="picCdnStatus" id="picCdnStatus">
						 			<option value="">全部</option>
						 			<option value="0" <#if media??><#if media.picCdnStatus??><#if (media.picCdnStatus == 0)>selected = "selected" </#if></#if></#if>>未上传</option>
						 			<option value="1" <#if media??><#if media.picCdnStatus??><#if (media.picCdnStatus == 1)>selected = "selected" </#if></#if></#if>>已上传</option>
						 		</select>
						 	</td>
						</tr>
						
						<tr style="height: 30px;">
						 	<td style="text-align:right">
								创建时间：
						 	</td>
						 	<td style="text-align:left" colspan=3>
								<input type="text" style="width: 150px;" type="text" name="creationDateStart"  value="<#if media??><#if media.creationDateStart??>${media.creationDateStart}</#if></#if>" class="Wdate"   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'creationDateEnd\')}'})" id="creationDateStart" >
								到
								<input type="text" style="width: 150px;" type="text" name="creationDateEnd" class="Wdate"  value="<#if media??><#if media.creationDateEnd??>${media.creationDateEnd}</#if></#if>"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'creationDateStart\')}'})" id="creationDateEnd">
						 	</td>
						 	<td style="text-align:right">
								更新时间：
						 	</td>
						 	<td style="text-align:left" colspan=3>
								<input type="text" style="width: 150px;" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'lastModifyDateEnd\')}'})" name="lastModifyDateStart" id="lastModifyDateStart" readonly="readonly" value="<#if media??><#if media.lastModifyDateStart??>${media.lastModifyDateStart}</#if></#if>">
								到
								<input class="Wdate" style="width: 150px;" type="text" name="lastModifyDateEnd"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'lastModifyDateStart\')}'})" id="lastModifyDateEnd" readonly="readonly" value="<#if media??><#if media.lastModifyDateEnd??>${media.lastModifyDateEnd}</#if></#if>">
						 	</td>
						 	<td>
						 		&nbsp;
						 	</td>
						 	<td style="text-align:left ">
								<button  onclick="javascript:return searchActivityTypeList();">&nbsp;&nbsp;查&nbsp;询&nbsp;&nbsp;</button>
						 	</td>
						</tr>
				    </form>
				    </table>
				    </div>
				    <div>
				    <table class="table2">
		            	<tr>
		                    <th style="width:5%; height:28px;">作品ID</th>
		                    <th style="width:10%">书名</th>
		                    <th style="width:5%">封面</th>
		                    <th style="width:10%">UID</th>
		                    <th style="width:5%">章节数</th>
		                    <th style="width:5%">作家笔名</th>
		                    <th style="width:7%">CP名称</th>
		                    <th style="width:5%">完结状态</th>
		                    <th style="width:5%">是否VIP</th>
		                    <th style="width:10%">是否支持全本购买</th>
		                    <th style="width:7%">创建时间</th>
		                    <th style="width:7%">更新时间</th>
		                    <th >操作</th>
	               	    </tr>
	               	    <#assign i = 0>
	               	 	<#if pageFinder.data??>
			    			<#list pageFinder.data as media>
			    				<#assign i = i+1>
			    				<#if i%2 == 0>
			    					<tr style="background-color: #E4EAF6;" onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				<#else>
			    					<tr onmouseover="overCurrent(${i})" onmouseout="outCurrent(${i})" id="row_${i}">
			    				</#if>
					    			<td><#if media.mediaId??>${media.mediaId?c}</#if></td>
					    			<td><#if media.title??>${media.title}</#if></td>
						      		<td><#if media.coverPic??>
						      		<img width="35" height="50" src="${picPath}${media.coverPic}?time=${time?c}"/>
						      		</#if></td>
						      		<td><#if media.uid??>${media.uid}</#if></td>
						      		<td><#if media.chapterCnt??>${media.chapterCnt}</#if></td>
						      		<td><#if media.authorPenname??>${media.authorPenname}</#if></td>
						      		<td><#if media.providerName??>${media.providerName}<#else>无</#if></td>
						      		<td><#if media.isFull??><#if (media.isFull == 1)>已完结 <#else>连载中</#if></#if></td>
						      		<td><#if media.isVip??><#if (media.isVip == 1)>是 <#else>否</#if></#if></td>
						      		<td><#if media.isSupportFullBuy??>
						      			<#if (media.isSupportFullBuy == 1)>是 <#else>否</#if>
						      		</#if></td>
						      		<td><#if media.creationDate??>${media.creationDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		<td><#if media.lastModifyDate??>${media.lastModifyDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						      		
						      		<td>
										<#if userSessionInfo?? && userSessionInfo.f['203']?? >
					      				<a href="javascript:parent.location='/media/toBaseInfo.go?mediaId=${media.mediaId?c}'">【基本信息】</a>&nbsp;
					      				</#if>
										<#if userSessionInfo?? && userSessionInfo.f['205']?? >
											<a href="#" onclick="catetorySign('${media.mediaId?c}')">【分类标签】</a>
					      				</#if>
										<#if userSessionInfo?? && userSessionInfo.f['206']?? >
											<a href="javascript:location='/media/toChapterVolume.go?mediaId=${media.mediaId?c}'">【分卷章节】</a>
										</#if>
										<#if userSessionInfo?? && userSessionInfo.f['204']?? >
					      					<#if media.shelfStatus??><#if (media.shelfStatus == 0)>
						      					<a href="javascript:upShelf(${media.mediaId?c},'<#if media.title??>${media.title}</#if>','${media.picCdnStatus}');">【上架】</a>&nbsp;
						      				</#if></#if>
						      				<#if media.shelfStatus??><#if (media.shelfStatus == 1)>
						      					<a href="javascript:downShelfView(${media.mediaId?c},'<#if media.title??>${media.title}</#if>');">【下架】</a>&nbsp;
						      				</#if></#if>
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
  <script>
	$(function(){
		$('.pagination').pagination(${pageFinder.rowCount?c}, {
			items_per_page: ${pageFinder.pageSize},
			current_page: ${pageFinder.pageNo?c} - 1,
			prev_show_always:false,
			next_show_always:false,
			link_to: encodeURI('/media/list.go?page=__id__'+makecondition())
	    });
	});
	
	function goPage(){
		var currentPage = $('#currentPage').val();
		if(currentPage > 0){
			window.location.href='/media/list.go?page='+currentPage+condition;
		}else{
			alert("页数不合法");
		}
		
	}
	
  </script>
</html>
