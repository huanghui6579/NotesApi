package com.yunxinlink.notes.api.service;

import com.yunxinlink.notes.api.model.FeedbackInfo;

/**
 * 反馈的服务层
 * @author huanghui-iri
 * @date 2016年11月16日 下午2:05:14
 */
public interface IFeedbackService {
	/**
	 * 添加反馈记录
	 * @param feedbackInfo
	 * @return
	 */
	public boolean addFeedback(FeedbackInfo feedbackInfo);
	
	/**
	 * 删除反馈信息
	 * @param feedbackInfo
	 * @return
	 */
	public boolean deleteFeedback(FeedbackInfo feedbackInfo);
}
