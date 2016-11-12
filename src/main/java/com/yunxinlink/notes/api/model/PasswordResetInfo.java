package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.yunxinlink.notes.api.util.Constant;

/**
 * 密码找回的信息
 * @author huanghui-iri
 * @date 2016年11月11日 下午4:29:43
 */
public class PasswordResetInfo implements Serializable {
	private static final long serialVersionUID = 4067944626006724016L;

	/**
	 * 主见
	 */
	private int id;
	
	/**
	 * 账号，目前主要是邮箱
	 */
	private String account;
	
	/**
	 * 校验码
	 */
	private String validataCode;
	
	/**
	 * 过期时间
	 */
	private Timestamp outDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Timestamp getOutDate() {
		return outDate;
	}

	public void setOutDate(Timestamp outDate) {
		this.outDate = outDate;
	}

	public String getValidataCode() {
		return validataCode;
	}

	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}
	
	/**
	 * 生成密钥：格式为：account + $ + date + secretKey
	 * @return
	 */
	public String generateKey(long date) {
		return account + Constant.TAG_KEY_SPLITER + date + Constant.TAG_KEY_SPLITER + validataCode;  
	}
}
