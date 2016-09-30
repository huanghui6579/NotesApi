package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class BaseController {
	protected static Logger logger = null;
	
	public BaseController() {
		logger = Logger.getLogger(getClass());
	}
	
	/**
	 * 保存文件到本地磁盘
	 * @param file
	 * @param saveFile 保存文件的路径
	 * @return true:保存到本地磁盘成功，false:保存到本地磁盘失败
	 */
	protected boolean saveFile(MultipartFile file, File saveFile) {
		if (saveFile != null && saveFile.exists()) {
			saveFile.delete();
		}
		try {
			file.transferTo(saveFile);
			return true;
		} catch (IllegalStateException | IOException e) {
			logger.error("save file error:" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
