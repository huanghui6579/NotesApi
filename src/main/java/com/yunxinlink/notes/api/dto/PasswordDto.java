package com.yunxinlink.notes.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 密码的dto
 * @author huanghui-iri
 * @date 2016年12月5日 下午8:24:35
 */
public class PasswordDto implements Serializable {
	private static final long serialVersionUID = 940169994332875741L;
	
	/**
	 * 用户的sid
	 */
	private String userSid;
	
	/**
	 * 原始密码
	 */
	private String oldPassword;
	
	/**
	 * 新密码
	 */
	private String newPassword;
	
	/**
	 * 确认新密码
	 */
	private String confirmPassword;

	public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	/**
	 * 是否有密码
	 * @return
	 */
	public boolean hasPassword() {
		return StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(confirmPassword);
	}
}
