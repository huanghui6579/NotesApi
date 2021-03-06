package com.yunxinlink.notes.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yunxinlink.notes.api.dao.VersionDao;
import com.yunxinlink.notes.api.model.VersionInfo;
import com.yunxinlink.notes.api.service.IVersionService;
import com.yunxinlink.notes.api.util.Constant;

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

	@CacheEvict(value = Constant.DEFAULT_CACHE, key = "'appVersion_' + #versionInfo.platform")
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

	@CacheEvict(value = Constant.DEFAULT_CACHE, key = "'appVersion_' + #id")
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

	@Cacheable(value = Constant.DEFAULT_CACHE, key = "'appVersion_' + #versionInfo.platform")
	@Override
	public VersionInfo getLastVersion(VersionInfo versionInfo) {
		List<VersionInfo> versionInfos = getVersionsByPlatform(versionInfo);
		if (CollectionUtils.isEmpty(versionInfos)) {
			return null;
		}
		return versionInfos.get(0);
	}

	@Cacheable(value = Constant.DEFAULT_CACHE, key = "'appVersion_' + #id")
	@Override
	public VersionInfo getVersionInfo(int id) {
		VersionInfo info = null;
		VersionInfo param = new VersionInfo();
		param.setId(id);
		try {
			info = versionDao.selectById(param);
		} catch (Exception e) {
			logger.error("get version into error:" + e.getMessage());
		}
		return info;
	}

	@Cacheable(value = Constant.DEFAULT_CACHE, key = "'appVersionList_' + #versionInfo.platform")
	@Override
	public List<VersionInfo> getVersionsByPlatform(VersionInfo versionInfo) {
		return getVersionList(versionInfo, null, null);
	}

}
