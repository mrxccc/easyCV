package cn.mrxccc.easycv.serivce.impl;

import cn.mrxccc.easycv.cache.DefaultCache;
import cn.mrxccc.easycv.cache.RecordInfoStorage;
import cn.mrxccc.easycv.entity.RecordTask;
import cn.mrxccc.easycv.serivce.RecordServiceTemplate;
import org.bytedeco.ffmpeg.global.avcodec;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
@Service("recordService")
public class RecordServiceImpl extends RecordServiceTemplate {

    @Override
    public RecordTask recordImg(String src, String out) throws IOException, Exception {
        init();
        RecordTask task = manager.createImageRecorder(src, out, avcodec.AV_CODEC_ID_JPEG2000);
        if (task != null) {
            manager.start(task);
        }
        return task;
    }

    @Override
    public RecordInfoStorage initCache() {
        DefaultCache cache=new DefaultCache(maxSize);
        return cache;
    }

}
