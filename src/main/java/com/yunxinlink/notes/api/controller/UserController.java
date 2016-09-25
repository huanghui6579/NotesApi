package com.yunxinlink.notes.api.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.UserDto;
import com.yunxinlink.notes.api.model.AccountType;
import com.yunxinlink.notes.api.model.OpenApi;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IOpenApiService;
import com.yunxinlink.notes.api.service.IUserService;

/**
 * 用户的控制器
 * @author huanghui1
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private IOpenApiService openApiService;
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = {"index"})
	public String index() {
		return "user/api";
	}
	
	/**
	 * 用户登录
	 * @param userDto
	 * @param autoCreate
	 * @return
	 */
	@RequestMapping(value = {"login"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<UserDto> login(UserDto userDto, Integer autoCreate) {
		ActionResult<UserDto> actionResult = new ActionResult<>();
		if (userDto == null) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("登录参数错误");
			return actionResult;
		}
		//1、根据登录的类型来查询
		Integer type = userDto.getType();
		boolean success = false;
		int code = ActionResult.RESULT_ERROR;
		String reason = null;
		if (type != null && type > 0) {	//第三方账号登录
			String openUserId = userDto.getOpenUserId();
			if (StringUtils.isBlank(openUserId)) {	//参数错误
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("登录参数错误");
				return actionResult;
			}
			OpenApi openApi = openApiService.getByOpenUserId(openUserId);
			if (openApi == null && autoCreate != null && autoCreate == 1) {	//没有该账号，且自动创建
				logger.info("login open api not exists will add");
				
				User user = userDto.getUser();
				if (user == null) {
					user = new User();
				}
				
				Date date = new Date();
				openApi = new OpenApi();
				openApi.setUser(user);
				openApi.setCreateTime(date);
				openApi.setModifyTime(date);
				openApi.setExpiresTime(userDto.getExpiresTime());
				openApi.setToken(userDto.getToken());
				openApi.setType(userDto.getType());
				openApi.setOpenUserId(openUserId);
				
				success = openApiService.addOpenApi(openApi);
				
				if (success) {
					UserDto result = new UserDto();
					result.setUser(openApi.getUser());
					result.setExpiresTime(openApi.getExpiresTime());
					result.setOpenUserId(openUserId);
					result.setToken(openApi.getToken());
					result.setType(openApi.getType());
					actionResult.setData(result);
					code = ActionResult.RESULT_SUCCESS;
					reason = "登录成功";
				} else {
					code = ActionResult.RESULT_ERROR;
					reason = "登录失败";
				}
				
			} else {	//open API 存在，则检测账号是否可用
				//token的超时时间与服务器的比较，如果超时了，则更新一下
				long expiresTime = openApi.getExpiresTime();
				long clientExpiresTime = userDto.getExpiresTime();
				if (expiresTime < clientExpiresTime) {	//服务器的token时间比较老，则更新
					logger.info("login update expiresTime--->" + clientExpiresTime);
					OpenApi api2 = new OpenApi();
					api2.setId(openApi.getId());
					api2.setExpiresTime(clientExpiresTime);
					api2.setToken(userDto.getToken());
					api2.setModifyTime(new Date());
					boolean updateResult = openApiService.updateOpenApi(api2);
					logger.info("login update expiresTime result:" + updateResult);
				}
				User user = openApi.getUser();
				if (user != null) {
					if (!user.checkState()) {	//用户不可用
						code = ActionResult.RESULT_STATE_DISABLE;
						reason = "当前账号已被禁用";
					} else {
						code = ActionResult.RESULT_SUCCESS;
						reason = "登录成功";
					}
					UserDto result = new UserDto();
					result.setUser(user);
					result.setType(type);
					actionResult.setData(result);
				} else {
					code = ActionResult.RESULT_ERROR;
					reason = "登录失败";
				}
			}
		} else {	//本账号系统登录，根据手机号来登录
			User user = userDto.getUser();
			if (user == null) {
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("登录参数错误");
				return actionResult;
			}
			User u = userService.getUserByAccount(user);
			if (u == null) {
				code = ActionResult.RESULT_FAILED;
				reason = "用户账号或密码错误";
			} else {
				if (!u.checkState()) {	//用户不可用
					code = ActionResult.RESULT_STATE_DISABLE;
					reason = "当前账号已被禁用";
				} else {
					code = ActionResult.RESULT_SUCCESS;
					reason = "登录成功";
				}
				UserDto result = new UserDto();
				result.setUser(u);
				result.setType(AccountType.TYPE_LOCAL);
				actionResult.setData(result);
			}
		}
		actionResult.setResultCode(code);
		actionResult.setReason(reason);
		return actionResult;
	}
	
	/**
	 * 用户注册
	 * @param userDto
	 * @param confirmPassword 确认密码
	 * @return
	 */
	@RequestMapping(value = {"register"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<UserDto> register(UserDto userDto, String confirmPassword) {
		ActionResult<UserDto> actionResult = new ActionResult<>();
		if (userDto == null || userDto.getUser() == null || StringUtils.isBlank(confirmPassword)) {	//参数错误
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		User user = userDto.getUser();
		String password = user.getPassword();
		String email = user.getEmail();
		String mobile = user.getMobile();
		if (StringUtils.isBlank(email) && StringUtils.isBlank(mobile)) {	//账号为空
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("账号不能为空");
			return actionResult;
		}
		if (StringUtils.isBlank(password)) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("密码不能为空或者空格");
			return actionResult;
		}
		
		if (!password.equals(confirmPassword)) {	//两次输入的密码不等
			actionResult.setResultCode(ActionResult.RESULT_NOT_EQUALS);
			actionResult.setReason("两次输入的密码不相等");
			return actionResult;
		}
		
		boolean hasUser = userService.hasUser(user);
		logger.info("register user has user:" + hasUser);
		if (hasUser) {
			actionResult.setResultCode(ActionResult.RESULT_DATA_REPEAT);
			actionResult.setReason("该用户已存在了");
			return actionResult;
		}
		boolean success = userService.addUser(user);
		if (success) {
			UserDto resultDto = new UserDto();
			resultDto.setType(AccountType.TYPE_LOCAL);
			resultDto.setUser(user);
			actionResult.setData(resultDto);
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("注册失败，请稍后再试");
		}
		logger.info("register user result:" + actionResult);
		return actionResult;
	}
}
