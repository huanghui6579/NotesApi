package com.yunxinlink.notes.api.dao;

/**
 * 数据库层dao的抽象类
 * @author huanghui1
 *
 * @param <T>
 */
public interface BaseDao<T> {
	/**
	 * 添加
	 * @param t
	 * @return 添加的实体的id
	 */
	public int add(T t);
	
	/**
	 * 删除
	 * @param t
	 * @return 删除的数量
	 */
	public int delete(T t);
	
	/**
	 * 更新
	 * @param t
	 * @return 更新的数量
	 */
	public int update(T t);
	
	/**
	 * 根据id获取实体信息
	 * @param t
	 * @return
	 */
	public T selectById(T t);
	
}
