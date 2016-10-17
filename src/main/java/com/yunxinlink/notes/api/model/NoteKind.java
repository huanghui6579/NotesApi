package com.yunxinlink.notes.api.model;

/**
 * 笔记的类型，主要分为文本笔记和清单笔记
 * 
 * @author tiger
 * @update 2016/3/7 21:44
 * @version 1.0.0
 */
public interface NoteKind {
	/**
	 * 文本笔记:0
	 */
	int TEXT = 0;
	
	/**
	 * 清单笔记:1
	 */
	int DETAILED_LIST = 1;
}