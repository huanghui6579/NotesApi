package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 软件的版本信息
 * @author huanghui-iri
 * @date 2016年11月16日 下午5:53:23
 */
public class VersionInfo implements Serializable {
	private static final long serialVersionUID = -1881931781373622924L;
	
	private int id;
	
	/**
	 * 更新的记录
	 */
	private String content;
	
	/**
	 * 版本号
	 */
	private int versionCode;
	
	/**
	 * 版本名称
	 */
	private String versionName;
	
	/**
	 * 用户的系统平台，0:Android, 1:IOS
	 */
	private int platform = Platform.PLATFORM_ANDROID;
	
	/**
	 * 更新时间
	 */
	private Date createTime;
	
	/**
	 * 是否是里程碑
	 */
	private boolean isMilestone;
	
	/**
	 * 软件包的大小
	 */
	private long size;
	
	/**
	 * 软件版本的本地路径
	 */
	private String localPath;
	
	/**
	 * 软件的hash值-MD5
	 */
	private String hash;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isMilestone() {
		return isMilestone;
	}

	public void setMilestone(boolean isMilestone) {
		this.isMilestone = isMilestone;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "VersionInfo [id=" + id + ", content=" + content + ", versionCode=" + versionCode + ", versionName="
				+ versionName + ", platform=" + platform + ", createTime=" + createTime + ", isMilestone=" + isMilestone
				+ ", size=" + size + ", localPath=" + localPath + ", hash=" + hash + "]";
	}
}
