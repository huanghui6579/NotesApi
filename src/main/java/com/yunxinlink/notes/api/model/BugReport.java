package com.yunxinlink.notes.api.model;

import java.io.Serializable;

/**
 * 上报的日志记录
 * @author huanghui-iri
 * @date 2016年12月8日 下午4:40:12
 */
public class BugReport extends DeviceInfo implements Serializable {
	private static final long serialVersionUID = -3430191486477744143L;
	
	/**
	 * 日志文件的路径
	 */
	private String logPath;

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	@Override
	public String toString() {
		return "BugReport [logPath=" + logPath + ", id=" + id + ", imei=" + imei + ", os=" + os + ", osVersion="
				+ osVersion + ", phoneModel=" + phoneModel + ", brand=" + brand + ", appVersionCode=" + appVersionCode
				+ ", appVersionName=" + appVersionName + ", createTime=" + createTime + "]";
	}
}
