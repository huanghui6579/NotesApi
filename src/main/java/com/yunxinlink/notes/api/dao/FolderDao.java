package com.yunxinlink.notes.api.dao;

import com.yunxinlink.notes.api.model.Folder;

/**
 * 笔记本的数据库操作层
 * @author tiger
 * @date 2016年10月6日 上午10:11:33
 */
public interface FolderDao extends BaseDao<Folder> {
	/**
	 * 根据sid查询基本的笔记本信息
	 * @param sid
	 * @return
	 */
	public Folder selectBasic(String sid);
}