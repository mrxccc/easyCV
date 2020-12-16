package cn.mrxccc.easycv.serivce.impl;

import cn.mrxccc.easycv.imagepusher.ImagePusher;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.mrxccc.easycv.mapper.ImgMapper;
import java.util.List;
import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.serivce.ImgService;
@Service
@Slf4j
public class ImgServiceImpl implements ImgService{

    @Resource
    private ImgMapper imgMapper;

    @Value("rtsp.url:rtsp://localhost/testVideoStream")
    private String rtspUrl;

    @Override
    public int updateBatch(List<Img> list) {
        return imgMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<Img> list) {
        return imgMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<Img> list) {
        return imgMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(Img record) {
        return imgMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(Img record) {
        return imgMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<Img> selectAll() {
        return imgMapper.selectAll();
    }

}
