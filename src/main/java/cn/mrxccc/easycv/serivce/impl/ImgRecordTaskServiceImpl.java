package cn.mrxccc.easycv.serivce.impl;

import cn.mrxccc.easycv.config.MyProperties;
import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import cn.mrxccc.easycv.entity.RecordTask;
import cn.mrxccc.easycv.manager.TasksManager;
import cn.mrxccc.easycv.mapper.ImgMapper;
import cn.mrxccc.easycv.recorder.ImageRecord;
import cn.mrxccc.easycv.serivce.EasyDarwin;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.mrxccc.easycv.mapper.ImgRecordTaskMapper;

import java.time.LocalDateTime;
import java.util.List;

import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.serivce.ImgRecordTaskService;
import org.springframework.transaction.annotation.Transactional;

@Service("imgRecordTaskService")
@Slf4j
public class ImgRecordTaskServiceImpl implements ImgRecordTaskService {

    @Resource
    private ImgRecordTaskMapper imgRecordTaskMapper;

    @Autowired
    ImgMapper imgMapper;

    @Autowired
    MyProperties myProperties;

    @Autowired
    EasyDarwin easyDarwin;

    @Resource(name = "imgRecordTasksManager")
    TasksManager tasksManager;

    @Resource(name = "asyncTaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public int updateBatch(List<ImgRecordTask> list) {
        return imgRecordTaskMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<ImgRecordTask> list) {
        return imgRecordTaskMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<ImgRecordTask> list) {
        return imgRecordTaskMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(ImgRecordTask record) {
        return imgRecordTaskMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(ImgRecordTask record) {
        return imgRecordTaskMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<ImgRecordTaskDto> selectImgRecordTask() {
        return imgRecordTaskMapper.selectImgRecordTask();
    }

    @Override
    public ImageRecord getImgRecordTask(Integer taskId) {
        return tasksManager.getRecorderTask(taskId);
    }

    @Override
    public boolean stopImgRecordTask(Integer taskId) {
        ImageRecord recorderTask = tasksManager.getRecorderTask(taskId);
        boolean isStop = tasksManager.stop(recorderTask);
        if (isStop){
            updateStatusByTaskId(taskId, -1);
        }
        return isStop;
    }

    /**
     * 功能描述: 根据图片id创建录制流
     *
     * @return
     * @Param [imageId]
     * @author caijiacheng
     * @date 2020/12/21 18:28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImgRecordTask recordImgByImgId(Integer imageId) {
        Img img = imgMapper.selectByPrimaryKey(imageId);
        if (img == null) {
            log.info("数据库中图片不存在");
            return null;
        }
        String playUrl = myProperties.getRtspPlayUrl() + img.getImgName().split(".")[0];
        ImgRecordTask imgRecordTask = null;
        try {
            imgRecordTask = imgRecordTaskMapper.selectByPrimaryKey(imageId);
            if (imgRecordTask == null) {
                imgRecordTask = new ImgRecordTask();
                imgRecordTask.setImageid(imageId);
                imgRecordTask.setPlayUrl(playUrl);
                imgRecordTask.setStatus(-1);
                imgRecordTask.setCreateTime(LocalDateTime.now());
                imgRecordTask.setUpdateTime(LocalDateTime.now());
                imgRecordTaskMapper.insert(imgRecordTask);
            }
            // 开启录像
            ImageRecord recorder = tasksManager.createRecorder(img.getImgPath(), imgRecordTask.getPlayUrl(), imgRecordTask.getId());
            boolean isStart = tasksManager.start(recorder);
            if (isStart){
                updateStatusByTaskId(imgRecordTask.getId(), 1);
            }
        } catch (Exception e) {
            log.info("开流失败");
            log.error(e.getMessage(), e);
        }

        return imgRecordTask;
    }

    @Override
    public boolean startImgRecordTask(Integer taskId) {
        try {
            // 获取任务
            ImageRecord recorderTask = tasksManager.getRecorderTask(taskId);
            if (recorderTask == null){
                ImgRecordTask imgRecordTask = imgRecordTaskMapper.selectByPrimaryKey(taskId);
                if (imgRecordTask != null){
                    Img img = imgMapper.selectByPrimaryKey(imgRecordTask.getImageid());
                    recorderTask = tasksManager.createRecorder(img.getImgPath(), imgRecordTask.getPlayUrl(), imgRecordTask.getId());
                }
            }
            // 开始任务
            boolean isStart = tasksManager.start(recorderTask);
            if (isStart){
                updateStatusByTaskId(taskId, 1);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean stopByTaskId(Integer taskId) {
        ImageRecord task = tasksManager.getRecorderTask(taskId);
        return tasksManager.stop(task);
    }

    @Override
    public void updateStatusByTaskId(Integer taskId, Integer status) {
        ImgRecordTask imgRecordTask = new ImgRecordTask();
        imgRecordTask.setId(taskId);
        imgRecordTask.setStatus(status);
        imgRecordTask.setUpdateTime(LocalDateTime.now());
        if (status.equals(-1)) {
            imgRecordTask.setEndTime(LocalDateTime.now());
        }
        imgRecordTaskMapper.updateByPrimaryKeySelective(imgRecordTask);
    }

    /**
     * 功能描述: 更新所有任务状态
     *
     * @return
     * @Param []
     * @author caijiacheng
     * @date 2020/12/25 10:23
     */
    @Override
    public void updateStatusAllDate() {
        List<ImgRecordTask> imgRecordTasks = imgRecordTaskMapper.selectAll();
        imgRecordTasks.forEach(e -> {
            e.setStatus(-1);
            e.setUpdateTime(LocalDateTime.now());
            e.setEndTime(LocalDateTime.now());
        });
        try {
            updateBatch(imgRecordTasks);
        } catch (Exception e) {
            log.error("更新所有任务为暂停状态失败");
        }
        log.info("更新成功{}条数据", imgRecordTasks.size());
    }

}
