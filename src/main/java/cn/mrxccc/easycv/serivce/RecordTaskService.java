package cn.mrxccc.easycv.serivce;

import java.util.List;
import cn.mrxccc.easycv.domain.ImgRecordTask;
public interface RecordTaskService{


    int updateBatch(List<ImgRecordTask> list);

    int updateBatchSelective(List<ImgRecordTask> list);

    int batchInsert(List<ImgRecordTask> list);

    int insertOrUpdate(ImgRecordTask record);

    int insertOrUpdateSelective(ImgRecordTask record);

}
