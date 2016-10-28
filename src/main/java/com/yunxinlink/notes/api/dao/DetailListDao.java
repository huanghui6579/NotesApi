package com.yunxinlink.notes.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunxinlink.notes.api.model.DetailList;

/**
 * 清单的数据库操作层
 * @author tiger
 * @date 2016年10月7日 上午10:58:52
 */
public interface DetailListDao extends BaseDao<DetailList> {
	/**
	 * 批量添加
	 * @param list
	 * @return
	 */
	public int addBatch(@Param("list") List<DetailList> list);
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public int deleteBatch(@Param("list") List<DetailList> list);
	
	/**
	 * 根据笔记的id删除该笔记的清单
	 * @param noteId
	 * @return
	 */
	public int deleteByNote(@Param("noteId") int noteId);
	
	/**
	 * 根据笔记的id查询该笔记下的清单
	 * @param noteId
	 * @return
	 */
	public List<DetailList> selectByNote(@Param("noteId") int noteId);
	
	/**
	 * 获取制定ID的清单
	 * @param list
	 * @return
	 */
	public List<DetailList> selectFilterDetail(@Param(value="list") List<Integer> list);
}
