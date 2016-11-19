package com.yunxinlink.notes.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yunxinlink.notes.api.model.Platform;

/**
 * 软件的版本信息
 * @author huanghui-iri
 * @date 2016年11月16日 下午5:53:23
 */
@JsonInclude(Include.NON_NULL)
public class VersionInfoDto implements Serializable {
	private static final long serialVersionUID = -1881931781373622924L;
	
	private int id;
	
	/**
	 * 更新的记录
	 */
	private String content;
	
	/**
	 * 版本号
	 */
	private Integer versionCode;
	
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
	private long createTime;
	
	/**
	 * 是否是里程碑
	 */
	private boolean isMilestone;
	
	/**
	 * 软件包的大小
	 */
	private long size;
	
	/**
	 * 软件的hash值-MD5
	 */
	private String hash;
	
	/**
	 * 文件名
	 */
	private String filename;

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

	public Integer getPlatform() {
		return platform;
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * 是否有内容，true：有更新日志内容
	 * @return
	 */
	public boolean checkContent() {
		return StringUtils.isNotBlank(content);
	}
}
