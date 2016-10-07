package com.yunxinlink.notes.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yunxinlink.notes.api.dao.AttachDao;
import com.yunxinlink.notes.api.dao.DetailListDao;
import com.yunxinlink.notes.api.dao.FolderDao;
import com.yunxinlink.notes.api.dao.NoteDao;
import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.model.Attach;
import com.yunxinlink.notes.api.model.DetailList;
import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.model.NoteInfo;
import com.yunxinlink.notes.api.service.INoteService;

/**
 * 笔记的服务层
 * @author tiger
 * @date 2016年10月6日 上午11:09:54
 */
@Service
public class NoteService implements INoteService {
	private final Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	private NoteDao noteDao;
	
	@Autowired
	private FolderDao folderDao;
	
	@Autowired
	private DetailListDao detailListDao;
	
	@Autowired
	private AttachDao attachDao;

	@Override
	public boolean addNote(NoteDto noteDto) {
		int rowCount = 0;
		Folder folder = noteDto.getFolder();
		boolean hasFolder = true;
		if (folder == null || folder.isDefaultFolder()) {	//这一组笔记没有指定所属笔记本，则为“所有”笔记本
			hasFolder = false;
		}
		Folder localFolder = null;
		boolean createFolder = false;
		if (hasFolder) {	//有指定笔记本
			//先检查笔记本是否存在
			localFolder = folderDao.selectBasic(folder.getSid());
			//是否需要创建笔记本
			createFolder = localFolder == null;
		}
		String folderSid = null;
		Date date = new Date();
		if (createFolder) {	//需要创建笔记本
			folder.setCreateTime(date);
			folder.setModifyTime(date);
			folderDao.add(folder);
			folderSid = folder.getSid();
			logger.info("add note but add folder first folder sid is:" + folderSid);
		} else {
			//设置folder的id
			folderSid = localFolder.getSid();
			folder.setId(localFolder.getId());
			logger.info("add note and not need create folder set folder sid:" + folderSid);
		}
		//添加笔记
		List<NoteInfo> list = noteDto.getNoteInfos();
		//有清单的笔记列表
		List<DetailList> detailNotes = new ArrayList<>();
		//有附件的笔记
		List<Attach> attachNotes = new ArrayList<>();
		for (NoteInfo info : list) {
			if (info.isDetailListNote()) {
				List<DetailList> detailLists = info.getDetails();
				if (!CollectionUtils.isEmpty(detailLists)) {
					detailNotes.addAll(detailLists);
				}
				if (info.hasAttachs()) {
					attachNotes.addAll(info.getAttachs());
				}
			}
			info.setFolderSid(folderSid);
			//该用户的id在controller层已设置好
			info.setUserId(folder.getUserId());
		}
		if (list.size() == 1) {	//只有一个笔记
			NoteInfo info = list.get(0);
			rowCount = noteDao.add(info);
		} else {
			rowCount = noteDao.addBatch(list);
		}
		//如果有清单，则添加清单信息
		if (!CollectionUtils.isEmpty(detailNotes)) {
			int size = detailNotes.size();
			logger.info("add note and has detail list notes will insert size:" + size);
			if (size == 1) {
				rowCount = detailListDao.add(detailNotes.get(0));
			} else {
				rowCount = detailListDao.addBatch(detailNotes);
			}
		}
		
		//如果有附件，则添加附件信息
		if (!CollectionUtils.isEmpty(attachNotes)) {
			int size = detailNotes.size();
			logger.info("add note and has attach notes will insert size:" + size);
			if (size == 1) {
				rowCount = attachDao.add(attachNotes.get(0));
			} else {
				rowCount = attachDao.addBatch(attachNotes);
			}
		}
		return rowCount > 0;
	}

	@Override
	public boolean updateNote(NoteInfo noteInfo) {
		int rowCount = 0;
		try {
			rowCount = noteDao.update(noteInfo);
		} catch (Exception e) {
			logger.error("update note error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public boolean deleteNote(NoteInfo noteInfo) {
		int rowCount = 0;
		try {
			rowCount = noteDao.delete(noteInfo);
		} catch (Exception e) {
			logger.error("delete note error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public NoteInfo getById(NoteInfo noteInfo) {
		NoteInfo result = null;
		try {
			result = noteDao.selectById(noteInfo);
		} catch (Exception e) {
			logger.error("get note by id error:" + e.getMessage());
		}
		return result;
	}

}
