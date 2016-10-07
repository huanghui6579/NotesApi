package com.yunxinlink.notes.api.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.model.DeviceInfo;
import com.yunxinlink.notes.api.service.IDeviceInfoService;

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
	
	/**
	 * 保存客户端上传的设备信息
	 * @param deviceInfo
	 * @return
	 */
	@RequestMapping(value = {"activate"}, method = RequestMethod.POST)
	@ResponseBody
	public ActionResult<Void> addDevice(DeviceInfo deviceInfo, HttpServletRequest request) {
		ActionResult<Void> actionResult = new ActionResult<>();
		getClientInfo(request);
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
	
	private void getClientInfo(HttpServletRequest request) {
		String agent = request.getHeader(HttpHeaders.USER_AGENT);
		logger.info("agent:" + agent);
//		StringTokenizer st = new StringTokenizer(agent, ";");   
//		st.nextToken();
//		//得到用户的浏览器名   
//		String userbrowser = st.nextToken(); 
//		logger.info("userbrowser:" + userbrowser);
//		//得到用户的操作系统名   
//		String useros = st.nextToken();
//		logger.info("useros:" + useros);
	}
}
