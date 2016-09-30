package com.yunxinlink.notes.api.model;

/**
 * 同步的状态
 * @author huanghui1
 *
 */
public interface SyncState {
	/**
     * 需要向上同步
     */
    public static int SYNC_UP = 0;

    /**
     * 需要向下同步
     */
    public static int SYNC_DOWN = 1;

    /**
     * 同步完毕
     */
    public static int SYNC_DONE = 2;
}
