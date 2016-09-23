package com.yunxinlink.notes.api.init;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.yunxinlink.notes.api.util.CustomUUID;
import com.yunxinlink.notes.api.util.SystemUtil;

/**
 * 初始化uuid的生成器，程序启动时加载
 * @author huanghui1
 *
 */
@Component
public class IdGenerator {
	private static final Logger logger = Logger.getLogger(IdGenerator.class);
	
	private static CustomUUID customUUID = null;
	
	@PostConstruct
	private void init() {
		logger.info("IdGenerator init ...");
		initUUID();
	}
	
	/**
	 * 初始化uuid的配置信息
	 */
	private void initUUID() {
		Properties properties = SystemUtil.getAppSystemProps();
		int[] uuidProps = null;
		if (properties != null) {
			uuidProps = SystemUtil.getUUIDProps(properties);
		} else {
			uuidProps = new int[] {0, 0};
			logger.info("initUUID error properties is null");
		}
		customUUID = new CustomUUID(uuidProps[0], uuidProps[1]);
		logger.info("IdGenerator initUUID ...");
	}
	
	/**
	 * 返回uuid
	 * @return
	 */
	public static String generateUUID() {
		if (customUUID == null) {
			customUUID = new CustomUUID(0, 0);
		}
		return String.valueOf(customUUID.generate());
	}
}
