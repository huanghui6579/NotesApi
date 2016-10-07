package com.yunxinlink.notes.api.service;

import java.util.List;

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
	 * 根据用户id查询该用户下的笔记
	 * @param userId
	 * @return
	 */
	public List<Folder> getFolders(int userId);
}
