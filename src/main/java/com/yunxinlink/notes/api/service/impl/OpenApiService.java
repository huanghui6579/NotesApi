package com.yunxinlink.notes.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.OpenApiDao;
import com.yunxinlink.notes.api.dao.UserDao;
import com.yunxinlink.notes.api.init.IdGenerator;
import com.yunxinlink.notes.api.model.OpenApi;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IOpenApiService;

/**
 * open API 服务层的具体实现
 * @author huanghui1
 *
 */
@Service
public class OpenApiService implements IOpenApiService {
	private static final Logger logger = Logger.getLogger(OpenApiService.class);
	
	@Autowired
	private OpenApiDao openApiDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean addOpenApi(OpenApi openApi) {
		if (openApi == null || openApi.getUser() == null) {
			logger.info("add open api failed open api id null or user is null " + openApi);
			return false;
		}
		//1、先添加用户
		User user = openApi.getUser();
		user.setSid(IdGenerator.generateUUID());
		int rowId = userDao.add(user);
		if (rowId > 0) {	//添加成功
			rowId = openApiDao.add(openApi);
			openApi.setId(rowId);
		}
		return rowId > 0;
	}

	@Override
	public boolean updateOpenApi(OpenApi openApi) {
		if (openApi == null) {
			logger.info("update open api failed open api is null");
		}
		int rowId = 0;
		try {
			rowId = openApiDao.update(openApi);
		} catch (Exception e) {
			logger.error("update open api error:" + e.getMessage());
		}
		return rowId > 0;
	}

	@Override
	public boolean deleteOpenApi(OpenApi openApi) {
		if (openApi == null) {
			logger.info("delete open api failed open api is null");
		}
		int	rowId = openApiDao.delete(openApi);
		if (rowId > 0 && openApi.getUser() != null) {
			//删除该open API 对应的用户
			userDao.delete(openApi.getUser());
		}
		return rowId > 0;
	}

	@Override
	public OpenApi getById(Integer id) {
		if (id == null) {
			logger.info("get open api by id failed id is null");
			return null;
		}
		OpenApi openApi = new OpenApi();
		openApi.setId(id);
		try {
			return openApiDao.selectById(openApi);
		} catch (Exception e) {
			logger.error("get open api by id error:" + e.getMessage());
		}
		return null;
	}

	@Override
	public OpenApi getByOpenUserId(String openUserId) {
		if (StringUtils.isBlank(openUserId)) {
			logger.info("get open api by id failed open user id is blank");
			return null;
		}
		OpenApi openApi = new OpenApi();
		openApi.setOpenUserId(openUserId);
		try {
			return openApiDao.selectByOpenUserId(openApi);
		} catch (Exception e) {
			logger.error("get open api by id error:" + e.getMessage());
		}
		return null;
	}

	@Override
	public OpenApi getByUserId(Integer userId) {
		if (userId == null) {
			logger.info("get open api by user id failed user id is null");
			return null;
		}
		User user = new User();
		user.setId(userId);
		try {
			return openApiDao.selectByUserId(user);
		} catch (Exception e) {
			logger.error("get open api by user id error:" + e.getMessage());
		}
		return null;
	}

}
