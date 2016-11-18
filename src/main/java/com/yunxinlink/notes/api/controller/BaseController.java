package com.yunxinlink.notes.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.yunxinlink.notes.api.init.SystemCache;
import com.yunxinlink.notes.api.model.Attach;
import com.yunxinlink.notes.api.util.AttachUsage;
import com.yunxinlink.notes.api.util.SystemUtil;

public abstract class BaseController {
	protected Logger logger = null;
	
	public BaseController() {
		logger = LoggerFactory.getLogger(getClass());
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
	
	/**
	 * 获取保存在本地磁盘的文件路径
	 * @param avatarFilename
	 * @param usage 文件的类型
	 * @return
	 */
	protected File getAttachSaveFile(String avatarFilename, AttachUsage usage) {
		return getAttachFile(avatarFilename, usage, true);
	}
	
	/**
	 * 根据文件名获取本地的文件全路径
	 * @param avatarFilename
	 * @param usage
	 * @param autoCreate
	 * @return
	 */
	protected File getAttachFile(String avatarFilename, AttachUsage usage, boolean autoCreate) {
		String rootDir = SystemCache.getUploadPath();
		File file = new File(rootDir, SystemUtil.generateAttachFilePath(usage, avatarFilename));
		if (autoCreate) {
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
		}
		logger.info("base controller get attach " + usage + " file:" + file);
		return file;
	}
	
	/**
	 * 将文件解析成下载的流
	 * @param file
	 * @param attach　可选参数
	 * @param resource
	 * @param headers
	 * @return
	 */
	protected InputStreamResource parseFile(File file, Attach attach, HttpHeaders headers) {
		InputStreamResource inputStreamResource = null;
		if (file != null && file.exists()) {	//文件存在
			String filename = null;
			String mime = null;
			String filePath = file.getAbsolutePath();
			if (attach != null) {
				filename = attach.getFilename();
				mime = attach.getMimeType();
			} else {
				filename = file.getName();
				mime = SystemUtil.getMime(filePath);
			}
			
			String encodeFilename = null;
			try {
				encodeFilename = URLEncoder.encode(filename, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				encodeFilename = filename;
			}
			MediaType mediaType = null;
	        try {
	            mediaType = MediaType.parseMediaType(mime);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			headers.setContentType(mediaType);
	        headers.setContentLength(file.length());
	        StringBuilder sb = new StringBuilder();
	        sb.append("attachment;filename=").append(encodeFilename).append(";filename*=UTF-8''").append(encodeFilename);
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, sb.toString());
	        try {
				inputStreamResource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("get app file but fil eis not exists:" + file);
		}
		return inputStreamResource;
	}
}
