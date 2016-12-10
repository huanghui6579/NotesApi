package com.yunxinlink.notes.api.dto;

import com.yunxinlink.notes.api.model.PageInfo;
import com.yunxinlink.notes.api.util.Constant;

/**
 * @author huanghui1
 * @date 2016年10月10日 下午4:11:50
 */
public abstract class BaseDto {
	/**
	 * 每页的记录数量，默认20条
	 */
	protected Integer limit = Constant.PAGE_SIZE_DEFAULT;
	
	/**
	 * 数据库的开始索引，从0开始
	 */
	protected Integer offset;

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
