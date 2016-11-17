package com.yunxinlink.notes.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.VersionDao;
import com.yunxinlink.notes.api.model.VersionInfo;
import com.yunxinlink.notes.api.service.IVersionService;

/**
 * 软件版本更新记录的服务层
 * @author huanghui-iri
 * @date 2016年11月16日 下午7:17:58
 */
@Service
public class VersionService implements IVersionService {
	
	private static final Logger logger = LoggerFactory.getLogger(VersionService.class);
	
	@Autowired
	private VersionDao versionDao;

	@Override
	public boolean addVersionInfo(VersionInfo versionInfo) {
		int rowCount = 0;
		try {
			rowCount = versionDao.add(versionInfo);
		} catch (Exception e) {
			logger.error("add version info error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public boolean deleteVersionInfo(int id) {
		VersionInfo info = new VersionInfo();
		info.setId(id);
		
		int rowCount = 0;
		try {
			rowCount = versionDao.delete(info);
		} catch (Exception e) {
			logger.error("delete version info error:" + e.getMessage());
		}
		
		return rowCount > 0;
	}

	@Override
	public List<VersionInfo> getVersionList(VersionInfo versionInfo, Integer offset, Integer limit) {
		List<VersionInfo> list = null;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("platform", versionInfo.getPlatform());
			if (offset != null && limit != null) {
				map.put("offset", offset);
				map.put("limit", limit);
			}
			list = versionDao.selectVersions(map);
		} catch (Exception e) {
			logger.error("list version info error:" + e.getMessage());
		}
		return list;
	}

}
