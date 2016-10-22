package com.yunxinlink.notes.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yunxinlink.notes.api.dao.FolderDao;
import com.yunxinlink.notes.api.dto.FolderDto;
import com.yunxinlink.notes.api.dto.PageInfo;
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
			String hash = folder.generateHash();
			folder.setHash(hash);
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
			String hash = folder.generateHash();
			folder.setHash(hash);
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
	public Folder getBasicInfo(String sid) {
		Folder result = null;
		try {
			result = folderDao.selectBasic(sid);
		} catch (Exception e) {
			logger.error("select folder basic info by sid error:" + e.getMessage());
		}
		return result;
	}

	@Override
	public PageInfo<List<Folder>> getFolders(FolderDto folderDto) {
		PageInfo<Void> paramPageInfo = folderDto.convert2PageInfo();
		
		int offset = paramPageInfo.calcPageOffset();
		folderDto.setOffset(offset);
		folderDto.setLimit(paramPageInfo.getPageSize());
		List<Folder> list = folderDao.selectFolders(folderDto);
		
		long count = 0;
		if (!CollectionUtils.isEmpty(list)) {
			count = list.size();
			if (paramPageInfo.getPageSize() == count ) {	//可能还有数据，则查询总记录数
				Folder folder = folderDto.getFolder();
				count = folderDao.selectCount(folder.getUserId());
			} else {
				logger.info("get folders no more folders count:" + count);
			}
		}
		
		PageInfo<List<Folder>> pageInfo = new PageInfo<>();
		pageInfo.setData(list);
		pageInfo.setPageNumber(paramPageInfo.getPageNumber());
		pageInfo.setPageSize(paramPageInfo.getPageSize());
		pageInfo.setCount(count);
		
		return pageInfo;
	}

	@Override
	public PageInfo<List<Folder>> getFolderSids(FolderDto folderDto) {
		PageInfo<Void> paramPageInfo = folderDto.convert2PageInfo();
		
		int offset = paramPageInfo.calcPageOffset();
		folderDto.setOffset(offset);
		folderDto.setLimit(paramPageInfo.getPageSize());
		List<Folder> list = folderDao.selectSids(folderDto);
		
		long count = 0;
		if (!CollectionUtils.isEmpty(list)) {
			count = list.size();
			if (paramPageInfo.getPageSize() == count ) {	//可能还有数据，则查询总记录数
				Folder folder = folderDto.getFolder();
				count = folderDao.selectCount(folder.getUserId());
			} else {
				logger.info("get folder sids no more folders count:" + count);
			}
		}
		PageInfo<List<Folder>> pageInfo = new PageInfo<>();
		pageInfo.setData(list);
		pageInfo.setPageNumber(paramPageInfo.getPageNumber());
		pageInfo.setPageSize(paramPageInfo.getPageSize());
		pageInfo.setCount(count);
		
		return pageInfo;
	}

	@Override
	public long getFolderCount(int userId) {
		long count = 0;
		try {
			count = folderDao.selectCount(userId);
		} catch (Exception e) {
			logger.error("select folder count by user id:" + userId + ", error:" + e.getMessage());
		}
		return count;
	}

	@Override
	public List<Folder> getFolders(List<Integer> idList) {
		List<Folder> fodlers = null;
		try {
			if (idList.size() == 1) {	//只有一条记录
				Folder param = new Folder();
				param.setId(idList.get(0));
				Folder folder = getById(param);
				if (folder != null) {
					fodlers = new ArrayList<>();
					fodlers.add(folder);
				}
			} else {
				fodlers = folderDao.selectFilterFolders(idList);
			}
		} catch (Exception e) {
			logger.error("get folders by id list error:" + e.getMessage());
		}
		return fodlers;
	}
}
