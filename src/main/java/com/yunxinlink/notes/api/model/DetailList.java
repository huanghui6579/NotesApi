package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 清单实体
 * @author huanghui1
 * @update 2016/7/28 20:25
 * @version: 0.0.1
 */
@JsonInclude(Include.NON_NULL)
public class DetailList implements Serializable, Comparator<DetailList> {
	private static final long serialVersionUID = 8474621238576264781L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * sid
     */
    private String sid;

    /**
     * 标题
     */
    private String title;

    /**
     * 笔记的sid,关联笔记表
     */
    private String noteSid;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否选中，true：选中
     */
    private Boolean checked;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    /**
     * 删除的状态
     */
    private Integer deleteState;

    /**
     * 该清单的hash，主要用来检测更新
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNoteSid() {
		return noteSid;
	}

	public void setNoteSid(String noteSid) {
		this.noteSid = noteSid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
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

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetailList that = (DetailList) o;

        return sid.equals(that.sid);

    }

    @Override
    public int hashCode() {
        return sid.hashCode();
    }

	@Override
	public String toString() {
		return "DetailList [id=" + id + ", sid=" + sid + ", title=" + title + ", noteSid=" + noteSid + ", sort=" + sort
				+ ", checked=" + checked + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + ", deleteState=" + deleteState + ", hash=" + hash + "]";
	}

	@Override
    public int compare(DetailList lhs, DetailList rhs) {
        if (lhs.sort > rhs.sort) {
            return 1;
        } else if (lhs.sort < rhs.sort) {
            return -1;
        } else {
            return 0;
        }
    }
}
