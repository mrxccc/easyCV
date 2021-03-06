package cn.mrxccc.easycv.serivce.impl;

import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.mrxccc.easycv.mapper.ImgMapper;
import java.util.List;
import cn.mrxccc.easycv.domain.Img;
import cn.mrxccc.easycv.serivce.ImgService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService{

    @Resource
    private ImgMapper imgMapper;

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
    @Transactional
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

    @Override
    public Integer hasTask(Integer imageId) {
        return imgMapper.countTaskByImageId(imageId);
    }

    @Override
    public List<ImgRecordTaskDto> selectImgRecordTask() {
        return imgMapper.selectImgRecordTask();
    }

    @Override
    public Img getImageById(Integer imageId) {
        return imgMapper.selectByPrimaryKey(imageId);
    }

    @Override
    @Transactional
    public void deleteByImageIds(List<Integer> imageIds) {
        imgMapper.batchDelete(imageIds);
    }


}
