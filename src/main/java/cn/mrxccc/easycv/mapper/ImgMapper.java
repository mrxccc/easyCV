package cn.mrxccc.easycv.mapper;

import cn.mrxccc.easycv.domain.Img;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

public interface ImgMapper extends MyMapper<Img> {
    int updateBatch(List<Img> list);

    int updateBatchSelective(List<Img> list);

    int batchInsert(@Param("list") List<Img> list);

    int insertOrUpdate(Img record);

    int insertOrUpdateSelective(Img record);
}