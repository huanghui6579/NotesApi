package com.yunxinlink.notes.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.UserDao;
import com.yunxinlink.notes.api.init.IdGenerator;
import com.yunxinlink.notes.api.model.State;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IUserService;

/**
 * 用户的服务层
 * @author huanghui1
 *
 */
@Service
public class UserService implements IUserService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean addUser(User user) {
		int id = 0;
		String password = user.getPassword();
		try {
			Integer state = user.getState();
			if (state == null) {
				state = State.NORMAL;
			}
			Date date = new Date();
			String encodePwd = DigestUtils.md5Hex(password);
			user.setPassword(encodePwd);
			user.setCreateTime(date);
			user.setSid(IdGenerator.generateUUID());
			id = userDao.add(user);
		} catch (Exception e) {
			logger.error("addUser error:" + e.getMessage());
		}
		user.setPassword(password);
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
			String mobile = user.getMobile();
			String email = user.getEmail();
			String password = user.getPassword();
			Map<String, String> map = new HashMap<>();
			String encodePwd = DigestUtils.md5Hex(password);
			map.put("password", encodePwd);
			if (StringUtils.isNotBlank(mobile)) {
				map.put("mobile", mobile);
			} else if (StringUtils.isNotBlank(email)) {
				map.put("email", user.getEmail());
			} else {
				return null;
			}
			User u = userDao.selectUserByAccount(map);
			if (u != null) {
				u.setPassword(password);
			}
			return u;
		} catch (Exception e) {
			logger.error("get user by account error:" + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean hasUser(User user) {
		int count = 0;
		try {
			count = userDao.selectCount(user);
		} catch (Exception e) {
			logger.error("has user error:" + e.getMessage());
		}
		return count > 0;
	}

	@Override
	public String getUserAvatar(User user) {
		String avatar = null;
		try {
			avatar = userDao.selectAvatar(user);
		} catch (Exception e) {
			logger.error("get user avatar error:" + e.getMessage());
		}
		return avatar;
	}

}
