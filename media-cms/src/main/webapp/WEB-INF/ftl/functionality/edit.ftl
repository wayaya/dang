
<script type="text/javascript">
	function editFunc(e){
		e.preventDefault();
		
		$.ajax({
			   type: "POST",
			   url: "${rc.contextPath}/functionality/saveEdit.go",
			   async: false,
			   cache: false,
			   data: $('#myform').serialize()+"&timestamp="+new Date().getTime(),
			   dataType:"json",
			   success: function(msg){
				   var editedNode = msg;
				   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
					   alert("编辑失败，"+msg.errorMessage);
				   }else{
					   var editedTreeNode = getTree().getNodesByParam('id', editedNode.id )[0];
					   editedTreeNode.name = editedNode.name;
					   getTree().updateNode(editedTreeNode);
					   clickSelectedNode();
				   }
			   }
			});
	}
	
	$("#editFuncButton").click(editFunc);
</script>
<div id="mainBd3">
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="grid" align="center" valign="top">
		<br/>
			<h4>修改权限</h4>
		<br/>
		<form id="myform" action="${rc.contextPath}/functionality/save.go" method="post">
			<table class="formtable1" > 
  				<#include "form_include.ftl">
            </table>
			<br/>
			<#if userSessionInfo?? && userSessionInfo.f['8']?? >
			<input class="button1 blue" id="editFuncButton" name="editFuncButton" type="submit" value="提交" />
			</#if>
			<button class="button1 blue" type="button" onclick="clickSelectedNode()">取消</button>
		</form>
		</td>
	</tr>
</table>
</div>
</body>
</html>
