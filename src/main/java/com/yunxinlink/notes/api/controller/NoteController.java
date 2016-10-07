package com.yunxinlink.notes.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.INoteService;
import com.yunxinlink.notes.api.service.IUserService;

/**
 * 笔记的控制层
 * @author tiger
 * @date 2016年10月6日 上午11:14:53
 */
@Controller
@RequestMapping("/note")
public class NoteController extends BaseController {
	
	@Autowired
	private INoteService noteService;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 新建笔记
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> createNote(NoteDto noteDto) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (noteDto == null || noteDto.isEmpty()) {	//笔记不可用，参数为空
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		String userSid = noteDto.getUserSid();
		
		User user = new User();
		user.setSid(userSid);
		
		user = userService.getUserById(user);
		if (user == null) {	//用户不存在
			actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
			actionResult.setReason("该用户不存在");
			return actionResult;
		}
		
		return null;
	}
}
