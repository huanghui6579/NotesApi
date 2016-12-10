package com.yunxinlink.notes.api.model;

import com.yunxinlink.notes.api.util.Constant;

/**
 * @author huanghui-iri
 * @date 2016年12月8日 下午5:00:23
 */
public class QueryParam<T> {
	private T data;
	
	/**
	 * 每页的记录数量，默认20条
	 */
	private Integer limit = Constant.PAGE_SIZE_DEFAULT;
	
	/**
	 * 数据库的开始索引，从0开始
	 */
	private Integer offset;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 转换成page info<br>
	 * 用户从页面传过来的参数，offset 相当于pageNumber, 即第几页，默认从1开始，当到数据库查询时，又根据pageinfo来转换，将offset设置为从0开始的索引
	 * @return
	 */
	public PageInfo<Void> convert2PageInfo() {
		PageInfo<Void> pageInfo = new PageInfo<>();
		pageInfo.setPageNumber(offset);
		pageInfo.setPageSize(limit);
		return pageInfo;
	}
}
