package com.yunxinlink.notes.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yunxinlink.notes.api.dao.FeedbackDao;
import com.yunxinlink.notes.api.model.FeedbackAttach;
import com.yunxinlink.notes.api.model.FeedbackInfo;
import com.yunxinlink.notes.api.service.IFeedbackService;

/**
 * 反馈的服务层
 * @author huanghui-iri
 * @date 2016年11月16日 下午2:05:44
 */
@Service
public class FeedbackService implements IFeedbackService {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

	@Autowired
	private FeedbackDao feedbackDao;
	
	@Override
	public boolean addFeedback(FeedbackInfo feedbackInfo) {
		int rowCount = 0;
		rowCount = feedbackDao.add(feedbackInfo);
		List<FeedbackAttach> attachs = feedbackInfo.getAttachs();
		if (rowCount > 0 && !CollectionUtils.isEmpty(attachs)) {
			//有附件，则添加附件
			if (attachs.size() == 1) {	//只有一个附件
				FeedbackAttach attach = attachs.get(0);
				attach.setFeedbackId(feedbackInfo.getId());
				rowCount = feedbackDao.addAttach(attach);
			} else {	//添加多个附件
				for (FeedbackAttach attach : attachs) {
					attach.setFeedbackId(feedbackInfo.getId());
				}
				rowCount = feedbackDao.addAttachBatch(attachs);
			}
		}
		return rowCount > 0;
	}

	@Override
	public boolean deleteFeedback(FeedbackInfo feedbackInfo) {
		int rowCount = 0;
		try {
			rowCount = feedbackDao.delete(feedbackInfo);
			//删除附件记录由触发器完成
		} catch (Exception e) {
			logger.error("delete feedback info error:" + e.getMessage());
		}
		return rowCount > 0;
	}
}
