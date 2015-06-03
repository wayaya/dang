function getTree(){
	return $.fn.zTree.getZTreeObj(treeContainer);
}

function getSelectedNode(){
	var treeObj = getTree();
	var nodes = treeObj.getSelectedNodes();
	return nodes[0];
}

function clickSelectedNode(){
	var selectedNode = getSelectedNode();
	clickNode(selectedNode.tId);
}

function getNodeById( id ){
	return getTree().getNodesByParam('id', id )[0];
}

function clickNode( tId ){
	$("#"+tId+"_a").trigger("click");
}