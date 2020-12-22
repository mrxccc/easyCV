package cn.mrxccc.easycv.serivce;

import cn.mrxccc.easycv.cache.RecordInfoStorage;
import cn.mrxccc.easycv.domain.ImgRecordTask;
import cn.mrxccc.easycv.entity.RecordInfo;
import cn.mrxccc.easycv.entity.RecordTask;

import java.io.IOException;
import java.util.List;

/**
 * 录像服务接口
 * @author mrxccc
 * @create 2020/12/16
 */
public interface RecordService {

    RecordTask record(String src, String out) throws IOException, Exception;

    boolean stop(int id);

    List<?> list(boolean isWork);

    RecordInfo get(Integer id);

    RecordInfoStorage initCache();

    void init();
}
