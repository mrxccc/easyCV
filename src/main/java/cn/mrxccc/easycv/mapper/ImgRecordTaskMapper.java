package cn.mrxccc.easycv.mapper;

import cn.mrxccc.easycv.domain.ImgRecordTask;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

public interface ImgRecordTaskMapper extends MyMapper<ImgRecordTask> {
    int updateBatch(List<ImgRecordTask> list);

    int updateBatchSelective(List<ImgRecordTask> list);

    int batchInsert(@Param("list") List<ImgRecordTask> list);

    int insertOrUpdate(ImgRecordTask record);

    int insertOrUpdateSelective(ImgRecordTask record);
}