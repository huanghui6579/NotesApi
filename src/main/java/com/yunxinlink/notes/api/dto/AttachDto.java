package com.yunxinlink.notes.api.dto;

import java.util.Date;

import com.yunxinlink.notes.api.model.Attach;

/**
 * @author huanghui1
 * @date 2016年10月18日 上午10:07:23
 */
public class AttachDto {
	/**
     * sid
     */
    private String sid;

    /**
     * 笔记的sid
     */
    private String noteSid;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件类型
     */
    private int type;

    /**
     * 文件的描述
     */
    private String description;

    /**
     * 删除状态
     */
    private int deleteState;

    /**
     * 文件的创建时间
     */
    private long createTime;

    /**
     * 文件的修改时间
     */
    private long modifyTime;

    /**
     * 文件的大小
     */
    private long size;

    /**
     * 文件的mime类型
     */
    private String mimeType;
    
    /**
     * 文件的hash
     */
    private String hash;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(int deleteState) {
		this.deleteState = deleteState;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
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
	 * 转换成Attach
	 * @return
	 */
	public Attach convert2Attach() {
		Attach attach = new Attach();
		attach.setSid(sid);
		attach.setNoteSid(noteSid);
		attach.setDescription(description);
		attach.setFilename(filename);
		attach.setType(type);
		attach.setDeleteState(deleteState);
		attach.setHash(hash);
		attach.setMimeType(mimeType);
		if (createTime <= 0) {
			createTime = System.currentTimeMillis();
		}
		if (modifyTime <= 0) {
			modifyTime = System.currentTimeMillis();
		}
		attach.setCreateTime(new Date(createTime));
		attach.setModifyTime(new Date(modifyTime));
		return attach;
	}
	
	@Override
	public String toString() {
		return "AttachDto [sid=" + sid + ", noteSid=" + noteSid + ", filename=" + filename + ", type=" + type
				+ ", description=" + description + ", deleteState=" + deleteState
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", size=" + size + ", mimeType="
				+ mimeType + ", hash=" + hash + "]";
	}
}
