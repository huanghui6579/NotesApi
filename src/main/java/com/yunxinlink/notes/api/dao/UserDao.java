package com.yunxinlink.notes.api.dao;

import java.util.List;

import com.yunxinlink.notes.api.model.User;

/**
 * 用户的持久层
 * @author huanghui1
 *
 */
public interface UserDao extends BaseDao<User> {
	
	/**
	 * 获取用户的基本信息，根据用户名获取
	 * @param user 用户的查询参数
	 * @return
	 */
	public User selectUserByMobile(User user);
	
	/**
	 * 获取用户的列表
	 * @return
	 */
	public List<User> selectUsers();
}
