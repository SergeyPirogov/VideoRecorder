package com.automation.remarks.video.recorder.custom;

import com.automation.remarks.video.recorder.VideoRecorder;

import java.io.File;

public class CustomRecorder extends VideoRecorder {
    private VideoRecorder recorder;

    public CustomRecorder() {
        try {
            Class<?> clazz = Class.forName(conf().recorderClass());
            recorder = (VideoRecorder) clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        recorder.start();
    }

    @Override
    public File stopAndSave(String filename) {
        return recorder.stopAndSave(filename);
    }
}
