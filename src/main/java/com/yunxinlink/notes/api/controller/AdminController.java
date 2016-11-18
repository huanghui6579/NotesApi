package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.model.Platform;
import com.yunxinlink.notes.api.model.VersionInfo;
import com.yunxinlink.notes.api.service.IVersionService;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.Constant;
import com.yunxinlink.notes.api.util.SystemUtil;

/**
 * 后台管理的控制器
 * @author huanghui-iri
 * @date 2016年11月16日 下午7:23:23
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	@Autowired
	private IVersionService versionService;
	
	/**
	 * 发布版本
	 * @param versionInfo 版本信息
	 * @param multipartFile 软件包
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "app/release", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> releaseApp(VersionInfo versionInfo, @RequestParam(name = "appFile", required = true) MultipartFile multipartFile, HttpServletRequest request) {
		//TODO 待完成
		ActionResult<Void> actionResult = new ActionResult<>();
		if (actionResult == null || !versionInfo.checkContent()) {
			actionResult.setReason("内容不能为空");
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			return actionResult;
		}
		String originalFilename = multipartFile.getOriginalFilename();
		String localPath = generateAppFilename(originalFilename);
		File file = generateAppFile(versionInfo.getPlatform(), localPath);
		if (file == null) {
			actionResult.setReason("不支持的系统平台");
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			return actionResult;
		}
		//保存文件到本地
		boolean success = saveFile(multipartFile, file);
		if (!success) {
			actionResult.setReason("上传文件失败");
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			return actionResult;
		}
		//保存记录到数据库
		//该localPath是相对路径
		
		versionInfo.setLocalPath(localPath);
		versionInfo.setCreateTime(new Date());
		
		String hash = SystemUtil.md5FileHex(file);
		
		versionInfo.setHash(hash);
		versionInfo.setSize(file.length());
		
		success = versionService.addVersionInfo(versionInfo);
		if (!success) {
			file.delete();
			actionResult.setReason("版本发布失败");
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			return actionResult;
		}
		actionResult.setReason("版本发布成功");
		actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
		return actionResult;
	}
	
	/**
	 * 根据软件的平台获取不同的文件夹
	 * @param platform
	 * @return
	 */
	private String getAppPrefix(int platform) {
		String prefix = null;
		switch (platform) {
		case Platform.PLATFORM_ANDROID:	//Android的软件包
			prefix = Constant.ANDROID;
			break;
		case Platform.PLATFORM_IOS:	//ios的软件包
			prefix = Constant.IOS;
			break;

		default:
			break;
		}
		return prefix;
	}
	
	/**
	 * 生成app包的本地文件路径
	 * @param platform 系统的平台，如Android、IOS
	 * @param filename
	 * @see Platform
	 * @return
	 */
	private File generateAppFile(int platform, String filename) {
		String prefix = getAppPrefix(platform);
		if (prefix == null) {
			return null;
		}
		filename = prefix + File.separator + filename;
		return getAttachSaveFile(filename, AttachUsage.SOFT_ATTACH);
	}
	
	
	/**
	 * 生成软件包的名称
	 * @param filename
	 * @return
	 */
	private String generateAppFilename(String filename) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return year + File.separator + filename;
	}
}
