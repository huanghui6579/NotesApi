package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yunxinlink.notes.api.util.Constant;

/**
 * 笔记的基本信息
 * @author tiger
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class NoteInfo implements Serializable {
	private static final long serialVersionUID = 5242858406072116301L;
	
	//最后修改时间排序
    public static final int SORT_MODIFY_TIME = 0;
    //创建时间排序
    public static final int SORT_CREATE_TIME = 1;
    //标题排序
    public static final int SORT_TITLE = 2;
    
    private Integer id;

    /**
     * 实际唯一标识
     */
    private String sid;

    /**
     * 对应的用户id
     */
    private Integer userId;

    /**
     * 笔记的标题
     */
    private String title;

    /**
     * 文本内容
     */
    private String content;

    /**
     * 提醒的id
     */
    private Integer remindId;

    /**
     * 提醒的时间
     */
    private Date remindTime;

    /**
     * 文件夹的sid
     */
    private String folderSid;

    /**
     * 笔记的类型，主要分为{@link com.yunxinlink.notes.api.model.NoteInfo.NoteKind#TEXT}和{@link com.yunxinlink.notes.api.model.NoteInfo.NoteKind#DETAILED_LIST}
     */
    private Integer kind;

    /**
     * 删除的状态
     * @see DeleteState
     */
    private Integer deleteState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 文本内容的hash
     */
    private String hash;
    
    /**
     * 附件列表
     */
    private List<Attach> attachs;
    
    /**
	 * 清单的列表
	 */
	private List<DetailList> details;
    
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRemindId() {
		return remindId;
	}

	public void setRemindId(Integer remindId) {
		this.remindId = remindId;
	}

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}

	public String getFolderSid() {
		return folderSid;
	}

	public void setFolderSid(String folderSid) {
		this.folderSid = folderSid;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public List<Attach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attach> attachs) {
		this.attachs = attachs;
	}
	
	public List<DetailList> getDetails() {
		return details;
	}

	public void setDetails(List<DetailList> details) {
		this.details = details;
	}
	
	/**
	 * 该笔记是否有附件
	 * @return
	 */
	public boolean hasAttachs() {
		return !CollectionUtils.isEmpty(attachs);
	}
	
	/**
	 * 生成hash值
	 * 该hash值由title;content;folderSid;kind;deleteState的格式组成，顺序不能错,如果为null,则用""代替
	 * @return
	 */
	public String generateHash() {
		String spliter = Constant.TAG_SEMICOLON;
		String title = this.title == null ? "" : this.title;
		String content = this.content == null ? "" : this.content;
		String folderSid = this.folderSid == null ? "" : this.folderSid;
		int kind = this.kind == null ? NoteKind.TEXT : this.kind;
		int deleteState = this.deleteState == null ? 0 : this.deleteState;
		StringBuilder builder = new StringBuilder();
		builder.append(title).append(spliter)
				.append(content).append(spliter)
				.append(folderSid).append(spliter)
				.append(kind).append(spliter)
				.append(deleteState);
		return DigestUtils.md5Hex(builder.toString());
	}

	/**
	 * 是否是清单笔记，true：是
	 * @return
	 */
	public boolean checkDetailListNote() {
		return kind != null && kind == NoteKind.DETAILED_LIST;
	}
	
	/**
	 * 判断该笔记是否是完全删除
	 * @return
	 */
	public boolean checkDeleteDone() {
		return deleteState != null && deleteState == DeleteState.DELETE_DONE;
	}

	@Override
	public String toString() {
		return "NoteInfo [id=" + id + ", sid=" + sid + ", userId=" + userId + ", title=" + title + ", content="
				+ content + ", remindId=" + remindId + ", remindTime=" + remindTime + ", folderSid=" + folderSid
				+ ", kind=" + kind + ", deleteState=" + deleteState + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + ", hash=" + hash + ", attachs=" + attachs + ", details=" + details + "]";
	}
}
