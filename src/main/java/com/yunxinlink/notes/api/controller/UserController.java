package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.UserDto;
import com.yunxinlink.notes.api.init.SystemCache;
import com.yunxinlink.notes.api.model.AccountType;
import com.yunxinlink.notes.api.model.OpenApi;
import com.yunxinlink.notes.api.model.PasswordResetInfo;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IOpenApiService;
import com.yunxinlink.notes.api.service.IUserService;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.SystemUtil;

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
				u.setAvatar(null);
				u.setPassword(null);
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
			user.setAvatar(null);
			user.setPassword(null);
			UserDto resultDto = new UserDto();
			resultDto.setType(AccountType.TYPE_LOCAL);
			resultDto.setUser(user);
			actionResult.setData(resultDto);
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setReason("注册成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("注册失败，请稍后再试");
		}
		logger.info("register user result:" + actionResult);
		return actionResult;
	}
	
	/**
	 * 修改用户信息
	 * @param sid 用户的sid
	 * @param userDto
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"{sid}/modify"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> modifyUser(@PathVariable String sid, UserDto userDto, @RequestParam(value = "avatarFile", required = false) MultipartFile[] files, HttpServletRequest request) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(sid) || userDto == null || userDto.getUser() == null) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		User user = userDto.getUser();
		if (files != null && files.length == 1) {	//有头像,且只处理一个头像
			logger.info("modify user has avatar and will save file");
			MultipartFile file = files[0];
			if (!file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				//文件的后缀
				String ext = FilenameUtils.getExtension(originalFilename);
				String avatarFilename = SystemUtil.generateAttachFilename(sid, ext);
				File saveFile = getAvatarSaveFile(avatarFilename);
				boolean saveResult = false;
				if (saveFile != null) {
					saveResult = saveFile(file, saveFile);
				}
				if (saveResult) {	//保存成功
					user.setAvatar(avatarFilename);
					String fileMd5 = user.getAvatarHash();
					if (StringUtils.isBlank(fileMd5)) {	//参数中没有MD5,则生成
						fileMd5 = SystemUtil.md5FileHex(saveFile);
						if (fileMd5 != null) {
							user.setAvatarHash(fileMd5);
						}
					}
				}
				logger.info("modify user has avatar and save result:" + saveResult + ", save file is:" + saveFile);
			} else {
				logger.info("modify user has avatar but file is empty:" + file);
			}
		} else {
			logger.info("modify user avatar file is null or more than 1");
		}
		user.setSid(sid);
		boolean success = false;
		try {
			success = userService.updateUser(user);
		} catch (Exception e) {
			actionResult.setResultCode(ActionResult.RESULT_ERROR);
			actionResult.setReason("服务器错误，请稍后再试");
			logger.error("modify user save user info error:" + e.getMessage());
		}
		if (success) {	//成功
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setReason("保存成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("保存失败");
		}
		logger.info("modify user result:" + success + ", user:" + user);
		return actionResult;
	}
	
	/**
	 * 获取用户基本信息
	 * @param sid 用户的sid
	 * @return
	 */
	@RequestMapping(value = {"{sid}/info"})
	@ResponseBody
	public ActionResult<User> getUserInfo(@PathVariable String sid) {
		ActionResult<User> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(sid)) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
		} else {
			User param = new User();
			param.setSid(sid);
			User user = userService.getUserById(param);
			if (user != null) {	//用户可用
				if (user.checkState()) {
					user.setPassword(null);
					user.setAvatar(null);
					actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
					actionResult.setData(user);
					actionResult.setReason("获取用户信息成功");
				} else {
					actionResult.setResultCode(ActionResult.RESULT_STATE_DISABLE);
					actionResult.setReason("该用户已被禁用");
				}
			} else {
				actionResult.setResultCode(ActionResult.RESULT_FAILED);
				actionResult.setReason("该用户不存在");
			}
		}
		return actionResult;
	}
	
	/**
	 * 下载用户头像
	 * @param sid
	 * @return
	 */
	@RequestMapping("{sid}/avatar")
    @ResponseBody
	public ResponseEntity<InputStreamResource> downloadAvatar(@PathVariable String sid, HttpServletRequest request) {
		//1、先根据用户sid获取用户头像
		User param = new User();
		param.setSid(sid);
		String avatarName = userService.getUserAvatar(param);
		boolean hasContent = false;
		InputStreamResource inputStreamResource = null;
		HttpHeaders headers = new HttpHeaders();
		if (!StringUtils.isBlank(avatarName)) {	//用户头像存在
			File file = getAvatarFile(avatarName);
			if (file != null && file.exists()) {	//文件存在
				String filePath = file.getAbsolutePath();
				String filename = file.getName();
				String encodeFilename = null;
				try {
					encodeFilename = URLEncoder.encode(filename, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
					encodeFilename = filename;
				}
				ServletContext context = request.getServletContext();
		        MediaType mediaType = null;
		        try {
		            mediaType = MediaType.parseMediaType(context.getMimeType(filePath));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        if (mediaType == null) {
		            mediaType = MediaType.IMAGE_PNG;
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
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		if (hasContent) {
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, httpStatus);
	}
	
	/**
	 * 发送找回密码的邮件
	 * @param account 账号，目前是邮箱，以后可能还有其他值
	 * @return
	 */
	@RequestMapping("{account}/forget")
    @ResponseBody
	public ActionResult<Void> forgetPassword(@PathVariable String account, HttpServletRequest request) {
		ActionResult<Void> actionResult = new ActionResult<>();
		//根据账号来查询该用户的信息
		User param = new User();
		param.setEmail(account);
		User user = userService.getUser(param);
		if (user == null) {
			actionResult.setResultCode(ActionResult.RESULT_DATA_NOT_EXISTS);
			actionResult.setReason("用户不存在");
		} else if (!user.checkState()) {	//用户状态不可用
			actionResult.setResultCode(ActionResult.RESULT_STATE_DISABLE);
			actionResult.setReason("用户被禁用");
		} else {
			PasswordResetInfo resetInfo = new PasswordResetInfo();
			String secretKey = UUID.randomUUID().toString();  //密钥  
			//60*60*1000 = 3600000
	        Timestamp outDate = new Timestamp(System.currentTimeMillis() + 3600000);//60分钟后过期  
	        long date = outDate.getTime() / 1000 * 1000;	//忽略毫秒数  
	        
	        resetInfo.setAccount(account);
	        resetInfo.setValidataCode(secretKey);
	        resetInfo.setOutDate(outDate);
	        //保存到数据库
	        
	        boolean success = userService.addPasswordResetInfo(resetInfo);
	        
	        if (!success) {
	        	actionResult.setResultCode(ActionResult.RESULT_FAILED);
	        	actionResult.setReason("发送失败");
	        	logger.info("forget password add or update info failed");
			} else {
				String key = resetInfo.generateKey(date);
		        //MD5加密
		        String digitalSignature = DigestUtils.md5Hex(key);
		        
		        String emailTitle = "云信笔记密码找回";  
		        String path = request.getContextPath();  
		        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";  
		        String resetPassHref = basePath + "user/resetPassword?sid=" + digitalSignature + "&account=" + account;
		        String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href=" + resetPassHref +" target='_BLANK'>点击我重新设置密码</a>" +  
		                "<br/>tips:本邮件超过60分钟,链接将会失效，需要重新申请'找回密码'" + key + "\t" + digitalSignature; 
		        logger.info("forget password reset password href:" + resetPassHref);
		        //TODO 发送邮件
		        actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
		        actionResult.setReason("邮件已发送");
			}
		}
		return actionResult;
	}
	
	/**
	 * @param avatarPath 头像的相对路径，存在数据库里的
	 * @param sid
	 * @param ext 文件的后缀，不带.
	 * @return
	 */
	private File getAvatarSaveFile(String avatarFilename) {
		String rootDir = SystemCache.getUploadPath();
		File file = new File(rootDir, SystemUtil.generateAttachFilePath(AttachUsage.AVATAR, avatarFilename));
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		logger.info("user controller get avatar save file:" + file);
		return file;
	}
	
	/**
	 * 根据头像名称获取头像在本地磁盘的物理地址
	 * @param avatarFilename
	 * @return
	 */
	private File getAvatarFile(String avatarFilename) {
		String rootDir = SystemCache.getUploadPath();
		File file = new File(rootDir, SystemUtil.generateAttachFilePath(AttachUsage.AVATAR, avatarFilename));
		return file;
	}
}
