package com.yunxinlink.notes.api.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.model.NoteInfo;

/**
 * 笔记的dto
 * @author tiger
 * @date 2016年10月6日 上午11:20:55
 */
public class NoteDto implements Serializable {
	private static final long serialVersionUID = -6445076440185638728L;

	/**
	 * 该笔记本下的笔记
	 */
	private List<NoteInfo> noteInfos;
	
	/**
	 * 笔记本
	 */
	private Folder folder;
	
	/**
	 * 用户的sid
	 */
	private String userSid;
	
	public List<NoteInfo> getNoteInfos() {
		return noteInfos;
	}

	public void setNoteInfos(List<NoteInfo> noteInfos) {
		this.noteInfos = noteInfos;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	/**
	 * 是否可用
	 * @return
	 */
	public boolean isEmpty() {
		return StringUtils.isEmpty(userSid) || noteInfos == null || noteInfos.size() == 0;
	}

	@Override
	public String toString() {
		return "NoteDto [noteInfos=" + noteInfos + ", folder=" + folder + ", userSid=" + userSid + "]";
	}

}