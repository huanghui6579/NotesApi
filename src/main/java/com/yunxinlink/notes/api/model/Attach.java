package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 附件
 * @author huanghui1
 * @update 2016/7/6 16:01
 * @version: 0.0.1
 */
@JsonInclude(Include.NON_NULL)
public class Attach implements Serializable {
	private static final long serialVersionUID = 1293179669775569141L;
	/**
     * 附件类型为图片
     */
    public static final int IMAGE = 1;
    /**
     * 附件类型为语音
     */
    public static final int VOICE = 2;

    /**
     * 涂鸦文件
     */
    public static final int PAINT = 3;

    /**
     * 压缩文件
     */
    public static final int ARCHIVE = 4;

    /**
     * 视频文件
     */
    public static final int VIDEO = 5;

    /**
     * 其他普通文件
     */
    public static final int FILE = 6;
    
    /**
     * 主键
     */
    private Integer id;

    /**
     * sid
     */
    private String sid;

    /**
     * 笔记的sid
     */
    private String noteSid;

    /**
     * 用户的id
     */
    private Integer userId;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件类型
     */
    private Integer type;
    
    /**
     * 本地的存储路径，全路径
     */
    private String localPath;

    /**
     * 文件的描述
     */
    private String description;

    /**
     * 服务器的全路径
     */
    private String serverPath;

    /**
     * 删除状态
     */
    private int deleteState;

    /**
     * 文件的创建时间
     */
    private Date createTime;

    /**
     * 文件的修改时间
     */
    private Date modifyTime;

    /**
     * 文件的大小
     */
    private Long size;

    /**
     * 文件的mime类型
     */
    private String mimeType;
    
    /**
     * 文件的MD5值
     */
    private String hash;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getNoteSid() {
		return noteSid;
	}

	public void setNoteSid(String noteSid) {
		this.noteSid = noteSid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public int getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(int deleteState) {
		this.deleteState = deleteState;
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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
     * 附件的文件类型是否是图片类型，包含图片文件和涂鸦
     * @return
     */
    public boolean isImage() {
        return type != null && (type == IMAGE || type == PAINT);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attach other = (Attach) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attach [id=" + id + ", sid=" + sid + ", noteSid=" + noteSid + ", userId=" + userId + ", filename="
				+ filename + ", type=" + type + ", localPath=" + localPath + ", description=" + description
				+ ", serverPath=" + serverPath + ", deleteState=" + deleteState + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + ", size=" + size + ", mimeType=" + mimeType + ", hash=" + hash + "]";
	}

}
