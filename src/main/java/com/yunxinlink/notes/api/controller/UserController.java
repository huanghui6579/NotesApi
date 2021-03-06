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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunxinlink.notes.api.annotation.TokenIgnore;
import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.PasswordDto;
import com.yunxinlink.notes.api.dto.PasswordResetInfoDto;
import com.yunxinlink.notes.api.dto.UserDto;
import com.yunxinlink.notes.api.init.SystemCache;
import com.yunxinlink.notes.api.model.AccountType;
import com.yunxinlink.notes.api.model.OpenApi;
import com.yunxinlink.notes.api.model.PasswordResetInfo;
import com.yunxinlink.notes.api.model.Token;
import com.yunxinlink.notes.api.model.User;
import com.yunxinlink.notes.api.service.IEmailService;
import com.yunxinlink.notes.api.service.IOpenApiService;
import com.yunxinlink.notes.api.service.IUserService;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.Constant;
import com.yunxinlink.notes.api.util.SystemUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 用户的控制器
 * @author huanghui1
 *
 */
@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController {
	@Autowired
	private IOpenApiService openApiService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IEmailService emailService;
	
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
	@TokenIgnore
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
		//是否需要创建token
		boolean createToken = false;
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
					createToken = true;
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
						createToken = shouldCreateToken(user);
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
					createToken = shouldCreateToken(user);
				}
				UserDto result = new UserDto();
				result.setUser(u);
				result.setType(AccountType.TYPE_LOCAL);
				actionResult.setData(result);
			}
		}
		if (createToken && actionResult.getData() != null && actionResult.getData().getUser() != null) {
			User resultUser = actionResult.getData().getUser();
			saveToken(resultUser);
		}
		actionResult.setResultCode(code);
		actionResult.setReason(reason);
		logger.info("login user:" + actionResult.getData());
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
	@TokenIgnore
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
			
			saveToken(user);
			
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
	 * 绑定用户，当用户不存在时，则创建，存在时，则根据用户账号和密码进行校验
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value = {"bind"}, method = RequestMethod.POST)
	@ResponseBody
	@TokenIgnore
	public ActionResult<UserDto> bindUser(UserDto userDto) {
		ActionResult<UserDto> actionResult = new ActionResult<>();
		if (userDto == null || userDto.getUser() == null || userDto.getUser().getPassword() == null) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		User user = userDto.getUser();
		String email = user.getEmail();
		String mobile = user.getMobile();
		if (StringUtils.isBlank(email) && StringUtils.isBlank(mobile)) {	//账号为空
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("账号不能为空");
			return actionResult;
		}
		String tokenStr = user.getToken();
		User u = userService.getUser(user);
		boolean success = false;
		if (u != null) {	//校验密码，可视为登录
			success = user.getPassword().equals(u.getPassword());
			if (success) {
				user = u;
			} else {
				actionResult.setResultCode(ActionResult.RESULT_VALIDATE_FAILED);
				actionResult.setReason("密码错误");
				return actionResult;
			}
		} else {	//创建用户，可视为注册
			success = userService.addUser(user);
		}
		if (success) {
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			user.setToken(tokenStr);
			boolean createToken = shouldCreateToken(user);
			if (createToken) {
				saveToken(u);
			}
			
			UserDto resultDto = new UserDto();
			resultDto.setType(AccountType.TYPE_LOCAL);
			resultDto.setUser(user);
			actionResult.setData(resultDto);
			actionResult.setReason("绑定成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("绑定失败");
		}
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
		String tokenSubject = (String) request.getAttribute(Constant.KEY_TOKEN_SUBJECT);
		ActionResult<Void> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(sid) || userDto == null || userDto.getUser() == null || !sid.equals(tokenSubject)) {
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
	
	@RequestMapping(value = "{sid}/pwd/modify", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> modifyPassword(@PathVariable String sid, PasswordDto passwordDto, HttpServletRequest request) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(sid) || passwordDto == null || !passwordDto.hasPassword()) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		String password = passwordDto.getNewPassword();
		String confirmPassword = passwordDto.getConfirmPassword();
		if (!password.equals(confirmPassword)) {	//两次输入的密码不一致
			actionResult.setResultCode(ActionResult.RESULT_NOT_EQUALS);
			actionResult.setReason("两次输入的密码不一致");
			return actionResult;
		}
		
		passwordDto.setUserSid(sid);
		
		boolean success = userService.updatePassword(passwordDto);
		if (success) {
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setReason("密码修改成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("密码修改失败");
		}
		return actionResult;
	}
	
	/**
	 * 获取用户基本信息
	 * @param sid 用户的sid
	 * @return
	 */
	@RequestMapping(value = {"{sid}/info"})
	@ResponseBody
	public ActionResult<User> getUserInfo(@PathVariable String sid, HttpServletRequest request) {
		String tokenSubject = (String) request.getAttribute(Constant.KEY_TOKEN_SUBJECT);
		ActionResult<User> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(sid) || !sid.equals(tokenSubject)) {
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
		String tokenSubject = (String) request.getAttribute(Constant.KEY_TOKEN_SUBJECT);
		InputStreamResource inputStreamResource = null;
		HttpHeaders headers = new HttpHeaders();
		if (!StringUtils.isBlank(avatarName) && sid.equals(tokenSubject)) {	//用户头像存在
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
	 * 跳转到发送邮件的界面
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "showForget", method = RequestMethod.GET)
	@TokenIgnore
	public String forgetPassword(String account, Model model) {
		if (StringUtils.isNotBlank(account)) {
			model.addAttribute("account", account);
		}
		return "user/send-mail";
	}
	
	/**
	 * 发送找回密码的邮件
	 * @param account 账号，目前是邮箱，以后可能还有其他值
	 * @return
	 */
	@RequestMapping(value = "{account}/forget", method = RequestMethod.POST)
    @ResponseBody
    @TokenIgnore
	public ActionResult<Void> forgetPassword(@PathVariable String account, HttpServletRequest request) {
		ActionResult<Void> actionResult = new ActionResult<>();
		//根据账号来查询该用户的信息
		if (!SystemUtil.isEmailAddress(account)) {	//判断是否是邮箱地址
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("不是正确的邮箱地址");
			return actionResult;
		}
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
	        long date = getOutTime(outDate);	//忽略毫秒数  
	        
	        resetInfo.setUserSid(user.getSid());
	        resetInfo.setAccount(account);
	        resetInfo.setValidataCode(secretKey);
	        outDate.setTime(date);
	        resetInfo.setOutDate(outDate);
	        //保存到数据库
	        logger.info("send mail outDate:" + outDate);
	        boolean success = userService.addPasswordResetInfo(resetInfo);
	        
	        if (!success) {
	        	actionResult.setResultCode(ActionResult.RESULT_FAILED);
	        	actionResult.setReason("发送失败");
	        	logger.info("forget password add or update info failed");
			} else {
				String key = resetInfo.generateKey(date);
		        //MD5加密
				logger.info("send mail key:" + key);
		        String digitalSignature = DigestUtils.md5Hex(key);
		        
		        String basePath = getBasePath(request);  
		        String resetPassHref = basePath + "user/resetPassword?sid=" + digitalSignature + "&account=" + account;

		        logger.info("forget password reset password href:" + resetPassHref);
		        
		        success = emailService.sendEmail(account, resetPassHref);
		        
		        if (success) {
		        	actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
		        	actionResult.setReason("邮件已发送");
				} else {
					actionResult.setResultCode(ActionResult.RESULT_FAILED);
		        	actionResult.setReason("邮件发送失败");
				}
			}
		}
		return actionResult;
	}
	
	/**
	 * 跳转到重置密码的界面
	 * @param sid
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = RequestMethod.GET)
	@TokenIgnore
	public String showResetPassword(String sid, String account, Model model, HttpServletRequest request) {
		String toUrl = "user/reset-pwd";
		ActionResult<Void> actionResult = new ActionResult<>();
		String basePath = getBasePath(request);  
		String errorTipUrl = basePath + "user/showForget?account=" + account;
		model.addAttribute("errorTipUrl", errorTipUrl);
		if (StringUtils.isBlank(sid) || StringUtils.isBlank(account)) {
			actionResult.setReason("链接无效");
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			model.addAttribute("actionResult", actionResult);
			logger.info("reset password sid or account is empty");
			return toUrl;
		}
		
		PasswordResetInfoDto resetInfoDto = userService.getPwdResetInfo(account);
		if (resetInfoDto == null) {	//记录不存在
			actionResult.setReason("链接无效");
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			model.addAttribute("actionResult", actionResult);
			logger.info("reset password reset info is null");
			return toUrl;
		}
		
		if (!resetInfoDto.checkUserState()) {	//用户状态不可用，用户被禁用了
			actionResult.setReason("用户被禁用了");
			actionResult.setResultCode(ActionResult.RESULT_STATE_DISABLE);
			model.addAttribute("actionResult", actionResult);
			logger.info("reset password user is disable");
			return toUrl;
		}
		
		Timestamp outDate = resetInfoDto.getOutDate();
		if (outDate.getTime() <= System.currentTimeMillis()) {	//已过期
			actionResult.setReason("链接已过期");
			actionResult.setResultCode(ActionResult.RESULT_OUT_DATE);
			model.addAttribute("actionResult", actionResult);
			logger.info("reset password reset info is out date");
			return toUrl;
		}
		
		long date = getOutTime(outDate);	//忽略毫秒数  
		logger.info("rest password mail outDate:" + outDate);
		String key = resetInfoDto.generateKey(date);          //数字签名
		logger.info("rest password mail key:" + key);
		String digitalSignature = DigestUtils.md5Hex(key);
		logger.info("reset password sid: " + sid + ", digitalSignature: " + digitalSignature);
		if(!sid.equals(digitalSignature)) {  
			actionResult.setReason("链接已失效");
			actionResult.setResultCode(ActionResult.RESULT_OUT_DATE);
			model.addAttribute("actionResult", actionResult);
			logger.info("reset password url is not validate");
			return toUrl;
		}
		actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
		model.addAttribute("actionResult", actionResult);
		model.addAttribute("isSuccess", true);
		model.addAttribute("account", account);
		model.addAttribute("userSid", resetInfoDto.getUserSid());
		return toUrl;
	}
	
	/**
	 * 重置密码
	 * @param userSid 用户的sid
	 * @param password 新密码
	 * @param confirmPassword 确认新密码
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	@ResponseBody
	@TokenIgnore
	public ActionResult<Void> resetPassword(@RequestParam(name = "userSid", required = true) String userSid, String password, String confirmPassword) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (StringUtils.isBlank(userSid) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		if (!password.equals(confirmPassword)) {	//两次输入的密码不一致
			actionResult.setResultCode(ActionResult.RESULT_NOT_EQUALS);
			actionResult.setReason("两次输入的密码不一致");
			return actionResult;
		}
		
		User param = new User();
		param.setSid(userSid);
		
		User user = userService.getUserById(param);
		boolean isOk = checkUser(actionResult, user);
		if (!isOk) {
			return actionResult;
		}
		
		param = new User();
		param.setSid(userSid);
		param.setPassword(password);
		//修改新密码
		boolean success = false;
		try {
			success = userService.updatePassword(param);
		} catch (Exception e) {
			logger.error("modify password param:" + param + ", error:" + e.getMessage());
		}
		if (success) {
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setReason("密码修改成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("密码修改失败");
		}
		return actionResult;
	}
	
	/**
	 * 校验用户的合法性，主要通过账号和密码来验证类似于登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value = {"validate"}, method = RequestMethod.POST)
	@ResponseBody
	@TokenIgnore
	public ActionResult<UserDto> validateUser(User user) {
		ActionResult<UserDto> actionResult = new ActionResult<>();
		if (user == null) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		int code = 0;
		String reason = null;
		
		User u = userService.getUserByAccount(user);
		if (u == null) {
			code = ActionResult.RESULT_VALIDATE_FAILED;
			reason = "用户账号或密码错误";
		} else {
			u.setAvatar(null);
			u.setPassword(null);
			if (!u.checkState()) {	//用户不可用
				code = ActionResult.RESULT_STATE_DISABLE;
				reason = "当前账号已被禁用";
			} else {
				code = ActionResult.RESULT_SUCCESS;
				reason = "验证成功";
			}
			UserDto result = new UserDto();
			result.setUser(u);
			actionResult.setData(result);
		}
		actionResult.setResultCode(code);
		actionResult.setReason(reason);
		return actionResult;
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
	
	/**
	 * 忽略毫秒数
	 * @param date
	 * @return
	 */
	private long getOutTime(Date date) {
		return date.getTime() / 1000 * 1000;
	}
	
	/**
	 * 是否需要创建token，当用户没有token或者token过期时，需要创建
	 * @param user
	 * @return
	 */
	private boolean shouldCreateToken(User user) {
		if (user == null) {
			return false;
		}
		boolean createToken = false;
		if (StringUtils.isNotBlank(user.getToken())) {	//用户当前有token
			Token token = SystemUtil.parseToken(user.getToken());
			if (token == null || token.isExpired()) {
				logger.info("user has no token or token is expired:" + token);
				
				createToken = true;
			}
		} else {
			createToken = true;
		}
		return createToken;
	}
	
	/**
	 * 生成并保存token
	 * @param user
	 */
	private void saveToken(User user) {
		Token token = SystemUtil.generateToken(user.getSid());
		if (token != null) {
			user.setToken(token.getContent());
			CacheManager cacheManager = CacheManager.getInstance();
			Cache cache = cacheManager.getCache(Constant.DEFAULT_TOKEN_CACHE);
			if (cache != null) {
				Element element = new Element(token.getId(), token.getContent());
				cache.put(element);
				logger.info("user save token sid:" + user.getSid() + ", generate new token:" + token.getContent());
			} else {
				logger.error("user save token but tolen cache is null");
			}
		}
	}
}
