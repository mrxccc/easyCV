package cn.mrxccc.easycv.cache;

import cn.mrxccc.easycv.entity.RecordInfo;

import java.util.List;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
public interface RecordInfoStorage {
    boolean save(RecordInfo info);

    List<RecordInfo> list();

    RecordInfo get(int id);
}
