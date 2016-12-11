/**
 * 
 */
package com.yunxinlink.notes.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.BugReportDao;
import com.yunxinlink.notes.api.model.BugReport;
import com.yunxinlink.notes.api.model.PageInfo;
import com.yunxinlink.notes.api.model.QueryParam;
import com.yunxinlink.notes.api.service.IBugReportService;

/**
 * 日志记录的service
 * @author huanghui-iri
 * @date 2016年12月8日 下午5:13:06
 */
@Service
public class BugReportService implements IBugReportService {
	private final Logger logger = LoggerFactory.getLogger(BugReportService.class);
	
	@Autowired
	private BugReportDao bugReportDao;

	/* (non-Javadoc)
	 * @see com.yunxinlink.notes.api.service.IBugReportService#addBug(com.yunxinlink.notes.api.model.BugReport)
	 */
	@Override
	public boolean addBug(BugReport bugReport) {
		int rowId = 0;
		try {
			rowId = bugReportDao.add(bugReport);
		} catch (Exception e) {
			logger.error("add bug report error:" + e);
		}
		return rowId > 0;
	}

	/* (non-Javadoc)
	 * @see com.yunxinlink.notes.api.service.IBugReportService#deleteBug(com.yunxinlink.notes.api.model.BugReport)
	 */
	@Override
	public boolean deleteBug(BugReport bugReport) {
		int rowCount = 0;
		try {
			rowCount = bugReportDao.delete(bugReport);
		} catch (Exception e) {
			logger.error("delete bug report error:" + e);
		}
		return rowCount > 0;
	}

	/* (non-Javadoc)
	 * @see com.yunxinlink.notes.api.service.IBugReportService#getBugReports(com.yunxinlink.notes.api.model.QueryParam)
	 */
	@Override
	public PageInfo<List<BugReport>> getBugReports(QueryParam<BugReport> queryParam) {
		PageInfo<Void> paramPageInfo = queryParam.convert2PageInfo();
		int offset = paramPageInfo.calcPageOffset();
		int pageSize = paramPageInfo.getPageSize();
		
		BugReport bugReport = queryParam.getData();
		
		Map<String, Object> param = new HashMap<>();
		param.put("bug", bugReport);
		param.put("offset", offset);
		param.put("limit", pageSize);
		
		List<BugReport> list = bugReportDao.selectBugs(param);
		PageInfo<List<BugReport>> pageInfo = new PageInfo<>();
		pageInfo.setData(list);
		
		long count = bugReportDao.selectBugCount(bugReport);
		pageInfo.setCount(count);
		
		int pageNumber = paramPageInfo.getPageNumber();
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNumber(pageNumber);
		return pageInfo;
	}

	@Override
	public long getBugReportCount(BugReport bugReport) {
		return bugReportDao.selectBugCount(bugReport);
	}

}
