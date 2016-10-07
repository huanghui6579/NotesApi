package com.yunxinlink.notes.api.dao;

import java.util.List;

import com.yunxinlink.notes.api.model.Attach;

/**
 * 附件的dao
 * @author tiger
 * @date 2016年10月4日 上午11:06:17
 */
public interface AttachDao extends BaseDao<Attach> {
	/**
	 * 根据笔记的id查询该笔记下所有可用的附件
	 * @param noteId
	 * @return
	 */
	public List<Attach> selectByNote(int noteId);
	
	/**
	 * 根据笔记的id删除笔记
	 * @param noteId
	 * @return
	 */
	public int deleteByNote(int noteId);
	
	/**
	 * 删除一组附件
	 * @param ids
	 * @return
	 */
	public int deleteBatch(int[] ids);
	
	/**
	 * 批量添加附件
	 * @param list
	 * @return
	 */
	public int addBatch(List<Attach> list);
}
