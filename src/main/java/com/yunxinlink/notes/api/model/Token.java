package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * token
 * @author tiger
 * @date 2016年12月3日 下午5:13:46
 */
public class Token implements Serializable {

	private static final long serialVersionUID = -4684875913743628963L;
	
	/**
	 * token的id
	 */
	private String id;
	
	/**
	 * token的主题
	 */
	private String subject;
	
	/**
	 * token的签发者
	 */
	private String issuer;
	
	/**
	 * token的过期时间
	 */
	private Date expiration;
	
	/**
	 * token的字符串内容
	 */
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", subject=" + subject + ", issuer=" + issuer + ", expiration=" + expiration
				+ ", content=" + content + "]";
	}

	/**
	 * token是否过期了，true：过期了
	 * @return
	 */
	public boolean isExpired() {
		return expiration == null || expiration.before(new Date());
	}
}
