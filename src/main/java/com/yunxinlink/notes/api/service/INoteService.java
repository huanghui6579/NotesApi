package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.dto.PageInfo;
import com.yunxinlink.notes.api.model.NoteInfo;

/**
 * 笔记的服务层
 * @author tiger
 * @date 2016年10月6日 上午11:07:00
 */
public interface INoteService {
	
	/**
	 * 添加笔记
	 * @param noteDto
	 * @return
	 */
	public boolean addNote(NoteDto noteDto);
	
	/**
	 * 更新笔记
	 * @param noteInfo
	 * @return
	 */
	public boolean updateNote(NoteInfo noteInfo);
	
	/**
	 * 删除笔记
	 * @param noteInfo
	 * @return
	 */
	public boolean deleteNote(NoteInfo noteInfo);
	
	/**
	 * 根据笔记的id来获取笔记
	 * @param noteInfo
	 * @return
	 */
	public NoteInfo getById(NoteInfo noteInfo);
	
	/**
	 * 获取用户的笔记，每次加载20条
	 * @param noteDto
	 * @param countSize 是否查询总记录
	 * @return
	 */
	public PageInfo<List<NoteInfo>> getNoteInfos(NoteDto noteDto, boolean countSize);
	
	/**
	 * 获取用户的笔记的sid，每次加载20条
	 * @param noteDto
	 * @param countSize 是否查询总记录
	 * @return
	 */
	public PageInfo<List<NoteInfo>> getNoteSids(NoteDto noteDto, boolean countSize);
	
	/**
	 * 获取指定的笔记信息
	 * @param idList
	 * @return
	 */
	public List<NoteInfo> getNotes(List<Integer> idList);
}
