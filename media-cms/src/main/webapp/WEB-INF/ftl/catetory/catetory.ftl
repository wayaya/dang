
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/bootstrap/media/js/jquery-1.10.1.min.js"></script>

<style type="text/css">
article, aside, figure, footer, header, hgroup, 
  menu, nav, section { display: block; }
  .west{
    width:200px;
    padding:10px;
  }
  .north{
    height:100px;
  }
  
  .add{  
  		background: url(/bootstrap/media/image/add.png) no-repeat !important;  
	} 
	.del{  
  		background: url(/bootstrap/media/image/delete.png) no-repeat !important;  
	} 
	.edit{  
  		background: url(/bootstrap/media/image/comment_edit.png) no-repeat !important;  
	} 
</style>
</head>
<body>
<table>
<tr>
	<td style="width:30%;">
		<div id="tree">
	
		</div>
	</td>
	<td>
		<div>
			<iframe src="/catetory/list.go"></iframe>
		</div>
	</td>
</tr>


</table>
<div id="tabsMenu" class="easyui-menu" style="width:120px;">  
    <div name="add" class="add">添加子分类</div>  
    <div name="edit" class="edit">修改</div>  
    <div name="del" class="del">删除</div>
  </div> 
<div id="win" iconCls="add" title="添加" style="display:none;">  
<br>
<input type="hidden" name="id">
<input type="hidden" name="parentId">
    &nbsp;&nbsp;&nbsp;&nbsp;名称：<input name="name"><br><br>
   &nbsp;&nbsp;&nbsp;&nbsp;编码：<input name="code"><br><br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="save();" value="保存">
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="javascript:$('#win').window('close');" value="关闭">
</div>
<script type="text/javascript">
function treeDate(data){
	 $("#tree").tree({
	        data : data,
	        lines : true,
	        onContextMenu: function (e, title) {
	            e.preventDefault();
	            $("#tabsMenu").menu('show', {
	                left: e.pageX,
	                top: e.pageY
	            }).data("node", title);
	            id = title.id;
	        },
			onClick : function (node) {
			}
	    });	
}

function save(){
	var name = $("input[name='name']").val();
	var code = $("input[name='code']").val(); 
	var id = $("input[name='id']").val(); 
	var parentId = $("input[name='parentId']").val(); 
	if(name == '' || code == ''){
		alert('请填写名称和编码!');
		return;
	}
	var params = {name:name,code:code};
	if(id!=''){
		params['id'] = id;
	}
	if(parentId!=''){
		params['parentId'] = parentId;
	}
	var node;
	$.post("/catetory/add.go", params,
			   function(data){
			     if(data.success){
			    	 $('#win').window('close');
			    	 $.post("/catetory/tree.go", 
			  			   function(data){
			    		 treeDate(data);
			  			   });
			     }else{
			    	 alert(data.msg);
			     }
			   }, "json");
}

$(function () {
    //动态菜单数据
    var treeData = [{
            text : "分类",
            id:0
        }
    ];
    
    $.post("/catetory/tree.go", 
			   function(data){
    	treeDate(data);
			   });
    var id;
   
    //实例化树形菜单
    //在右边center区域打开菜单，新增tab
    function Open(text, url) {
        if ($("#tabs").tabs('exists', text)) {
            $('#tabs').tabs('select', text);
        } else {
            $('#tabs').tabs('add', {
                title : text,
                closable : true,
                content : text
            });
        }
    }
    
    //实例化menu的onClick事件
    $("#tabsMenu").menu({
        onClick : function (item) {
            click(this, item.name);
        }
    });
    
    //几个关闭事件的实现
    function click(menu, type) {
    	var node = $("#tabsMenu").data('node');
    	 $("input[name='id']").val('');
			 $("input[name='name']").val('');
			 $("input[name='code']").val('');
			$("input[name='parentId']").val('');
			
        if(type == 'add'){
        	 $('#win').window({   
        		 width:300,   
        		 height:200,   
        		 modal:true,
        		 onBeforeOpen:function(){
        			 document.getElementById('win').style.display='';
        			 $("input[name='parentId']").val(node.id);
        		 }
        	});         	
        }
        if(type == 'edit'){
        	$('#win').window({   
	       		 width:300,   
	       		 height:200,   
	       		 modal:true,
	       		 onBeforeOpen:function(){
	       			 document.getElementById('win').style.display='';
	       			 $("input[name='id']").val(node.id);
	       			 $("input[name='name']").val(node.text);
	       			 $("input[name='code']").val(node.code);
	       			$("input[name='parentId']").val(node.parentId);
	       		 }
       		}); 
        }
        
        if(type == 'del'){
        	$.post("/catetory/del.go?id="+node.id, 
     			   function(data){
        		if(data.success){
			    	 $.post("/catetory/tree.go", 
			  			   function(data){
			    		 		treeDate(data);
			  			   });
			     }else{
			    	 alert(data.msg);
			     }
        		
     			   },'json');
        }
    }});
</script>
</body>
</html>