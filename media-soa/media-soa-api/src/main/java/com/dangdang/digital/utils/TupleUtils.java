package com.dangdang.digital.utils;

import java.io.Serializable;

/**
 * 
 * Description:返回结果包装器 
 * All Rights Reserved.
 * @version 1.0  2014年12月13日 下午4:21:35  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class TupleUtils {
	public static class ResultTwoTuple<A,L> implements Serializable{
		
		private static final long serialVersionUID = -8639447095254551736L;
		//总记录数
		public final A total;
		//Id List
		public final L listId;
		
		public ResultTwoTuple(A total,L listId){
			this.total = total;
			this.listId = listId;
		}
		
	}
}
