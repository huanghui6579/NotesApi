package com.yunxinlink.notes.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.AttachDao;
import com.yunxinlink.notes.api.model.Attach;
import com.yunxinlink.notes.api.service.IAttachService;

/**
 * 附件服务层
 * @author tiger
 * @date 2016年10月6日 上午9:31:15
 */
@Service
public class AttachService implements IAttachService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AttachDao attachDao;

	@Override
	public boolean addAttach(Attach attach) {
		int rowCount = 0;
		try {
			rowCount = attachDao.add(attach);
		} catch (Exception e) {
			logger.error("add attach error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public boolean addAttachs(List<Attach> attachs) {
		int rowCount = 0;
		for (Attach attach : attachs) {
			rowCount = attachDao.add(attach);
		}
		return rowCount > 0;
	}

	@Override
	public boolean updateAttach(Attach attach) {
		int rowCount = 0;
		try {
			rowCount = attachDao.update(attach);
		} catch (Exception e) {
			logger.error("update attach error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public boolean deleteAttach(Attach attach) {
		int rowCount = 0;
		try {
			rowCount = attachDao.delete(attach);
		} catch (Exception e) {
			logger.error("delete attach error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public boolean deleteAttachs(List<Attach> attachs) {
		int[] ids = new int[attachs.size()];
		int size = attachs.size();
		for (int i = 0; i < size; i++) {
			ids[i] = attachs.get(i).getId();
		}
		int rowCount = attachDao.deleteBatch(ids);
		return rowCount > 0;
	}

	@Override
	public boolean deleteByNote(int noteId) {
		int rowCount = 0;
		try {
			rowCount = attachDao.deleteByNote(noteId);
		} catch (Exception e) {
			logger.error("delete attach by note error:" + e.getMessage());
		}
		return rowCount > 0;
	}

	@Override
	public Attach getById(Attach attach) {
		Attach result = null;
		try {
			result = attachDao.selectById(attach);
		} catch (Exception e) {
			logger.error("get attach by note error:" + e.getMessage());
		}
		return result;
	}

	@Override
	public List<Attach> getByNote(int noteId) {
		List<Attach> list = null;
		try {
			list = attachDao.selectByNote(noteId);
		} catch (Exception e) {
			logger.error("get attachs by note error:" + e.getMessage());
		}
		return list;
	}

}
