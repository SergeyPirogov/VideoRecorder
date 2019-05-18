package com.automation.remarks.video.recorder;

import com.automation.remarks.video.SystemUtils;
import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.enums.VideoSaveMode;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

import java.awt.*;
import java.io.File;

/**
 * Created by sergey on 4/13/16.
 */
@LoadPolicy(LoadType.MERGE)
@Sources({ "classpath:video.properties",
           "classpath:ffmpeg-${os.type}.properties" })
public interface VideoConfiguration extends Config {

  @Key("video.folder")
  default String folder() {
    final String defaultFolder = System.getProperty("user.dir") + File.separator + "video";
    return System.getProperty("video.folder", defaultFolder);
  }

  @Key("video.enabled")
  @DefaultValue("true")
  Boolean videoEnabled();

  @Key("video.mode")
  @DefaultValue("ANNOTATED")
  RecordingMode mode();

  @DefaultValue("http://localhost:4444")
  @Key("remote.video.hub")
  String remoteUrl();

  @DefaultValue("false")
  @Key("video.remote")
  Boolean isRemote();

  @Key("video.name")
  String fileName();

  @Key("recorder.type")
  @DefaultValue("MONTE")
  RecorderType recorderType();

  @Key("recorder.class")
  String recorderClass();

  @Key("video.save.mode")
  @DefaultValue("FAILED_ONLY")
  VideoSaveMode saveMode();

  @DefaultValue("24")
  @Key("video.frame.rate")
  int frameRate();

  @Key("video.screen.size")
  default Dimension screenSize() {
    return SystemUtils.getSystemScreenDimension();
  }

  @Key("ffmpeg.format")
  String ffmpegFormat();

  @Key("ffmpeg.display")
  String ffmpegDisplay();

  @DefaultValue("yuv420p")
  @Key("ffmpeg.pixelFormat")
  String ffmpegPixelFormat();
}
