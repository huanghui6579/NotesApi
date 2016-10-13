package com.yunxinlink.notes.api.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 笔记的分类文件夹
 * @author huanghui1
 * @update 2016/2/24 18:23
 * @version: 0.0.1
 */
@JsonInclude(Include.NON_NULL)
public class Folder implements Cloneable, Comparator<Folder>, Serializable {
	private static final long serialVersionUID = 2650283428254685146L;

	private Integer id;

    /**
     * 实际的主键
     */
    private String sid;

    /**
     * 用户的id
     */
    private Integer userId;

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 是否被锁定
     */
    private Boolean isLock;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除的状态
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
     * 该文件夹下笔记的数量
     */
    private Integer count;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Folder other = (Folder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compare(Folder lhs, Folder rhs) {
		if (lhs.sort != null && rhs.sort != null) {
			if (lhs.sort > rhs.sort) {
	            return 1;
	        } else if (lhs.sort < rhs.sort) {
	            return -1;
	        } else {
	            return 0;
	        }
		} else if (lhs.sort != null && rhs.sort == null) {
			return 1;
		} else if (lhs.sort == null && rhs.sort != null) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	/**
	 * 是否是所有笔记本，即没有归类的笔记都放在此笔记本中
	 * @return
	 */
	public boolean isDefaultFolder() {
		return (id != null && id == 0) || StringUtils.isBlank(sid);
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", sid=" + sid + ", userId=" + userId + ", name=" + name + ", isLock=" + isLock
				+ ", sort=" + sort + ", deleteState=" + deleteState + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + ", count=" + count + "]";
	}

}
