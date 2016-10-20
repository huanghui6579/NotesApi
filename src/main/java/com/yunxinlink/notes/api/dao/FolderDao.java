package com.yunxinlink.notes.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunxinlink.notes.api.dto.FolderDto;
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
	
	/**
	 * 查询笔记本列表
	 * @param folderDto
	 * @return
	 */
	public List<Folder> selectFolders(FolderDto folderDto);
	
	/**
	 * 查询笔记的基本sid、hash、deletestate
	 * @return
	 */
	public List<Folder> selectSids(FolderDto folderDto);
	
	/**
	 * 获取用户笔记本的数量
	 * @param userId
	 * @return
	 */
	public long selectCount(@Param(value="userId") int userId);
}
