package com.yunxinlink.notes.api.dto;

import com.yunxinlink.notes.api.util.Constant;

/**
 * 分页相关
 * @author huanghui1
 * @date 2016年10月10日 下午6:07:24
 */
public class PageInfo<T> {
	/**
	 * 第几页，从1开始
	 */
	private Integer pageNumber;
	
	private Integer pageSize = Constant.PAGE_SIZE_DEFAULT;
	
	private long count;
	
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取数据库查询的开始索引，从0开始
	 * @return
	 */
	public int calcPageOffset() {
		pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
		int offset = (pageNumber - 1) * pageSize;
		return offset;
	}
}
