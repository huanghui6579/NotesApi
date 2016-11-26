package com.yunxinlink.notes.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunxinlink.notes.api.model.FeedbackAttach;
import com.yunxinlink.notes.api.model.FeedbackInfo;

/**
 * 用户反馈的dao
 * @author huanghui-iri
 * @date 2016年11月16日 上午11:01:41
 */
public interface FeedbackDao extends BaseDao<FeedbackInfo> {
	/**
	 * 添加一个附件
	 * @param attach
	 * @return
	 */
	public int addAttach(FeedbackAttach attach);
	
	/**
	 * 添加一组附件
	 * @param list
	 * @return
	 */
	public int addAttachBatch(@Param("list") List<FeedbackAttach> list);
	
	/**
	 * 删除一个附件
	 * @param attach
	 * @return
	 */
	public int deleteAttach(FeedbackAttach attach);
}
