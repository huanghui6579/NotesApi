package com.yunxinlink.notes.api.service;

import java.util.List;

import com.yunxinlink.notes.api.model.DeviceInfo;

/**
 * 设备的信息服务层
 * @author huanghui1
 *
 */
public interface IDeviceInfoService {
	/**
	 * 添加设备信息
	 * @param DeviceInfo
	 * @return
	 */
	public boolean addDeviceInfo(DeviceInfo deviceInfo);
	
	/**
	 * 删除设备信息
	 * @param DeviceInfo
	 * @return
	 */
	public boolean deleteDeviceInfo(DeviceInfo deviceInfo);
	
	/**
	 * 更新设备信息信息
	 * @param DeviceInfo
	 * @return
	 */
	public boolean updateDeviceInfo(DeviceInfo deviceInfo);
	
	/**
	 * 根据id获取设备信息信息
	 * @param DeviceInfo
	 * @return
	 */
	public DeviceInfo getDeviceInfoById(DeviceInfo deviceInfo);
	
	/**
	 * 获取设备信息列表
	 * @return
	 */
	public List<DeviceInfo> getDeviceInfos();
}
