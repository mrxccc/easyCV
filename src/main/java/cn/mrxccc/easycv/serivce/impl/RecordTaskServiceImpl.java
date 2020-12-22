package cn.mrxccc.easycv.serivce.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.mrxccc.easycv.mapper.ImgRecordTaskMapper;
import java.util.List;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.serivce.RecordTaskService;
@Service
public class RecordTaskServiceImpl implements RecordTaskService{

    @Resource
    private ImgRecordTaskMapper imgRecordTaskMapper;

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

}
