package com.yunxinlink.notes.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 返回数据的实体，以json形式返回
 * @author huanghui1
 *
 * @param <T>
 */
@JsonInclude(Include.NON_NULL)
public class ActionResult<T> {
	public static final int RESULT_SUCCESS = 100;
	
	public static final int RESULT_FAILED = 200;
	
	/**
	 * 参数错误
	 */
	public static final int RESULT_PARAM_ERROR = 201;
	
	/**
	 * 状态-不可用
	 */
	public static final int RESULT_STATE_DISABLE = 202;
	
	/**
	 * 数据不存在
	 */
	public static final int RESULT_DATA_NOT_EXISTS = 203;
	
	/**
	 * 其他错误
	 */
	public static final int RESULT_ERROR = 204;
	
	/**
	 * 数据不等
	 */
	public static final int RESULT_NOT_EQUALS = 205;
	
	/**
	 * 数据重复了
	 */
	public static final int RESULT_DATA_REPEAT = 206;
	
	/**
	 * 已过期
	 */
	public static final int RESULT_OUT_DATE = 207;
	
	/**
	 * 用户密码校验失败
	 */
	public static final int RESULT_VALIDATE_FAILED = 208;
	
	/**
	 * token校验失败
	 */
	public static final int RESULT_TOKEN_UNAUTHOZIED = 401;
	
	/**
	 * 返回码
	 */
	private int resultCode = RESULT_ERROR;
	
	private T data;
	
	/**
	 * 结果描述语
	 */
	private String reason;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "ActionResult [resultCode=" + resultCode + ", data=" + data + ", reason=" + reason + "]";
	}
	
}
