package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用户实体
 * @author huanghui1
 *
 */
@JsonInclude(Include.NON_NULL)
public class User implements Serializable {
	private static final long serialVersionUID = -3297032486782428619L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户的唯一id
	 */
	private String username;
	
	/**
	 * 用户登录密码
	 */
	private String password;
	
	/**
	 * 用户昵称
	 */
	private String nickname;
	
	/**
	 * 用户电话，可登录用
	 */
	private String mobile;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 用户的唯一标识，手动生成
	 */
	private String sid;

	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 性别，0：未知，1：男，2：女
	 */
	private Integer gender;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 用户的状态
	 */
	private Integer state;
	
    /**
     * 头像的hash值
     */
    private String avatarHash;
    
    /**
     * 同步的状态
     */
    private Integer syncState;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatarHash() {
		return avatarHash;
	}

	public void setAvatarHash(String avatarHash) {
		this.avatarHash = avatarHash;
	}

	public Integer getSyncState() {
		return syncState;
	}

	public void setSyncState(Integer syncState) {
		this.syncState = syncState;
	}

	/**
	 * 检查该用户是否可用
	 * @return
	 */
	public boolean checkState() {
		return State.NORMAL == state;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", mobile=" + mobile + ", email=" + email + ", sid=" + sid + ", avatar=" + avatar + ", gender="
				+ gender + ", createTime=" + createTime + ", state=" + state + ", avatarHash=" + avatarHash
				+ ", syncState=" + syncState + "]";
	}
}
