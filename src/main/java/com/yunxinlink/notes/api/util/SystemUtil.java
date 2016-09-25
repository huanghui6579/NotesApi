package com.yunxinlink.notes.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SystemUtil {
	private static final Logger logger = Logger.getLogger(SystemUtil.class);
	
	/**
     * 获取系统的system.properties文件
     * @return
     * @author tiger
     * @version 1.0.0
     * @update 2016年4月10日 下午5:05:22
     */
    public static Properties getAppSystemProps() {
    	InputStream is = null;
    	Properties properties = null;
    	is = SystemUtil.class.getResourceAsStream("/system.properties");
    	try {
    		properties = new Properties();
			properties.load(is);
		} catch (IOException e) {
			logger.error("----getAppSystemProps---error---" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("----getAppSystemProps----InputStream--close---error---" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
    	return properties;
    }
    
    /**
     * 获取uuid生成器的配置信息，返回的是int数组[0]:workid,[1]:regionId
     * @param properties
     * @return
     */
    public static int[] getUUIDProps(Properties properties) {
    	String workIdStr = properties.getProperty("uuid.workerId");
    	String regionIdStr = properties.getProperty("uuid.regionId");
    	int workId = 0;
    	int regionId = 0;
    	int[] ids = new int[2];
    	try {
    		workId = Integer.parseInt(workIdStr);
    		regionId = Integer.parseInt(regionIdStr);
		} catch (NumberFormatException e) {
			logger.error("---getUUIDProps---error---" + e.getMessage());
		}
    	ids[0] = workId;
    	ids[1] = regionId;
    	return ids;
    }
}
