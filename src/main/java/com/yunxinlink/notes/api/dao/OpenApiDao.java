package com.yunxinlink.notes.api.dao;

import com.yunxinlink.notes.api.model.OpenApi;
import com.yunxinlink.notes.api.model.User;

/**
 * 第三方账号的数据库层
 * @author huanghui1
 *
 */
public interface OpenApiDao extends BaseDao<OpenApi> {
	/**
	 * 根据openUserId 查询 用户信息
	 * @param openApi
	 * @return
	 */
	public OpenApi selectByOpenUserId(OpenApi openApi);
	
	/**
	 * 根据用户的 id来查询openAPI的信息
	 * @param user
	 * @return
	 */
	public OpenApi selectByUserId(User user);
}
