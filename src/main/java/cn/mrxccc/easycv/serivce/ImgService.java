package cn.mrxccc.easycv.serivce;

import java.util.List;
import cn.mrxccc.easycv.domain.Img;

public interface ImgService{

    int updateBatch(List<Img> list);

    int updateBatchSelective(List<Img> list);

    int batchInsert(List<Img> list);

    int insertOrUpdate(Img record);

    int insertOrUpdateSelective(Img record);

    List<Img> selectAll();
}
