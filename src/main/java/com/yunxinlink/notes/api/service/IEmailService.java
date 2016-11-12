package com.yunxinlink.notes.api.service;

/**
 * 邮件发送的服务
 * @author tiger
 *
 */
public interface IEmailService {
	/**
	 * 向指定人发送密码重置的邮件
	 * @param toAddress 收件人的邮箱
	 * @return 是否发送成功
	 */
	public boolean sendEmail(String toAddress);
}
