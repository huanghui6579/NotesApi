package com.yunxinlink.notes.api.dao;

import java.util.List;

import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.model.NoteInfo;

/**
 * 笔记的数据库操作层
 * @author tiger
 * @date 2016年10月6日 上午10:52:30
 */
public interface NoteDao extends BaseDao<NoteInfo> {
	/**
	 * 批量添加笔记
	 * @param list
	 * @return
	 */
	public int addBatch(List<NoteInfo> list);
	
	/**
	 * 获取用户的笔记
	 * @param noteDto 参数
	 * @return
	 */
	public List<NoteInfo> selectNoteInfos(NoteDto noteDto);
}
