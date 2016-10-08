package com.yunxinlink.notes.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SystemUtil {
	private final static Logger logger = LoggerFactory.getLogger(SystemUtil.class);
	
	/**
     * 文件保存路根目录
     */
    public static String HOME_FILEPATH = new File(SystemUtils.getUserHome(), "Yunxin" + File.separator + "Notes" + File.separator + "Files").getAbsolutePath();
    
    /**
     * 头像文件夹的时间格式化，格式为:yyyy/MM/dd
     */
    private static SimpleDateFormat avatarFormat = new SimpleDateFormat("yyyy/MM/dd");
	
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
    
    /**
     * 获取文件存放根目录
     * 
     * @return
     * @author tiger
     * @version 1.0.0
     * @update 2016年4月10日 下午12:33:58
     */
    public static String getAppUploadPath(Properties properties) {
    	String uploadPath = null;
		try {
			if (properties == null) {
				properties = getAppSystemProps();
			}
			if (properties != null) {
                if (SystemUtils.IS_OS_WINDOWS) {
                    uploadPath = properties.getProperty("win.upload.dir");
                } else {
                    uploadPath = properties.getProperty("linux.upload.dir");
                }
				if (uploadPath != null) {
					File path = new File(uploadPath);
					if (!path.exists()) {
						path.mkdirs();
					}
					logger.info("----getAppUploadPath---uploadPath---properties-path-----" + path);
					if (path.isDirectory() && path.canWrite()) {	//该目录可用
						logger.info("----getAppUploadPath---uploadPath----form----properties------" + uploadPath);
						return uploadPath;
					} else {
						uploadPath = HOME_FILEPATH;
						logger.info("----getAppUploadPath---uploadPath---properties-can not write,set user dirs-----" + uploadPath);
					}
				}
			}
		} catch (Exception e) {
			logger.error("----getAppUploadPath---error---" + e.getMessage());
			e.printStackTrace();
		}
		if (StringUtils.isBlank(uploadPath)) {
			logger.info("----getAppUploadPath---uploadPath----form----properties---can---not---write---" + uploadPath);
            uploadPath = HOME_FILEPATH;
			logger.info("----getAppUploadPath---uploadPath----form----userHome------" + uploadPath);
		}
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			logger.info("----getAppUploadPath---uploadPath----not---exists----will----mkdirs------" + uploadPath);
			dir.mkdirs();
		}
		return uploadPath;
    }
    
    /**
     * 根据用户的sid获取用户存储的本地路径,这里只是相对路径，如:icon/2016/09/30/45754527857.png
     * @param sid
     * @param ext 文件的后缀名，不带.
     * @return
     */
    public static String generateAvatarFilePath(String sid, String ext) {
    	return generateAvatarFilePath(generateAvatarFilename(sid, ext));
    }
    
    /**
     * 根据用户的sid获取用户存储的本地路径,这里只是相对路径，如:icon/2016/09/30/45754527857.png
     * @param sid
     * @param ext 文件的后缀名，不带.
     * @return
     */
    public static String generateAvatarFilePath(String filename) {
    	return Constant.AVATAR_ROOT + File.separator + filename;
    }
    
    /**
     * 根据用户的sid获取用户存储的本地路径,这里只是相对路径，如:2016/09/30/45754527857.png
     * @param sid
     * @param ext 文件的后缀名，不带.
     * @return
     */
    public static String generateAvatarFilename(String sid, String ext) {
    	String dateDir = avatarFormat.format(new Date());
    	String suffix = ext == null ? "" : "." + ext;
    	return dateDir + "/" + sid + suffix;
    }
    
    /**
     * MD5 文件
     * @param file
     * @return
     */
    public static String md5FileHex(File file) {
    	FileInputStream fis = null;
    	String md5 = null;
		try {
			fis = new FileInputStream(file);    
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fis);  
		}
		return md5;
    }
    
    private static JsonFactory factory;
	private static ObjectMapper mapper;
	
	static {
		getFactory();
		getMapper();
	}
	
	public static JsonFactory getFactory() {
		if(factory == null) {
			factory = new JsonFactory();
		}
		return factory;
	}
	
	public static ObjectMapper getMapper() {
		if(mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	public static <T> String obj2json(T obj) {
		StringWriter out = new StringWriter();
		JsonGenerator generator = null;
		try {
			generator = factory.createGenerator(out);
			mapper.writeValue(generator, obj);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(generator != null) {
				try {
					generator.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static <T> T json2obj(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		T t = mapper.readValue(json, clazz);
		return t;
	}

}
