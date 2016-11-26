package com.yunxinlink.notes.api.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yunxinlink.notes.api.model.State;
import com.yunxinlink.notes.api.util.Constant;

/**
 * 重置密码的dto
 * @author huanghui-iri
 * @date 2016年11月14日 上午10:43:24
 */
@JsonInclude(Include.NON_NULL)
public class PasswordResetInfoDto {
	/**
	 * 主见
	 */
	private Integer id;
	
	/**
	 * 用户的sid
	 */
	private String userSid;
	
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
	
	/**
	 * 用户的状态
	 */
	private Integer userState;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getValidataCode() {
		return validataCode;
	}

	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}

	public Timestamp getOutDate() {
		return outDate;
	}

	public void setOutDate(Timestamp outDate) {
		this.outDate = outDate;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	
	/**
	 * 检查用户的状态,true:可用
	 * @return
	 */
	public boolean checkUserState() {
		return userState == null || userState == State.NORMAL;
	}
	
	/**
	 * 生成密钥：格式为：userSid + $ + account + $ + date + secretKey
	 * @return
	 */
	public String generateKey(long date) {
		return userSid + Constant.TAG_KEY_SPLITER + account + Constant.TAG_KEY_SPLITER + date + Constant.TAG_KEY_SPLITER + validataCode;  
	}
}
