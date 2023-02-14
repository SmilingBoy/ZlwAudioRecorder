package com.zlw.main.recorderlib;


import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.RecordHelperPlus;
import com.zlw.main.recorderlib.recorder.RecordState;
import com.zlw.main.recorderlib.recorder.listener.RecordDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;
import com.zlw.main.recorderlib.utils.FileUtils;
import com.zlw.main.recorderlib.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author zhaolewei on 2018/7/10.
 * 非单利方法
 */
public class RecordManagerPlus {
    private static final String TAG = RecordManagerPlus.class.getSimpleName();

    private RecordHelperPlus mRecordHelper;
    /**
     * 录音配置
     */
    private static RecordConfig currentConfig = new RecordConfig();


    public RecordManagerPlus() {
        this(false);
    }

    public RecordManagerPlus(boolean showLog) {
        Logger.IsDebug = showLog;
        mRecordHelper = new RecordHelperPlus();
    }

    public void start() {
        Logger.i(TAG, "start...");
        mRecordHelper.start(getFilePath(), currentConfig);
    }

    public void stop() {
        Logger.i(TAG, "stop...");
        mRecordHelper.stop();
    }

    public void resume() {
        mRecordHelper.resume();
    }

    public void pause() {
        mRecordHelper.pause();
    }

    /**
     * 录音状态监听回调
     */
    public void setRecordStateListener(RecordStateListener listener) {
        mRecordHelper.setRecordStateListener(listener);
    }

    /**
     * 录音数据监听回调
     */
    public void setRecordDataListener(RecordDataListener listener) {
        mRecordHelper.setRecordDataListener(listener);
    }

    /**
     * 录音可视化数据回调，傅里叶转换后的频域数据
     */
    public void setRecordFftDataListener(RecordFftDataListener recordFftDataListener) {
        mRecordHelper.setRecordFftDataListener(recordFftDataListener);
    }

    /**
     * 录音文件转换结束回调
     */
    public void setRecordResultListener(RecordResultListener listener) {
        mRecordHelper.setRecordResultListener(listener);
    }

    /**
     * 录音音量监听回调
     */
    public void setRecordSoundSizeListener(RecordSoundSizeListener listener) {
        mRecordHelper.setRecordSoundSizeListener(listener);
    }


    public boolean changeFormat(RecordConfig.RecordFormat recordFormat) {
        if (getState() == RecordState.IDLE) {
            currentConfig.setFormat(recordFormat);
            return true;
        }
        return false;
    }


    public boolean changeRecordConfig(RecordConfig recordConfig) {
        if (getState() == RecordState.IDLE) {
            currentConfig = recordConfig;
            return true;
        }
        return false;
    }

    public RecordConfig getRecordConfig() {
        return currentConfig;
    }

    /**
     * 修改录音文件存放路径
     */
    public void changeRecordDir(String recordDir) {
        currentConfig.setRecordDir(recordDir);
    }

    /**
     * 获取当前的录音状态
     *
     * @return 状态
     */
    public RecordState getState() {
        return mRecordHelper.getState();
    }


    private static String getFilePath() {

        String fileDir =
                currentConfig.getRecordDir();
        if (!FileUtils.createOrExistsDir(fileDir)) {
            Logger.w(TAG, "文件夹创建失败：%s", fileDir);
            return null;
        }
        String fileName = String.format(Locale.getDefault(), "record_%s", FileUtils.getNowString(new SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.SIMPLIFIED_CHINESE)));
        return String.format(Locale.getDefault(), "%s%s%s", fileDir, fileName, currentConfig.getFormat().getExtension());
    }


}
