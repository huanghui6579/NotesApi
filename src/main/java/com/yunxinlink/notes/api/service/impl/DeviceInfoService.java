package com.yunxinlink.notes.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunxinlink.notes.api.dao.DeviceInfoDao;
import com.yunxinlink.notes.api.model.DeviceInfo;
import com.yunxinlink.notes.api.service.IDeviceInfoService;

/**
 * 设备信息的服务层具体实现
 * @author huanghui1
 *
 */
@Service
public class DeviceInfoService implements IDeviceInfoService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DeviceInfoDao deviceInfoDao;
	
	@Override
	public boolean addDeviceInfo(DeviceInfo deviceInfo) {
		int rowId = 0;
		try {
			rowId = deviceInfoDao.add(deviceInfo);
			deviceInfo.setId(rowId);
		} catch (Exception e) {
			logger.error("add device info error:" + e.getMessage());
		}
		return rowId > 0;
	}

	@Override
	public boolean deleteDeviceInfo(DeviceInfo deviceInfo) {
		int rowId = 0;
		try {
			rowId = deviceInfoDao.delete(deviceInfo);
		} catch (Exception e) {
			logger.error("add device info error:" + e.getMessage());
		}
		return rowId > 0;
	}

	@Override
	public boolean updateDeviceInfo(DeviceInfo deviceInfo) {
		int rowId = 0;
		try {
			rowId = deviceInfoDao.update(deviceInfo);
		} catch (Exception e) {
			logger.error("update device info error:" + e.getMessage());
		}
		return rowId > 0;
	}

	@Override
	public DeviceInfo getDeviceInfoById(DeviceInfo deviceInfo) {
		try {
			return deviceInfoDao.selectById(deviceInfo);
		} catch (Exception e) {
			logger.error("get device info by id error:" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<DeviceInfo> getDeviceInfos() {
		// TODO Auto-generated method stub
		return null;
	}

}
