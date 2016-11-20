package com.yunxinlink.notes.api.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yunxinlink.notes.api.dto.VersionInfoDto;

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
	private Integer versionCode;
	
	/**
	 * 版本名称
	 */
	private String versionName;
	
	/**
	 * 用户的系统平台，0:Android, 1:IOS
	 */
	private Integer platform;
	
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

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
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
	
	/**
	 * 是否有内容，true：有更新日志内容
	 * @return
	 */
	public boolean checkContent() {
		return StringUtils.isNotBlank(content);
	}
	
	/**
	 * 转换成版本信息
	 * @return
	 */
	public VersionInfoDto convert2Dto() {
		VersionInfoDto infoDto = new VersionInfoDto();
		infoDto.setContent(content);
		if (createTime != null) {
			infoDto.setCreateTime(createTime.getTime());
		}
		infoDto.setHash(hash);
		infoDto.setMilestone(isMilestone);
		infoDto.setId(id);
		infoDto.setPlatform(platform);
		infoDto.setSize(size);
		infoDto.setVersionCode(versionCode);
		infoDto.setVersionName(versionName);
		
		if (StringUtils.isNotBlank(localPath)) {
			int index = localPath.lastIndexOf(File.separator);
			if (index != -1) {
				String filename = null;
				try {
					filename = localPath.substring(index + 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				infoDto.setFilename(filename);
			}
		}
		return infoDto;
	}

	@Override
	public String toString() {
		return "VersionInfo [id=" + id + ", content=" + content + ", versionCode=" + versionCode + ", versionName="
				+ versionName + ", platform=" + platform + ", createTime=" + createTime + ", isMilestone=" + isMilestone
				+ ", size=" + size + ", localPath=" + localPath + ", hash=" + hash + "]";
	}
}
