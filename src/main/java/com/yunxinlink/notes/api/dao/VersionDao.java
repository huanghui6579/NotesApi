package com.yunxinlink.notes.api.dao;

import java.util.List;
import java.util.Map;

import com.yunxinlink.notes.api.model.VersionInfo;

/**
 * 软件版本的更新记录dao
 * @author huanghui-iri
 * @date 2016年11月16日 下午6:11:35
 */
public interface VersionDao extends BaseDao<VersionInfo> {
	/**
	 * 查询所有的版本更新记录
	 * @param params 参数
	 * @return
	 */
	public List<VersionInfo> selectVersions(Map<String, Object> params);
}
