package com.automation.remarks.junit5;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Optional;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;
import static com.automation.remarks.video.RecordingUtils.videoEnabled;

/**
 * Created by sergey on 12.02.17.
 */
public class VideoExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

  private IVideoRecorder recorder;

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    if (videoDisabled(context.getTestMethod().get())) {
      return;
    }
    recorder = RecorderFactory.getRecorder(VideoRecorder.conf().recorderType());
    recorder.start();
  }

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
    if (videoDisabled(context.getTestMethod().get())) {
      return;
    }

    String fileName = getFileName(context.getTestMethod().get());
    File video = stopRecording(fileName);
    if (context.getExecutionException().isPresent()) {
      doVideoProcessing(false, video);
    } else {
      doVideoProcessing(true, video);
    }
  }

  private boolean videoDisabled(Method testMethod) {
    Optional<com.automation.remarks.video.annotations.Video> video = AnnotationUtils.findAnnotation(testMethod, com.automation.remarks.video.annotations.Video.class);

    return !videoEnabled(video.orElse(null));
  }

  private String getFileName(Method testMethod) {
    String methodName = testMethod.getName();
    Video video = testMethod.getAnnotation(Video.class);
    return getVideoFileName(video, methodName);
  }

  private static String getVideoFileName(Video annotation, String methodName) {
    if (annotation == null) {
      return methodName;
    }
    String name = annotation.name();
    return name.length() > 1 ? name : methodName;
  }

  private File stopRecording(String filename) {
    if (recorder != null) {
      return recorder.stopAndSave(filename);
    }
    return null;
  }
}
