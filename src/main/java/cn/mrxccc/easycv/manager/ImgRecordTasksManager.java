package cn.mrxccc.easycv.manager;

import cn.mrxccc.easycv.domain.TaskStatusEnum;
import cn.mrxccc.easycv.mapper.ImgRecordTaskMapper;
import cn.mrxccc.easycv.recorder.ImageRecord;
import cn.mrxccc.easycv.serivce.ImgRecordTaskService;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FrameRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认任务管理（内置对象池管理）
 *
 * @author mrxccc
 * @create 2020/12/16
 */
@Component("imgRecordTasksManager")
@Slf4j
public class ImgRecordTasksManager implements TasksManager {

    @Autowired
    ImgRecordTaskMapper imgRecordTaskMapper;

    @Autowired
    ImgRecordTaskService imgRecordTaskService;

    @Resource(name = "asyncTaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;


    ConcurrentHashMap<Integer, ImageRecord> taskMap = new ConcurrentHashMap<>();

    @Override
    public synchronized ImageRecord createRecorder(String imgPath, String rtspPalyPath, Integer taskId) throws Exception {
        log.debug("创建时，当前池数量：" + taskExecutor.getPoolSize() + ",空闲数量：" + (taskExecutor.getPoolSize() - taskExecutor.getActiveCount()) + ",工作数量：" + taskExecutor.getActiveCount());
        ImageRecord recorder = new ImageRecord(imgPath, rtspPalyPath, avutil.AV_PIX_FMT_YUV420P);
        recorder.setImgRecordTaskService(imgRecordTaskService);
        recorder.from(imgPath).to(rtspPalyPath);
        recorder.setTaskId(taskId);
        return recorder;
    }


    @Override
    public boolean start(ImageRecord task) {
        if (task != null) {
            task.setStartTime(LocalDateTime.now());// 设置开始时间
            task.setStatus(TaskStatusEnum.WORKING.getCode());// 状态设为开始
            taskExecutor.execute(task);
            taskMap.put(task.getTaskId(), task);
            return true;
        }
        return false;
    }

    @Override
    public boolean pause(ImageRecord task) {
        return false;
    }

    @Override
    public boolean carryon(ImageRecord task) {
        return false;
    }

    @Override
    public boolean stop(ImageRecord task) {
        if (task != null) {
            try {
                task.setStatus(TaskStatusEnum.STOP.getCode());
                task.getRecord().stop();// 停止录制
                taskMap.remove(task.getTaskId());
                return true;
            } catch (FrameRecorder.Exception e) {
                log.info("停止任务{}失败", task.getTaskId());
                return false;
            }
        }
        return false;
    }

    @Override
    public List<ImageRecord> list() {
        Set<Map.Entry<Integer, ImageRecord>> entrySet = taskMap.entrySet();
        List<ImageRecord> list = Arrays.asList();
        entrySet.forEach(e -> {
            list.add(e.getValue());
        });
        return list;
    }

    @Override
    public ImageRecord getRecorderTask(Integer id) {
        Set<Map.Entry<Integer, ImageRecord>> entrySet = taskMap.entrySet();
        Iterator<Map.Entry<Integer, ImageRecord>> iterator = entrySet.iterator();
        for (; iterator.hasNext(); ) {
            ImageRecord imageRecord = iterator.next().getValue();
            if (imageRecord.getTaskId().equals(id)) {
                return imageRecord;
            }
        }
        return null;
    }

    @Override
    public boolean exist(String src, String out) {
        return false;
    }

}
