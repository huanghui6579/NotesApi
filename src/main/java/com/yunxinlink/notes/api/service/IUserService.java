package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.model.User;

/**
 * 用户的服务层
 * @author huanghui1
 *
 */
public interface IUserService {
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public boolean addUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public boolean deleteUser(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);
	
	/**
	 * 根据id获取用户信息
	 * @param user
	 * @return
	 */
	public User getUserById(User user);
	
	/**
	 * 根据用户名和密码来获取用户信息登录时用到
	 * @param user
	 * @return
	 */
	public User getUserByAccount(User user);
	
	/**
	 * 获取用户列表
	 * @return
	 */
	public List<User> getUsers();
}
