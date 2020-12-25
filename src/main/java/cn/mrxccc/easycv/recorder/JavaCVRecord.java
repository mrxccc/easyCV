package cn.mrxccc.easycv.recorder;

import java.io.IOException;

import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;
import static org.bytedeco.ffmpeg.global.avcodec.*;
import static org.bytedeco.ffmpeg.global.avutil.*;

import cn.mrxccc.easycv.work.RecordThread;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
public abstract class JavaCVRecord{
    FFmpegFrameGrabber grabber;
    FFmpegFrameRecorder record;
    String src;
    String out;
    // 视频参数
    // 转换视频宽度，不设置不会改变视频源宽度，设置后会进行转换
    int width = -1;
    // 转换视频高度，不设置不会改变视频源高度，设置后会进行转换
    int height = -1;
    // 视频编码，使用avcodec中的编码常量，例如：avcodec.AV_CODEC_ID_JPEG2000
    int videoCodec;
    // 帧率
    double framerate;
    // 比特率
    int bitrate;
    // 缩放
    int imageScalingFlags;
    // 像素格式
    int pixelFormat;

    // 音频参数
    // 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
    int audioChannels;
    int audioBitrate;
    int sampleRate;

    public FFmpegFrameGrabber getGrabber() {
        return grabber;
    }

    public FFmpegFrameRecorder getRecord() {
        return record;
    }

}
