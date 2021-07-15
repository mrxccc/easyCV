package cn.mrxccc.easycv.serivce;

import java.util.List;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import cn.mrxccc.easycv.recorder.ImageRecord;

public interface ImgRecordTaskService {

    int updateBatch(List<ImgRecordTask> list);

    int updateBatchSelective(List<ImgRecordTask> list);

    int batchInsert(List<ImgRecordTask> list);

    int insertOrUpdate(ImgRecordTask record);

    int insertOrUpdateSelective(ImgRecordTask record);

    ImgRecordTask recordImgByImgId(Integer imageId);

    boolean stopByTaskId(Integer taskId);

    void updateStatusByTaskId(Integer taskId, Integer status);

    void updateStatusAllDate();

    boolean startImgRecordTask(Integer taskId);

    List<ImgRecordTaskDto> selectImgRecordTask();

    ImageRecord getImgRecordTask(Integer taskId);

    boolean stopImgRecordTask(Integer taskId);

    ImgRecordTask selectTaskById(Integer taskId);

    void deleteTaskById(Integer taskId);
}
