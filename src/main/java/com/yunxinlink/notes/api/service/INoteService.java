package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.dto.PageInfo;
import com.yunxinlink.notes.api.model.DetailList;
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
	 * 根据笔记的id来获取笔记，包含清单和附件的信息
	 * @param noteInfo
	 * @return
	 */
	public NoteInfo getById(NoteInfo noteInfo);
	
	/**
	 * 只获取笔记的信息，清单和附件的不获取
	 * @param noteInfo
	 * @return
	 */
	public NoteInfo getSimpleById(NoteInfo noteInfo);
	
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
	 * 获取指定的笔记信息,包含清单和附件的信息
	 * @param idList
	 * @param simple 是否只加载基本的笔记数据，清单和附件不加载
	 * @return
	 */
	public List<NoteInfo> getNotes(List<Integer> idList, boolean simple);
	
	/**
	 * 获取指定清单的数据
	 * @param idList
	 * @return
	 */
	public List<DetailList> getFilterDetailList(List<Integer> idList);
}
