package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.dto.VersionInfoDto;
import com.yunxinlink.notes.api.model.DeviceInfo;
import com.yunxinlink.notes.api.model.Platform;
import com.yunxinlink.notes.api.model.VersionInfo;
import com.yunxinlink.notes.api.service.IDeviceInfoService;
import com.yunxinlink.notes.api.service.IVersionService;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.Constant;

/**
 * 设备基本信息的控制器
 * @author huanghui1
 *
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {
	@Autowired
	private IDeviceInfoService deviceInfoService;
	
	@Autowired
	private IVersionService versionService;
	
	/**
	 * 保存客户端上传的设备信息
	 * @param deviceInfo
	 * @return
	 */
	@RequestMapping(value = {"activate"}, method = RequestMethod.POST)
	@ResponseBody
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
	 * 获取最新的版本信息
	 * @param versionInfo
	 * @return
	 */
	@RequestMapping(value = "app/check", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<VersionInfoDto> checkAppVersion(VersionInfo versionInfo) {
		ActionResult<VersionInfoDto> actionResult = new ActionResult<>();
		if (versionInfo == null || versionInfo.getPlatform() == null) {
			actionResult.setResultCode(ActionResult.RESULT_PARAM_ERROR);
			actionResult.setReason("参数错误");
			return actionResult;
		}
		versionInfo = versionService.getLastVersion(versionInfo, 0, 1);
		if (versionInfo != null) {
			VersionInfoDto infoDto = versionInfo.convert2Dto();
			actionResult.setResultCode(ActionResult.RESULT_SUCCESS);
			actionResult.setData(infoDto);
			actionResult.setReason("获取成功");
		} else {
			actionResult.setResultCode(ActionResult.RESULT_FAILED);
			actionResult.setReason("没有新版本");
		}
		return actionResult;
	}
	
	/**
	 * 下载APP包
	 * @param versionInfo
	 * @return
	 */
	@RequestMapping(value = "app/download")
	@ResponseBody
	public ResponseEntity<InputStreamResource> downloadApp(VersionInfo versionInfo) {
		InputStreamResource inputStreamResource = null;
		HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = null;
		if (versionInfo != null && versionInfo.getId() > 0) {
			versionInfo = versionService.getVersionInfo(versionInfo.getId());
			if (versionInfo != null && StringUtils.isNotBlank(versionInfo.getLocalPath())) {
				String localPath = versionInfo.getLocalPath();
				File file = getAppFile(versionInfo.getPlatform(), localPath);
				inputStreamResource = parseFile(file, null, headers);
			}
		}
		
		if (inputStreamResource != null) {
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, httpStatus);
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
	 * 获取APP的本地路径
	 * @param platform
	 * @param filename
	 * @return
	 */
	private File getAppFile(int platform, String filename) {
		String prefix = getAppPrefix(platform);
		if (prefix == null) {
			return null;
		}
		filename = prefix + File.separator + filename;
		return getAttachFile(filename, AttachUsage.SOFT_ATTACH, false);
	}
	
}
