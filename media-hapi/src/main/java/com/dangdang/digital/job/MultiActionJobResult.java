package com.dangdang.digital.job;

import java.io.Serializable;

import com.dangdang.digital.utils.ResultSender;

/**
 * 
 * Description: 复合接口多线程job执行结果 All Rights Reserved.
 * 
 * @version 1.0 2015年1月10日 上午11:12:45 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class MultiActionJobResult implements Serializable {
	
	private static final long serialVersionUID = 4480342086186170625L;

	ResultSender tmpSender;

	String returnKey;

	public ResultSender getTmpSender() {
		return tmpSender;
	}

	public void setTmpSender(ResultSender tmpSender) {
		this.tmpSender = tmpSender;
	}

	public String getReturnKey() {
		return returnKey;
	}

	public void setReturnKey(String returnKey) {
		this.returnKey = returnKey;
	}

}
