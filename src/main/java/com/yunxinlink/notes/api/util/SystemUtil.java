package com.yunxinlink.notes.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

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
import com.yunxinlink.notes.api.model.Attach;

public class SystemUtil {
	private final static Logger logger = LoggerFactory.getLogger(SystemUtil.class);
	
	/**
     * 文件保存路根目录
     */
    public static String HOME_FILEPATH = new File(SystemUtils.getUserHome(), "Yunxin" + File.separator + "Notes" + File.separator + "Files").getAbsolutePath();
    
    /**
     * 头像文件夹的时间格式化，格式为:yyyy/MM/dd
     */
    private static SimpleDateFormat attachFormat = new SimpleDateFormat("yyyy/MM/dd");
    
    //邮箱的正则表达式
    private static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
	
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
    	return generateAttachFilePath(AttachUsage.AVATAR, generateAttachFilename(sid, ext));
    }
    
    /**
     * 根据用户的sid获取用户存储的本地路径,这里只是相对路径，如:icon/2016/09/30/45754527857.png
     * @param usage 附件的用途，主要是用户头像和笔记的附件
     * @param filename 文件的后缀名，不带.
     * @see AttachUsage
     * @return
     */
    public static String generateAttachFilePath(AttachUsage usage, String filename) {
    	if (usage == null) {
			return null;
		}
    	String dir = "";
    	switch (usage) {
		case AVATAR:	//头像
			dir = Constant.AVATAR_ROOT + File.separator;
			break;
		case ATTACH:
			dir = Constant.ATTACH_ROOT + File.separator;
			break;
		case FEEDBACK_ATTACH:	//意见反馈的附件
			dir = Constant.FEEDBACK_ROOT + File.separator;
			break;
		default:
			break;
		}
    	return dir + filename;
    }
    
    /**
     * 根据用户的sid获取用户存储的本地路径,这里只是相对路径，如:2016/09/30/45754527857.png
     * @param sid
     * @param ext 文件的后缀名，不带.
     * @return
     */
    public static String generateAttachFilename(String sid, String ext) {
    	String dateDir = attachFormat.format(new Date());
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
	
	/**
	 * 根据文件的全路径获取mime类型
	 * @param filePath
	 * @return
	 */
	public static String getMime(String filePath) {
		String mime = null;
		Path source = Paths.get(filePath);
		try {
			mime = Files.probeContentType(source);
			if (mime == null) {
				mime = new MimetypesFileTypeMap().getContentType(filePath);
			}
		} catch (IOException e) {
			logger.error("get mime error:" + e.getMessage());
		}
		return mime;
	}
	
	/**
	 * 获取附件的类型
	 * @param mimeType
	 * @return
	 */
	public static int getAttachType(String mimeType) {
		if (mimeType == null) {
			return 0;
		}
		int type = 0;
		if (mimeType.startsWith("image/")) {
			type = Attach.IMAGE;
		} else if (mimeType.startsWith("audio/")) {
			type = Attach.VOICE;
		} else if (mimeType.startsWith("video/")) {
			type = Attach.VIDEO;
		} else if (mimeType.startsWith("application/x-gzip") || mimeType.startsWith("application/x-tar")) {
			type = Attach.ARCHIVE;
		} else {
			type = Attach.FILE;
		}
		return type;
	}

	/**
	 * <pre>
	 * 合法E-mail地址：     
	1. 必须包含一个并且只有一个符号“@”     
	2. 第一个字符不得是“@”或者“.”     
	3. 不允许出现“@.”或者.@     
	4. 结尾不得是字符“@”或者“.”     
	5. 允许“@”前的字符中出现“＋”     
	6. 不允许“＋”在最前面，或者“＋@”     
	    
	正则表达式如下：     
	-----------------------------------------------------------------------     
	^(\w+((-\w+)|(\.\w+))*)\+\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$     
	-----------------------------------------------------------------------     
	    
	字符描述：     
	^ ：匹配输入的开始位置。     
	\：将下一个字符标记为特殊字符或字面值。     
	* ：匹配前一个字符零次或几次。     
	+ ：匹配前一个字符一次或多次。     
	(pattern) 与模式匹配并记住匹配。     
	x|y：匹配 x 或 y。     
	[a-z] ：表示某个范围内的字符。与指定区间内的任何字符匹配。     
	\w ：与任何单词字符匹配，包括下划线。     
	$ ：匹配输入的结尾。 
		</pre>
	 * @param addess
	 * @return
	 */
	public static boolean isEmailAddress(String addess) {
		Matcher matcher = emailPattern.matcher(addess);
		return matcher.matches();
	}
	
}
