package com.automation.remarks.video;

import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.custom.CustomRecorder;
import com.automation.remarks.video.recorder.ffmpeg.LinuxFFmpegRecorder;
import com.automation.remarks.video.recorder.ffmpeg.MacFFmpegRecorder;
import com.automation.remarks.video.recorder.ffmpeg.WindowsFFmpegRecorder;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.apache.commons.lang3.SystemUtils;

/**
 * Created by sepi on 19.07.16.
 */
public class RecorderFactory {

    public static IVideoRecorder getRecorder(RecorderType recorderType) {
        switch (recorderType) {
            case CUSTOM:
                return new CustomRecorder();
            case FFMPEG:
                if (SystemUtils.IS_OS_WINDOWS) {
                    return new WindowsFFmpegRecorder();
                } else if (SystemUtils.IS_OS_MAC) {
                    return new MacFFmpegRecorder();
                }
                return new LinuxFFmpegRecorder();
            case MONTE:
            default:
                return new MonteRecorder();
        }
    }
}
