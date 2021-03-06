package com.yunxinlink.notes.api.util;

/**
 * 附件的用途，主要是用户头像和笔记的附件
 * @author huanghui1
 * @date 2016年10月11日 上午9:45:17
 */
public enum AttachUsage {
	/**
	 * 用户头像
	 */
	AVATAR,
	/**
	 * 笔记的附件
	 */
	ATTACH,
	
	/**
	 * 意见反馈的附件
	 */
	FEEDBACK_ATTACH,
	
	/**
	 * 软件包
	 */
	SOFT_ATTACH,
	
	/**
	 * 上报的bug日志
	 */
	BUG_REPORT;
}
