package com.zlw.main.recorderlib.recorder;

/**
 * 表示当前状态
 */
public enum RecordState {
    /**
     * 空闲状态
     */
    IDLE,
    /**
     * 录音中
     */
    RECORDING,
    /**
     * 暂停中
     */
    PAUSE,
    /**
     * 正在停止
     */
    STOP,
    /**
     * 录音流程结束（转换结束）
     */
    FINISH
}
