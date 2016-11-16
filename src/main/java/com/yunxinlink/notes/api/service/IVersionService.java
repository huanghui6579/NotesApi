package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.model.VersionInfo;

/**
 * 软件版本更新记录的服务层
 * @author huanghui-iri
 * @date 2016年11月16日 下午7:16:03
 */
public interface IVersionService {
	/**
	 * 添加一条版本记录
	 * @param versionInfo
	 * @return
	 */
	public boolean addVersionInfo(VersionInfo versionInfo);
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	public boolean deleteVersionInfo(int id);
	
	/**
	 * 查询版本的记录列表
	 * @return
	 */
	public List<VersionInfo> getVersionList();
}
