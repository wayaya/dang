<script type="text/javascript">
	
	function addFunc(e){
		e.preventDefault();
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/functionality/save.go",
			   async: false,
			   cache: false,
			   data: $('#myform').serialize()+"&timestamp="+new Date().getTime(),
			   dataType:"json",
			   success: function(msg){
				   var newNode = msg;
				   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
					   alert("添加失败，"+msg.errorMessage);
				   }else{
					   
					   //若是添加根节点，设置父node为null
					   if(newNode.pId == '0'){
						   getTree().addNodes(null, newNode);
					   }else{
						   getTree().addNodes(getSelectedNode(), newNode);
					   }
					  
					   var newTreeNode = getTree().getNodesByParam('id', newNode.id )[0];
					   getTree().selectNode(newTreeNode);
					   clickSelectedNode();
				   }
			   }
			});
	}
	
	$("#addFuncButton").click(addFunc);
	
</script>
<div id="mainBd3">
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="grid" align="center" valign="top">
		<br/>
			<h4>添加权限</h4>
		<br/>
		<form id="myform" action="${rc.contextPath}/functionality/save.go" method="post">
			<table class="formtable1" >
  				<#include "form_include.ftl">
			</table>
			<br/>
			
			<#if userSessionInfo?? && userSessionInfo.f['7']?? >
			<input class="button1 blue" id="addFuncButton" name="addFuncButton" type="submit" value="提交" />
			</#if>
			
			<#if functionality.parentId?? && functionality.parentId!=0>
				<button class="button1 blue" type="button" onclick="clickSelectedNode()">取消</button>
			</#if>
		</form>
		</td>
	</tr>
</table>
</div>
</body>
</html>
