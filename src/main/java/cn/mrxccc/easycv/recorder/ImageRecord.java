package cn.mrxccc.easycv.recorder;

import cn.mrxccc.easycv.serivce.ImgRecordTaskService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_ERROR;
import static org.bytedeco.ffmpeg.global.avutil.av_log_set_level;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
@Slf4j
@Data
public class ImageRecord extends JavaCVRecord implements Recorder,Runnable {

    ImgRecordTaskService imgRecordTaskService;

    Integer taskId;

    Integer err_stop_num;

    /**
     * 任务创建时间
     */
    protected LocalDateTime startTime;

    /**
     * 任务结束时间
     */
    protected LocalDateTime endTime;

    /**
     * 运行状态，0-初始状态，1-运行，-1-停止
     */
    protected volatile int status = 0;

    protected volatile int pause = 0;//是否暂停，1-暂停

    /**
     * 开始状态
     */
    public static final int START_STATUS = 1;
    /**
     * 停止状态
     */
    public static final int STOP_STATUS = -1;

    public ImgRecordTaskService getImgRecordTaskService() {
        return imgRecordTaskService;
    }

    public void setImgRecordTaskService(ImgRecordTaskService imgRecordTaskService) {
        this.imgRecordTaskService = imgRecordTaskService;
    }

    public ImageRecord(){}

    public ImageRecord(String src, String out, int videoCodec) {
        this.src = src;
        this.out = out;
        this.videoCodec = videoCodec;
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
        grabber.setFrameRate(30);
        grabber.setVideoBitrate(3000000);
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
        record = new FFmpegFrameRecorder(out, grabber.getImageWidth(), grabber.getImageHeight(), 0);
        record.setOption("rtsp_transport", "tcp");
        //编码格式
        record.setVideoCodecName("libx264");
        record.setFrameRate(30);
        record.setVideoBitrate(3000000);
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
    public Recorder videoParam(Integer width, Integer height, Double framerate, int bitrate) {
        return null;
    }

    @Override
    public void start() {
        status = 1;
    }

    /**
     * 核心转换处理循环
     */
    private void mainLoop() {
        long startime = System.currentTimeMillis();
        //采集或推流失败次数
        long err_index = 0;
        try {
            // 由于图片只有一帧，重复调用grab自然会抛出异常，所以只调用一次
            Frame pkt = grabber.grabImage();
            for (; status == START_STATUS ;) {
                //采集空包结束
                if (pkt == null) {
                    //超过三次则终止录制
                    if (err_index > err_stop_num) {
                        log.info(taskId + "工作线程采集视频帧连续" + err_index + "次空包，本次任务终止");
                        // 更新状态
                        imgRecordTaskService.updateStatusByTaskId(taskId, -1);
                        break;
                    }
                    err_index++;
                    continue;
                }
                record.record(pkt);
            }
        }
        //推流失败
        catch (Exception e) {
            //到这里表示已经停止了
            log.error("异常导致停止录像，详情：" + e.getMessage());
            imgRecordTaskService.updateStatusByTaskId(taskId, -1);
        } finally {
            log.info(taskId + "工作线程的录像任务已结束，持续时长：" + (System.currentTimeMillis() - startime) / 1000 + "遇到的错误数：" + err_index + ",录制期间共暂停次数：");
        }
    }

    /**
     * 暂停
     *
     * @return
     */
    @Override
    public Recorder pause() {
        status = 2;
        return this;
    }

    /**
     * 从暂停中恢复
     *
     * @return
     */
    @Override
    public Recorder carryon() {
        return this;
    }

    /**
     * 停止录制线程和录制器
     *
     * @return
     */
    @Override
    public ImageRecord stop() {
        status = -1;
        return this;
    }

    @Override
    public boolean alive() {
        /**用于空闲定时器定期执行*/
        return true;
    }

    @Override
    public int status() {
        return status;
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

    @Override
    public void run() {
        mainLoop();
    }
}
