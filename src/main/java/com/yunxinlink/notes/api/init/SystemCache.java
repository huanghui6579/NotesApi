package com.yunxinlink.notes.api.init;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.yunxinlink.notes.api.util.SystemUtil;

/**
 * 系统缓存
 * @author huanghui1
 *
 */
@Component
public class SystemCache {
	private static final Logger logger = Logger.getLogger(SystemCache.class);
	
	/**
	 * 上传文件的存放路径
	 */
	private static String uploadPath = null;
	
	@PostConstruct
	private void init() {
		logger.info("SystemCache init ...");
		initUpLoadPath();
	}
	
	/**
	 * 初始化文件上传的保存路径
	 */
	private void initUpLoadPath() {
		if (uploadPath == null) {
			synchronized (this) {
				Properties properties = SystemUtil.getAppSystemProps();
				if (properties != null) {
					if (uploadPath == null) {
						uploadPath = SystemUtil.getAppUploadPath(properties);
					}
				}
			}
		}
		logger.info("system cache init upload file path :" + uploadPath);
	}
	
	/**
	 * 获取文件保存的根目录
	 * @return
	 */
	public static String getUploadPath() {
		return uploadPath;
	}
}
