package com.yunxinlink.notes.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.yunxinlink.notes.api.dto.PageInfo;
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
		String folderSid = folder.getSid();
		Date date = new Date();
		if (StringUtils.isNotBlank(folderSid)) {	//需要创建或者更新笔记
			logger.info("add or update folder sid:" + folderSid);
			folder.setCreateTime(date);
			Date modifyTime = folder.getModifyTime();
			if (modifyTime == null || modifyTime.getTime() == 0) {
				modifyTime = date;
				folder.setModifyTime(modifyTime);
			}
			String hash = folder.getHash();
			if (StringUtils.isBlank(hash)) {
				hash = folder.generateHash();
				folder.setHash(hash);
			}
			//在sql语句里进行了添加或者更新操作
			rowCount = folderDao.add(folder);
		}
		//添加笔记
		List<NoteInfo> list = noteDto.getNoteInfos();
		//有清单的笔记列表
		List<DetailList> detailNotes = new ArrayList<>();
		//有附件的笔记
		List<Attach> attachNotes = new ArrayList<>();
		if (CollectionUtils.isEmpty(list)) {	//没有笔记
			logger.info("add note and update folder not has notes sid:" + folderSid);
			return rowCount > 0;
		}
		for (NoteInfo info : list) {
			if (info.isDetailListNote()) {
				List<DetailList> detailLists = info.getDetails();
				if (!CollectionUtils.isEmpty(detailLists)) {
					detailNotes.addAll(detailLists);
				}
			}
			if (info.hasAttachs()) {
				attachNotes.addAll(info.getAttachs());
			}
			
			Date createTime = info.getCreateTime();
			if (createTime == null) {
				createTime = date;
				info.setCreateTime(createTime);
			}
			
			Date modifyTime = info.getModifyTime();
			if (modifyTime == null) {
				modifyTime = date;
				info.setModifyTime(modifyTime);
			}
			
			Date remindTime = info.getRemindTime();
			if (remindTime != null && remindTime.getTime() == 0) {	//起始时间，则认为是没有设置提醒时间
				info.setRemindTime(null);
			}
						
			info.setFolderSid(folderSid);
			//该用户的id在controller层已设置好
			info.setUserId(folder.getUserId());
			if (StringUtils.isEmpty(info.getHash())) {
				String hash = null;
				if (info.isDetailListNote()) {	//清单笔记
					hash = DigestUtils.md5Hex(info.getTitle());
				} else {
					hash = DigestUtils.md5Hex(info.getContent());
				}
				info.setHash(hash);
			}
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
			int size = attachNotes.size();
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

	@Override
	public List<NoteInfo> getNoteInfos(NoteDto noteDto) {
		Folder folder = noteDto.getFolder();
		if (folder == null || folder.getUserId() == null) {
			return null;
		}
		
		PageInfo<Void> pageInfo = noteDto.convert2PageInfo();
		
		int offset = pageInfo.calcPageOffset();
		noteDto.setOffset(offset);
		noteDto.setLimit(pageInfo.getPageSize());
		return noteDao.selectNoteInfos(noteDto);
	}

}
