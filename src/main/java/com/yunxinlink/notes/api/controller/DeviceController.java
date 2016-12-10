package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunxinlink.notes.api.annotation.TokenIgnore;
import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.init.IdGenerator;
import com.yunxinlink.notes.api.model.BugReport;
import com.yunxinlink.notes.api.model.DeviceInfo;
import com.yunxinlink.notes.api.service.IBugReportService;
import com.yunxinlink.notes.api.service.IDeviceInfoService;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.SystemUtil;

/**
 * 设备基本信息的控制器
 * @author huanghui1
 *
 */
@Controller
@RequestMapping("/api/device")
public class DeviceController extends BaseController {
	@Autowired
	private IDeviceInfoService deviceInfoService;
	
	@Autowired
	private IBugReportService bugReportService;
	
	/**
	 * 保存客户端上传的设备信息
	 * @param deviceInfo
	 * @return
	 */
	@RequestMapping(value = {"activate"}, method = RequestMethod.POST)
	@ResponseBody
	@TokenIgnore
	public ActionResult<Void> addDevice(DeviceInfo deviceInfo, HttpServletRequest request) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (deviceInfo == null) {
			actionResult.setReason("添加失败，设备信息为空");
			logger.info("device info is null");
		} else {
			Date time = new Date();
			deviceInfo.setCreateTime(time);
			deviceInfo.setModifyTime(time);
			if (!deviceInfo.checkInfo()) {	//设备的IMEI为空或者0
				deviceInfo.setImei(null);
				logger.info("add device info imei is empty:" + deviceInfo.getImei());
			}
			boolean success = deviceInfoService.addDeviceInfo(deviceInfo);
			if (success) {
				actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
				actionResult.setReason("添加成功");
				logger.info("add device info success :" + deviceInfo);
			} else {
				actionResult.setReason("添加失败，该设备已存在");
			}
		}
		return actionResult;
	}
	
	/**
	 * 上报日志记录
	 * @param bugReport
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"bug/report"}, method = RequestMethod.POST)
	@ResponseBody
	@TokenIgnore
	public ActionResult<Void> reportBug(BugReport bugReport, @RequestParam(value = "logFile", required = false) MultipartFile multipartFile,  HttpServletRequest request) {
		ActionResult<Void> actionResult = new ActionResult<>();
		if (bugReport == null) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		if (multipartFile != null) {	//有日志文件，则保存
			String originalFilename = multipartFile.getOriginalFilename();
			String sid = IdGenerator.generateUUID();
			//文件的后缀
			String ext = FilenameUtils.getExtension(originalFilename);
			//文件的后缀
			String attachFilename = SystemUtil.generateAttachFilename(sid, ext);
			File saveFile = getAttachSaveFile(attachFilename, AttachUsage.BUG_REPORT);
			boolean saveResult = false;
			if (saveFile != null) {
				saveResult = saveFile(multipartFile, saveFile);
			}
			if (saveResult) {	//保存成功
				
				bugReport.setLogPath(attachFilename);
			}
			logger.info("bug report save log file to local result:" + saveResult + ", file--->" + saveFile);
		} else {
			logger.info("bug report but no log file:" + bugReport);
		}
		boolean success = false;
		try {
			bugReport.setCreateTime(new Date());
			success = bugReportService.addBug(bugReport);
		} catch (Exception e) {
			logger.error("bug report error:" + e);
		}
		if (success) {
			actionResult.setReason("上报成功");
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
		} else {
			actionResult.setReason("上报失败");
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
		}
		return actionResult;
	}
}
