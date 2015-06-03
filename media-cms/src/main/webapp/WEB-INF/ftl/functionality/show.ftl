
<script type="text/javascript">
	
	function deleteFunc(e){
		e.preventDefault();
		
		if(confirm("是否将此权限删除?")){
			if(getSelectedNode().isParent){
				alert("请依次删除此节点的子节点，再继续操作");
			}else{
				
				$.ajax({
					   type: "POST",
					   url: "${rc.contextPath}/functionality/delete.go",
					   async: false,
					   cache: false,
					   data: {"functionalityId":getSelectedNode().id, parentId:(getSelectedNode().pId==null?0:getSelectedNode().pId), "timestamp":new Date().getTime()},
					   dataType:"text",
					   success: function(msg){
						   if(typeof msg.result != 'undefined' && msg.result == 'failure'){
							   alert("删除失败");
						   }else{
							   var selectedNode = getSelectedNode();
							   //删除根节点
							   if(selectedNode.pId == 0 || selectedNode.pId == null){
								   getTree().removeNode(selectedNode);
								   $(".content_wrap > .right").html('');
							   }else{
								   //删除一般节点
								   getTree().removeNode(selectedNode);
								   var parentNode = getNodeById(selectedNode.pId);
								   getTree().selectNode(parentNode);
								   clickSelectedNode();
							   }
							   
						   }
					   }
					});
			}
			
		}else{
			return;
		}
	}
	
	$("#addSubNode").click(function(e){
		$(".content_wrap > .right").load("${rc.contextPath}/functionality/create.go", {parentId: '${functionality.functionalityId?c}'}, function(){});
	});
	
	$("#editNode").click(function(e){
		$(".content_wrap > .right").load("${rc.contextPath}/functionality/edit.go", {id: '${functionality.functionalityId?c}'}, function(){});
	});
	
	$("#deleteNode").click(deleteFunc);
	
</script>

<div id="mainBd3">

<table  width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="grid" align="center" valign="top">
		<br/>
			<#if userSessionInfo?? && userSessionInfo.f['5']?? >
			<button id="addSubNode" class="button1 blue">添加子权限</button>
			</#if>
			<#if userSessionInfo?? && userSessionInfo.f['6']?? >
			<button id="editNode" class="button1 blue">编辑</button>
			</#if>
			<#if userSessionInfo?? && userSessionInfo.f['36']?? >
			<button id="deleteNode" class="button1 blue">删除</button> 
			</#if>
		<br/>
			<table class="formtable2">
				<tr>	
					<td class="field"  width="100px">ID:</td>	
					<td>${functionality.functionalityId?c}</td>
				</tr>
				<tr>	
					<td class="field">权限名称：</td>	
					<td>${functionality.name}</td>
				</tr>
				<tr>	
					<td class="field">Url Pattern：</td>	
					<td>${functionality.urlPattern}</td>
				</tr>
				
				<tr>	
					<td class="field">层级</td>	
					<td>${functionality.level}</td>
				</tr>
				<tr>	
					<td class="field">父节点ID：</td>	
					<td>${functionality.parentId?c}</td>
				</tr>
				<tr>	
					<td class="field">Path：</td>	
					<td>${functionality.path}</td>
				</tr>
				<tr>	
					<td class="field">是否是叶子节点（没有子节点）</td>	
					<td>${functionality.leaf?string('true','false')}</td>
				</tr>
				<tr>	
					<td class="field">创建者ID</td>	
					<td><#if functionality??><#if functionality.creator??>${functionality.creator}</#if></#if></td>
				</tr>
				<tr>	
					<td class="field">创建时间</td>	
					<td>${functionality.creationDate?string("yyyy-MM-dd HH:mm:ss")}</td>
				</tr>
				<tr>	
					<td class="field">修改者ID</td>	
					<td><#if functionality??><#if functionality.modifier??>${functionality.modifier}</#if></#if></td>
				</tr>
				<tr>	
					<td class="field">上次修改时间</td>	
					<td><#if functionality??><#if functionality.lastChangedDate??>${functionality.lastChangedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></#if></td>
				</tr>
				
			</table>
			<br/>
			<!-- <input class="btn" type="button" value="返回" onclick="history.back();"/> -->
		</td>
	</tr>
</table>
</div>
</body>

</html>