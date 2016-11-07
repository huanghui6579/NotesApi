package com.yunxinlink.notes.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.model.NoteInfo;

/**
 * 笔记的数据库操作层
 * @author tiger
 * @date 2016年10月6日 上午10:52:30
 */
public interface NoteDao extends BaseDao<NoteInfo> {
	
	/**
	 * 仅获取笔记的基本信息，不包含清单和附件信息
	 * @param noteInfo
	 * @return
	 */
	public NoteInfo selectBasicById(NoteInfo noteInfo);
	
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
	
	/**
	 * 分页获取笔记的sid信息
	 * @param noteDto
	 * @return
	 */
	public List<NoteInfo> selectNoteSids(NoteDto noteDto);
	
	/**
	 * 获取对应用户的笔记数量
	 * @param userId
	 * @return
	 */
	public long selectCount(@Param(value="userId") int userId);
	
	/**
	 * 获取指定的笔记信息,包含清单和附件的基本信息
	 * @param list
	 * @return
	 */
	public List<NoteInfo> selectFilterNotes(@Param(value="list") List<Integer> list);
	
	/**
	 * 获取指定的笔记信息，不包含清单和附件的基本信息
	 * @param list
	 * @return
	 */
	public List<NoteInfo> selectBasicFilterNotes(@Param(value="list") List<Integer> list);
	
	/**
	 * 更新一条笔记的删除状态
	 * @param noteInfo
	 * @return
	 */
	public int updateState(NoteInfo noteInfo);
	
	/**
	 * 更新一组笔记的删除状态
	 * @param noteList
	 * @return
	 */
	public int updateStateList(@Param("list") List<String> list);
	
	/**
	 * 根据sid的集合删除对应的笔记
	 * @param list
	 * @return
	 */
	public int deleteSidList(@Param("list") List<String> list);
	

	/**
	 * 根据id的集合删除对应的笔记
	 * @param list
	 * @return
	 */
	public int deleteIdList(@Param("list") List<Integer> list);
	
	
	/**
	 * 根据sid的集合删除对应的笔记
	 * @param list
	 * @return
	 */
	public int deleteList(@Param("list") List<NoteInfo> list);
	
}
