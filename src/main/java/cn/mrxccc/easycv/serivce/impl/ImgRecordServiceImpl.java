package cn.mrxccc.easycv.serivce.impl;

import cn.mrxccc.easycv.cache.DefaultCache;
import cn.mrxccc.easycv.cache.RecordInfoStorage;
import cn.mrxccc.easycv.config.MyProperties;
import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.entity.RecordTask;
import cn.mrxccc.easycv.mapper.ImgMapper;
import cn.mrxccc.easycv.mapper.ImgRecordTaskMapper;
import cn.mrxccc.easycv.query.PushersQuery;
import cn.mrxccc.easycv.serivce.EasyDarwin;
import cn.mrxccc.easycv.serivce.ImgRecordServiceTemplate;
import cn.mrxccc.easycv.vo.EasyDarwinPushers;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author mrxccc
 * @create 2020/12/21
 */
@Service("imgRecordService")
@Slf4j
public class ImgRecordServiceImpl extends ImgRecordServiceTemplate {

    @Autowired
    ImgRecordTaskMapper imgRecordTaskMapper;

    @Autowired
    ImgMapper imgMapper;

    @Autowired
    MyProperties myProperties;

    @Autowired
    EasyDarwin easyDarwin;

    @Override
    public RecordTask record(String src, String out) throws IOException, Exception {
        RecordTask task = manager.createImageRecorder(src, out, avcodec.AV_CODEC_ID_JPEG2000);
        if (task != null) {
            manager.start(task);
        }
        return task;
    }

    @Override
    public EasyDarwinPushers list(PushersQuery pushersQuery) {
        return easyDarwin.getpPushers(pushersQuery);
    }

    /**
     * 功能描述: 根据图片id创建录制流
     * @Param [imageId]
     * @return
     * @author caijiacheng
     * @date 2020/12/21 18:28
     */
    @Transactional(rollbackFor = Exception.class)
    public ImgRecordTask recordImgById(Integer imageId) {
        Img img = imgMapper.selectByPrimaryKey(imageId);
        if (img == null) {
            log.info("数据库中图片不存在");
            return null;
        }
        String playUrl = myProperties.getRtspPlayUrl() + "/" + img.getImgName();
        ImgRecordTask imgRecordTask = null;
        try {
            init();
            RecordTask recordTask = manager.createImageRecorder(img.getImgPath(), playUrl, avcodec.AV_CODEC_ID_JPEG2000);
            if (recordTask != null) {
                manager.start(recordTask);
            } else {
                log.info("开流失败,线程池中数量已满，且空闲池已空，或超出限制数量");
                return null;
            }
            ImgRecordTask imgRecordTask1 = imgRecordTaskMapper.selectByPrimaryKey(imageId);
            if (imgRecordTask1 == null){
                imgRecordTask = new ImgRecordTask();
                imgRecordTask.setImageid(imageId);
                imgRecordTask.setPlayUrl(playUrl);
                imgRecordTask.setStatus(recordTask.getStatus());
                imgRecordTask.setCreateTime(LocalDateTime.now());
                imgRecordTask.setUpdateTime(LocalDateTime.now());
                imgRecordTaskMapper.insert(imgRecordTask);
            }

        } catch (Exception e) {
            log.info("开流失败");
            log.error(e.getMessage(), e);
        }

        return imgRecordTask;
    }

}
