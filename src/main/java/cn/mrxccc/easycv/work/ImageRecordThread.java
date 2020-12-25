package cn.mrxccc.easycv.work;

import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.mapper.ImgRecordTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
@Slf4j
public class ImageRecordThread implements Runnable {

    @Autowired
    ImgRecordTaskMapper imgRecordTaskMapper;

    private Integer taskId;
    /**
     * 开始状态
     */
    public static final int START_STATUS = 1;
    /**
     * 停止状态
     */
    public static final int STOP_STATUS = 2;
    /**
     * 暂停状态
     */
    public static final int PAUSE_STATUS = 1;

    protected FFmpegFrameGrabber grabber = null;
    protected FFmpegFrameRecorder record = null;

    /**
     * 运行状态，0-初始状态，1-运行，2-停止
     */
    protected volatile int status = 0;
    protected volatile int pause = 0;//是否暂停，1-暂停
    protected int err_stop_num = 3;//默认错误数量达到三次终止录制

    /**
     * @param grabber      -抓取器
     * @param record       -录制器
     * @param err_stop_num 允许的错误次数，超过该次数后即停止任务
     */
    public ImageRecordThread(FFmpegFrameGrabber grabber, FFmpegFrameRecorder record, Integer err_stop_num, Integer taskId) {
        this.grabber = grabber;
        this.record = record;
        this.taskId = taskId;
        if (err_stop_num != null) {
            this.err_stop_num = err_stop_num;
        }
    }

    /**
     * 运行过一次后必须进行重置参数和运行状态
     */
    public void reset(FFmpegFrameGrabber grabber, FFmpegFrameRecorder record) {
        this.grabber = grabber;
        this.record = record;

    }

    public int getErr_stop_num() {
        return err_stop_num;
    }

    public void setErr_stop_num(int err_stop_num) {
        this.err_stop_num = err_stop_num;
    }

    public FFmpegFrameGrabber getGrabber() {
        return grabber;
    }

    public void setGrabber(FFmpegFrameGrabber grabber) {
        this.grabber = grabber;
    }

    public FFmpegFrameRecorder getRecord() {
        return record;
    }

    public void setRecord(FFmpegFrameRecorder record) {
        this.record = record;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public void run() {
        go();
        for (; ; ) {
            if (status == 2) {
                log.debug("工作线程进入等待状态");
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
                log.debug("工作线程唤醒");
                continue;
            }
            //核心任务循环
            mainLoop();
        }
    }

    /**
     * 核心转换处理循环
     */
    private void mainLoop() {
        long startime = System.currentTimeMillis();
        //采集或推流失败次数
        long err_index = 0;
        long frame_index = 0;
        //暂停次数
        int pause_num = 0;
        try {
            // 由于图片只有一帧，重复调用grab自然会抛出异常，所以只调用一次
            Frame pkt = grabber.grabImage();
            for (; status == START_STATUS; frame_index++) {
                //暂停状态
                if (pause == 1) {
                    pause_num++;
                    continue;
                }
                //采集空包结束
                if (pkt == null) {
                    //超过三次则终止录制
                    if (err_index > err_stop_num) {
                        log.info(taskId + "工作线程采集视频帧连续" + err_index + "次空包，本次任务终止");
                        ImgRecordTask imgRecordTask = new ImgRecordTask();
                        imgRecordTask.setId(taskId);
                        imgRecordTask.setStatus(-1);
                        imgRecordTask.setUpdateTime(LocalDateTime.now());
                        imgRecordTask.setEndTime(LocalDateTime.now());
                        imgRecordTaskMapper.updateByPrimaryKeySelective(imgRecordTask);
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
            status = STOP_STATUS;
            log.error("异常导致停止录像，详情：" + e.getMessage());
        } finally {
            status = STOP_STATUS;
            stopRecord();
            log.info(taskId + "工作线程的录像任务已结束，持续时长：" + (System.currentTimeMillis() - startime) / 1000 + "秒，共录制：" + frame_index + "帧，遇到的错误数：" + err_index + ",录制期间共暂停次数：" + pause_num);
        }
    }

    /**
     * 停止录制
     */
    private void stopRecord() {
        try {
            if (grabber != null) {
                grabber.close();
            }
            if (record != null) {
                record.stop();
            }
        } catch (IOException e) {
            log.error("停止录制失败");
            log.error(e.getMessage(),e);
        }

    }

    /**
     * 暂停
     */
    public void pause() {
        pause = PAUSE_STATUS;
    }

    /**
     * 继续（从暂停中恢复）
     */
    public void carryon() {
        //如果暂停状态为1才允许
        if (pause == PAUSE_STATUS) {
            pause = 0;
            status = START_STATUS;
        }
    }

    /**
     * 结束
     */
    public void over() {
        status = STOP_STATUS;
    }

    /**
     * 开始（如果线程处于等待状态则唤醒）
     */
    public void go() {
        //如果初始状态，则设置为开始状态1
        if (status == 0) {
            status = START_STATUS;
            log.info("开启工作线程");
        }
        //如果是停止状态，设置为开始状态1，并唤醒线程
        if (status == STOP_STATUS) {
            this.status = START_STATUS;
            synchronized (this) {
                notify();
            }
            log.info("唤醒工作线程");
        }
    }
}
