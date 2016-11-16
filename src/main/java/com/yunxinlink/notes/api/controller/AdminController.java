package com.yunxinlink.notes.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.model.VersionInfo;

/**
 * 后台管理的控制器
 * @author huanghui-iri
 * @date 2016年11月16日 下午7:23:23
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	/**
	 * 发布版本
	 * @param versionInfo 版本信息
	 * @param multipartFile 软件包
	 * @param request
	 * @return
	 */
	public ActionResult<Void> releaseApp(@RequestParam(name = "versionInfo", required = true) VersionInfo versionInfo, @RequestParam(name = "appFile", required = true) MultipartFile multipartFile, HttpServletRequest request) {
		//TODO 待完成
		ActionResult<Void> actionResult = new ActionResult<>();
		return actionResult;
	}
}
