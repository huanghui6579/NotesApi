package com.yunxinlink.notes.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.FolderDto;
import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.model.NoteInfo;
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
	 * 检查用户的合法性
	 * @param actionResult
	 * @param user
	 */
	private boolean checkUser(ActionResult<?> actionResult, User user) {
		boolean isok = false;
		if (user == null) {	//用户不存在
			actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
			actionResult.setReason("该用户不存在");
		} else if (!user.checkState()) {//用户被禁用了
			actionResult.setResultCode(ActionResult.RESULT_STATE_DISABLE);
			actionResult.setReason("该用户已被禁用");
		} else {
			isok = true;
		}
		return isok;
	}
	
	/**
	 * 新建或者更新笔记
	 * @return
	 */
	@RequestMapping(value = "{userSid}/up", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> upNote(@PathVariable String userSid, @RequestBody NoteDto noteDto) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid) || noteDto == null || noteDto.checkEmpty()) {	//笔记不可用，参数为空
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		
		User user = new User();
		user.setSid(userSid);
		
		user = userService.getUserById(user);
		boolean isOk = checkUser(actionResult, user);
		if (!isOk) {
			return actionResult;
		}
		
		//笔记所属的笔记本，文件夹
		Folder folder = noteDto.getFolder();
		if (folder == null || folder.isDefaultFolder()) {	//这一组笔记没有指定所属笔记本，则为“所有”笔记本
			if (folder == null) {
				folder = new Folder();
				noteDto.setFolder(folder);
			}
		}
		//先清除id，根据此id是否有值来判断是否需要创建笔记
		folder.setId(null);
		folder.setUserId(user.getId());
		
		boolean success = false;
		try {
			success = noteService.addNote(noteDto);
			logger.info("add note success:" + noteDto);
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
	
	/**
	 * 加载笔记本，每次加载20条记录
	 * @param userSid
	 * @return
	 */
	@RequestMapping(value = "{userSid}/folders", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<List<Folder>> listFolders(@PathVariable String userSid, FolderDto folderDto) {
		ActionResult<List<Folder>> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid)) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		
		if (folderDto == null) {
			folderDto = new FolderDto();
		}
		
		User user = new User();
		user.setSid(userSid);
		
		user = userService.getUserById(user);
		boolean isOk = checkUser(actionResult, user);
		if (!isOk) {
			return actionResult;
		}
		
		Folder folder = new Folder();
		folder.setUserId(user.getId());
		
		folderDto.setFolder(folder);
		
		try {
			List<Folder> folders = folderService.getFolders(folderDto);
			
			if (CollectionUtils.isEmpty(folders)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有笔记本");
				logger.info("list folders this user not has folder :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(folders);
				actionResult.setReason("获取成功");
			}
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误");
			logger.error("down folder get folders error:" + e.getMessage());
			e.printStackTrace();
		}
		
		return actionResult;
	}
	
	/**
	 * 加载笔记的列表，每次加载20条记录
	 * @param userSid 用户的sid
	 * @param noteDto 参数
	 * @return
	 */
	@RequestMapping(value = "{userSid}/list", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<List<NoteInfo>> listNotes(@PathVariable String userSid, NoteDto noteDto) {
		ActionResult<List<NoteInfo>> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid)) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		
		if (noteDto == null) {
			noteDto = new NoteDto();
		}
		
		User user = new User();
		user.setSid(userSid);
		
		user = userService.getUserById(user);
		boolean isOk = checkUser(actionResult, user);
		if (!isOk) {
			return actionResult;
		}
		
		Folder folder = new Folder();
		folder.setUserId(user.getId());
		
		noteDto.setFolder(folder);
		try {
			List<NoteInfo> infos = noteService.getNoteInfos(noteDto);
			logger.info("infos:" + infos);
			if (CollectionUtils.isEmpty(infos)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有笔记");
				logger.info("list note info this user not has note :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(infos);
				actionResult.setReason("获取成功");
			}
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误");
			logger.error("list note info get notes error:" + e.getMessage());
			e.printStackTrace();
		}
		
		return actionResult;
	}
}
