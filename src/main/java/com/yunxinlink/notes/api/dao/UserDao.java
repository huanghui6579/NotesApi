package com.yunxinlink.notes.api.dao;

import java.util.List;
import java.util.Map;

import com.yunxinlink.notes.api.dto.PasswordDto;
import com.yunxinlink.notes.api.dto.PasswordResetInfoDto;
import com.yunxinlink.notes.api.model.PasswordResetInfo;
import com.yunxinlink.notes.api.model.User;

/**
 * 用户的持久层
 * @author huanghui1
 *
 */
public interface UserDao extends BaseDao<User> {
	
	/**
	 * 获取用户的基本信息，根据用户名获取
	 * @param params 用户的查询参数
	 * @return
	 */
	public User selectUserByAccount(Map<String, String> params);
	
	/**
	 * 获取用户的列表
	 * @return
	 */
	public List<User> selectUsers();
	
	/**
	 * 查询用户是否存在
	 * @param user
	 * @return
	 */
	public int selectCount(User user);
	
	/**
	 * 用户用户id或者sid获取用户的头像
	 * @param user
	 * @return
	 */
	public String selectAvatar(User user);
	
	/**
	 * 根据账号信息来查询用户
	 * @param user
	 * @return
	 */
	public User selectUser(User user);
	
	/**
	 * 添加或者更新找回密码的记录
	 * @param resetInfo
	 * @return
	 */
	public int insertPwdResetInfo(PasswordResetInfo resetInfo);
	
	/**
	 * 查询重置密码的记录
	 * @param account 一般有邮箱
	 * @return
	 */
	public PasswordResetInfoDto selectPwdResetInfo(String account);
	
	/**
	 * 删除重置密码的记录
	 * @param resetInfo
	 * @return
	 */
	public int deletePwdResetInfo(PasswordResetInfo resetInfo);
	
	/**
	 * 修改密码，需要校验原始密码的正确性
	 * @param passwordDto
	 * @return
	 */
	public int updatePassword(PasswordDto passwordDto);
}
