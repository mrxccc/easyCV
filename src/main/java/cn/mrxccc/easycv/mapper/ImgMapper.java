package cn.mrxccc.easycv.mapper;

import cn.mrxccc.easycv.domain.Img;
import java.util.List;

import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.dto.ImgRecordTaskDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

public interface ImgMapper extends MyMapper<Img> {
    int updateBatch(List<Img> list);

    int updateBatchSelective(List<Img> list);

    int batchInsert(@Param("list") List<Img> list);

    int insertOrUpdate(Img record);

    int insertOrUpdateSelective(Img record);

    Integer countTaskByImageId(@Param("imageId") Integer imageId);

    List<ImgRecordTaskDto> selectImgRecordTask();

    void batchDelete(@Param("imageIds") List<Integer> imageIds);
}