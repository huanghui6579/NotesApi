package com.yunxinlink.notes.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IFolderService;
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
	
	@Autowired
	private IFolderService folderService;
	
	@RequestMapping(value = {"index"})
	public String index() {
		return "note/api";
	}
	
	/**
	 * 新建笔记
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> createNote(@RequestBody NoteDto noteDto) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (noteDto == null || noteDto.checkEmpty()) {	//笔记不可用，参数为空
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
		
		//笔记所属的笔记本，文件夹
		Folder folder = noteDto.getFolder();
		boolean hasFolder = true;
		if (folder == null || folder.isDefaultFolder()) {	//这一组笔记没有指定所属笔记本，则为“所有”笔记本
			hasFolder = false;
			if (folder == null) {
				folder = new Folder();
				noteDto.setFolder(folder);
			}
		}
		Folder localFolder = null;
		boolean createFolder = false;
		//先清除id，根据此id是否有值来判断是否需要创建笔记
		folder.setId(null);
		folder.setUserId(user.getId());
		if (hasFolder) {	//有指定笔记本
			logger.info("create note has folder and will load folder basic info ");
			//先检查笔记本是否存在
			localFolder = folderService.getBasicInfo(folder.getSid());
			//是否需要创建笔记本
			createFolder = localFolder == null;
			
			if (!createFolder) {
				//设置folder的id
				folder.setId(localFolder.getId());
			}
		}
		
		boolean success = false;
		try {
			success = noteService.addNote(noteDto);
			if (success) {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setReason("添加成功");
			} else {
				actionResult.setResultCode(ActionResult.RESULT_FAILED);
				actionResult.setReason("添加失败");
			}
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("添加错误");
			logger.error("create note add error: " + e.getMessage());
		}
		
		return actionResult;
	}
}
