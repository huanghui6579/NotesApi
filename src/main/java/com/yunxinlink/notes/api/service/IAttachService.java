package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.model.Attach;

/**
 * 附件的服务层
 * @author tiger
 * @date 2016年10月6日 上午9:17:51
 */
public interface IAttachService {
	
	/**
	 * 添加附件
	 * @param attach
	 */
	public boolean addAttach(Attach attach);
	
	/**
	 * 添加一组附件
	 * @param attachs
	 * @return
	 */
	public boolean addAttachs(List<Attach> attachs);
	
	/**
	 * 更新附件
	 * @param attach
	 * @return
	 */
	public boolean updateAttach(Attach attach);
	
	/**
	 * 删除附件
	 * @param attach
	 * @return
	 */
	public boolean deleteAttach(Attach attach);
	
	/**
	 * 删除一组附件
	 * @param attachs
	 * @return
	 */
	public boolean deleteAttachs(List<Attach> attachs);
	
	/**
	 * 根据笔记的id删除该笔记下的附件
	 * @param noteId 笔记id
	 * @return
	 */
	public boolean deleteByNote(int noteId);
	
	/**
	 * 根据附件的id查询，该id可以是id或者是sId
	 * @param attach
	 * @return
	 */
	public Attach getById(Attach attach);
	
	/**
	 * 根据笔记的id查询该笔记下所有可用的附件
	 * @param noteId 笔记id
	 * @return
	 */
	public List<Attach> getByNote(int noteId);
}
