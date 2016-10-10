package com.yunxinlink.notes.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yunxinlink.notes.api.model.Folder;


/**
 * 笔记本的dto
 * @author huanghui1
 * @date 2016年10月10日 下午4:10:24
 */
@JsonInclude(Include.NON_NULL)
public class FolderDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = 6617467033465584780L;

	/**
	 * 笔记本
	 */
	private Folder folder;

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return "FolderDto [folder=" + folder + ", limit=" + limit + ", offset=" + offset + "]";
	}
}
