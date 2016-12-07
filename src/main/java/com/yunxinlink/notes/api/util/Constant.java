package com.yunxinlink.notes.api.util;

/**
 * 系统的一些常亮
 * @author huanghui1
 *
 */
public class Constant {
	/**
	 * 头像的根文件夹名称
	 */
	public static final String AVATAR_ROOT = "icon";
	
	/**
	 * 笔记附件的根目录
	 */
	public static final String ATTACH_ROOT = "att";
	
	/**
	 * 意见反馈的文件目录
	 */
	public static final String FEEDBACK_ROOT = "feedback";
	
	/**
	 * 软件包的存放目录
	 */
	public static final String SOFT_PACKAGE_ROOT = "soft";
	
	/**
	 * 默认的每页记录条数，默认20条
	 */
	public static final int PAGE_SIZE_DEFAULT = 20;
	
	/**
	 * 分隔符:","
	 */
	public static final String TAG_COMMA = ",";
	
	/**
	 * 英文的分号：";"
	 */
	public static final String TAG_SEMICOLON = ";";
	
	/**
	 * 安全码的分隔符："$"
	 */
	public static final String TAG_KEY_SPLITER = "$";
	
	public static final String RESET_PWD_SUBJECT = "云信笔记密码重置请求";
	
	public static final String ANDROID = "Android";
	
	public static final String IOS = "IOS";
	
	/**
	 * 生成token的密钥
	 */
	public static final String TOKEN_KEY_SALT = "ncjhjdekjrkh!@#%$fdsfdj%$65152757_~fdskf!@#$$%35m=jfksj%$#^ldsifl";
	/**
	 * token 的签发者
	 */
	public static final String TOKEN_ISSUER = "notes.yunxinlink.com";
	/**
	 * token的有效期，7天=3600 x 1000 x 24 x 7,单位毫秒
	 */
	public static final long TOKEN_EXP_TIME = 604800000L;
	
	/**
	 * 默认ehcache名称
	 */
	public static final String DEFAULT_CACHE = "dbCache";
	/**
	 * 用户token的缓存
	 */
	public static final String DEFAULT_TOKEN_CACHE = "tokenCache";
	
	/**
	 * request中的token subject
	 */
	public static final String KEY_TOKEN_SUBJECT = "tokenSubject";
}
