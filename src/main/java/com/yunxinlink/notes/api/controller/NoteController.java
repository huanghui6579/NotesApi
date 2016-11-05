package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.AttachDto;
import com.yunxinlink.notes.api.dto.FolderDto;
import com.yunxinlink.notes.api.dto.NoteDto;
import com.yunxinlink.notes.api.dto.PageInfo;
import com.yunxinlink.notes.api.init.SystemCache;
import com.yunxinlink.notes.api.model.Attach;
import com.yunxinlink.notes.api.model.DetailList;
import com.yunxinlink.notes.api.model.Folder;
import com.yunxinlink.notes.api.model.NoteInfo;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IAttachService;
import com.yunxinlink.notes.api.service.IFolderService;
import com.yunxinlink.notes.api.service.INoteService;
import com.yunxinlink.notes.api.service.IUserService;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.Constant;
import com.yunxinlink.notes.api.util.SystemUtil;

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
	
	@Autowired
	private IAttachService attachService;
	
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
		if (user == null || user.getId() == null) {	//用户不存在
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
	 * 检查用户的合法性
	 * @param actionResult
	 * @param user
	 */
	private boolean checkUser(User user) {
		boolean isok = false;
		if (user != null) {	//用户不存在
			isok = user.checkState();
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
		if (folder == null || folder.checkDefaultFolder()) {	//这一组笔记没有指定所属笔记本，则为“所有”笔记本
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
			logger.info("add note result:" + success + ", note:" + noteDto);
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
	public ActionResult<PageInfo<List<Folder>>> listFolders(@PathVariable String userSid, FolderDto folderDto) {
		ActionResult<PageInfo<List<Folder>>> actionResult = new ActionResult<>();
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
			PageInfo<List<Folder>> pageInfo = folderService.getFolders(folderDto);
			List<Folder> folders = pageInfo.getData();
			
			if (CollectionUtils.isEmpty(folders)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有笔记本");
				logger.info("list folders this user no folder :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(pageInfo);
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
	 * 获取指定笔记本的数据
	 * @param userSid
	 * @param idStr 笔记本的id列表字符串，之间用,分隔
	 * @return
	 */
	@RequestMapping(value = "{userSid}/folders/filter", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<List<Folder>> filterFolders(@PathVariable String userSid, String idStr) {
		ActionResult<List<Folder>> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid) || StringUtils.isBlank(idStr)) {
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
		
		try {
			//id之间用,分隔
			String[] idStrs = idStr.split(Constant.TAG_COMMA);
			if (idStrs == null) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			
			List<Integer> idList = new ArrayList<>();
			for (String str : idStrs) {
				try {
					int id = Integer.parseInt(str);
					idList.add(id);
				} catch (Exception e) {
					logger.error("down folder filter folders parse id:" + str + ", error:" + e.getMessage());
				}
			}
			
			if (idList.size() == 0) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			
			List<Folder> folderList = folderService.getFolders(idList);
			
			if (CollectionUtils.isEmpty(folderList)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有笔记本");
				logger.info("filter folders this user no folder :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(folderList);
				actionResult.setReason("获取成功");
			}
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误");
			logger.error("down folder filter folders error:" + e.getMessage());
			e.printStackTrace();
		}
		
		return actionResult;
	}
	
	/**
	 * 获取制定用户的文件夹的hash值
	 * @param userSid
	 * @return
	 */
	@RequestMapping(value = "{userSid}/folder/sids", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<PageInfo<List<Folder>>> listFolderSids(@PathVariable String userSid, FolderDto folderDto) {
		ActionResult<PageInfo<List<Folder>>> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid)) {
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
		if (folderDto == null) {
			folderDto = new FolderDto();
		}
		Folder folder = new Folder();
		folder.setUserId(user.getId());
		
		folderDto.setFolder(folder);
		
		try {
			PageInfo<List<Folder>> pageInfo = folderService.getFolderSids(folderDto);
			List<Folder> folders = pageInfo.getData();
			
			if (CollectionUtils.isEmpty(folders)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有笔记本");
				logger.info("list folder sids this user no folder :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(pageInfo);
				actionResult.setReason("获取成功");
			}
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误");
			logger.error("list folder sids get folders error:" + e.getMessage());
			e.printStackTrace();
		}
		return actionResult;
	}
	
	/**
	 * 加载笔记的列表，每次加载20条记录
	 * @param userSid 用户的sid
	 * @param noteDto 参数
	 * @param countSize 是否查询总记录数,1:需要查询
	 * @return
	 */
	@RequestMapping(value = "{userSid}/list", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<PageInfo<List<NoteInfo>>> listNotes(@PathVariable String userSid, NoteDto noteDto, Integer countSize) {
		return getNotes(false, userSid, noteDto, countSize);
	}
	
	/**
	 * 分页获取笔记的sid列表，并带有hash数据
	 * @param userSid 用户的sid
	 * @param noteDto 参数 
	 * @param countSize 是否查询总记录数,1:需要查询
	 * @return
	 */
	@RequestMapping(value = "{userSid}/sids", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<PageInfo<List<NoteInfo>>> listNoteSids(@PathVariable String userSid, NoteDto noteDto, Integer countSize) {
		return getNotes(true, userSid, noteDto, countSize);
	}
	
	/**
	 * 获取指定的笔记ID集合的笔记信息，包含附件和清单的信息
	 * @param userSid
	 * @param idStr
	 * @param simple 是否只加载基本数据，不包含清单的和附件的
	 * @return
	 */
	@RequestMapping(value = "{userSid}/list/filter", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<List<NoteInfo>> filterNotes(@PathVariable String userSid, String idStr, Boolean simple) {
		ActionResult<List<NoteInfo>> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid) || StringUtils.isBlank(idStr)) {
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
		
		try {
			//id之间用,分隔
			String[] idStrs = idStr.split(Constant.TAG_COMMA);
			if (idStrs == null) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			
			List<Integer> idList = new ArrayList<>();
			for (String str : idStrs) {
				try {
					int id = Integer.parseInt(str);
					idList.add(id);
				} catch (Exception e) {
					logger.error("down note filter notes parse id:" + str + ", error:" + e.getMessage());
				}
			}
			
			if (idList.size() == 0) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			boolean loadSimple = simple == null ? false : simple;
			List<NoteInfo> noteList = noteService.getNotes(idList, loadSimple);
			
			if (CollectionUtils.isEmpty(noteList)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有笔记");
				logger.info("filter notes this user no note :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(noteList);
				actionResult.setReason("获取成功");
			}
			
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误");
			logger.error("down note filter notes error:" + e.getMessage());
			e.printStackTrace();
		}
		return actionResult;
	}
	
	/**
	 * 获取指定的清单ID集合的清单信息
	 * @param userSid
	 * @param idStr
	 * @return
	 */
	@RequestMapping(value = "{userSid}/detaillist/filter", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<List<DetailList>> filterDetailLists(@PathVariable String userSid, String idStr) {
		ActionResult<List<DetailList>> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid) || StringUtils.isBlank(idStr)) {
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
		
		try {
			//id之间用,分隔
			String[] idStrs = idStr.split(Constant.TAG_COMMA);
			if (idStrs == null) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			
			List<Integer> idList = new ArrayList<>();
			for (String str : idStrs) {
				try {
					int id = Integer.parseInt(str);
					idList.add(id);
				} catch (Exception e) {
					logger.error("down detail filter notes parse id:" + str + ", error:" + e.getMessage());
				}
			}
			
			if (idList.size() == 0) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			List<DetailList> detailLists = noteService.getFilterDetailList(idList);
			
			if (CollectionUtils.isEmpty(detailLists)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有清单");
				logger.info("filter detail this user no note :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(detailLists);
				actionResult.setReason("获取成功");
			}
			
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误");
			logger.error("down detail list filter error:" + e.getMessage());
			e.printStackTrace();
		}
		return actionResult;
	}
	
	/**
	 * 获取指定的附件ID集合的附件信息
	 * @param userSid
	 * @param idStr
	 * @return
	 */
	@RequestMapping(value = "{userSid}/attach/filter", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<List<Attach>> filterAttachs(@PathVariable String userSid, String idStr) {
		ActionResult<List<Attach>> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid) || StringUtils.isBlank(idStr)) {
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
		
		try {
			//id之间用,分隔
			String[] idStrs = idStr.split(Constant.TAG_COMMA);
			if (idStrs == null) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			
			List<Integer> idList = new ArrayList<>();
			for (String str : idStrs) {
				try {
					int id = Integer.parseInt(str);
					idList.add(id);
				} catch (Exception e) {
					logger.error("down attach filter notes parse id:" + str + ", error:" + e.getMessage());
				}
			}
			
			if (idList.size() == 0) {	//
				actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
				actionResult.setReason("参数错误");
				return actionResult;
			}
			List<Attach> detailLists = attachService.getFilterAttachs(idList);
			
			if (CollectionUtils.isEmpty(detailLists)) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有附件");
				logger.info("filter attach this user no note :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(detailLists);
				actionResult.setReason("获取成功");
			}
			
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误");
			logger.error("down attach list filter error:" + e.getMessage());
			e.printStackTrace();
		}
		return actionResult;
	}
	
	/**
	 * 获取笔记的列表
	 * @param onlySid
	 * @param userSid
	 * @param noteDto
	 * @param countSize
	 * @return
	 */
	private ActionResult<PageInfo<List<NoteInfo>>> getNotes(boolean onlySid ,String userSid, NoteDto noteDto, Integer countSize) {
		ActionResult<PageInfo<List<NoteInfo>>> actionResult = new ActionResult<>();
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
			boolean needCount = (countSize != null && countSize == 1) ? true : false;
			PageInfo<List<NoteInfo>> pageInfo = null;
			if (onlySid) {	//只加载笔记的sid等基本信息
				pageInfo = noteService.getNoteSids(noteDto, needCount);
			} else {
				pageInfo = noteService.getNoteInfos(noteDto, needCount);
			}
			if (pageInfo != null && CollectionUtils.isEmpty(pageInfo.getData())) {
				actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
				actionResult.setReason("没有笔记");
				logger.info("list note info this user no note :" + userSid);
			} else {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setData(pageInfo);
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
	
	/**
	 * 上传附件，一次只能上传一个
	 * @param attach
	 * @param partFile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"att/upload"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> uploadAttach(AttachDto attachDto, @RequestParam(name = "attFile", required = true) MultipartFile partFile) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (attachDto == null || StringUtils.isBlank(attachDto.getSid()) || StringUtils.isBlank(attachDto.getNoteSid()) || partFile == null || partFile.isEmpty()) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		Attach attach = attachDto.convert2Attach();
		String attachSid = attach.getSid();
		String originalFilename = partFile.getOriginalFilename();
		//文件的后缀
		String ext = FilenameUtils.getExtension(originalFilename);
		String attachFilename = SystemUtil.generateAttachFilename(attachSid, ext);
		File saveFile = getAttachSaveFile(attachFilename);
		boolean saveResult = false;
		if (saveFile != null) {
			saveResult = saveFile(partFile, saveFile);
		}
		if (saveResult) {	//保存成功
			attach.setLocalPath(attachFilename);
			attach.setFilename(originalFilename);
			String fileMd5 = attach.getHash();
			if (StringUtils.isBlank(fileMd5)) {	//参数中没有MD5,则生成
				fileMd5 = SystemUtil.md5FileHex(saveFile);
				if (fileMd5 != null) {
					attach.setHash(fileMd5);
				}
			}
			logger.info("update load attach save file:" + saveFile.getAbsolutePath() + ", result:" + saveResult);
			
			String savePath = saveFile.getAbsolutePath();
			if (StringUtils.isEmpty(attach.getFilename())) {
				attach.setFilename(originalFilename);
			}
			String mime = attach.getMimeType();
			if (StringUtils.isBlank(mime)) {
				mime = SystemUtil.getMime(savePath);
				attach.setMimeType(mime);
			}
			if (attach.getType() == null || attach.getType() == 0) {
				int type = SystemUtil.getAttachType(mime);
				attach.setType(type);
			}
			
			Date date = new Date();
			if (attach.getCreateTime() == null) {
				attach.setCreateTime(date);
			}
			
			if (attach.getModifyTime() == null) {
				attach.setModifyTime(date);
			}
			
			attach.setSize(saveFile.length());
			
			saveResult = attachService.addAttach(attach);
		}
		if (saveResult) {
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setReason("上传成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("上传失败");
		}
		logger.info("update load attach: " + saveFile + ", result:" + saveResult);
		return actionResult;
	}
	
	@RequestMapping("att/{sid}")
    @ResponseBody
	public ResponseEntity<InputStreamResource> downloadAttach(@PathVariable String sid, @RequestParam(name = "userSid", required = true) String userSid, HttpServletRequest request) {
		boolean hasContent = false;
		InputStreamResource inputStreamResource = null;
		HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = null;
		if (StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(userSid)) {
			User user = new User();
			user.setSid(userSid);
			user = userService.getUserById(user);
			boolean isOk = checkUser(user);
			
			if (isOk) {
				Attach attach = new Attach();
				attach.setSid(sid);
				attach = attachService.getById(attach);
				if (attach != null && StringUtils.isNotBlank(attach.getLocalPath())) {	//附件存在
					String attLocalPath = attach.getLocalPath();
					File file = getAttachFile(attLocalPath);
					if (file != null && file.exists()) {	//文件存在
						String filename = attach.getFilename();
						String mime = attach.getMimeType();
						String encodeFilename = null;
						try {
							encodeFilename = URLEncoder.encode(filename, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							encodeFilename = filename;
						}
						MediaType mediaType = null;
				        try {
				            mediaType = MediaType.parseMediaType(mime);
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
						
						headers.setContentType(mediaType);
				        headers.setContentLength(file.length());
				        StringBuilder sb = new StringBuilder();
				        sb.append("attachment;filename=").append(encodeFilename).append(";filename*=UTF-8''").append(encodeFilename);
				        headers.add(HttpHeaders.CONTENT_DISPOSITION, sb.toString());
				        try {
							inputStreamResource = new InputStreamResource(new FileInputStream(file));
							hasContent = true;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			} else {	//用户不存在或者用户被禁用
				httpStatus = HttpStatus.UNAUTHORIZED;
			}
		}
		if (hasContent) {
			httpStatus = HttpStatus.OK;
		} else {
			if (httpStatus == null) {
				httpStatus = HttpStatus.NOT_FOUND;
			}
		}
		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, httpStatus);
	}
	
	/**
	 * 只改变笔记的删除状态
	 * @param userSid 用户的sid
	 * @param noteList 笔记列表
	 * @return
	 */
	@RequestMapping("state/{userSid}/change")
	@ResponseBody
	public ActionResult<Void> changeNoteState(@PathVariable String userSid, @RequestBody List<NoteInfo> noteList) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid) || CollectionUtils.isEmpty(noteList)) {	//参数为空
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		User user = new User();
		user.setSid(userSid);
		
		user = userService.getUserById(user);
		boolean isOk = checkUser(actionResult, user);
		if (!isOk) {	//检查用户状态
			return actionResult;
		}
		boolean success = noteService.updateState(noteList);
		if (success) {
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setReason("更新成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("更新失败");
		}
		return actionResult;
	}
	
	/**
	 * @param avatarPath 头像的相对路径，存在数据库里的
	 * @param sid
	 * @param ext 文件的后缀，不带.
	 * @return
	 */
	private File getAttachSaveFile(String avatarFilename) {
		String rootDir = SystemCache.getUploadPath();
		File file = new File(rootDir, SystemUtil.generateAttachFilePath(AttachUsage.ATTACH, avatarFilename));
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		logger.info("note controller get avatar save file:" + file);
		return file;
	}
	
	/**
	 * 根据头像名称获取头像在本地磁盘的物理地址
	 * @param avatarFilename
	 * @return
	 */
	private File getAttachFile(String avatarFilename) {
		String rootDir = SystemCache.getUploadPath();
		File file = new File(rootDir, SystemUtil.generateAttachFilePath(AttachUsage.ATTACH, avatarFilename));
		return file;
	}
}
