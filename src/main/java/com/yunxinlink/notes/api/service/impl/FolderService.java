package com.yunxinlink.notes.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.FolderDao;
import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.service.IFolderService;

/**
 * 笔记本服务层的具体实现
 * @author tiger
 * @date 2016年10月6日 上午10:47:11
 */
@Service
public class FolderService implements IFolderService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FolderDao folderDao;

	@Override
	public boolean addFolder(Folder folder) {
		int rowCount = 0;
		try {
			rowCount = folderDao.add(folder);
		} catch (Exception e) {
			logger.error("add folder error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public boolean updateFolder(Folder folder) {
		int rowCount = 0;
		try {
			rowCount = folderDao.update(folder);
		} catch (Exception e) {
			logger.error("update folder error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public boolean deleteFolder(Folder folder) {
		int rowCount = 0;
		try {
			rowCount = folderDao.delete(folder);
		} catch (Exception e) {
			logger.error("delete folder error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public Folder getById(Folder folder) {
		Folder result = null;
		try {
			result = folderDao.selectById(folder);
		} catch (Exception e) {
			logger.error("select folder by id error:" + e.getMessage());
		}
		return result;
	}

	@Override
	public List<Folder> getFolders(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
