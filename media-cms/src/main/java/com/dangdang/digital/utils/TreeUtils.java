package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.List;

import com.dangdang.digital.model.Functionality;
import com.dangdang.digital.view.ZTreeNode;

public class TreeUtils {

	
	public static ZTreeNode convertToZTreeNode(Functionality functionality){
		
		ZTreeNode ztreeNode = new ZTreeNode();
		ztreeNode.setId( functionality.getFunctionalityId() );
		ztreeNode.setName( functionality.getName() );
		ztreeNode.setOpen( !functionality.getLeaf() && functionality.getLevel() <= 3 );
		ztreeNode.setPId( functionality.getParentId() );
		
		return ztreeNode;
	}
	
	public static List<ZTreeNode> convertToZTreeNodeList(List<Functionality> funcList){
		
		List<ZTreeNode> ztreeNodeList = new ArrayList<ZTreeNode>();
		if( funcList==null ){
			return ztreeNodeList;
		}
		for( Functionality func : funcList ) {
			ztreeNodeList.add (convertToZTreeNode( func ) );
		}
		return ztreeNodeList;
	}
}
