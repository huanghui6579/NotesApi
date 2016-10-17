package com.yunxinlink.notes.api.model;

/**
 * 删除的状态，主要是没删除0，到回收站1，真删除2
 * @author huanghui1
 * @update 2016/3/8 9:55
 * @version: 0.0.1
 */
public interface DeleteState {
    /**
     * 没有删除
     */
    int DELETE_NONE = 0;

    /**
     * 删除到垃圾桶
     */
    int DELETE_TRASH = 1;

    /**
     * 隐藏
     */
    int DELETE_HIDE = 2;

    /**
     * 完全删除
     */
    int DELETE_DONE = 3;
}
