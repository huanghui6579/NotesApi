package com.yunxinlink.notes.api.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.UserDao;
import com.yunxinlink.notes.api.init.IdGenerator;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IUserService;

/**
 * 用户的服务层
 * @author huanghui1
 *
 */
@Service
public class UserService implements IUserService {
	private static final Logger logger = Logger.getLogger(UserService.class);
	@Autowired
	private UserDao userDao;

	@Override
	public boolean addUser(User user) {
		int id = 0;
		try {
			user.setSid(IdGenerator.generateUUID());
			id = userDao.add(user);
			user.setId(id);
		} catch (Exception e) {
			logger.error("addUser error:" + e.getMessage());
		}
		return id > 0;
	}

	@Override
	public boolean deleteUser(User user) {
		return userDao.delete(user) > 0;
	}

	@Override
	public boolean updateUser(User user) {
		return userDao.update(user) > 0;
	}

	@Override
	public User getUserById(User user) {
		try {
			return userDao.selectById(user);
		} catch (Exception e) {
			logger.error("get user error:" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
		return userDao.selectUsers();
	}

	@Override
	public User getUserByAccount(User user) {
		try {
			return userDao.selectUserByMobile(user);
		} catch (Exception e) {
			logger.error("get user by account error:" + e.getMessage());
		}
		return null;
	}

}
