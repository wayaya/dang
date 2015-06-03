package com.dangdang.digital.exception;

/**
 * API接口的异常.
 * 
 * @author yang.ming
 *
 */
public class ApiException extends Exception {
	
	private static final long serialVersionUID = -2445488256264617507L;
	
	private int code;

	public int getCode() {
		return code;
	}

	public ApiException(int code) {
		this.code = code;
	}
	
	public ApiException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ApiException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	/**
	 * api不存在.
	 */
	public static ApiException unknownApi() {
		return new ApiException(-1001, "API不存在");
	}
	
	/**
	 * token无效.
	 */
	public static ApiException invalidToken() {
		return new ApiException(-1002, "token无效");
	}
	
	/**
	 * 参数无效.
	 */
	public static ApiException invalidParams() {
		return new ApiException(-1003, "参数无效");
	}
	
	
	/**
	 * 参数无效.
	 */
	public static ApiException invalidPubKey() {
		return new ApiException(-1080, "传入公钥参数无效");
	}
	/**
	 * 参数无效.
	 */
	public static ApiException invalidParams(String param) {
		return new ApiException(-1003, param + ":参数无效");
	}
	
	/**
	 * 无操作权限.
	 */
	public static ApiException noPermission() {
		return new ApiException(-1004, "无操作权限");
	}
	
	/**
	 * 返回操作结果失败.
	 */
	public static ApiException responseFailed() {
		return new ApiException(-1005, "返回操作结果失败");
	}
	
}
