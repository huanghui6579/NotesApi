package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.dto.FolderDto;
import com.yunxinlink.notes.api.dto.PageInfo;
import com.yunxinlink.notes.api.model.Folder;

/**
 * 笔记本的服务层
 * @author tiger
 * @date 2016年10月6日 上午10:42:55
 */
public interface IFolderService {
	/**
	 * 添加笔记本
	 * @param folder
	 * @return
	 */
	public boolean addFolder(Folder folder);
	
	/**
	 * 更新笔记本
	 * @param folder
	 * @return
	 */
	public boolean updateFolder(Folder folder);
	
	/**
	 * 删除笔记本
	 * @param folder
	 * @return
	 */
	public boolean deleteFolder(Folder folder);
	
	/**
	 * 查询该笔记的信息
	 * @param folder
	 * @return
	 */
	public Folder getById(Folder folder);
	
	/**
	 * 根据sid获取笔记本的基本信息
	 * @param sid
	 * @return
	 */
	public Folder getBasicInfo(String sid);
	
	/**
	 * 根据用户id查询该用户下的笔记
	 * @param folderDto
	 * @return
	 */
	public PageInfo<List<Folder>> getFolders(FolderDto folderDto);
	
	/**
	 * 获取笔记基本的sid、hash、deletestate
	 * @param folderDto
	 * @return
	 */
	public PageInfo<List<Folder>> getFolderSids(FolderDto folderDto);
	
	/**
	 * 根据用户的id或者该用户的笔记本数量
	 * @param userId
	 * @return
	 */
	public long getFolderCount(int userId);
	
	/**
	 * 获取指定id集合的笔记本数据
	 * @param idList
	 * @return
	 */
	public List<Folder> getFolders(List<Integer> idList);
}
