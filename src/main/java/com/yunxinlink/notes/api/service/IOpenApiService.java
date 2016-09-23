package com.yunxinlink.notes.api.service;

import com.yunxinlink.notes.api.model.OpenApi;

/**
 * open api的服务层
 * @author huanghui1
 *
 */
public interface IOpenApiService {
	/**
	 * 添加open api
	 * @param openApi
	 * @return
	 */
	public boolean addOpenApi(OpenApi openApi);
	
	/**
	 * 更新open api
	 * @param openApi
	 * @return
	 */
	public boolean updateOpenApi(OpenApi openApi);
	
	/**
	 * 删除open api
	 * @param openApi
	 * @return
	 */
	public boolean deleteOpenApi(OpenApi openApi);
	
	/**
	 * 根据id获取open API 的信息
	 * @param id
	 * @return
	 */
	public OpenApi getById(Integer id);
	
	/**
	 * 根据openUserId来获取open API的信息
	 * @param openUserId
	 * @return
	 */
	public OpenApi getByOpenUserId(String openUserId);
	
	/**
	 * 根据userId来获取open API的信息
	 * @param userId
	 * @return
	 */
	public OpenApi getByUserId(Integer userId);
}
