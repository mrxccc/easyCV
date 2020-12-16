package cn.mrxccc.easycv.recorder;

import cn.mrxccc.easycv.work.RecordThread;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;

import java.io.IOException;

import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_AAC;
import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_ERROR;
import static org.bytedeco.ffmpeg.global.avutil.av_log_set_level;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
public class ImageRecord implements Recorder {
    private static int threadInitNumber;

    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }

    private final static String THREAD_NAME = "录像工作线程";

    /**
     * 当前线程
     */
    protected RecordThread cuThread;

    FFmpegFrameGrabber grabber = null;
    FFmpegFrameRecorder record = null;
    String src, out;
    // 转换视频宽度，不设置不会改变视频源宽度，设置后会进行转换
    int width = -1;
    // 转换视频高度，不设置不会改变视频源高度，设置后会进行转换
    int height = -1;

    // 视频编码，使用avcodec中的编码常量，例如：avcodec.AV_CODEC_ID_JPEG2000
    private int videoCodec;
    // 帧率
    private double framerate;
    // 比特率
    private int bitrate;
    // 缩放
    private int imageScalingFlags;
    // 像素格式
    private int pixelFormat;

    public RecordThread getCuThread() {
        return cuThread;
    }

    public void setCuThread(RecordThread cuThread) {
        this.cuThread = cuThread;
    }

    private ImageRecord() {
        super();
    }

    public ImageRecord(String src, String out, int width, int height, int videoCodec, double framerate, int bitrate, int imageScalingFlags, int pixelFormat) {
        this.src = src;
        this.out = out;
        this.width = width;
        this.height = height;
        this.videoCodec = videoCodec;
        this.framerate = framerate;
        this.bitrate = bitrate;
        this.imageScalingFlags = imageScalingFlags;
        this.pixelFormat = pixelFormat;
    }

    public String getSrc() {
        return src;
    }

    public ImageRecord setSrc(String src) {
        this.src = src;
        return this;
    }

    public String getOut() {
        return out;
    }

    public ImageRecord setOut(String out) {
        this.out = out;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ImageRecord setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ImageRecord setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getVideoCodec() {
        return videoCodec;
    }

    public ImageRecord setVideoCodec(int videoCodec) {
        this.videoCodec = videoCodec;
        return this;
    }

    public double getFramerate() {
        return framerate;
    }

    public ImageRecord setFramerate(double framerate) {
        this.framerate = framerate;
        return this;
    }

    public int getBitrate() {
        return bitrate;
    }

    public ImageRecord setBitrate(int bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public int getImageScalingFlags() {
        return imageScalingFlags;
    }

    public ImageRecord setImageScalingFlags(int imageScalingFlags) {
        this.imageScalingFlags = imageScalingFlags;
        return this;
    }

    public int getPixelFormat() {
        return pixelFormat;
    }

    public ImageRecord setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
        return this;
    }

    @Override
    public Recorder from(String src) throws FrameGrabber.Exception {
        av_log_set_level(AV_LOG_ERROR);
        if (src == null) {
            throw new FrameGrabber.Exception("源视频不能为空");
        }
        this.src = src;
        // 采集/抓取器
        grabber = new FFmpegFrameGrabber(src);
        if (hasRTSP(src)) {
            grabber.setOption("rtsp_transport", "tcp");
        }
        //如果不触发SwsContext像素格式转换，pixelFormat、width、height和imageScalingFlags参数不会起效果，
        grabber.setImageWidth(width);
        grabber.setImageHeight(height);
        grabber.setImageScalingFlags(imageScalingFlags);
        grabber.setPixelFormat(pixelFormat);//使用avutil中的像素格式常量，例如：avutil.AV_PIX_FMT_NONE
        grabber.setVideoCodec(videoCodec);//使用avcodec中的编码常量，例如：avcodec.AV_CODEC_ID_NONE

        grabber.setAudioCodecName(null);//如果为空，会触发自动检索音频编码
        /*设置下面三个参数会触发ffmpeg的swresample音频重采样*/
        //在对音频编码解码成pcm之后，如果sampleFormat与pcm不同，则会对音频采样格式进行转换
        grabber.setSampleFormat(avutil.AV_SAMPLE_FMT_NONE);//音频采样格式,使用avutil中的像素格式常量，例如：avutil.AV_SAMPLE_FMT_NONE
        grabber.setAudioChannels(0);
        grabber.setSampleRate(0);
        grabber.start();
        return this;
    }

    @Override
    public Recorder to(String out) throws IOException {
        if (out == null) {
            throw new FrameGrabber.Exception("输出视频不能为空");
        }
        this.out = out;
        // 录制/推流器
        record = new FFmpegFrameRecorder(out, width, height, 0);
        record.setOption("rtsp_transport", "tcp");
        record.setVideoCodecName(null);//优先级高于videoCodec
        record.setPixelFormat(pixelFormat);// 只有pixelFormat，width，height三个参数任意一个不为空才会进行像素格式转换
        record.setImageScalingFlags(imageScalingFlags);
        // 关键帧间隔
        record.setGopSize((int) (framerate * 2));
        record.setFrameRate(framerate);
        record.setVideoBitrate(-1);
        record.setVideoQuality(-1);
        record.setFormat("rtsp");
        AVFormatContext fc = null;
        record.setVideoCodec(avcodec.AV_CODEC_ID_NONE);
        //rtmp、rtsp和flv
//        if (hasRTMPFLV(out)) {
//            // 封装格式flv，并使用h264和aac编码
//            record.setFormat("flv");
//            if (hasRTMPFLV(src)) {
//                fc = grabber.getFormatContext();
//            }
//        } else if (hasMP4(out)) {//MP4
//            record.setFormat("mp4");
//        } else if (hasRTSP(out)) {
//            record.setFormat("rtsp");
//        }
        record.start();
        return this;
    }

    @Override
    public Recorder stream(String src, String out) throws IOException {
        return null;
    }

    @Override
    public Recorder audioParam(int audioChannels, int audioBitrate, int sampleRate) {
        return null;
    }

    @Override
    public Recorder videoParam(Integer width, Integer height, int framerate, int bitrate) {
        if (width != null) {
            this.width = width;
        }
        if (height != null) {
            this.height = height;
        }
        this.framerate = framerate;
        this.bitrate = bitrate;
        return this;
    }

    @Override
    public Recorder start() {
        if (cuThread == null) {
            String name = THREAD_NAME + nextThreadNum();
            cuThread = new RecordThread(name, grabber, record, 1);
            cuThread.setDaemon(false);
            cuThread.start();
        } else {
            cuThread.reset(grabber, record);// 重置
            cuThread.go();
        }

        return this;
    }

    /**
     * 重新开始，实际链式调用了：from(src).to(out).start()
     *
     * @return
     * @throws FrameGrabber.Exception
     * @throws IOException
     */
    public Recorder restart() throws FrameGrabber.Exception, IOException {
        return from(src).to(out).start();
    }

    /**
     * 暂停
     *
     * @return
     */
    @Override
    public Recorder pause() {
        if (cuThread != null && cuThread.isAlive()) {
            cuThread.pause();
        }
        return this;
    }

    /**
     * 从暂停中恢复
     *
     * @return
     */
    @Override
    public Recorder carryon() {
        if (cuThread != null && cuThread.isAlive()) {
            cuThread.carryon();
        }
        return this;
    }

    /**
     * 停止录制线程和录制器
     *
     * @return
     */
    @Override
    public ImageRecord stop() {
        if (cuThread != null && cuThread.isAlive()) {
            cuThread.over();// 先结束线程，然后终止录制
        }
        return this;
    }

    @Override
    public boolean alive() {
        /**用于空闲定时器定期执行*/
        return cuThread.isAlive();
    }

    @Override
    public int status() {
        /**定时器会根据此状态判断是否需要回收线程至空闲池*/
        return cuThread.getStatus();
    }

    /*
     * 是否包含rtmp或flv
     */
    private boolean hasRTMPFLV(String str) {
        return str.indexOf("rtmp") > -1 || str.indexOf("flv") > 0;
    }

    /*
     * 是否包含mp4
     */
    private boolean hasMP4(String str) {
        return str.indexOf("mp4") > 0;
    }

    /*
     * 是否包含rtsp
     */
    private boolean hasRTSP(String str) {
        return str.indexOf("rtsp") > -1;
    }
}
