package com.yunxinlink.notes.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IUserService;

@Controller
@RequestMapping()
public class IndexController extends BaseController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = {"/", ""})
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = {"regist"}, method = RequestMethod.GET)
	public String showRegist() {
		return "index";
	}
	
	@RequestMapping(value = {"regist"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> regist(User user) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (user == null) {
			actionResult.setReason("注册失败，用户信息为空");
			logger.info("regist user is null");
		} else {
			boolean success = userService.addUser(user);
			if (success) {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setReason("注册成功");
				logger.info("regist user :" + user);
			} else {
				actionResult.setReason("注册失败，该用户已经存在了");
			}
		}
		return actionResult;
	}
	
	@RequestMapping(value = {"login"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> login(User user) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (user == null) {
			actionResult.setReason("登录失败，用户信息为空");
			logger.info("login user is null");
		} else {
			User u = userService.getUserByAccount(user);
			if (u != null) {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setReason("登录成功");
				logger.info("login user :" + u);
			} else {
				actionResult.setReason("登录失败，用户名或密码不正确");
			}
		}
		return actionResult;
	}
	
}
