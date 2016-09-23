package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方账号的信息
 * @author huanghui1
 *
 */
public class OpenApi implements Serializable {
	private static final long serialVersionUID = -1772231231897108658L;

	private Integer id;
	
	/**
	 * 第三方账号的用户id
	 */
	private String openUserId;
	
	/**
	 * 用户的 id，关联用户表的id
	 */
	private User user;
	
	/**
	 * 访问的token
	 */
	private String token;
	
	/**
	 * token的过期时间
	 */
	private Long expiresTime;
	
	/**
	 * 第三方账号的类型，1：微信，2：QQ，3：微博
	 */
	private Integer type;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Long expiresTime) {
		this.expiresTime = expiresTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOpenUserId() {
		return openUserId;
	}

	public void setOpenUserId(String openUserId) {
		this.openUserId = openUserId;
	}

	@Override
	public String toString() {
		return "OpenApi [id=" + id + ", openUserId=" + openUserId + ", user=" + user + ", token=" + token
				+ ", expiresTime=" + expiresTime + ", type=" + type + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + "]";
	}
}
