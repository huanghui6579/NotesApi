package com.yunxinlink.notes.api.dao;

import java.util.List;
import java.util.Map;

import com.yunxinlink.notes.api.model.BugReport;

/**
 * 日志记录的dao
 * @author huanghui-iri
 * @date 2016年12月8日 下午4:49:43
 */
public interface BugReportDao extends BaseDao<BugReport> {
	/**
	 * 分页查询日志记录
	 * @param param
	 * @return
	 */
	public List<BugReport> selectBugs(Map<String, Object> param);
	
	/**
	 * 查询bug的数量
	 * @param bugReport
	 * @return
	 */
	public long selectBugCount(BugReport bugReport);
}
