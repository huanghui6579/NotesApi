package com.yunxinlink.notes.api.model;

import java.io.Serializable;

/**
 * 反馈的附件信息
 * @author huanghui-iri
 * @date 2016年11月16日 下午2:18:59
 */
public class FeedbackAttach implements Serializable {
	private static final long serialVersionUID = -4913682673674937691L;
	
	/**
	 * 主键
	 */
	private int id;
	
	/**
	 * 关联反馈的ID
	 */
	private int feedbackId;
	
	/**
	 * 文件名
	 */
	private String filename;
	
	/**
	 * 本地的存放路径
	 */
	private String localPath;
	
	/**
	 * 文件的mime类型
	 */
	private String mime;
	
	/**
	 * 文件的大小
	 */
	private long size;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "FeedbackAttach [id=" + id + ", feedbackId=" + feedbackId + ", filename=" + filename + ", localPath="
				+ localPath + ", mime=" + mime + ", size=" + size + "]";
	}
}
