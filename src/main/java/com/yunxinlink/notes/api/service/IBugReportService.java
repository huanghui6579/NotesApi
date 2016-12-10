package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.model.BugReport;
import com.yunxinlink.notes.api.model.PageInfo;
import com.yunxinlink.notes.api.model.QueryParam;

/**
 * 错误日志记录
 * @author huanghui-iri
 * @date 2016年12月8日 下午4:54:34
 */
public interface IBugReportService {
	/**
	 * 添加一条日志记录
	 * @param bugReport
	 * @return
	 */
	public boolean addBug(BugReport bugReport);
	
	/**
	 * 删除一条日志记录
	 * @param bugReport
	 * @return
	 */
	public boolean deleteBug(BugReport bugReport);
	
	/**
	 * 分页查询日志记录
	 * @param queryParam
	 * @return
	 */
	public PageInfo<List<BugReport>> getBugReports(QueryParam<BugReport> queryParam);
	
	/**
	 * 查询bug report的数量
	 * @param bugReport
	 * @return
	 */
	public long getBugReportCount(BugReport bugReport);
}
